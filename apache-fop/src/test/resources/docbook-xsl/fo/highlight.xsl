<?xml version='1.0'?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
		xmlns:fo="http://www.w3.org/1999/XSL/Format"
		xmlns:xslthl="http://xslthl.sf.net"
                exclude-result-prefixes="xslthl"
                version='1.0'>

<!-- ********************************************************************
     $Id: highlight.xsl 8419 2009-04-29 20:37:52Z kosek $
     ********************************************************************

     This file is part of the XSL DocBook Stylesheet distribution.
     See ../README or http://docbook.sf.net/release/xsl/current/ for
     and other information.

     ******************************************************************** -->

<xsl:import href="../highlighting/common.xsl"/>

<xsl:template match='xslthl:keyword' mode="xslthl">
  <fo:inline font-weight="bold"><xsl:apply-templates mode="xslthl"/></fo:inline>
</xsl:template>

<xsl:template match='xslthl:string' mode="xslthl">
  <fo:inline font-weight="bold" font-style="italic"><xsl:apply-templates mode="xslthl"/></fo:inline>
</xsl:template>

<xsl:template match='xslthl:comment' mode="xslthl">
  <fo:inline font-style="italic"><xsl:apply-templates mode="xslthl"/></fo:inline>
</xsl:template>

<xsl:template match='xslthl:tag' mode="xslthl">
  <fo:inline font-weight="bold"><xsl:apply-templates mode="xslthl"/></fo:inline>
</xsl:template>

<xsl:template match='xslthl:attribute' mode="xslthl">
  <fo:inline font-weight="bold"><xsl:apply-templates mode="xslthl"/></fo:inline>
</xsl:template>

<xsl:template match='xslthl:value' mode="xslthl">
  <fo:inline font-weight="bold"><xsl:apply-templates mode="xslthl"/></fo:inline>
</xsl:template>

<!--
<xsl:template match='xslthl:html'>
  <span style='background:#AFF'><font color='blue'><xsl:apply-templates/></font></span>
</xsl:template>

<xsl:template match='xslthl:xslt'>
  <span style='background:#AAA'><font color='blue'><xsl:apply-templates/></font></span>
</xsl:template>

<xsl:template match='xslthl:section'>
  <span style='background:yellow'><xsl:apply-templates/></span>
</xsl:template>
-->

<xsl:template match='xslthl:number' mode="xslthl">
  <xsl:apply-templates mode="xslthl"/>
</xsl:template>

<xsl:template match='xslthl:annotation' mode="xslthl">
  <fo:inline color="gray"><xsl:apply-templates mode="xslthl"/></fo:inline>
</xsl:template>

<xsl:template match='xslthl:directive' mode="xslthl">
  <xsl:apply-templates mode="xslthl"/>
</xsl:template>

<!-- Not sure which element will be in final XSLTHL 2.0 -->
<xsl:template match='xslthl:doccomment|xslthl:doctype' mode="xslthl">
  <fo:inline font-weight="bold"><xsl:apply-templates mode="xslthl"/></fo:inline>
</xsl:template>


</xsl:stylesheet>

