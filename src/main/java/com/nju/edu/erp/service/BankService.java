package com.nju.edu.erp.service;

import com.nju.edu.erp.model.po.BankPO;
import com.nju.edu.erp.model.vo.financial.BankVO;

import java.util.List;

/**
 * @author Hong ZiXian
 * @date 2022/6/29
 */
public interface BankService {
    List<BankPO> getAllBank();
    void createBankAccount(BankVO bankVO);
    void editBankAccount(BankVO bankVO);
    void deleteBankAccount(String id);
}
