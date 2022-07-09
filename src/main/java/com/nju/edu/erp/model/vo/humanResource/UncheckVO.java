package com.nju.edu.erp.model.vo.humanResource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UncheckVO {
    /**
     * 员工姓名
     */
    String name;

    /**
     * 员工职位
     */
    String role;
}
