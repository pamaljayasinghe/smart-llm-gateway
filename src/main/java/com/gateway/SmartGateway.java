package com.gateway;

public class SmartGateway {
    private final MistralClassifier classifier;
    private final RequestRouter router;

    public SmartGateway() {
        this.classifier = new MistralClassifier();
        this.router = new RequestRouter();
    }

    public GatewayResponse process(UserRequest request) {
        RequestCategory category = classifier.classify(request);
        ModelEndpoint endpoint = router.route(category);
        return new GatewayResponse(category, endpoint, request.getContent());
    }

    public static void main(String[] args) {
        SmartGateway gateway = new SmartGateway();

        UserRequest request = new UserRequest("Write a Python function to sort a list");
        GatewayResponse response = gateway.process(request);

        System.out.println("Request: " + response.getOriginalRequest());
        System.out.println("Category: " + response.getCategory());
        System.out.println("Routed to: " + response.getEndpoint().getUrl());
    }
}
