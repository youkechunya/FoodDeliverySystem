package com.javaee.fooddeliverysystem.mapper;

import com.javaee.fooddeliverysystem.entity.Report;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReportMapper {
    // 审核员查所有「待处理」的举报
    List<Report> selectPending();

    // 商家查自己店铺的举报
    List<Report> selectByStoreId(@Param("storeId") Integer storeId);

    Report selectById(@Param("id") Integer id);

    int insert(Report report);

    int updateStatus(@Param("id") Integer id, @Param("status") String status);

    // 检查某条评论是否已经被举报过（避免重复举报）
    int countByReviewId(@Param("reviewId") Integer reviewId);
}