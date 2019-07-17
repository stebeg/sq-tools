package com.github.stebeg.tools.sql.param;

import com.github.stebeg.tools.sql.common.SignType;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

/**
 * @author Steffen Berger
 */
public final class BigIntQueryParameter
        extends AbstractNumericQueryParameter<BigInteger> {

    public static final int SQL_TYPE = java.sql.Types.BIGINT;

    public static final Map<SignType, BigInteger> MIN_VALUES = ImmutableMap.of(
            SignType.SIGNED, new BigInteger("-9223372036854775808"),
            SignType.UNSIGNED, BigInteger.ZERO);
    public static final Map<SignType, BigInteger> MAX_VALUES = ImmutableMap.of(
            SignType.SIGNED, new BigInteger("9223372036854775807"),
            SignType.UNSIGNED, new BigInteger("18446744073709551615"));

    public BigIntQueryParameter(
            final BigInteger value,
            final SignType signType) {
        super(value, signType);
        checkPreconditions(value, signType);
    }

    @Override
    public void setValue(
            final PreparedStatement preparedStatement,
            final int index) throws SQLException {
        preparedStatement.setObject(index, getValue(), SQL_TYPE);
    }

    private void checkPreconditions(
            final BigInteger value,
            final SignType signType) {
        if (value != null) {
            Preconditions.checkArgument(value.compareTo(MIN_VALUES.get(signType)) >= 0);
            Preconditions.checkArgument(value.compareTo(MAX_VALUES.get(signType)) <= 0);
        }
    }
}
