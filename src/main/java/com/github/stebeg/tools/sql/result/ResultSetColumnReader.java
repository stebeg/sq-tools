package com.github.stebeg.tools.sql.result;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Steffen Berger
 */
interface ResultSetColumnReader {

    List<ResultSetColumn> readColumns(
            ResultSet resultSet) throws SQLException;

}
