package com.nju.edu.erp.model.vo.humanResource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostInformanceVO {
    /**
     * 姓名
     */
    private String post_name;

    /**
     * 基本工资
     */
    private Integer basic_salary;

    /**
     * 岗位工资
     */
    private Integer post_salary;

    /**
     * 职位
     */
    private String role;

    /**
     * 薪资计算方式
     */
    private String salary_calculation;

    /**
     * 薪资发放方式
     */
    private String salary_grant;

    /**
     * 税收
     */
    private Integer tax;
}
