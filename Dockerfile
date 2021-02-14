FROM adoptopenjdk/maven-openjdk11 as builder
COPY pom.xml /tmp/
COPY src /tmp/src/
WORKDIR /tmp/
RUN mvn package

FROM adoptopenjdk/openjdk11:alpine
COPY --from=builder /tmp/target/*.jar app.jar
#ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-XX:+PrintFlagsFinal", "-XX:+UnlockExperimentalVMOptions", "-XX:+UseCGroupMemoryLimitForHeap", "-Xmx750m", "-Xms750m", "-XX:MaxPermSize=1000m", "-jar", "/app.jar"]
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
ENTRYPOINT ["java","-jar", "/app.jar"]
