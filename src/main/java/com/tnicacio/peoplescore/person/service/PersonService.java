package com.tnicacio.peoplescore.person.service;

import com.tnicacio.peoplescore.affinity.service.AffinityService;
import com.tnicacio.peoplescore.exception.service.ResourceNotFoundException;
import com.tnicacio.peoplescore.person.dto.PersonDTO;
import com.tnicacio.peoplescore.person.model.PersonModel;
import com.tnicacio.peoplescore.person.repository.PersonRepository;
import com.tnicacio.peoplescore.score.service.ScoreService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final ScoreService scoreService;
    private final AffinityService affinityService;

    public PersonService(PersonRepository personRepository, ScoreService scoreService, AffinityService affinityService) {
        this.personRepository = personRepository;
        this.scoreService = scoreService;
        this.affinityService = affinityService;
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
        PersonModel personModel = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found"));
        String scoreDescription = scoreService.findScoreDescription(personModel.getScore());

        PersonDTO personDTO = new PersonDTO(personModel);
        personDTO.setScoreDescription(scoreDescription);
        return personDTO;
    }
}
