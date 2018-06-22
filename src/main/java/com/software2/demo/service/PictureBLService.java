package com.software2.demo.service;

import com.software2.demo.bean.picture;

import java.io.FileInputStream;
import java.util.List;

public interface PictureBLService {
    public String uploadPicture(FileInputStream fileInputStream);
    public List<picture> searchUnlabeledPictures(String userID);
    public List<picture> searchLabeledPictures(String userID);
    public List<picture> getAllPictures();
    public picture getSinglePicture(int picID);
    public picture addPicture(picture p);
    public picture modify(picture p);
}
