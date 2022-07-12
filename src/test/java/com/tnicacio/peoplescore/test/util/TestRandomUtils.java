package com.tnicacio.peoplescore.test.util;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.RandomUtils;
import org.jeasy.random.EasyRandom;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class TestRandomUtils {

    private final EasyRandom EASY_RANDOM = new EasyRandom();

    /**
     * Dado um array de itens, retorna um dos itens pertencentes a ele. Utiliza a implementação de
     * {@link RandomUtils#nextInt(int, int)} para a escolha aleatória do item.
     *
     * @param items array de itens.
     * @param <T>   tipo dos itens.
     * @return um item aleatório pertencente ao array de itens.
     * @see #chooseRandom(List)
     */
    @SafeVarargs
    public <T> T chooseRandom(T... items) {
        return items[RandomUtils.nextInt(0, items.length)];
    }

    /**
     * Dada uma lista de itens, retorna um dos itens pertencentes a ele. Utiliza a implementação de
     * {@link RandomUtils#nextInt(int, int)} para a escolha aleatória do item.
     *
     * @param items lista de itens.
     * @param <T>   tipo dos itens.
     * @return um item aleatório pertencente à lista de itens.
     * @see #chooseRandom(Object[])
     */
    public <T> T chooseRandom(List<T> items) {
        return items.get(RandomUtils.nextInt(0, items.size()));
    }

    /**
     * Retorna um objeto populado com dados aleatórios, de classe do tipo passado como parâmetro. Utiliza a
     * implementação de {@link EasyRandom#nextObject(Class)} para a geração aleatória de dados.
     *
     * @param type tipo da classe do objeto a ser gerado.
     * @param <T>  tipo do objeto.
     * @return classe do tipo {@link T}.
     */
    public <T> T randomObject(Class<T> type) {
        return EASY_RANDOM.nextObject(type);
    }

    /**
     * Retorna um enum com constantes aleatórias dentre as constantes da enumeração passada como parâmetro. Utiliza a
     * implementação de {@link EasyRandom#nextObject(Class)} para a geração aleatória de dados.
     *
     * @param type classe da enumeração a ser gerada.
     * @param <T>  tipo da enumeração.
     * @return enumeração do tipo {@link T}.
     */
    public <T extends Enum<T>> T randomEnum(Class<T> type) {
        return EASY_RANDOM.nextObject(type);
    }


    /**
     * Retorna uma lista de tamanho definido no parâmetro <em>size</em>, populada com classes do tipo <em>classType</em>
     * com dados aleatórios. Utiliza a implementação de {@link EasyRandom#objects(Class, int)} para a geração
     * aleatória de
     * dados. Lança a exceção {@link IllegalArgumentException} caso se tente gerar uma lista com tamanho negativo.
     *
     * @param classType classe dos objetos
     * @param size      tamanho da lista
     * @param <T>       tipo dos objetos
     * @return a lista de tamanho definido, contendo objetos gerados com dados aleatórios.
     */
    public <T> List<T> randomList(Class<T> classType, int size) {
        return EASY_RANDOM.objects(classType, size)
                .collect(Collectors.toList());
    }

}
