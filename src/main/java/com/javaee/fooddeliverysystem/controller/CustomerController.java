package com.javaee.fooddeliverysystem.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.javaee.fooddeliverysystem.entity.Item;
import com.javaee.fooddeliverysystem.entity.Review;
import com.javaee.fooddeliverysystem.entity.Store;
import com.javaee.fooddeliverysystem.entity.Ticket;
import com.javaee.fooddeliverysystem.service.ItemService;
import com.javaee.fooddeliverysystem.service.ReviewService;
import com.javaee.fooddeliverysystem.service.StoreService;
import com.javaee.fooddeliverysystem.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/// 顾客端
@Controller
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    private StoreService storeService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private ReviewService reviewService;

    @GetMapping("/store/page")
    public String storePage() {
        return "customer/store_list";
    }

    @GetMapping("/ticket/page")
    public String ticketPage() {
        return "customer/ticket_list";
    }

    @GetMapping("/review/page")
    public String reviewPage() {
        return "customer/review_list";
    }

    @GetMapping("/store/list")
    @ResponseBody
    public Map<String, Object> listStores(@RequestParam(defaultValue = "1") int pageNum,
                                          @RequestParam(defaultValue = "5") int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Store> stores = storeService.selectAll();
        PageInfo<Store> pageInfo = new PageInfo<>(stores);

        Map<String, Object> result = new HashMap<>();
        result.put("list", pageInfo.getList());
        result.put("total", pageInfo.getTotal());
        result.put("pageNum", pageInfo.getPageNum());
        result.put("pages", pageInfo.getPages());
        return result;
    }

    @GetMapping("/store/{id}")
    @ResponseBody
    public Store getStore(@PathVariable Integer id) {
        return storeService.selectById(id);
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

    @GetMapping("/item/{id}")
    @ResponseBody
    public Item getItem(@PathVariable Integer id) {
        return itemService.selectById(id);
    }

    @GetMapping("/item/type/{typeId}")
    @ResponseBody
    public List<Item> listItemsByType(@PathVariable Integer typeId) {
        return itemService.selectByTypeId(typeId);
    }

    @PostMapping("/ticket/add")
    @ResponseBody
    public Map<String, Object> addTicket(@RequestBody Ticket ticket) {
        Map<String, Object> result = new HashMap<>();
        try {
            boolean success = ticketService.insert(ticket);
            result.put("success", success);
            result.put("message", success ? "下单成功" : "下单失败");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "下单失败：" + e.getMessage());
        }
        return result;
    }

    @GetMapping("/ticket/{id}")
    @ResponseBody
    public Ticket getTicket(@PathVariable Integer id) {
        return ticketService.selectById(id);
    }

    @PostMapping("/review/add")
    @ResponseBody
    public Map<String, Object> addReview(@RequestBody Review review) {
        Map<String, Object> result = new HashMap<>();
        try {
            boolean success = reviewService.insert(review);
            result.put("success", success);
            result.put("message", success ? "评价成功" : "评价失败");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "评价失败：" + e.getMessage());
        }
        return result;
    }

    @GetMapping("/review/store/{storeId}")
    @ResponseBody
    public List<Review> listReviewsByStore(@PathVariable Integer storeId) {
        return reviewService.selectByStoreId(storeId);
    }
}