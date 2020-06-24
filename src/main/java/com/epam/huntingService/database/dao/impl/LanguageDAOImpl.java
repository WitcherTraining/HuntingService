package com.epam.huntingService.database.dao.impl;

import com.epam.huntingService.database.connection.ConnectionPool;
import com.epam.huntingService.database.dao.interfaces.LanguageDAO;
import com.epam.huntingService.entity.Language;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LanguageDAOImpl implements LanguageDAO {
    private TemplateOperationsImpl templateOperations = TemplateOperationsImpl.getInstance();
    private static final String GET_LANGUAGE_ID = "SELECT ID FROM LANGUAGE WHERE LANGUAGE=?";
    private static final String SELECT_ALL_LANGUAGES = "SELECT * FROM LANGUAGE";

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
    public List<Language> getAll() throws SQLException {
        List<Language> languages = new ArrayList<>();
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_LANGUAGES)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Language language = new Language();
                language.setId(resultSet.getInt("ID"));
                language.setLanguage(resultSet.getString("LANGUAGE"));
                languages.add(language);
            }
        } finally {
            connectionPool.returnConnection(connection);
        }
        return languages;
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
