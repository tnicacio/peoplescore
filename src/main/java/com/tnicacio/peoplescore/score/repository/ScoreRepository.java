package com.tnicacio.peoplescore.score.repository;

import com.tnicacio.peoplescore.score.model.ScoreModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ScoreRepository extends JpaRepository<ScoreModel, Long> {

    @Query("SELECT s.descricao FROM ScoreModel s WHERE s.inicial <= ")
    String findDescription(Long score);
}
