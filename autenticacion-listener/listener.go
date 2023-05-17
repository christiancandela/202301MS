package main

import (
	"bytes"
	"encoding/json"
	"fmt"
	amqp "github.com/rabbitmq/amqp091-go"
	"log"
)

func listenerQUEUE(uri string, exchange string, callBack func(<-chan amqp.Delivery)) {
	fmt.Println("Hello, World!")

	conn, err := amqp.Dial(uri)
	failOnError(err, "Failed to connect to RabbitMQ")
	defer conn.Close()

	ch, err := conn.Channel()
	failOnError(err, "Failed to open a channel")
	defer ch.Close()

	err = ch.ExchangeDeclare(
		exchange, // name
		"fanout", // type
		false,    // durable
		false,    // auto-deleted
		false,    // internal
		false,    // no-wait
		nil,      // arguments
	)
	failOnError(err, "Failed to declare an exchange")

	q, err := ch.QueueDeclare(
		"",    // name
		false, // durable
		false, // delete when unused
		true,  // exclusive
		false, // no-wait
		nil,   // arguments
	)
	failOnError(err, "Failed to declare a queue")

	err = ch.QueueBind(
		q.Name,   // queue name
		"",       // routing key
		exchange, // exchange
		false,
		nil)
	failOnError(err, "Failed to bind a queue")

	msgs, err := ch.Consume(
		q.Name, // queue
		"",     // consumer
		true,   // auto ack
		false,  // exclusive
		false,  // no local
		false,  // no wait
		nil,    // args
	)
	failOnError(err, "Failed to register a consumer")

	var forever chan struct{}

	go callBack(msgs)
	//func() {
	//	collection,ctx,err := mongoConnect()
	//	if err != nil {
	//		log.Printf(" [x] %s", err.Error())
	//	} else {
	//		for d := range msgs {
	//			log.Printf(" [x] %s", d.Body)
	//			data := map[string]interface{}{}
	//			err = json.NewDecoder(bytes.NewReader(d.Body)).Decode(&data)
	//			createRecord(collection, ctx, data)
	//		}
	//	}
	//}()

	log.Printf(" [*] Waiting for logs. To exit press CTRL+C")
	<-forever
}

func failOnError(err error, msg string) {
	if err != nil {
		log.Panicf("%s: %s", msg, err)
	}
}

func processAddToken(msgs <-chan amqp.Delivery) {
	collection, ctx, err := mongoConnect()
	if err != nil {
		log.Printf(" [x] %s", err.Error())
	} else {
		for d := range msgs {
			log.Printf(" [x] %s", d.Body)
			data := map[string]interface{}{}
			err = json.NewDecoder(bytes.NewReader(d.Body)).Decode(&data)
			createRecord(collection, ctx, data)
		}
	}
}

func processRemoveToken(msgs <-chan amqp.Delivery) {
	collection, ctx, err := mongoConnect()
	if err != nil {
		log.Printf(" [x] %s", err.Error())
	} else {
		for d := range msgs {
			log.Printf(" [x] %s", d.Body)
			data := map[string]interface{}{}
			err = json.NewDecoder(bytes.NewReader(d.Body)).Decode(&data)
			deleteRecord(collection, ctx, data)
		}
	}
}
