package main

import (
	"context"
	"go.mongodb.org/mongo-driver/mongo"
)

func createRecord(collection *mongo.Collection, ctx context.Context, data map[string]interface{}) error {
	_, err := collection.InsertOne(ctx, data)
	return err
}
