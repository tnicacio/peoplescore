package com.tnicacio.peoplescore.person.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tnicacio.peoplescore.person.model.PersonModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonDTO extends RepresentationModel<PersonDTO> {

    @JsonIgnore
    private Long id;

    @NotBlank(message = "O nome é obrigatório")
    private String name;

    private String phone;

    private String age;

    private String city;

    private String state;

    @NotNull(message = "O score é obrigatório")
    @PositiveOrZero(message = "O score deve ser um valor positivo")
    private Long score;

    private String region;

    @JsonProperty("nome")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("telefone")
    public void setPhone(String phone) {
        this.phone = phone;
    }

    @JsonProperty("idade")
    public void setAge(String age) {
        this.age = age;
    }

    @JsonProperty("cidade")
    public void setCity(String city) {
        this.city = city;
    }

    @JsonProperty("estado")
    public void setState(String state) {
        this.state = state;
    }

    @JsonProperty("regiao")
    public void setRegion(String region) {
        this.region = region;
    }

    public PersonDTO(PersonModel personModel) {
        this.name = personModel.getName();
        this.phone = personModel.getPhone();
        this.age = personModel.getAge();
        this.city = personModel.getCity();
        this.state = personModel.getState();
        this.score = personModel.getScore();
        this.region = personModel.getRegion();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PersonDTO personDTO = (PersonDTO) o;
        return Objects.equals(id, personDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }
}
