<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                version="1.0">

<xsl:output method="xml" indent="no"
            doctype-public="-//Norman Walsh//DTD Website Autolayout V2.5.0//EN"
            doctype-system="http://docbook.sourceforge.net/release/website/2.5.0/schema/dtd/autolayout.dtd"
/>

<xsl:strip-space elements="toc tocentry layout copyright"/>

<xsl:template match="layout">
  <autolayout>
    <xsl:text>&#10;</xsl:text>
    <xsl:apply-templates/>
  </autolayout>
  <xsl:text>&#10;</xsl:text>
</xsl:template>

<xsl:template match="style|script|headlink|copyright|config">
  <xsl:apply-templates select="." mode="copy"/>
</xsl:template>

<xsl:template match="*" mode="copy">
  <xsl:element name="{name(.)}">
    <xsl:copy-of select="@*"/>
    <xsl:apply-templates mode="copy"/>
  </xsl:element>
</xsl:template>

<xsl:template match="toc">
  <xsl:if test="not(@page)">
    <xsl:message terminate="yes">
      <xsl:text>All toc entries must have a page attribute.</xsl:text>
    </xsl:message>
  </xsl:if>

  <xsl:variable name="page" select="document(@page,.)"/>

  <toc>
    <xsl:call-template name="tocentry"/>
  </toc>
  <xsl:text>&#10;</xsl:text>
</xsl:template>

<xsl:template match="tocentry">
  <tocentry>
    <xsl:call-template name="tocentry"/>
  </tocentry>
  <xsl:text>&#10;</xsl:text>
</xsl:template>

<xsl:template match="notoc">
  <notoc>
    <xsl:call-template name="tocentry"/>
  </notoc>
  <xsl:text>&#10;</xsl:text>
</xsl:template>

<xsl:template name="tocentry">
  <xsl:if test="@revisionflag">
    <xsl:attribute name="revisionflag">
      <xsl:value-of select="@revisionflag"/>
    </xsl:attribute>
  </xsl:if>
  <xsl:choose>
    <xsl:when test="@href">
      <xsl:call-template name="tocentry.href"/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:call-template name="tocentry.page"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template name="tocentry.href">
  <xsl:if test="not(@href)">
    <xsl:message terminate="yes">
      <xsl:text>All toc entries must have an href attribute.</xsl:text>
    </xsl:message>
  </xsl:if>

  <xsl:if test="not(@id)">
    <xsl:message terminate="yes">
      <xsl:text>All href toc entries must have an id attribute.</xsl:text>
    </xsl:message>
  </xsl:if>

  <xsl:message>
    <xsl:text>off site: </xsl:text>
    <xsl:value-of select="@href"/>
  </xsl:message>

  <xsl:attribute name="id">
    <xsl:value-of select="@id"/>
  </xsl:attribute>
  <xsl:attribute name="href">
    <xsl:value-of select="@href"/>
  </xsl:attribute>
  <xsl:if test="@tocskip = '1'">
    <xsl:attribute name="tocskip">
      <xsl:value-of select="@tocskip"/>
    </xsl:attribute>
  </xsl:if>

  <xsl:if test="not(title)">
    <xsl:message terminate="yes">
      <xsl:text>Off-site links must provide a title.</xsl:text>
    </xsl:message>
  </xsl:if>

  <xsl:text>&#10;</xsl:text>
  <xsl:apply-templates select="title|titleabbrev|summary" mode="copy"/>
  <xsl:text>&#10;</xsl:text>
  <xsl:apply-templates select="tocentry"/>
</xsl:template>

<xsl:template name="tocentry.page">
  <xsl:if test="not(@page)">
    <xsl:message terminate="yes">
      <xsl:text>All toc entries must have a page attribute.</xsl:text>
    </xsl:message>
  </xsl:if>

  <xsl:variable name="page" select="document(@page,.)"/>

  <xsl:if test="not($page/*[1]/@id)">
    <xsl:message terminate="yes">
      <xsl:value-of select="@page"/>
      <xsl:text>: missing ID.</xsl:text>
    </xsl:message>
  </xsl:if>

  <xsl:variable name="id" select="$page/*[1]/@id"/>

  <xsl:variable name="filename">
    <xsl:choose>
      <xsl:when test="@filename">
        <xsl:value-of select="@filename"/>
      </xsl:when>
      <xsl:when test="/layout/config[@param='default-filename']">
        <xsl:value-of select="(/layout/config[@param='default-filename'])[1]/@value"/>
      </xsl:when>
      <xsl:otherwise>index.html</xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:variable name="dir">
    <xsl:apply-templates select="." mode="calculate-dir"/>
  </xsl:variable>

  <xsl:if test="$filename = ''">
    <xsl:message terminate="yes">
      <xsl:value-of select="@page"/>
      <xsl:text>: missing filename.</xsl:text>
    </xsl:message>
  </xsl:if>

  <xsl:message>
    <xsl:value-of select="@page"/>
    <xsl:text>: </xsl:text>
    <xsl:if test="$dir != ''">
      <xsl:value-of select="$dir"/>
    </xsl:if>
    <xsl:value-of select="$filename"/>
  </xsl:message>

  <xsl:attribute name="page">
    <xsl:value-of select="@page"/>
  </xsl:attribute>
  <xsl:attribute name="id">
    <xsl:value-of select="$id"/>
  </xsl:attribute>
  <xsl:if test="$dir != ''">
    <xsl:attribute name="dir">
      <xsl:value-of select="$dir"/>
    </xsl:attribute>
  </xsl:if>
  <xsl:attribute name="filename">
    <xsl:value-of select="$filename"/>
  </xsl:attribute>
  <xsl:if test="@tocskip = '1'">
    <xsl:attribute name="tocskip">
      <xsl:value-of select="@tocskip"/>
    </xsl:attribute>
  </xsl:if>

  <xsl:text>&#10;</xsl:text>
  <xsl:choose>
    <xsl:when test="title">
      <xsl:apply-templates select="title" mode="copy"/>
    </xsl:when>
    <xsl:otherwise>
      <title>
        <xsl:apply-templates select="$page/*[1]/head/title"/>
      </title>
    </xsl:otherwise>
  </xsl:choose>
  <xsl:text>&#10;</xsl:text>

  <xsl:if test="titleabbrev or $page/*[1]/head/titleabbrev">
    <xsl:choose>
      <xsl:when test="titleabbrev">
        <xsl:apply-templates select="titleabbrev" mode="copy"/>
      </xsl:when>
      <xsl:otherwise>
        <titleabbrev>
          <xsl:apply-templates select="$page/*[1]/head/titleabbrev"/>
        </titleabbrev>
      </xsl:otherwise>
    </xsl:choose>
    <xsl:text>&#10;</xsl:text>
  </xsl:if>

  <xsl:if test="summary or $page/*[1]/head/summary">
    <xsl:choose>
      <xsl:when test="summary">
        <xsl:apply-templates select="summary" mode="copy"/>
      </xsl:when>
      <xsl:otherwise>
        <summary>
          <xsl:apply-templates select="$page/*[1]/head/summary"/>
        </summary>
      </xsl:otherwise>
    </xsl:choose>
    <xsl:text>&#10;</xsl:text>
  </xsl:if>

  <xsl:apply-templates select="tocentry"/>
</xsl:template>

<xsl:template match="*" mode="calculate-dir">
  <xsl:choose>
    <xsl:when test="starts-with(@dir, '/')">
      <!-- if the directory on this begins with a "/", we're done... -->
      <xsl:value-of select="substring-after(@dir, '/')"/>
      <xsl:text>/</xsl:text>
    </xsl:when>

    <xsl:when test="parent::*">
      <!-- if there's a parent, try it -->
      <xsl:apply-templates select="parent::*" mode="calculate-dir"/>
      <xsl:if test="@dir">
        <xsl:value-of select="@dir"/>
        <xsl:text>/</xsl:text>
      </xsl:if>
    </xsl:when>

    <xsl:otherwise>
      <xsl:if test="@dir">
        <xsl:value-of select="@dir"/>
        <xsl:text>/</xsl:text>
      </xsl:if>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

</xsl:stylesheet>
