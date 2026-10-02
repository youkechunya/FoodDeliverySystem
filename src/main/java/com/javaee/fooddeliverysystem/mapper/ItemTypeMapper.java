package com.javaee.fooddeliverysystem.mapper;

import com.javaee.fooddeliverysystem.entity.Item;
import com.javaee.fooddeliverysystem.entity.ItemType;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ItemTypeMapper {
    List<ItemType> selectAll();
    ItemType selectById(@Param("id") Integer id);
    int insert(ItemType itemType);
    int update(ItemType itemType);
    int deleteById(@Param("id") Integer id);
}
