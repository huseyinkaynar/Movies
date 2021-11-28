package com.koctas.movie.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author hkaynar on 27.11.2021
 */
@AllArgsConstructor
@Getter
public enum ProcessStatus {
    SUCCESS("SUCCESS"), ERROR("ERROR");

    private String value;
}
