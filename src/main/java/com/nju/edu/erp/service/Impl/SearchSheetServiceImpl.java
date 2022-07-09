package com.nju.edu.erp.service.Impl;

import com.nju.edu.erp.dao.SearchSheetDao;
import com.nju.edu.erp.model.po.User;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.model.vo.financial.SalesDetailVO;
import com.nju.edu.erp.model.vo.financial.SalesSearchVO;
import com.nju.edu.erp.model.vo.financial.SearchSheetVO;
import com.nju.edu.erp.model.vo.purchase.PurchaseSheetVO;
import com.nju.edu.erp.model.vo.purchaseReturns.PurchaseReturnsSheetVO;
import com.nju.edu.erp.service.PurchaseReturnsService;
import com.nju.edu.erp.service.PurchaseService;
import com.nju.edu.erp.service.SearchSheetService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Hong ZiXian
 * @date 2022/7/8
 */
@Service
public class SearchSheetServiceImpl implements SearchSheetService {
    SearchSheetDao searchSheetDao;
    PurchaseService purchaseService;
    PurchaseReturnsService purchaseReturnsService;

    @Autowired
    public SearchSheetServiceImpl(SearchSheetDao searchSheetDao,PurchaseService purchaseService,PurchaseReturnsService purchaseReturnsService){
        this.searchSheetDao = searchSheetDao;
        this.purchaseService = purchaseService;
        this.purchaseReturnsService = purchaseReturnsService;
    }

    /**
     * 獲取全部銷售业務员
     *
     * @return
     */
    @Override
    @Transactional
    public List<UserVO> getAllSalesmen() {
        List<User> res = searchSheetDao.getAllSalesmen();
        List<UserVO> userVOList = new ArrayList<>();
        for(User u : res){
            UserVO v = new UserVO();
            BeanUtils.copyProperties(u,v);
            userVOList.add(v);
        }
        return userVOList;
    }

    /**
     * 獲耶銷售詳情
     * @return
     */
    @Override
    @Transactional
    public List<SalesDetailVO> getSalesDetails(SalesSearchVO salesSearchVO) {
        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try{
            Date beginTime =dateFormat.parse(salesSearchVO.getBeginDateStr());
            Date endTime=dateFormat.parse(salesSearchVO.getEndDateStr());
            if(beginTime.compareTo(endTime)>0){
                return null;
            }else{

                return searchSheetDao.getSalesDetails(beginTime,endTime,salesSearchVO.getProductName(),salesSearchVO.getCustomer(),salesSearchVO.getSalesmen());
            }
        }catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<PurchaseSheetVO> getPurchaseSheet(SearchSheetVO searchSheetVO) {
        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try{
            Date beginTime =dateFormat.parse(searchSheetVO.getBeginDateStr());
            Date endTime=dateFormat.parse(searchSheetVO.getEndDateStr());
            if(beginTime.compareTo(endTime)>0){
                return null;
            }else{
                List<String> idList = searchSheetDao.getPurchaseSheet(beginTime,endTime,searchSheetVO.getCustomer());
                List<PurchaseSheetVO> res = new ArrayList<>();
                for(String i : idList){
                    PurchaseSheetVO a = purchaseService.getPurchaseSheetById(i);
                    res.add(a);
                }

                return res;
            }
        }catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<PurchaseReturnsSheetVO> getPurchaseSheetReturn(SearchSheetVO searchSheetVO) {
        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try{
            Date beginTime =dateFormat.parse(searchSheetVO.getBeginDateStr());
            Date endTime=dateFormat.parse(searchSheetVO.getEndDateStr());
            if(beginTime.compareTo(endTime)>0){
                return null;
            }else{
                List<String> idList = searchSheetDao.getPurchaseSheetReturn(beginTime,endTime);
                List<PurchaseReturnsSheetVO> res = new ArrayList<>();
                for(String i : idList){
                    PurchaseReturnsSheetVO a = purchaseReturnsService.getPurchaseReturnsSheetById(i);
                    res.add(a);
                }

                return res;
            }
        }catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
