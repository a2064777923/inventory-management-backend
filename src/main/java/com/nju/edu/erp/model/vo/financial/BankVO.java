package com.nju.edu.erp.model.vo.financial;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BankVO {
    private Integer id;

    private String name ;

    private String bankNo;

    private BigDecimal amount;
}
