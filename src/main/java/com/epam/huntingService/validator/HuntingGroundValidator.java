package com.epam.huntingService.validator;

import com.epam.huntingService.database.dao.impl.OrganizationDAOImpl;
import com.epam.huntingService.database.dao.interfaces.OrganizationDAO;
import com.epam.huntingService.entity.HuntingGround;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.*;

import static com.epam.huntingService.util.ParameterNamesConstants.*;

public class HuntingGroundValidator {
    private static OrganizationDAO organizationDAO = new OrganizationDAOImpl();

    public static boolean isHuntingGroundExist(List<HuntingGround> huntingGrounds, String[] huntingGroundNameParams) {
        List<Boolean> checks = new ArrayList<>();
        boolean isExist = false;
        for (String huntingGroundName : huntingGroundNameParams) {
            for (HuntingGround hg : huntingGrounds) {
                if (hg.getName().equalsIgnoreCase(huntingGroundName)) {
                    checks.add(true);
                }
            }
        }
        if (checks.size() == huntingGroundNameParams.length) {
            isExist = true;
        }
        return isExist;
    }

    public static boolean isHuntingGroundExist(List<HuntingGround> huntingGrounds, String huntingGroundName) {
        boolean isExist = false;
        for (HuntingGround hg : huntingGrounds) {
            if (hg.getName().equalsIgnoreCase(huntingGroundName)) {
                isExist = true;
            }
        }
        return isExist;
    }

    public static boolean isDistrictExist(List<HuntingGround> huntingGrounds, String district) {
        boolean isExist = false;
        for (HuntingGround hg : huntingGrounds) {
            if (hg.getDistrict().equalsIgnoreCase(district)) {
                isExist = true;
            }
        }
        return isExist;
    }

    public static boolean isEmptyFields(HttpServletRequest request) {
        boolean isEmptyFields = false;
        if (request.getParameter(HUNTING_GROUND_NAME).length() == ZERO_REQUEST_LENGTH ||
                request.getParameter(ORGANIZATION_NAME).length() == ZERO_REQUEST_LENGTH ||
                request.getParameter(DESCRIPTION).length() == ZERO_REQUEST_LENGTH ||
                request.getParameter(DISTRICT).length() == ZERO_REQUEST_LENGTH) {
            isEmptyFields = true;
        }
        return isEmptyFields;
    }

    public static boolean isOrganizationNamesEqual(HttpServletRequest request) throws SQLException {
        Set<Long> checkSet = new HashSet<>();
        boolean isEqual = false;
        String[] organizationNameParams = request.getParameterValues(ORGANIZATION_NAME);
        for (String name : organizationNameParams) {
            checkSet.add(organizationDAO.takeOrganizationIdByName(name));
        }
        if (checkSet.size() == SIZE_WITHOUT_DOUBLES) {
            isEqual = true;
        }
        return isEqual;
    }
}
