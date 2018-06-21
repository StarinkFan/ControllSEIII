package com.software2.demo.service;

import com.software2.demo.ResultMessage;
import com.software2.demo.bean.Label;
import com.software2.demo.bean.WorkTask;

import java.util.List;

public interface LabelBLService {
    public ResultMessage addLabel(Label label, int WorkTaskID);
    public List<Label> searchMyLabel(String userID);
    public ResultMessage deleteLabel(List<Integer> labelIDs,int TaskID);
    public ResultMessage modifyLabel(Label l);
    public Label getSingleLabel(int id);
    public List<Label> getAll();
    public WorkTask getWTask(int id);
}
