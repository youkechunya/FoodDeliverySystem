package com.javaee.fooddeliverysystem;

import com.javaee.fooddeliverysystem.entity.*;
import com.javaee.fooddeliverysystem.service.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 后端 CRUD 集成测试类
 * 每个模块按"增 → 查 → 改 → 查 → 删 → 查"的顺序执行
 * 不依赖前端，直接调用 Service 验证 Mapper 可行性
 *
 * @author ASUS
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FoodDeliverySystemApplicationTests {

    @Autowired private AccountService accountService;
    @Autowired private RoleService roleService;
    @Autowired private StoreService storeService;
    @Autowired private ItemService itemService;
    @Autowired private ItemTypeService itemTypeService;
    @Autowired private ReviewService reviewService;
    @Autowired private TicketService ticketService;
    @Autowired private TicketItemService ticketItemService;

    // ============================================================
    // 工具方法：打印分隔线
    // ============================================================
    private void line(String title) {
        System.out.println("\n==================== " + title + " ====================");
    }

    private void step(String step, String msg) {
        System.out.println("  【" + step + "】" + msg);
    }

    // ============================================================
    // 1. AccountService —— 账号 CRUD
    // ============================================================

    @Test
    @Order(1)
    @DisplayName("Account 完整 CRUD")
    void testAccountCRUD() {
        line("Account 完整 CRUD");

        // 1) 增
        Account account = new Account();
        account.setAccount("test_crud_user");
        account.setPassword("pwd123");
        account.setStatus("正常");
        boolean inserted = accountService.insert(account);
        step("增", "insert 返回 = " + inserted + "，自增 id = " + account.getId());
        Assertions.assertTrue(inserted);
        Assertions.assertTrue(account.getId() > 0);

        // 2) 查（按 id）
        Account found = accountService.selectById(account.getId());
        step("查", "selectById(" + account.getId() + ") = " + found.getAccount()
                + " / " + found.getStatus());
        Assertions.assertNotNull(found);
        Assertions.assertEquals("test_crud_user", found.getAccount());
        Assertions.assertEquals("正常", found.getStatus());

        // 3) 改
        found.setStatus("冻结");
        boolean updated = accountService.update(found);
        step("改", "update 返回 = " + updated + "，状态改为 = " + found.getStatus());
        Assertions.assertTrue(updated);

        // 4) 再查（验证改动）
        Account afterUpdate = accountService.selectById(account.getId());
        step("查", "改后 selectById = " + afterUpdate.getStatus());
        Assertions.assertEquals("冻结", afterUpdate.getStatus());

        // 5) 删
        boolean deleted = accountService.deleteById(account.getId());
        step("删", "deleteById 返回 = " + deleted);
        Assertions.assertTrue(deleted);

        // 6) 再查（验证删除）
        Account afterDelete = accountService.selectById(account.getId());
        step("查", "删后 selectById = " + afterDelete);
        Assertions.assertNull(afterDelete);

        System.out.println("  ✅ Account CRUD 全部通过");
    }

    // ============================================================
    // 2. RoleService —— 角色 CRUD
    // ============================================================

    @Test
    @Order(2)
    @DisplayName("Role 完整 CRUD")
    void testRoleCRUD() {
        line("Role 完整 CRUD");

        // 1) 增
        Role role = new Role();
        role.setRoleName("测试角色");
        boolean inserted = roleService.insert(role);
        step("增", "insert 返回 = " + inserted + "，自增 id = " + role.getId());
        Assertions.assertTrue(inserted);
        Assertions.assertTrue(role.getId() > 0);

        // 2) 查
        Role found = roleService.selectById(role.getId());
        step("查", "selectById(" + role.getId() + ") = " + found.getRoleName());
        Assertions.assertNotNull(found);
        Assertions.assertEquals("测试角色", found.getRoleName());

        // 3) 改
        found.setRoleName("测试角色-改");
        boolean updated = roleService.update(found);
        step("改", "update 返回 = " + updated);
        Assertions.assertTrue(updated);

        // 4) 再查
        Role afterUpdate = roleService.selectById(role.getId());
        step("查", "改后 selectById = " + afterUpdate.getRoleName());
        Assertions.assertEquals("测试角色-改", afterUpdate.getRoleName());

        // 5) 删
        boolean deleted = roleService.deleteById(role.getId());
        step("删", "deleteById 返回 = " + deleted);
        Assertions.assertTrue(deleted);

        // 6) 再查
        Role afterDelete = roleService.selectById(role.getId());
        step("查", "删后 selectById = " + afterDelete);
        Assertions.assertNull(afterDelete);

        System.out.println("  ✅ Role CRUD 全部通过");
    }

    // ============================================================
    // 3. StoreService —— 店铺 CRUD
    // ============================================================

    @Test
    @Order(3)
    @DisplayName("Store 完整 CRUD")
    void testStoreCRUD() {
        line("Store 完整 CRUD");

        // 1) 增
        Store store = new Store();
        store.setName("测试店铺");
        store.setAddress("测试地址");
        store.setScore(new BigDecimal("4.5"));
        boolean inserted = storeService.insert(store);
        step("增", "insert 返回 = " + inserted + "，自增 id = " + store.getId());
        Assertions.assertTrue(inserted);
        Assertions.assertTrue(store.getId() > 0);

        // 2) 查
        Store found = storeService.selectById(store.getId());
        step("查", "selectById = " + found.getName()
                + " / " + found.getAddress() + " / 评分 " + found.getScore());
        Assertions.assertNotNull(found);
        Assertions.assertEquals("测试店铺", found.getName());

        // 3) 改
        found.setName("测试店铺-改");
        found.setScore(new BigDecimal("4.9"));
        boolean updated = storeService.update(found);
        step("改", "update 返回 = " + updated);
        Assertions.assertTrue(updated);

        // 4) 再查
        Store afterUpdate = storeService.selectById(store.getId());
        step("查", "改后 = " + afterUpdate.getName() + " / 评分 " + afterUpdate.getScore());
        Assertions.assertEquals("测试店铺-改", afterUpdate.getName());
        Assertions.assertEquals(new BigDecimal("4.9"), afterUpdate.getScore());

        // 5) 删
        boolean deleted = storeService.deleteById(store.getId());
        step("删", "deleteById 返回 = " + deleted);
        Assertions.assertTrue(deleted);

        // 6) 再查
        Store afterDelete = storeService.selectById(store.getId());
        step("查", "删后 selectById = " + afterDelete);
        Assertions.assertNull(afterDelete);

        System.out.println("  ✅ Store CRUD 全部通过");
    }

    // ============================================================
    // 4. ItemTypeService —— 商品类型 CRUD
    // ============================================================

    @Test
    @Order(4)
    @DisplayName("ItemType 完整 CRUD")
    void testItemTypeCRUD() {
        line("ItemType 完整 CRUD");

        // 1) 增
        ItemType type = new ItemType();
        type.setType("测试类型");
        boolean inserted = itemTypeService.insert(type);
        step("增", "insert 返回 = " + inserted + "，自增 id = " + type.getId());
        Assertions.assertTrue(inserted);
        Assertions.assertTrue(type.getId() > 0);

        // 2) 查
        ItemType found = itemTypeService.selectById(type.getId());
        step("查", "selectById = " + found.getType());
        Assertions.assertNotNull(found);
        Assertions.assertEquals("测试类型", found.getType());

        // 3) 改
        found.setType("测试类型-改");
        boolean updated = itemTypeService.update(found);
        step("改", "update 返回 = " + updated);
        Assertions.assertTrue(updated);

        // 4) 再查
        ItemType afterUpdate = itemTypeService.selectById(type.getId());
        step("查", "改后 = " + afterUpdate.getType());
        Assertions.assertEquals("测试类型-改", afterUpdate.getType());

        // 5) 删
        boolean deleted = itemTypeService.deleteById(type.getId());
        step("删", "deleteById 返回 = " + deleted);
        Assertions.assertTrue(deleted);

        // 6) 再查
        ItemType afterDelete = itemTypeService.selectById(type.getId());
        step("查", "删后 = " + afterDelete);
        Assertions.assertNull(afterDelete);

        System.out.println("  ✅ ItemType CRUD 全部通过");
    }

    // ============================================================
    // 5. ItemService —— 商品 CRUD
    // ============================================================

    @Test
    @Order(5)
    @DisplayName("Item 完整 CRUD")
    void testItemCRUD() {
        line("Item 完整 CRUD");

        // 1) 增
        Item item = new Item();
        item.setName("测试商品");
        item.setTypeId(1);
        item.setPrice(new BigDecimal("9.99"));
        item.setSales(0);
        item.setRemain(100);
        item.setNote("测试备注");
        boolean inserted = itemService.insert(item);
        step("增", "insert 返回 = " + inserted + "，自增 id = " + item.getId());
        Assertions.assertTrue(inserted);
        Assertions.assertTrue(item.getId() > 0);

        // 2) 查
        Item found = itemService.selectById(item.getId());
        step("查", "selectById = " + found.getName()
                + " / 价格 " + found.getPrice() + " / 库存 " + found.getRemain());
        Assertions.assertNotNull(found);
        Assertions.assertEquals("测试商品", found.getName());
        Assertions.assertEquals(new BigDecimal("9.99"), found.getPrice());

        // 3) 改
        found.setPrice(new BigDecimal("19.99"));
        found.setRemain(50);
        boolean updated = itemService.update(found);
        step("改", "update 返回 = " + updated
                + "，价格改为 19.99，库存改为 50");
        Assertions.assertTrue(updated);

        // 4) 再查
        Item afterUpdate = itemService.selectById(item.getId());
        step("查", "改后 = 价格 " + afterUpdate.getPrice()
                + " / 库存 " + afterUpdate.getRemain());
        Assertions.assertEquals(new BigDecimal("19.99"), afterUpdate.getPrice());
        Assertions.assertEquals(50, afterUpdate.getRemain());

        // 5) 删
        boolean deleted = itemService.deleteById(item.getId());
        step("删", "deleteById 返回 = " + deleted);
        Assertions.assertTrue(deleted);

        // 6) 再查
        Item afterDelete = itemService.selectById(item.getId());
        step("查", "删后 = " + afterDelete);
        Assertions.assertNull(afterDelete);

        System.out.println("  ✅ Item CRUD 全部通过");
    }

    // ============================================================
    // 6. ReviewService —— 评论 CRUD
    // ============================================================

    @Test
    @Order(6)
    @DisplayName("Review 完整 CRUD")
    void testReviewCRUD() {
        line("Review 完整 CRUD");

        // 1) 增
        Review review = new Review();
        review.setUserId(1);
        review.setStoreId(1);
        review.setContent("测试评论内容");
        boolean inserted = reviewService.insert(review);
        step("增", "insert 返回 = " + inserted + "，自增 id = " + review.getId());
        Assertions.assertTrue(inserted);
        Assertions.assertTrue(review.getId() > 0);

        // 2) 查
        Review found = reviewService.selectById(review.getId());
        step("查", "selectById = 用户 " + found.getUserId()
                + " / 店铺 " + found.getStoreId() + " / 内容：" + found.getContent());
        Assertions.assertNotNull(found);
        Assertions.assertEquals("测试评论内容", found.getContent());

        // 3) 改
        found.setContent("测试评论内容-改");
        boolean updated = reviewService.update(found);
        step("改", "update 返回 = " + updated);
        Assertions.assertTrue(updated);

        // 4) 再查
        Review afterUpdate = reviewService.selectById(review.getId());
        step("查", "改后内容 = " + afterUpdate.getContent());
        Assertions.assertEquals("测试评论内容-改", afterUpdate.getContent());

        // 5) 删
        boolean deleted = reviewService.deleteById(review.getId());
        step("删", "deleteById 返回 = " + deleted);
        Assertions.assertTrue(deleted);

        // 6) 再查
        Review afterDelete = reviewService.selectById(review.getId());
        step("查", "删后 = " + afterDelete);
        Assertions.assertNull(afterDelete);

        System.out.println("  ✅ Review CRUD 全部通过");
    }

    // ============================================================
    // 7. TicketService —— 订单 CRUD
    // ============================================================

    @Test
    @Order(7)
    @DisplayName("Ticket 完整 CRUD")
    void testTicketCRUD() {
        line("Ticket 完整 CRUD");

        // 1) 增
        Ticket ticket = new Ticket();
        ticket.setPublishDate(new Date());
        ticket.setAddress("测试地址");
        ticket.setStoreId(1);
        ticket.setStatus("待接单");
        ticket.setDeliveryGuyId(null);
        ticket.setNote("测试订单");
        boolean inserted = ticketService.insert(ticket);
        step("增", "insert 返回 = " + inserted + "，自增 id = " + ticket.getId());
        Assertions.assertTrue(inserted);
        Assertions.assertTrue(ticket.getId() > 0);

        // 2) 查
        Ticket found = ticketService.selectById(ticket.getId());
        step("查", "selectById = 店铺 " + found.getStoreId()
                + " / 状态 " + found.getStatus()
                + " / 配送员 " + found.getDeliveryGuyId());
        Assertions.assertNotNull(found);
        Assertions.assertEquals("待接单", found.getStatus());

        // 3) 改
        found.setStatus("配送中");
        found.setDeliveryGuyId(3);
        boolean updated = ticketService.update(found);
        step("改", "update 返回 = " + updated
                + "，状态改为 配送中，配送员改为 3");
        Assertions.assertTrue(updated);

        // 4) 再查
        Ticket afterUpdate = ticketService.selectById(ticket.getId());
        step("查", "改后 = 状态 " + afterUpdate.getStatus()
                + " / 配送员 " + afterUpdate.getDeliveryGuyId());
        Assertions.assertEquals("配送中", afterUpdate.getStatus());
        Assertions.assertEquals(3, afterUpdate.getDeliveryGuyId());

        // 5) 删
        boolean deleted = ticketService.deleteById(ticket.getId());
        step("删", "deleteById 返回 = " + deleted);
        Assertions.assertTrue(deleted);

        // 6) 再查
        Ticket afterDelete = ticketService.selectById(ticket.getId());
        step("查", "删后 = " + afterDelete);
        Assertions.assertNull(afterDelete);

        System.out.println("  ✅ Ticket CRUD 全部通过");
    }

    // ============================================================
    // 8. TicketItemService —— 订单项 CRUD
    // ============================================================

    @Test
    @Order(8)
    @DisplayName("TicketItem 完整 CRUD")
    void testTicketItemCRUD() {
        line("TicketItem 完整 CRUD");

        // 1) 增
        TicketItem ticketItem = new TicketItem();
        ticketItem.setTicketId(1);
        ticketItem.setItemId(2);
        ticketItem.setQuantity(3);
        ticketItem.setPrice(new BigDecimal("25.00"));
        boolean inserted = ticketItemService.insert(ticketItem);
        step("增", "insert 返回 = " + inserted + "，自增 id = " + ticketItem.getId());
        Assertions.assertTrue(inserted);
        Assertions.assertTrue(ticketItem.getId() > 0);

        // 2) 查（按订单 id）
        List<TicketItem> items = ticketItemService.selectByTicketId(1);
        step("查", "selectByTicketId(1) 返回 " + items.size() + " 条");
        items.forEach(ti -> System.out.println("        - 商品:" + ti.getItemId()
                + " 数量:" + ti.getQuantity() + " 单价:" + ti.getPrice()));
        Assertions.assertNotNull(items);

        // 3) 删
        boolean deleted = ticketItemService.deleteById(ticketItem.getId());
        step("删", "deleteById 返回 = " + deleted);
        Assertions.assertTrue(deleted);

        // 4) 再查（验证条数减少）
        List<TicketItem> afterDelete = ticketItemService.selectByTicketId(1);
        step("查", "删后 selectByTicketId(1) 返回 " + afterDelete.size() + " 条");
        Assertions.assertEquals(items.size() - 1, afterDelete.size());

        System.out.println("  ✅ TicketItem CRUD 全部通过");
    }

    // ============================================================
    // 9. 综合业务场景：完整下单流程
    // ============================================================

    @Test
    @Order(9)
    @DisplayName("综合测试：完整下单流程")
    void testFullOrderFlow() {
        line("综合测试：完整下单流程");

        // 1) 顾客选择店铺
        List<Store> stores = storeService.selectAll();
        Store targetStore = stores.get(0);
        step("1", "选择店铺：" + targetStore.getName());

        // 2) 顾客选择商品
        List<Item> items = itemService.selectAll();
        Item targetItem = items.get(0);
        step("2", "选择商品：" + targetItem.getName() + "，单价 " + targetItem.getPrice());

        // 3) 创建订单
        Ticket ticket = new Ticket();
        ticket.setPublishDate(new Date());
        ticket.setAddress("综合测试地址");
        ticket.setStoreId(targetStore.getId());
        ticket.setStatus("待接单");
        ticket.setNote("综合测试订单");
        Assertions.assertTrue(ticketService.insert(ticket));
        step("3", "订单创建成功，id = " + ticket.getId());

        // 4) 添加订单项
        TicketItem ticketItem = new TicketItem();
        ticketItem.setTicketId(ticket.getId());
        ticketItem.setItemId(targetItem.getId());
        ticketItem.setQuantity(2);
        ticketItem.setPrice(targetItem.getPrice());
        Assertions.assertTrue(ticketItemService.insert(ticketItem));
        step("4", "订单项添加成功：商品 " + targetItem.getName()
                + " × 2，单价 " + targetItem.getPrice());

        // 5) 配送员接单
        ticket.setDeliveryGuyId(3);
        ticket.setStatus("配送中");
        Assertions.assertTrue(ticketService.update(ticket));
        step("5", "配送员 3 接单，状态：配送中");

        // 6) 订单完成
        ticket.setStatus("已完成");
        Assertions.assertTrue(ticketService.update(ticket));
        step("6", "订单完成，状态：已完成");

        // 7) 顾客评价
        Review review = new Review();
        review.setUserId(1);
        review.setStoreId(targetStore.getId());
        review.setContent("综合测试好评！");
        Assertions.assertTrue(reviewService.insert(review));
        step("7", "评价成功，id = " + review.getId());

        // 清理
        reviewService.deleteById(review.getId());
        ticketItemService.deleteById(ticketItem.getId());
        ticketService.deleteById(ticket.getId());
        step("清理", "测试数据已删除（评价、订单项、订单）");

        System.out.println("  ✅ 完整下单流程通过");
    }
}