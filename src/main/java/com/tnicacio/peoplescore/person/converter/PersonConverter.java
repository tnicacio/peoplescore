package com.tnicacio.peoplescore.person.converter;

import com.tnicacio.peoplescore.person.dto.PersonDTO;
import com.tnicacio.peoplescore.person.model.PersonModel;
import com.tnicacio.peoplescore.util.converter.Converter;
import org.springframework.beans.BeanUtils;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class PersonConverter implements Converter<PersonModel, PersonDTO> {

    @Override
    public PersonModel toModel(@NonNull PersonDTO dto) {
        final PersonModel personModel = new PersonModel();
        BeanUtils.copyProperties(dto, personModel);
        return personModel;
    }

    @Override
    public PersonDTO toDTO(@NonNull PersonModel model) {
        final PersonDTO dto = new PersonDTO();
        BeanUtils.copyProperties(model, dto);
        return dto;
    }

}
