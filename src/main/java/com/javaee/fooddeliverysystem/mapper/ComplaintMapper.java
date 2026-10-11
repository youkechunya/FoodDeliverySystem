package com.javaee.fooddeliverysystem.mapper;

import com.javaee.fooddeliverysystem.entity.Complaint;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ComplaintMapper {
    List<Complaint> selectAll();
    List<Complaint> selectByStoreId(@Param("storeId") Integer storeId);
    List<Complaint> selectByUserId(@Param("userId") Integer userId);
    Complaint selectById(@Param("id") Integer id);
    int insert(Complaint complaint);
    int update(Complaint complaint);
    int deleteById(@Param("id") Integer id);
}