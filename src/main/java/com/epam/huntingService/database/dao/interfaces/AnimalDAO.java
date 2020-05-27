package com.epam.huntingService.database.dao.interfaces;

import com.epam.huntingService.entity.Animal;

import java.sql.SQLException;
import java.util.List;

public interface AnimalDAO extends BaseDAO<Animal> {

    List<Animal> getAllAnimalsInHuntingGroundByID(Long id, Integer languageID) throws SQLException;

    List<Animal> takeAllLocalizedAnimalsByID(Long animalID) throws SQLException;

    Animal getByID(Long id, Integer languageID) throws SQLException;

    Animal takeByName(String animalName) throws SQLException;

    Long takeLastID() throws SQLException;

    List<String> takeAllLocalizedAnimalNames() throws SQLException;
}
