package com.epam.huntingService.service;

import com.epam.huntingService.database.dao.factory.FactoryDAO;
import com.epam.huntingService.database.dao.impl.UserDAOImpl;
import com.epam.huntingService.database.dao.interfaces.UserDAO;
import com.epam.huntingService.validator.AccessValidator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

import static com.epam.huntingService.database.dao.factory.ImplEnum.USER_DAO;
import static com.epam.huntingService.util.constants.PageNameConstants.ACCESS_ERROR_JSP;
import static com.epam.huntingService.util.constants.ParameterNamesConstants.ADMIN_ROLE_ID;
import static com.epam.huntingService.util.constants.ParameterNamesConstants.USER_ID;
import static com.epam.huntingService.service.ServiceConstants.SHOW_ALL_USERS_BY_CATEGORIES_SERVICE;

public class DeleteUserService implements Service {
    private FactoryDAO factoryDAO = FactoryDAO.getInstance();
    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private UserDAO userDAO = (UserDAOImpl) factoryDAO.getDAO(USER_DAO);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SQLException {
        HttpSession session = request.getSession(true);
        RequestDispatcher dispatcher;

        if (AccessValidator.checkAccess(ADMIN_ROLE_ID, session)){

            Long userID = Long.parseLong(request.getParameter(USER_ID));

            userDAO.delete(userID);

            serviceFactory.getService(SHOW_ALL_USERS_BY_CATEGORIES_SERVICE).execute(request, response);
        } else {
            dispatcher = request.getRequestDispatcher(ACCESS_ERROR_JSP);
            dispatcher.forward(request, response);
        }
    }
}
