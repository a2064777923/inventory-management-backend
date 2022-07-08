package com.nju.edu.erp.service.Impl;

import com.nju.edu.erp.dao.ProductDao;
import com.nju.edu.erp.dao.SaleSheetDao;
import com.nju.edu.erp.dao.SalesReturnsSheetDao;
import com.nju.edu.erp.dao.WarehouseDao;
import com.nju.edu.erp.enums.sheetState.SalesReturnsSheetState;
import com.nju.edu.erp.model.po.*;
import com.nju.edu.erp.model.vo.ProductInfoVO;
import com.nju.edu.erp.model.vo.SaleReturns.SalesReturnsSheetContentVO;
import com.nju.edu.erp.model.vo.SaleReturns.SalesReturnsSheetVO;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.service.CustomerService;
import com.nju.edu.erp.service.ProductService;
import com.nju.edu.erp.service.SalesReturnsService;
import com.nju.edu.erp.service.WarehouseService;
import com.nju.edu.erp.utils.IdGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service

/**
 * @author Sun Sihan
 * @date 2022/6/27
 */
public class SalesReturnsServiceImpl implements SalesReturnsService {

    SalesReturnsSheetDao salesReturnsSheetDao;

    ProductService productService;

    ProductDao productDao;

    SaleSheetDao saleSheetDao;

    CustomerService customerService;

    WarehouseService warehouseService;

    WarehouseDao warehouseDao;

    @Autowired
    public SalesReturnsServiceImpl(SalesReturnsSheetDao salesReturnsSheetDao, ProductService productService, ProductDao productDao, SaleSheetDao saleSheetDao, CustomerService customerService, WarehouseService warehouseService, WarehouseDao warehouseDao) {
        this.salesReturnsSheetDao = salesReturnsSheetDao;
        this.productService = productService;
        this.productDao = productDao;
        this.saleSheetDao = saleSheetDao;
        this.customerService = customerService;
        this.warehouseService = warehouseService;
        this.warehouseDao = warehouseDao;
    }

    /**
     * 制定销售退货单
     *
     * @param salesReturnsSheetVO 销售退货单
     */
    @Override
    @Transactional
    public void makeSalesReturnsSheet(UserVO userVO, SalesReturnsSheetVO salesReturnsSheetVO) {
        SalesReturnsSheetPO salesReturnsSheetPO = new SalesReturnsSheetPO();
        BeanUtils.copyProperties(salesReturnsSheetVO, salesReturnsSheetPO);

        //根据制定单据人员确定操作员
        salesReturnsSheetPO.setOperator(userVO.getName());
        salesReturnsSheetPO.setCreateTime(new Date());
        SalesReturnsSheetPO latest = salesReturnsSheetDao.getLatest();
        String id = IdGenerator.generateSheetId(latest == null ? null : latest.getId(), "XSTHD");
        salesReturnsSheetPO.setId(id);
        salesReturnsSheetPO.setState(SalesReturnsSheetState.PENDING_LEVEL_1);
        BigDecimal totalAmount = BigDecimal.ZERO;
        List<SaleSheetContentPO> saleSheetContent = saleSheetDao.findContentBySheetId(salesReturnsSheetPO.getSalesSheetId());
        Map<String, SaleSheetContentPO> map = new HashMap<>();
        for(SaleSheetContentPO item : saleSheetContent) {
            map.put(item.getPid(), item);
        }
        List<SalesReturnsSheetContentPO> sContentPOList = new ArrayList<>();
        for(SalesReturnsSheetContentVO content : salesReturnsSheetVO.getSalesReturnsSheetContent()){
            SalesReturnsSheetContentPO sContentPO = new SalesReturnsSheetContentPO();
            BeanUtils.copyProperties(content, sContentPO);
            sContentPO.setSalesReturnsSheetId(id);
            SaleSheetContentPO item  = map.get(sContentPO.getPid());
            sContentPO.setUnitPrice(item.getUnitPrice());

            BigDecimal unitPrice = sContentPO.getUnitPrice();
            sContentPO.setTotalPrice(unitPrice.multiply(BigDecimal.valueOf(sContentPO.getQuantity())));
            sContentPOList.add(sContentPO);
            totalAmount = totalAmount.add(sContentPO.getTotalPrice());
        }
        salesReturnsSheetDao.saveBatch(sContentPOList);
        salesReturnsSheetPO.setRawTotalAmount(totalAmount);
        BigDecimal finalAmount = (totalAmount.multiply(salesReturnsSheetPO.getDiscount().subtract(salesReturnsSheetPO.getVoucherAmount())));
        salesReturnsSheetPO.setFinalAmount(finalAmount);
        salesReturnsSheetDao.save(salesReturnsSheetPO);
    }

    /**
     * 根据状态获取销售退货单[不包括content信息](state == null 则获取所有销售退货单)
     *
     * @param state 销售退货单状态
     * @return 销售退货单
     */
    @Override
    public List<SalesReturnsSheetVO> getSalesReturnsSheetByState(SalesReturnsSheetState state) {
        List<SalesReturnsSheetVO> res = new ArrayList<>();
        List<SalesReturnsSheetPO> all;
        if(state == null) {
            all = salesReturnsSheetDao.findAll();
        } else {
            all = salesReturnsSheetDao.findAllByState(state);
        }
        for(SalesReturnsSheetPO po : all) {
            SalesReturnsSheetVO vo = new SalesReturnsSheetVO();
            BeanUtils.copyProperties(po, vo);
            List<SalesReturnsSheetContentPO> alll = salesReturnsSheetDao.findContentBySalesReturnsSheetId(po.getId());
            List<SalesReturnsSheetContentVO> vos = new ArrayList<>();
            for(SalesReturnsSheetContentPO p : alll) {
                SalesReturnsSheetContentVO v = new SalesReturnsSheetContentVO();
                BeanUtils.copyProperties(p, v);
                vos.add(v);
            }
            vo.setSalesReturnsSheetContent(vos);
            res.add(vo);
        }
        return res;
    }

    /**
     * 根据销售退货单id进行审批(state == "待二级审批"/"审批完成"/"审批失败")
     * 在controller层进行权限控制
     *
     * @param salesReturnsSheetId 销售退货单id
     * @param state 销售退货单要达到的状态
     */
    @Override
    @Transactional
    public void approval(String salesReturnsSheetId, SalesReturnsSheetState state) {
        SalesReturnsSheetPO salesReturnsSheet = salesReturnsSheetDao.findOneById(salesReturnsSheetId);
        if(state.equals(SalesReturnsSheetState.FAILURE)) {
            if(salesReturnsSheet.getState() == SalesReturnsSheetState.SUCCESS) throw new RuntimeException("状态更新失败");
            int effectLines = salesReturnsSheetDao.updateState(salesReturnsSheetId, state);
            if(effectLines == 0) throw new RuntimeException("状态更新失败");
        }
        else{
            SalesReturnsSheetState prevState;
            if(state.equals(SalesReturnsSheetState.SUCCESS)) {
                prevState = SalesReturnsSheetState.PENDING_LEVEL_2;
            } else if(state.equals(SalesReturnsSheetState.PENDING_LEVEL_2)) {
                prevState = SalesReturnsSheetState.PENDING_LEVEL_1;
            } else {
                throw new RuntimeException("状态更新失败");
            }
            int effectLines = salesReturnsSheetDao.updateStateV2(salesReturnsSheetId, prevState, state);
            if(effectLines == 0) throw new RuntimeException("状态更新失败");
            if(state.equals(SalesReturnsSheetState.SUCCESS)) {
                // 销售退货单id，关联的销售单id 【销售退货单id -> 销售单id -> 出库单id -> 批次id】
                Integer batchId = salesReturnsSheetDao.findBatchId(salesReturnsSheetId);

                // 销售退货单id-pid，quantity 【批次id+pid -> 定位到库存的一个条目 - 库存加上quantity】
                // 【pid -> 定位到销售单价 -> Σ销售单价*quantity=要退给客户的钱】
                List<SalesReturnsSheetContentPO> contents = salesReturnsSheetDao.findContentBySalesReturnsSheetId(salesReturnsSheetId);
                BigDecimal payableToDeduct = BigDecimal.ZERO;
                for(SalesReturnsSheetContentPO content : contents) {
                    String pid = content.getPid();
                    Integer quantity = content.getQuantity();
                    WarehousePO warehousePO = warehouseDao.findOneByPidAndBatchId(pid, batchId);
                    if(warehousePO == null) throw new RuntimeException("单据发生错误！请联系管理员！");
                    if(warehousePO.getQuantity() >= quantity) {
                        warehousePO.setQuantity(quantity);
                        warehouseDao.deductQuantity(warehousePO);
                        ProductInfoVO productInfoVO = productService.getOneProductByPid(pid);
                        productInfoVO.setQuantity(productInfoVO.getQuantity() + quantity);
                        productService.updateProduct(productInfoVO);
                        payableToDeduct = payableToDeduct.add(content.getUnitPrice().multiply(BigDecimal.valueOf(quantity)));
                    } else {
                        salesReturnsSheetDao.updateState(salesReturnsSheetId, SalesReturnsSheetState.FAILURE);
                        throw new RuntimeException("商品数量不足！审批失败！");
                    }
                }

                SaleSheetPO saleSheetPO = saleSheetDao.findSheetById(salesReturnsSheet.getSalesSheetId());
                Integer supplier = saleSheetPO.getSupplier();
                CustomerPO customer = customerService.findCustomerById(supplier);

                customer.setPayable(customer.getPayable().subtract(payableToDeduct));
                customerService.updateCustomer(customer);
            }
        }
    }
}
