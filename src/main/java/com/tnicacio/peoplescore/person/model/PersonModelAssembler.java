package com.tnicacio.peoplescore.person.model;

import com.tnicacio.peoplescore.person.controller.PersonController;
import com.tnicacio.peoplescore.person.dto.PersonDTO;
import com.tnicacio.peoplescore.util.converter.Converter;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@Setter(onMethod_ = @Autowired)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PersonModelAssembler implements RepresentationModelAssembler<PersonModel, PersonDTO> {

    Converter<PersonModel, PersonDTO> personConverter;

    @Override
    public PersonDTO toModel(PersonModel entity) {
        PersonDTO personDTO = personConverter.toDTO(entity);
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
