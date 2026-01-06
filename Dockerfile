# syntax=docker/dockerfile:1

FROM eclipse-temurin:25-jdk-jammy AS build
WORKDIR /workspace

COPY .mvn .mvn
COPY mvnw pom.xml ./
COPY common/pom.xml common/pom.xml
COPY api-gateway/pom.xml api-gateway/pom.xml
COPY auth-service/pom.xml auth-service/pom.xml
COPY content-type-service/pom.xml content-type-service/pom.xml
COPY content-service/pom.xml content-service/pom.xml
COPY media-service/pom.xml media-service/pom.xml
COPY permission-service/pom.xml permission-service/pom.xml

RUN chmod +x mvnw && ./mvnw -q -DskipTests -pl api-gateway,auth-service,content-type-service,content-service,media-service,permission-service -am dependency:go-offline

COPY . .
RUN chmod +x mvnw && ./mvnw -q -DskipTests -pl api-gateway,auth-service,content-type-service,content-service,media-service,permission-service -am clean package

FROM eclipse-temurin:25-jre-jammy AS runner
WORKDIR /app

ENV JAVA_OPTS=""

RUN useradd -m -u 10001 appuser \
  && mkdir -p /app/services /app/uploads /app/.run/pids /app/.run/logs \
  && chown -R appuser:appuser /app

COPY --from=build /workspace/api-gateway/target/api-gateway-*.jar /app/services/api-gateway.jar
COPY --from=build /workspace/auth-service/target/auth-service-*.jar /app/services/auth-service.jar
COPY --from=build /workspace/content-type-service/target/content-type-service-*.jar /app/services/content-type-service.jar
COPY --from=build /workspace/content-service/target/content-service-*.jar /app/services/content-service.jar
COPY --from=build /workspace/media-service/target/media-service-*.jar /app/services/media-service.jar
COPY --from=build /workspace/permission-service/target/permission-service-*.jar /app/services/permission-service.jar

COPY docker-entrypoint.sh /app/docker-entrypoint.sh
RUN chmod +x /app/docker-entrypoint.sh && chown appuser:appuser /app/docker-entrypoint.sh

USER appuser

EXPOSE 8080 8081 8082 8083 8084 8085
ENTRYPOINT ["/app/docker-entrypoint.sh"]
