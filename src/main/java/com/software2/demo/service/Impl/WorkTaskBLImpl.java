package com.software2.demo.service.Impl;

import com.alibaba.fastjson.JSON;
import com.software2.demo.ResultMessage;
import com.software2.demo.bean.InitTask;
import com.software2.demo.bean.WorkTask;
import com.software2.demo.bean.WorkerTaskVO;
import com.software2.demo.dao.*;
import com.software2.demo.service.WorkTaskBLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
@Transactional
@Service
public class WorkTaskBLImpl implements WorkTaskBLService{
    @Autowired
    WorkTaskDataService wD;
    @Autowired
    UserDataService uD;
    @Autowired
    InitTaskDataService iD;
    @Autowired
    pictureDataService pD;
    public WorkTask addWTask(WorkTask w) {
        return wD.save(w);
    }

    public ResultMessage modifyTask(WorkTask w) {
        wD.save(w);
        return ResultMessage.SUCCESS;
    }

    public ResultMessage deleteTask(int id) {
        wD.deleteById(id);
        return ResultMessage.SUCCESS;
    }

    public WorkTask getSingleWTask(int id) {
        return wD.findById(id).get();
    }

    public List<WorkTask> getAll() {
        return wD.findAll();
    }

    public List<WorkerTaskVO> getUser(String userID) {
        List<WorkerTaskVO> result = new ArrayList<>();
        List<WorkTask> wLis = wD.findByWorkerID(userID);
        if(wLis==null)
            return result;
        InitTask iT;
        for(WorkTask w:wLis){
            iT = iD.findById(w.getInitTaskID()).get();
            String strLOP=iT.getListOfP();
            List<Integer> listOfP= JSON.parseArray(strLOP,Integer.class);
            String URL = pD.findById(listOfP.get(0)).get().getUrl();
            String strLOWorker=iT.getListOfWoker();
            List<String> listOfWorker=JSON.parseArray(strLOWorker,String.class);
            List<String> mark=JSON.parseArray(iT.getListOfTags(),String.class);
            String strMark="";
            for(String str:mark){
                strMark=strMark+str+":";
            }
            result.add(new WorkerTaskVO(w.getID(),iT.getInitorID(),iT.getInitorName(),iT.getRequest(),iT.getTag(),w.getCredit(),w.getActualCredit(),iT.getNum(),listOfWorker.size(),iT.getStartTime(), iT.getEndTime(),iT.getDeadline(),URL,w.getState(),w.getType(), iT.getKind(),strMark,uD.findById(iT.getInitorID()).get().getHeadShotUrl()));
        }
        return result;
    }

    public List<WorkTask> getUndone(String userID) {
        List<WorkTask> list=wD.findByWorkerID(userID);
        List<WorkTask> toReturn=new ArrayList<>();
        if(list==null)
            return toReturn;
        for(WorkTask w:list){
            if(w.getState()==0)
                toReturn.add(w);
        }
        return toReturn;
    }

    public List<WorkTask> getDone(String userID) {
        List<WorkTask> list=wD.findByWorkerID(userID);
        List<WorkTask> toReturn=new ArrayList<>();
        if(list==null)
            return toReturn;
        for(WorkTask w:list){
            if(w.getState()==1)
                toReturn.add(w);
        }
        return toReturn;
    }

    public List<WorkTask> getUnderGoing(String uid) {
        List<WorkTask> list=wD.findByWorkerID(uid);
        List<WorkTask> toReturn=new ArrayList<>();
        if(list==null)
            return toReturn;
        for(WorkTask w:list){
            if(w.getState()==2)
                toReturn.add(w);
        }
        return toReturn;
    }
}
