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
public class MediumIntQueryParameterTest {

    public MediumIntQueryParameterTest() {
    }

    @ParameterizedTest
    @ValueSource(ints = {-8388608, 0, 8388607})
    public void testGetValue_Signed_Success(final Integer value) {
        final SignType signType = SignType.SIGNED;
        final MediumIntQueryParameter instance
                = new MediumIntQueryParameter(value, signType);
        final Integer result = instance.getValue();
        assertEquals(value, result);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 16777215})
    public void testGetValue_Unsigned_Success(final Integer value) {
        final SignType signType = SignType.UNSIGNED;
        final MediumIntQueryParameter instance
                = new MediumIntQueryParameter(value, signType);
        final Integer result = instance.getValue();
        assertEquals(value, result);
    }

    @ParameterizedTest
    @ValueSource(ints = {-8388609, 8388608})
    public void testGetValue_Signed_Fail(final Integer value) {
        final SignType signType = SignType.SIGNED;
        final Exception exception = assertThrows(
                IllegalArgumentException.class, ()
                -> new MediumIntQueryParameter(value, signType));
        assertNotNull(exception);
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 16777216})
    public void testGetValue_Unsigned_Fail(final Integer value) {
        final SignType signType = SignType.UNSIGNED;
        final Exception exception = assertThrows(
                IllegalArgumentException.class, () -> new MediumIntQueryParameter(value, signType));
        assertNotNull(exception);
    }

    @Test
    public void testSetValue() throws Exception {
        final int index = 5;
        final int value = 6;
        final PreparedStatement preparedStatementMock = mock(PreparedStatement.class);
        final MediumIntQueryParameter instance
                = new MediumIntQueryParameter(value, SignType.SIGNED);

        instance.setValue(preparedStatementMock, index);
        verify(preparedStatementMock).setInt(index, value);
    }

}
