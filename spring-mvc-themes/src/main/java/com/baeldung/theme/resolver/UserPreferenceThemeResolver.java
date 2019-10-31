package com.baeldung.theme.resolver;

import com.baeldung.domain.UserPreference;
import com.baeldung.repository.UserPreferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ThemeResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserPreferenceThemeResolver implements ThemeResolver {

    public static final String THEME_REQUEST_ATTRIBUTE_NAME = UserPreferenceThemeResolver.class.getName() + ".THEME";

    @Autowired(required = false)
    Authentication authentication;

    @Autowired
    UserPreferenceRepository userPreferenceRepository;

    private String defaultThemeName;

    public String getDefaultThemeName() {
        return defaultThemeName;
    }

    public void setDefaultThemeName(String defaultThemeName) {
        this.defaultThemeName = defaultThemeName;
    }

    @Override
    public String resolveThemeName(HttpServletRequest request) {
        String themeName = (String) request.getAttribute(THEME_REQUEST_ATTRIBUTE_NAME);
        if (themeName != null) {
            return themeName;
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() && isNotAnonymous(authentication)) {
            User user = (User) authentication.getPrincipal();
            UserPreference userPreference = userPreferenceRepository.findById(user.getUsername()).orElse(null);

            if (userPreference != null) {
                themeName = userPreference.getTheme();
            }
        }

        // Fall back to default theme.
        if (themeName == null) {
            themeName = getDefaultThemeName();
        }
        request.setAttribute(THEME_REQUEST_ATTRIBUTE_NAME, themeName);
        return themeName;
    }

    private boolean isNotAnonymous(Authentication authentication) {
        return !isAnonymous(authentication);
    }

    private boolean isAnonymous(Authentication authentication) {
        return authentication != null && authentication.getPrincipal() instanceof String && "anonymousUser".equals(authentication.getPrincipal());
    }

    @Override
    public void setThemeName(HttpServletRequest request, HttpServletResponse response, String theme) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && isNotAnonymous(authentication)) {
            request.setAttribute(THEME_REQUEST_ATTRIBUTE_NAME, theme);
            User user = (User) authentication.getPrincipal();

            UserPreference userPreference = userPreferenceRepository.findById(user.getUsername()).orElse(new UserPreference());

            userPreference.setUsername(user.getUsername());
            userPreference.setTheme(StringUtils.hasText(theme) ? theme : null);
            userPreferenceRepository.save(userPreference);

        }

        if (!StringUtils.hasText(theme)) {
            request.setAttribute(THEME_REQUEST_ATTRIBUTE_NAME, getDefaultThemeName());
        }
    }
}
