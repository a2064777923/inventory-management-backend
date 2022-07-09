package com.nju.edu.erp.service;

import com.nju.edu.erp.model.po.DiscountPO;
import com.nju.edu.erp.model.po.SaleSheetContentPO;
import com.nju.edu.erp.model.vo.discount.BagVO;
import com.nju.edu.erp.model.vo.discount.DiscountVO;

import java.math.BigDecimal;
import java.util.List;

public interface DiscountService {
    /**
     * 确定销售策略
     * @param discount
     */
    public void setDiscount(DiscountVO discount);

    /**
     * 删除销售策略
     * @param id
     */
    public void deleteDiscount(Integer id);

    /**
     * 获取所有销售策略
     * @return
     */
    public List<DiscountVO> findAll();

    /**
     * 选择销售策略
     * @param level
     * @param saleContent
     * @param totalAmount
     * @return
     */
    public List<BigDecimal> chooseDiscount(Integer level, List<SaleSheetContentPO> saleContent, BigDecimal totalAmount);
}
