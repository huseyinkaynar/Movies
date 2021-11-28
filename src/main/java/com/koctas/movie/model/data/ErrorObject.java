package com.koctas.movie.model.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author hkaynar on 28.11.2021
 */
@Data
@AllArgsConstructor
public class ErrorObject {
    @JsonProperty("error_message")
    private String errorMessage;
    @JsonProperty("status_code")
    private int statusCode;
    private String timestamp;

    public ErrorObject(String errorMessage, int statusCode) {
        this.errorMessage = errorMessage;
        this.statusCode = statusCode;
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    }
}
