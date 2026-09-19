-- database
create schema if not exists storage_management_system collate utf8mb4_0900_ai_ci;
use storage_management_system;

-- user
create table if not exists user
(
    id         bigint auto_increment comment 'id'
        primary key,
    username   varchar(50)          not null comment 'username',
    name       varchar(100)         not null comment 'full name',
    password   varchar(255)         not null comment 'password',
    age        int                  not null comment 'age',
    gender     varchar(20)          not null comment 'gender: male, female, others',
    email      varchar(255)         not null comment 'e-mail',
    role       varchar(20)          not null comment 'role: SUPER_ADMIN, ADMIN, USER',
    status     varchar(20)          not null comment 'status: VALID, BANNED',
    deleted_at timestamp    null comment 'when the user is deleted, null if not deleted',
    constraint username
        unique (username),
    constraint age_positive
        check (`age` > 0),
    constraint role_enum_value
        check (`role` in ('SUPER_ADMIN','ADMIN','USER','UNKNOWN')),
	constraint status_enum_value
		check (`status` in ('VALID','BANNED'))
);

-- refresh token
create table if not exists refresh_token
(
    id         bigint auto_increment comment 'id'
        primary key,
    user_id    bigint    not null comment 'user id',
    token_hash char(64)  not null comment 'hashed token',
    expiration timestamp not null comment 'when the token expires',
    revoked_at timestamp null comment 'when the refresh token is revoked',
    constraint token_hash
        unique (token_hash),
    constraint fk_refresh_token_user
        foreign key (user_id) references user (id)
);

-- items category
create table if not exists category
(
    id        bigint auto_increment comment 'id'
        primary key,
    name      varchar(100) not null comment 'category''s name',
    parent_id bigint       null comment 'parent category''s name',
    constraint name
        unique (name),
    constraint fk_category_parent_id
        foreign key (parent_id) references category (id)
);

-- items info
create table if not exists item_info
(
    id          bigint auto_increment comment 'id'
        primary key,
    name        varchar(100) not null comment 'item name',
    description varchar(200) not null comment 'item description',
    category_id bigint       not null comment 'category',
    unit        varchar(20)  not null comment 'unit for counting',
    sku         varchar(100) not null comment 'stock keeping unit',
    price       int          not null comment 'price for a single item, in US dollar',
    constraint sku
        unique (sku),
    constraint fk_item_info_category
        foreign key (category_id) references category (id)
);

-- storage
create table if not exists storage
(
    id         bigint auto_increment comment 'id'
        primary key,
    name       varchar(100)         not null comment 'storage name',
    address    varchar(200)         not null comment 'location',
    manager_id bigint               not null comment 'person in charge, should not be user-level',
    created_at timestamp    not null comment 'when the storage is created',
    created_by bigint       not null comment 'person who created the storage',
    updated_at timestamp    not null comment 'the last update time of the storage',
    updated_by bigint       null comment 'the last person updated the storage',
    remark     varchar(150) null comment 'remark',
    deleted_at timestamp    null comment 'when the storage is deleted, null if not deleted',
    constraint name
        unique (name),
    constraint fk_storage_created_by
        foreign key (created_by) references user (id),
    constraint fk_storage_manager_id
        foreign key (manager_id) references user (id),
    constraint fk_storage_updated_by
        foreign key (updated_by) references user (id)
);

-- items
create table if not exists items
(
    id         bigint auto_increment comment 'id'
        primary key,
    item_id    bigint        not null comment 'item''s id',
    storage_id bigint        not null comment 'storage id',
    count      int default 0 null comment 'items count',
    constraint uk_items
        unique (item_id, storage_id),
    constraint fk_items_item_info
        foreign key (item_id) references item_info (id),
    constraint fk_items_storage
        foreign key (storage_id) references storage (id)
);

-- stock adjustment history
create table if not exists stock_history
(
    id         bigint auto_increment comment 'id'
        primary key,
    storage_id bigint       not null comment 'the storage the adjustment happened (id)',
    created_by bigint       not null comment 'who created this adjustment (id)',
    created_at timestamp    not null comment 'when is this adjustment created',
    type       varchar(20)  not null comment 'the type of transaction',
    amount     int          not null comment 'the number of items changed',
    remark     varchar(150) not null comment 'reason',
    constraint fk_item_stock_history_storage_id
        foreign key (storage_id) references storage (id),
    constraint fk_stock_adjustment_history_created_by
        foreign key (created_by) references user (id)
);

-- stock change history for each item
create table if not exists item_stock_history
(
    id               bigint auto_increment comment 'id'
        primary key,
    stock_history_id bigint not null comment 'which adjustment did the stock change happened',
    item_id          bigint not null comment 'which item is being changed (id)',
    stock_before     int    not null comment 'the stock before the change',
    amount_change    int    not null comment 'the amount of stock being changed',
    stock_after      int    not null comment 'the stock after the change',
    constraint fk_item_stock_history_item_id
        foreign key (item_id) references item_info (id),
    constraint fk_item_stock_history_stock_adjustment_history
        foreign key (adjustment_id) references stock_adjustment_history (id)
);

