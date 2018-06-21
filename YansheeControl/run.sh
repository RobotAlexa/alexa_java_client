#!/bin/bash

pid=`netstat -nlp | grep 29599 | awk '{print $7}' | awk -F"/" '{print $1}'`
if [ ! -d $pid ]; then 
    kill -9 $pid
fi
cd $YANSHEE_CONTROL && python main.py