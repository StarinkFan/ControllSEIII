package com.software2.demo.controller;

import com.software2.demo.bean.CostRecord;
import com.software2.demo.bean.Recharge;
import com.software2.demo.dao.RechargeDataService;
import com.software2.demo.service.AdminBLService;
import com.software2.demo.service.UserBLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author：Wang Mo
 * @Description：用于管理员得到数据
 */
@Controller
@Transactional
public class AdminServlet {
    @Autowired
    AdminBLService aS;
    @Autowired
    UserBLService uS;
    @Autowired
    RechargeDataService rD;
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
        Date[] dates = new Date[6];
        String[] mydates = new String[6];
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        dates[0] = new Date();
        mydates[0] = simpleDateFormat.format(dates[0]);
        for(int i = 1;i<6;i++){
            dates[i] = new Date(dates[i-1].getTime() - 86400000L);
            mydates[i] = simpleDateFormat.format(dates[i]);
        }
        int[] peopleNumbers = {1,2,4,4,6,0};
        peopleNumbers[5] = uS.getAllUser().size();
        int[] credits = {0,1000,1000,11000,31000,31000};
        List<Recharge> rechargeList = rD.findAll();
        int sum = 0;
        for(Recharge recharge:rechargeList){
            if(simpleDateFormat.format(recharge.getDate()).equals(mydates[0])){
                sum += recharge.getMoney();
            }
        }
        credits[5] += sum;
        resultMap.put("dates",dates);
        resultMap.put("peopleNumbers", peopleNumbers);
        resultMap.put("credits",credits);
        return resultMap;
    }
}
