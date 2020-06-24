package com.epam.huntingService.service;

import com.epam.huntingService.entity.CartItem;
import com.epam.huntingService.service.factory.CartItemFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import static com.epam.huntingService.util.constants.PageNameConstants.HUNTING_GROUND_JSP;
import static com.epam.huntingService.util.constants.ParameterNamesConstants.*;

public class AddToCartService implements Service {
    private CartItemFactory cartItemFactory = CartItemFactory.getInstance();
    private final Integer COUNTER_BEGIN = 1;
    private Integer itemsCounter = COUNTER_BEGIN;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        HttpSession session = request.getSession(true);

        ArrayList<CartItem> cartItems = (ArrayList<CartItem>) session.getAttribute(CART_ITEMS);
        java.sql.Date todaySql = new java.sql.Date(new Date().getTime());
        String today = todaySql.toString();
        java.sql.Date.valueOf(today);

        if (cartItems == null) {
            cartItems = new ArrayList<>();
            CartItem cartItem = cartItemFactory.setCartItemParameters(request, session, itemsCounter);
            cartItems.add(cartItem);
        } else {
            itemsCounter++;
            CartItem cartItem = cartItemFactory.setCartItemParameters(request, session, itemsCounter);
            cartItems.add(cartItem);
        }

        session.setAttribute(CART_ITEMS, cartItems);
        session.setAttribute(TODAY, today);
        session.setAttribute(CART_WITH_ITEMS, itemsCounter);

        response.sendRedirect(HUNTING_GROUND_JSP);
    }
}
