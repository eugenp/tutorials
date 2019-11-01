<?xml version="1.0"?>
<!-- This file is generated from param.xweb -->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

<!-- ********************************************************************
     $Id: param.xweb 6633 2007-02-21 18:33:33Z xmldoc $
     ********************************************************************

     This file is part of the DocBook Slides Stylesheet distribution.
     See ../README or http://docbook.sf.net/release/xsl/current/ for
     copyright and other information.

     ******************************************************************** -->

<xsl:param name="foil.title.master">36</xsl:param>
<!-- Inconsistant use of point size? -->
    <xsl:param name="foil.title.size">
      <xsl:value-of select="$foil.title.master"/><xsl:text>pt</xsl:text>
    </xsl:param>
  
    <xsl:param name="generate.copyright">1</xsl:param>
  
    <xsl:param name="generate.foilgroup.numbered.toc">1</xsl:param>
  
    <xsl:param name="generate.foilgroup.toc">1</xsl:param>
  
    <xsl:param name="generate.handoutnotes">0</xsl:param>
  
    <xsl:param name="generate.page.number">compact</xsl:param>
  
    <xsl:param name="generate.pubdate">1</xsl:param>
  
    <xsl:param name="generate.speakernotes">0</xsl:param>
  
    <xsl:param name="generate.titlepage">1</xsl:param>
  
    <xsl:param name="mml.embedding.mode">external-graphic</xsl:param>
  
<xsl:param name="slide.font.family">Helvetica</xsl:param>
<xsl:param name="slide.title.font.family">Helvetica</xsl:param>
    <xsl:param name="svg.embedding.mode">instream-foreign-object</xsl:param>
  

    <xsl:attribute-set name="foil.header.properties">
      <xsl:attribute name="background-color">white</xsl:attribute>
      <xsl:attribute name="color">black</xsl:attribute>
      <xsl:attribute name="font-weight">bold</xsl:attribute>
      <xsl:attribute name="text-align">center</xsl:attribute>
      <xsl:attribute name="font-family">
        <xsl:value-of select="$slide.title.font.family"/>
      </xsl:attribute>
      <xsl:attribute name="space-after">12pt</xsl:attribute>
    </xsl:attribute-set>
  
    <xsl:attribute-set name="foil.master.properties">
      <xsl:attribute name="page-width">
        <xsl:value-of select="$page.width"/>
      </xsl:attribute>
      <xsl:attribute name="page-height">
        <xsl:value-of select="$page.height"/>
      </xsl:attribute>
      <xsl:attribute name="margin-top">
        <xsl:value-of select="$page.margin.top"/>
      </xsl:attribute>
      <xsl:attribute name="margin-bottom">
        <xsl:value-of select="$page.margin.bottom"/>
      </xsl:attribute>
      <xsl:attribute name="margin-left">
        <xsl:value-of select="$page.margin.inner"/>
      </xsl:attribute>
      <xsl:attribute name="margin-right">
        <xsl:value-of select="$page.margin.outer"/>
      </xsl:attribute>
    </xsl:attribute-set>
  
    <xsl:attribute-set name="foil.page-sequence.properties">
      <xsl:attribute name="hyphenate">
        <xsl:value-of select="$hyphenate"/>
      </xsl:attribute>
    </xsl:attribute-set>
  
    <xsl:attribute-set name="foil.properties">
      <xsl:attribute name="font-family">
        <xsl:value-of select="$slide.font.family"/>
      </xsl:attribute>
      <xsl:attribute name="margin-{$direction.align.start}">1in</xsl:attribute>
      <xsl:attribute name="margin-{$direction.align.end}">1in</xsl:attribute>
      <xsl:attribute name="font-size">
        <xsl:value-of select="$body.font.size"/>
      </xsl:attribute>
    </xsl:attribute-set>
  
    <xsl:attribute-set name="foil.region-after.properties">
      <xsl:attribute name="extent">
        <xsl:value-of select="$region.after.extent"/>
      </xsl:attribute>
      <xsl:attribute name="display-align">after</xsl:attribute>
    </xsl:attribute-set>
  
    <xsl:attribute-set name="foil.region-before.properties">
      <xsl:attribute name="extent">
        <xsl:value-of select="$region.before.extent"/>
      </xsl:attribute>
      <xsl:attribute name="display-align">
        <xsl:value-of select="'before'"/>
      </xsl:attribute>
    </xsl:attribute-set>
  
    <xsl:attribute-set name="foil.region-body.properties">
      <xsl:attribute name="margin-bottom">
        <xsl:value-of select="$body.margin.bottom"/>
      </xsl:attribute>
      <xsl:attribute name="margin-top">
        <xsl:value-of select="$body.margin.top"/>
      </xsl:attribute>
      <xsl:attribute name="column-count">
        <xsl:value-of select="$column.count.body"/>
      </xsl:attribute>
    </xsl:attribute-set>
  
    <xsl:attribute-set name="foil.subtitle.properties">
      <xsl:attribute name="font-family">
        <xsl:value-of select="$slide.title.font.family"/>
      </xsl:attribute>
      <xsl:attribute name="text-align">center</xsl:attribute>
      <xsl:attribute name="font-size">
        <xsl:value-of select="$foil.title.master * 0.8"/><xsl:text>pt</xsl:text>
      </xsl:attribute>
      <xsl:attribute name="space-after">12pt</xsl:attribute>
    </xsl:attribute-set>
  
    <xsl:attribute-set name="foil.title.properties">
      <xsl:attribute name="font-size">
        <xsl:value-of select="$foil.title.size"/>
      </xsl:attribute>
    </xsl:attribute-set>
  
<xsl:attribute-set name="handoutnotes.properties"/>
    <xsl:attribute-set name="slides.properties">
      <xsl:attribute name="font-family">
        <xsl:value-of select="$slide.font.family"/>
      </xsl:attribute>
    </xsl:attribute-set>
  
    <xsl:attribute-set name="slides.titlepage.master.properties">
      <xsl:attribute name="page-width">
        <xsl:value-of select="$page.width"/>
      </xsl:attribute>
      <xsl:attribute name="page-height">
        <xsl:value-of select="$page.height"/>
      </xsl:attribute>
      <xsl:attribute name="margin-top">
        <xsl:value-of select="$page.margin.top"/>
      </xsl:attribute>
      <xsl:attribute name="margin-bottom">
        <xsl:value-of select="$page.margin.bottom"/>
      </xsl:attribute>
      <xsl:attribute name="margin-left">
        <xsl:value-of select="$page.margin.inner"/>
      </xsl:attribute>
      <xsl:attribute name="margin-right">
        <xsl:value-of select="$page.margin.outer"/>
      </xsl:attribute>
    </xsl:attribute-set>
  
    <xsl:attribute-set name="slides.titlepage.region-body.properties">
      <xsl:attribute name="margin-bottom">0pt</xsl:attribute>
      <xsl:attribute name="margin-top">0pt</xsl:attribute>
      <xsl:attribute name="column-count">
        <xsl:value-of select="$column.count.body"/>
      </xsl:attribute>
    </xsl:attribute-set>
  
<xsl:attribute-set name="speakernotes.properties"/>

    <xsl:attribute-set name="slides.titlepage.corpauthor.properties">
      <xsl:attribute name="text-align">center</xsl:attribute>
      <xsl:attribute name="space-after">1em</xsl:attribute>
      <xsl:attribute name="font-size">20.736pt</xsl:attribute>
    </xsl:attribute-set>
  
    <xsl:attribute-set name="slides.titlepage.title.properties">
      <xsl:attribute name="text-align">center</xsl:attribute>
      <xsl:attribute name="space-after">1em</xsl:attribute>
      <xsl:attribute name="padding-top">1.5in</xsl:attribute>
      <xsl:attribute name="keep-with-next">always</xsl:attribute>
      <xsl:attribute name="font-size">
	<xsl:value-of select="$foil.title.size"/>
      </xsl:attribute>
      <xsl:attribute name="font-weight">bold</xsl:attribute>
      <xsl:attribute name="font-family">
	<xsl:value-of select="$slide.title.font.family"/>
      </xsl:attribute>
    </xsl:attribute-set>
  
    <xsl:attribute-set name="slides.titlepage.subtitle.properties">
      <xsl:attribute name="text-align">center</xsl:attribute>
      <xsl:attribute name="space-after">1em</xsl:attribute>
      <xsl:attribute name="font-family">
	<xsl:value-of select="$slide.title.font.family"/>
      </xsl:attribute>
    </xsl:attribute-set>
  
    <xsl:attribute-set name="foil.footer.properties"/>
  
    <xsl:attribute-set name="slides.titlepage.author.properties">
      <xsl:attribute name="text-align">center</xsl:attribute>
      <xsl:attribute name="space-after">1em</xsl:attribute>
      <xsl:attribute name="font-size">20.736pt</xsl:attribute>
    </xsl:attribute-set>
  
    <xsl:attribute-set name="slides.titlepage.pubdate.properties">
      <xsl:attribute name="text-align">center</xsl:attribute>
      <xsl:attribute name="space-after">1em</xsl:attribute>
      <xsl:attribute name="font-size">17.28pt</xsl:attribute>
    </xsl:attribute-set>
  
    <xsl:attribute-set name="slides.titlepage.authorgroup.properties"/>
  

</xsl:stylesheet>

