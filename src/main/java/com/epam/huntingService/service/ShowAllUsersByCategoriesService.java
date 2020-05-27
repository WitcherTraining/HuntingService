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
import java.util.List;

import static com.epam.huntingService.database.dao.factory.ImplEnum.USER_DAO;
import static com.epam.huntingService.util.PageNameConstants.ACCESS_ERROR_JSP;
import static com.epam.huntingService.util.PageNameConstants.USERS_JSP;
import static com.epam.huntingService.util.ParameterNamesConstants.*;

public class ShowAllUsersByCategoriesService implements Service {
    private FactoryDAO factoryDAO = FactoryDAO.getInstance();
    private UserDAO userDAO = (UserDAOImpl) factoryDAO.getDAO(USER_DAO);

    @SuppressWarnings("Duplicates")
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SQLException {
        HttpSession session = request.getSession(true);
        RequestDispatcher dispatcher;

        if (AccessValidator.checkAccess(ADMIN_ROLE_ID, session)){

            Integer languageID = (Integer) session.getAttribute(LANGUAGE_ID);
            List<User> users = userDAO.getAll(languageID);

            session.setAttribute(USERS, users);
            response.sendRedirect(USERS_JSP);
        } else {
            dispatcher = request.getRequestDispatcher(ACCESS_ERROR_JSP);
            dispatcher.forward(request, response);
        }
    }
}
