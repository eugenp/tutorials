package com.baeldung.ormlite;

import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

public class LibraryDaoImpl extends BaseDaoImpl<Library, Long> implements LibraryDao {

    public LibraryDaoImpl(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, Library.class);
    }

    @Override
    public List<Library> findByName(String name) throws SQLException {
        return super.queryForEq("name", name);

    }

}
