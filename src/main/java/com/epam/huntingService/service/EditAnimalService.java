package com.epam.huntingService.service;

import com.epam.huntingService.database.dao.factory.FactoryDAO;
import com.epam.huntingService.database.dao.interfaces.AnimalDAO;
import com.epam.huntingService.database.dao.impl.AnimalDAOImpl;
import com.epam.huntingService.entity.Animal;
import com.epam.huntingService.service.factory.AnimalFactory;
import com.epam.huntingService.validator.AccessValidator;

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
import static com.epam.huntingService.util.ErrorConstants.*;
import static com.epam.huntingService.util.PageNameConstants.ACCESS_ERROR_JSP;
import static com.epam.huntingService.util.PageNameConstants.EDIT_ANIMAL_JSP;
import static com.epam.huntingService.util.ParameterNamesConstants.*;
import static com.epam.huntingService.service.ServiceConstants.SHOW_ALL_ANIMALS_SERVICE;
import static com.epam.huntingService.validator.AnimalValidator.isAnimalExist;

public class EditAnimalService implements Service {
    private FactoryDAO factoryDAO = FactoryDAO.getInstance();
    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private AnimalFactory animalFactory = AnimalFactory.getInstance();
    private AnimalDAO animalDAO = (AnimalDAOImpl) factoryDAO.getDAO(ANIMAL_DAO);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SQLException {
        HttpSession session = request.getSession(true);
        RequestDispatcher dispatcher;

        if (AccessValidator.checkAccess(ADMIN_ROLE_ID, session)) {

            String[] animalsNameParams = request.getParameterValues(ANIMAL_NAME);

            Long animalID = Long.parseLong(request.getParameter(ANIMAL_ID));
            List<String> animalNames = animalDAO.takeAllLocalizedAnimalNames();

            if (request.getParameter(ANIMAL_NAME).length() == ZERO_REQUEST_LENGTH) {
                request.setAttribute(EMPTY_DATA, FILL_DATA_ERROR);
                dispatcher = request.getRequestDispatcher(EDIT_ANIMAL_JSP);
                dispatcher.forward(request, response);
            } else if (isAnimalExist(animalNames, animalsNameParams)) {
                request.setAttribute(WRONG_ANIMAL_DATA, ANIMAL_ERROR);
                dispatcher = request.getRequestDispatcher(EDIT_ANIMAL_JSP);
                dispatcher.forward(request, response);
            } else {
                List <Animal> updatingAnimals = animalFactory.fillAnimalsForUpdating(request);
                for (Animal animal: updatingAnimals){
                    animalDAO.update(animalID, animal.getLanguageID(), animal);
                }
                serviceFactory.getService(SHOW_ALL_ANIMALS_SERVICE).execute(request, response);
            }
        } else {
            dispatcher = request.getRequestDispatcher(ACCESS_ERROR_JSP);
            dispatcher.forward(request, response);
        }
    }
}
