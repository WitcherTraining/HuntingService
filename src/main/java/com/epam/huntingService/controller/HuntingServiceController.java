package com.epam.huntingService.controller;

import com.epam.huntingService.service.Service;
import com.epam.huntingService.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

public class HuntingServiceController extends HttpServlet {
    private static long serialVersionUID = 1L;
    private final Logger LOGGER = LogManager.getLogger(this.getClass().getName());

    public HuntingServiceController(){
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestString = request.getRequestURI();
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        Service service = serviceFactory.getService(requestString);
        try {
            service.execute(request, response);
        } catch (ParseException | SQLException e) {
            LOGGER.error(e);
        }
    }
}
