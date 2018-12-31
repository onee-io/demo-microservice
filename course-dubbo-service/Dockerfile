FROM registry.cn-beijing.aliyuncs.com/kuone/openjdk:8-jre
MAINTAINER onee 774247851@qq.com

COPY target/course-dubbo-service-1.0-SNAPSHOT.jar /course-service.jar

ENTRYPOINT ["java", "-jar", "/course-service.jar"]