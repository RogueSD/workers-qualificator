FROM node:18-alpine

COPY ["package.json", "package-lock.json*", "./"]

RUN npm install -g --force

COPY project_pd .

EXPOSE 3000

CMD ["npm", "start"]
