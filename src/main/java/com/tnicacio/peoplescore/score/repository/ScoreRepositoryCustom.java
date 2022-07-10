package com.tnicacio.peoplescore.score.repository;

import java.util.Optional;

public interface ScoreRepositoryCustom {

    Optional<String> findScoreDescriptionByScore(Long score);
}
