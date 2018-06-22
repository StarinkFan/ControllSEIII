package com.software2.demo.service.Impl;

import com.software2.demo.bean.UserData;
import com.software2.demo.dao.UserDataDAO;
import com.software2.demo.service.UserDataBLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author:Wang Mo
 * @Description：
 */
@Service
@Transactional
public class UserDataBLImpl implements UserDataBLService{
    @Autowired
    UserDataDAO userDataDAO;
    @Override
    public String getPercents(int id) {
        return userDataDAO.findById(id).get().getPercents();
    }

    @Override
    public String getData0(int id) {
        return userDataDAO.findById(id).get().getData0();
    }

    @Override
    public String getData(int id) {
        return userDataDAO.findById(id).get().getData1();
    }

    @Override
    public String getPicNumbers(int id) {
        return userDataDAO.findById(id).get().getPicNumbers();
    }

    @Override
    public String getCredits(int id) {
        return userDataDAO.findById(id).get().getCredits();
    }

    @Override
    public UserData add(UserData u) {
        return userDataDAO.save(u);
    }
}
