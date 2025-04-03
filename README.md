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

## 📝 API 명세서 [PostMan API Document]

> https://documenter.getpostman.com/view/43154257/2sB2cSgip2


---

## 🔗 ERD

- 도전 과제 기반으로 제작됐습니다.

![ERD](https://github.com/user-attachments/assets/5292473f-7db2-405b-ab99-95b864376273)

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

## 요구사항 확인

### Lv 1. 일정 CRUD  `필수`

> Branch : Level01 참고

- [x] 일정을 생성, 조회, 수정, 삭제할 수 있습니다.

- [x] 일정은 일정의 고유 ID, 제목, 내용, 작성일, 수정일 필드를 가집니다. 

1️⃣ 일정 생성

> `POST` /plans

- 일정 정보를 입력하여 새 일정을 생성합니다.

- 응답 코드: 201 Created

2️⃣ 일정 전체 조회
> `GET` /plans

- 저장된 모든 일정을 조회합니다.

- 응답 코드: 200 OK

3️⃣ 일정 단건 조회

> `GET` /plans/{id}

- 특정 ID에 해당하는 일정을 조회합니다.

- 응답 코드: 200 OK

4️⃣ 일정 수정

> `PATCH` /plans/{id}

- 일정의 제목과 내용을 수정합니다.

- 응답 코드: 200 OK

5️⃣ 일정 삭제

> `DELETE` /plans/{id}

- 특정 ID의 일정을 삭제합니다.

6️⃣ 일정 필드

- `id (Long)`: 일정의 고유 ID

- `title (String)`: 일정 제목 (필수 입력)

- `contents (String)`: 일정 내용 (필수 입력)

- `user_id (Long)`: 작성자 정보

- `createdAt (LocalDateTime)` : 작성일

- `updatedAt (LocalDateTime)` : 수정일


### Lv 2. 유저 CRUD  `필수`

> Branch : Level02 참고

- [x]  유저를 생성, 조회, 수정, 삭제할 수 있습니다.
- [x]  유저는 `유저명`, `이메일`, `작성일` , `수정일` 같은 필드를 가집니다.
- [x]  연관관계 구현


1️⃣ 유저 생성 (회원가입)

> `POST` /users/signup

- 유저 정보를 입력하여 새로운 회원을 등록합니다.

- 응답 코드: 201 Created

2️⃣ 유저 전체 조회

> `GET` /users

- 등록된 모든 유저 정보를 조회합니다.

- 응답 코드: 200 OK

3️⃣ 유저 단건 조회

> `GET` /users/{id}

- 특정 ID에 해당하는 유저 정보를 조회합니다.

- 응답 코드: 200 OK

4️⃣ 유저 정보 수정

> `PATCH` /users/{id}

- 유저의 이름 또는 이메일을 수정합니다.

- 응답 코드: 200 OK

5️⃣ 유저 삭제

> `DELETE` /users/{id}

- 특정 ID의 유저를 삭제합니다.

- 응답 코드: 200 OK


6️⃣ 유저는 아래 필드를 가집니다.

- `id (Long)`: 유저의 고유 ID

- `username (String)`: 유저명 (필수 입력)

- `email (String)`: 유저 이메일 (필수 입력)

- `createdAt (LocalDateTime)`: 유저 생성일 

- `updatedAt (LocalDateTime)`: 유저 수정일 

7️⃣ 일정은 유저 고유 식별자 필드를 가집니다.

- `user_id (Long)`: 작성자 정보






## 📚 Stacks

### ✔️ Language & Framework
<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white">
<img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white">
<img src="https://img.shields.io/badge/SpringBoot-6DB33F?style=for-the-badge&logo=Spring Boot&logoColor=white"/>

### ✔️ DataBase
  <img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white"> 


### ✔️ Environment
<img src="https://img.shields.io/badge/intellijidea-000000?style=for-the-badge&logo=intellijidea&logoColor=white"> <img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white"> <img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white">


