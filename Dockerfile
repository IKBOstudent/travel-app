FROM node:18.16.0-alpine as front
WORKDIR /frontend
COPY vue-frontend/package*.json /frontend
RUN npm install
COPY vue-frontend /frontend
RUN npm run build


FROM maven:3.8.5 AS build
WORKDIR /backend
COPY pom.xml /backend
COPY src /backend/src
COPY --from=front /frontend/dist /backend/src/main/resources/static
RUN mvn -f pom.xml clean package -DskipTests=true


FROM openjdk:17-alpine
COPY --from=build /backend/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
