package com.github.stebeg.tools.sql.result;

import com.google.common.collect.Lists;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Steffen Berger
 */
class ResultSetScannerImpl implements ResultSetScanner {

    private final ResultSetColumnReader resultSetColumnReader;

    ResultSetScannerImpl(ResultSetColumnReader resultSetColumnReader) {
        this.resultSetColumnReader = resultSetColumnReader;
    }

    @Override
    public <T> SelectResult<T> scanResultSet(
            final ResultSet resultSet,
            final QueryResultItemFactory<T> itemFactory,
            final long size,
            final long offset) throws SQLException {
        final List<T> rows = Lists.newLinkedList();
        final List<ResultSetColumn> columns = this.resultSetColumnReader
                .readColumns(resultSet);

        long affectedRows = 0;
        int index = 0, count = 0;

        while (resultSet.next()) {
            if (index >= offset && count < size) {
                rows.add(itemFactory.build(resultSet, columns));
                count++;
            }
            index++;
            affectedRows++;
        }
        resultSet.close();
        return new SelectResult<>(affectedRows, rows);
    }

}
