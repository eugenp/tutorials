package com.baeldung.vaadin;
import static org.junit.Assert.assertEquals;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.vaadin.server.DefaultUIProvider;
import com.vaadin.server.VaadinServlet;


public class VaadinUITests {

    private WebDriver driver;
    private Server server;

    @Before
    public void setUp() throws Exception{
        startJettyServer();
        driver = new HtmlUnitDriver(BrowserVersion.FIREFOX_45,true);
        driver.get("http://localhost:8080");
        Thread.sleep(10000);
    }
    
    @Test 
    public void whenPageLoadedThenShouldSeeURL(){
        String url = driver.getCurrentUrl();
        assertEquals("http://localhost:8080/", url);
    }
    
    @Test
    public void givenLabel_WhenGetValue_ThenValueMatch() {
        WebElement label = driver.findElement(By.id("Label"));
        assertEquals("Label Value", label.getText());
    }
    
    @Test
    public void givenTextField_WhenGetValue_ThenValueMatch() {
        WebElement textField = driver.findElement(By.id("TextField"));
        assertEquals("TextField Value", textField.getAttribute("Value"));
    }
    
    @Test
    public void givenTextArea_WhenGetValue_ThenValueMatch() {
        WebElement textArea = driver.findElement(By.id("TextArea"));
        assertEquals("TextArea Value", textArea.getAttribute("Value"));
    }
    
    @Test
    public void givenDateField_WhenGetValue_ThenValueMatch() {
        WebElement dateField = driver.findElement(By.id("DateField"));
        assertEquals("12/31/69", dateField.getText());
    }
    
    @Test
    public void givenPasswordField_WhenGetValue_ThenValueMatch() {
        WebElement passwordField = driver.findElement(By.id("PasswordField"));
        assertEquals("password", passwordField.getAttribute("Value"));
    }
    
    @After
    public void cleanUp() throws Exception{
        driver.close();
        server.stop();
    }

    public void startJettyServer() throws Exception{

        int maxThreads = 100;
        int minThreads = 10;
        int idleTimeout = 120;

        QueuedThreadPool threadPool = new QueuedThreadPool(maxThreads, minThreads, idleTimeout);
        server = new Server(threadPool);
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(8080);
        server.setConnectors(new Connector[]{connector});
        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        contextHandler.setContextPath("/");
        ServletHolder sh = new ServletHolder(new VaadinServlet());
        contextHandler.addServlet(sh, "/*");
        contextHandler.setInitParameter("ui", VaadinUI.class.getName());
        contextHandler.setInitParameter(VaadinServlet.SERVLET_PARAMETER_UI_PROVIDER, DefaultUIProvider.class.getName());
        server.setHandler(contextHandler);

        server.start();
    }
}