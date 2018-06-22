package com.software2.demo.controller;

import com.software2.demo.service.UserDataBLService;
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
    private int UserID=1;
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
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("percents",percents);
        resultMap.put("data0",data0);
        resultMap.put("data",data);
        resultMap.put("picNumbers",picNumbers);
        resultMap.put("credits",credits);
        return resultMap;
    }
}
