#!/bin/bash

USER=ngthotuan
IMAGE=em-be
VERSION=1.0
mvn clean package -DskipTest
docker build -t $USER/$IMAGE:$VERSION .
