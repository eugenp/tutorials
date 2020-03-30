package com.baeldung.evaluation.tictactoe.adapter.log;

    import com.baeldung.evaluation.tictactoe.port.ILog;

    import java.io.File;
    import java.io.FileNotFoundException;
    import java.io.FileOutputStream;
    import java.io.PrintWriter;
    import java.text.DateFormat;
    import java.text.SimpleDateFormat;
    import java.util.Date;

public class FileLog implements ILog {
    @Override public void saveGame(String playerXName, String playerOName, String game) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            PrintWriter pw = new PrintWriter(new FileOutputStream(new File("log.txt"), true));
            pw.append(dateFormat.format(new Date()) + " - " + playerXName + " VS " + playerOName + " | " + game + System.lineSeparator());
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
