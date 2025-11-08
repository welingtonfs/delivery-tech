FROM maven:3.9.10-eclipse-temurin-21 AS build
#Define o diretório de trabalho dentro do contêiner como /app
WORKDIR /app

#Copia o arquivo pom.xml (que define as dependências do Maven) para o diretório de trabalho atual no contêiner
COPY pom.xml .

#Executa o comando Maven para baixar todas as dependências definidas no pom.xml
RUN mvn dependency:go-offline -B

#Copia a pasta src (código-fonte) para o diretório de trabalho no contêiner
COPY src ./src

#Executa o processo de build Maven para compilar e empacotar a aplicação em um arquivo JAR, pulando os testes.
RUN mvn clean package -DskipTests

# Crie o diretório de dados e ajuste permissões AQUI (no build)
RUN mkdir -p /app/data && chmod -R 777 /app/data

# Stage 2: Runtime seguro - Inicia a segunda fase do build multi-estágio
FROM gcr.io/distroless/java21-debian12:nonroot

#Adiciona metadados à imagem indicando quem é o mantenedor
LABEL maintainer="Delivery Tech Team"

#Copia o arquivo JAR gerado na etapa “build” para a nova imagem
COPY --from=build /app/target/*.jar /app/delivery-api.jar

#Define que o contêiner deve executar como usuário nonroot (segurança)
USER nonroot:nonroot

#Define o diretório de trabalho para /app na nova imagem
WORKDIR /app

#nforma que a aplicação dentro do contêiner escuta na porta 8080
EXPOSE 8080
ENTRYPOINT ["java", "-Xms256m", "-Xmx512m", "-Dspring.profiles.active=docker", "-jar", "/app/delivery-api.jar"]
