package com.software2.demo.service;

import com.software2.demo.bean.Complaint;

import java.util.List;

public interface ComplaintBLService {
    public List<Complaint> getAll();
    public Complaint addComplaint(Complaint c);
}
