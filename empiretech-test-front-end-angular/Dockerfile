FROM node:18 AS build

WORKDIR /app
COPY empiretech-test-front-end-angular/package*.json ./

RUN npm install
COPY ./empiretech-test-front-end-angular/ ./
RUN npm run build

EXPOSE 4200

CMD ["npm", "run", "start"]