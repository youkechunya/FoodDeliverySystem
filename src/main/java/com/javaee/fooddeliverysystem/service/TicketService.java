package com.javaee.fooddeliverysystem.service;

import com.javaee.fooddeliverysystem.entity.Ticket;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TicketService {
    List<Ticket> selectAll();
    Ticket selectById(@Param("id") Integer id);
    List<Ticket> selectByStoreId(@Param("storeId") Integer storeId);
    List<Ticket> selectByDeliveryGuyId(@Param("deliveryGuyId") Integer deliveryGuyId);
    boolean insert(Ticket ticket);
    boolean update(Ticket ticket);
    boolean deleteById(@Param("id") Integer id);
}