-- -----------------------------------------2016-11-28 16:25 开始-------------------------------------------

/**
初始化运营后台数据
@author 黄伟
@date 2016-11-28 16:25
**/
CREATE TABLE `sys_button` (
  `button_id` int(11) NOT NULL AUTO_INCREMENT,
  `menu_id` int(11) NOT NULL COMMENT '子菜单名称',
  `button_name` varchar(64) NOT NULL COMMENT '按钮名称',
  `button_url` varchar(255) NOT NULL COMMENT '按钮路径',
  `status` int(3) NOT NULL DEFAULT '1',
  PRIMARY KEY (`button_id`)
) ENGINE=INNODB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;

CREATE TABLE `sys_menu` (
  `menu_id` int(11) NOT NULL AUTO_INCREMENT,
  `menu_name` varchar(64) NOT NULL COMMENT '菜单名称',
  `menu_url` varchar(255) DEFAULT NULL COMMENT '菜单URL',
  `parent_id` int(11) DEFAULT NULL COMMENT '父菜单ID',
  `menu_order` int(11) DEFAULT NULL COMMENT '菜单顺序',
  `menu_icon` varchar(32) DEFAULT NULL COMMENT '菜单图标',
  `menu_type` int(11) DEFAULT NULL COMMENT '菜单类型 资源类型：1-分类； 2-菜单',
  `removable` int(11) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `status` int(3) NOT NULL DEFAULT '1',
  PRIMARY KEY (`menu_id`)
) ENGINE=INNODB AUTO_INCREMENT=65 DEFAULT CHARSET=utf8;

CREATE TABLE `sys_role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(64) NOT NULL COMMENT '角色名称',
  `removable` int(3) DEFAULT NULL COMMENT '可被删除 0=不行 1=可以',
  `allocatable` int(3) DEFAULT NULL COMMENT '可被分配 0=不行 1=可以',
  `description` varchar(255) DEFAULT NULL,
  `status` int(3) NOT NULL DEFAULT '1',
   create_time          bigint(20) comment '创建时间',
   create_id            bigint,
   update_time          bigint(20) comment '最后更新时间',
   update_id            bigint,
  PRIMARY KEY (`role_id`)
) ENGINE=INNODB AUTO_INCREMENT=127 DEFAULT CHARSET=utf8;

CREATE TABLE `sys_role_resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL DEFAULT '0',
  `resource_id` int(11) NOT NULL DEFAULT '0',
  `resource_type` int(3) NOT NULL DEFAULT '0' COMMENT '资源类型：1-分类； 2-菜单；3-按钮',
  PRIMARY KEY (`id`),
  KEY `role_id_idx` (`role_id`),
  KEY `resource_id_idx` (`resource_id`)
) ENGINE=INNODB AUTO_INCREMENT=501 DEFAULT CHARSET=utf8 COMMENT='权限和资源（菜单+按钮）';

CREATE TABLE `sys_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_name` varchar(64) NOT NULL COMMENT '登录名',
  `password` varchar(64) NOT NULL COMMENT '密码',
  `name` varchar(64) DEFAULT NULL COMMENT '姓名',
  `last_login` varchar(255) DEFAULT NULL COMMENT '上次登录时间',
  `ip` varchar(32) DEFAULT NULL COMMENT 'IP',
  `status` varchar(32) DEFAULT NULL COMMENT '状态',
  `description` varchar(255) DEFAULT NULL,
  `email` varchar(32) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(32) DEFAULT NULL,
  `skin` int(11) NOT NULL DEFAULT '1',
   create_time          bigint(20) comment '创建时间',
   create_id            bigint,
  PRIMARY KEY (`user_id`)
) ENGINE=INNODB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8;

CREATE TABLE `sys_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL DEFAULT '0',
  `role_id` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8 COMMENT='用户和角色';
-- -----------------------------------------2016-11-28 16:25 结束-------------------------------------------

-- -----------------------------------------2016-12-8 20:12 开始-------------------------------------------
/**
初始化区域数据
@author 郭灏
@date 2016-12-8 20:12
**/
CREATE TABLE `geog_contry` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(32) DEFAULT NULL COMMENT '国家名称',
  `create_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `create_id` bigint(20) DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `geog_contry_name_uni_idx` (`name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='地理国家信息表';

CREATE TABLE `geog_province` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `contry_id` int(11) DEFAULT NULL COMMENT '国家ID',
  `name` varchar(32) DEFAULT NULL COMMENT '省份名称',
  `create_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `create_id` bigint(20) DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `geog_province_name_uni_idx` (`name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8 COMMENT='地理省份信息';

CREATE TABLE `geog_city` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `contry_id` int(11) DEFAULT NULL COMMENT '国家ID',
  `province_id` int(11) DEFAULT NULL COMMENT '省份ID',
  `name` varchar(32) DEFAULT NULL COMMENT '城市名称',
  `create_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `create_id` bigint(20) DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `geog_city_name_uni_idx` (`name`) USING BTREE,
  KEY `geog_city_province_id_idx` (`province_id`)
) ENGINE=InnoDB AUTO_INCREMENT=364 DEFAULT CHARSET=utf8 COMMENT='地理城市信息';

CREATE TABLE `geog_area` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `contry_id` int(11) DEFAULT NULL COMMENT '国家ID',
  `province_id` int(11) DEFAULT NULL COMMENT '省份ID',
  `city_id` int(11) DEFAULT NULL COMMENT '城市ID',
  `name` varchar(32) DEFAULT NULL COMMENT '县区名称',
  `create_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `create_id` bigint(20) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `geog_area_name_uni_idx` (`name`,`city_id`) USING BTREE,
  KEY `geog_area_province_id_idx` (`province_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3343 DEFAULT CHARSET=utf8 COMMENT='地理县区信息';

CREATE TABLE `geog_village` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `contry_id` int(11) DEFAULT NULL COMMENT '国家ID',
  `province_id` int(11) DEFAULT NULL COMMENT '省份ID',
  `city_id` int(11) DEFAULT NULL COMMENT '城市ID',
  `area_id` int(11) DEFAULT NULL COMMENT '县区ID',
  `name` varchar(32) DEFAULT NULL COMMENT '街道-村名称',
  `post_no` varchar(16) DEFAULT NULL COMMENT '邮政编码',
  `lng` varchar(32) DEFAULT NULL COMMENT '经度',
  `lat` varchar(32) DEFAULT NULL COMMENT '纬度',
  `create_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `create_id` bigint(20) DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `geog_village_name_uni_idx` (`name`,`area_id`) USING BTREE,
  KEY `geog_village_province_id_idx` (`province_id`),
  KEY `geog_village_city_id_idx` (`city_id`),
  KEY `geog_village_post_no_idx` (`post_no`)
) ENGINE=InnoDB AUTO_INCREMENT=19933 DEFAULT CHARSET=utf8 COMMENT='地理街道-村信息';
-- -----------------------------------------2016-12-8 20:12 16:25 结束-------------------------------------------

create table notice
(
   id                   bigint not null auto_increment comment '主键',
   title                varchar(250) comment '公告标题',
   content              longtext comment '公告内容',
   send_object          tinyint(4) comment '发送对象（1-商户）',
   device_type          tinyint(4) comment '设备类型（1-安卓，2-苹果）',
   province             bigint(20) comment '区域省',
   city                 bigint(20) comment '区域市',
   county               bigint(20) comment '区域区',
   org_id               bigint comment '门店id（多个用,分割）',
   send_time            bigint(20) comment '发布时间（定时发送是未来时间）',
   send_status          tinyint(4) comment '发送状态（1-草搞，2-已发送，3-取消发送，4-定时发送）',
   send_channel         varchar(10) comment '发送渠道（omp-运营后台，smp-商户后台）',
   send_user_id         bigint comment '发送公告的用户',
   send_username        varchar(100) comment '发送用户名',
   create_time          bigint(20) comment '创建时间',
   create_id            bigint,
   update_time          bigint(20) comment '最后更新时间',
   update_id            bigint,
   is_delete            tinyint not null default 1 comment '是否删除(1:未删除 2:删除)',
   primary key (id)
);
alter table notice comment '系统公告';

create table notice_user
(
   id                   bigint(20) not null auto_increment comment '主键',
   title                varchar(250) comment '公告标题',
   notice_id            bigint(20) comment '公告id',
   user_id              bigint(20) comment '用户id',
   imei                 varchar(20) comment '用户imei号',
   org_id               bigint(20) comment '用户门店',
   send_time            bigint(20) comment '发送时间',
   send_status          tinyint(4) comment '发送状态(1-已发送，2-待发送，3-取消发送)',
   update_time          bigint(20) comment '最后更新时间',
   update_id            bigint,
   create_id            bigint,
   create_time          bigint,
   is_delete            tinyint not null default 1 comment '是否删除(1:未删除 2:删除)',
   primary key (id)
);
alter table notice_user comment '公告用户';

create table notice_org
(
   id                   bigint not null auto_increment,
   notice_id            bigint,
   org_id               bigint,
   create_time          bigint(20) comment '创建时间',
   create_id            bigint,
   update_time          bigint(20) comment '最后更新时间',
   update_id            bigint,
   is_delete            tinyint not null default 1 comment '是否删除(1:未删除 2:删除)',
   primary key (id)
);
alter table notice_org comment '公告门店记录 只做记录，不作业务查询';

create table working_machine
(
   id                   bigint not null auto_increment,
   imei                 varchar(50),
   org_id               bigint,
   wechat_id            varchar(50),
   device_status        tinyint comment '1:离线       2:正常',
   wechat_status        tinyint comment '1--未登录     2--登陆,',
   activation_time      bigint,
   is_delete            tinyint not null default 1 comment '是否删除(1:未删除 2:删除)',
   create_time          bigint,
   create_id            bigint,
   update_id            bigint,
   update_time          bigint(20) comment '最后更新时间',
   primary key (id)
);
alter table working_machine comment '工作机管理';

create table operate_log
(
   id                   bigint not null auto_increment,
   name                 varchar(30),
   role_name            varchar(30),
   user_name            varchar(30),
   description          varchar(100),
   user_id              bigint comment '操作用户id',
   create_id            bigint,
   create_time          bigint,
   primary key (id)
);
alter table operate_log comment '操作日志';




