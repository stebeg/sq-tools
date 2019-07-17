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
public final class TinyIntQueryParameter
        extends AbstractNumericQueryParameter<Integer> {

    public static final Map<SignType, Integer> MIN_VALUES = ImmutableMap.of(
            SignType.SIGNED, -128,
            SignType.UNSIGNED, 0);
    public static final Map<SignType, Integer> MAX_VALUES = ImmutableMap.of(
            SignType.SIGNED, 127,
            SignType.UNSIGNED, 255);

    public TinyIntQueryParameter(
            final Integer value,
            final SignType signType) {
        super(value, signType);
        checkPrecondition(value, signType);
    }

    @Override
    public void setValue(
            final PreparedStatement preparedStatement,
            final int index) throws SQLException {
        preparedStatement.setInt(index, getValue());
    }

    private void checkPrecondition(
            final Integer value,
            final SignType signType) {
        if (value != null) {
            Preconditions.checkArgument(value >= MIN_VALUES.get(signType));
            Preconditions.checkArgument(value <= MAX_VALUES.get(signType));
        }
    }

}
