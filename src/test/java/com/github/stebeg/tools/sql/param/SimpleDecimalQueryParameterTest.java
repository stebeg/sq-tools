package com.github.stebeg.tools.sql.param;

import com.github.stebeg.tools.sql.common.SignType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.sql.PreparedStatement;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * @author Steffen Berger
 */
public class SimpleDecimalQueryParameterTest {

    public SimpleDecimalQueryParameterTest() {
    }

    @ParameterizedTest
    @ValueSource(doubles = {-343.344, 0.0, 125.98})
    public void testGetValue_Signed_Success(Double value) {
        final SignType signType = SignType.SIGNED;
        final SimpleDecimalQueryParameter instance
                = new SimpleDecimalQueryParameter(value, signType);
        final Double result = instance.getValue();
        assertEquals(value, result);
    }

    @ParameterizedTest
    @ValueSource(doubles = {0, 65535.5609})
    public void testGetValue_Unsigned_Success(Double value) {
        final SignType signType = SignType.UNSIGNED;
        final SimpleDecimalQueryParameter instance
                = new SimpleDecimalQueryParameter(value, signType);
        final Double result = instance.getValue();
        assertEquals(value, result);
    }

    @ParameterizedTest
    @ValueSource(doubles = {-0.000001, -1})
    public void testGetValue_Unsigned_Fail(Double value) {
        final SignType signType = SignType.UNSIGNED;
        final Exception exception = assertThrows(
                IllegalArgumentException.class, ()
                -> new SimpleDecimalQueryParameter(value, signType));
        assertNotNull(exception);
    }

    @Test
    public void testGetValue_NullValue() {
        final SimpleDecimalQueryParameter instance
                = new SimpleDecimalQueryParameter(null, SignType.SIGNED);
        assertNull(instance.getValue());
    }

    @Test
    public void testSetValue() throws Exception {
        final int index = 5;
        final double value = 6.7;
        final PreparedStatement preparedStatementMock = mock(PreparedStatement.class);
        final SimpleDecimalQueryParameter instance
                = new SimpleDecimalQueryParameter(value, SignType.SIGNED);

        instance.setValue(preparedStatementMock, index);
        verify(preparedStatementMock).setDouble(index, value);
    }
}
