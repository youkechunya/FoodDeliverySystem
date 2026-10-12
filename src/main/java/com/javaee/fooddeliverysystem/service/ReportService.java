package com.javaee.fooddeliverysystem.service;

import com.javaee.fooddeliverysystem.entity.Report;

import java.util.List;

public interface ReportService {
    List<Report> selectPending();
    List<Report> selectByStoreId(Integer storeId);
    Report selectById(Integer id);
    boolean insert(Report report);
    boolean updateStatus(Integer id, String status);
    boolean existsByReviewId(Integer reviewId);
}