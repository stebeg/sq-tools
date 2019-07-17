package com.github.stebeg.tools.sql.param;

import com.github.stebeg.tools.sql.common.SignType;

/**
 * @param <T>
 * @author Steffen Berger
 */
public abstract class AbstractNumericQueryParameter<T>
        extends AbstractQueryParameter<T> {

    private final SignType signType;

    public AbstractNumericQueryParameter(T value, SignType signType) {
        super(value);
        this.signType = signType;
    }

    protected SignType getSignType() {
        return this.signType;
    }

}
