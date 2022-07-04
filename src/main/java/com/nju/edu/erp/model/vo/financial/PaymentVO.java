package com.nju.edu.erp.model.vo.financial;

import com.nju.edu.erp.enums.sheetState.PaymentSheetState;
import com.nju.edu.erp.enums.sheetState.PurchaseSheetState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Hong ZiXian
 * @date 2022/7/3
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentVO {
    /**
     * 付款单单据编号（格式为：FKD-yyyyMMdd-xxxxx), 新建单据时前端传null
     */
    private String id;
    /**
     * 客戶ID
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
    * 总额合计, 新建单据时前端传null(在后端计算总金额)
     */
    private BigDecimal totalAmount;
    /**
     * 单据状态, 新建单据时前端传null
     */
    private PaymentSheetState state;

    /**
     * 付款单项具体內容
     */
    List<PaymentContentVO> transferSheetContent;
    
}
