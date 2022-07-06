package com.nju.edu.erp.dao;

import com.nju.edu.erp.enums.sheetState.PaymentSheetState;
import com.nju.edu.erp.model.po.CollectionSheetContentPO;
import com.nju.edu.erp.model.po.CollectionSheetPO;
import com.nju.edu.erp.model.po.PaymentSheetContentPO;
import com.nju.edu.erp.model.po.PaymentSheetPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Hong ZiXian
 * @date 2022/7/4
 */
@Repository
@Mapper
public interface PaymentSheetDao {
    /**
     * 获取最近一条付款单
     * @return 最近一条付款单
     */
    PaymentSheetPO getLatest();

    /**
     * 存入一条付款单记录
     * @param toSave 一条进货单记录
     * @return 影响的行数
     */
    int save(PaymentSheetPO toSave);

    /**
     * 把收款单上的具体内容存入数据库
     * @param paymentSheetContent 进货单上的具体内容
     */
    void saveBatch(List<PaymentSheetContentPO> paymentSheetContent);

    /**
     * 获取所有收款单
     */
    List<PaymentSheetPO> findAll();
    /**
     * 依狀态获取
     */
    List<PaymentSheetPO> findAllByState(PaymentSheetState state);

    List<PaymentSheetContentPO> findContentByPaymentSheetId(String paymentSheetId);


    /**
     * 用id获取付款单
     * @param paymentSheetId
     * @return
     */
    PaymentSheetPO findOneById(String paymentSheetId);

    /**
     * 更新狀态
     * @param paymentSheetId
     * @param state
     * @return
     */
    int updateState(String paymentSheetId, PaymentSheetState state);
}
