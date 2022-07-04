package com.nju.edu.erp.service.Impl;

import com.nju.edu.erp.dao.CollectionSheetDao;
import com.nju.edu.erp.dao.PaymentSheetDao;
import com.nju.edu.erp.dao.ProductDao;
import com.nju.edu.erp.dao.PurchaseSheetDao;
import com.nju.edu.erp.enums.sheetState.PaymentSheetState;
import com.nju.edu.erp.enums.sheetState.PurchaseSheetState;
import com.nju.edu.erp.model.po.*;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.model.vo.financial.CollectionContentVO;
import com.nju.edu.erp.model.vo.financial.CollectionVO;
import com.nju.edu.erp.model.vo.financial.PaymentVO;
import com.nju.edu.erp.model.vo.purchase.PurchaseSheetContentVO;
import com.nju.edu.erp.service.CustomerService;
import com.nju.edu.erp.service.PaymentService;
import com.nju.edu.erp.service.ProductService;
import com.nju.edu.erp.service.WarehouseService;
import com.nju.edu.erp.utils.IdGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Hong ZiXian
 * @date 2022/7/4
 */
@Service
public class PaymentServiceImpl implements PaymentService {
    ProductService productService;
    CustomerService customerService;
    CollectionSheetDao collectionDao;
    PaymentSheetDao paymentSheetDao;


    @Autowired
    public PaymentServiceImpl(ProductService productService, CustomerService customerService, CollectionSheetDao collectionDao, PaymentSheetDao paymentSheetDao) {
        this.productService = productService;
        this.customerService = customerService;
        this.collectionDao = collectionDao;
        this.paymentSheetDao = paymentSheetDao;
    }


    @Override
    @Transactional
    public List<PaymentVO> getAllPayment(PaymentSheetState state) {
        return null;
    }

    /**
     * 制定收款单
     *
     * @param collectionVO 收付款单
     */
    @Override
    @Transactional
    public void createCollectionSheet(UserVO userVO, CollectionVO collectionVO){
        CollectionSheetPO collectionSheetPO = new CollectionSheetPO();
        BeanUtils.copyProperties(collectionVO, collectionSheetPO);
        // 此处根据制定单据人员确定操作员
        collectionSheetPO.setOperator(userVO.getName());
        collectionSheetPO.setCreateTime(new Date());
        CollectionSheetPO latest = collectionDao.getLatest();
        String id = IdGenerator.generateSheetId(latest == null ? null : latest.getId(), "SKD");
        collectionSheetPO.setId(id);
        collectionSheetPO.setState(PaymentSheetState.PENDING_MANAGER);
        BigDecimal totalAmount = BigDecimal.ZERO;
        List<CollectionSheetContentPO> collectionContentPOList = new ArrayList<>();
        for(CollectionContentVO content : collectionVO.getTransferSheetContent()) {
            CollectionSheetContentPO collectionContentPO = new CollectionSheetContentPO();
            BeanUtils.copyProperties(content,collectionContentPO);
            collectionContentPO.setCollectionSheetId(id);
            collectionContentPOList.add(collectionContentPO);
            totalAmount = totalAmount.add(collectionContentPO.getAmount());
        }
        collectionDao.saveBatch(collectionContentPOList);
        collectionSheetPO.setTotalAmount(totalAmount);
        collectionDao.save(collectionSheetPO);

    }
    /**
     * 制定付款单
     *
     * @param paymentVO 收付款单
     */
    @Override
    @Transactional
    public void createPaymentSheet(UserVO userVO, PaymentVO paymentVO){

    }

}
