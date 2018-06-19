package com.software2.demo.util;

import com.alibaba.fastjson.JSON;
import com.software2.demo.bean.InitTask;
import com.software2.demo.bean.User;
import com.software2.demo.service.InitTaskBLService;
import com.software2.demo.service.PictureBLService;
import com.software2.demo.service.UserBLService;
import com.software2.demo.service.WorkTaskBLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Component
public class RequestTaskUtil {
    @Autowired
    UserBLService userBLService;
    @Autowired
    PictureBLService pS;
    @Autowired
    InitTaskBLService iS;
    @Autowired
    WorkTaskBLService wS;
    static RequestTaskUtil r;
    @PostConstruct
    public void init(){
        r=this;
        r.iS=this.iS;
        r.pS=this.pS;
        r.userBLService=this.userBLService;
        r.wS=this.wS;
    }
    public static Map<String,Object> getReturn(List<InitTask> list){

        List<Integer> id =new ArrayList<>();
        List<String> Rid=new ArrayList<>();
        List<String> Rname=new ArrayList<>();
        List<String> request=new ArrayList<>();
        List<Integer> tag=new ArrayList<>();
        List<Integer> credit=new ArrayList<>();
        List<Integer> people=new ArrayList<>();
        List<Integer> participant=new ArrayList<>();
        List<String> picurl=new ArrayList<>();
        List<Long> starttime=new ArrayList<>();
        List<Long> deadline=new ArrayList<>();
        List<String> kind=new ArrayList<>();
        List<String> mark=new ArrayList<>();
        List<String> headShots=new ArrayList<>();
        for(InitTask i:list){
            id.add(i.getID());
            Rid.add(i.getInitorID());
            Rname.add(i.getInitorName());
            request.add(i.getRequest());
            tag.add(i.getTag());
            credit.add(i.getCredit());
            people.add(i.getNum());
            participant.add(JSON.parseArray(i.getListOfWoker(),String.class).size());
            picurl.add("http://"+r.pS.getSinglePicture(JSON.parseArray(i.getListOfP(),Integer.class).get(0)).getUrl());
            System.out.println(picurl.get(0));
            starttime.add(i.getStartTime());
            deadline.add(i.getDeadline());
            headShots.add("http://"+r.userBLService.getSingle(i.getInitorID()).getHeadShotUrl());
            kind.add(i.getKind());
            String m="";
            for(String str:JSON.parseArray(i.getListOfTags(),String.class)){
                m=m+str+":";
            }
            mark.add(m);
        }
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("id",id);
        resultMap.put("Rid",Rid);
        resultMap.put("Rname",Rname);
        resultMap.put("request",request);
        resultMap.put("tag",tag);
        resultMap.put("credit",credit);
        resultMap.put("people",people);
        resultMap.put("participant",participant);
        resultMap.put("picurl",picurl);
        resultMap.put("starttime",starttime);
        resultMap.put("deadline",deadline);
        resultMap.put("kind",kind);
        resultMap.put("marklist",mark);
        resultMap.put("rheadshot",headShots);
        return resultMap;
    }

    public static List<InitTask> getTask(Map<String ,Object> requestMap){
        String workerID=requestMap.get("workerID").toString();
        String searchWord=requestMap.get("searchWord").toString();
        String searchKind=requestMap.get("searchKind").toString();

        User worker;
        if(!workerID.equals("admin")) {
            worker=r.userBLService.getSingle(workerID);
        }
        else {
            worker=new User();
        }
        List<InitTask> iT=r.iS.getUndergoing();
        List<InitTask> list=new ArrayList<>();
        for(InitTask i:iT){
            if(i.getRequest().contains(searchWord)&&(i.getKind().equals(searchKind)||searchKind.equals("全部"))){
                boolean in=false;
                if(!workerID.equals("admin")) {
                    for (int wname : JSON.parseArray(worker.getListOfWTask(),Integer.class)) {
                        if (i.getID()==(r.wS.getSingleWTask(wname).getInitTaskID())) {
                            in = true;
                            break;
                        }
                    }
                    for (int iname : JSON.parseArray(worker.getListOfITask(),Integer.class)) {
                        if (i.getID()==(iname)) {
                            in = true;
                            break;
                        }
                    }
                }
                if(!in){
                    list.add(i);
                }
            }
        }
        return list;
    }
}
