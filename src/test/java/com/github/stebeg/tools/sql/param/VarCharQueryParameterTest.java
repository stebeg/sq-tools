package com.github.stebeg.tools.sql.param;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.sql.PreparedStatement;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * @author Steffen Berger
 */
public class VarCharQueryParameterTest {

    public VarCharQueryParameterTest() {
    }

    static Stream<Arguments> getValidValuesAndValidResults() {
        return Stream.of(
                Arguments.of("foo", 3, true, "foo%"),
                Arguments.of("foo", 3, false, "foo")
        );
    }

    static Stream<Arguments> getValidValuesAndInvalidResults() {
        return Stream.of(
                Arguments.of("foo", 3, true, "foo"),
                Arguments.of("foo", 3, false, "foo%")
        );
    }

    static Stream<Arguments> getValidValuesAndInvalidLengths() {
        return Stream.of(
                Arguments.of("foo", 2, true),
                Arguments.of("foo", 2, false)
        );
    }

    @ParameterizedTest
    @MethodSource("getValidValuesAndValidResults")
    public void testGetValue_Success(String value, int maxLength, boolean addLikeSuffix, String expResult) {
        final VarCharQueryParameter instance
                = new VarCharQueryParameter(value, maxLength, addLikeSuffix);
        final String result = instance.getValue();
        assertEquals(expResult, result);
    }

    @ParameterizedTest
    @MethodSource("getValidValuesAndInvalidResults")
    public void testGetValue_Fail(String value, int maxLength, boolean addLikeSuffix, String expResult) {
        final VarCharQueryParameter instance
                = new VarCharQueryParameter(value, maxLength, addLikeSuffix);
        final String result = instance.getValue();
        assertNotEquals(expResult, result);
    }

    @ParameterizedTest
    @MethodSource("getValidValuesAndInvalidLengths")
    public void testGetValue_Fail_InvalidLegth(String value, int maxLength, boolean addLikeSuffix) {
        final Exception exception = assertThrows(
                IllegalArgumentException.class, ()
                -> new VarCharQueryParameter(value, maxLength, addLikeSuffix));
        assertNotNull(exception);
    }

    @Test
    public void testGetValue_VarArgs() {
        final String expResult = "it is a foo bar test";
        final String value = "it is a %s %s test";
        final String arg1 = "foo", arg2 = "bar";

        final VarCharQueryParameter instance
                = new VarCharQueryParameter(value, 20, arg1, arg2);
        assertEquals(expResult, instance.getValue());
    }

    @Test
    public void testGetValue_VarArgs_InvalidLegth() {
        final String value = "it is a %s %s test";
        final String arg1 = "foo", arg2 = "bar";
        final Exception exception = assertThrows(
                IllegalArgumentException.class, ()
                -> new VarCharQueryParameter(value, 19, arg1, arg2));
        assertNotNull(exception);
    }

    @Test
    public void testGetMaxLength() {
        final int maxLength = 5;
        final VarCharQueryParameter instance
                = new VarCharQueryParameter(null, maxLength);
        assertEquals(maxLength, instance.getMaxLength());
    }

    @Test
    public void testIsAddLikeSuffix() {
        final VarCharQueryParameter instance
                = new VarCharQueryParameter(null, 0, true);
        assertTrue(instance.isAddLikeSuffix());
    }

    @Test
    public void testIsAddLikeSuffix_DefaultFalse() {
        final VarCharQueryParameter instance
                = new VarCharQueryParameter(null, 0);
        assertFalse(instance.isAddLikeSuffix());
    }

    @Test
    public void testSetValue() throws Exception {
        final int index = 5;
        final String value = "foo";
        final PreparedStatement preparedStatementMock = mock(PreparedStatement.class);
        final VarCharQueryParameter instance
                = new VarCharQueryParameter(value, 3);

        instance.setValue(preparedStatementMock, index);
        verify(preparedStatementMock).setString(index, value);
    }
}
