package com.epam.huntingService.service;

import com.epam.huntingService.entity.Organization;
import com.epam.huntingService.service.factory.OrganizationFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import static com.epam.huntingService.util.constants.PageNameConstants.ORGANIZATIONS_JSP;
import static com.epam.huntingService.util.constants.ParameterNamesConstants.LANGUAGE_ID;
import static com.epam.huntingService.util.constants.ParameterNamesConstants.ORGANIZATIONS;

public class ShowAllOrganizationsService implements Service {
    private OrganizationFactory organizationFactory = OrganizationFactory.getInstance();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SQLException {
        HttpSession session = request.getSession(true);

        Integer languageID = (Integer) session.getAttribute(LANGUAGE_ID);
        List<Organization> organizations = organizationFactory.prepareOrganizationInfo(languageID);

        session.setAttribute(ORGANIZATIONS, organizations);
        response.sendRedirect(ORGANIZATIONS_JSP);
    }
}
