FROM adoptopenjdk/openjdk11
ENV TZ=Asia/Seoul
COPY build/libs/sunflower-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar","/app.jar"]