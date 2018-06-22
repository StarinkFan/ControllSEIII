package com.software2.demo.controller;

import com.alibaba.fastjson.JSON;
import com.software2.demo.ResultMessage;
import com.software2.demo.bean.*;
import com.software2.demo.service.LabelBLService;
import com.software2.demo.service.PictureBLService;
import com.software2.demo.util.CheckStateUtil;
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
@RequestMapping("/editLabel")
public class EditLabelServlet {
    @Autowired
    PictureBLService pS;
    @Autowired
    LabelBLService lS;
    @RequestMapping("/wholeLabel")
    @ResponseBody
    public Map<String,Object> loadWholeLabel(@RequestBody Map<String,Object> requestMap){
        System.out.println("loadWholeLabel");
        String workerID=requestMap.get("workerAccount").toString();
        String picID=requestMap.get("picID").toString();
        picture p=pS.getSinglePicture(Integer.parseInt(picID));
        List<Integer> lID= JSON.parseArray(p.getLID(),Integer.class);
        Map<String,Object> resultMap=new HashMap<>();
        for(int id:lID){
            Label l=lS.getSingleLabel(id);
            if(l.getTag()==1)
                if(l.getGiverID().equals(workerID)) {
                    Label temp=new Label();
                    temp.setD(l.getD());
                    temp.setTag(l.getTag());
                    temp.setState(l.getState());
                    temp.setInfo(l.getInfo());
                    temp.setPID(l.getPID());
                    temp.setGiverID(l.getGiverID());
                    temp.setLis(l.getLis());
                    temp.setColor(l.getColor());
                    temp.setID(l.getID());
                    Description d=JSON.parseObject(temp.getD(),Description.class);
                    temp.setD(d.getC());
                    resultMap.put("wl",temp);
                    resultMap.put("id",l.getID());
                    System.out.println(resultMap.toString());
/*                    l.setD(JSON.toJSONString(d));*/
                    return resultMap;
                }
        }
        return null;
    }
    @RequestMapping("/modifyWholeLabel")
    @ResponseBody
    public boolean modifyLabel(@RequestBody Map<String,Object> requestMap){
        System.out.println("modifyLabel");

        String info=requestMap.get("info").toString();
        String D=  requestMap.get("d").toString();
        String c=((Map)requestMap.get("d")).get("c").toString();
        System.out.println(D);
        Description d= new Description();
        d.setC(c);
        String pID=requestMap.get("pID").toString();
        String id=requestMap.get("id").toString();
        Label l=lS.getSingleLabel(Integer.parseInt(id));
        l.setInfo(info);
        l.setD(JSON.toJSONString(d));
        l.setPID(Integer.parseInt(pID));
        ResultMessage result=lS.modifyLabel(l);
        if(result==ResultMessage.SUCCESS)

            return true;
        else
            return false;
    }
    @RequestMapping("/modifyPartLabel")
    @ResponseBody
    public boolean modifyPartLabel(@RequestBody Map<String,Object> requestMap){
        System.out.println("modifyPartLabel");
        String info=requestMap.get("info").toString();
        String D=requestMap.get("d").toString();
        String c=((Map)requestMap.get("d")).get("c").toString();
        System.out.println(D);
        Description d= new Description();
        d.setC(c);
        String pID=requestMap.get("pID").toString();
        List<Point> list= (List<Point>) requestMap.get("lis");
        String id=requestMap.get("id").toString();
        Label pl=lS.getSingleLabel(Integer.parseInt(id));
        pl.setInfo(info);
        pl.setLis(JSON.toJSONString(list));
        pl.setPID(Integer.parseInt(pID));
        pl.setD(JSON.toJSONString(d));
        System.out.println(pl.getInfo());
        System.out.println(pl.getPID());
        ResultMessage result=lS.modifyLabel(pl);
        if(result==ResultMessage.SUCCESS)

            return true;
        else
            return false;
    }
    @RequestMapping("/deleteLabel")
    @ResponseBody
    public boolean deleteWholeLabel(@RequestBody Map<String,Object> requestMap){
        System.out.println("deleteWholeLabel");
        String TaskID=requestMap.get("TaskID").toString();
        String lID=requestMap.get("labelID").toString();
        List<Integer> todelete=new ArrayList<>();
        todelete.add(Integer.parseInt(lID));
        ResultMessage result=lS.deleteLabel(todelete,Integer.parseInt(TaskID));
        CheckStateUtil.checkWT(Integer.parseInt(TaskID));
        if(result==ResultMessage.SUCCESS)
            return true;
        else
            return false;

    }
    @RequestMapping("/partLabel")
    @ResponseBody
    public Map<String,Object> loadPartLabel(@RequestBody Map<String,Object> requestMap){
        System.out.println("loadPartLabel");
        String workerID=requestMap.get("workerAccount").toString();
        String picID=requestMap.get("picID").toString();
        picture p=pS.getSinglePicture(Integer.parseInt(picID));
        List<Integer> plID=JSON.parseArray(p.getLID(),Integer.class);
        Map<String,Object> resultMap=new HashMap<>();
        for(int id:plID){
            Label pl=lS.getSingleLabel(id);
            if(pl.getTag()!=1)
                if(pl.getGiverID().equals(workerID)) {
                    Label temp=new Label();
                    temp.setD(pl.getD());
                    temp.setTag(pl.getTag());
                    temp.setState(pl.getState());
                    temp.setInfo(pl.getInfo());
                    temp.setPID(pl.getPID());
                    temp.setGiverID(pl.getGiverID());
                    temp.setLis(pl.getLis());
                    temp.setColor(pl.getColor());
                    temp.setID(pl.getID());
                    Description d=JSON.parseObject(pl.getD(),Description.class);
                    temp.setD(d.getC());
                    List<String> answer=JSON.parseArray(p.getListOfAnswers(),String.class);
                    resultMap.put("pl",temp);
                    resultMap.put("id",pl.getID());
                    resultMap.put("answer",answer);
                    return resultMap;
                }
        }
        return null;
    }


}
