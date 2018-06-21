package com.software2.demo.controller;

import com.software2.demo.ResultMessage;
import com.software2.demo.bean.InitTask;
import com.software2.demo.service.InitTaskBLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
/**
 * @Author：Wang Mo
 * @Description：用于标注界面切换图片
 */
@Controller
@Transactional
public class CheckReviewServlet {
    @Autowired
    InitTaskBLService iS;
    @RequestMapping("/checkReview/pass")
    @ResponseBody
    public boolean pass(@RequestBody Map<String,Object> requestMap){
        System.out.println("pass");
        String taskID=requestMap.get("taskID").toString();
        InitTask i=iS.getSingleITask(Integer.parseInt(taskID));
        i.setState(2);
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String today = simpleDateFormat.format(date);
        long D=Long.parseLong(today);
        i.setStartTime(D);
        ResultMessage result=iS.modifyIT(i);
        if(result==ResultMessage.SUCCESS)
            return true;
        else
            return false;
    }

    @RequestMapping("/checkReview/nopass")
    @ResponseBody
    public boolean nopass(@RequestBody Map<String,Object> requestMap){
        System.out.println("nopass");
        String taskID=requestMap.get("taskID").toString();
        String nopassreason=requestMap.get("nopassreason").toString();
        InitTask i=iS.getSingleITask(Integer.parseInt(taskID));
        i.setState(4);
        i.setRemarks(nopassreason);
        ResultMessage result=iS.setRemarks(i);
        if(result==ResultMessage.SUCCESS)
            return true;
        else
            return false;
    }
}
