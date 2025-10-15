package com.gateway;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class MistralClassifier {
    private final HttpClient httpClient;

    public MistralClassifier() {
        this.httpClient = HttpClient.newHttpClient();
    }

    public RequestCategory classify(UserRequest request) {
        String classificationPrompt = buildClassificationPrompt(request.getContent());
        String response = callMistralAPI(classificationPrompt);
        return parseCategory(response);
    }

    private String buildClassificationPrompt(String userContent) {
        return String.format(
            "Classify the following request into ONE category only. " +
            "Respond with ONLY the category name: CODE_GENERATION, TRANSLATION, SUMMARIZATION, CHAT, or UNKNOWN.\n\n" +
            "Request: %s\n\n" +
            "Category:", userContent
        );
    }

    private String callMistralAPI(String prompt) {
        try {
            JsonObject requestBody = new JsonObject();
            requestBody.addProperty("model", Config.getMistralModel());
            requestBody.add("messages", createMessagesArray(prompt));
            requestBody.addProperty("temperature", 0.0);

            HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(Config.getMistralApiUrl()))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + Config.getMistralApiKey())
                .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
                .build();

            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            JsonObject jsonResponse = JsonParser.parseString(response.body()).getAsJsonObject();
            return jsonResponse.getAsJsonArray("choices")
                .get(0).getAsJsonObject()
                .getAsJsonObject("message")
                .get("content").getAsString();
        } catch (Exception e) {
            throw new RuntimeException("Classification failed", e);
        }
    }

    private JsonObject createMessagesArray(String prompt) {
        JsonObject message = new JsonObject();
        message.addProperty("role", "user");
        message.addProperty("content", prompt);
        
        com.google.gson.JsonArray messages = new com.google.gson.JsonArray();
        messages.add(message);
        
        JsonObject wrapper = new JsonObject();
        wrapper.add("messages", messages);
        return wrapper;
    }

    private RequestCategory parseCategory(String response) {
        String category = response.trim().toUpperCase();
        try {
            return RequestCategory.valueOf(category);
        } catch (IllegalArgumentException e) {
            return RequestCategory.UNKNOWN;
        }
    }
}
