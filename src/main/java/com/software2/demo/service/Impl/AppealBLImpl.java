package com.software2.demo.service.Impl;

import com.software2.demo.bean.Appeal;
import com.software2.demo.dao.AppealDataService;
import com.software2.demo.service.AppealBLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class AppealBLImpl implements AppealBLService{
    @Autowired
    AppealDataService appealDataService;
    @Override
    public List<Appeal> getAll() {
        return appealDataService.findAll();
    }

    @Override
    public Appeal add(Appeal a) {
        return appealDataService.save(a);
    }

    @Override
    public Appeal getSingle(int id) {
        return appealDataService.findById(id).get();
    }

    @Override
    public Appeal modify(Appeal a) {
        return appealDataService.save(a);
    }

}
