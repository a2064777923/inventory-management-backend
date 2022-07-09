package com.nju.edu.erp.model.vo.humanResource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WorkerVO {
    /**
     *  员工姓名
     */
    private String name;

    /**
     *  员工性别
     */
    private String sex;

    /**
     *  员工生日（格式为：yyyy-MM-dd）
     */
    private String birthday;

    /**
     *  员工职位
     */
    private String role;

    /**
     * 入职日期
     */
    private String inductionday;

    /**
     * 电话
     */
    private String phone;

    /**
     * 邮件
     */
    private String email;

    /**
     * 工资卡
     */
    private String salarycard;
}
