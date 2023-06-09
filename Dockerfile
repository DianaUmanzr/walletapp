FROM openjdk:11-jdk
MAINTAINER dianaumanzor.com
ARG POSTGRES_DB_URL
ENV POSTGRES_DB_URL $POSTGRES_DB_URL
ARG POSTGRES_DB_USERNAME
ENV POSTGRES_DB_USERNAME $POSTGRES_DB_USERNAME
ARG POSTGRES_DB_PASSWORD
ENV POSTGRES_DB_PASSWORD $POSTGRES_DB_PASSWORD
ARG WEB_PORT
ENV WEB_PORT $WEB_PORT
ARG EXTERNAL_API_URL
ENV EXTERNAL_API_URL $EXTERNAL_API_URL
COPY target/wallet.jar wallet.jar
ENTRYPOINT ["java","-jar","/wallet.jar"]
