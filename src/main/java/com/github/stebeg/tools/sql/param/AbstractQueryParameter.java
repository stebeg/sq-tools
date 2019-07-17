package com.github.stebeg.tools.sql.param;

/**
 * @param <T>
 * @author Steffen Berger
 */
abstract class AbstractQueryParameter<T> implements QueryParameter<T> {

    private final T value;

    public AbstractQueryParameter(T value) {
        this.value = value;
    }

    @Override
    public T getValue() {
        return this.value;
    }

}
