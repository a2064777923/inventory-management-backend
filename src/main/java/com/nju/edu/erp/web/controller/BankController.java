package com.nju.edu.erp.web.controller;

import com.nju.edu.erp.auth.Authorized;
import com.nju.edu.erp.enums.CustomerType;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.model.vo.financial.BankVO;
import com.nju.edu.erp.service.BankService;
import com.nju.edu.erp.service.CustomerService;
import com.nju.edu.erp.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Hong ZiXian
 * @date 2022/6/29
 */
@RestController
@RequestMapping(path = "/api/bank")
public class BankController {

    private final BankService bankService;

    @Autowired
    public BankController(BankService bankService) {
            this.bankService = bankService;
    }
    @GetMapping("/getAllBank")
    @Authorized(roles = {Role.ADMIN, Role.GM, Role.FINANCIAL_STAFF})
    public Response getAllBank() {
        return Response.buildSuccess(bankService.getAllBank());
    }

    @PostMapping("/bank-make")
    @Authorized(roles = {Role.ADMIN,  Role.FINANCIAL_STAFF})
    public Response createBankAccount(@RequestBody BankVO bankvo){
        bankService.createBankAccount(bankvo);
        return Response.buildSuccess();
    }
    @PostMapping("/bank-edit")
    @Authorized(roles = {Role.ADMIN, Role.FINANCIAL_STAFF})
    public Response editBankAccount(@RequestBody BankVO bankvo){
        bankService.editBankAccount(bankvo);
        return Response.buildSuccess();
    }

    @GetMapping("/bank-delete")
    @Authorized(roles = {Role.ADMIN, Role.FINANCIAL_STAFF})
    public Response deleteBankAccount(@RequestParam String id){
        bankService.deleteBankAccount(id);
        return Response.buildSuccess();
    }

}
