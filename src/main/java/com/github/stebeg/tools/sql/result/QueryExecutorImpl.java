package com.github.stebeg.tools.sql.result;

import com.github.stebeg.tools.sql.query.Select;
import com.github.stebeg.tools.sql.query.Update;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Steffen Berger
 */
class QueryExecutorImpl implements QueryExecutor {

    private final ResultSetScanner resultSetScanner;
    private final StatementFactory statementFactory;

    QueryExecutorImpl(
            ResultSetScanner resultSetScanner,
            StatementFactory statementFactory) {
        this.resultSetScanner = resultSetScanner;
        this.statementFactory = statementFactory;
    }

    @Override
    public <T> SelectResult<T> runQuery(
            final Connection connection,
            final Select query,
            final QueryResultItemFactory<T> itemFactory)
            throws SQLException, NullPointerException, IllegalStateException {
        Preconditions.checkNotNull(connection);
        Preconditions.checkNotNull(query);
        Preconditions.checkNotNull(query.getQueryString());
        Preconditions.checkNotNull(itemFactory);

        Preconditions.checkState(connection.isClosed() == false);
        Preconditions.checkState(connection.isValid(1));

        final PreparedStatement statement = this.statementFactory
                .createPreparedStatement(connection, query);
        final ResultSet resultSet = statement.executeQuery();
        final SelectResult<T> result = this.resultSetScanner.scanResultSet(
                resultSet, itemFactory, query.getSize(), query.getOffset());
        statement.clearParameters();
        return result;
    }

    @Override
    public UpdateResult runUpdate(
            final Connection connection,
            final Update query) throws SQLException {
        return runUpdate(connection, query, false);
    }

    @Override
    public UpdateResult runLargeUpdate(
            final Connection connection,
            final Update query) throws SQLException {
        return runUpdate(connection, query, true);
    }

    private UpdateResult runUpdate(
            final Connection connection,
            final Update query,
            final boolean isLarge) throws SQLException {
        Preconditions.checkNotNull(connection);
        Preconditions.checkNotNull(query);
        Preconditions.checkNotNull(query.getQueryString());

        Preconditions.checkState(connection.isClosed() == false);
        Preconditions.checkState(connection.isValid(1));

        final PreparedStatement statement = this.statementFactory
                .createPreparedStatement(connection, query);

        final List<Long> generatedKeys = Lists.newArrayList();
        final long numberOfAffectedRows = isLarge
                ? statement.executeLargeUpdate()
                : statement.executeUpdate();
        if (query.isGenerateKey()) {
            final ResultSet resultSet = statement.getGeneratedKeys();
            while (resultSet.next()) {
                generatedKeys.add(resultSet.getLong(1));
            }
        }
        final UpdateResult result
                = generatedKeys.isEmpty()
                ? new UpdateResult(numberOfAffectedRows)
                : new UpdateResult(numberOfAffectedRows, generatedKeys);
        statement.clearParameters();

        return result;
    }

}
