/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package chat;


import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.comet.CometEvent;
import org.apache.catalina.comet.CometProcessor;


/**
 * Helper class to implement Comet functionality.
 */
public class ChatServlet
    extends HttpServlet implements CometProcessor {

    private static final long serialVersionUID = 1L;

    private static final String CHARSET = "UTF-8";

    protected ArrayList<HttpServletResponse> connections =
        new ArrayList<HttpServletResponse>();
    protected transient MessageSender messageSender = null;

    @Override
    public void init() throws ServletException {
        messageSender = new MessageSender();
        Thread messageSenderThread =
            new Thread(messageSender, "MessageSender[" + getServletContext().getContextPath() + "]");
        messageSenderThread.setDaemon(true);
        messageSenderThread.start();
    }

    @Override
    public void destroy() {
        connections.clear();
        messageSender.stop();
        messageSender = null;
    }

    /**
     * Process the given Comet event.
     *
     * @param event The Comet event that will be processed
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void event(CometEvent event)
        throws IOException, ServletException {

        // Note: There should really be two servlets in this example, to avoid
        // mixing Comet stuff with regular connection processing
        HttpServletRequest request = event.getHttpServletRequest();
        HttpServletResponse response = event.getHttpServletResponse();

        if (event.getEventType() == CometEvent.EventType.BEGIN) {
            String action = request.getParameter("action");
            if (action != null) {
                if ("login".equals(action)) {
                    String nickname = request.getParameter("nickname");
                    request.getSession(true).setAttribute("nickname", nickname);
                    response.sendRedirect("index.jsp");
                    event.close();
                    return;
                }
                String nickname = (String) request.getSession(true).getAttribute("nickname");
                String message = request.getParameter("message");
                messageSender.send(nickname, message);
                response.sendRedirect("post.jsp");
                event.close();
                return;
            }
            if (request.getSession(true).getAttribute("nickname") == null) {
                // Redirect to "login"
                log("Redirect to login for session: " + request.getSession(true).getId());
                response.sendRedirect("login.jsp");
                event.close();
                return;
            }
            begin(event, request, response);
        } else if (event.getEventType() == CometEvent.EventType.ERROR) {
            error(event, request, response);
        } else if (event.getEventType() == CometEvent.EventType.END) {
            end(event, request, response);
        } else if (event.getEventType() == CometEvent.EventType.READ) {
            read(event, request, response);
        }
    }

    protected void begin(@SuppressWarnings("unused") CometEvent event,
            HttpServletRequest request, HttpServletResponse response)
        throws IOException {
        log("Begin for session: " + request.getSession(true).getId());

        response.setContentType("text/html; charset=" + CHARSET);

        PrintWriter writer = response.getWriter();
        writer.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");
        writer.println("<html><head><title>JSP Chat</title></head><body bgcolor=\"#FFFFFF\">");
        writer.println("<div>Welcome to the chat. <a href='chat'>Click here to reload this window</a></div>");
        writer.flush();

        synchronized(connections) {
            connections.add(response);
        }

        messageSender.send("Tomcat", request.getSession(true).getAttribute("nickname") + " joined the chat.");
    }

    protected void end(CometEvent event, HttpServletRequest request, HttpServletResponse response)
        throws IOException {
        log("End for session: " + request.getSession(true).getId());
        synchronized(connections) {
            connections.remove(response);
        }

        PrintWriter writer = response.getWriter();
        writer.println("</body></html>");

        event.close();
    }

    protected void error(CometEvent event, HttpServletRequest request, HttpServletResponse response)
        throws IOException {
        log("Error for session: " + request.getSession(true).getId());
        synchronized(connections) {
            connections.remove(response);
        }
        event.close();
    }

    protected void read(CometEvent event, HttpServletRequest request, HttpServletResponse response)
        throws IOException {
        InputStream is = request.getInputStream();
        byte[] buf = new byte[512];
        while (is.available() > 0) {
            log("Available: " + is.available());
            int n = is.read(buf);
            if (n > 0) {
                log("Read " + n + " bytes: " + new String(buf, 0, n)
                        + " for session: " + request.getSession(true).getId());
            } else if (n < 0) {
                log("End of file: " + n);
                end(event, request, response);
                return;
            }
        }
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {
        // Compatibility method: equivalent method using the regular connection model
        response.setContentType("text/html; charset=" + CHARSET);
        PrintWriter writer = response.getWriter();
        writer.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");
        writer.println("<html><head><title>JSP Chat</title></head><body bgcolor=\"#FFFFFF\">");
        writer.println("Chat example only supports Comet processing. ");
        writer.println("Configure a connector that supports Comet and try again.");
        writer.println("</body></html>");
    }


    /**
     * Poller class.
     */
    public class MessageSender implements Runnable {

        protected boolean running = true;
        protected ArrayList<String> messages = new ArrayList<String>();

        public MessageSender() {
            // Default contructor
        }

        public void stop() {
            running = false;
            synchronized (messages) {
                messages.notify();
            }
        }

        public void send(String user, String message) {
            synchronized (messages) {
                messages.add("[" + user + "]: " + message);
                messages.notify();
            }
        }

        /**
         * The background thread that listens for incoming TCP/IP connections and
         * hands them off to an appropriate processor.
         */
        @Override
        public void run() {

            // Loop until we receive a shutdown command
            while (running) {
                String[] pendingMessages;
                synchronized (messages) {
                    try {
                        if (running && messages.size() == 0) {
                            messages.wait();
                        }
                    } catch (InterruptedException e) {
                        // Ignore
                    }
                    pendingMessages = messages.toArray(new String[0]);
                    messages.clear();
                }

                synchronized (connections) {
                    for (int i = 0; i < connections.size(); i++) {
                        try {
                            PrintWriter writer = connections.get(i).getWriter();
                            for (int j = 0; j < pendingMessages.length; j++) {
                                writer.println("<div>"+filter(pendingMessages[j]) + "</div>");
                            }
                            writer.flush();
                        } catch (IOException e) {
                            log("IOException sending message", e);
                        }
                    }
                }

            }

        }

    }

    /**
     * Filter the specified message string for characters that are sensitive
     * in HTML.
     *
     * @param message The message string to be filtered
     * @author Copied from org.apache.catalina.util.RequestUtil#filter(String)
     */
    protected static String filter(String message) {
        if (message == null)
            return (null);

        char content[] = new char[message.length()];
        message.getChars(0, message.length(), content, 0);
        StringBuilder result = new StringBuilder(content.length + 50);
        for (int i = 0; i < content.length; i++) {
            switch (content[i]) {
            case '<':
                result.append("&lt;");
                break;
            case '>':
                result.append("&gt;");
                break;
            case '&':
                result.append("&amp;");
                break;
            case '"':
                result.append("&quot;");
                break;
            default:
                result.append(content[i]);
            }
        }
        return (result.toString());
    }
}
