package com.javaee.fooddeliverysystem.service;

import com.javaee.fooddeliverysystem.entity.Item;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ItemService {
    List<Item> selectAll();
    Item selectById(@Param("id") Integer id);
    List<Item> selectByTypeId(@Param("typeId") Integer typeId);
    boolean insert(Item item);
    boolean update(Item item);
    boolean deleteById(@Param("id") Integer id);
}
