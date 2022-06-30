package com.nju.edu.erp.dao;

import com.nju.edu.erp.model.po.BankPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Hong ZiXian
 * @date 2022/6/29
 */

@Repository
@Mapper
public interface BankDao {

    List<BankPO> getAllBank();
    void createBankAccount(BankPO bankPO);
    int editBankAccount(BankPO bankPO);
    int deleteBankAccount(String id);
}
