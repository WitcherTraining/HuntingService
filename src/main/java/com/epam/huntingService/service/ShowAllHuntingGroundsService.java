package com.epam.huntingService.service;

import com.epam.huntingService.database.dao.factory.FactoryDAO;
import com.epam.huntingService.database.dao.interfaces.OrganizationDAO;
import com.epam.huntingService.database.dao.impl.OrganizationDAOImpl;
import com.epam.huntingService.entity.HuntingGround;
import com.epam.huntingService.entity.Organization;
import com.epam.huntingService.service.factory.HuntingGroundFactory;
import com.epam.huntingService.util.DateConverter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import static com.epam.huntingService.database.dao.factory.ImplEnum.*;
import static com.epam.huntingService.util.PageNameConstants.HUNTING_GROUNDS_JSP;
import static com.epam.huntingService.util.ParameterNamesConstants.*;

public class ShowAllHuntingGroundsService implements Service {
    private FactoryDAO factoryDAO = FactoryDAO.getInstance();
    private HuntingGroundFactory huntingGroundFactory = HuntingGroundFactory.getInstance();
    private OrganizationDAO organizationDAO = (OrganizationDAOImpl) factoryDAO.getDAO(ORGANIZATION_DAO);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException,
            ParseException, SQLException {
        HttpSession session = request.getSession(true);

        Integer languageID = (Integer) session.getAttribute(LANGUAGE_ID);
        List<HuntingGround> huntingGrounds = huntingGroundFactory.prepareHuntingGroundsInfo(languageID);

        List<Organization> localizedOrgs = organizationDAO.takeAllLocalizedOrganizations();

        session.setAttribute(LOCALIZED_ORGANIZATIONS, localizedOrgs);
        session.setAttribute(HUNTING_GROUNDS, huntingGrounds);
        session.setAttribute(CURRENT_YEAR, DateConverter.getCurrentYear());
        response.sendRedirect(HUNTING_GROUNDS_JSP);
    }
}
