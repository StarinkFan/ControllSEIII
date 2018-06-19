package com.software2.demo.service;

import com.software2.demo.ResultMessage;
import com.software2.demo.bean.InitTask;
import com.software2.demo.bean.User;

import java.util.List;

public interface UserBLService {
    public ResultMessage addUser(User u);
    public User modifyUser(User u);
    public ResultMessage deleteUser(String uid);
    public int charge(String uid, String cardid, String cardpw, int money);
    public User getSingle(String uid);
    public List<User> getAllUser();
    public boolean check(String uid);
    public int login(String uid, String password);
    public String findPass(String uid);
    public List<Integer> getPersonalRequest(String uid);
    public List<Integer> getPersonalCompletion(String uid);
    public void rank(int rank);
    void modifyTitle(InitTask initTask);//对title修改
    public void modify_picTitle(int pic_id,String kind,List<String> anslis,boolean isComplaint,double value,String initorID);//修改单个图片
}
