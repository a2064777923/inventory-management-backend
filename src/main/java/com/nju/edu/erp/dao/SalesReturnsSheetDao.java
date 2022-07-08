package com.nju.edu.erp.dao;

import com.nju.edu.erp.enums.sheetState.SalesReturnsSheetState;
import com.nju.edu.erp.model.po.SalesReturnsSheetPO;
import com.nju.edu.erp.model.po.SalesReturnsSheetContentPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper

/**
 * @author Sun Sihan
 * @date 2022/6/27
 */
public interface SalesReturnsSheetDao {
    /**
     * 获取最近一条销售退货单
     * @return 最近一条销售退货单
     */
    SalesReturnsSheetPO getLatest();

    /**
     * 存入一条销售退货单记录
     * @param toSave 一条销售退货单记录
     * @return 影响的行数
     */
    int save(SalesReturnsSheetPO toSave);

    /**
     * 把销售退货单上的具体内容存入数据库
     * @param SalesReturnsSheetContent 销售退货单上的具体内容
     */
    void saveBatch(List<SalesReturnsSheetContentPO> SalesReturnsSheetContent);

    /**
     * 返回所有销售退货单
     * @return 销售退货单列表
     */
    List<SalesReturnsSheetPO> findAll();

    /**
     * 根据state返回销售退货单
     * @param state 销售退货单状态
     * @return 销售退货单列表
     */
    List<SalesReturnsSheetPO> findAllByState(SalesReturnsSheetState state);

    /**
     * 根据 salesReturnsSheetId 找到条目， 并更新其状态为state
     * @param salesReturnsSheetId 销售退货单id
     * @param state 销售退货单状态
     * @return 影响的条目数
     */
    int updateState(String salesReturnsSheetId, SalesReturnsSheetState state);

    /**
     * 根据 salesReturnsSheetId 和 prevState 找到条目， 并更新其状态为state
     * @param salesReturnsSheetId 销售退货单id
     * @param prevState 销售退货单之前的状态
     * @param state 销售退货单状态
     * @return 影响的条目数
     */
    int updateStateV2(String salesReturnsSheetId, SalesReturnsSheetState prevState, SalesReturnsSheetState state);

    /**
     * 通过salesReturnsSheetId找到对应条目
     * @param salesReturnsSheetId 销售退货单id
     * @return
     */
    SalesReturnsSheetPO findOneById(String salesReturnsSheetId);

    /**
     * 通过salesReturnsSheetId找到对应的content条目
     * @param salesReturnsSheetId
     * @return
     */
    List<SalesReturnsSheetContentPO> findContentBySalesReturnsSheetId(String salesReturnsSheetId);

    /**
     * 通过salesReturnsSheetId找到退的货的对应批次
     * @param salesReturnsSheetId
     * @return 批次号
     */
    Integer findBatchId(String salesReturnsSheetId);
}
