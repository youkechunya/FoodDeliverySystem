package com.javaee.fooddeliverysystem.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.javaee.fooddeliverysystem.entity.Item;
import com.javaee.fooddeliverysystem.entity.Store;
import com.javaee.fooddeliverysystem.entity.Ticket;
import com.javaee.fooddeliverysystem.service.ItemService;
import com.javaee.fooddeliverysystem.service.StoreService;
import com.javaee.fooddeliverysystem.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/// 商家端
@Controller
@RequestMapping("/api/store")
public class StoreController {

    @Autowired
    private StoreService storeService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private TicketService ticketService;

    @GetMapping("/info/page")
    public String infoPage() {
        return "store/store_info";
    }

    @GetMapping("/item/page")
    public String itemPage() {
        return "store/item_list";
    }

    @GetMapping("/ticket/page")
    public String ticketPage() {
        return "store/ticket_list";
    }

    @GetMapping("/info/{id}")
    @ResponseBody
    public Store getStore(@PathVariable Integer id) {
        return storeService.selectById(id);
    }

    @PutMapping("/info/update")
    @ResponseBody
    public Map<String, Object> updateStore(@RequestBody Store store) {
        Map<String, Object> result = new HashMap<>();
        try {
            boolean success = storeService.update(store);
            result.put("success", success);
            result.put("message", success ? "修改成功" : "修改失败");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "修改失败：" + e.getMessage());
        }
        return result;
    }

    @GetMapping("/item/list")
    @ResponseBody
    public Map<String, Object> listItems(@RequestParam(defaultValue = "1") int pageNum,
                                         @RequestParam(defaultValue = "5") int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Item> items = itemService.selectAll();
        PageInfo<Item> pageInfo = new PageInfo<>(items);

        Map<String, Object> result = new HashMap<>();
        result.put("list", pageInfo.getList());
        result.put("total", pageInfo.getTotal());
        result.put("pageNum", pageInfo.getPageNum());
        result.put("pages", pageInfo.getPages());
        return result;
    }

    @PostMapping("/item/add")
    @ResponseBody
    public Map<String, Object> addItem(@RequestBody Item item) {
        Map<String, Object> result = new HashMap<>();
        try {
            boolean success = itemService.insert(item);
            result.put("success", success);
            result.put("message", success ? "添加成功" : "添加失败");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "添加失败：" + e.getMessage());
        }
        return result;
    }

    @PutMapping("/item/update")
    @ResponseBody
    public Map<String, Object> updateItem(@RequestBody Item item) {
        Map<String, Object> result = new HashMap<>();
        try {
            boolean success = itemService.update(item);
            result.put("success", success);
            result.put("message", success ? "修改成功" : "修改失败");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "修改失败：" + e.getMessage());
        }
        return result;
    }

    @DeleteMapping("/item/delete/{id}")
    @ResponseBody
    public Map<String, Object> deleteItem(@PathVariable Integer id) {
        Map<String, Object> result = new HashMap<>();
        boolean success = itemService.deleteById(id);
        result.put("success", success);
        result.put("message", success ? "删除成功" : "删除失败");
        return result;
    }

    @GetMapping("/ticket/list/{storeId}")
    @ResponseBody
    public List<Ticket> listTickets(@PathVariable Integer storeId) {
        return ticketService.selectByStoreId(storeId);
    }

    @PutMapping("/ticket/status/{id}")
    @ResponseBody
    public Map<String, Object> updateTicketStatus(@PathVariable Integer id,
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