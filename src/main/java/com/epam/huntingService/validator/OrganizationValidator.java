package com.epam.huntingService.validator;

import com.epam.huntingService.entity.Organization;

import javax.servlet.http.Part;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.epam.huntingService.util.ParameterNamesConstants.*;

public class OrganizationValidator {
    private static final int MINIMUM_SIZE = 0;

    public static boolean isOrganizationExist(List<Organization> organizations, String organizationName) {
        boolean isExist = false;
        for (Organization org : organizations) {
            if (org.getName().equalsIgnoreCase(organizationName)) {
                isExist = true;
            }
        }
        return isExist;
    }

    public static boolean isOrganizationExist(List<Organization> organizations, String[] organizationNameParams) {
        List<Boolean> checks = new ArrayList<>();
        boolean isExist = false;
        for (String name : organizationNameParams) {
            for (Organization org : organizations) {
                if (org.getName().equalsIgnoreCase(name)) {
                    checks.add(true);
                }
            }
        }
        if (checks.size() == organizationNameParams.length) {
            isExist = true;
        }
        return isExist;
    }

    public static boolean isValidFileType(Collection<Part> parts) {
        boolean isValid = false;
        for (Part part : parts) {
            if (part.getName().equalsIgnoreCase(LOGO)){
                if (part.getSize() > MINIMUM_SIZE && part.getSize() < MAX_FILE_SIZE ||
                        part.getContentType().equalsIgnoreCase(JPEG_CONTENT_TYPE) ||
                        part.getContentType().equalsIgnoreCase(JPG_CONTENT_TYPE) ||
                        part.getContentType().equalsIgnoreCase(PNG_CONTENT_TYPE))
                    isValid = true;
            }
        }
        return !isValid;
    }
}
