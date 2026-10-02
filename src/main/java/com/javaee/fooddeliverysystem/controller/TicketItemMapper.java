package com.javaee.fooddeliverysystem.controller;

import com.javaee.fooddeliverysystem.entity.TicketItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TicketItemMapper {
    List<TicketItem> selectByTicketId(@Param("ticketId") Integer ticketId);
    int insert(TicketItem ticketItem);
    int deleteById(@Param("id") Integer id);
}