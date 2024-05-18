package com.baeldung.libraries.ormlite;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

public class LibraryDaoImpl extends BaseDaoImpl<Library, Long> implements LibraryDao {

    public LibraryDaoImpl(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, Library.class);
    }

    @Override
    public List<Library> findByName(String name) throws SQLException {
        return super.queryForEq("name", name);

    }

}
