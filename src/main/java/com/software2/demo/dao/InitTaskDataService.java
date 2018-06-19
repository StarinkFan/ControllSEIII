package com.software2.demo.dao;

import com.software2.demo.ResultMessage;
import com.software2.demo.bean.InitTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional
public interface InitTaskDataService extends JpaRepository<InitTask,Integer>{
}
