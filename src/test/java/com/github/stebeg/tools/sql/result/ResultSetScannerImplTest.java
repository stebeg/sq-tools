package com.github.stebeg.tools.sql.result;

import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Steffen Berger
 */
@SuppressWarnings({"unchecked"})
public class ResultSetScannerImplTest {

    private final ResultSetColumnReader resultSetColumnReaderMock;
    private final ResultSetScanner instance;

    public ResultSetScannerImplTest() {
        this.resultSetColumnReaderMock = mock(ResultSetColumnReader.class);
        this.instance = new ResultSetScannerImpl(this.resultSetColumnReaderMock);
    }

    @Test
    public void testScanResultSet() throws Exception {
        final ResultSet resultSetMock = mock(ResultSet.class);
        final QueryResultItemFactory<Object> itemFactoryMock = mock(QueryResultItemFactory.class);
        final long size = ResultSetOptions.MAX_SIZE.getValue();
        final long offset = ResultSetOptions.NO_OFFSET.getValue();

        final List<ResultSetColumn> columns = mock(List.class);
        when(this.resultSetColumnReaderMock.readColumns(resultSetMock))
                .thenReturn(columns);

        final Object object1 = new Object(), object2 = new Object();
        when(resultSetMock.next()).thenReturn(true, true, false);
        when(itemFactoryMock.build(resultSetMock, columns))
                .thenReturn(object1, object2);

        final int expNumberOfTotalRows = 2;
        final SelectResult<Object> result = this.instance.scanResultSet(
                resultSetMock, itemFactoryMock, size, offset);
        assertEquals(expNumberOfTotalRows, result.getNumberOfTotalRows());
        assertEquals(expNumberOfTotalRows, result.getRows().size());

        assertTrue(result.getRows().contains(object1));
        assertTrue(result.getRows().contains(object2));
    }

    @Test
    public void testScanResultSet_WithSizeAndOffset() throws Exception {
        final ResultSet resultSetMock = mock(ResultSet.class);
        final QueryResultItemFactory<Object> itemFactoryMock = mock(QueryResultItemFactory.class);
        final long size = 2;
        final long offset = 1;

        final List<ResultSetColumn> columns = mock(List.class);
        when(this.resultSetColumnReaderMock.readColumns(resultSetMock))
                .thenReturn(columns);

        final Object object1 = new Object(), object2 = new Object();
        when(resultSetMock.next()).thenReturn(true, true, true, true, true, false);
        when(itemFactoryMock.build(resultSetMock, columns)).thenReturn(object1, object2);

        final int expNumberOfRows = 2;
        final int expNumberOfTotalRows = 5;
        final SelectResult<Object> result = this.instance.scanResultSet(
                resultSetMock, itemFactoryMock, size, offset);
        assertEquals(expNumberOfTotalRows, result.getNumberOfTotalRows());
        assertEquals(expNumberOfRows, result.getRows().size());

        assertTrue(result.getRows().contains(object1));
        assertTrue(result.getRows().contains(object2));
    }

}
