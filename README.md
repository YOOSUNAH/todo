## SpringBoot-Project-TodoApplication
### 스프링 부트 + JWT 

스파르타-내배캠-개인과제


노션 링크 : <https://robust-tiglon-c3e.notion.site/8b69540ade674eb9990a0357036c99d1>


🖥️ 프로젝트 소개
---
회원가입 하고 로그인 후, Todo를 작성하고 관리하는 App

⚙️ 개발 환경
---
* Java 8
* JDK 17
* Framework : Springboot(3.x)
* Database : MySql

📌 주요 기능
---
* 회원 가입
* 로그인 
  * DB값 검증
  * ID찾기, PW찾기
  * 로그인 시 토큰(Token) 및 세션(Session) 생성

* Todo(할일) 생성
  * 토큰 확인 후 생성
* Todo(할일) 전체 조회
  * 토큰 확인 후 조회
* Todo(할일) 선택 하여 수정
  * 토큰 확인 후 수정
* Todo(할일) 선택 하여 삭제
  * 토근 확인 후 삭제
