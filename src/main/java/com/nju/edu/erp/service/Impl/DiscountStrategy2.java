package com.nju.edu.erp.service.Impl;

import com.nju.edu.erp.dao.DiscountDao;
import com.nju.edu.erp.model.po.DiscountPO;
import com.nju.edu.erp.model.vo.discount.BagVO;
import com.nju.edu.erp.service.DiscountStrategyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DiscountStrategy2 implements DiscountStrategyService {
    private final DiscountDao discountDao;
    private List<String> bagContent = new ArrayList<>();

    @Autowired
    public DiscountStrategy2(DiscountDao discountDao) {
        this.discountDao = discountDao;
    }

    void setBagContent(List<BagVO> list) {
        for(BagVO i : list) {
            bagContent.add(i.getPid());
        }
    }

    @Override
    public void makeDiscount(DiscountPO discountPO) {
        List<Integer> list = discountDao.getBagId();
        Integer tempId = 0;
        for (Integer i : list) {
            List<String> list1 = discountDao.getBagInfo(i);
            if (list1 != null && bagContent != null) {
                if (list1.containsAll(bagContent) && bagContent.containsAll(list1)) {
                    tempId = i;
                    break;
                }
            }
        }
        if (tempId != 0) {
            discountPO.setId(tempId);
            discountDao.updateById(discountPO);
        } else {
            discountDao.createDiscount(discountPO);
            Integer id = discountDao.getLatest();
            for (String i : bagContent) {
                discountDao.addBagContent(id, i);
            }
        }
    }
}
