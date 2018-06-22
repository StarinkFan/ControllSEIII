package com.software2.demo.controller;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.exceptions.ClientException;
import com.software2.demo.bean.Appeal;
import com.software2.demo.bean.InitTask;
import com.software2.demo.bean.Query;
import com.software2.demo.service.AppealBLService;
import com.software2.demo.service.InitTaskBLService;
import com.software2.demo.service.QueryBLService;
import com.software2.demo.service.UserBLService;
import com.software2.demo.util.SendTextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @Author：Wang Mo
 * @Description：用于检查申诉检查
 */
@Controller
@Transactional
public class CheckAppealServlet {
    @Autowired
    InitTaskBLService initTaskBLService;
    @Autowired
    UserBLService userBLService;
    @Autowired
    AppealBLService appealBLService;
    @RequestMapping("/checkAppeal/pass")
    @ResponseBody
    public boolean appealPass(@RequestBody Map<String,Object> requestMap){
        int appealID= Integer.parseInt(requestMap.get("appealID").toString());
        Appeal a=appealBLService.getSingle(appealID);
        a.setCheck(1);
        appealBLService.modify(a);
        int taskID=Integer.parseInt(requestMap.get("taskID").toString());
        int picID=Integer.parseInt(requestMap.get("picID").toString());
        String workerID=requestMap.get("workerID").toString();
        InitTask initTask = initTaskBLService.getSingleITask(taskID);
        try {
            SendTextMessage.sendAnswerChangeToInitor(initTask.getInitorID());
        } catch (ClientException e) {
            e.printStackTrace();
        }
        List<String> listOfWorkerID=JSON.parseArray(initTask.getListOfWoker(),String.class);
        for(String phone:listOfWorkerID){
            try {
                SendTextMessage.sendAnswerChange(phone);
            } catch (ClientException e) {
                e.printStackTrace();
            }
        }
        List<Integer> pic_ids = JSON.parseArray(initTask.getListOfP(), Integer.class);
        double value = (double)initTask.getCredit()/initTask.getNum()/pic_ids.size();
        List<String> answer= (List<String>) requestMap.get("answer");
        userBLService.modify_picTitle(picID,initTask.getKind(),answer,true,value,initTask.getID());
        userBLService.modify_picTitle(picID,initTask.getKind(),answer,false,value,initTask.getID());
        return true;
    }

    @RequestMapping("/checkAppeal/nopass")
    @ResponseBody
    public boolean appealNoPass(@RequestBody Map<String,Object> requestMap){
        int appealID= Integer.parseInt(requestMap.get("appealID").toString());
        Appeal a=appealBLService.getSingle(appealID);
        a.setCheck(1);
        appealBLService.modify(a);
        String taskID=requestMap.get("taskID").toString();
        String picID=requestMap.get("picID").toString();
        String workerID=requestMap.get("workerID").toString();
        try {
            SendTextMessage.sendAppealFail(workerID);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return true;

    }
}
