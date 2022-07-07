package com.nju.edu.erp.model.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author Hong ZiXian
 * @date 2022/7/7
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StaffWageInfoPO {
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
     * 銀行
     */
    private String bankNo;
}
