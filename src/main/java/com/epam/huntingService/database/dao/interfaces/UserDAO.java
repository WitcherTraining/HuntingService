package com.epam.huntingService.database.dao.interfaces;

import com.epam.huntingService.entity.User;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;

public interface UserDAO extends BaseDAO<User> {

    User getUserByLoginPassword(String login, String password, Integer languageID) throws SQLException, IOException;

    void uploadDocument(Long id, InputStream fileInputStream) throws SQLException;

    Blob downloadDocument(Long id) throws SQLException;

    User getByID(Long id, Integer languageID) throws SQLException, IOException;

    void changePassword(String password, Long id) throws SQLException;

    void changeUserRole(Integer roleID, Long userID) throws SQLException;

    boolean isLoginExist(String login) throws SQLException;

    void delete(Long id) throws SQLException;
}
