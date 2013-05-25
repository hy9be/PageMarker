CREATE DATABASE dlproject;

CREATE TABLE to_read_items(
id int(11) primary key auto_increment,
title varchar(100) not null,
url varchar(500) not null,
zync_code varchar(50) not null,
is_read boolean not null default false,
created_at timestamp default now(),
last_read_at timestamp
);