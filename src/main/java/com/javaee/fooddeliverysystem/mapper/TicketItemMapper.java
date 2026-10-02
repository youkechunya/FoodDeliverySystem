package com.javaee.fooddeliverysystem.mapper;

import com.javaee.fooddeliverysystem.entity.TicketItem;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TicketItemMapper {
    List<TicketItem> selectByTicketId(@Param("ticketId") Integer ticketId);
    int insert(TicketItem ticketItem);
    int deleteById(@Param("id") Integer id);
}