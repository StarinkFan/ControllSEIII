package com.software2.demo.dao;

import com.software2.demo.bean.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
@Transactional
public interface ComplaintDataService extends JpaRepository<Complaint,Integer>{
}
