package com.software2.demo.controller;

import com.software2.demo.bean.User;
import com.software2.demo.service.UserBLService;
import com.software2.demo.util.TextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;
@Transactional
@Controller
public class PasswordServlet {
    @Autowired
    UserBLService userBLService;
    @RequestMapping(value = "/main/findSend")
    @ResponseBody
    public Map<String, Object> sendMsg(@RequestBody Map<String,Object> requestMap){
        System.out.println("sendMsg");
        if(!userBLService.check(requestMap.get("phone").toString())){
            Map<String,Object> resultMap=new HashMap<>();
            resultMap.put("mark",2);
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
        resultMap.put("mark",0);
        return resultMap;*/
        return TextUtil.sendMsg(requestMap);
    }

    @RequestMapping(value = "main/findPassword")
    @ResponseBody
    public int validateNum(@RequestBody Map<String,Object> requestMap){
        System.out.println("validateNum");
        int r=TextUtil.validateNum(requestMap);
        if(r==0){
            String password=userBLService.findPass(requestMap.get("phone").toString());
            if(password==null)
                return 3;
            else
                return 0;
        }
        return r;
    }

    @RequestMapping("/main/resetPW")
    @ResponseBody
    public boolean resetPW(@RequestBody Map<String,Object> requestMap){
        System.out.println("resetPW");
        String uid=requestMap.get("account").toString();
        String pw=requestMap.get("pw").toString();
        User u=userBLService.getSingle(uid);
        if(u==null)
            return false;
        System.out.println(u.getPassword());
        u.setPassword(pw);
        System.out.println(u.getPassword());
        userBLService.modifyUser(u);
        return true;
    }
}
