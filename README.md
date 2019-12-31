# Otp Service
This micro-service allows to generate otp and send the otp as otp and email 

## Requirements
* Spring boot 2.2.1
* Open JDK 11
* Redis 5.0.7
* Maven 3.6 (only to run maven commands)

## Install Redis
```
wget http://download.redis.io/releases/redis-5.0.7.tar.gz
tar xzf redis-5.0.7.tar.gz
cd redis-5.0.7
make
```
## Configuring Redis
* Adding password protecting
* Go to <redis-installation-dir>
* Edit redis.conf, uncomment requirepass 
```
eg: requirepass yourpassword
```

## Start Redis-Server
```
Goto redis installation directory and cd src 
./redis-server
```

##Using the Redis-Cli
To view the data in redis cache we can you the redis-cli tool

* Go to redis installation directory <redis-installation-dir> 
* cd <redis-installation-dir>/src
* run ./redis-cli
* Authenticate by typing 
```
AUTH your password
```
##Using Redis commands
* In the redis cli you can type redis commands
* To get keys 
```
keys * 
```
* Get value of a key
```
get keyName
```
* View ttl
```
ttl keyName
```

Refer [https://redis.io/commands]

##Configuring the micro-service
* Add the password to redis server in <otp-service>/src/main/resources/application.properties
## Dependencies
All dependencies are available in pom.xml.

## Build
```
mvn clean compile package
```

## Run
```
java -jar target/otp-service-0.0.1-SNAPSHOT.jar
```

## Test
```
mvn test
```

### Reference Documentation
For further reference, please consider the following sections:

* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.2.1.RELEASE/maven-plugin/)


## License

Copyright (c) Tokoin - 
This source code is licensed under the  license. 
