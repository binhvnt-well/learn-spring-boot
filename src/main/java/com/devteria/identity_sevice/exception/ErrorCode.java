package com.devteria.identity_sevice.exception;

//https://www.youtube.com/watch?v=a9wMrj-JHV4&list=PL2xsxmVse9IaxzE8Mght4CFltGOqcG6FC&index=6&ab_channel=Devteria
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized Exception"),
    INVALID_KEY_EXCEPTION(99999, "Invalid Key Exception"), // Handle when mistake key validate
    USER_EXISTED(1002, "User already exist"),
    INVALID_PASSWORD(1003, "Password must be at least 8 character");


    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private final int code;
    private final String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
