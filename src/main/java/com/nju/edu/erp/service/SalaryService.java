package com.nju.edu.erp.service;


import com.nju.edu.erp.enums.sheetState.PaymentSheetState;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.model.vo.financial.SalarySheetVO;
import com.nju.edu.erp.model.vo.financial.StaffWageInfoVO;

import java.util.List;

/**
 * @author Hong ZiXian
 * @date 2022/7/6
 */
public interface SalaryService {
    /**
     * 獲取全部员工薪资訊息
     * @return
     */
    List<StaffWageInfoVO> getAllStaffInfo();

    /**
     * 创建工资单
     * @param userVO
     * @param salarySheetVO
     */
    void createSalarySheet(UserVO userVO, SalarySheetVO salarySheetVO);

    /**
     * 獲取全部工资单，有state依state獲，沒就獲全部
     * @param state
     * @return
     */
    List<SalarySheetVO> getAllSalary(PaymentSheetState state);

    /**
     *
     * @param salarySheetId 工资单id
     * @param state 更改后狀态
     */
    void approval(String salarySheetId, PaymentSheetState state);
}
