package com.javaee.fooddeliverysystem.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.javaee.fooddeliverysystem.entity.Complaint;
import com.javaee.fooddeliverysystem.entity.Report;
import com.javaee.fooddeliverysystem.entity.Review;
import com.javaee.fooddeliverysystem.entity.Store;
import com.javaee.fooddeliverysystem.service.ComplaintService;
import com.javaee.fooddeliverysystem.service.ReportService;
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

    @Autowired
    private ReportService reportService;

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
    public List<Report> listReports() {
        return reportService.selectPending();
    }

    @DeleteMapping("/review/delete/{reportId}")
    @ResponseBody
    public Map<String, Object> deleteReviewByReport(@PathVariable Integer reportId) {
        Map<String, Object> result = new HashMap<>();
        Report report = reportService.selectById(reportId);
        if (report == null) {
            result.put("success", false);
            result.put("message", "举报记录不存在");
            return result;
        }

        reviewService.deleteById(report.getReviewId());
        reportService.updateStatus(reportId, "已删除");

        result.put("success", true);
        result.put("message", "评论已删除");
        return result;
    }

    @PutMapping("/review/keep/{reportId}")
    @ResponseBody
    public Map<String, Object> keepReview(@PathVariable Integer reportId) {
        Map<String, Object> result = new HashMap<>();
        boolean success = reportService.updateStatus(reportId, "已保留");
        result.put("success", success);
        result.put("message", success ? "已保留该评论" : "操作失败");
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
                                         @RequestParam(required = false, defaultValue = "待处理") String status,
                                         @RequestParam(required = false, defaultValue = "") String resultText) {
        Map<String, Object> result = new HashMap<>();
        Complaint complaint = complaintService.selectById(id);
        if (complaint == null) {
            result.put("success", false);
            result.put("message", "投诉不存在");
            return result;
        }
        //三种状态
        if (!"待处理".equals(status) && !"已通过".equals(status) && !"未通过".equals(status)) {
            result.put("success", false);
            result.put("message", "非法的仲裁状态");
            return result;
        }

        complaint.setStatus(status);
        complaint.setReply(resultText);
        boolean success = complaintService.update(complaint);
        result.put("success", success);
        result.put("message", success ? "仲裁完成" : "仲裁失败");
        return result;
    }
}