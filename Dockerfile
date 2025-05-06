# Usando a imagem oficial do OpenJDK como base
FROM openjdk:11-jre-slim

# Instalando o Maven
RUN apt-get update && apt-get install -y maven

# Copiando todo o código para dentro do container
COPY . /app

# Definindo o diretório de trabalho
WORKDIR /app

# Rodando o build do Maven
RUN mvn clean package

# Comando para rodar o bot
CMD ["java", "-jar", "target/botfurioso-1.0-SNAPSHOT.jar"]
