package com.nju.edu.erp.service.Impl;

import com.nju.edu.erp.dao.DiscountDao;
import com.nju.edu.erp.model.po.DiscountPO;
import com.nju.edu.erp.service.DiscountStrategyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class DiscountStrategy1 implements DiscountStrategyService {
    private final DiscountDao discountDao;

    @Autowired
    public DiscountStrategy1(DiscountDao discountDao) {
        this.discountDao = discountDao;
    }

    @Override
    public void makeDiscount(DiscountPO discountPO) {
        BigDecimal discount = discountPO.getDiscount();
        BigDecimal voucherAmount = discountPO.getVoucherAmount();
        Integer level = discountPO.getCustomerLevel();
        List<DiscountPO> listLess = discountDao.getLessL(level);
        List<DiscountPO> listLarger = discountDao.getLargerL(level);

        if(listLess != null) {
            for (DiscountPO i : listLess) {
                if (i.getDiscount().compareTo(discount) == -1) {
                    voucherAmount = i.getVoucherAmount();
                }
                if (i.getVoucherAmount().compareTo(voucherAmount) == 1) {
                    voucherAmount = i.getVoucherAmount();
                }
            }
        }

        if(listLarger != null) {
            for (DiscountPO i : listLarger) {
                if (i.getDiscount().compareTo(discount) == 1) {
                    discount = i.getDiscount();
                }
                if (i.getVoucherAmount().compareTo(voucherAmount) == -1) {
                    voucherAmount = i.getVoucherAmount();
                }
            }
        }

        discountPO.setDiscount(discount);
        discountPO.setVoucherAmount(voucherAmount);
        if(discountDao.getByLevel(level) != null) {
            discountDao.updateByLevel(discountPO);
        } else {
            discountDao.createDiscount(discountPO);
        }
    }
}
