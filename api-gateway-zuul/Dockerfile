FROM registry.cn-beijing.aliyuncs.com/kuone/openjdk:8-jre
MAINTAINER onee 774247851@qq.com

COPY target/api-gateway-zuul-1.0-SNAPSHOT.jar /api-gateway-zuul.jar

ENTRYPOINT ["java", "-jar", "/api-gateway-zuul.jar"]