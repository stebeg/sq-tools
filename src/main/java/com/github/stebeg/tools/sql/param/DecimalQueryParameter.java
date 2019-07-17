package com.github.stebeg.tools.sql.param;

import com.github.stebeg.tools.sql.common.SignType;
import com.google.common.base.Preconditions;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Steffen Berger
 */
public final class DecimalQueryParameter
        extends AbstractNumericQueryParameter<BigDecimal> {

    private final BigDecimal value;
    private final int precision;
    private final int scale;

    public DecimalQueryParameter(
            final BigDecimal value,
            final SignType signType,
            final int precision,
            final int scale) {
        super(value, signType);
        this.value = value;
        this.precision = precision;
        this.scale = scale;
        checkPreconditions(getValue(), signType);
    }

    @Override
    public BigDecimal getValue() {
        if (value != null && this.scale < this.value.scale()) {
            return this.value.setScale(this.scale, RoundingMode.HALF_UP);
        }
        return this.value;
    }

    @Override
    public void setValue(
            final PreparedStatement preparedStatement,
            final int index) throws SQLException {
        preparedStatement.setBigDecimal(index, getValue());
    }

    private void checkPreconditions(
            final BigDecimal value,
            final SignType signType) {
        if (value != null) {
            if (signType == SignType.UNSIGNED) {
                Preconditions.checkArgument(value.compareTo(BigDecimal.ZERO) >= 0);
            }
            Preconditions.checkArgument(this.precision <= 65);
            Preconditions.checkArgument(this.scale >= 0);
            Preconditions.checkArgument(value.precision() <= this.precision);
            Preconditions.checkArgument(value.scale() <= this.scale);
        }
    }

}
