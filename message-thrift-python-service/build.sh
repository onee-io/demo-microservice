#!/usr/bin/env bash

docker build -t registry.cn-beijing.aliyuncs.com/kuone/message-service:lastest .
docker push registry.cn-beijing.aliyuncs.com/kuone/message-service:lastest