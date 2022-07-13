package com.tnicacio.peoplescore.test.builder;

import com.tnicacio.peoplescore.affinity.model.AffinityModel;
import com.tnicacio.peoplescore.test.util.TestRandomUtils;

import java.util.function.UnaryOperator;

public class TestAffinityModelBuilder {

    public static TestAffinityModelBuilder builder() {
        return new TestAffinityModelBuilder();
    }

    private AffinityModel entity;
    private UnaryOperator<AffinityModel> affinityModelPersistenceFunction;

    private TestAffinityModelBuilder() {
        entity = TestRandomUtils.randomObject(AffinityModel.class);
    }

    public TestAffinityModelBuilder withRegion(String region) {
        entity.setRegion(region);
        return this;
    }

    public TestAffinityModelBuilder withAffinityPersistenceFunction(UnaryOperator<AffinityModel> affinityModelPersistenceFunction) {
        this.affinityModelPersistenceFunction = affinityModelPersistenceFunction;
        return this;
    }

    public AffinityModel build() {
        if (this.affinityModelPersistenceFunction != null) {
            this.entity.setId(null);
            this.entity = this.affinityModelPersistenceFunction.apply(this.entity);
        }
        return entity;
    }
}
