package com.epam.huntingService.service;

import com.epam.huntingService.database.dao.factory.FactoryDAO;
import com.epam.huntingService.database.dao.interfaces.UserDAO;
import com.epam.huntingService.database.dao.impl.UserDAOImpl;
import com.epam.huntingService.entity.User;
import com.epam.huntingService.validator.AccessValidator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.ParseException;

import static com.epam.huntingService.database.dao.factory.ImplEnum.USER_DAO;
import static com.epam.huntingService.util.constants.ErrorConstants.*;
import static com.epam.huntingService.util.constants.PageNameConstants.ACCESS_ERROR_JSP;
import static com.epam.huntingService.util.constants.PageNameConstants.CABINET_JSP;
import static com.epam.huntingService.util.constants.ParameterNamesConstants.*;

public class UploadHunterDocumentService implements Service {
    private FactoryDAO factoryDAO = FactoryDAO.getInstance();
    private UserDAO userDAO = (UserDAOImpl) factoryDAO.getDAO(USER_DAO);
    private static final int EMPTY_FILE = 0;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SQLException {
        HttpSession session = request.getSession(true);
        RequestDispatcher dispatcher;

        if (AccessValidator.checkAccess(HUNTER_ROLE_ID, session) ||
                AccessValidator.checkAccess(USER_ROLE_ID, session)) {

            Long userID = (Long) session.getAttribute(USER_ID);
            Integer languageID = (Integer) session.getAttribute(LANGUAGE_ID);
            Part part = request.getPart(DOCUMENT);
            User user = userDAO.getByID(userID, languageID);

            if (part.getSize() > EMPTY_FILE && part.getSize() < MAX_FILE_SIZE && part.getContentType().equalsIgnoreCase(PDF_CONTENT_TYPE)) {
                try (InputStream fileInputStream = part.getInputStream()) {
                    user.setUploadingDocument(fileInputStream);
                }
                userDAO.uploadDocument(userID, user.getUploadingDocument());

                request.setAttribute(UPLOAD_COMPLETE, UPLOAD_SUCCESS_MESSAGE);
                dispatcher = request.getRequestDispatcher(CABINET_JSP);
                dispatcher.forward(request, response);
            } else if (part.getSize() == EMPTY_FILE || part.getSize() > MAX_FILE_SIZE || !part.getContentType().equalsIgnoreCase(PDF_CONTENT_TYPE)){
                request.setAttribute(WRONG_FILE_DATA, FILE_ERROR);
                dispatcher = request.getRequestDispatcher(CABINET_JSP);
                dispatcher.forward(request, response);
            }
        } else {
            dispatcher = request.getRequestDispatcher(ACCESS_ERROR_JSP);
            dispatcher.forward(request, response);
        }
    }
}
