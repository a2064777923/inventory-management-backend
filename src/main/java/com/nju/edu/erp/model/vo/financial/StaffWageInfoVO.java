package com.nju.edu.erp.model.vo.financial;

import com.nju.edu.erp.enums.sheetState.PaymentSheetState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Hong ZiXian
 * @date 2022/7/7
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StaffWageInfoVO {
    /**
     * id
     */
    private int staff_id;
    /**
     * 名字
     */
    private String name;
    /**
     * 工资
     */
    private BigDecimal wage;
    /**
     * 个人所得稅
     */
    private BigDecimal PersonalIncomeTax;
    /**
     * 失业保險
     */
    private BigDecimal unemploymentInsurance;
    /**
     * 住房公积金
     */
    private BigDecimal housingFund;
    /**
     * 实發工资
     */
    private BigDecimal finalSalary;
    /**
     * 銀行
     */
    private String bankNo;
}
