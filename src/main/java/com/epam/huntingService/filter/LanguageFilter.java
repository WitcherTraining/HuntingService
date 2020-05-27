package com.epam.huntingService.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.epam.huntingService.util.ParameterNamesConstants.*;

public class LanguageFilter implements Filter {
    private static final String CONFIG_LANGUAGE_ID_NAME = "defaultLanguageID";
    private static final String CONFIG_LANGUAGE_NAME = "defaultLanguage";
    private Integer defaultLanguageID;
    private String defaultLanguage;

    @Override
    public void init(FilterConfig config) throws ServletException {
        defaultLanguageID = Integer.parseInt(config.getInitParameter(CONFIG_LANGUAGE_ID_NAME));
        defaultLanguage = config.getInitParameter(CONFIG_LANGUAGE_NAME);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws java.io.IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpSession session = httpServletRequest.getSession(true);

        Integer languageID = (Integer) session.getAttribute(LANGUAGE_ID);
        String language = (String) session.getAttribute(LANGUAGE);

        if (languageID == null || language == null) {
            session.setAttribute(LANGUAGE_ID, defaultLanguageID);
            session.setAttribute(LANGUAGE, defaultLanguage);
        } else if (languageID.equals(ENGLISH_ID) && language.equalsIgnoreCase(ENGLISH)) {
            session.setAttribute(LANGUAGE_ID, ENGLISH_ID);
            session.setAttribute(LANGUAGE, ENGLISH);
        } else if (languageID.equals(RUSSIAN_ID) && language.equalsIgnoreCase(RUSSIAN)) {
            session.setAttribute(LANGUAGE_ID, RUSSIAN_ID);
            session.setAttribute(LANGUAGE, RUSSIAN);
        }

        chain.doFilter(request, response);
    }
}
