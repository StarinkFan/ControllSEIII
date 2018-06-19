package com.software2.demo.controller;

import com.software2.demo.service.AdminBLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Transactional
public class AdminServlet {
    @Autowired
    AdminBLService aS;
    @RequestMapping("/admin/getAllData")
    @ResponseBody
    public Map<String,Object> getAllData(){
        System.out.println("getAllData");
        List<Integer> list=aS.getAllData();
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("credit",list.get(0));
        resultMap.put("complete",list.get(1));
        resultMap.put("undergoing",list.get(2));
        resultMap.put("uncomplete",list.get(3));
        resultMap.put("task",list.get(4));
        resultMap.put("user",list.get(5));
        return resultMap;
    }
}
