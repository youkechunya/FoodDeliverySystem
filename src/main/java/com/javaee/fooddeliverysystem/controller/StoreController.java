package com.javaee.fooddeliverysystem.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.javaee.fooddeliverysystem.entity.*;
import com.javaee.fooddeliverysystem.mapper.StoreItemMapper;
import com.javaee.fooddeliverysystem.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/store")
public class StoreController {

    @Autowired
    private StoreService storeService;

    @Autowired
    private StoreItemMapper storeItemMapper;

    @Autowired
    private ItemService itemService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private ComplaintService complaintService;

    @Autowired
    private StoreOwnerService storeOwnerService;

    @Autowired
    private TicketItemService ticketItemService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ReportService reportService;

    @GetMapping("/info/page")
    public String infoPage() {
        return "store/store_index";
    }

    @GetMapping("/item/page")
    public String itemPage() {
        return "store/goods_manage";
    }

    @GetMapping("/ticket/page")
    public String ticketPage() {
        return "store/store_order";
    }

    @GetMapping("/complaint/page")
    public String complaintPage() {
        return "store/complaint_handle";
    }

    @GetMapping("/info/{id}")
    @ResponseBody
    public Store getStore(@PathVariable Integer id) {
        return storeService.selectById(id);
    }

    @GetMapping("/my/stores")
    @ResponseBody
    public List<Store> myStores(HttpSession session) {
        Account user = (Account) session.getAttribute("loginUser");
        if (user == null) {
            return new ArrayList<>();
        }

        List<Integer> ids = storeOwnerService.selectStoreIdsByAccountId(user.getId());
        List<Store> list = new ArrayList<>();
        for (Integer id : ids) {
            Store s = storeService.selectById(id);
            if (s != null) {
                list.add(s);
            }
        }
        return list;
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

    @GetMapping("/item/{id}")
    @ResponseBody
    public Item getItem(@PathVariable Integer id, HttpSession session) {
        // 1. 校验登录
        Account user = (Account) session.getAttribute("loginUser");
        if (user == null) return null;

        // 2. 反查当前商家的店铺
        List<Integer> storeIds = storeOwnerService.selectStoreIdsByAccountId(user.getId());
        if (storeIds == null || storeIds.isEmpty()) return null;
        Integer storeId = storeIds.get(0);

        // 3. 校验这个 item 是否属于该店铺
        List<Integer> itemIds = storeItemMapper.selectItemIdByStoreId(storeId);
        if (itemIds == null || !itemIds.contains(id)) return null;

        // 4. 返回
        return itemService.selectById(id);
    }

    @GetMapping("/item/list")
    @ResponseBody
    public Map<String, Object> listItems(@RequestParam(defaultValue = "1") int pageNum,
                                         @RequestParam(defaultValue = "5") int pageSize,
                                         HttpSession session) {
        // 空结果模板
        Map<String, Object> result = new HashMap<>();

        Account user = (Account) session.getAttribute("loginUser");
        if (user == null) {
            result.put("list", new ArrayList<>());
            result.put("total", 0);
            result.put("pageNum", pageNum);
            result.put("pages", 0);
            return result;
        }

        // 1. 通过 store_owner 反查当前商家的店铺 id
        List<Integer> storeIds = storeOwnerService.selectStoreIdsByAccountId(user.getId());
        if (storeIds == null || storeIds.isEmpty()) {
            result.put("list", new ArrayList<>());
            result.put("total", 0);
            result.put("pageNum", pageNum);
            result.put("pages", 0);
            return result;
        }
        Integer storeId = storeIds.get(0);

        // 2. 通过 store_item 查这个店铺拥有的 itemId
        List<Integer> itemIds = storeItemMapper.selectItemIdByStoreId(storeId);
        if (itemIds == null || itemIds.isEmpty()) {
            result.put("list", new ArrayList<>());
            result.put("total", 0);
            result.put("pageNum", pageNum);
            result.put("pages", 0);
            return result;
        }

        // 3. 只查这些 itemId
        PageHelper.startPage(pageNum, pageSize);
        List<Item> items = itemService.selectByIds(itemIds);
        PageInfo<Item> pageInfo = new PageInfo<>(items);

        result.put("list", pageInfo.getList());
        result.put("total", pageInfo.getTotal());
        result.put("pageNum", pageInfo.getPageNum());
        result.put("pages", pageInfo.getPages());
        return result;
    }

    @PostMapping("/item/add")
    @ResponseBody
    public Map<String, Object> addItem(@RequestBody Item item, HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        try {
            // 1. 反查当前商家的店铺
            Account user = (Account) session.getAttribute("loginUser");
            if (user == null) {
                result.put("success", false);
                result.put("message", "请先登录");
                return result;
            }
            List<Integer> storeIds = storeOwnerService.selectStoreIdsByAccountId(user.getId());
            if (storeIds == null || storeIds.isEmpty()) {
                result.put("success", false);
                result.put("message", "当前账号未绑定店铺");
                return result;
            }
            Integer storeId = storeIds.get(0);

            // 2. 插入 item（自增 id 会回填到 item.id）
            boolean success = itemService.insert(item);
            if (!success) {
                result.put("success", false);
                result.put("message", "添加失败");
                return result;
            }

            // 3. 维护 store_item 关联
            storeItemMapper.insert(storeId, item.getId());

            result.put("success", true);
            result.put("message", "添加成功");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "添加失败：" + e.getMessage());
        }
        return result;
    }

    @PutMapping("/item/update")
    @ResponseBody
    public Map<String, Object> updateItem(@RequestBody Item item, HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        Account user = (Account) session.getAttribute("loginUser");
        if (user == null) {
            result.put("success", false);
            result.put("message", "请先登录");
            return result;
        }
        List<Integer> storeIds = storeOwnerService.selectStoreIdsByAccountId(user.getId());
        if (storeIds == null || storeIds.isEmpty()) {
            result.put("success", false);
            result.put("message", "当前账号未绑定店铺");
            return result;
        }
        Integer storeId = storeIds.get(0);

        List<Integer> itemIds = storeItemMapper.selectItemIdByStoreId(storeId);
        if (itemIds == null || !itemIds.contains(item.getId())) {
            result.put("success", false);
            result.put("message", "无权修改该商品");
            return result;
        }

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
    public Map<String, Object> deleteItem(@PathVariable Integer id, HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        try {
            Account user = (Account) session.getAttribute("loginUser");
            if (user == null) {
                result.put("success", false);
                result.put("message", "请先登录");
                return result;
            }
            List<Integer> storeIds = storeOwnerService.selectStoreIdsByAccountId(user.getId());
            if (storeIds == null || storeIds.isEmpty()) {
                result.put("success", false);
                result.put("message", "当前账号未绑定店铺");
                return result;
            }
            Integer storeId = storeIds.get(0);

            storeItemMapper.delete(storeId, id);
            boolean success = itemService.deleteById(id);

            result.put("success", success);
            result.put("message", success ? "删除成功" : "删除失败");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "删除失败：" + e.getMessage());
        }
        return result;
    }

    @GetMapping("/ticket/list/{storeId}")
    @ResponseBody
    public List<Ticket> listTickets(@PathVariable Integer storeId) {
        return ticketService.selectByStoreId(storeId);
    }

    @PutMapping("/ticket/status/{id}")
    @ResponseBody
    @Transactional
    public Map<String, Object> updateTicketStatus(@PathVariable Integer id,
                                                  @RequestParam String status,
                                                  @RequestParam(required = false) java.math.BigDecimal rate,
                                                  HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        Ticket ticket = ticketService.selectById(id);
        if (ticket == null) {
            result.put("success", false);
            result.put("message", "订单不存在");
            return result;
        }

        Store store = storeService.selectById(ticket.getStoreId());
        if (store == null || !"通过".equals(store.getStatus())) {
            result.put("success", false);
            result.put("message", "该店铺未通过审核，无法接单");
            return result;
        }

        String oldStatus = ticket.getStatus();

        if (!"已接单".equals(status)) {
            result.put("success", false);
            result.put("message", "非法的状态变更");
            return result;
        }

        if (!"待接单".equals(oldStatus)) {
            result.put("success", false);
            result.put("message", "该订单不是待接单状态，无法接单");
            return result;
        }

        if (rate != null) {
            if (rate.compareTo(java.math.BigDecimal.ZERO) < 0
                    || rate.compareTo(java.math.BigDecimal.ONE) > 0) {
                result.put("success", false);
                result.put("message", "倍率必须在 0 ~ 1 之间");
                return result;
            }
            ticket.setDeliveryRate(rate);
        }

        List<TicketItem> items = ticketItemService.selectByTicketId(id);
        for (TicketItem ti : items) {
            Item item = itemService.selectById(ti.getItemId());
            if (item == null) continue;
            int remain = item.getRemain() == null ? 0 : item.getRemain();
            int sales = item.getSales() == null ? 0 : item.getSales();
            item.setRemain(Math.max(remain - ti.getQuantity(), 0));
            item.setSales(sales + ti.getQuantity());
            itemService.update(item);
        }

        ticket.setStatus("已接单");

        boolean success = ticketService.update(ticket);
        result.put("success", success);
        result.put("message", success ? "更新成功" : "更新失败");
        return result;
    }

    @GetMapping("/complaint/list/{storeId}")
    @ResponseBody
    public List<Complaint> listComplaints(@PathVariable Integer storeId) {
        return complaintService.selectByStoreId(storeId);
    }

    @PutMapping("/complaint/reply/{id}")
    @ResponseBody
    public Map<String, Object> replyComplaint(@PathVariable Integer id,
                                              @RequestParam String reply) {
        Map<String, Object> result = new HashMap<>();
        Complaint complaint = complaintService.selectById(id);
        if (complaint == null) {
            result.put("success", false);
            result.put("message", "投诉不存在");
            return result;
        }
        complaint.setReply(reply);
        complaint.setStatus("已处理");
        boolean success = complaintService.update(complaint);
        result.put("success", success);
        result.put("message", success ? "处理成功" : "处理失败");
        return result;
    }

    @GetMapping("/ticket/items/{ticketId}")
    @ResponseBody
    public List<TicketItem> ticketItems(@PathVariable Integer ticketId) {
        return ticketItemService.selectByTicketId(ticketId);
    }

    @GetMapping("/review/page")
    public String reviewPage() {
        return "store/store_review";
    }

    @GetMapping("/review/list")
    @ResponseBody
    public List<Review> listMyReviews(HttpSession session) {
        Account user = (Account) session.getAttribute("loginUser");
        if (user == null) {
            return new ArrayList<>();
        }

        List<Integer> storeIds = storeOwnerService.selectStoreIdsByAccountId(user.getId());
        if (storeIds.isEmpty()) {
            return new ArrayList<>();
        }

        List<Review> all = new ArrayList<>();
        for (Integer sid : storeIds) {
            all.addAll(reviewService.selectByStoreId(sid));
        }
        return all;
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

        // 校验这条评论是不是本店铺的
        List<Integer> storeIds = storeOwnerService.selectStoreIdsByAccountId(user.getId());
        if (storeIds == null || !storeIds.contains(review.getStoreId())) {
            result.put("success", false);
            result.put("message", "无权举报该评论");
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
}