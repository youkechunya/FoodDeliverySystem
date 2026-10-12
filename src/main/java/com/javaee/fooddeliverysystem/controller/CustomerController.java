package com.javaee.fooddeliverysystem.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.javaee.fooddeliverysystem.entity.*;
import com.javaee.fooddeliverysystem.entity.dto.TicketSubmitDTO;
import com.javaee.fooddeliverysystem.mapper.StoreItemMapper;
import com.javaee.fooddeliverysystem.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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

    @Autowired
    private StoreItemMapper storeItemMapper;

    @Autowired
    private ReportService reportService;

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

    @GetMapping("/review/list/page")
    public String reviewListPage() {
        return "customer/review_list";
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
        List<Store> stores = storeService.selectByStatus("通过");
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
                                         @RequestParam(defaultValue = "5") int pageSize,
                                         @RequestParam(required = false) Integer storeId) {
        Map<String, Object> result = new HashMap<>();

        // 1. 没传 storeId，直接返回空（或全部）
        if (storeId == null) {
            result.put("list", new ArrayList<>());
            result.put("total", 0);
            result.put("pageNum", pageNum);
            result.put("pages", 0);
            return result;
        }

        // 2. 查这个店铺拥有的 itemId
        List<Integer> itemIds = storeItemMapper.selectItemIdByStoreId(storeId);
        if (itemIds == null || itemIds.isEmpty()) {
            result.put("list", new ArrayList<>());
            result.put("total", 0);
            result.put("pageNum", pageNum);
            result.put("pages", 0);
            return result;
        }

        // 3. 只查这些 item
        PageHelper.startPage(pageNum, pageSize);
        List<Item> items = itemService.selectByIds(itemIds);
        PageInfo<Item> pageInfo = new PageInfo<>(items);

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

    @Autowired
    private TicketItemService ticketItemService;

    @PostMapping("/ticket/add")
    @ResponseBody
    public Map<String, Object> addTicket(@RequestBody TicketSubmitDTO dto, HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        Store store = storeService.selectById(dto.getStoreId());
        if (store == null || !"通过".equals(store.getStatus())) {
            result.put("success", false);
            result.put("message", "该店铺暂不可下单");
            return result;
        }
        Account user = (Account) session.getAttribute("loginUser");
        if (user == null) {
            result.put("success", false);
            result.put("message", "请先登录");
            return result;
        }
        if (dto.getItems() == null || dto.getItems().isEmpty()) {
            result.put("success", false);
            result.put("message", "购物车为空");
            return result;
        }

        try {
            Ticket ticket = new Ticket();
            ticket.setUserId(user.getId());
            ticket.setStoreId(dto.getStoreId());
            ticket.setAddress(dto.getAddress());
            ticket.setNote(dto.getNote());
            ticket.setStatus("待接单");
            ticket.setPublishDate(new Date());
            ticket.setDeliveryRate(new java.math.BigDecimal("0.40"));

            boolean ok = ticketService.insert(ticket);
            if (!ok) {
                result.put("success", false);
                result.put("message", "下单失败");
                return result;
            }

            for (TicketSubmitDTO.ItemEntry e : dto.getItems()) {
                TicketItem ti = new TicketItem();
                ti.setTicketId(ticket.getId());   // 自增 id 会回填到 ticket 里
                ti.setItemId(e.getItemId());
                ti.setQuantity(e.getQuantity());
                ti.setPrice(e.getPrice());
                ticketItemService.insert(ti);
            }

            result.put("success", true);
            result.put("message", "下单成功");
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

    @GetMapping("/ticket/items/{ticketId}")
    @ResponseBody
    public List<TicketItem> ticketItems(@PathVariable Integer ticketId) {
        return ticketItemService.selectByTicketId(ticketId);
    }

    @GetMapping("/ticket/mine")
    @ResponseBody
    public List<Ticket> myTickets(HttpSession session) {
        Account user = (Account) session.getAttribute("loginUser");
        if (user == null) {
            return new ArrayList<>();
        }
        return ticketService.selectByUserId(user.getId());
    }

    @PostMapping("/review/add")
    @ResponseBody
    public Map<String, Object> addReview(@RequestBody Review review, HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        Account user = (Account) session.getAttribute("loginUser");
        if (user == null) {
            result.put("success", false);
            result.put("message", "请先登录");
            return result;
        }
        try {
            review.setUserId(user.getId());
            boolean success = reviewService.insert(review);
            result.put("success", success);
            result.put("message", success ? "评价成功" : "评价失败");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "评价失败：" + e.getMessage());
        }
        return result;
    }

    @PostMapping("/review/report")
    @ResponseBody
    public Map<String, Object> reportReview(@RequestParam Integer reviewId,
                                            @RequestParam String reason,
                                            HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        Account user = (Account) session.getAttribute("loginUser");
        if (user == null) {
            result.put("success", false);
            result.put("message", "请先登录");
            return result;
        }
        if (reason == null || reason.trim().isEmpty()) {
            result.put("success", false);
            result.put("message", "举报理由不能为空");
            return result;
        }
        if (reportService.existsByReviewId(reviewId)) {
            result.put("success", false);
            result.put("message", "该评论已被举报");
            return result;
        }
        Review review = reviewService.selectById(reviewId);
        if (review == null) {
            result.put("success", false);
            result.put("message", "评论不存在");
            return result;
        }

        Report report = new Report();
        report.setReviewId(reviewId);
        report.setStoreId(review.getStoreId());
        report.setReporterId(user.getId());
        report.setReason(reason);

        boolean success = reportService.insert(report);
        result.put("success", success);
        result.put("message", success ? "举报成功，等待审核" : "举报失败");
        return result;
    }

    @GetMapping("/review/store/{storeId}")
    @ResponseBody
    public List<Review> listReviewsByStore(@PathVariable Integer storeId) {
        return reviewService.selectByStoreId(storeId);
    }

    @PostMapping("/complaint/add")
    @ResponseBody
    public Map<String, Object> addComplaint(@RequestBody Complaint complaint, HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        Account user = (Account) session.getAttribute("loginUser");
        if (user == null) {
            result.put("success", false);
            result.put("message", "请先登录");
            return result;
        }
        try {
            complaint.setUserId(user.getId());
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