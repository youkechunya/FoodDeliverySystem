package com.javaee.fooddeliverysystem.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.javaee.fooddeliverysystem.entity.Complaint;
import com.javaee.fooddeliverysystem.entity.Review;
import com.javaee.fooddeliverysystem.entity.Store;
import com.javaee.fooddeliverysystem.service.ComplaintService;
import com.javaee.fooddeliverysystem.service.ReviewService;
import com.javaee.fooddeliverysystem.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/audit")
public class AuditController {

    @Autowired
    private StoreService storeService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ComplaintService complaintService;

    @GetMapping("/store/page")
    public String storePage() {
        return "audit/goods_audit";
    }

    @GetMapping("/review/page")
    public String reviewPage() {
        return "audit/complaint_arbitrate";
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
        store.setStatus("通过");
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
        store.setStatus("驳回");
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

    @GetMapping("/complaint/list")
    @ResponseBody
    public Map<String, Object> listComplaints(@RequestParam(defaultValue = "1") int pageNum,
                                              @RequestParam(defaultValue = "5") int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Complaint> list = complaintService.selectAll();
        PageInfo<Complaint> pageInfo = new PageInfo<>(list);

        Map<String, Object> result = new HashMap<>();
        result.put("list", pageInfo.getList());
        result.put("total", pageInfo.getTotal());
        result.put("pageNum", pageInfo.getPageNum());
        result.put("pages", pageInfo.getPages());
        return result;
    }

    @PutMapping("/complaint/arbitrate/{id}")
    @ResponseBody
    public Map<String, Object> arbitrate(@PathVariable Integer id,
                                         @RequestParam String resultText) {
        Map<String, Object> result = new HashMap<>();
        Complaint complaint = complaintService.selectById(id);
        if (complaint == null) {
            result.put("success", false);
            result.put("message", "投诉不存在");
            return result;
        }
        complaint.setReply(resultText);
        complaint.setStatus("已仲裁");
        boolean success = complaintService.update(complaint);
        result.put("success", success);
        result.put("message", success ? "仲裁完成" : "仲裁失败");
        return result;
    }
}