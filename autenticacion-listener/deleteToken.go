package main

import (
	"context"
	"go.mongodb.org/mongo-driver/bson"
	"go.mongodb.org/mongo-driver/mongo"
)

func deleteRecord(collection *mongo.Collection, ctx context.Context, data map[string]interface{}) error {
	_, err := collection.DeleteOne(ctx, bson.M{"token": data["token"]})
	return err
}
