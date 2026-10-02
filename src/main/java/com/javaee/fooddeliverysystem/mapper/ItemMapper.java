package com.javaee.fooddeliverysystem.mapper;

import com.javaee.fooddeliverysystem.entity.Item;
import org.apache.ibatis.annotations.Param;

import java.util.List;

// ItemMapper.java
public interface ItemMapper {
    List<Item> selectAll();
    Item selectById(@Param("id") Integer id);
    List<Item> selectByTypeId(@Param("typeId") Integer typeId);
    int insert(Item item);
    int update(Item item);
    int deleteById(@Param("id") Integer id);
}