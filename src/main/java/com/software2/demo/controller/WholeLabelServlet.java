package com.software2.demo.controller;

import com.alibaba.fastjson.JSON;
import com.software2.demo.ResultMessage;
import com.software2.demo.bean.Description;
import com.software2.demo.bean.Label;
import com.software2.demo.service.LabelBLService;
import com.software2.demo.util.CheckStateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@Transactional
@RequestMapping("/wholeLabel")
public class WholeLabelServlet {
/*    @GetMapping("/wholeLabel")
    public String wholeLabelForm(Model model){
        model.addAttribute("label",new label());
        return "homepage";
    }*/
    @Autowired
    LabelBLService service;
    @RequestMapping("/save")
    @ResponseBody
    public boolean wholeLabelSubmit(@RequestBody Map<String,Object> requestMap){
        System.out.println("wholeLabelSubmit");
        String workTaskID=requestMap.get("TaskID").toString();
        String info=requestMap.get("info").toString();
        String D=  requestMap.get("d").toString();
        String c=((Map)requestMap.get("d")).get("c").toString();
        System.out.println(D);
        Description d= new Description();
        d.setC(c);
        String pID=requestMap.get("pID").toString();
        String uId=requestMap.get("workerAccount").toString();
        Label l=new Label();
        l.setInfo(info);
        l.setGiverID(uId);
        l.setD(JSON.toJSONString(d));
        l.setPID(Integer.parseInt(pID));
        l.setState(0);
        ResultMessage result=service.addLabel(l,Integer.parseInt(workTaskID));
        CheckStateUtil.checkWT(Integer.parseInt(workTaskID));
        if(result==ResultMessage.SUCCESS)
        return true;
        else
            return false;
    }
}
