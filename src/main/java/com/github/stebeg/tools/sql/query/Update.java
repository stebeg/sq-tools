package com.github.stebeg.tools.sql.query;

/**
 * @author Steffen Berger
 */
public class Update extends AbstractQuery {

    private final boolean generateKey;

    public Update(
            final String queryString,
            final boolean generateKey) {
        super(queryString);
        this.generateKey = generateKey;
    }

    public boolean isGenerateKey() {
        return this.generateKey;
    }

}
