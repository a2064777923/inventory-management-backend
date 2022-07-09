package com.nju.edu.erp.service.Impl;

import com.nju.edu.erp.dao.*;
import com.nju.edu.erp.enums.sheetState.SaleReturnsSheetState;
import com.nju.edu.erp.model.po.*;
import com.nju.edu.erp.model.vo.SaleReturns.SaleReturnsSheetContentVO;
import com.nju.edu.erp.model.vo.SaleReturns.SaleReturnsSheetVO;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.service.CustomerService;
import com.nju.edu.erp.service.ProductService;
import com.nju.edu.erp.service.SaleReturnsService;
import com.nju.edu.erp.service.WarehouseService;
import com.nju.edu.erp.utils.IdGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
@Transactional
public class SaleReturnsServiceImpl implements SaleReturnsService {
    SaleReturnsSheetDao saleReturnsSheetDao;

    ProductDao productDao;

    CustomerDao customerDao;

    ProductService productService;

    CustomerService customerService;

    WarehouseService warehouseService;

    SaleSheetDao saleSheetDao;

    WarehouseDao warehouseDao;

    @Autowired
    public SaleReturnsServiceImpl(SaleReturnsSheetDao saleReturnsSheetDao, ProductDao productDao, CustomerDao customerDao, ProductService productService, CustomerService customerService, WarehouseService warehouseService, WarehouseDao warehouseDao, SaleSheetDao saleSheetDao) {
        this.saleReturnsSheetDao = saleReturnsSheetDao;
        this.productDao = productDao;
        this.customerDao = customerDao;
        this.productService = productService;
        this.customerService = customerService;
        this.warehouseService = warehouseService;
        this.warehouseDao = warehouseDao;
        this.saleSheetDao = saleSheetDao;
    }

    @Override
    @Transactional
    public void makeSaleReturnsSheet(UserVO userVO, SaleReturnsSheetVO saleReturnsSheetVO) {
        // TODO
        // 需要持久化销售退货单（SaleSheet）和销售退货单content（SaleSheetContent），其中总价或者折后价格的计算需要在后端进行
        SaleReturnsSheetPO saleReturnsSheetPO = new SaleReturnsSheetPO();
        BeanUtils.copyProperties(saleReturnsSheetVO, saleReturnsSheetPO);
        // 此处根据制定单据人员确定操作员
        saleReturnsSheetPO.setOperator(userVO.getName());
        saleReturnsSheetPO.setCreateTime(new Date());
        SaleReturnsSheetPO latest = saleReturnsSheetDao.getLatestReturnsSheet();
        String id = IdGenerator.generateSheetId(latest == null ? null : latest.getId(), "XSTHD");
        saleReturnsSheetPO.setId(id);
        saleReturnsSheetPO.setState(SaleReturnsSheetState.PENDING_LEVEL_1);

        BigDecimal dis =saleSheetDao.findSheetById(saleReturnsSheetPO.getSaleSheetId()).getDiscount();
        BigDecimal vouAmount = saleSheetDao.findSheetById(saleReturnsSheetPO.getSaleSheetId()).getVoucherAmount();

        saleReturnsSheetPO.setVoucherAmount(vouAmount);
        saleReturnsSheetPO.setDiscount(dis);

        BigDecimal totalAmount = BigDecimal.ZERO;
        Integer totalQuantity = 0;
        List<SaleSheetContentPO> saleSheetContent = saleSheetDao.findContentBySheetId(saleReturnsSheetPO.getSaleSheetId());
        Map<String, SaleSheetContentPO> map = new HashMap<>();
        for(SaleSheetContentPO item : saleSheetContent) {
            map.put(item.getPid(), item);
            totalQuantity = totalQuantity + item.getQuantity();
        }



        List<SaleReturnsSheetContentPO> sReturnsContentPOList = new ArrayList<>();
        for(SaleReturnsSheetContentVO content : saleReturnsSheetVO.getSaleReturnsSheetContent()) {
            SaleReturnsSheetContentPO sContentPO = new SaleReturnsSheetContentPO();
            BeanUtils.copyProperties(content,sContentPO);

            sContentPO.setSaleReturnsSheetId(id);
            SaleSheetContentPO item = map.get(sContentPO.getPid());
            sContentPO.setUnitPrice(item.getUnitPrice());

            Integer t = sContentPO.getQuantity();
            BigDecimal vs = vouAmount.multiply(BigDecimal.valueOf(t/totalQuantity));
            BigDecimal unitPrice = sContentPO.getUnitPrice();
            BigDecimal onePrice = unitPrice.multiply(BigDecimal.valueOf(t)).multiply(dis).subtract(vs);
            sContentPO.setTotalPrice(onePrice);
            sReturnsContentPOList.add(sContentPO);
            totalAmount = totalAmount.add(onePrice);
        }
        saleReturnsSheetDao.saveBatchReturnsSheetContent(sReturnsContentPOList);
        saleReturnsSheetPO.setTotalAmount(totalAmount);
        saleReturnsSheetDao.saveReturnsSheet(saleReturnsSheetPO);
    }

    @Override
    @Transactional
    public List<SaleReturnsSheetVO> getSaleReturnsSheetByState(SaleReturnsSheetState state) {
        // TODO
        // 根据单据状态获取销售单（注意：VO包含SaleReturnsSheetContent）
        // 依赖的dao层方法未提供，需要自己实现
        List<SaleReturnsSheetVO> res = new ArrayList<>();
        List<SaleReturnsSheetPO> all;
        if(state == null) {
            all = saleReturnsSheetDao.findAllReturnsSheet();
        } else {
            all = saleReturnsSheetDao.findallReturnsByState(state);
        }
        for(SaleReturnsSheetPO po: all) {
            SaleReturnsSheetVO vo = new SaleReturnsSheetVO();
            BeanUtils.copyProperties(po, vo);
            List<SaleReturnsSheetContentPO> alll = saleReturnsSheetDao.findReturnsContentBySheetId(po.getId());
            List<SaleReturnsSheetContentVO> vos = new ArrayList<>();
            for (SaleReturnsSheetContentPO p : alll) {
                SaleReturnsSheetContentVO v = new SaleReturnsSheetContentVO();
                BeanUtils.copyProperties(p, v);
                vos.add(v);
            }
            vo.setSaleReturnsSheetContent(vos);
            res.add(vo);
        }
        return res;

    }

    /**
     * 根据销售退货单id进行审批(state == "待二级审批"/"审批完成"/"审批失败")
     * 在controller层进行权限控制
     *
     * @param saleReturnsSheetId 销售单id
     * @param state       销售单要达到的状态
     */
    @Override
    @Transactional
    public void approval(String saleReturnsSheetId, SaleReturnsSheetState state) {
        // TODO
        // 需要的service和dao层相关方法自己实现一遍
        /* 一些注意点：
            1. 二级审批成功之后需要进行
                 1. 修改单据状态
                 2. 更新商品表
                 3. 更新客户表
                 4. 新建出库草稿
            2. 一级审批状态不能直接到审批完成状态； 二级审批状态不能回到一级审批状态
         */
        SaleReturnsSheetPO saleReturnsSheet = saleReturnsSheetDao.findReturnsSheetById(saleReturnsSheetId);
        if(state.equals(SaleReturnsSheetState.FAILURE)) {

            if(saleReturnsSheet.getState() == SaleReturnsSheetState.SUCCESS) throw new RuntimeException("状态更新失败");
            int effectLines = saleReturnsSheetDao.updateReturnsSheetState(saleReturnsSheetId, state);
            if(effectLines == 0) throw new RuntimeException("状态更新失败");
        } else {
            SaleReturnsSheetState prevReturnsState;
            if(state.equals(SaleReturnsSheetState.SUCCESS)) {
                prevReturnsState = SaleReturnsSheetState.PENDING_LEVEL_2;
            } else if(state.equals(SaleReturnsSheetState.PENDING_LEVEL_2)) {
                prevReturnsState = SaleReturnsSheetState.PENDING_LEVEL_1;
            } else {
                throw new RuntimeException("状态更新失败");
            }
            int effectLines = saleReturnsSheetDao.updateReturnsSheetStateOnPrev(saleReturnsSheetId, prevReturnsState, state);
            if(effectLines == 0) throw new RuntimeException("状态更新失败");
            if(state.equals(SaleReturnsSheetState.SUCCESS)) {
                // TODO 审批完成, 修改一系列状态
                // 销售退货单id， 关联的销售单id 【   销售退货单id->销售单id->入库单id->批次id】
                //Integer batchId = saleReturnsSheetDao.findReturnsBatchId(saleReturnsSheetId);
                //销售退货单id—销售单id-出库单id-出库单内容-批次-增加库存
                //- 销售退货单id-pid， quantity 【批次id+pid -> 定位到库存的一个条目->库存减去quantity】
                //- 【 pid -> 定位到单位售价->Σ单位售价*quantity * 折扣 - 优惠券 = 要收回的钱->客户payable减去要收回的钱】
                List<SaleReturnsSheetContentPO> contents = saleReturnsSheetDao.findReturnsContentBySheetId(saleReturnsSheetId);
                for (SaleReturnsSheetContentPO content: contents) {
                    Integer quantity = content.getQuantity();
                    Integer bid = warehouseDao.getOneBID(content.getPid());
                    warehouseDao.addNum(content.getPid(),bid,quantity);
                }

                SaleSheetPO saleSheetPO = saleSheetDao.findSheetById(saleReturnsSheet.getSaleSheetId());
                Integer supplier = saleSheetPO.getSupplier();
                CustomerPO customer = customerService.findCustomerById(supplier);
                customer.setPayable(customer.getPayable().add(saleReturnsSheet.getTotalAmount()));
                customerService.updateCustomer(customer);

            }
        }
    }

}
