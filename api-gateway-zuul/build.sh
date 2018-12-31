#!/usr/bin/env bash

mvn clean package

docker build -t registry.cn-beijing.aliyuncs.com/kuone/api-gateway-zuul:lastest .
docker push registry.cn-beijing.aliyuncs.com/kuone/api-gateway-zuul:lastest