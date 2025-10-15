package com.gateway;

public class UserRequest {
    private final String content;

    public UserRequest(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
