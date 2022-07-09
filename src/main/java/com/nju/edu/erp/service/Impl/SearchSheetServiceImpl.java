package com.nju.edu.erp.service.Impl;

import com.nju.edu.erp.dao.SearchSheetDao;
import com.nju.edu.erp.model.po.User;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.model.vo.financial.SalesDetailVO;
import com.nju.edu.erp.model.vo.financial.SalesSearchVO;
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

    @Autowired
    public SearchSheetServiceImpl(SearchSheetDao searchSheetDao){
        this.searchSheetDao = searchSheetDao;
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
}
