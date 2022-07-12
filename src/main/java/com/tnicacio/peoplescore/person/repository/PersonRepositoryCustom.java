package com.tnicacio.peoplescore.person.repository;

import com.tnicacio.peoplescore.person.dto.PersonDTO;

import java.util.List;

public interface PersonRepositoryCustom {

    List<PersonDTO> findPeopleBasicData();
}
