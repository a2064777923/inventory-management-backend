package com.nju.edu.erp.dao;

import com.nju.edu.erp.enums.sheetState.SaleReturnsSheetState;
import com.nju.edu.erp.model.po.SaleReturnsSheetContentPO;
import com.nju.edu.erp.model.po.SaleReturnsSheetPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface SaleReturnsSheetDao {
    /**
     * 获取最近一条销售退货单
     * @return 最近一条销售退货单
     */
    SaleReturnsSheetPO getLatestReturnsSheet();

    /**
     * 存入一条销售退货单记录
     * @param toSave 一条销售退货单记录
     * @return 影响的行数
     */
    int saveReturnsSheet(SaleReturnsSheetPO toSave);

    /**
     * 把销售退货单上的具体内容存入数据库
     * @param saleReturnsSheetContent 入销售退货单上的具体内容
     */
    int saveBatchReturnsSheetContent(List<SaleReturnsSheetContentPO> saleReturnsSheetContent);

    /**
     * 查找所有销售退货单
     */
    List<SaleReturnsSheetPO> findAllReturnsSheet();

    /**
     * 查找指定id的销售退货单
     * @param saleReturnsSheetId
     * @return
     */
    SaleReturnsSheetPO findReturnsSheetById(String saleReturnsSheetId);

    /**
     * 根据state返回销售退货单
     * @param state 销售退货单单状态
     * @return 销售退货单列表
     */
    List<SaleReturnsSheetPO> findallReturnsByState(SaleReturnsSheetState state);

    /**
     * 查找指定销售退货单下具体的商品内容
     * @param sale_returns_sheet_id
     */
    List<SaleReturnsSheetContentPO> findReturnsContentBySheetId(String sale_returns_sheet_id);

    /**
     * 更新指定销售退货单的状态
     * @param saleReturnsSheetId
     * @param state
     * @return
     */
    int updateReturnsSheetState(String saleReturnsSheetId, SaleReturnsSheetState state);

    /**
     * 根据当前状态更新销售单状态
     * @param saleReturnsSheetId
     * @param prevState
     * @param state
     * @return
     */
    int updateReturnsSheetStateOnPrev(String saleReturnsSheetId, SaleReturnsSheetState prevState, SaleReturnsSheetState state);

    List<SaleReturnsSheetPO> getSRSByTime(String b, String e);
    List<SaleReturnsSheetPO> getSRSByOperator(String o);
    List<SaleReturnsSheetPO> getSRSBySupplier(Integer c);

    List<SaleReturnsSheetPO> getSRSByTimeEX(String b, String e);

}
