package com.github.stebeg.tools.sql.param;

import com.github.stebeg.tools.sql.common.SignType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * @author Steffen Berger
 */
public class DecimalQueryParameterTest {

    public DecimalQueryParameterTest() {
    }

    static Stream<Arguments> getValidValues() {
        return Stream.of(
                Arguments.of(new BigDecimal(-123.45).setScale(2, RoundingMode.HALF_UP), SignType.SIGNED, 5, 2),
                Arguments.of(new BigDecimal(123.456).setScale(3, RoundingMode.HALF_UP), SignType.SIGNED, 5, 2),
                Arguments.of(new BigDecimal(345.5678).setScale(4, RoundingMode.HALF_UP), SignType.UNSIGNED, 7, 4),
                Arguments.of(BigDecimal.ZERO.setScale(0, RoundingMode.HALF_UP), SignType.SIGNED, 1, 0),
                Arguments.of(BigDecimal.ZERO.setScale(0, RoundingMode.HALF_UP), SignType.UNSIGNED, 1, 0)
        );
    }

    static Stream<Arguments> getInvalidValues() {
        return Stream.of(
                Arguments.of(new BigDecimal(-123.45).setScale(2, RoundingMode.HALF_UP), SignType.SIGNED, 3, 2),
                Arguments.of(new BigDecimal(123.45).setScale(2, RoundingMode.HALF_UP), SignType.SIGNED, 3, 2),
                Arguments.of(new BigDecimal(-1).setScale(0, RoundingMode.HALF_UP), SignType.UNSIGNED, 1, 0)
        );
    }

    @ParameterizedTest
    @MethodSource("getValidValues")
    public void testGetValue_Success(
            final BigDecimal value,
            final SignType signType,
            final int precision,
            final int scale) {
        final BigDecimal expResult = value.setScale(scale, RoundingMode.HALF_UP);
        final DecimalQueryParameter instance
                = new DecimalQueryParameter(value, signType, precision, scale);
        final BigDecimal result = instance.getValue();
        assertEquals(expResult, result);
    }

    @ParameterizedTest
    @MethodSource("getInvalidValues")
    public void testGetValue_Fail(
            final BigDecimal value,
            final SignType signType,
            final int precision,
            final int scale) {
        final Exception exception = assertThrows(
                IllegalArgumentException.class, ()
                -> new DecimalQueryParameter(value, signType, precision, scale));
        assertNotNull(exception);
    }

    @Test
    public void testGetValue_NullValue() {
        final BigDecimal value = null;
        final DecimalQueryParameter instance
                = new DecimalQueryParameter(value, SignType.SIGNED, 5, 2);
        assertNull(instance.getValue());
    }

    @Test
    public void testSetValue() throws Exception {
        final int index = 5;
        final BigDecimal value = BigDecimal.TEN;
        final PreparedStatement preparedStatementMock = mock(PreparedStatement.class);
        final DecimalQueryParameter instance
                = new DecimalQueryParameter(value, SignType.SIGNED, 5, 2);

        instance.setValue(preparedStatementMock, index);
        verify(preparedStatementMock).setBigDecimal(index, value);
    }

}
