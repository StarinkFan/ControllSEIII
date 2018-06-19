package com.software2.demo.service.Impl;

import com.alibaba.fastjson.JSON;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;

public class QINIUUPLOAD {
    public static final org.slf4j.Logger logger = LoggerFactory.getLogger(QINIUUPLOAD.class);
    private String accesskey = "otfcH3RzTc0YSPJU1oq5oYkZ6CbwsQ03c__YM025";
    private String secretkey = "PfYruMaVe46_Xm28myeHnIlxWSWC4uVRchpeO8Ph";
    private String bucket = "jackjayimage";
    private String path = "p7ogpwb2n.bkt.clouddn.com";

    public String uploadImg(FileInputStream file, String key){
        Configuration cfg = new Configuration(Zone.autoZone());
        UploadManager UM = new UploadManager(cfg);
        try {
            Auth auth = Auth.create(accesskey, secretkey);
            String upToken = auth.uploadToken(bucket);
            try {
                Response response = UM.put(file,key,upToken,null,null);
                DefaultPutRet putRet = JSON.parseObject(response.bodyString(),DefaultPutRet.class);
                String returnpath = path+"/"+putRet.key;
                logger.info("保存地址={}",returnpath);
                return returnpath;
            } catch (QiniuException e) {
                Response r = e.response;
                System.err.println(r.toString());
                try{
                    System.err.println(r.bodyString());
                } catch (QiniuException e1) {
                    e1.printStackTrace();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

}
