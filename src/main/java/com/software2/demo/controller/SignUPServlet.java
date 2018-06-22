package com.software2.demo.controller;

import com.alibaba.fastjson.JSON;
import com.software2.demo.ResultMessage;
import com.software2.demo.bean.User;
import com.software2.demo.service.UserBLService;
import com.software2.demo.util.Md5Utils;
import com.software2.demo.util.SendTextMessage;
import com.software2.demo.util.TextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@Controller
@Transactional
@RequestMapping("/main")
public class SignUPServlet {
    @Autowired
    UserBLService userBLService;
    @RequestMapping("/send")
    @ResponseBody
    public Map<String, Object> sendMsg(@RequestBody Map<String,Object> requestMap) {
        System.out.println("sendMsg");
/*        System.out.println("Get");*/
/*        System.out.println(request);
        Map requestMap= JSON.parseObject(request);*/
        String phoneNumber = requestMap.get("phone").toString();
        System.out.println(phoneNumber);
        if(userBLService.check(phoneNumber)){
            System.out.println(3);
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("mark",3);
            return resultMap;
        }
/*        String randomNum = SendTextMessage.createRandomNum(6);// 生成随机数
        System.out.println(randomNum);
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MINUTE, 5);
        String currentTime = sf.format(c.getTime());// 生成5分钟后时间，用户校验是否过期
        String hash = Md5Utils.getPwd("zmmdmbd" + "@" + currentTime + "@" + randomNum);//生成MD5值
                System.out.println(hash);
        Map<String, Object> resultMap = new HashMap<>();

        resultMap.put("hash", hash);
        resultMap.put("tamp", currentTime);
        resultMap.put("mark",0);*/
        return TextUtil.sendMsg(requestMap);

/*        return resultMap;*/
    }

    @RequestMapping("/signup")
    @ResponseBody
    public int validateNum(@RequestBody Map<String,Object> requestMap) {
        System.out.println("validateNum");
        String phoneNumber = requestMap.get("phone").toString();
        System.out.println(phoneNumber);
        int result=TextUtil.validateNum(requestMap);
        if(result==0){
            User u=new User();
            u.setID(requestMap.get("phone").toString());
            u.setName(requestMap.get("nickname").toString());
            u.setPassword(requestMap.get("pw").toString());
            u.setCredit(0);
            u.setListOfITask(JSON.toJSONString(new ArrayList<>()));
            u.setListOfWTask(JSON.toJSONString(new ArrayList<>()));
            u.setListOfTags(JSON.toJSONString(new ArrayList<>()));
            u.setListOfTitles(JSON.toJSONString(new ArrayList<>()));
            u.setAddress("");
            u.setIntroduction("");
            u.setSex("");
            u.setWechat("");
            u.setListOfCRecord(JSON.toJSONString(new ArrayList<>()));
            u.setHeadShotUrl("p7ogpwb2n.bkt.clouddn.com/默认头像.jpg");
            ResultMessage r=userBLService.addUser(u);
            if(r.equals(ResultMessage.EXIST))
                return 3;
        }
        System.out.println(result);
        return result;
    }
}
