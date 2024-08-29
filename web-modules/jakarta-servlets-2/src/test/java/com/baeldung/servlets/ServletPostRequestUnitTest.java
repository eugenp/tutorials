package com.baeldung.servlets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.baeldung.model.Product;
import com.google.gson.Gson;

class ServletPostRequestUnitTest {

    private FormDataServlet formDataServlet;
    private RawDataServlet rawDataServlet;
    private ProductServlet productServlet;
    private OrderServlet orderServlet;
    private FileUploadServlet fileUploadServlet;

    @BeforeEach
    void setUp() {
        formDataServlet = new FormDataServlet();
        rawDataServlet = new RawDataServlet();
        productServlet = new ProductServlet();
        orderServlet = new OrderServlet();
        fileUploadServlet = new FileUploadServlet();
    }

    @Test
    void whenPostRequestWithFormData_thenReturnCorrectResponse() throws IOException {
        HttpServletRequest mockedServletRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockedServletResponse = mock(HttpServletResponse.class);

        when(mockedServletRequest.getParameter("first_name")).thenReturn("John");
        when(mockedServletRequest.getParameter("last_name")).thenReturn("Doe");

        StringWriter writer = new StringWriter();

        when(mockedServletResponse.getWriter()).thenReturn(new PrintWriter(writer));

        formDataServlet.doPost(mockedServletRequest, mockedServletResponse);

        assertThat(writer.toString()).isEqualTo("Full Name: John Doe");
    }

    @Test
    void whenPostRequestWithRawData_thenReturnCorrectResponse() throws IOException {
        HttpServletRequest mockedServletRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockedServletResponse = mock(HttpServletResponse.class);

        String payload = "Red red car, blue blue sky, green green grass";

        when(mockedServletRequest.getReader()).thenReturn(new BufferedReader(new StringReader(payload)));

        StringWriter writer = new StringWriter();

        when(mockedServletResponse.getWriter()).thenReturn(new PrintWriter(writer));

        rawDataServlet.doPost(mockedServletRequest, mockedServletResponse);

        assertThat(writer.toString()).isEqualTo("{Red=1, red=1, green=2, blue=2, car,=1, grass=1, sky,=1}");
    }

    @Test
    void whenPostRequestWithJsonPayload_thenReturnCorrectResponse() throws IOException {
        HttpServletRequest mockedServletRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockedServletResponse = mock(HttpServletResponse.class);

        Product product = new Product(1, "Product A", "This is Product A", 19.99, "Electronics");

        when(mockedServletRequest.getReader()).thenReturn(new BufferedReader(new StringReader(new Gson().toJson(product))));
        when(mockedServletRequest.getContentType()).thenReturn("application/json");

        StringWriter writer = new StringWriter();

        when(mockedServletResponse.getWriter()).thenReturn(new PrintWriter(writer));

        productServlet.doPost(mockedServletRequest, mockedServletResponse);

        assertThat(writer.toString()).isEqualTo("Added new Product with name: Product A");
    }

    @Test
    void whenPostRequestWithXMLPayload_thenReturnCorrectResponse() throws IOException {
        HttpServletRequest mockedServletRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockedServletResponse = mock(HttpServletResponse.class);

        String payload = "<Order>\n" + "    <orderId>12345</orderId>\n" + "    <customerName>John Doe</customerName>\n" + "    <product>Widget</product>\n" +
            "    <quantity>10</quantity>\n" + "</Order>";

        when(mockedServletRequest.getReader()).thenReturn(new BufferedReader(new StringReader(payload)));
        when(mockedServletRequest.getContentType()).thenReturn("application/xml");

        StringWriter writer = new StringWriter();

        when(mockedServletResponse.getWriter()).thenReturn(new PrintWriter(writer));

        orderServlet.doPost(mockedServletRequest, mockedServletResponse);

        assertThat(writer.toString()).isEqualTo("Created new Order with orderId: 12345 for Product: Widget");
    }

    @Test
    void whenPostRequestWithMultipartFile_thenUploadFile() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        ServletConfig servletConfig = mock(ServletConfig.class);
        ServletContext servletContext = mock(ServletContext.class);

        Part filePart = mock(Part.class);
        PrintWriter writer = mock(PrintWriter.class);

        when(servletConfig.getServletContext()).thenReturn(servletContext);
        when(servletContext.getRealPath("")).thenReturn(System.getProperty("java.io.tmpdir"));

        fileUploadServlet.init(servletConfig);

        when(request.getPart("file")).thenReturn(filePart);
        when(filePart.getSubmittedFileName()).thenReturn("testfile.txt");
        when(filePart.getInputStream()).thenReturn(new ByteArrayInputStream("file content".getBytes()));

        when(response.getWriter()).thenReturn(writer);

        fileUploadServlet.doPost(request, response);

        verify(writer).println(contains("File uploaded to:"));
    }
}