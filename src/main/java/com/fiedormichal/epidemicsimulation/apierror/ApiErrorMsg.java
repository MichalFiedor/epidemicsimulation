package com.fiedormichal.epidemicsimulation.apierror;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ApiErrorMsg {

    VALIDATION_ERRORS("Occurred some validation errors. Please check if JSON contains correct values. "
            + "Check errors list for details."),
    VIOLATION_ERRORS("Occurred some violation errors. Please check if JSON contains correct values. "
            + "Check errors list for details.");

    private String value;
}
