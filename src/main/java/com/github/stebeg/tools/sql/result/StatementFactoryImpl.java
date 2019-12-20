package com.github.stebeg.tools.sql.result;

import com.github.stebeg.tools.sql.param.QueryParameter;
import com.github.stebeg.tools.sql.query.Select;
import com.github.stebeg.tools.sql.query.Update;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * TODO add Cache for PreparedStatements
 *
 * @author Steffen Berger
 */
class StatementFactoryImpl implements StatementFactory {

    StatementFactoryImpl() {
    }

    @Override
    public PreparedStatement createPreparedStatement(
            final Connection connection,
            final Select query) throws SQLException {
        final PreparedStatement statement = connection
                .prepareStatement(query.getQueryString());
        int index = 1;
        for (QueryParameter parameter : query.getParameterList()) {
            parameter.setValue(statement, index);
            index++;
        }
        return statement;
    }

    @Override
    public PreparedStatement createPreparedStatement(
            final Connection connection,
            final Update query) throws SQLException {
        final int generateKey
                = query.isGenerateKey()
                ? Statement.RETURN_GENERATED_KEYS
                : Statement.NO_GENERATED_KEYS;
        final PreparedStatement statement = connection
                .prepareStatement(query.getQueryString(), generateKey);
        int index = 1;
        for (QueryParameter parameter : query.getParameterList()) {
            if (parameter.getValue() == null) {
                statement.setNull(index, java.sql.Types.NULL);
            } else {
                parameter.setValue(statement, index);
            }
            index++;
        }
        return statement;
    }

}
