package com.epam.huntingService.service;

import com.epam.huntingService.database.dao.factory.FactoryDAO;
import com.epam.huntingService.database.dao.interfaces.AnimalDAO;
import com.epam.huntingService.database.dao.interfaces.HuntingGroundDAO;
import com.epam.huntingService.database.dao.impl.AnimalDAOImpl;
import com.epam.huntingService.database.dao.impl.HuntingGroundDAOImpl;
import com.epam.huntingService.entity.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import static com.epam.huntingService.database.dao.factory.ImplEnum.ANIMAL_DAO;
import static com.epam.huntingService.database.dao.factory.ImplEnum.HUNTING_GROUND_DAO;
import static com.epam.huntingService.service.factory.QuotaFactory.prepareQuotas;
import static com.epam.huntingService.util.PageNameConstants.QUOTA_JSP;
import static com.epam.huntingService.util.ParameterNamesConstants.*;

public class ShowQuotaService implements Service {
    private FactoryDAO factoryDAO = FactoryDAO.getInstance();
    private AnimalDAO animalDAO = (AnimalDAOImpl) factoryDAO.getDAO(ANIMAL_DAO);
    private HuntingGroundDAO huntingGroundDAO = (HuntingGroundDAOImpl) factoryDAO.getDAO(HUNTING_GROUND_DAO);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SQLException {
        HttpSession session = request.getSession(true);

        Integer languageID = (Integer) session.getAttribute(LANGUAGE_ID);
        Long huntingGroundID = Long.parseLong(request.getParameter(HUNTING_GROUND_ID));
        HuntingGround huntingGround = huntingGroundDAO.getByID(huntingGroundID, languageID);
        List<AnimalQuotaHistory> animalQuotas = prepareQuotas(huntingGroundID, languageID);
        List<Animal> animals = animalDAO.getAll(languageID);

        session.setAttribute(ANIMAL_QUOTAS, animalQuotas);
        session.setAttribute(HUNTING_GROUND, huntingGround);
        session.setAttribute(ANIMALS, animals);
        response.sendRedirect(QUOTA_JSP);
    }
}
