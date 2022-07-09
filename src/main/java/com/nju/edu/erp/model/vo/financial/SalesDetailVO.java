package com.nju.edu.erp.model.vo.financial;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SalesDetailVO {
    /**
     * 类型，銷售或退货
     */
    private String state;
    /**
     * 自增id
     */
    private Integer id;
    /**
     * 商品id
     */
    private String name;
    /**
     * 型号
     */
    private String type;
    /**
     * 数量
     */
    private Integer quantity;
    /**
     * 单价
     */
    private BigDecimal unitPrice;
    /**
     * 总金额
     */
    private BigDecimal totalPrice;
    /**
     * 时间
     */
    private Date createTime;
}
