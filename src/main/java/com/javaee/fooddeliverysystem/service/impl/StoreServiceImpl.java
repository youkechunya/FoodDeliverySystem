package com.javaee.fooddeliverysystem.service.impl;

import com.javaee.fooddeliverysystem.entity.Store;
import com.javaee.fooddeliverysystem.mapper.StoreMapper;
import com.javaee.fooddeliverysystem.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreServiceImpl implements StoreService {

    @Autowired
    private StoreMapper storeMapper;

    @Override
    public List<Store> selectAll() {
        return storeMapper.selectAll();
    }

    @Override
    public Store selectById(Integer id) {
        return storeMapper.selectById(id);
    }

    @Override
    public List<Store> selectByStatus(String status) {
        return storeMapper.selectByStatus(status);
    }

    @Override
    public boolean insert(Store store) {
        return storeMapper.insert(store) > 0;
    }

    @Override
    public boolean update(Store store) {
        return storeMapper.update(store) > 0;
    }

    @Override
    public boolean deleteById(Integer id) {
        return storeMapper.deleteById(id) > 0;
    }
}