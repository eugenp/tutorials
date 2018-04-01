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
package org.apache.catalina.connector;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Test;

import org.apache.catalina.Context;
import org.apache.catalina.Globals;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.startup.TomcatBaseTest;
import org.apache.tomcat.util.buf.ByteChunk;

public class TestSendFile extends TomcatBaseTest {

    public static final int ITERATIONS = 10;
    public static final int EXPECTED_CONTENT_LENGTH = 100000;

    @Test
    public void testSendFile() throws Exception {

        Tomcat tomcat = getTomcatInstance();

        Context root = tomcat.addContext("", TEMP_DIR);

        File[] files = new File[ITERATIONS];
        for (int i = 0; i < ITERATIONS; i++) {
            files[i] = generateFile(TEMP_DIR, "-" + i, EXPECTED_CONTENT_LENGTH * (i + 1));
        }
        try {

            for (int i = 0; i < ITERATIONS; i++) {
                WritingServlet servlet = new WritingServlet(files[i]);
                Tomcat.addServlet(root, "servlet" + i, servlet);
                root.addServletMapping("/servlet" + i, "servlet" + i);
            }

            tomcat.start();

            ByteChunk bc = new ByteChunk();
            Map<String, List<String>> respHeaders = new HashMap<String, List<String>>();
            for (int i = 0; i < ITERATIONS; i++) {
                long start = System.currentTimeMillis();
                int rc = getUrl("http://localhost:" + getPort() + "/servlet" + i, bc, null,
                        respHeaders);
                assertEquals(HttpServletResponse.SC_OK, rc);
                System.out.println("Client received " + bc.getLength() + " bytes in "
                        + (System.currentTimeMillis() - start) + " ms.");
                assertEquals(EXPECTED_CONTENT_LENGTH * (i + 1), bc.getLength());

                bc.recycle();
            }
        } finally {
            for (File f : files) {
                f.delete();
            }
        }
    }

    public File generateFile(String dir, String suffix, int size) throws IOException {
        String name = "testSendFile-" + System.currentTimeMillis() + suffix + ".txt";
        File f = new File(dir, name);
        FileWriter fw = null;
        BufferedWriter w = null;
        try  {
            fw = new FileWriter(f, false);
            w = new BufferedWriter(fw);
            int defSize = 8192;
            while (size > 0) {
                int bytes = Math.min(size, defSize);
                char[] b = new char[bytes];
                Arrays.fill(b, 'X');
                w.write(b);
                size = size - bytes;
            }
            w.flush();
        } finally {
            if (w != null) {
                w.close();
            }
            if (fw != null) {
                fw.close();
            }
        }
        System.out
                .println("Created file:" + f.getAbsolutePath() + " with " + f.length() + " bytes.");
        return f;

    }


    private static class WritingServlet extends HttpServlet {

        private static final long serialVersionUID = 1L;

        private final File f;

        public WritingServlet(File f) {
            this.f = f;
        }

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                throws ServletException, IOException {

            resp.setContentType("'application/octet-stream");
            resp.setCharacterEncoding("ISO-8859-1");
            resp.setContentLength((int) f.length());
            if (Boolean.TRUE.equals(req.getAttribute(Globals.SENDFILE_SUPPORTED_ATTR))) {
                req.setAttribute(Globals.SENDFILE_FILENAME_ATTR, f.getAbsolutePath());
                req.setAttribute(Globals.SENDFILE_FILE_START_ATTR, new Long(0));
                req.setAttribute(Globals.SENDFILE_FILE_END_ATTR, new Long(f.length()));
            } else {
                byte[] c = new byte[8192];
                BufferedInputStream in = null;
                try {
                    in = new BufferedInputStream(new FileInputStream(f));
                    int len = 0;
                    int written = 0;
                    long start = System.currentTimeMillis();
                    do {
                        len = in.read(c);
                        if (len > 0) {
                            resp.getOutputStream().write(c, 0, len);
                            written += len;
                        }
                    } while (len > 0);
                    System.out.println("Server Wrote " + written + " bytes in "
                            + (System.currentTimeMillis() - start) + " ms.");
                } finally {
                    if (in != null) {
                        in.close();
                    }
                }
            }
        }
    }


    @Test
    public void testBug60409() throws Exception {
        Tomcat tomcat = getTomcatInstance();

        Context ctx = tomcat.addContext("", TEMP_DIR);
        File file = generateFile(TEMP_DIR, "", EXPECTED_CONTENT_LENGTH);
        Tomcat.addServlet(ctx, "test", new Bug60409Servlet(file));
        ctx.addServletMapping("/", "test");

        tomcat.start();

        ByteChunk bc = new ByteChunk();
        getUrl("http://localhost:" + getPort() + "/test/?" + Globals.SENDFILE_SUPPORTED_ATTR
                + "=true", bc, null);

        CountDownLatch latch = new CountDownLatch(2);
        List<Throwable> exceptions = new ArrayList<Throwable>();
        new Thread(
                new RequestExecutor("http://localhost:" + getPort() + "/test/", latch, exceptions))
                        .start();
        new Thread(
                new RequestExecutor("http://localhost:" + getPort() + "/test/", latch, exceptions))
                        .start();

        latch.await(3000, TimeUnit.MILLISECONDS);

        if (exceptions.size() > 0) {
            Assert.fail();
        }
    }

    private static final class Bug60409Servlet extends HttpServlet {
        private static final long serialVersionUID = 1L;
        private final File file;

        Bug60409Servlet(File file) {
            this.file = file;
        }

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                throws ServletException, IOException {
            if (Boolean.valueOf(req.getParameter(Globals.SENDFILE_SUPPORTED_ATTR)).booleanValue()) {
                resp.setContentType("'application/octet-stream");
                resp.setCharacterEncoding("ISO-8859-1");
                resp.setContentLength((int) file.length());
                req.setAttribute(Globals.SENDFILE_FILENAME_ATTR, file.getAbsolutePath());
                req.setAttribute(Globals.SENDFILE_FILE_START_ATTR, new Long(0));
                req.setAttribute(Globals.SENDFILE_FILE_END_ATTR, new Long(file.length()));
                file.delete();
            } else {
                byte[] c = new byte[1024];
                Random rd = new Random();
                rd.nextBytes(c);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                resp.getOutputStream().write(c);
            }
        }

    }

    private static final class RequestExecutor implements Runnable {
        private final String url;
        private final CountDownLatch latch;
        private final List<Throwable> exceptions;

        RequestExecutor(String url, CountDownLatch latch, List<Throwable> exceptions) {
            this.url = url;
            this.latch = latch;
            this.exceptions = exceptions;
        }

        @Override
        public void run() {
            try {
                ByteChunk result = new ByteChunk();
                int rc = getUrl(url, result, null);
                Assert.assertEquals(HttpServletResponse.SC_OK, rc);
                Assert.assertEquals(1024, result.getLength());
            } catch (Throwable e) {
                e.printStackTrace();
                exceptions.add(e);
            } finally {
                latch.countDown();
            }
        }

    }
}
