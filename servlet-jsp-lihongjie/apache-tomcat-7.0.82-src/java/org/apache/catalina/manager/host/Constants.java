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


package org.apache.catalina.manager.host;


public class Constants {

    public static final String Package = "org.apache.catalina.manager.host";

    public static final String BODY_HEADER_SECTION =
        "<title>{0}</title>\n" +
        "</head>\n" +
        "\n" +
        "<body bgcolor=\"#FFFFFF\">\n" +
        "\n" +
        "<table cellspacing=\"4\" border=\"0\">\n" +
        " <tr>\n" +
        "  <td colspan=\"2\">\n" +
        "   <a href=\"http://tomcat.apache.org/\">\n" +
        "    <img border=\"0\" alt=\"The Tomcat Servlet/JSP Container\"\n" +
        "         align=\"left\" src=\"{0}/images/tomcat.gif\">\n" +
        "   </a>\n" +
        "   <a href=\"http://www.apache.org/\">\n" +
        "    <img border=\"0\" alt=\"The Apache Software Foundation\" align=\"right\"\n" +
        "         src=\"{0}/images/asf-logo.svg\" style=\"width: 266px; height: 83px;\">\n" +
        "   </a>\n" +
        "  </td>\n" +
        " </tr>\n" +
        "</table>\n" +
        "<hr size=\"1\" noshade=\"noshade\">\n" +
        "<table cellspacing=\"4\" border=\"0\">\n" +
        " <tr>\n" +
        "  <td class=\"page-title\" bordercolor=\"#000000\" " +
        "align=\"left\" nowrap>\n" +
        "   <font size=\"+2\">{1}</font>\n" +
        "  </td>\n" +
        " </tr>\n" +
        "</table>\n" +
        "<br>\n" +
        "\n";

    public static final String MESSAGE_SECTION =
        "<table border=\"1\" cellspacing=\"0\" cellpadding=\"3\">\n" +
        " <tr>\n" +
        "  <td class=\"row-left\" width=\"10%\">" +
        "<small><strong>{0}</strong></small>&nbsp;</td>\n" +
        "  <td class=\"row-left\"><pre>{1}</pre></td>\n" +
        " </tr>\n" +
        "</table>\n" +
        "<br>\n" +
        "\n";

    public static final String MANAGER_SECTION =
        "<table border=\"1\" cellspacing=\"0\" cellpadding=\"3\">\n" +
        "<tr>\n" +
        " <td colspan=\"4\" class=\"title\">{0}</td>\n" +
        "</tr>\n" +
        " <tr>\n" +
        "  <td class=\"row-left\"><a href=\"{1}\">{2}</a></td>\n" +
        "  <td class=\"row-center\"><a href=\"{3}\">{4}</a></td>\n" +
        "  <td class=\"row-center\"><a href=\"{5}\">{6}</a></td>\n" +
        "  <td class=\"row-right\"><a href=\"{7}\">{8}</a></td>\n" +
        " </tr>\n" +
        "</table>\n" +
        "<br>\n" +
        "\n";

    public static final String SERVER_HEADER_SECTION =
        "<table border=\"1\" cellspacing=\"0\" cellpadding=\"3\">\n" +
        "<tr>\n" +
        " <td colspan=\"6\" class=\"title\">{0}</td>\n" +
        "</tr>\n" +
        "<tr>\n" +
        " <td class=\"header-center\"><small>{1}</small></td>\n" +
        " <td class=\"header-center\"><small>{2}</small></td>\n" +
        " <td class=\"header-center\"><small>{3}</small></td>\n" +
        " <td class=\"header-center\"><small>{4}</small></td>\n" +
        " <td class=\"header-center\"><small>{5}</small></td>\n" +
        " <td class=\"header-center\"><small>{6}</small></td>\n" +
        "</tr>\n";

    public static final String SERVER_ROW_SECTION =
        "<tr>\n" +
        " <td class=\"row-center\"><small>{0}</small></td>\n" +
        " <td class=\"row-center\"><small>{1}</small></td>\n" +
        " <td class=\"row-center\"><small>{2}</small></td>\n" +
        " <td class=\"row-center\"><small>{3}</small></td>\n" +
        " <td class=\"row-center\"><small>{4}</small></td>\n" +
        " <td class=\"row-center\"><small>{5}</small></td>\n" +
        "</tr>\n" +
        "</table>\n" +
        "<br>\n" +
        "\n";

    public static final String HTML_TAIL_SECTION =
        "<hr size=\"1\" noshade=\"noshade\">\n" +
        "<center><font size=\"-1\" color=\"#525D76\">\n" +
        " <em>Copyright &copy; 1999-2017, Apache Software Foundation</em>" +
        "</font></center>\n" +
        "\n" +
        "</body>\n" +
        "</html>";
    public static final String CHARSET="utf-8";

}

