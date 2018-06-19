package com.software2.demo.controller;

import com.alibaba.fastjson.JSON;
import com.software2.demo.bean.InitTask;
import com.software2.demo.service.InitTaskBLService;
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

@Controller
@Transactional
public class TaskAccouncementServlet {
    @Autowired
    InitTaskBLService iS;
    @RequestMapping("/taskAccouncement/search")
    @ResponseBody
    public Map<String,Object> search(@RequestBody Map<String,Object> requestMap){
        String keyword=requestMap.get("uname").toString();
        List<String> toReturn=new ArrayList<>();
        List<InitTask> IT=iS.getAll();
        for(InitTask i:IT){
            List<String> listOfTags= JSON.parseArray(i.getListOfTags(),String.class);
            for(String str:listOfTags){
                if(str.contains(keyword)){
                    boolean in=false;
                    for(String temp:toReturn){
                        if(temp.equals(str)){
                            in=true;
                            break;
                        }
                    }
                    if(!in){
                        toReturn.add(str);
                    }
                }
            }
        }

        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("taskMarks",toReturn);
        return resultMap;
    }
}
