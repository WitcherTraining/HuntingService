package com.epam.huntingService.service;

import com.epam.huntingService.entity.CartItem;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

import static com.epam.huntingService.util.constants.PageNameConstants.CART_JSP;
import static com.epam.huntingService.util.constants.ParameterNamesConstants.*;

public class RemoveFromCartService implements Service {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(true);

        Integer cartItemID = Integer.parseInt(request.getParameter(ITEM_ID));
        Integer itemsCounter = (Integer) session.getAttribute(CART_WITH_ITEMS);
        ArrayList<CartItem> cartItems = (ArrayList<CartItem>) session.getAttribute(CART_ITEMS);

        cartItems.removeIf(cartItem -> cartItem.getID().equals(cartItemID));
        itemsCounter--;

        session.setAttribute(CART_WITH_ITEMS, itemsCounter);
        session.setAttribute(CART_ITEMS, cartItems);
        response.sendRedirect(CART_JSP);
    }
}
