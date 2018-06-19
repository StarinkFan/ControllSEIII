package com.software2.demo.service.Impl;

import com.alibaba.fastjson.JSON;
import com.software2.demo.bean.picture;
import com.software2.demo.dao.pictureDataService;
import com.software2.demo.service.PictureBLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PictureBLImpl implements PictureBLService{
    @Autowired
    pictureDataService pictureData;

    public String uploadPicture(FileInputStream fileInputStream) {
        QINIUUPLOAD QL = new QINIUUPLOAD();
        String path = QL.uploadImg(fileInputStream,null);

        return path;
    }

    public List<picture> searchUnlabeledPictures(String userID) {

        List<picture> list=pictureData.findAll();
        List<picture> reList=new ArrayList<picture>();
        for(picture p:list){
            String strLID=p.getLID();
            List<Integer> LID= JSON.parseArray(strLID,Integer.class);
            if((p.getLID()==null||LID.size()==0))
                reList.add(p);
        }
        return reList;
    }

    public List<picture> searchLabeledPictures(String userID) {
        List<picture> list=pictureData.findAll();
        List<picture> reList=new ArrayList<picture>();
        for(picture p:list){
            String strLID=p.getLID();
            List<Integer> LID=JSON.parseArray(strLID,Integer.class);
            if((p.getLID()!=null&&LID.size()!=0))
                reList.add(p);
        }
        return reList;
    }
    public List<picture> getAllPictures() {

        return pictureData.findAll();
    }
//    public Label getPlabel(String ID) {
//        return pictureData.getPlabel(ID);
//    }

    public picture getSinglePicture(int picID) {
        return pictureData.findById(picID).get();
    }

    public picture addPicture(picture p) {
        return pictureData.save(p);
    }
}
