--liquibase formatted sql

--changeSet eosreign:1
drop table if exists images cascade;
CREATE TABLE images
(
    id           BIGSERIAL  NOT NULL PRIMARY KEY,
    ads          INT8 REFERENCES ads (id),
    file         TEXT       NOT NULL
);

--changeSet eosreign:2
drop table if exists ads cascade;
CREATE TABLE ads
(
    id           BIGSERIAL  NOT NULL PRIMARY KEY,
    image        INT8 REFERENCES image (id),
    title        TEXT       NOT NULL,
    author_id    INT8 REFERENCES users (id),
    description  TEXT       NOT NULL,
    price        INT8       NOT NULL
);

--changeSet eosreign:3
drop table if exists avatars cascade;
CREATE TABLE avatars
(
    id           BIGSERIAL  NOT NULL PRIMARY KEY,
    user_id      INT8       NOT NULL,
    file         TEXT       NOT NULL
);

--changeSet eosreign:4
drop table if exists comments cascade;
CREATE TABLE comments
(
    id           BIGSERIAL  NOT NULL PRIMARY KEY,
    author_id    INT8 REFERENCES users (id),
    ads_id       INT8 REFERENCES ads (id),
    text         TEXT       NOT NULL,
    created_at   TEXT       NOT NULL
);

--changeSet eosreign:5
drop table if exists users cascade;
CREATE TABLE users
(
    id           BIGSERIAL  NOT NULL PRIMARY KEY,
    first_name   TEXT       NOT NULL,
    second_name  TEXT       NOT NULL,
    phone        TEXT       NOT NULL,
    email        TEXT       NOT NULL,
    city         TEXT       NOT NULL,
    reg_date     TEXT       NOT NULL,
    avatar       INT8 REFERENCES avatar (id),
    role         TEXT       NOT NULL
);

