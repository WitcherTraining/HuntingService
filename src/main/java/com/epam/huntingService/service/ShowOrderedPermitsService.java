package com.epam.huntingService.service;

import com.epam.huntingService.entity.*;
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

import static com.epam.huntingService.service.factory.PermitFactory.*;
import static com.epam.huntingService.util.PageNameConstants.CABINET_JSP;
import static com.epam.huntingService.util.PageNameConstants.REGISTRATION_JSP;
import static com.epam.huntingService.util.ParameterNamesConstants.*;

public class ShowOrderedPermitsService implements Service {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SQLException {
        HttpSession session = request.getSession(true);
        RequestDispatcher dispatcher;

        if (AccessValidator.checkAccess(HUNTER_ROLE_ID, session) ||
                AccessValidator.checkAccess(USER_ROLE_ID, session) ||
                AccessValidator.checkAccess(ADMIN_ROLE_ID, session)) {

            Long userID = (Long) session.getAttribute(USER_ID);
            Integer languageID = (Integer) session.getAttribute(LANGUAGE_ID);
            List<Permit> permits = prepareAllPermitsByUser(userID, languageID);
            session.setAttribute(PERMITS, permits);
            response.sendRedirect(CABINET_JSP);
        } else {
            dispatcher = request.getRequestDispatcher(REGISTRATION_JSP);
            dispatcher.forward(request, response);
        }
    }
}
