package com.epam.huntingService.service;

import com.epam.huntingService.database.dao.factory.FactoryDAO;
import com.epam.huntingService.database.dao.interfaces.AnimalLimitHistoryDAO;
import com.epam.huntingService.database.dao.impl.AnimalLimitHistoryDAOImpl;
import com.epam.huntingService.entity.AnimalLimitHistory;
import com.epam.huntingService.service.factory.LimitFactory;
import com.epam.huntingService.validator.AccessValidator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

import static com.epam.huntingService.database.dao.factory.ImplEnum.ANIMAL_LIMIT_HISTORY_DAO;
import static com.epam.huntingService.util.ErrorConstants.EMPTY_DATA;
import static com.epam.huntingService.util.ErrorConstants.FILL_DATA_ERROR;
import static com.epam.huntingService.util.PageNameConstants.ACCESS_ERROR_JSP;
import static com.epam.huntingService.util.PageNameConstants.EDIT_LIMIT_JSP;
import static com.epam.huntingService.util.ParameterNamesConstants.*;
import static com.epam.huntingService.service.ServiceConstants.SHOW_ANIMALS_LIMIT_SERVICE;

public class EditAnimalLimitService implements Service {
    private FactoryDAO factoryDAO = FactoryDAO.getInstance();
    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private LimitFactory limitFactory = LimitFactory.getInstance();
    private AnimalLimitHistoryDAO animalLimitHistoryDAO = (AnimalLimitHistoryDAOImpl) factoryDAO.getDAO(ANIMAL_LIMIT_HISTORY_DAO);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SQLException {
        HttpSession session = request.getSession(true);
        RequestDispatcher dispatcher;

        if (AccessValidator.checkAccess(ADMIN_ROLE_ID, session)){
            Integer languageID = (Integer) session.getAttribute(LANGUAGE_ID);

            if (request.getParameter(ALL_LIMIT).length() != ZERO_REQUEST_LENGTH ||
                    request.getParameter(MCI).length() != ZERO_REQUEST_LENGTH ||
                    request.getParameter(COST_IN_MCI).length() != ZERO_REQUEST_LENGTH ||
                    request.getParameter(TERM_BEGIN).length() != ZERO_REQUEST_LENGTH ||
                    request.getParameter(TERM_END).length() != ZERO_REQUEST_LENGTH) {
                AnimalLimitHistory animalLimitHistory = limitFactory.fillAnimalLimitHistory(request, session);
                animalLimitHistory.setId(Long.parseLong(request.getParameter(LIMIT_ID)));

                animalLimitHistoryDAO.update(animalLimitHistory.getId(), languageID, animalLimitHistory);
                serviceFactory.getService(SHOW_ANIMALS_LIMIT_SERVICE).execute(request, response);
            } else {
                request.setAttribute(EMPTY_DATA, FILL_DATA_ERROR);
                dispatcher = request.getRequestDispatcher(EDIT_LIMIT_JSP);
                dispatcher.forward(request, response);
            }
        } else {
            dispatcher = request.getRequestDispatcher(ACCESS_ERROR_JSP);
            dispatcher.forward(request, response);
        }
    }
}
