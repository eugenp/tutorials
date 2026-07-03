package com.baeldung.cn1tutorial.ui;

import com.baeldung.cn1tutorial.service.AppContext;
import com.baeldung.cn1tutorial.service.NativeLogSupport;
import com.baeldung.cn1tutorial.service.NetworkSupport;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.Log;
import com.codename1.io.Util;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.TextArea;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import java.io.IOException;
import java.io.Reader;

/**
 * Diagnostics form combining the CN1 log file and the optional native platform logs.
 */
public class NativeLogsForm extends BaseForm {
    private final TextArea logsArea = new TextArea();
    private final Container logsContainer = new Container(BoxLayout.y());

    /**
     * @param context shared runtime context
     * @param previousForm previous form for back navigation
     */
    public NativeLogsForm(AppContext context, Form previousForm) {
        super(context, "native.logs.title", previousForm);
        logsArea.setName("nativeLogsArea");
        logsArea.setEditable(false);
        logsArea.setFocusable(false);
        logsArea.setActAsLabel(true);
        logsArea.setRows(1);
        logsArea.setGrowByContent(true);
        logsContainer.setScrollableY(true);
        logsContainer.add(logsArea);
        Button refresh = new Button(context.text("native.logs.refresh"));
        Button clear = new Button(context.text("native.logs.clear"));
        Button copy = new Button(context.text("copy.details"));
        refresh.setName("nativeLogsRefreshButton");
        clear.setName("nativeLogsClearButton");
        copy.setName("nativeLogsShareButton");
        refresh.addActionListener(evt -> refreshLogs());
        clear.addActionListener(evt -> clearLogs());
        copy.addActionListener(evt -> copyLogs());
        com.codename1.ui.Container buttons = new com.codename1.ui.Container(BoxLayout.y());
        buttons.add(refresh);
        buttons.add(clear);
        buttons.add(copy);
        add(BorderLayout.CENTER, logsContainer);
        add(BorderLayout.SOUTH, buttons);
        refreshLogs();
    }

    /**
     * Refreshes the combined log view.
     */
    private void refreshLogs() {
        logsArea.setText(buildCombinedLogs());
        logsContainer.revalidateLater();
    }

    /**
     * Clears both the CN1 log file and the native log buffer when available.
     */
    private void clearLogs() {
        Log.deleteLog();
        NativeLogSupport.clearLogs();
        refreshLogs();
    }

    /**
     * Copies the currently displayed diagnostic text to the clipboard.
     */
    private void copyLogs() {
        NetworkSupport.copyDiagnostics(context, logsArea.getText());
    }

    /**
     * Builds the full text shown in the form, with ASCII headers for both log sources.
     *
     * @return combined diagnostics text
     */
    private String buildCombinedLogs() {
        StringBuilder out = new StringBuilder();
        appendSection(out, context.text("cn1.logs.section"), cn1LogsText());
        out.append('\n');
        appendSection(out, context.text("native.logs.section"), nativeLogsText());
        return out.toString();
    }

    /**
     * Reads the CN1 log file, or returns the localized empty message.
     *
     * @return CN1 log text
     */
    private String cn1LogsText() {
        String logs = readCn1LogFile();
        return logs == null || logs.trim().length() == 0
                ? context.text("cn1.logs.empty")
                : logs.trim();
    }

    /**
     * Reads the platform-native logs when the native library is available.
     *
     * @return native log text or a localized fallback message
     */
    private String nativeLogsText() {
        if (!NativeLogSupport.isSupported()) {
            return context.text("native.logs.unavailable");
        }
        String logs = NativeLogSupport.readLogs();
        return logs == null || logs.trim().length() == 0
                ? context.text("native.logs.empty")
                : logs.trim();
    }

    /**
     * Appends one titled ASCII section.
     *
     * @param out destination buffer
     * @param title section title
     * @param body section content
     */
    private void appendSection(StringBuilder out, String title, String body) {
        out.append("==== ").append(title).append(" ====").append('\n');
        out.append(body);
        out.append('\n');
        out.append("----");
        out.append('\n');
    }

    /**
     * Reads the CN1 log file configured by {@link com.baeldung.cn1tutorial.DailyRoutine}.
     *
     * @return raw log file content, or an empty string when unavailable
     */
    private String readCn1LogFile() {
        String fileUrl = Log.getInstance().getFileURL();
        if (fileUrl == null || fileUrl.length() == 0 || !FileSystemStorage.getInstance().exists(fileUrl)) {
            return "";
        }
        Reader reader = null;
        try {
            reader = Util.getReader(FileSystemStorage.getInstance().openInputStream(fileUrl));
            char[] buffer = new char[2048];
            int size = reader.read(buffer);
            StringBuilder out = new StringBuilder();
            while (size > -1) {
                out.append(buffer, 0, size);
                size = reader.read(buffer);
            }
            return out.toString();
        } catch (IOException ex) {
            Log.e(ex);
            return "";
        } finally {
            Util.cleanup(reader);
        }
    }
}
