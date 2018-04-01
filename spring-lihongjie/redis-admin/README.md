# redis-admin [![Build Status](https://travis-ci.org/mauersu/redis-admin.svg?branch=master)](https://travis-ci.org/mauersu/redis-admin)
[![GitHub release](https://img.shields.io/badge/release-download-orange.svg)](https://github.com/mauersu/redis-admin/releases)
[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)

This is a redis client web tool written based on Java EE and Jedis. It's my objective to build the most convenient redis client web tool in the world. In the first place, it will facilitate in editing redis data, such as: add, update, delete, search, cut, copy, paste etc.

![](https://www.google.com/logos/2012/halloween-2012-hp.jpg)

## Features

**Multiple Redis version adaptive**

 1. Manage redis server, support server password authentication
 2. Manage redis data
 	* New redis data: string, list, hash, set, sorted set
 	* Delete redis data
 	* Update redis data
 	* Search redis data by key
 	* Support paging query redis data
 	* Support multiple language, now support English

##  Screenshots
![Showcase](http://mauersu.github.io/img/redis_admin_zset.png)

##  Quick Start

`First Step`: edit file:'redis.properties' :

**Notice**: redis.server.num must set

```
redis.server.num=1
redis.language=English

#must set a default redis
redis.host.1=10.100.142.9
redis.name.1=10.100.142.9
redis.port.1=6379
redis.password.1=SH89qwIO

redis.host.2=yours
redis.name.2=yours
redis.port.2=yours
redis.password.2=yours
```

`Second Step`: edit file:'application.properties' :

```
####Security Manager
manager.username=admin
manager.password=admin
```

`Third Step`: deploy project

run maven command : mvn clean package
you will found war in 'target/redis-admin.war'
move war to '../tomcat/wabapps/.' and start tomcat 

`Last Step`: visit redis-admin

open your brower and visit: http://localhost:8080/redis-admin/redis

then, enter username and password what you set in file 'application.properties'


##  Releases Notes

**Please Note: trunk is current development branch.**

* [**Releases-Notes**](https://github.com/mauersu/redis-admin/wiki/Recent-Releases-Notes)

##  FAQ

* [**FAQ**](https://github.com/mauersu/redis-admin/wiki/FAQ)

![img-source-from-https://github.com/docker/dockercraft](https://github.com/docker/dockercraft/raw/master/docs/img/contribute.png?raw=true)
