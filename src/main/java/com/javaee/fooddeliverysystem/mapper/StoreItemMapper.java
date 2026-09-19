package com.javaee.fooddeliverysystem.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StoreItemMapper {
    /// 查询店铺拥有的商品 ID
    List<Integer> selectItemIdByStoreId(@Param("storeId") Integer storeId);
    /// 新增商品
    int insert(@Param("storeId") Integer storeId,@Param("itemId") Integer itemId);
    /// 删除商品
    int delete(@Param("storeId") Integer storeId,@Param("itemId") Integer itemId);
}