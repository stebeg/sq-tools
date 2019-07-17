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
public class SmallIntQueryParameterTest {

    public SmallIntQueryParameterTest() {
    }

    @ParameterizedTest
    @ValueSource(ints = {-32768, 0, 32767})
    public void testGetValue_Signed_Success(Integer value) {
        final SignType signType = SignType.SIGNED;
        final SmallIntQueryParameter instance
                = new SmallIntQueryParameter(value, signType);
        final Integer result = instance.getValue();
        assertEquals(value, result);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 65535})
    public void testGetValue_Unsigned_Success(Integer value) {
        final SignType signType = SignType.UNSIGNED;
        final SmallIntQueryParameter instance
                = new SmallIntQueryParameter(value, signType);
        final Integer result = instance.getValue();
        assertEquals(value, result);
    }

    @ParameterizedTest
    @ValueSource(ints = {-32769, 32768})
    public void testGetValue_Signed_Fail(Integer value) {
        final SignType signType = SignType.SIGNED;
        final Exception exception = assertThrows(
                IllegalArgumentException.class, ()
                -> new SmallIntQueryParameter(value, signType));
        assertNotNull(exception);
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 65536})
    public void testGetValue_Unsigned_Fail(Integer value) {
        final SignType signType = SignType.UNSIGNED;
        final Exception exception = assertThrows(
                IllegalArgumentException.class, ()
                -> new SmallIntQueryParameter(value, signType));
        assertNotNull(exception);
    }

    @Test
    public void testSetValue() throws Exception {
        final int index = 5;
        final int value = 6;
        final PreparedStatement preparedStatementMock = mock(PreparedStatement.class);
        final SmallIntQueryParameter instance
                = new SmallIntQueryParameter(value, SignType.SIGNED);

        instance.setValue(preparedStatementMock, index);
        verify(preparedStatementMock).setInt(index, value);
    }

}
