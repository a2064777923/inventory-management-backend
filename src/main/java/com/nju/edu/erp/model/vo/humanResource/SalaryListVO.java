package com.nju.edu.erp.model.vo.humanResource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SalaryListVO {
    /**
     * 员工姓名
     */
    private String name;

    /**
     * 员工职位
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
     * 奖金
     */
    private Integer bonus;

    /**
     * 未签到
     */
    private Integer uncheck;

    /**
     * 薪资发放方式
     */
    private String grantway;

    /**
     * 实际工资
     */
    private Integer realsalary;
}
