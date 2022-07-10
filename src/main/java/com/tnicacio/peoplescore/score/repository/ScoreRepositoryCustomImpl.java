package com.tnicacio.peoplescore.score.repository;

import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.Optional;

import static com.tnicacio.peoplescore.score.model.QScoreModel.scoreModel;

@Component
public class ScoreRepositoryCustomImpl implements ScoreRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<String> findScoreDescriptionByScore(Long score) {
        return new JPAQuery<>(entityManager)
                .select(scoreModel.description)
                .from(scoreModel)
                .where(scoreModel.initialScore.loe(score)
                        .and(scoreModel.finalScore.goe(score)))
                .limit(1)
                .fetch()
                .stream()
                .findFirst();
    }
}
