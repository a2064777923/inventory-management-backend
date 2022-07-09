package com.nju.edu.erp.service.Impl;

import com.nju.edu.erp.dao.DiscountDao;
import com.nju.edu.erp.model.po.DiscountPO;
import com.nju.edu.erp.service.DiscountStrategyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class DiscountStrategy3 implements DiscountStrategyService {
    private final DiscountDao discountDao;

    @Autowired
    public DiscountStrategy3(DiscountDao discountDao) {
        this.discountDao = discountDao;
    }

    @Override
    public void makeDiscount(DiscountPO discountPO) {
        BigDecimal discount = discountPO.getDiscount();
        BigDecimal voucher = discountPO.getVoucherAmount();
        BigDecimal amount = discountPO.getTotalAmount();

        List<DiscountPO> listLessA = discountDao.getLessA(amount);
        List<DiscountPO> listLargerA = discountDao.getLargerA(amount);

        if (listLessA != null) {
            for (DiscountPO i : listLessA) {
                if (i.getDiscount().compareTo(discount) == -1) {
                    discount = i.getDiscount();
                }
                if (i.getVoucherAmount() != null && i.getVoucherAmount().compareTo(voucher) == 1) {
                    voucher = i.getVoucherAmount();
                }
            }
        }

        if (listLargerA != null) {
            for (DiscountPO i : listLargerA) {
                if (i.getDiscount().compareTo(discount) == 1) {
                    discount = i.getDiscount();
                }
                if (voucher != null && i.getVoucherAmount().compareTo(voucher) == -1) {
                    voucher = i.getVoucherAmount();
                }
            }
        }

        discountPO.setDiscount(discount);
        discountPO.setVoucherAmount(voucher);
        if (discountDao.getByAmount(amount) != null) {
            discountDao.updateByAmount(discountPO);
        } else {
            discountDao.createDiscount(discountPO);
        }
    }
}
