<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                version="1.0">

<xsl:param name="filename-prefix" select="''"/>
<xsl:param name="depends-file" select="''"/>
<xsl:param name="output-root" select="''"/>
<!-- Additional dependencies for target website -->
<xsl:param name="add-website-depends" select="''"/>
<!-- Remove output root dir instead removing each html output file.
     Useful for removing subdirs and none html files: images, css etc. -->
<xsl:param name="remove-output-root" select="0"/>

<xsl:output method="text"/>

<xsl:template match="autolayout">
  <xsl:text>website: </xsl:text>
  <xsl:value-of select="$add-website-depends"/>
  <xsl:text> </xsl:text>
  <xsl:apply-templates select="toc" mode="all"/>
  <xsl:apply-templates select="notoc" mode="all"/>
  <xsl:text>&#10;&#10;</xsl:text>
  <xsl:apply-templates select="toc"/>
  <xsl:apply-templates select="notoc"/>
  <xsl:text>&#10;</xsl:text>
  <xsl:text>distclean: clean
&#9;-rm -f </xsl:text>
  <xsl:text>autolayout.xml website.database.xml </xsl:text>
  <xsl:text>&#32;</xsl:text>
  <xsl:value-of select="$depends-file"/>
  <xsl:text>&#10;&#10;</xsl:text>
  <xsl:text>clean:&#10;</xsl:text>
  <xsl:choose>
    <xsl:when test="$remove-output-root and not($output-root='')">
      <xsl:text>&#9;-rm -rf </xsl:text>
      <xsl:call-template name="output-root"/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:text>&#9;-rm -f </xsl:text>
      <xsl:apply-templates select="toc" mode="all"/>
      <xsl:apply-templates select="notoc" mode="all"/>
    </xsl:otherwise>
  </xsl:choose>
  <xsl:text>&#10;&#10;</xsl:text>
</xsl:template>

<xsl:template match="toc">
<!--
  <xsl:apply-templates select="." mode="calculate-dir"/>
-->
  <xsl:call-template name="output-root"/>
  <xsl:value-of select="@dir"/>
  <xsl:value-of select="$filename-prefix"/>
  <xsl:value-of select="@filename"/>
  <xsl:text>: </xsl:text>
  <xsl:value-of select="@page"/>
  <xsl:text>&#10;</xsl:text>
  <xsl:apply-templates select=".//tocentry"/>
</xsl:template>

<xsl:template match="tocentry|notoc">
<!--
  <xsl:apply-templates select="." mode="calculate-dir"/>
-->
  <xsl:if test="@filename">
    <xsl:call-template name="output-root"/>
    <xsl:value-of select="@dir"/>
    <xsl:value-of select="$filename-prefix"/>
    <xsl:value-of select="@filename"/>
    <xsl:text>: </xsl:text>
    <xsl:value-of select="@page"/>
    <xsl:text>&#10;</xsl:text>
  </xsl:if>
</xsl:template>

<xsl:template match="toc" mode="all">
  <xsl:apply-templates select=".//tocentry" mode="all"/>
<!--
  <xsl:apply-templates select="." mode="calculate-dir"/>
-->
  <xsl:call-template name="output-root"/>
  <xsl:value-of select="@dir"/>
  <xsl:value-of select="$filename-prefix"/>
  <xsl:value-of select="@filename"/>
  <xsl:text> </xsl:text>
</xsl:template>

<xsl:template match="tocentry|notoc" mode="all">
<!--
  <xsl:apply-templates select="." mode="calculate-dir"/>
-->
  <xsl:if test="@filename">
    <xsl:call-template name="output-root"/>
    <xsl:value-of select="@dir"/>
    <xsl:value-of select="$filename-prefix"/>
    <xsl:value-of select="@filename"/>
    <xsl:text> </xsl:text>
  </xsl:if>
</xsl:template>

<xsl:template match="*" mode="calculate-dir">
  <xsl:choose>
    <xsl:when test="starts-with(@dir, '/')">
      <!-- if the directory on this begins with a "/", we're done... -->
      <xsl:value-of select="substring-after(@dir, '/')"/>
<!--
      <xsl:if test="@dir != '/'">
        <xsl:text>/</xsl:text>
      </xsl:if>
-->
    </xsl:when>

    <xsl:when test="parent::*">
      <!-- if there's a parent, try it -->
      <xsl:apply-templates select="parent::*" mode="calculate-dir"/>
      <xsl:if test="@dir">
        <xsl:value-of select="@dir"/>
<!--
        <xsl:text>/</xsl:text>
-->
      </xsl:if>
    </xsl:when>

    <xsl:otherwise>
      <xsl:if test="@dir">
        <xsl:value-of select="@dir"/>
<!--
        <xsl:text>/</xsl:text>
-->
      </xsl:if>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template name="output-root">
  <xsl:if test="$output-root != ''">
    <xsl:value-of select="$output-root"/>
    <xsl:text>/</xsl:text>
  </xsl:if>
</xsl:template>


</xsl:stylesheet>
