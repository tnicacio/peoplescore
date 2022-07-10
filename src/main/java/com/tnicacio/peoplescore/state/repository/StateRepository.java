package com.tnicacio.peoplescore.state.repository;

import com.tnicacio.peoplescore.state.model.StateModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateRepository extends JpaRepository<StateModel, Long> {
}
