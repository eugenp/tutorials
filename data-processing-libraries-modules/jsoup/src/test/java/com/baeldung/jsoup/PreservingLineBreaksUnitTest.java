package com.baeldung.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Safelist;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class PreservingLineBreaksUnitTest {

    @Test
    public void whenBackSlashNNewLineCharacter_thenPreserveLineBreak() {
        String strHTML = "<html><body>Hello\nworld</body></html>";
        Document.OutputSettings outputSettings = new Document.OutputSettings();
        outputSettings.prettyPrint(false);
        String strWithNewLines = Jsoup.clean(strHTML, "", Safelist.none(), outputSettings);
        assertEquals("Hello\nworld", strWithNewLines);
    }

    @Test
    public void whenHTMLNewLineCharacters_thenPreserveLineBreak() {
        String strHTML = "<html><body>" +
                "Hello" +
                "<br>" +
                "World" +
                "<p>Paragraph</p>" +
                "</body></html>";
        Document jsoupDoc = Jsoup.parse(strHTML);
        Document.OutputSettings outputSettings = new Document.OutputSettings();
        outputSettings.prettyPrint(false);
        jsoupDoc.outputSettings(outputSettings);
        jsoupDoc.select("br").before("\\n");
        jsoupDoc.select("p").before("\\n");
        String str = jsoupDoc.html().replaceAll("\\\\n", "\n");
        String strWithNewLines =
                Jsoup.clean(str, "", Safelist.none(), outputSettings);
        assertEquals("Hello\nWorld\nParagraph", strWithNewLines);
    }
}
