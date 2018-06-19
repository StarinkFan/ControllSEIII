package com.software2.demo.service;

import com.software2.demo.bean.Query;

import java.util.List;

public interface QueryBLService {
    public List<Query> getAll();
    public Query add(Query q);
}
