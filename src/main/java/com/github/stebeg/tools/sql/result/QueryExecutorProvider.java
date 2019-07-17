package com.github.stebeg.tools.sql.result;

public class QueryExecutorProvider {

    private static final QueryExecutor INSTANCE = new QueryExecutorImpl(
            new ResultSetScannerImpl(
                    new ResultSetColumnReaderImpl()),
            new StatementFactoryImpl());

    private QueryExecutorProvider() {
        throw new UnsupportedOperationException();
    }

    public static QueryExecutor getInstance() {
        return INSTANCE;
    }
}
