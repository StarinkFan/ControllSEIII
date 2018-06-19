package com.software2.demo.service;

import com.software2.demo.bean.Appeal;

import java.util.List;

public interface AppealBLService {
    public List<Appeal> getAll();
    public Appeal add(Appeal a);
}
