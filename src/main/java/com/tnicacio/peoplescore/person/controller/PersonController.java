package com.tnicacio.peoplescore.person.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.tnicacio.peoplescore.person.dto.PersonDTO;
import com.tnicacio.peoplescore.person.service.PersonService;
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

@RestController
@RequestMapping(value = "/pessoa")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping
    @JsonView(PersonDTO.PersonView.RegistrationPost.class)
    public ResponseEntity<PersonDTO> insert(@RequestBody @Validated(PersonDTO.PersonView.RegistrationPost.class)
                                            @JsonView(PersonDTO.PersonView.RegistrationPost.class) PersonDTO dto) {
        dto = personService.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @GetMapping(value = "/{id}")
    @JsonView(PersonDTO.PersonView.DetailsGet.class)
    public ResponseEntity<PersonDTO> findById(@PathVariable Long id) {
        var personDTO = personService.findById(id);
        return ResponseEntity.ok().body(personDTO);
    }

}
