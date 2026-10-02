package com.javaee.fooddeliverysystem.mapper;

import com.javaee.fooddeliverysystem.entity.Store;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StoreMapper {
    /// 选中所有商店
    List<Store> selectAll();
    /// 通过 ID 选择商店
    Store selectById(@Param("id") Integer id);
    /// 新增商店
    int insert(Store store);
    /// 更新商店
    int update(Store store);
    /// 删除商店
    int deleteById(@Param("id") Integer id);
}
