package com.epam.huntingService.service;

import com.epam.huntingService.validator.AccessValidator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

import static com.epam.huntingService.util.PageNameConstants.ACCESS_ERROR_JSP;
import static com.epam.huntingService.util.PageNameConstants.MAIN_JSP;
import static com.epam.huntingService.util.ParameterNamesConstants.*;

public class LogOutService implements Service {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException, SQLException {
        HttpSession session = request.getSession(true);
        RequestDispatcher dispatcher;

        if (AccessValidator.checkAccess(HUNTER_ROLE_ID, session) ||
                AccessValidator.checkAccess(USER_ROLE_ID, session) ||
                AccessValidator.checkAccess(ADMIN_ROLE_ID, session)) {

            request.getSession().invalidate();
            response.sendRedirect(MAIN_JSP);
        } else {
            dispatcher = request.getRequestDispatcher(ACCESS_ERROR_JSP);
            dispatcher.forward(request, response);
        }
    }
}
