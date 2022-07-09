package com.tnicacio.peoplescore.person.service;

import com.tnicacio.peoplescore.exception.service.ResourceNotFoundException;
import com.tnicacio.peoplescore.person.dto.PersonDTO;
import com.tnicacio.peoplescore.person.model.PersonModel;
import com.tnicacio.peoplescore.person.repository.PersonRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Transactional
    public PersonDTO insert(PersonDTO personDTO) {
        PersonModel personModel = new PersonModel();
        BeanUtils.copyProperties(personDTO, personModel);
        personRepository.save(personModel);
        return new PersonDTO(personModel);
    }

    @Transactional(readOnly = true)
    public PersonDTO findById(Long id) {
        return personRepository.findById(id)
                .map(PersonDTO::new)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found"));
    }
}
