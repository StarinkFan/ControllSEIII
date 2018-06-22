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

/**
 * @Author:Wang Mo
 * @Description：实现返回图片的详细标注结果；生成质疑
 */

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

    /**
     * @Description：根据图片Id，查看图片的标注结果。返回标注id、标注information、是否正确、人数（标注该information的人数、对应的workerid、workername、该工人的标注方式（第一个放正确的）、workTaskID
     */
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
        List<Integer> tag=new ArrayList<>();
        List<Integer> worktaskid=new ArrayList<>();
        int count=0;
        for(int i=0;i<listOflabel.size();i++){
            Label l=listOflabel.get(i);
            boolean in=false;

            //判断同样内容的标注是否已添加进入info
            //先将正确的标注信息加入
            if(l.getState()==1) {
                for (String str : info) {
                    if (str.equals(l.getInfo())) {
                        in = true;
                        break;
                    }
                }
                if (in) {
                    break;
                } else {
                    labelid.add(l.getID());
                    info.add(l.getInfo());
                    count++;
                    workerid.add(l.getGiverID());
                    workername.add(userBLService.getSingle(l.getGiverID()).getName());
                    tag.add(l.getTag());
                    worktaskid.add(lS.getWTask(l.getID()).getID());
                    if(l.getState()==3){
                        right.add(2);
                    }
                    else
                        if(l.getState()==2)
                            right.add(0);
                    else
                        right.add(1);
                    //从当前标注开始，向后查找同内容标注
                    for (int n = i + 1; n < listOflabel.size(); n++) {
                        Label temp = listOflabel.get(n);
                        if (temp.getInfo().equals(l.getInfo())) {
                            labelid.add(temp.getID());
                            info.add(temp.getInfo());
                            count++;
                            if(l.getState()==3){
                                right.add(2);
                            } else
                            if(l.getState()==1)
                                right.add(1);
                            else
                                right.add(0);
                            workerid.add(temp.getGiverID());
                            workername.add(userBLService.getSingle(temp.getGiverID()).getName());
                            tag.add(temp.getTag());
                            worktaskid.add(lS.getWTask(l.getID()).getID());
                        }
                    }
                }
                for (int m = 0; m < count; m++) {
                    num.add(count);
                }
                count = 0;
            }
        }
        for(int i=0;i<listOflabel.size();i++){
            Label l=listOflabel.get(i);
            boolean in=false;

            //判断同样内容的标注是否已添加进入info
            //再将错误的标注信息加入
            if(l.getState()==2||l.getState()==3) {
                for (String str : info) {
                    if (str.equals(l.getInfo())) {
                        in = true;
                        break;
                    }
                }
                if (in) {
                    break;
                } else {
                    labelid.add(l.getID());
                    info.add(l.getInfo());
                    count++;
                    workerid.add(l.getGiverID());
                    workername.add(userBLService.getSingle(l.getGiverID()).getName());
                    tag.add(l.getTag());
                    worktaskid.add(lS.getWTask(l.getID()).getID());
                    if(l.getState()==3){
                        right.add(2);
                    }
                    else
                    if(l.getState()==2)
                        right.add(0);
                    else
                        right.add(1);
                    //从当前标注开始，向后查找同内容标注
                    for (int n = i + 1; n < listOflabel.size(); n++) {
                        Label temp = listOflabel.get(n);
                        if (temp.getInfo().equals(l.getInfo())) {
                            count++;
                            labelid.add(temp.getID());
                            info.add(temp.getInfo());
                            if(l.getState()==3){
                                right.add(2);
                            }
                            else
                            if(l.getState()==2)
                                right.add(0);
                            else
                                right.add(1);
                            workerid.add(temp.getGiverID());
                            workername.add(userBLService.getSingle(temp.getGiverID()).getName());
                            tag.add(temp.getTag());
                            worktaskid.add(lS.getWTask(l.getID()).getID());
                        }
                    }
                }
                for (int m = 0; m < count; m++) {
                    num.add(count);
                }
                count = 0;
            }
        }
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("labelid",labelid);
        resultMap.put("info",info);
        resultMap.put("num",num);
        resultMap.put("right",right);
        resultMap.put("workerid",workerid);
        resultMap.put("workername",workername);
        resultMap.put("tag",tag);
        resultMap.put("worktaskid",worktaskid);
        return resultMap;
    }

    /**
     *@Description：根据initTaskID、图片ID、质疑理由、质疑用户ID，生成质疑
     */
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
        query.setCheck(0);
        queryBLService.add(query);
        return true;
    }
}
