package com.javaee.fooddeliverysystem.service.impl;

import com.javaee.fooddeliverysystem.entity.Review;
import com.javaee.fooddeliverysystem.mapper.ReviewMapper;
import com.javaee.fooddeliverysystem.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewMapper reviewMapper;

    @Override
    public List<Review> selectAll() {
        return reviewMapper.selectAll();
    }

    @Override
    public List<Review> selectByStoreId(Integer storeId) {
        return reviewMapper.selectByStoreId(storeId);
    }

    @Override
    public List<Review> selectByUserId(Integer userId) {
        return reviewMapper.selectByUserId(userId);
    }

    @Override
    public Review selectById(Integer id) {
        return reviewMapper.selectById(id);
    }

    @Override
    public boolean insert(Review review) {
        return reviewMapper.insert(review) > 0;
    }

    @Override
    public boolean update(Review review) {
        return reviewMapper.update(review) > 0;
    }

    @Override
    public boolean deleteById(Integer id) {
        return reviewMapper.deleteById(id) > 0;
    }
}