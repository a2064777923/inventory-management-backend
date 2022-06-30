package com.nju.edu.erp.service.Impl;

import com.nju.edu.erp.dao.BankDao;
import com.nju.edu.erp.dao.CustomerDao;
import com.nju.edu.erp.exception.MyServiceException;
import com.nju.edu.erp.model.po.BankPO;
import com.nju.edu.erp.model.vo.financial.BankVO;
import com.nju.edu.erp.service.BankService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Hong ZiXian
 * @date 2022/6/29
 */
@Service
public class BankServiceImpl implements BankService {

    private final BankDao bankDao;

    @Autowired
    public BankServiceImpl(BankDao bankDao) {
        this.bankDao = bankDao;
    }

    @Override
    public List<BankPO> getAllBank() {
        return bankDao.getAllBank();
    }

    @Override
    public void createBankAccount(BankVO bankVO) {
        BankPO savePO = new BankPO();
        BeanUtils.copyProperties(bankVO,savePO);
        bankDao.createBankAccount(savePO);

    }

    @Override
    public void editBankAccount(BankVO bankVO) {
        BankPO savePO = new BankPO();
        BeanUtils.copyProperties(bankVO,savePO);
        int ans = bankDao.editBankAccount(savePO);
        if (ans == 0) {
            throw new MyServiceException("B0003", "更新失败！");
        }
    }

    @Override
    public void deleteBankAccount(String id) {
        int ans = bankDao.deleteBankAccount(id);
        if (ans == 0) {
            throw new MyServiceException("B0004", "删除失败！");
        }
    }
}
