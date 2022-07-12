package com.tnicacio.peoplescore.util.converter;

import org.springframework.lang.NonNull;

public interface Converter<T, U> {

    T toModel(@NonNull U dto);

    U toDTO(@NonNull T model);
}
