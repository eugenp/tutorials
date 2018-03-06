package i18n;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.TagSupport;
import java.awt.*;
import java.io.IOException;
import java.util.*;

public class InsertText extends TagSupport implements LocaleSupport {
    private Locale locale;
    private String textID;
    private String resourceBundleName;
    private final static String DIV_LTR = "<DIV DIR =\"LTR\">";
    private final static String DIV_RTL = "<DIV DIR =\"RTL\">";
    private final static String CLOSING_DIV = "</DIV>";

    public InsertText() {
        locale = LocaleSupport.APPLICATION_DEFAULT_LOCALE;
        textID = null;
        resourceBundleName = null;
    }

    public void setLocale(Locale inLocale) {
        this.locale = inLocale;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setTextID(String textID) {
        this.textID = textID;
    }

    public void setResourceBundleName(String resourceBundleName) {
        this.resourceBundleName = resourceBundleName;
    }

    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        ResourceBundle rb = ResourceBundle.getBundle(resourceBundleName,
                locale);
        try {
            ComponentOrientation co =
                    ComponentOrientation.getOrientation(locale);
            if (co.isLeftToRight() ||
                    co.equals(ComponentOrientation.UNKNOWN)) {
                out.print(this.DIV_LTR);
            } else {
                out.print(this.DIV_RTL);
            }
            out.print(rb.getString(textID));
            out.print(this.CLOSING_DIV);
        } catch (IOException ioe) {
            throw new JspTagException(ioe.toString());
        } catch (MissingResourceException mre) {
            throw new JspTagException(mre.toString());
        }
        return SKIP_BODY;
    }

    public void release() {
        super.release();
        locale = LocaleSupport.APPLICATION_DEFAULT_LOCALE;
        textID = null;
        resourceBundleName = null;
    }
}
