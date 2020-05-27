package com.epam.huntingService.service;

import com.epam.huntingService.database.dao.factory.FactoryDAO;
import com.epam.huntingService.database.dao.interfaces.AnimalDAO;
import com.epam.huntingService.database.dao.interfaces.HuntingGroundDAO;
import com.epam.huntingService.database.dao.interfaces.OrganizationDAO;
import com.epam.huntingService.database.dao.impl.AnimalDAOImpl;
import com.epam.huntingService.database.dao.impl.HuntingGroundDAOImpl;
import com.epam.huntingService.database.dao.impl.OrganizationDAOImpl;
import com.epam.huntingService.entity.Animal;
import com.epam.huntingService.entity.HuntingGround;
import com.epam.huntingService.entity.Organization;
import com.epam.huntingService.service.factory.HuntingGroundFactory;
import com.epam.huntingService.service.factory.OrganizationFactory;
import com.epam.huntingService.validator.AnimalValidator;
import com.epam.huntingService.validator.HuntingGroundValidator;
import com.epam.huntingService.validator.OrganizationValidator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import static com.epam.huntingService.database.dao.factory.ImplEnum.*;
import static com.epam.huntingService.util.ErrorConstants.*;
import static com.epam.huntingService.util.PageNameConstants.SEARCH_RESULTS_JSP;
import static com.epam.huntingService.util.ParameterNamesConstants.*;

public class SearchService implements Service {
    private FactoryDAO factoryDAO = FactoryDAO.getInstance();
    private HuntingGroundFactory huntingGroundFactory = HuntingGroundFactory.getInstance();
    private OrganizationFactory organizationFactory = OrganizationFactory.getInstance();
    private HuntingGroundDAO huntingGroundDAO = (HuntingGroundDAOImpl) factoryDAO.getDAO(HUNTING_GROUND_DAO);
    private AnimalDAO animalDAO = (AnimalDAOImpl) factoryDAO.getDAO(ANIMAL_DAO);
    private OrganizationDAO organizationDAO = (OrganizationDAOImpl) factoryDAO.getDAO(ORGANIZATION_DAO);
    private static final String HUNTING_GROUNDS_WITH_THIS_ANIMAL = "huntingGroundsWithThisAnimal";
    private static final String HUNTING_GROUNDS_BY_DISTRICT = "huntingGroundsByDistrict";
    private RequestDispatcher dispatcher;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException,
            ParseException, SQLException {
        HttpSession session = request.getSession(true);

        Integer languageID = (Integer) session.getAttribute(LANGUAGE_ID);
        String searchSequence = request.getParameter(SEARCH_SEQUENCE);
        session.setAttribute(SEARCH_SEQUENCE, searchSequence);
        List<HuntingGround> huntingGrounds = huntingGroundFactory.prepareHuntingGroundsInfo(languageID);
        List<Organization> organizations = organizationFactory.prepareOrganizationInfo(languageID);
        List<Animal> animals = animalDAO.getAll(languageID);

        if (HuntingGroundValidator.isHuntingGroundExist(huntingGrounds, searchSequence)) {
            Long huntingGroundID = huntingGroundDAO.takeIDByName(searchSequence);
            setSearchingParameters(request, response, session, searchSequence, huntingGroundID, HUNTING_GROUND_ID,
                    HUNTING_GROUND_NAME);
        } else if (AnimalValidator.isAnimalExist(animals, searchSequence)) {
            List<HuntingGround> huntingGroundsWithThisAnimal =
                    huntingGroundDAO.getAllWithThisAnimal(searchSequence, languageID);
            session.setAttribute(HUNTING_GROUNDS_WITH_THIS_ANIMAL, huntingGroundsWithThisAnimal);
            dispatcher = request.getRequestDispatcher(SEARCH_RESULTS_JSP);
            dispatcher.forward(request, response);
            session.removeAttribute(HUNTING_GROUNDS_WITH_THIS_ANIMAL);
        } else if (OrganizationValidator.isOrganizationExist(organizations, searchSequence)) {
            Long organizationID = organizationDAO.takeOrganizationIdByName(searchSequence);
            setSearchingParameters(request, response, session, searchSequence, organizationID, ORGANIZATION_ID,
                    ORGANIZATION_NAME);
        } else if (HuntingGroundValidator.isDistrictExist(huntingGrounds, searchSequence)) {
            List<HuntingGround> huntingGroundsByDistrict =
                    huntingGroundDAO.takeAllHuntingGroundsInDistrict(languageID, searchSequence);
            session.setAttribute(HUNTING_GROUNDS_BY_DISTRICT, huntingGroundsByDistrict);
            dispatcher = request.getRequestDispatcher(SEARCH_RESULTS_JSP);
            dispatcher.forward(request, response);
            session.removeAttribute(HUNTING_GROUNDS_BY_DISTRICT);
        } else if (searchSequence.length() == ZERO_REQUEST_LENGTH) {
            request.setAttribute(EMPTY_SEARCH, EMPTY_SEARCH_ERROR);
            dispatcher = request.getRequestDispatcher(SEARCH_RESULTS_JSP);
            dispatcher.forward(request, response);
        } else {
            request.setAttribute(FOUND_NOTHING, FOUND_NOTHING_ERROR);
            dispatcher = request.getRequestDispatcher(SEARCH_RESULTS_JSP);
            dispatcher.forward(request, response);
        }
    }

    private void setSearchingParameters(HttpServletRequest request, HttpServletResponse response,
                                        HttpSession session, String searchSequence, Long searchingObjectID,
                                        String firstParam, String secondParam) throws ServletException, IOException {
        session.setAttribute(firstParam, searchingObjectID);
        session.setAttribute(secondParam, searchSequence);
        dispatcher = request.getRequestDispatcher(SEARCH_RESULTS_JSP);
        dispatcher.forward(request, response);
        session.removeAttribute(firstParam);
        session.removeAttribute(secondParam);
    }
}
