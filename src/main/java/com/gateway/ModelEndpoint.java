package com.gateway;

public enum ModelEndpoint {
    AZURE,
    GEMINI;

    public String getUrl() {
        return switch (this) {
            case AZURE -> Config.getAzureEndpointUrl();
            case GEMINI -> Config.getGeminiEndpointUrl();
        };
    }

    public String getApiKey() {
        return switch (this) {
            case AZURE -> Config.getAzureApiKey();
            case GEMINI -> Config.getGeminiApiKey();
        };
    }
}
