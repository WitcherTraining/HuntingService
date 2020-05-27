package com.epam.huntingService.database.dao.impl;

import com.epam.huntingService.database.connection.ConnectionPool;
import com.epam.huntingService.database.dao.interfaces.OrganizationDAO;
import com.epam.huntingService.entity.Organization;
import com.epam.huntingService.util.FileManager;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrganizationDAOImpl implements OrganizationDAO {
    private TemplateOperationsImpl templateOperations = TemplateOperationsImpl.getInstance();
    private static final String ADD_NEW_ORGANIZATION = "INSERT INTO ORGANIZATION (ID, NAME, DESCRIPTION, LOGO, " +
            "LANGUAGE_ID) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_ALL_ORGANIZATIONS = "SELECT * FROM ORGANIZATION O, LANGUAGE L " +
            "WHERE O.LANGUAGE_ID = L.ID AND L.ID = ?";
    private static final String SELECT_ORGANIZATION_BY_ID = "SELECT * FROM ORGANIZATION WHERE ID = ? AND LANGUAGE_ID = ?";
    private static final String EDIT_ORGANIZATION_BY_ID = "UPDATE ORGANIZATION SET NAME = ?, DESCRIPTION = ?, " +
            "LOGO = ? WHERE ID = ? AND LANGUAGE_ID = ?";
    private static final String SELECT_ORGANIZATION_ID_BY_NAME = "SELECT ID FROM ORGANIZATION WHERE NAME = ?";
    private static final String SELECT_ALL_LOCALIZED_ORGANIZATIONS = "SELECT * FROM ORGANIZATION";
    private static final String SELECT_ALL_LOCALIZED_ORGANIZATIONS_BY_ID = "SELECT * FROM ORGANIZATION WHERE ID = ?";
    private static final String SELECT_LAST_ID_FROM_ORGANIZATION = "SELECT MAX(ID) FROM ORGANIZATION";

    private ConnectionPool connectionPool;
    private Connection connection;

    @Override
    public Long takeLastID() throws SQLException {
        return templateOperations.extractLastID(SELECT_LAST_ID_FROM_ORGANIZATION);
    }

    @Override
    public Long takeOrganizationIdByName(String orgName) throws SQLException {
        return templateOperations.extractIDByName(orgName, SELECT_ORGANIZATION_ID_BY_NAME);
    }

    @Override
    public List<Organization> takeAllLocalizedOrganizations() throws SQLException, IOException {
        List <Organization> organizations = new ArrayList<>();
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_LOCALIZED_ORGANIZATIONS)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                setDataToOrganizationList(organizations, resultSet);
            }
        } finally {
            connectionPool.returnConnection(connection);
        }
        return organizations;
    }

    @Override
    public List<Organization> takeAllLocalizedOrganizationsByID(Long huntingGroundID) throws SQLException, IOException {
        List <Organization> organizations = new ArrayList<>();
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_LOCALIZED_ORGANIZATIONS_BY_ID)) {
            preparedStatement.setLong(1, huntingGroundID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                setDataToOrganizationList(organizations, resultSet);
            }
        } finally {
            connectionPool.returnConnection(connection);
        }
        return organizations;
    }

    @Override
    public void create(Organization organization) throws SQLException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        try (PreparedStatement ps = connection.prepareStatement(ADD_NEW_ORGANIZATION)) {
            ps.setLong(1, organization.getId());
            ps.setString(2, organization.getName());
            ps.setString(3, organization.getDescription());
            ps.setBlob(4, organization.getUploadingLogo());
            ps.setLong(5, organization.getLanguageID());
            ps.executeUpdate();
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    private void setParametersToOrganization(Organization organization, ResultSet resultSet) throws SQLException, IOException {
        FileManager fileManager = new FileManager();

        organization.setId(resultSet.getLong("ID"));
        organization.setName(resultSet.getString("NAME"));
        organization.setDescription(resultSet.getString("DESCRIPTION"));
        organization.setLogo(fileManager.takeFileFromDataBase(resultSet.getBlob("LOGO")));
        organization.setLanguageID(resultSet.getInt("LANGUAGE_ID"));
    }

    private void setDataToOrganizationList(List<Organization> organizations, ResultSet resultSet) throws SQLException, IOException {
        Organization organization = new Organization();
        setParametersToOrganization(organization, resultSet);
        organizations.add(organization);
    }

    @Override
    public List<Organization> getAll(Integer languageID) throws SQLException, IOException {
        List<Organization> organizations = new ArrayList<>();
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_ORGANIZATIONS)) {
            preparedStatement.setInt(1, languageID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                setDataToOrganizationList(organizations, resultSet);
            }
        } finally {
            connectionPool.returnConnection(connection);
        }
        return organizations;
    }

    @Override
    public Organization getByID(Long id, Integer languageID) throws SQLException, IOException {
        Organization organization = new Organization();
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ORGANIZATION_BY_ID)) {
            preparedStatement.setLong(1, id);
            preparedStatement.setInt(2, languageID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                setParametersToOrganization(organization, resultSet);
            }
        } finally {
            connectionPool.returnConnection(connection);
        }
        return organization;
    }

    @Override
    public void update(Long id, Integer languageID, Organization organization) throws SQLException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(EDIT_ORGANIZATION_BY_ID)) {
            preparedStatement.setString(1, organization.getName());
            preparedStatement.setString(2, organization.getDescription());
            preparedStatement.setBlob(3, organization.getUploadingLogo());
            preparedStatement.setLong(4, id);
            preparedStatement.setInt(5, languageID);
            preparedStatement.executeUpdate();
        } finally {
            connectionPool.returnConnection(connection);
        }
    }
}
