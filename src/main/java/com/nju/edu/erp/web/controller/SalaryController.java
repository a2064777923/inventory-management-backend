package com.nju.edu.erp.web.controller;


import com.nju.edu.erp.auth.Authorized;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.enums.sheetState.PaymentSheetState;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.model.vo.financial.CollectionVO;
import com.nju.edu.erp.model.vo.financial.SalarySheetVO;
import com.nju.edu.erp.service.PaymentService;
import com.nju.edu.erp.service.SalaryService;
import com.nju.edu.erp.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Hong ZiXian
 * @date 2022/7/6
 */
@RestController
@RequestMapping(path = "/api/salary")
public class SalaryController {
    private final SalaryService salaryService;

    @Autowired
    public SalaryController(SalaryService salaryService) { this.salaryService = salaryService;}

    /**
     * 獲取全部员工信息
     */
    @GetMapping("/staff-info-all")
    @Authorized(roles = {Role.ADMIN,  Role.FINANCIAL_STAFF})
    public Response getAllStaffInfo() {
        return Response.buildSuccess(salaryService.getAllStaffInfo());
    }

    /**
     * 獲取全部工资單
     */
    @GetMapping("/salary-sheet-all")
    @Authorized(roles = {Role.ADMIN,  Role.FINANCIAL_STAFF})
    public Response getAllSalary(@RequestParam(value = "state", required = false) PaymentSheetState state) {
        return Response.buildSuccess(salaryService.getAllSalary(state));
    }

    /**
     * 创建工资单
     * @param salarySheetVO 收款单
     */
    @PostMapping("/salary-sheet-make")
    @Authorized(roles = {Role.ADMIN,  Role.FINANCIAL_STAFF})
    public Response createCollectionSheet(UserVO userVO , @RequestBody SalarySheetVO salarySheetVO){
        salaryService.createSalarySheet(userVO, salarySheetVO);
        return Response.buildSuccess();
    }

    /**
     * 总经理审批工资单
     * @param salarySheetId 工资单id
     * @param state 修改后的状态("审批失败"/"审批完成")
     */
    @Authorized (roles = {Role.GM, Role.ADMIN})
    @GetMapping(value = "/salary-sheet-approval")
    public Response salaryApproval(@RequestParam("salarySheetId") String salarySheetId,
                                       @RequestParam("state") PaymentSheetState state)  {
        if(state.equals(PaymentSheetState.FAILURE) || state.equals(PaymentSheetState.SUCCESS)) {
            salaryService.approval(salarySheetId, state);
            return Response.buildSuccess();
        } else {
            return Response.buildFailed("000000","操作失败"); // code可能得改一个其他的
        }
    }
}
