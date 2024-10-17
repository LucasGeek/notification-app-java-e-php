# Etapa 1: Construção do projeto com Maven e Java 23
FROM maven:3.9.9-eclipse-temurin-23 AS builder
WORKDIR /app

# Copiar o arquivo pom.xml e baixar as dependências
COPY pom.xml ./
RUN mvn dependency:go-offline -B

# Copiar o restante do código do projeto
COPY src ./src

# Fazer o build do projeto
RUN mvn clean package -DskipTests

# Etapa 2: Imagem final de produção com Java 23
FROM eclipse-temurin:23-jdk-alpine

# Diretório de trabalho dentro do contêiner
WORKDIR /app

# Copiar o jar gerado na fase de build
COPY --from=builder /app/target/notification-0.0.1-SNAPSHOT.jar ./app.jar

# Porta em que a aplicação será exposta
EXPOSE 8080

# Comando para executar o projeto
ENTRYPOINT ["java", "-jar", "app.jar"]
