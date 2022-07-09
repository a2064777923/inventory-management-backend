package com.nju.edu.erp.service.Impl;

import com.nju.edu.erp.dao.DiscountDao;
import com.nju.edu.erp.model.po.DiscountPO;
import com.nju.edu.erp.model.po.SaleSheetContentPO;
import com.nju.edu.erp.model.vo.discount.BagVO;
import com.nju.edu.erp.model.vo.discount.DiscountVO;
import com.nju.edu.erp.service.DiscountService;
import com.nju.edu.erp.service.DiscountStrategyService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DiscountServiceImpl implements DiscountService {
    private final DiscountDao discountDao;

    @Autowired
    public DiscountServiceImpl(DiscountDao discountDao) {
        this.discountDao = discountDao;
    }

    DiscountStrategyService discountStrategyService;

    @Override
    public void setDiscount(DiscountVO discountVO) {
        DiscountPO discountPO = new DiscountPO();
        BeanUtils.copyProperties(discountVO, discountPO);
        discountPO.setCreateTime(new Date());
        switch (discountPO.getType()) {
            case 1:
                discountStrategyService = new DiscountStrategy1(discountDao);
                break;
            case 2:
                DiscountStrategy2 discountStrategy2 = new DiscountStrategy2(discountDao);
                discountStrategy2.setBagContent(discountVO.getBag());
                discountStrategyService = discountStrategy2;
                break;
            case 3:
                discountStrategyService = new DiscountStrategy3(discountDao);
                break;
        }
        discountStrategyService.makeDiscount(discountPO);
    }

    @Override
    public void deleteDiscount(Integer id) {
        discountDao.deleteDiscount(id);
    }

    @Override
    public List<DiscountVO> findAll() {
        List<DiscountVO> list = new ArrayList<>();
        List<DiscountPO> list1 = discountDao.getAll();
        for (DiscountPO discountPO : list1) {
            DiscountVO discountVO = new DiscountVO();
            BeanUtils.copyProperties(discountPO, discountVO);
            if (discountVO.getType() == 2) {
                List<String> list2 = discountDao.getBagInfo(discountVO.getId());
                List<BagVO> list3 = new ArrayList<>();
                for (String i : list2) {
                    BagVO bag = new BagVO();
                    bag.setPid(i);
                    list3.add(bag);
                }
                discountVO.setBag(list3);
            }
            list.add(discountVO);
        }
        return list;
    }

    @Override
    public List<BigDecimal> chooseDiscount(Integer level, List<SaleSheetContentPO> saleContent, BigDecimal totalAmount) {
        List<BigDecimal> list = new ArrayList<>();
        DiscountPO strategy1 = new DiscountPO();
        DiscountPO strategy3 = new DiscountPO();
        if (discountDao.getByLevel(level) != null) {
            strategy1 = discountDao.getByLevel(level);
        } else {
            strategy1.setDiscount(BigDecimal.valueOf(1));
            strategy1.setVoucherAmount(BigDecimal.valueOf(0));
        }
        if (discountDao.getMax(totalAmount) != null) {
            strategy3 = discountDao.getMax(totalAmount);
        } else {
            strategy3.setDiscount(BigDecimal.valueOf(1));
            strategy3.setVoucherAmount(BigDecimal.valueOf(0));
        }
        if (totalAmount.multiply(strategy1.getDiscount()).subtract(strategy1.getVoucherAmount()).compareTo(totalAmount.multiply(strategy3.getDiscount()).subtract(strategy3.getVoucherAmount())) != 1) {
            list.add(0, strategy1.getDiscount());
            list.add(1, strategy1.getVoucherAmount());
        } else {
            list.add(0, strategy3.getDiscount());
            list.add(1, strategy3.getVoucherAmount());
        }

        List<String> list1 = new ArrayList<>();
        for (SaleSheetContentPO item : saleContent) {
            list1.add(item.getPid());
        }

        List<Integer> list2 = discountDao.getBagId();
        Integer tempId = 0;
        for (Integer i : list2) {
            List<String> list3 = discountDao.getBagInfo(i);
            if (list3 != null) {
                if (list3.containsAll(list1) && list1.containsAll(list3)) {
                    tempId = i;
                    break;
                }
            }
        }
        if (tempId == 0) {
            return list;
        } else {
            DiscountPO strategy2 = discountDao.getById(tempId);
            if (totalAmount.multiply(strategy2.getDiscount()).subtract(strategy2.getVoucherAmount()).compareTo(totalAmount.multiply(list.get(0)).subtract(list.get(1))) != 1) {
                list.set(0, strategy2.getDiscount());
                list.set(1, strategy2.getVoucherAmount());
            }
        }
        return list;
    }
}
