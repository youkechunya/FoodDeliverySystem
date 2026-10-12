package com.javaee.fooddeliverysystem.service.impl;

import com.javaee.fooddeliverysystem.entity.StoreOwner;
import com.javaee.fooddeliverysystem.mapper.StoreOwnerMapper;
import com.javaee.fooddeliverysystem.service.StoreOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreOwnerServiceImpl implements StoreOwnerService {

    @Autowired
    private StoreOwnerMapper storeOwnerMapper;

    @Override
    public List<Integer> selectStoreIdsByAccountId(Integer accountId) {
        return storeOwnerMapper.selectStoreIdsByAccountId(accountId);
    }

    @Override
    public boolean bind(Integer accountId, Integer storeId) {
        StoreOwner so = new StoreOwner();
        so.setAccountId(accountId);
        so.setStoreId(storeId);
        return storeOwnerMapper.insert(so) > 0;
    }

    @Override
    public boolean unbind(Integer accountId, Integer storeId) {
        return storeOwnerMapper.delete(accountId, storeId) > 0;
    }
}