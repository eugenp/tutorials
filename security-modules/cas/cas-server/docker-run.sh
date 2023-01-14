#!/bin/bash

docker stop cas > /dev/null 2>&1
docker rm cas > /dev/null 2>&1
image_tag=(`cat gradle.properties | grep "cas.version" | cut -d= -f2`)
docker run -d -p 8080:8080 -p 8443:8443 --name="cas" org.apereo.cas/cas:"${image_tag}"
docker logs -f cas