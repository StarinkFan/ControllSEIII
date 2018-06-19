package com.software2.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class ComplaintServlet {
    @RequestMapping("/complaint/pass")
    @ResponseBody
    public Map<String,Object> getPass(@RequestBody Map<String,Object> requestMap){
        return null;
    }
}
