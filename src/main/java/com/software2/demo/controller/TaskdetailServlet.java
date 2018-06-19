package com.software2.demo.controller;

import com.alibaba.fastjson.JSON;
import com.software2.demo.bean.Label;
import com.software2.demo.bean.Query;
import com.software2.demo.bean.picture;
import com.software2.demo.service.LabelBLService;
import com.software2.demo.service.PictureBLService;
import com.software2.demo.service.QueryBLService;
import com.software2.demo.service.UserBLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
public class TaskdetailServlet {
    @Autowired
    PictureBLService pS;
    @Autowired
    LabelBLService lS;
    @Autowired
    UserBLService userBLService;
    @Autowired
    QueryBLService queryBLService;
    @RequestMapping("/taskDetail")
    @ResponseBody
    public Map<String,Object> taskDetail(@RequestBody Map<String,Object> requestMap){
        int picID=Integer.parseInt(requestMap.get("picID").toString());
        picture p=pS.getSinglePicture(picID);
        List<Integer> listOflabelID= JSON.parseArray(p.getLID(),Integer.class);
        List<Label> listOflabel=new ArrayList<>();
        for(int i:listOflabelID){
            listOflabel.add(lS.getSingleLabel(i));
        }
        List<Integer> labelid=new ArrayList<>();
        List<String> info=new ArrayList<>();
        List<Integer> num=new ArrayList<>();
        List<String> workerid=new ArrayList<>();
        List<String> workername=new ArrayList<>();
        List<Integer> right=new ArrayList<>();
        int count=0;
        for(int i=0;i<listOflabel.size();i++){
            Label l=listOflabel.get(i);
            boolean in=false;
            for(String str:info){
                if(str.equals(l.getInfo())){
                    in=true;
                }
            }
            if(in){
                break;
            }
            else{
                labelid.add(l.getID());
                info.add(l.getInfo());
                count++;
                workerid.add(l.getGiverID());
                workername.add(userBLService.getSingle(l.getGiverID()).getName());
                for(int n=i+1;n<listOflabel.size();n++){
                    Label temp=listOflabel.get(n);
                    if(temp.getInfo().equals(l.getInfo())){
                        labelid.add(temp.getID());
                        info.add(temp.getInfo());
                        count++;
                        right.add(0);//因自动评估暂未实现，此处全部设为0，待改
                        workerid.add(temp.getGiverID());
                        workername.add(userBLService.getSingle(temp.getGiverID()).getName());
                    }
                }
            }
            for(int m=0;m<count;m++){
                num.add(count);
            }
            count=0;
        }
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("labelid",labelid);
        resultMap.put("info",info);
        resultMap.put("num",num);
        resultMap.put("right",right);
        resultMap.put("workerid",workerid);
        resultMap.put("workername",workername);
        return resultMap;
    }

    @RequestMapping("/taskDetail/query")
    @ResponseBody
    public boolean query(@RequestBody Map<String,Object> requestMap){
        Integer picID= Integer.valueOf(requestMap.get("picID").toString());
        Integer taskId= Integer.valueOf(requestMap.get("taskID").toString());
        String queryReason=requestMap.get("queryReason").toString();
        Query query=new Query();
        query.setPictureID(picID);
        query.setInitTaskID(taskId);
        query.setReason(queryReason);
        query.setRequestorID(pS.getSinglePicture(picID).getPID());
        queryBLService.add(query);
        return true;
    }
}
