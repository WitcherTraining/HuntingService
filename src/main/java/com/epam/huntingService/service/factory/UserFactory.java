package com.epam.huntingService.service.factory;

import com.epam.huntingService.entity.User;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.http.HttpServletRequest;

import static com.epam.huntingService.util.constants.ParameterNamesConstants.*;

public class UserFactory {
    private static UserFactory instance = new UserFactory();

    private UserFactory() {
    }

    public User fillUser(HttpServletRequest request) {
        User newUser = new User();
        newUser.setName(request.getParameter(NAME));
        newUser.setSurname(request.getParameter(SURNAME));
        newUser.setLogin(request.getParameter(LOGIN));
        newUser.setEmail(request.getParameter(EMAIL));
        newUser.setPhone(request.getParameter(PHONE));
        String password = request.getParameter(PASSWORD);
        String securedPassword = DigestUtils.md5Hex(password);
        newUser.setPassword(securedPassword);
        newUser.setRole(USER);
        return newUser;
    }

    public static UserFactory getInstance() {
        if (instance == null) {
            instance = new UserFactory();
        }
        return instance;
    }
}
