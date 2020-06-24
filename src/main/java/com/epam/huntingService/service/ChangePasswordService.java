package com.epam.huntingService.service;

import com.epam.huntingService.database.dao.factory.FactoryDAO;
import com.epam.huntingService.database.dao.impl.UserDAOImpl;
import com.epam.huntingService.database.dao.interfaces.UserDAO;
import com.epam.huntingService.entity.User;
import com.epam.huntingService.validator.AccessValidator;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

import static com.epam.huntingService.database.dao.factory.ImplEnum.*;
import static com.epam.huntingService.util.constants.ErrorConstants.*;
import static com.epam.huntingService.util.constants.PageNameConstants.*;
import static com.epam.huntingService.util.constants.ParameterNamesConstants.*;
import static com.epam.huntingService.validator.AuthorizationValidator.validatePasswordRegex;

public class ChangePasswordService implements Service {
    private FactoryDAO factoryDAO = FactoryDAO.getInstance();
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
            String oldPassword = request.getParameter(OLD_PASSWORD);
            String oldSecuredPassword = DigestUtils.md5Hex(oldPassword);
            String newPassword = request.getParameter(PASSWORD);

            User user = userDAO.getByID(userID, languageID);

            String newSecuredPassword = DigestUtils.md5Hex(newPassword);

            if (!user.getPassword().equals(oldSecuredPassword)) {
                request.setAttribute(WRONG_EQUALITY_ERROR, PASSWORD_EQUALITY_ERROR);
                dispatcher = request.getRequestDispatcher(EDIT_PASSWORD_JSP);
                dispatcher.forward(request, response);
            } else if (!validatePasswordRegex(request.getParameter(PASSWORD))) {
                request.setAttribute(WRONG_PASSWORD, PASSWORD_ERROR);
                dispatcher = request.getRequestDispatcher(EDIT_PASSWORD_JSP);
                dispatcher.forward(request, response);
            } else if (user.getPassword().equals(oldSecuredPassword)) {
                userDAO.changePassword(newSecuredPassword, userID);
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
