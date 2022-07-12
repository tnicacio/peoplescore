package com.tnicacio.peoplescore.person.service;

import com.tnicacio.peoplescore.exception.ResourceNotFoundException;
import com.tnicacio.peoplescore.exception.factory.ExceptionFactory;
import com.tnicacio.peoplescore.person.dto.PersonDTO;
import com.tnicacio.peoplescore.person.model.PersonModel;
import com.tnicacio.peoplescore.person.repository.PersonRepository;
import com.tnicacio.peoplescore.score.service.ScoreService;
import com.tnicacio.peoplescore.state.service.StateService;
import com.tnicacio.peoplescore.util.converter.Converter;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Setter(onMethod_ = @Autowired)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PersonService {

    Converter<PersonModel, PersonDTO> personConverter;
    ExceptionFactory exceptionFactory;
    PersonRepository personRepository;
    ScoreService scoreService;
    StateService stateService;

    @Transactional
    public PersonDTO insert(PersonDTO personDTO) {
        PersonModel personModel = personConverter.toModel(personDTO);
        personRepository.save(personModel);
        return personConverter.toDTO(personModel);
    }

    @Transactional(readOnly = true)
    public PersonDTO findById(Long id) {
        PersonModel personModel = personRepository.findById(id)
                .orElseThrow(() -> exceptionFactory.notFound("Pessoa não encontrada"));

        PersonDTO personDTO = personConverter.toDTO(personModel);
        findScoreDescriptionByScoreValueAndSetToPersonDTO(personModel.getScore(), personDTO);
        findStatesByPersonRegionAndSetToPersonDTO(personModel.getRegion(), personDTO);
        return personDTO;
    }

    @Transactional(readOnly = true)
    public List<PersonDTO> findAll() {
        final List<PersonDTO> peopleBasicDataList = personRepository.findPeopleBasicData();
        if (peopleBasicDataList.isEmpty()) {
            throw exceptionFactory.notFound("Nenhuma pessoa foi encontrada");
        }
        return peopleBasicDataList.stream()
                .map(person -> findStatesByPersonRegionAndSetToPersonDTO(person.getRegion(), person))
                .collect(Collectors.toList());
    }

    private void findScoreDescriptionByScoreValueAndSetToPersonDTO(Long scoreValue, PersonDTO personDTO) {
        String scoreDescription = scoreService.findScoreDescription(scoreValue);
        personDTO.setScoreDescription(scoreDescription);
    }

    private PersonDTO findStatesByPersonRegionAndSetToPersonDTO(String personRegion, PersonDTO personDTO) {
        List<String> stateList = stateService.findStateAbbreviationListByRegion(personRegion);
        personDTO.getAffinityStates().addAll(stateList);
        return personDTO;
    }

}
