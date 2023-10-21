# MOASHARE

사이드 프로젝트 협업을 위한 프로젝트 팀원 모집 사이트 MOASHARE 입니다. 
게시판 형식을 띄고 있습니다. 



# 프로젝트 목표

* 오류없이 게시판 기능을 구현해 내는 것이 목표입니다.
* 객체지향 원칙에 근거한 클린 코드를 작성하는 것이 목표입니다.
* 성능 & 부하 테스트에서 발생하는 문제 해결 경험에 집중하는 것이 목표입니다.



# 아키텍처
```
소스코드 블록은 다음과 같이 작성할 수 있습니다.

```c
#include <stdio.h>

int main(void) {
  printf("Hello World!");
  return 0;
}
```

# 프로젝트 기능 및 성능

** 주요 기능
* 유효성검사 적용하고 이메일 인증을 거친 회원가입
* 기본 사이트 로그인, MAVER 로그인 API, 구글 로그인 API, 로그아웃
* 메인페이지에 주간 인기글 매일 업데이트
* 게시판 형식 차용한 프로젝트 팀원 모집
* 북마크
* 관리자만 작성 가능한 공지사항
* 프로필 수정
* 에러 발생 시 필요한 에러메세지 문구 또는 관련에러페이지 이동

## 성능
* 이메일 인증서비스 메서드 탬플릿 패턴으로 리팩토링
* 북마크페이지 이동 시 캐싱처리
* @Scheduled를 활용 게시판 테이블에 인기글 조회 및 저장, 인기글테이블 데이터 캐싱 
* DB 인덱스
  
## ERD

