-- liquibase formatted sql

--changeSet eosreign:1
drop table if exists user cascade;
create table user
(
    id           BIGSERIAL  NOT NULL PRIMARY KEY,
    firstName    TEXT       not null,
    secondName   TEXT       not null,
    phone        TEXT       not null,
    email        TEXT       not null,
    city         TEXT       not null,
    regDate      TEXT       not null,
    avatar       TEXT       not null,
    role         TEXT       not null
);

--changeSet eosreign:2
drop table if exists avatars cascade;
create table avatars
(
    id       BIGSERIAL  NOT NULL PRIMARY KEY,
    userId   INT8       not null,
    file     TEXT       not null
);

--changeSet eosreign:3
drop table if exists comments cascade;
create table comments
(
    id       BIGSERIAL  NOT NULL PRIMARY KEY,
    authorId INT8       not null,
    file     TEXT       not null
);