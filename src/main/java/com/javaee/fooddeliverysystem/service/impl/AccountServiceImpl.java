package com.javaee.fooddeliverysystem.service.impl;

import com.javaee.fooddeliverysystem.entity.Account;
import com.javaee.fooddeliverysystem.mapper.AccountMapper;
import com.javaee.fooddeliverysystem.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountMapper accountMapper;

    @Override
    public List<Account> selectAll() {
        return accountMapper.selectAll();
    }

    @Override
    public Account selectById(Integer id) {
        return accountMapper.selectById(id);
    }

    @Override
    public boolean insert(Account account) {
        return accountMapper.insert(account) > 0;
    }

    @Override
    public boolean update(Account account) {
        return accountMapper.update(account) > 0;
    }

    @Override
    public boolean deleteById(Integer id) {
        return accountMapper.deleteById(id) > 0;
    }
}