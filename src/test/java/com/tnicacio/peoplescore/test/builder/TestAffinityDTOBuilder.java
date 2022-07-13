package com.tnicacio.peoplescore.test.builder;

import com.tnicacio.peoplescore.affinity.dto.AffinityDTO;
import com.tnicacio.peoplescore.test.util.TestRandomUtils;
import org.apache.commons.lang3.RandomUtils;

import java.util.Set;

public class TestAffinityDTOBuilder {

    public static TestAffinityDTOBuilder builder() {
        return new TestAffinityDTOBuilder();
    }

    private AffinityDTO dto;

    private TestAffinityDTOBuilder() {
        dto = TestRandomUtils.randomObject(AffinityDTO.class);
        dto.getStates().addAll(TestRandomUtils.randomList(String.class, RandomUtils.nextInt(1, 10)));
    }

    public TestAffinityDTOBuilder withStates(Set<String> stateAbbreviations) {
        dto.getStates().clear();
        dto.getStates().addAll(stateAbbreviations);
        return this;
    }

    public AffinityDTO build() {
        return dto;
    }
}
