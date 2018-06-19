package com.software2.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.software2.demo.bean.InitTask;
import com.software2.demo.bean.User;
import com.software2.demo.service.InitTaskBLService;
import com.software2.demo.service.UserBLService;
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
public class personalSkillServlet {
    @Autowired
    UserBLService userBLService;
    @Autowired
    InitTaskBLService iS;
    @RequestMapping("/personalSkill/getSkillTags")
    @ResponseBody
    public Map<String,Object> getSkillTags(@RequestBody Map<String,Object> requestMap){
        String id=requestMap.get("account").toString();
        User u=userBLService.getSingle(id);
        List<String> tags= JSON.parseArray(u.getListOfTags(),String.class);
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("tags",tags);
        return resultMap;
    }

    @RequestMapping("/personalSkill/refreshSkillTags")
    @ResponseBody
    public boolean refreshSkillTags(@RequestBody Map<String,Object> requestMap){
        List<String> tags= (List<String>) requestMap.get("tags");
        String id=requestMap.get("account").toString();
        User u=userBLService.getSingle(id);
        u.setListOfTags(JSON.toJSONString(tags));
        userBLService.modifyUser(u);
        if(userBLService.getSingle(id).getListOfTags().equals(JSON.toJSONString(tags)))
            return true;
        else
            return false;
    }

    @RequestMapping("/personalSkill/searchSkillTags")
    @ResponseBody
    public Map<String,Object> searchSkillTags(@RequestBody Map<String,Object> requestMap){
        String keyword=requestMap.get("keywords").toString();
        List<User> listOfUser=userBLService.getAllUser();
        List<InitTask> listOfInitTask=iS.getAll();
        List<String> listOfTag=new ArrayList<>();
        for(User u:listOfUser){
            List<String> userTags=JSON.parseArray(u.getListOfTags(),String.class);
            for(String str:userTags){
                boolean in=false;
                if(str.contains(keyword)){
                    for(String temp:listOfTag){
                        if(str.equals(temp)){
                            in=true;
                            break;
                        }
                    }
                    if(!in){
                        listOfTag.add(str);
                    }
                }
            }
        }

        for(InitTask i:listOfInitTask){
            List<String> taskTags=JSON.parseArray(i.getListOfTags(),String.class);
            for(String str:taskTags){
                boolean in=false;
                if(str.contains(keyword)){
                    for(String temp:listOfTag){
                        if(str.equals(temp)){
                            in=true;
                            break;
                        }
                    }
                    if(!in){
                        listOfTag.add(str);
                    }
                }
            }
        }
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("matchNumber",listOfTag.size());
        resultMap.put("matchTags",listOfTag);
        return resultMap;
    }

}
