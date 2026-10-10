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
 * 后端集成测试类：不依赖前端，直接测试 Service / Mapper 的可行性
 *
 * @author ASUS
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FoodDeliverySystemApplicationTests {

    @Autowired
    private AccountService accountService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private StoreService storeService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemTypeService itemTypeService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private TicketItemService ticketItemService;

    // ============================================================
    // 1. AccountService 测试
    // ============================================================

    @Test
    @Order(1)
    @DisplayName("测试：查询全部账号")
    void testAccountSelectAll() {
        List<Account> accounts = accountService.selectAll();
        Assertions.assertNotNull(accounts, "账号列表不应为 null");
        Assertions.assertFalse(accounts.isEmpty(), "账号列表不应为空");
        System.out.println("【账号总数】" + accounts.size());
        accounts.forEach(a -> System.out.println("  " + a.getId() + " - " + a.getAccount() + " - " + a.getStatus()));
    }

    @Test
    @Order(2)
    @DisplayName("测试：按 ID 查询账号")
    void testAccountSelectById() {
        Account account = accountService.selectById(1);
        Assertions.assertNotNull(account, "id=1 的账号应存在");
        Assertions.assertEquals("reimu", account.getAccount());
        System.out.println("【查询结果】" + account.getAccount() + " / " + account.getPassword());
    }

    @Test
    @Order(3)
    @DisplayName("测试：新增、修改、删除账号")
    void testAccountCRUD() {
        // 新增
        Account account = new Account();
        account.setAccount("test_user");
        account.setPassword("test_pwd");
        account.setStatus("正常");
        boolean inserted = accountService.insert(account);
        Assertions.assertTrue(inserted, "新增账号应成功");
        Assertions.assertTrue(account.getId() > 0, "自增主键应回填");
        System.out.println("【新增账号】id=" + account.getId());

        // 修改
        account.setStatus("冻结");
        boolean updated = accountService.update(account);
        Assertions.assertTrue(updated, "修改账号应成功");
        Account updatedAccount = accountService.selectById(account.getId());
        Assertions.assertEquals("冻结", updatedAccount.getStatus());

        // 删除
        boolean deleted = accountService.deleteById(account.getId());
        Assertions.assertTrue(deleted, "删除账号应成功");
        Assertions.assertNull(accountService.selectById(account.getId()), "删除后应查不到");
    }

    // ============================================================
    // 2. RoleService 测试
    // ============================================================

    @Test
    @Order(4)
    @DisplayName("测试：查询全部角色")
    void testRoleSelectAll() {
        List<Role> roles = roleService.selectAll();
        Assertions.assertNotNull(roles);
        Assertions.assertFalse(roles.isEmpty());
        System.out.println("【角色总数】" + roles.size());
        roles.forEach(r -> System.out.println("  " + r.getId() + " - " + r.getRoleName()));
    }

    // ============================================================
    // 3. StoreService 测试
    // ============================================================

    @Test
    @Order(5)
    @DisplayName("测试：查询全部店铺")
    void testStoreSelectAll() {
        List<Store> stores = storeService.selectAll();
        Assertions.assertNotNull(stores);
        Assertions.assertFalse(stores.isEmpty());
        System.out.println("【店铺总数】" + stores.size());
        stores.forEach(s -> System.out.println("  " + s.getId() + " - " + s.getName()
                + " - 评分:" + s.getScore()));
    }

    @Test
    @Order(6)
    @DisplayName("测试：按 ID 查询店铺")
    void testStoreSelectById() {
        Store store = storeService.selectById(1);
        Assertions.assertNotNull(store);
        Assertions.assertEquals("博丽神社团子铺", store.getName());
        System.out.println("【店铺详情】" + store.getName() + " / " + store.getAddress());
    }

    @Test
    @Order(7)
    @DisplayName("测试：新增、修改、删除店铺")
    void testStoreCRUD() {
        Store store = new Store();
        store.setName("测试店铺");
        store.setAddress("测试地址");
        store.setScore(new BigDecimal("4.5"));
        boolean inserted = storeService.insert(store);
        Assertions.assertTrue(inserted, "新增店铺应成功");
        Assertions.assertTrue(store.getId() > 0);
        System.out.println("【新增店铺】id=" + store.getId());

        store.setName("测试店铺-改");
        Assertions.assertTrue(storeService.update(store), "修改店铺应成功");
        Assertions.assertEquals("测试店铺-改", storeService.selectById(store.getId()).getName());

        Assertions.assertTrue(storeService.deleteById(store.getId()), "删除店铺应成功");
        Assertions.assertNull(storeService.selectById(store.getId()));
    }

    // ============================================================
    // 4. ItemTypeService 测试
    // ============================================================

    @Test
    @Order(8)
    @DisplayName("测试：查询全部商品类型")
    void testItemTypeSelectAll() {
        List<ItemType> types = itemTypeService.selectAll();
        Assertions.assertNotNull(types);
        Assertions.assertFalse(types.isEmpty());
        System.out.println("【类型总数】" + types.size());
        types.forEach(t -> System.out.println("  " + t.getId() + " - " + t.getType()));
    }

    // ============================================================
    // 5. ItemService 测试
    // ============================================================

    @Test
    @Order(9)
    @DisplayName("测试：查询全部商品")
    void testItemSelectAll() {
        List<Item> items = itemService.selectAll();
        Assertions.assertNotNull(items);
        Assertions.assertFalse(items.isEmpty());
        System.out.println("【商品总数】" + items.size());
        items.forEach(i -> System.out.println("  " + i.getId() + " - " + i.getName()
                + " - 价格:" + i.getPrice() + " - 库存:" + i.getRemain()));
    }

    @Test
    @Order(10)
    @DisplayName("测试：按类型查询商品")
    void testItemSelectByTypeId() {
        List<Item> items = itemService.selectByTypeId(3); // 3 = 甜品
        Assertions.assertNotNull(items);
        System.out.println("【甜品类商品数】" + items.size());
        items.forEach(i -> System.out.println("  " + i.getName()));
    }

    @Test
    @Order(11)
    @DisplayName("测试：新增、修改、删除商品")
    void testItemCRUD() {
        Item item = new Item();
        item.setName("测试商品");
        item.setTypeId(1);
        item.setPrice(new BigDecimal("9.99"));
        item.setSales(0);
        item.setRemain(100);
        item.setNote("测试备注");
        boolean inserted = itemService.insert(item);
        Assertions.assertTrue(inserted);
        Assertions.assertTrue(item.getId() > 0);
        System.out.println("【新增商品】id=" + item.getId());

        item.setPrice(new BigDecimal("19.99"));
        Assertions.assertTrue(itemService.update(item));
        Assertions.assertEquals(new BigDecimal("19.99"),
                itemService.selectById(item.getId()).getPrice());

        Assertions.assertTrue(itemService.deleteById(item.getId()));
        Assertions.assertNull(itemService.selectById(item.getId()));
    }

    // ============================================================
    // 6. ReviewService 测试
    // ============================================================

    @Test
    @Order(12)
    @DisplayName("测试：查询全部评论")
    void testReviewSelectAll() {
        List<Review> reviews = reviewService.selectAll();
        Assertions.assertNotNull(reviews);
        Assertions.assertFalse(reviews.isEmpty());
        System.out.println("【评论总数】" + reviews.size());
        reviews.forEach(r -> System.out.println("  " + r.getId() + " - 用户:"
                + r.getUserId() + " - 店铺:" + r.getStoreId() + " - " + r.getContent()));
    }

    @Test
    @Order(13)
    @DisplayName("测试：按店铺 ID 查询评论")
    void testReviewSelectByStoreId() {
        List<Review> reviews = reviewService.selectByStoreId(1);
        Assertions.assertNotNull(reviews);
        System.out.println("【店铺1的评论数】" + reviews.size());
    }

    @Test
    @Order(14)
    @DisplayName("测试：新增、修改、删除评论")
    void testReviewCRUD() {
        Review review = new Review();
        review.setUserId(1);
        review.setStoreId(1);
        review.setContent("测试评论内容");
        Assertions.assertTrue(reviewService.insert(review));
        Assertions.assertTrue(review.getId() > 0);
        System.out.println("【新增评论】id=" + review.getId());

        review.setContent("测试评论内容-改");
        Assertions.assertTrue(reviewService.update(review));
        Assertions.assertEquals("测试评论内容-改",
                reviewService.selectById(review.getId()).getContent());

        Assertions.assertTrue(reviewService.deleteById(review.getId()));
        Assertions.assertNull(reviewService.selectById(review.getId()));
    }

    // ============================================================
    // 7. TicketService 测试
    // ============================================================

    @Test
    @Order(15)
    @DisplayName("测试：查询全部订单")
    void testTicketSelectAll() {
        List<Ticket> tickets = ticketService.selectAll();
        Assertions.assertNotNull(tickets);
        Assertions.assertFalse(tickets.isEmpty());
        System.out.println("【订单总数】" + tickets.size());
        tickets.forEach(t -> System.out.println("  " + t.getId() + " - 店铺:"
                + t.getStoreId() + " - 状态:" + t.getStatus()
                + " - 配送员:" + t.getDeliveryGuyId()));
    }

    @Test
    @Order(16)
    @DisplayName("测试：按店铺 ID / 配送员 ID 查询订单")
    void testTicketSelectByStoreAndDelivery() {
        List<Ticket> byStore = ticketService.selectByStoreId(1);
        Assertions.assertNotNull(byStore);
        System.out.println("【店铺1的订单数】" + byStore.size());

        List<Ticket> byDelivery = ticketService.selectByDeliveryGuyId(3);
        Assertions.assertNotNull(byDelivery);
        System.out.println("【配送员3的订单数】" + byDelivery.size());
    }

    @Test
    @Order(17)
    @DisplayName("测试：新增、修改、删除订单")
    void testTicketCRUD() {
        Ticket ticket = new Ticket();
        ticket.setPublishDate(new Date());
        ticket.setAddress("测试地址");
        ticket.setStoreId(1);
        ticket.setStatus("待接单");
        ticket.setDeliveryGuyId(null);
        ticket.setNote("测试订单");
        Assertions.assertTrue(ticketService.insert(ticket));
        Assertions.assertTrue(ticket.getId() > 0);
        System.out.println("【新增订单】id=" + ticket.getId());

        ticket.setStatus("配送中");
        ticket.setDeliveryGuyId(3);
        Assertions.assertTrue(ticketService.update(ticket));
        Ticket updated = ticketService.selectById(ticket.getId());
        Assertions.assertEquals("配送中", updated.getStatus());
        Assertions.assertEquals(3, updated.getDeliveryGuyId());

        Assertions.assertTrue(ticketService.deleteById(ticket.getId()));
        Assertions.assertNull(ticketService.selectById(ticket.getId()));
    }

    // ============================================================
    // 8. TicketItemService 测试
    // ============================================================

    @Test
    @Order(18)
    @DisplayName("测试：按订单 ID 查询订单项")
    void testTicketItemSelectByTicketId() {
        List<TicketItem> items = ticketItemService.selectByTicketId(1);
        Assertions.assertNotNull(items);
        System.out.println("【订单1的订单项数】" + items.size());
        items.forEach(ti -> System.out.println("  商品:" + ti.getItemId()
                + " - 数量:" + ti.getQuantity() + " - 单价:" + ti.getPrice()));
    }

    @Test
    @Order(19)
    @DisplayName("测试：新增、删除订单项")
    void testTicketItemCRUD() {
        TicketItem ticketItem = new TicketItem();
        ticketItem.setTicketId(1);
        ticketItem.setItemId(2);
        ticketItem.setQuantity(3);
        ticketItem.setPrice(new BigDecimal("25.00"));
        Assertions.assertTrue(ticketItemService.insert(ticketItem));
        Assertions.assertTrue(ticketItem.getId() > 0);
        System.out.println("【新增订单项】id=" + ticketItem.getId());

        Assertions.assertTrue(ticketItemService.deleteById(ticketItem.getId()));
    }

    // ============================================================
    // 9. 综合业务场景测试：下单流程
    // ============================================================

    @Test
    @Order(20)
    @DisplayName("综合测试：模拟一次完整下单流程")
    void testFullOrderFlow() {
        System.out.println("\n========== 模拟下单流程开始 ==========");

        // 1) 顾客浏览店铺
        List<Store> stores = storeService.selectAll();
        Assertions.assertFalse(stores.isEmpty());
        Store targetStore = stores.get(0);
        System.out.println("【1】选择店铺：" + targetStore.getName());

        // 2) 浏览该店铺商品（示例：直接查全部）
        List<Item> items = itemService.selectAll();
        Assertions.assertFalse(items.isEmpty());
        Item targetItem = items.get(0);
        System.out.println("【2】选择商品：" + targetItem.getName()
                + "，单价 " + targetItem.getPrice());

        // 3) 创建订单
        Ticket ticket = new Ticket();
        ticket.setPublishDate(new Date());
        ticket.setAddress("测试下单地址");
        ticket.setStoreId(targetStore.getId());
        ticket.setStatus("待接单");
        ticket.setNote("综合测试订单");
        Assertions.assertTrue(ticketService.insert(ticket));
        System.out.println("【3】订单创建成功，id=" + ticket.getId());

        // 4) 添加订单项
        TicketItem ticketItem = new TicketItem();
        ticketItem.setTicketId(ticket.getId());
        ticketItem.setItemId(targetItem.getId());
        ticketItem.setQuantity(2);
        ticketItem.setPrice(targetItem.getPrice());
        Assertions.assertTrue(ticketItemService.insert(ticketItem));
        System.out.println("【4】订单项添加成功，数量 2，单价 " + targetItem.getPrice());

        // 5) 配送员接单
        ticket.setDeliveryGuyId(3);
        ticket.setStatus("配送中");
        Assertions.assertTrue(ticketService.update(ticket));
        System.out.println("【5】配送员3已接单，状态：" + ticket.getStatus());

        // 6) 订单完成
        ticket.setStatus("已完成");
        Assertions.assertTrue(ticketService.update(ticket));
        System.out.println("【6】订单已完成");

        // 7) 顾客评价
        Review review = new Review();
        review.setUserId(1);
        review.setStoreId(targetStore.getId());
        review.setContent("综合测试好评！");
        Assertions.assertTrue(reviewService.insert(review));
        System.out.println("【7】评价成功，id=" + review.getId());

        // 清理测试数据
        reviewService.deleteById(review.getId());
        ticketItemService.deleteById(ticketItem.getId());
        ticketService.deleteById(ticket.getId());

        System.out.println("========== 模拟下单流程结束（测试数据已清理） ==========\n");
    }
}