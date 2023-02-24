--liquibase formatted sql

--changeSet eosreign:1
CREATE TABLE avatars
(
    id           SERIAL     NOT NULL PRIMARY KEY,
    file         TEXT       NOT NULL
);

--changeSet eosreign:2
CREATE TABLE users
(
    id           SERIAL     NOT NULL PRIMARY KEY,
    first_name   TEXT       NOT NULL,
    last_name    TEXT       NOT NULL,
    phone        TEXT       NOT NULL,
    email        TEXT       NOT NULL,
    city         TEXT       NOT NULL,
    reg_date     TEXT       NOT NULL,
    avatar_id    INT4       NOT NULL,
    role         TEXT       NOT NULL
);

--changeSet eosreign:3
CREATE TABLE comments
(
    id           SERIAL     NOT NULL PRIMARY KEY,
    author_id    INT4       NOT NULL,
    ads_id       INT4       NOT NULL,
    text         TEXT       NOT NULL,
    created_at   TEXT       NOT NULL
);

--changeSet eosreign:4
CREATE TABLE ads
(
    id           SERIAL     NOT NULL PRIMARY KEY,
    image        INT4       NOT NULL,
    title        TEXT       NOT NULL,
    author_id    INT4       NOT NULL,
    description  TEXT       NOT NULL,
    price        INT4       NOT NULL
);

--changeSet eosreign:5
CREATE TABLE images
(
    id           SERIAL     NOT NULL PRIMARY KEY,
    ads_id       INT4       NOT NULL,
    file         TEXT       NOT NULL
);

--changeSet mrgabriell:6
ALTER TABLE users add column username text;
ALTER TABLE users add column password text;

--changeSEt mrgabriell:7
ALTER TABLE ads rename column author_Id to author;
ALTER TABLE avatars rename column file to file_path;
ALTER TABLE comments rename column author_id to author;
ALTER TABLE comments rename column ads_id to ads;
ALTER TABLE users rename column avatar_id to avatar;
ALTER TABLE images rename column ads_id to ads;
ALTER TABLE images rename column file to file_path;




