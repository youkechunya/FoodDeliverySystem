package com.javaee.fooddeliverysystem.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.javaee.fooddeliverysystem.entity.Account;
import com.javaee.fooddeliverysystem.entity.Store;
import com.javaee.fooddeliverysystem.entity.Ticket;
import com.javaee.fooddeliverysystem.entity.TicketItem;
import com.javaee.fooddeliverysystem.service.StoreService;
import com.javaee.fooddeliverysystem.service.TicketItemService;
import com.javaee.fooddeliverysystem.service.TicketService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/delivery")
public class DeliveryController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private StoreService storeService;

    @Autowired
    private TicketItemService ticketItemService;

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
        List<Ticket> tickets = ticketService.selectByStatus("已接单");
        PageInfo<Ticket> pageInfo = new PageInfo<>(tickets);

        // 把每个 ticket 转成带 storeName、totalAmount、deliveryAmount 的 Map
        List<Map<String, Object>> list = new ArrayList<>();
        for (Ticket t : pageInfo.getList()) {
            Map<String, Object> row = new HashMap<>();
            row.put("id", t.getId());
            row.put("address", t.getAddress());
            row.put("status", t.getStatus());

            Store s = storeService.selectById(t.getStoreId());
            if (s == null || !"通过".equals(s.getStatus())) {
                continue;
            }
            row.put("storeName", s.getName());

            // 订单金额 = sum(price * quantity)
            java.math.BigDecimal total = java.math.BigDecimal.ZERO;
            List<TicketItem> items = ticketItemService.selectByTicketId(t.getId());
            for (TicketItem ti : items) {
                total = total.add(ti.getPrice().multiply(java.math.BigDecimal.valueOf(ti.getQuantity())));
            }
            row.put("totalAmount", total);
            row.put("deliveryRate", t.getDeliveryRate());
            row.put("deliveryAmount", total.multiply(t.getDeliveryRate()));
            list.add(row);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("list", list);
        result.put("total", pageInfo.getTotal());
        result.put("pageNum", pageInfo.getPageNum());
        result.put("pages", pageInfo.getPages());
        return result;
    }

    @GetMapping("/ticket/mine")
    @ResponseBody
    public List<Ticket> myTickets(HttpSession session) {
        Account user = (Account) session.getAttribute("loginUser");
        if (user == null) return new ArrayList<>();
        return ticketService.selectByDeliveryGuyId(user.getId());
    }

    @PutMapping("/ticket/accept/{id}")
    @ResponseBody
    public Map<String, Object> acceptTicket(@PathVariable Integer id,
                                            HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        Account user = (Account) session.getAttribute("loginUser");
        if (user == null) {
            result.put("success", false);
            result.put("message", "请先登录");
            return result;
        }
        Ticket ticket = ticketService.selectById(id);
        if (ticket == null) {
            result.put("success", false);
            result.put("message", "订单不存在");
            return result;
        }
        if (!"已接单".equals(ticket.getStatus())) {
            result.put("success", false);
            result.put("message", "该订单当前不可接");
            return result;
        }
        ticket.setDeliveryGuyId(user.getId());
        ticket.setStatus("配送中");
        boolean success = ticketService.update(ticket);
        result.put("success", success);
        result.put("message", success ? "接单成功" : "接单失败");
        return result;
    }

    @GetMapping("/ticket/mine/{deliveryGuyId}")
    @ResponseBody
    public List<Ticket> listMyTickets(@PathVariable Integer deliveryGuyId) {
        return ticketService.selectByDeliveryGuyId(deliveryGuyId);
    }

    @PutMapping("/ticket/status/{id}")
    @ResponseBody
    public Map<String, Object> updateStatus(@PathVariable Integer id,
                                            @RequestParam String status,
                                            HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        Account user = (Account) session.getAttribute("loginUser");
        if (user == null) {
            result.put("success", false);
            result.put("message", "请先登录");
            return result;
        }
        Ticket ticket = ticketService.selectById(id);
        if (ticket == null) {
            result.put("success", false);
            result.put("message", "订单不存在");
            return result;
        }
        if (ticket.getDeliveryGuyId() == null
                || !ticket.getDeliveryGuyId().equals(user.getId())) {
            result.put("success", false);
            result.put("message", "这不是你的订单");
            return result;
        }
        // 可选：状态机校验
        if ("已完成".equals(ticket.getStatus())) {
            result.put("success", false);
            result.put("message", "订单已完成，无法再修改");
            return result;
        }
        ticket.setStatus(status);
        boolean success = ticketService.update(ticket);
        result.put("success", success);
        result.put("message", success ? "更新成功" : "更新失败");
        return result;
    }

    @GetMapping("/ticket/items/{ticketId}")
    @ResponseBody
    public List<TicketItem> ticketItems(@PathVariable Integer ticketId) {
        return ticketItemService.selectByTicketId(ticketId);
    }
}