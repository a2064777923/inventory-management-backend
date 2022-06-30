package com.nju.edu.erp.service;

import com.nju.edu.erp.enums.sheetState.PurchaseReturnsSheetState;
import com.nju.edu.erp.enums.sheetState.SalesReturnsSheetState;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.model.vo.SaleReturns.SalesReturnsSheetContentVO;
import com.nju.edu.erp.model.vo.SaleReturns.SalesReturnsSheetVO;
import com.nju.edu.erp.model.vo.purchaseReturns.PurchaseReturnsSheetVO;

import java.util.List;

/**
 * @author Sun Sihan
 * @date 2022/6/27
 */
// 制定销售退货单 + 销售经理审批/总经理二级审批 + 更新客户表 + 更新库存
public interface SalesReturnsService {
    /**
     * 制定销售退货单
     * @param salesReturnsSheetVO 销售退货单
     */
    void makeSalesReturnsSheet(UserVO userVO, SalesReturnsSheetVO salesReturnsSheetVO);

    /**
     * 根据状态获取销售退货单(state == null 则获取所有销售退货单)
     * @param state 销售退货单状态
     * @return 销售退货单
     */
    List<SalesReturnsSheetVO> getSalesReturnsSheetByState(SalesReturnsSheetState state);

    /**
     * 根据销售退货单id进行审批(state == "待二级审批"/"审批完成"/"审批失败")
     * 在controller层进行权限控制
     * @param salesReturnsSheetId 销售退货单id
     * @param state 销售退货单修改后的状态
     */
    void approval(String salesReturnsSheetId, SalesReturnsSheetState state);
}
