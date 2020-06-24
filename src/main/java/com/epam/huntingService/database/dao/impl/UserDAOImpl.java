package com.epam.huntingService.database.dao.impl;

import com.epam.huntingService.database.dao.interfaces.UserDAO;
import com.epam.huntingService.util.FileManager;
import com.epam.huntingService.validator.AuthorizationValidator;
import com.epam.huntingService.database.connection.ConnectionPool;
import com.epam.huntingService.entity.User;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.epam.huntingService.util.constants.ParameterNamesConstants.USER_ROLE_ID;

public class UserDAOImpl implements UserDAO {
    private RoleDAOImpl roleDAO = new RoleDAOImpl();

    private static final String ADD_USER = "INSERT INTO USER (NAME, SURNAME, LOGIN, PASSWORD, EMAIL, PHONE, ROLE_ID) " +
            "VALUES (?,?,?,?,?,?,?)";
    private static final String SELECT_ALL_USERS = "SELECT * FROM USER";
    private static final String CHANGE_PASSWORD = "UPDATE USER SET PASSWORD = ? WHERE (ID = ?)";
    private static final String CHANGE_ROLE = "UPDATE USER SET ROLE_ID = ? WHERE (ID = ?)";
    private static final String GET_USER_BY_ID = "SELECT U.ID, U.NAME, U.SURNAME, U.LOGIN, U.PASSWORD, " +
            "U.EMAIL, U.PHONE, U.DOCUMENT, U.ROLE_ID, R.ROLE FROM USER U, ROLE R WHERE U.ROLE_ID=R.ID AND U.ID=?";
    private static final String GET_USER_BY_LOGIN_PASSWORD = "SELECT U.ID, U.NAME, U.SURNAME, U.LOGIN, U.PASSWORD, " +
            "U.EMAIL, U.PHONE, U.DOCUMENT, U.ROLE_ID, R.ROLE FROM USER U, ROLE R WHERE U.ROLE_ID=R.ID AND U.LOGIN = ? AND U.PASSWORD = ?";
    private static final String DELETE_USER = "DELETE FROM USER WHERE ID = ?";
    private static final String CHECK_LOGIN = "SELECT * FROM USER WHERE LOGIN = ?";
    private static final String CHANGE_USER_DATA = "UPDATE USER SET PASSWORD = ?, EMAIL = ?, PHONE = ? WHERE ID = ?";
    private static final String UPLOAD_HUNTER_DOCUMENT = "UPDATE USER SET DOCUMENT = ? WHERE ID = ?";
    private static final String DOWNLOAD_HUNTER_DOCUMENT = "SELECT DOCUMENT FROM USER WHERE ID = ?";

    private ConnectionPool connectionPool;
    private Connection connection;

    private void setParametersToUser(User user, ResultSet resultSet, Integer languageID) throws SQLException, IOException {
        FileManager fileManager = new FileManager();

        user.setId(resultSet.getLong("ID"));
        user.setName(resultSet.getString("NAME"));
        user.setSurname(resultSet.getString("SURNAME"));
        user.setLogin(resultSet.getString("LOGIN"));
        user.setPassword(resultSet.getString("PASSWORD"));
        user.setEmail(resultSet.getString("EMAIL"));
        user.setPhone(resultSet.getString("PHONE"));
        user.setDocument(fileManager.takeFileFromDataBase(resultSet.getBlob("DOCUMENT")));
        user.setRoleID(resultSet.getInt("ROLE_ID"));
        String role = roleDAO.getRole(user.getRoleID(), languageID);
        user.setRole(role);
    }

    @Override
    public void create(User user) throws SQLException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_USER)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getSurname());
            preparedStatement.setString(3, user.getLogin());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getEmail());
            preparedStatement.setString(6, user.getPhone());
            preparedStatement.setInt(7, USER_ROLE_ID);
            preparedStatement.executeUpdate();
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public User getUserByLoginPassword(String login, String password, Integer languageID) throws SQLException, IOException {
        User user = null;
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_BY_LOGIN_PASSWORD)) {
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String passwordInDataBase = resultSet.getString("PASSWORD");
                user = new User();
                if (AuthorizationValidator.checkPassword(passwordInDataBase, password)) {
                    setParametersToUser(user, resultSet, languageID);
                }
            }
        } finally {
            connectionPool.returnConnection(connection);
        }
        return user;
    }

    @Override
    public List<User> getAll(Integer languageID) throws SQLException, IOException {
        List<User> users = new ArrayList<>();
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                setParametersToUser(user, resultSet, languageID);
                users.add(user);
            }
        } finally {
            connectionPool.returnConnection(connection);
        }
        return users;
    }

    @Override
    public User getByID(Long id, Integer languageID) throws SQLException, IOException {
        User user = new User();
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user = new User();
                setParametersToUser(user, resultSet, languageID);
            }
        } finally {
            connectionPool.returnConnection(connection);
        }
        return user;
    }



    @Override
    public Blob downloadDocument(Long id) throws SQLException {
        Blob document = null;
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(DOWNLOAD_HUNTER_DOCUMENT)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                document = resultSet.getBlob("DOCUMENT");
            }
        } finally {
            connectionPool.returnConnection(connection);
        }
        return document;
    }

    @Override
    public boolean isLoginExist(String login) throws SQLException {
        boolean isExist = false;
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(CHECK_LOGIN)) {
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                isExist = true;
            }
        } finally {
            connectionPool.returnConnection(connection);
        }
        return isExist;
    }

    @Override
    public void update(Long id, Integer languageID, User user) throws SQLException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(CHANGE_USER_DATA)) {
            preparedStatement.setString(1, user.getPassword());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPhone());
            preparedStatement.setLong(4, id);
            preparedStatement.executeUpdate();
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public void uploadDocument(Long id, InputStream fileInputStream) throws SQLException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPLOAD_HUNTER_DOCUMENT)) {
            preparedStatement.setBlob(1, fileInputStream);
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public void changePassword(String password, Long id) throws SQLException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(CHANGE_PASSWORD)) {
            preparedStatement.setString(1, password);
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public void changeUserRole(Integer roleID, Long userID) throws SQLException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(CHANGE_ROLE)) {
            preparedStatement.setInt(1, roleID);
            preparedStatement.setLong(2, userID);
            preparedStatement.executeUpdate();
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } finally {
            connectionPool.returnConnection(connection);
        }
    }
}
