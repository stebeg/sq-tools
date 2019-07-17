package com.github.stebeg.tools.sql.param;

import com.github.stebeg.tools.sql.common.SignType;
import org.junit.jupiter.api.Test;

import java.sql.PreparedStatement;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Steffen Berger
 */
public class AbstractNumericQueryParameterTest {

    public AbstractNumericQueryParameterTest() {
    }

    @Test
    public void testGetSignType() {
        final SignType signType = SignType.UNSIGNED;
        final AbstractNumericQueryParameter<Integer> instance
                = new AbstractNumericQueryParameterImpl(5, signType);
        assertEquals(signType, instance.getSignType());
    }

    private class AbstractNumericQueryParameterImpl
            extends AbstractNumericQueryParameter<Integer> {

        AbstractNumericQueryParameterImpl(Integer value, SignType signType) {
            super(value, signType);
        }

        @Override
        protected SignType getSignType() {
            return super.getSignType();
        }

        @Override
        public void setValue(
                final PreparedStatement preparedStatement,
                final int index) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

    }

}
