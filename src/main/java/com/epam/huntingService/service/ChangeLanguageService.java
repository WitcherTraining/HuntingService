package com.epam.huntingService.service;

import com.epam.huntingService.database.dao.factory.FactoryDAO;
import com.epam.huntingService.database.dao.interfaces.LanguageDAO;
import com.epam.huntingService.database.dao.impl.LanguageDAOImpl;
import com.epam.huntingService.database.dao.impl.RoleDAOImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

import static com.epam.huntingService.database.dao.factory.ImplEnum.LANGUAGE_DAO;
import static com.epam.huntingService.database.dao.factory.ImplEnum.ROLE_DAO;
import static com.epam.huntingService.util.ParameterNamesConstants.*;
import static com.epam.huntingService.service.ServiceConstants.FORWARD_SERVICE;
import static com.epam.huntingService.service.ServiceConstants.SHOW_ORDERED_PERMITS_SERVICE;

public class ChangeLanguageService implements Service {
    private FactoryDAO factoryDAO = FactoryDAO.getInstance();
    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private LanguageDAO languageDAO = (LanguageDAOImpl) factoryDAO.getDAO(LANGUAGE_DAO);
    private RoleDAOImpl roleDAO = (RoleDAOImpl) factoryDAO.getDAO(ROLE_DAO);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SQLException {
        HttpSession session = request.getSession(true);

        String languageToChange = request.getParameter(LANGUAGE_TO_CHANGE);
        Integer roleID = (Integer) session.getAttribute(ROLE_ID);
        Integer languageID = languageDAO.getLanguageId(languageToChange);
        String role = roleDAO.getRole(roleID, languageID);

        session.setAttribute(LANGUAGE, languageToChange);
        session.setAttribute(LANGUAGE_ID, languageID);
        session.setAttribute(ROLE, role);

        if (roleID.equals(HUNTER_ROLE_ID)){
            serviceFactory.getService(SHOW_ORDERED_PERMITS_SERVICE).execute(request, response);
        } else {
            serviceFactory.getService(FORWARD_SERVICE).execute(request, response);
        }
    }
}
