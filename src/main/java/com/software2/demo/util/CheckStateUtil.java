package com.software2.demo.util;

import com.alibaba.fastjson.JSON;
import com.software2.demo.bean.*;
import com.software2.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Component
public class CheckStateUtil {
    @Autowired
    WorkTaskBLService wS;
    @Autowired
    InitTaskBLService iS;
    @Autowired
    UserBLService userBLService;
    @Autowired
    PictureBLService pictureBLService;
    @Autowired
    LabelBLService labelBLService;
    static CheckStateUtil c;
    @PostConstruct
    public void init(){
        c=this;
        c.userBLService=this.userBLService;
        c.iS=this.iS;
        c.wS=this.wS;
        c.pictureBLService=this.pictureBLService;
        c.labelBLService=this.labelBLService;
    }
    public static void checkWT(int WorkTaskID){
        Date day=new Date();

        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        Long time = Long.parseLong(df.format(day));


        WorkTask w=c.wS.getSingleWTask(WorkTaskID);
        InitTask i=c.iS.getSingleITask(w.getInitTaskID());
//        User u=c.userBLService.getSingle(w.getWorkerID());
//        User u2=c.userBLService.getSingle(i.getInitorID());
        String strLOP=i.getListOfP();
        List<Integer> listOfP= JSON.parseArray(strLOP,Integer.class);
        String strLOL=w.getListOfLabel();
        List<Integer> listOfLabel=JSON.parseArray(strLOL,Integer.class);
        boolean isDeleted=false;
        if(listOfP.size()==listOfLabel.size()){
            String strLOW=i.getListOfWoker();
            List<String> workers=JSON.parseArray(strLOW,String.class);
            workers.add(w.getWorkerID());
            String strW=JSON.toJSONString(workers);
            i.setListOfWoker(strW);
            w.setState(1);
//            w.setActualCredit(w.getCredit());
//            u.setCredit(u.getCredit()+w.getActualCredit());
//            u.setEarncredit(u.getEarncredit()+w.getActualCredit());
//            u2.setCredit(u2.getCredit()-w.getActualCredit());
            if(workers.size()==i.getNum()){
                i.setState(1);
                i.setEndTime(time);

                //任务完成改变所有该任务中worktask的状态位
                List<WorkTask> workTaskList = c.wS.getByInitTaskID(i.getID());
                for(WorkTask workTask: workTaskList){
                    if(workTask.getState()==0){
                        User u=c.userBLService.getSingle(workTask.getWorkerID());
                        List<Integer> listOfWTask=JSON.parseArray(u.getListOfWTask(),Integer.class);
                        List<Integer> newList=new ArrayList<>();
                        for(int wtid:listOfWTask){
                            if(wtid!=workTask.getID()){
                                newList.add(wtid);
                            }
                        }
                        u.setListOfWTask(JSON.toJSONString(newList));
                        c.userBLService.modifyUser(u);

                        List<Integer> labelID=JSON.parseArray(workTask.getListOfLabel(),Integer.class);
                        c.labelBLService.deleteLabel(labelID,workTask.getID());
                        c.wS.deleteTask(workTask.getID());
                        isDeleted=true;
/*                        for(int temp:labelID){
                            Label label=c.labelBLService.getSingleLabel(temp);
                            picture picture=c.pictureBLService.getSinglePicture(label.getPID());
                            List<Integer> listOfLabelID=JSON.parseArray(picture.getLID(),Integer.class);
                            List<Integer> newListOfLabel=new ArrayList<>();
                            for(int temp2:listOfLabelID){
                                if(temp2!=temp){
                                    newListOfLabel.add(temp2);
                                }
                            }
                            picture.setLID(JSON.toJSONString(newListOfLabel));
                            c.pictureBLService.modify(picture);
                            c.labelBLService.deleteLabel()
                        }*/
                    }else{
                        workTask.setState(2);
                    }
                }
                //上方的方法可能没有对其保存，因为find方法返回的是一个数据库对象可以直接修改（可能出错，没有试过）

                c.userBLService.modifyTitle(i);//整合评判修分
                System.out.println("Task Finshed");
            }
        }
        else{
            String strLOW=i.getListOfWoker();
            List<String> workers=JSON.parseArray(strLOW,String.class);
            List<String> newWorker=new ArrayList<>();
            for(String str:workers){
                if(!str.equals(w.getWorkerID())){
                    newWorker.add(str);
                }
            }
            String strNewWorkers=JSON.toJSONString(newWorker);
            i.setListOfWoker(strNewWorkers);
//            u2.setCredit(u2.getCredit()+w.getActualCredit());
//            u.setCredit(u.getCredit()-w.getActualCredit());
//            u.setEarncredit(u.getEarncredit()-w.getActualCredit());
            w.setState(0);
            w.setActualCredit(0);
            if(newWorker.size()!=i.getNum()){
                i.setState(2);
                i.setEndTime(0);
                System.out.println("Task UnderGoing");
            }

        }
//        c.userBLService.modifyUser(u);
//        c.userBLService.modifyUser(u2);
        if(!isDeleted) {
            c.wS.modifyTask(w);
        }
        c.iS.modifyIT(i);
    }
}
