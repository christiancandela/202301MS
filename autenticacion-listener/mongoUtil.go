package main

import (
	"context"
	"fmt"
	"go.mongodb.org/mongo-driver/mongo"
	"go.mongodb.org/mongo-driver/mongo/options"
	_ "log"
	"os"
)

func mongoConnect() (*mongo.Collection, context.Context, error) {

	ctx := context.Background()
	uri := os.Getenv("MONGODB_URI")
	dbName := os.Getenv("MONGODB_NAME")
	client, err := mongo.Connect(ctx, options.Client().ApplyURI(uri))
	if err != nil {
		fmt.Println(err.Error())
	}

	collection := client.Database(dbName).Collection("tokens")

	return collection, ctx, err
}
