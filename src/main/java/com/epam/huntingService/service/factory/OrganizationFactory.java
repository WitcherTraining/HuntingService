package com.epam.huntingService.service.factory;

import com.epam.huntingService.database.dao.factory.FactoryDAO;
import com.epam.huntingService.database.dao.interfaces.AnimalDAO;
import com.epam.huntingService.database.dao.interfaces.AnimalQuotaHistoryDAO;
import com.epam.huntingService.database.dao.impl.*;
import com.epam.huntingService.database.dao.interfaces.HuntingGroundDAO;
import com.epam.huntingService.database.dao.interfaces.OrganizationDAO;
import com.epam.huntingService.entity.Animal;
import com.epam.huntingService.entity.AnimalQuotaHistory;
import com.epam.huntingService.entity.HuntingGround;
import com.epam.huntingService.entity.Organization;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.epam.huntingService.database.dao.factory.ImplEnum.*;
import static com.epam.huntingService.util.ParameterNamesConstants.*;

public class OrganizationFactory {
    private static OrganizationFactory instance = new OrganizationFactory();
    private FactoryDAO factoryDAO = FactoryDAO.getInstance();
    private AnimalDAO animalDAO = (AnimalDAOImpl) factoryDAO.getDAO(ANIMAL_DAO);
    private AnimalQuotaHistoryDAO animalQuotaHistoryDAO = (AnimalQuotaHistoryDAOImpl) factoryDAO.getDAO(ANIMAL_QUOTA_HISTORY_DAO);
    private HuntingGroundDAO huntingGroundDAO = (HuntingGroundDAOImpl) factoryDAO.getDAO(HUNTING_GROUND_DAO);
    private OrganizationDAO organizationDAO = (OrganizationDAOImpl) factoryDAO.getDAO(ORGANIZATION_DAO);

    private OrganizationFactory() {
    }

    public Organization fillOrganizationData(Long organizationID, Integer languageID) throws SQLException, IOException {
        Organization organization = organizationDAO.getByID(organizationID, languageID);
        List<HuntingGround> huntingGrounds = huntingGroundDAO.getAllByOrganizationID(organizationID, languageID);
        for (HuntingGround hg : huntingGrounds) {
            List<Animal> animals = animalDAO.getAllAnimalsInHuntingGroundByID(hg.getId(), languageID);
            for (Animal animal : animals) {
                AnimalQuotaHistory animalQuotaHistory = animalQuotaHistoryDAO.getThisYearHistory(hg.getId(), animal.getId());
                animal.setAnimalQuotaHistory(animalQuotaHistory);
            }
            hg.setAnimals(animals);
        }
        organization.setHuntingGrounds(huntingGrounds);
        return organization;
    }

    public List<Organization> prepareOrganizationInfo(Integer languageID) throws SQLException, IOException {
        List<Organization> organizations = organizationDAO.getAll(languageID);

        for (Organization organization : organizations) {
            List<HuntingGround> huntingGrounds = huntingGroundDAO.getAllByOrganizationID(organization.getId(), languageID);
            organization.setHuntingGrounds(huntingGrounds);
        }

        return organizations;
    }

    public List<Organization> fillOrganizationsForCreating(HttpServletRequest request) throws SQLException, IOException, ServletException { // wrapper
        List<Organization> organizations = fillOrganizationsForUpdating(request);
        Long generatedID = generateNewOrganizationID();
        for (Organization organization : organizations) {
            organization.setId(generatedID);
        }
        return organizations;
    }

    private Long generateNewOrganizationID() throws SQLException {
        return organizationDAO.takeLastID() + INCREMENT_ID;
    }

    public List<Organization> fillOrganizationsForUpdating(HttpServletRequest request) throws IOException, ServletException {
        List<Organization> organizations = new ArrayList<>();
        String[] organizationIDParams = request.getParameterValues(ORGANIZATION_ID);
        String[] organizationNameParams = request.getParameterValues(ORGANIZATION_NAME);
        String[] langIDForAdding = request.getParameterValues(LANGUAGE_ID_FOR_ADDING_TO_DATA_BASE);
        String[] descriptionParams = request.getParameterValues(ORGANIZATION_DESCRIPTION);

        Collection<Part> parts = request.getParts();

        for (int i = 0; i < organizationNameParams.length; i++) {
            Organization organization = new Organization();
            if (organizationIDParams != null){
                organization.setId(Long.parseLong(organizationIDParams[i]));
            }
            organization.setLanguageID(Integer.parseInt(langIDForAdding[i]));
            organization.setName(organizationNameParams[i]);
            for (Part part : parts) {
                if (part.getName().equalsIgnoreCase(LOGO)) {
                    try (InputStream fileInputStream = part.getInputStream()) {
                        organization.setUploadingLogo(fileInputStream);
                    }
                }
            }
            organization.setDescription(descriptionParams[i]);
            organizations.add(organization);
        }
        return organizations;
    }

    public static OrganizationFactory getInstance() {
        if (instance == null) {
            instance = new OrganizationFactory();
        }
        return instance;
    }
}
