package com.github.stebeg.tools.sql.param;

import com.github.stebeg.tools.sql.common.SignType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * @author Steffen Berger
 */
public class BigIntQueryParameterTest {

    public BigIntQueryParameterTest() {
    }

    static Stream<Arguments> getValidValues() {
        return Stream.of(
                Arguments.of(new BigInteger("-9223372036854775808"), SignType.SIGNED),
                Arguments.of(new BigInteger("9223372036854775807"), SignType.SIGNED),
                Arguments.of(new BigInteger("18446744073709551615"), SignType.UNSIGNED),
                Arguments.of(BigInteger.ZERO, SignType.SIGNED),
                Arguments.of(BigInteger.ZERO, SignType.UNSIGNED)
        );
    }

    static Stream<Arguments> getInvalidValues() {
        return Stream.of(
                Arguments.of(new BigInteger("-9223372036854775809"), SignType.SIGNED),
                Arguments.of(new BigInteger("9223372036854775808"), SignType.SIGNED),
                Arguments.of(new BigInteger("18446744073709551616"), SignType.UNSIGNED),
                Arguments.of(new BigInteger("-1"), SignType.UNSIGNED)
        );
    }

    @ParameterizedTest
    @MethodSource("getValidValues")
    public void testGetValue_Success(
            final BigInteger value,
            final SignType signType) {
        final BigIntQueryParameter instance
                = new BigIntQueryParameter(value, signType);
        final BigInteger result = instance.getValue();
        assertEquals(value, result);
    }

    @ParameterizedTest
    @MethodSource("getInvalidValues")
    public void testGetValue_Fail(
            final BigInteger value,
            final SignType signType) {
        final Exception exception = assertThrows(
                IllegalArgumentException.class, ()
                -> new BigIntQueryParameter(value, signType));
        assertNotNull(exception);
    }

    @Test
    public void testSetValue() throws Exception {
        final int index = 5;
        final BigInteger value = BigInteger.TEN;
        final PreparedStatement preparedStatementMock = mock(PreparedStatement.class);
        final BigIntQueryParameter instance
                = new BigIntQueryParameter(value, SignType.SIGNED);

        instance.setValue(preparedStatementMock, index);
        verify(preparedStatementMock).setObject(index, value, java.sql.Types.BIGINT);
    }

}
