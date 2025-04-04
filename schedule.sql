/* Table 추가 */

-- User Table
create table user
(
    created_at datetime(6)  null,
    id         bigint auto_increment primary key,
    update_at  datetime(6)  null,
    email      varchar(255) not null,
    name       varchar(255) not null,
    pwd        varchar(255) not null,
    constraint uq_user_email unique (email)
);

-- Plan Table
create table plan
(
    created_at datetime(6)  null,
    id         bigint auto_increment primary key,
    update_at  datetime(6)  null,
    user_id    bigint       null,
    contents   varchar(255) not null,
    title      varchar(255) not null,
    constraint fk_plan_user foreign key (user_id) references user (id)
        on delete cascade
);

-- Comment Table
create table comment
(
    created_at datetime(6)  null,
    id         bigint auto_increment primary key,
    plan_id    bigint       null,
    update_at  datetime(6)  null,
    user_id    bigint       null,
    content    varchar(255) not null,
    constraint fk_comment_user foreign key (user_id) references user (id)
        on delete cascade,
    constraint fk_comment_plan foreign key (plan_id) references plan (id)
        on delete cascade
);