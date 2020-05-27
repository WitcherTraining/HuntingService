package com.epam.huntingService.service;

import com.epam.huntingService.database.dao.factory.FactoryDAO;
import com.epam.huntingService.database.dao.interfaces.AnimalLimitHistoryDAO;
import com.epam.huntingService.database.dao.impl.AnimalLimitHistoryDAOImpl;
import com.epam.huntingService.entity.AnimalLimitHistory;
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
import static com.epam.huntingService.util.PageNameConstants.ACCESS_ERROR_JSP;
import static com.epam.huntingService.util.PageNameConstants.EDIT_LIMIT_JSP;
import static com.epam.huntingService.util.ParameterNamesConstants.*;

public class PrepareAnimalLimitEditingService implements Service {
    private FactoryDAO factoryDAO = FactoryDAO.getInstance();
    private AnimalLimitHistoryDAO animalLimitHistoryDAO = (AnimalLimitHistoryDAOImpl) factoryDAO.getDAO(ANIMAL_LIMIT_HISTORY_DAO);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SQLException {
        HttpSession session = request.getSession(true);
        RequestDispatcher dispatcher;

        if (AccessValidator.checkAccess(ADMIN_ROLE_ID, session)){

            Long animalLimitID = Long.parseLong(request.getParameter(ANIMAL_LIMIT_ID));
            Integer year = Integer.parseInt(request.getParameter(YEAR));
            String animalName = request.getParameter(ANIMAL_NAME);

            AnimalLimitHistory animalLimitHistory = animalLimitHistoryDAO.getByIdAndYear(animalLimitID, year);

            session.setAttribute(ANIMAL_LIMIT_HISTORY, animalLimitHistory);
            session.setAttribute(ANIMAL_NAME, animalName);
            response.sendRedirect(EDIT_LIMIT_JSP);
        } else {
            dispatcher = request.getRequestDispatcher(ACCESS_ERROR_JSP);
            dispatcher.forward(request, response);
        }
    }
}
