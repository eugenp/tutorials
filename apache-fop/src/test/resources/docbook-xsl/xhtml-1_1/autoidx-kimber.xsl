<?xml version="1.0" encoding="ASCII"?>
<!--This file was created automatically by html2xhtml-->
<!--from the HTML stylesheets.-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:k="http://www.isogen.com/functions/com.isogen.saxoni18n.Saxoni18nService" xmlns="http://www.w3.org/1999/xhtml" exclude-result-prefixes="k" version="1.0">

<!-- ********************************************************************
     $Id: autoidx-kimber.xsl 8729 2010-07-15 16:43:56Z bobstayton $
     ********************************************************************

     This file is part of the XSL DocBook Stylesheet distribution.
     See ../README or http://docbook.sf.net/release/xsl/current/ for
     copyright and other information.

     ******************************************************************** -->

<!-- ==================================================================== -->
<!-- The "kimber" method contributed by Eliot Kimber of Innodata Isogen.  -->
<!-- ==================================================================== -->
<!--   *** THIS MODULE ONLY WORKS WITH SAXON 6 OR SAXON 8 ***             -->
<!-- ==================================================================== -->


<xsl:include href="../common/autoidx-kimber.xsl"/>

<!-- Java sort apparently works only on lang part, not country -->
<xsl:param name="sort.lang">
  <xsl:choose>
    <xsl:when test="contains(concat(/*/@lang, /*/@xml:lang), '-')">
      <xsl:value-of select="substring-before(concat(/*/@lang, /*/@xml:lang), '-')"/>
    </xsl:when>
    <xsl:when test="contains(concat(/*/@lang, /*/@xml:lang), '_')">
      <xsl:value-of select="substring-before(concat(/*/@lang, /*/@xml:lang), '_')"/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:value-of select="concat(/*/@lang, /*/@xml:lang)"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:param>

<xsl:template name="generate-kimber-index">
  <xsl:param name="scope" select="NOTANODE"/>

  <xsl:variable name="vendor" select="system-property('xsl:vendor')"/>
  <xsl:if test="not(contains($vendor, 'SAXON '))">
    <xsl:message terminate="yes">
      <xsl:text>ERROR: the 'kimber' index method requires the </xsl:text>
      <xsl:text>Saxon version 6 or 8 XSLT processor.</xsl:text>
    </xsl:message>
  </xsl:if>

  <xsl:if test="not(function-available('k:getIndexGroupKey'))">
    <xsl:message terminate="yes">
      <xsl:text>ERROR: the 'kimber' index method requires the </xsl:text>
      <xsl:text>Innodata Isogen 
Java extensions for </xsl:text>
      <xsl:text>internationalized indexes. 
Install those </xsl:text>
      <xsl:text>extensions, or use a different index method.
</xsl:text>
      <xsl:text>For more information, see:
</xsl:text>
      <xsl:text>http://www.innodata-isogen.com/knowledge_center/tools_downloads/i18nsupport</xsl:text>
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

  <xsl:variable name="terms" select="//indexterm[count(.|key('k-group', k:getIndexGroupKey(concat(/*/@lang, /*/@xml:lang), normalize-space(concat(primary/@sortas, &quot; &quot;, primary))))[count(ancestor::node()|$scope) = count(ancestor::node()) and ($role = @role or $type = @type or (string-length($role) = 0 and string-length($type) = 0))][1]) = 1 and not(@class = 'endofrange')]"/>

  <xsl:variable name="alphabetical" select="$terms[not(starts-with(                 k:getIndexGroupKey(concat(/*/@lang, /*/@xml:lang), normalize-space(concat(primary/@sortas, &quot; &quot;, primary))),                 '#NUMERIC'                 ))]"/>

  <xsl:variable name="others" select="$terms[starts-with(                 k:getIndexGroupKey(concat(/*/@lang, /*/@xml:lang), normalize-space(concat(primary/@sortas, &quot; &quot;, primary))),                 '#NUMERIC'                 )]"/>

  <div class="index">
    <xsl:if test="$others">
      <div class="indexdev">
        <h3>
          <xsl:call-template name="gentext">
            <xsl:with-param name="key" select="'index symbols'"/>
          </xsl:call-template>
        </h3>
        <dl>
          <xsl:apply-templates select="$others" mode="index-symbol-div">
            <xsl:with-param name="scope" select="$scope"/>
            <xsl:with-param name="role" select="$role"/>
            <xsl:with-param name="type" select="$type"/>
            <xsl:sort lang="{$sort.lang}" select="k:getIndexGroupSortKey(concat(/*/@lang, /*/@xml:lang),                         k:getIndexGroupKey(concat(/*/@lang, /*/@xml:lang), normalize-space(concat(primary/@sortas, &quot; &quot;, primary))))"/>
          </xsl:apply-templates>
        </dl>
      </div>
    </xsl:if>

    <xsl:apply-templates select="$alphabetical" mode="index-div-kimber">
      <xsl:with-param name="scope" select="$scope"/>
      <xsl:with-param name="role" select="$role"/>
      <xsl:with-param name="type" select="$type"/>
      <xsl:sort lang="{$sort.lang}" select="k:getIndexGroupSortKey(concat(/*/@lang, /*/@xml:lang),                      k:getIndexGroupKey(concat(/*/@lang, /*/@xml:lang), normalize-space(concat(primary/@sortas, &quot; &quot;, primary))))"/>
    </xsl:apply-templates>
  </div>

</xsl:template>

<xsl:template match="indexterm" mode="index-div-kimber">
  <xsl:param name="scope" select="."/>
  <xsl:param name="role" select="''"/>
  <xsl:param name="type" select="''"/>

  <xsl:variable name="key" select="k:getIndexGroupKey(concat(/*/@lang, /*/@xml:lang), normalize-space(concat(primary/@sortas, &quot; &quot;, primary)))"/>

  <xsl:variable name="label" select="k:getIndexGroupLabel(concat(/*/@lang, /*/@xml:lang), $key)"/>

  <xsl:if test="key('k-group', $label)[count(ancestor::node()|$scope) = count(ancestor::node()) and ($role = @role or $type = @type or (string-length($role) = 0 and string-length($type) = 0))][count(.|key('primary', normalize-space(concat(primary/@sortas, &quot; &quot;, primary)))[count(ancestor::node()|$scope) = count(ancestor::node()) and ($role = @role or $type = @type or (string-length($role) = 0 and string-length($type) = 0))][1]) = 1]">
    <div class="indexdiv">
      <h3>
        <xsl:value-of select="$label"/>
      </h3>
      <dl>
        <xsl:apply-templates select="key('k-group', $key)[count(ancestor::node()|$scope) = count(ancestor::node()) and ($role = @role or $type = @type or (string-length($role) = 0 and string-length($type) = 0))]                             [count(.|key('primary', normalize-space(concat(primary/@sortas, &quot; &quot;, primary)))[count(ancestor::node()|$scope) = count(ancestor::node()) and ($role = @role or $type = @type or (string-length($role) = 0 and string-length($type) = 0))]                             [1])=1]" mode="index-primary">
          <xsl:sort select="normalize-space(concat(primary/@sortas, &quot; &quot;, primary))" lang="{$sort.lang}"/>
          <xsl:with-param name="scope" select="$scope"/>
          <xsl:with-param name="role" select="$role"/>
          <xsl:with-param name="type" select="$type"/>
        </xsl:apply-templates>
      </dl>
    </div>
  </xsl:if>
</xsl:template>

</xsl:stylesheet>
