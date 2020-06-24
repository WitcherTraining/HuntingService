package com.epam.huntingService.validator;

import javax.servlet.http.HttpSession;

import static com.epam.huntingService.util.constants.ParameterNamesConstants.ROLE_ID;

public class AccessValidator {

    public static boolean checkAccess(Integer permittedRole, HttpSession session) {
        boolean isAccess = false;
        Integer roleID = (Integer) session.getAttribute(ROLE_ID);

        if (roleID != null && roleID.equals(permittedRole)){
            isAccess = true;
        }
        return isAccess;
    }
}
