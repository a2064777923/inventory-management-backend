package com.nju.edu.erp.service;

import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.model.vo.financial.SalesDetailVO;
import com.nju.edu.erp.model.vo.financial.SalesSearchVO;
import com.nju.edu.erp.model.vo.financial.SearchSheetVO;
import com.nju.edu.erp.model.vo.purchase.PurchaseSheetVO;
import com.nju.edu.erp.model.vo.purchaseReturns.PurchaseReturnsSheetVO;

import java.util.List;

/**
 * @author Hong ZiXian
 * @date 2022/7/8
 */
public interface SearchSheetService {
    /**
     * 獲取全部銷售业務员
     * @return
     */
    List<UserVO> getAllSalesmen();

    List<SalesDetailVO> getSalesDetails(SalesSearchVO salesSearchVO);

    List<PurchaseSheetVO> getPurchaseSheet(SearchSheetVO searchSheetVO);

    List<PurchaseReturnsSheetVO> getPurchaseSheetReturn(SearchSheetVO searchSheetVO);
}
