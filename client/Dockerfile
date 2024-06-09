FROM node:10.13.0-alpine AS build

WORKDIR /usr/src/app

COPY package.json package-lock.json ./
RUN npm install

COPY . .
RUN npm run build

FROM nginx:1.17.1-alpine

COPY .nginx.conf /etc/nginx/nginx.conf

# Copy SSL certificates
COPY certs/localhost.crt /etc/nginx/certs/localhost.crt
COPY certs/localhost.key /etc/nginx/certs/localhost.key

RUN rm -rf /usr/share/nginx/html/*

COPY --from=build /usr/src/app/dist/client /usr/share/nginx/html

EXPOSE 4200 80 443
ENTRYPOINT ["nginx", "-g", "daemon off;"]
