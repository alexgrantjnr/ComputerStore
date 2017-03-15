# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table product (
  id                            bigint not null,
  name                          varchar(255),
  quantity                      integer,
  price                         double,
  description                   varchar(255),
  product_image                 varbinary(255),
  constraint pk_product primary key (id)
);
create sequence product_seq;

create table search (
  search                        varchar(255) not null,
  constraint pk_search primary key (search)
);

create table user (
  id                            bigint not null,
  first_name                    varchar(255),
  last_name                     varchar(255),
  email                         varchar(255),
  password                      varchar(255),
  age                           varchar(255),
  phone                         varchar(255),
  mobile                        varchar(255),
  profile_pic                   varbinary(255),
  constraint pk_user primary key (id)
);
create sequence user_seq;


# --- !Downs

drop table if exists product;
drop sequence if exists product_seq;

drop table if exists search;

drop table if exists user;
drop sequence if exists user_seq;

