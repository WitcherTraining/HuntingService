package com.epam.huntingService.service;

import com.epam.huntingService.database.dao.factory.FactoryDAO;
import com.epam.huntingService.database.dao.interfaces.UserDAO;
import com.epam.huntingService.database.dao.impl.UserDAOImpl;
import com.epam.huntingService.entity.User;
import com.epam.huntingService.service.factory.UserFactory;
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
import static com.epam.huntingService.util.ErrorConstants.*;
import static com.epam.huntingService.util.PageNameConstants.*;
import static com.epam.huntingService.util.ParameterNamesConstants.*;
import static com.epam.huntingService.validator.AuthorizationValidator.*;

public class EditProfileService implements Service {
    private FactoryDAO factoryDAO = FactoryDAO.getInstance();
    private UserFactory userFactory = UserFactory.getInstance();
    private UserDAO userDAO = (UserDAOImpl) factoryDAO.getDAO(USER_DAO);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SQLException {
        HttpSession session = request.getSession(true);
        RequestDispatcher dispatcher;

        if (AccessValidator.checkAccess(HUNTER_ROLE_ID, session) ||
                AccessValidator.checkAccess(USER_ROLE_ID, session) ||
                AccessValidator.checkAccess(ADMIN_ROLE_ID, session)) {

            Long userID = (Long) session.getAttribute(USER_ID);
            Integer languageID = (Integer) session.getAttribute(LANGUAGE_ID);

            if (!validateMailRegex(request.getParameter(EMAIL))) {
                request.setAttribute(WRONG_EMAIL, EMAIL_ERROR);
                dispatcher = request.getRequestDispatcher(EDIT_PROFILE_JSP);
                dispatcher.forward(request, response);
            } else if (!validatePhoneNumber(request.getParameter(PHONE))) {
                request.setAttribute(WRONG_PHONE, PHONE_ERROR);
                dispatcher = request.getRequestDispatcher(EDIT_PROFILE_JSP);
                dispatcher.forward(request, response);
            } else if (!validatePasswordRegex(request.getParameter(PASSWORD))) {
                request.setAttribute(WRONG_PASSWORD, PASSWORD_ERROR);
                dispatcher = request.getRequestDispatcher(EDIT_PROFILE_JSP);
                dispatcher.forward(request, response);
            } else {
                User newUser = userFactory.fillUser(request);
                userDAO.update(userID, languageID, newUser);
                session.setAttribute(UPDATE_PROFILE_COMPLETE, UPDATE_PROFILE_SUCCESS_MESSAGE);
                dispatcher = request.getRequestDispatcher(CABINET_JSP);
                dispatcher.forward(request, response);
            }
        } else {
            dispatcher = request.getRequestDispatcher(ACCESS_ERROR_JSP);
            dispatcher.forward(request, response);
        }
    }
}
