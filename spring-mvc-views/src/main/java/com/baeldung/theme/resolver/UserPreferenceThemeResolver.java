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
import java.util.Optional;

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
        String themeName = findThemeFromRequest(request).orElse(findUserPreferredTheme().orElse(getDefaultThemeName()));
        request.setAttribute(THEME_REQUEST_ATTRIBUTE_NAME, themeName);
        return themeName;
    }

    private Optional<String> findUserPreferredTheme() {
        Authentication authentication = SecurityContextHolder.getContext()
            .getAuthentication();
        UserPreference userPreference = getUserPreference(authentication).orElse(new UserPreference());
        return Optional.ofNullable(userPreference.getTheme());
    }

    private Optional<String> findThemeFromRequest(HttpServletRequest request) {
        return Optional.ofNullable((String) request.getAttribute(THEME_REQUEST_ATTRIBUTE_NAME));
    }

    private Optional<UserPreference> getUserPreference(Authentication authentication) {
        return isAuthenticated(authentication) ? userPreferenceRepository.findById(((User) authentication.getPrincipal()).getUsername()) : Optional.empty();
    }

    private boolean isAuthenticated(Authentication authentication) {
        return authentication != null && authentication.isAuthenticated();
    }

    @Override
    public void setThemeName(HttpServletRequest request, HttpServletResponse response, String theme) {
        Authentication authentication = SecurityContextHolder.getContext()
            .getAuthentication();
        if (isAuthenticated(authentication)) {
            request.setAttribute(THEME_REQUEST_ATTRIBUTE_NAME, theme);
            UserPreference userPreference = getUserPreference(authentication).orElse(new UserPreference());
            userPreference.setUsername(((User) authentication.getPrincipal()).getUsername());
            userPreference.setTheme(StringUtils.hasText(theme) ? theme : null);
            userPreferenceRepository.save(userPreference);
        }
    }
}
