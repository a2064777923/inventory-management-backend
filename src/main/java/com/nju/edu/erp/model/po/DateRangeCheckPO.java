package com.nju.edu.erp.model.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DateRangeCheckPO {
    /**
     * 员工姓名
     */
    private String name;

    /**
     * 员工职位
     */
    private String role;

    /**
     * 签到次数
     */
    private Integer times;
}
