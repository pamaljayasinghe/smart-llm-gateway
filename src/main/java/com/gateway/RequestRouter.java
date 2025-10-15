package com.gateway;

public class RequestRouter {

    public ModelEndpoint route(RequestCategory category) {
        String endpointName = Config.getRouting(category);
        return ModelEndpoint.valueOf(endpointName);
    }
}
