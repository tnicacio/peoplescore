package com.tnicacio.peoplescore.affinity.service;

import com.tnicacio.peoplescore.affinity.dto.AffinityDTO;
import com.tnicacio.peoplescore.affinity.model.AffinityModel;
import com.tnicacio.peoplescore.affinity.repository.AffinityRepository;
import com.tnicacio.peoplescore.util.converter.Converter;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.tnicacio.peoplescore.config.caching.CachingConfig.AFFINITY_REGION_CACHE;

@Service
@Setter(onMethod_ = @Autowired)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AffinityService {

    AffinityRepository affinityRepository;
    Converter<AffinityModel, AffinityDTO> affinityConverter;

    @Qualifier(AFFINITY_REGION_CACHE)
    CacheManager cacheManager;

    @Transactional
    public AffinityDTO insert(AffinityDTO affinityDTO) {
        final AffinityModel affinityModel = affinityConverter.toModel(affinityDTO);
        affinityRepository.save(affinityModel);
        evictCacheIfRegionPresent(affinityModel.getRegion());
        return affinityConverter.toDTO(affinityModel);
    }

    public boolean regionValid(String region) {
        return !affinityRepository.existsByRegion(region);
    }

    private void evictCacheIfRegionPresent(String region) {
        final Cache affinityRegionCache = cacheManager.getCache(AFFINITY_REGION_CACHE);
        if (affinityRegionCache != null) {
            affinityRegionCache.evictIfPresent(region);
        }
    }

}
