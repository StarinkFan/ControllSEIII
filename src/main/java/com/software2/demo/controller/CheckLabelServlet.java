package com.software2.demo.controller;

import com.alibaba.fastjson.JSON;
import com.software2.demo.bean.InitTask;
import com.software2.demo.bean.picture;
import com.software2.demo.service.InitTaskBLService;
import com.software2.demo.service.PictureBLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
@Transactional
@RequestMapping("/check")
public class CheckLabelServlet {
    @Autowired
    PictureBLService service;
    @Autowired
    InitTaskBLService iS;
    @RequestMapping("/labeled")
    @ResponseBody
    public String getLabeledPicture(){
        System.out.println("getLabeledPicture");
        List<picture> pList=service.searchLabeledPictures("0000");
/*        List<picture> pList=new ArrayList<>();
        picture p=new picture(null,"http://student.mtstudent.se/~sh14hp3151/wordpress/wp-content/uploads/2015/01/pluvia-2880x1800.jpg","15311",null,null);
        pList.add(p);*/
        String str= JSON.toJSONString(pList);
        System.out.print(str);
        return str;
/*        List<List<String>> rList=new ArrayList<List<String>>();
        for(picture p:pList){
            ArrayList<String> l=new ArrayList<String>();
            l.add(p.getID());
            l.add(p.getURL());
            rList.add(l);
        }
        return rList;*/
    }

    @RequestMapping("/unlabeled")
    @ResponseBody
    public String getUnlabeledPicture(){
        System.out.println("getUnlabeledPicture");
        List<picture> pList=service.searchUnlabeledPictures("0000");
/*        List<picture> pList=new ArrayList<>();
        picture p=new picture("122","http://student.mtstudent.se/~sh14hp3151/wordpress/wp-content/uploads/2015/01/pluvia-2880x1800.jpg","15311",null,null);
        pList.add(p);*/

        String str=JSON.toJSONString(pList);
        System.out.println(str);
        return str;
/*        List<List<String>> rList=new ArrayList<List<String>>();
        for(picture pic:pList){
            ArrayList<String> l=new ArrayList<String>();
            l.add(pic.getID());
            l.add(pic.getURL());
            rList.add(l);
        }
        return rList;*/
    }



    @RequestMapping(value = "/TaskPic")
    public Map<String,Object> getTaskPic(@RequestBody Map<String,Object> requestMap){
        System.out.println("getTaskPic");
        String workerID=requestMap.get("workerID").toString();
        String TaskID=requestMap.get("taskID").toString();
        return null;
    }

    @RequestMapping(value = "/GetITask")
    public Map<String,Object> getITask(@RequestBody Map<String,Object> requestMap){
        System.out.println("getITask");
        String requestorID=requestMap.get("requestorID").toString();
        List<InitTask> list=iS.getUser(requestorID);
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("TaskList",list);
        return resultMap;
    }
}
