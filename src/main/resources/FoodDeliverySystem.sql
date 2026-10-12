drop database if exists foodDeliverySystem;
create database foodDeliverySystem;
use foodDeliverySystem;

create table account(
                        id int primary key auto_increment,
                        account varchar(20) not null unique,
                        password varchar(20) not null,
                        status varchar(10) not null
);

create table role(
                     id int primary key auto_increment,
                     role varchar(10)
);

create table personal_information(
                                     id int primary key auto_increment,
                                     accountId int not null,
                                     address varchar(50),
                                     foreign key (accountId) references account(id)
);

create table store(
                      id int primary key auto_increment,
                      name varchar(50) not null,
                      address varchar(50),
                      score decimal(3,1),
                      status varchar(10) default '待审核'
);

create table item_type(
                          id int primary key auto_increment,
                          type varchar(10) not null
);

create table item(
                     id int primary key auto_increment,
                     name varchar(20) not null,
                     typeId int,
                     price decimal(10,2),
                     sales int,
                     remain int,
                     note varchar(50),
                     foreign key (typeId) references item_type(id)
);

create table ticket(
                       id int primary key auto_increment,
                       publishDate datetime,
                       address varchar(50),
                       storeId int,
                       status varchar(10),
                       deliveryGuyId int,
                       note varchar(100),
                       userId int,
                       deliveryRate decimal(3,2) default 0.40,
                       foreign key (storeId) references store(id),
                       foreign key (deliveryGuyId) references account(id),
                       foreign key (userId) references account(id)
);

create table ticket_item(
                            id int primary key auto_increment,
                            ticketId int,
                            itemId int,
                            quantity int,
                            price decimal(10,2),
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

create table store_owner(
                            id int primary key auto_increment,
                            accountId int not null,
                            storeId int not null,
                            createTime datetime default current_timestamp,
                            foreign key (accountId) references account(id) on delete cascade,
                            foreign key (storeId) references store(id) on delete cascade,
                            unique key uk_account_store (accountId, storeId)
);

create table complaint(
                          id int primary key auto_increment,
                          userId int not null,
                          storeId int not null,
                          ticketId int,
                          content varchar(500) not null,
                          status varchar(20) default '待处理',
                          reply varchar(500),
                          createTime datetime default current_timestamp,
                          foreign key (userId) references account(id),
                          foreign key (storeId) references store(id)
);

create table menu(
                     id int primary key auto_increment,
                     name varchar(50) not null,
                     url varchar(100) not null,
                     roleId int not null,
                     foreign key (roleId) references role(id)
);

create table report(
                       id int primary key auto_increment,
                       reviewId int not null,
                       storeId int not null,
                       reporterId int not null,
                       reason varchar(200),
                       status varchar(20) default '待处理',
                       createTime datetime default current_timestamp,
                       foreign key (reviewId) references review(id) on delete cascade,
                       foreign key (storeId) references store(id),
                       foreign key (reporterId) references account(id)
);

insert into account (id, account, password, status) values
                                                        (1,  'reimu',    'reimu123',    '正常'),
                                                        (2,  'marisa',   'marisa456',   '正常'),
                                                        (3,  'sakuya',   'sakuya789',   '正常'),
                                                        (4,  'youmu',    'youmu101',    '正常'),
                                                        (5,  'remilia',  'remilia202',  '正常'),
                                                        (6,  'flandre',  'flandre303',  '正常'),
                                                        (7,  'yuyuko',   'yuyuko404',   '正常'),
                                                        (8,  'yukari',   'yukari505',   '冻结'),
                                                        (9,  'sanae',    'sanae606',    '正常'),
                                                        (10, 'cirno',    'cirno707',    '冻结'),
                                                        (11, 'patchouli','patchouli808','正常'),
                                                        (12, 'koakuma',  'koakuma909',  '正常'),
                                                        (13, 'meiling',  'meiling010',  '正常'),
                                                        (14, 'ran',      'ran111',      '正常'),
                                                        (15, 'chen',     'chen222',     '正常'),
                                                        (16, 'suika',    'suika333',    '正常'),
                                                        (17, 'aya',      'aya444',      '正常'),
                                                        (18, 'momiji',   'momiji555',   '冻结');

insert into role (id, role) values
                                (1, '系统管理员'),
                                (2, '商家'),
                                (3, '配送员'),
                                (4, '审核员'),
                                (5, '顾客');

insert into personal_information (id, accountId, address) values
                                                              (1,  1,  '幻想乡博丽神社'),
                                                              (2,  2,  '魔法森林雾雨魔法店'),
                                                              (3,  3,  '红魔馆女仆休息室'),
                                                              (4,  4,  '白玉楼庭院'),
                                                              (5,  5,  '红魔馆地下室'),
                                                              (6,  6,  '红魔馆地下图书馆'),
                                                              (7,  7,  '白玉楼西行寺邸'),
                                                              (8,  8,  '隙间境界'),
                                                              (9,  9,  '妖怪之山守矢神社'),
                                                              (10, 10, '雾之湖湖畔'),
                                                              (11, 11, '红魔馆大图书馆'),
                                                              (12, 12, '红魔馆侧馆'),
                                                              (13, 13, '红魔馆门卫室'),
                                                              (14, 14, '八云邸'),
                                                              (15, 15, '八云邸后院'),
                                                              (16, 16, '妖怪之山山顶'),
                                                              (17, 17, '妖怪之山鸦天狗报社'),
                                                              (18, 18, '妖怪之山山脚');

insert into store (id, name, address, score, status) values
                                                         (1,  '博丽神社团子铺',   '幻想乡博丽神社',     4.9, '通过'),
                                                         (2,  '雾雨魔法咖啡厅',   '魔法森林雾雨魔法店', 4.7, '通过'),
                                                         (3,  '红魔馆红茶馆',     '红魔馆大厅',         4.8, '通过'),
                                                         (4,  '白玉楼食堂',       '白玉楼庭院',         4.6, '通过'),
                                                         (5,  '永远亭药膳外卖',   '迷途竹林永远亭',     4.9, '通过'),
                                                         (6,  '守矢神社快餐',     '妖怪之山守矢神社',   4.5, '待审核'),
                                                         (7,  '太阳花田轻食',     '太阳花田',           4.8, '待审核'),
                                                         (8,  '地灵殿烧烤',       '旧地狱地灵殿',       4.4, '待审核'),
                                                         (9,  '命莲寺素食',       '命莲寺',             4.7, '待审核'),
                                                         (10, '香霖堂小吃',       '魔法森林香霖堂',     4.3, '待审核'),
                                                         (11, '红魔馆女仆咖啡',   '红魔馆别馆',         4.6, '通过'),
                                                         (12, '八云邸便当',       '八云邸',             4.5, '通过'),
                                                         (13, '妖怪之山居酒屋',   '妖怪之山山脚',       4.2, '驳回'),
                                                         (14, '雾之湖冰品店',     '雾之湖湖畔',         4.1, '待审核');

insert into item_type (id, type) values
                                     (1, '主食'),
                                     (2, '饮品'),
                                     (3, '甜品'),
                                     (4, '小吃'),
                                     (5, '套餐'),
                                     (6, '夜宵');

insert into item (id, name, typeId, price, sales, remain, note) values
                                                                    (1,  '博丽神社特制团子',   3, 15.00,  800,  200, '甜而不腻'),
                                                                    (2,  '雾雨魔法蘑菇汤',     2, 25.00,  600,  150, '提神醒脑'),
                                                                    (3,  '红魔馆锡兰红茶',     2, 30.00, 1200,  300, '女仆长亲手泡制'),
                                                                    (4,  '白玉楼樱饼',         3, 18.00,  900,  250, '春季限定'),
                                                                    (5,  '永远亭药膳粥',       1, 35.00,  500,  100, '健康养生'),
                                                                    (6,  '守矢神社炸豆腐',     4, 12.00,  700,  200, '外酥里嫩'),
                                                                    (7,  '太阳花田向日葵蛋糕', 3, 28.00,  400,   80, '阳光的味道'),
                                                                    (8,  '地灵殿烤肉串',       4, 20.00, 1500,  400, '微辣'),
                                                                    (9,  '命莲寺素面',         1, 16.00,  600,  180, '清淡爽口'),
                                                                    (10, '香霖堂特制关东煮',   4, 22.00, 1000,  300, '热腾腾'),
                                                                    (11, '红魔馆女仆特调奶茶', 2, 26.00,  850,  220, '女仆长秘方'),
                                                                    (12, '八云邸豪华便当',     5, 45.00,  320,   60, '每日限量'),
                                                                    (13, '雾之湖冰镇西瓜汁',   2, 18.00,  760,  240, '夏日限定'),
                                                                    (14, '红魔馆深夜布丁',     6, 15.00,  520,  160, '夜宵首选'),
                                                                    (15, '妖怪之山烤肉套餐',   5, 55.00,  280,   50, '双人份'),
                                                                    (16, '白玉楼幽灵布丁',     3, 20.00,  610,  190, '入口即化'),
                                                                    (17, '命莲寺素斋套餐',     5, 40.00,  250,   70, '清净素食'),
                                                                    (18, '永远亭安神茶',       2, 22.00,  430,  130, '睡前一杯');

insert into user_role (userId, roleId) values
                                           (1,  1),
                                           (2,  2),
                                           (2,  5),
                                           (3,  3),
                                           (3,  5),
                                           (4,  4),
                                           (5,  5),
                                           (5,  3),
                                           (6,  5),
                                           (7,  5),
                                           (7,  2),
                                           (8,  5),
                                           (9,  5),
                                           (10, 5),
                                           (11, 2),
                                           (11, 5),
                                           (12, 5),
                                           (13, 5),
                                           (14, 2),
                                           (15, 3),
                                           (16, 5),
                                           (17, 5),
                                           (18, 5);

insert into store_owner (accountId, storeId) values
                                                 (2,  1),
                                                 (2,  2),
                                                 (11, 3),
                                                 (11, 11),
                                                 (4,  4),
                                                 (5,  5),
                                                 (14, 12),
                                                 (7,  6),
                                                 (9,  7),
                                                 (12, 8),
                                                 (13, 9),
                                                 (15, 10),
                                                 (3,  13),
                                                 (10, 14);

insert into ticket (id, publishDate, address, storeId, status, deliveryGuyId, note, userId, deliveryRate) values
                                                                                                              (1,  '2026-09-01 12:00:00', '幻想乡博丽神社',     1,  '已完成', 3,   '放在赛钱箱旁边', 5,  0.40),
                                                                                                              (2,  '2026-09-02 14:30:00', '魔法森林雾雨魔法店', 2,  '配送中', 3,   '不要香菜',       5,  0.40),
                                                                                                              (3,  '2026-09-03 10:15:00', '红魔馆大厅',         3,  '已完成', 3,   '快点送',         6,  0.35),
                                                                                                              (4,  '2026-09-04 18:20:00', '白玉楼庭院',         4,  '待接单', null,'放前台',         6,  0.40),
                                                                                                              (5,  '2026-09-05 09:00:00', '迷途竹林永远亭',     5,  '已完成', 3,   '无',             7,  0.30),
                                                                                                              (6,  '2026-09-06 21:45:00', '妖怪之山守矢神社',   6,  '配送中', 3,   '多加糖',         7,  0.40),
                                                                                                              (7,  '2026-09-07 11:10:00', '太阳花田',           7,  '已完成', 15,  '不要辣',         8,  0.40),
                                                                                                              (8,  '2026-09-08 16:30:00', '旧地狱地灵殿',       8,  '待接单', null,'放保安室',       8,  0.40),
                                                                                                              (9,  '2026-09-09 13:50:00', '命莲寺',             9,  '已完成', 15,  '带包纸巾',       9,  0.25),
                                                                                                              (10, '2026-09-10 19:00:00', '魔法森林香霖堂',     10, '配送中', 3,   '轻拿轻放',       9,  0.40),
                                                                                                              (11, '2026-09-11 12:30:00', '红魔馆别馆',         11, '已完成', 15,  '多加奶',         12, 0.40),
                                                                                                              (12, '2026-09-12 20:10:00', '八云邸',             12, '待接单', null,'不要葱',         13, 0.40),
                                                                                                              (13, '2026-09-13 08:45:00', '雾之湖湖畔',         14, '配送中', 3,   '快一点',         16, 0.40),
                                                                                                              (14, '2026-09-14 17:20:00', '红魔馆大厅',         3,  '已完成', 15,  '温度刚好',       17, 0.20),
                                                                                                              (15, '2026-09-15 22:00:00', '白玉楼庭院',         4,  '配送中', 15,  '深夜加餐',       18, 0.40),
                                                                                                              (16, '2026-09-16 09:30:00', '迷途竹林永远亭',     5,  '待接单', null,'无',             5,  0.40),
                                                                                                              (17, '2026-09-17 11:45:00', '妖怪之山守矢神社',   6,  '已完成', 3,   '加辣',           6,  0.40),
                                                                                                              (18, '2026-09-18 19:30:00', '红魔馆别馆',         11, '配送中', 15,  '不要冰',         12, 0.40);

insert into ticket_item (id, ticketId, itemId, quantity, price) values
                                                                    (1,  1,  1,  2,  15.00),
                                                                    (2,  1,  3,  1,  30.00),
                                                                    (3,  2,  2,  2,  25.00),
                                                                    (4,  2,  5,  1,  35.00),
                                                                    (5,  3,  3,  3,  30.00),
                                                                    (6,  4,  4,  2,  18.00),
                                                                    (7,  5,  5,  12, 35.00),
                                                                    (8,  6,  6,  1,  12.00),
                                                                    (9,  7,  7,  2,  28.00),
                                                                    (10, 8,  8,  1,  20.00),
                                                                    (11, 9,  9,  2,  16.00),
                                                                    (12, 10, 10, 3,  22.00),
                                                                    (13, 11, 11, 2,  26.00),
                                                                    (14, 12, 12, 1,  45.00),
                                                                    (15, 13, 13, 4,  18.00),
                                                                    (16, 14, 3,  2,  30.00),
                                                                    (17, 14, 14, 1,  15.00),
                                                                    (18, 15, 4,  1,  18.00),
                                                                    (19, 15, 16, 2,  20.00),
                                                                    (20, 16, 5,  1,  35.00),
                                                                    (21, 17, 6,  2,  12.00),
                                                                    (22, 17, 17, 1,  40.00),
                                                                    (23, 18, 11, 3,  26.00),
                                                                    (24, 18, 14, 2,  15.00);

insert into review (id, userId, storeId, content) values
                                                      (1,  1,  1,  '博丽神社的团子非常好吃，配送速度也很快！'),
                                                      (2,  2,  2,  '蘑菇汤很鲜美，喝完感觉整个人都精神了。'),
                                                      (3,  5,  3,  '红魔馆的红茶果然是女仆长亲手泡的，味道一流。'),
                                                      (4,  4,  4,  '白玉楼的樱饼很有春天的气息，包装也很精美。'),
                                                      (5,  5,  5,  '永远亭的药膳粥很养生，喝完胃里暖暖的。'),
                                                      (6,  6,  6,  '守矢神社的炸豆腐外酥里嫩，非常推荐。'),
                                                      (7,  7,  7,  '向日葵蛋糕甜而不腻，带有一丝阳光的清香。'),
                                                      (8,  8,  8,  '地灵殿的烤肉串分量很足，微辣口味刚刚好。'),
                                                      (9,  9,  9,  '命莲寺的素面清淡爽口，非常适合减脂期吃。'),
                                                      (10, 10, 10, '香霖堂的关东煮汤底很浓郁，配送员态度也很好。'),
                                                      (11, 12, 11, '女仆特调奶茶真的太好喝了，一秒回到红魔馆。'),
                                                      (12, 13, 12, '八云邸的便当分量很足，荤素搭配合理。'),
                                                      (13, 16, 14, '雾之湖的西瓜汁很新鲜，夏天来一杯太爽了。'),
                                                      (14, 17, 3,  '第二次回购红茶了，稳定发挥。'),
                                                      (15, 18, 4,  '深夜布丁拯救了我的夜宵。'),
                                                      (16, 5,  11, '女仆咖啡别馆环境很好，饮品也不错。'),
                                                      (17, 7,  12, '便当里的紫菜卷很惊艳，下次还会再点。'),
                                                      (18, 9,  13, '居酒屋的烤肉很香，就是上菜有点慢。'),
                                                      (19, 16, 14, '冰品店的分量稍微小了一点，但味道不错。'),
                                                      (20, 6,  6,  '炸豆腐真的值得推荐，回购三次了。');

insert into store_item (storeId, itemId) values
                                             (1,  1),
                                             (2,  2),
                                             (3,  3),
                                             (3,  11),
                                             (4,  4),
                                             (4,  16),
                                             (5,  5),
                                             (5,  18),
                                             (6,  6),
                                             (6,  17),
                                             (7,  7),
                                             (8,  8),
                                             (8,  15),
                                             (9,  9),
                                             (9,  17),
                                             (10, 10),
                                             (11, 11),
                                             (11, 14),
                                             (12, 12),
                                             (13, 15),
                                             (14, 13);

insert into complaint (id, userId, storeId, ticketId, content, status, reply, createTime) values
                                                                                              (1,  5,  1,  1,  '团子送到时已经凉了，希望改进配送速度。',       '已处理', '已反馈配送员并补偿优惠券。',        '2026-09-01 13:10:00'),
                                                                                              (2,  5,  2,  2,  '蘑菇汤里有一根头发，太失望了。',               '已仲裁', '经查证为个别情况，已对商家做出警告。','2026-09-02 15:20:00'),
                                                                                              (3,  6,  3,  3,  '红茶味道很好但是配送太慢。',                   '已处理', '已提醒配送员注意时效。',            '2026-09-03 11:00:00'),
                                                                                              (4,  6,  4,  4,  '樱饼包装破损了一个。',                         '待处理', null,                                '2026-09-04 19:00:00'),
                                                                                              (5,  7,  5,  5,  '药膳粥分量比图片上少。',                       '已处理', '已说明图片仅供参考并退款 5 元。',  '2026-09-05 10:00:00'),
                                                                                              (6,  7,  6,  6,  '炸豆腐太咸了，希望能改进。',                   '已处理', '已反馈厨师调整口味。',              '2026-09-06 22:00:00'),
                                                                                              (7,  8,  7,  7,  '蛋糕上的奶油化了。',                           '已处理', '已补偿同款蛋糕一份。',              '2026-09-07 12:00:00'),
                                                                                              (8,  8,  8,  8,  '烤肉串有一根签子断了。',                       '已仲裁', '已确认包装问题，商家承担赔偿。',  '2026-09-08 17:30:00'),
                                                                                              (9,  9,  9,  9,  '素面里吃出异物，非常不满。',                   '已仲裁', '已对商家开出罚单，全额退款。',    '2026-09-09 14:30:00'),
                                                                                              (10, 9,  10, 10, '关东煮的汤太淡了。',                           '已处理', '已反馈商家调整汤底。',              '2026-09-10 20:00:00'),
                                                                                              (11, 12, 11, 11, '奶茶里冰太多了。',                             '已处理', '已提醒商家注意备注。',              '2026-09-11 13:00:00'),
                                                                                              (12, 13, 12, 12, '便当送到时已经洒了。',                         '待处理', null,                                '2026-09-12 21:00:00'),
                                                                                              (13, 16, 14, 13, '西瓜汁没有想象中新鲜。',                       '待处理', null,                                '2026-09-13 09:30:00'),
                                                                                              (14, 17, 3,  14, '红茶温度不够热。',                             '已处理', '已提醒商家加保温措施。',            '2026-09-14 18:00:00'),
                                                                                              (15, 18, 4,  15, '深夜布丁口感有点硬。',                         '待处理', null,                                '2026-09-15 23:00:00'),
                                                                                              (16, 5,  11, 18, '女仆咖啡的奶茶珍珠太硬了。',                   '待处理', null,                                '2026-09-18 20:00:00');

insert into report (id, reviewId, storeId, reporterId, reason, status, createTime) values
                                                                                       (1, 2,  2,  2,  '内容含不实信息，污蔑本店食材。', '待处理', '2026-10-01 10:00:00'),
                                                                                       (2, 8,  8,  12, '内容与事实不符，已构成诽谤。',   '待处理', '2026-10-02 11:00:00'),
                                                                                       (3, 18, 13, 3,  '恶意差评，无实际用餐记录。',     '已保留', '2026-10-03 12:00:00');

insert into menu (name, url, roleId) values
                                         ('用户管理', '/api/admin/account/page',    1),
                                         ('角色管理', '/api/admin/role/page',       1),
                                         ('权限管理', '/api/admin/permission/page', 1),
                                         ('店铺管理', '/api/admin/store/page',      1);

insert into menu (name, url, roleId) values
                                         ('店铺信息', '/api/store/info/page',      2),
                                         ('商品管理', '/api/store/item/page',      2),
                                         ('店铺订单', '/api/store/ticket/page',    2),
                                         ('投诉处理', '/api/store/complaint/page', 2),
                                         ('店铺评价', '/api/store/review/page',    2);

insert into menu (name, url, roleId) values
                                         ('可接订单', '/api/delivery/ticket/page',      3),
                                         ('我的配送', '/api/delivery/ticket/mine/page', 3);

insert into menu (name, url, roleId) values
                                         ('店铺审核', '/api/audit/store/page',  4),
                                         ('评价仲裁', '/api/audit/review/page', 4);

insert into menu (name, url, roleId) values
                                         ('店铺',     '/api/customer/store/page',  5),
                                         ('购物车',   '/api/customer/cart/page',   5),
                                         ('我的订单', '/api/customer/ticket/page', 5);