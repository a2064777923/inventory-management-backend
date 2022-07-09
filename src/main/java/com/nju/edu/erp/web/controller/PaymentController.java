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
     * 獲取全部收款單
     */
    @GetMapping("/collection-get-all")
    @Authorized(roles = {Role.ADMIN,  Role.GM,Role.FINANCIAL_STAFF})
    public Response getAllCollection(@RequestParam(value = "state", required = false) PaymentSheetState state) {
        return Response.buildSuccess(paymentService.getAllCollection(state));
    }
    /**
     * 獲取全部付款單
     */
    @GetMapping("/payment-get-all")
    @Authorized(roles = {Role.ADMIN,  Role.GM,Role.FINANCIAL_STAFF})
    public Response getAllPayment(@RequestParam(value = "state", required = false) PaymentSheetState state) {
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

    /**
     * 总经理审批收付款单
     * @param transferSheetId 收付款单id
     * @param state 修改后的状态("审批失败"/"审批完成")
     */
    @Authorized (roles = {Role.GM, Role.ADMIN})
    @GetMapping(value = "/transfer-sheet-approval")
    public Response collectionApproval(@RequestParam("transferSheetId") String transferSheetId,
                                       @RequestParam("state") PaymentSheetState state)  {
        if(state.equals(PaymentSheetState.FAILURE) || state.equals(PaymentSheetState.SUCCESS)) {
            paymentService.approval(transferSheetId, state);
            return Response.buildSuccess();
        } else {
            return Response.buildFailed("000000","操作失败"); // code可能得改一个其他的
        }
    }


}
