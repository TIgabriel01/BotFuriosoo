# Use uma imagem que já tenha o Maven instalado
FROM maven:3.8.6-openjdk-17

# Define o diretório de trabalho dentro do container
WORKDIR /app

# Copia os arquivos do projeto para dentro do container
COPY . /app

# Executa o Maven para compilar o projeto
RUN mvn clean package

# Expõe a porta que o seu bot vai usar
EXPOSE 8080

# Comando para rodar o bot após a construção
CMD ["java", "-jar", "target/botfurioso-1.0-SNAPSHOT.jar"]
