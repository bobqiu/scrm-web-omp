
create table industry
(
   id                   bigint not null auto_increment,
   name                 varchar(30) not null,
   parent_id            bigint comment '父行业的id',
   level                tinyint comment '1--一级行业,2--二级行业)',
   remark               varchar(100) comment '100',
   update_time          bigint(20) comment '最后更新时间',
   update_id            bigint,
   create_id            bigint,
   create_time          bigint,
   is_delete            tinyint not null default 1 comment '是否删除(1:未删除 2:删除)',
   primary key (id)
);
alter table industry comment '行业';