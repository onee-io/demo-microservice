#!/usr/bin/env bash

mvn clean package

docker build -t registry.cn-beijing.aliyuncs.com/kuone/user-edge-service:lastest .
docker push registry.cn-beijing.aliyuncs.com/kuone/user-edge-service:lastest