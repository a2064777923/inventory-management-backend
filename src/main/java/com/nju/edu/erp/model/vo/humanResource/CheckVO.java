package com.nju.edu.erp.model.vo.humanResource;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
//打卡
public class CheckVO {
    /**
     * 员工姓名
     */
    private String name;

    /**
     * 员工种类
     */
    private String role;

    /**
     * 签到时间
     */
    private String checktime;
}
