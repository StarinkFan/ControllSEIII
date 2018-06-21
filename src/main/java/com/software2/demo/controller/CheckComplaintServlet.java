package com.software2.demo.controller;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.exceptions.ClientException;
import com.software2.demo.bean.InitTask;
import com.software2.demo.bean.Label;
import com.software2.demo.bean.User;
import com.software2.demo.bean.WorkTask;
import com.software2.demo.service.*;
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
 * @Description：用于投诉检查
 */
@Controller
@Transactional
public class CheckComplaintServlet {
    @Autowired
    PictureBLService pictureBLService;
    @Autowired
    WorkTaskBLService workTaskBLService;
    @Autowired
    LabelBLService labelBLService;
    @Autowired
    InitTaskBLService initTaskBLService;
    @Autowired
    UserBLService userBLService;
    @RequestMapping("/checkComplaint/pass")
    @ResponseBody
    public boolean pass(@RequestBody Map<String,Object> requestMap){
        Integer taskID= Integer.valueOf(requestMap.get("taskID").toString());
        Integer picID= Integer.valueOf(requestMap.get("picID").toString());
        String workerID=requestMap.get("workerID").toString();
        Integer worktaskID= Integer.valueOf(requestMap.get("worktaskID").toString());
        String requestorID=pictureBLService.getSinglePicture(picID).getPID();
        WorkTask w=workTaskBLService.getSingleWTask(worktaskID);
        InitTask iT=initTaskBLService.getSingleITask(taskID);
        List<Integer> listOfL= JSON.parseArray(w.getListOfLabel(),Integer.class);
        User worker=userBLService.getSingle(workerID);
        User requestor=userBLService.getSingle(requestorID);
        for(int i:listOfL){
            Label l=labelBLService.getSingleLabel(i);
            if(l.getPID()==picID){
                l.setState(2);
                w.setActualCredit(w.getActualCredit()-(iT.getCredit()/iT.getNum())/JSON.parseArray(iT.getListOfP(),Integer.class).size());
                worker.setCredit(worker.getCredit()-(iT.getCredit()/iT.getNum())/JSON.parseArray(iT.getListOfP(),Integer.class).size());
                worker.setEarncredit(worker.getEarncredit()-(iT.getCredit()/iT.getNum())/JSON.parseArray(iT.getListOfP(),Integer.class).size());
                requestor.setCredit(requestor.getCredit()+(iT.getCredit()/iT.getNum())/JSON.parseArray(iT.getListOfP(),Integer.class).size());
                labelBLService.modifyLabel(l);
                workTaskBLService.modifyTask(w);
                userBLService.modifyUser(worker);
                userBLService.modifyUser(requestor);
                try {
                    SendTextMessage.sendComplaintResult(requestorID,"成功",workerID);
                } catch (ClientException e) {
                    e.printStackTrace();
                }
                try {
                    SendTextMessage.sendBeComplainted(workerID);
                } catch (ClientException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
        return true;
    }

    @RequestMapping("/checkComplaint/nopass")
    @ResponseBody
    public boolean nopass(@RequestBody Map<String,Object> requestMap){
        Integer taskID= Integer.valueOf(requestMap.get("taskID").toString());
        Integer picID= Integer.valueOf(requestMap.get("picID").toString());
        String requestorID=pictureBLService.getSinglePicture(picID).getPID();
        String workerID=requestMap.get("workerID").toString();
        try {
            SendTextMessage.sendComplaintResult(requestorID,"失败",workerID);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return true;
    }
}
