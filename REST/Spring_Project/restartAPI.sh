#!/bin/bash
sudo fuser -k 9090/tcp
java -jar /home/zuccante/upload/F1-0.0.1.jar &
