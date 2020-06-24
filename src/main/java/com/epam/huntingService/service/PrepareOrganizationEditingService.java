package com.epam.huntingService.service;

import com.epam.huntingService.database.dao.factory.FactoryDAO;
import com.epam.huntingService.database.dao.interfaces.OrganizationDAO;
import com.epam.huntingService.database.dao.impl.OrganizationDAOImpl;
import com.epam.huntingService.entity.Organization;
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

import static com.epam.huntingService.database.dao.factory.ImplEnum.ORGANIZATION_DAO;
import static com.epam.huntingService.util.constants.PageNameConstants.ACCESS_ERROR_JSP;
import static com.epam.huntingService.util.constants.PageNameConstants.EDIT_ORGANIZATION_JSP;
import static com.epam.huntingService.util.constants.ParameterNamesConstants.*;

public class PrepareOrganizationEditingService implements Service {
    private FactoryDAO factoryDAO = FactoryDAO.getInstance();
    private OrganizationDAO organizationDAO = (OrganizationDAOImpl) factoryDAO.getDAO(ORGANIZATION_DAO);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SQLException {
        HttpSession session = request.getSession(true);
        RequestDispatcher dispatcher;

        if (AccessValidator.checkAccess(ADMIN_ROLE_ID, session)){

            Long organizationID = Long.parseLong(request.getParameter(ORGANIZATION_ID));
            List<Organization> localizedOrganizations = organizationDAO.takeAllLocalizedOrganizationsByID(organizationID);

            session.setAttribute(LOCALIZED_ORGANIZATIONS, localizedOrganizations);
            response.sendRedirect(EDIT_ORGANIZATION_JSP);
        } else {
            dispatcher = request.getRequestDispatcher(ACCESS_ERROR_JSP);
            dispatcher.forward(request, response);
        }
    }
}
