package com.epam.huntingService.service;

import com.epam.huntingService.database.dao.factory.FactoryDAO;
import com.epam.huntingService.database.dao.interfaces.OrganizationDAO;
import com.epam.huntingService.database.dao.impl.OrganizationDAOImpl;
import com.epam.huntingService.entity.Permit;
import com.epam.huntingService.entity.CartItem;
import com.epam.huntingService.validator.AccessValidator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import static com.epam.huntingService.database.dao.factory.ImplEnum.ORGANIZATION_DAO;
import static com.epam.huntingService.service.factory.PermitFactory.*;
import static com.epam.huntingService.util.ErrorConstants.*;
import static com.epam.huntingService.util.PageNameConstants.ACCESS_ERROR_JSP;
import static com.epam.huntingService.util.PageNameConstants.CART_JSP;
import static com.epam.huntingService.util.ParameterNamesConstants.*;
import static com.epam.huntingService.service.ServiceConstants.REMOVE_FROM_CART_SERVICE;
import static com.epam.huntingService.validator.PermitValidator.*;

public class OrderDailyPermitService implements Service {
    private FactoryDAO factoryDAO = FactoryDAO.getInstance();
    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private OrganizationDAO organizationDAO = (OrganizationDAOImpl) factoryDAO.getDAO(ORGANIZATION_DAO);
    private int countAnimalsForHunt;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SQLException {
        HttpSession session = request.getSession(true);
        RequestDispatcher dispatcher;

        if (AccessValidator.checkAccess(HUNTER_ROLE_ID, session)) {

            ArrayList<CartItem> cartItems = (ArrayList<CartItem>) session.getAttribute(CART_ITEMS);
            Long idUser = (Long) session.getAttribute(USER_ID);
            Date huntingDayUtilDate = null;

            if (request.getParameter(HUNTING_DATE).length() == ZERO_REQUEST_LENGTH ||
                    request.getParameter(COUNT_ANIMALS_FOR_HUNT).length() == ZERO_REQUEST_LENGTH) {
                request.setAttribute(EMPTY_DATA, FILL_DATA_ERROR);
                dispatcher = request.getRequestDispatcher(CART_JSP);
                dispatcher.forward(request, response);
            } else {
                java.sql.Date huntingDay = java.sql.Date.valueOf(request.getParameter(HUNTING_DAY));
                huntingDayUtilDate = new Date(huntingDay.getTime());
                countAnimalsForHunt = Integer.parseInt(request.getParameter(COUNT_ANIMALS_FOR_HUNT));
            }

            CartItem cartItem = getCartItem(cartItems, request);
            Long organizationID = organizationDAO.takeOrganizationIdByName(cartItem.getOrganizationName());
            Permit permit = fillDailyPermit(cartItem, countAnimalsForHunt, idUser, huntingDayUtilDate, organizationID);

            if (!validateDailyPermitHuntingDate(permit.getHuntingDay(), cartItem.getAnimalTermEndUDate(),
                    cartItem.getAnimalTermBeginUDate())){
                request.setAttribute(WRONG_HUNTING_DATE, HUNTING_DATE_ERROR);
                dispatcher = request.getRequestDispatcher(CART_JSP);
                dispatcher.forward(request, response);
            } else if (!validateCountOrderedAnimalsForDailyPermit(permit.getCountOrderedAnimals(), cartItem.getAnimalQuota())){
                request.setAttribute(WRONG_ORDERED_ANIMALS_COUNT, ORDERED_ANIMALS_COUNT_ERROR);
                dispatcher = request.getRequestDispatcher(CART_JSP);
                dispatcher.forward(request, response);
            } else {
                createPermitOperations(cartItem, permit);
                serviceFactory.getService(REMOVE_FROM_CART_SERVICE).execute(request, response);
            }
        } else {
            dispatcher = request.getRequestDispatcher(ACCESS_ERROR_JSP);
            dispatcher.forward(request, response);
        }
    }
}
