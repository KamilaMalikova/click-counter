#!/usr/bin/env bash
hdfs dfs -mkdir /input
hdfs dfs -put /click-counter/input/* /input/
echo "Initialization complete"