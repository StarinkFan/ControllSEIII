package com.software2.demo.dao;

import com.software2.demo.bean.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface QueryDataService extends JpaRepository<Query, Integer> {

}
