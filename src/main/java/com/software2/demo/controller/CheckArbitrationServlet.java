package com.software2.demo.controller;

import com.alibaba.fastjson.JSON;
import com.software2.demo.bean.InitTask;
import com.software2.demo.service.InitTaskBLService;
import com.software2.demo.service.PictureBLService;
import com.software2.demo.service.UserBLService;
import com.software2.demo.service.WorkTaskBLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @Author:Wang Mo
 * @Description：用于接受仲裁答案
 */

@Controller
@Transactional
public class CheckArbitrationServlet {

    @Autowired
    InitTaskBLService initTaskBLService;
    @Autowired
    PictureBLService pictureBLService;
    @Autowired
    UserBLService userBLService;
    @Autowired
    WorkTaskBLService workTaskBLService;

    /**
     * @Description：接受仲裁返回的正确答案
     */

    @RequestMapping("/checkArbitration")
    @ResponseBody
    public boolean checkArbitration(@RequestBody Map<String,Object> requestMap){
        int workTaskID= Integer.parseInt(requestMap.get("workTaskID").toString());
        int picID= Integer.parseInt(requestMap.get("picID").toString());
        List<String> answer= (List<String>) requestMap.get("answer");
        int taskID=workTaskBLService.getSingleWTask(workTaskID).getInitTaskID();
        InitTask initTask = initTaskBLService.getSingleITask(taskID);

        List<Integer> pic_ids = JSON.parseArray(initTask.getListOfP(), Integer.class);
        double value = (double)initTask.getCredit()/initTask.getNum()/pic_ids.size();
        userBLService.modify_picTitle(picID,initTask.getKind(),answer,false,value,taskID);
        return true;
    }
}
