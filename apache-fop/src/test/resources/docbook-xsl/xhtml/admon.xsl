<?xml version="1.0" encoding="ASCII"?>
<!--This file was created automatically by html2xhtml-->
<!--from the HTML stylesheets.-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns="http://www.w3.org/1999/xhtml" version="1.0">

<!-- ********************************************************************
     $Id: admon.xsl 9728 2013-03-08 00:16:41Z bobstayton $
     ********************************************************************

     This file is part of the XSL DocBook Stylesheet distribution.
     See ../README or http://docbook.sf.net/release/xsl/current/ for
     copyright and other information.

     ******************************************************************** -->

<xsl:template match="*" mode="admon.graphic.width">
  <xsl:param name="node" select="."/>
  <xsl:text>25</xsl:text>
</xsl:template>

<xsl:template match="note|important|warning|caution|tip">
  <xsl:choose>
    <xsl:when test="$admon.graphics != 0">
      <xsl:call-template name="graphical.admonition"/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:call-template name="nongraphical.admonition"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template name="admon.graphic">
  <xsl:param name="node" select="."/>
  <xsl:value-of select="$admon.graphics.path"/>
  <xsl:choose>
    <xsl:when test="local-name($node)='note'">note</xsl:when>
    <xsl:when test="local-name($node)='warning'">warning</xsl:when>
    <xsl:when test="local-name($node)='caution'">caution</xsl:when>
    <xsl:when test="local-name($node)='tip'">tip</xsl:when>
    <xsl:when test="local-name($node)='important'">important</xsl:when>
    <xsl:otherwise>note</xsl:otherwise>
  </xsl:choose>
  <xsl:value-of select="$admon.graphics.extension"/>
</xsl:template>

<xsl:template name="graphical.admonition">
  <xsl:variable name="admon.type">
    <xsl:choose>
      <xsl:when test="local-name(.)='note'">Note</xsl:when>
      <xsl:when test="local-name(.)='warning'">Warning</xsl:when>
      <xsl:when test="local-name(.)='caution'">Caution</xsl:when>
      <xsl:when test="local-name(.)='tip'">Tip</xsl:when>
      <xsl:when test="local-name(.)='important'">Important</xsl:when>
      <xsl:otherwise>Note</xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:variable name="alt">
    <xsl:call-template name="gentext">
      <xsl:with-param name="key" select="$admon.type"/>
    </xsl:call-template>
  </xsl:variable>

  <div>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:if test="$admon.style != '' and $make.clean.html = 0">
      <xsl:attribute name="style">
        <xsl:value-of select="$admon.style"/>
      </xsl:attribute>
    </xsl:if>

    <table border="{$table.border.off}">
      <!-- omit summary attribute in html5 output -->
      <xsl:if test="$div.element != 'section'">
        <xsl:attribute name="summary">
          <xsl:value-of select="$admon.type"/>
          <xsl:if test="title|info/title">
            <xsl:text>: </xsl:text>
            <xsl:value-of select="(title|info/title)[1]"/>
          </xsl:if>
        </xsl:attribute>
      </xsl:if>
      <tr>
        <td rowspan="2" align="center" valign="top">
          <xsl:attribute name="width">
            <xsl:apply-templates select="." mode="admon.graphic.width"/>
          </xsl:attribute>
          <img alt="[{$alt}]">
            <xsl:attribute name="src">
              <xsl:call-template name="admon.graphic"/>
            </xsl:attribute>
          </img>
        </td>
        <th align="{$direction.align.start}">
          <xsl:call-template name="anchor"/>
          <xsl:if test="$admon.textlabel != 0 or title or info/title">
            <xsl:apply-templates select="." mode="object.title.markup"/>
          </xsl:if>
        </th>
      </tr>
      <tr>
        <td align="{$direction.align.start}" valign="top">
          <xsl:apply-templates/>
        </td>
      </tr>
    </table>
  </div>
</xsl:template>

<xsl:template name="nongraphical.admonition">
  <div>
    <xsl:call-template name="common.html.attributes">
      <xsl:with-param name="inherit" select="1"/>
    </xsl:call-template>
    <xsl:call-template name="id.attribute"/>
    <xsl:if test="$admon.style != '' and $make.clean.html = 0">
      <xsl:attribute name="style">
        <xsl:value-of select="$admon.style"/>
      </xsl:attribute>
    </xsl:if>

    <xsl:if test="$admon.textlabel != 0 or title or info/title">
      <h3 class="title">
        <xsl:call-template name="anchor"/>
        <xsl:apply-templates select="." mode="object.title.markup"/>
      </h3>
    </xsl:if>

    <xsl:apply-templates/>
  </div>
</xsl:template>

<xsl:template match="note/title"/>
<xsl:template match="important/title"/>
<xsl:template match="warning/title"/>
<xsl:template match="caution/title"/>
<xsl:template match="tip/title"/>

</xsl:stylesheet>
