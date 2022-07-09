package com.nju.edu.erp.model.vo.financial;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Hong ZiXian
 * @date 2022/7/9
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchSheetVO {
    private String beginDateStr;
    private String endDateStr;
    private List<String> customer;
    private List<String> salesmen;
}
