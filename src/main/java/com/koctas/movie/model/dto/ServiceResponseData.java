package com.koctas.movie.model.dto;

import lombok.Data;

/**
 * @author hkaynar on 27.11.2021
 */
@Data
public class ServiceResponseData {
    private String status;
    private String errorMessage;
    private String errorMessageDetail;
    private String detail;
    private Object data;
}
