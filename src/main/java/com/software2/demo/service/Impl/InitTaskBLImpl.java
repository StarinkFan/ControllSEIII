package com.software2.demo.service.Impl;

import com.alibaba.fastjson.JSON;
import com.software2.demo.ResultMessage;
import com.software2.demo.bean.*;
import com.software2.demo.dao.InitTaskDataService;
import com.software2.demo.dao.UserDataService;
import com.software2.demo.service.InitTaskBLService;
import com.software2.demo.service.PictureBLService;
import com.software2.demo.service.UserBLService;
import com.software2.demo.service.WorkTaskBLService;
import com.software2.demo.util.MyTimer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

@Service
@Transactional
public class InitTaskBLImpl implements InitTaskBLService{
    @Autowired
    InitTaskDataService itS;
    @Autowired
    UserDataService uS;
    @Autowired
    UserBLService userBLService;
    @Autowired
    PictureBLService pBL;
    @Autowired
    WorkTaskBLService wB;
    public InitTask addITask(InitTask i) {
        itS.save(i);
        int id = i.getID();
        //设置触发器，当到达任务截止时间任务仍未完成则将任务设置为未完成状态
        MyTimer myTimer = new MyTimer();
        myTimer.setTrigger(i.getDeadline(), new TimerTask() {
            @Override
            public void run() {
                InitTask task = getSingleITask(id);
                if(task.getState() == 2){
                    System.out.println(id);
                    task.setState(0);
                    setState(task);//设置状态

                    //任务完成改变所有该任务中worktask的状态位
                    List<WorkTask> workTaskList = wB.getByInitTaskID(task.getID());
                    for(WorkTask workTask: workTaskList){
                        if(workTask.getState()==0){
                            workTask.setState(3);
                        }else{
                            workTask.setState(2);
                        }
                    }
                    //上方的方法可能没有对其保存，因为find方法返回的是一个数据库对象可以直接修改（可能出错，没有试过）

                    userBLService.modifyTitle(task);
                    System.gc();
                }
            }
        });
        return i;
    }

    public InitTask getSingleITask(int id){
        return itS.findById(id).get();
    }
    public List<InitTask> getAll() {
        return itS.findAll();
    }

    public List<InitTask> getUser(String userID) {
        User user = uS.findById(userID).get();
        List<Integer> list = JSON.parseArray(user.getListOfITask(),Integer.class);
        return itS.findAllById(list);
    }

    public List<InitTask> getUndone(String userID) {
        List<InitTask> ITask=getUser(userID);
        List<InitTask> toReturn=new ArrayList<>();
        if(ITask==null)
            return toReturn;
        for(InitTask i:ITask){
            if(i.getState()==0)
                toReturn.add(i);
        }
        return toReturn;
    }

    public List<InitTask> getDone(String userID) {
        List<InitTask> ITask=getUser(userID);
        List<InitTask> toReturn=new ArrayList<>();
        if(ITask==null)
            return toReturn;
        for(InitTask i:ITask){
            if(i.getState()==1)
                toReturn.add(i);
        }
        return toReturn;
    }
    public List<InitTask> getUnderGoing(String userID) {
        List<InitTask> ITask=getUser(userID);
        List<InitTask> toReturn=new ArrayList<>();
        if(ITask==null)
            return toReturn;
        for(InitTask i:ITask){
            if(i.getState()==2)
                toReturn.add(i);
        }
        return toReturn;
    }
    public List<InitTask> getUndergoing() {
        List<InitTask> list=getAll();
        List<InitTask> left=new ArrayList<>();  //正在进行的任务
        if(list==null)
            return left;
        for(InitTask i:list){
            if(i.getState()==2)
                left.add(i);
        }
        return left;
    }
    public List<InitTask> getUncomplete() {
        List<InitTask> list=getAll();
        List<InitTask> left=new ArrayList<>();  //未完成任务
        if(list==null)
            return left;
        for(InitTask i:list){
            if(i.getState()==0)
                left.add(i);
        }
        return left;
    }
    public List<InitTask> getComplete() {
        List<InitTask> list=getAll();
        List<InitTask> left=new ArrayList<>();  //已完成任务
        if(list==null)
            return left;
        for(InitTask i:list){
            if(i.getState()==1)
                left.add(i);
        }
        return left;
    }
    public ResultMessage setState(InitTask i) {
        int former_state = i.getState();
        itS.save(i);
        if(i.getState()==former_state)
        return ResultMessage.SUCCESS;
        return ResultMessage.FAILED;
    }
    public ResultMessage setRemarks(InitTask i) {
        itS.save(i);
        return ResultMessage.SUCCESS;
    }
    public ResultMessage modifyIT(InitTask i) {
        itS.save(i);
        return ResultMessage.SUCCESS;
    }
}
