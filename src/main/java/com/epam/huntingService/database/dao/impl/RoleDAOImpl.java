package com.epam.huntingService.database.dao.impl;

import com.epam.huntingService.database.connection.ConnectionPool;
import com.epam.huntingService.database.dao.interfaces.BaseDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class RoleDAOImpl implements BaseDAO {
    private TemplateOperationsImpl templateOperations = TemplateOperationsImpl.getInstance();
    private static final String GET_ROLE_BY_ID = "SELECT ROLE FROM ROLE WHERE ID = ? AND LANGUAGE_ID = ?";
    private static final String GET_ROLE_ID = "SELECT * FROM ROLE WHERE ROLE = ?";

    public String getRole(Integer id, Integer languageID) throws SQLException{
        String role = "";
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ROLE_BY_ID)) {
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, languageID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                role = resultSet.getString("ROLE");
            }
        } finally {
            connectionPool.returnConnection(connection);
        }
        return role;
    }

    public Integer getRoleID(String role) throws SQLException{
        long roleID = templateOperations.extractIDByName(role, GET_ROLE_ID);
        return (int) roleID;
    }

    @Override
    public void create(Object object) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List getAll(Integer languageID) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object getByID(Long id, Integer languageID) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(Long id, Integer languageID, Object object) throws SQLException {
        throw new UnsupportedOperationException();
    }
}
