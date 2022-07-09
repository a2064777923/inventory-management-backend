package com.nju.edu.erp.model.vo.humanResource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SalaryStrategyVO {
    /**
     * 职位
     */
    private String role;

    /**
     * 基本工资
     */
    private Integer basicsalary;

    /**
     * 岗位工资
     */
    private Integer postsalary;

    /**
     * 回扣
     */
    private Integer commission;

    /**
     * 扣除比率
     */
    private Double punishmentrate;
}