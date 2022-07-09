package com.tnicacio.peoplescore.score.service;

import com.tnicacio.peoplescore.score.dto.ScoreDTO;
import com.tnicacio.peoplescore.score.model.ScoreModel;
import com.tnicacio.peoplescore.score.repository.ScoreRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ScoreService {

    private final ScoreRepository scoreRepository;

    public ScoreService(ScoreRepository scoreRepository) {
        this.scoreRepository = scoreRepository;
    }

    @Transactional
    public ScoreDTO insert(ScoreDTO scoreDTO) {
        ScoreModel scoreModel = new ScoreModel();
        BeanUtils.copyProperties(scoreDTO, scoreModel);
        scoreRepository.save(scoreModel);
        return new ScoreDTO(scoreModel);
    }
}
