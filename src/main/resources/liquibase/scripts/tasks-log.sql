--liquibase formatted sql

--changeSet eosreign:1
CREATE TABLE avatars
(
    id           SERIAL     NOT NULL PRIMARY KEY,
    file_path    TEXT       NOT NULL
);

--changeSet eosreign:2
CREATE TABLE users
(
    id           SERIAL     NOT NULL PRIMARY KEY,
    username     TEXT       NOT NULL UNIQUE,
    password     TEXT       NOT NULL,
    first_name   TEXT,
    last_name    TEXT,
    phone        TEXT,
    city         TEXT,
    reg_date     TEXT,
    avatar       INT4,
    role         TEXT,
    enabled      BOOLEAN    NOT NULL
);

--changeSet eosreign:3
CREATE TABLE comments
(
    id           SERIAL     NOT NULL PRIMARY KEY,
    author       INT4       NOT NULL,
    ads          INT4       NOT NULL,
    text         TEXT       NOT NULL,
    created_at   TEXT       NOT NULL
);

--changeSet eosreign:4
CREATE TABLE ads
(
    id           SERIAL     PRIMARY KEY,
    image        INT4,
    title        TEXT       NOT NULL,
    author       INT4       NOT NULL,
    description  TEXT       NOT NULL,
    price        INT4       NOT NULL
);

--changeSet eosreign:5
CREATE TABLE images
(
    id           SERIAL     NOT NULL PRIMARY KEY,
    ads_id       INT4       NOT NULL,
    file_path    TEXT       NOT NULL
);

--changeSet eosreign:6
CREATE TABLE authorities
(
    id        SERIAL        NOT NULL PRIMARY KEY,
    username  VARCHAR(30)   NOT NULL UNIQUE,
    authority VARCHAR(30)   NOT NULL
);