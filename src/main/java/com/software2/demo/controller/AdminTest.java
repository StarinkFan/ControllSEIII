package com.software2.demo.controller;

import com.software2.demo.bean.Admin;
import com.software2.demo.dao.AdminDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminTest {
    @Autowired
    AdminDAO adminDAO;

    @GetMapping("/getadmin")
    public Admin getAdmin(){
        Admin admin = new Admin();
        admin.setName("test");
        admin.setInfomation("testinfo");
        admin.setPassword("testpw");
        adminDAO.save(admin);
        return admin;
    }
    @PostMapping("/admintest")
    public String addAdmin(){
        Admin admin = new Admin();
        admin.setName("test");
        admin.setInfomation("testinfo");
        admin.setPassword("testpw");
        adminDAO.save(admin);
        return "SUCCESS";
    }
}
