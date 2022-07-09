package com.nju.edu.erp.model.vo.SaleReturns;

import com.nju.edu.erp.enums.sheetState.SaleReturnsSheetState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaleReturnsSheetVO {
    /**
     * 销售退货单单据编号（格式为：XSTHD-yyyyMMdd-xxxxx), 新建单据时前端传null
     */
    private String id;

    /**
     * 业务员
     */
    private String saleSheetId;
    /**
     * 备注
     */
    private String remark;

    private String operator;

    /**
     * 单据状态, 新建单据时前端传null
     */
    private SaleReturnsSheetState state;
    /**
     * 折让后总额, 新建单据时前端传null
     */
    private BigDecimal totalAmount;
    /**
     * 折扣
     */
    private BigDecimal discount;
    /**
     * 使用代金券总额
     */
    private BigDecimal voucherAmount;
    /**
     * 进货单具体内容
     */
    List<SaleReturnsSheetContentVO> saleReturnsSheetContent;
}
