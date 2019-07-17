package com.github.stebeg.tools.sql.param;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @param <T> Der Typ des Parameters
 * @author Steffen Berger
 */
public interface QueryParameter<T> {

    T getValue();

    void setValue(PreparedStatement preparedStatement, int index)
            throws SQLException;

}
