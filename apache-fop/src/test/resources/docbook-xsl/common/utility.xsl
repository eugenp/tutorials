<?xml version='1.0'?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:doc="http://nwalsh.com/xsl/documentation/1.0"
                xmlns:dyn="http://exslt.org/dynamic"
                xmlns:saxon="http://icl.com/saxon"
                xmlns:xlink="http://www.w3.org/1999/xlink"
                exclude-result-prefixes="doc dyn saxon"
                version='1.0'>

<!-- ********************************************************************
     $Id: utility.xsl 7101 2007-07-20 15:32:12Z xmldoc $
     ********************************************************************

     This file is part of the XSL DocBook Stylesheet distribution.
     See ../README or http://docbook.sf.net/release/xsl/current/ for
     copyright and other information.

     ******************************************************************** -->
<doc:reference xmlns="" xml:id="utility">
  <info>
    <title>Common » Utility Template Reference</title>
    <releaseinfo role="meta">
      $Id: utility.xsl 7101 2007-07-20 15:32:12Z xmldoc $
    </releaseinfo>
  </info>
  <!-- * yes, partintro is a valid child of a reference... -->
  <partintro xml:id="partintro">
    <title>Introduction</title>
    <para>This is technical reference documentation for the
      miscellaneous utility templates in the DocBook XSL
      Stylesheets.</para>
    <note>
      <para>These templates are defined in a separate file from the set
        of “common” templates because some of the common templates
        reference DocBook XSL stylesheet parameters, requiring the
        entire set of parameters to be imported/included in any
        stylesheet that imports/includes the common templates.</para>
      <para>The utility templates don’t import or include any DocBook
        XSL stylesheet parameters, so the utility templates can be used
        without importing the whole set of parameters.</para>
    </note>
    <para>This is not intended to be user documentation. It is
      provided for developers writing customization layers for the
      stylesheets.</para>
  </partintro>
</doc:reference>

<!-- ====================================================================== -->

<doc:template name="log.message" xmlns="">
  <refpurpose>Logs/emits formatted notes and warnings</refpurpose>

  <refdescription id="log.message-desc">
    <para>The <function>log.message</function> template is a utility
    template for logging/emitting formatted messages&#xa0;– that is,
    notes and warnings, along with a given log “level” and an
    identifier for the “source” that the message relates to.</para>
  </refdescription>

  <refparameter id="log.message-params">
    <variablelist>
      <varlistentry><term>level</term>
        <listitem>
          <para>Text to log/emit in the message-level field to
            indicate the message level
          (<literal>Note</literal> or
          <literal>Warning</literal>)</para>
        </listitem>
      </varlistentry>
      <varlistentry><term>source</term>
        <listitem>
          <para>Text to log/emit in the source field to identify the
            “source” to which the notification/warning relates.
            This can be any arbitrary string, but because the
            message lacks line and column numbers to identify the
            exact part of the source document to which it
            relates, the intention is that the value you pass
            into the <literal>source</literal> parameter should
            give the user some way to identify the portion of
            their source document on which to take potentially
            take action in response to the log message (for
            example, to edit, change, or add content).</para>
          <para>So the <literal>source</literal> value should be,
            for example, an ID, book/chapter/article title, title
            of some formal object, or even a string giving an
            XPath expression.</para>
        </listitem>
      </varlistentry>
      <varlistentry><term>context-desc</term>
        <listitem>
          <para>Text to log/emit in the context-description field to
            describe the context for the message.</para>
        </listitem>
      </varlistentry>
      <varlistentry><term>context-desc-field-length</term>
        <listitem>
          <para>Specifies length of the context-description field
            (in characters); default is 12</para>
          <para>If the text specified by the
            <literal>context-desc</literal> parameter is longer
            than the number of characters specified in
            <literal>context-desc-field-length</literal>, it is
            truncated to <literal>context-desc-field-length</literal>
            (12 characters by default).</para>
          <para>If the specified text is shorter than
            <literal>context-desc-field-length</literal>,
          it is right-padded out to
          <literal>context-desc-field-length</literal> (12 by
          default).</para>
        <para>If no value has been specified for the
          <literal>context-desc</literal> parameter, the field is
          left empty and the text of the log message begins with
          the value of the <literal>message</literal>
          parameter.</para>
        </listitem>
      </varlistentry>
      <varlistentry><term>message</term>
        <listitem>
          <para>Text to log/emit in the actual message field</para>
        </listitem>
      </varlistentry>
      <varlistentry><term>message-field-length</term>
        <listitem>
          <para>Specifies length of the message
            field (in characters); default is 45</para>
        </listitem>
      </varlistentry>
    </variablelist>
  </refparameter>
  <refreturn id="log.message-returns">
  <para>Outputs a message (generally, to standard error).</para></refreturn>
</doc:template>
<xsl:template name="log.message">
  <xsl:param name="level"/>
  <xsl:param name="source"/>
  <xsl:param name="context-desc"/>
  <xsl:param name="context-desc-field-length">12</xsl:param>
  <xsl:param name="context-desc-padded">
    <xsl:if test="not($context-desc = '')">
      <xsl:call-template name="pad-string">
        <xsl:with-param name="leftRight">right</xsl:with-param>
        <xsl:with-param name="padVar"
          select="substring($context-desc, 1, $context-desc-field-length)"/>
        <xsl:with-param name="length" select="$context-desc-field-length"/>
      </xsl:call-template>
    </xsl:if>
  </xsl:param>
  <xsl:param name="message"/>
  <xsl:param name="message-field-length" select="45"/>
  <xsl:param name="message-padded">
    <xsl:variable name="spaces-for-blank-level">
      <!-- * if the level field is blank, we'll need to pad out -->
      <!-- * the message field with spaces to compensate -->
      <xsl:choose>
        <xsl:when test="$level = ''">
          <xsl:value-of select="4 + 2"/>
          <!-- * 4 = hard-coded length of comment text ("Note" or "Warn") -->
          <!-- * + 2 = length of colon-plus-space separator ": " -->
        </xsl:when>
        <xsl:otherwise>
          <xsl:value-of select="0"/>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:variable name="spaces-for-blank-context-desc">
      <!-- * if the context-description field is blank, we'll need -->
      <!-- * to pad out the message field with spaces to compensate -->
      <xsl:choose>
        <xsl:when test="$context-desc = ''">
          <xsl:value-of select="$context-desc-field-length + 2"/>
          <!-- * + 2 = length of colon-plus-space separator ": " -->
        </xsl:when>
        <xsl:otherwise>
          <xsl:value-of select="0"/>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:variable name="extra-spaces"
      select="$spaces-for-blank-level + $spaces-for-blank-context-desc"/>
    <xsl:call-template name="pad-string">
      <xsl:with-param name="leftRight">right</xsl:with-param>
      <xsl:with-param name="padVar"
        select="substring($message, 1, ($message-field-length + $extra-spaces))"/>
      <xsl:with-param name="length"
        select="$message-field-length + $extra-spaces"/>
    </xsl:call-template>
  </xsl:param>
  <!-- * emit the actual log message -->
  <xsl:message>
    <xsl:if test="not($level = '')">
      <xsl:value-of select="$level"/>
      <xsl:text>: </xsl:text>
    </xsl:if>
    <xsl:if test="not($context-desc = '')">
      <xsl:value-of select="$context-desc-padded"/>
      <xsl:text>: </xsl:text>
    </xsl:if>
    <xsl:value-of select="$message-padded"/>
    <xsl:text>  </xsl:text>
    <xsl:value-of select="$source"/>
  </xsl:message>
</xsl:template>

<!-- ===================================== -->
<doc:template name="get.doc.title" xmlns="">
  <refpurpose>Gets a title from the current document</refpurpose>
  <refdescription id="get.doc.title-desc">
    <para>The <function>get.doc.title</function> template is a
      utility template for returning the first title found in the
      current document.</para>
  </refdescription>
  <refreturn id="get.doc.title-returns">
  <para>Returns a string containing some identifying title for the
    current document .</para></refreturn>
</doc:template>
<xsl:template name="get.doc.title">
  <xsl:choose>
    <xsl:when test="//*[local-name() = 'title'
      or local-name() = 'refname']">
      <xsl:value-of select="//*[local-name() = 'title'
        or local-name() = 'refname'][1]"/>
    </xsl:when>
    <xsl:when test="substring(local-name(*[1]),
      string-length(local-name(*[1])-3) = 'info')
      and *[1]/*[local-name() = 'title']">
      <xsl:value-of select="*[1]/*[local-name() = 'title'][1]"/>
    </xsl:when>
  </xsl:choose>
</xsl:template>

<!-- ===================================== -->
<doc:template name="pad-string" xmlns="">
  <refpurpose>Right-pads or left-pads a string out to a certain length</refpurpose>
  <refdescription id="pad-string-desc">
    <para>This function takes string <parameter>padVar</parameter> and
      pads it out in the direction <parameter>rightLeft</parameter> to
      the string-length <parameter>length</parameter>, using string
      <parameter>padChar</parameter> (a space character by default) as
      the padding string (note that <parameter>padChar</parameter> can
      be a string; it is not limited to just being a single
      character).</para>
    <note>
      <para>This function began as a copy of Nate Austin's
        <function>prepend-pad</function> function in the <link
          xlink:href="http://www.dpawson.co.uk/xsl/sect2/padding.html" >Padding
          Content</link> section of Dave Pawson's <link
          xlink:href="http://www.dpawson.co.uk/xsl/index.html" >XSLT
          FAQ</link>.</para>
    </note>
  </refdescription>
  <refreturn id="pad-string-returns">
  <para>Returns a (padded) string.</para></refreturn>
</doc:template>
<xsl:template name="pad-string">
  <!-- * recursive template to right/left pad the value with -->
  <!-- * whatever padChar is passed in -->
  <xsl:param name="padChar" select="' '"/>
  <xsl:param name="leftRight">left</xsl:param>
  <xsl:param name="padVar"/>
  <xsl:param name="length"/>
  <xsl:choose>
    <xsl:when test="string-length($padVar) &lt; $length">
      <xsl:call-template name="pad-string">
        <xsl:with-param name="padChar" select="$padChar"/>
        <xsl:with-param name="leftRight" select="$leftRight"/>
        <xsl:with-param name="padVar">
          <xsl:choose>
            <!-- * determine whether string should be -->
            <!-- * right- or left-padded -->
            <xsl:when test="$leftRight = 'left'">
              <!-- * pad it to left -->
              <xsl:value-of select="concat($padChar,$padVar)"/>
            </xsl:when>
            <xsl:otherwise>
              <!-- * otherwise, right-pad the string -->
              <xsl:value-of select="concat($padVar,$padChar)"/>
            </xsl:otherwise>
          </xsl:choose>
        </xsl:with-param>
        <xsl:with-param name="length" select="$length"/>
      </xsl:call-template>
    </xsl:when>
    <xsl:otherwise>
      <xsl:value-of 
        select="substring($padVar,string-length($padVar) - $length + 1)"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

</xsl:stylesheet>
