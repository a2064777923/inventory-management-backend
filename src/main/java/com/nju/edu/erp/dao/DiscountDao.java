package com.nju.edu.erp.dao;

import com.nju.edu.erp.model.po.DiscountPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
@Mapper
public interface DiscountDao {
    //获取最近
    Integer getLatest();

    //创建销售策略
    void createDiscount(DiscountPO discountPO);

    //删除销售策略
    void deleteDiscount(Integer id);

    //获取折扣信息
    List<DiscountPO> getAll();

    //获取指定级别的折扣情况
    DiscountPO getByLevel(Integer level);

    //获取低于指定级别的折扣情况
    List<DiscountPO> getLessL(Integer level);

    //获取高于指定级别的折扣情况
    List<DiscountPO> getLargerL(Integer level);

    //更新（依据级别）
    void updateByLevel(DiscountPO discountPO);

    //获取所有组合包id
    List<Integer> getBagId();

    //获取某一策略下所有商品的id
    List<String> getBagInfo(Integer id);

    //更新（依据id）
    void updateById(DiscountPO discountPO);

    //添加组合包内容
    void addBagContent(Integer id, String pid);

    //获取指定id的折扣情况
    DiscountPO getById(Integer id);

    //获取某一价位的折扣情况
    DiscountPO getByAmount(BigDecimal amount);

    //获取低于指定价位的折扣情况
    List<DiscountPO> getLessA(BigDecimal amount);

    //获取高于指定价位的折扣情况
    List<DiscountPO> getLargerA(BigDecimal amount);

    //获取最大折扣
    DiscountPO getMax(BigDecimal amount);

    //更新（依据价位）
    void updateByAmount(DiscountPO discountPO);

}
