package com.nju.edu.erp.dao;

import com.nju.edu.erp.enums.sheetState.PaymentSheetState;
import com.nju.edu.erp.model.po.CollectionSheetContentPO;
import com.nju.edu.erp.model.po.CollectionSheetPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Hong ZiXian
 * @date 2022/7/4
 */
@Repository
@Mapper
public interface CollectionSheetDao {
    /**
     * 获取最近一条收款单
     * @return 最近一条收款单
     */
    CollectionSheetPO getLatest();

    /**
     * 存入一条收款单记录
     * @param toSave 一条进货单记录
     * @return 影响的行数
     */
    int save(CollectionSheetPO toSave);

    /**
     * 把收款单上的具体内容存入数据库
     * @param collectionSheetContent 进货单上的具体内容
     */
    void saveBatch(List<CollectionSheetContentPO> collectionSheetContent);

    /**
     * 获取所有收款单
     */
    List<CollectionSheetPO> findAll();
    /**
     * 依狀态获取
     */
    List<CollectionSheetPO> findAllByState(PaymentSheetState state);

    List<CollectionSheetContentPO> findContentByCollectionSheetId(String collectionSheetId);

    /**
     * 用id获取收款单
     * @param collectionSheetId
     * @return
     */
    CollectionSheetPO findOneById(String collectionSheetId);

    /**
     * 更新狀态
     * @param collectionSheetId
     * @param state
     * @return
     */
    int updateState(String collectionSheetId, PaymentSheetState state);
}
