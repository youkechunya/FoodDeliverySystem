package com.javaee.fooddeliverysystem.service;

import com.javaee.fooddeliverysystem.entity.Store;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StoreService {
    List<Store> selectAll();
    Store selectById(@Param("id") Integer id);
    boolean insert(Store store);
    boolean update(Store store);
    boolean deleteById(@Param("id") Integer id);
}