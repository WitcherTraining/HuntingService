package com.epam.huntingService.service;

import com.epam.huntingService.database.dao.factory.FactoryDAO;
import com.epam.huntingService.database.dao.interfaces.AnimalDAO;
import com.epam.huntingService.database.dao.impl.AnimalDAOImpl;
import com.epam.huntingService.entity.Animal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import static com.epam.huntingService.database.dao.factory.ImplEnum.ANIMAL_DAO;
import static com.epam.huntingService.util.PageNameConstants.ANIMALS_JSP;
import static com.epam.huntingService.util.ParameterNamesConstants.ANIMALS;
import static com.epam.huntingService.util.ParameterNamesConstants.LANGUAGE_ID;

public class ShowAllAnimalsService implements Service {
    private FactoryDAO factoryDAO = FactoryDAO.getInstance();
    private AnimalDAO animalDAO = (AnimalDAOImpl) factoryDAO.getDAO(ANIMAL_DAO);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SQLException {
        HttpSession session = request.getSession(true);

        Integer languageID = (Integer) session.getAttribute(LANGUAGE_ID);
        List<Animal> animals = animalDAO.getAll(languageID);

        session.setAttribute(ANIMALS, animals);
        response.sendRedirect(ANIMALS_JSP);
    }
}
