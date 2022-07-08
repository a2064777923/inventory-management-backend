package com.nju.edu.erp.model.po;

import com.nju.edu.erp.enums.sheetState.PurchaseReturnsSheetState;
import com.nju.edu.erp.enums.sheetState.SalesReturnsSheetState;
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

/**
 * @author Sun Sihan
 * @date 2022/6/27
 */
public class SalesReturnsSheetPO {
    /**
     * 销售退货单单据编号（格式为：XSTHD-yyyyMMdd-xxxxx
     */
    private String id;
    /**
     * 关联的进货单id
     */
    private String salesSheetId;
    /**
     * 业务员
     */
    private String salesman;
    /**
     * 操作员
     */
    private String operator;
    /**
     * 备注
     */
    private String remark;
    /**
     * 折让前总额
     */
    private BigDecimal rawTotalAmount;
    /**
     * 折让
     */
    private BigDecimal discount;
    /**
     * 使用代金券总额
     */
    private BigDecimal voucherAmount;
    /**
     * 折让后总额
     */
    private BigDecimal finalAmount;
    /**
     * 单据状态
     */
    private SalesReturnsSheetState state;
    /**
     * 创建时间
     */
    private Date createTime;
}
