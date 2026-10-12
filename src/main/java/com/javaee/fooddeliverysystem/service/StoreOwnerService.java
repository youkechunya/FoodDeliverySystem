package com.javaee.fooddeliverysystem.service;

import java.util.List;

public interface StoreOwnerService {

    // 通过账号 ID 查询绑定的所有店铺 ID
    List<Integer> selectStoreIdsByAccountId(Integer accountId);

    // 绑定
    boolean bind(Integer accountId, Integer storeId);

    // 解绑
    boolean unbind(Integer accountId, Integer storeId);
}