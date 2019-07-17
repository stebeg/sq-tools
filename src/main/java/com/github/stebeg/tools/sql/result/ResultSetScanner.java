package com.github.stebeg.tools.sql.result;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Steffen Berger
 */
interface ResultSetScanner {

    <T> SelectResult<T> scanResultSet(
            final ResultSet resultSet,
            final QueryResultItemFactory<T> queryResultItemFactory,
            final long size,
            final long offset) throws SQLException;
}
