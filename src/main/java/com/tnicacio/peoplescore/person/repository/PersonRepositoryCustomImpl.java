package com.tnicacio.peoplescore.person.repository;

import com.tnicacio.peoplescore.common.repository.CommonRepository;
import com.tnicacio.peoplescore.person.dto.PersonDTO;

import java.util.List;
import java.util.stream.Collectors;

import static com.tnicacio.peoplescore.person.model.QPersonModel.personModel;
import static com.tnicacio.peoplescore.score.model.QScoreModel.scoreModel;

public class PersonRepositoryCustomImpl extends CommonRepository implements PersonRepositoryCustom {

    @Override
    public List<PersonDTO> findPeopleBasicData() {
        return super.select(
                        personModel.id,
                        personModel.name,
                        personModel.city,
                        personModel.state,
                        personModel.region,
                        scoreModel.description
                )
                .from(personModel)
                .join(scoreModel).on(scoreModel.initialScore.loe(personModel.score)
                        .and(scoreModel.finalScore.goe(personModel.score)))
                .orderBy(personModel.name.asc())
                .fetch()
                .stream()
                .map(tuple -> PersonDTO.builder()
                        .id(tuple.get(personModel.id))
                        .name(tuple.get(personModel.name))
                        .city(tuple.get(personModel.city))
                        .state(tuple.get(personModel.state))
                        .region(tuple.get(personModel.region))
                        .scoreDescription(tuple.get(scoreModel.description))
                        .build())
                .collect(Collectors.toList());
    }
}
