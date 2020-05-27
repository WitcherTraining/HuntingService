package com.epam.huntingService.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.huntingService.util.ParameterNamesConstants.DIRECTION;

public class ForwardService implements Service {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String direction = request.getParameter(DIRECTION);
        response.sendRedirect(direction);
    }
}
