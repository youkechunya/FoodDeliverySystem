package com.javaee.fooddeliverysystem.mapper;

import com.javaee.fooddeliverysystem.entity.Review;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ReviewMapper {
    List<Review> selectAll();
    List<Review> selectByStoreId(@Param("storeId") Integer storeId);
    List<Review> selectByUserId(@Param("userId") Integer userId);
    Review selectById(@Param("id") Integer id);
    int insert(Review review);
    int update(Review review);
    int deleteById(@Param("id") Integer id);
}