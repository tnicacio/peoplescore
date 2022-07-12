package com.tnicacio.peoplescore.state.repository;

import java.util.List;

public interface StateRepositoryCustom {

    List<String> findStateAbbreviationListByRegion(String region);
}
