package com.epam.huntingService.service;

import com.epam.huntingService.database.dao.factory.FactoryDAO;
import com.epam.huntingService.database.dao.impl.HuntingGroundDAOImpl;
import com.epam.huntingService.database.dao.interfaces.HuntingGroundDAO;
import com.epam.huntingService.entity.HuntingGround;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import static com.epam.huntingService.database.dao.factory.ImplEnum.HUNTING_GROUND_DAO;
import static com.epam.huntingService.util.constants.PageNameConstants.SEARCH_RESULTS_JSP;
import static com.epam.huntingService.util.constants.ParameterNamesConstants.*;

public class ShowHuntingGroundsByAnimalService implements Service {
    private FactoryDAO factoryDAO = FactoryDAO.getInstance();
    private HuntingGroundDAO huntingGroundDAO = (HuntingGroundDAOImpl) factoryDAO.getDAO(HUNTING_GROUND_DAO);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SQLException {
        RequestDispatcher dispatcher;
        HttpSession session = request.getSession(true);

        Integer languageID = (Integer) session.getAttribute(LANGUAGE_ID);

        Long animalID = Long.parseLong(request.getParameter(ANIMAL_ID));

        List<HuntingGround> huntingGroundsByAnimal = huntingGroundDAO.takeAllHuntingGroundsByAnimalID(animalID, languageID);

        session.setAttribute(HUNTING_GROUNDS, huntingGroundsByAnimal);
        dispatcher = request.getRequestDispatcher(SEARCH_RESULTS_JSP);
        dispatcher.forward(request, response);
    }
}
