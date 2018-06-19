package com.software2.demo.dao;

import com.software2.demo.bean.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static javafx.scene.input.KeyCode.T;

@Transactional
public interface AdminDAO extends JpaRepository<Admin,Integer>{
}
