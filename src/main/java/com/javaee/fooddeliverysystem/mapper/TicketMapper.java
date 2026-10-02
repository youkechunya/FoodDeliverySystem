package com.javaee.fooddeliverysystem.mapper;

import com.javaee.fooddeliverysystem.entity.Ticket;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TicketMapper {
    List<Ticket> selectAll();
    Ticket selectById(@Param("id") Integer id);
    List<Ticket> selectByStoreId(@Param("storeId") Integer storeId);
    List<Ticket> selectByDeliveryGuyId(@Param("deliveryGuyId") Integer deliveryGuyId);
    int insert(Ticket ticket);
    int update(Ticket ticket);
    int deleteById(@Param("id") Integer id);
}
