package com.epam.huntingService.service;

import com.epam.huntingService.database.dao.factory.FactoryDAO;
import com.epam.huntingService.database.dao.interfaces.HuntingGroundDAO;
import com.epam.huntingService.database.dao.impl.HuntingGroundDAOImpl;
import com.epam.huntingService.entity.HuntingGround;
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

import static com.epam.huntingService.database.dao.factory.ImplEnum.*;
import static com.epam.huntingService.util.PageNameConstants.ACCESS_ERROR_JSP;
import static com.epam.huntingService.util.PageNameConstants.EDIT_HUNTING_GROUND_JSP;
import static com.epam.huntingService.util.ParameterNamesConstants.*;

public class PrepareHuntingGroundEditingService implements Service {
    private FactoryDAO factoryDAO = FactoryDAO.getInstance();
    private HuntingGroundDAO huntingGroundDAO = (HuntingGroundDAOImpl) factoryDAO.getDAO(HUNTING_GROUND_DAO);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SQLException {
        HttpSession session = request.getSession(true);
        RequestDispatcher dispatcher;

        if (AccessValidator.checkAccess(ADMIN_ROLE_ID, session)){

            Long huntingGroundID = Long.parseLong(request.getParameter(HUNTING_GROUND_ID));
            List<HuntingGround> localizedHuntingGrounds = huntingGroundDAO.takeAllLocalizedHuntingGroundsByID(huntingGroundID);

            session.setAttribute(LOCALIZED_HUNTING_GROUNDS, localizedHuntingGrounds);
            response.sendRedirect(EDIT_HUNTING_GROUND_JSP);
        } else {
            dispatcher = request.getRequestDispatcher(ACCESS_ERROR_JSP);
            dispatcher.forward(request, response);
        }
    }
}
