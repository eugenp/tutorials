package com.baeldung.simpleopenai;

import java.lang.System.Logger;
import java.lang.System.Logger.Level;

public class GeminiApiKeyCheck {

    public static void main(String[] args) {

        Logger logger = System.getLogger("simpleopenai");
        logger.log(Level.INFO,
            "GEMINI_API_KEY configured: {0}",
            System.getenv("GEMINI_API_KEY") != null);

    }

}
