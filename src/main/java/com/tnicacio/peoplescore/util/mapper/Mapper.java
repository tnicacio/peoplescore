package com.tnicacio.peoplescore.util.mapper;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public interface Mapper<T, U> {

    /**
     * Realiza o mapeamento de um objeto para outro, ou seja, faz a cópia dos atributos de um objeto e coloca em um
     * novo objeto.
     *
     * @param object objeto que terá seus atributos copiados, o objeto pode ser nulo.
     * @return o objeto resultante com os valores copiados do objeto de origem.
     */
    default U mapNullable(@Nullable T object) {
        if (object == null) {
            return null;
        } else {
            return mapNonNull(object);
        }
    }

    /**
     * Realiza o mapeamento de um objeto para outro, ou seja, faz a cópia dos atributos de um objeto e coloca em um
     * novo objeto.
     *
     * @param object objeto que terá seus atributos copiados, o objeto não pode ser nulo.
     * @return o objeto resultante com os valores copiados do objeto de origem.
     */
    U mapNonNull(@NonNull T object);

    /**
     * Mapeia uma lista de objetos para outra lista de objetos de outro tipo.
     *
     * @param collectionOfObjects lista de objetos originais.
     * @return uma lista contendo os objetos que foram o resultado do mapeamento.
     */
    default List<U> mapList(@Nullable List<T> collectionOfObjects) {
        if (CollectionUtils.isEmpty(collectionOfObjects)) {
            return new ArrayList<>();
        } else {
            return collectionOfObjects.stream()
                    .map(this::mapNullable)
                    .collect(Collectors.toList());
        }
    }

}
