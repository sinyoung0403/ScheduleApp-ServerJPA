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
- `src/main/resources/sql` 에 있는 `sql.sql` 파일을 실행시켜주세요.

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

### 1. 일정 API

![일정API](https://github.com/user-attachments/assets/5dc72a18-c564-42e9-8c3d-2deec09b70e4)

### 2. 작성자 API

![작성자 API](https://github.com/user-attachments/assets/8a559907-9d56-48f8-8f1d-c4bb249e02a0)


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

---

## 📜 PostMan Test

### 1. Author

#### 1) 작성자 추가

![image](https://github.com/user-attachments/assets/1b4e571b-cedf-444c-af1c-f488072001e6)

#### 2) 작성자 전부 조회

![image](https://github.com/user-attachments/assets/7263c306-533c-48db-8550-342c4659c2f9)

#### 3) 작성자 단건 조회

![image](https://github.com/user-attachments/assets/17d9e056-b2f0-466b-8bb5-c842612e268a)

#### 4) 작성자 수정

![image](https://github.com/user-attachments/assets/3ca5a079-88bd-43e9-9565-3887e1d2ccef)

#### 5) 작성자 삭제

![image](https://github.com/user-attachments/assets/ae0b92ff-2b59-4368-87d0-94eb0edd9715)

### 2. Plan

#### 1) 할일 추가

![image](https://github.com/user-attachments/assets/2faaba39-f1d4-49b8-8a2e-297a85648918)

#### 2) 할일 전부 조회 (작성자 이름과 수정일을 기준으로)

![image](https://github.com/user-attachments/assets/06232449-24cb-4824-bc81-5856ef0cf59a)

#### 3) 할일 단건 조회 (할일의 식별자)

![image](https://github.com/user-attachments/assets/5b62d3a4-b9d8-4f16-9a49-6492d00f98ba)

#### 4) 할일 단건 조회 (작성자의 식별자)

![image](https://github.com/user-attachments/assets/5c036eb4-bb2d-4c47-bdfc-84b3dae0049a)

#### 5) 할일 수정

![image](https://github.com/user-attachments/assets/1c0624a8-88a4-4e20-8413-6c5075956547)

#### 6) 할일 삭제

![image](https://github.com/user-attachments/assets/ea293b06-9eac-472e-bd78-fc17365ccb29)

#### 7) 할일 페이징 조회

![image](https://github.com/user-attachments/assets/1cf1d943-5956-4cea-a708-4d80fafa16c7)

### 3. Error

#### 1) MissingServletRequestParameterException

![파라미터가 없을 경우](https://github.com/user-attachments/assets/63f9e49f-6049-47d3-bb92-e1f9e08e4854)

#### 2) InvalidPasswordException

![비밀번호가 잘못되었을 경우](https://github.com/user-attachments/assets/952b3c7c-dec6-4a48-b220-fd7719826961)

#### 3) DataNotFoundException // 데이터 조회 불가

![데이터가 없을 경우](https://github.com/user-attachments/assets/ad276278-0eae-482a-b2ae-29c16b605120)

#### 4) ConstraintViolationException

![validated 에러](https://github.com/user-attachments/assets/05e8bddd-1ac0-47b9-a99f-496532140e47)

#### 5) MethodArgumentNotValidException

![valid 에러](https://github.com/user-attachments/assets/8a658987-d463-47ff-97af-43b2677d8374)

#### 6) InvalidInputException

![InvalidInputException](https://github.com/user-attachments/assets/05e790d6-c969-40e7-83f4-e2ab4b734b2d)



## 📚 Stacks

### ✔️ Language & Framework
<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white">
<img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white">
<img src="https://img.shields.io/badge/SpringBoot-6DB33F?style=for-the-badge&logo=Spring Boot&logoColor=white"/>

### ✔️ DataBase
  <img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white"> 


### ✔️ Environment
<img src="https://img.shields.io/badge/intellijidea-000000?style=for-the-badge&logo=intellijidea&logoColor=white"> <img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white"> <img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white">


