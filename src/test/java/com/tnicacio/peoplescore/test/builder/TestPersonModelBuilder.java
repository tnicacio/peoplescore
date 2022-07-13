package com.tnicacio.peoplescore.test.builder;

import com.tnicacio.peoplescore.person.model.PersonModel;
import com.tnicacio.peoplescore.test.util.TestRandomUtils;

import java.util.function.UnaryOperator;

public class TestPersonModelBuilder {

    public static TestPersonModelBuilder builder() {
        return new TestPersonModelBuilder();
    }

    private PersonModel entity;
    private UnaryOperator<PersonModel> personModelPersistenceFunction;

    private TestPersonModelBuilder() {
        entity = TestRandomUtils.randomObject(PersonModel.class);
    }

    public TestPersonModelBuilder withPersonModelPersistenceFunction(UnaryOperator<PersonModel> personModelPersistenceFunction) {
        this.personModelPersistenceFunction = personModelPersistenceFunction;
        return this;
    }

    public TestPersonModelBuilder withScore(Long score) {
        this.entity.setScore(score);
        return this;
    }

    public TestPersonModelBuilder withName(String name) {
        this.entity.setName(name);
        return this;
    }

    public PersonModel build() {
        if (this.personModelPersistenceFunction != null) {
            this.entity.setId(null);
            this.entity = this.personModelPersistenceFunction.apply(this.entity);
        }
        return entity;
    }
}
