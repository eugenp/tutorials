package com.baeldung.script;

import org.apache.ibatis.jdbc.ScriptRunner;
import java.sql.Connection;

public class MyBatisScriptUtility {
    public static void runScript(
            String path,
            Connection connection,
            boolean sendFullScript,
            boolean stopOnError
    ) throws Exception {
        ScriptRunner scriptRunner = new ScriptRunner(connection);
        scriptRunner.setSendFullScript(sendFullScript);
        scriptRunner.setStopOnError(stopOnError);
        scriptRunner.runScript(new java.io.FileReader(path));
    }
}
