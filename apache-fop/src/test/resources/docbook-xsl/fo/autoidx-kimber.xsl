<?xml version="1.0"?>
<!DOCTYPE xsl:stylesheet [
<!ENTITY % common.entities SYSTEM "../common/entities.ent">
%common.entities;

<!-- Documents using the kimber index method must have a lang attribute -->
<!-- Only one of these should be present in the entity -->
<!ENTITY lang 'concat(/*/@lang, /*/@xml:lang)'>

]>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fo="http://www.w3.org/1999/XSL/Format"
                xmlns:k="java:com.isogen.saxoni18n.Saxoni18nService"
                exclude-result-prefixes="k"
                version="1.0">

<!-- ********************************************************************
     $Id: autoidx-kimber.xsl 8729 2010-07-15 16:43:56Z bobstayton $
     ********************************************************************

     This file is part of the DocBook XSL Stylesheet distribution.
     See ../README or http://docbook.sf.net/ for copyright
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
    <xsl:when test="contains(&lang;, '-')">
      <xsl:value-of select="substring-before(&lang;, '-')"/>
    </xsl:when>
    <xsl:when test="contains(&lang;, '_')">
      <xsl:value-of select="substring-before(&lang;, '_')"/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:value-of select="&lang;"/>
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
      <xsl:text>Innodata Isogen &#x0A;Java extensions for </xsl:text>
      <xsl:text>internationalized indexes. &#x0A;Install those </xsl:text>
      <xsl:text>extensions, or use a different index method.&#x0A;</xsl:text>
      <xsl:text>For more information, see:&#x0A;</xsl:text>
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

  <xsl:variable name="terms"
                select="//indexterm[count(.|key('k-group',
                   k:getIndexGroupKey(&lang;, &primary;))
                   [&scope;][1]) = 1
                   and not(@class = 'endofrange')]"/>

  <xsl:variable name="alphabetical"
                select="$terms[not(starts-with(
                k:getIndexGroupKey(&lang;, &primary;),
                '#NUMERIC'
                ))]"/>

  <xsl:variable name="others"
                select="$terms[starts-with(
                k:getIndexGroupKey(&lang;, &primary;),
                '#NUMERIC'
                )]"/>

  <fo:block>
    <xsl:if test="$others">
      <xsl:call-template name="indexdiv.title">
        <xsl:with-param name="titlecontent">
          <xsl:call-template name="gentext">
            <xsl:with-param name="key" select="'index symbols'"/>
          </xsl:call-template>
        </xsl:with-param>
      </xsl:call-template>

      <fo:block>
        <xsl:apply-templates select="$others"
                             mode="index-symbol-div">
          <xsl:with-param name="scope" select="$scope"/>
          <xsl:with-param name="role" select="$role"/>
          <xsl:with-param name="type" select="$type"/>
          <xsl:sort lang="{$sort.lang}"
              select="k:getIndexGroupSortKey(&lang;,
                      k:getIndexGroupKey(&lang;, &primary;))"/>
        </xsl:apply-templates>
      </fo:block>
    </xsl:if>

    <xsl:apply-templates select="$alphabetical"
                         mode="index-div-kimber">
      <xsl:with-param name="scope" select="$scope"/>
      <xsl:with-param name="role" select="$role"/>
      <xsl:with-param name="type" select="$type"/>
      <xsl:sort lang="{$sort.lang}"
             select="k:getIndexGroupSortKey(&lang;,
                     k:getIndexGroupKey(&lang;, &primary;))"/>
    </xsl:apply-templates>
  </fo:block>

</xsl:template>

<xsl:template match="indexterm" mode="index-div-kimber">
  <xsl:param name="scope" select="."/>
  <xsl:param name="role" select="''"/>
  <xsl:param name="type" select="''"/>

  <xsl:variable name="key"
          select="k:getIndexGroupKey(&lang;, &primary;)"/>

  <xsl:variable name="label"
          select="k:getIndexGroupLabel(&lang;, $key)"/>

  <xsl:if test="key('k-group', $key)[&scope;]
                [count(.|key('primary', &primary;)[&scope;][1]) = 1]">
    <fo:block>
      <xsl:call-template name="indexdiv.title">
        <xsl:with-param name="titlecontent">
          <xsl:value-of select="$label"/>
        </xsl:with-param>
      </xsl:call-template>
      <fo:block>
        <xsl:apply-templates select="key('k-group', $key)[&scope;]
                            [count(.|key('primary', &primary;)[&scope;]
                            [1])=1]"
                             mode="index-primary">
          <xsl:sort select="&primary;" lang="{$sort.lang}"/>
          <xsl:with-param name="scope" select="$scope"/>
          <xsl:with-param name="role" select="$role"/>
          <xsl:with-param name="type" select="$type"/>
        </xsl:apply-templates>
      </fo:block>
    </fo:block>
  </xsl:if>
</xsl:template>

</xsl:stylesheet>
