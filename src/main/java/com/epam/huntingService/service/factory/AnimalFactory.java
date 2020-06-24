package com.epam.huntingService.service.factory;

import com.epam.huntingService.database.dao.factory.FactoryDAO;
import com.epam.huntingService.database.dao.interfaces.AnimalDAO;
import com.epam.huntingService.database.dao.impl.AnimalDAOImpl;
import com.epam.huntingService.entity.Animal;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.epam.huntingService.database.dao.factory.ImplEnum.ANIMAL_DAO;
import static com.epam.huntingService.util.constants.ParameterNamesConstants.*;

public class AnimalFactory {
    private static AnimalFactory instance = new AnimalFactory();
    private FactoryDAO factoryDAO = FactoryDAO.getInstance();
    private AnimalDAO animalDAO = (AnimalDAOImpl) factoryDAO.getDAO(ANIMAL_DAO);

    private AnimalFactory() {
    }

    public List<Animal> fillAnimalsForCreating(HttpServletRequest request) throws SQLException { // wrapper
        List<Animal> animals = fillAnimalsForUpdating(request);
        Long generatedID = generateNewAnimalID();
        for (Animal animal : animals) {
            animal.setId(generatedID);
        }
        return animals;
    }

    private Long generateNewAnimalID() throws SQLException {
        return animalDAO.takeLastID() + INCREMENT_ID;
    }

    public List<Animal> fillAnimalsForUpdating(HttpServletRequest request) {
        List<Animal> animals = new ArrayList<>();
        String[] animalsNameParams = request.getParameterValues(ANIMAL_NAME);
        String[] langIDForAdding = request.getParameterValues(LANGUAGE_ID_FOR_ADDING_TO_DATA_BASE);

        for (int i = 0; i < animalsNameParams.length; i++) {
            Animal animal = new Animal();
            if (request.getParameterValues(ANIMAL_ID) != null) {
                animal.setId(Long.parseLong(request.getParameterValues(ANIMAL_ID)[i]));
            }
            animal.setLanguageID(Integer.parseInt(langIDForAdding[i]));
            animal.setName(animalsNameParams[i]);
            animals.add(animal);
        }
        return animals;
    }

    public static AnimalFactory getInstance() {
        if (instance == null) {
            instance = new AnimalFactory();
        }
        return instance;
    }
}
