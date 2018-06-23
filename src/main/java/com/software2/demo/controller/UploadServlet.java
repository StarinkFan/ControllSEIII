package com.software2.demo.controller;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.exceptions.ClientException;
import com.software2.demo.bean.InitTask;
import com.software2.demo.bean.Title;
import com.software2.demo.bean.User;
import com.software2.demo.bean.picture;
import com.software2.demo.service.InitTaskBLService;
import com.software2.demo.service.PictureBLService;
import com.software2.demo.service.UserBLService;
import com.software2.demo.util.SendTextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
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
public class UploadServlet {
    @Autowired
    PictureBLService pS;
    @Autowired
    InitTaskBLService iS;
    @Autowired
    UserBLService userBLService;
    @RequestMapping("/taskAccouncement/info")
    @ResponseBody
    public boolean publishTask(@RequestBody Map<String,Object> requestMap){
        System.out.println("publishTask");
        List<String> listOfTags= (List<String>) requestMap.get("taskMarks");
        List<String> url= (List<String>) requestMap.get("picid");
        String requestorID=requestMap.get("requestorID").toString();
        int num=Integer.parseInt(requestMap.get("num").toString());
        int credit=Integer.parseInt(requestMap.get("credit").toString());
        int tag=Integer.parseInt(requestMap.get("tag").toString());
        String request=requestMap.get("request").toString();
        String requestorName=requestMap.get("requestorName").toString();
        String date=requestMap.get("date").toString();
        int hasQ= Integer.parseInt(requestMap.get("hasQuestion").toString());
        String question=requestMap.get("question").toString();
        List<String> options= (List<String>) requestMap.get("options");
        String answer=requestMap.get("questionAnswer").toString();
        String kind=requestMap.get("kind").toString();
        String temp=date.substring(0,4)+date.substring(5,7)+date.substring(8,10);
        Long deadline=Long.parseLong(temp);
        List<Integer> pID=new ArrayList<>();

        for(String str:url){
            picture p=new picture();
            p.setUrl(str);
            List<Integer> lID=new ArrayList<>();
            String strlID= JSON.toJSONString(lID);
            p.setLID(strlID);
            p.setTag(tag);
            p.setPID(requestorID);
            p.setListOfAnswers(JSON.toJSONString(new ArrayList<>()));
            pID.add(pS.addPicture(p).getID());
        }
        InitTask i=new InitTask();
        List<String> user=new ArrayList<>();
        i.setRemarks("");
        i.setState(3);
        i.setCredit(credit);
        i.setDeadline(deadline);
        i.setEndTime(0);
        i.setHasQuestion(hasQ);
        i.setQuestion(question);
        i.setListOfOptions(JSON.toJSONString(options));
        i.setAnswer(answer);
        i.setInitorID(requestorID);
        i.setInitorName(requestorName);
        String strpID=JSON.toJSONString(pID);
        i.setListOfP(strpID);
        String strUser=JSON.toJSONString(user);
        i.setListOfWoker(strUser);
        i.setNum(num);
        i.setRequest(request);
        i.setStartTime(0);
        i.setTag(tag);
        i.setListOfTags(JSON.toJSONString(listOfTags));
        i.setKind(kind);
        i.setWrongAnswerWorker(JSON.toJSONString(new ArrayList<>()));
        int iID=iS.addITask(i).getID();
        List<User> listOfUser=userBLService.getAllUser();
        for(User u:listOfUser){
            List<Title> listOfTitle=JSON.parseArray(u.getListOfTitles(), Title.class);
            for(Title t:listOfTitle){
                if(t.getAchieve()==1){
                    if(t.getName().equals(kind)){
                        try {
                            SendTextMessage.sendAnswerChange(u.getID());
                        } catch (ClientException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                }
            }
        }
        User u=userBLService.getSingle(requestorID);
        String strIT=u.getListOfITask();
        List<Integer> iT=JSON.parseArray(strIT,Integer.class);
        iT.add(iID);
        String strIT2=JSON.toJSONString(iT);
        u.setListOfITask(strIT2);
        userBLService.modifyUser(u);
        return true;
    }

    @RequestMapping("/taskAccouncement/uploadImg")
    @ResponseBody
    public Map<String,Object> uploadImg(MultipartFile file[], String areaName) throws Exception {
        System.out.println("得到的areaName:"+areaName);
        List<String> url=new ArrayList<>();
        for (MultipartFile f : file) {
            System.out.println(f.getSize());
            System.out.println(f.getBytes());
            FileInputStream inputStream=(FileInputStream) f.getInputStream();
            String path=pS.uploadPicture(inputStream);
            url.add(path);
        }
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("picid",url);
        return resultMap;
    }
}
