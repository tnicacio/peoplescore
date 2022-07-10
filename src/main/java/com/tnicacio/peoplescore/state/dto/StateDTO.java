package com.tnicacio.peoplescore.state.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.tnicacio.peoplescore.state.model.StateModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class StateDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonIgnore
    private Long id;

    private String abbreviation;
//
//    public StateDTO(StateModel stateModel) {
//        this.id = stateModel.getId();
//        this.abbreviation = stateModel.getAbbreviation();
//    }
//
//    public StateDTO(String abbreviation) {
//        this.abbreviation = abbreviation;
//    }
}