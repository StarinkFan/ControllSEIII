package com.software2.demo.controller;

import com.alibaba.fastjson.JSON;
import com.software2.demo.bean.*;
import com.software2.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Transactional
public class ReviewServlet {
    @Autowired
    InitTaskBLService iS;
    @Autowired
    ComplaintBLService complaintBLService;
    @Autowired
    WorkTaskBLService workTaskBLService;
    @Autowired
    UserBLService userBLService;
    @Autowired
    PictureBLService pictureBLService;
    @Autowired
    AppealBLService appealBLService;
    @Autowired
    QueryBLService queryBLService;
    @Autowired
    LabelBLService labelBLService;

    @RequestMapping("/review/pass")
    @ResponseBody
    public Map<String,Object> getPass(){
        System.out.println("getPass");
        List<InitTask> ug=iS.getUndergoing();
        List<InitTask> c=iS.getComplete();
        List<InitTask> uc=iS.getUncomplete();
        List<InitTask> list=new ArrayList<>();
        for(InitTask i:ug){
            list.add(i);
        }
        for(InitTask i:c){
            list.add(i);
        }
        for(InitTask i:uc){
            list.add(i);
        }
        List<Integer> id=new ArrayList<>();
        List<String> requestorid=new ArrayList<>();
        List<String> requestorname=new ArrayList<>();
        List<String> request=new ArrayList<>();
        List<Integer> tag=new ArrayList<>();
        List<Integer> credit=new ArrayList<>();
        List<Integer> people=new ArrayList<>();
        List<Long> deadline=new ArrayList<>();
        List<Integer> condition=new ArrayList<>();
        List<String> kind=new ArrayList<>();
        List<String> marklist=new ArrayList<>();
        for(InitTask i:list){
            id.add(i.getID());
            requestorid.add(i.getInitorID());
            requestorname.add(i.getInitorName());
            request.add(i.getRequest());
            tag.add(i.getTag());
            credit.add(i.getCredit());
            people.add(i.getNum());
            deadline.add(i.getDeadline());
            kind.add(i.getKind());
            List<String> mark= JSON.parseArray(i.getListOfTags(),String.class);
            String strMarkList="";
            for(String str:mark){
                strMarkList=strMarkList+str+":";
            }
            marklist.add(strMarkList);
        }
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("id",id);
        resultMap.put("requestorid",requestorid);
        resultMap.put("requestorname",requestorname);
        resultMap.put("request",request);
        resultMap.put("tag",tag);
        resultMap.put("credit",credit);
        resultMap.put("people",people);
        resultMap.put("deadline",deadline);
        resultMap.put("condition",condition);
        resultMap.put("kind",kind);
        resultMap.put("marklist",marklist);
        return resultMap;
    }

    @RequestMapping("/review/underway")
    @ResponseBody
    public Map<String,Object> getUnderWay(){
        System.out.println("getUnderWay");
        List<InitTask> IT=iS.getAll();
        List<InitTask> list=new ArrayList<>();
        for(InitTask i:IT){
            if(i.getState()==3)
            list.add(i);
        }
        List<Integer> id=new ArrayList<>();
        List<String> requestorid=new ArrayList<>();
        List<String> requestorname=new ArrayList<>();
        List<String> request=new ArrayList<>();
        List<Integer> tag=new ArrayList<>();
        List<Integer> credit=new ArrayList<>();
        List<Integer> people=new ArrayList<>();
        List<Long> deadline=new ArrayList<>();
        List<Integer> condition=new ArrayList<>();
        List<String> nopassreason=new ArrayList<>();
        List<String> kind=new ArrayList<>();
        List<String> marklist=new ArrayList<>();
        for(InitTask i:list){
            id.add(i.getID());
            requestorid.add(i.getInitorID());
            requestorname.add(i.getInitorName());
            request.add(i.getRequest());
            tag.add(i.getTag());
            credit.add(i.getCredit());
            people.add(i.getNum());
            deadline.add(i.getDeadline());
            kind.add(i.getKind());
            List<String> mark= JSON.parseArray(i.getListOfTags(),String.class);
            String strMarkList="";
            for(String str:mark){
                strMarkList=strMarkList+str+":";
            }
            marklist.add(strMarkList);
        }
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("id",id);
        resultMap.put("requestorid",requestorid);
        resultMap.put("requestorname",requestorname);
        resultMap.put("request",request);
        resultMap.put("tag",tag);
        resultMap.put("credit",credit);
        resultMap.put("people",people);
        resultMap.put("deadline",deadline);
        resultMap.put("condition",condition);
        resultMap.put("kind",kind);
        resultMap.put("marklist",marklist);
        return resultMap;
    }

    @RequestMapping("/review/nopass")
    @ResponseBody
    public Map<String,Object> getNoPass(){
        System.out.println("getNoPass");
        List<InitTask> IT=iS.getAll();
        List<InitTask> list=new ArrayList<>();
        for(InitTask i:IT){
            if(i.getState()==4)
            list.add(i);
        }

        List<Integer> id=new ArrayList<>();
        List<String> requestorid=new ArrayList<>();
        List<String> requestorname=new ArrayList<>();
        List<String> request=new ArrayList<>();
        List<Integer> tag=new ArrayList<>();
        List<Integer> credit=new ArrayList<>();
        List<Integer> people=new ArrayList<>();
        List<Long> deadline=new ArrayList<>();
        List<Integer> condition=new ArrayList<>();
        List<String> nopassreason=new ArrayList<>();
        List<String> kind=new ArrayList<>();
        List<String> marklist=new ArrayList<>();
        for(InitTask i:list){
            id.add(i.getID());
            requestorid.add(i.getInitorID());
            requestorname.add(i.getInitorName());
            request.add(i.getRequest());
            tag.add(i.getTag());
            credit.add(i.getCredit());
            people.add(i.getNum());
            deadline.add(i.getDeadline());
            nopassreason.add(i.getRemarks());
            kind.add(i.getKind());
            List<String> mark= JSON.parseArray(i.getListOfTags(),String.class);
            String strMarkList="";
            for(String str:mark){
                strMarkList=strMarkList+str+":";
            }
            marklist.add(strMarkList);
        }
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("id",id);
        resultMap.put("requestorid",requestorid);
        resultMap.put("requestorname",requestorname);
        resultMap.put("request",request);
        resultMap.put("tag",tag);
        resultMap.put("credit",credit);
        resultMap.put("people",people);
        resultMap.put("deadline",deadline);
        resultMap.put("condition",condition);
        resultMap.put("nopassreason",nopassreason);
        resultMap.put("kind",kind);
        resultMap.put("marklist",marklist);
        return resultMap;
    }

    @RequestMapping("/review/complaint")
    @ResponseBody
    public Map<String,Object> getComplaint(){
        Map<String,Object> resultMap=new HashMap<>();
        List<Complaint> list=complaintBLService.getAll();
        List<Integer> id=new ArrayList<>();
        List<String> requestorid=new ArrayList<>();
        List<String> requestorname=new ArrayList<>();
        List<Integer> tag=new ArrayList<>();
        List<String> marklist=new ArrayList<>();
        List<String> rheadshot=new ArrayList<>();
        List<String> workerid=new ArrayList<>();
        List<String> workername=new ArrayList<>();
        List<String> wheadshot=new ArrayList<>();
        List<String> picurl=new ArrayList<>();
        List<Integer> picid=new ArrayList<>();
        List<String> reason=new ArrayList<>();
        List<Integer> workTaskid=new ArrayList<>();
        List<Integer> complaintid=new ArrayList<>();
        for(Complaint c:list){
            if(c.getChecking()==0) {
                InitTask i = iS.getSingleITask(c.getInitTaskID());
                WorkTask w = workTaskBLService.getSingleWTask(c.getWorkTaskID());
                User requestor = userBLService.getSingle(c.getRequestorID());
                User worker = userBLService.getSingle(c.getWorkerID());
                picture p = pictureBLService.getSinglePicture(c.getPictureID());
                id.add(c.getInitTaskID());
                requestorid.add(requestor.getID());
                requestorname.add(requestor.getName());
                tag.get(p.getTag());
                List<String> mark = JSON.parseArray(i.getListOfTags(), String.class);
                String strMark = "";
                for (String str : mark) {
                    strMark = strMark + str + ":";
                }
                marklist.add(strMark);
                rheadshot.add("http://" + requestor.getHeadShotUrl());
                workerid.add(worker.getID());
                workername.add(worker.getName());
                wheadshot.add("http://" + worker.getHeadShotUrl());
                picurl.add("http://" + p.getUrl());
                picid.add(p.getID());
                reason.add(c.getReason());
                workTaskid.add(w.getID());
                complaintid.add(c.getID());
            }
        }
        resultMap.put("id",id);
        resultMap.put("requestorid",requestorid);
        resultMap.put("requestorname",requestorname);
        resultMap.put("tag",tag);
        resultMap.put("marklist",marklist);
        resultMap.put("rheadshot",rheadshot);
        resultMap.put("workerid",workerid);
        resultMap.put("workername",workername);
        resultMap.put("wheadshot",wheadshot);
        resultMap.put("picurl",picurl);
        resultMap.put("picid",picid);
        resultMap.put("reason",reason);
        resultMap.put("workTaskid",workTaskid);
        resultMap.put("complaintid",complaintid);
        return resultMap;
    }

    @RequestMapping("/review/appeal")
    @ResponseBody
    public Map<String,Object> appeal(){
        Map<String,Object> resultMap=new HashMap<>();
        List<Integer> id=new ArrayList<>();
        List<String> requestorid=new ArrayList<>();
        List<String> requestorname=new ArrayList<>();
        List<String> workerid=new ArrayList<>();
        List<String> workername=new ArrayList<>();
        List<Integer> picid=new ArrayList<>();
        List<String> picurl=new ArrayList<>();
        List<String> reason=new ArrayList<>();
        List<Integer> appealid=new ArrayList<>();
        List<Appeal> list=appealBLService.getAll();
        for(Appeal a:list){
            if(a.getChecking()==0) {
                User requestor = userBLService.getSingle(a.getRequestorID());
                User worker = userBLService.getSingle(a.getWorkerID());
                picture p = pictureBLService.getSinglePicture(a.getPictureID());
                id.add(a.getInitTaskID());
                requestorid.add(a.getRequestorID());
                requestorname.add(requestor.getName());
                workerid.add(a.getWorkerID());
                workername.add(worker.getName());
                picid.add(a.getPictureID());
                picurl.add("http://" + p.getUrl());
                reason.add(a.getReason());
                appealid.add(a.getID());
            }
        }
        resultMap.put("id",id);
        resultMap.put("requestorid",requestorid);
        resultMap.put("requestorname",requestorname);
        resultMap.put("workerid",workerid);
        resultMap.put("workername",workername);
        resultMap.put("picid",picid);
        resultMap.put("picurl",picurl);
        resultMap.put("reason",reason);
        resultMap.put("appealid",appealid);
        return resultMap;
    }

    @RequestMapping("/review/arbitration")
    @ResponseBody
    public Map<String,Object> arbitration(){
        List<Integer> picID=new ArrayList<>();
        List<Label> listOfLabel=labelBLService.getAll();
        for(Label l:listOfLabel){
            if(l.getState()==3){
                boolean in=false;
                for(int i:picID){
                    if(l.getPID()==i){
                        in=true;
                        break;
                    }
                }
                if(!in){
                    picID.add(l.getPID());
                }
            }
        }
        List<String> requestorid=new ArrayList<>();
        List<String> requestorname=new ArrayList<>();
        List<String> picurl=new ArrayList<>();
        for(int i:picID){
            picture p=pictureBLService.getSinglePicture(i);
            requestorid.add(p.getPID());
            requestorname.add(userBLService.getSingle(p.getPID()).getName());
            picurl.add("http://"+p.getUrl());
        }
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("requestorid",requestorid);
        resultMap.put("requestorname",requestorname);
        resultMap.put("picid",picID);
        resultMap.put("picurl",picurl);
        return resultMap;
    }

    @RequestMapping("/review/query")
    @ResponseBody
    public Map<String,Object> query(){
        Map<String,Object> resultMap=new HashMap<>();
        List<Integer> id=new ArrayList<>();
        List<String> requestorid=new ArrayList<>();
        List<String> requestorname=new ArrayList<>();
        List<Integer> picid=new ArrayList<>();
        List<String> picurl=new ArrayList<>();
        List<String> reason=new ArrayList<>();
        List<Query> list=queryBLService.getAll();
        List<Integer> queryid=new ArrayList<>();
        for(Query a:list){
            if(a.getChecking()==0) {
                User requestor = userBLService.getSingle(a.getRequestorID());
                picture p = pictureBLService.getSinglePicture(a.getPictureID());
                id.add(a.getInitTaskID());
                requestorid.add(a.getRequestorID());
                requestorname.add(requestor.getName());
                picid.add(a.getPictureID());
                picurl.add("http://" + p.getUrl());
                reason.add(a.getReason());
                queryid.add(a.getID());
            }
        }
        resultMap.put("id",id);
        resultMap.put("requestorid",requestorid);
        resultMap.put("requestorname",requestorname);
        resultMap.put("picid",picid);
        resultMap.put("picurl",picurl);
        resultMap.put("reason",reason);
        resultMap.put("queryid",queryid);
        return resultMap;
    }

/*    *//**
     * @Description：根据图片id，获取list，含标注id，标注information，标注该information的人数，对应的workerid，workername
     *//*
    @RequestMapping("/review/arbitrationlist")
    @ResponseBody
    public Map<String,Object> arbitrationlist(@RequestBody Map<String,Object> requestMap){
        int picID= Integer.parseInt(requestMap.get("picID").toString());
        List<Integer> labelid=new ArrayList<>();
        List<String> info=new ArrayList<>();
        List<Integer> right=new ArrayList<>();
        List<Integer> num=new ArrayList<>();
        List<String> workerid=new ArrayList<>();
        List<String> workername=new ArrayList<>();

    }*/
}
