package com.github.stebeg.tools.sql.result;

import com.github.stebeg.tools.sql.common.SignType;

/**
 * @author Steffen Berger
 */
public class ResultSetColumn {

    private final String name;
    private final int sqlType;
    private final SignType signType;
    private final Integer precision;
    private final Integer scale;

    public ResultSetColumn(
            final String name,
            final int sqlType) {
        this.name = name;
        this.sqlType = sqlType;
        this.signType = null;
        this.precision = null;
        this.scale = null;
    }

    public ResultSetColumn(
            final String name,
            final int sqlType,
            final int precision) {
        this.name = name;
        this.sqlType = sqlType;
        this.signType = SignType.SIGNED;
        this.precision = precision;
        this.scale = null;
    }

    public ResultSetColumn(
            final String name,
            final int sqlType,
            final SignType signType) {
        this.name = name;
        this.sqlType = sqlType;
        this.signType = signType;
        this.precision = null;
        this.scale = null;
    }

    public ResultSetColumn(
            final String name,
            final int sqlType,
            final SignType signType,
            final int precision,
            final int scale) {
        this.name = name;
        this.sqlType = sqlType;
        this.signType = signType;
        this.precision = precision;
        this.scale = scale;
    }

    public String getName() {
        return this.name;
    }

    public int getSqlType() {
        return this.sqlType;
    }

    public SignType getSignType() {
        return this.signType;
    }

    public Integer getPrecision() {
        return this.precision;
    }

    public Integer getScale() {
        return this.scale;
    }

}
