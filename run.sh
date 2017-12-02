#!/usr/bin/env bash
./gradlew shadowJar

LOG_CONFIG="./src/main/resources/logback.xml"
SERVICE_JAVA_OPTS="-server -Djava.awt.headless=true -Xms170M -Xmx200M -XX:+UseG1GC -XX:MaxGCPauseMillis=100 -Dcom.sun.management.jmxremote.port=28080 -Djava.rmi.server.hostname=127.0.0.1 -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false -Dsun.net.inetaddr.ttl=60"
SERVICE_JAR="./build/libs/ratpack-infinity-all.jar"

java -Dratpack.port="8080" -Dlogback.configurationFile="${LOG_CONFIG}" ${SERVICE_JAVA_OPTS} -jar ${SERVICE_JAR}
