package com.epam.huntingService.service;

import com.epam.huntingService.database.dao.factory.FactoryDAO;
import com.epam.huntingService.database.dao.interfaces.AnimalLimitHistoryDAO;
import com.epam.huntingService.database.dao.impl.AnimalLimitHistoryDAOImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

import static com.epam.huntingService.database.dao.factory.ImplEnum.ANIMAL_LIMIT_HISTORY_DAO;
import static com.epam.huntingService.util.DateConverter.getCurrentYear;
import static com.epam.huntingService.util.constants.ParameterNamesConstants.CURRENT_YEAR;
import static com.epam.huntingService.util.constants.ParameterNamesConstants.FIRST_YEAR_LIMIT;
import static com.epam.huntingService.service.ServiceConstants.SHOW_ANIMALS_LIMIT_SERVICE;

public class PrepareLimitInfoService implements Service {
    private FactoryDAO factoryDAO = FactoryDAO.getInstance();
    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private AnimalLimitHistoryDAO animalLimitHistoryDAO = (AnimalLimitHistoryDAOImpl) factoryDAO.getDAO(ANIMAL_LIMIT_HISTORY_DAO);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SQLException {
        HttpSession session = request.getSession(true);

        Integer firstYearLimit = animalLimitHistoryDAO.takeFirstYearForLimitHistory();
        session.setAttribute(CURRENT_YEAR, getCurrentYear());
        session.setAttribute(FIRST_YEAR_LIMIT, firstYearLimit);
        serviceFactory.getService(SHOW_ANIMALS_LIMIT_SERVICE).execute(request, response);
    }
}
