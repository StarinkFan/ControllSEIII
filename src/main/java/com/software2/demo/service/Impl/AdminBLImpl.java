package com.software2.demo.service.Impl;

import com.software2.demo.bean.InitTask;
import com.software2.demo.bean.User;
import com.software2.demo.service.AdminBLService;
import com.software2.demo.service.InitTaskBLService;
import com.software2.demo.service.UserBLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
@Transactional
@Service
public class AdminBLImpl implements AdminBLService {
    @Autowired
    UserBLService userBLService;
    @Autowired
    InitTaskBLService iS;
    @Override
    public List<Integer> getAllData() {
        List<Integer> list=new ArrayList<>();
        List<User> userList=userBLService.getAllUser();
        int user=userList.size();
        int credit=0;
        for(int i=0;i<user;i++){
            credit+=userList.get(i).getCredit();
        }
        List<InitTask> CTask=iS.getComplete();
        int complete=CTask.size();
        List<InitTask> UGTask=iS.getUndergoing();
        int undergoing=UGTask.size();
        List<InitTask> UCTask=iS.getUncomplete();
        int uncomplete=UCTask.size();
        List<InitTask> Task=iS.getAll();
        int task=Task.size();
        list.add(credit);
        list.add(complete);
        list.add(undergoing);
        list.add(uncomplete);
        list.add(task);
        list.add(user);
        return list;
    }
}
