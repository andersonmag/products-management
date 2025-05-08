FROM node:18

WORKDIR /app

COPY empiretech-test-front-end-angular/package*.json ./
RUN npm install
COPY ./empiretech-test-front-end-angular/ ./

EXPOSE 4200

CMD ["npm", "run", "start", "--", "--host", "0.0.0.0", "--disable-host-check"]