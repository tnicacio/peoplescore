package com.tnicacio.peoplescore.state.service;

import com.tnicacio.peoplescore.state.repository.StateRepository;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import static com.tnicacio.peoplescore.config.caching.CachingConfig.AFFINITY_REGION_CACHE;

@Service
@Setter(onMethod_ = @Autowired)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StateService {

    StateRepository stateRepository;

    @Cacheable(AFFINITY_REGION_CACHE)
    @Transactional(readOnly = true)
    public List<String> findStateAbbreviationListByRegion(String region) {
        return stateRepository.findStateAbbreviationListByRegion(region);
    }

    public boolean stateAbbreviationsValid(Set<String> abbreviations) {
        return !stateRepository.existsByAbbreviationIn(abbreviations);
    }

    public boolean existsByAbbreviation(String abbreviation) {
        return stateRepository.existsByAbbreviation(abbreviation);
    }
}
