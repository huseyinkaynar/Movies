package com.koctas.movie.model.data;

import com.koctas.movie.model.ProcessStatus;
import lombok.Data;

/**
 * @author hkaynar on 27.11.2021
 */
@Data
public class ServiceResponseData {
    private ProcessStatus status;
    private String errorMessage;
    private String errorMessageDetail;
    private Object data;

}
