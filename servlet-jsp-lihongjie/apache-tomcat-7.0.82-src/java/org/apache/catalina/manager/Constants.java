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


package org.apache.catalina.manager;


public class Constants {

    public static final String Package = "org.apache.catalina.manager";

    public static final String HTML_HEADER_SECTION;
    public static final String BODY_HEADER_SECTION;
    public static final String MESSAGE_SECTION;
    public static final String MANAGER_SECTION;
    public static final String SERVER_HEADER_SECTION;
    public static final String SERVER_ROW_SECTION;
    public static final String HTML_TAIL_SECTION;
    
    static {
        HTML_HEADER_SECTION =
            "<html>\n" +
            "<head>\n" +
            "<style>\n" +
            org.apache.catalina.util.TomcatCSS.TOMCAT_CSS + "\n" +
            "  table {\n" +
            "    width: 100%;\n" +
            "  }\n" +
            "  td.page-title {\n" +
            "    text-align: center;\n" +
            "    vertical-align: top;\n" +
            "    font-family:sans-serif,Tahoma,Arial;\n" +
            "    font-weight: bold;\n" +
            "    background: white;\n" +
            "    color: black;\n" +
            "  }\n" +
            "  td.title {\n" +
            "    text-align: left;\n" +
            "    vertical-align: top;\n" +
            "    font-family:sans-serif,Tahoma,Arial;\n" +
            "    font-style:italic;\n" +
            "    font-weight: bold;\n" +
            "    background: #D2A41C;\n" +
            "  }\n" +
            "  td.header-left {\n" +
            "    text-align: left;\n" +
            "    vertical-align: top;\n" +
            "    font-family:sans-serif,Tahoma,Arial;\n" +
            "    font-weight: bold;\n" +
            "    background: #FFDC75;\n" +
            "  }\n" +
            "  td.header-center {\n" +
            "    text-align: center;\n" +
            "    vertical-align: top;\n" +
            "    font-family:sans-serif,Tahoma,Arial;\n" +
            "    font-weight: bold;\n" +
            "    background: #FFDC75;\n" +
            "  }\n" +
            "  td.row-left {\n" +
            "    text-align: left;\n" +
            "    vertical-align: middle;\n" +
            "    font-family:sans-serif,Tahoma,Arial;\n" +
            "    color: black;\n" +
            "  }\n" +
            "  td.row-center {\n" +
            "    text-align: center;\n" +
            "    vertical-align: middle;\n" +
            "    font-family:sans-serif,Tahoma,Arial;\n" +
            "    color: black;\n" +
            "  }\n" +
            "  td.row-right {\n" +
            "    text-align: right;\n" +
            "    vertical-align: middle;\n" +
            "    font-family:sans-serif,Tahoma,Arial;\n" +
            "    color: black;\n" +
            "  }\n" +
            "  TH {\n" +
            "    text-align: center;\n" +
            "    vertical-align: top;\n" +
            "    font-family:sans-serif,Tahoma,Arial;\n" +
            "    font-weight: bold;\n" +
            "    background: #FFDC75;\n" +
            "  }\n" +
            "  TD {\n" +
            "    text-align: center;\n" +
            "    vertical-align: middle;\n" +
            "    font-family:sans-serif,Tahoma,Arial;\n" +
            "    color: black;\n" +
            "  }\n" +
            "  form {\n" +
            "    margin: 1;\n" +
            "  }\n" +
            "  form.inline {\n" +
            "    display: inline;\n" +
            "  }\n" +
            "</style>\n";
    
        BODY_HEADER_SECTION =
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
    
        MESSAGE_SECTION =
            "<table border=\"1\" cellspacing=\"0\" cellpadding=\"3\">\n" +
            " <tr>\n" +
            "  <td class=\"row-left\" width=\"10%\">" +
            "<small><strong>{0}</strong></small>&nbsp;</td>\n" +
            "  <td class=\"row-left\"><pre>{1}</pre></td>\n" +
            " </tr>\n" +
            "</table>\n" +
            "<br>\n" +
            "\n";
    
        MANAGER_SECTION =
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
    
        SERVER_HEADER_SECTION =
            "<table border=\"1\" cellspacing=\"0\" cellpadding=\"3\">\n" +
            "<tr>\n" +
            " <td colspan=\"8\" class=\"title\">{0}</td>\n" +
            "</tr>\n" +
            "<tr>\n" +
            " <td class=\"header-center\"><small>{1}</small></td>\n" +
            " <td class=\"header-center\"><small>{2}</small></td>\n" +
            " <td class=\"header-center\"><small>{3}</small></td>\n" +
            " <td class=\"header-center\"><small>{4}</small></td>\n" +
            " <td class=\"header-center\"><small>{5}</small></td>\n" +
            " <td class=\"header-center\"><small>{6}</small></td>\n" +
            " <td class=\"header-center\"><small>{7}</small></td>\n" +
            " <td class=\"header-center\"><small>{8}</small></td>\n" +
            "</tr>\n";
    
        SERVER_ROW_SECTION =
            "<tr>\n" +
            " <td class=\"row-center\"><small>{0}</small></td>\n" +
            " <td class=\"row-center\"><small>{1}</small></td>\n" +
            " <td class=\"row-center\"><small>{2}</small></td>\n" +
            " <td class=\"row-center\"><small>{3}</small></td>\n" +
            " <td class=\"row-center\"><small>{4}</small></td>\n" +
            " <td class=\"row-center\"><small>{5}</small></td>\n" +
            " <td class=\"row-center\"><small>{6}</small></td>\n" +
            " <td class=\"row-center\"><small>{7}</small></td>\n" +
            "</tr>\n" +
            "</table>\n" +
            "<br>\n" +
            "\n";
    
        HTML_TAIL_SECTION =
            "<hr size=\"1\" noshade=\"noshade\">\n" +
            "<center><font size=\"-1\" color=\"#525D76\">\n" +
            " <em>Copyright &copy; 1999-2017, Apache Software Foundation</em>" +
            "</font></center>\n" +
            "\n" +
            "</body>\n" +
            "</html>";
    }

    public static final String CHARSET="utf-8";

    public static final String XML_DECLARATION =
        "<?xml version=\"1.0\" encoding=\""+CHARSET+"\"?>";

    public static final String XML_STYLE =
        "<?xml-stylesheet type=\"text/xsl\" href=\"{0}/xform.xsl\" ?>\n";

}

