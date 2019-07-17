package com.github.stebeg.tools.sql.param;

import com.github.stebeg.tools.sql.common.SignType;
import com.google.common.base.Preconditions;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Steffen Berger
 */
public final class SimpleDecimalQueryParameter
        extends AbstractNumericQueryParameter<Double> {

    public SimpleDecimalQueryParameter(
            final Double value,
            final SignType signType) {
        super(value, signType);
        checkPreconditions(value, signType);
    }

    @Override
    public void setValue(
            final PreparedStatement preparedStatement,
            final int index) throws SQLException {
        preparedStatement.setDouble(index, getValue());
    }

    private void checkPreconditions(
            final Double value,
            final SignType signType) {
        if (signType == SignType.UNSIGNED) {
            Preconditions.checkArgument(value >= 0.0);
        }
    }
}
