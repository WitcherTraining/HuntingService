package com.epam.huntingService.service;

import com.epam.huntingService.database.dao.factory.FactoryDAO;
import com.epam.huntingService.database.dao.interfaces.AnimalDAO;
import com.epam.huntingService.database.dao.impl.AnimalDAOImpl;
import com.epam.huntingService.entity.Animal;
import com.epam.huntingService.entity.AnimalLimitHistory;
import com.epam.huntingService.service.factory.LimitFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import static com.epam.huntingService.database.dao.factory.ImplEnum.ANIMAL_DAO;
import static com.epam.huntingService.util.DateConverter.*;
import static com.epam.huntingService.util.constants.ErrorConstants.WRONG_YEAR_DATA;
import static com.epam.huntingService.util.constants.ErrorConstants.YEAR_ERROR;
import static com.epam.huntingService.util.constants.PageNameConstants.LIMIT_JSP;
import static com.epam.huntingService.util.constants.ParameterNamesConstants.*;

public class ShowAnimalsLimitService implements Service {
    private FactoryDAO factoryDAO = FactoryDAO.getInstance();
    private LimitFactory limitFactory = LimitFactory.getInstance();
    private AnimalDAO animalDAO = (AnimalDAOImpl) factoryDAO.getDAO(ANIMAL_DAO);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SQLException {
        HttpSession session = request.getSession(true);
        RequestDispatcher dispatcher;

        Integer languageID = (Integer) session.getAttribute(LANGUAGE_ID);
        Integer chosenYear = Integer.parseInt(request.getParameter(CHOSEN_YEAR));
        List<Animal> animalsForLimit = animalDAO.getAll(languageID);
        java.sql.Date todaySql = new java.sql.Date(new Date().getTime());

        if (request.getParameter(CHOSEN_YEAR).length() != ZERO_REQUEST_LENGTH && chosenYear <= getCurrentYear()) {
            List<AnimalLimitHistory> animalLimitHistories = limitFactory.fillLimitList(languageID, chosenYear);
            session.setAttribute(ANIMALS_FOR_LIMIT, animalsForLimit);
            session.setAttribute(TODAY, todaySql);
            session.setAttribute(ANIMAL_LIMIT_HISTORIES, animalLimitHistories);
            session.setAttribute(CHOSEN_YEAR, chosenYear);
            session.setAttribute(CURRENT_YEAR, getCurrentYear());
            response.sendRedirect(LIMIT_JSP);
        } else {
            request.setAttribute(WRONG_YEAR_DATA, YEAR_ERROR);
            dispatcher = request.getRequestDispatcher(LIMIT_JSP);
            dispatcher.forward(request, response);
        }
    }
}
