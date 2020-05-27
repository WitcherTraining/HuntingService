package com.epam.huntingService.database.dao.interfaces;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface BaseDAO<T> {

    void create(T object) throws SQLException;

    List<T> getAll(Integer languageID) throws SQLException, IOException;

    T getByID(Long id, Integer languageID) throws SQLException, IOException;

    void update(Long id, Integer languageID, T object) throws SQLException;
}
