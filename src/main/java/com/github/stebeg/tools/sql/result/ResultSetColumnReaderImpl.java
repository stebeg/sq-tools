package com.github.stebeg.tools.sql.result;

import com.github.stebeg.tools.sql.common.SignType;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Steffen Berger
 */
class ResultSetColumnReaderImpl implements ResultSetColumnReader {

    ResultSetColumnReaderImpl() {
    }

    @Override
    public List<ResultSetColumn> readColumns(
            final ResultSet resultSet) throws SQLException {
        final List<ResultSetColumn> columnList = Lists.newArrayList();
        final ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        for (int index = 1; index <= resultSetMetaData.getColumnCount(); index++) {
            final ResultSetColumn column = new ResultSetColumn(
                    resultSetMetaData.getColumnLabel(index),
                    resultSetMetaData.getColumnType(index),
                    resultSetMetaData.isSigned(index) ? SignType.SIGNED : SignType.UNSIGNED,
                    resultSetMetaData.getPrecision(index),
                    resultSetMetaData.getScale(index));
            columnList.add(column);
        }
        return ImmutableList.copyOf(columnList);
    }

}
