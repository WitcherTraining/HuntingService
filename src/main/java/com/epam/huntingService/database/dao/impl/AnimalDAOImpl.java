package com.epam.huntingService.database.dao.impl;

import com.epam.huntingService.database.connection.ConnectionPool;
import com.epam.huntingService.database.dao.interfaces.AnimalDAO;
import com.epam.huntingService.entity.Animal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class AnimalDAOImpl implements AnimalDAO {
    private TemplateOperationsImpl templateOperations = TemplateOperationsImpl.getInstance();
    private static final String ADD_ANIMAL = "INSERT INTO ANIMAL (NAME, LANGUAGE_ID, ID) VALUES (?, ?, ?)";
    private static final String SELECT_ALL_ANIMALS = "SELECT * FROM ANIMAL WHERE LANGUAGE_ID = ?";
    private static final String SELECT_ALL_ANIMAL_NAMES = "SELECT NAME FROM ANIMAL";
    private static final String SELECT_ALL_LOCALIZED_ANIMALS_BY_ID = "SELECT * FROM ANIMAL WHERE ID = ?";
    private static final String SELECT_ANIMAL_BY_ID = "SELECT ID, NAME, LANGUAGE_ID FROM ANIMAL WHERE ID = ? AND LANGUAGE_ID = ?";
    private static final String SELECT_ANIMAL_BY_NAME = "SELECT * FROM ANIMAL WHERE NAME = ?";
    private static final String UPDATE_ANIMAL = "UPDATE ANIMAL SET NAME = ? WHERE ID = ? AND LANGUAGE_ID = ?";
    private static final String GET_QUOTA_ANIMALS_IN_HUNTING_GROUND_BY_ID = "SELECT A.ID, A.LANGUAGE_ID, A.NAME " +
            "FROM ANIMAL A, HUNTING_GROUND HG, ANIMAL_QUOTA_HISTORY AQH, LANGUAGE L " +
            "WHERE A.LANGUAGE_ID = L.ID AND HG.LANGUAGE_ID = L.ID AND AQH.ANIMAL_ID = A.ID " +
            "AND AQH.HUNTING_GROUND_ID = HG.ID AND HG.ID = ? AND L.ID = ?";
    private static final String SELECT_LAST_ID_FROM_ANIMAL = "SELECT MAX(ID) FROM ANIMAL";

    private ConnectionPool connectionPool;
    private Connection connection;

    @Override
    public Long takeLastID() throws SQLException {
        return templateOperations.extractLastID(SELECT_LAST_ID_FROM_ANIMAL);
    }

    @Override
    public List<Animal> takeAllLocalizedAnimalsByID(Long animalID) throws SQLException {
        List<Animal> animals = new ArrayList<>();
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_LOCALIZED_ANIMALS_BY_ID)) {
            preparedStatement.setLong(1, animalID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                setParametersToAnimalList(animals, resultSet);
            }
            for (Animal animal: animals){
                System.out.println(animal);
            }
        } finally {
            connectionPool.returnConnection(connection);
        }
        return animals;
    }

    @Override
    public void create(Animal animal) throws SQLException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_ANIMAL)) {
            preparedStatement.setString(1, animal.getName());
            preparedStatement.setLong(2, animal.getLanguageID());
            preparedStatement.setLong(3, animal.getId());
            preparedStatement.executeUpdate();
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public Animal takeByName(String animalName) throws SQLException {
        Animal animal = new Animal();
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ANIMAL_BY_NAME)) {
            preparedStatement.setString(1, animalName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                setParametersToAnimal(animal, resultSet);
            }
        } finally {
            connectionPool.returnConnection(connection);
        }
        return animal;
    }

    private void setParametersToAnimal(Animal animal, ResultSet resultSet) throws SQLException {
        animal.setId(resultSet.getLong("ID"));
        animal.setLanguageID(resultSet.getInt("LANGUAGE_ID"));
        animal.setName(resultSet.getString("NAME"));
    }

    private void setParametersToAnimalList(List<Animal> animals, ResultSet resultSet) throws SQLException {
        Animal animal = new Animal();
        setParametersToAnimal(animal, resultSet);
        animals.add(animal);
    }

    @Override
    public List<Animal> getAll(Integer languageID) throws SQLException {
        List<Animal> animals = new ArrayList<>();
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_ANIMALS)) {
            preparedStatement.setInt(1, languageID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                setParametersToAnimalList(animals, resultSet);
            }
        } finally {
            connectionPool.returnConnection(connection);
        }
        return animals;
    }

    @Override
    public List<String> takeAllLocalizedAnimalNames() throws SQLException {
        List<String> names = new ArrayList<>();
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_ANIMAL_NAMES)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                names.add(resultSet.getString("NAME"));
            }
        } finally {
            connectionPool.returnConnection(connection);
        }
        return names;
    }

    @Override
    public List<Animal> getAllAnimalsInHuntingGroundByID(Long huntingGroundID, Integer languageID) throws SQLException {
        List<Animal> animals = new ArrayList<>();
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_QUOTA_ANIMALS_IN_HUNTING_GROUND_BY_ID)) {
            preparedStatement.setLong(1, huntingGroundID);
            preparedStatement.setInt(2, languageID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                setParametersToAnimalList(animals, resultSet);
            }
        } finally {
            connectionPool.returnConnection(connection);
        }
        return animals;
    }

    @Override
    public Animal getByID(Long id, Integer languageID) throws SQLException {
        Animal animal = new Animal();
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ANIMAL_BY_ID)) {
            preparedStatement.setLong(1, id);
            preparedStatement.setInt(2, languageID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                setParametersToAnimal(animal, resultSet);
            }
        } finally {
            connectionPool.returnConnection(connection);
        }
        return animal;
    }

    @Override
    public void update(Long id, Integer languageID, Animal animal) throws SQLException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ANIMAL)) {
            preparedStatement.setString(1, animal.getName());
            preparedStatement.setLong(2, id);
            preparedStatement.setLong(3, languageID);
            preparedStatement.executeUpdate();
        } finally {
            connectionPool.returnConnection(connection);
        }
    }
}
