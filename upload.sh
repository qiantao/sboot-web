#!/bin/sh
mvn clean package
scp target/sboot-0.0.1.jar root@10.10.16.105:/root/muyang/

ssh root@10.10.16.105  /bin/bash << EOF
    cd ~/muyang
    rm -rf ./log
    rm -rf ./sboot.log
    kill -9 `lsof -i:8808 | grep java | awk '{print $2}'`
    nohup java -jar sboot-0.0.1.jar > sboot.log &

EOF
