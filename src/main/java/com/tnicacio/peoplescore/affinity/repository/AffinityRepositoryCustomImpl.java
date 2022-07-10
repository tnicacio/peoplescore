package com.tnicacio.peoplescore.affinity.repository;

import com.tnicacio.peoplescore.common.repository.CommonRepository;
import com.tnicacio.peoplescore.state.dto.StateDTO;

import java.util.List;

public class AffinityRepositoryCustomImpl extends CommonRepository implements AffinityRepositoryCustom {

    @Override
    public List<StateDTO> findStatesByRegion(String region) {
        return null;
    }
}
