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

import static com.epam.huntingService.database.dao.factory.ImplEnum.HUNTING_GROUND_DAO;
import static com.epam.huntingService.util.ErrorConstants.*;
import static com.epam.huntingService.util.PageNameConstants.ACCESS_ERROR_JSP;
import static com.epam.huntingService.util.PageNameConstants.EDIT_HUNTING_GROUND_JSP;
import static com.epam.huntingService.util.ParameterNamesConstants.ADMIN_ROLE_ID;
import static com.epam.huntingService.service.ServiceConstants.SHOW_ALL_HUNTING_GROUNDS_SERVICE;

public class EditHuntingGroundService implements Service {
    private FactoryDAO factoryDAO = FactoryDAO.getInstance();
    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private HuntingGroundFactory huntingGroundFactory = HuntingGroundFactory.getInstance();
    private HuntingGroundDAO huntingGroundDAO = (HuntingGroundDAOImpl) factoryDAO.getDAO(HUNTING_GROUND_DAO);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SQLException {
        HttpSession session = request.getSession(true);
        RequestDispatcher dispatcher;

        if (AccessValidator.checkAccess(ADMIN_ROLE_ID, session)) {

            if (HuntingGroundValidator.isEmptyFields(request)) {
                request.setAttribute(EMPTY_DATA, FILL_DATA_ERROR);
                dispatcher = request.getRequestDispatcher(EDIT_HUNTING_GROUND_JSP);
                dispatcher.forward(request, response);
            } else {
                List<HuntingGround> updatingHuntingGrounds = huntingGroundFactory.fillHuntingGroundsForUpdating(request);
                for (HuntingGround huntingGround : updatingHuntingGrounds) {
                    huntingGroundDAO.update(huntingGround.getId(), huntingGround.getLanguageID(), huntingGround);
                }
                serviceFactory.getService(SHOW_ALL_HUNTING_GROUNDS_SERVICE).execute(request, response);
            }
        } else {
            dispatcher = request.getRequestDispatcher(ACCESS_ERROR_JSP);
            dispatcher.forward(request, response);
        }
    }
}