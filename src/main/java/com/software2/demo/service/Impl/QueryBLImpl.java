package com.software2.demo.service.Impl;

import com.software2.demo.bean.Query;
import com.software2.demo.dao.QueryDataService;
import com.software2.demo.service.QueryBLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class QueryBLImpl implements QueryBLService{
    @Autowired
    QueryDataService queryDataService;
    @Override
    public List<Query> getAll() {
        return queryDataService.findAll();
    }

    @Override
    public Query add(Query q) {
        return queryDataService.save(q);
    }

    @Override
    public Query getSingle(int id) {
        return queryDataService.findById(id).get();
    }

    @Override
    public Query modify(Query q) {
        return queryDataService.save(q);
    }
}
