package com.software2.demo.controller;

import com.software2.demo.service.PictureBLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Transactional
public class EditPersonalInformationServlet {
    @Autowired
    PictureBLService pS;
    @RequestMapping("/editPersonalInformation/uploadPhoto")
    @ResponseBody
    public Map<String,Object> uploadImg(MultipartFile file, String areaName) throws Exception {

        System.out.println(file.getSize());
        System.out.println(file.getBytes());
        FileInputStream inputStream=(FileInputStream) file.getInputStream();
        String url=pS.uploadPicture(inputStream);
        Map<String,Object> resultMap=new HashMap<>();
        System.out.println(url);
        resultMap.put("photoSrc",url);
        return resultMap;
    }
}
