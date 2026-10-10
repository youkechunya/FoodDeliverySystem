package com.javaee.fooddeliverysystem.service;

import com.javaee.fooddeliverysystem.entity.ItemType;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ItemTypeService {
    List<ItemType> selectAll();
    ItemType selectById(@Param("id") Integer id);
    boolean insert(ItemType itemType);
    boolean update(ItemType itemType);
    boolean deleteById(@Param("id") Integer id);
}