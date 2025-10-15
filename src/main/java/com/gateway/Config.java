package com.gateway;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = Config.class.getClassLoader().getResourceAsStream("gateway.properties")) {
            if (input == null) {
                throw new RuntimeException("Unable to find gateway.properties");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration", e);
        }
    }

    public static String getMistralApiKey() {
        return getEnvOrThrow("MISTRAL_API_KEY");
    }

    public static String getAzureApiKey() {
        return getEnvOrThrow("AZURE_API_KEY");
    }

    public static String getGeminiApiKey() {
        return getEnvOrThrow("GEMINI_API_KEY");
    }

    public static String getMistralApiUrl() {
        return properties.getProperty("mistral.api.url");
    }

    public static String getMistralModel() {
        return properties.getProperty("mistral.model");
    }

    public static String getAzureEndpointUrl() {
        return properties.getProperty("azure.endpoint.url");
    }

    public static String getGeminiEndpointUrl() {
        return properties.getProperty("gemini.endpoint.url");
    }

    public static String getRouting(RequestCategory category) {
        return properties.getProperty("routing." + category.name());
    }

    private static String getEnvOrThrow(String key) {
        String value = System.getenv(key);
        if (value == null || value.isEmpty()) {
            throw new RuntimeException("Environment variable " + key + " is not set");
        }
        return value;
    }
}
