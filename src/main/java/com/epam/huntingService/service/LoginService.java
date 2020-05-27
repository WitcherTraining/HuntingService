package com.epam.huntingService.service;

import com.epam.huntingService.database.dao.factory.FactoryDAO;
import com.epam.huntingService.database.dao.interfaces.UserDAO;
import com.epam.huntingService.database.dao.impl.UserDAOImpl;
import com.epam.huntingService.entity.User;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

import static com.epam.huntingService.database.dao.factory.ImplEnum.USER_DAO;
import static com.epam.huntingService.util.ErrorConstants.AUTH_ERROR;
import static com.epam.huntingService.util.ErrorConstants.LOGIN_ERROR;
import static com.epam.huntingService.util.ErrorManager.getErrorFromLanguageBundle;
import static com.epam.huntingService.util.PageNameConstants.MAIN_JSP;
import static com.epam.huntingService.util.ParameterNamesConstants.*;
import static com.epam.huntingService.service.ServiceConstants.FORWARD_SERVICE;

public class LoginService implements Service {
    private FactoryDAO factoryDAO = FactoryDAO.getInstance();
    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private UserDAO userDAO = (UserDAOImpl) factoryDAO.getDAO(USER_DAO);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException,
            SQLException, ParseException {

        HttpSession session = request.getSession(true);
        RequestDispatcher dispatcher;

        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);
        String securedPassword = DigestUtils.md5Hex(password);

        Integer languageID = (Integer) session.getAttribute(LANGUAGE_ID);

        User user = userDAO.getUserByLoginPassword(login, securedPassword, languageID);

        if (user != null) {
            session.setAttribute(USER_ID, user.getId());
            session.setAttribute(LOGIN, login);
            session.setAttribute(ROLE, user.getRole());
            session.setAttribute(ROLE_ID, user.getRoleID());
            session.setAttribute(EMAIL, user.getEmail());
            session.setAttribute(PHONE, user.getPhone());
            serviceFactory.getService(FORWARD_SERVICE).execute(request, response);
        } else {
            request.setAttribute(AUTH_ERROR, getErrorFromLanguageBundle(request, LOGIN_ERROR));
            dispatcher = request.getRequestDispatcher(MAIN_JSP);
            dispatcher.forward(request, response);
        }
    }
}
