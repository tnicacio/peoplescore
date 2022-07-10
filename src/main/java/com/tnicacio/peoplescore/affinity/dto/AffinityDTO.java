package com.tnicacio.peoplescore.affinity.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tnicacio.peoplescore.affinity.model.AffinityModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AffinityDTO {

    @JsonIgnore
    private Long id;

    private String region;

    @JsonProperty("estados")
    private final Set<String> states = new HashSet<>();

    public AffinityDTO(AffinityModel affinityModel) {
        this.id = affinityModel.getId();
        this.region = affinityModel.getRegion();
        affinityModel.getStates().forEach(stateModel -> this.states.add(stateModel.getAbbreviation()));
    }

    @JsonProperty("regiao")
    public void setRegion(String region) {
        this.region = region;
    }

//    @JsonProperty("estados")
//    public void setStates(Set<String> abbreviations) {
//        this.states.addAll(abbreviations);
//    }

    public Set<String> getStates() {
        return states;
    }
}
