package com.nju.edu.erp.web.controller;


import com.nju.edu.erp.auth.Authorized;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.enums.sheetState.PaymentSheetState;
import com.nju.edu.erp.enums.sheetState.PurchaseSheetState;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.model.vo.financial.BankVO;
import com.nju.edu.erp.model.vo.financial.CollectionVO;
import com.nju.edu.erp.model.vo.financial.PaymentVO;
import com.nju.edu.erp.service.BankService;
import com.nju.edu.erp.service.PaymentService;
import com.nju.edu.erp.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Hong ZiXian
 * @date 2022/7/3
 */

@RestController
@RequestMapping(path = "/api/payment")
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) { this.paymentService = paymentService;}

    /**
     * 獲取全部收付款單
     */
    @GetMapping("/getAllSheet")
    @Authorized(roles = {Role.ADMIN,  Role.FINANCIAL_STAFF})
    public Response getPaymentSheet(@RequestParam(value = "state", required = false) PaymentSheetState state) {
        return Response.buildSuccess(paymentService.getAllPayment(state));
    }

    /**
     * 创建收款单
     * @param collectionVO 收款单
     */
    @PostMapping("/collection-sheet-make")
    @Authorized(roles = {Role.ADMIN,  Role.FINANCIAL_STAFF})
    public Response createCollectionSheet(UserVO userVO ,@RequestBody CollectionVO collectionVO){
        paymentService.createCollectionSheet(userVO, collectionVO);
        return Response.buildSuccess();
    }
    /**
     * 创建付款单
     * @param paymentVO 收付款单
     */
    @PostMapping("/payment-sheet-make")
    @Authorized(roles = {Role.ADMIN,  Role.FINANCIAL_STAFF})
    public Response createPaymentSheet(UserVO userVO ,@RequestBody PaymentVO paymentVO){
        paymentService.createPaymentSheet(userVO, paymentVO);
        return Response.buildSuccess();
    }

}
