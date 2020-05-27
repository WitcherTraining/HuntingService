package com.epam.huntingService.service;

import com.epam.huntingService.database.dao.factory.FactoryDAO;
import com.epam.huntingService.database.dao.interfaces.OrganizationDAO;
import com.epam.huntingService.database.dao.impl.OrganizationDAOImpl;
import com.epam.huntingService.entity.Organization;
import com.epam.huntingService.service.factory.OrganizationFactory;
import com.epam.huntingService.validator.AccessValidator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Collection;
import java.util.List;

import static com.epam.huntingService.database.dao.factory.ImplEnum.ORGANIZATION_DAO;
import static com.epam.huntingService.util.ErrorConstants.*;
import static com.epam.huntingService.util.PageNameConstants.ACCESS_ERROR_JSP;
import static com.epam.huntingService.util.PageNameConstants.EDIT_ORGANIZATION_JSP;
import static com.epam.huntingService.util.ParameterNamesConstants.*;
import static com.epam.huntingService.service.ServiceConstants.SHOW_ALL_ORGANIZATIONS_SERVICE;
import static com.epam.huntingService.validator.OrganizationValidator.isValidFileType;

public class EditOrganizationService implements Service {
    private FactoryDAO factoryDAO = FactoryDAO.getInstance();
    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private OrganizationFactory organizationFactory = OrganizationFactory.getInstance();
    private OrganizationDAO organizationDAO = (OrganizationDAOImpl) factoryDAO.getDAO(ORGANIZATION_DAO);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SQLException {
        HttpSession session = request.getSession(true);
        RequestDispatcher dispatcher;

        if (AccessValidator.checkAccess(ADMIN_ROLE_ID, session)) {

            Collection<Part> parts = request.getParts();

            if (request.getParameter(ORGANIZATION_NAME).length() == ZERO_REQUEST_LENGTH ||
                    request.getParameter(ORGANIZATION_DESCRIPTION).length() == ZERO_REQUEST_LENGTH) {
                request.setAttribute(EMPTY_DATA, FILL_DATA_ERROR);
                dispatcher = request.getRequestDispatcher(EDIT_ORGANIZATION_JSP);
                dispatcher.forward(request, response);
            } else if (isValidFileType(parts)) {
                request.setAttribute(WRONG_FILE_DATA, FILE_ERROR);
                dispatcher = request.getRequestDispatcher(EDIT_ORGANIZATION_JSP);
                dispatcher.forward(request, response);
            } else {
                List<Organization> updatingOrganizations = organizationFactory.fillOrganizationsForUpdating(request);
                for (Organization organization : updatingOrganizations) {
                    organizationDAO.update(organization.getId(), organization.getLanguageID(), organization);
                }
                serviceFactory.getService(SHOW_ALL_ORGANIZATIONS_SERVICE).execute(request, response);
            }
        } else {
            dispatcher = request.getRequestDispatcher(ACCESS_ERROR_JSP);
            dispatcher.forward(request, response);
        }
    }
}
