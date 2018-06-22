package com.software2.demo.dao;

import com.software2.demo.bean.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author:Wang Mo
 * @Description：
 */
@Transactional
public interface UserDataDAO extends JpaRepository<UserData,Integer> {
}
