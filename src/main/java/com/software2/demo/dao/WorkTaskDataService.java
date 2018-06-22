package com.software2.demo.dao;

import com.software2.demo.ResultMessage;
import com.software2.demo.bean.WorkTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface WorkTaskDataService extends JpaRepository<WorkTask,Integer>{
    List<WorkTask> findByWorkerID(String workID);
    List<WorkTask> findByInitTaskID(int inittaskid);
}
