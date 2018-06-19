package com.software2.demo.controller;

import com.alibaba.fastjson.JSON;
import com.software2.demo.ResultMessage;
import com.software2.demo.bean.*;
import com.software2.demo.service.LabelBLService;
import com.software2.demo.util.CheckStateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@Transactional
@RequestMapping("/partLabel")
public class PartLabelServlet {

    @Autowired
    LabelBLService service;
    @RequestMapping("/save")
    @ResponseBody
    public boolean partLabelSubmit(@RequestBody Map<String,Object> requestMap){
        System.out.println("partLabelSubmit");
        String info=requestMap.get("info").toString();
        String TaskID=requestMap.get("TaskID").toString();
        String D=requestMap.get("d").toString();
        String c=((Map)requestMap.get("d")).get("c").toString();
        System.out.println(D);
        Description d= new Description();
        String color=requestMap.get("color").toString();
        d.setC(c);
        String pID=requestMap.get("pID").toString();
        int tag=Integer.parseInt(requestMap.get("tag").toString());
        String uid=requestMap.get("workerAccount").toString();
        List<Point> list= (List<Point>) requestMap.get("lis");


        Label pl=new Label();
        pl.setGiverID(uid);
        pl.setInfo(info);
        pl.setLis(JSON.toJSONString(list));
        pl.setTag(tag);
        pl.setPID(Integer.parseInt(pID));
        pl.setD(JSON.toJSONString(d));
        pl.setColor(color);
        pl.setState(0);
        System.out.println(tag);
        System.out.println(pl.getInfo());
        System.out.println(pl.getPID());
        ResultMessage result=service.addLabel(pl,Integer.parseInt(TaskID));
        CheckStateUtil.checkWT(Integer.parseInt(TaskID));
        if(result==ResultMessage.SUCCESS)
            return true;
        else
            return false;
    }
}
