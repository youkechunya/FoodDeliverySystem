create database foodDeliverySystem;
use foodDeliverySystem;

create table account(
    id int primary key auto_increment,
    account varchar(20) not null unique comment '账号名',
    password varchar(20) not null comment '密码',
    status varchar(10) not null comment '当前状态（正常，冻结）'
);

create table role(
    id int primary key auto_increment,
    role varchar(10)
);

create table personal_information(
    id int primary key auto_increment,
    accountId int not null comment '账号',
    address varchar(50) comment '地址',
    foreign key (accountId) references account(id)
);

create table store(
    id int primary key auto_increment,
    name varchar(50) not null comment '店铺名称',
    address varchar(50) comment '地址',
    score decimal(2,1) comment '评分'
);

create table item_type(
    id int primary key auto_increment,
    type varchar(10) not null comment '类型名'
);

create table item(
    id int primary key auto_increment,
    name varchar(20) not null comment '商品名称',
    typeId int comment '商品类型',
    price decimal(10,2) comment '价格',
    sales int comment '销量',
    remain int comment '库存量',
    note varchar(50) comment '备注',
    foreign key (typeId) references item_type(id)
);

create table ticket(
    id int primary key auto_increment,
    publishDate datetime comment '订单发布日期',
    address varchar(50) comment '地址',
    storeId int comment '商家',
    status varchar(10) comment '状态',
    deliveryGuyId int comment '配送员',
    note varchar(100) comment '备注',
    foreign key (storeId) references store(id),
    foreign key (deliveryGuyId) references account(id)
);

create table ticket_item(
    id int primary key auto_increment,
    ticketId int,
    itemId int ,
    quantity int comment '数量',
    price decimal(10,2) comment '单价',
    foreign key (ticketId) references ticket(id),
    foreign key (itemId) references item(id)
);

create table review(
    id int primary key auto_increment,
    userId int,
    storeId int,
    content varchar(50),
    foreign key (userId) references account(id),
    foreign key (storeId) references store(id)
);

create table user_role(
    userId int,
    roleId int,
    primary key (userId, roleId),
    foreign key (userId) references account(id),
    foreign key (roleId) references role(id)
);

create table store_item(
    storeId int,
    itemId int,
    primary key (storeId, itemId),
    foreign key (storeId) references store(id),
    foreign key (itemId) references item(id)
);

insert into account (id, account, password, status) values
(1, 'reimu', 'reimu123', '正常'),
(2, 'marisa', 'marisa456', '正常'),
(3, 'sakuya', 'sakuya789', '正常'),
(4, 'youmu', 'youmu101', '正常'),
(5, 'remilia', 'remilia202', '正常'),
(6, 'flandre', 'flandre303', '正常'),
(7, 'yuyuko', 'yuyuko404', '正常'),
(8, 'yukari', 'yukari505', '冻结'),
(9, 'sanae', 'sanae606', '正常'),
(10, 'cirno', 'cirno707', '冻结');

insert into role (id, role) values
(1, '系统管理员'),
(2, '商家'),
(3, '配送员'),
(4, '审核员'),
(5, '顾客');

insert into personal_information (id, accountId, address) values
(1, 1, '幻想乡博丽神社'),
(2, 2, '魔法森林雾雨魔法店'),
(3, 3, '红魔馆女仆休息室'),
(4, 4, '白玉楼庭院'),
(5, 5, '红魔馆地下室'),
(6, 6, '红魔馆地下图书馆'),
(7, 7, '白玉楼西行寺邸'),
(8, 8, '隙间境界'),
(9, 9, '妖怪之山守矢神社'),
(10, 10, '雾之湖湖畔');

insert into store (id, name, address, score) values
(1, '博丽神社团子铺', '幻想乡博丽神社', 4.9),
(2, '雾雨魔法咖啡厅', '魔法森林雾雨魔法店', 4.7),
(3, '红魔馆红茶馆', '红魔馆大厅', 4.8),
(4, '白玉楼食堂', '白玉楼庭院', 4.6),
(5, '永远亭药膳外卖', '迷途竹林永远亭', 4.9),
(6, '守矢神社快餐', '妖怪之山守矢神社', 4.5),
(7, '太阳花田轻食', '太阳花田', 4.8),
(8, '地灵殿烧烤', '旧地狱地灵殿', 4.4),
(9, '命莲寺素食', '命莲寺', 4.7),
(10, '香霖堂小吃', '魔法森林香霖堂', 4.3);

insert into item_type (id, type) values
(1, '主食'),
(2, '饮品'),
(3, '甜品'),
(4, '小吃');

insert into item (id, name, typeId, price, sales, remain, note) values
(1, '博丽神社特制团子', 3, 15.00, 800, 200, '甜而不腻'),
(2, '雾雨魔法蘑菇汤', 2, 25.00, 600, 150, '提神醒脑'),
(3, '红魔馆锡兰红茶', 2, 30.00, 1200, 300, '女仆长亲手泡制'),
(4, '白玉楼樱饼', 3, 18.00, 900, 250, '春季限定'),
(5, '永远亭药膳粥', 1, 35.00, 500, 100, '健康养生'),
(6, '守矢神社炸豆腐', 4, 12.00, 700, 200, '外酥里嫩'),
(7, '太阳花田向日葵蛋糕', 3, 28.00, 400, 80, '阳光的味道'),
(8, '地灵殿烤肉串', 4, 20.00, 1500, 400, '微辣'),
(9, '命莲寺素面', 1, 16.00, 600, 180, '清淡爽口'),
(10, '香霖堂特制关东煮', 4, 22.00, 1000, 300, '热腾腾');

insert into user_role (userId, roleId) values
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5),
(6, 5),
(7, 5),
(8, 5),
(9, 5),
(10, 5);

insert into ticket (id, publishDate, address, storeId, status, deliveryGuyId, note) values
(1, '2026-09-01 12:00:00', '幻想乡博丽神社', 1, '已完成', 3, '放在赛钱箱旁边'),
(2, '2026-09-02 14:30:00', '魔法森林雾雨魔法店', 2, '配送中', 3, '不要香菜'),
(3, '2026-09-03 10:15:00', '红魔馆大厅', 3, '已完成', 3, '快点送'),
(4, '2026-09-04 18:20:00', '白玉楼庭院', 4, '待接单', 3, '放前台'),
(5, '2026-09-05 09:00:00', '迷途竹林永远亭', 5, '已完成', 3, '无'),
(6, '2026-09-06 21:45:00', '妖怪之山守矢神社', 6, '配送中', 3, '多加糖'),
(7, '2026-09-07 11:10:00', '太阳花田', 7, '已完成', 3, '不要辣'),
(8, '2026-09-08 16:30:00', '旧地狱地灵殿', 8, '待接单', 3, '放保安室'),
(9, '2026-09-09 13:50:00', '命莲寺', 9, '已完成', 3, '带包纸巾'),
(10, '2026-09-10 19:00:00', '魔法森林香霖堂', 10, '配送中', 3, '轻拿轻放');

insert into ticket_item (id, ticketId, itemId, quantity, price) values
(1, 1, 1, 2, 15.00),
(2, 1, 3, 1, 30.00),
(3, 2, 2, 2, 25.00),
(4, 2, 5, 1, 35.00),
(5, 3, 3, 3, 30.00),
(6, 4, 4, 2, 18.00),
(7, 5, 5, 12, 35.00),
(8, 6, 6, 1, 12.00),
(9, 7, 7, 2, 28.00),
(10, 8, 8, 1, 20.00);

insert into review (id, userId, storeId, content) values
(1, 1, 1, '博丽神社的团子非常好吃，配送速度也很快！'),
(2, 2, 2, '蘑菇汤很鲜美，喝完感觉整个人都精神了。'),
(3, 5, 3, '红魔馆的红茶果然是女仆长亲手泡的，味道一流。'),
(4, 4, 4, '白玉楼的樱饼很有春天的气息，包装也很精美。'),
(5, 5, 5, '永远亭的药膳粥很养生，喝完胃里暖暖的。'),
(6, 6, 6, '守矢神社的炸豆腐外酥里嫩，非常推荐。'),
(7, 7, 7, '向日葵蛋糕甜而不腻，带有一丝阳光的清香。'),
(8, 8, 8, '地灵殿的烤肉串分量很足，微辣口味刚刚好。'),
(9, 9, 9, '命莲寺的素面清淡爽口，非常适合减脂期吃。'),
(10, 10, 10, '香霖堂的关东煮汤底很浓郁，配送员态度也很好。');

insert into store_item (storeId, itemId) values
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5),
(6, 6),
(7, 7),
(8, 8),
(9, 9),
(10, 10);