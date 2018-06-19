package com.software2.demo.dao;

import com.software2.demo.bean.picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
@Transactional
public interface pictureDataService extends JpaRepository<picture,Integer>{
    //public label getPlabel(String ID);//根据图片ID寻找对应的label
}
