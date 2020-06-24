package com.epam.huntingService.service;

import com.epam.huntingService.database.dao.factory.FactoryDAO;
import com.epam.huntingService.database.dao.interfaces.HuntingGroundDAO;
import com.epam.huntingService.database.dao.impl.HuntingGroundDAOImpl;
import com.epam.huntingService.entity.HuntingGround;
import com.epam.huntingService.service.factory.HuntingGroundFactory;
import com.epam.huntingService.validator.AccessValidator;
import com.epam.huntingService.validator.HuntingGroundValidator;

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
import static com.epam.huntingService.util.constants.ErrorConstants.*;
import static com.epam.huntingService.util.constants.PageNameConstants.ACCESS_ERROR_JSP;
import static com.epam.huntingService.util.constants.PageNameConstants.HUNTING_GROUNDS_JSP;
import static com.epam.huntingService.util.constants.ParameterNamesConstants.ADMIN_ROLE_ID;
import static com.epam.huntingService.util.constants.ParameterNamesConstants.HUNTING_GROUND_NAME;
import static com.epam.huntingService.service.ServiceConstants.SHOW_ALL_HUNTING_GROUNDS_SERVICE;
import static com.epam.huntingService.validator.HuntingGroundValidator.*;

public class AddHuntingGroundService implements Service {
    private FactoryDAO factoryDAO = FactoryDAO.getInstance();
    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private HuntingGroundFactory huntingGroundFactory = HuntingGroundFactory.getInstance();
    private HuntingGroundDAO huntingGroundDAO = (HuntingGroundDAOImpl) factoryDAO.getDAO(HUNTING_GROUND_DAO);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SQLException {
        HttpSession session = request.getSession(true);
        RequestDispatcher dispatcher;

        if (AccessValidator.checkAccess(ADMIN_ROLE_ID, session)) {

            String[] huntingGroundNameParams = request.getParameterValues(HUNTING_GROUND_NAME);
            List<HuntingGround> localizedHuntingGrounds = huntingGroundDAO.takeAllLocalizedHuntingGrounds();

            if (HuntingGroundValidator.isEmptyFields(request)) {
                request.setAttribute(EMPTY_DATA, FILL_DATA_ERROR);
                dispatcher = request.getRequestDispatcher(HUNTING_GROUNDS_JSP);
                dispatcher.forward(request, response);
            } else if (!isOrganizationNamesEqual(request)) {
                request.setAttribute(WRONG_ORGANIZATIONS_EQUALITY, ORGANIZATIONS_EQUALITY_ERROR);
                dispatcher = request.getRequestDispatcher(HUNTING_GROUNDS_JSP);
                dispatcher.forward(request, response);
            } else if (isHuntingGroundExist(localizedHuntingGrounds, huntingGroundNameParams)) {
                request.setAttribute(WRONG_HUNTING_GROUND_NAME, HUNTING_GROUND_NAME_ERROR);
                dispatcher = request.getRequestDispatcher(HUNTING_GROUNDS_JSP);
                dispatcher.forward(request, response);
            } else {
                List<HuntingGround> creatingHuntingGrounds = huntingGroundFactory.fillHuntingGroundsForCreating(request);
                for (HuntingGround huntingGround : creatingHuntingGrounds) {
                    huntingGroundDAO.create(huntingGround);
                }
                serviceFactory.getService(SHOW_ALL_HUNTING_GROUNDS_SERVICE).execute(request, response);
            }
        } else {
            dispatcher = request.getRequestDispatcher(ACCESS_ERROR_JSP);
            dispatcher.forward(request, response);
        }
    }
}
