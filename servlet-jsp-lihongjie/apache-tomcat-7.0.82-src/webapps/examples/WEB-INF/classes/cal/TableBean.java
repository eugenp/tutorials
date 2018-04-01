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
package cal;

import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;

public class TableBean {

    Hashtable<String, Entries> table;
    JspCalendar JspCal;
    Entries entries;
    String date;
    String name = null;
    String email = null;
    boolean processError = false;

    public TableBean() {
        this.table = new Hashtable<String, Entries>(10);
        this.JspCal = new JspCalendar();
        this.date = JspCal.getCurrentDate();
    }

    public void setName(String nm) {
        this.name = nm;
    }

    public String getName() {
        return this.name;
    }

    public void setEmail(String mail) {
        this.email = mail;
    }

    public String getEmail() {
        return this.email;
    }

    public String getDate() {
        return this.date;
    }

    public Entries getEntries() {
        return this.entries;
    }

    public void processRequest(HttpServletRequest request) {

        // Get the name and e-mail.
        this.processError = false;
        if (name == null || name.equals(""))
            setName(request.getParameter("name"));
        if (email == null || email.equals(""))
            setEmail(request.getParameter("email"));
        if (name == null || email == null || name.equals("")
                || email.equals("")) {
            this.processError = true;
            return;
        }

        // Get the date.
        String dateR = request.getParameter("date");
        if (dateR == null)
            date = JspCal.getCurrentDate();
        else if (dateR.equalsIgnoreCase("next"))
            date = JspCal.getNextDate();
        else if (dateR.equalsIgnoreCase("prev"))
            date = JspCal.getPrevDate();

        entries = table.get(date);
        if (entries == null) {
            entries = new Entries();
            table.put(date, entries);
        }

        // If time is provided add the event.
        String time = request.getParameter("time");
        if (time != null)
            entries.processRequest(request, time);
    }

    public boolean getProcessError() {
        return this.processError;
    }
}
