package com.javaee.fooddeliverysystem.service.impl;

import com.javaee.fooddeliverysystem.entity.Ticket;
import com.javaee.fooddeliverysystem.mapper.TicketMapper;
import com.javaee.fooddeliverysystem.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketMapper ticketMapper;

    @Override
    public List<Ticket> selectAll() {
        return ticketMapper.selectAll();
    }

    @Override
    public Ticket selectById(Integer id) {
        return ticketMapper.selectById(id);
    }

    @Override
    public List<Ticket> selectByStoreId(Integer storeId) {
        return ticketMapper.selectByStoreId(storeId);
    }

    @Override
    public List<Ticket> selectByDeliveryGuyId(Integer deliveryGuyId) {
        return ticketMapper.selectByDeliveryGuyId(deliveryGuyId);
    }

    @Override
    public boolean insert(Ticket ticket) {
        return ticketMapper.insert(ticket) > 0;
    }

    @Override
    public boolean update(Ticket ticket) {
        return ticketMapper.update(ticket) > 0;
    }

    @Override
    public boolean deleteById(Integer id) {
        return ticketMapper.deleteById(id) > 0;
    }
}