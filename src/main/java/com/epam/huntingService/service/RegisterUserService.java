package com.epam.huntingService.service;

import com.epam.huntingService.database.dao.factory.FactoryDAO;
import com.epam.huntingService.database.dao.interfaces.UserDAO;
import com.epam.huntingService.database.dao.impl.UserDAOImpl;
import com.epam.huntingService.entity.User;
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
import static com.epam.huntingService.service.factory.UserFactory.fillUser;
import static com.epam.huntingService.util.ErrorConstants.*;
import static com.epam.huntingService.util.PageNameConstants.*;
import static com.epam.huntingService.util.ParameterNamesConstants.*;
import static com.epam.huntingService.validator.AuthorizationValidator.*;


public class RegisterUserService implements Service {
    private FactoryDAO factoryDAO = FactoryDAO.getInstance();
    private UserDAO userDAO = (UserDAOImpl) factoryDAO.getDAO(USER_DAO);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SQLException {
        HttpSession session = request.getSession(true);
        RequestDispatcher dispatcher;

        if (!AccessValidator.checkAccess(HUNTER_ROLE_ID, session) ||
                !AccessValidator.checkAccess(USER_ROLE_ID, session) ||
                !AccessValidator.checkAccess(ADMIN_ROLE_ID, session)) {

            if (userDAO.isLoginExist(request.getParameter(LOGIN))) {
                request.setAttribute(WRONG_LOGIN, LOGIN_EXISTS_ERROR);
                dispatcher = request.getRequestDispatcher(REGISTRATION_JSP);
                dispatcher.forward(request, response);
            } else if (!validateMailRegex(request.getParameter(EMAIL))) {
                request.setAttribute(WRONG_EMAIL, EMAIL_ERROR);
                dispatcher = request.getRequestDispatcher(REGISTRATION_JSP);
                dispatcher.forward(request, response);
            } else if (!validatePhoneNumber(request.getParameter(PHONE))) {
                request.setAttribute(WRONG_PHONE, PHONE_ERROR);
                dispatcher = request.getRequestDispatcher(REGISTRATION_JSP);
                dispatcher.forward(request, response);
            } else if (!validatePasswordRegex(request.getParameter(PASSWORD))) {
                request.setAttribute(WRONG_PASSWORD, PASSWORD_ERROR);
                dispatcher = request.getRequestDispatcher(REGISTRATION_JSP);
                dispatcher.forward(request, response);
            } else {
                User newUser = fillUser(request);
                userDAO.create(newUser);
                session.setAttribute(USER_ID, newUser.getId());
                session.setAttribute(ROLE, newUser.getRole());
                session.setAttribute(LOGIN, newUser);
                session.setAttribute(ROLE_ID, newUser.getRoleID());
                dispatcher = request.getRequestDispatcher(MAIN_JSP);
                dispatcher.forward(request, response);
            }
        } else {
            dispatcher = request.getRequestDispatcher(ACCESS_ERROR_JSP);
            dispatcher.forward(request, response);
        }
    }
}
