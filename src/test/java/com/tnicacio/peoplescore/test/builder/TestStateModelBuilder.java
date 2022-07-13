package com.tnicacio.peoplescore.test.builder;

import com.tnicacio.peoplescore.affinity.model.AffinityModel;
import com.tnicacio.peoplescore.state.model.StateModel;
import com.tnicacio.peoplescore.test.util.TestRandomUtils;
import org.apache.commons.lang3.RandomUtils;

import java.util.List;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestStateModelBuilder {

    public static TestStateModelBuilder builder() {
        return new TestStateModelBuilder();
    }

    public static ListBuilder listBuilder() {
        return new ListBuilder();
    }

    private StateModel entity;
    private UnaryOperator<StateModel> stateModelPersistenceFunction;

    private TestStateModelBuilder() {
        entity = TestRandomUtils.randomObject(StateModel.class);
    }

    public TestStateModelBuilder withStateModelPersistenceFunction(UnaryOperator<StateModel> stateModelPersistenceFunction) {
        this.stateModelPersistenceFunction = stateModelPersistenceFunction;
        return this;
    }

    public TestStateModelBuilder withAffinity(AffinityModel affinityModel) {
        this.entity.setAffinity(affinityModel);
        return this;
    }

    public StateModel build() {
        if (this.stateModelPersistenceFunction != null) {
            this.entity.setId(null);
            this.entity = this.stateModelPersistenceFunction.apply(this.entity);
        }
        return entity;
    }

    public static class ListBuilder {

        private int size;
        private AffinityModel affinityModel;
        private UnaryOperator<StateModel> stateModelPersistenceFunction;

        private ListBuilder() {
        }

        public ListBuilder withSize(int size) {
            this.size = size;
            return this;
        }

        public ListBuilder withAffinity(AffinityModel affinityModel) {
            this.affinityModel = affinityModel;
            return this;
        }

        public ListBuilder withStateModelPersistenceFunction(UnaryOperator<StateModel> stateModelPersistenceFunction) {
            this.stateModelPersistenceFunction = stateModelPersistenceFunction;
            return this;
        }

        public List<StateModel> buildList() {
            return Stream.generate(this::build)
                    .limit(size > 0 ? size : RandomUtils.nextInt(1, 10))
                    .collect(Collectors.toList());
        }

        private StateModel build() {
            TestStateModelBuilder builder = new TestStateModelBuilder();
            if (this.affinityModel != null) {
                builder.withAffinity(this.affinityModel);
            }
            return builder
                    .withStateModelPersistenceFunction(this.stateModelPersistenceFunction)
                    .build();
        }
    }
}
