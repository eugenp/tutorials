<?xml version="1.0" encoding="ASCII"?>
<!--This file was created automatically by html2xhtml-->
<!--from the HTML stylesheets.-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:exsl="http://exslt.org/common" xmlns:cf="http://docbook.sourceforge.net/xmlns/chunkfast/1.0" xmlns="http://www.w3.org/1999/xhtml" version="1.0" exclude-result-prefixes="exsl cf">

<!-- ********************************************************************
     $Id: chunk-changebars.xsl 8399 2009-04-08 07:37:42Z bobstayton $
     ********************************************************************

     This file is part of the XSL DocBook Stylesheet distribution.
     See ../README or http://docbook.sf.net/release/xsl/current/ for
     copyright and other information.

     ******************************************************************** -->

<!-- ==================================================================== -->

<!-- This file is a variant of chunk.xsl, to be used for generating chunked 
     output with highlighting based on change markup. -->

<xsl:import href="changebars.xsl"/>
<xsl:import href="chunk-common.xsl"/>

<!-- This customization of "process-chunk-element" is needed in order to make change 
     highlighting be inherited by chunked children of an element with change markup. -->
<xsl:template name="process-chunk-element">
  <xsl:param name="content">
    <xsl:choose>

      <xsl:when test="ancestor-or-self::*[@revisionflag] and $show.revisionflag != 0">
	<xsl:variable name="revisionflag" select="ancestor-or-self::*[@revisionflag][1]/@revisionflag"/>
	<xsl:call-template name="block.or.inline.revision">
	  <xsl:with-param name="revisionflag" select="$revisionflag"/>
	</xsl:call-template>
      </xsl:when>

      <xsl:otherwise>
	<xsl:apply-imports/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:param>

  <xsl:choose>
    <xsl:when test="$chunk.fast != 0 and $exsl.node.set.available != 0">
      <xsl:variable name="chunks" select="exsl:node-set($chunk.hierarchy)//cf:div"/>
      <xsl:variable name="genid" select="generate-id()"/>

      <xsl:variable name="div" select="$chunks[@id=$genid or @xml:id=$genid]"/>

      <xsl:variable name="prevdiv" select="($div/preceding-sibling::cf:div|$div/preceding::cf:div|$div/parent::cf:div)[last()]"/>
      <xsl:variable name="prev" select="key('genid', ($prevdiv/@id|$prevdiv/@xml:id)[1])"/>

      <xsl:variable name="nextdiv" select="($div/following-sibling::cf:div|$div/following::cf:div|$div/cf:div)[1]"/>
      <xsl:variable name="next" select="key('genid', ($nextdiv/@id|$nextdiv/@xml:id)[1])"/>

      <xsl:choose>
        <xsl:when test="$onechunk != 0 and parent::*">
          <xsl:copy-of select="$content"/>
        </xsl:when>
        <xsl:otherwise>
          <xsl:call-template name="process-chunk">
            <xsl:with-param name="prev" select="$prev"/>
            <xsl:with-param name="next" select="$next"/>
            <xsl:with-param name="content" select="$content"/>
          </xsl:call-template>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:when>
    <xsl:otherwise>
      <xsl:choose>
        <xsl:when test="$onechunk != 0 and not(parent::*)">
          <xsl:call-template name="chunk-all-sections">
            <xsl:with-param name="content" select="$content"/>
          </xsl:call-template>
        </xsl:when>
        <xsl:when test="$onechunk != 0">
          <xsl:copy-of select="$content"/>
        </xsl:when>
        <xsl:when test="$chunk.first.sections = 0">
          <xsl:call-template name="chunk-first-section-with-parent">
            <xsl:with-param name="content" select="$content"/>
          </xsl:call-template>
        </xsl:when>
        <xsl:otherwise>
          <xsl:call-template name="chunk-all-sections">
            <xsl:with-param name="content" select="$content"/>
          </xsl:call-template>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:include href="chunk-code.xsl"/>

</xsl:stylesheet>
