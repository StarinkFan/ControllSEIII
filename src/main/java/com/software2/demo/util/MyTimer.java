package com.software2.demo.util;

import com.software2.demo.bean.InitTask;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MyTimer {
    Calendar calendar = Calendar.getInstance();
    static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmm");
    public static void setTrigger(long endtime, TimerTask t){
        String str = Long.toString(endtime);

        Timer timer = new Timer();
        try {
            timer.schedule(t, simpleDateFormat.parse(str+"0000"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
