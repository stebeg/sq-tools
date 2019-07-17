package com.github.stebeg.tools.sql.param;

import com.github.stebeg.tools.sql.common.SignType;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

/**
 * @author Steffen Berger
 */
public final class IntQueryParameter extends AbstractNumericQueryParameter<Long> {

    public static final Map<SignType, Long> MIN_VALUES = ImmutableMap.of(
            SignType.SIGNED, -2147483648L,
            SignType.UNSIGNED, 0L);
    public static final Map<SignType, Long> MAX_VALUES = ImmutableMap.of(
            SignType.SIGNED, 2147483647L,
            SignType.UNSIGNED, 4294967295L);

    public IntQueryParameter(
            final Long value,
            final SignType signType) {
        super(value, signType);
        checkPrecondition(value, signType);
    }

    @Override
    public void setValue(
            final PreparedStatement preparedStatement,
            final int index) throws SQLException {
        preparedStatement.setLong(index, getValue());
    }

    private void checkPrecondition(
            final Long value,
            final SignType signType) {
        if (value != null) {
            Preconditions.checkArgument(value >= MIN_VALUES.get(signType));
            Preconditions.checkArgument(value <= MAX_VALUES.get(signType));
        }
    }

}
