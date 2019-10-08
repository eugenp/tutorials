package com.baeldung.ormlite;

import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.dao.Dao;

public interface LibraryDao extends Dao<Library, Long> {
    public List<Library> findByName(String name) throws SQLException;
}
