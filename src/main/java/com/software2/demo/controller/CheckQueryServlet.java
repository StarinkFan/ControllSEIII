package com.software2.demo.controller;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.exceptions.ClientException;
import com.software2.demo.bean.InitTask;
import com.software2.demo.bean.Query;
import com.software2.demo.service.InitTaskBLService;
import com.software2.demo.service.PictureBLService;
import com.software2.demo.service.QueryBLService;
import com.software2.demo.service.UserBLService;
import com.software2.demo.util.SendTextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;
/**
 * @Author：Wang Mo
 * @Description：用于质疑检查
 */
@Controller
@Transactional
public class CheckQueryServlet {
    @Autowired
    PictureBLService pictureBLService;
    @Autowired
    UserBLService userBLService;
    @Autowired
    InitTaskBLService initTaskBLService;
    @Autowired
    QueryBLService queryBLService;
    @RequestMapping("/checkQuery/pass")
    @ResponseBody
    public boolean pass(@RequestBody Map<String,Object> requestMap){
        int QueryID= Integer.parseInt(requestMap.get("QueryID").toString());
        Query q=queryBLService.getSingle(QueryID);
        q.setChecking(1);
        queryBLService.modify(q);
        Integer taskID= Integer.valueOf(requestMap.get("taskID").toString());
        InitTask initTask = initTaskBLService.getSingleITask(taskID);
        List<String> listOfWorker=JSON.parseArray(initTask.getListOfWoker(),String.class);
        for(String id:listOfWorker){
            try {
                SendTextMessage.sendAnswerChange(id);
            } catch (ClientException e) {
                e.printStackTrace();
            }
        }
        String initorID=initTask.getInitorID();
        try {
            SendTextMessage.sendQuery(initorID,"成功");
        } catch (ClientException e) {
            e.printStackTrace();
        }
        List<Integer> pic_ids = JSON.parseArray(initTask.getListOfP(), Integer.class);
        double value = (double)initTask.getCredit()/initTask.getNum()/pic_ids.size();
        Integer picID= Integer.valueOf(requestMap.get("picID").toString());
        List<String> answer= (List<String>) requestMap.get("answer");
        userBLService.modify_picTitle(picID,initTask.getKind(),answer,true,value,initTask.getID());
        userBLService.modify_picTitle(picID,initTask.getKind(),answer,false,value,initTask.getID());
        return true;
    }

    @RequestMapping("/checkQuery/nopass")
    @ResponseBody
    public boolean nopass(@RequestBody Map<String,Object> requestMap){
        int QueryID= Integer.parseInt(requestMap.get("QueryID").toString());
        Query q=queryBLService.getSingle(QueryID);
        q.setChecking(1);
        queryBLService.modify(q);
        Integer taskID= Integer.valueOf(requestMap.get("taskID").toString());
        Integer picID= Integer.valueOf(requestMap.get("picID").toString());
        String requestorID=pictureBLService.getSinglePicture(picID).getPID();
        try {
            SendTextMessage.sendQuery(requestorID,"失败");
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return true;
    }
}
