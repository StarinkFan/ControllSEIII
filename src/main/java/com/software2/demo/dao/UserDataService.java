package com.software2.demo.dao;

import com.software2.demo.ResultMessage;
import com.software2.demo.bean.InitTask;
import com.software2.demo.bean.User;
import com.software2.demo.bean.WorkTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface UserDataService extends JpaRepository<User,String>{
    //void Rank(int formerrank);
}
