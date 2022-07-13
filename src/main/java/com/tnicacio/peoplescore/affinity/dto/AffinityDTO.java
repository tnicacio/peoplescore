package com.tnicacio.peoplescore.affinity.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tnicacio.peoplescore.affinity.validation.AffinityValidator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@AffinityValidator
@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AffinityDTO {

    @JsonIgnore
    private Long id;

    @NotBlank(message = "{validation.affinity_region_not_blank}")
    @JsonProperty("regiao")
    private String region;

    @NotEmpty(message = "{validation.affinity_states_not_empty}")
    @JsonProperty("estados")
    private final Set<String> states = new HashSet<>();

}
