package com.nju.edu.erp.model.vo.financial;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author Hong ZiXian
 * @date 2022/7/8
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SalesSearchVO {
    private String beginDateStr;
    private String endDateStr;
    private List<String> productName;
    private List<String> customer;
    private List<String> salesmen;
}
