package com.software2.demo.controller;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.exceptions.ClientException;
import com.software2.demo.bean.InitTask;
import com.software2.demo.service.InitTaskBLService;
import com.software2.demo.service.PictureBLService;
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
    @RequestMapping("/checkQuery/pass")
    @ResponseBody
    public boolean pass(@RequestBody Map<String,Object> requestMap){
        Integer taskID= Integer.valueOf(requestMap.get("taskID").toString());
        InitTask initTask = initTaskBLService.getSingleITask(taskID);
        List<Integer> pic_ids = JSON.parseArray(initTask.getListOfP(), Integer.class);
        double value = (double)initTask.getCredit()/initTask.getNum()/pic_ids.size();
        Integer picID= Integer.valueOf(requestMap.get("picID").toString());
        List<String> answer= (List<String>) requestMap.get("answer");
        userBLService.modify_picTitle(picID,initTask.getKind(),answer,true,value,initTask.getInitorID());
        userBLService.modify_picTitle(picID,initTask.getKind(),answer,false,value,initTask.getInitorID());
        return true;
    }

    @RequestMapping("/checkQuery/nopass")
    @ResponseBody
    public boolean nopass(@RequestBody Map<String,Object> requestMap){
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
