package com.tnicacio.peoplescore.person.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.HashSet;
import java.util.Set;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonDTO {

    public interface PersonView {
        interface RegistrationPost {}
        interface DetailsGet {}
        interface ListGet {}
    }

    @JsonIgnore
    private Long id;

    @NotBlank(message = "{validation.person_name_not_blank}", groups = PersonView.RegistrationPost.class)
    @JsonView({PersonView.DetailsGet.class, PersonView.RegistrationPost.class, PersonView.ListGet.class})
    private String name;

    @JsonView({PersonView.DetailsGet.class, PersonView.RegistrationPost.class})
    private String phone;

    @JsonView({PersonView.DetailsGet.class, PersonView.RegistrationPost.class})
    private String age;

    @JsonView({PersonView.RegistrationPost.class, PersonView.ListGet.class})
    private String city;

    @JsonView({PersonView.RegistrationPost.class, PersonView.ListGet.class})
    private String state;

    @NotNull(message = "{validation.person_score_not_null}", groups = PersonView.RegistrationPost.class)
    @PositiveOrZero(message = "{validation.person_score_positive_or_zero}",
            groups = PersonView.RegistrationPost.class)
    @JsonView(PersonView.RegistrationPost.class)
    private Long score;

    @JsonView(PersonView.RegistrationPost.class)
    private String region;

    @JsonProperty(value = "scoreDescricao", access = JsonProperty.Access.READ_ONLY)
    @JsonView({PersonView.DetailsGet.class, PersonView.ListGet.class})
    private String scoreDescription;

    @JsonProperty(value = "estados", access = JsonProperty.Access.READ_ONLY)
    @JsonView({PersonView.DetailsGet.class, PersonView.ListGet.class})
    private final Set<String> affinityStates = new HashSet<>();

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

}
