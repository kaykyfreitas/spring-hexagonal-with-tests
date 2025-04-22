# Etapa de build
FROM gradle:8.13.0-jdk21-alpine AS build

WORKDIR /app

# Copia apenas os arquivos de build primeiro para otimizar o cache
COPY build.gradle settings.gradle ./

# Copia dependências para cache
RUN gradle build --no-daemon || return 0

# Copia o restante do projeto
COPY . .

# Faz o build do projeto
# Como os containers são efêmeros, o daemon perde sua funcionalidade, podendo até gerar gargalos
RUN gradle build --no-daemon

# Etapa de runtime
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copia o jar gerado
COPY --from=build /app/build/libs/app.jar app.jar

# Argumento para definir o profile
ARG PROFILE=development

# Variável de ambiente para o profile
ENV APP_PROFILE=${PROFILE}

# Argumento para definir a porta
ARG PORT=8080

# Variável de ambiente para a porta da aplicação
ENV APP_PORT=${PORT}

# Definindo a porta que será exposta
EXPOSE ${APP_PORT}

# JVM otimizada
ENV JAVA_OPTS="-XX:+UseContainerSupport \
               -XX:MaxRAMPercentage=75.0 \
               -XX:+ExitOnOutOfMemoryError \
               -XX:+AlwaysActAsServerClassMachine \
               -XX:+UseG1GC \
               -Dfile.encoding=UTF-8"

# Descrição dos flags:
# -XX:+UseContainerSupport: JVM reconhece limites de CPU/RAM no container
# -XX:MaxRAMPercentage=75.0: Limita uso de RAM da JVM a 75% da memória disponível
# -XX:+ExitOnOutOfMemoryError: Mata o processo se der OOM (pra evitar travar o container)
# -XX:+AlwaysActAsServerClassMachine: Força JVM a otimizar como se fosse servidor
# -XX:+UseG1GC: Usa coletor G1GC (mais moderno, balanceado e eficiente)
# -Dfile.encoding=UTF-8: Garante encoding padrão UTF-8 (bom pra evitar bugs com acentos etc)

# Execução do jar
# A execução é feita usando o sh -c para que seja possível passar variáveis de ambiente ($JAVA_OPTS)
CMD ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
