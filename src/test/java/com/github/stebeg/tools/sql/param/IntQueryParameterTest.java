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
public class IntQueryParameterTest {

    public IntQueryParameterTest() {
    }

    @ParameterizedTest
    @ValueSource(longs = {-2147483648L, 0, 2147483647L})
    public void testGetValue_Signed_Success(final Long value) {
        final SignType signType = SignType.SIGNED;
        final IntQueryParameter instance
                = new IntQueryParameter(value, signType);
        final Long result = instance.getValue();
        assertEquals(value, result);
    }

    @ParameterizedTest
    @ValueSource(longs = {0L, 4294967295L})
    public void testGetValue_Unsigned_Success(final Long value) {
        final SignType signType = SignType.UNSIGNED;
        final IntQueryParameter instance
                = new IntQueryParameter(value, signType);
        final Long result = instance.getValue();
        assertEquals(value, result);
    }

    @ParameterizedTest
    @ValueSource(longs = {-2147483649L, 2147483648L})
    public void testGetValue_Signed_Fail(final Long value) {
        final SignType signType = SignType.SIGNED;
        final Exception exception = assertThrows(
                IllegalArgumentException.class, ()
                -> new IntQueryParameter(value, signType));
        assertNotNull(exception);
    }

    @ParameterizedTest
    @ValueSource(longs = {-1, 4294967296L})
    public void testGetValue_Unsigned_Fail(final Long value) {
        final SignType signType = SignType.UNSIGNED;
        final Exception exception = assertThrows(
                IllegalArgumentException.class, ()
                -> new IntQueryParameter(value, signType));
        assertNotNull(exception);
    }

    @Test
    public void testSetValue() throws Exception {
        final int index = 5;
        final long value = 6L;
        final PreparedStatement preparedStatementMock = mock(PreparedStatement.class);
        final IntQueryParameter instance
                = new IntQueryParameter(value, SignType.SIGNED);

        instance.setValue(preparedStatementMock, index);
        verify(preparedStatementMock).setLong(index, value);
    }
}
