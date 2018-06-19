package com.software2.demo.dao;

import com.software2.demo.bean.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Transactional
public interface LabelDataService extends JpaRepository<Label,Integer>{
    List<Label> findByGiverID(String id);
}
