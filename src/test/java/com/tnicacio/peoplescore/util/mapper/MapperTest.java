package com.tnicacio.peoplescore.util.mapper;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@FieldDefaults(level = AccessLevel.PRIVATE)
class MapperTest {

    MapperDefaultImplTest mapperDefaultImplTest;

    @BeforeEach
    public void setUp() {
        mapperDefaultImplTest = new MapperDefaultImplTest();
    }

    @Test
    void mapListShouldReturnEmptyListWhenArgumentIsNull() {
        List<DummyObject> result = mapperDefaultImplTest.mapList(null);
        assertThat(result).isNotNull().isEmpty();
    }

    @Test
    void mapListShouldReturnEmptyListWhenArgumentIsEmptyList() {
        List<DummyObject> result = mapperDefaultImplTest.mapList(Collections.emptyList());
        assertThat(result).isNotNull().isEmpty();
    }

    @Test
    void mapListShouldMapAllObjectsInCollection() {
        int size = Math.abs(new Random().nextInt(10));
        List<DummyObject> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(new DummyObject());
        }
        List<DummyObject> result = mapperDefaultImplTest.mapList(list);
        assertThat(result).isNotNull().hasSize(size);
    }

    @Test
    void mapNullableShouldReturnNullWhenValueIsNull() {
        DummyObject result = mapperDefaultImplTest.mapNullable(null);
        assertThat(result).isNull();
    }

    @Test
    void mapNullableShouldReturnMapNonNullResultWhenArgumentIsNotNull() {
        DummyObject dummy = mock(DummyObject.class);
        DummyObject result = mapperDefaultImplTest.mapNullable(dummy);
        assertThat(result).isNotSameAs(dummy);
        verify(dummy).dummyMethod();
    }

    private static class MapperDefaultImplTest implements Mapper<DummyObject, DummyObject> {

        @Override
        public DummyObject mapNonNull(@NonNull DummyObject dummyObject) {
            dummyObject.dummyMethod();
            return new DummyObject();
        }

    }

    private static class DummyObject {

        DummyObject() {
        }

        void dummyMethod() {
        }
    }
}
