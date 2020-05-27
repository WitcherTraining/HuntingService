package com.epam.huntingService.service.factory;

import com.epam.huntingService.database.dao.factory.FactoryDAO;
import com.epam.huntingService.database.dao.impl.*;
import com.epam.huntingService.database.dao.interfaces.*;
import com.epam.huntingService.entity.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.epam.huntingService.database.dao.factory.ImplEnum.*;
import static com.epam.huntingService.util.ParameterNamesConstants.*;

public class HuntingGroundFactory {
    private static HuntingGroundFactory instance = new HuntingGroundFactory();
    private FactoryDAO factoryDAO = FactoryDAO.getInstance();
    private HuntingGroundDAO huntingGroundDAO = (HuntingGroundDAOImpl) factoryDAO.getDAO(HUNTING_GROUND_DAO);
    private OrganizationDAO organizationDAO = (OrganizationDAOImpl) factoryDAO.getDAO(ORGANIZATION_DAO);
    private AnimalDAO animalDAO = (AnimalDAOImpl) factoryDAO.getDAO(ANIMAL_DAO);
    private AnimalLimitHistoryDAO animalLimitHistoryDAO = (AnimalLimitHistoryDAOImpl) factoryDAO.getDAO(ANIMAL_LIMIT_HISTORY_DAO);
    private AnimalQuotaHistoryDAO animalQuotaHistoryDAO = (AnimalQuotaHistoryDAOImpl) factoryDAO.getDAO(ANIMAL_QUOTA_HISTORY_DAO);

    private HuntingGroundFactory() {
    }

    public HuntingGround fillHuntingGroundData(Long huntingGroundID, Integer languageID) throws SQLException, IOException {
        HuntingGround huntingGround = huntingGroundDAO.getByID(huntingGroundID, languageID);

        List<Animal> animals = animalDAO.getAllAnimalsInHuntingGroundByID(huntingGround.getId(), languageID);

        for (Animal animal : animals) {
            AnimalLimitHistory animalLimitHistory = animalLimitHistoryDAO.getByAnimalIDInThisYear(animal.getId(), languageID);
            AnimalQuotaHistory animalQuotaHistory = animalQuotaHistoryDAO.getThisYearHistory(huntingGroundID, animal.getId());
            animal.setAnimalLimitHistory(animalLimitHistory);
            animal.setAnimalQuotaHistory(animalQuotaHistory);
        }
        huntingGround.setAnimals(animals);
        Organization organization = organizationDAO.getByID(huntingGround.getOrganizationID(), languageID);
        huntingGround.setOrganization(organization);
        return huntingGround;
    }

    public List<HuntingGround> prepareHuntingGroundsInfo(Integer languageID) throws SQLException, IOException {
        List<HuntingGround> huntingGrounds = huntingGroundDAO.getAll(languageID);
        List<Organization> organizations = organizationDAO.getAll(languageID);

        for (HuntingGround hg : huntingGrounds) {
            for (Organization org : organizations) {
                if (hg.getOrganizationID().equals(org.getId())) {
                    hg.setOrganization(org);
                }
            }
        }

        for (HuntingGround huntingGround : huntingGrounds) {
            List<Animal> animals = animalDAO.getAllAnimalsInHuntingGroundByID(huntingGround.getId(), languageID);
            huntingGround.setAnimals(animals);
        }
        return huntingGrounds;
    }

    public List<HuntingGround> fillHuntingGroundsForCreating(HttpServletRequest request) throws SQLException { // wrapper
        List<HuntingGround> huntingGrounds = fillHuntingGroundsForUpdating(request);
        Long generatedID = generateNewHuntingGroundID();
        for (HuntingGround huntingGround : huntingGrounds) {
            huntingGround.setId(generatedID);
        }
        return huntingGrounds;
    }

    private Long generateNewHuntingGroundID() throws SQLException {
        return huntingGroundDAO.takeLastID() + INCREMENT_ID;
    }

    public List<HuntingGround> fillHuntingGroundsForUpdating(HttpServletRequest request) throws SQLException {
        List<HuntingGround> huntingGrounds = new ArrayList<>();

        for (int i = 0; i < request.getParameterValues(HUNTING_GROUND_NAME).length; i++) {
            HuntingGround huntingGround = new HuntingGround();
            Long orgID = organizationDAO.takeOrganizationIdByName(request.getParameterValues(ORGANIZATION_NAME)[i]);
            if (request.getParameterValues(HUNTING_GROUND_ID) != null) {
                huntingGround.setId(Long.parseLong(request.getParameterValues(HUNTING_GROUND_ID)[i]));
            }
            huntingGround.setLanguageID(Integer.parseInt(request.getParameterValues(LANGUAGE_ID_FOR_ADDING_TO_DATA_BASE)[i]));
            huntingGround.setName(request.getParameterValues(HUNTING_GROUND_NAME)[i]);
            huntingGround.setDescription(request.getParameterValues(DESCRIPTION)[i]);
            huntingGround.setDistrict(request.getParameterValues(DISTRICT)[i]);
            huntingGround.setOrganizationID(orgID);
            huntingGrounds.add(huntingGround);
        }
        return huntingGrounds;
    }

    public static HuntingGroundFactory getInstance() {
        if (instance == null) {
            instance = new HuntingGroundFactory();
        }
        return instance;
    }
}
