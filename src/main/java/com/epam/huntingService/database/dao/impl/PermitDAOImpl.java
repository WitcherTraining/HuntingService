package com.epam.huntingService.database.dao.impl;

import com.epam.huntingService.database.connection.ConnectionPool;
import com.epam.huntingService.database.dao.interfaces.AnimalLimitHistoryDAO;
import com.epam.huntingService.database.dao.interfaces.AnimalQuotaHistoryDAO;
import com.epam.huntingService.database.dao.interfaces.PermitDAO;
import com.epam.huntingService.entity.Permit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.epam.huntingService.util.DateConverter.convert;

public class PermitDAOImpl implements PermitDAO {
    private static final String SEASON_PERMIT_TYPE = "Сезонная";
    private static final String DAILY_PERMIT_TYPE = "Суточная";
    private AnimalLimitHistoryDAO animalLimitHistoryDAO = new AnimalLimitHistoryDAOImpl();
    private AnimalQuotaHistoryDAO animalQuotaHistoryDAO = new AnimalQuotaHistoryDAOImpl();
    private static final String ADD_PERMIT = "INSERT INTO PERMIT (USER_ID, ANIMAL_ID, HUNTING_GROUND_ID, ORGANIZATION_ID," +
            " ORDER_DATE, COUNT_ORDERED_ANIMALS, PERMIT_TYPE, HUNTING_DAY, TOTAL_COST) VALUES (?,?,?,?,?,?,?,?,?)";
    private static final String SELECT_ALL_PERMITS = "SELECT * FROM PERMIT";
    private static final String SELECT_PERMIT_BY_ID = "SELECT ORDER_DATE, COUNT_ORDERED_ANIMALS, PERMIT_TYPE, " +
            "HUNTING_DAY, USER_ID, ANIMAL_ID, HUNTING_GROUND_ID, ORGANIZATION_ID, TOTAL_COST FROM PERMIT WHERE ID = ?";
    private static final String SELECT_ALL_PERMITS_BY_USER_ID = "SELECT * FROM PERMIT WHERE USER_ID = ?";

    private ConnectionPool connectionPool;
    private Connection connection;

    @Override
    public void create(Permit permit) throws SQLException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_PERMIT)) {
            java.sql.Date sqlPermitOrderDate = convert(permit.getOrderDate());
            java.sql.Date sqlPermitHuntingDay = convert(permit.getHuntingDay());
            preparedStatement.setLong(1, permit.getUserID());
            preparedStatement.setLong(2, permit.getAnimalID());
            preparedStatement.setLong(3, permit.getHuntingGroundID());
            preparedStatement.setLong(4, permit.getOrganizationID());
            preparedStatement.setDate(5, sqlPermitOrderDate);
            preparedStatement.setInt(6, permit.getCountOrderedAnimals());
            preparedStatement.setString(7, permit.getPermitType());
            preparedStatement.setDate(8, sqlPermitHuntingDay);
            Double totalCost = calculateTotalCost(permit.getCountOrderedAnimals(),
                    permit.getPermitType(), permit.getAnimalID(), permit.getHuntingGroundID());
            preparedStatement.setDouble(9, totalCost);
            preparedStatement.executeUpdate();
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    private Double calculateTotalCost(Integer countOrderedAnimals, String permitType, Long animalID, Long huntingGroundID)
            throws SQLException {
        double totalCost = 0.0;
        double animalCost = animalLimitHistoryDAO.getAnimalCost(animalID);
        double seasonPrice = animalQuotaHistoryDAO.getSeasonPrice(animalID, huntingGroundID);
        double dailyPrice = animalQuotaHistoryDAO.getDailyPrice(animalID, huntingGroundID);
        if (permitType.equals(DAILY_PERMIT_TYPE)) {
            totalCost = (animalCost * countOrderedAnimals) + dailyPrice;
        } else if (permitType.equals(SEASON_PERMIT_TYPE)) {
            totalCost = (animalCost * countOrderedAnimals) + seasonPrice;
        }
        return Math.ceil(totalCost);
    }

    private void setParametersToPermit(Permit permit, ResultSet resultSet) throws SQLException {
        permit.setId(resultSet.getLong("ID"));
        permit.setOrderDate(resultSet.getDate("ORDER_DATE"));
        permit.setCountOrderedAnimals(resultSet.getInt("COUNT_ORDERED_ANIMALS"));
        permit.setPermitType(resultSet.getString("PERMIT_TYPE"));
        permit.setHuntingDay(resultSet.getDate("HUNTING_DAY"));
        permit.setUserID(resultSet.getLong("USER_ID"));
        permit.setAnimalID(resultSet.getLong("ANIMAL_ID"));
        permit.setHuntingGroundID(resultSet.getLong("HUNTING_GROUND_ID"));
        permit.setOrganizationID(resultSet.getLong("ORGANIZATION_ID"));
        permit.setTotalCost(resultSet.getDouble("TOTAL_COST"));
    }

    private void setDataToPermitList(List<Permit> permits, ResultSet resultSet) throws SQLException {
        Permit permit = new Permit();
        setParametersToPermit(permit, resultSet);
        permits.add(permit);
    }

    @Override
    public List<Permit> getAllByUserID(Long userID, Integer languageID) throws SQLException {
        List<Permit> permits = new ArrayList<>();
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_PERMITS_BY_USER_ID)) {
            preparedStatement.setLong(1, userID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                setDataToPermitList(permits, resultSet);
            }
        } finally {
            connectionPool.returnConnection(connection);
        }
        return permits;
    }

    @Override
    public List<Permit> getAll(Integer languageID) throws SQLException {
        List<Permit> permits = new ArrayList<>();
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_PERMITS)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                setDataToPermitList(permits, resultSet);
            }
        } finally {
            connectionPool.returnConnection(connection);
        }
        return permits;
    }

    @Override
    public Permit getByID(Long id, Integer languageID) throws SQLException {
        Permit permit = new Permit();
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PERMIT_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                setParametersToPermit(permit, resultSet);
            }
        } finally {
            connectionPool.returnConnection(connection);
        }
        return permit;
    }

    @Override
    public void update(Long id, Integer languageID, Permit permit) throws SQLException {
        throw new UnsupportedOperationException();
    }
}
