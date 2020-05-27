package com.epam.huntingService.database.dao.impl;

import com.epam.huntingService.database.connection.ConnectionPool;
import com.epam.huntingService.database.dao.interfaces.AnimalLimitHistoryDAO;
import com.epam.huntingService.entity.AnimalLimitHistory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.epam.huntingService.util.DateConverter.convert;
import static com.epam.huntingService.util.DateConverter.getCurrentYear;

public class AnimalLimitHistoryDAOImpl implements AnimalLimitHistoryDAO {
    private static final String ADD_GLOBAL_LIMIT_HISTORY = "INSERT INTO ANIMAL_LIMIT_HISTORY " +
            " (YEAR, ALL_LIMIT, MONTHLY_CALCULATION_INDEX, ANIMAL_COST_IN_MCI, ANIMAL_COST, TERM_BEGIN, TERM_END, ANIMAL_ID) " +
            "VALUES (?,?,?,?,(MONTHLY_CALCULATION_INDEX * ANIMAL_COST_IN_MCI),?,?,?)";
    private static final String GET_BY_ANIMAL_ID_IN_THIS_YEAR = "SELECT * FROM ANIMAL_LIMIT_HISTORY HAL, ANIMAL A, LANGUAGE L " +
            "WHERE A.LANGUAGE_ID = L.ID AND HAL.ANIMAL_ID = A.ID AND A.ID = ? AND L.ID = ? AND YEAR = ?";
    private static final String GET_BY_ID_AND_YEAR = "SELECT * FROM ANIMAL_LIMIT_HISTORY WHERE ID = ? AND YEAR = ?";
    private static final String GET_ANIMAL_COST = "SELECT ANIMAL_COST FROM ANIMAL_LIMIT_HISTORY " +
            "WHERE ANIMAL_ID = ? AND YEAR = ?";
    private static final String SELECT_ALL_BY_YEAR = "SELECT * FROM ANIMAL_LIMIT_HISTORY WHERE YEAR = ?";
    private static final String UPDATE_THIS_YEAR_ANIMAL_LIMIT = "UPDATE ANIMAL_LIMIT_HISTORY SET ALL_LIMIT = ?, " +
            " MONTHLY_CALCULATION_INDEX = ?, ANIMAL_COST_IN_MCI = ?, " +
            "ANIMAL_COST = (MONTHLY_CALCULATION_INDEX * ANIMAL_COST_IN_MCI), TERM_BEGIN = ?, TERM_END = ? WHERE (ID = ?)";
    private static final String SELECT_FIRST_YEAR_FOR_LIMIT = "SELECT MIN(YEAR) FROM ANIMAL_LIMIT_HISTORY";

    private ConnectionPool connectionPool;
    private Connection connection;

    @Override
    public Integer takeFirstYearForLimitHistory() throws SQLException {
        int firstYearLimit = 0;
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement(SELECT_FIRST_YEAR_FOR_LIMIT)){
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                firstYearLimit = resultSet.getInt("MIN(YEAR)");
            }
        } finally {
            connectionPool.returnConnection(connection);
        }
        return firstYearLimit;
    }

    @Override
    public List<AnimalLimitHistory> getAllByYear(Integer year) throws SQLException {
        List<AnimalLimitHistory> animalLimitHistories = new ArrayList<>();
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_BY_YEAR)) {
            preparedStatement.setInt(1, year);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                AnimalLimitHistory animalLimitHistory = new AnimalLimitHistory();
                setParametersToAnimalLimitHistory(animalLimitHistory, resultSet);
                animalLimitHistories.add(animalLimitHistory);
            }
        } finally {
            connectionPool.returnConnection(connection);
        }
        return animalLimitHistories;
    }

    @Override
    public Double getAnimalCost(Long animalID) throws SQLException {
        double animalCost = 0.0;
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        Integer currentYear = getCurrentYear();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ANIMAL_COST)) {
            preparedStatement.setLong(1, animalID);
            preparedStatement.setInt(2, currentYear);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                animalCost = resultSet.getDouble("ANIMAL_COST");
            }
        } finally {
            connectionPool.returnConnection(connection);
        }
        return animalCost;
    }

    @Override
    public AnimalLimitHistory getByAnimalIDInThisYear(Long animalID, Integer languageID) throws SQLException {
        AnimalLimitHistory animalLimitHistory = new AnimalLimitHistory();
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        Integer currentYear = getCurrentYear();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_ANIMAL_ID_IN_THIS_YEAR)) {
            preparedStatement.setLong(1, animalID);
            preparedStatement.setInt(2, languageID);
            preparedStatement.setInt(3, currentYear);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                setParametersToAnimalLimitHistory(animalLimitHistory, resultSet);
            }
        } finally {
            connectionPool.returnConnection(connection);
        }
        return animalLimitHistory;
    }

    private void setParametersToAnimalLimitHistory(AnimalLimitHistory animalLimitHistory, ResultSet resultSet) throws SQLException {
        animalLimitHistory.setId(resultSet.getLong("ID"));
        animalLimitHistory.setYear(resultSet.getInt("YEAR"));
        animalLimitHistory.setAllLimit(resultSet.getInt("ALL_LIMIT"));
        animalLimitHistory.setMonthlyCalculationIndex(resultSet.getDouble("MONTHLY_CALCULATION_INDEX"));
        animalLimitHistory.setAnimalCostInMCI(resultSet.getDouble("ANIMAL_COST_IN_MCI"));
        animalLimitHistory.setAnimalCost(resultSet.getDouble("ANIMAL_COST"));
        animalLimitHistory.setTermBegin(resultSet.getDate("TERM_BEGIN"));
        animalLimitHistory.setTermEnd(resultSet.getDate("TERM_END"));
        animalLimitHistory.setAnimalID(resultSet.getLong("ANIMAL_ID"));
    }

    @Override
    public void create(AnimalLimitHistory animalLimitHistory) throws SQLException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_GLOBAL_LIMIT_HISTORY)) {
            java.sql.Date sqlTermBegin = convert(animalLimitHistory.getTermBegin());
            java.sql.Date sqlTermEnd = convert(animalLimitHistory.getTermEnd());
            preparedStatement.setInt(1, animalLimitHistory.getYear());
            preparedStatement.setInt(2, animalLimitHistory.getAllLimit());
            preparedStatement.setDouble(3, animalLimitHistory.getMonthlyCalculationIndex());
            preparedStatement.setDouble(4, animalLimitHistory.getAnimalCostInMCI());
            preparedStatement.setDate(5, sqlTermBegin);
            preparedStatement.setDate(6, sqlTermEnd);
            preparedStatement.setLong(7, animalLimitHistory.getAnimalID());
            preparedStatement.executeUpdate();
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public List<AnimalLimitHistory> getAll(Integer languageID) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public AnimalLimitHistory getByID(Long id, Integer languageID) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public AnimalLimitHistory getByIdAndYear(Long id, Integer year) throws SQLException {
        AnimalLimitHistory animalLimitHistory = new AnimalLimitHistory();
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_ID_AND_YEAR)) {
            preparedStatement.setLong(1, id);
            preparedStatement.setInt(2, year);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                setParametersToAnimalLimitHistory(animalLimitHistory, resultSet);
            }
        } finally {
            connectionPool.returnConnection(connection);
        }
        return animalLimitHistory;
    }

    @Override
    public void update(Long id, Integer languageID, AnimalLimitHistory animalLimitHistory) throws SQLException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_THIS_YEAR_ANIMAL_LIMIT)) {
            java.sql.Date sqlTermBegin = convert(animalLimitHistory.getTermBegin());
            java.sql.Date sqlTermEnd = convert(animalLimitHistory.getTermEnd());
            preparedStatement.setInt(1, animalLimitHistory.getAllLimit());
            preparedStatement.setDouble(2, animalLimitHistory.getMonthlyCalculationIndex());
            preparedStatement.setDouble(3, animalLimitHistory.getAnimalCostInMCI());
            preparedStatement.setDate(4, sqlTermBegin);
            preparedStatement.setDate(5, sqlTermEnd);
            preparedStatement.setLong(6, animalLimitHistory.getId());
            preparedStatement.executeUpdate();
        } finally {
            connectionPool.returnConnection(connection);
        }
    }
}
