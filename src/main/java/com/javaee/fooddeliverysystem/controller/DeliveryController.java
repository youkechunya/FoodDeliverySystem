package com.javaee.fooddeliverysystem.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.javaee.fooddeliverysystem.entity.Ticket;
import com.javaee.fooddeliverysystem.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/delivery")
public class DeliveryController {

    @Autowired
    private TicketService ticketService;

    @GetMapping("/ticket/page")
    public String ticketPage() {
        return "delivery/delivery_task";
    }

    @GetMapping("/ticket/mine/page")
    public String minePage() {
        return "delivery/delivery_detail";
    }

    @GetMapping("/ticket/available")
    @ResponseBody
    public Map<String, Object> listAvailableTickets(@RequestParam(defaultValue = "1") int pageNum,
                                                    @RequestParam(defaultValue = "5") int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Ticket> tickets = ticketService.selectAll();
        PageInfo<Ticket> pageInfo = new PageInfo<>(tickets);

        Map<String, Object> result = new HashMap<>();
        result.put("list", pageInfo.getList());
        result.put("total", pageInfo.getTotal());
        result.put("pageNum", pageInfo.getPageNum());
        result.put("pages", pageInfo.getPages());
        return result;
    }

    @GetMapping("/ticket/mine/{deliveryGuyId}")
    @ResponseBody
    public List<Ticket> listMyTickets(@PathVariable Integer deliveryGuyId) {
        return ticketService.selectByDeliveryGuyId(deliveryGuyId);
    }

    @PutMapping("/ticket/accept/{id}")
    @ResponseBody
    public Map<String, Object> acceptTicket(@PathVariable Integer id,
                                            @RequestParam Integer deliveryGuyId) {
        Map<String, Object> result = new HashMap<>();
        Ticket ticket = ticketService.selectById(id);
        if (ticket == null) {
            result.put("success", false);
            result.put("message", "订单不存在");
            return result;
        }
        ticket.setDeliveryGuyId(deliveryGuyId);
        ticket.setStatus("已接单");
        boolean success = ticketService.update(ticket);
        result.put("success", success);
        result.put("message", success ? "接单成功" : "接单失败");
        return result;
    }

    @PutMapping("/ticket/status/{id}")
    @ResponseBody
    public Map<String, Object> updateStatus(@PathVariable Integer id,
                                            @RequestParam String status) {
        Map<String, Object> result = new HashMap<>();
        Ticket ticket = ticketService.selectById(id);
        if (ticket == null) {
            result.put("success", false);
            result.put("message", "订单不存在");
            return result;
        }
        ticket.setStatus(status);
        boolean success = ticketService.update(ticket);
        result.put("success", success);
        result.put("message", success ? "更新成功" : "更新失败");
        return result;
    }
}