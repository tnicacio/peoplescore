package com.tnicacio.peoplescore.person.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.tnicacio.peoplescore.person.dto.PersonDTO;
import com.tnicacio.peoplescore.person.service.PersonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Api(tags = "Pessoa")
@RestController
@RequestMapping(value = "/pessoa")
@Setter(onMethod_ = @Autowired)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PersonController {

    PersonService personService;

    @ApiModelProperty(hidden = true)
    @ApiOperation(value = "Insere uma pessoa")
    @PostMapping(produces = "application/json", consumes = "application/json")
    @JsonView(PersonDTO.PersonView.RegistrationPost.class)
    public ResponseEntity<PersonDTO> insert(@RequestBody @Validated(PersonDTO.PersonView.RegistrationPost.class)
                                            @JsonView(PersonDTO.PersonView.RegistrationPost.class) PersonDTO dto) {
        dto = personService.insert(dto);
        final URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @ApiOperation(value = "Retorna uma lista de pessoas")
    @GetMapping(produces = "application/json")
    @JsonView(PersonDTO.PersonView.ListGet.class)
    public ResponseEntity<List<PersonDTO>> findAll() {
        final List<PersonDTO> list = personService.findAll();
        return ResponseEntity.ok().body(list);
    }

    @ApiOperation(value = "Retorna a pessoa pelo seu identificador")
    @GetMapping(value = "/{id}", produces = "application/json")
    @JsonView(PersonDTO.PersonView.DetailsGet.class)
    public ResponseEntity<PersonDTO> findById(@PathVariable Long id) {
        final PersonDTO personDTO = personService.findById(id);
        return ResponseEntity.ok().body(personDTO);
    }
}
