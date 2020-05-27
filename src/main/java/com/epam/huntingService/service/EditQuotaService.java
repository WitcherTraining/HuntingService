package com.epam.huntingService.service;

import com.epam.huntingService.database.dao.factory.FactoryDAO;
import com.epam.huntingService.database.dao.interfaces.AnimalQuotaHistoryDAO;
import com.epam.huntingService.database.dao.impl.AnimalQuotaHistoryDAOImpl;
import com.epam.huntingService.entity.AnimalQuotaHistory;
import com.epam.huntingService.service.factory.QuotaFactory;
import com.epam.huntingService.validator.AccessValidator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

import static com.epam.huntingService.database.dao.factory.ImplEnum.ANIMAL_QUOTA_HISTORY_DAO;
import static com.epam.huntingService.util.ErrorConstants.EMPTY_DATA;
import static com.epam.huntingService.util.ErrorConstants.FILL_DATA_ERROR;
import static com.epam.huntingService.util.PageNameConstants.ACCESS_ERROR_JSP;
import static com.epam.huntingService.util.PageNameConstants.QUOTA_JSP;
import static com.epam.huntingService.util.ParameterNamesConstants.*;
import static com.epam.huntingService.service.ServiceConstants.SHOW_QUOTA_SERVICE;

public class EditQuotaService implements Service {
    private FactoryDAO factoryDAO = FactoryDAO.getInstance();
    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private AnimalQuotaHistoryDAO animalQuotaHistoryDAO = (AnimalQuotaHistoryDAOImpl) factoryDAO.getDAO(ANIMAL_QUOTA_HISTORY_DAO);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SQLException {
        HttpSession session = request.getSession(true);
        RequestDispatcher dispatcher;

        if (AccessValidator.checkAccess(ADMIN_ROLE_ID, session)) {
            Integer languageID = (Integer) session.getAttribute(LANGUAGE_ID);

            if (request.getParameter(ANIMAL_QUOTA).length() == ZERO_REQUEST_LENGTH ||
                    request.getParameter(DAILY_PRICE).length() == ZERO_REQUEST_LENGTH ||
                    request.getParameter(SEASON_PRICE).length() == ZERO_REQUEST_LENGTH) {
                request.setAttribute(EMPTY_DATA, FILL_DATA_ERROR);
                dispatcher = request.getRequestDispatcher(QUOTA_JSP);
                dispatcher.forward(request, response);
            } else {
                AnimalQuotaHistory animalQuotaHistory = QuotaFactory.fillQuota(request, session);
                animalQuotaHistory.setId(Long.parseLong(request.getParameter(QUOTA_ID)));
                animalQuotaHistoryDAO.update(animalQuotaHistory.getId(), languageID, animalQuotaHistory);
                serviceFactory.getService(SHOW_QUOTA_SERVICE).execute(request, response);
            }
        } else {
            dispatcher = request.getRequestDispatcher(ACCESS_ERROR_JSP);
            dispatcher.forward(request, response);
        }
    }
}
