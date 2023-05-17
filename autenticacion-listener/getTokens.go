package main

import (
	"context"
	"go.mongodb.org/mongo-driver/bson"
	"go.mongodb.org/mongo-driver/mongo"
)

func getTokens(collection *mongo.Collection, ctx context.Context) (map[string]interface{}, error) {
	cur, err := collection.Find(ctx, bson.D{})
	if err != nil {
		return nil, err
	}
	defer cur.Close(ctx)

	var tokens []bson.M

	for cur.Next(ctx) {
		var token bson.M
		if err = cur.Decode(&token); err != nil {
			return nil, err
		}
		tokens = append(tokens, token)
	}

	res := map[string]interface{}{}
	res = map[string]interface{}{
		"data": tokens,
	}

	return res, nil
}
