package com.nju.edu.erp.model.po;

import com.nju.edu.erp.enums.sheetState.PaymentSheetState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Hong ZiXian
 * @date 2022/7/6
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SalarySheetPO {
    /**
     * 付款单单据编号（格式为：GZD-yyyyMMdd-xxxxx
     */
    private String id;
    /**
     * 操作员
     */
    private String operator;
    /**
     * 备注
     */
    private String remark;
    /**
     * 总额合计
     */
    private BigDecimal totalAmount;
    /**
     * 单据状态
     */
    private PaymentSheetState state;
    /**
     * 创建时间
     */
    private Date createTime;
}