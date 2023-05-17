package main

import (
	"encoding/json"
	"fmt"
	_ "log"
	"net/http"
	"os"
)

func main() {
	go listenerQUEUE(os.Getenv("MESSAGE_BUS"), os.Getenv("TOKEN_ADD_QUEUE_NAME"), processAddToken)
	go listenerQUEUE(os.Getenv("MESSAGE_BUS"), os.Getenv("TOKEN_REMOVE_QUEUE_NAME"), processRemoveToken)
	http.HandleFunc("/api/tokens", requestHandler)
	http.ListenAndServe(":8080", nil)
}

func requestHandler(w http.ResponseWriter, req *http.Request) {
	w.Header().Set("Content-Type", "application/json")
	response := map[string]interface{}{}

	collection, ctx, err := mongoConnect()

	data := map[string]interface{}{}
	err = json.NewDecoder(req.Body).Decode(&data)
	if err != nil {
		fmt.Println(err.Error())
	}

	switch req.Method {

	case "POST":
		w.WriteHeader(403)
		response = map[string]interface{}{"error": "Operacion no permitida"}
	case "GET":
		response, err = getTokens(collection, ctx)
	case "PUT":
		w.WriteHeader(403)
		response = map[string]interface{}{"error": "Operacion no permitida"}
	case "DELETE":
		w.WriteHeader(403)
		response = map[string]interface{}{"error": "Operacion no permitida"}
	}

	if err != nil {
		response = map[string]interface{}{"error": err.Error()}
	}

	enc := json.NewEncoder(w)
	enc.SetIndent("", "  ")
	if err := enc.Encode(response); err != nil {
		fmt.Println(err.Error())
	}
}
