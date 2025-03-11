package com.example.management.exceptoin;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class ApiException extends RuntimeException {
    private int statusCode;

    public ApiException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    @Override
    public String toString() {
        return "{\n" +
                "  \"statusCode\": " + statusCode + ",\n" +
                "  \"message\": \"" + getMessage() + "\"\n" +
                "}";
    }
}
