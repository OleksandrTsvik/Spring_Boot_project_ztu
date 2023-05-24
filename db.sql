create database ztu_restaurant;

use ztu_restaurant;

create table ztu_restaurant.users
(
    id           int auto_increment not null primary key,
    full_name    varchar(64)        not null,
    phone_number varchar(12) unique not null,
    email        varchar(64) unique not null,
    password     varchar(256)       not null
);

create table ztu_restaurant.admins
(
    id       int auto_increment not null primary key,
    email    varchar(64) unique not null,
    password varchar(256)       not null
);

create table ztu_restaurant.orders
(
    id          int auto_increment not null primary key,
    id_user     int                not null,
    date        datetime           not null,
    status      tinyint            not null,
    total_price decimal            not null,
    foreign key (id_user) references ztu_restaurant.users (id)
);

create table ztu_restaurant.categories_dishes
(
    id   int auto_increment  not null primary key,
    name varchar(128) unique not null
);

create table ztu_restaurant.dishes
(
    id                   int auto_increment not null primary key,
    id_categories_dishes int                null,
    name                 varchar(128)       not null,
    description          text               null,
    components           text               not null,
    price                decimal            not null,
    image_name           varchar(256)       null,
    foreign key (id_categories_dishes) references ztu_restaurant.categories_dishes (id)
);

create table ztu_restaurant.oredered_dishes
(
    id       int auto_increment not null primary key,
    id_dish  int                not null,
    id_order int                not null,
    count    int                not null,
    price    decimal(10, 2)     not null,
    foreign key (id_dish) references ztu_restaurant.dishes (id),
    foreign key (id_order) references ztu_restaurant.orders (id)
);