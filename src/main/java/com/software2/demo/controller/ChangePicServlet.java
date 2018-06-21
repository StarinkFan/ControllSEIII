package com.software2.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @Author：Wang Mo
 * @Description：用于标注界面切换图片
 */
@Controller
@Transactional
public class ChangePicServlet {
    /**
     * @Description：
     */
    @RequestMapping("/previousPic/check")
    @ResponseBody
    public Map<String,Object> checkPreviousPic(@RequestBody Map<String,Object> requestMap){
        int picID= Integer.parseInt(requestMap.get("picID").toString());
        return null;
    }
}
