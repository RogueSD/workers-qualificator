FROM node:18 as builder
WORKDIR /app
ADD package.json package.json
RUN npm install --silent
COPY . /app
RUN npm run build

FROM node:18-alpine
COPY --from=builder ["/app/build", "/app/public", "/app/src", "/app/node_modules", "./"]
COPY ["package.json", "release/proxy.conf.json", "./"]
EXPOSE 3000
CMD ["ls", "|", "cat"]
CMD ["npm", "run", "start"]
