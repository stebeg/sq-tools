package com.github.stebeg.tools.sql.result;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class QueryExecutorProviderTest {

    public QueryExecutorProviderTest() {
    }

    @Test
    public void getInstance() {
        final QueryExecutor queryExecutor1 = QueryExecutorProvider.getInstance();
        final QueryExecutor queryExecutor2 = QueryExecutorProvider.getInstance();

        assertTrue(queryExecutor1 instanceof QueryExecutorImpl);
        assertEquals(queryExecutor1, queryExecutor2);
    }
}
