package com.software2.demo.service;

import com.software2.demo.ResultMessage;
import com.software2.demo.bean.WorkTask;
import com.software2.demo.bean.WorkerTaskVO;

import java.util.List;

public interface WorkTaskBLService {
    public WorkTask addWTask(WorkTask w);
    public ResultMessage modifyTask(WorkTask w);
    public ResultMessage deleteTask(int id);
    public WorkTask getSingleWTask(int id);
    public List<WorkTask> getAll();
    public List<WorkerTaskVO> getUser(String userID);
    public List<WorkTask> getUndone(String userID);
    public List<WorkTask> getDone(String userID);
    public List<WorkTask> getUnderGoing(String uid);
    public List<WorkTask> getByInitTaskID(int id);
}
