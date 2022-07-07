package com.nju.edu.erp.model.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author Hong ZiXian
 * @date 2022/7/6
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SalarySheetContentPO {
    /**
     * 自增id
     */
    private Integer id;
    /**
     * 工资单id
     */
    private String salarySheetId;
    /**
     * 名字
     */
    private String name;
    /**
     * 稅前工资
     */
    private BigDecimal grossSalary;
    /**
     * 个人所得稅
     */
    private BigDecimal personalIncomeTax;
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
     * 銀行賬戶
     */
    private String bankNo;
    /**
     * 备注
     */
    private String remark;
}
