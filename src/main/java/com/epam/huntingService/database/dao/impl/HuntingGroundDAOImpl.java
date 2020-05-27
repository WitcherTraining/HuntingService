package com.epam.huntingService.database.dao.impl;

import com.epam.huntingService.database.connection.ConnectionPool;
import com.epam.huntingService.database.dao.interfaces.HuntingGroundDAO;
import com.epam.huntingService.entity.HuntingGround;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HuntingGroundDAOImpl implements HuntingGroundDAO {
    private TemplateOperationsImpl templateOperations = TemplateOperationsImpl.getInstance();
    private static final String ADD_HUNTING_GROUND = "INSERT INTO HUNTING_GROUND (ID, LANGUAGE_ID, NAME, DESCRIPTION, " +
            " DISTRICT, ORGANIZATION_ID) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SELECT_ALL_HUNTING_GROUNDS = "SELECT * FROM HUNTING_GROUND WHERE LANGUAGE_ID = ?";
    private static final String SELECT_ALL_HUNTING_GROUNDS_BY_ORGANIZATION_ID = "SELECT HG.ID, HG.NAME, HG.DESCRIPTION, " +
            " HG.LANGUAGE_ID, HG.DISTRICT, HG.ORGANIZATION_ID FROM ORGANIZATION O, " +
            "HUNTING_GROUND HG, LANGUAGE L WHERE O.LANGUAGE_ID = L.ID AND HG.ORGANIZATION_ID = O.ID " +
            "AND HG.LANGUAGE_ID = L.ID AND O.ID = ? AND L.ID = ?";
    private static final String SELECT_HUNTING_GROUND_BY_ID = "SELECT * FROM HUNTING_GROUND WHERE HUNTING_GROUND.ID = ? " +
            "AND HUNTING_GROUND.LANGUAGE_ID = ?";
    private static final String SELECT_HUNTING_GROUNDS_BY_ANIMAL_ID = "SELECT HG.ID, HG.NAME, HG.DESCRIPTION, " +
            "HG.LANGUAGE_ID, HG.DISTRICT, HG.ORGANIZATION_ID FROM ANIMAL A, HUNTING_GROUND HG, ANIMAL_QUOTA_HISTORY AQH, " +
            "LANGUAGE L WHERE A.LANGUAGE_ID = L.ID AND HG.LANGUAGE_ID = L.ID AND AQH.HUNTING_GROUND_ID = HG.ID " +
            "AND AQH.ANIMAL_ID = A.ID AND A.ID = ? AND L.ID = ?";
    private static final String SELECT_HUNTING_GROUND_ID_BY_NAME = "SELECT ID FROM HUNTING_GROUND WHERE NAME = ?";
    private static final String UPDATE_HUNTING_GROUND = "UPDATE HUNTING_GROUND SET NAME = ?, DESCRIPTION = ?, " +
            " DISTRICT = ?, ORGANIZATION_ID = ? WHERE ID = ? AND LANGUAGE_ID = ?";
    private static final String GET_ORGANIZATION_NAME_BY_HUNTING_GROUND_NAME = "SELECT ORGANIZATION.NAME FROM ORGANIZATION, " +
            "HUNTING_GROUND, LANGUAGE WHERE HUNTING_GROUND.ORGANIZATION_ID = ORGANIZATION.ID " +
            "AND HUNTING_GROUND.NAME = ? AND HUNTING_GROUND.LANGUAGE_ID = LANGUAGE.ID " +
            "AND ORGANIZATION.LANGUAGE_ID = LANGUAGE.ID AND LANGUAGE.ID = ?";
    private static final String SELECT_HUNTING_GROUNDS_WITH_THIS_ANIMAL = "SELECT HG.ID, HG.DISTRICT, HG.LANGUAGE_ID, " +
            "HG.ORGANIZATION_ID, HG.NAME, HG.DESCRIPTION FROM HUNTING_GROUND HG, ANIMAL A, " +
            "ANIMAL_QUOTA_HISTORY AQH, LANGUAGE L WHERE HG.LANGUAGE_ID = L.ID AND A.LANGUAGE_ID = L.ID " +
            "AND AQH.ANIMAL_ID = A.ID AND AQH.HUNTING_GROUND_ID = HG.ID AND A.NAME = ? AND L.ID = ?";
    private static final String SELECT_HUNTING_GROUNDS_BY_DISTRICT = "SELECT * FROM HUNTING_GROUND HG, LANGUAGE L " +
            "WHERE HG.LANGUAGE_ID = L.ID AND L.ID = ? AND HG.DISTRICT = ?";
    private static final String SELECT_LAST_ID_FROM_HUNTING_GROUND = "SELECT MAX(ID) FROM HUNTING_GROUND";
    private static final String SELECT_ALL_LOCALIZED_HUNTING_GROUNDS = "SELECT * FROM HUNTING_GROUND";
    private static final String SELECT_ALL_LOCALIZED_HUNTING_GROUNDS_BY_ID = "SELECT * FROM HUNTING_GROUND WHERE ID = ?";

    private ConnectionPool connectionPool;
    private Connection connection;

    @Override
    public List<HuntingGround> takeAllHuntingGroundsByAnimalID(Long animalID, Integer languageID) throws SQLException {
        List <HuntingGround> huntingGrounds = new ArrayList<>();
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_HUNTING_GROUNDS_BY_ANIMAL_ID)) {
            preparedStatement.setLong(1, animalID);
            preparedStatement.setLong(2, languageID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                setDataToHuntingGroundList(huntingGrounds, resultSet);
            }
        } finally {
            connectionPool.returnConnection(connection);
        }
        return huntingGrounds;
    }

    @Override
    public List<HuntingGround> takeAllLocalizedHuntingGroundsByID(Long huntingGroundID) throws SQLException {
        List <HuntingGround> huntingGrounds = new ArrayList<>();
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_LOCALIZED_HUNTING_GROUNDS_BY_ID)) {
            preparedStatement.setLong(1, huntingGroundID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                setDataToHuntingGroundList(huntingGrounds, resultSet);
            }
        } finally {
            connectionPool.returnConnection(connection);
        }
        return huntingGrounds;
    }

    @Override
    public List<HuntingGround> takeAllLocalizedHuntingGrounds() throws SQLException {
        List <HuntingGround> huntingGrounds = new ArrayList<>();
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_LOCALIZED_HUNTING_GROUNDS)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                setDataToHuntingGroundList(huntingGrounds, resultSet);
            }
        } finally {
            connectionPool.returnConnection(connection);
        }
        return huntingGrounds;
    }

    @Override
    public Long takeIDByName(String huntingGroundName) throws SQLException {
        return templateOperations.extractIDByName(huntingGroundName, SELECT_HUNTING_GROUND_ID_BY_NAME);
    }

    @Override
    public Long takeLastID() throws SQLException {
        return templateOperations.extractLastID(SELECT_LAST_ID_FROM_HUNTING_GROUND);
    }

    @Override
    public List<HuntingGround> takeAllHuntingGroundsInDistrict(Integer languageID, String district) throws SQLException {
        List<HuntingGround> huntingGrounds = new ArrayList<>();
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_HUNTING_GROUNDS_BY_DISTRICT)) {
            preparedStatement.setInt(1, languageID);
            preparedStatement.setString(2, district);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                setDataToHuntingGroundList(huntingGrounds, resultSet);
            }
        } finally {
            connectionPool.returnConnection(connection);
        }
        return huntingGrounds;
    }

    @Override
    public List<HuntingGround> getAllWithThisAnimal(String animalName,Integer languageID) throws SQLException {
        List<HuntingGround> huntingGrounds = new ArrayList<>();
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_HUNTING_GROUNDS_WITH_THIS_ANIMAL)) {
            preparedStatement.setString(1, animalName);
            preparedStatement.setInt(2, languageID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                setDataToHuntingGroundList(huntingGrounds, resultSet);
            }
        } finally {
            connectionPool.returnConnection(connection);
        }
        return huntingGrounds;
    }

    @Override
    public String getOrganizationNameByHuntingGroundName(String huntingGroundName, Integer languageID) throws SQLException {
        String organizationName = "";
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ORGANIZATION_NAME_BY_HUNTING_GROUND_NAME)) {
            preparedStatement.setString(1, huntingGroundName);
            preparedStatement.setInt(2, languageID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                organizationName = resultSet.getString("NAME");
            }
        } finally {
            connectionPool.returnConnection(connection);
        }
        return organizationName;
    }

    @Override
    public void create(HuntingGround huntingGround) throws SQLException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_HUNTING_GROUND)) {
            preparedStatement.setLong(1, huntingGround.getId());
            preparedStatement.setInt(2, huntingGround.getLanguageID());
            preparedStatement.setString(3, huntingGround.getName());
            preparedStatement.setString(4, huntingGround.getDescription());
            preparedStatement.setString(5, huntingGround.getDistrict());
            preparedStatement.setLong(6, huntingGround.getOrganizationID());
            preparedStatement.executeUpdate();
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    private void setParametersToHuntingGround(HuntingGround huntingGround, ResultSet resultSet) throws SQLException {
        huntingGround.setId(resultSet.getLong("ID"));
        huntingGround.setLanguageID(resultSet.getInt("LANGUAGE_ID"));
        huntingGround.setName(resultSet.getString("NAME"));
        huntingGround.setDescription(resultSet.getString("DESCRIPTION"));
        huntingGround.setDistrict(resultSet.getString("DISTRICT"));
        huntingGround.setOrganizationID(resultSet.getLong("ORGANIZATION_ID"));
    }

    private void setDataToHuntingGroundList(List<HuntingGround> huntingGrounds, ResultSet resultSet) throws SQLException {
        HuntingGround huntingGround = new HuntingGround();
        setParametersToHuntingGround(huntingGround, resultSet);
        huntingGrounds.add(huntingGround);
    }

    @Override
    public List<HuntingGround> getAll(Integer languageID) throws SQLException {
        List<HuntingGround> huntingGrounds = new ArrayList<>();
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_HUNTING_GROUNDS)) {
            preparedStatement.setInt(1, languageID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                setDataToHuntingGroundList(huntingGrounds, resultSet);
            }
        } finally {
            connectionPool.returnConnection(connection);
        }
        return huntingGrounds;
    }

    @Override
    public List<HuntingGround> getAllByOrganizationID(Long organizationID, Integer languageID) throws SQLException {
        List<HuntingGround> huntingGrounds = new ArrayList<>();
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_HUNTING_GROUNDS_BY_ORGANIZATION_ID)) {
            preparedStatement.setLong(1, organizationID);
            preparedStatement.setInt(2, languageID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                setDataToHuntingGroundList(huntingGrounds, resultSet);
            }
        } finally {
            connectionPool.returnConnection(connection);
        }
        return huntingGrounds;
    }

    @Override
    public HuntingGround getByID(Long id, Integer languageID) throws SQLException {
        HuntingGround huntingGround = new HuntingGround();
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_HUNTING_GROUND_BY_ID)) {
            preparedStatement.setLong(1, id);
            preparedStatement.setInt(2, languageID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                setParametersToHuntingGround(huntingGround, resultSet);
            }
        } finally {
            connectionPool.returnConnection(connection);
        }
        return huntingGround;
    }

    @Override
    public void update(Long huntingGroundID, Integer languageID, HuntingGround huntingGround) throws SQLException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_HUNTING_GROUND)) {
            preparedStatement.setString(1, huntingGround.getName());
            preparedStatement.setString(2, huntingGround.getDescription());
            preparedStatement.setString(3, huntingGround.getDistrict());
            preparedStatement.setLong(4, huntingGround.getOrganizationID());
            preparedStatement.setLong(5, huntingGroundID);
            preparedStatement.setInt(6, languageID);
            preparedStatement.executeUpdate();
        } finally {
            connectionPool.returnConnection(connection);
        }
    }
}
