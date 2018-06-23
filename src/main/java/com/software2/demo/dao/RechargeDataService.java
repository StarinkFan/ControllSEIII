package com.software2.demo.dao;

import com.software2.demo.bean.Recharge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Transactional
public interface RechargeDataService extends JpaRepository<Recharge,Integer>{
}
