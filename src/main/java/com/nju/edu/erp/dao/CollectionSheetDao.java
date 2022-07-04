package com.nju.edu.erp.dao;

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

}
