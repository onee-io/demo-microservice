#!/usr/bin/env bash

# 生成 python 代码
thrift --gen py -out ../ message_service.thrift

# 生成 java 代码
thrift --gen java -out ../../message-thrift-service-api/src/main/java/ message_service.thrift