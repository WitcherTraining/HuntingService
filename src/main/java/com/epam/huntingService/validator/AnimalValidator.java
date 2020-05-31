package com.epam.huntingService.validator;

import com.epam.huntingService.database.dao.interfaces.AnimalDAO;
import com.epam.huntingService.database.dao.impl.AnimalDAOImpl;
import com.epam.huntingService.entity.Animal;
import com.epam.huntingService.entity.AnimalLimitHistory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AnimalValidator {
    private static final int ANIMALS_NONE = 0;
    private static AnimalDAO animalDAO = new AnimalDAOImpl();
    private static AnimalValidator instance = new AnimalValidator();

    public boolean isAnimalExist(List<Animal> animals, String animalName) {
        boolean isExist = false;
        for (Animal an : animals) {
            if (an.getName().equalsIgnoreCase(animalName)) {
                isExist = true;
            }
        }
        return isExist;
    }

    public boolean isAnimalExist(List<String> animalNames, String[] animalsNameParams) {
        List<Boolean> checks = new ArrayList<>();
        boolean isExist = false;
        for (String animalName : animalsNameParams) {
            for (String name : animalNames) {
                if (name.equalsIgnoreCase(animalName)) {
                    checks.add(true);
                }
            }
        }
        if (checks.size() == animalsNameParams.length) {
            isExist = true;
        }
        return isExist;
    }

    public boolean isAnimalLimitExist(List<AnimalLimitHistory> limitHistories, String animalName) throws SQLException {
        boolean isLimitExist = false;
        Animal animalForCheck = animalDAO.takeByName(animalName);
        for (AnimalLimitHistory animalLimitHistory : limitHistories) {
            if (animalLimitHistory.getAnimalID().equals(animalForCheck.getId())) {
                if (animalLimitHistory.getAllLimit() > ANIMALS_NONE) {
                    isLimitExist = true;
                }
            }
        }
        return isLimitExist;
    }

    public static AnimalValidator getInstance() {
        if (instance == null) {
            instance = new AnimalValidator();
        }
        return instance;
    }
}
