package com.nju.edu.erp.model.vo.financial;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author Hong ZiXian
 * @date 2022/7/4
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentContentVO {
    /**
     * 自增id, 新建单据时前端传null
     */
    private Integer id;

    /**
     * 銀行帳戶号
     */
    private String bankNo;

    /**
     * 金额
     */
    private BigDecimal amount;
    /**
     * 备注
     */
    private String remark;
}
