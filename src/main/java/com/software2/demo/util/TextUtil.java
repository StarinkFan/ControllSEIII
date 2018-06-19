package com.software2.demo.util;

import com.aliyuncs.exceptions.ClientException;
import org.springframework.web.bind.annotation.RequestBody;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class TextUtil {
    private static final String KEY = "zmmdmbd"; // KEY为自定义秘钥

    public static Map<String, Object> sendMsg(Map<String,Object> requestMap) {
        String phoneNumber = requestMap.get("phone").toString();
        String randomNum = SendTextMessage.createRandomNum(6);// 生成随机数
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MINUTE, 5);
        String currentTime = sf.format(c.getTime());// 生成5分钟后时间，用户校验是否过期
        try {
            SendTextMessage.sendMsg(phoneNumber,randomNum); //此处执行发送短信验证码方法
        } catch (ClientException e) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("mark",1);

            e.printStackTrace();
            return requestMap;
        }
        String hash = Md5Utils.getPwd(KEY + "@" + currentTime + "@" + randomNum);//生成MD5值
        System.out.println(hash);
        Map<String, Object> resultMap = new HashMap<>();

        resultMap.put("hash", hash);
        resultMap.put("tamp", currentTime);
        resultMap.put("mark",0);
/*        System.out.println(resultMap.get("hash").toString());
        System.out.println(resultMap.get("tamp").toString());
        System.out.println(resultMap.get("mark").toString());*/
        return resultMap; //将hash值和tamp时间返回给前端
    }
    public static int validateNum(Map<String,Object> requestMap) {
        String requestHash = requestMap.get("hash").toString();
        String tamp = requestMap.get("tamp").toString();
        String msgNum = requestMap.get("msgNum").toString();
        String hash = Md5Utils.getPwd(KEY + "@" + tamp + "@" + msgNum);
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar c = Calendar.getInstance();
        String currentTime = sf.format(c.getTime());
        if (tamp.compareTo(currentTime) > 0) {
            if (hash.equalsIgnoreCase(requestHash)){
                return 0;
//校验成功
            }else {
//验证码不正确，校验失败
                return 1;
            }
        } else {
            return 2;
// 超时
        }
    }
}
