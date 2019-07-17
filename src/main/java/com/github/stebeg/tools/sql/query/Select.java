package com.github.stebeg.tools.sql.query;

import com.github.stebeg.tools.sql.result.ResultSetOptions;

/**
 * @author Steffen Berger
 */
public class Select extends AbstractQuery {

    private long size = ResultSetOptions.MAX_SIZE.getValue();
    private long offset = ResultSetOptions.NO_OFFSET.getValue();

    public Select(
            final String queryString) {
        super(queryString);
    }

    public Select(
            final String queryString,
            final long size,
            final long offset) {
        super(queryString);
        this.size = size;
        this.offset = offset;
    }

    public long getSize() {
        return this.size;
    }

    public long getOffset() {
        return this.offset;
    }
}
