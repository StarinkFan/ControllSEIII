package com.software2.demo.controller;

import com.alibaba.fastjson.JSON;
import com.software2.demo.bean.UserData;
import com.software2.demo.service.UserDataBLService;
import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author:Wang Mo
 * @Description：
 */
@Controller
@Transactional
public class CreditAndTaskServlet {
    private int UserID=200;
    @Autowired
    UserDataBLService userDataBLService;

    /**
     * @Description：获取所需数据
     */
    @RequestMapping("/creditAndTask/getCreditCondition")
    @ResponseBody
    public Map<String,Object> getCreditCondition(){
        int uid=UserID;
        String percents=userDataBLService.getPercents(uid);
        String data0=userDataBLService.getData0(uid);
        String data=userDataBLService.getData(uid);
        String picNumbers=userDataBLService.getPicNumbers(uid);
        String credits=userDataBLService.getCredits(uid);
        Map<String,Object> resultMap=JSON.parseObject(percents,HashMap.class);
/*        resultMap.put("percents",percents);
        resultMap.put("data0",data0);
        resultMap.put("data",data);
        resultMap.put("picNumbers",picNumbers);
        resultMap.put("credits",credits);*/
        return resultMap;
    }

    @RequestMapping("/getData")
    @ResponseBody
    public boolean getData(@RequestBody Map<String,Object> requestMap){
        System.out.println("aaaaa");
        String a= JSON.toJSONString(requestMap);
        String percents=requestMap.get("percents").toString();
        String data0=requestMap.get("data0").toString();
        String data=requestMap.get("data").toString();
        String picNumbers=requestMap.get("picNumbers").toString();
        String credits=requestMap.get("credits").toString();
        UserData userData=new UserData();
        userData.setCredits(credits);
        userData.setData0(data0);
        userData.setData1(data);
        userData.setPercents(a);
        userData.setPicNumbers(picNumbers);
        userDataBLService.add(userData);
        return true;
    }
}
