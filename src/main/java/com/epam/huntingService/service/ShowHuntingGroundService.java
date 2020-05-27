package com.epam.huntingService.service;

import com.epam.huntingService.entity.HuntingGround;
import com.epam.huntingService.service.factory.HuntingGroundFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

import static com.epam.huntingService.util.PageNameConstants.HUNTING_GROUND_JSP;
import static com.epam.huntingService.util.ParameterNamesConstants.*;

public class ShowHuntingGroundService implements Service {
    private HuntingGroundFactory huntingGroundFactory = HuntingGroundFactory.getInstance();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        HttpSession session = request.getSession(true);

        Long huntingGroundID = Long.parseLong(request.getParameter(HUNTING_GROUND_ID));
        Integer languageID = (Integer) session.getAttribute(LANGUAGE_ID);

        HuntingGround huntingGround = huntingGroundFactory.fillHuntingGroundData(huntingGroundID, languageID);

        session.setAttribute(HUNTING_GROUND, huntingGround);

        response.sendRedirect(HUNTING_GROUND_JSP);
    }
}
