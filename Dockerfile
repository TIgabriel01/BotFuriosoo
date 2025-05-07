# Usa imagem oficial com Maven e JDK 17
FROM maven:3.9.6-eclipse-temurin-17

# Define o diretório de trabalho dentro do container
WORKDIR /app

# Copia os arquivos do projeto para dentro do container
COPY . /app

# Executa o Maven para compilar o projeto
RUN mvn clean package

# Expõe a porta que o seu bot usa (ajuste se necessário)
EXPOSE 8080

# Comando para rodar o bot
CMD ["java", "-jar", "/app/target/BotFurioso-1.0-SNAPSHOT.jar"]
