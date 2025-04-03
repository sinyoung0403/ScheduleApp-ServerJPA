|                                          📅 ScheduleApp-Server 📅                                          |  
|:------------------------------------------------------------------------------------------------------------------:|
| <img src="https://github.com/user-attachments/assets/3f0705da-87a1-4590-9731-7c6942571f74" width="200"> |


---

## 🧑‍💻 개발자 소개

|  |                                   팀원                                   |
|:-------------:|:----------------------------------------------------------------------:|
|프로필| ![image](https://avatars.githubusercontent.com/u/94594402?v=4&size=64) |
|이름|                                  박신영                                   |
|GitHub|                              sinyoung0403                              |
|기술블로그|                 [블로그](https://sintory-04.tistory.com/)                 |

---

## 📌 프로젝트

### 1. 프로젝트 이름

- **" ScheduleApp-ServerJPA "**

### 2. 프로젝트 소개: 일정 관리 웹 애플리케이션

- 이 프로젝트는 **Spring Boot**와 **JPA**, **MySQL**을 활용한 **일정 관리 웹 애플리케이션**입니다.
- 사용자는 일정과 댓글을 등록, 조회, 수정, 삭제할 수 있으며, 회원가입 및 로그인 기능도 제공합니다.
- 인증/인가를 구현하여 수정 및 삭제 시 사용자의 접근을 제어할 수 있도록 설계되었습니다.
 

---

## 🚀 프로젝트 실행 방법

### 1. Git clone

```bash
git https://github.com/sinyoung0403/ScheduleApp-ServerJPA.git
cd ScheduleApp-ServerJPA
```

### 2. My SQL 설정 ( DB 실행 )

- MySQL 이 설치되어 있어야 합니다.
- `src/main/resources/sql` 에 있는 `schedule.sql` 파일을 실행시켜주세요.

### 3. 환경변수 설정

- `src/main/resources/application.properties` 에 있는 properties 파일을 본인의 DB 정보에 맞게 수정해주세요.
- `spring.datasource.username=root` root 를 본인의 username 으로 수정해주세요.
- `spring.datasource.password=1111` 1111 를 본인의 비밀번호로 수정해주세요.

### 4. Run ScheduleAppServerApplication

- `src/main/java/com/example/scheduleappserver/ScheduleAppServerApplication.java` 를 실행해주세요.
- 상단의 Run 버튼을 클릭하거나, Shift+F10 을 눌러주세요.


### 5. PostMan Test

- `src/main/resources/schedule.postman_collection.json` 를 통해서 Postman json 을 import 를 해주면 됩니다.

---

## 📝 API 명세

> https://documenter.getpostman.com/view/43154257/2sB2cSgip2


---

## 🔗 ERD

- 필수 과제를 기반입니다.

---

## 🗄️ 데이터베이스 스키마 (SQL Query)

### 1. user Table

```mysql
create table user
(
    created_at datetime(6)  null,
    id         bigint auto_increment primary key,
    update_at  datetime(6)  null,
    email      varchar(255) not null,
    name       varchar(255) not null,
    pwd        varchar(255) not null,
    constraint uq_user_email unique (email)
)
```

### 2. plan Table

```mysql
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
)

```

### 2. plan Table

```mysql
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
)
```

## 📚 Stacks

### ✔️ Language & Framework
<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white">
<img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white">
<img src="https://img.shields.io/badge/SpringBoot-6DB33F?style=for-the-badge&logo=Spring Boot&logoColor=white"/>

### ✔️ DataBase
  <img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white"> 


### ✔️ Environment
<img src="https://img.shields.io/badge/intellijidea-000000?style=for-the-badge&logo=intellijidea&logoColor=white"> <img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white"> <img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white">


