FROM node:18-alpine
WORKDIR /app/
# Зависимости node
RUN apk add --no-cache python3 py3-pip make g++
# Данные проекта
COPY public/ /app/public
COPY src/ /app/src
COPY tsconfig.json /app/
COPY release/package.json /app/
COPY release/proxy.conf.json /app/
# Зависимости
RUN npm install
# Сборка
RUN npm run build
# Открыть порт
EXPOSE 3000
# Запуск проекта
CMD ["npm", "run", "start"]
