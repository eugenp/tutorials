package com.baeldung.test.dependencyinjection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

import java.text.ParseException;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.junit.BeforeClass;
import org.junit.Test;

import com.baeldung.dependencyinjection.imagefileeditors.PngFileEditor;
import com.baeldung.dependencyinjection.imageprocessors.ImageFileProcessor;
import com.baeldung.dependencyinjection.loggers.TimeLogger;

public class ImageProcessorUnitTest {

    private static ImageFileProcessor imageFileProcessor;

    @BeforeClass
    public static void setImageProcessorInstance() {
        Weld weld = new Weld();
        WeldContainer container = weld.initialize();
        imageFileProcessor = container.select(ImageFileProcessor.class)
            .get();
        container.shutdown();
    }

    @Test
    public void givenImageProcessorInstance_whenInjectedPngFileEditorandTimeLoggerInstances_thenTwoAssertions() {
        assertThat(imageFileProcessor.getImageFileditor()).isInstanceOf(PngFileEditor.class);
        assertThat(imageFileProcessor.getTimeLogger()).isInstanceOf(TimeLogger.class);
    }

    @Test
    public void givenImageProcessorInstance_whenCalledopenFile_thenOneAssertion() throws ParseException {
        LocalTime currentTime = LocalTime.now();

        String openFileLog = imageFileProcessor.openFile("file1.png");
        assertThat(openFileLog).contains("Opening PNG file file1.png at: ");

        LocalTime loggedTime = getLoggedTime(openFileLog);
        assertThat(loggedTime).isCloseTo(currentTime, within(2, ChronoUnit.MINUTES));
    }

    @Test
    public void givenImageProcessorInstance_whenCallededitFile_thenOneAssertion() throws ParseException {
        LocalTime currentTime = LocalTime.now();

        String editFileLog = imageFileProcessor.editFile("file1.png");
        assertThat(editFileLog).contains("Editing PNG file file1.png at: ");

        LocalTime loggedTime = getLoggedTime(editFileLog);
        assertThat(loggedTime).isCloseTo(currentTime, within(2, ChronoUnit.MINUTES));
    }

    @Test
    public void givenImageProcessorInstance_whenCalledwriteFile_thenOneAssertion() throws ParseException {
        LocalTime currentTime = LocalTime.now();

        String writeFileLog = imageFileProcessor.writeFile("file1.png");
        assertThat(writeFileLog).contains("Writing PNG file file1.png at: ");

        LocalTime loggedTime = getLoggedTime(writeFileLog);
        assertThat(loggedTime).isCloseTo(currentTime, within(2, ChronoUnit.MINUTES));
    }

    @Test
    public void givenImageProcessorInstance_whenCalledsaveFile_thenOneAssertion() throws ParseException {
        LocalTime currentTime = LocalTime.now();

        String saveFileLog = imageFileProcessor.saveFile("file1.png");
        assertThat(saveFileLog).contains("Saving PNG file file1.png at: ");

        LocalTime loggedTime = getLoggedTime(saveFileLog);
        assertThat(loggedTime).isCloseTo(currentTime, within(2, ChronoUnit.MINUTES));
    }

    private LocalTime getLoggedTime(String log) throws ParseException {
        String logTimeString = log.split("at: ")[1];

        int hour = Integer.valueOf(logTimeString.split(":")[0]);
        int minutes = Integer.valueOf(logTimeString.split(":")[1]);

        LocalTime loggedTime = LocalTime.of(hour, minutes);
        return loggedTime;
    }
}