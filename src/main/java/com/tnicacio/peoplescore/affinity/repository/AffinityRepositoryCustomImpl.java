package com.tnicacio.peoplescore.affinity.repository;

import com.tnicacio.peoplescore.common.repository.CommonRepository;
import com.tnicacio.peoplescore.state.dto.StateDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static com.tnicacio.peoplescore.affinity.model.QAffinityModel.affinityModel;

@Component
public class AffinityRepositoryCustomImpl extends CommonRepository implements AffinityRepositoryCustom {

    @Override
    public List<StateDTO> findStatesByRegion(String region) {
        return super.select(affinityModel.states)
                .from(affinityModel)
                .where(affinityModel.region.eq(region))
                .fetchFirst()
                .stream()
                .map(tuple -> StateDTO.builder()
                        .id(tuple.getId())
                        .abbreviation(tuple.getAbbreviation())
                        .build())
                .collect(Collectors.toList());
    }
}
