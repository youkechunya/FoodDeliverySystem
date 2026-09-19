package com.javaee.fooddeliverysystem.mapper;

import com.javaee.fooddeliverysystem.entity.Account;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AccountMapper {
    /// 查询全部账号
    List<Account> selectAll();
    /// 通过 ID 查询账号
    Account selectById(@Param("id") Integer id);
    /// 新增账号
    int insert(Account account);
    /// 修改账号
    int update(Account account);
    /// 通过 ID 删除账号
    int deleteById(@Param("id") Integer id);
}
