<?xml version='1.0'?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                version='1.0'>

<!-- ********************************************************************
     $Id: xbel.xsl 6910 2007-06-28 23:23:30Z xmldoc $
     ********************************************************************

     This file is part of the XSL DocBook Stylesheet distribution.
     See ../README or http://docbook.sf.net/release/xsl/current/ for
     copyright and other information.

     ******************************************************************** -->

<!-- ==================================================================== -->

<xsl:output method="xml"/>

<!-- ==================================================================== -->

<xsl:template match="xbel">
  <ul>
    <xsl:apply-templates/>
  </ul>
</xsl:template>

<!-- Only partial support for xbel elements -->
<xsl:template match="xbel/info|xbel/title|xbel/desc|
                     xbel/alias|xbel/separator">
  <!-- No op -->
</xsl:template>

<xsl:template match="folder">
  <li>
    <xsl:apply-templates select="title"/>
    <ul>
      <xsl:apply-templates select="folder|bookmark"/>
    </ul>
  </li>
</xsl:template>

<xsl:template match="folder/title">
    <b><xsl:apply-templates/></b>
</xsl:template>

<xsl:template match="bookmark">
  <li>
    <a href="{@href}" target="_top">
      <xsl:apply-templates select="title"/>
    </a>
  </li>
</xsl:template>

<xsl:template match="bookmark/title">
    <xsl:apply-templates/>
</xsl:template>

<!-- ==================================================================== -->

<xsl:template match="xbel" mode="dynamic">
  <ul>
    <xsl:apply-templates mode="dynamic"/>
  </ul>
</xsl:template>

<xsl:template match="info" mode="dynamic">
</xsl:template>

<xsl:template match="folder" mode="dynamic">
  <li>
    <xsl:apply-templates select="title" mode="dynamic"/>
    <ul style="display:none" id="{@id}">
      <xsl:apply-templates select="folder|bookmark" mode="dynamic"/>
    </ul>
  </li>
</xsl:template>

<xsl:template match="folder/title" mode="dynamic">
  <b>
    <span>
      <xsl:choose>
	<xsl:when test="../@id">
	  <xsl:attribute name="onClick">
	    <xsl:text>toggleList('</xsl:text>
	    <xsl:value-of select="../@id"/>
	    <xsl:text>')</xsl:text>
	  </xsl:attribute>
	  <xsl:attribute name="class">exlist</xsl:attribute>
	  <xsl:attribute name="style">color: blue</xsl:attribute>
	  <xsl:apply-templates mode="dynamic"/>
	</xsl:when>
	<xsl:otherwise>
	  <xsl:apply-templates mode="dynamic"/>
	</xsl:otherwise>
      </xsl:choose>
    </span>
  </b>
</xsl:template>

<xsl:template match="bookmark" mode="dynamic">
  <li>
    <a href="{@href}" target="_top">
      <xsl:apply-templates select="title" mode="dynamic"/>
    </a>
  </li>
</xsl:template>

<xsl:template match="bookmark/title" mode="dynamic">
    <xsl:apply-templates mode="dynamic"/>
</xsl:template>

<!-- ==================================================================== -->

</xsl:stylesheet>
