package com.tnicacio.peoplescore.test.builder;

import com.tnicacio.peoplescore.score.model.ScoreModel;
import com.tnicacio.peoplescore.test.util.TestRandomUtils;

import java.util.function.UnaryOperator;

public class TestScoreModelBuilder {

    public static TestScoreModelBuilder builder() {
        return new TestScoreModelBuilder();
    }

    private ScoreModel entity;
    private UnaryOperator<ScoreModel> scoreModelPersistenceFunction;

    private TestScoreModelBuilder() {
        entity = TestRandomUtils.randomObject(ScoreModel.class);
    }

    public TestScoreModelBuilder withDescription(String description) {
        entity.setDescription(description);
        return this;
    }

    public TestScoreModelBuilder withInitialScore(Long initialScore) {
        entity.setInitialScore(initialScore);
        return this;
    }

    public TestScoreModelBuilder withFinalScore(Long finalScore) {
        entity.setFinalScore(finalScore);
        return this;
    }

    public TestScoreModelBuilder withScoreModelPersistenceFunction(UnaryOperator<ScoreModel> scoreModelPersistenceFunction) {
        this.scoreModelPersistenceFunction = scoreModelPersistenceFunction;
        return this;
    }

    public ScoreModel build() {
        if (this.scoreModelPersistenceFunction != null) {
            this.entity.setId(null);
            this.entity = this.scoreModelPersistenceFunction.apply(this.entity);
        }
        return entity;
    }
}
