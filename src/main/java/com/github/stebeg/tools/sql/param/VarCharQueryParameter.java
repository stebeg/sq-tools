package com.github.stebeg.tools.sql.param;

import com.google.common.base.Preconditions;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Steffen Berger
 */
public class VarCharQueryParameter extends AbstractQueryParameter<String> {

    private static final String LIKE_PLACE_HOLDER = "%";
    private final int maxLength;
    private final boolean addLikeSuffix;

    public VarCharQueryParameter(
            final String value,
            final int maxLegth) {
        super(value);
        this.maxLength = maxLegth;
        this.addLikeSuffix = false;
        checkPreconditions(value);
    }

    public VarCharQueryParameter(
            final String value,
            final int maxLength,
            final boolean addLikeSuffix) {
        super(value);
        this.maxLength = maxLength;
        this.addLikeSuffix = addLikeSuffix;
        checkPreconditions(value);
    }

    public VarCharQueryParameter(
            final String format,
            final int maxLength,
            final Object... args) {
        super(String.format(format, args));
        this.maxLength = maxLength;
        this.addLikeSuffix = false;
        checkPreconditions(String.format(format, args));
    }

    private void checkPreconditions(String value) {
        if (value != null) {
            Preconditions.checkArgument(value.length() <= this.maxLength);
        }
    }

    @Override
    public String getValue() {
        final String value = super.getValue();
        if (this.addLikeSuffix) {
            return (value != null) ? value + LIKE_PLACE_HOLDER : LIKE_PLACE_HOLDER;
        }
        return value;
    }

    @Override
    public void setValue(
            final PreparedStatement preparedStatement,
            final int index) throws SQLException {
        preparedStatement.setString(index, getValue());
    }

    public int getMaxLength() {
        return this.maxLength;
    }

    public boolean isAddLikeSuffix() {
        return this.addLikeSuffix;
    }

}
