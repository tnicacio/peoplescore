package com.tnicacio.peoplescore.state.repository;

import com.tnicacio.peoplescore.common.repository.CommonRepository;

import java.util.List;

import static com.tnicacio.peoplescore.state.model.QStateModel.stateModel;

public class StateRepositoryCustomImpl extends CommonRepository implements StateRepositoryCustom {

    @Override
    public List<String> findStateAbbreviationListByRegion(String region) {
        return super.select(stateModel.abbreviation)
                .from(stateModel)
                .where(stateModel.affinity.region.eq(region))
                .fetch();
    }
}
