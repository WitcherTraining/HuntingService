package com.epam.huntingService.service.factory;

import com.epam.huntingService.database.dao.factory.FactoryDAO;
import com.epam.huntingService.database.dao.impl.*;
import com.epam.huntingService.database.dao.interfaces.*;
import com.epam.huntingService.entity.*;
import com.epam.huntingService.entity.CartItem;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.epam.huntingService.database.dao.factory.ImplEnum.*;
import static com.epam.huntingService.util.constants.ParameterNamesConstants.ITEM_ID;

public class PermitFactory {
    private static PermitFactory instance = new PermitFactory();
    private FactoryDAO factoryDAO = FactoryDAO.getInstance();

    private PermitDAO permitDAO = (PermitDAOImpl) factoryDAO.getDAO(PERMIT_DAO);
    private OrganizationDAO organizationDAO = (OrganizationDAOImpl) factoryDAO.getDAO(ORGANIZATION_DAO);
    private UserDAO userDAO = (UserDAOImpl) factoryDAO.getDAO(USER_DAO);
    private HuntingGroundDAO huntingGroundDAO = (HuntingGroundDAOImpl) factoryDAO.getDAO(HUNTING_GROUND_DAO);
    private AnimalDAO animalDAO = (AnimalDAOImpl) factoryDAO.getDAO(ANIMAL_DAO);
    private AnimalLimitHistoryDAO animalLimitHistoryDAO = (AnimalLimitHistoryDAOImpl) factoryDAO.getDAO(ANIMAL_LIMIT_HISTORY_DAO);

    private PermitFactory() {
    }

    public List<Permit> prepareAllPermitsByUser(Long userID, Integer languageID) throws SQLException, IOException {
        List<Permit> permits = permitDAO.getAllByUserID(userID, languageID);

        for (Permit permit : permits) {
            preparePermit(userID, languageID, permit);
        }
        return permits;
    }

    public List<Permit> preparePermits(Integer languageID) throws SQLException, IOException {
        List<Permit> permits = permitDAO.getAll(languageID);

        for (Permit permit : permits) {
            User user = userDAO.getByID(permit.getUserID(), languageID);
            permit.setUser(user);
            Animal animal = animalDAO.getByID(permit.getAnimalID(), languageID);
            permit.setAnimal(animal);
            HuntingGround huntingGround = huntingGroundDAO.getByID(permit.getHuntingGroundID(), languageID);
            permit.setHuntingGround(huntingGround);
            Organization organization = organizationDAO.getByID(permit.getOrganizationID(), languageID);
            permit.setOrganization(organization);
        }
        return permits;
    }

    public void preparePermit(Long userID, Integer languageID, Permit permit) throws SQLException, IOException {
        Organization organization = organizationDAO.getByID(permit.getOrganizationID(), languageID);
        permit.setOrganization(organization);
        HuntingGround huntingGround = huntingGroundDAO.getByID(permit.getOrganizationID(), languageID);
        permit.setHuntingGround(huntingGround);
        User user = userDAO.getByID(userID, languageID);
        permit.setUser(user);
        Animal animal = animalDAO.getByID(permit.getAnimalID(), languageID);
        AnimalLimitHistory animalLimitHistory = animalLimitHistoryDAO.getByAnimalIDInThisYear(animal.getId(), languageID);
        animal.setAnimalLimitHistory(animalLimitHistory);
        permit.setAnimal(animal);
    }

    public CartItem getCartItem(ArrayList<CartItem> cartItems, HttpServletRequest request) {
        CartItem cartItem = new CartItem();
        Integer cartItemID = Integer.parseInt(request.getParameter(ITEM_ID));
        for (CartItem item : cartItems) {
            if (item.getID().equals(cartItemID)) {
                cartItem = item;
            }
        }
        return cartItem;
    }

    public Permit fillDailyPermit(CartItem cartItem, Integer countAnimalsForHunt, Long idUser, Date huntingDay, Long organizationID) {
        Permit permit = new Permit();
        permit.setOrderDate(new Date());
        permit.setCountOrderedAnimals(countAnimalsForHunt);
        permit.setPermitType(cartItem.getPermitType());
        permit.setHuntingDay(huntingDay);
        permit.setUserID(idUser);
        permit.setHuntingGroundID(cartItem.getHuntingGroundID());
        permit.setAnimalID(cartItem.getAnimalID());
        permit.setOrganizationID(organizationID);
        return permit;
    }

    public Permit fillSeasonPermit(CartItem cartItem, Integer countAnimalsForHunt, Long idUser, Long organizationID) {
        Permit permit = new Permit();
        permit.setOrderDate(new Date());
        permit.setCountOrderedAnimals(countAnimalsForHunt);
        permit.setPermitType(cartItem.getPermitType());
        permit.setUserID(idUser);
        permit.setAnimalID(cartItem.getAnimalID());
        permit.setOrganizationID(organizationID);
        permit.setHuntingGroundID(cartItem.getHuntingGroundID());
        return permit;
    }

    public void createPermitOperations(CartItem cartItem, Permit permit) throws SQLException {
        Integer updatedQuota = cartItem.getAnimalQuota() - permit.getCountOrderedAnimals();
        new PermitDAOImpl().create(permit);
        new AnimalQuotaHistoryDAOImpl().updateQuotaAfterOrder(updatedQuota, cartItem.getAnimalID(), cartItem.getHuntingGroundID());
    }

    public static PermitFactory getInstance() {
        if (instance == null) {
            instance = new PermitFactory();
        }
        return instance;
    }
}
