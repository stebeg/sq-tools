package com.github.stebeg.tools.sql.result;

import com.github.stebeg.tools.sql.query.Select;
import com.github.stebeg.tools.sql.query.Update;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Interface zum Ausfuehren von SQL-Statements
 *
 * @author Steffen Berger
 */
public interface QueryExecutor {

    <T> SelectResult<T> runQuery(
            final Connection connection,
            final Select query,
            final QueryResultItemFactory<T> itemFactory) throws SQLException;

    UpdateResult runUpdate(
            final Connection connection,
            final Update query) throws SQLException;

    UpdateResult runLargeUpdate(
            final Connection connection,
            final Update query) throws SQLException;

}
