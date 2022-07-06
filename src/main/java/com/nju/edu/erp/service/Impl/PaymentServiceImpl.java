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
import com.nju.edu.erp.model.vo.financial.PaymentContentVO;
import com.nju.edu.erp.model.vo.financial.PaymentVO;
import com.nju.edu.erp.model.vo.purchase.PurchaseSheetContentVO;
import com.nju.edu.erp.model.vo.purchase.PurchaseSheetVO;
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
    PaymentSheetDao paymentDao;


    @Autowired
    public PaymentServiceImpl(ProductService productService, CustomerService customerService, CollectionSheetDao collectionDao, PaymentSheetDao paymentSheetDao) {
        this.productService = productService;
        this.customerService = customerService;
        this.collectionDao = collectionDao;
        this.paymentDao = paymentSheetDao;
    }



    /**
     * @param state 狀态
     * @return
     */
    @Override
    @Transactional
    public List<CollectionVO> getAllCollection(PaymentSheetState state) {
        List<CollectionVO> res = new ArrayList<>();
        List<CollectionSheetPO> all;
        if(state == null) {
            all = collectionDao.findAll();
        } else {
            all = collectionDao.findAllByState(state);
        }
        for(CollectionSheetPO po: all) {
            CollectionVO vo = new CollectionVO();
            BeanUtils.copyProperties(po, vo);
            List<CollectionSheetContentPO> alll = collectionDao.findContentByCollectionSheetId(po.getId());
            List<CollectionContentVO> vos = new ArrayList<>();
            for (CollectionSheetContentPO p : alll) {
                CollectionContentVO v = new CollectionContentVO();
                BeanUtils.copyProperties(p, v);
                vos.add(v);
            }
            vo.setTransferSheetContent(vos);
            res.add(vo);
        }
        return res;
    }
    @Override
    @Transactional
    public List<PaymentVO> getAllPayment(PaymentSheetState state) {
        List<PaymentVO> res = new ArrayList<>();
        List<PaymentSheetPO> all;
        if(state == null) {
            all = paymentDao.findAll();
        } else {
            all = paymentDao.findAllByState(state);
        }
        for(PaymentSheetPO po: all) {
            PaymentVO vo = new PaymentVO();
            BeanUtils.copyProperties(po, vo);
            List<PaymentSheetContentPO> alll = paymentDao.findContentByPaymentSheetId(po.getId());
            List<PaymentContentVO> vos = new ArrayList<>();
            for (PaymentSheetContentPO p : alll) {
                PaymentContentVO v = new PaymentContentVO();
                BeanUtils.copyProperties(p, v);
                vos.add(v);
            }
            vo.setTransferSheetContent(vos);
            res.add(vo);
        }
        return res;
    }

    /**
     * 制定收款单
     *
     * @param collectionVO 收款单
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
     * @param paymentVO 付款单
     */
    @Override
    @Transactional
    public void createPaymentSheet(UserVO userVO, PaymentVO paymentVO){
        PaymentSheetPO paymentSheetPO = new PaymentSheetPO();
        BeanUtils.copyProperties(paymentVO, paymentSheetPO);
        // 此处根据制定单据人员确定操作员
        paymentSheetPO.setOperator(userVO.getName());
        paymentSheetPO.setCreateTime(new Date());
        PaymentSheetPO latest = paymentDao.getLatest();
        String id = IdGenerator.generateSheetId(latest == null ? null : latest.getId(), "FKD");
        paymentSheetPO.setId(id);
        paymentSheetPO.setState(PaymentSheetState.PENDING_MANAGER);
        BigDecimal totalAmount = BigDecimal.ZERO;
        List<PaymentSheetContentPO> paymentContentPOList = new ArrayList<>();
        for(PaymentContentVO content : paymentVO.getTransferSheetContent()) {
            PaymentSheetContentPO paymentContentPO = new PaymentSheetContentPO();
            BeanUtils.copyProperties(content,paymentContentPO);
            paymentContentPO.setPaymentSheetId(id);
            paymentContentPOList.add(paymentContentPO);
            totalAmount = totalAmount.add(paymentContentPO.getAmount());
        }
        paymentDao.saveBatch(paymentContentPOList);
        paymentSheetPO.setTotalAmount(totalAmount);
        paymentDao.save(paymentSheetPO);

    }

    /**
     * 根据收款单id进行审批
     *
     * @param transferSheetId 收付款单id
     * @param state           审批后狀态
     */
    @Override
    @Transactional
    public void approval(String transferSheetId, PaymentSheetState state) {
        if(transferSheetId.charAt(0) == 'S'){
            if(state.equals(PaymentSheetState.FAILURE)) {
                CollectionSheetPO collectionSheet = collectionDao.findOneById(transferSheetId);
                if(collectionSheet.getState() == PaymentSheetState.SUCCESS) throw new RuntimeException("状态更新失败");
                int effectLines = collectionDao.updateState(transferSheetId, state);
                if(effectLines == 0) throw new RuntimeException("状态更新失败");
            } else {
                if(state.equals(PaymentSheetState.SUCCESS)) {
                    int effectLines = collectionDao.updateState(transferSheetId, state);
                    if(effectLines == 0) throw new RuntimeException("状态更新失败");
                } else {
                    throw new RuntimeException("状态更新失败");
                }

        }
            if(state.equals(PaymentSheetState.SUCCESS)) {
                // 更新客户表(更新应付字段)
                // 更新应付 payable
                CollectionSheetPO collectionSheet = collectionDao.findOneById(transferSheetId);
                CustomerPO customerPO = customerService.findCustomerById(collectionSheet.getCustomer());
                customerPO.setPayable(customerPO.getPayable().add(collectionSheet.getTotalAmount()));
                customerService.updateCustomer(customerPO);
            }

        }else if(transferSheetId.charAt(0) == 'F'){
            if(state.equals(PaymentSheetState.FAILURE)) {
                PaymentSheetPO paymentSheet = paymentDao.findOneById(transferSheetId);
                if(paymentSheet.getState() == PaymentSheetState.SUCCESS) throw new RuntimeException("状态更新失败");
                int effectLines = paymentDao.updateState(transferSheetId, state);
                if(effectLines == 0) throw new RuntimeException("状态更新失败");
            } else {
                if(state.equals(PaymentSheetState.SUCCESS)) {
                    int effectLines = paymentDao.updateState(transferSheetId, state);
                    if(effectLines == 0) throw new RuntimeException("状态更新失败");
                } else {
                    throw new RuntimeException("状态更新失败");
                }

            }
            if(state.equals(PaymentSheetState.SUCCESS)) {
                // 更新客户表(更新应收字段)
                // 更新应收 receivable
                PaymentSheetPO paymentSheet = paymentDao.findOneById(transferSheetId);
                CustomerPO customerPO = customerService.findCustomerById(paymentSheet.getCustomer());
                customerPO.setReceivable(customerPO.getPayable().add(paymentSheet.getTotalAmount()));
                customerService.updateCustomer(customerPO);
            }
        }

    }


}
