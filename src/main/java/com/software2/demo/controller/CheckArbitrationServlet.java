package com.software2.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @Author:Wang Mo
 * @Description：用于接受仲裁答案
 */

@Controller
@Transactional
public class CheckArbitrationServlet {

    /**
     * @Description：接受仲裁返回的正确答案
     */

    @RequestMapping("/checkArbitration")
    @ResponseBody
    public boolean checkArbitration(@RequestBody Map<String,Object> requestMap){
        int taskID= Integer.parseInt(requestMap.get("taakID").toString());
        int picID= Integer.parseInt(requestMap.get("picID").toString());
        List<String> answer= (List<String>) requestMap.get("answer");
        return true;
    }
}
