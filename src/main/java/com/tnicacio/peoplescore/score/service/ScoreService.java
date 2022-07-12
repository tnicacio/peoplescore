package com.tnicacio.peoplescore.score.service;

import com.tnicacio.peoplescore.exception.factory.ExceptionFactory;
import com.tnicacio.peoplescore.score.dto.ScoreDTO;
import com.tnicacio.peoplescore.score.model.ScoreModel;
import com.tnicacio.peoplescore.score.repository.ScoreRepository;
import com.tnicacio.peoplescore.util.converter.Converter;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Setter(onMethod_ = @Autowired)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ScoreService {

    Converter<ScoreModel, ScoreDTO> scoreConverter;
    ExceptionFactory exceptionFactory;
    ScoreRepository scoreRepository;

    @Transactional
    public ScoreDTO insert(ScoreDTO scoreDTO) {
        ScoreModel scoreModel = scoreConverter.toModel(scoreDTO);
        scoreRepository.save(scoreModel);
        return scoreConverter.toDTO(scoreModel);
    }

    @Transactional(readOnly = true)
    public String findScoreDescription(Long score) {
        return scoreRepository.findScoreDescriptionByScore(score)
                .orElseThrow(() -> exceptionFactory.notFound("Descrição não encontrada para o score " + score));
    }
}
