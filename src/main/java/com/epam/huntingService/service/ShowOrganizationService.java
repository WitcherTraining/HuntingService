package com.epam.huntingService.service;

import com.epam.huntingService.entity.Organization;
import com.epam.huntingService.service.factory.OrganizationFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

import static com.epam.huntingService.util.constants.PageNameConstants.ORGANIZATION_JSP;
import static com.epam.huntingService.util.constants.ParameterNamesConstants.*;

public class ShowOrganizationService implements Service {
    private OrganizationFactory organizationFactory = OrganizationFactory.getInstance();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        HttpSession session = request.getSession(true);

        Long organizationID = Long.parseLong(request.getParameter(ORGANIZATION_ID));
        Integer languageID = (Integer) session.getAttribute(LANGUAGE_ID);

        Organization organization = organizationFactory.fillOrganizationData(organizationID, languageID);

        session.setAttribute(ORGANIZATION, organization);
        response.sendRedirect(ORGANIZATION_JSP);
    }
}
