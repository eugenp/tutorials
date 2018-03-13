/*
* Licensed to the Apache Software Foundation (ASF) under one or more
* contributor license agreements.  See the NOTICE file distributed with
* this work for additional information regarding copyright ownership.
* The ASF licenses this file to You under the Apache License, Version 2.0
* (the "License"); you may not use this file except in compliance with
* the License.  You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package org.apache.tomcat.buildutil;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.FileSet;

/**
 * Ant task that checks that all the files in the given fileset have end-of-line
 * delimiters that are appropriate for the current OS.
 *
 * <p>
 * The goal is to check whether we have problems with svn:eol-style property
 * when files are committed on one OS and then checked on another one.
 */
public class CheckEol extends Task {

    private static final String eoln = System.getProperty("line.separator");

    /** The files to be checked */
    private final List<FileSet> filesets = new LinkedList<FileSet>();

    /**
     * Sets the files to be checked
     *
     * @param fs The fileset to be checked.
     */
    public void addFileset( FileSet fs ) {
        filesets.add( fs );
    }

    /**
     * Perform the check
     *
     * @throws BuildException if an error occurs during execution of
     *    this task.
     */
    @Override
    public void execute() throws BuildException {

        Mode mode = null;
        if ("\n".equals(eoln)) {
            mode = Mode.LF;
        } else if ("\r\n".equals(eoln)) {
            mode = Mode.CRLF;
        } else {
            log("Line ends check skipped, because OS line ends setting is neither LF nor CRLF.",
                    Project.MSG_VERBOSE);
            return;
        }

        int count = 0;

        List<CheckFailure> errors = new ArrayList<CheckFailure>();

        // Step through each file and check.
        for (FileSet fs : filesets) {
            DirectoryScanner ds = fs.getDirectoryScanner(getProject());
            File basedir = ds.getBasedir();
            String[] files = ds.getIncludedFiles();
            if (files.length > 0) {
                log("Checking line ends in " + files.length + " file(s)");
                for (int i = 0; i < files.length; i++) {
                    File file = new File(basedir, files[i]);
                    log("Checking file '" + file + "' for correct line ends",
                            Project.MSG_DEBUG);
                    try {
                        check(file, errors, mode);
                    } catch (IOException e) {
                        throw new BuildException("Could not check file '"
                                + file.getAbsolutePath() + "'", e);
                    }
                    count++;
                }
            }
        }
        if (count > 0) {
            log("Done line ends check in " + count + " file(s), "
                    + errors.size() + " error(s) found.");
        }
        if (errors.size() > 0) {
            String message = "The following files have wrong line ends: "
                    + errors;
            // We need to explicitly write the message to the log, because
            // long BuildException messages may be trimmed. E.g. I observed
            // this problem with Eclipse IDE 3.7.
            log(message, Project.MSG_ERR);
            throw new BuildException(message);
        }
    }

    private static enum Mode {
        LF, CRLF
    }

    private static class CheckFailure {
        private final File file;
        private final int line;
        private final String value;

        public CheckFailure(File file, int line, String value) {
            this.file = file;
            this.line = line;
            this.value = value;
        }

        @Override
        public String toString() {
            return eoln + file + ": uses " + value + " on line " + line;
        }
    }

    private void check(File file, List<CheckFailure> errors, Mode mode)
            throws IOException {
        BufferedInputStream is = new BufferedInputStream(new FileInputStream(
                file));
        try {
            int line = 1;
            int prev = -1;
            int ch;
            while ((ch = is.read()) != -1) {
                if (ch == '\n') {
                    if (mode == Mode.LF && prev == '\r') {
                        errors.add(new CheckFailure(file, line, "CRLF"));
                        return;
                    } else if (mode == Mode.CRLF && prev != '\r') {
                        errors.add(new CheckFailure(file, line, "LF"));
                        return;
                    }
                    line++;
                } else if (prev == '\r') {
                    errors.add(new CheckFailure(file, line, "CR"));
                    return;
                }
                prev = ch;
            }
        } finally {
            is.close();
        }
    }
}
