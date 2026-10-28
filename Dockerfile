# Estágio 1: Build da aplicação usando o Maven (Software que cria build em estágios)
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copia o pom.xml e baixa as dependências (otimiza o cache do Docker)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copia o código fonte e compila o .jar, ignorando os testes na hora de subir
COPY src ./src
RUN mvn clean package -DskipTests

# Estágio 2: Imagem final enxuta apenas com o JRE (Java Runtime Environment)
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copia o .jar gerado no Estágio 1 para a imagem final
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta 8080 que configuramos no application.yml e no docker-compose
EXPOSE 8080

# Comando que inicializa o servidor Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]