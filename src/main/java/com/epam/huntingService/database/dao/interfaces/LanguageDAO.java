package com.epam.huntingService.database.dao.interfaces;

import com.epam.huntingService.entity.Language;

import java.sql.SQLException;
import java.util.List;

public interface LanguageDAO extends BaseDAO<Language> {

    Integer getLanguageId(String language) throws SQLException;

    List<Language> getAll() throws SQLException;
}
