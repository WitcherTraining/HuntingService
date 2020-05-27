package com.epam.huntingService.service.factory;

import com.epam.huntingService.database.dao.interfaces.AnimalDAO;
import com.epam.huntingService.database.dao.interfaces.AnimalLimitHistoryDAO;
import com.epam.huntingService.database.dao.impl.AnimalDAOImpl;
import com.epam.huntingService.database.dao.impl.AnimalLimitHistoryDAOImpl;
import com.epam.huntingService.entity.Animal;
import com.epam.huntingService.entity.AnimalLimitHistory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

import static com.epam.huntingService.util.ParameterNamesConstants.*;

public class LimitFactory {
    private static AnimalDAO animalDAO = new AnimalDAOImpl();
    private static AnimalLimitHistoryDAO animalLimitHistoryDAO = new AnimalLimitHistoryDAOImpl();

    public static AnimalLimitHistory fillAnimalLimitHistory(HttpServletRequest request, HttpSession session) throws SQLException {
        AnimalLimitHistory animalLimitHistory = new AnimalLimitHistory();
        Animal animal = animalDAO.takeByName(request.getParameter(ANIMAL_NAME));
        Integer chosenYear = (Integer) session.getAttribute(CHOSEN_YEAR);

        animalLimitHistory.setYear(chosenYear);
        animalLimitHistory.setAllLimit(Integer.parseInt(request.getParameter(ALL_LIMIT)));
        animalLimitHistory.setMonthlyCalculationIndex(Double.parseDouble(request.getParameter(MCI)));
        animalLimitHistory.setAnimalCostInMCI(Double.parseDouble(request.getParameter(COST_IN_MCI)));
        animalLimitHistory.setTermBegin(java.sql.Date.valueOf(request.getParameter(TERM_BEGIN)));
        animalLimitHistory.setTermEnd(java.sql.Date.valueOf(request.getParameter(TERM_END)));
        animalLimitHistory.setAnimalID(animal.getId());

        return animalLimitHistory;
    }

    public static List<AnimalLimitHistory> fillLimitList(Integer languageID, Integer year) throws SQLException {
        List<AnimalLimitHistory> animalLimitHistories = animalLimitHistoryDAO.getAllByYear(year);

        for (AnimalLimitHistory animalLimitHistory: animalLimitHistories){
            Animal animal = animalDAO.getByID(animalLimitHistory.getAnimalID(), languageID);
            animalLimitHistory.setAnimal(animal);
        }
        return animalLimitHistories;
    }
}
