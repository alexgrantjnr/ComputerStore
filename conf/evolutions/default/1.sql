# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table category (
  category_id                   bigint not null,
  constraint pk_category primary key (category_id)
);
create sequence category_seq increment by 1;

create table order_item (
  order_item_id                 bigint not null,
  quantity                      integer,
  total_price                   double,
  order_id                      bigint,
  product_id                    bigint,
  constraint pk_order_item primary key (order_item_id)
);
create sequence order_item_seq increment by 1;

create table orders (
  order_id                      bigint not null,
  date                          varchar(255),
  dispatched                    varchar(255),
  user_id                       varchar(255),
  constraint pk_orders primary key (order_id)
);
create sequence orders_seq increment by 1;

create table product (
  product_id                    bigint not null,
  name                          varchar(255),
  quantity                      integer,
  price                         double,
  description                   varchar(255),
  product_image                 varbinary(255),
  constraint pk_product primary key (product_id)
);
create sequence product_seq increment by 1;

create table search (
  search                        varchar(255) not null,
  constraint pk_search primary key (search)
);

create table user (
  user_id                       bigint not null,
  first_name                    varchar(255),
  last_name                     varchar(255),
  role                          varchar(255),
  email                         varchar(255),
  password                      varchar(255),
  age                           varchar(255),
  phone                         varchar(255),
  mobile                        varchar(255),
  profile_pic                   varbinary(255),
  constraint pk_user primary key (user_id)
);
create sequence user_seq increment by 1;

create table wish_list (
  wishlist_id                   bigint not null,
  num_items                     integer,
  user_id                       bigint,
  product_id                    bigint,
  constraint pk_wish_list primary key (wishlist_id)
);
create sequence wish_list_seq increment by 1;


# --- !Downs

drop table if exists category;
drop sequence if exists category_seq;

drop table if exists order_item;
drop sequence if exists order_item_seq;

drop table if exists orders;
drop sequence if exists orders_seq;

drop table if exists product;
drop sequence if exists product_seq;

drop table if exists search;

drop table if exists user;
drop sequence if exists user_seq;

drop table if exists wish_list;
drop sequence if exists wish_list_seq;

