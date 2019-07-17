package com.github.stebeg.tools.sql.result;

import com.github.stebeg.tools.sql.param.QueryParameter;
import com.github.stebeg.tools.sql.query.Select;
import com.github.stebeg.tools.sql.query.Update;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

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
        query.getParameterList().add(firstParameterMock);

        final QueryParameter secondParameterMock = mock(QueryParameter.class);
        query.getParameterList().add(secondParameterMock);

        final PreparedStatement expResult = mock(PreparedStatement.class);
        when(connectionMock.prepareStatement(query.getQueryString(), Statement.RETURN_GENERATED_KEYS))
                .thenReturn(expResult);

        final PreparedStatement result = this.instance.createPreparedStatement(connectionMock, query);
        assertEquals(expResult, result);

        verify(firstParameterMock).setValue(result, 1);
        verify(secondParameterMock).setValue(result, 2);
    }

    @Test
    public void createPreparedStatement_Update_NoGeneratedKeys() throws Exception {
        final Connection connectionMock = mock(Connection.class);
        final Update query = new Update("UPDATE", false);

        final QueryParameter firstParameterMock = mock(QueryParameter.class);
        query.getParameterList().add(firstParameterMock);

        final QueryParameter secondParameterMock = mock(QueryParameter.class);
        query.getParameterList().add(secondParameterMock);

        final PreparedStatement expResult = mock(PreparedStatement.class);
        when(connectionMock.prepareStatement(query.getQueryString(), Statement.NO_GENERATED_KEYS))
                .thenReturn(expResult);

        final PreparedStatement result = this.instance.createPreparedStatement(connectionMock, query);
        assertEquals(expResult, result);

        verify(firstParameterMock).setValue(result, 1);
        verify(secondParameterMock).setValue(result, 2);
    }

}
