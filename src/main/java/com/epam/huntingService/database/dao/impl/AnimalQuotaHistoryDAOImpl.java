package com.epam.huntingService.database.dao.impl;

import com.epam.huntingService.database.connection.ConnectionPool;
import com.epam.huntingService.database.dao.interfaces.AnimalQuotaHistoryDAO;
import com.epam.huntingService.entity.AnimalQuotaHistory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;
import java.sql.SQLException;

import static com.epam.huntingService.util.DateConverter.getCurrentYear;

public class AnimalQuotaHistoryDAOImpl implements AnimalQuotaHistoryDAO {
    private final static String UPDATE_QUOTA_FROM_HUNTING_GROUND_BY_ID = "UPDATE ANIMAL_QUOTA_HISTORY " +
            "SET ANIMAL_QUOTA = ? WHERE ANIMAL_ID = ? AND HUNTING_GROUND_ID = ?";
    private final static String GET_QUOTA__HISTORY_ANIMAL_BY_HUNTING_GROUND_AND_ANIMAL_ID_IN_THIS_YEAR =
            "SELECT * FROM ANIMAL_QUOTA_HISTORY WHERE HUNTING_GROUND_ID = ? AND ANIMAL_ID = ? AND YEAR = ?";
    private static final String GET_SEASON_PRICE_BY_ANIMAL_AND_HUNTING_GROUND = "SELECT SEASON_PRICE " +
            "FROM ANIMAL_QUOTA_HISTORY WHERE ANIMAL_ID = ? AND HUNTING_GROUND_ID = ? AND YEAR = ?";
    private static final String GET_DAILY_PRICE_BY_ANIMAL_AND_HUNTING_GROUND = "SELECT DAILY_PRICE " +
            "FROM ANIMAL_QUOTA_HISTORY WHERE ANIMAL_ID = ? AND HUNTING_GROUND_ID = ? AND YEAR = ?";
    private static final String SELECT_QUOTAS_BY_HUNTING_GROUND_ID = "SELECT * FROM ANIMAL_QUOTA_HISTORY WHERE HUNTING_GROUND_ID = ?";
    private static final String ADD_QUOTA = "INSERT INTO ANIMAL_QUOTA_HISTORY (YEAR, ANIMAL_QUOTA, DAILY_PRICE, " +
            "SEASON_PRICE, HUNTING_GROUND_ID, ANIMAL_ID) VALUES (?,?,?,?,?,?)";
    private static final String SELECT_QUOTA_BY_ID = "SELECT * FROM ANIMAL_QUOTA_HISTORY WHERE ID = ?";
    private static final String UPDATE_QUOTA_BY_ID = "UPDATE ANIMAL_QUOTA_HISTORY SET ANIMAL_QUOTA = ?, DAILY_PRICE = ?, " +
            "SEASON_PRICE = ? WHERE ID = ?";

    private ConnectionPool connectionPool;
    private Connection connection;

    @Override
    public Integer getQuotaByHuntingGroundAndAnimalID(Long huntingGroundID, Long animalID) throws SQLException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        int animalQuota = 0;
        Integer currentYear = getCurrentYear();
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(GET_QUOTA__HISTORY_ANIMAL_BY_HUNTING_GROUND_AND_ANIMAL_ID_IN_THIS_YEAR)) {
            preparedStatement.setLong(1, huntingGroundID);
            preparedStatement.setLong(2, animalID);
            preparedStatement.setInt(3, currentYear);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                animalQuota = resultSet.getInt("ANIMAL_QUOTA");
            }
        } finally {
            connectionPool.returnConnection(connection);
        }
        return animalQuota;
    }

    @Override
    public List<AnimalQuotaHistory> getAllByID(Long huntingGroundID) throws SQLException {
        List<AnimalQuotaHistory> animalQuotas = new ArrayList<>();
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUOTAS_BY_HUNTING_GROUND_ID)) {
            preparedStatement.setLong(1, huntingGroundID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                setDataForQuotaList(animalQuotas, resultSet);
            }
        } finally {
            connectionPool.returnConnection(connection);
        }
        return animalQuotas;
    }

    private void setDataForQuotaList(List<AnimalQuotaHistory> animalQuotas, ResultSet resultSet) throws SQLException {
        AnimalQuotaHistory history = new AnimalQuotaHistory();
        setParametersToHistory(history, resultSet);
        animalQuotas.add(history);
    }

    @Override
    public void updateQuotaAfterOrder(Integer updatedQuota, Long animalID, Long huntingGroundID) throws SQLException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUOTA_FROM_HUNTING_GROUND_BY_ID)) {
            preparedStatement.setInt(1, updatedQuota);
            preparedStatement.setLong(2, animalID);
            preparedStatement.setLong(3, huntingGroundID);
            preparedStatement.executeUpdate();
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public void create(AnimalQuotaHistory animalQuotaHistory) throws SQLException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_QUOTA)) {
            preparedStatement.setInt(1, animalQuotaHistory.getYear());
            preparedStatement.setInt(2, animalQuotaHistory.getAnimalQuota());
            preparedStatement.setDouble(3, animalQuotaHistory.getDailyPrice());
            preparedStatement.setDouble(4, animalQuotaHistory.getSeasonPrice());
            preparedStatement.setLong(5, animalQuotaHistory.getHuntingGroundID());
            preparedStatement.setLong(6, animalQuotaHistory.getAnimalID());
            preparedStatement.executeUpdate();
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public List<AnimalQuotaHistory> getAll(Integer languageID) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public AnimalQuotaHistory getByID(Long id, Integer languageID) throws SQLException {
        AnimalQuotaHistory animalQuotaHistory = new AnimalQuotaHistory();
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUOTA_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                setParametersToHistory(animalQuotaHistory, resultSet);
            }
        } finally {
            connectionPool.returnConnection(connection);
        }
        return animalQuotaHistory;
    }

    @Override
    public void update(Long id, Integer languageID, AnimalQuotaHistory animalQuotaHistory) throws SQLException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUOTA_BY_ID)) {
            preparedStatement.setInt(1, animalQuotaHistory.getAnimalQuota());
            preparedStatement.setDouble(2, animalQuotaHistory.getDailyPrice());
            preparedStatement.setDouble(3, animalQuotaHistory.getSeasonPrice());
            preparedStatement.setLong(4, id);
            preparedStatement.executeUpdate();
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public AnimalQuotaHistory getThisYearHistory(Long huntingGroundID, Long animalID) throws SQLException {
        AnimalQuotaHistory history = new AnimalQuotaHistory();
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        Integer currentYear = getCurrentYear();
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(GET_QUOTA__HISTORY_ANIMAL_BY_HUNTING_GROUND_AND_ANIMAL_ID_IN_THIS_YEAR)) {
            preparedStatement.setLong(1, huntingGroundID);
            preparedStatement.setLong(2, animalID);
            preparedStatement.setInt(3, currentYear);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                setParametersToHistory(history, resultSet);
            }
        } finally {
            connectionPool.returnConnection(connection);
        }
        return history;
    }

    @Override
    public Double getSeasonPrice(Long animalID, Long huntingGroundID) throws SQLException {
        double seasonPrice = 0.0;
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        Integer currentYear = getCurrentYear();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_SEASON_PRICE_BY_ANIMAL_AND_HUNTING_GROUND)) {
            preparedStatement.setLong(1, animalID);
            preparedStatement.setLong(2, huntingGroundID);
            preparedStatement.setInt(3, currentYear);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                seasonPrice = resultSet.getDouble("SEASON_PRICE");
            }
        } finally {
            connectionPool.returnConnection(connection);
        }
        return seasonPrice;
    }

    @Override
    public Double getDailyPrice(Long animalID, Long huntingGroundID) throws SQLException {
        double dailyPrice = 0.0;
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        Integer currentYear = getCurrentYear();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_DAILY_PRICE_BY_ANIMAL_AND_HUNTING_GROUND)) {
            preparedStatement.setLong(1, animalID);
            preparedStatement.setLong(2, huntingGroundID);
            preparedStatement.setInt(3, currentYear);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                dailyPrice = resultSet.getDouble("DAILY_PRICE");
            }
        } finally {
            connectionPool.returnConnection(connection);
        }
        return dailyPrice;
    }

    private void setParametersToHistory(AnimalQuotaHistory quota, ResultSet resultSet) throws SQLException {
        quota.setId(resultSet.getLong("ID"));
        quota.setYear(resultSet.getInt("YEAR"));
        quota.setAnimalQuota(resultSet.getInt("ANIMAL_QUOTA"));
        quota.setDailyPrice(resultSet.getDouble("DAILY_PRICE"));
        quota.setSeasonPrice(resultSet.getDouble("SEASON_PRICE"));
        quota.setHuntingGroundID(resultSet.getLong("HUNTING_GROUND_ID"));
        quota.setAnimalID(resultSet.getLong("ANIMAL_ID"));
    }
}
