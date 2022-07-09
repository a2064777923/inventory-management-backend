package com.nju.edu.erp.model.vo.discount;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DiscountVO {
    /**
     * 编号
     */
    private Integer id;

    /**
     * 策略类型
     */
    private Integer type;

    /**
     * 客户级别
     */
    private  Integer customerLevel;

    /**
     * 总价
     */
    private BigDecimal totalAmount;

    /**
     * 折扣
     */
    private BigDecimal discount;

    /**
     * 代金券总额
     */
    private BigDecimal voucherAmount;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 组合包
     */
    private List<BagVO> bag;
}
