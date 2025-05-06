# Use a imagem oficial do OpenJDK (versão 17 ou a que você preferir)
FROM openjdk:17-jdk

# Define o diretório de trabalho dentro do container
WORKDIR /app

# Copia os arquivos do projeto para dentro do container
COPY . /app

# Instala o Maven
RUN apt-get update && apt-get install -y maven

# Executa o Maven para compilar o projeto
RUN mvn clean package

# Expõe a porta que o seu bot vai usar
EXPOSE 8080

# Comando para rodar o bot após a construção
CMD ["java", "-jar", "target/botfurioso-1.0-SNAPSHOT.jar"]
