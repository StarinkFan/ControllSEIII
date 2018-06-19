package com.software2.demo.service;

import com.software2.demo.ResultMessage;
import com.software2.demo.bean.InitTask;

import java.util.List;

public interface InitTaskBLService {
    public InitTask addITask(InitTask i);
    public InitTask getSingleITask(int id);
    public List<InitTask> getAll();
    public List<InitTask> getUser(String userID);
    public List<InitTask> getUndone(String userID);
    public List<InitTask> getDone(String userID);
    public List<InitTask> getUnderGoing(String userID);
    public List<InitTask> getUndergoing();
    public List<InitTask> getUncomplete();
    public List<InitTask> getComplete();
    public ResultMessage setState(InitTask i);
    public ResultMessage setRemarks(InitTask i);
    public ResultMessage modifyIT(InitTask i);
}
