<?xml version='1.0'?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fo="http://www.w3.org/1999/XSL/Format"
                version='1.0'>

<!-- ********************************************************************
     $Id: fo.xsl 9720 2013-01-31 18:24:47Z bobstayton $
     ********************************************************************

     This file is part of the XSL DocBook Stylesheet distribution.
     See ../README or http://docbook.sf.net/release/xsl/current/ for
     copyright and other information.

     ******************************************************************** -->

<!-- These variables set the margin-left or -right attribute value for FO output based on
     the writing-mode specified in the gentext file for the document's lang. -->

<xsl:param name="direction.align.start">
  <xsl:choose>
    <!-- FOP does not support writing-mode="rl-tb" -->
    <xsl:when test="$fop.extensions != 0">left</xsl:when>
    <xsl:when test="$fop1.extensions != 0">left</xsl:when>
    <xsl:when test="starts-with($writing.mode, 'lr')">left</xsl:when>
    <xsl:when test="starts-with($writing.mode, 'rl')">right</xsl:when>
    <xsl:when test="starts-with($writing.mode, 'tb')">top</xsl:when>
    <xsl:otherwise>left</xsl:otherwise>
  </xsl:choose>
</xsl:param>

<xsl:param name="direction.align.end">
  <xsl:choose>
    <xsl:when test="$fop.extensions != 0">right</xsl:when>
    <xsl:when test="$fop1.extensions != 0">right</xsl:when>
    <xsl:when test="starts-with($writing.mode, 'lr')">right</xsl:when>
    <xsl:when test="starts-with($writing.mode, 'rl')">left</xsl:when>
    <xsl:when test="starts-with($writing.mode, 'tb')">bottom</xsl:when>
    <xsl:otherwise>right</xsl:otherwise>
  </xsl:choose>
</xsl:param>

<xsl:param name="direction.mode">
  <xsl:choose>
    <xsl:when test="$fop.extensions != 0 and
                    starts-with($writing.mode, 'rl')">
      <xsl:message>WARNING: FOP does not support right-to-left writing-mode</xsl:message>
      <xsl:text>lr-tb</xsl:text>
    </xsl:when>
    <xsl:when test="$fop1.extensions != 0 and
                    starts-with($writing.mode, 'rl')">
      <xsl:message>WARNING: FOP does not support right-to-left writing-mode</xsl:message>
      <xsl:text>lr-tb</xsl:text>
    </xsl:when>
    <xsl:when test="starts-with($writing.mode, 'lr')">lr-tb</xsl:when>
    <xsl:when test="starts-with($writing.mode, 'rl')">rl-tb</xsl:when>
    <xsl:when test="starts-with($writing.mode, 'tb')">tb-rl</xsl:when>
    <xsl:otherwise>lr-tb</xsl:otherwise>
  </xsl:choose>
</xsl:param>


<xsl:template name="anchor">
  <xsl:param name="node" select="."/>
  <xsl:param name="conditional" select="1"/>
  <xsl:variable name="id">
    <xsl:call-template name="object.id">
      <xsl:with-param name="object" select="$node"/>
    </xsl:call-template>
  </xsl:variable>
  <xsl:if test="$conditional = 0 or $node/@id or $node/@xml:id">
    <xsl:attribute name="id"><xsl:value-of select="$id"/></xsl:attribute>
  </xsl:if>
</xsl:template>

<xsl:template name="dingbat">
  <xsl:param name="dingbat">bullet</xsl:param>
  <xsl:variable name="symbol">
    <xsl:choose>
      <xsl:when test="$dingbat='bullet'">o</xsl:when>
      <xsl:when test="$dingbat='copyright'">&#x00A9;</xsl:when>
      <xsl:when test="$dingbat='trademark'">&#x2122;</xsl:when>
      <xsl:when test="$dingbat='trade'">&#x2122;</xsl:when>
      <xsl:when test="$dingbat='registered'">&#x00AE;</xsl:when>
      <xsl:when test="$dingbat='service'">(SM)</xsl:when>
      <xsl:when test="$dingbat='ldquo'">"</xsl:when>
      <xsl:when test="$dingbat='rdquo'">"</xsl:when>
      <xsl:when test="$dingbat='lsquo'">'</xsl:when>
      <xsl:when test="$dingbat='rsquo'">'</xsl:when>
      <xsl:when test="$dingbat='em-dash'">&#x2014;</xsl:when>
      <xsl:when test="$dingbat='en-dash'">-</xsl:when>
      <xsl:otherwise>o</xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:choose>
    <xsl:when test="$dingbat.fontset = ''">
      <xsl:copy-of select="$symbol"/>
    </xsl:when>
    <xsl:otherwise>
      <fo:inline font-family="{$dingbat.fontset}">
        <xsl:copy-of select="$symbol"/>
      </fo:inline>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template name="href.target">
  <xsl:param name="context" select="."/>
  <xsl:param name="object" select="."/>
  <xsl:text>#</xsl:text>
  <xsl:call-template name="object.id">
    <xsl:with-param name="object" select="$object"/>
  </xsl:call-template>
</xsl:template>

</xsl:stylesheet>

