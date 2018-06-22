package com.software2.demo.service.Impl;

import com.software2.demo.ResultMessage;
import com.software2.demo.bean.Complaint;
import com.software2.demo.dao.ComplaintDataService;
import com.software2.demo.service.ComplaintBLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class ComplaintBLImpl implements ComplaintBLService{
    @Autowired
    ComplaintDataService complaintDataService;
    @Override
    public List<Complaint> getAll() {
        return complaintDataService.findAll();
    }

    @Override
    public Complaint addComplaint(Complaint c) {

        return complaintDataService.save(c);
    }

    @Override
    public Complaint getSingle(int id) {
        return complaintDataService.findById(id).get();
    }

    @Override
    public Complaint modify(Complaint c) {
        return complaintDataService.save(c);
    }
}
