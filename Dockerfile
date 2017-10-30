FROM openjdk:8-alpine

MAINTAINER Hani Naguib <hani.naguib@gimbal.com>

EXPOSE 7011 7012

WORKDIR /code

COPY target/isb-man.jar /code/isb-man.jar

ENV MEM=256m
ENV JAVA_OPTS='-server -Dnetworkaddress.cache.ttl=5 -Dsun.misc.URLClassPath.disableJarChecking=true'
ENV JAVA_ARGS=''
ENV DEPLOY_ENV=''
ENV SPRING_PROFILES_ACTIVE=${DEPLOY_ENV}

CMD java $JAVA_OPTS -Xms${MEM} -Xmx${MEM} -jar /code/isb-man.jar
