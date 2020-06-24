package com.epam.huntingService.service;

import com.epam.huntingService.database.dao.factory.FactoryDAO;
import com.epam.huntingService.database.dao.impl.PermitDAOImpl;
import com.epam.huntingService.database.dao.interfaces.PermitDAO;
import com.epam.huntingService.entity.Permit;
import com.epam.huntingService.service.factory.PermitFactory;
import com.epam.huntingService.util.FileManager;
import com.epam.huntingService.validator.AccessValidator;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

import static com.epam.huntingService.database.dao.factory.ImplEnum.*;
import static com.epam.huntingService.util.constants.PageNameConstants.*;
import static com.epam.huntingService.util.constants.ParameterNamesConstants.*;

public class PreparePermitToPrintService implements Service {
    private FactoryDAO factoryDAO = FactoryDAO.getInstance();
    private PermitDAO permitDAO = (PermitDAOImpl) factoryDAO.getDAO(PERMIT_DAO);
    private PermitFactory permitFactory = PermitFactory.getInstance();
    private FileManager fileManager = new FileManager();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SQLException {
        HttpSession session = request.getSession(true);

        RequestDispatcher dispatcher;

        if (AccessValidator.checkAccess(HUNTER_ROLE_ID, session)) {

            Long userID = (Long) session.getAttribute(USER_ID);
            Integer languageID = (Integer) session.getAttribute(LANGUAGE_ID);
            Long permitID = Long.parseLong(request.getParameter(PERMIT_ID));
            Permit permit = permitDAO.getByID(permitID, languageID);
            permitFactory.preparePermit(userID, languageID, permit);

            String qrText = "Путевка выписана на следующие имя и фамилию: " + session.getAttribute(NAME) + " " +
                    session.getAttribute(SURNAME) + ", уникальный № " + permit.getId();

            ByteArrayOutputStream out = QRCode.from(qrText).withCharset(UTF_ENCODE).to(ImageType.PNG).stream();

            String qrBase64 = fileManager.convertToBase64(out);

            session.setAttribute(BASE_64_QR, qrBase64);
            session.setAttribute(PERMIT, permit);
            response.sendRedirect(PERMIT_PRINT_JSP);
        } else {
            dispatcher = request.getRequestDispatcher(ACCESS_ERROR_JSP);
            dispatcher.forward(request, response);
        }
    }
}
