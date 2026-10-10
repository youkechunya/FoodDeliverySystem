package com.javaee.fooddeliverysystem.service;

import com.javaee.fooddeliverysystem.entity.Review;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ReviewService {
    List<Review> selectAll();
    List<Review> selectByStoreId(@Param("storeId") Integer storeId);
    List<Review> selectByUserId(@Param("userId") Integer userId);
    Review selectById(@Param("id") Integer id);
    boolean insert(Review review);
    boolean update(Review review);
    boolean deleteById(@Param("id") Integer id);
}
