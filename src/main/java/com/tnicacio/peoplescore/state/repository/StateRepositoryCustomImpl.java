package com.tnicacio.peoplescore.state.repository;

import com.tnicacio.peoplescore.common.repository.CommonRepository;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.tnicacio.peoplescore.state.model.QStateModel.stateModel;

@Component
public class StateRepositoryCustomImpl extends CommonRepository implements StateRepositoryCustom {

    @Override
    public List<String> findStateAbbreviationListByRegion(String region) {
        return super.select(stateModel.abbreviation)
                .from(stateModel)
                .where(stateModel.affinity.region.eq(region))
                .fetch();
    }
}
