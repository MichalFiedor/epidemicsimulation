package com.fiedormichal.epidemicsimulation.apierror;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ApiErrorMsg {

    ERROR_OCCURRED("Some exceptions occurred. Check errors list."),
    METHOD_NOT_FOUND("Method with this URL not found."),
    UNSUPPORTED_MEDIA_TYPE("Specified request media type (Content type) is not supported. Got to errors list for details."),
    MISMATCH_TYPE("Wrong method parameter in URL."),
    VALIDATION_ERRORS("Occurred some validation errors. Please check if JSON contains correct values. "
            + "Check errors list for details."),
    VIOLATION_ERRORS("Occurred some violation errors. Please check if JSON contains correct values. "
            + "Check errors list for details."),
    INITIAL_DATA_NOT_FOUND("Initial data not found. Check errors list for details.");

    private String value;
}
