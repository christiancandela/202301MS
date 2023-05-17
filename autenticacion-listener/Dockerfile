FROM golang:1.16-alpine AS build
WORKDIR /app
RUN go get github.com/rabbitmq/amqp091-go
RUN go get go.mongodb.org/mongo-driver/mongo
RUN go get github.com/joho/godotenv
COPY go.mod ./
COPY go.sum ./
COPY *.go ./
RUN go build -o /autenticacion-listener

## Deploy
#FROM gcr.io/distroless/base-debian10

EXPOSE 8080

CMD [ "/autenticacion-listener" ]