package com.software2.demo.controller;

import com.alibaba.fastjson.JSON;
import com.software2.demo.bean.*;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @Author：Wang Mo
 * @Description：用于查看工人workTask信息
 */
@Controller
@Transactional
public class WorkerTaskServlet {
    @Autowired
    WorkTaskBLService wS;
    @Autowired
    InitTaskBLService iS;
    @Autowired
    PictureBLService pS;
    @Autowired
    LabelBLService lS;

    /**
     * @Description：根据workerID返回workTask的详细信息
     */
    @RequestMapping(value = "/workerTask/total")
    @ResponseBody
    public Map<String,Object> getWTask(@RequestBody Map<String,Object> requestMap){
        System.out.println("getWTask");
        String workerID=requestMap.get("workerID").toString();
        System.out.println(workerID+"test");
        List<WorkerTaskVO> list=wS.getUser(workerID);
        List<Integer> id=new ArrayList<>();
        List<String> Rid=new ArrayList<>();
        List<String> Rname=new ArrayList<>();
        List<String> request=new ArrayList<>();
        List<Integer> tag=new ArrayList<>();
        List<Integer> credit=new ArrayList<>();
        List<Integer> actualcredit=new ArrayList<>();
        List<Integer> people=new ArrayList<>();
        List<Integer> participant=new ArrayList<>();
        List<String> picurl=new ArrayList<>();
        List<Integer> condition=new ArrayList<>();
        List<Integer> achieve=new ArrayList<>();
        List<Long> starttime=new ArrayList<>();
        List<Long> endtime=new ArrayList<>();
        List<Long> deadline=new ArrayList<>();
        for(WorkerTaskVO w:list){
            id.add(w.getID());
            Rid.add(w.getInitorID());
            System.out.println(Rid.get(0));
            Rname.add(w.getInitorName());
            System.out.println(Rname.get(0));
            request.add(w.getTaskRequest());
            tag.add(w.getTag());
            credit.add(w.getCredit());
            actualcredit.add(w.getActualCredit());
            people.add(w.getNum());
            participant.add(w.getPartnum());
            System.out.println(participant.get(0));
            picurl.add("http://"+w.getURL());
            condition.add(w.getState());
            System.out.println();
            achieve.add(w.getType());
            starttime.add(w.getStartTime());
            endtime.add(w.getEndtime());
            deadline.add(w.getDeadline());
        }
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("id",id);
        resultMap.put("Rid",Rid);
        resultMap.put("Rname",Rname);
        resultMap.put("request",request);
        resultMap.put("tag",tag);
        resultMap.put("credit",credit);
        resultMap.put("actualcredit",actualcredit);
        resultMap.put("people",people);
        resultMap.put("participant",participant);
        resultMap.put("picurl",picurl);
        resultMap.put("condition",condition);
        resultMap.put("achieve",achieve);
        resultMap.put("starttime",starttime);
        resultMap.put("endtime",endtime);
        resultMap.put("deadline",deadline);
        System.out.println(requestMap.toString());
        System.out.println(resultMap.toString());
        return resultMap;
    }

    /**
     * 根据
     */
    @RequestMapping("/workerTask/piclist")
    @ResponseBody
    public Map<String,Object> getpicList(@RequestBody Map<String,Object> requestMap){
        System.out.println("getpicList");
        String taskID=requestMap.get("taskID").toString();
        WorkTask w=wS.getSingleWTask(Integer.parseInt(taskID));
        InitTask i=iS.getSingleITask(w.getInitTaskID());
        List<Integer> id=new ArrayList<>();
        List<String> url=new ArrayList<>();
        List<Integer> condition=new ArrayList<>();
        List<Integer> tag=new ArrayList<>();
        for(int pid: JSON.parseArray(i.getListOfP(),Integer.class)){
            id.add(pid);
            picture pic=pS.getSinglePicture(pid);
            url.add("http://"+pic.getUrl());
/*            boolean in=false;*/
            for(int l: JSON.parseArray(w.getListOfLabel(),Integer.class)){
/*                for(String str: pic.getLID()){
                    if(str.equals(w.getListOfLabel().get(c))){
                        condition.add(1);
                        tag.add(1);
                        in=true;
                    }
                }
                for(String str:pic.getPlID()){
                    if(str.equals(w.getListOfLabel().get(c))){
                        condition.add(1);
                        tag.add(plS.getSinglePartLabel(w.getListOfLabel().get(c)).getTag());
                        in=true;
                    }
                }
                if(!in){
                    condition.add(0);
                    if(i.getTag()!=0)
                        tag.add(i.getTag());
                    else
                        tag.add(0);
                    c--;
                }
                in=false;*/
                Label label = lS.getSingleLabel(l);

                    if (label.getPID()==(pid)){
                        condition.add(1);

                        tag.add(label.getTag());

                    }

/*                else{
                    condition.add(0);
                    if(i.getTag()!=0)
                        tag.add(i.getTag());
                    else
                        tag.add(0);
                }*/
            }
            if(condition.size()<id.size())
                condition.add(0);
            if(tag.size()<id.size()){
                if(i.getTag()!=0)
                    tag.add(i.getTag());
                else
                    tag.add(0);
            }
        }
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("id",id);
        resultMap.put("url",url);
        resultMap.put("condition",condition);
        resultMap.put("tag",tag);
        System.out.println(condition.get(0));
        System.out.println(tag.get(0));
        return resultMap;
    }
    @RequestMapping("/workerTask")
    @ResponseBody
    public Map<String,Object> workerTask(@RequestBody Map<String,Object> requestMap){
        String workerID=requestMap.get("workerID").toString();
        int searchCondition= Integer.parseInt(requestMap.get("searchCondition").toString());
        String searchKind=requestMap.get("searchKind").toString();
        List<WorkerTaskVO> list=wS.getUser(workerID);
        List<Integer> id=new ArrayList<>();
        List<String> Rid=new ArrayList<>();
        List<String> Rname=new ArrayList<>();
        List<String> request=new ArrayList<>();
        List<Integer> tag=new ArrayList<>();
        List<Integer> credit=new ArrayList<>();
        List<Integer> actualcredit=new ArrayList<>();
        List<Integer> people=new ArrayList<>();
        List<Integer> participant=new ArrayList<>();
        List<String> picurl=new ArrayList<>();
        List<Integer> condition=new ArrayList<>();
        List<Long> starttime=new ArrayList<>();
        List<Long> endtime=new ArrayList<>();
        List<Long> deadline=new ArrayList<>();
        List<String> kind=new ArrayList<>();
        List<String> mark=new ArrayList<>();
        List<String> rheadshot=new ArrayList<>();
        for(WorkerTaskVO w:list){
            if(w.getKind().equals(searchKind)||searchKind.equals("全部")) {
                if(w.getState()==searchCondition||searchCondition==4) {
                    id.add(w.getID());
                    Rid.add(w.getInitorID());
                    System.out.println(Rid.get(0));
                    Rname.add(w.getInitorName());
                    System.out.println(Rname.get(0));
                    request.add(w.getTaskRequest());
                    tag.add(w.getTag());
                    credit.add(w.getCredit());
                    actualcredit.add(w.getActualCredit());
                    people.add(w.getNum());
                    participant.add(w.getPartnum());
                    System.out.println(participant.get(0));
                    picurl.add("http://" + w.getURL());
                    condition.add(w.getState());
                    System.out.println();
                    starttime.add(w.getStartTime());
                    endtime.add(w.getEndtime());
                    deadline.add(w.getDeadline());
                    mark.add(w.getMark());
                    kind.add(w.getKind());
                    rheadshot.add("http://"+w.getRequestorHeadShotUrl());
                }
            }
        }
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("id",id);
        resultMap.put("Rid",Rid);
        resultMap.put("Rname",Rname);
        resultMap.put("request",request);
        resultMap.put("tag",tag);
        resultMap.put("credit",credit);
        resultMap.put("actualcredit",actualcredit);
        resultMap.put("people",people);
        resultMap.put("participant",participant);
        resultMap.put("picurl",picurl);
        resultMap.put("condition",condition);
        resultMap.put("starttime",starttime);
        resultMap.put("endtime",endtime);
        resultMap.put("deadline",deadline);
        resultMap.put("kind",kind);
        resultMap.put("marklist",mark);
        resultMap.put("rheadshot",rheadshot);
        return resultMap;
    }
    @RequestMapping("/workerTask/UnfinishedPiclist")
    @ResponseBody
    public Map<String,Object> UnfinishedPiclist(@RequestBody Map<String,Object> requestMap){
        System.out.println("UnfinishedPiclist");
        String taskID=requestMap.get("taskID").toString();
        WorkTask w=wS.getSingleWTask(Integer.parseInt(taskID));
        InitTask i=iS.getSingleITask(w.getInitTaskID());
        List<Integer> id=new ArrayList<>();
        List<String> url=new ArrayList<>();
        List<Integer> condition=new ArrayList<>();
        List<Integer> tag=new ArrayList<>();
        List<String> label=new ArrayList<>();

            for (int pid : JSON.parseArray(i.getListOfP(), Integer.class)) {
                id.add(pid);
                picture pic = pS.getSinglePicture(pid);
                url.add("http://" + pic.getUrl());
                /*            boolean in=false;*/
                for (int l : JSON.parseArray(w.getListOfLabel(), Integer.class)) {

                    Label temp = lS.getSingleLabel(l);

                    if (temp.getPID() == (pid)) {
                        condition.add(1);

                        tag.add(temp.getTag());
                        label.add(temp.getInfo());

                    }

                }
                if (condition.size() < id.size())
                    condition.add(0);
                if (tag.size() < id.size()) {
                    if (i.getTag() != 0)
                        tag.add(i.getTag());
                    else
                        tag.add(0);
                }
                if(label.size()<id.size()){
                    label.add("");
                }
            }

        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("id",id);
        resultMap.put("url",url);
        resultMap.put("condition",condition);
        resultMap.put("tag",tag);
        resultMap.put("label",label);
        System.out.println(condition.get(0));
        System.out.println(tag.get(0));
        return resultMap;
    }
    @RequestMapping("/workerTask/FininishedPiclist")
    @ResponseBody
    public Map<String,Object> FininishedPiclist(@RequestBody Map<String,Object> requestMap){
        System.out.println("FininishedPiclist");
        String taskID=requestMap.get("taskID").toString();
        WorkTask w=wS.getSingleWTask(Integer.parseInt(taskID));
        InitTask i=iS.getSingleITask(w.getInitTaskID());
        List<Integer> id=new ArrayList<>();
        List<String> url=new ArrayList<>();
        List<Integer> tag=new ArrayList<>();
        List<String> label=new ArrayList<>();
        List<Integer> isright=new ArrayList<>();
            for (int pid : JSON.parseArray(i.getListOfP(), Integer.class)) {
                id.add(pid);
                picture pic = pS.getSinglePicture(pid);
                url.add("http://" + pic.getUrl());
                /*            boolean in=false;*/
                for (int l : JSON.parseArray(w.getListOfLabel(), Integer.class)) {

                    Label temp = lS.getSingleLabel(l);

                    if (temp.getPID() == (pid)) {

                        tag.add(temp.getTag());
                        label.add(temp.getInfo());
                        if(temp.getState()==3){
                            isright.add(2);
                        }
                        else
                        if(temp.getState()==2)
                            isright.add(0);
                        else
                            isright.add(1);

                    }

                }
                if (tag.size() < id.size()) {
                    if (i.getTag() != 0)
                        tag.add(i.getTag());
                    else
                        tag.add(0);
                }
                if(label.size()<id.size()){
                    label.add("");
                }
                if(isright.size()<id.size()){
                    isright.add(0);
                }
            }
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("id",id);
        resultMap.put("url",url);
        resultMap.put("tag",tag);
        resultMap.put("label",label);
        resultMap.put("isright",isright);
        System.out.println(tag.get(0));
        return resultMap;
    }
}
