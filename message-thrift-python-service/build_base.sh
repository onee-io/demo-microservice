#!/usr/bin/env bash

docker build -t registry.cn-beijing.aliyuncs.com/kuone/python-base:lastest -f Dockerfile.base .
docker push registry.cn-beijing.aliyuncs.com/kuone/python-base:lastest