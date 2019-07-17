package com.github.stebeg.tools.sql.result;

/**
 * @author Steffen Berger
 */
public enum ResultSetOptions {

    MAX_SIZE(Long.MAX_VALUE),
    NO_OFFSET(0);

    private final long value;

    ResultSetOptions(final long newValue) {
        this.value = newValue;
    }

    public long getValue() {
        return this.value;
    }
}
