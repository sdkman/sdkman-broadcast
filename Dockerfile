FROM java:8

MAINTAINER Marco Vermeulen

RUN mkdir /broadcast

ADD build/libs /broadcast

ENTRYPOINT java -jar /broadcast/application.jar

