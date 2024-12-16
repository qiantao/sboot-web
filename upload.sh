#!/bin/sh
mvn clean package
scp target/sboot-0.0.1.jar root@47.116.25.140:/qt/app/

ssh root@47.116.25.140  /bin/bash << EOF
    cd /qt/app/
    rm -rf ./log
    rm -rf ./sboot.log
    kill -9 `lsof -i:8808 | grep java | awk '{print $2}'`
    nohup java -jar sboot-0.0.1.jar > sboot.log &
 kill -9 `ps -ef | grep sboot | awk '{print $2}'`
EOF
