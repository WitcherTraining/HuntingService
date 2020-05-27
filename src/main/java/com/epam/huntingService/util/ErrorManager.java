package com.epam.huntingService.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Locale;
import java.util.ResourceBundle;

import static com.epam.huntingService.util.ParameterNamesConstants.LANGUAGE;

public class ErrorManager {

    public static String getErrorFromLanguageBundle(HttpServletRequest request, String error) {

        HttpSession session = request.getSession(true);

        String currentLanguage = (String) session.getAttribute(LANGUAGE);
        Locale sessionLocale = new Locale(currentLanguage);
        ResourceBundle languageBundle = ResourceBundle.getBundle(LANGUAGE, sessionLocale);

        return languageBundle.getString(error);
    }
}
