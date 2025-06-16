package com.nipa.agroneed.utils;
import com.nipa.agroneed.dto.Response;
import org.springframework.http.HttpStatus;

public final class ResponseBuilder {
    private ResponseBuilder() {
    }
    public static Response getSuccessResponse(HttpStatus status, Object content, String message) {
        Response response = new Response();
        response.setStatusCode(status.value());
        response.setStatus(status.getReasonPhrase());
        response.setMessage(message);
        response.setContent(content);
        return response;
    }
    public static Response getFailResponse(HttpStatus status,Object content, String message) {
        Response response = new Response();
        response.setStatusCode(status.value());
        response.setStatus(status.getReasonPhrase());
        response.setMessage(message);
        response.setContent(content);
        return response;
    }
}
