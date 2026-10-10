package com.javaee.fooddeliverysystem.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.javaee.fooddeliverysystem.entity.Review;
import com.javaee.fooddeliverysystem.entity.Store;
import com.javaee.fooddeliverysystem.service.ReviewService;
import com.javaee.fooddeliverysystem.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/// 审核员端
@Controller
@RequestMapping("/api/audit")
public class AuditController {

    @Autowired
    private StoreService storeService;

    @Autowired
    private ReviewService reviewService;

    @GetMapping("/store/page")
    public String storePage() {
        return "audit/store_audit";
    }

    @GetMapping("/review/page")
    public String reviewPage() {
        return "audit/review_audit";
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

    @PutMapping("/store/approve/{id}")
    @ResponseBody
    public Map<String, Object> approveStore(@PathVariable Integer id) {
        Map<String, Object> result = new HashMap<>();
        Store store = storeService.selectById(id);
        if (store == null) {
            result.put("success", false);
            result.put("message", "店铺不存在");
            return result;
        }
        boolean success = storeService.update(store);
        result.put("success", success);
        result.put("message", success ? "审核通过" : "审核失败");
        return result;
    }

    @PutMapping("/store/reject/{id}")
    @ResponseBody
    public Map<String, Object> rejectStore(@PathVariable Integer id) {
        Map<String, Object> result = new HashMap<>();
        Store store = storeService.selectById(id);
        if (store == null) {
            result.put("success", false);
            result.put("message", "店铺不存在");
            return result;
        }
        boolean success = storeService.update(store);
        result.put("success", success);
        result.put("message", success ? "已驳回" : "操作失败");
        return result;
    }

    @GetMapping("/review/list")
    @ResponseBody
    public Map<String, Object> listReviews(@RequestParam(defaultValue = "1") int pageNum,
                                           @RequestParam(defaultValue = "5") int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Review> reviews = reviewService.selectAll();
        PageInfo<Review> pageInfo = new PageInfo<>(reviews);

        Map<String, Object> result = new HashMap<>();
        result.put("list", pageInfo.getList());
        result.put("total", pageInfo.getTotal());
        result.put("pageNum", pageInfo.getPageNum());
        result.put("pages", pageInfo.getPages());
        return result;
    }

    @DeleteMapping("/review/delete/{id}")
    @ResponseBody
    public Map<String, Object> deleteReview(@PathVariable Integer id) {
        Map<String, Object> result = new HashMap<>();
        boolean success = reviewService.deleteById(id);
        result.put("success", success);
        result.put("message", success ? "删除成功" : "删除失败");
        return result;
    }
}