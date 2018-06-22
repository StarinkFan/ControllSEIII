package com.software2.demo.util;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.software2.demo.util.AliyunMessageUtil;

import java.util.HashMap;
import java.util.Map;

public class SendTextMessage {
    public static void sendAnswerChangeToInitor(String phone) throws ClientException {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("phoneNumber", phone);
        paramMap.put("msgSign", "COUNTS众包");
        paramMap.put("templateCode", "SMS_137673485");
        SendSmsResponse sendSmsResponse = AliyunMessageUtil.sendSms(paramMap);
    }
    public static void sendAppealFail(String phone) throws ClientException {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("phoneNumber", phone);
        paramMap.put("msgSign", "COUNTS众包");
        paramMap.put("templateCode", "SMS_137688319");
        SendSmsResponse sendSmsResponse = AliyunMessageUtil.sendSms(paramMap);
    }
    public static void sendBeComplainted(String phone) throws ClientException {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("phoneNumber", phone);
        paramMap.put("msgSign", "COUNTS众包");
        paramMap.put("templateCode", "SMS_137685757");
        SendSmsResponse sendSmsResponse = AliyunMessageUtil.sendSms(paramMap);
    }
    public static void sendComplaintResult(String phone,String result,String worker) throws ClientException {
        String jsonContent= "{\"code\":\"" + result + "\",\"worker\":\""+worker+"\"}";
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("phoneNumber", phone);
        paramMap.put("msgSign", "COUNTS众包");
        paramMap.put("templateCode", "SMS_137655771");
        paramMap.put("jsonContent", jsonContent);
        SendSmsResponse sendSmsResponse = AliyunMessageUtil.sendSms(paramMap);
    }
    public static void sendAnswerChange(String phone) throws ClientException {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("phoneNumber", phone);
        paramMap.put("msgSign", "COUNTS众包");
        paramMap.put("templateCode", "SMS_137685755");
        SendSmsResponse sendSmsResponse = AliyunMessageUtil.sendSms(paramMap);
    }
    public static void sendQuery(String phone,String result) throws ClientException {
        String jsonContent= "{\"result\":\"" + result + "\"}";
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("phoneNumber", phone);
        paramMap.put("msgSign", "COUNTS众包");
        paramMap.put("templateCode", "SMS_137655772");
        paramMap.put("jsonContent", jsonContent);
        SendSmsResponse sendSmsResponse = AliyunMessageUtil.sendSms(paramMap);
    }
    public static String sendMsg(String phoneNumber, String randomNum) throws ClientException {
/*        String randomNum = createRandomNum(6);*/
        System.out.println(randomNum);
        String jsonContent = "{\"code\":\"" + randomNum + "\"}";
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("phoneNumber", phoneNumber);
        paramMap.put("msgSign", "COUNTS众包");
        paramMap.put("templateCode", "SMS_133050179");
        paramMap.put("jsonContent", jsonContent);
        SendSmsResponse sendSmsResponse = AliyunMessageUtil.sendSms(paramMap);
        System.out.println(sendSmsResponse.getCode());
        if(!(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK"))) {
            if(sendSmsResponse.getCode() == null) {
                //这里可以抛出自定义异常
                return "发送验证码失败！";
            }
            if(!sendSmsResponse.getCode().equals("OK")) {
                //这里可以抛出自定义异常
                return "Fail!";
            }
        }
        return "Success";
    }
    /**
     * 生成随机数
     * @param num 位数
     * @return
     */
    public static String createRandomNum(int num){
        String randomNumStr = "";
        for(int i = 0; i < num;i ++){
            int randomNum = (int)(Math.random() * 10);
            randomNumStr += randomNum;
        }
        return randomNumStr;
    }

    public static void main(String args[]){

        try {
            System.out.println(sendMsg("13236552118",createRandomNum(6)));
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}
