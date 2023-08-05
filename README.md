# 🌼오태식(오늘 우리가 택한 식단)
<img width="880" alt="스크린샷 2023-04-07 오전 1 25 42" src="https://user-images.githubusercontent.com/101540771/230439621-d339f015-d7e4-4135-9b48-cded6400c87a.png">
</br>

- [프로젝트 개요](#프로젝트-개요)
- [프로젝트 목표 및 주요 기능](#프로젝트-목표-및-주요-기능)
- [기술 스택](#기술-스택)
- [ERD 설계 및 아키텍쳐](#ERD-설계-및-아키텍쳐)
- [프로젝트 시연](#프로젝트-시연)
- [트러블 슈팅](#트러블-슈팅)

</br>

## 프로젝트 개요
> 여러 사람과 공유하고 영양정보를 정리하며 식단을 관리하는 '식단 관리 SNS'
- 팀 명 : 해바라기
- 프로젝트 명 : 오태식(오늘 우리가 택한 식단)
- 개발 기간 : 2023.02.13 - 03.31
- 참여 인원 : 2명(백엔드 1명, 프론트엔드 1명 - [github](https://github.com/hoinlee-moi/sunFlower))
</br>
</br>

## 프로젝트 목표 및 주요 기능
#### 목표
- 도전적으로 구현하며 원하는 서비스를 제공하기
- open API를 활용하여 정확한 정보를 전달하기
- Redis, Docker 등의 기술 사용 및 비교하기
- API 문서화, 규칙적인 스크럼 등을 통해 협업하기
- 기획부터 배포까지 전체 흐름 파악하기

#### 주요 기능
- 소셜 로그인을 통한 회원가입
- 식단 포스트 게시/댓글/좋아요
- 검색조건을 통한 필터링 조회 및 정렬
- 영양성분 검색
</br>

## 기술 스택
  | **BE** | <img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white"> <img src="https://img.shields.io/badge/spring boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"> <img src="https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white"> <img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white"> <img src="https://img.shields.io/badge/Spring Data JPA-7A1FA2?style=for-the-badge&logo=Spring Data JPA&logoColor=white"> <img src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=Docker&logoColor=white"/> <img src="https://img.shields.io/badge/spring security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white"/> <img src="https://img.shields.io/badge/redis-DC382D?style=for-the-badge&logo=redis&logoColor=white"/>  <img src="https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens"> <img src="https://img.shields.io/badge/hibernate-59666C?style=for-the-badge&logo=hibernate&logoColor=white"/>|
  | :--- | :---- |
  | **CLOUD** |<img src="https://img.shields.io/badge/AWS S3-569A31?style=for-the-badge&logo=Amazon%20S3&logoColor=white"/>  <img src="https://img.shields.io/badge/AWS EC2-FF9900?style=for-the-badge&logo=Amazon%20EC2&logoColor=white"/> <img src="https://img.shields.io/badge/AWS RDS-527FFF?style=for-the-badge&logo=Amazon%20RDS&logoColor=white"/> <img src="https://img.shields.io/badge/AWS ECR-FF9900?style=for-the-badge&logo=Amazon%20ECS&logoColor=white"/> |
  | **ETC** | <img src="https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=Git&logoColor=white"/> <img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white"> <img src="https://img.shields.io/badge/Notion-000000?style=for-the-badge&logo=Notion&logoColor=white"/> <img src="https://img.shields.io/badge/swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=white"/> <img src="https://img.shields.io/badge/github actions-2088FF?style=for-the-badge&logo=github actions&logoColor=white"/>|
</br>

## ERD 설계 및 아키텍쳐

</br>

**ERD 설계**

![SunFlower](https://user-images.githubusercontent.com/101540771/234029995-35467431-e51f-4693-bd25-657f938f6864.png)</br>
</br>

**아키텍쳐**
</br>
</br>
<img width="950" alt="스크린샷 2023-04-24 오후 10 50 05" src="https://user-images.githubusercontent.com/101540771/234018027-dc066820-9d92-4e8f-b04e-b1bde137690a.png">

</br>

## 프로젝트 시연
<details>
<summary>프로젝트 시연</summary>
<div markdown="1">

- 메인화면
  <img width="927" alt="스크린샷 2023-08-05 오후 5 46 47" src="https://github.com/yeonjue-2/sunFlowerP_back/assets/101540771/b89c8ef8-a6fc-46a2-89e1-489f8b50244a">
- 회원가입 / 로그인
  <img width="939" alt="스크린샷 2023-08-05 오후 5 48 01" src="https://github.com/yeonjue-2/sunFlowerP_back/assets/101540771/92137185-2685-4172-aab3-a5d98051f6c6">
- 이메일 인증 </p>
  <img width="470" alt="스크린샷 2023-08-05 오후 5 49 04" src="https://github.com/yeonjue-2/sunFlowerP_back/assets/101540771/27b0d2e0-0a12-4a28-a4d0-7a02aa370979">
- 식품 영양 정보 검색 </p>
  <img width="470" alt="스크린샷 2023-08-05 오후 5 50 42" src="https://github.com/yeonjue-2/sunFlowerP_back/assets/101540771/d5719f33-a660-40b2-8f4e-d908a497b965">
- 식단 공유하기 </p>
  <img width="470" alt="스크린샷 2023-08-05 오후 5 51 45" src="https://github.com/yeonjue-2/sunFlowerP_back/assets/101540771/d2733cff-a38a-4acc-b507-be1d386e4788">
- 댓글, 좋아요 기능 </p>
  <img width="470" alt="스크린샷 2023-08-05 오후 5 52 20" src="https://github.com/yeonjue-2/sunFlowerP_back/assets/101540771/6a60896c-f246-4060-a71d-36af24dfd37b">
- 포스트 내 검색 </p>
  <img width="470" alt="스크린샷 2023-08-05 오후 5 52 48" src="https://github.com/yeonjue-2/sunFlowerP_back/assets/101540771/fd2dc01e-1526-41eb-808b-d51e475ef56c">

</div>
</details>

</br>

## 트러블 슈팅
</br>

