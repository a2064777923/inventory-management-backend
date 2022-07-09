package com.nju.edu.erp.model.po;

import com.nju.edu.erp.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Staff {

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
    private String birthdate;

    /**
     *  员工电话
     */
    private String phone;

    /**
     *  员工职位
     */
    private String role;

    /**
     * 员工级别
     */
    private Integer rank;

    /**
     * 工资卡
     */
    private String salaryCard;

}
