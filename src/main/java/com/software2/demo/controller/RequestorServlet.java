package com.software2.demo.controller;

import com.software2.demo.service.UserBLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@Transactional
public class RequestorServlet {
    @Autowired
    UserBLService userBLService;
    @RequestMapping("/requestor/recharge")
    @ResponseBody
    public int recharge(@RequestBody Map<String,Object> requestMap){
        System.out.println("recharge");
        String uid=requestMap.get("account").toString();
        String cardID=requestMap.get("cardID").toString();
        String cardPW=requestMap.get("cardPW").toString();
        int money=Integer.parseInt(requestMap.get("money").toString());
        return userBLService.charge(uid,cardID,cardPW,money);
    }
}
