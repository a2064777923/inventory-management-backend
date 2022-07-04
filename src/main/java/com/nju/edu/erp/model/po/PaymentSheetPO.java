package com.nju.edu.erp.model.po;

import com.nju.edu.erp.enums.sheetState.PaymentSheetState;
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
public class PaymentSheetPO {
    /**
     * 进货单单据编号（格式为：JHD-yyyyMMdd-xxxxx
     */
    private String id;
    /**
     * 供应商id
     */
    private Integer customer;
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
