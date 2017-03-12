DROP TABLE prodcat;
DROP TABLE categorylist;
DROP TABLE orderitem;
DROP TABLE wishlist;
DROP TABLE orders;
DROP TABLE consumer;
DROP TABLE product;

CREATE TABLE product(
product_id NUMBER (25),
product_name VARCHAR2 (25),
quantity NUMBER (4),
price NUMBER (5),
description VARCHAR2 (255),
PRIMARY KEY (product_id)
);

CREATE TABLE consumer(
user_id NUMBER(25),
user_name VARCHAR2(25),
user_role VARCHAR2(12),
email VARCHAR2(50),
user_password VARCHAR2(30),
address VARCHAR2(255),
phone_number NUMBER(10),
PRIMARY KEY (user_id)
);

CREATE TABLE orders(
order_id NUMBER(25),
order_date DATE,
dispatched VARCHAR2(3),
user_id NUMBER (25),
PRIMARY KEY (order_id),
FOREIGN KEY (user_id) REFERENCES consumer
);

CREATE TABLE wishlist(
list_id NUMBER(25),
quantity NUMBER(3),
user_id NUMBER(25),
product_id NUMBER(25),
PRIMARY KEY(list_id),
FOREIGN KEY (user_id) REFERENCES consumer,
FOREIGN KEY (product_id) REFERENCES product
);

CREATE TABLE orderitem (
order_item_id NUMBER(25),
quantity NUMBER (3),
order_id NUMBER (25),
product_id NUMBER (25),
PRIMARY KEY (order_item_id),
FOREIGN KEY (order_id) REFERENCES orders,
FOREIGN KEY (product_id) REFERENCES product
);

CREATE TABLE categorylist(
cat_id NUMBER (25),
cat_type VARCHAR2 (15),
PRIMARY KEY (cat_id)
);

CREATE TABLE prodcat(
cat_id NUMBER (15),
product_id NUMBER (15),
PRIMARY KEY (cat_id, product_id),
FOREIGN KEY (cat_id) REFERENCES categorylist,
FOREIGN KEY (product_id) REFERENCES product
)