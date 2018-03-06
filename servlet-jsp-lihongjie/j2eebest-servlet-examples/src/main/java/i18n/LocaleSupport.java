package i18n;

import java.util.Locale;

public interface LocaleSupport {
    
    static final String USER_PREFERRED_LOCALE = "com.ora.bestpractices.j2ee.i18n.USER_PREFERRED_LOCALE";
    static final Locale APPLICATION_DEFAULT_LOCALE = Locale.getDefault();

    public void setLocale(Locale inLocale);

    public Locale getLocale();
}
