package com.tnicacio.peoplescore.affinity.service;

import com.tnicacio.peoplescore.affinity.dto.AffinityDTO;
import com.tnicacio.peoplescore.affinity.model.AffinityModel;
import com.tnicacio.peoplescore.affinity.repository.AffinityRepository;
import com.tnicacio.peoplescore.state.model.StateModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AffinityService {

    private final AffinityRepository affinityRepository;

    public AffinityService(AffinityRepository affinityRepository) {
        this.affinityRepository = affinityRepository;
    }

    @Transactional
    public AffinityDTO insert(AffinityDTO affinityDTO) {
        final AffinityModel affinityModel = new AffinityModel();
        final Set<StateModel> states = affinityDTO.getStates().stream()
                .map(abbreviation -> {
                    StateModel stateModel = new StateModel();
                    stateModel.setAbbreviation(abbreviation);
                    stateModel.setAffinity(affinityModel);
                    return stateModel;
                })
                .collect(Collectors.toUnmodifiableSet());
        affinityModel.setId(affinityDTO.getId());
        affinityModel.setRegion(affinityDTO.getRegion());
        affinityModel.getStates().addAll(states);

        affinityRepository.save(affinityModel);
        return new AffinityDTO(affinityModel);
    }
}
