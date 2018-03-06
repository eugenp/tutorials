<?xml version="1.0" encoding="ISO-8859-1"?>
<!--
  Licensed to the Apache Software Foundation (ASF) under one or more
  contributor license agreements.  See the NOTICE file distributed with
  this work for additional information regarding copyright ownership.
  The ASF licenses this file to You under the Apache License, Version 2.0
  (the "License"); you may not use this file except in compliance with
  the License.  You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
-->
<!-- Content Stylesheet for "tomcat-docs" Documentation -->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  version="1.0">


  <!-- Output method -->
  <xsl:output method="html"
            encoding="iso-8859-1"
              indent="no"/>


  <!-- Defined parameters (overrideable) -->
  <xsl:param    name="home-name"           select="'The Tomcat Project'"/>
  <xsl:param    name="home-href"           select="'http://tomcat.apache.org/'"/>
  <xsl:param    name="home-logo"           select="'/images/tomcat.png'"/>
  <xsl:param    name="printer-logo"        select="'/images/printer.gif'"/>
  <xsl:param    name="apache-logo"         select="'/images/asf-logo.svg'"/>
  <xsl:param    name="subdir"              select="''"/>
  <xsl:param    name="relative-path"       select="'.'"/>
  <xsl:param    name="version"             select="'7.0.x'"/>
  <xsl:param    name="majorversion"        select="'7'"/>
  <xsl:param    name="majorminorversion"   select="'7.0'"/>
  <xsl:param    name="build-date"          select="'MMM d yyyy'"/>
  <xsl:param    name="year"                select="'yyyy'"/>
  <xsl:param    name="void-image"          select="'/images/void.gif'"/>
  <xsl:param    name="project-menu"        select="'menu'"/>
  <xsl:param    name="buglink"             select="'http://bz.apache.org/bugzilla/show_bug.cgi?id='"/>
  <xsl:param    name="revlink"             select="'http://svn.apache.org/viewvc?view=rev&amp;rev='"/>
  <xsl:param    name="doclink"             select="'http://tomcat.apache.org/tomcat-7.0-doc'"/>
  <xsl:param    name="sylink"              select="'http://tomcat.apache.org/security-7.html'"/>
  <xsl:param    name="dllink"              select="'http://tomcat.apache.org/download-70.cgi'"/>
  <xsl:param    name="sitedir"             select="''"/>
  <xsl:param    name="filename"            select="'-'"/>
  <!-- Defined variables (non-overrideable) -->
  <xsl:variable name="body-bg"          select="'#ffffff'"/>
  <xsl:variable name="body-fg"          select="'#000000'"/>
  <xsl:variable name="body-link"        select="'#525D76'"/>
  <xsl:variable name="banner-bg"        select="'#525D76'"/>
  <xsl:variable name="banner-fg"        select="'#ffffff'"/>
  <xsl:variable name="sub-banner-bg"    select="'#828DA6'"/>
  <xsl:variable name="sub-banner-fg"    select="'#ffffff'"/>
  <xsl:variable name="source-color"     select="'#023264'"/>
  <xsl:variable name="attributes-color" select="'#023264'"/>
  <xsl:variable name="table-th-bg"      select="'#039acc'"/>
  <xsl:variable name="table-td-bg"      select="'#a0ddf0'"/>
  <xsl:variable name="commentslink"><xsl:value-of select="$relative-path"/>/comments.html</xsl:variable>

  <!-- Process an entire document into an HTML page -->
  <xsl:template match="document">
  <xsl:variable name="project"
              select="document('project.xml')/project"/>
    <html>
    <head>
    <title><xsl:value-of select="project/title"/> (<xsl:value-of select="$version"/>) - <xsl:value-of select="properties/title"/></title>
    <xsl:for-each select="properties/author">
      <xsl:variable name="name">
        <xsl:value-of select="."/>
      </xsl:variable>
      <!--
      <xsl:variable name="email">
        <xsl:value-of select="@email"/>
      </xsl:variable>
       -->
      <meta name="author" content="{$name}"/>
      <!-- Don't publish e-mail addresses
      <meta name="email" content="{$email}"/>
       -->
    </xsl:for-each>
<style type="text/css" media="print">
    .noPrint {display: none;}
    td#mainBody {width: 100%;}
</style>
<style type="text/css"><![CDATA[
code {background-color:rgb(224,255,255);padding:0 0.1em;}
code.attributeName, code.propertyName {background-color:transparent;}


table {
  border-collapse: collapse;
  text-align: left;
}
table *:not(table) {
  /* Prevent border-collapsing for table child elements like <div> */
  border-collapse: separate;
}

th {
  text-align: left;
}


div.codeBox pre code, code.attributeName, code.propertyName, code.noHighlight, .noHighlight code {
  background-color: transparent;
}
div.codeBox {
  overflow: auto;
  margin: 1em 0;
}
div.codeBox pre {
  margin: 0;
  padding: 4px;
  border: 1px solid #999;
  border-radius: 5px;
  background-color: #eff8ff;
  display: table; /* To prevent <pre>s from taking the complete available width. */
  /*
  When it is officially supported, use the following CSS instead of display: table
  to prevent big <pre>s from exceeding the browser window:
  max-width: available;
  width: min-content;
  */
}

div.codeBox pre.wrap {
  white-space: pre-wrap;
}


table.defaultTable tr, table.detail-table tr {
    border: 1px solid #CCC;
}

table.defaultTable tr:nth-child(even), table.detail-table tr:nth-child(even) {
    background-color: #FAFBFF;
}

table.defaultTable tr:nth-child(odd), table.detail-table tr:nth-child(odd) {
    background-color: #EEEFFF;
}

table.defaultTable th, table.detail-table th {
  background-color: #88b;
  color: #fff;
}

table.defaultTable th, table.defaultTable td, table.detail-table th, table.detail-table td {
  padding: 5px 8px;
}


p.notice {
    border: 1px solid rgb(255, 0, 0);
    background-color: rgb(238, 238, 238);
    color: rgb(0, 51, 102);
    padding: 0.5em;
    margin: 1em 2em 1em 1em;
}
]]></style>
    </head>

    <body bgcolor="{$body-bg}" text="{$body-fg}" link="{$body-link}"
          alink="{$body-link}" vlink="{$body-link}">

    <table border="0" width="100%" cellspacing="0">

      <xsl:comment>PAGE HEADER</xsl:comment>
      <tr>
        <td>
        <xsl:if test="project/logo">
          <xsl:variable name="alt">
            <xsl:value-of select="project/logo"/>
          </xsl:variable>
          <xsl:variable name="home">
            <xsl:value-of select="project/@href"/>
          </xsl:variable>
          <xsl:variable name="src">
            <xsl:value-of select="$relative-path"/><xsl:value-of select="project/logo/@href"/>
          </xsl:variable>

          <xsl:comment>PROJECT LOGO</xsl:comment>
          <a href="{$home}">
            <img src="{$src}" align="right" alt="{$alt}" border="0"/>
          </a>
        </xsl:if>
        </td>
        <td>
          <h1><font face="arial,helvetica,sanserif"><xsl:value-of select="$project/title"/></font></h1>
          <font face="arial,helvetica,sanserif">Version <xsl:value-of select="$version"/>, <xsl:value-of select="$build-date"/></font>
        </td>
        <td>
          <xsl:comment>APACHE LOGO</xsl:comment>
          <xsl:variable name="src">
            <xsl:value-of select="$relative-path"/><xsl:value-of select="$apache-logo"/>
          </xsl:variable>
          <a href="http://www.apache.org/">
            <img src="{$src}" align="right" alt="Apache Logo" border="0" style="width: 266px;height: 83px;"/>
          </a>
        </td>
      </tr>
    </table>

    <table border="0" width="100%" cellspacing="4">

      <xsl:comment>HEADER SEPARATOR</xsl:comment>
      <tr>
        <td colspan="2">
          <hr noshade="noshade" size="1"/>
        </td>
      </tr>

      <tr>

        <xsl:comment>LEFT SIDE NAVIGATION</xsl:comment>
        <td width="20%" valign="top" nowrap="nowrap" class="noPrint">
          <xsl:apply-templates select="project/body/menu"/>
        </td>

        <xsl:comment>RIGHT SIDE MAIN BODY</xsl:comment>
        <td width="80%" valign="top" align="left" id="mainBody">
          <h1><xsl:value-of select="properties/title"/></h1>
          <xsl:apply-templates select="body/section"/>
        </td>

      </tr>

      <xsl:if test="not(properties/no-comments)">
      <tr class="noPrint">

        <td width="20%" valign="top" nowrap="nowrap" class="noPrint">
        </td>
        <td width="80%" valign="top" align="left">
          <table border="0" cellspacing="0" cellpadding="2">
            <!-- Comment heading -->
            <tr><td bgcolor="{$banner-bg}">
                <font color="{$banner-fg}" face="arial,helvetica.sanserif">
                <a name="comments_section" id="comments_section"><strong>Comments</strong></a></font>
              </td>
            </tr>
            <!-- Comment body -->
            <tr><td>
            <blockquote>
            <p class="notice">
              <strong>Notice: </strong>This comments section collects your suggestions
              on improving documentation for Apache Tomcat.<br/><br/>
              If you have trouble and need help, read
              <a href="http://tomcat.apache.org/findhelp.html">Find Help</a> page
              and ask your question on the tomcat-users
              <a href="http://tomcat.apache.org/lists.html">mailing list</a>.
              Do not ask such questions here. This is not a Q&amp;A section.<br/><br/>
              The Apache Comments System is explained <a href="{$commentslink}">here</a>.
              Comments may be removed by our moderators if they are either
              implemented or considered invalid/off-topic.</p>
              <script type="text/javascript">
              <xsl:text disable-output-escaping="yes"><![CDATA[<!--//--><![CDATA[//><!--
              var comments_shortname = 'tomcat';
              var comments_identifier = 'http://tomcat.apache.org/]]></xsl:text><xsl:value-of select="$sitedir"/><xsl:value-of select="$subdir"/><xsl:value-of select="substring($filename,1,string-length($filename)-4)"/><xsl:text disable-output-escaping="yes"><![CDATA[.html';
              (function(w, d) {
                  if (w.location.hostname.toLowerCase() == "tomcat.apache.org") {
                      d.write('<div id="comments_thread"><\/div>');
                      var s = d.createElement('script');
                      s.type = 'text/javascript';
                      s.async = true;
                      s.src = 'https://comments.apache.org/show_comments.lua?site=' + comments_shortname + '&page=' + comments_identifier;
                      (d.getElementsByTagName('head')[0] || d.getElementsByTagName('body')[0]).appendChild(s);
                  }
                  else {
                      d.write('<div id="comments_thread"><strong>Comments are disabled for this page at the moment.<\/strong><\/div>');
                  }
              })(window, document);
              //--><!]]]]>></xsl:text></script>
            </blockquote></td></tr>
          </table>
        </td>
      </tr>
      </xsl:if>

      <xsl:comment>FOOTER SEPARATOR</xsl:comment>
      <tr>
        <td colspan="2">
          <hr noshade="noshade" size="1"/>
        </td>
      </tr>

      <xsl:comment>PAGE FOOTER</xsl:comment>
      <tr><td colspan="2">
        <div align="center"><font color="{$body-link}" size="-1"><em>
        Copyright &#169; 1999-<xsl:value-of select="$year"/>, Apache Software Foundation
        </em></font></div>
      </td></tr>

    </table>
    </body>
    </html>

  </xsl:template>


  <!-- Process a menu for the navigation bar -->
  <xsl:template match="menu">
    <p><strong><xsl:value-of select="@name"/></strong></p>
    <ul>
      <xsl:apply-templates select="item"/>
    </ul>
  </xsl:template>


  <!-- Process a menu item for the navigation bar -->
  <xsl:template match="item">
    <xsl:variable name="href">
      <xsl:value-of select="@href"/>
    </xsl:variable>
    <li><a href="{$href}"><xsl:value-of select="@name"/></a></li>
  </xsl:template>


  <!-- Process a documentation section -->
  <xsl:template match="section">
    <xsl:variable name="name">
      <xsl:choose>
        <xsl:when test="@anchor">
          <xsl:value-of select="@anchor" />
        </xsl:when>
        <xsl:otherwise>
          <xsl:value-of select="@name"/>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:variable name="name2">
      <xsl:value-of select="translate($name, ' #', '__')"/>
    </xsl:variable>
    <table border="0" cellspacing="0" cellpadding="2">
      <!-- Section heading -->
      <tr><td bgcolor="{$banner-bg}">
          <font color="{$banner-fg}" face="arial,helvetica.sanserif">
          <xsl:if test="$name != $name2"><a name="{$name}"><xsl:comment>()</xsl:comment></a></xsl:if>
          <a name="{$name2}">
          <strong><xsl:value-of select="@name"/></strong></a></font>
        </td>
      <xsl:if test="@rtext">
        <!-- Additional right-aligned text cell in section heading. It is used by changelog.xml -->
        <td align="right" bgcolor="{$banner-bg}">
          <font color="{$banner-fg}" face="arial,helvetica.sanserif">
          <strong><xsl:value-of select="@rtext"/></strong></font>
        </td>
      </xsl:if>
      </tr>
      <!-- Section body -->
      <tr><td>
      <xsl:if test="@rtext">
          <xsl:attribute name="colspan">2</xsl:attribute>
      </xsl:if>
      <blockquote>
        <xsl:apply-templates/>
      </blockquote></td></tr>
    </table>
  </xsl:template>


  <!-- Process a documentation subsection -->
  <xsl:template match="subsection">
    <xsl:variable name="name">
      <xsl:choose>
        <xsl:when test="@anchor">
          <xsl:value-of select="@anchor" />
        </xsl:when>
        <xsl:otherwise>
          <xsl:if test="
              count(//*[self::section or self::subsection][@name=current()/@name]) &gt; 1
              ">
            <xsl:value-of select="concat(parent::*[self::section or self::subsection]/@name, '/')"/>
          </xsl:if>
          <xsl:value-of select="@name"/>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:variable name="name2">
      <xsl:value-of select="translate($name, ' #', '__')"/>
    </xsl:variable>
    <table border="0" cellspacing="0" cellpadding="2">
      <!-- Subsection heading -->
      <tr><td bgcolor="{$sub-banner-bg}">
          <font color="{$sub-banner-fg}" face="arial,helvetica.sanserif">
          <xsl:if test="$name != $name2"><a name="{$name}"><xsl:comment>()</xsl:comment></a></xsl:if>
          <a name="{$name2}">
          <strong><xsl:value-of select="@name"/></strong></a></font>
      </td></tr>
      <!-- Subsection body -->
      <tr><td><blockquote>
        <xsl:apply-templates/>
      </blockquote></td></tr>
    </table>
  </xsl:template>


  <!-- Generate table of contents -->
  <xsl:template match="toc">
    <ul><xsl:apply-templates mode="toc" select="following::section"/></ul>
  </xsl:template>

  <xsl:template mode="toc" match="section|subsection">
    <xsl:variable name="name">
      <xsl:choose>
        <xsl:when test="@anchor">
          <xsl:value-of select="@anchor" />
        </xsl:when>
        <xsl:otherwise>
          <xsl:if test="local-name()='subsection' and
              count(//*[self::section or self::subsection][@name=current()/@name]) &gt; 1
              ">
            <xsl:value-of select="concat(parent::*[self::section or self::subsection]/@name, '/')"/>
          </xsl:if>
          <xsl:value-of select="@name"/>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:variable name="name2">
      <xsl:value-of select="translate($name, ' #', '__')"/>
    </xsl:variable>
    <li><a href="#{$name2}"><xsl:value-of select="@name"/></a>
    <xsl:if test="subsection">
      <ol><xsl:apply-templates mode="toc" select="subsection"/></ol>
    </xsl:if>
    </li>
  </xsl:template>


  <!-- Process a source code example -->
  <xsl:template match="source">
  <div class="codeBox">
    <pre>
      <xsl:if test="@wrapped='true'">
        <xsl:attribute name="class">wrap</xsl:attribute>
      </xsl:if>
      <code><xsl:apply-templates/></code>
    </pre>
  </div>
  </xsl:template>

  <!-- Process an attributes list with nested attribute elements -->
  <xsl:template match="attributes">
    <table border="1" cellpadding="5">
      <tr>
        <th width="15%" bgcolor="{$attributes-color}">
          <font color="#ffffff">Attribute</font>
        </th>
        <th width="85%" bgcolor="{$attributes-color}">
          <font color="#ffffff">Description</font>
        </th>
      </tr>
      <xsl:for-each select="attribute">
        <tr>
          <td align="left" valign="center">
            <xsl:if test="@required = 'true'">
              <strong><code class="attributeName"><xsl:value-of select="@name"/></code></strong>
            </xsl:if>
            <xsl:if test="@required != 'true'">
              <code class="attributeName"><xsl:value-of select="@name"/></code>
            </xsl:if>
          </td>
          <td align="left" valign="center">
            <xsl:apply-templates/>
          </td>
        </tr>
      </xsl:for-each>
    </table>
  </xsl:template>

  <!-- Process a properties list with nested property elements -->
  <xsl:template match="properties">
    <table border="1" cellpadding="5">
      <tr>
        <th width="15%" bgcolor="{$attributes-color}">
          <font color="#ffffff">Property</font>
        </th>
        <th width="85%" bgcolor="{$attributes-color}">
          <font color="#ffffff">Description</font>
        </th>
      </tr>
      <xsl:for-each select="property">
        <tr>
          <td align="left" valign="center">
            <code class="propertyName"><xsl:value-of select="@name"/></code>
          </td>
          <td align="left" valign="center">
            <xsl:apply-templates/>
          </td>
        </tr>
      </xsl:for-each>
    </table>
  </xsl:template>

  <!-- Changelog related tags -->
  <xsl:template match="changelog">
    <table border="0" cellpadding="2" cellspacing="2">
      <xsl:apply-templates/>
    </table>
  </xsl:template>

  <xsl:template match="changelog/add">
    <tr>
      <xsl:variable name="src"><xsl:value-of select="$relative-path"/>/images/add.gif</xsl:variable>
      <td><img alt="add" class="icon" src="{$src}"/></td>
      <td><xsl:apply-templates/></td>
    </tr>
  </xsl:template>

  <xsl:template match="changelog/update">
    <tr>
      <xsl:variable name="src"><xsl:value-of select="$relative-path"/>/images/update.gif</xsl:variable>
      <td><img alt="update" class="icon" src="{$src}"/></td>
      <td><xsl:apply-templates/></td>
    </tr>
  </xsl:template>

  <xsl:template match="changelog/design">
    <tr>
      <xsl:variable name="src"><xsl:value-of select="$relative-path"/>/images/design.gif</xsl:variable>
      <td><img alt="design" class="icon" src="{$src}"/></td>
      <td><xsl:apply-templates/></td>
    </tr>
  </xsl:template>

  <xsl:template match="changelog/docs">
    <tr>
      <xsl:variable name="src"><xsl:value-of select="$relative-path"/>/images/docs.gif</xsl:variable>
      <td><img alt="docs" class="icon" src="{$src}"/></td>
      <td><xsl:apply-templates/></td>
    </tr>
  </xsl:template>

  <xsl:template match="changelog/fix">
    <tr>
      <xsl:variable name="src"><xsl:value-of select="$relative-path"/>/images/fix.gif</xsl:variable>
      <td><img alt="fix" class="icon" src="{$src}"/></td>
      <td><xsl:apply-templates/></td>
    </tr>
  </xsl:template>

  <xsl:template match="changelog/scode">
    <tr>
      <xsl:variable name="src"><xsl:value-of select="$relative-path"/>/images/code.gif</xsl:variable>
      <td><img alt="code" class="icon" src="{$src}"/></td>
      <td><xsl:apply-templates/></td>
    </tr>
  </xsl:template>

  <!-- Process an attributes list with nested attribute elements -->
  <xsl:template match="status">
    <table border="1" cellpadding="5">
      <tr>
        <th width="15%" bgcolor="{$attributes-color}">
          <font color="#ffffff">Priority</font>
        </th>
        <th width="50%" bgcolor="{$attributes-color}">
          <font color="#ffffff">Action Item</font>
        </th>
        <th width="25%" bgcolor="{$attributes-color}">
          <font color="#ffffff">Volunteers</font>
        </th>
        <xsl:for-each select="item">
        <tr>
          <td align="left" valign="center">
            <xsl:value-of select="@priority"/>
          </td>
          <td align="left" valign="center">
            <xsl:apply-templates/>
          </td>
          <td align="left" valign="center">
            <xsl:value-of select="@owner"/>
          </td>
        </tr>
        </xsl:for-each>
      </tr>
    </table>
  </xsl:template>

  <!-- Link to a bug report -->
  <xsl:template match="bug">
      <xsl:variable name="link"><xsl:value-of select="$buglink"/><xsl:value-of select="text()"/></xsl:variable>
      <a href="{$link}"><xsl:apply-templates/></a>
  </xsl:template>

  <!-- Link to a SVN revision report -->
  <xsl:template match="rev">
      <xsl:variable name="link"><xsl:value-of select="$revlink"/><xsl:value-of select="text()"/></xsl:variable>
      <a href="{$link}">r<xsl:apply-templates/></a>
  </xsl:template>

  <!-- Link to online docs -->
  <xsl:template match="doc">
      <xsl:variable name="link"><xsl:value-of select="$doclink"/><xsl:value-of select="@path"/></xsl:variable>
      <a href="{$link}"><xsl:apply-templates/></a>
  </xsl:template>

  <!-- Link to security page -->
  <xsl:template match="security">
      <xsl:variable name="link"><xsl:value-of select="$sylink"/></xsl:variable>
      <a href="{$link}"><xsl:apply-templates/></a>
  </xsl:template>

  <!-- Link to download page -->
  <xsl:template match="download">
      <xsl:variable name="link"><xsl:value-of select="$dllink"/></xsl:variable>
      <a href="{$link}"><xsl:apply-templates/></a>
  </xsl:template>

  <!-- Version numbers -->
  <xsl:template match="version-major-minor">
    <xsl:value-of select="$majorminorversion"/>
  </xsl:template>
  <xsl:template match="version-major">
    <xsl:value-of select="$majorversion"/>
  </xsl:template>

  <!-- specially process td tags ala site.vsl -->
  <xsl:template match="table[@class='detail-table']/tr/td">
    <td bgcolor="{$table-td-bg}" valign="top" align="left">
        <xsl:if test="@colspan"><xsl:attribute name="colspan"><xsl:value-of select="@colspan"/></xsl:attribute></xsl:if>
        <xsl:if test="@rowspan"><xsl:attribute name="rowspan"><xsl:value-of select="@rowspan"/></xsl:attribute></xsl:if>
        <font color="#000000" size="-1" face="arial,helvetica,sanserif">
            <xsl:apply-templates/>
        </font>
    </td>
  </xsl:template>

  <!-- handle th ala site.vsl -->
  <xsl:template match="table[@class='detail-table']/tr/th">
    <td bgcolor="{$table-th-bg}" valign="top">
        <xsl:if test="@colspan"><xsl:attribute name="colspan"><xsl:value-of select="@colspan"/></xsl:attribute></xsl:if>
        <xsl:if test="@rowspan"><xsl:attribute name="rowspan"><xsl:value-of select="@rowspan"/></xsl:attribute></xsl:if>
        <font color="#000000" size="-1" face="arial,helvetica,sanserif">
            <xsl:apply-templates />
        </font>
    </td>
  </xsl:template>

  <!-- Process everything else by just passing it through -->
  <xsl:template match="*|@*">
    <xsl:copy>
      <xsl:apply-templates select="@*|*|text()"/>
    </xsl:copy>
  </xsl:template>

</xsl:stylesheet>
