package com.github.stebeg.tools.sql.result;

import java.util.List;

/**
 * @param <T>
 * @author Steffen Berger
 */
public class SelectResult<T> implements QueryResult {

    private final long numberOfTotalRows;
    private final List<T> rows;

    SelectResult(
            final List<T> rows) {
        this.numberOfTotalRows = rows.size();
        this.rows = rows;
    }

    SelectResult(
            final long numberOfTotalRows,
            final List<T> rows) {
        this.numberOfTotalRows = numberOfTotalRows;
        this.rows = rows;
    }

    public long getNumberOfTotalRows() {
        return this.numberOfTotalRows;
    }

    public List<T> getRows() {
        return this.rows;
    }
}
