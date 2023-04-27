package com.ossovita.insurancecampaignapi.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ossovita.insurancecampaignapi.utilities.validators.UniqueUserEmail;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class UserRequest {

    //user
    @NotNull(message = "{ossovita.constraint.email.NotNull.message}")
    @Size(min = 4, max = 255)
    @Email(message = "Email should be valid")
    @UniqueUserEmail
    private String userEmail;

    @NotNull
    @Size(min = 8, max = 255)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "{ossovita.constraint.password.Pattern.message}")
    private String userPassword;


    @NotNull
    @Size(min = 1, max = 255)
    private String userFirstName;

    @NotNull
    @Size(min = 1, max = 255)
    private String userLastName;

}
