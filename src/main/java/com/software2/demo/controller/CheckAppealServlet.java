package com.software2.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Transactional
public class CheckAppealServlet {
    @RequestMapping("/checkAppeal/pass")
    @ResponseBody
    public boolean appealPass(@RequestBody Map<String,Object> requestMap){
        String taskID=requestMap.get("taskID").toString();
        String picID=requestMap.get("picID").toString();
        List<String> answer= (List<String>) requestMap.get("answer");
        return true;
    }

    @RequestMapping("/checkAppeal/nopass")
    @ResponseBody
    public boolean appealNoPass(@RequestBody Map<String,Object> requestMap){
        String taskID=requestMap.get("taskID").toString();
        String picID=requestMap.get("picID").toString();
        String workerID=requestMap.get("workerID").toString();
        return true;

    }
}
