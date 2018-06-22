package com.software2.demo.controller;

import com.alibaba.fastjson.JSON;
import com.software2.demo.bean.InitTask;
import com.software2.demo.bean.Label;
import com.software2.demo.bean.picture;
import com.software2.demo.service.InitTaskBLService;
import com.software2.demo.service.LabelBLService;
import com.software2.demo.service.PictureBLService;
import com.software2.demo.service.UserBLService;
import com.software2.demo.util.RequestTaskUtil;
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

@Controller
@Transactional
public class RequestorTaskServlet {
    @Autowired
    PictureBLService pS;
    @Autowired
    UserBLService userBLService;
    @Autowired
    LabelBLService lS;
    @Autowired
    InitTaskBLService iS;
    @RequestMapping("/requestorTask/workerlist")
    @ResponseBody
    public Map<String,Object> getWorkerList(@RequestBody Map<String,Object> map){
        String picID=map.get("picID").toString();
        picture p=pS.getSinglePicture(Integer.parseInt(picID));
        List<Integer> labelID= JSON.parseArray(p.getLID(),Integer.class);
        List<String> workerIDs=new ArrayList<>();
        List<String> workerNames=new ArrayList<>();
        List<Integer> tag=new ArrayList<>();
        for(int lid:labelID){
            Label l=lS.getSingleLabel(lid);
            tag.add(l.getTag());
            workerIDs.add(l.getGiverID());
            workerNames.add(userBLService.getSingle(l.getGiverID()).getName());
        }
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("id",workerIDs);
        resultMap.put("name",workerNames);
        resultMap.put("tag",tag);
        return resultMap;
    }
    @RequestMapping("/requestorTask/unfinished")
    @ResponseBody
    public Map<String,Object> getUnfinishedByID(@RequestBody Map<String,Object> requestMap){
        System.out.println("getUnfinishedByID");
        int sortNum= Integer.parseInt(requestMap.get("sortNum").toString());
        String sortKind=requestMap.get("sortKind").toString();
        String requestorID=requestMap.get("requestorID").toString();
        List<InitTask> ITask=iS.getUnderGoing(requestorID);
        List<InitTask> a=new ArrayList<>();
        for(InitTask i:ITask){
            if(i.getKind().equals(sortKind)||sortKind.equals("全部")){
                a.add(i);
            }
        }
        if(sortNum==1){
            System.out.println("unfinishedByNum");
            for(int i=0;i<a.size()-1;i++){
                for(int j=i+1;j<a.size();j++){
                    if(a.get(i).getNum()>a.get(j).getNum()){
                        InitTask temp=a.get(j);
                        a.set(j,a.get(i));
                        a.set(i,temp);
                    }

                }
            }
        }
        if(sortNum==2){
            System.out.println("getUnfinishedByCredit");
            for(int i=0;i<a.size()-1;i++){
                for(int j=i+1;j<a.size();j++){
                    if(a.get(i).getCredit()>a.get(j).getCredit()){
                        InitTask temp=a.get(j);
                        a.set(j,a.get(i));
                        a.set(i,temp);
                    }

                }
            }
        }

        return RequestTaskUtil.getReturn(a);
    }

/*    @RequestMapping("/requestorTask/unfinishedByCredit")
    @ResponseBody
    public Map<String,Object> getUnfinishedByCredit(@RequestBody Map<String,Object> requestMap){
        System.out.println("getUnfinishedByCredit");
        String requestorID=requestMap.get("requestorID").toString();

        List<InitTask> ITask=iS.getUnderGoing(requestorID);

        for(int i=0;i<ITask.size()-1;i++){
            for(int j=i+1;j<ITask.size();j++){
                if(ITask.get(i).getCredit()>ITask.get(j).getCredit()){
                    InitTask temp=ITask.get(j);
                    ITask.set(j,ITask.get(i));
                    ITask.set(i,temp);
                }

            }
        }

        return RequestTaskUtil.getReturn(ITask);
    }
    @RequestMapping("/requestorTask/unfinishedByNum")
    @ResponseBody
    public Map<String,Object> unfinishedByNum(@RequestBody Map<String,Object> requestMap){
        System.out.println("unfinishedByNum");
        String requestorID=requestMap.get("requestorID").toString();
        List<InitTask> ITask=iS.getUnderGoing(requestorID);
        for(int i=0;i<ITask.size()-1;i++){
            for(int j=i+1;j<ITask.size();j++){
                if(ITask.get(i).getNum()>ITask.get(j).getNum()){
                    InitTask temp=ITask.get(j);
                    ITask.set(j,ITask.get(i));
                    ITask.set(i,temp);
                }

            }
        }
        return RequestTaskUtil.getReturn(ITask);
    }*/

    @RequestMapping("/requestorTask/piclist")
    @ResponseBody
    public Map<String,Object> getPiclist(@RequestBody Map<String,Object> requestMap){
        System.out.println("getPiclist");
        List<Integer> id=new ArrayList<>();
        List<String> url=new ArrayList<>();
        String taskID=requestMap.get("taskID").toString();
        InitTask i=iS.getSingleITask(Integer.parseInt(taskID));
        for(int x=0;x<JSON.parseArray(i.getListOfP(),Integer.class).size();x++){
            id.add(JSON.parseArray(i.getListOfP(),Integer.class).get(x));
            url.add("http://"+pS.getSinglePicture(JSON.parseArray(i.getListOfP(),Integer.class).get(x)).getUrl());
        }
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("id",id);
        resultMap.put("url",url);
        return resultMap;
    }

    @RequestMapping("/requestorTask/nopass")
    @ResponseBody
    public Map<String, Object> getNoPass(@RequestBody Map<String,Object> requestMap){
        System.out.println("getNoPass");
        String requestorID=requestMap.get("requestorID").toString();
        List<InitTask> noPass=iS.getUser(requestorID);
        List<Integer> id=new ArrayList<>();
        List<String> request=new ArrayList<>();
        List<Integer> tag=new ArrayList<>();
        List<Integer> credit=new ArrayList<>();
        List<Integer> people=new ArrayList<>();
        List<String> picurl=new ArrayList<>();
        List<String> reason=new ArrayList<>();
        List<Long> deadline=new ArrayList<>();
        List<String> kind=new ArrayList<>();
        List<String> marklist=new ArrayList<>();
        for(InitTask i:noPass){
            if(i.getState()==4){
                id.add(i.getID());
                request.add(i.getRequest());
                tag.add(i.getTag());
                credit.add(i.getCredit());
                people.add(i.getNum());
                picurl.add("http://"+pS.getSinglePicture(JSON.parseArray(i.getListOfP(),Integer.class).get(0)).getUrl());
                reason.add(i.getRemarks());
                deadline.add(i.getDeadline());
                kind.add(i.getKind());
                String s="";
                for(String str:JSON.parseArray(i.getListOfTags(),String.class)){
                    s=s+str+":";
                }
                marklist.add(s);
            }
        }
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("id",id);
        resultMap.put("request",request);
        resultMap.put("tag",tag);
        resultMap.put("credit",credit);
        resultMap.put("people",people);
        resultMap.put("picurl",picurl);
        resultMap.put("reason",reason);
        resultMap.put("deadline",deadline);
        resultMap.put("kind",kind);
        resultMap.put("marklist",marklist);
        return resultMap;
    }

    /**
     * @Description：返回审核中的任务信息
     */
    @RequestMapping("/requestorTask/review")
    @ResponseBody
    public Map<String,Object> getReview(@RequestBody Map<String,Object> requestMap){
        System.out.println("getReview");
        String requestorID=requestMap.get("requestorID").toString();
        String sortKind=requestMap.get("sortKind").toString();
        List<InitTask> noPass=iS.getUser(requestorID);
        List<Integer> id=new ArrayList<>();
        List<String> request=new ArrayList<>();
        List<Integer> tag=new ArrayList<>();
        List<Integer> credit=new ArrayList<>();
        List<Integer> people=new ArrayList<>();
        List<String> picurl=new ArrayList<>();
        List<Long> deadline=new ArrayList<>();
        List<String> kind=new ArrayList<>();
        List<String> marklist=new ArrayList<>();
        for(InitTask i:noPass){
            if(i.getState()==3){
                if(i.getKind().equals(sortKind)||sortKind.equals("全部")) {
                    id.add(i.getID());
                    request.add(i.getRequest());
                    tag.add(i.getTag());
                    credit.add(i.getCredit());
                    people.add(i.getNum());
                    deadline.add(i.getDeadline());
                    kind.add(i.getKind());
                    String s = "";
                    for (String str : JSON.parseArray(i.getListOfTags(), String.class)) {
                        s = s + str + ":";
                    }
                    marklist.add(s);
                    picurl.add("http://" + pS.getSinglePicture(JSON.parseArray(i.getListOfP(), Integer.class).get(0)).getUrl());
                }
            }
        }
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("id",id);
        resultMap.put("request",request);
        resultMap.put("tag",tag);
        resultMap.put("credit",credit);
        resultMap.put("people",people);
        resultMap.put("picurl",picurl);
        resultMap.put("deadline",deadline);
        resultMap.put("kind",kind);
        resultMap.put("marklist",marklist);
        return resultMap;
    }

//    @RequestMapping("/requestorTask/finishedByNum")
//    @ResponseBody
//    public Map<String,Object> finishedByNum(@RequestBody Map<String,Object> requestMap){
//        System.out.println("finishedByNum");
//        String requestorID=requestMap.get("requestorID").toString();
//        List<InitTask> IT=iS.getDone(requestorID);
//        for(int i=0;i<IT.size()-1;i++){
//            for(int j=i+1;j<IT.size();j++){
//                if(IT.get(i).getNum()>IT.get(j).getNum()){
//                    InitTask temp=IT.get(j);
//                    IT.set(j,IT.get(i));
//                    IT.set(i,temp);
//                }
//
//            }
//        }
//        return RequestTaskUtil.getReturn(IT);
//    }
//
//    @RequestMapping("/requestorTask/finishedByCredit")
//    @ResponseBody
//    public Map<String,Object> getfinishedByCredit(@RequestBody Map<String,Object> requestMap){
//        System.out.println("getfinishedByCredit");
//        String requestorID=requestMap.get("requestorID").toString();
//
//        List<InitTask> IT=iS.getDone(requestorID);
//
//        for(int i=0;i<IT.size()-1;i++){
//            for(int j=i+1;j<IT.size();j++){
//                if(IT.get(i).getCredit()>IT.get(j).getCredit()){
//                    InitTask temp=IT.get(j);
//                    IT.set(j,IT.get(i));
//                    IT.set(i,temp);
//                }
//
//            }
//        }
//
//        return RequestTaskUtil.getReturn(IT);
//    }

    @RequestMapping("/requestorTask/finished")
    @ResponseBody
    public Map<String,Object> getfinishedByID(@RequestBody Map<String,Object> requestMap){
        System.out.println("getfinishedByID");
        System.out.println("testAAAAA");
        String requestorID=requestMap.get("requestorID").toString();
        List<InitTask> IT=iS.getDone(requestorID);
        String sortKind=requestMap.get("sortKind").toString();
        int sortNum= Integer.parseInt(requestMap.get("sortNum").toString());
        List<InitTask> a=new ArrayList<>();
        for(InitTask i:IT){
            if(i.getKind().equals(sortKind)||sortKind.equals("全部")){
                a.add(i);
            }
        }
        if(sortNum==1){
            System.out.println("finishedByNum");
            for(int i=0;i<a.size()-1;i++){
                for(int j=i+1;j<a.size();j++){
                    if(a.get(i).getNum()>a.get(j).getNum()){
                        InitTask temp=a.get(j);
                        a.set(j,a.get(i));
                        a.set(i,temp);
                    }

                }
            }
        }
        if(sortNum==2){
            System.out.println("getfinishedByCredit");
            for(int i=0;i<a.size()-1;i++){
                for(int j=i+1;j<a.size();j++){
                    if(a.get(i).getCredit()>a.get(j).getCredit()){
                        InitTask temp=a.get(j);
                        a.set(j,a.get(i));
                        a.set(i,temp);
                    }

                }
            }
        }
        return RequestTaskUtil.getReturn(a);
    }
/*    @RequestMapping("/requestorTask/EndByNum")
    @ResponseBody
    public Map<String,Object> EndByNum(@RequestBody Map<String,Object> requestMap){
        System.out.println("EndByNum");
        String requestorID=requestMap.get("requestorID").toString();
        List<InitTask> list=iS.getUndone(requestorID);
        for(int i=0;i<list.size()-1;i++){
            for(int j=i+1;j<list.size();j++){
                if(list.get(i).getNum()>list.get(j).getNum()){
                    InitTask temp=list.get(j);
                    list.set(j,list.get(i));
                    list.set(i,temp);
                }

            }
        }
        return RequestTaskUtil.getReturn(list);
    }

    @RequestMapping("/requestorTask/EndByCredit")
    @ResponseBody
    public Map<String,Object> getEndByCredit(@RequestBody Map<String,Object> requestMap){
        System.out.println("getEndByCredit");
        String requestorID=requestMap.get("requestorID").toString();

        List<InitTask> list=iS.getUndone(requestorID);

        for(int i=0;i<list.size()-1;i++){
            for(int j=i+1;j<list.size();j++){
                if(list.get(i).getCredit()>list.get(j).getCredit()){
                    InitTask temp=list.get(j);
                    list.set(j,list.get(i));
                    list.set(i,temp);
                }

            }
        }

        return RequestTaskUtil.getReturn(list);
    }*/

    @RequestMapping("/requestorTask/End")
    @ResponseBody
    public Map<String,Object> getEndByID(@RequestBody Map<String,Object> requestMap){
        System.out.println("getEndByID");
        String requestorID=requestMap.get("requestorID").toString();
        String sortKind=requestMap.get("sortKind").toString();
        List<InitTask> list=iS.getUndone(requestorID);
        int sortNum= Integer.parseInt(requestMap.get("sortNum").toString());
        List<InitTask> a=new ArrayList<>();
        for(InitTask i:list){
            if(i.getKind().equals(sortKind)||sortKind.equals("全部")){
                a.add(i);
            }
        }
        if(sortNum==1){
            System.out.println("EndByNum");
            for(int i=0;i<a.size()-1;i++){
                for(int j=i+1;j<a.size();j++){
                    if(a.get(i).getNum()>a.get(j).getNum()){
                        InitTask temp=a.get(j);
                        a.set(j,a.get(i));
                        a.set(i,temp);
                    }

                }
            }
        }
        if(sortNum==2){
            System.out.println("getEndByCredit");
            for(int i=0;i<a.size()-1;i++){
                for(int j=i+1;j<a.size();j++){
                    if(a.get(i).getCredit()>a.get(j).getCredit()){
                        InitTask temp=a.get(j);
                        a.set(j,a.get(i));
                        a.set(i,temp);
                    }

                }
            }
        }

        return RequestTaskUtil.getReturn(a);
    }

}
