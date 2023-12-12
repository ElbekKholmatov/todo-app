FROM openjdk:17
VOLUME /tmp
EXPOSE 8080
COPY target/todo-app-0.0.1-SNAPSHOT.jar todo-application.jar
ENTRYPOINT ["java","-jar","/todo-application.jar"]