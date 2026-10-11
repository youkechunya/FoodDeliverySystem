package com.javaee.fooddeliverysystem.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.javaee.fooddeliverysystem.entity.*;
import com.javaee.fooddeliverysystem.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private ComplaintService complaintService;

    @GetMapping("/store/page")
    public String storePage() {
        return "customer/index";
    }

    @GetMapping("/ticket/page")
    public String ticketPage() {
        return "customer/order_list";
    }

    @GetMapping("/review/page")
    public String reviewPage() {
        return "customer/review";
    }

    @GetMapping("/cart/page")
    public String cartPage() {
        return "customer/cart";
    }

    @GetMapping("/order/submit/page")
    public String orderSubmitPage() {
        return "customer/order_submit";
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

    @GetMapping("/ticket/list/{userId}")
    @ResponseBody
    public List<Ticket> listTicketsByUser(@PathVariable Integer userId) {
        return ticketService.selectByUserId(userId);
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

    @PostMapping("/complaint/add")
    @ResponseBody
    public Map<String, Object> addComplaint(@RequestBody Complaint complaint) {
        Map<String, Object> result = new HashMap<>();
        try {
            complaint.setStatus("待处理");
            complaint.setCreateTime(new Date());
            boolean success = complaintService.insert(complaint);
            result.put("success", success);
            result.put("message", success ? "投诉已提交" : "提交失败");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "提交失败：" + e.getMessage());
        }
        return result;
    }

    @GetMapping("/item/page")
    public String itemPage() {
        return "/customer/item_list";
    }
}