package com.baeldung.libraries.ormlite;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

public interface LibraryDao extends Dao<Library, Long> {
    public List<Library> findByName(String name) throws SQLException;
}
