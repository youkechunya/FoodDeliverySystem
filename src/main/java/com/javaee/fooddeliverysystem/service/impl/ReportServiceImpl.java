package com.javaee.fooddeliverysystem.service.impl;

import com.javaee.fooddeliverysystem.entity.Report;
import com.javaee.fooddeliverysystem.mapper.ReportMapper;
import com.javaee.fooddeliverysystem.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportMapper reportMapper;

    @Override
    public List<Report> selectPending() {
        return reportMapper.selectPending();
    }

    @Override
    public List<Report> selectByStoreId(Integer storeId) {
        return reportMapper.selectByStoreId(storeId);
    }

    @Override
    public Report selectById(Integer id) {
        return reportMapper.selectById(id);
    }

    @Override
    public boolean insert(Report report) {
        return reportMapper.insert(report) > 0;
    }

    @Override
    public boolean updateStatus(Integer id, String status) {
        return reportMapper.updateStatus(id, status) > 0;
    }

    @Override
    public boolean existsByReviewId(Integer reviewId) {
        return reportMapper.countByReviewId(reviewId) > 0;
    }
}