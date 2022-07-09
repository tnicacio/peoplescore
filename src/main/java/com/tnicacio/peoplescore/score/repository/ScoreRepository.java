package com.tnicacio.peoplescore.score.repository;

import com.tnicacio.peoplescore.score.model.ScoreModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScoreRepository extends JpaRepository<ScoreModel, Long> {

}
