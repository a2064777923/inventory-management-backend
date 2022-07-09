package com.nju.edu.erp.web.controller;

import com.nju.edu.erp.auth.Authorized;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.model.vo.discount.DiscountVO;
import com.nju.edu.erp.service.DiscountService;
import com.nju.edu.erp.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping(path = "/discount")
public class DiscountController {
    public DiscountService discountService;

    @Autowired
    public DiscountController(DiscountService discountService) {
        this.discountService = discountService;
    }

    @Authorized(roles = {Role.GM, Role.ADMIN})
    @PostMapping(value = "/discount-make")
    public Response makeDiscount(@RequestBody DiscountVO discountVO) throws ParseException {
        discountService.setDiscount(discountVO);
        return Response.buildSuccess();
    }

    @Authorized(roles = {Role.GM, Role.ADMIN})
    @DeleteMapping(value = "discount-delete")
    public Response deleteDiscount(@RequestParam Integer id) throws ParseException {
        discountService.deleteDiscount(id);
        return Response.buildSuccess();
    }

    @Authorized(roles = {Role.GM, Role.ADMIN})
    @GetMapping(value = "/discount-show")
    public Response showDiscount() {
        return Response.buildSuccess(discountService.findAll());
    }
}
