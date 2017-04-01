package org.baeldung.shell.simple;

import java.io.File;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.shell.Bootstrap;
import org.springframework.shell.core.CommandResult;
import org.springframework.shell.core.JLineShellComponent;

public class SimpleCLIUnitTest {

    static JLineShellComponent shell;

    @BeforeClass
    public static void startUp() throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        shell = bootstrap.getJLineShellComponent();
    }

    @AfterClass
    public static void shutdown() {
        shell.stop();
    }

    public static JLineShellComponent getShell() {
        return shell;
    }

    @Test
    public void givenCommandConfig_whenExecutingWebGetCommand_thenCorrectResult() {

        CommandResult resultWebSave = shell.executeCommand("web-get --url https://www.google.com");

        Assert.assertTrue(resultWebSave.isSuccess());
    }

    @Test
    public void givenCommandConfig_whenExecutingWebSaveCommand_thenCorrectResult() {

        shell.executeCommand("admin-enable");
        CommandResult result = shell.executeCommand("web-save --url https://www.google.com --out contents.txt");

        Assert.assertArrayEquals(
                new boolean[]{
                    result.isSuccess(),
                    new File("contents.txt").exists()},
                new boolean[]{true, true});
    }

    @Test
    public void givenCommandConfig_whenAdminEnableCommandExecuted_thenCorrectAvailability() {
        
        CommandResult resultAdminDisable = shell.executeCommand("admin-disable");
        CommandResult resultWebSaveUnavailable = shell.executeCommand("web-save --url https://www.google.com --out contents.txt");
        CommandResult resultAdminEnable = shell.executeCommand("admin-enable");
        CommandResult resultWebSaveAvailable = shell.executeCommand("web-save --url https://www.google.com --out contents.txt");

        Assert.assertArrayEquals(
                new boolean[]{
                    resultAdminDisable.isSuccess(),
                    resultWebSaveUnavailable.isSuccess(),
                    resultAdminEnable.isSuccess(),
                    resultWebSaveAvailable.isSuccess()},
                new boolean[]{true, false, true, true});
    }

    @Test
    public void givenCommandConfig_whenWebSaveCommandExecutedNoOutArgument_thenError() {

        shell.executeCommand("admin-enable");
        CommandResult resultWebSave = shell.executeCommand("web-save --url https://www.google.com");

        Assert.assertEquals(resultWebSave.isSuccess(), false);
    }

    @Test
    public void givenCommandConfig_whenExecutingWebGetCommandWithDefaultArgument_thenCorrectResult() {

        CommandResult result = shell.executeCommand("web-get https://www.google.com");

        Assert.assertEquals(result.isSuccess(), true);
    }

}
