package com.javaee.fooddeliverysystem.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.javaee.fooddeliverysystem.entity.Account;
import com.javaee.fooddeliverysystem.entity.Permission;
import com.javaee.fooddeliverysystem.entity.Role;
import com.javaee.fooddeliverysystem.entity.Store;
import com.javaee.fooddeliverysystem.entity.dto.AccountRoleDTO;
import com.javaee.fooddeliverysystem.service.AccountService;
import com.javaee.fooddeliverysystem.service.PermissionService;
import com.javaee.fooddeliverysystem.service.RoleService;
import com.javaee.fooddeliverysystem.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private StoreService storeService;

    @Autowired
    private PermissionService permissionService;

    @GetMapping("/account/page")
    public String accountPage() {
        return "admin/user_manage";
    }

    @GetMapping("/role/page")
    public String rolePage() {
        return "admin/role_manage";
    }

    @GetMapping("/permission/page")
    public String permissionPage() {
        return "admin/permission_manage";
    }

    @GetMapping("/store/page")
    public String storePage() {
        return "admin/store_list";
    }

    @GetMapping("/account/list")
    @ResponseBody
    public Map<String, Object> listAccounts(@RequestParam(defaultValue = "1") int pageNum,
                                            @RequestParam(defaultValue = "5") int pageSize,
                                            @RequestParam(required = false) String keyword) {
        PageHelper.startPage(pageNum, pageSize);
        List<Account> accounts = accountService.selectAll();
        PageInfo<Account> pageInfo = new PageInfo<>(accounts);

        Map<String, Object> result = new HashMap<>();
        result.put("list", pageInfo.getList());
        result.put("total", pageInfo.getTotal());
        result.put("pageNum", pageInfo.getPageNum());
        result.put("pages", pageInfo.getPages());
        return result;
    }

    @GetMapping("/account/{id}")
    @ResponseBody
    public Account getAccount(@PathVariable Integer id) {
        return accountService.selectById(id);
    }

    @PostMapping("/account/add")
    @ResponseBody
    public Map<String, Object> addAccount(@RequestBody Account account) {
        Map<String, Object> result = new HashMap<>();
        try {
            boolean success = accountService.insert(account);
            result.put("success", success);
            result.put("message", success ? "添加成功" : "添加失败");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "添加失败：" + e.getMessage());
        }
        return result;
    }

    @PutMapping("/account/update")
    @ResponseBody
    public Map<String, Object> updateAccount(@RequestBody Account account) {
        Map<String, Object> result = new HashMap<>();
        try {
            boolean success = accountService.update(account);
            result.put("success", success);
            result.put("message", success ? "修改成功" : "修改失败");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "修改失败：" + e.getMessage());
        }
        return result;
    }

    @DeleteMapping("/account/delete/{id}")
    @ResponseBody
    public Map<String, Object> deleteAccount(@PathVariable Integer id) {
        Map<String, Object> result = new HashMap<>();
        boolean success = accountService.deleteById(id);
        result.put("success", success);
        result.put("message", success ? "删除成功" : "删除失败");
        return result;
    }

    @GetMapping("/role/list")
    @ResponseBody
    public Map<String, Object> listRoles(@RequestParam(defaultValue = "1") int pageNum,
                                         @RequestParam(defaultValue = "5") int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Role> roles = roleService.selectAll();
        PageInfo<Role> pageInfo = new PageInfo<>(roles);

        Map<String, Object> result = new HashMap<>();
        result.put("list", pageInfo.getList());
        result.put("total", pageInfo.getTotal());
        result.put("pageNum", pageInfo.getPageNum());
        result.put("pages", pageInfo.getPages());
        return result;
    }

    @GetMapping("/role/{id}")
    @ResponseBody
    public Role getRole(@PathVariable Integer id) {
        return roleService.selectById(id);
    }

    @PostMapping("/role/add")
    @ResponseBody
    public Map<String, Object> addRole(@RequestBody Role role) {
        Map<String, Object> result = new HashMap<>();
        try {
            boolean success = roleService.insert(role);
            result.put("success", success);
            result.put("message", success ? "添加成功" : "添加失败");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "添加失败：" + e.getMessage());
        }
        return result;
    }

    @PutMapping("/role/update")
    @ResponseBody
    public Map<String, Object> updateRole(@RequestBody Role role) {
        Map<String, Object> result = new HashMap<>();
        try {
            boolean success = roleService.update(role);
            result.put("success", success);
            result.put("message", success ? "修改成功" : "修改失败");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "修改失败：" + e.getMessage());
        }
        return result;
    }

    @DeleteMapping("/role/delete/{id}")
    @ResponseBody
    public Map<String, Object> deleteRole(@PathVariable Integer id) {
        Map<String, Object> result = new HashMap<>();
        boolean success = roleService.deleteById(id);
        result.put("success", success);
        result.put("message", success ? "删除成功" : "删除失败");
        return result;
    }

    /**
     * 查询所有可选角色，用于权限管理页面的角色多选框
     */
    @GetMapping("/permission/role/list")
    @ResponseBody
    public List<Role> listAllRolesForPermission() {
        return roleService.selectAll();
    }

    /**
     * 查询某个账号当前已经拥有的所有角色
     */
    @GetMapping("/permission/roles/{userId}")
    @ResponseBody
    public List<Role> listRolesByUserId(@PathVariable Integer userId) {
        return roleService.selectRolesByUserId(userId);
    }

    /**
     * 保存账号角色配置
     * roleIds 可以包含多个角色，也可以为空表示清除该账号的全部角色
     */
    @PostMapping("/permission/saveUserRoles")
    @ResponseBody
    public Map<String, Object> saveUserRoles(@RequestBody AccountRoleDTO dto) {
        Map<String, Object> result = new HashMap<>();

        try {
            if (dto == null || dto.getUserId() == null) {
                throw new IllegalArgumentException("账号ID不能为空");
            }

            boolean success = roleService.updateUserRoles(
                    dto.getUserId(),
                    dto.getRoleIds()
            );

            result.put("success", success);
            result.put("message", success ? "角色配置成功" : "角色配置失败");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "角色配置失败：" + e.getMessage());
        }

        return result;
    }

    @GetMapping("/permission/list")
    @ResponseBody
    public Map<String, Object> listPermissions(@RequestParam(defaultValue = "1") int pageNum,
                                               @RequestParam(defaultValue = "5") int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Permission> list = permissionService.selectAll();
        PageInfo<Permission> pageInfo = new PageInfo<>(list);

        Map<String, Object> result = new HashMap<>();
        result.put("list", pageInfo.getList());
        result.put("total", pageInfo.getTotal());
        result.put("pageNum", pageInfo.getPageNum());
        result.put("pages", pageInfo.getPages());
        return result;
    }

    @GetMapping("/permission/{id}")
    @ResponseBody
    public Permission getPermission(@PathVariable Integer id) {
        return permissionService.selectById(id);
    }

    @PostMapping("/permission/add")
    @ResponseBody
    public Map<String, Object> addPermission(@RequestBody Permission permission) {
        Map<String, Object> result = new HashMap<>();
        try {
            boolean success = permissionService.insert(permission);
            result.put("success", success);
            result.put("message", success ? "添加成功" : "添加失败");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "添加失败：" + e.getMessage());
        }
        return result;
    }

    @PutMapping("/permission/update")
    @ResponseBody
    public Map<String, Object> updatePermission(@RequestBody Permission permission) {
        Map<String, Object> result = new HashMap<>();
        try {
            boolean success = permissionService.update(permission);
            result.put("success", success);
            result.put("message", success ? "修改成功" : "修改失败");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "修改失败：" + e.getMessage());
        }
        return result;
    }

    @DeleteMapping("/permission/delete/{id}")
    @ResponseBody
    public Map<String, Object> deletePermission(@PathVariable Integer id) {
        Map<String, Object> result = new HashMap<>();
        boolean success = permissionService.deleteById(id);
        result.put("success", success);
        result.put("message", success ? "删除成功" : "删除失败");
        return result;
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

    @PostMapping("/store/add")
    @ResponseBody
    public Map<String, Object> addStore(@RequestBody Store store) {
        Map<String, Object> result = new HashMap<>();
        if (store.getScore() != null) {
            if (store.getScore().compareTo(new java.math.BigDecimal("0")) < 0
                    || store.getScore().compareTo(new java.math.BigDecimal("10")) > 0) {
                result.put("success", false);
                result.put("message", "评分必须在 0 ~ 10 之间");
                return result;
            }
        }
        try {
            boolean success = storeService.insert(store);
            result.put("success", success);
            result.put("message", success ? "添加成功" : "添加失败");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "添加失败：" + e.getMessage());
        }
        return result;
    }

    @PutMapping("/store/update")
    @ResponseBody
    public Map<String, Object> updateStore(@RequestBody Store store) {
        Map<String, Object> result = new HashMap<>();
        if (store.getScore() != null) {
            if (store.getScore().compareTo(new java.math.BigDecimal("0")) < 0
                    || store.getScore().compareTo(new java.math.BigDecimal("10")) > 0) {
                result.put("success", false);
                result.put("message", "评分必须在 0 ~ 10 之间");
                return result;
            }
        }
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

    @DeleteMapping("/store/delete/{id}")
    @ResponseBody
    public Map<String, Object> deleteStore(@PathVariable Integer id) {
        Map<String, Object> result = new HashMap<>();
        boolean success = storeService.deleteById(id);
        result.put("success", success);
        result.put("message", success ? "删除成功" : "删除失败");
        return result;
    }
}