<?xml version="1.0" encoding="ASCII"?>
<!--This file was created automatically by html2xhtml-->
<!--from the HTML stylesheets.-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:i="urn:cz-kosek:functions:index" xmlns:l="http://docbook.sourceforge.net/xmlns/l10n/1.0" xmlns:func="http://exslt.org/functions" xmlns:k="http://www.isogen.com/functions/com.isogen.saxoni18n.Saxoni18nService" xmlns:exslt="http://exslt.org/common" xmlns="http://www.w3.org/1999/xhtml" extension-element-prefixes="func exslt" exclude-result-prefixes="func exslt i l k" version="1.0">

<!-- ********************************************************************
     $Id: autoidx-kosek.xsl 8725 2010-07-15 08:08:04Z kosek $
     ********************************************************************

     This file is part of the XSL DocBook Stylesheet distribution.
     See ../README or http://docbook.sf.net/release/xsl/current/ for
     copyright and other information.

     ******************************************************************** -->

<!-- ==================================================================== -->
<!-- The "kosek" method contributed by Jirka Kosek. -->

<xsl:include href="../common/autoidx-kosek.xsl"/>

<xsl:template name="generate-kosek-index">
  <xsl:param name="scope" select="(ancestor::book|/)[last()]"/>

  <xsl:variable name="vendor" select="system-property('xsl:vendor')"/>
  <xsl:if test="contains($vendor, 'libxslt')">
    <xsl:message terminate="yes">
      <xsl:text>ERROR: the 'kosek' index method does not </xsl:text>
      <xsl:text>work with the xsltproc XSLT processor.</xsl:text>
    </xsl:message>
  </xsl:if>

  <xsl:if test="contains($vendor, 'Saxonica')">
    <xsl:message terminate="yes">
      <xsl:text>ERROR: the 'kosek' index method does not </xsl:text>
      <xsl:text>work with the Saxon 8 XSLT processor.</xsl:text>
    </xsl:message>
  </xsl:if>

  <xsl:if test="$exsl.node.set.available = 0">
    <xsl:message terminate="yes">
      <xsl:text>ERROR: the 'kosek' index method requires the </xsl:text>
      <xsl:text>exslt:node-set() function. Use a processor that </xsl:text>
      <xsl:text>has it, or use a different index method.</xsl:text>
    </xsl:message>
  </xsl:if>

  <xsl:if test="not(function-available('i:group-index'))">
    <xsl:message terminate="yes">
      <xsl:text>ERROR: the 'kosek' index method requires the
</xsl:text>
      <xsl:text>index extension functions be imported:
</xsl:text>
      <xsl:text>  xsl:import href="common/autoidx-kosek.xsl"</xsl:text>
    </xsl:message>
  </xsl:if>

  <xsl:variable name="role">
    <xsl:if test="$index.on.role != 0">
      <xsl:value-of select="@role"/>
    </xsl:if>
  </xsl:variable>

  <xsl:variable name="type">
    <xsl:if test="$index.on.type != 0">
      <xsl:value-of select="@type"/>
    </xsl:if>
  </xsl:variable>

  <xsl:variable name="terms" select="//indexterm[count(.|key('group-code', i:group-index(normalize-space(concat(primary/@sortas, &quot; &quot;, primary))))[count(ancestor::node()|$scope) = count(ancestor::node()) and ($role = @role or $type = @type or (string-length($role) = 0 and string-length($type) = 0))][1]) = 1 and not(@class = 'endofrange')]"/>

  <div class="index">
    <xsl:apply-templates select="$terms" mode="index-div-kosek">
      <xsl:with-param name="scope" select="$scope"/>
      <xsl:with-param name="role" select="$role"/>
      <xsl:with-param name="type" select="$type"/>
      <xsl:sort select="i:group-index(normalize-space(concat(primary/@sortas, &quot; &quot;, primary)))" data-type="number"/>
    </xsl:apply-templates>
  </div>
</xsl:template>

<xsl:template match="indexterm" mode="index-div-kosek">
  <xsl:param name="scope" select="."/>
  <xsl:param name="role" select="''"/>
  <xsl:param name="type" select="''"/>

  <xsl:variable name="key" select="i:group-index(normalize-space(concat(primary/@sortas, &quot; &quot;, primary)))"/>

  <xsl:variable name="lang">
    <xsl:call-template name="l10n.language"/>
  </xsl:variable>

  <xsl:if test="key('group-code', $key)[count(ancestor::node()|$scope) = count(ancestor::node()) and ($role = @role or $type = @type or (string-length($role) = 0 and string-length($type) = 0))][count(.|key('primary', normalize-space(concat(primary/@sortas, &quot; &quot;, primary)))[count(ancestor::node()|$scope) = count(ancestor::node()) and ($role = @role or $type = @type or (string-length($role) = 0 and string-length($type) = 0))][1]) = 1]">
    <div class="indexdiv">
      <h3>
        <xsl:value-of select="i:group-letter($key)"/>
      </h3>
      <dl>
        <xsl:apply-templates select="key('group-code', $key)[count(ancestor::node()|$scope) = count(ancestor::node()) and ($role = @role or $type = @type or (string-length($role) = 0 and string-length($type) = 0))][count(.|key('primary', normalize-space(concat(primary/@sortas, &quot; &quot;, primary)))[count(ancestor::node()|$scope) = count(ancestor::node()) and ($role = @role or $type = @type or (string-length($role) = 0 and string-length($type) = 0))][1])=1]" mode="index-primary">
          <xsl:sort select="normalize-space(concat(primary/@sortas, &quot; &quot;, primary))" lang="{$lang}"/>
          <xsl:with-param name="scope" select="$scope"/>
          <xsl:with-param name="role" select="$role"/>
          <xsl:with-param name="type" select="$type"/>
        </xsl:apply-templates>
      </dl>
    </div>
  </xsl:if>
</xsl:template>

</xsl:stylesheet>
