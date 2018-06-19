package com.software2.demo.dao;

import com.software2.demo.bean.Appeal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface AppealDataService extends JpaRepository<Appeal,Integer> {
}
