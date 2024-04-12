package com.baeldung.spring.jdbc.template.guide;

import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;

public class CustomSQLErrorCodeTranslator extends SQLErrorCodeSQLExceptionTranslator {

    @Override
    protected DataAccessException customTranslate(final String task, final String sql, final SQLException sqlException) {
        if (sqlException.getErrorCode() == 23505) {
            return new DuplicateKeyException("Custome Exception translator - Integrity contraint voilation.", sqlException);
        }
        return null;
    }

}
