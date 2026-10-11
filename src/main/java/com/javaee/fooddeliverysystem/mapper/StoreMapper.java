package com.javaee.fooddeliverysystem.mapper;

import com.javaee.fooddeliverysystem.entity.Store;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StoreMapper {
    List<Store> selectAll();
    Store selectById(@Param("id") Integer id);
    List<Store> selectByStatus(@Param("status") String status);
    int insert(Store store);
    int update(Store store);
    int deleteById(@Param("id") Integer id);
}