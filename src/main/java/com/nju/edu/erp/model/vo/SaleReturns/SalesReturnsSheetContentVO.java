package com.nju.edu.erp.model.vo.SaleReturns;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

/**
 * @author Sun Sihan
 * @date 2022/6/27
 */
public class SalesReturnsSheetContentVO {
    /**
     * 自增id
     */
    private Integer id;
    /**
     * 销售退货单id
     */
    private String SalesReturnsSheetId;
    /**
     * 商品id
     */
    private String pid;
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
     * 备注
     */
    private String remark;
}
