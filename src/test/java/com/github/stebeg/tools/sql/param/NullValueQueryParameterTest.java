package com.github.stebeg.tools.sql.param;

import org.junit.jupiter.api.Test;

import java.sql.PreparedStatement;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * @author Steffen Berger
 */
public class NullValueQueryParameterTest {

    public NullValueQueryParameterTest() {
    }

    @Test
    public void testGetValue() {
        final NullValueQueryParameter instance = new NullValueQueryParameter();
        assertNull(instance.getValue());
    }

    @Test
    public void testSetValue() throws Exception {
        final int index = 5;
        final PreparedStatement preparedStatementMock = mock(PreparedStatement.class);
        final NullValueQueryParameter instance = new NullValueQueryParameter();

        instance.setValue(preparedStatementMock, index);
        verify(preparedStatementMock).setNull(index, java.sql.Types.NULL);
    }

}
