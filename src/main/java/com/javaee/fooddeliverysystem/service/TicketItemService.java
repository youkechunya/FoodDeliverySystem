package com.javaee.fooddeliverysystem.service;

import com.javaee.fooddeliverysystem.entity.TicketItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TicketItemService {
    List<TicketItem> selectByTicketId(@Param("ticketId") Integer ticketId);
    boolean insert(TicketItem ticketItem);
    boolean deleteById(@Param("id") Integer id);
}