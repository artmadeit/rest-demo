# Problemas de seguridad
# No hay optimizaciones

FROM openjdk:11-jdk-alpine
COPY target/** hola.jar
ENTRYPOINT exec java -jar hola.jar