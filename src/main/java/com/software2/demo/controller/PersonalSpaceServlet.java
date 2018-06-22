package com.software2.demo.controller;

import com.alibaba.fastjson.JSON;
import com.software2.demo.bean.*;
import com.software2.demo.service.InitTaskBLService;
import com.software2.demo.service.UserBLService;
import com.software2.demo.service.WorkTaskBLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Transactional
public class PersonalSpaceServlet {
    @Autowired
    UserBLService userBLService;
    @Autowired
    WorkTaskBLService wS;
    @Autowired
    InitTaskBLService iS;
    @RequestMapping(value = "/personalSpace/getPersonalInformation")
    @ResponseBody
    public Map<String,Object> getInfo(@RequestBody Map<String,Object> requestMap){
        System.out.println("getInfo");
        User u=userBLService.getSingle(requestMap.get("account").toString());
        if(u!=null){
            Map<String ,Object> resultMap=new HashMap<>();
            resultMap.put("nickname",u.getName());
            resultMap.put("sex",u.getSex());
            resultMap.put("credit",u.getCredit());
            resultMap.put("address",u.getAddress());
            resultMap.put("wechat",u.getWechat());
            resultMap.put("introduction",u.getIntroduction());
            resultMap.put("photoSrc","http://"+u.getHeadShotUrl());
            System.out.println(resultMap.get("nickname").toString());
            System.out.println(resultMap.get("sex").toString());
            System.out.println(resultMap.get("credit").toString());
            System.out.println(resultMap.get("address").toString());
            System.out.println(resultMap.get("wechat").toString());
            System.out.println(resultMap.get("introduction").toString());
            return resultMap;
        }
        return null;
    }

    @RequestMapping("/personalSpace/getPersonalRequest")
    @ResponseBody
    public Map<String,Object> getPRequest(@RequestBody Map<String,Object> requestMap){
        System.out.println("getPRequest");
        String uid=requestMap.get("account").toString();
        List<Integer> list=userBLService.getPersonalRequest(uid);
        User user = userBLService.getSingle(uid);
        List<CostRecord> recordList =JSON.parseArray(user.getListOfCRecord(),CostRecord.class);
        Date[] dates = new Date[6];
        String[] mydates = new String[6];
        int[] costs = new int[6];
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dates[0] = new Date();
        mydates[0] = simpleDateFormat.format(dates[0]);
        for(CostRecord costRecord : recordList){
            if(simpleDateFormat.format(costRecord.getDate()).equals(mydates[0])){
                costs[0] = costRecord.getCredit();
                break;
            }
        }
        for(int i = 1;i<6;i++){
            dates[i] = new Date(dates[i-1].getTime() - 86400000L);
            mydates[i] = simpleDateFormat.format(dates[i]);
            for(CostRecord costRecord : recordList){
                if(simpleDateFormat.format(costRecord.getDate()).equals(mydates[i])){
                    costs[i] = costRecord.getCredit();
                    break;
                }

            }
        }

        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("costCredit",list.get(0));
        resultMap.put("complete",list.get(1));
        resultMap.put("undergoing",list.get(2));
        resultMap.put("uncomplete",list.get(3));
        resultMap.put("total",list.get(4));
        resultMap.put("dates",mydates);
        resultMap.put("creditsOfDate",costs);
/*        resultMap.put("costCredit",100);
        resultMap.put("complete",2);
        resultMap.put("undergoing",2);
        resultMap.put("uncomplete",1);
        resultMap.put("total",5);*/
        System.out.println(resultMap.get("costCredit").toString());
        System.out.println(resultMap.get("complete").toString());
        System.out.println(resultMap.get("undergoing").toString());
        System.out.println(resultMap.get("uncomplete").toString());
        System.out.println(resultMap.get("total").toString());
        return resultMap;
    }

    @RequestMapping("/personalSpace/getPersonalCompletion")
    @ResponseBody
    public Map<String,Object> getPCompletion(@RequestBody Map<String,Object> requestMap){
        System.out.println("getPCompletion");
        String uid=requestMap.get("account").toString();
        List<Integer> list=userBLService.getPersonalCompletion(uid);
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("earnedCredit",list.get(0));
        resultMap.put("complete",list.get(1));
        resultMap.put("undergoing",list.get(2));
        resultMap.put("uncomplete",list.get(3));
        resultMap.put("rank",list.get(4));
/*        resultMap.put("earnedCredit",100);
        resultMap.put("complete",2);
        resultMap.put("undergoing",1);
        resultMap.put("uncomplete",1);
        resultMap.put("rank",5);*/
        System.out.println(resultMap.get("earnedCredit").toString());
        System.out.println(resultMap.get("complete").toString());
        System.out.println(resultMap.get("undergoing").toString());
        System.out.println(resultMap.get("uncomplete").toString());
        System.out.println(resultMap.get("rank").toString());
        return resultMap;
    }

    @RequestMapping("/personalSpace/getPersonalAchievements")
    @ResponseBody
    public Map<String,Object> getPersonalCompletion(@RequestBody Map<String,Object> requestMap){
        String uid=requestMap.get("account").toString();
        User u=userBLService.getSingle(uid);
        double [][] a=new double[11][5];
        for(int i=0;i<11;i++){
            for(int j=0;j<5;j++){
                a[i][j]=0;
            }
        }
        int i = 0;
        for(Title t: JSON.parseArray(u.getListOfTitles(),Title.class)){
            a[i++] = t.toArray();
        }
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("data",a);
        return resultMap;
    }

    @RequestMapping("/personalSpace/editPersonalInformation")
    @ResponseBody
    public boolean editInformation(@RequestBody Map<String,Object> requestMap){
        System.out.println("editPersonalInformation");
        String nickname=requestMap.get("nickname").toString();
        String wechat=requestMap.get("wechat").toString();
        String address=requestMap.get("address").toString();
        String description=requestMap.get("description").toString();
        String sex=requestMap.get("sex").toString();
        String uid=requestMap.get("account").toString();
        String photoSrc=requestMap.get("photoSrc").toString();
        User u=userBLService.getSingle(uid);
        u.setName(nickname);
        if(photoSrc!=null&&photoSrc.length()!=0)
            u.setHeadShotUrl(photoSrc);
        u.setWechat(wechat);
        u.setAddress(address);
        u.setIntroduction(description);
        u.setSex(sex);
        userBLService.modifyUser(u);
        return true;
    }
}
