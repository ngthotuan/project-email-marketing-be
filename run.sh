#!/bin/bash

USER=ngthotuan
IMAGE=em-be
VERSION=1.0
echo "docker run --rm -p 9002:8080 $USER/$IMAGE:$VERSION"
docker run --rm -p 9002:8080 $USER/$IMAGE:$VERSION
