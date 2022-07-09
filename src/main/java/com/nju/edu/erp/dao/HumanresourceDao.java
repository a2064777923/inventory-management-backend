package com.nju.edu.erp.dao;

import com.nju.edu.erp.model.po.*;
import com.nju.edu.erp.model.vo.humanResource.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface HumanresourceDao {
    /**
     * 创建打卡
     * @param checkVO
     */
    void createCheck(CheckVO checkVO);

    /**
     * 显示打卡
     * @param checktime
     * @return
     */
    List<CheckPO> showDateChecks(String checktime);

    /**
     * 显示未打卡
     * @param checktime
     * @return
     */
    List<UncheckPO> showDateUncheck(String checktime);

    /**
     * 显示打卡日期
     * @param beginDate
     * @param endDate
     * @return
     */
    List<DateRangeCheckPO> showDateRangeCheck(String beginDate, String endDate);

    /**
     * 员工入职
     * @param workerVO
     */
    void createWorker(WorkerVO workerVO);

    /**
     * 获取所有员工信息
     * @return
     */
    List<WorkerPO> getAllWorkers();

    /**
     * 创建工资发放信息
     * @param postInformanceVO
     */
    void createPostInformance(PostInformanceVO postInformanceVO);

    /**
     * 获取所有发放信息
     * @return
     */
    List<PostInformancePO> getAllPosts();

    /**
     * 创建工资单
     * @param salaryListVO
     */
    void createSalaryList(SalaryListVO salaryListVO);

    /**
     * 获取工资单
     * @param name
     * @param role
     * @return
     */
    SalaryListVO getSalaryList(String name,String role);

    /**
     * 创建薪资方式
     * @param salaryStrategyVO
     */
    void createSalaryStrategy(SalaryStrategyVO salaryStrategyVO);

    /**
     * 获取薪资方式
     * @param role
     * @return
     */
    List<SalaryStrategyVO> getSalaryStrategy(String role);
}
