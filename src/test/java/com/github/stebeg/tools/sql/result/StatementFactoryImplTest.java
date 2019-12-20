package com.github.stebeg.tools.sql.result;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.github.stebeg.tools.sql.param.QueryParameter;
import com.github.stebeg.tools.sql.query.Select;
import com.github.stebeg.tools.sql.query.Update;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import org.junit.jupiter.api.Test;

public class StatementFactoryImplTest {

    private final StatementFactoryImpl instance;

    public StatementFactoryImplTest() {
        this.instance = new StatementFactoryImpl();
    }

    @Test
    public void createPreparedStatement_Select() throws Exception {
        final Connection connectionMock = mock(Connection.class);
        final Select query = new Select("SELECT");

        final QueryParameter firstParameterMock = mock(QueryParameter.class);
        query.getParameterList().add(firstParameterMock);

        final QueryParameter secondParameterMock = mock(QueryParameter.class);
        query.getParameterList().add(secondParameterMock);

        final PreparedStatement expResult = mock(PreparedStatement.class);
        when(connectionMock.prepareStatement(query.getQueryString())).thenReturn(expResult);

        final PreparedStatement result = this.instance.createPreparedStatement(connectionMock, query);
        assertEquals(expResult, result);

        verify(firstParameterMock).setValue(result, 1);
        verify(secondParameterMock).setValue(result, 2);
    }

    @Test
    public void createPreparedStatement_Update() throws Exception {
        final Connection connectionMock = mock(Connection.class);
        final Update query = new Update("UPDATE", true);

        final QueryParameter firstParameterMock = mock(QueryParameter.class);
        when(firstParameterMock.getValue()).thenReturn(mock(Object.class));
        query.getParameterList().add(firstParameterMock);

        final QueryParameter secondParameterMock = mock(QueryParameter.class);
        when(secondParameterMock.getValue()).thenReturn(null);
        query.getParameterList().add(secondParameterMock);

        final PreparedStatement expResult = mock(PreparedStatement.class);
        when(connectionMock
            .prepareStatement(query.getQueryString(), Statement.RETURN_GENERATED_KEYS))
            .thenReturn(expResult);

        final PreparedStatement result = this.instance
            .createPreparedStatement(connectionMock, query);
        assertEquals(expResult, result);

        verify(firstParameterMock).setValue(result, 1);
        verify(secondParameterMock, never()).setValue(result, 2);
        verify(expResult).setNull(2, java.sql.Types.NULL);
    }

    @Test
    public void createPreparedStatement_Update_NoGeneratedKeys() throws Exception {
        final Connection connectionMock = mock(Connection.class);
        final Update query = new Update("UPDATE", false);

        final QueryParameter firstParameterMock = mock(QueryParameter.class);
        when(firstParameterMock.getValue()).thenReturn(mock(Object.class));
        query.getParameterList().add(firstParameterMock);

        final QueryParameter secondParameterMock = mock(QueryParameter.class);
        when(secondParameterMock.getValue()).thenReturn(null);
        query.getParameterList().add(secondParameterMock);

        final QueryParameter thirdParameterMock = mock(QueryParameter.class);
        when(thirdParameterMock.getValue()).thenReturn(mock(Object.class));
        query.getParameterList().add(thirdParameterMock);

        final PreparedStatement expResult = mock(PreparedStatement.class);
        when(connectionMock.prepareStatement(query.getQueryString(), Statement.NO_GENERATED_KEYS))
            .thenReturn(expResult);

        final PreparedStatement result = this.instance
            .createPreparedStatement(connectionMock, query);
        assertEquals(expResult, result);

        verify(firstParameterMock).setValue(result, 1);
        verify(secondParameterMock, never()).setValue(result, 2);
        verify(thirdParameterMock).setValue(result, 3);
        verify(expResult).setNull(2, java.sql.Types.NULL);
    }

}
