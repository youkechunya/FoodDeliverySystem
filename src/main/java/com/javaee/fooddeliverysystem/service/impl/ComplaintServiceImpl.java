package com.javaee.fooddeliverysystem.service.impl;

import com.javaee.fooddeliverysystem.entity.Complaint;
import com.javaee.fooddeliverysystem.mapper.ComplaintMapper;
import com.javaee.fooddeliverysystem.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComplaintServiceImpl implements ComplaintService {

    @Autowired
    private ComplaintMapper complaintMapper;

    @Override
    public List<Complaint> selectAll() { return complaintMapper.selectAll(); }

    @Override
    public List<Complaint> selectByStoreId(Integer storeId) { return complaintMapper.selectByStoreId(storeId); }

    @Override
    public List<Complaint> selectByUserId(Integer userId) { return complaintMapper.selectByUserId(userId); }

    @Override
    public Complaint selectById(Integer id) { return complaintMapper.selectById(id); }

    @Override
    public boolean insert(Complaint complaint) { return complaintMapper.insert(complaint) > 0; }

    @Override
    public boolean update(Complaint complaint) { return complaintMapper.update(complaint) > 0; }

    @Override
    public boolean deleteById(Integer id) { return complaintMapper.deleteById(id) > 0; }
}