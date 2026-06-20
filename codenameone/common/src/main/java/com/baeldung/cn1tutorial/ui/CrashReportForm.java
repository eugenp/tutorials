package com.baeldung.cn1tutorial.ui;

import com.baeldung.cn1tutorial.service.AppContext;
import com.baeldung.cn1tutorial.service.NetworkSupport;
import com.baeldung.cn1tutorial.util.ThrowableUtil;
import com.codename1.io.Log;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.TextArea;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.GridLayout;

/**
 * Minimal crash screen used instead of CN1's default crash protection UI.
 */
public class CrashReportForm extends Form {
    /**
     * @param context shared runtime context, or {@code null} during very early failures
     * @param throwable throwable to render
     * @param title already localized form title
     */
    public CrashReportForm(AppContext context, Throwable throwable, String title) {
        super(title, new BorderLayout());
        AutoShrinkSupport.install(this);
        TextArea stackTrace = new TextArea(ThrowableUtil.stackTraceToString(throwable), 18, 40);
        stackTrace.setName("crashStackTrace");
        stackTrace.setEditable(false);
        stackTrace.setGrowByContent(false);
        Button copy = new Button(context == null ? "Copy Details" : context.text("copy.details"));
        copy.setName("crashShareButton");
        copy.addActionListener(evt -> {
            Log.e(throwable);
            String body = ThrowableUtil.stackTraceToString(throwable);
            NetworkSupport.copyDiagnostics(context, body);
        });
        Button home = new Button(context == null ? "Close" : context.text("home.title"));
        home.setName("crashHomeButton");
        home.addActionListener(evt -> {
            if (context != null) {
                context.showHome();
            }
        });
        Container buttons = new Container(new GridLayout(1, 2));
        buttons.add(copy);
        buttons.add(home);
        add(BorderLayout.CENTER, stackTrace);
        add(BorderLayout.SOUTH, buttons);
    }
}
