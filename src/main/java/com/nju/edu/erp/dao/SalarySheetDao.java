package com.nju.edu.erp.dao;


import com.nju.edu.erp.enums.sheetState.PaymentSheetState;
import com.nju.edu.erp.model.po.CollectionSheetPO;
import com.nju.edu.erp.model.po.SalarySheetContentPO;
import com.nju.edu.erp.model.po.SalarySheetPO;
import com.nju.edu.erp.model.po.StaffWageInfoPO;
import com.nju.edu.erp.model.vo.financial.SalarySheetVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Hong ZiXian
 * @date 2022/7/7
 */
@Repository
@Mapper
public interface SalarySheetDao {

    /**
     *     獲取全部职工信息
     */
    List<StaffWageInfoPO> findAllStaff();

    /**
     * 獲取最新工资单
     * @return
     */
    SalarySheetPO getLatest();

    /**
     * 存具体工资發放项
     * @param salaryContentPOList
     */
    void saveBatch(List<SalarySheetContentPO> salaryContentPOList);

    /**
     * 存工资单
     * @param salarySheetPO
     */
    void save(SalarySheetPO salarySheetPO);

    /**
     * 查找
     * @return
     */
    List<SalarySheetPO> findAll();

    List<SalarySheetPO> findAllByState(PaymentSheetState state);
    List<SalarySheetContentPO> findContentBySalarySheetId(String salarySheetId) ;

    int updateState(String salarySheetId, PaymentSheetState state);

    SalarySheetPO findOneById(String salarySheetId);
}
