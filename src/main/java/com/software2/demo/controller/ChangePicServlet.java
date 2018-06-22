package com.software2.demo.controller;

import com.alibaba.fastjson.JSON;
import com.software2.demo.bean.InitTask;
import com.software2.demo.bean.Label;
import com.software2.demo.bean.WorkTask;
import com.software2.demo.bean.picture;
import com.software2.demo.service.InitTaskBLService;
import com.software2.demo.service.LabelBLService;
import com.software2.demo.service.PictureBLService;
import com.software2.demo.service.WorkTaskBLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author：Wang Mo
 * @Description：用于标注界面切换图片
 */
@Controller
@Transactional
public class ChangePicServlet {


    @Autowired
    WorkTaskBLService workTaskBLService;
    @Autowired
    InitTaskBLService initTaskBLService;
    @Autowired
    PictureBLService pictureBLService;
    @Autowired
    LabelBLService labelBLService;

    /**
     * @Description：查看已关闭的任务的某张图片的上一张图片
     */
    @RequestMapping("/previousPic/check")
    @ResponseBody
    public Map<String,Object> checkPreviousPic(@RequestBody Map<String,Object> requestMap){
        Map<String,Object> resultMap=new HashMap<>();
        int picID= Integer.parseInt(requestMap.get("picID").toString());
        int TaskID= Integer.parseInt(requestMap.get("TaskID").toString());
        String workerID=requestMap.get("workerAccount").toString();
        WorkTask workTask=workTaskBLService.getSingleWTask(TaskID);
        InitTask initTask=initTaskBLService.getSingleITask(workTask.getInitTaskID());
        List<Integer> listOfPicID= JSON.parseArray(initTask.getListOfP(),Integer.class);
        for(int i=0;i<listOfPicID.size();i++){
            if(listOfPicID.get(i)==picID){
                if(i==0){
                    resultMap.put("result",false);
                }
                else{
                    picture p=pictureBLService.getSinglePicture(listOfPicID.get(i-1));
                    resultMap.put("result",true);
                    resultMap.put("picSrc","http://"+p.getUrl());
                    resultMap.put("picID",p.getID());
                    resultMap.put("kind",p.getTag());
                }
            }
        }
        return resultMap;
    }

    /**
     * @Description：查看已关闭任务的某张图片的下一张图片
     */
    @RequestMapping("/nextPic/check")
    @ResponseBody
    public Map<String,Object> checkNextPic(@RequestBody Map<String,Object> requestMap){
        Map<String,Object> resultMap=new HashMap<>();
        int picID= Integer.parseInt(requestMap.get("picID").toString());
        int TaskID= Integer.parseInt(requestMap.get("TaskID").toString());
        String workerID=requestMap.get("workerAccount").toString();
        WorkTask workTask=workTaskBLService.getSingleWTask(TaskID);
        InitTask initTask=initTaskBLService.getSingleITask(workTask.getInitTaskID());
        List<Integer> listOfPicID= JSON.parseArray(initTask.getListOfP(),Integer.class);
        for(int i=0;i<listOfPicID.size();i++){
            if(listOfPicID.get(i)==picID){
                if(i==listOfPicID.size()-1){
                    resultMap.put("result",false);
                }
                else{
                    picture p=pictureBLService.getSinglePicture(listOfPicID.get(i+1));
                    resultMap.put("result",true);
                    resultMap.put("picSrc","http://"+p.getUrl());
                    resultMap.put("picID",p.getID());
                    resultMap.put("kind",p.getTag());
                }
            }
        }
        return resultMap;
    }

    /**
     * @Description：根据图片ID，workerID，返回未关闭的任务的上一张图片信息，是否被标注以及标注类型
     */
    @RequestMapping("/previousPic/addOrEdit")
    @ResponseBody
    public Map<String,Object> addOrEditPrevious(@RequestBody Map<String,Object> requestMap){
        Map<String,Object> resultMap=new HashMap<>();
        int picID= Integer.parseInt(requestMap.get("picID").toString());
        int TaskID= Integer.parseInt(requestMap.get("TaskID").toString());
        String workerID=requestMap.get("workerAccount").toString();
        WorkTask workTask=workTaskBLService.getSingleWTask(TaskID);
        InitTask initTask=initTaskBLService.getSingleITask(workTask.getInitTaskID());
        List<Integer> listOfPicID= JSON.parseArray(initTask.getListOfP(),Integer.class);
        for(int i=0;i<listOfPicID.size();i++){
            if(listOfPicID.get(i)==picID){
                if(i==0){
                    resultMap.put("result",false);
                }
                else{
                    picture p=pictureBLService.getSinglePicture(listOfPicID.get(i-1));
                    resultMap.put("result",true);
                    resultMap.put("picSrc","http://"+p.getUrl());
                    resultMap.put("picID",p.getID());
                    List<Integer> listOfLabel=JSON.parseArray(workTask.getListOfLabel(),Integer.class);
                    boolean isLabeled=false;
                    for(int labelID:listOfLabel){
                        Label label=labelBLService.getSingleLabel(labelID);
                        if(label.getPID()==p.getID()){

                            resultMap.put("kind",label.getTag());
                            isLabeled=true;
                            resultMap.put("ifLabeled",isLabeled);
                            break;
                        }
                    }
                    if(!isLabeled) {
                        resultMap.put("kind", p.getTag());
                        resultMap.put("ifLabeled",isLabeled);
                    }
                }
            }
        }
        return resultMap;
    }

    /**
     * @Description：根据图片ID，workerID，返回未关闭的任务的下一张图片信息，是否被标注以及标注类型
     */
    @RequestMapping("/nextPic/addOrEdit")
    @ResponseBody
    public Map<String,Object> addOrEditNext(@RequestBody Map<String,Object> requestMap){
        Map<String,Object> resultMap=new HashMap<>();
        int picID= Integer.parseInt(requestMap.get("picID").toString());
        int TaskID= Integer.parseInt(requestMap.get("TaskID").toString());
        String workerID=requestMap.get("workerAccount").toString();
        WorkTask workTask=workTaskBLService.getSingleWTask(TaskID);
        InitTask initTask=initTaskBLService.getSingleITask(workTask.getInitTaskID());
        List<Integer> listOfPicID= JSON.parseArray(initTask.getListOfP(),Integer.class);
        for(int i=0;i<listOfPicID.size();i++){
            if(listOfPicID.get(i)==picID){
                if(i==listOfPicID.size()-1){
                    resultMap.put("result",false);
                }
                else{
                    picture p=pictureBLService.getSinglePicture(listOfPicID.get(i+1));
                    resultMap.put("picSrc","http://"+p.getUrl());
                    resultMap.put("picID",p.getID());
                    resultMap.put("result",true);
                    List<Integer> listOfLabel=JSON.parseArray(workTask.getListOfLabel(),Integer.class);
                    boolean isLabeled=false;
                    for(int labelID:listOfLabel){
                        Label label=labelBLService.getSingleLabel(labelID);
                        if(label.getPID()==p.getID()){
                            isLabeled=true;
                            resultMap.put("ifLabeled",isLabeled);
                            resultMap.put("kind",label.getTag());

                            break;
                        }
                    }
                    if(!isLabeled) {
                        resultMap.put("kind", p.getTag());
                        resultMap.put("ifLabeled",isLabeled);
                    }
                }
            }
        }
        return resultMap;
    }
}
