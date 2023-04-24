FROM node:19-alpine

WORKDIR /app

COPY package.json /app/

COPY yarn.lock /app/

RUN npm install -g npm@9.4.1

RUN yarn

COPY . .

EXPOSE 3000

CMD ["yarn", "run", "start"]
