package com.tnicacio.peoplescore.person.model;

import com.tnicacio.peoplescore.person.controller.PersonController;
import com.tnicacio.peoplescore.person.dto.PersonDTO;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PersonModelAssembler implements RepresentationModelAssembler<PersonModel, PersonDTO> {

    @Override
    public PersonDTO toModel(PersonModel entity) {
        PersonDTO personDTO = new PersonDTO(entity);
        personDTO.add(linkTo(methodOn(PersonController.class).findById(personDTO.getId())).withSelfRel());
        return personDTO;
    }

    @Override
    public CollectionModel<PersonDTO> toCollectionModel(Iterable<? extends PersonModel> entities) {
        Iterator<PersonDTO> personDTOIterator = StreamSupport.stream(entities.spliterator(), true)
                .map(this::toModel)
                .iterator();
        return CollectionModel.of(() -> personDTOIterator);
    }
}
