FROM java:8

MAINTAINER Marco Vermeulen

RUN mkdir /broadcast

ADD build/libs /broadcast

ENTRYPOINT java -Djava.security.egd=file:/dev/./urandom -jar /broadcast/application.jar

