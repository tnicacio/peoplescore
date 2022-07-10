package com.tnicacio.peoplescore.score.repository;

import com.tnicacio.peoplescore.common.repository.CommonRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.tnicacio.peoplescore.score.model.QScoreModel.scoreModel;

@Component
public class ScoreRepositoryCustomImpl extends CommonRepository implements ScoreRepositoryCustom {

    @Override
    public Optional<String> findScoreDescriptionByScore(Long score) {
        return super.select(scoreModel.description)
                .from(scoreModel)
                .where(scoreModel.initialScore.loe(score)
                        .and(scoreModel.finalScore.goe(score)))
                .limit(1)
                .fetch()
                .stream()
                .findFirst();
    }
}
