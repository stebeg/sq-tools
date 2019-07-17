package com.github.stebeg.tools.sql.result;

import com.github.stebeg.tools.sql.common.SignType;
import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Steffen Berger
 */
public class ResultSetColumnReaderImplTest {

    private final ResultSetColumnReader instance;

    public ResultSetColumnReaderImplTest() {
        this.instance = new ResultSetColumnReaderImpl();
    }

    @Test
    public void testReadColumns() throws Exception {
        final ResultSet mockedResultSet = mock(ResultSet.class);
        final ResultSetMetaData mockedResultSetMetaData = mock(ResultSetMetaData.class);
        final int expResultSize = 2;

        when(mockedResultSet.getMetaData()).thenReturn(mockedResultSetMetaData);
        when(mockedResultSetMetaData.getColumnCount()).thenReturn(expResultSize);

        final ResultSetColumn expResultSetColumn1 = new ResultSetColumn(
                "value", java.sql.Types.DECIMAL, SignType.SIGNED, 10, 3);
        when(mockedResultSetMetaData.getColumnLabel(1))
                .thenReturn(expResultSetColumn1.getName());
        when(mockedResultSetMetaData.getColumnType(1))
                .thenReturn(expResultSetColumn1.getSqlType());
        when(mockedResultSetMetaData.isSigned(1))
                .thenReturn(expResultSetColumn1.getSignType() == SignType.SIGNED);
        when(mockedResultSetMetaData.getPrecision(1))
                .thenReturn(expResultSetColumn1.getPrecision());
        when(mockedResultSetMetaData.getScale(1))
                .thenReturn(expResultSetColumn1.getScale());

        final ResultSetColumn expResultSetColumn2 = new ResultSetColumn(
                "value", java.sql.Types.VARCHAR, SignType.UNSIGNED, 25, 0);
        when(mockedResultSetMetaData.getColumnLabel(2))
                .thenReturn(expResultSetColumn2.getName());
        when(mockedResultSetMetaData.getColumnType(2))
                .thenReturn(expResultSetColumn2.getSqlType());
        when(mockedResultSetMetaData.isSigned(2))
                .thenReturn(expResultSetColumn2.getSignType() == SignType.SIGNED);
        when(mockedResultSetMetaData.getPrecision(2))
                .thenReturn(expResultSetColumn2.getPrecision());
        when(mockedResultSetMetaData.getScale(2))
                .thenReturn(expResultSetColumn2.getScale());

        final List<ResultSetColumn> expResultList = ImmutableList
                .of(expResultSetColumn1, expResultSetColumn2);
        final List<ResultSetColumn> result = this.instance.readColumns(mockedResultSet);
        assertEquals(expResultSize, result.size());

        int index = 0;
        for (final ResultSetColumn resultSetColumn : result) {
            final ResultSetColumn expResult = expResultList.get(index);
            assertEquals(expResult.getName(), resultSetColumn.getName());
            assertEquals(expResult.getSqlType(), resultSetColumn.getSqlType());
            assertEquals(expResult.getSignType(), resultSetColumn.getSignType());
            assertEquals(expResult.getPrecision(), resultSetColumn.getPrecision());
            assertEquals(expResult.getScale(), resultSetColumn.getScale());
            index++;
        }
    }

}
