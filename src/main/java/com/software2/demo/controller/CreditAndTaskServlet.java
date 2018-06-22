package com.software2.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @Author:Wang Mo
 * @Description：
 */
@Controller
@Transactional
public class CreditAndTaskServlet {

    /**
     * @Description：获取所需数据
     */
    @RequestMapping("/creditAndTask/getCreditCondition")
    @ResponseBody
    public Map<String,Object> getCreditCondition(){
        String percents;
        return null;
    }
}
