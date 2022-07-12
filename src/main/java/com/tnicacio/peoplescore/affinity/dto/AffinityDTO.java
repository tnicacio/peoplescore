package com.tnicacio.peoplescore.affinity.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tnicacio.peoplescore.affinity.validation.AffinityValidator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@AffinityValidator
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AffinityDTO {

    @JsonIgnore
    private Long id;

    @NotBlank(message = "A região é obrigatória")
    @JsonProperty("regiao")
    private String region;

    @NotNull(message = "A lista de estados é obrigatória")
    @NotEmpty(message = "A lista de estados precisa possuir no mínimo um estado")
    @JsonProperty("estados")
    private final Set<String> states = new HashSet<>();

}
