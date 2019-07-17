package com.github.stebeg.tools.sql.result;

import com.github.stebeg.tools.sql.query.Select;
import com.github.stebeg.tools.sql.query.Update;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Steffen Berger
 */
interface StatementFactory {

    PreparedStatement createPreparedStatement(
            final Connection connection,
            final Select query) throws SQLException;

    PreparedStatement createPreparedStatement(
            final Connection connection,
            final Update query) throws SQLException;

}
