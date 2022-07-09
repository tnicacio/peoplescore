package com.tnicacio.peoplescore.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    @NotBlank
    @Size(min = 4, max = 50, message = "username must be between {min} and {max} characters long")
    private String username;

    @NotBlank
    @Size(min = 6, max = 20, message = "username must be between {min} and {max} characters long")
    private String password;

}
