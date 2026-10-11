package com.javaee.fooddeliverysystem.service.impl;

import com.javaee.fooddeliverysystem.entity.TicketItem;
import com.javaee.fooddeliverysystem.mapper.TicketItemMapper;
import com.javaee.fooddeliverysystem.service.TicketItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketItemServiceImpl implements TicketItemService {

    @Autowired
    private TicketItemMapper ticketItemMapper;

    @Override
    public List<TicketItem> selectByTicketId(Integer ticketId) {
        return ticketItemMapper.selectByTicketId(ticketId);
    }

    @Override
    public boolean insert(TicketItem ticketItem) {
        return ticketItemMapper.insert(ticketItem) > 0;
    }

    @Override
    public boolean deleteById(Integer id) {
        return ticketItemMapper.deleteById(id) > 0;
    }
}