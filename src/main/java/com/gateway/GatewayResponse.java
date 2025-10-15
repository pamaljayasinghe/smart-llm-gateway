package com.gateway;

public class GatewayResponse {
    private final RequestCategory category;
    private final ModelEndpoint endpoint;
    private final String originalRequest;

    public GatewayResponse(RequestCategory category, ModelEndpoint endpoint, String originalRequest) {
        this.category = category;
        this.endpoint = endpoint;
        this.originalRequest = originalRequest;
    }

    public RequestCategory getCategory() {
        return category;
    }

    public ModelEndpoint getEndpoint() {
        return endpoint;
    }

    public String getOriginalRequest() {
        return originalRequest;
    }
}
