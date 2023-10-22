FROM node:18-alpine as builder
WORKDIR /usr/src/app
COPY ["package.json", "./"]
RUN npm install

FROM node:18-alpine
COPY package.json ./
COPY proxy.conf.json ./
COPY --from=builder /usr/src/app/dist ./dist

EXPOSE 3000

CMD ["npm", "start"]
