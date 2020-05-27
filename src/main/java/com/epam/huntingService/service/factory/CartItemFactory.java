package com.epam.huntingService.service.factory;

import com.epam.huntingService.database.dao.interfaces.AnimalQuotaHistoryDAO;
import com.epam.huntingService.database.dao.interfaces.HuntingGroundDAO;
import com.epam.huntingService.database.dao.impl.AnimalQuotaHistoryDAOImpl;
import com.epam.huntingService.database.dao.impl.HuntingGroundDAOImpl;
import com.epam.huntingService.entity.CartItem;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.Date;

import static com.epam.huntingService.util.ParameterNamesConstants.*;

public class CartItemFactory {
    private static HuntingGroundDAO huntingGroundDAO = new HuntingGroundDAOImpl();
    private static AnimalQuotaHistoryDAO animalQuotaHistoryDAO = new AnimalQuotaHistoryDAOImpl();

    public static CartItem setCartItemParameters(HttpServletRequest request, HttpSession session, Integer itemsCounter) throws SQLException {
        CartItem cartItem = new CartItem();

        Integer languageID = (Integer) session.getAttribute(LANGUAGE_ID);

        cartItem.setID(itemsCounter);
        String orgName = huntingGroundDAO.getOrganizationNameByHuntingGroundName(request.getParameter(HUNTING_GROUND_NAME), languageID);
        cartItem.setOrganizationName(orgName);
        cartItem.setHuntingGroundName(request.getParameter(HUNTING_GROUND_NAME));
        cartItem.setAnimalName(request.getParameter(ANIMAL_NAME));
        cartItem.setPermitType(request.getParameter(PERMIT_TYPE));

        java.sql.Date sqlAnimalTermBegin = java.sql.Date.valueOf(request.getParameter(ANIMAL_TERM_BEGIN));
        java.sql.Date sqlAnimalTermEnd = java.sql.Date.valueOf(request.getParameter(ANIMAL_TERM_END));

        cartItem.setAnimalTermBeginUDate(new Date(sqlAnimalTermBegin.getTime()));
        cartItem.setAnimalTermEndUDate(new Date(sqlAnimalTermEnd.getTime()));

        cartItem.setAnimalCost(Double.parseDouble(request.getParameter(ANIMAL_COST)));
        if (request.getParameter(DAILY_PRICE) != null) {
            cartItem.setDailyPrice(Double.parseDouble(request.getParameter(DAILY_PRICE)));
        }
        if (request.getParameter(SEASON_PRICE) != null) {
            cartItem.setSeasonPrice(Double.parseDouble(request.getParameter(SEASON_PRICE)));
        }
        cartItem.setHuntingGroundID(Long.parseLong(request.getParameter(HUNTING_GROUND_ID)));
        cartItem.setAnimalID(Long.parseLong(request.getParameter(ANIMAL_ID)));
        Integer animalQuota = animalQuotaHistoryDAO.getQuotaByHuntingGroundAndAnimalID(cartItem.getHuntingGroundID(),
                cartItem.getAnimalID());
        cartItem.setAnimalQuota(animalQuota);
        return cartItem;
    }
}
