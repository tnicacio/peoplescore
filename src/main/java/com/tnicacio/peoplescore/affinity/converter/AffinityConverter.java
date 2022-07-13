package com.tnicacio.peoplescore.affinity.converter;

import com.tnicacio.peoplescore.affinity.dto.AffinityDTO;
import com.tnicacio.peoplescore.affinity.model.AffinityModel;
import com.tnicacio.peoplescore.state.model.StateModel;
import com.tnicacio.peoplescore.util.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AffinityConverter implements Converter<AffinityModel, AffinityDTO> {

    @Override
    public AffinityModel toModel(@NonNull AffinityDTO dto) {
        final AffinityModel affinityModel = new AffinityModel();
        final Set<StateModel> states = dto.getStates().stream()
                .map(abbreviation -> {
                    final StateModel stateModel = new StateModel();
                    stateModel.setAbbreviation(abbreviation);
                    stateModel.setAffinity(affinityModel);
                    return stateModel;
                })
                .collect(Collectors.toUnmodifiableSet());
        affinityModel.setId(dto.getId());
        affinityModel.setRegion(dto.getRegion());
        affinityModel.getStates().addAll(states);
        return affinityModel;
    }

    @Override
    public AffinityDTO toDTO(@NonNull AffinityModel model) {
        AffinityDTO affinityDTO = new AffinityDTO();
        affinityDTO.setId(model.getId());
        affinityDTO.setRegion(model.getRegion());
        model.getStates().stream()
                .map(StateModel::getAbbreviation)
                .forEach(affinityDTO.getStates()::add);
        return affinityDTO;
    }
}
