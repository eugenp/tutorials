<?xml version='1.0'?>
<xsl:stylesheet
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns:doc="http://nwalsh.com/xsl/documentation/1.0"
  xmlns:date="http://exslt.org/dates-and-times"
  xmlns:exsl="http://exslt.org/common"
  xmlns:xlink="http://www.w3.org/1999/xlink"
  exclude-result-prefixes="doc date exsl"
  extension-element-prefixes="date exsl"
  version='1.0'>

<!-- ********************************************************************
     $Id: pi.xsl 8782 2010-07-27 21:15:17Z mzjn $
     ********************************************************************

     This file is part of the XSL DocBook Stylesheet distribution.
     See ../README or http://docbook.sf.net/release/xsl/current/ for
     copyright and other information.

     ******************************************************************** -->

<doc:reference xmlns=""><info><title>Common Processing Instruction Reference</title>
    <releaseinfo role="meta">
      $Id: pi.xsl 8782 2010-07-27 21:15:17Z mzjn $
    </releaseinfo>
  </info>
  <partintro id="partintro">
    <title>Introduction</title>
    <para>This is generated reference documentation for all
      user-specifiable processing instructions (PIs) in the
      “common” part of the DocBook XSL stylesheets.
      <note>
        <para>You add these PIs at particular points in a document to
          cause specific “exceptions” to formatting/output behavior. To
          make global changes in formatting/output behavior across an
          entire document, it’s better to do it by setting an
          appropriate stylesheet parameter (if there is one).</para>
      </note>
    </para>
  </partintro>
</doc:reference>

<!-- ==================================================================== -->
<doc:pi name="dbchoice_choice" xmlns="">
  <refpurpose>Generates a localized choice separator</refpurpose>
  <refdescription id="select.choice.separator-desc">
    <para>Use the <tag class="xmlpi">dbchoice choice</tag> PI to
      generate an appropriate localized “choice” separator (for
      example, <literal>and</literal> or <literal>or</literal>)
      before the final item in an inline <tag>simplelist</tag></para>
    <warning>
      <para>This PI is a less-than-ideal hack; support for it may
        disappear in the future (particularly if and when a more
        appropriate means for marking up "choice" lists becomes
        available in DocBook).</para>
    </warning>
  </refdescription>
  <refsynopsisdiv>
    <synopsis><tag class="xmlpi">dbchoice choice="and"|"or"|<replaceable>string</replaceable>"</tag></synopsis>
  </refsynopsisdiv>
  <refparameter>
    <variablelist>
      <varlistentry><term>choice="and"</term>
        <listitem>
          <para>generates a localized <literal>and</literal> separator</para>
        </listitem>
      </varlistentry>
      <varlistentry><term>choice="or"</term>
        <listitem>
          <para>generates a localized <literal>or</literal> separator</para>
        </listitem>
      </varlistentry>
      <varlistentry><term>choice="<replaceable>string</replaceable>"</term>
        <listitem>
          <para>generates a literal <replaceable>string</replaceable> separator</para>
        </listitem>
      </varlistentry>
    </variablelist>
  </refparameter>
</doc:pi>
<xsl:template name="pi.dbchoice_choice">
  <xsl:param name="node" select="."/>
  <xsl:call-template name="pi-attribute">
    <xsl:with-param name="pis" select="$node/processing-instruction('dbchoice')"/>
    <xsl:with-param name="attribute">choice</xsl:with-param>
  </xsl:call-template>
</xsl:template>

<doc:pi name="dbtimestamp" xmlns="">
  <refpurpose>Inserts a date timestamp</refpurpose>
  <refdescription>
    <para>Use the <tag class="xmlpi">dbtimestamp</tag> PI at any point in a
      source document to cause a date timestamp (a formatted
      string representing the current date and time) to be
      inserted in output of the document.</para>
  </refdescription>
  <refsynopsisdiv>
    <synopsis><tag class="xmlpi">dbtimestamp format="<replaceable>formatstring</replaceable>" [padding="0"|"1"]</tag></synopsis>
  </refsynopsisdiv>
  <refparameter>
    <variablelist>
      <varlistentry><term>format="<replaceable>formatstring</replaceable>"</term>
        <listitem>
          <para>Specifies format in which the date and time are
            output</para>
        <note>
          <para>For details of the content of the format string,
            see <link role="tcg" xlink:href="Datetime.html"
              >Date and time</link>.</para>
        </note>
        </listitem>
      </varlistentry>
      <varlistentry><term>padding="0"|"1"</term>
        <listitem>
          <para>Specifies padding behavior; if non-zero, padding is is added</para>
        </listitem>
      </varlistentry>
    </variablelist>
  </refparameter>
</doc:pi>
<xsl:template name="pi.dbtimestamp">
  <xsl:variable name="format">
    <xsl:variable name="pi-format">
      <xsl:call-template name="pi-attribute">
        <xsl:with-param name="pis" select="."/>
        <xsl:with-param name="attribute">format</xsl:with-param>
      </xsl:call-template>
    </xsl:variable>
    <xsl:choose>
      <xsl:when test="$pi-format != ''">
        <xsl:value-of select="$pi-format"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:call-template name="gentext.template">
          <xsl:with-param name="context" select="'datetime'"/>
          <xsl:with-param name="name" select="'format'"/>
        </xsl:call-template>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable> 
  <xsl:variable name="padding">
    <xsl:variable name="pi-padding">
      <xsl:call-template name="pi-attribute">
        <xsl:with-param name="pis" select="."/>
        <xsl:with-param name="attribute">padding</xsl:with-param>
      </xsl:call-template>
    </xsl:variable>
    <xsl:choose>
      <xsl:when test="$pi-padding != ''">
        <xsl:value-of select="$pi-padding"/>
      </xsl:when>
      <xsl:otherwise>1</xsl:otherwise>
    </xsl:choose>
  </xsl:variable>
  <xsl:variable name="date">
    <xsl:choose>
      <xsl:when test="function-available('date:date-time')">
        <xsl:value-of select="date:date-time()"/>
      </xsl:when>
      <xsl:when test="function-available('date:dateTime')">
        <!-- Xalan quirk -->
        <xsl:value-of select="date:dateTime()"/>
      </xsl:when>
    </xsl:choose>
  </xsl:variable>
  <xsl:choose>
    <xsl:when test="function-available('date:date-time') or
      function-available('date:dateTime')">
      <xsl:call-template name="datetime.format">
        <xsl:with-param name="date" select="$date"/>
        <xsl:with-param name="format" select="$format"/>
        <xsl:with-param name="padding" select="$padding"/>
      </xsl:call-template>
    </xsl:when>
    <xsl:otherwise>
      <xsl:message>
        Timestamp processing requires XSLT processor with EXSLT date support.
      </xsl:message>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<doc:pi name="dbtex_delims" xmlns="">
  <refpurpose>Generates delimiters around embedded TeX equations
    in output</refpurpose>
  <refdescription>
    <para>Use the <tag class="xmlpi">dbtex delims</tag> PI as a
      child of a <tag>textobject</tag> containing embedded TeX
      markup, to cause that markup to be surrounded by
      <literal>$</literal> delimiter characters in output.</para>
      <warning>
       <para>This feature is useful for print/PDF output only if you
       use the obsolete and now unsupported PassiveTeX XSL-FO
       engine.</para>
      </warning>
  </refdescription>
  <refsynopsisdiv>
    <synopsis><tag class="xmlpi">dbtex delims="no"|"yes"</tag></synopsis>
  </refsynopsisdiv>
  <refparameter>
    <variablelist>
      <varlistentry><term>dbtex delims="no"|"yes"</term>
        <listitem>
          <para>Specifies whether delimiters are output</para>
        </listitem>
      </varlistentry>
    </variablelist>
  </refparameter>

  <refsee role="params">
    <para><parameter>tex.math.delims</parameter></para>
  </refsee>
 
</doc:pi>
<xsl:template name="pi.dbtex_delims">
  <xsl:param name="node" select="."/>
  <xsl:call-template name="pi-attribute">
    <xsl:with-param name="pis" select="$node/processing-instruction('dbtex')"/>
    <xsl:with-param name="attribute" select="'delims'"/>
  </xsl:call-template>
</xsl:template>

<!-- ==================================================================== -->

<xsl:template match="processing-instruction()" mode="titlepage.mode">
  <!-- * Als process PIs on title pages -->
  <xsl:apply-templates select="."/>
</xsl:template>

<xsl:template match="processing-instruction('dbtimestamp')">
  <xsl:call-template name="pi.dbtimestamp"/>
</xsl:template>

<xsl:template name="datetime.format">
  <xsl:param name="date"/>
  <xsl:param name="format"/>
  <xsl:param name="padding" select="1"/>
  <xsl:if test="$format != ''">
    <!-- replace any whitespace in the format string with a non-breaking space -->
    <xsl:variable name="format-nbsp"
      select="translate($format,
      '&#x20;&#x9;&#xd;&#xa;',
      '&#xa0;&#xa0;&#xa0;&#xa0;')"/>
    <xsl:variable name="tokenized-format-string">
      <xsl:call-template name="str.tokenize.keep.delimiters">
        <xsl:with-param name="string" select="$format-nbsp"/>
        <xsl:with-param name="delimiters" select="'&#xa0;,./-()[]:'"/>
      </xsl:call-template>
    </xsl:variable>
    <xsl:choose>
      <xsl:when test="$exsl.node.set.available != 0">
        <!-- We must preserve context node in order to get valid language -->
        <xsl:variable name="context" select="."/>
        <xsl:for-each select="exsl:node-set($tokenized-format-string)/node()">
          <xsl:variable name="token">
            <xsl:value-of select="."/>
          </xsl:variable>
          <!-- Restore context node -->
          <xsl:for-each select="$context">
            <xsl:choose>
              <xsl:when test="$token = 'a'">
                <xsl:call-template name="gentext.template">
                  <xsl:with-param name="context" select="'datetime-abbrev'"/>
                  <xsl:with-param name="name" select="date:day-abbreviation($date)"/>
                </xsl:call-template>
              </xsl:when>
              <xsl:when test="$token = 'A'">
                <xsl:call-template name="gentext.template">
                  <xsl:with-param name="context" select="'datetime-full'"/>
                  <xsl:with-param name="name" select="date:day-name($date)"/>
                </xsl:call-template>
              </xsl:when>
              <xsl:when test="$token = 'b'">
                <xsl:call-template name="gentext.template">
                  <xsl:with-param name="context" select="'datetime-abbrev'"/>
                  <xsl:with-param name="name" select="date:month-abbreviation($date)"/>
                </xsl:call-template>
              </xsl:when>
              <xsl:when test="$token = 'c'">
                <xsl:value-of select="date:date($date)"/>
                <xsl:text> </xsl:text>
                <xsl:value-of select="date:time($date)"/>
              </xsl:when>
              <xsl:when test="$token = 'B'">
                <xsl:call-template name="gentext.template">
                  <xsl:with-param name="context" select="'datetime-full'"/>
                  <xsl:with-param name="name" select="date:month-name($date)"/>
                </xsl:call-template>
              </xsl:when>
              <xsl:when test="$token = 'd'">
                <xsl:if test="$padding = 1 and
                  string-length(date:day-in-month($date)) = 1">0</xsl:if>
                <xsl:value-of select="date:day-in-month($date)"/>
              </xsl:when>
              <xsl:when test="$token = 'H'">
                <xsl:if test="$padding = 1 and string-length(date:hour-in-day($date)) = 1">0</xsl:if>
                <xsl:value-of select="date:hour-in-day($date)"/>
              </xsl:when>
              <xsl:when test="$token = 'j'">
                <xsl:value-of select="date:day-in-year($date)"/>
              </xsl:when>
              <xsl:when test="$token = 'm'">
                <xsl:if test="$padding = 1 and string-length(date:month-in-year($date)) = 1">0</xsl:if>
                <xsl:value-of select="date:month-in-year($date)"/>
              </xsl:when>
              <xsl:when test="$token = 'M'">
                <xsl:if test="string-length(date:minute-in-hour($date)) = 1">0</xsl:if>
                <xsl:value-of select="date:minute-in-hour($date)"/>
              </xsl:when>
              <xsl:when test="$token = 'S'">
                <xsl:if test="string-length(date:second-in-minute($date)) = 1">0</xsl:if>
                <xsl:value-of select="date:second-in-minute($date)"/>
              </xsl:when>
              <xsl:when test="$token = 'U'">
                <xsl:value-of select="date:week-in-year($date)"/>
              </xsl:when>
              <xsl:when test="$token = 'w'">
                <xsl:value-of select="date:day-in-week($date)"/>
              </xsl:when>
              <xsl:when test="$token = 'x'">
                <xsl:value-of select="date:date($date)"/>
              </xsl:when>
              <xsl:when test="$token = 'X'">
                <xsl:value-of select="date:time($date)"/>
              </xsl:when>
              <xsl:when test="$token = 'Y'">
                <xsl:value-of select="date:year($date)"/>
              </xsl:when>
              <xsl:otherwise>
                <xsl:value-of select="$token"/>
              </xsl:otherwise>
            </xsl:choose>
          </xsl:for-each>
        </xsl:for-each>
      </xsl:when>
      <xsl:otherwise>
        <xsl:message>
          Timestamp processing requires an XSLT processor with support
          for the EXSLT node-set() function.
        </xsl:message>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:if>
</xsl:template>

</xsl:stylesheet>
