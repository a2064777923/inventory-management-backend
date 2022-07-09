package com.nju.edu.erp.service;

import com.nju.edu.erp.model.po.DiscountPO;

public interface DiscountStrategyService {
    /**
     * 制定销售策略
     * @param discountPO
     */
    public void makeDiscount(DiscountPO discountPO);
}
