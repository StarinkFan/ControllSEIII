package com.software2.demo.controller;

import com.software2.demo.bean.Admin;
import com.software2.demo.bean.User;
import com.software2.demo.dao.AdminDAO;
import com.software2.demo.service.UserBLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminTest {
    @Autowired
    AdminDAO adminDAO;
    @Autowired
    UserBLService userBLService;

    @GetMapping("/getadmin")
    public void getAdmin(){
            User user0 = userBLService.getSingle("13236552118");
            User user1= userBLService.getSingle("18252605889");
            User user2 = userBLService.getSingle("18851175250");
            User user3 = userBLService.getSingle("18138345849");
            userBLService.addUser(user0);
            userBLService.addUser(user1);

            userBLService.addUser(user2);
            userBLService.addUser(user3);

    }
    @GetMapping("/admintest")
    public String addAdmin(){
        User user0 = userBLService.getSingle("13236552118");
        User user1= userBLService.getSingle("18252605889");
        User user2 = userBLService.getSingle("18851175250");
        User user3 = userBLService.getSingle("18138345849");
        userBLService.addUser(user0);
        userBLService.addUser(user1);

        userBLService.addUser(user2);
        userBLService.addUser(user3);
        return "SUCCESS";
    }
}
