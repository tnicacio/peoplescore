package com.tnicacio.peoplescore.affinity.service;

import com.tnicacio.peoplescore.affinity.dto.AffinityDTO;
import com.tnicacio.peoplescore.affinity.model.AffinityModel;
import com.tnicacio.peoplescore.affinity.repository.AffinityRepository;
import com.tnicacio.peoplescore.test.util.TestRandomUtils;
import com.tnicacio.peoplescore.util.converter.Converter;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.concurrent.Callable;

import static com.tnicacio.peoplescore.config.caching.CachingConfig.AFFINITY_REGION_CACHE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class AffinityServiceTest {

    @Nested
    @FieldDefaults(level = AccessLevel.PRIVATE)
    class InsertTest {

        @InjectMocks
        AffinityService affinityService;

        @Mock
        AffinityRepository affinityRepository;
        @Mock
        Converter<AffinityModel, AffinityDTO> affinityConverter;
        @Mock
        @Qualifier(AFFINITY_REGION_CACHE)
        CacheManager cacheManager;

        @Captor
        ArgumentCaptor<String> cacheKey;

        @BeforeEach
        void setUp() {
            openMocks(this);
        }

        @Test
        void shouldSaveAndEvictCacheWhenRegionAlreadyCached() {
            final AffinityDTO affinityDTOBeforeSave = TestRandomUtils.randomObject(AffinityDTO.class);
            final AffinityModel affinityModel = TestRandomUtils.randomObject(AffinityModel.class);
            final AffinityDTO affinityDTOAfterSave = TestRandomUtils.randomObject(AffinityDTO.class);
            final Cache affinityRegionCache = mock(DummyCacheImpl.class);

            when(affinityConverter.toModel(affinityDTOBeforeSave)).thenReturn(affinityModel);
            when(affinityConverter.toDTO(affinityModel)).thenReturn(affinityDTOAfterSave);
            when(affinityRepository.save(affinityModel)).thenReturn(affinityModel);
            when(cacheManager.getCache(AFFINITY_REGION_CACHE)).thenReturn(affinityRegionCache);

            final AffinityDTO result = affinityService.insert(affinityDTOBeforeSave);

            verify(affinityRegionCache).evictIfPresent(cacheKey.capture());

            assertThat(result).isEqualTo(affinityDTOAfterSave);
            assertThat(cacheKey.getValue()).isEqualTo(affinityModel.getRegion());
        }

        @Test
        void shouldSaveAndDoNotEvictCacheWhenRegionIsNotCached() {
            final AffinityDTO affinityDTOBeforeSave = TestRandomUtils.randomObject(AffinityDTO.class);
            final AffinityModel affinityModel = TestRandomUtils.randomObject(AffinityModel.class);
            final AffinityDTO affinityDTOAfterSave = TestRandomUtils.randomObject(AffinityDTO.class);
            final Cache affinityRegionCache = mock(DummyCacheImpl.class);

            when(affinityConverter.toModel(affinityDTOBeforeSave)).thenReturn(affinityModel);
            when(affinityConverter.toDTO(affinityModel)).thenReturn(affinityDTOAfterSave);
            when(affinityRepository.save(affinityModel)).thenReturn(affinityModel);
            when(cacheManager.getCache(AFFINITY_REGION_CACHE)).thenReturn(null);

            final AffinityDTO result = affinityService.insert(affinityDTOBeforeSave);

            verify(affinityRegionCache, never()).evictIfPresent(anyString());

            assertThat(result).isEqualTo(affinityDTOAfterSave);
        }

        private class DummyCacheImpl implements Cache {

            @Override
            public String getName() {
                return null;
            }

            @Override
            public Object getNativeCache() {
                return null;
            }

            @Override
            public ValueWrapper get(Object key) {
                return null;
            }

            @Override
            public <T> T get(Object key, Class<T> type) {
                return null;
            }

            @Override
            public <T> T get(Object key, Callable<T> valueLoader) {
                return null;
            }

            @Override
            public void put(Object key, Object value) {
            }

            @Override
            public void evict(Object key) {
            }

            @Override
            public void clear() {
            }
        }
    }

    @Nested
    @FieldDefaults(level = AccessLevel.PRIVATE)
    class ValidateRegionTest {

        @InjectMocks
        AffinityService affinityService;

        @Mock
        AffinityRepository affinityRepository;

        @BeforeEach
        void setUp() {
            openMocks(this);
        }

        @Test
        void shouldCallRepositoryExistsByRegionAndReturnTrueIfDoesNotExist() {
            final String nonPersistedRegion = "nonPersistedRegion";

            when(affinityRepository.existsByRegion(nonPersistedRegion)).thenReturn(false);

            final boolean result = affinityService.regionValid(nonPersistedRegion);

            assertThat(result).isTrue();
        }

        @Test
        void shouldCallRepositoryExistsByRegionAndReturnFalseIfExists() {
            final String persistedRegion = "persistedRegion";

            when(affinityRepository.existsByRegion(persistedRegion)).thenReturn(true);

            final boolean result = affinityService.regionValid(persistedRegion);

            assertThat(result).isFalse();
        }
    }

}