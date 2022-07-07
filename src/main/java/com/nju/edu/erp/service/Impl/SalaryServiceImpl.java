package com.nju.edu.erp.service.Impl;

import com.nju.edu.erp.dao.SalarySheetDao;
import com.nju.edu.erp.enums.sheetState.PaymentSheetState;
import com.nju.edu.erp.model.po.*;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.model.vo.financial.*;
import com.nju.edu.erp.service.SalaryService;
import com.nju.edu.erp.utils.IdGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Hong ZiXian
 * @date 2022/7/7
 */
@Service
public class SalaryServiceImpl implements SalaryService {
    SalarySheetDao salarySheetDao;
    private static int[] personalIncomeList = new int[]{5000,8000,17000,30000,40000,60000,85000};
    private static int[] personalTaxList = new int[]{0,3,10,20,25,30,35,45};
    private static int[] quickSubList = new int[]{0,0,210,1410,2660,4410,7160,15160};


    @Autowired
    public SalaryServiceImpl(SalarySheetDao salarySheetDao){
        this.salarySheetDao = salarySheetDao;
    }
    /**
     * 獲取全部员工薪资訊息
     *
     * @return
     */
    @Override
    @Transactional
    public List<StaffWageInfoVO> getAllStaffInfo() {
        List<StaffWageInfoPO> wagePO;
        List<StaffWageInfoVO>res = new ArrayList<>();
        wagePO = salarySheetDao.findAllStaff();
        for(StaffWageInfoPO p :wagePO){
            StaffWageInfoVO v = new StaffWageInfoVO();
            BeanUtils.copyProperties(p,v);
            //失业保險按每月收入2%
            BigDecimal uEInsurance = p.getWage().multiply( BigDecimal.valueOf(0.02));
            v.setUnemploymentInsurance(uEInsurance);

            //住房公积金12%
            BigDecimal housingFund = p.getWage().multiply( BigDecimal.valueOf(0.12));
            v.setHousingFund(housingFund);
            //表駆动算个人所得稅
            int tax = personalTaxList[personalTaxList.length-1];
            int quickSub = quickSubList[quickSubList.length-1];
            for(int i = 0; i <personalIncomeList.length; i++){
                if(v.getWage().compareTo(BigDecimal.valueOf(personalIncomeList[i]))<0){
                    tax = personalTaxList[i];
                    quickSub = quickSubList[i];
                    break;
                }
            }
            BigDecimal personalIncomeTax = (p.getWage().subtract(uEInsurance).subtract(housingFund)
                                           .subtract(BigDecimal.valueOf(5000.00))).multiply((BigDecimal.valueOf(tax)
                                            .divide(BigDecimal.valueOf(100)))).subtract(BigDecimal.valueOf(quickSub));
            if(personalIncomeTax.compareTo(BigDecimal.ZERO) < 0){
                personalIncomeTax = BigDecimal.ZERO;
            }

            v.setPersonalIncomeTax(personalIncomeTax);
            v.setFinalSalary(p.getWage().subtract(uEInsurance).subtract(housingFund).subtract(personalIncomeTax));
            res.add(v);
        }
        return res;
    }

    /**
     * 创建工资单
     *
     * @param userVO
     * @param salarySheetVO
     */
    @Override
    @Transactional
    public void createSalarySheet(UserVO userVO, SalarySheetVO salarySheetVO) {
        SalarySheetPO salarySheetPO = new SalarySheetPO();
        BeanUtils.copyProperties(salarySheetVO, salarySheetPO);
        // 此处根据制定单据人员确定操作员
        salarySheetPO.setOperator(userVO.getName());
        salarySheetPO.setCreateTime(new Date());
        SalarySheetPO latest = salarySheetDao.getLatest();
        String id = IdGenerator.generateSheetId(latest == null ? null : latest.getId(), "GZD");
        salarySheetPO.setId(id);
        salarySheetPO.setState(PaymentSheetState.PENDING_MANAGER);
        BigDecimal totalAmount = BigDecimal.ZERO;
        List<SalarySheetContentPO> salaryContentPOList = new ArrayList<>();
        for(SalarySheetContentVO content : salarySheetVO.getStaffSheetContent()) {
            SalarySheetContentPO salarySheetContentPO = new SalarySheetContentPO();
            BeanUtils.copyProperties(content,salarySheetContentPO);
            salarySheetContentPO.setSalarySheetId(id);
            salaryContentPOList.add(salarySheetContentPO);
            totalAmount = totalAmount.add(salarySheetContentPO.getFinalSalary());
        }
        salarySheetDao.saveBatch(salaryContentPOList);
        salarySheetPO.setTotalAmount(totalAmount);
        salarySheetDao.save(salarySheetPO);
    }

    /**
     * 獲取全部工资单，有state依state獲，沒就獲全部
     *
     * @param state
     * @return
     */
    @Override
    @Transactional
    public List<SalarySheetVO> getAllSalary(PaymentSheetState state) {
        List<SalarySheetVO> res = new ArrayList<>();
        List<SalarySheetPO> all;
        if(state == null) {
            all = salarySheetDao.findAll();
        } else {
            all = salarySheetDao.findAllByState(state);
        }
        for(SalarySheetPO po: all) {
            SalarySheetVO vo = new SalarySheetVO();
            BeanUtils.copyProperties(po, vo);
            List<SalarySheetContentPO> alll = salarySheetDao.findContentBySalarySheetId(po.getId());
            List<SalarySheetContentVO> vos = new ArrayList<>();
            for (SalarySheetContentPO p : alll) {
                SalarySheetContentVO v = new SalarySheetContentVO();
                BeanUtils.copyProperties(p, v);
                vos.add(v);
            }
            vo.setStaffSheetContent(vos);
            res.add(vo);
        }
        return res;
    }

    /**
     * @param salarySheetId 工资单id
     * @param state         更改后狀态
     */
    @Override
    @Transactional
    public void approval(String salarySheetId, PaymentSheetState state) {
        if(state.equals(PaymentSheetState.FAILURE)) {
            SalarySheetPO salarySheet = salarySheetDao.findOneById(salarySheetId);
            if(salarySheet.getState() == PaymentSheetState.SUCCESS) throw new RuntimeException("状态更新失败");
            int effectLines = salarySheetDao.updateState(salarySheetId, state);
            if(effectLines == 0) throw new RuntimeException("状态更新失败");
        } else {
            if(state.equals(PaymentSheetState.SUCCESS)) {
                int effectLines = salarySheetDao.updateState(salarySheetId, state);
                if(effectLines == 0) throw new RuntimeException("状态更新失败");
            } else {
                throw new RuntimeException("状态更新失败");
            }

        }
        if(state.equals(PaymentSheetState.SUCCESS)) {

        }
    }
}
