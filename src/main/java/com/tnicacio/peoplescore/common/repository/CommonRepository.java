package com.tnicacio.peoplescore.common.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Expression;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommonRepository {

    @PersistenceContext
    EntityManager entityManager;

    protected <T> JPAQuery<T> select(Expression<T> expression) {
        return new JPAQuery<>(entityManager).select(expression);
    }

    protected JPAQuery<Tuple> select(Expression<?>... expressions) {
        return new JPAQuery<>(entityManager).select(expressions);
    }

}

