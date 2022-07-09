package com.nju.edu.erp.dao;

import com.nju.edu.erp.model.po.User;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.model.vo.financial.SalesDetailVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author Hong ZiXian
 * @date 2022/7/8
 */
@Repository
@Mapper
public interface SearchSheetDao {
    /**
     * 找銷售
     */
    List<User> getAllSalesmen();

    List<SalesDetailVO> getSalesDetails(Date beginTime, Date endTime, @Param("productName")List<String> productName, @Param("customer") List<String> customer, @Param("salesman")List<String> salesmen);
}
