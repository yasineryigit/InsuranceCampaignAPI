FROM openjdk:17
ARG JAR_FILE=target/InsuranceCampaignAPI-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} application.jar
ENTRYPOINT ["java", "-jar", "application.jar"]

