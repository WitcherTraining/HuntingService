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

import static com.epam.huntingService.service.factory.PermitFactory.preparePermits;
import static com.epam.huntingService.util.PageNameConstants.ACCESS_ERROR_JSP;
import static com.epam.huntingService.util.PageNameConstants.PERMITS_JSP;
import static com.epam.huntingService.util.ParameterNamesConstants.*;

public class ShowAllPermitsService implements Service {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SQLException {
        HttpSession session = request.getSession(true);
        RequestDispatcher dispatcher;

        if (AccessValidator.checkAccess(ADMIN_ROLE_ID, session)){

            Integer languageID = (Integer) session.getAttribute(LANGUAGE_ID);
            List<Permit> permits = preparePermits(languageID);

            session.setAttribute(PERMITS, permits);
            response.sendRedirect(PERMITS_JSP);
        } else {
            dispatcher = request.getRequestDispatcher(ACCESS_ERROR_JSP);
            dispatcher.forward(request, response);
        }
    }
}
