package com.nju.edu.erp.model.vo.SaleReturns;

import com.nju.edu.erp.enums.sheetState.PurchaseReturnsSheetState;
import com.nju.edu.erp.enums.sheetState.SalesReturnsSheetState;
import com.nju.edu.erp.model.vo.purchaseReturns.PurchaseReturnsSheetContentVO;
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

/**
 * @author Sun Sihan
 * @date 2022/6/27
 */
public class SalesReturnsSheetVO {
    /**
     * 销售退货单单据编号（格式为：XSTHD-yyyyMMdd-xxxxx
     */
    private String id;
    /**
     * 关联的销售单id
     */
    private String salesSheetId;
    /**
     * 操作员
     */
    private String operator;
    /**
     * 备注
     */
    private String remark;
    /**
     * 退货的总额合计
     */
    private BigDecimal totalAmount;
    /**
     * 单据状态
     */
    private SalesReturnsSheetState state;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 销售单具体内容
     */
    List<SalesReturnsSheetContentVO> salesReturnsSheetContent;
}
