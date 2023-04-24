FROM openjdk:20
ADD . /src
WORKDIR /src
RUN ./mvnw package -DskipTests
EXPOSE 8080
ENTRYPOINT ["java","-jar","target/Bookstore-0.0.1-SNAPSHOT.jar"]