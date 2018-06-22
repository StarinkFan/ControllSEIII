package com.software2.demo.service.Impl;

import com.alibaba.fastjson.JSON;
import com.software2.demo.ResultMessage;
import com.software2.demo.bean.*;
import com.software2.demo.dao.UserDataService;
import com.software2.demo.dao.WorkTaskDataService;
import com.software2.demo.service.LabelBLService;
import com.software2.demo.service.PictureBLService;
import com.software2.demo.service.UserBLService;
import com.software2.demo.service.WorkTaskBLService;
import com.software2.demo.util.AutoIntegrationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.rmi.CORBA.Tie;
import java.util.ArrayList;
import java.util.List;
@Service
@Transactional
@Component("userBLService")
public class UserBLImpl implements UserBLService{
    //获得专家称号的标准分s
    private static int standard = 1000;
    @Autowired
    UserDataService uS;
    @Autowired
    WorkTaskBLService wS;
    @Autowired
    PictureBLService ps;
    @Autowired
    LabelBLService ls;
    @Autowired
    WorkTaskDataService wD;
    public ResultMessage addUser(User u) {
        if(uS.existsById(u.getID())){
            return ResultMessage.EXIST;
        }
        uS.save(u);
        return ResultMessage.SUCCESS;
    }
    public User modifyUser(User u) {
        return uS.save(u);
    }

    public ResultMessage deleteUser(String uid) {
        uS.deleteById(uid);
        return ResultMessage.SUCCESS;
    }

    public int charge(String uid, String cardid, String cardpw, int money) {
        User u=uS.findById(uid).get();
        if(u==null)
            return 1;
        money+=u.getCredit();
        u.setCredit(money);
        uS.save(u);
        return 0;
    }

    public User getSingle(String uid) {
        if(!uS.existsById(uid)){
            return null;
        }
        User u=uS.findById(uid).get();
        System.out.println(u.getID());
        System.out.println(u.getPassword());
        return uS.findById(uid).get();
    }

    public List<User> getAllUser() {
        return uS.findAll();
    }

    public boolean check(String uid) {
        return uS.existsById(uid);
    }
    public int login(String uid, String password) {
        if(uid.equals("admin")&&password.equals("admin"))
            return 2;
        if(!uS.existsById(uid)){
            return 0;
        }
        User u=uS.findById(uid).get();
        if(u==null)
            return 0;
        if(u.getPassword().equals(password))
            return 1;
        return 0;
    }

    public String findPass(String uid) {
        User u=uS.findById(uid).get();
        if(u!=null)
            return u.getPassword();
        else
            return null;
    }

    public List<Integer> getPersonalRequest(String uid) {
        User u=uS.findById(uid).get();
        List<Integer> list=new ArrayList<>();
        int costCredit=0;
        int complete=0;
        int undergoing=0;
        int uncomplete=0;
        int total=0;
        if(u.getListOfITask()!=null) {
            String strLOITask=u.getListOfITask();
            List<Integer> listOfITask= JSON.parseArray(strLOITask,Integer.class);
            total =listOfITask.size();
        }
        InitTaskBLImpl iS=new InitTaskBLImpl();
        String strLOITask=u.getListOfITask();
        List<Integer> listOfITask=JSON.parseArray(strLOITask,Integer.class);
        for(int i=0;i<total;i++){

            InitTask IT=iS.getSingleITask(listOfITask.get(i));
            if(IT.getState()==0){
                uncomplete++;
                int cost=IT.getCredit();
                String strLOWorker=IT.getListOfWoker();
                List<String> listOfWorker=JSON.parseArray(strLOWorker,String.class);
                costCredit+=cost/IT.getNum()*listOfWorker.size();
            }
            if(IT.getState()==1){
                complete++;
                costCredit+=IT.getCredit();
            }
            if(IT.getState()==2){
                undergoing++;
                int cost=IT.getCredit();
                String strLOWorker=IT.getListOfWoker();
                List<String> listOfWorker=JSON.parseArray(strLOWorker,String.class);
                costCredit+=cost/IT.getNum()*listOfWorker.size();
            }
        }
        list.add(costCredit);
        list.add(complete);
        list.add(undergoing);
        list.add(uncomplete);
        list.add(total);
        return list;
    }

    public List<Integer> getPersonalCompletion(String uid) {
        User u=uS.findById(uid).get();
        int earnedCredit=u.getEarncredit();
        List<WorkTask> CTask=wS.getDone(uid);
        int complete=CTask.size();
        List<WorkTask> UGTask=wS.getUnderGoing(uid);
        int undergoing=UGTask.size();
        List<WorkTask> UCTask=wS.getUndone(uid);
        int uncomplete=UCTask.size();
        int rank=u.getRanking();
        List<Integer> list=new ArrayList<>();
        list.add(earnedCredit);
        list.add(complete);
        list.add(undergoing);
        list.add(uncomplete);
        list.add(rank);
        return list;
    }

    public void rank(int rank) {
        Sort sort = new Sort(Sort.Direction.DESC,"earncredit");
        List<User> userList = uS.findAll(sort);
        int ranking = 1;
        for(User user:userList){
            user.setRanking(ranking);
            ranking++;
        }
    }
    public void modifyTitle(InitTask initTask) {
        List<Integer> pic_ids = JSON.parseArray(initTask.getListOfP(), Integer.class);
        String kind = initTask.getKind();
        double value = (double)initTask.getCredit()/initTask.getNum()/pic_ids.size();
        List<String > ids = JSON.parseArray(initTask.getListOfWoker(),String.class);
        //修改title的时间来计算效率
        for(String id:ids){
            User user = uS.getOne(id);
            double time = 0;
            List<WorkTask> lis = wD.findByWorkerID(id);
            for(WorkTask workTask:lis){
                if(workTask.getInitTaskID()==initTask.getID()){
                    time = (workTask.getEndtime().getTime()-workTask.getStarttime().getTime())/(1000* 60 * 60);
                    break;
                }
            }
            List<Title> titleList = JSON.parseArray(user.getListOfTitles(),Title.class);
            for(Title title:titleList){
                if(title.getName().equals(kind)){
                    title.setTime(title.getTime()+time);
                    break;
                }
            }
            uS.save(user);
        }
        for (int pic_id:pic_ids){

                AutoIntegrationUtil autoIntegrationUtil = new AutoIntegrationUtil();
                List<String> anslist = getAutoAnswers(pic_id, kind,autoIntegrationUtil);
                if(!autoIntegrationUtil.isNeed_human_judge())
                    modify_picTitle(pic_id,kind,anslist,false,value,initTask.getID());
        }

    }

    /*
    修改用户的名称信息
    已知完成的init_task可获得其中pic_id
    可以通过pic得到标注，并进行自动化整合得到正确答案
    通过标注和正确答案得到对title的修改情况，并通过标注得到标注者进行修改

    title修改可能是task完成后自动评估造成的，也可能是申诉造成的，也可能是申诉结束新答案造成的
     */
    public void modify_picTitle(int pic_id,String kind,List<String> anslis,boolean isComplaint,double value,int initID) {
            picture pic = ps.getSinglePicture(pic_id);
            String initorID = pic.getPID();
            List<Integer> label_ids = JSON.parseArray(pic.getLID(),Integer.class);
            if(isComplaint){
                anslis = JSON.parseArray(pic.getListOfAnswers(),String .class);
                pic.setListOfAnswers(JSON.toJSONString(new ArrayList<String>()));
            }else {
                pic.setListOfAnswers(JSON.toJSONString(anslis));
            }
            ps.addPicture(pic);//修改图片的正确答案列表

            //遍历所有标签修改标注者的title
            for(int label_id:label_ids){

                Label label = ls.getSingleLabel(label_id);
                User user = uS.getOne(label.getGiverID());
                int flag = 0;

                List<Title> titles = JSON.parseArray(user.getListOfTitles(),Title.class);
                for(Title title : titles){
                    if(title.getName().equals(kind)){
                        if(isComplaint) title.setNum_of_complete(title.getNum_of_complete()-1);
                        else title.setNum_of_complete(title.getNum_of_complete()+1);
                        for(String answer:anslis) {
                            if (label.getInfo().equals(answer)) {
                                flag = 1;
                                WorkTask thisWT = new WorkTask();
                                List<WorkTask> lis = wD.findByWorkerID(label.getGiverID());
                                for(WorkTask workTask:lis){
                                    if(workTask.getInitTaskID()==initID){
                                        thisWT = workTask;
                                        break;
                                    }
                                }
                                if(isComplaint)
                                    { title.setNum_of_right(title.getNum_of_right()-1);thisWT.setActualCredit(thisWT.getActualCredit()-(int)value);change_score(-value,label.getGiverID());change_score(value,initorID);}
                                else
                                    {title.setNum_of_right(title.getNum_of_right() + 1); thisWT.setActualCredit(thisWT.getActualCredit()+(int)value);label.setState(1);change_score(value,label.getGiverID());change_score(-value,initorID);}
                                    wD.save(thisWT);
                                    break;
                            }
                        }
                        if(flag==0){
                            label.setState(2);
                        }
                        if(isComplaint){
                            flag = 0;
                            label.setState(0);
                        }
                        judgeTitle(title);
                        break;
                    }
                }
                uS.save(user);
            }


    }

    public List<String> getAutoAnswers(int pic_id,String kind,AutoIntegrationUtil autoIntegrationUtil){
            picture pic = ps.getSinglePicture(pic_id);
            List<Integer> label_ids = JSON.parseArray(pic.getLID(),Integer.class);
            for(int label_id:label_ids){
                Label label = ls.getSingleLabel(label_id);
                User user = uS.getOne(label.getGiverID());

                double ability = 0;
                List<Title> titles = JSON.parseArray(user.getListOfTitles(),Title.class);
                for(Title title : titles){
                    if(title.getName().equals(kind)){
                        ability = title.getNum_of_right()/(double)title.getNum_of_complete();
                        break;
                    }
                }

                autoIntegrationUtil.input(label.getGiverID(),label.getInfo(),ability );
            }
            autoIntegrationUtil.run();
            String answer = autoIntegrationUtil.get_label();
            List<String> anslis = new ArrayList<>();
            anslis.add(answer);
            if(autoIntegrationUtil.isNeed_human_judge()){
                for(int label_id:label_ids){
                    Label label = ls.getSingleLabel(label_id);
                    label.setState(3);
                    ls.modifyLabel(label);
                }
            }
            return anslis;
    }
    public void judgeTitle(Title title){
        double[] doubles = title.toArray();
        double newmarks = 4*title.getNum_of_right()-title.getNum_of_complete()+300*doubles[2];//4*正确率*完成数-完成数+效率*k

        title.setMarks(newmarks);

        if(title.getMarks()>=standard){
            title.setAchieve(1);
        }else{
            title.setAchieve(0);
        }
    }
    //对相应id的worker进行修改得分
    public void change_score(double value,String id){
        User user = uS.getOne(id);
        user.setCredit(user.getCredit()+(int)value);
        user.setEarncredit(user.getEarncredit()+(int)value);
        modifyUser(user);
    }
}
