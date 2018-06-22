package com.software2.demo.util;

import com.baidu.aip.nlp.AipNlp;

public class Client {
    //设置APPID/AK/SK
    private static final String APP_ID = "11433698";
    private static final String API_KEY = "zAseVGlXIsWKgd74YCKmEEP1";
    private static final String SECRET_KEY = "CFIXMYGTcPmLkKVPYz0kpLpvg7uugZoM";
    private static AipNlp client= null;

    public static AipNlp getClient(){
        if(client==null){
            client = new AipNlp(APP_ID, API_KEY, SECRET_KEY);
        }
        return client;
    }
}
