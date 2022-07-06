package com.nju.edu.erp.service;

import com.nju.edu.erp.enums.sheetState.PaymentSheetState;
import com.nju.edu.erp.enums.sheetState.PurchaseSheetState;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.model.vo.financial.CollectionVO;
import com.nju.edu.erp.model.vo.financial.PaymentVO;
import com.nju.edu.erp.model.vo.purchase.PurchaseSheetVO;

import java.util.List;

/**
 * @author Hong ZiXian
 * @date 2022/7/3
 */
// 制定收付款单 + 总经理审批  + 更新帳戶以及应付应收
public interface PaymentService {

    /**
     *
     * @param state 狀态
     * @return
     */
    List<CollectionVO> getAllCollection(PaymentSheetState state);
    List<PaymentVO> getAllPayment(PaymentSheetState state);

    /**
     * 制定收款单
     * @param collectionVO 收款单
     */
    void createCollectionSheet(UserVO userVO, CollectionVO collectionVO);
    /**
     * 制定付款单
     * @param paymentVO 进货单
     */
    void createPaymentSheet(UserVO userVO, PaymentVO paymentVO);

    /**
     * 根据收款单id进行审批
     * @param transferSheetId 收付款单id
     * @param state 审批后狀态
     */
    void approval(String transferSheetId, PaymentSheetState state);
}
