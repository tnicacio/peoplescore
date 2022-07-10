package com.tnicacio.peoplescore.affinity.repository;

import com.tnicacio.peoplescore.state.dto.StateDTO;

import java.util.List;

public interface AffinityRepositoryCustom {

    List<StateDTO> findStatesByRegion(String region);
}
