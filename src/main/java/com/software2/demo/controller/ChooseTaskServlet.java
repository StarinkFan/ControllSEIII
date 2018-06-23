package com.software2.demo.controller;

import com.alibaba.fastjson.JSON;
import com.software2.demo.bean.*;
import com.software2.demo.service.InitTaskBLService;
import com.software2.demo.service.UserBLService;
import com.software2.demo.service.WorkTaskBLService;
import com.software2.demo.util.AutoIntegrationUtil;
import com.software2.demo.util.RequestTaskUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@Transactional
public class ChooseTaskServlet {

    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Autowired
    InitTaskBLService iS;
    @Autowired
    WorkTaskBLService bS;
    @Autowired
    UserBLService userBLService;
    @Autowired
    WorkTaskBLService workTaskBLService;
    @RequestMapping("/chooseTask/SearchByNum")
    @ResponseBody
    public Map<String,Object> searchByNum(@RequestBody Map<String,Object> requestMap){
        System.out.println("searchByNum");
        List<InitTask> list=RequestTaskUtil.getTask(requestMap);
        for(int i=0;i<list.size()-1;i++){
            for(int j=i+1;j<list.size();j++){
                if(JSON.parseArray(list.get(i).getListOfWoker(),String.class).size()<JSON.parseArray(list.get(j).getListOfWoker(),Integer.class).size()){
                    InitTask temp=list.get(j);
                    list.set(j,list.get(i));
                    list.set(i,temp);
                }
            }
        }
        return RequestTaskUtil.getReturn(list);
    }

    @RequestMapping("/chooseTask/SearchByCredit")
    @ResponseBody
    public Map<String,Object> searchByCredit(@RequestBody Map<String,Object> requestMap){
        System.out.println("searchByCredit");
        List<InitTask> list=RequestTaskUtil.getTask(requestMap);
        for(int i=0;i<list.size()-1;i++){
            for(int j=i+1;j<list.size();j++){
                if(list.get(i).getCredit()<list.get(j).getCredit()){
                    InitTask temp=list.get(j);
                    list.set(j,list.get(i));
                    list.set(i,temp);
                }
            }
        }
        return RequestTaskUtil.getReturn(list);

    }

    @RequestMapping("/chooseTask/SearchByID")
    @ResponseBody
    public Map<String,Object> searchByID(@RequestBody  Map<String,Object> requestMap){
        System.out.println("searchByID");
        List<InitTask> list=RequestTaskUtil.getTask(requestMap);
        return RequestTaskUtil.getReturn(list);
    }

    @RequestMapping("/chooseTask/gain")
    @ResponseBody
    public boolean gain(@RequestBody Map<String,Object> requestMap){
        System.out.println("gain");
        String uid=requestMap.get("workerID").toString();
        String tID=requestMap.get("taskID").toString();

        WorkTask w=new WorkTask();
        Date date = new Date();
        InitTask i=iS.getSingleITask(Integer.parseInt(tID));
        w.setStarttime(date);
        w.setActualCredit(0);
        w.setCredit(i.getCredit()/i.getNum());
        w.setInitTaskID(i.getID());
        w.setState(0);
        w.setType(2);
        w.setWorkerID(uid);
        List<Integer> l=new ArrayList<>();
        w.setListOfLabel(JSON.toJSONString(l));
        int wID=bS.addWTask(w).getID();
        User u=userBLService.getSingle(uid);
        List<Integer> list=JSON.parseArray(u.getListOfWTask(),Integer.class);
        list.add(wID);
        u.setListOfWTask(JSON.toJSONString(list));
        userBLService.modifyUser(u);
        return true;
    }

    @RequestMapping("/chooseTask/Recommendation")
    @ResponseBody
    public Map<String,Object> recommendation(@RequestBody Map<String,Object> requestMap){
        AutoIntegrationUtil autoIntegrationUtil = new AutoIntegrationUtil();
        String userID=requestMap.get("workerID").toString();
        User u=userBLService.getSingle(userID);
        List<String> listOfTags=JSON.parseArray(u.getListOfTags(),String.class);
        List<InitTask> listOfTasks=iS.getUndergoing();
        List<InitTask> list=new ArrayList<>();
        for(InitTask i:listOfTasks){
            if(!i.getInitorID().equals(userID)) {
                boolean in = false;
                List<String> tags = JSON.parseArray(i.getListOfTags(), String.class);
                for (String str : tags) {
                    for (String str2 : listOfTags) {
                        if (autoIntegrationUtil.calculate_alike(str,str2)>0.7) {
                            in = true;
                            break;
                        }
                    }
                    if (in)
                        break;
                }
                if (in) {
                    boolean alreadyin = false;
                    for (int id : JSON.parseArray(u.getListOfWTask(), Integer.class)) {
                        WorkTask w = workTaskBLService.getSingleWTask(id);
                        if (w.getInitTaskID() == i.getID()) {
                            alreadyin = true;
                            break;
                        }


                    }
                    if (!alreadyin) {
                        list.add(i);
                    }
                }
            }
        }
        return RequestTaskUtil.getReturn(list);
    }
    @RequestMapping("/chooseTask/gainQuestion")
    @ResponseBody
    public Map<String,Object> gainQuestion(@RequestBody Map<String,Object> requestMap){
        String workerID=requestMap.get("workerID").toString();
        int taskID= Integer.parseInt(requestMap.get("taskID").toString());
        InitTask i=iS.getSingleITask(taskID);
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("hasQuestion",i.getHasQuestion());
        resultMap.put("question",i.getQuestion());
        List<String> options=JSON.parseArray(i.getListOfOptions(),String.class);
        resultMap.put("qOptions",options);
        resultMap.put("answer",i.getAnswer());
        List<String> wrongAnswerWorker=JSON.parseArray(i.getWrongAnswerWorker(),String.class);
        int in=0;
        for(String str:wrongAnswerWorker){
            if(str.equals(workerID)){
                in=1;
            }
        }
        resultMap.put("isQualified",in);
        return resultMap;
    }

    @RequestMapping("/chooseTask/gainSuccess")
    @ResponseBody
    public boolean gainSuccess(@RequestBody Map<String,Object> requestMap){
        String workerID=requestMap.get("workerID").toString();
        String taskID=requestMap.get("taskID").toString();
        WorkTask w=new WorkTask();
        InitTask i=iS.getSingleITask(Integer.parseInt(taskID));
        w.setActualCredit(0);
        w.setCredit(i.getCredit()/i.getNum());
        w.setInitTaskID(i.getID());
        w.setState(0);
        w.setType(2);
        w.setWorkerID(workerID);
        List<Integer> l=new ArrayList<>();
        w.setListOfLabel(JSON.toJSONString(l));
        int wID=bS.addWTask(w).getID();
        User u=userBLService.getSingle(workerID);
        List<Integer> list=JSON.parseArray(u.getListOfWTask(),Integer.class);
        list.add(wID);
        u.setListOfWTask(JSON.toJSONString(list));
        userBLService.modifyUser(u);
        return true;
    }

    @RequestMapping("/chooseTask/gainFail")
    @ResponseBody
    public boolean gainFail(@RequestBody Map<String,Object> requestMap){
        String workerID=requestMap.get("workerID").toString();
        int taskID= Integer.parseInt(requestMap.get("taskID").toString());
        InitTask i= iS.getSingleITask(taskID);
        List<String> wrongAnswerWorker=JSON.parseArray(i.getWrongAnswerWorker(),String.class);
        wrongAnswerWorker.add(workerID);
        i.setWrongAnswerWorker(JSON.toJSONString(wrongAnswerWorker));
        iS.modifyIT(i);
        return true;
    }
}
