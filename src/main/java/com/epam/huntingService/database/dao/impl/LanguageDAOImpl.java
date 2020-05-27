package com.epam.huntingService.database.dao.impl;

import com.epam.huntingService.database.dao.interfaces.LanguageDAO;
import com.epam.huntingService.entity.Language;

import java.sql.SQLException;
import java.util.List;

public class LanguageDAOImpl implements LanguageDAO {
    private TemplateOperationsImpl templateOperations = TemplateOperationsImpl.getInstance();
    private static final String GET_LANGUAGE_ID = "SELECT ID FROM LANGUAGE WHERE LANGUAGE=?";

    @Override
    public Integer getLanguageId(String language) throws SQLException {
        long languageID = templateOperations.extractIDByName(language, GET_LANGUAGE_ID);
        return (int) languageID;
    }

    @Override
    public void create(Language object) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Language> getAll(Integer languageID) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Language getByID(Long id, Integer languageID) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(Long id, Integer languageID, Language language) throws SQLException {
        throw new UnsupportedOperationException();
    }
}
