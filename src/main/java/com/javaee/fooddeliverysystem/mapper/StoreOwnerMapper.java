package com.javaee.fooddeliverysystem.mapper;

import com.javaee.fooddeliverysystem.entity.StoreOwner;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StoreOwnerMapper {

    // 通过账号 ID 查询其绑定的所有店铺 ID
    List<Integer> selectStoreIdsByAccountId(@Param("accountId") Integer accountId);

    // 通过店铺 ID 查询绑定关系（含 accountId）
    List<StoreOwner> selectByStoreId(@Param("storeId") Integer storeId);

    // 绑定
    int insert(StoreOwner storeOwner);

    // 解绑
    int delete(@Param("accountId") Integer accountId,
               @Param("storeId") Integer storeId);
}