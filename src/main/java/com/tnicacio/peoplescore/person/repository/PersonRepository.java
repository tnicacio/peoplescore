package com.tnicacio.peoplescore.person.repository;

import com.tnicacio.peoplescore.person.model.PersonModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<PersonModel, Long>, PersonRepositoryCustom {
}
