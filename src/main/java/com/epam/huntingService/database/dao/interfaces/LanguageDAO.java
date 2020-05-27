package com.epam.huntingService.database.dao.interfaces;

import com.epam.huntingService.entity.Language;

import java.sql.SQLException;

public interface LanguageDAO extends BaseDAO<Language> {

    Integer getLanguageId(String language) throws SQLException;
}
