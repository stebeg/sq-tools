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
public class TinyIntQueryParameterTest {

    public TinyIntQueryParameterTest() {
    }

    @ParameterizedTest
    @ValueSource(ints = {-128, 0, 127})
    public void testGetValue_Signed_Success(Integer value) {
        final SignType signType = SignType.SIGNED;
        final TinyIntQueryParameter instance
                = new TinyIntQueryParameter(value, signType);
        final Integer result = instance.getValue();
        assertEquals(value, result);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 255})
    public void testGetValue_Unsigned_Success(Integer value) {
        final SignType signType = SignType.UNSIGNED;
        final TinyIntQueryParameter instance
                = new TinyIntQueryParameter(value, signType);
        final Integer result = instance.getValue();
        assertEquals(value, result);
    }

    @ParameterizedTest
    @ValueSource(ints = {-129, 128})
    public void testGetValue_Signed_Fail(Integer value) {
        final SignType signType = SignType.SIGNED;
        final Exception exception = assertThrows(
                IllegalArgumentException.class, ()
                -> new TinyIntQueryParameter(value, signType));
        assertNotNull(exception);
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 256})
    public void testGetValue_Unsigned_Fail(Integer value) {
        final SignType signType = SignType.UNSIGNED;
        final Exception exception = assertThrows(
                IllegalArgumentException.class, ()
                -> new TinyIntQueryParameter(value, signType));
        assertNotNull(exception);
    }

    @Test
    public void testSetValue() throws Exception {
        final int index = 5;
        final int value = 6;
        final PreparedStatement preparedStatementMock = mock(PreparedStatement.class);
        final TinyIntQueryParameter instance
                = new TinyIntQueryParameter(value, SignType.SIGNED);

        instance.setValue(preparedStatementMock, index);
        verify(preparedStatementMock).setInt(index, value);
    }

}
