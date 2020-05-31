package com.epam.huntingService.service.factory;

import com.epam.huntingService.database.dao.factory.FactoryDAO;
import com.epam.huntingService.database.dao.interfaces.AnimalDAO;
import com.epam.huntingService.database.dao.interfaces.AnimalQuotaHistoryDAO;
import com.epam.huntingService.database.dao.interfaces.HuntingGroundDAO;
import com.epam.huntingService.database.dao.impl.AnimalDAOImpl;
import com.epam.huntingService.database.dao.impl.AnimalQuotaHistoryDAOImpl;
import com.epam.huntingService.database.dao.impl.HuntingGroundDAOImpl;
import com.epam.huntingService.entity.Animal;
import com.epam.huntingService.entity.AnimalQuotaHistory;
import com.epam.huntingService.entity.HuntingGround;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static com.epam.huntingService.database.dao.factory.ImplEnum.*;
import static com.epam.huntingService.util.ParameterNamesConstants.*;

public class QuotaFactory {
    private static QuotaFactory instance = new QuotaFactory();
    private FactoryDAO factoryDAO = FactoryDAO.getInstance();

    private AnimalDAO animalDAO = (AnimalDAOImpl) factoryDAO.getDAO(ANIMAL_DAO);
    private AnimalQuotaHistoryDAO animalQuotaHistoryDAO = (AnimalQuotaHistoryDAOImpl) factoryDAO.getDAO(ANIMAL_QUOTA_HISTORY_DAO);
    private HuntingGroundDAO huntingGroundDAO = (HuntingGroundDAOImpl) factoryDAO.getDAO(HUNTING_GROUND_DAO);

    private QuotaFactory() {
    }

    public AnimalQuotaHistory fillQuota(HttpServletRequest request, HttpSession session) throws SQLException {
        AnimalQuotaHistory animalQuotaHistory = new AnimalQuotaHistory();

        animalQuotaHistory.setYear((Integer) session.getAttribute(CURRENT_YEAR));
        animalQuotaHistory.setAnimalQuota(Integer.parseInt(request.getParameter(ANIMAL_QUOTA)));
        animalQuotaHistory.setDailyPrice(Double.parseDouble(request.getParameter(DAILY_PRICE)));
        animalQuotaHistory.setSeasonPrice(Double.parseDouble(request.getParameter(SEASON_PRICE)));
        animalQuotaHistory.setHuntingGroundID(Long.parseLong(request.getParameter(HUNTING_GROUND_ID)));
        Animal animal = animalDAO.takeByName(request.getParameter(ANIMAL_NAME));
        animalQuotaHistory.setAnimalID(animal.getId());

        return animalQuotaHistory;
    }

    public List<AnimalQuotaHistory> prepareQuotas(Long huntingGroundID, Integer languageID) throws SQLException, IOException {
        List<AnimalQuotaHistory> animalQuotas = animalQuotaHistoryDAO.getAllByID(huntingGroundID);

        for (AnimalQuotaHistory animalQuotaHistory : animalQuotas) {
            HuntingGround huntingGround = huntingGroundDAO.getByID(animalQuotaHistory.getHuntingGroundID(), languageID);
            animalQuotaHistory.setHuntingGround(huntingGround);
            Animal animal = animalDAO.getByID(animalQuotaHistory.getAnimalID(), languageID);
            animalQuotaHistory.setAnimal(animal);
        }
        return animalQuotas;
    }

    public static QuotaFactory getInstance() {
        if (instance == null) {
            instance = new QuotaFactory();
        }
        return instance;
    }
}
