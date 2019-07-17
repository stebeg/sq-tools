package com.github.stebeg.tools.sql.result;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @param <T>
 * @author Steffen Berger
 */
public interface QueryResultItemFactory<T> {

    T build(
            final ResultSet resultSet,
            final List<ResultSetColumn> columns) throws SQLException;

}
