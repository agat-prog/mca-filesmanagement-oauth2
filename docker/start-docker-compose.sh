#!/usr/bin/env bash

DOCKER_LOCAL_IMAGE=agatalba/tfm-mca-filemanagement-oauth2:$(mvn -f ../pom.xml help:evaluate -Dexpression=project.version -q -DforceStdout)
echo "DOCKER_LOCAL_IMAGE=${DOCKER_LOCAL_IMAGE}">".env"

# Start locally created container
printf "\n==> Start locally container and its dependencies using docker-compose\n"
docker-compose start

exit 0
