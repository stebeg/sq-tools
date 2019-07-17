package com.github.stebeg.tools.sql.query;

import com.github.stebeg.tools.sql.param.QueryParameter;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * @author Steffen Berger
 */
abstract class AbstractQuery {

    private final String queryString;
    private final List<QueryParameter> parameterList = Lists.newArrayList();

    AbstractQuery(String queryString) {
        Preconditions.checkNotNull(queryString);
        this.queryString = queryString;
    }

    public String getQueryString() {
        return this.queryString;
    }

    public List<QueryParameter> getParameterList() {
        return this.parameterList;
    }
}
