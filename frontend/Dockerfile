FROM node:18 as builder
WORKDIR /app
ADD package.json package.json
RUN npm install --silent
COPY . /app
RUN npm run build

FROM node:18-alpine
COPY ["package.json", "release/proxy.conf.json", "./"]
COPY --from=builder /app/build ./build

EXPOSE 3000

CMD ["npm", "run", "start"]
