package com.javaee.fooddeliverysystem.service;

import com.javaee.fooddeliverysystem.entity.Complaint;

import java.util.List;

public interface ComplaintService {
    List<Complaint> selectAll();
    List<Complaint> selectByStoreId(Integer storeId);
    List<Complaint> selectByUserId(Integer userId);
    Complaint selectById(Integer id);
    boolean insert(Complaint complaint);
    boolean update(Complaint complaint);
    boolean deleteById(Integer id);
}