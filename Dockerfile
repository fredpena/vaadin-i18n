FROM eclipse-temurin:17-jre
MAINTAINER fredpena.dev

COPY target/i18n.jar i18n.jar
EXPOSE 37186

ENTRYPOINT ["java", "-jar", "/i18n.jar"]