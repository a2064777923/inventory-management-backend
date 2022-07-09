package com.nju.edu.erp.service;

import com.nju.edu.erp.enums.sheetState.SaleReturnsSheetState;
import com.nju.edu.erp.model.vo.SaleReturns.SaleReturnsSheetVO;
import com.nju.edu.erp.model.vo.UserVO;

import java.util.List;

public interface SaleReturnsService {
    /**
     * 指定销售退货单
     * @param userVO
     * @param saleReturnsSheetVO
     */
    void makeSaleReturnsSheet(UserVO userVO, SaleReturnsSheetVO saleReturnsSheetVO);

    /**
     * 根据单据状态获取销售退货单
     * @param state
     * @return
     */
    List<SaleReturnsSheetVO> getSaleReturnsSheetByState(SaleReturnsSheetState state);

    /**
     * 审批单据
     * @param saleReturnsSheetId
     * @param state
     */
    void approval(String saleReturnsSheetId, SaleReturnsSheetState state);



}
