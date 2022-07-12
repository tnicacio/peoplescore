package com.tnicacio.peoplescore.score.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScoreDTO {

    @JsonIgnore
    private Long id;

    @NotBlank(message = "A descrição é obrigatória")
    private String description;

    @NotNull(message = "O valor inicial é obrigatório")
    private Long initialScore;

    @NotNull(message = "O valor final é obrigatório")
    private Long finalScore;

    @JsonProperty("scoreDescricao")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("inicial")
    public void setInitialScore(Long initialScore) {
        this.initialScore = initialScore;
    }

    @JsonProperty("final")
    public void setFinalScore(Long finalScore) {
        this.finalScore = finalScore;
    }

}
