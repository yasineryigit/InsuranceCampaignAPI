package com.ossovita.insurancecampaignapi.error.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RepetitiveCampaignException extends RuntimeException {

    public RepetitiveCampaignException(String message) {
        super(message);
    }
}
