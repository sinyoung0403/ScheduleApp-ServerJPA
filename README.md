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


### Lv 3. 회원가입  `필수`

> Branch : Level03 참고

- [x]  유저에 `비밀번호` 필드를 추가합니다.

1️⃣ 유저에 비밀번호 필드 추가

- `private String pwd` 필드 추가

2️⃣ Request Dto 에 비밀번호 추가

- `private String pwd`


### Lv 4. 로그인(인증)  `필수`

- [x] `이메일`과 `비밀번호`를 활용해 로그인 기능을 구현합니다.
- [x] 회원가입, 로그인 요청은 인증 처리에서 제외합니다.
- [x] 예외처리- 로그인 시 이메일과 비밀번호가 일치하지 않을 경우 `HTTP Status code 401` 을 반환합니다.

1️⃣ 로그인 (/users/login)

- 사용자가 이메일(email)과 비밀번호(pwd)를 입력하면 로그인 검증을 수행한다.

- 로그인 성공 시 `HttpSession` 을 생성하고, 로그인한 유저 정보를 세션에 저장한다.

- 로그인 검증 실패 시 예외가 발생하며, `401 Unauthorized` 상태 코드가 반환된다.

2️⃣ 로그아웃 (/users/logout)

- `POST /users/logout` 요청을 받으면 현재 세션을 가져와 `session.invalidate()`를 호출하여 세션을 삭제한다.

3️⃣ 인증 필터 (LoginFilter)

- `Filter` 를 구현하여 로그인 여부를 검증하는 `LoginFilter` 를 작성하였다.

- 요청된 `URL` 이 화이트 리스트`(/, /users/signup, /users/login)`에 포함되지 않은 경우, 로그인 여부를 확인한다.

- 세션이 없거나, 세션에 저장된 `loginUser` 가 없으면 `401 Unauthorized` 예외를 발생시킨다.

- 필터 내부에서 `PatternMatchUtils.simpleMatch(WHITE_LIST, string)` 을 사용하여 화이트 리스트 검사를 수행한다.

- 인증이 필요한 요청은 로그인 여부를 확인한 후 `filterChain.doFilter(servletRequest, servletResponse)` 를 호출하여 다음 필터로 전달한다.

4️⃣ 필터 등록 (WebConfig)

- `@Configuration` 을 활용하여 `FilterRegistrationBean` 을 통해 `LoginFilter` 를 등록한다.

- 필터의 실행 순서를 `setOrder(1)` 로 설정하여 가장 먼저 실행되도록 한다.

- `filterRegistrationBean.addUrlPatterns("/*")` 을 통해 모든 요청`(/*)`에 대해 필터를 적용한다.

5️⃣ 예외 처리
- 로그인 시 이메일 또는 비밀번호가 일치하지 않으면 `401 Unauthorized` 상태 코드가 반환된다.

- 로그인되지 않은 사용자가 인증이 필요한 `URL` 에 접근하면 `401 Unauthorized` 예외가 발생한다.
    - 이후의 branch 에서는 `401 Unauthorized` 대신 `403 Forbidden` 예외 발생



### Lv 5. 다양한 예외처리 적용하기  `도전`

- [x]  Validation 을 활용해 다양한 예외처리를 적용
- [x]  정해진 예외처리 항목이 있는것이 아닌 프로젝트를 분석하고 예외사항을 지정
- [x]  할일 제목은 10글자 이내, 유저명은 4글자 이내
- [x]  `@Pattern`을 사용해서 회원 가입 Email 데이터 검증

1️⃣ `@Valid` 사용 (@RequestBody DTO 검증)

- `@Valid` 를 적용하여 요청 본문의 값을 검증함.

- `GlobalExceptionHandler()` 에서 `ConstraintViolationException` 에외 처리

2️⃣ `@Validated` 사용 (`@PathVariable`, `@RequestParam` 검증)

- `@Validated` 를 적용하여 `@PathVariable`, `@RequestParam` 사용하여 검증함.

- `GlobalExceptionHandler()` 에서 `MethodArgumentNotValidException` 에외 처리 

3️⃣ 데이터 조회 및 유효성 검사 관련 예외 처리

- 데이터 없음 (`DataNotFoundException`)

  → 조회하려는 데이터가 없을 경우 `404 Not Found` 반환.

- 잘못된 입력값 (`InvalidRequestException`)

  → 유효하지 않은 요청이 들어오면 `400 Bad Request` 반환.

- 비밀번호 불일치 (`InvalidPasswordException`)

     → 로그인 시 입력한 비밀번호가 올바르지 않으면 `401 Unauthorized` 반환.

4️⃣ 클라이언트 요청 관련 예외 처리

- 파라미터 누락 (`MissingServletRequestParameterException`)

   → 요청 시 필수 파라미터가 없을 경우 `400 Bad Request` 반환.

- 요청 본문이 없을 경우 (`HttpMessageNotReadableException`)

    → 요청에 body 가 포함되지 않았을 때 `400 Bad Request` 반환.

6️⃣ 이후의 Branch 수정 사항

➕ UnauthorizedAccessException 예외 처리가 추가됨.

➕ 응답 코드 명확성을 위해 일부 예외의 상태 코드가 정리.

### Lv 6. 비밀번호 암호화  `도전`

- [x]  Lv.3에서 추가한 `비밀번호` 필드에 들어가는 비밀번호를 암호화합니다.

1️⃣ PasswordEncoder (암호화 클래스)

- `BCrypt` 를 사용하여 비밀번호 암호화 및 비교 기능을 제공하는 클래스

- `encode(String rawPassword)`: 사용자의 원본 비밀번호를 `BCrypt` 알고리즘을 이용해 암호화.

- `matches(String rawPassword, String encodedPassword)`: 사용자가 입력한 비밀번호와 DB에 저장된 암호화된 비밀번호를 비교.

2️⃣ UserController (회원 관련 API 컨트롤러)

1. `@PostMapping("/signup")` - 유저 회원가입

- 사용자가 입력한 정보를 비밀번호를 암호화한 후, 새로운 회원을 DB에 저장.

2. `@PostMapping("/login")` - 로그인

- 사용자의 이메일과 비밀번호를 검증 후 세션에 저장.

3️⃣ 이후의 Branch 수정 사항

➕ `@PatchMapping` - 유저 정보 수정

- 사용자가 입력한 비밀번호를 검증 후 회원 정보 수정 요청 처리.

➕ `@DeleteMapping` - 유저 삭제

- 사용자가 입력한 비밀번호를 검증 후 회원 삭제 요청 처리.

### Lv 7. 댓글 CRUD  `도전`

- [x]  생성한 일정에 댓글을 남길 수 있습니다.
- [x]  댓글과 일정은 연관관계를 가집니다. 
- [x]  댓글을 저장, 조회, 수정, 삭제할 수 있습니다.
- [x]  댓글은 `댓글 내용`, `작성일`, `수정일`, `유저 고유 식별자`, `일정 고유 식별자` 필드를 가집니다.



## 📚 Stacks

### ✔️ Language & Framework
<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white">
<img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white">
<img src="https://img.shields.io/badge/SpringBoot-6DB33F?style=for-the-badge&logo=Spring Boot&logoColor=white"/>

### ✔️ DataBase
  <img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white"> 


### ✔️ Environment
<img src="https://img.shields.io/badge/intellijidea-000000?style=for-the-badge&logo=intellijidea&logoColor=white"> <img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white"> <img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white">


