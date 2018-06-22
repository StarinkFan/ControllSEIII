package com.software2.demo.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.software2.demo.ResultMessage;
import com.software2.demo.bean.*;
import com.software2.demo.dao.*;
import com.software2.demo.service.LabelBLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class LabelBLImpl implements LabelBLService{
    @Autowired
    LabelDataService labelData;
    @Autowired
    UserDataService userDataService;
    @Autowired
    WorkTaskDataService workTaskDataService;
    @Autowired
    pictureDataService pictureDataService;
    public ResultMessage addLabel(Label label,int WorkTaskID) {
        labelData.save(label);
        WorkTask workTask = workTaskDataService.findById(WorkTaskID).get();
        List<Integer> list = JSON.parseObject(workTask.getListOfLabel(),List.class);
        list.add(label.getID());
        workTask.setListOfLabel(JSON.toJSONString(list));
        workTaskDataService.save(workTask);
        picture picture = pictureDataService.findById(label.getPID()).get();
        List<Integer> list1 = JSON.parseObject(picture.getLID(),List.class);
        list1.add(label.getID());
        picture.setLID(JSON.toJSONString(list1));
        pictureDataService.save(picture);
        return ResultMessage.SUCCESS;
    }

    public List<Label> searchMyLabel(String userID) {
        return labelData.findByGiverID(userID);
    }

    public ResultMessage deleteLabel(List<Integer> labelIDs,int TaskID) {
        ResultMessage rs = ResultMessage.SUCCESS;
        List<Label> list = labelData.findAllById(labelIDs);
        WorkTask workTask = workTaskDataService.findById(TaskID).get();
        String listOfL=workTask.getListOfLabel();
        List<Integer> list1 =JSON.parseArray(listOfL,Integer.class);
        for(Label l:list){
            picture picture = pictureDataService.findById(l.getPID()).get();
            String lisOfLID=picture.getLID();
            List<Integer> i = JSON.parseArray(lisOfLID,Integer.class);
            i.remove(i.indexOf(l.getID()));
            //
            picture.setLID(JSON.toJSONString(i));
            //
            pictureDataService.save(picture);
            list1.remove(list1.indexOf(l.getID()));
        }
        workTaskDataService.save(workTask);
        labelData.deleteInBatch(list);
        return rs;
    }

    public ResultMessage modifyLabel(Label l) {
        labelData.save(l);
        return ResultMessage.SUCCESS;
    }

    public Label getSingleLabel(int id) {
        return labelData.findById(id).get();
    }

    @Override
    public List<Label> getAll() {
        return labelData.findAll();
    }

    @Override
    public WorkTask getWTask(int id) {
        Label l=labelData.findById(id).get();
        String workerID=l.getGiverID();
        User worker=userDataService.findById(workerID).get();
        List<Integer> listOfWorkTask=JSON.parseArray(worker.getListOfWTask(),Integer.class);
        for(int i:listOfWorkTask){
            WorkTask workTask=workTaskDataService.findById(i).get();
            List<Integer> listOfLabel=JSON.parseArray(workTask.getListOfLabel(),Integer.class);
            for(int labelID:listOfLabel){
                if(labelID==id){
                    return workTask;
                }
            }
        }
        return null;
    }
}
