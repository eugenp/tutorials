<?xml version='1.0'?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fo="http://www.w3.org/1999/XSL/Format"
                xmlns:mml="http://www.w3.org/1998/Math/MathML"
                exclude-result-prefixes="mml"
                version='1.0'>

<!-- ********************************************************************
     $Id: math.xsl 9647 2012-10-26 17:42:03Z bobstayton $
     ********************************************************************

     This file is part of the XSL DocBook Stylesheet distribution.
     See ../README or http://docbook.sf.net/release/xsl/current/ for
     copyright and other information.

     ******************************************************************** -->

<xsl:template match="inlineequation">
  <xsl:apply-templates/>
</xsl:template>

<xsl:template match="alt">
</xsl:template>

<xsl:template match="mathphrase">
  <fo:inline>
    <xsl:apply-templates/>
  </fo:inline>
</xsl:template>

<!-- "Support" for MathML -->

<xsl:template match="mml:math" xmlns:mml="http://www.w3.org/1998/Math/MathML">
  <fo:instream-foreign-object>
    <xsl:copy>
      <xsl:copy-of select="@*"/>
      <xsl:apply-templates/>
    </xsl:copy>
  </fo:instream-foreign-object>
</xsl:template>

<xsl:template match="mml:*" xmlns:mml="http://www.w3.org/1998/Math/MathML">
  <xsl:copy>
    <xsl:copy-of select="@*"/>
    <xsl:apply-templates/>
  </xsl:copy>
</xsl:template>

<xsl:template match="equation/graphic | informalequation/graphic">
  <xsl:if test="$tex.math.in.alt = ''">
    <fo:block>
      <xsl:call-template name="process.image"/>
    </fo:block>
  </xsl:if>
</xsl:template>

<xsl:template match="inlineequation/alt[@role='tex'] | 
                     inlineequation/inlinemediaobject/textobject[@role='tex']" priority="1">
  <xsl:param name="output.delims" select="1"/>
</xsl:template>

<xsl:template match="equation/alt[@role='tex'] | informalequation/alt[@role='tex'] |
                     equation/mediaobject/textobject[@role='tex'] |
                     informalequation/mediaobject/textobject[@role='tex']" priority="1">
  <xsl:variable name="output.delims">
    <xsl:call-template name="tex.math.output.delims"/>
  </xsl:variable>
</xsl:template>

<xsl:template name="tex.math.output.delims">
  <xsl:variable name="pi.delims">
    <xsl:call-template name="pi-attribute">
      <xsl:with-param name="pis" select=".//processing-instruction('dbtex')"/>
      <xsl:with-param name="attribute" select="'delims'"/>
    </xsl:call-template>
  </xsl:variable>
  <xsl:variable name="result">
    <xsl:choose>
      <xsl:when test="$pi.delims = 'no'">0</xsl:when>
      <xsl:when test="$pi.delims = '' and $tex.math.delims = 0">0</xsl:when>
      <xsl:otherwise>1</xsl:otherwise>
    </xsl:choose>
  </xsl:variable>
  <xsl:value-of select="$result"/>
</xsl:template>

</xsl:stylesheet>
