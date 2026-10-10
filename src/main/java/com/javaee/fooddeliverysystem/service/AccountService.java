package com.javaee.fooddeliverysystem.service;

import com.javaee.fooddeliverysystem.entity.Account;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AccountService {
    List<Account> selectAll();
    Account selectById(@Param("id") Integer id);
    boolean insert(Account account);
    boolean update(Account account);
    boolean deleteById(@Param("id") Integer id);
}
