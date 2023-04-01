# Base image 설정
FROM adoptopenjdk/openjdk11:latest

# TimeZone 설정
ENV TZ=Asia/Seoul

# 작업 디렉토리 설정
WORKDIR /app

# 해당 폴더의 권한을 변경합니다.
RUN chmod -R 777 /app

# Jar 파일 복사
COPY build/libs/sunflower-0.0.1-SNAPSHOT.jar app.jar

# 컨테이너 실행
CMD ["java", "-jar", "sunflower-0.0.1-SNAPSHOT.jar"]


#FROM adoptopenjdk/openjdk11
#ENV TZ=Asia/Seoul
#COPY build/libs/sunflower-0.0.1-SNAPSHOT.jar app.jar
#ENTRYPOINT ["java", "-jar","/app.jar"]