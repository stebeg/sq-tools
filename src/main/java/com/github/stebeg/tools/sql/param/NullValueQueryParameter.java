package com.github.stebeg.tools.sql.param;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Steffen Berger
 */
public final class NullValueQueryParameter extends AbstractQueryParameter<Object> {

    public NullValueQueryParameter() {
        super(null);
    }

    @Override
    public void setValue(
            final PreparedStatement preparedStatement,
            final int index) throws SQLException {
        preparedStatement.setNull(index, java.sql.Types.NULL);
    }

}
