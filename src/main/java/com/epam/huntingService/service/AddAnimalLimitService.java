package com.epam.huntingService.service;

import com.epam.huntingService.database.dao.factory.FactoryDAO;
import com.epam.huntingService.database.dao.interfaces.AnimalDAO;
import com.epam.huntingService.database.dao.interfaces.AnimalLimitHistoryDAO;
import com.epam.huntingService.database.dao.impl.AnimalDAOImpl;
import com.epam.huntingService.database.dao.impl.AnimalLimitHistoryDAOImpl;
import com.epam.huntingService.entity.Animal;
import com.epam.huntingService.entity.AnimalLimitHistory;
import com.epam.huntingService.service.factory.LimitFactory;
import com.epam.huntingService.validator.AccessValidator;
import com.epam.huntingService.validator.AnimalValidator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import static com.epam.huntingService.database.dao.factory.ImplEnum.ANIMAL_DAO;
import static com.epam.huntingService.database.dao.factory.ImplEnum.ANIMAL_LIMIT_HISTORY_DAO;
import static com.epam.huntingService.util.DateConverter.getCurrentYear;
import static com.epam.huntingService.util.ErrorConstants.*;
import static com.epam.huntingService.util.PageNameConstants.ACCESS_ERROR_JSP;
import static com.epam.huntingService.util.PageNameConstants.LIMIT_JSP;
import static com.epam.huntingService.util.ParameterNamesConstants.*;
import static com.epam.huntingService.service.ServiceConstants.ADD_ANIMAL_LIMIT_SERVICE;

public class AddAnimalLimitService implements Service {
    private FactoryDAO factoryDAO = FactoryDAO.getInstance();
    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private LimitFactory limitFactory = LimitFactory.getInstance();
    private AnimalValidator animalValidator = AnimalValidator.getInstance();
    private AnimalLimitHistoryDAO animalLimitHistoryDAO = (AnimalLimitHistoryDAOImpl) factoryDAO.getDAO(ANIMAL_LIMIT_HISTORY_DAO);
    private AnimalDAO animalDAO = (AnimalDAOImpl) factoryDAO.getDAO(ANIMAL_DAO);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SQLException {
        HttpSession session = request.getSession(true);
        RequestDispatcher dispatcher;

        Integer languageID = (Integer) session.getAttribute(LANGUAGE_ID);

        if (AccessValidator.checkAccess(ADMIN_ROLE_ID, session)) {

            List<Animal> animals = animalDAO.getAll(languageID);
            List<AnimalLimitHistory> limitHistories = animalLimitHistoryDAO.getAllByYear(getCurrentYear());

            if (request.getParameter(ANIMAL_NAME).length() == ZERO_REQUEST_LENGTH ||
                    request.getParameter(ALL_LIMIT).length() == ZERO_REQUEST_LENGTH ||
                    request.getParameter(MCI).length() == ZERO_REQUEST_LENGTH ||
                    request.getParameter(COST_IN_MCI).length() == ZERO_REQUEST_LENGTH ||
                    request.getParameter(TERM_BEGIN).length() == ZERO_REQUEST_LENGTH ||
                    request.getParameter(TERM_END).length() == ZERO_REQUEST_LENGTH) {
                request.setAttribute(EMPTY_DATA, FILL_DATA_ERROR);
                dispatcher = request.getRequestDispatcher(LIMIT_JSP);
                dispatcher.forward(request, response);
            } else if (!animalValidator.isAnimalExist(animals, request.getParameter(ANIMAL_NAME))) {
                request.setAttribute(ANIMAL_IS_NOT_EXIST_IN_SERVICE, NOT_EXISTING_ANIMAL_ERROR);
                dispatcher = request.getRequestDispatcher(LIMIT_JSP);
                dispatcher.forward(request, response);
            } else if (animalValidator.isAnimalLimitExist(limitHistories, request.getParameter(ANIMAL_NAME))) {
                request.setAttribute(WRONG_LIMIT_DATA, LIMIT_DATA_ERROR);
                dispatcher = request.getRequestDispatcher(LIMIT_JSP);
                dispatcher.forward(request, response);
            } else {
                AnimalLimitHistory animalLimitHistory = limitFactory.fillAnimalLimitHistory(request, session);
                animalLimitHistoryDAO.create(animalLimitHistory);
                serviceFactory.getService(ADD_ANIMAL_LIMIT_SERVICE).execute(request, response);
            }
        } else {
            dispatcher = request.getRequestDispatcher(ACCESS_ERROR_JSP);
            dispatcher.forward(request, response);
        }
    }
}
