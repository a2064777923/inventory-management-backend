package com.nju.edu.erp.web.controller;

import com.nju.edu.erp.auth.Authorized;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.model.vo.financial.SalarySheetVO;
import com.nju.edu.erp.model.vo.financial.SalesSearchVO;
import com.nju.edu.erp.service.SaleService;
import com.nju.edu.erp.service.SearchSheetService;
import com.nju.edu.erp.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/search-sheet")
public class SearchSheetController {

    private final SearchSheetService searchSheetService;

    @Autowired
    public SearchSheetController(SearchSheetService searchSheetService) {
        this.searchSheetService = searchSheetService;
    }
    /**
     * 獲取銷售业務员
     */
    @GetMapping(value = "/get-all-salesmen")
    @Authorized(roles = {Role.FINANCIAL_STAFF,Role.GM, Role.ADMIN})
    public Response getAllSalesmen(){

        return Response.buildSuccess(searchSheetService.getAllSalesmen());
    }

    @PostMapping(value = "/get-sales-details")
    @Authorized(roles = {Role.FINANCIAL_STAFF, Role.GM, Role.ADMIN})
    public Response getSalesDetails(@RequestBody SalesSearchVO salesSearchVO){

        return Response.buildSuccess(searchSheetService.getSalesDetails(salesSearchVO));
    }
}
