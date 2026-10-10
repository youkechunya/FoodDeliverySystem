package com.javaee.fooddeliverysystem.service.impl;

import com.javaee.fooddeliverysystem.entity.ItemType;
import com.javaee.fooddeliverysystem.mapper.ItemTypeMapper;
import com.javaee.fooddeliverysystem.service.ItemTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemTypeServiceImpl implements ItemTypeService {

    @Autowired
    private ItemTypeMapper itemTypeMapper;

    @Override
    public List<ItemType> selectAll() {
        return itemTypeMapper.selectAll();
    }

    @Override
    public ItemType selectById(Integer id) {
        return itemTypeMapper.selectById(id);
    }

    @Override
    public boolean insert(ItemType itemType) {
        return itemTypeMapper.insert(itemType) > 0;
    }

    @Override
    public boolean update(ItemType itemType) {
        return itemTypeMapper.update(itemType) > 0;
    }

    @Override
    public boolean deleteById(Integer id) {
        return itemTypeMapper.deleteById(id) > 0;
    }
}