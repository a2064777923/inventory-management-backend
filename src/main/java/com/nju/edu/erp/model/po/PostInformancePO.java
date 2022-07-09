package com.nju.edu.erp.model.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostInformancePO {
    /**
     * 姓名
     */
    private String postname;

    /**
     * 基本工资
     */
    private Integer basicsalary;

    /**
     * 岗位工资
     */
    private Integer postsalary;

    /**
     * 职位
     */
    private String role;

    /**
     * 薪资计算方法
     */
    private String salarycalculation;

    /**
     * 薪资发放方式
     */
    private String salarygrant;

    /**
     * 税收
     */
    private Integer tax;
}
