package com.software2.demo.service;

import com.software2.demo.bean.UserData;

/**
 * @Author:Wang Mo
 * @Description：
 */
public interface UserDataBLService {
    public String getPercents(int id);
    public String getData0(int id);
    public String getData(int id);
    public String getPicNumbers(int id);
    public String getCredits(int id);
    public UserData add(UserData u);
}
