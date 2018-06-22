package com.software2.demo.controller;

import com.software2.demo.bean.Appeal;
import com.software2.demo.bean.Complaint;
import com.software2.demo.bean.WorkTask;
import com.software2.demo.service.AppealBLService;
import com.software2.demo.service.ComplaintBLService;
import com.software2.demo.service.InitTaskBLService;
import com.software2.demo.service.WorkTaskBLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@Transactional
public class FeedbackServlet {
    @Autowired
    ComplaintBLService complaintBLService;
    @Autowired
    WorkTaskBLService workTaskBLService;
    @Autowired
    InitTaskBLService initTaskBLService;
    @Autowired
    AppealBLService appealBLService;
    @RequestMapping("/feedback/complain")
    @ResponseBody
    public boolean complain(@RequestBody Map<String,Object> requestMap){
        String requestorAccount=requestMap.get("requestorAccount").toString();
        String workerAccount=requestMap.get("workerAccount").toString();
        Integer picID= Integer.valueOf(requestMap.get("picID").toString());
        Integer taskID= Integer.valueOf(requestMap.get("taskID").toString());
        Complaint c=new Complaint();
        c.setPictureID(picID);
        c.setInitTaskID(workTaskBLService.getSingleWTask(taskID).getInitTaskID());
        c.setWorkerID(workerAccount);
        c.setRequestorID(requestorAccount);
        c.setWorkTaskID(taskID);
        c.setReason("");
        c.setCheck(0);
        complaintBLService.addComplaint(c);
        return true;
    }

    @RequestMapping("/feedback/appeal")
    @ResponseBody
    public boolean appeal(@RequestBody Map<String,Object> requestMap){
        String workerAccount=requestMap.get("workerAccount").toString();
        Integer picID= Integer.valueOf(requestMap.get("picID").toString());
        Integer taskID= Integer.valueOf(requestMap.get("taskID").toString());
        Appeal a=new Appeal();
        WorkTask w=workTaskBLService.getSingleWTask(taskID);
        a.setPictureID(picID);
        a.setWorkerID(workerAccount);
        a.setWorkTaskID(taskID);
        a.setInitTaskID(w.getInitTaskID());
        a.setReason("");
        a.setCheck(0);
        a.setRequestorID(initTaskBLService.getSingleITask(w.getInitTaskID()).getInitorID());
        appealBLService.add(a);
        return true;
    }
}
