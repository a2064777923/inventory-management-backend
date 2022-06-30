package com.nju.edu.erp.model.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


/**
 * @author Hong ZiXian
 * @date 2022/6/29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BankPO {

    private Integer id;  //編号

    private String name; //名称

    private String bankNo;//帳戶号

    private BigDecimal amount;//金额

}
