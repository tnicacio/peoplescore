package com.tnicacio.peoplescore.score.converter;

import com.tnicacio.peoplescore.score.dto.ScoreDTO;
import com.tnicacio.peoplescore.score.model.ScoreModel;
import com.tnicacio.peoplescore.util.converter.Converter;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Setter(onMethod_ = @Autowired)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ScoreConverter implements Converter<ScoreModel, ScoreDTO> {

    @Override
    public ScoreModel toModel(ScoreDTO dto) {
        final ScoreModel model = new ScoreModel();
        BeanUtils.copyProperties(dto, model);
        return model;
    }

    @Override
    public ScoreDTO toDTO(ScoreModel model) {
        final ScoreDTO dto = new ScoreDTO();
        BeanUtils.copyProperties(model, dto);
        return dto;
    }
}
