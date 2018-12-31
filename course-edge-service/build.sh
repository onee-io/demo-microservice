#!/usr/bin/env bash

mvn clean package

docker build -t registry.cn-beijing.aliyuncs.com/kuone/course-edge-service:lastest .
docker push registry.cn-beijing.aliyuncs.com/kuone/course-edge-service:lastest