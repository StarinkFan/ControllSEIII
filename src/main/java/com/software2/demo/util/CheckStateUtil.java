package com.software2.demo.util;

import com.alibaba.fastjson.JSON;
import com.software2.demo.bean.InitTask;
import com.software2.demo.bean.User;
import com.software2.demo.bean.WorkTask;
import com.software2.demo.service.InitTaskBLService;
import com.software2.demo.service.UserBLService;
import com.software2.demo.service.WorkTaskBLService;
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
    static CheckStateUtil c;
    @PostConstruct
    public void init(){
        c=this;
        c.userBLService=this.userBLService;
        c.iS=this.iS;
        c.wS=this.wS;
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
        if(time>=i.getDeadline()){
            w.setState(2);
        }
        w.setEndtime(day);
//        c.userBLService.modifyUser(u);
//        c.userBLService.modifyUser(u2);
        c.wS.modifyTask(w);
        c.iS.modifyIT(i);
    }
}
