package com.javaee.fooddeliverysystem.service.impl;

import com.javaee.fooddeliverysystem.entity.Item;
import com.javaee.fooddeliverysystem.mapper.ItemMapper;
import com.javaee.fooddeliverysystem.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemMapper itemMapper;

    @Override
    public List<Item> selectAll() {
        return itemMapper.selectAll();
    }

    @Override
    public Item selectById(Integer id) {
        return itemMapper.selectById(id);
    }

    @Override
    public List<Item> selectByTypeId(Integer typeId) {
        return itemMapper.selectByTypeId(typeId);
    }

    @Override
    public List<Item> selectByIds(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            return java.util.Collections.emptyList();
        }
        return itemMapper.selectByIds(ids);
    }

    @Override
    public boolean insert(Item item) {
        return itemMapper.insert(item) > 0;
    }

    @Override
    public boolean update(Item item) {
        return itemMapper.update(item) > 0;
    }

    @Override
    public boolean deleteById(Integer id) {
        return itemMapper.deleteById(id) > 0;
    }
}