FROM openjdk:8-jdk-alpine

#工作环境 此后会将包默认放到此目录下
WORKDIR /apps/default

COPY target/sboot-0.0.1.jar app.jar

EXPOSE 8086

ENTRYPOINT ["java","-jar","app.jar"]