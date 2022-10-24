#!/bin/bash

VERSION=1.0
mvn clean package -DskipTest
docker build -t em-be:$VERSION .
