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


/* 데이터 삽입 */

-- User insert
INSERT INTO planner.user (created_at, id, update_at, email, name, pwd)
VALUES ('2025-04-02 18:49:58.927485', 1, '2025-04-02 18:49:58.927485', 'aa@aa.com', 'aa',
        '$2a$04$Ic8M0bhPsucMterxafJdzu8ki4BTYQddnRD108e5CgxHxifhZ.TCm');
INSERT INTO planner.user (created_at, id, update_at, email, name, pwd)
VALUES ('2025-04-02 18:50:03.954701', 2, '2025-04-02 18:50:03.954701', 'bb@bb.com', 'bb',
        '$2a$04$QhzfU3RA/W393tqlbfUfpOqEiWu14yp1crkZtf0TWhPoG65QyE4oa');
INSERT INTO planner.user (created_at, id, update_at, email, name, pwd)
VALUES ('2025-04-02 18:50:08.622466', 3, '2025-04-02 18:50:08.622466', 'cc@cc.com', 'cc',
        '$2a$04$cNSeOpYKmrMWj.MM8Aq/jO60wpDNO3dmBHblSeGaRHvSJxFPF.npq');
INSERT INTO planner.user (created_at, id, update_at, email, name, pwd)
VALUES ('2025-04-02 18:50:15.340236', 4, '2025-04-02 18:50:15.340236', 'dd@dd.com', 'dd',
        '$2a$04$iuZFwLBdxMXd1AxUY3nF2OxW90BS8pvGag23Bggztr9xdv7gT/9vW');
INSERT INTO planner.user (created_at, id, update_at, email, name, pwd)
VALUES ('2025-04-02 18:50:31.537655', 5, '2025-04-02 18:50:31.537655', 'ee@ee.com', 'ee',
        '$2a$04$c0rrxHQU5PxqCax3ygsKh.Y3T8jmuoZ2qC8EBCZKvXVYnadkK1wKS');

-- Plan insert
INSERT INTO planner.plan (created_at, id, update_at, user_id, contents, title)
VALUES ('2025-04-02 18:51:24.469325', 1, '2025-04-02 18:51:24.469325', 1, 'contentsTest', 'titleTest');
INSERT INTO planner.plan (created_at, id, update_at, user_id, contents, title)
VALUES ('2025-04-02 18:51:25.362860', 2, '2025-04-02 18:51:25.362860', 1, 'contentsTest', 'titleTest');
INSERT INTO planner.plan (created_at, id, update_at, user_id, contents, title)
VALUES ('2025-04-02 18:51:41.091333', 3, '2025-04-02 18:51:41.091333', 2, 'contentsTest', 'titleTest');
INSERT INTO planner.plan (created_at, id, update_at, user_id, contents, title)
VALUES ('2025-04-02 18:51:42.047379', 4, '2025-04-02 18:51:42.047379', 2, 'contentsTest', 'titleTest');
INSERT INTO planner.plan (created_at, id, update_at, user_id, contents, title)
VALUES ('2025-04-02 18:51:53.561438', 5, '2025-04-02 18:51:53.561438', 3, 'contentsTest', 'titleTest');
INSERT INTO planner.plan (created_at, id, update_at, user_id, contents, title)
VALUES ('2025-04-02 18:51:54.772669', 6, '2025-04-02 18:51:54.772669', 3, 'contentsTest', 'titleTest');
INSERT INTO planner.plan (created_at, id, update_at, user_id, contents, title)
VALUES ('2025-04-02 18:52:08.794507', 7, '2025-04-02 18:52:08.794507', 4, 'contentsTest', 'titleTest');
INSERT INTO planner.plan (created_at, id, update_at, user_id, contents, title)
VALUES ('2025-04-02 18:52:09.689866', 8, '2025-04-02 18:52:09.689866', 4, 'contentsTest', 'titleTest');
INSERT INTO planner.plan (created_at, id, update_at, user_id, contents, title)
VALUES ('2025-04-02 18:52:51.864792', 9, '2025-04-02 18:52:51.864792', 5, 'contentsTest', 'titleTest');
INSERT INTO planner.plan (created_at, id, update_at, user_id, contents, title)
VALUES ('2025-04-02 18:52:52.614585', 10, '2025-04-02 18:53:36.875476', 5, 'contentsTestTT', 'titleTest');

-- Comment insert
INSERT INTO planner.comment (created_at, id, plan_id, update_at, user_id, content)
VALUES ('2025-04-02 18:55:18.106950', 1, 1, '2025-04-02 18:55:18.106950', 1, '댓글테스트');
INSERT INTO planner.comment (created_at, id, plan_id, update_at, user_id, content)
VALUES ('2025-04-02 18:55:20.397155', 2, 1, '2025-04-02 18:55:20.397155', 1, '댓글테스트');
INSERT INTO planner.comment (created_at, id, plan_id, update_at, user_id, content)
VALUES ('2025-04-02 18:55:23.324803', 3, 2, '2025-04-02 18:55:23.324803', 1, '댓글테스트');
INSERT INTO planner.comment (created_at, id, plan_id, update_at, user_id, content)
VALUES ('2025-04-02 18:55:24.543878', 4, 2, '2025-04-02 18:55:24.543878', 1, '댓글테스트');
INSERT INTO planner.comment (created_at, id, plan_id, update_at, user_id, content)
VALUES ('2025-04-02 18:55:38.842640', 5, 4, '2025-04-02 18:55:38.842640', 2, '댓글테스트');
INSERT INTO planner.comment (created_at, id, plan_id, update_at, user_id, content)
VALUES ('2025-04-02 18:55:39.582178', 6, 4, '2025-04-02 18:55:39.582178', 2, '댓글테스트');
INSERT INTO planner.comment (created_at, id, plan_id, update_at, user_id, content)
VALUES ('2025-04-02 18:55:42.074601', 7, 5, '2025-04-02 18:55:42.074601', 2, '댓글테스트');
INSERT INTO planner.comment (created_at, id, plan_id, update_at, user_id, content)
VALUES ('2025-04-02 18:55:43.234473', 8, 5, '2025-04-02 18:55:43.234473', 2, '댓글테스트');
INSERT INTO planner.comment (created_at, id, plan_id, update_at, user_id, content)
VALUES ('2025-04-02 18:56:10.708728', 9, 6, '2025-04-02 18:56:10.708728', 3, '댓글테스트');
INSERT INTO planner.comment (created_at, id, plan_id, update_at, user_id, content)
VALUES ('2025-04-02 18:56:11.363893', 10, 6, '2025-04-02 18:56:44.486474', 3, '수정된 댓글입니다.');
