package com.nju.edu.erp.web.controller;
import com.nju.edu.erp.model.vo.humanResource.CheckVO;
import com.nju.edu.erp.model.vo.humanResource.PostInformanceVO;
import com.nju.edu.erp.model.vo.humanResource.SalaryListVO;
import com.nju.edu.erp.model.vo.humanResource.WorkerVO;
import com.nju.edu.erp.service.HumanResourceService;
import com.nju.edu.erp.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/humanresource")
public class HumanresourceController {
    private final HumanResourceService humanresourceService;
    @Autowired
    public HumanresourceController(HumanResourceService humanresourceService){
        this.humanresourceService = humanresourceService;
    }
    @PostMapping("/create")
    public Response createCheck(@RequestBody CheckVO checkVO){
        humanresourceService.createCheck(checkVO);
        return Response.buildSuccess();
    }

    @GetMapping("/showdatechecks")
    public Response showDateChecks(@RequestParam String checktime){
        return Response.buildSuccess(humanresourceService.showDateChecks(checktime));
    }
    @GetMapping("/showdateuncheck")
    public Response showDateUncheck(@RequestParam String checktime){
        return Response.buildSuccess(humanresourceService.showDateUncheck(checktime));
    }
    @GetMapping("/showdaterangecheck")
    public Response showDateRangeCheck(@RequestParam String beginDate,String endDate){
        return Response.buildSuccess(humanresourceService.showDateRangeCheck(beginDate,endDate));
    }
    @PostMapping("/createworker")
    public Response createWorker(@RequestBody WorkerVO workerVO){
        humanresourceService.createWorker(workerVO);
        return Response.buildSuccess();
    }
    @GetMapping ("/getallworkers")
    public Response getAllWorkers(){
        return Response.buildSuccess(humanresourceService.getAllWorkers());
    }
    @PostMapping("/createpostinformance")
    public Response createPostInformance(@RequestBody PostInformanceVO postInformanceVO){
        humanresourceService.createPostInformance(postInformanceVO);
        return Response.buildSuccess();
    }
    @GetMapping("/getallposts")
    public Response getAllPosts(){
        return Response.buildSuccess(humanresourceService.getAllPosts());
    }

    @GetMapping("/getsalarystrategy")
    public Response getSalaryStrategy(@RequestParam String role){
        return Response.buildSuccess(humanresourceService.getSalaryStrategy(role));
    }

    @GetMapping("/createSalaryList")
    public Response createSalaryList(@RequestBody SalaryListVO salaryListVO){
        humanresourceService.createSalaryList(salaryListVO);
        return Response.buildSuccess();
    }

}
