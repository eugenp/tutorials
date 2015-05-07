<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fo="http://www.w3.org/1999/XSL/Format"
                version="1.0">

<!-- ********************************************************************
     $Id: pagesetup.xsl 9720 2013-01-31 18:24:47Z bobstayton $
     ********************************************************************

     This file is part of the DocBook XSL Stylesheet distribution.
     See ../README or http://docbook.sf.net/ for copyright
     copyright and other information.

     ******************************************************************** -->

<!-- ==================================================================== -->

<xsl:param name="body.fontset">
  <xsl:value-of select="$body.font.family"/>
  <xsl:if test="$body.font.family != ''
                and $symbol.font.family  != ''">,</xsl:if>
  <xsl:value-of select="$symbol.font.family"/>
</xsl:param>

<xsl:param name="title.fontset">
  <xsl:value-of select="$title.font.family"/>
  <xsl:if test="$title.font.family != ''
                and $symbol.font.family  != ''">,</xsl:if>
  <xsl:value-of select="$symbol.font.family"/>
</xsl:param>

<xsl:param name="dingbat.fontset">
  <xsl:value-of select="$dingbat.font.family"/>
  <xsl:if test="$dingbat.font.family != ''
                and $symbol.font.family  != ''">,</xsl:if>
  <xsl:value-of select="$symbol.font.family"/>
</xsl:param>

<!-- These are internal parameters are for the individual precedence attributes -->
<xsl:param name="region.start.precedence">
  <xsl:choose>
    <xsl:when test="$side.region.precedence = 'true'">true</xsl:when>
    <xsl:otherwise>false</xsl:otherwise>
  </xsl:choose>
</xsl:param>

<xsl:param name="region.end.precedence">
  <xsl:choose>
    <xsl:when test="$side.region.precedence = 'true'">true</xsl:when>
    <xsl:otherwise>false</xsl:otherwise>
  </xsl:choose>
</xsl:param>

<xsl:param name="region.before.precedence">
  <xsl:choose>
    <xsl:when test="$side.region.precedence = 'true'">false</xsl:when>
    <xsl:otherwise>true</xsl:otherwise>
  </xsl:choose>
</xsl:param>

<xsl:param name="region.after.precedence">
  <xsl:choose>
    <xsl:when test="$side.region.precedence = 'true'">false</xsl:when>
    <xsl:otherwise>true</xsl:otherwise>
  </xsl:choose>
</xsl:param>

<xsl:template name="setup.pagemasters">
  <fo:layout-master-set>
    <!-- blank pages -->
    <fo:simple-page-master master-name="blank"
                           page-width="{$page.width}"
                           page-height="{$page.height}"
                           margin-top="{$page.margin.top}"
                           margin-bottom="{$page.margin.bottom}">
      <xsl:attribute name="margin-{$direction.align.start}">
        <xsl:value-of select="$page.margin.outer"/>
	<xsl:if test="$fop.extensions != 0">
	  <xsl:value-of select="concat(' - (',$title.margin.left,')')"/>
        </xsl:if>
      </xsl:attribute>
      <xsl:attribute name="margin-{$direction.align.end}">
        <xsl:value-of select="$page.margin.inner"/>
      </xsl:attribute>
      <xsl:if test="$axf.extensions != 0">
        <xsl:call-template name="axf-page-master-properties">
          <xsl:with-param name="page.master">blank</xsl:with-param>
        </xsl:call-template>
      </xsl:if>
      <fo:region-body display-align="center"
                      margin-bottom="{$body.margin.bottom}"
                      margin-top="{$body.margin.top}">
        <xsl:attribute name="margin-{$direction.align.start}">
          <xsl:value-of select="$body.margin.outer"/>
        </xsl:attribute>
        <xsl:attribute name="margin-{$direction.align.end}">
          <xsl:value-of select="$body.margin.inner"/>
        </xsl:attribute>
        <xsl:if test="$fop.extensions = 0 and $fop1.extensions = 0">
          <xsl:attribute name="region-name">blank-body</xsl:attribute>
        </xsl:if>
      </fo:region-body>
      <fo:region-before region-name="xsl-region-before-blank"
                        extent="{$region.before.extent}"
                        precedence="{$region.before.precedence}"
                        display-align="before"/>
      <fo:region-after region-name="xsl-region-after-blank"
                       extent="{$region.after.extent}"
                       precedence="{$region.after.precedence}"
                       display-align="after"/>
      <xsl:call-template name="region.outer">
        <xsl:with-param name="sequence">blank</xsl:with-param>
        <xsl:with-param name="pageclass">blank</xsl:with-param>
      </xsl:call-template>
      <xsl:call-template name="region.inner">
        <xsl:with-param name="sequence">blank</xsl:with-param>
        <xsl:with-param name="pageclass">blank</xsl:with-param>
      </xsl:call-template>
    </fo:simple-page-master>

    <!-- title pages -->
    <fo:simple-page-master master-name="titlepage-first"
                           page-width="{$page.width}"
                           page-height="{$page.height}"
                           margin-top="{$page.margin.top}"
                           margin-bottom="{$page.margin.bottom}">
      <xsl:attribute name="margin-{$direction.align.start}">
        <xsl:value-of select="$page.margin.inner"/>
	<xsl:if test="$fop.extensions != 0">
	  <xsl:value-of select="concat(' - (',$title.margin.left,')')"/>
        </xsl:if>
      </xsl:attribute>
      <xsl:attribute name="margin-{$direction.align.end}">
        <xsl:value-of select="$page.margin.outer"/>
      </xsl:attribute>
      <xsl:if test="$axf.extensions != 0">
        <xsl:call-template name="axf-page-master-properties">
          <xsl:with-param name="page.master">titlepage-first</xsl:with-param>
        </xsl:call-template>
      </xsl:if>
      <fo:region-body margin-bottom="{$body.margin.bottom}"
                      margin-top="{$body.margin.top}"
                      column-gap="{$column.gap.titlepage}"
                      column-count="{$column.count.titlepage}">
        <xsl:attribute name="margin-{$direction.align.start}">
          <xsl:value-of select="$body.margin.inner"/>
        </xsl:attribute>
        <xsl:attribute name="margin-{$direction.align.end}">
          <xsl:value-of select="$body.margin.outer"/>
        </xsl:attribute>
      </fo:region-body>
      <fo:region-before region-name="xsl-region-before-first"
                        extent="{$region.before.extent}"
                        precedence="{$region.before.precedence}"
                        display-align="before"/>
      <fo:region-after region-name="xsl-region-after-first"
                       extent="{$region.after.extent}"
                        precedence="{$region.after.precedence}"
                        display-align="after"/>
      <xsl:call-template name="region.inner">
        <xsl:with-param name="sequence">first</xsl:with-param>
        <xsl:with-param name="pageclass">titlepage</xsl:with-param>
      </xsl:call-template>
      <xsl:call-template name="region.outer">
        <xsl:with-param name="sequence">first</xsl:with-param>
        <xsl:with-param name="pageclass">titlepage</xsl:with-param>
      </xsl:call-template>
    </fo:simple-page-master>

    <fo:simple-page-master master-name="titlepage-odd"
                           page-width="{$page.width}"
                           page-height="{$page.height}"
                           margin-top="{$page.margin.top}"
                           margin-bottom="{$page.margin.bottom}">
      <xsl:attribute name="margin-{$direction.align.start}">
        <xsl:value-of select="$page.margin.inner"/>
	<xsl:if test="$fop.extensions != 0">
	  <xsl:value-of select="concat(' - (',$title.margin.left,')')"/>
        </xsl:if>
      </xsl:attribute>
      <xsl:attribute name="margin-{$direction.align.end}">
        <xsl:value-of select="$page.margin.outer"/>
      </xsl:attribute>
      <xsl:if test="$axf.extensions != 0">
        <xsl:call-template name="axf-page-master-properties">
          <xsl:with-param name="page.master">titlepage-odd</xsl:with-param>
        </xsl:call-template>
      </xsl:if>
      <fo:region-body margin-bottom="{$body.margin.bottom}"
                      margin-top="{$body.margin.top}"
                      column-gap="{$column.gap.titlepage}"
                      column-count="{$column.count.titlepage}">
        <xsl:attribute name="margin-{$direction.align.start}">
          <xsl:value-of select="$body.margin.inner"/>
        </xsl:attribute>
        <xsl:attribute name="margin-{$direction.align.end}">
          <xsl:value-of select="$body.margin.outer"/>
        </xsl:attribute>
      </fo:region-body>
      <fo:region-before region-name="xsl-region-before-odd"
                        extent="{$region.before.extent}"
                        precedence="{$region.before.precedence}"
                        display-align="before"/>
      <fo:region-after region-name="xsl-region-after-odd"
                       extent="{$region.after.extent}"
                        precedence="{$region.after.precedence}"
                        display-align="after"/>
      <xsl:call-template name="region.inner">
        <xsl:with-param name="sequence">odd</xsl:with-param>
        <xsl:with-param name="pageclass">titlepage</xsl:with-param>
      </xsl:call-template>
      <xsl:call-template name="region.outer">
        <xsl:with-param name="sequence">odd</xsl:with-param>
        <xsl:with-param name="pageclass">titlepage</xsl:with-param>
      </xsl:call-template>
    </fo:simple-page-master>

    <fo:simple-page-master master-name="titlepage-even"
                           page-width="{$page.width}"
                           page-height="{$page.height}"
                           margin-top="{$page.margin.top}"
                           margin-bottom="{$page.margin.bottom}">
      <xsl:attribute name="margin-{$direction.align.start}">
        <xsl:value-of select="$page.margin.outer"/>
	<xsl:if test="$fop.extensions != 0">
	  <xsl:value-of select="concat(' - (',$title.margin.left,')')"/>
        </xsl:if>
      </xsl:attribute>
      <xsl:attribute name="margin-{$direction.align.end}">
        <xsl:value-of select="$page.margin.inner"/>
      </xsl:attribute>
      <xsl:if test="$axf.extensions != 0">
        <xsl:call-template name="axf-page-master-properties">
          <xsl:with-param name="page.master">titlepage-even</xsl:with-param>
        </xsl:call-template>
      </xsl:if>
      <fo:region-body margin-bottom="{$body.margin.bottom}"
                      margin-top="{$body.margin.top}"
                      column-gap="{$column.gap.titlepage}"
                      column-count="{$column.count.titlepage}">
        <xsl:attribute name="margin-{$direction.align.start}">
          <xsl:value-of select="$body.margin.outer"/>
        </xsl:attribute>
        <xsl:attribute name="margin-{$direction.align.end}">
          <xsl:value-of select="$body.margin.inner"/>
        </xsl:attribute>
      </fo:region-body>
      <fo:region-before region-name="xsl-region-before-even"
                        extent="{$region.before.extent}"
                        precedence="{$region.before.precedence}"
                        display-align="before"/>
      <fo:region-after region-name="xsl-region-after-even"
                       extent="{$region.after.extent}"
                        precedence="{$region.after.precedence}"
                        display-align="after"/>
      <xsl:call-template name="region.outer">
        <xsl:with-param name="sequence">even</xsl:with-param>
        <xsl:with-param name="pageclass">titlepage</xsl:with-param>
      </xsl:call-template>
      <xsl:call-template name="region.inner">
        <xsl:with-param name="sequence">even</xsl:with-param>
        <xsl:with-param name="pageclass">titlepage</xsl:with-param>
      </xsl:call-template>
    </fo:simple-page-master>

    <!-- list-of-title pages -->
    <fo:simple-page-master master-name="lot-first"
                           page-width="{$page.width}"
                           page-height="{$page.height}"
                           margin-top="{$page.margin.top}"
                           margin-bottom="{$page.margin.bottom}">
      <xsl:attribute name="margin-{$direction.align.start}">
        <xsl:value-of select="$page.margin.inner"/>
	<xsl:if test="$fop.extensions != 0">
	  <xsl:value-of select="concat(' - (',$title.margin.left,')')"/>
        </xsl:if>
      </xsl:attribute>
      <xsl:attribute name="margin-{$direction.align.end}">
        <xsl:value-of select="$page.margin.outer"/>
      </xsl:attribute>
      <xsl:if test="$axf.extensions != 0">
        <xsl:call-template name="axf-page-master-properties">
          <xsl:with-param name="page.master">lot-first</xsl:with-param>
        </xsl:call-template>
      </xsl:if>
      <fo:region-body margin-bottom="{$body.margin.bottom}"
                      margin-top="{$body.margin.top}"
                      column-gap="{$column.gap.lot}"
                      column-count="{$column.count.lot}">
        <xsl:attribute name="margin-{$direction.align.start}">
          <xsl:value-of select="$body.margin.inner"/>
        </xsl:attribute>
        <xsl:attribute name="margin-{$direction.align.end}">
          <xsl:value-of select="$body.margin.outer"/>
        </xsl:attribute>
      </fo:region-body>
      <fo:region-before region-name="xsl-region-before-first"
                        extent="{$region.before.extent}"
                        precedence="{$region.before.precedence}"
                        display-align="before"/>
      <fo:region-after region-name="xsl-region-after-first"
                       extent="{$region.after.extent}"
                        precedence="{$region.after.precedence}"
                       display-align="after"/>
      <xsl:call-template name="region.inner">
        <xsl:with-param name="sequence">first</xsl:with-param>
        <xsl:with-param name="pageclass">lot</xsl:with-param>
      </xsl:call-template>
      <xsl:call-template name="region.outer">
        <xsl:with-param name="sequence">first</xsl:with-param>
        <xsl:with-param name="pageclass">lot</xsl:with-param>
      </xsl:call-template>
    </fo:simple-page-master>

    <fo:simple-page-master master-name="lot-odd"
                           page-width="{$page.width}"
                           page-height="{$page.height}"
                           margin-top="{$page.margin.top}"
                           margin-bottom="{$page.margin.bottom}">
      <xsl:attribute name="margin-{$direction.align.start}">
        <xsl:value-of select="$page.margin.inner"/>
	<xsl:if test="$fop.extensions != 0">
	  <xsl:value-of select="concat(' - (',$title.margin.left,')')"/>
        </xsl:if>
      </xsl:attribute>
      <xsl:attribute name="margin-{$direction.align.end}">
        <xsl:value-of select="$page.margin.outer"/>
      </xsl:attribute>
      <xsl:if test="$axf.extensions != 0">
        <xsl:call-template name="axf-page-master-properties">
          <xsl:with-param name="page.master">lot-odd</xsl:with-param>
        </xsl:call-template>
      </xsl:if>
      <fo:region-body margin-bottom="{$body.margin.bottom}"
                      margin-top="{$body.margin.top}"
                      column-gap="{$column.gap.lot}"
                      column-count="{$column.count.lot}">
        <xsl:attribute name="margin-{$direction.align.start}">
          <xsl:value-of select="$body.margin.inner"/>
        </xsl:attribute>
        <xsl:attribute name="margin-{$direction.align.end}">
          <xsl:value-of select="$body.margin.outer"/>
        </xsl:attribute>
      </fo:region-body>
      <fo:region-before region-name="xsl-region-before-odd"
                        extent="{$region.before.extent}"
                        precedence="{$region.before.precedence}"
                        display-align="before"/>
      <fo:region-after region-name="xsl-region-after-odd"
                       extent="{$region.after.extent}"
                        precedence="{$region.after.precedence}"
                        display-align="after"/>
      <xsl:call-template name="region.inner">
        <xsl:with-param name="sequence">odd</xsl:with-param>
        <xsl:with-param name="pageclass">lot</xsl:with-param>
      </xsl:call-template>
      <xsl:call-template name="region.outer">
        <xsl:with-param name="sequence">odd</xsl:with-param>
        <xsl:with-param name="pageclass">lot</xsl:with-param>
      </xsl:call-template>
    </fo:simple-page-master>

    <fo:simple-page-master master-name="lot-even"
                           page-width="{$page.width}"
                           page-height="{$page.height}"
                           margin-top="{$page.margin.top}"
                           margin-bottom="{$page.margin.bottom}">
      <xsl:attribute name="margin-{$direction.align.start}">
        <xsl:value-of select="$page.margin.outer"/>
	<xsl:if test="$fop.extensions != 0">
	  <xsl:value-of select="concat(' - (',$title.margin.left,')')"/>
        </xsl:if>
      </xsl:attribute>
      <xsl:attribute name="margin-{$direction.align.end}">
        <xsl:value-of select="$page.margin.inner"/>
      </xsl:attribute>
      <xsl:if test="$axf.extensions != 0">
        <xsl:call-template name="axf-page-master-properties">
          <xsl:with-param name="page.master">lot-even</xsl:with-param>
        </xsl:call-template>
      </xsl:if>
      <fo:region-body margin-bottom="{$body.margin.bottom}"
                      margin-top="{$body.margin.top}"
                      column-gap="{$column.gap.lot}"
                      column-count="{$column.count.lot}">
        <xsl:attribute name="margin-{$direction.align.start}">
          <xsl:value-of select="$body.margin.outer"/>
        </xsl:attribute>
        <xsl:attribute name="margin-{$direction.align.end}">
          <xsl:value-of select="$body.margin.inner"/>
        </xsl:attribute>
      </fo:region-body>
      <fo:region-before region-name="xsl-region-before-even"
                        extent="{$region.before.extent}"
                        precedence="{$region.before.precedence}"
                        display-align="before"/>
      <fo:region-after region-name="xsl-region-after-even"
                       extent="{$region.after.extent}"
                        precedence="{$region.after.precedence}"
                        display-align="after"/>
      <xsl:call-template name="region.outer">
        <xsl:with-param name="sequence">even</xsl:with-param>
        <xsl:with-param name="pageclass">lot</xsl:with-param>
      </xsl:call-template>
      <xsl:call-template name="region.inner">
        <xsl:with-param name="sequence">even</xsl:with-param>
        <xsl:with-param name="pageclass">lot</xsl:with-param>
      </xsl:call-template>
    </fo:simple-page-master>

    <!-- frontmatter pages -->
    <fo:simple-page-master master-name="front-first"
                           page-width="{$page.width}"
                           page-height="{$page.height}"
                           margin-top="{$page.margin.top}"
                           margin-bottom="{$page.margin.bottom}">
      <xsl:attribute name="margin-{$direction.align.start}">
        <xsl:value-of select="$page.margin.inner"/>
	<xsl:if test="$fop.extensions != 0">
	  <xsl:value-of select="concat(' - (',$title.margin.left,')')"/>
        </xsl:if>
      </xsl:attribute>
      <xsl:attribute name="margin-{$direction.align.end}">
        <xsl:value-of select="$page.margin.outer"/>
      </xsl:attribute>
      <xsl:if test="$axf.extensions != 0">
        <xsl:call-template name="axf-page-master-properties">
          <xsl:with-param name="page.master">front-first</xsl:with-param>
        </xsl:call-template>
      </xsl:if>
      <fo:region-body margin-bottom="{$body.margin.bottom}"
                      margin-top="{$body.margin.top}"
                      column-gap="{$column.gap.front}"
                      column-count="{$column.count.front}">
        <xsl:attribute name="margin-{$direction.align.start}">
          <xsl:value-of select="$body.margin.inner"/>
        </xsl:attribute>
        <xsl:attribute name="margin-{$direction.align.end}">
          <xsl:value-of select="$body.margin.outer"/>
        </xsl:attribute>
      </fo:region-body>
      <fo:region-before region-name="xsl-region-before-first"
                        extent="{$region.before.extent}"
                        precedence="{$region.before.precedence}"
                        display-align="before"/>
      <fo:region-after region-name="xsl-region-after-first"
                       extent="{$region.after.extent}"
                        precedence="{$region.after.precedence}"
                        display-align="after"/>
      <xsl:call-template name="region.inner">
        <xsl:with-param name="sequence">first</xsl:with-param>
        <xsl:with-param name="pageclass">front</xsl:with-param>
      </xsl:call-template>
      <xsl:call-template name="region.outer">
        <xsl:with-param name="sequence">first</xsl:with-param>
        <xsl:with-param name="pageclass">front</xsl:with-param>
      </xsl:call-template>
    </fo:simple-page-master>

    <fo:simple-page-master master-name="front-odd"
                           page-width="{$page.width}"
                           page-height="{$page.height}"
                           margin-top="{$page.margin.top}"
                           margin-bottom="{$page.margin.bottom}">
      <xsl:attribute name="margin-{$direction.align.start}">
        <xsl:value-of select="$page.margin.inner"/>
	<xsl:if test="$fop.extensions != 0">
	  <xsl:value-of select="concat(' - (',$title.margin.left,')')"/>
        </xsl:if>
      </xsl:attribute>
      <xsl:attribute name="margin-{$direction.align.end}">
        <xsl:value-of select="$page.margin.outer"/>
      </xsl:attribute>
      <xsl:if test="$axf.extensions != 0">
        <xsl:call-template name="axf-page-master-properties">
          <xsl:with-param name="page.master">front-odd</xsl:with-param>
        </xsl:call-template>
      </xsl:if>
      <fo:region-body margin-bottom="{$body.margin.bottom}"
                      margin-top="{$body.margin.top}"
                      column-gap="{$column.gap.front}"
                      column-count="{$column.count.front}">
        <xsl:attribute name="margin-{$direction.align.start}">
          <xsl:value-of select="$body.margin.inner"/>
        </xsl:attribute>
        <xsl:attribute name="margin-{$direction.align.end}">
          <xsl:value-of select="$body.margin.outer"/>
        </xsl:attribute>
      </fo:region-body>
      <fo:region-before region-name="xsl-region-before-odd"
                        extent="{$region.before.extent}"
                        precedence="{$region.before.precedence}"
                        display-align="before"/>
      <fo:region-after region-name="xsl-region-after-odd"
                       extent="{$region.after.extent}"
                        precedence="{$region.after.precedence}"
                        display-align="after"/>
      <xsl:call-template name="region.inner">
        <xsl:with-param name="sequence">odd</xsl:with-param>
        <xsl:with-param name="pageclass">front</xsl:with-param>
      </xsl:call-template>
      <xsl:call-template name="region.outer">
        <xsl:with-param name="sequence">odd</xsl:with-param>
        <xsl:with-param name="pageclass">front</xsl:with-param>
      </xsl:call-template>
    </fo:simple-page-master>

    <fo:simple-page-master master-name="front-even"
                           page-width="{$page.width}"
                           page-height="{$page.height}"
                           margin-top="{$page.margin.top}"
                           margin-bottom="{$page.margin.bottom}">
      <xsl:attribute name="margin-{$direction.align.start}">
        <xsl:value-of select="$page.margin.outer"/>
	<xsl:if test="$fop.extensions != 0">
	  <xsl:value-of select="concat(' - (',$title.margin.left,')')"/>
        </xsl:if>
      </xsl:attribute>
      <xsl:attribute name="margin-{$direction.align.end}">
        <xsl:value-of select="$page.margin.inner"/>
      </xsl:attribute>
      <xsl:if test="$axf.extensions != 0">
        <xsl:call-template name="axf-page-master-properties">
          <xsl:with-param name="page.master">front-even</xsl:with-param>
        </xsl:call-template>
      </xsl:if>
      <fo:region-body margin-bottom="{$body.margin.bottom}"
                      margin-top="{$body.margin.top}"
                      column-gap="{$column.gap.front}"
                      column-count="{$column.count.front}">
        <xsl:attribute name="margin-{$direction.align.start}">
          <xsl:value-of select="$body.margin.outer"/>
        </xsl:attribute>
        <xsl:attribute name="margin-{$direction.align.end}">
          <xsl:value-of select="$body.margin.inner"/>
        </xsl:attribute>
      </fo:region-body>
      <fo:region-before region-name="xsl-region-before-even"
                        extent="{$region.before.extent}"
                        precedence="{$region.before.precedence}"
                        display-align="before"/>
      <fo:region-after region-name="xsl-region-after-even"
                       extent="{$region.after.extent}"
                        precedence="{$region.after.precedence}"
                        display-align="after"/>
      <xsl:call-template name="region.outer">
        <xsl:with-param name="sequence">even</xsl:with-param>
        <xsl:with-param name="pageclass">front</xsl:with-param>
      </xsl:call-template>
      <xsl:call-template name="region.inner">
        <xsl:with-param name="sequence">even</xsl:with-param>
        <xsl:with-param name="pageclass">front</xsl:with-param>
      </xsl:call-template>
    </fo:simple-page-master>

    <!-- body pages -->
    <fo:simple-page-master master-name="body-first"
                           page-width="{$page.width}"
                           page-height="{$page.height}"
                           margin-top="{$page.margin.top}"
                           margin-bottom="{$page.margin.bottom}">
      <xsl:attribute name="margin-{$direction.align.start}">
        <xsl:value-of select="$page.margin.inner"/>
	<xsl:if test="$fop.extensions != 0">
	  <xsl:value-of select="concat(' - (',$title.margin.left,')')"/>
        </xsl:if>
	<xsl:if test="$fop.extensions != 0">
	  <xsl:value-of select="concat(' - (',$title.margin.left,')')"/>
        </xsl:if>
      </xsl:attribute>
      <xsl:attribute name="margin-{$direction.align.end}">
        <xsl:value-of select="$page.margin.outer"/>
      </xsl:attribute>
      <xsl:if test="$axf.extensions != 0">
        <xsl:call-template name="axf-page-master-properties">
          <xsl:with-param name="page.master">body-first</xsl:with-param>
        </xsl:call-template>
      </xsl:if>
      <fo:region-body margin-bottom="{$body.margin.bottom}"
                      margin-top="{$body.margin.top}"
                      column-gap="{$column.gap.body}"
                      column-count="{$column.count.body}">
        <xsl:attribute name="margin-{$direction.align.start}">
          <xsl:value-of select="$body.margin.inner"/>
        </xsl:attribute>
        <xsl:attribute name="margin-{$direction.align.end}">
          <xsl:value-of select="$body.margin.outer"/>
        </xsl:attribute>
      </fo:region-body>
      <fo:region-before region-name="xsl-region-before-first"
                        extent="{$region.before.extent}"
                        precedence="{$region.before.precedence}"
                        display-align="before"/>
      <fo:region-after region-name="xsl-region-after-first"
                       extent="{$region.after.extent}"
                        precedence="{$region.after.precedence}"
                       display-align="after"/>
      <xsl:call-template name="region.inner">
        <xsl:with-param name="sequence">first</xsl:with-param>
        <xsl:with-param name="pageclass">body</xsl:with-param>
      </xsl:call-template>
      <xsl:call-template name="region.outer">
        <xsl:with-param name="sequence">first</xsl:with-param>
        <xsl:with-param name="pageclass">body</xsl:with-param>
      </xsl:call-template>
    </fo:simple-page-master>

    <fo:simple-page-master master-name="body-odd"
                           page-width="{$page.width}"
                           page-height="{$page.height}"
                           margin-top="{$page.margin.top}"
                           margin-bottom="{$page.margin.bottom}">
      <xsl:attribute name="margin-{$direction.align.start}">
        <xsl:value-of select="$page.margin.inner"/>
	<xsl:if test="$fop.extensions != 0">
	  <xsl:value-of select="concat(' - (',$title.margin.left,')')"/>
        </xsl:if>
      </xsl:attribute>
      <xsl:attribute name="margin-{$direction.align.end}">
        <xsl:value-of select="$page.margin.outer"/>
      </xsl:attribute>
      <xsl:if test="$axf.extensions != 0">
        <xsl:call-template name="axf-page-master-properties">
          <xsl:with-param name="page.master">body-odd</xsl:with-param>
        </xsl:call-template>
      </xsl:if>
      <fo:region-body margin-bottom="{$body.margin.bottom}"
                      margin-top="{$body.margin.top}"
                      column-gap="{$column.gap.body}"
                      column-count="{$column.count.body}">
        <xsl:attribute name="margin-{$direction.align.start}">
          <xsl:value-of select="$body.margin.inner"/>
        </xsl:attribute>
        <xsl:attribute name="margin-{$direction.align.end}">
          <xsl:value-of select="$body.margin.outer"/>
        </xsl:attribute>
      </fo:region-body>
      <fo:region-before region-name="xsl-region-before-odd"
                        extent="{$region.before.extent}"
                        precedence="{$region.before.precedence}"
                        display-align="before"/>
      <fo:region-after region-name="xsl-region-after-odd"
                       extent="{$region.after.extent}"
                       precedence="{$region.after.precedence}"
                       display-align="after"/>
      <xsl:call-template name="region.inner">
        <xsl:with-param name="pageclass">body</xsl:with-param>
        <xsl:with-param name="sequence">odd</xsl:with-param>
      </xsl:call-template>
      <xsl:call-template name="region.outer">
        <xsl:with-param name="pageclass">body</xsl:with-param>
        <xsl:with-param name="sequence">odd</xsl:with-param>
      </xsl:call-template>
    </fo:simple-page-master>

    <fo:simple-page-master master-name="body-even"
                           page-width="{$page.width}"
                           page-height="{$page.height}"
                           margin-top="{$page.margin.top}"
                           margin-bottom="{$page.margin.bottom}">
      <xsl:attribute name="margin-{$direction.align.start}">
        <xsl:value-of select="$page.margin.outer"/>
	<xsl:if test="$fop.extensions != 0">
	  <xsl:value-of select="concat(' - (',$title.margin.left,')')"/>
        </xsl:if>
      </xsl:attribute>
      <xsl:attribute name="margin-{$direction.align.end}">
        <xsl:value-of select="$page.margin.inner"/>
      </xsl:attribute>
      <xsl:if test="$axf.extensions != 0">
        <xsl:call-template name="axf-page-master-properties">
          <xsl:with-param name="page.master">body-even</xsl:with-param>
        </xsl:call-template>
      </xsl:if>
      <fo:region-body margin-bottom="{$body.margin.bottom}"
                      margin-top="{$body.margin.top}"
                      column-gap="{$column.gap.body}"
                      column-count="{$column.count.body}">
        <xsl:attribute name="margin-{$direction.align.start}">
          <xsl:value-of select="$body.margin.outer"/>
        </xsl:attribute>
        <xsl:attribute name="margin-{$direction.align.end}">
          <xsl:value-of select="$body.margin.inner"/>
        </xsl:attribute>
      </fo:region-body>
      <fo:region-before region-name="xsl-region-before-even"
                        extent="{$region.before.extent}"
                        precedence="{$region.before.precedence}"
                        display-align="before"/>
      <fo:region-after region-name="xsl-region-after-even"
                       extent="{$region.after.extent}"
                       precedence="{$region.after.precedence}"
                       display-align="after"/>
      <xsl:call-template name="region.outer">
        <xsl:with-param name="pageclass">body</xsl:with-param>
        <xsl:with-param name="sequence">even</xsl:with-param>
      </xsl:call-template>
      <xsl:call-template name="region.inner">
        <xsl:with-param name="pageclass">body</xsl:with-param>
        <xsl:with-param name="sequence">even</xsl:with-param>
      </xsl:call-template>
    </fo:simple-page-master>

    <!-- backmatter pages -->
    <fo:simple-page-master master-name="back-first"
                           page-width="{$page.width}"
                           page-height="{$page.height}"
                           margin-top="{$page.margin.top}"
                           margin-bottom="{$page.margin.bottom}">
      <xsl:attribute name="margin-{$direction.align.start}">
        <xsl:value-of select="$page.margin.inner"/>
	<xsl:if test="$fop.extensions != 0">
	  <xsl:value-of select="concat(' - (',$title.margin.left,')')"/>
        </xsl:if>
      </xsl:attribute>
      <xsl:attribute name="margin-{$direction.align.end}">
        <xsl:value-of select="$page.margin.outer"/>
      </xsl:attribute>
      <xsl:if test="$axf.extensions != 0">
        <xsl:call-template name="axf-page-master-properties">
          <xsl:with-param name="page.master">back-first</xsl:with-param>
        </xsl:call-template>
      </xsl:if>
      <fo:region-body margin-bottom="{$body.margin.bottom}"
                      margin-top="{$body.margin.top}"
                      column-gap="{$column.gap.back}"
                      column-count="{$column.count.back}">
        <xsl:attribute name="margin-{$direction.align.start}">
          <xsl:value-of select="$body.margin.inner"/>
        </xsl:attribute>
        <xsl:attribute name="margin-{$direction.align.end}">
          <xsl:value-of select="$body.margin.outer"/>
        </xsl:attribute>
      </fo:region-body>
      <fo:region-before region-name="xsl-region-before-first"
                        extent="{$region.before.extent}"
                        precedence="{$region.before.precedence}"
                        display-align="before"/>
      <fo:region-after region-name="xsl-region-after-first"
                       extent="{$region.after.extent}"
                        precedence="{$region.after.precedence}"
                       display-align="after"/>
      <xsl:call-template name="region.inner">
        <xsl:with-param name="sequence">first</xsl:with-param>
        <xsl:with-param name="pageclass">back</xsl:with-param>
      </xsl:call-template>
      <xsl:call-template name="region.outer">
        <xsl:with-param name="sequence">first</xsl:with-param>
        <xsl:with-param name="pageclass">back</xsl:with-param>
      </xsl:call-template>
    </fo:simple-page-master>

    <fo:simple-page-master master-name="back-odd"
                           page-width="{$page.width}"
                           page-height="{$page.height}"
                           margin-top="{$page.margin.top}"
                           margin-bottom="{$page.margin.bottom}">
      <xsl:attribute name="margin-{$direction.align.start}">
        <xsl:value-of select="$page.margin.inner"/>
	<xsl:if test="$fop.extensions != 0">
	  <xsl:value-of select="concat(' - (',$title.margin.left,')')"/>
        </xsl:if>
      </xsl:attribute>
      <xsl:attribute name="margin-{$direction.align.end}">
        <xsl:value-of select="$page.margin.outer"/>
      </xsl:attribute>
      <xsl:if test="$axf.extensions != 0">
        <xsl:call-template name="axf-page-master-properties">
          <xsl:with-param name="page.master">back-odd</xsl:with-param>
        </xsl:call-template>
      </xsl:if>
      <fo:region-body margin-bottom="{$body.margin.bottom}"
                      margin-top="{$body.margin.top}"
                      column-gap="{$column.gap.back}"
                      column-count="{$column.count.back}">
        <xsl:attribute name="margin-{$direction.align.start}">
          <xsl:value-of select="$body.margin.inner"/>
        </xsl:attribute>
        <xsl:attribute name="margin-{$direction.align.end}">
          <xsl:value-of select="$body.margin.outer"/>
        </xsl:attribute>
      </fo:region-body>
      <fo:region-before region-name="xsl-region-before-odd"
                        extent="{$region.before.extent}"
                        precedence="{$region.before.precedence}"
                        display-align="before"/>
      <fo:region-after region-name="xsl-region-after-odd"
                       extent="{$region.after.extent}"
                        precedence="{$region.after.precedence}"
                       display-align="after"/>
      <xsl:call-template name="region.inner">
        <xsl:with-param name="sequence">odd</xsl:with-param>
        <xsl:with-param name="pageclass">back</xsl:with-param>
      </xsl:call-template>
      <xsl:call-template name="region.outer">
        <xsl:with-param name="sequence">odd</xsl:with-param>
        <xsl:with-param name="pageclass">back</xsl:with-param>
      </xsl:call-template>
    </fo:simple-page-master>

    <fo:simple-page-master master-name="back-even"
                           page-width="{$page.width}"
                           page-height="{$page.height}"
                           margin-top="{$page.margin.top}"
                           margin-bottom="{$page.margin.bottom}">
      <xsl:attribute name="margin-{$direction.align.start}">
        <xsl:value-of select="$page.margin.outer"/>
	<xsl:if test="$fop.extensions != 0">
	  <xsl:value-of select="concat(' - (',$title.margin.left,')')"/>
        </xsl:if>
      </xsl:attribute>
      <xsl:attribute name="margin-{$direction.align.end}">
        <xsl:value-of select="$page.margin.inner"/>
      </xsl:attribute>
      <xsl:if test="$axf.extensions != 0">
        <xsl:call-template name="axf-page-master-properties">
          <xsl:with-param name="page.master">back-even</xsl:with-param>
        </xsl:call-template>
      </xsl:if>
      <fo:region-body margin-bottom="{$body.margin.bottom}"
                      margin-top="{$body.margin.top}"
                      column-gap="{$column.gap.back}"
                      column-count="{$column.count.back}">
        <xsl:attribute name="margin-{$direction.align.start}">
          <xsl:value-of select="$body.margin.outer"/>
        </xsl:attribute>
        <xsl:attribute name="margin-{$direction.align.end}">
          <xsl:value-of select="$body.margin.inner"/>
        </xsl:attribute>
      </fo:region-body>
      <fo:region-before region-name="xsl-region-before-even"
                        extent="{$region.before.extent}"
                        precedence="{$region.before.precedence}"
                        display-align="before"/>
      <fo:region-after region-name="xsl-region-after-even"
                       extent="{$region.after.extent}"
                        precedence="{$region.after.precedence}"
                       display-align="after"/>
      <xsl:call-template name="region.outer">
        <xsl:with-param name="sequence">even</xsl:with-param>
        <xsl:with-param name="pageclass">back</xsl:with-param>
      </xsl:call-template>
      <xsl:call-template name="region.inner">
        <xsl:with-param name="sequence">even</xsl:with-param>
        <xsl:with-param name="pageclass">back</xsl:with-param>
      </xsl:call-template>
    </fo:simple-page-master>

    <!-- index pages -->
    <fo:simple-page-master master-name="index-first"
                           page-width="{$page.width}"
                           page-height="{$page.height}"
                           margin-top="{$page.margin.top}"
                           margin-bottom="{$page.margin.bottom}">
      <xsl:attribute name="margin-{$direction.align.start}">
        <xsl:value-of select="$page.margin.inner"/>
	<xsl:if test="$fop.extensions != 0">
	  <xsl:value-of select="concat(' - (',$title.margin.left,')')"/>
        </xsl:if>
      </xsl:attribute>
      <xsl:attribute name="margin-{$direction.align.end}">
        <xsl:value-of select="$page.margin.outer"/>
      </xsl:attribute>
      <xsl:if test="$axf.extensions != 0">
        <xsl:call-template name="axf-page-master-properties">
          <xsl:with-param name="page.master">index-first</xsl:with-param>
        </xsl:call-template>
      </xsl:if>
      <fo:region-body margin-bottom="{$body.margin.bottom}"
                      margin-top="{$body.margin.top}"
                      column-gap="{$column.gap.index}"
                      column-count="{$column.count.index}">
        <xsl:attribute name="margin-{$direction.align.start}">
          <xsl:value-of select="$body.margin.inner"/>
        </xsl:attribute>
        <xsl:attribute name="margin-{$direction.align.end}">
          <xsl:value-of select="$body.margin.outer"/>
        </xsl:attribute>
      </fo:region-body>
      <fo:region-before region-name="xsl-region-before-first"
                        extent="{$region.before.extent}"
                        precedence="{$region.before.precedence}"
                        display-align="before"/>
      <fo:region-after region-name="xsl-region-after-first"
                       extent="{$region.after.extent}"
                        precedence="{$region.after.precedence}"
                       display-align="after"/>
      <xsl:call-template name="region.inner">
        <xsl:with-param name="sequence">first</xsl:with-param>
        <xsl:with-param name="pageclass">index</xsl:with-param>
      </xsl:call-template>
      <xsl:call-template name="region.outer">
        <xsl:with-param name="sequence">first</xsl:with-param>
        <xsl:with-param name="pageclass">index</xsl:with-param>
      </xsl:call-template>
    </fo:simple-page-master>

    <fo:simple-page-master master-name="index-odd"
                           page-width="{$page.width}"
                           page-height="{$page.height}"
                           margin-top="{$page.margin.top}"
                           margin-bottom="{$page.margin.bottom}">
      <xsl:attribute name="margin-{$direction.align.start}">
        <xsl:value-of select="$page.margin.inner"/>
	<xsl:if test="$fop.extensions != 0">
	  <xsl:value-of select="concat(' - (',$title.margin.left,')')"/>
        </xsl:if>
      </xsl:attribute>
      <xsl:attribute name="margin-{$direction.align.end}">
        <xsl:value-of select="$page.margin.outer"/>
      </xsl:attribute>
      <xsl:if test="$axf.extensions != 0">
        <xsl:call-template name="axf-page-master-properties">
          <xsl:with-param name="page.master">index-odd</xsl:with-param>
        </xsl:call-template>
      </xsl:if>
      <fo:region-body margin-bottom="{$body.margin.bottom}"
                      margin-top="{$body.margin.top}"
                      column-gap="{$column.gap.index}"
                      column-count="{$column.count.index}">
        <xsl:attribute name="margin-{$direction.align.start}">
          <xsl:value-of select="$body.margin.inner"/>
        </xsl:attribute>
        <xsl:attribute name="margin-{$direction.align.end}">
          <xsl:value-of select="$body.margin.outer"/>
        </xsl:attribute>
      </fo:region-body>
      <fo:region-before region-name="xsl-region-before-odd"
                        extent="{$region.before.extent}"
                        precedence="{$region.before.precedence}"
                        display-align="before"/>
      <fo:region-after region-name="xsl-region-after-odd"
                       extent="{$region.after.extent}"
                        precedence="{$region.after.precedence}"
                       display-align="after"/>
      <xsl:call-template name="region.inner">
        <xsl:with-param name="sequence">odd</xsl:with-param>
        <xsl:with-param name="pageclass">index</xsl:with-param>
      </xsl:call-template>
      <xsl:call-template name="region.outer">
        <xsl:with-param name="sequence">odd</xsl:with-param>
        <xsl:with-param name="pageclass">index</xsl:with-param>
      </xsl:call-template>
    </fo:simple-page-master>

    <fo:simple-page-master master-name="index-even"
                           page-width="{$page.width}"
                           page-height="{$page.height}"
                           margin-top="{$page.margin.top}"
                           margin-bottom="{$page.margin.bottom}">
      <xsl:attribute name="margin-{$direction.align.start}">
        <xsl:value-of select="$page.margin.outer"/>
	<xsl:if test="$fop.extensions != 0">
	  <xsl:value-of select="concat(' - (',$title.margin.left,')')"/>
        </xsl:if>
      </xsl:attribute>
      <xsl:attribute name="margin-{$direction.align.end}">
        <xsl:value-of select="$page.margin.inner"/>
      </xsl:attribute>
      <xsl:if test="$axf.extensions != 0">
        <xsl:call-template name="axf-page-master-properties">
          <xsl:with-param name="page.master">index-even</xsl:with-param>
        </xsl:call-template>
      </xsl:if>
      <fo:region-body margin-bottom="{$body.margin.bottom}"
                      margin-top="{$body.margin.top}"
                      column-gap="{$column.gap.index}"
                      column-count="{$column.count.index}">
        <xsl:attribute name="margin-{$direction.align.start}">
          <xsl:value-of select="$body.margin.outer"/>
        </xsl:attribute>
        <xsl:attribute name="margin-{$direction.align.end}">
          <xsl:value-of select="$body.margin.inner"/>
        </xsl:attribute>
      </fo:region-body>
      <fo:region-before region-name="xsl-region-before-even"
                        extent="{$region.before.extent}"
                        precedence="{$region.before.precedence}"
                        display-align="before"/>
      <fo:region-after region-name="xsl-region-after-even"
                       extent="{$region.after.extent}"
                        precedence="{$region.after.precedence}"
                       display-align="after"/>
      <xsl:call-template name="region.outer">
        <xsl:with-param name="sequence">even</xsl:with-param>
        <xsl:with-param name="pageclass">index</xsl:with-param>
      </xsl:call-template>
      <xsl:call-template name="region.inner">
        <xsl:with-param name="sequence">even</xsl:with-param>
        <xsl:with-param name="pageclass">index</xsl:with-param>
      </xsl:call-template>
    </fo:simple-page-master>

    <xsl:if test="$draft.mode != 'no'">
      <!-- draft blank pages -->
      <fo:simple-page-master master-name="blank-draft"
                             page-width="{$page.width}"
                             page-height="{$page.height}"
                             margin-top="{$page.margin.top}"
                             margin-bottom="{$page.margin.bottom}">
        <xsl:attribute name="margin-{$direction.align.start}">
          <xsl:value-of select="$page.margin.outer"/>
	  <xsl:if test="$fop.extensions != 0">
	    <xsl:value-of select="concat(' - (',$title.margin.left,')')"/>
          </xsl:if>
        </xsl:attribute>
        <xsl:attribute name="margin-{$direction.align.end}">
          <xsl:value-of select="$page.margin.inner"/>
        </xsl:attribute>
        <xsl:if test="$axf.extensions != 0">
          <xsl:call-template name="axf-page-master-properties">
            <xsl:with-param name="page.master">blank-draft</xsl:with-param>
          </xsl:call-template>
        </xsl:if>
        <fo:region-body margin-bottom="{$body.margin.bottom}"
                        margin-top="{$body.margin.top}">
          <xsl:attribute name="margin-{$direction.align.start}">
            <xsl:value-of select="$body.margin.outer"/>
          </xsl:attribute>
          <xsl:attribute name="margin-{$direction.align.end}">
            <xsl:value-of select="$body.margin.inner"/>
          </xsl:attribute>
          <xsl:if test="$draft.watermark.image != ''">
            <xsl:attribute name="background-image">
              <xsl:call-template name="fo-external-image">
                <xsl:with-param name="filename" select="$draft.watermark.image"/>
              </xsl:call-template>
            </xsl:attribute>
            <xsl:attribute name="background-attachment">fixed</xsl:attribute>
            <xsl:attribute name="background-repeat">no-repeat</xsl:attribute>
            <xsl:attribute name="background-position-horizontal">center</xsl:attribute>
            <xsl:attribute name="background-position-vertical">center</xsl:attribute>
          </xsl:if>
        </fo:region-body>
        <fo:region-before region-name="xsl-region-before-blank"
                          extent="{$region.before.extent}"
                          precedence="{$region.before.precedence}"
                          display-align="before"/>
        <fo:region-after region-name="xsl-region-after-blank"
                         extent="{$region.after.extent}"
                          precedence="{$region.after.precedence}"
                         display-align="after"/>
        <xsl:call-template name="region.outer">
          <xsl:with-param name="sequence">blank</xsl:with-param>
          <xsl:with-param name="pageclass">blank</xsl:with-param>
        </xsl:call-template>
        <xsl:call-template name="region.inner">
          <xsl:with-param name="sequence">blank</xsl:with-param>
          <xsl:with-param name="pageclass">blank</xsl:with-param>
        </xsl:call-template>
      </fo:simple-page-master>

      <!-- draft title pages -->
      <fo:simple-page-master master-name="titlepage-first-draft"
                             page-width="{$page.width}"
                             page-height="{$page.height}"
                             margin-top="{$page.margin.top}"
                             margin-bottom="{$page.margin.bottom}">
        <xsl:attribute name="margin-{$direction.align.start}">
          <xsl:value-of select="$page.margin.inner"/>
	  <xsl:if test="$fop.extensions != 0">
	    <xsl:value-of select="concat(' - (',$title.margin.left,')')"/>
          </xsl:if>
        </xsl:attribute>
        <xsl:attribute name="margin-{$direction.align.end}">
          <xsl:value-of select="$page.margin.outer"/>
        </xsl:attribute>
        <xsl:if test="$axf.extensions != 0">
          <xsl:call-template name="axf-page-master-properties">
            <xsl:with-param name="page.master">titlepage-first-draft</xsl:with-param>
          </xsl:call-template>
        </xsl:if>
        <fo:region-body margin-bottom="{$body.margin.bottom}"
                        margin-top="{$body.margin.top}"
                        column-gap="{$column.gap.titlepage}"
                        column-count="{$column.count.titlepage}">
          <xsl:attribute name="margin-{$direction.align.start}">
            <xsl:value-of select="$body.margin.inner"/>
          </xsl:attribute>
          <xsl:attribute name="margin-{$direction.align.end}">
            <xsl:value-of select="$body.margin.outer"/>
          </xsl:attribute>
          <xsl:if test="$draft.watermark.image != ''">
            <xsl:attribute name="background-image">
              <xsl:call-template name="fo-external-image">
                <xsl:with-param name="filename" select="$draft.watermark.image"/>
              </xsl:call-template>
            </xsl:attribute>
            <xsl:attribute name="background-attachment">fixed</xsl:attribute>
            <xsl:attribute name="background-repeat">no-repeat</xsl:attribute>
            <xsl:attribute name="background-position-horizontal">center</xsl:attribute>
            <xsl:attribute name="background-position-vertical">center</xsl:attribute>
          </xsl:if>
        </fo:region-body>
        <fo:region-before region-name="xsl-region-before-first"
                          extent="{$region.before.extent}"
                          precedence="{$region.before.precedence}"
                          display-align="before"/>
        <fo:region-after region-name="xsl-region-after-first"
                         extent="{$region.after.extent}"
                          precedence="{$region.after.precedence}"
                         display-align="after"/>
        <xsl:call-template name="region.inner">
          <xsl:with-param name="sequence">first</xsl:with-param>
          <xsl:with-param name="pageclass">titlepage</xsl:with-param>
        </xsl:call-template>
        <xsl:call-template name="region.outer">
          <xsl:with-param name="sequence">first</xsl:with-param>
          <xsl:with-param name="pageclass">titlepage</xsl:with-param>
        </xsl:call-template>
      </fo:simple-page-master>

      <fo:simple-page-master master-name="titlepage-odd-draft"
                             page-width="{$page.width}"
                             page-height="{$page.height}"
                             margin-top="{$page.margin.top}"
                             margin-bottom="{$page.margin.bottom}">
        <xsl:attribute name="margin-{$direction.align.start}">
          <xsl:value-of select="$page.margin.inner"/>
	  <xsl:if test="$fop.extensions != 0">
	    <xsl:value-of select="concat(' - (',$title.margin.left,')')"/>
          </xsl:if>
        </xsl:attribute>
        <xsl:attribute name="margin-{$direction.align.end}">
          <xsl:value-of select="$page.margin.outer"/>
        </xsl:attribute>
        <xsl:if test="$axf.extensions != 0">
          <xsl:call-template name="axf-page-master-properties">
            <xsl:with-param name="page.master">titlepage-odd-draft</xsl:with-param>
          </xsl:call-template>
        </xsl:if>
        <fo:region-body margin-bottom="{$body.margin.bottom}"
                        margin-top="{$body.margin.top}"
                        column-gap="{$column.gap.titlepage}"
                        column-count="{$column.count.titlepage}">
          <xsl:attribute name="margin-{$direction.align.start}">
            <xsl:value-of select="$body.margin.inner"/>
          </xsl:attribute>
          <xsl:attribute name="margin-{$direction.align.end}">
            <xsl:value-of select="$body.margin.outer"/>
          </xsl:attribute>
          <xsl:if test="$draft.watermark.image != ''">
            <xsl:attribute name="background-image">
              <xsl:call-template name="fo-external-image">
                <xsl:with-param name="filename" select="$draft.watermark.image"/>
              </xsl:call-template>
            </xsl:attribute>
            <xsl:attribute name="background-attachment">fixed</xsl:attribute>
            <xsl:attribute name="background-repeat">no-repeat</xsl:attribute>
            <xsl:attribute name="background-position-horizontal">center</xsl:attribute>
            <xsl:attribute name="background-position-vertical">center</xsl:attribute>
          </xsl:if>
        </fo:region-body>
        <fo:region-before region-name="xsl-region-before-odd"
                          extent="{$region.before.extent}"
                          precedence="{$region.before.precedence}"
                          display-align="before"/>
        <fo:region-after region-name="xsl-region-after-odd"
                         extent="{$region.after.extent}"
                          precedence="{$region.after.precedence}"
                         display-align="after"/>
        <xsl:call-template name="region.inner">
          <xsl:with-param name="sequence">odd</xsl:with-param>
          <xsl:with-param name="pageclass">titlepage</xsl:with-param>
        </xsl:call-template>
        <xsl:call-template name="region.outer">
          <xsl:with-param name="sequence">odd</xsl:with-param>
          <xsl:with-param name="pageclass">titlepage</xsl:with-param>
        </xsl:call-template>
      </fo:simple-page-master>

      <fo:simple-page-master master-name="titlepage-even-draft"
                             page-width="{$page.width}"
                             page-height="{$page.height}"
                             margin-top="{$page.margin.top}"
                             margin-bottom="{$page.margin.bottom}">
        <xsl:attribute name="margin-{$direction.align.start}">
          <xsl:value-of select="$page.margin.outer"/>
	  <xsl:if test="$fop.extensions != 0">
	    <xsl:value-of select="concat(' - (',$title.margin.left,')')"/>
          </xsl:if>
        </xsl:attribute>
        <xsl:attribute name="margin-{$direction.align.end}">
          <xsl:value-of select="$page.margin.inner"/>
        </xsl:attribute>
        <xsl:if test="$axf.extensions != 0">
          <xsl:call-template name="axf-page-master-properties">
            <xsl:with-param name="page.master">titlepage-even-draft</xsl:with-param>
          </xsl:call-template>
        </xsl:if>
        <fo:region-body margin-bottom="{$body.margin.bottom}"
                        margin-top="{$body.margin.top}"
                        column-gap="{$column.gap.titlepage}"
                        column-count="{$column.count.titlepage}">
          <xsl:attribute name="margin-{$direction.align.start}">
            <xsl:value-of select="$body.margin.outer"/>
          </xsl:attribute>
          <xsl:attribute name="margin-{$direction.align.end}">
            <xsl:value-of select="$body.margin.inner"/>
          </xsl:attribute>
          <xsl:if test="$draft.watermark.image != ''">
            <xsl:attribute name="background-image">
              <xsl:call-template name="fo-external-image">
                <xsl:with-param name="filename" select="$draft.watermark.image"/>
              </xsl:call-template>
            </xsl:attribute>
            <xsl:attribute name="background-attachment">fixed</xsl:attribute>
            <xsl:attribute name="background-repeat">no-repeat</xsl:attribute>
            <xsl:attribute name="background-position-horizontal">center</xsl:attribute>
            <xsl:attribute name="background-position-vertical">center</xsl:attribute>
          </xsl:if>
        </fo:region-body>
        <fo:region-before region-name="xsl-region-before-even"
                          extent="{$region.before.extent}"
                          precedence="{$region.before.precedence}"
                          display-align="before"/>
        <fo:region-after region-name="xsl-region-after-even"
                         extent="{$region.after.extent}"
                          precedence="{$region.after.precedence}"
                         display-align="after"/>
        <xsl:call-template name="region.outer">
          <xsl:with-param name="sequence">even</xsl:with-param>
          <xsl:with-param name="pageclass">titlepage</xsl:with-param>
        </xsl:call-template>
        <xsl:call-template name="region.inner">
          <xsl:with-param name="sequence">even</xsl:with-param>
          <xsl:with-param name="pageclass">titlepage</xsl:with-param>
        </xsl:call-template>
      </fo:simple-page-master>

      <!-- draft list-of-title pages -->
      <fo:simple-page-master master-name="lot-first-draft"
                             page-width="{$page.width}"
                             page-height="{$page.height}"
                             margin-top="{$page.margin.top}"
                             margin-bottom="{$page.margin.bottom}">
        <xsl:attribute name="margin-{$direction.align.start}">
          <xsl:value-of select="$page.margin.inner"/>
	  <xsl:if test="$fop.extensions != 0">
	    <xsl:value-of select="concat(' - (',$title.margin.left,')')"/>
          </xsl:if>
        </xsl:attribute>
        <xsl:attribute name="margin-{$direction.align.end}">
          <xsl:value-of select="$page.margin.outer"/>
        </xsl:attribute>
        <xsl:if test="$axf.extensions != 0">
          <xsl:call-template name="axf-page-master-properties">
            <xsl:with-param name="page.master">lot-first-draft</xsl:with-param>
          </xsl:call-template>
        </xsl:if>
        <fo:region-body margin-bottom="{$body.margin.bottom}"
                        margin-top="{$body.margin.top}"
                        column-gap="{$column.gap.lot}"
                        column-count="{$column.count.lot}">
          <xsl:attribute name="margin-{$direction.align.start}">
            <xsl:value-of select="$body.margin.inner"/>
          </xsl:attribute>
          <xsl:attribute name="margin-{$direction.align.end}">
            <xsl:value-of select="$body.margin.outer"/>
          </xsl:attribute>
          <xsl:if test="$draft.watermark.image != ''">
            <xsl:attribute name="background-image">
              <xsl:call-template name="fo-external-image">
                <xsl:with-param name="filename" select="$draft.watermark.image"/>
              </xsl:call-template>
            </xsl:attribute>
            <xsl:attribute name="background-attachment">fixed</xsl:attribute>
            <xsl:attribute name="background-repeat">no-repeat</xsl:attribute>
            <xsl:attribute name="background-position-horizontal">center</xsl:attribute>
            <xsl:attribute name="background-position-vertical">center</xsl:attribute>
          </xsl:if>
        </fo:region-body>
        <fo:region-before region-name="xsl-region-before-first"
                          extent="{$region.before.extent}"
                          precedence="{$region.before.precedence}"
                          display-align="before"/>
        <fo:region-after region-name="xsl-region-after-first"
                         extent="{$region.after.extent}"
                          precedence="{$region.after.precedence}"
                         display-align="after"/>
        <xsl:call-template name="region.inner">
          <xsl:with-param name="sequence">first</xsl:with-param>
          <xsl:with-param name="pageclass">lot</xsl:with-param>
        </xsl:call-template>
        <xsl:call-template name="region.outer">
          <xsl:with-param name="sequence">first</xsl:with-param>
          <xsl:with-param name="pageclass">lot</xsl:with-param>
        </xsl:call-template>
      </fo:simple-page-master>

      <fo:simple-page-master master-name="lot-odd-draft"
                             page-width="{$page.width}"
                             page-height="{$page.height}"
                             margin-top="{$page.margin.top}"
                             margin-bottom="{$page.margin.bottom}">
        <xsl:attribute name="margin-{$direction.align.start}">
          <xsl:value-of select="$page.margin.inner"/>
	  <xsl:if test="$fop.extensions != 0">
	    <xsl:value-of select="concat(' - (',$title.margin.left,')')"/>
          </xsl:if>
        </xsl:attribute>
        <xsl:attribute name="margin-{$direction.align.end}">
          <xsl:value-of select="$page.margin.outer"/>
        </xsl:attribute>
        <xsl:if test="$axf.extensions != 0">
          <xsl:call-template name="axf-page-master-properties">
            <xsl:with-param name="page.master">lot-odd-draft</xsl:with-param>
          </xsl:call-template>
        </xsl:if>
        <fo:region-body margin-bottom="{$body.margin.bottom}"
                        margin-top="{$body.margin.top}"
                        column-gap="{$column.gap.lot}"
                        column-count="{$column.count.lot}">
          <xsl:attribute name="margin-{$direction.align.start}">
            <xsl:value-of select="$body.margin.inner"/>
          </xsl:attribute>
          <xsl:attribute name="margin-{$direction.align.end}">
            <xsl:value-of select="$body.margin.outer"/>
          </xsl:attribute>
          <xsl:if test="$draft.watermark.image != ''">
            <xsl:attribute name="background-image">
              <xsl:call-template name="fo-external-image">
                <xsl:with-param name="filename" select="$draft.watermark.image"/>
              </xsl:call-template>
            </xsl:attribute>
            <xsl:attribute name="background-attachment">fixed</xsl:attribute>
            <xsl:attribute name="background-repeat">no-repeat</xsl:attribute>
            <xsl:attribute name="background-position-horizontal">center</xsl:attribute>
            <xsl:attribute name="background-position-vertical">center</xsl:attribute>
          </xsl:if>
        </fo:region-body>
        <fo:region-before region-name="xsl-region-before-odd"
                          extent="{$region.before.extent}"
                          precedence="{$region.before.precedence}"
                          display-align="before"/>
        <fo:region-after region-name="xsl-region-after-odd"
                         extent="{$region.after.extent}"
                          precedence="{$region.after.precedence}"
                         display-align="after"/>
        <xsl:call-template name="region.inner">
          <xsl:with-param name="sequence">odd</xsl:with-param>
          <xsl:with-param name="pageclass">lot</xsl:with-param>
        </xsl:call-template>
        <xsl:call-template name="region.outer">
          <xsl:with-param name="sequence">odd</xsl:with-param>
          <xsl:with-param name="pageclass">lot</xsl:with-param>
        </xsl:call-template>
      </fo:simple-page-master>

      <fo:simple-page-master master-name="lot-even-draft"
                             page-width="{$page.width}"
                             page-height="{$page.height}"
                             margin-top="{$page.margin.top}"
                             margin-bottom="{$page.margin.bottom}">
        <xsl:attribute name="margin-{$direction.align.start}">
          <xsl:value-of select="$page.margin.outer"/>
	  <xsl:if test="$fop.extensions != 0">
	    <xsl:value-of select="concat(' - (',$title.margin.left,')')"/>
          </xsl:if>
        </xsl:attribute>
        <xsl:attribute name="margin-{$direction.align.end}">
          <xsl:value-of select="$page.margin.inner"/>
        </xsl:attribute>
        <xsl:if test="$axf.extensions != 0">
          <xsl:call-template name="axf-page-master-properties">
            <xsl:with-param name="page.master">lot-even-draft</xsl:with-param>
          </xsl:call-template>
        </xsl:if>
        <fo:region-body margin-bottom="{$body.margin.bottom}"
                        margin-top="{$body.margin.top}"
                        column-gap="{$column.gap.lot}"
                        column-count="{$column.count.lot}">
          <xsl:attribute name="margin-{$direction.align.start}">
            <xsl:value-of select="$body.margin.outer"/>
          </xsl:attribute>
          <xsl:attribute name="margin-{$direction.align.end}">
            <xsl:value-of select="$body.margin.inner"/>
          </xsl:attribute>
          <xsl:if test="$draft.watermark.image != ''">
            <xsl:attribute name="background-image">
              <xsl:call-template name="fo-external-image">
                <xsl:with-param name="filename" select="$draft.watermark.image"/>
              </xsl:call-template>
            </xsl:attribute>
            <xsl:attribute name="background-attachment">fixed</xsl:attribute>
            <xsl:attribute name="background-repeat">no-repeat</xsl:attribute>
            <xsl:attribute name="background-position-horizontal">center</xsl:attribute>
            <xsl:attribute name="background-position-vertical">center</xsl:attribute>
          </xsl:if>
        </fo:region-body>
        <fo:region-before region-name="xsl-region-before-even"
                          extent="{$region.before.extent}"
                          precedence="{$region.before.precedence}"
                          display-align="before"/>
        <fo:region-after region-name="xsl-region-after-even"
                         extent="{$region.after.extent}"
                          precedence="{$region.after.precedence}"
                         display-align="after"/>
        <xsl:call-template name="region.outer">
          <xsl:with-param name="sequence">even</xsl:with-param>
          <xsl:with-param name="pageclass">lot</xsl:with-param>
        </xsl:call-template>
        <xsl:call-template name="region.inner">
          <xsl:with-param name="sequence">even</xsl:with-param>
          <xsl:with-param name="pageclass">lot</xsl:with-param>
        </xsl:call-template>
      </fo:simple-page-master>

      <!-- draft frontmatter pages -->
      <fo:simple-page-master master-name="front-first-draft"
                             page-width="{$page.width}"
                             page-height="{$page.height}"
                             margin-top="{$page.margin.top}"
                             margin-bottom="{$page.margin.bottom}">
        <xsl:attribute name="margin-{$direction.align.start}">
          <xsl:value-of select="$page.margin.inner"/>
	  <xsl:if test="$fop.extensions != 0">
	    <xsl:value-of select="concat(' - (',$title.margin.left,')')"/>
          </xsl:if>
        </xsl:attribute>
        <xsl:attribute name="margin-{$direction.align.end}">
          <xsl:value-of select="$page.margin.outer"/>
        </xsl:attribute>
        <xsl:if test="$axf.extensions != 0">
          <xsl:call-template name="axf-page-master-properties">
            <xsl:with-param name="page.master">front-first-draft</xsl:with-param>
          </xsl:call-template>
        </xsl:if>
        <fo:region-body margin-bottom="{$body.margin.bottom}"
                        margin-top="{$body.margin.top}"
                        column-gap="{$column.gap.front}"
                        column-count="{$column.count.front}">
          <xsl:attribute name="margin-{$direction.align.start}">
            <xsl:value-of select="$body.margin.inner"/>
          </xsl:attribute>
          <xsl:attribute name="margin-{$direction.align.end}">
            <xsl:value-of select="$body.margin.outer"/>
          </xsl:attribute>
          <xsl:if test="$draft.watermark.image != ''">
            <xsl:attribute name="background-image">
              <xsl:call-template name="fo-external-image">
                <xsl:with-param name="filename" select="$draft.watermark.image"/>
              </xsl:call-template>
            </xsl:attribute>
            <xsl:attribute name="background-attachment">fixed</xsl:attribute>
            <xsl:attribute name="background-repeat">no-repeat</xsl:attribute>
            <xsl:attribute name="background-position-horizontal">center</xsl:attribute>
            <xsl:attribute name="background-position-vertical">center</xsl:attribute>
          </xsl:if>
        </fo:region-body>
        <fo:region-before region-name="xsl-region-before-first"
                          extent="{$region.before.extent}"
                          precedence="{$region.before.precedence}"
                          display-align="before"/>
        <fo:region-after region-name="xsl-region-after-first"
                         extent="{$region.after.extent}"
                          precedence="{$region.after.precedence}"
                         display-align="after"/>
        <xsl:call-template name="region.inner">
          <xsl:with-param name="sequence">first</xsl:with-param>
          <xsl:with-param name="pageclass">front</xsl:with-param>
        </xsl:call-template>
        <xsl:call-template name="region.outer">
          <xsl:with-param name="sequence">first</xsl:with-param>
          <xsl:with-param name="pageclass">front</xsl:with-param>
        </xsl:call-template>
      </fo:simple-page-master>

      <fo:simple-page-master master-name="front-odd-draft"
                             page-width="{$page.width}"
                             page-height="{$page.height}"
                             margin-top="{$page.margin.top}"
                             margin-bottom="{$page.margin.bottom}">
        <xsl:attribute name="margin-{$direction.align.start}">
          <xsl:value-of select="$page.margin.inner"/>
	  <xsl:if test="$fop.extensions != 0">
	    <xsl:value-of select="concat(' - (',$title.margin.left,')')"/>
          </xsl:if>
        </xsl:attribute>
        <xsl:attribute name="margin-{$direction.align.end}">
          <xsl:value-of select="$page.margin.outer"/>
        </xsl:attribute>
        <xsl:if test="$axf.extensions != 0">
          <xsl:call-template name="axf-page-master-properties">
            <xsl:with-param name="page.master">front-odd-draft</xsl:with-param>
          </xsl:call-template>
        </xsl:if>
        <fo:region-body margin-bottom="{$body.margin.bottom}"
                        margin-top="{$body.margin.top}"
                        column-gap="{$column.gap.front}"
                        column-count="{$column.count.front}">
          <xsl:attribute name="margin-{$direction.align.start}">
            <xsl:value-of select="$body.margin.inner"/>
          </xsl:attribute>
          <xsl:attribute name="margin-{$direction.align.end}">
            <xsl:value-of select="$body.margin.outer"/>
          </xsl:attribute>
          <xsl:if test="$draft.watermark.image != ''">
            <xsl:attribute name="background-image">
              <xsl:call-template name="fo-external-image">
                <xsl:with-param name="filename" select="$draft.watermark.image"/>
              </xsl:call-template>
            </xsl:attribute>
            <xsl:attribute name="background-attachment">fixed</xsl:attribute>
            <xsl:attribute name="background-repeat">no-repeat</xsl:attribute>
            <xsl:attribute name="background-position-horizontal">center</xsl:attribute>
            <xsl:attribute name="background-position-vertical">center</xsl:attribute>
          </xsl:if>
        </fo:region-body>
        <fo:region-before region-name="xsl-region-before-odd"
                          extent="{$region.before.extent}"
                          precedence="{$region.before.precedence}"
                          display-align="before"/>
        <fo:region-after region-name="xsl-region-after-odd"
                         extent="{$region.after.extent}"
                          precedence="{$region.after.precedence}"
                         display-align="after"/>
        <xsl:call-template name="region.inner">
          <xsl:with-param name="sequence">odd</xsl:with-param>
          <xsl:with-param name="pageclass">front</xsl:with-param>
        </xsl:call-template>
        <xsl:call-template name="region.outer">
          <xsl:with-param name="sequence">odd</xsl:with-param>
          <xsl:with-param name="pageclass">front</xsl:with-param>
        </xsl:call-template>
      </fo:simple-page-master>

      <fo:simple-page-master master-name="front-even-draft"
                             page-width="{$page.width}"
                             page-height="{$page.height}"
                             margin-top="{$page.margin.top}"
                             margin-bottom="{$page.margin.bottom}">
        <xsl:attribute name="margin-{$direction.align.start}">
          <xsl:value-of select="$page.margin.outer"/>
	  <xsl:if test="$fop.extensions != 0">
	    <xsl:value-of select="concat(' - (',$title.margin.left,')')"/>
          </xsl:if>
        </xsl:attribute>
        <xsl:attribute name="margin-{$direction.align.end}">
          <xsl:value-of select="$page.margin.inner"/>
        </xsl:attribute>
        <xsl:if test="$axf.extensions != 0">
          <xsl:call-template name="axf-page-master-properties">
            <xsl:with-param name="page.master">front-even-draft</xsl:with-param>
          </xsl:call-template>
        </xsl:if>
        <fo:region-body margin-bottom="{$body.margin.bottom}"
                        margin-top="{$body.margin.top}"
                        column-gap="{$column.gap.front}"
                        column-count="{$column.count.front}">
          <xsl:attribute name="margin-{$direction.align.start}">
            <xsl:value-of select="$body.margin.outer"/>
          </xsl:attribute>
          <xsl:attribute name="margin-{$direction.align.end}">
            <xsl:value-of select="$body.margin.inner"/>
          </xsl:attribute>
          <xsl:if test="$draft.watermark.image != ''">
            <xsl:attribute name="background-image">
              <xsl:call-template name="fo-external-image">
                <xsl:with-param name="filename" select="$draft.watermark.image"/>
              </xsl:call-template>
            </xsl:attribute>
            <xsl:attribute name="background-attachment">fixed</xsl:attribute>
            <xsl:attribute name="background-repeat">no-repeat</xsl:attribute>
            <xsl:attribute name="background-position-horizontal">center</xsl:attribute>
            <xsl:attribute name="background-position-vertical">center</xsl:attribute>
          </xsl:if>
        </fo:region-body>
        <fo:region-before region-name="xsl-region-before-even"
                          extent="{$region.before.extent}"
                          precedence="{$region.before.precedence}"
                          display-align="before"/>
        <fo:region-after region-name="xsl-region-after-even"
                         extent="{$region.after.extent}"
                          precedence="{$region.after.precedence}"
                         display-align="after"/>
        <xsl:call-template name="region.outer">
          <xsl:with-param name="sequence">even</xsl:with-param>
          <xsl:with-param name="pageclass">front</xsl:with-param>
        </xsl:call-template>
        <xsl:call-template name="region.inner">
          <xsl:with-param name="sequence">even</xsl:with-param>
          <xsl:with-param name="pageclass">front</xsl:with-param>
        </xsl:call-template>
      </fo:simple-page-master>

      <!-- draft body pages -->
      <fo:simple-page-master master-name="body-first-draft"
                             page-width="{$page.width}"
                             page-height="{$page.height}"
                             margin-top="{$page.margin.top}"
                             margin-bottom="{$page.margin.bottom}">
        <xsl:attribute name="margin-{$direction.align.start}">
          <xsl:value-of select="$page.margin.inner"/>
	  <xsl:if test="$fop.extensions != 0">
	    <xsl:value-of select="concat(' - (',$title.margin.left,')')"/>
          </xsl:if>
        </xsl:attribute>
        <xsl:attribute name="margin-{$direction.align.end}">
          <xsl:value-of select="$page.margin.outer"/>
        </xsl:attribute>
        <xsl:if test="$axf.extensions != 0">
          <xsl:call-template name="axf-page-master-properties">
            <xsl:with-param name="page.master">body-first-draft</xsl:with-param>
          </xsl:call-template>
        </xsl:if>
        <fo:region-body margin-bottom="{$body.margin.bottom}"
                        margin-top="{$body.margin.top}"
                        column-gap="{$column.gap.body}"
                        column-count="{$column.count.body}">
          <xsl:attribute name="margin-{$direction.align.start}">
            <xsl:value-of select="$body.margin.inner"/>
          </xsl:attribute>
          <xsl:attribute name="margin-{$direction.align.end}">
            <xsl:value-of select="$body.margin.outer"/>
          </xsl:attribute>
          <xsl:if test="$draft.watermark.image != ''">
            <xsl:attribute name="background-image">
              <xsl:call-template name="fo-external-image">
                <xsl:with-param name="filename" select="$draft.watermark.image"/>
              </xsl:call-template>
            </xsl:attribute>
            <xsl:attribute name="background-attachment">fixed</xsl:attribute>
            <xsl:attribute name="background-repeat">no-repeat</xsl:attribute>
            <xsl:attribute name="background-position-horizontal">center</xsl:attribute>
            <xsl:attribute name="background-position-vertical">center</xsl:attribute>
          </xsl:if>
        </fo:region-body>
        <fo:region-before region-name="xsl-region-before-first"
                          extent="{$region.before.extent}"
                          precedence="{$region.before.precedence}"
                          display-align="before"/>
        <fo:region-after region-name="xsl-region-after-first"
                         extent="{$region.after.extent}"
                          precedence="{$region.after.precedence}"
                         display-align="after"/>
        <xsl:call-template name="region.inner">
          <xsl:with-param name="sequence">first</xsl:with-param>
          <xsl:with-param name="pageclass">body</xsl:with-param>
        </xsl:call-template>
        <xsl:call-template name="region.outer">
          <xsl:with-param name="sequence">first</xsl:with-param>
          <xsl:with-param name="pageclass">body</xsl:with-param>
        </xsl:call-template>
      </fo:simple-page-master>

      <fo:simple-page-master master-name="body-odd-draft"
                             page-width="{$page.width}"
                             page-height="{$page.height}"
                             margin-top="{$page.margin.top}"
                             margin-bottom="{$page.margin.bottom}">
        <xsl:attribute name="margin-{$direction.align.start}">
          <xsl:value-of select="$page.margin.inner"/>
	  <xsl:if test="$fop.extensions != 0">
	    <xsl:value-of select="concat(' - (',$title.margin.left,')')"/>
          </xsl:if>
        </xsl:attribute>
        <xsl:attribute name="margin-{$direction.align.end}">
          <xsl:value-of select="$page.margin.outer"/>
        </xsl:attribute>
        <xsl:if test="$axf.extensions != 0">
          <xsl:call-template name="axf-page-master-properties">
            <xsl:with-param name="page.master">body-odd-draft</xsl:with-param>
          </xsl:call-template>
        </xsl:if>
        <fo:region-body margin-bottom="{$body.margin.bottom}"
                        margin-top="{$body.margin.top}"
                        column-gap="{$column.gap.body}"
                        column-count="{$column.count.body}">
          <xsl:attribute name="margin-{$direction.align.start}">
            <xsl:value-of select="$body.margin.inner"/>
          </xsl:attribute>
          <xsl:attribute name="margin-{$direction.align.end}">
            <xsl:value-of select="$body.margin.outer"/>
          </xsl:attribute>
          <xsl:if test="$draft.watermark.image != ''">
            <xsl:attribute name="background-image">
              <xsl:call-template name="fo-external-image">
                <xsl:with-param name="filename" select="$draft.watermark.image"/>
              </xsl:call-template>
            </xsl:attribute>
            <xsl:attribute name="background-attachment">fixed</xsl:attribute>
            <xsl:attribute name="background-repeat">no-repeat</xsl:attribute>
            <xsl:attribute name="background-position-horizontal">center</xsl:attribute>
            <xsl:attribute name="background-position-vertical">center</xsl:attribute>
          </xsl:if>
        </fo:region-body>
        <fo:region-before region-name="xsl-region-before-odd"
                          extent="{$region.before.extent}"
                          precedence="{$region.before.precedence}"
                          display-align="before"/>
        <fo:region-after region-name="xsl-region-after-odd"
                         extent="{$region.after.extent}"
                          precedence="{$region.after.precedence}"
                         display-align="after"/>
        <xsl:call-template name="region.inner">
          <xsl:with-param name="sequence">odd</xsl:with-param>
          <xsl:with-param name="pageclass">body</xsl:with-param>
        </xsl:call-template>
        <xsl:call-template name="region.outer">
          <xsl:with-param name="sequence">odd</xsl:with-param>
          <xsl:with-param name="pageclass">body</xsl:with-param>
        </xsl:call-template>
      </fo:simple-page-master>

      <fo:simple-page-master master-name="body-even-draft"
                             page-width="{$page.width}"
                             page-height="{$page.height}"
                             margin-top="{$page.margin.top}"
                             margin-bottom="{$page.margin.bottom}">
        <xsl:attribute name="margin-{$direction.align.start}">
          <xsl:value-of select="$page.margin.outer"/>
	  <xsl:if test="$fop.extensions != 0">
	    <xsl:value-of select="concat(' - (',$title.margin.left,')')"/>
          </xsl:if>
        </xsl:attribute>
        <xsl:attribute name="margin-{$direction.align.end}">
          <xsl:value-of select="$page.margin.inner"/>
        </xsl:attribute>
        <xsl:if test="$axf.extensions != 0">
          <xsl:call-template name="axf-page-master-properties">
            <xsl:with-param name="page.master">body-even-draft</xsl:with-param>
          </xsl:call-template>
        </xsl:if>
        <fo:region-body margin-bottom="{$body.margin.bottom}"
                        margin-top="{$body.margin.top}"
                        column-gap="{$column.gap.body}"
                        column-count="{$column.count.body}">
          <xsl:attribute name="margin-{$direction.align.start}">
            <xsl:value-of select="$body.margin.outer"/>
          </xsl:attribute>
          <xsl:attribute name="margin-{$direction.align.end}">
            <xsl:value-of select="$body.margin.inner"/>
          </xsl:attribute>
          <xsl:if test="$draft.watermark.image != ''">
            <xsl:attribute name="background-image">
              <xsl:call-template name="fo-external-image">
                <xsl:with-param name="filename" select="$draft.watermark.image"/>
              </xsl:call-template>
            </xsl:attribute>
            <xsl:attribute name="background-attachment">fixed</xsl:attribute>
            <xsl:attribute name="background-repeat">no-repeat</xsl:attribute>
            <xsl:attribute name="background-position-horizontal">center</xsl:attribute>
            <xsl:attribute name="background-position-vertical">center</xsl:attribute>
          </xsl:if>
        </fo:region-body>
        <fo:region-before region-name="xsl-region-before-even"
                          extent="{$region.before.extent}"
                          precedence="{$region.before.precedence}"
                          display-align="before"/>
        <fo:region-after region-name="xsl-region-after-even"
                         extent="{$region.after.extent}"
                          precedence="{$region.after.precedence}"
                         display-align="after"/>
        <xsl:call-template name="region.outer">
          <xsl:with-param name="sequence">even</xsl:with-param>
          <xsl:with-param name="pageclass">body</xsl:with-param>
        </xsl:call-template>
        <xsl:call-template name="region.inner">
          <xsl:with-param name="sequence">even</xsl:with-param>
          <xsl:with-param name="pageclass">body</xsl:with-param>
        </xsl:call-template>
      </fo:simple-page-master>

      <!-- draft backmatter pages -->
      <fo:simple-page-master master-name="back-first-draft"
                             page-width="{$page.width}"
                             page-height="{$page.height}"
                             margin-top="{$page.margin.top}"
                             margin-bottom="{$page.margin.bottom}">
        <xsl:attribute name="margin-{$direction.align.start}">
          <xsl:value-of select="$page.margin.inner"/>
	  <xsl:if test="$fop.extensions != 0">
	    <xsl:value-of select="concat(' - (',$title.margin.left,')')"/>
          </xsl:if>
        </xsl:attribute>
        <xsl:attribute name="margin-{$direction.align.end}">
          <xsl:value-of select="$page.margin.outer"/>
        </xsl:attribute>
        <xsl:if test="$axf.extensions != 0">
          <xsl:call-template name="axf-page-master-properties">
            <xsl:with-param name="page.master">back-first-draft</xsl:with-param>
          </xsl:call-template>
        </xsl:if>
        <fo:region-body margin-bottom="{$body.margin.bottom}"
                        margin-top="{$body.margin.top}"
                        column-gap="{$column.gap.back}"
                        column-count="{$column.count.back}">
          <xsl:attribute name="margin-{$direction.align.start}">
            <xsl:value-of select="$body.margin.inner"/>
          </xsl:attribute>
          <xsl:attribute name="margin-{$direction.align.end}">
            <xsl:value-of select="$body.margin.outer"/>
          </xsl:attribute>
          <xsl:if test="$draft.watermark.image != ''">
            <xsl:attribute name="background-image">
              <xsl:call-template name="fo-external-image">
                <xsl:with-param name="filename" select="$draft.watermark.image"/>
              </xsl:call-template>
            </xsl:attribute>
            <xsl:attribute name="background-attachment">fixed</xsl:attribute>
            <xsl:attribute name="background-repeat">no-repeat</xsl:attribute>
            <xsl:attribute name="background-position-horizontal">center</xsl:attribute>
            <xsl:attribute name="background-position-vertical">center</xsl:attribute>
          </xsl:if>
        </fo:region-body>
        <fo:region-before region-name="xsl-region-before-first"
                          extent="{$region.before.extent}"
                          precedence="{$region.before.precedence}"
                          display-align="before"/>
        <fo:region-after region-name="xsl-region-after-first"
                         extent="{$region.after.extent}"
                          precedence="{$region.after.precedence}"
                         display-align="after"/>
        <xsl:call-template name="region.inner">
          <xsl:with-param name="sequence">first</xsl:with-param>
          <xsl:with-param name="pageclass">back</xsl:with-param>
        </xsl:call-template>
        <xsl:call-template name="region.outer">
          <xsl:with-param name="sequence">first</xsl:with-param>
          <xsl:with-param name="pageclass">back</xsl:with-param>
        </xsl:call-template>
      </fo:simple-page-master>

      <fo:simple-page-master master-name="back-odd-draft"
                             page-width="{$page.width}"
                             page-height="{$page.height}"
                             margin-top="{$page.margin.top}"
                             margin-bottom="{$page.margin.bottom}">
        <xsl:attribute name="margin-{$direction.align.start}">
          <xsl:value-of select="$page.margin.inner"/>
	  <xsl:if test="$fop.extensions != 0">
	    <xsl:value-of select="concat(' - (',$title.margin.left,')')"/>
          </xsl:if>
        </xsl:attribute>
        <xsl:attribute name="margin-{$direction.align.end}">
          <xsl:value-of select="$page.margin.outer"/>
        </xsl:attribute>
        <xsl:if test="$axf.extensions != 0">
          <xsl:call-template name="axf-page-master-properties">
            <xsl:with-param name="page.master">back-odd-draft</xsl:with-param>
          </xsl:call-template>
        </xsl:if>
        <fo:region-body margin-bottom="{$body.margin.bottom}"
                        margin-top="{$body.margin.top}"
                        column-gap="{$column.gap.back}"
                        column-count="{$column.count.back}">
          <xsl:attribute name="margin-{$direction.align.start}">
            <xsl:value-of select="$body.margin.inner"/>
          </xsl:attribute>
          <xsl:attribute name="margin-{$direction.align.end}">
            <xsl:value-of select="$body.margin.outer"/>
          </xsl:attribute>
          <xsl:if test="$draft.watermark.image != ''">
            <xsl:attribute name="background-image">
              <xsl:call-template name="fo-external-image">
                <xsl:with-param name="filename" select="$draft.watermark.image"/>
              </xsl:call-template>
            </xsl:attribute>
            <xsl:attribute name="background-attachment">fixed</xsl:attribute>
            <xsl:attribute name="background-repeat">no-repeat</xsl:attribute>
            <xsl:attribute name="background-position-horizontal">center</xsl:attribute>
            <xsl:attribute name="background-position-vertical">center</xsl:attribute>
          </xsl:if>
        </fo:region-body>
        <fo:region-before region-name="xsl-region-before-odd"
                          extent="{$region.before.extent}"
                          precedence="{$region.before.precedence}"
                          display-align="before"/>
        <fo:region-after region-name="xsl-region-after-odd"
                         extent="{$region.after.extent}"
                          precedence="{$region.after.precedence}"
                         display-align="after"/>
        <xsl:call-template name="region.inner">
          <xsl:with-param name="sequence">odd</xsl:with-param>
          <xsl:with-param name="pageclass">back</xsl:with-param>
        </xsl:call-template>
        <xsl:call-template name="region.outer">
          <xsl:with-param name="sequence">odd</xsl:with-param>
          <xsl:with-param name="pageclass">back</xsl:with-param>
        </xsl:call-template>
      </fo:simple-page-master>

      <fo:simple-page-master master-name="back-even-draft"
                             page-width="{$page.width}"
                             page-height="{$page.height}"
                             margin-top="{$page.margin.top}"
                             margin-bottom="{$page.margin.bottom}">
        <xsl:attribute name="margin-{$direction.align.start}">
          <xsl:value-of select="$page.margin.outer"/>
	  <xsl:if test="$fop.extensions != 0">
	    <xsl:value-of select="concat(' - (',$title.margin.left,')')"/>
          </xsl:if>
        </xsl:attribute>
        <xsl:attribute name="margin-{$direction.align.end}">
          <xsl:value-of select="$page.margin.inner"/>
        </xsl:attribute>
        <xsl:if test="$axf.extensions != 0">
          <xsl:call-template name="axf-page-master-properties">
            <xsl:with-param name="page.master">back-even-draft</xsl:with-param>
          </xsl:call-template>
        </xsl:if>
        <fo:region-body margin-bottom="{$body.margin.bottom}"
                        margin-top="{$body.margin.top}"
                        column-gap="{$column.gap.back}"
                        column-count="{$column.count.back}">
          <xsl:attribute name="margin-{$direction.align.start}">
            <xsl:value-of select="$body.margin.outer"/>
          </xsl:attribute>
          <xsl:attribute name="margin-{$direction.align.end}">
            <xsl:value-of select="$body.margin.inner"/>
          </xsl:attribute>
          <xsl:if test="$draft.watermark.image != ''">
            <xsl:attribute name="background-image">
              <xsl:call-template name="fo-external-image">
                <xsl:with-param name="filename" select="$draft.watermark.image"/>
              </xsl:call-template>
            </xsl:attribute>
            <xsl:attribute name="background-attachment">fixed</xsl:attribute>
            <xsl:attribute name="background-repeat">no-repeat</xsl:attribute>
            <xsl:attribute name="background-position-horizontal">center</xsl:attribute>
            <xsl:attribute name="background-position-vertical">center</xsl:attribute>
          </xsl:if>
        </fo:region-body>
        <fo:region-before region-name="xsl-region-before-even"
                          extent="{$region.before.extent}"
                          precedence="{$region.before.precedence}"
                          display-align="before"/>
        <fo:region-after region-name="xsl-region-after-even"
                         extent="{$region.after.extent}"
                          precedence="{$region.after.precedence}"
                         display-align="after"/>
        <xsl:call-template name="region.outer">
          <xsl:with-param name="sequence">even</xsl:with-param>
          <xsl:with-param name="pageclass">back</xsl:with-param>
        </xsl:call-template>
        <xsl:call-template name="region.inner">
          <xsl:with-param name="sequence">even</xsl:with-param>
          <xsl:with-param name="pageclass">back</xsl:with-param>
        </xsl:call-template>
      </fo:simple-page-master>

      <!-- draft index pages -->
      <fo:simple-page-master master-name="index-first-draft"
                             page-width="{$page.width}"
                             page-height="{$page.height}"
                             margin-top="{$page.margin.top}"
                             margin-bottom="{$page.margin.bottom}">
        <xsl:attribute name="margin-{$direction.align.start}">
          <xsl:value-of select="$page.margin.inner"/>
	  <xsl:if test="$fop.extensions != 0">
	    <xsl:value-of select="concat(' - (',$title.margin.left,')')"/>
          </xsl:if>
        </xsl:attribute>
        <xsl:attribute name="margin-{$direction.align.end}">
          <xsl:value-of select="$page.margin.outer"/>
        </xsl:attribute>
        <xsl:if test="$axf.extensions != 0">
          <xsl:call-template name="axf-page-master-properties">
            <xsl:with-param name="page.master">index-first-draft</xsl:with-param>
          </xsl:call-template>
        </xsl:if>
        <fo:region-body margin-bottom="{$body.margin.bottom}"
                        margin-top="{$body.margin.top}"
                        column-gap="{$column.gap.index}"
                        column-count="{$column.count.index}">
          <xsl:attribute name="margin-{$direction.align.start}">
            <xsl:value-of select="$body.margin.inner"/>
          </xsl:attribute>
          <xsl:attribute name="margin-{$direction.align.end}">
            <xsl:value-of select="$body.margin.outer"/>
          </xsl:attribute>
          <xsl:if test="$draft.watermark.image != ''">
            <xsl:attribute name="background-image">
              <xsl:call-template name="fo-external-image">
                <xsl:with-param name="filename" select="$draft.watermark.image"/>
              </xsl:call-template>
            </xsl:attribute>
            <xsl:attribute name="background-attachment">fixed</xsl:attribute>
            <xsl:attribute name="background-repeat">no-repeat</xsl:attribute>
            <xsl:attribute name="background-position-horizontal">center</xsl:attribute>
            <xsl:attribute name="background-position-vertical">center</xsl:attribute>
          </xsl:if>
        </fo:region-body>
        <fo:region-before region-name="xsl-region-before-first"
                          extent="{$region.before.extent}"
                          precedence="{$region.before.precedence}"
                          display-align="before"/>
        <fo:region-after region-name="xsl-region-after-first"
                         extent="{$region.after.extent}"
                          precedence="{$region.after.precedence}"
                         display-align="after"/>
        <xsl:call-template name="region.inner">
          <xsl:with-param name="sequence">first</xsl:with-param>
          <xsl:with-param name="pageclass">index</xsl:with-param>
        </xsl:call-template>
        <xsl:call-template name="region.outer">
          <xsl:with-param name="sequence">first</xsl:with-param>
          <xsl:with-param name="pageclass">index</xsl:with-param>
        </xsl:call-template>
      </fo:simple-page-master>

      <fo:simple-page-master master-name="index-odd-draft"
                             page-width="{$page.width}"
                             page-height="{$page.height}"
                             margin-top="{$page.margin.top}"
                             margin-bottom="{$page.margin.bottom}">
        <xsl:attribute name="margin-{$direction.align.start}">
          <xsl:value-of select="$page.margin.inner"/>
	  <xsl:if test="$fop.extensions != 0">
	    <xsl:value-of select="concat(' - (',$title.margin.left,')')"/>
          </xsl:if>
        </xsl:attribute>
        <xsl:attribute name="margin-{$direction.align.end}">
          <xsl:value-of select="$page.margin.outer"/>
        </xsl:attribute>
        <xsl:if test="$axf.extensions != 0">
          <xsl:call-template name="axf-page-master-properties">
            <xsl:with-param name="page.master">index-odd-draft</xsl:with-param>
          </xsl:call-template>
        </xsl:if>
        <fo:region-body margin-bottom="{$body.margin.bottom}"
                        margin-top="{$body.margin.top}"
                        column-gap="{$column.gap.index}"
                        column-count="{$column.count.index}">
          <xsl:attribute name="margin-{$direction.align.start}">
            <xsl:value-of select="$body.margin.inner"/>
          </xsl:attribute>
          <xsl:attribute name="margin-{$direction.align.end}">
            <xsl:value-of select="$body.margin.outer"/>
          </xsl:attribute>
          <xsl:if test="$draft.watermark.image != ''">
            <xsl:attribute name="background-image">
              <xsl:call-template name="fo-external-image">
                <xsl:with-param name="filename" select="$draft.watermark.image"/>
              </xsl:call-template>
            </xsl:attribute>
            <xsl:attribute name="background-attachment">fixed</xsl:attribute>
            <xsl:attribute name="background-repeat">no-repeat</xsl:attribute>
            <xsl:attribute name="background-position-horizontal">center</xsl:attribute>
            <xsl:attribute name="background-position-vertical">center</xsl:attribute>
          </xsl:if>
        </fo:region-body>
        <fo:region-before region-name="xsl-region-before-odd"
                          extent="{$region.before.extent}"
                          precedence="{$region.before.precedence}"
                          display-align="before"/>
        <fo:region-after region-name="xsl-region-after-odd"
                         extent="{$region.after.extent}"
                          precedence="{$region.after.precedence}"
                         display-align="after"/>
        <xsl:call-template name="region.inner">
          <xsl:with-param name="sequence">odd</xsl:with-param>
          <xsl:with-param name="pageclass">index</xsl:with-param>
        </xsl:call-template>
        <xsl:call-template name="region.outer">
          <xsl:with-param name="sequence">odd</xsl:with-param>
          <xsl:with-param name="pageclass">index</xsl:with-param>
        </xsl:call-template>
      </fo:simple-page-master>

      <fo:simple-page-master master-name="index-even-draft"
                             page-width="{$page.width}"
                             page-height="{$page.height}"
                             margin-top="{$page.margin.top}"
                             margin-bottom="{$page.margin.bottom}">
        <xsl:attribute name="margin-{$direction.align.start}">
          <xsl:value-of select="$page.margin.outer"/>
	  <xsl:if test="$fop.extensions != 0">
	    <xsl:value-of select="concat(' - (',$title.margin.left,')')"/>
          </xsl:if>
        </xsl:attribute>
        <xsl:attribute name="margin-{$direction.align.end}">
          <xsl:value-of select="$page.margin.inner"/>
        </xsl:attribute>
        <xsl:if test="$axf.extensions != 0">
          <xsl:call-template name="axf-page-master-properties">
            <xsl:with-param name="page.master">index-even-draft</xsl:with-param>
          </xsl:call-template>
        </xsl:if>
        <fo:region-body margin-bottom="{$body.margin.bottom}"
                        margin-top="{$body.margin.top}"
                        column-gap="{$column.gap.index}"
                        column-count="{$column.count.index}">
          <xsl:attribute name="margin-{$direction.align.start}">
            <xsl:value-of select="$body.margin.outer"/>
          </xsl:attribute>
          <xsl:attribute name="margin-{$direction.align.end}">
            <xsl:value-of select="$body.margin.inner"/>
          </xsl:attribute>
          <xsl:if test="$draft.watermark.image != ''">
            <xsl:attribute name="background-image">
              <xsl:call-template name="fo-external-image">
                <xsl:with-param name="filename" select="$draft.watermark.image"/>
              </xsl:call-template>
            </xsl:attribute>
            <xsl:attribute name="background-attachment">fixed</xsl:attribute>
            <xsl:attribute name="background-repeat">no-repeat</xsl:attribute>
            <xsl:attribute name="background-position-horizontal">center</xsl:attribute>
            <xsl:attribute name="background-position-vertical">center</xsl:attribute>
          </xsl:if>
        </fo:region-body>
        <fo:region-before region-name="xsl-region-before-even"
                          extent="{$region.before.extent}"
                          precedence="{$region.before.precedence}"
                          display-align="before"/>
        <fo:region-after region-name="xsl-region-after-even"
                         extent="{$region.after.extent}"
                          precedence="{$region.after.precedence}"
                         display-align="after"/>
        <xsl:call-template name="region.outer">
          <xsl:with-param name="sequence">even</xsl:with-param>
          <xsl:with-param name="pageclass">index</xsl:with-param>
        </xsl:call-template>
        <xsl:call-template name="region.inner">
          <xsl:with-param name="sequence">even</xsl:with-param>
          <xsl:with-param name="pageclass">index</xsl:with-param>
        </xsl:call-template>
      </fo:simple-page-master>
    </xsl:if>

    <!-- setup for title page(s) -->
    <fo:page-sequence-master master-name="titlepage">
      <fo:repeatable-page-master-alternatives>
        <fo:conditional-page-master-reference master-reference="blank"
                                              blank-or-not-blank="blank"/>
        <xsl:if test="$force.blank.pages != 0">
          <fo:conditional-page-master-reference master-reference="titlepage-first"
                                                page-position="first"/>
        </xsl:if>
        <fo:conditional-page-master-reference master-reference="titlepage-odd"
                                              odd-or-even="odd"/>
        <fo:conditional-page-master-reference 
                                              odd-or-even="even">
          <xsl:attribute name="master-reference">
            <xsl:choose>
              <xsl:when test="$double.sided != 0">titlepage-even</xsl:when>
              <xsl:otherwise>titlepage-odd</xsl:otherwise>
            </xsl:choose>
          </xsl:attribute>
        </fo:conditional-page-master-reference>
      </fo:repeatable-page-master-alternatives>
    </fo:page-sequence-master>

    <!-- setup for lots -->
    <fo:page-sequence-master master-name="lot">
      <fo:repeatable-page-master-alternatives>
        <fo:conditional-page-master-reference master-reference="blank"
                                              blank-or-not-blank="blank"/>
        <xsl:if test="$force.blank.pages != 0">
          <fo:conditional-page-master-reference master-reference="lot-first"
                                                page-position="first"/>
        </xsl:if>
        <fo:conditional-page-master-reference master-reference="lot-odd"
                                              odd-or-even="odd"/>
        <fo:conditional-page-master-reference 
                                              odd-or-even="even">
          <xsl:attribute name="master-reference">
            <xsl:choose>
              <xsl:when test="$double.sided != 0">lot-even</xsl:when>
              <xsl:otherwise>lot-odd</xsl:otherwise>
            </xsl:choose>
          </xsl:attribute>
        </fo:conditional-page-master-reference>
      </fo:repeatable-page-master-alternatives>
    </fo:page-sequence-master>

    <!-- setup front matter -->
    <fo:page-sequence-master master-name="front">
      <fo:repeatable-page-master-alternatives>
        <fo:conditional-page-master-reference master-reference="blank"
                                              blank-or-not-blank="blank"/>
        <xsl:if test="$force.blank.pages != 0">
          <fo:conditional-page-master-reference master-reference="front-first"
                                                page-position="first"/>
        </xsl:if>
        <fo:conditional-page-master-reference master-reference="front-odd"
                                              odd-or-even="odd"/>
        <fo:conditional-page-master-reference 
                                              odd-or-even="even">
          <xsl:attribute name="master-reference">
            <xsl:choose>
              <xsl:when test="$double.sided != 0">front-even</xsl:when>
              <xsl:otherwise>front-odd</xsl:otherwise>
            </xsl:choose>
          </xsl:attribute>
        </fo:conditional-page-master-reference>
      </fo:repeatable-page-master-alternatives>
    </fo:page-sequence-master>

    <!-- setup for body pages -->
    <fo:page-sequence-master master-name="body">
      <fo:repeatable-page-master-alternatives>
        <fo:conditional-page-master-reference master-reference="blank"
                                              blank-or-not-blank="blank"/>
        <xsl:if test="$force.blank.pages != 0">
          <fo:conditional-page-master-reference master-reference="body-first"
                                                page-position="first"/>
        </xsl:if>
        <fo:conditional-page-master-reference master-reference="body-odd"
                                              odd-or-even="odd"/>
        <fo:conditional-page-master-reference 
                                              odd-or-even="even">
          <xsl:attribute name="master-reference">
            <xsl:choose>
              <xsl:when test="$double.sided != 0">body-even</xsl:when>
              <xsl:otherwise>body-odd</xsl:otherwise>
            </xsl:choose>
          </xsl:attribute>
        </fo:conditional-page-master-reference>
      </fo:repeatable-page-master-alternatives>
    </fo:page-sequence-master>

    <!-- setup back matter -->
    <fo:page-sequence-master master-name="back">
      <fo:repeatable-page-master-alternatives>
        <fo:conditional-page-master-reference master-reference="blank"
                                              blank-or-not-blank="blank"/>
        <xsl:if test="$force.blank.pages != 0">
          <fo:conditional-page-master-reference master-reference="back-first"
                                                page-position="first"/>
        </xsl:if>
        <fo:conditional-page-master-reference master-reference="back-odd"
                                              odd-or-even="odd"/>
        <fo:conditional-page-master-reference 
                                              odd-or-even="even">
          <xsl:attribute name="master-reference">
            <xsl:choose>
              <xsl:when test="$double.sided != 0">back-even</xsl:when>
              <xsl:otherwise>back-odd</xsl:otherwise>
            </xsl:choose>
          </xsl:attribute>
        </fo:conditional-page-master-reference>
      </fo:repeatable-page-master-alternatives>
    </fo:page-sequence-master>

    <!-- setup back matter -->
    <fo:page-sequence-master master-name="index">
      <fo:repeatable-page-master-alternatives>
        <fo:conditional-page-master-reference master-reference="blank"
                                              blank-or-not-blank="blank"/>
        <xsl:if test="$force.blank.pages != 0">
          <fo:conditional-page-master-reference master-reference="index-first"
                                                page-position="first"/>
        </xsl:if>
        <fo:conditional-page-master-reference master-reference="index-odd"
                                              odd-or-even="odd"/>
        <fo:conditional-page-master-reference 
                                              odd-or-even="even">
          <xsl:attribute name="master-reference">
            <xsl:choose>
              <xsl:when test="$double.sided != 0">index-even</xsl:when>
              <xsl:otherwise>index-odd</xsl:otherwise>
            </xsl:choose>
          </xsl:attribute>
        </fo:conditional-page-master-reference>
      </fo:repeatable-page-master-alternatives>
    </fo:page-sequence-master>

    <xsl:if test="$draft.mode != 'no'">
      <!-- setup for draft title page(s) -->
      <fo:page-sequence-master master-name="titlepage-draft">
        <fo:repeatable-page-master-alternatives>
          <fo:conditional-page-master-reference master-reference="blank-draft"
                                                blank-or-not-blank="blank"/>
          <xsl:if test="$force.blank.pages != 0">
            <fo:conditional-page-master-reference master-reference="titlepage-first-draft"
                                                  page-position="first"/>
          </xsl:if>
          <fo:conditional-page-master-reference master-reference="titlepage-odd-draft"
                                                odd-or-even="odd"/>
          <fo:conditional-page-master-reference 
                                                odd-or-even="even">
            <xsl:attribute name="master-reference">
              <xsl:choose>
                <xsl:when test="$double.sided != 0">titlepage-even-draft</xsl:when>
                <xsl:otherwise>titlepage-odd-draft</xsl:otherwise>
              </xsl:choose>
            </xsl:attribute>
          </fo:conditional-page-master-reference>
        </fo:repeatable-page-master-alternatives>
      </fo:page-sequence-master>

      <!-- setup for draft lots -->
      <fo:page-sequence-master master-name="lot-draft">
        <fo:repeatable-page-master-alternatives>
          <fo:conditional-page-master-reference master-reference="blank-draft"
                                                blank-or-not-blank="blank"/>
          <xsl:if test="$force.blank.pages != 0">
            <fo:conditional-page-master-reference master-reference="lot-first-draft"
                                                  page-position="first"/>
          </xsl:if>
          <fo:conditional-page-master-reference master-reference="lot-odd-draft"
                                                odd-or-even="odd"/>
          <fo:conditional-page-master-reference 
                                                odd-or-even="even">
            <xsl:attribute name="master-reference">
              <xsl:choose>
                <xsl:when test="$double.sided != 0">lot-even-draft</xsl:when>
                <xsl:otherwise>lot-odd-draft</xsl:otherwise>
              </xsl:choose>
            </xsl:attribute>
          </fo:conditional-page-master-reference>
        </fo:repeatable-page-master-alternatives>
      </fo:page-sequence-master>

      <!-- setup draft front matter -->
      <fo:page-sequence-master master-name="front-draft">
        <fo:repeatable-page-master-alternatives>
          <fo:conditional-page-master-reference master-reference="blank-draft"
                                                blank-or-not-blank="blank"/>
          <xsl:if test="$force.blank.pages != 0">
            <fo:conditional-page-master-reference master-reference="front-first-draft"
                                                  page-position="first"/>
          </xsl:if>
          <fo:conditional-page-master-reference master-reference="front-odd-draft"
                                                odd-or-even="odd"/>
          <fo:conditional-page-master-reference 
                                                odd-or-even="even">
            <xsl:attribute name="master-reference">
              <xsl:choose>
                <xsl:when test="$double.sided != 0">front-even-draft</xsl:when>
                <xsl:otherwise>front-odd-draft</xsl:otherwise>
              </xsl:choose>
            </xsl:attribute>
          </fo:conditional-page-master-reference>
        </fo:repeatable-page-master-alternatives>
      </fo:page-sequence-master>

      <!-- setup for draft body pages -->
      <fo:page-sequence-master master-name="body-draft">
        <fo:repeatable-page-master-alternatives>
          <fo:conditional-page-master-reference master-reference="blank-draft"
                                                blank-or-not-blank="blank"/>
          <xsl:if test="$force.blank.pages != 0">
            <fo:conditional-page-master-reference master-reference="body-first-draft"
                                                  page-position="first"/>
          </xsl:if>
          <fo:conditional-page-master-reference master-reference="body-odd-draft"
                                                odd-or-even="odd"/>
          <fo:conditional-page-master-reference 
                                                odd-or-even="even">
            <xsl:attribute name="master-reference">
              <xsl:choose>
                <xsl:when test="$double.sided != 0">body-even-draft</xsl:when>
                <xsl:otherwise>body-odd-draft</xsl:otherwise>
              </xsl:choose>
            </xsl:attribute>
          </fo:conditional-page-master-reference>
        </fo:repeatable-page-master-alternatives>
      </fo:page-sequence-master>

      <!-- setup draft back matter -->
      <fo:page-sequence-master master-name="back-draft">
        <fo:repeatable-page-master-alternatives>
          <fo:conditional-page-master-reference master-reference="blank-draft"
                                                blank-or-not-blank="blank"/>
          <xsl:if test="$force.blank.pages != 0">
            <fo:conditional-page-master-reference master-reference="back-first-draft"
                                                  page-position="first"/>
          </xsl:if>
          <fo:conditional-page-master-reference master-reference="back-odd-draft"
                                                odd-or-even="odd"/>
          <fo:conditional-page-master-reference 
                                                odd-or-even="even">
            <xsl:attribute name="master-reference">
              <xsl:choose>
                <xsl:when test="$double.sided != 0">back-even-draft</xsl:when>
                <xsl:otherwise>back-odd-draft</xsl:otherwise>
              </xsl:choose>
            </xsl:attribute>
          </fo:conditional-page-master-reference>
        </fo:repeatable-page-master-alternatives>
      </fo:page-sequence-master>

      <!-- setup draft index pages -->
      <fo:page-sequence-master master-name="index-draft">
        <fo:repeatable-page-master-alternatives>
          <fo:conditional-page-master-reference master-reference="blank-draft"
                                                blank-or-not-blank="blank"/>
          <xsl:if test="$force.blank.pages != 0">
            <fo:conditional-page-master-reference master-reference="index-first-draft"
                                                  page-position="first"/>
          </xsl:if>
          <fo:conditional-page-master-reference master-reference="index-odd-draft"
                                                odd-or-even="odd"/>
          <fo:conditional-page-master-reference 
                                                odd-or-even="even">
            <xsl:attribute name="master-reference">
              <xsl:choose>
                <xsl:when test="$double.sided != 0">index-even-draft</xsl:when>
                <xsl:otherwise>index-odd-draft</xsl:otherwise>
              </xsl:choose>
            </xsl:attribute>
          </fo:conditional-page-master-reference>
        </fo:repeatable-page-master-alternatives>
      </fo:page-sequence-master>
    </xsl:if>

    <xsl:call-template name="user.pagemasters"/>

    </fo:layout-master-set>
</xsl:template>

<!-- ==================================================================== -->

<xsl:template name="user.pagemasters"/> <!-- intentionally empty -->

<!-- ==================================================================== -->

<xsl:template name="select.pagemaster">
  <xsl:param name="element" select="local-name(.)"/>
  <xsl:param name="pageclass" select="''"/>

  <xsl:variable name="pagemaster">
    <xsl:choose>
      <xsl:when test="$pageclass != ''">
        <xsl:value-of select="$pageclass"/>
      </xsl:when>
      <xsl:when test="$pageclass = 'lot'">lot</xsl:when>
      <xsl:when test="$element = 'dedication'">front</xsl:when>
      <xsl:when test="$element = 'acknowledgements'">front</xsl:when>
      <xsl:when test="$element = 'preface'">front</xsl:when>
      <xsl:when test="$element = 'appendix'">back</xsl:when>
      <xsl:when test="$element = 'glossary'">back</xsl:when>
      <xsl:when test="$element = 'bibliography'">back</xsl:when>
      <xsl:when test="$element = 'index'">index</xsl:when>
      <xsl:when test="$element = 'colophon'">back</xsl:when>
      <xsl:otherwise>body</xsl:otherwise>
    </xsl:choose>

    <xsl:choose>
      <xsl:when test="$draft.mode = 'yes'">
        <xsl:text>-draft</xsl:text>
      </xsl:when>
      <xsl:when test="$draft.mode = 'no'">
        <!-- nop -->
      </xsl:when>
      <xsl:when test="ancestor-or-self::*[@status][1]/@status = 'draft'">
        <xsl:text>-draft</xsl:text>
      </xsl:when>
      <xsl:otherwise>
        <!-- nop -->
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:call-template name="select.user.pagemaster">
    <xsl:with-param name="element" select="$element"/>
    <xsl:with-param name="pageclass" select="$pageclass"/>
    <xsl:with-param name="default-pagemaster" select="$pagemaster"/>
  </xsl:call-template>
</xsl:template>

<xsl:template name="select.user.pagemaster">
  <xsl:param name="element"/>
  <xsl:param name="pageclass"/>
  <xsl:param name="default-pagemaster"/>

  <!-- by default, return the default. But if you've created your own
       pagemasters in user.pagemasters, you might want to select one here. -->
  <xsl:value-of select="$default-pagemaster"/>
</xsl:template>

<!-- ==================================================================== -->

<xsl:template name="head.sep.rule">
  <xsl:param name="pageclass"/>
  <xsl:param name="sequence"/>
  <xsl:param name="gentext-key"/>

  <xsl:if test="$header.rule != 0">
    <xsl:attribute name="border-bottom-width">0.5pt</xsl:attribute>
    <xsl:attribute name="border-bottom-style">solid</xsl:attribute>
    <xsl:attribute name="border-bottom-color">black</xsl:attribute>
  </xsl:if>
</xsl:template>

<xsl:template name="foot.sep.rule">
  <xsl:param name="pageclass"/>
  <xsl:param name="sequence"/>
  <xsl:param name="gentext-key"/>

  <xsl:if test="$footer.rule != 0">
    <xsl:attribute name="border-top-width">0.5pt</xsl:attribute>
    <xsl:attribute name="border-top-style">solid</xsl:attribute>
    <xsl:attribute name="border-top-color">black</xsl:attribute>
  </xsl:if>
</xsl:template>

<!-- ==================================================================== -->

<xsl:template match="*" mode="running.head.mode">
  <xsl:param name="master-reference" select="'unknown'"/>
  <xsl:param name="gentext-key" select="local-name(.)"/>

  <!-- remove -draft from reference -->
  <xsl:variable name="pageclass">
    <xsl:choose>
      <xsl:when test="contains($master-reference, '-draft')">
        <xsl:value-of select="substring-before($master-reference, '-draft')"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:value-of select="$master-reference"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <fo:static-content flow-name="xsl-region-before-first">
    <fo:block xsl:use-attribute-sets="header.content.properties">
      <xsl:call-template name="header.table">
        <xsl:with-param name="pageclass" select="$pageclass"/>
        <xsl:with-param name="sequence" select="'first'"/>
        <xsl:with-param name="gentext-key" select="$gentext-key"/>
      </xsl:call-template>
    </fo:block>
  </fo:static-content>

  <fo:static-content flow-name="xsl-region-before-odd">
    <fo:block xsl:use-attribute-sets="header.content.properties">
      <xsl:call-template name="header.table">
        <xsl:with-param name="pageclass" select="$pageclass"/>
        <xsl:with-param name="sequence" select="'odd'"/>
        <xsl:with-param name="gentext-key" select="$gentext-key"/>
      </xsl:call-template>
    </fo:block>
  </fo:static-content>

  <fo:static-content flow-name="xsl-region-before-even">
    <fo:block xsl:use-attribute-sets="header.content.properties">
      <xsl:call-template name="header.table">
        <xsl:with-param name="pageclass" select="$pageclass"/>
        <xsl:with-param name="sequence" select="'even'"/>
        <xsl:with-param name="gentext-key" select="$gentext-key"/>
      </xsl:call-template>
    </fo:block>
  </fo:static-content>

  <fo:static-content flow-name="xsl-region-before-blank">
    <fo:block xsl:use-attribute-sets="header.content.properties">
      <xsl:call-template name="header.table">
        <xsl:with-param name="pageclass" select="$pageclass"/>
        <xsl:with-param name="sequence" select="'blank'"/>
        <xsl:with-param name="gentext-key" select="$gentext-key"/>
      </xsl:call-template>
    </fo:block>
  </fo:static-content>

  <xsl:call-template name="footnote-separator"/>

  <xsl:if test="$fop.extensions = 0 and $fop1.extensions = 0">
    <xsl:call-template name="blank.page.content"/>
  </xsl:if>

  <xsl:apply-templates select="." mode="region.inner.mode">
    <xsl:with-param name="master-reference" select="$master-reference"/>
    <xsl:with-param name="gentext-key" select="$gentext-key"/>
  </xsl:apply-templates>

  <xsl:apply-templates select="." mode="region.outer.mode">
    <xsl:with-param name="master-reference" select="$master-reference"/>
    <xsl:with-param name="gentext-key" select="$gentext-key"/>
  </xsl:apply-templates>

</xsl:template>

<xsl:template name="footnote-separator">
  <fo:static-content flow-name="xsl-footnote-separator">
    <fo:block>
      <fo:leader xsl:use-attribute-sets="footnote.sep.leader.properties"/>
    </fo:block>
  </fo:static-content>
</xsl:template>

<xsl:template name="blank.page.content">
  <fo:static-content flow-name="blank-body">
    <fo:block text-align="center"/>
  </fo:static-content>
</xsl:template>

<xsl:template name="running.side.content">
  <xsl:apply-templates select="." mode="region.inner.mode"/>
</xsl:template>

<xsl:template name="header.table">
  <xsl:param name="pageclass" select="''"/>
  <xsl:param name="sequence" select="''"/>
  <xsl:param name="gentext-key" select="''"/>

  <!-- default is a single table style for all headers -->
  <!-- Customize it for different page classes or sequence location -->

  <xsl:choose>
      <xsl:when test="$pageclass = 'index'">
          <xsl:attribute name="margin-{$direction.align.start}">0pt</xsl:attribute>
      </xsl:when>
  </xsl:choose>

  <xsl:variable name="column1">
    <xsl:choose>
      <xsl:when test="$double.sided = 0">1</xsl:when>
      <xsl:when test="$sequence = 'first' or $sequence = 'odd'">1</xsl:when>
      <xsl:otherwise>3</xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:variable name="column3">
    <xsl:choose>
      <xsl:when test="$double.sided = 0">3</xsl:when>
      <xsl:when test="$sequence = 'first' or $sequence = 'odd'">3</xsl:when>
      <xsl:otherwise>1</xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:variable name="candidate">
    <fo:table xsl:use-attribute-sets="header.table.properties">
      <xsl:call-template name="head.sep.rule">
        <xsl:with-param name="pageclass" select="$pageclass"/>
        <xsl:with-param name="sequence" select="$sequence"/>
        <xsl:with-param name="gentext-key" select="$gentext-key"/>
      </xsl:call-template>

      <fo:table-column column-number="1">
        <xsl:attribute name="column-width">
          <xsl:text>proportional-column-width(</xsl:text>
          <xsl:call-template name="header.footer.width">
            <xsl:with-param name="location">header</xsl:with-param>
            <xsl:with-param name="position" select="$column1"/>
            <xsl:with-param name="pageclass" select="$pageclass"/>
            <xsl:with-param name="sequence" select="$sequence"/>
            <xsl:with-param name="gentext-key" select="$gentext-key"/>
          </xsl:call-template>
          <xsl:text>)</xsl:text>
        </xsl:attribute>
      </fo:table-column>
      <fo:table-column column-number="2">
        <xsl:attribute name="column-width">
          <xsl:text>proportional-column-width(</xsl:text>
          <xsl:call-template name="header.footer.width">
            <xsl:with-param name="location">header</xsl:with-param>
            <xsl:with-param name="position" select="2"/>
            <xsl:with-param name="pageclass" select="$pageclass"/>
            <xsl:with-param name="sequence" select="$sequence"/>
            <xsl:with-param name="gentext-key" select="$gentext-key"/>
          </xsl:call-template>
          <xsl:text>)</xsl:text>
        </xsl:attribute>
      </fo:table-column>
      <fo:table-column column-number="3">
        <xsl:attribute name="column-width">
          <xsl:text>proportional-column-width(</xsl:text>
          <xsl:call-template name="header.footer.width">
            <xsl:with-param name="location">header</xsl:with-param>
            <xsl:with-param name="position" select="$column3"/>
            <xsl:with-param name="pageclass" select="$pageclass"/>
            <xsl:with-param name="sequence" select="$sequence"/>
            <xsl:with-param name="gentext-key" select="$gentext-key"/>
          </xsl:call-template>
          <xsl:text>)</xsl:text>
        </xsl:attribute>
      </fo:table-column>

      <fo:table-body>
        <fo:table-row>
          <xsl:attribute name="block-progression-dimension.minimum">
            <xsl:value-of select="$header.table.height"/>
          </xsl:attribute>
          <fo:table-cell text-align="start"
                         display-align="before">
            <xsl:if test="$fop.extensions = 0">
              <xsl:attribute name="relative-align">baseline</xsl:attribute>
            </xsl:if>
            <fo:block>
              <xsl:call-template name="header.content">
                <xsl:with-param name="pageclass" select="$pageclass"/>
                <xsl:with-param name="sequence" select="$sequence"/>
                <xsl:with-param name="position" select="$direction.align.start"/>
                <xsl:with-param name="gentext-key" select="$gentext-key"/>
              </xsl:call-template>
            </fo:block>
          </fo:table-cell>
          <fo:table-cell text-align="center"
                         display-align="before">
            <xsl:if test="$fop.extensions = 0">
              <xsl:attribute name="relative-align">baseline</xsl:attribute>
            </xsl:if>
            <fo:block>
              <xsl:call-template name="header.content">
                <xsl:with-param name="pageclass" select="$pageclass"/>
                <xsl:with-param name="sequence" select="$sequence"/>
                <xsl:with-param name="position" select="'center'"/>
                <xsl:with-param name="gentext-key" select="$gentext-key"/>
              </xsl:call-template>
            </fo:block>
          </fo:table-cell>
          <fo:table-cell text-align="right"
                         display-align="before">
            <xsl:if test="$fop.extensions = 0">
              <xsl:attribute name="relative-align">baseline</xsl:attribute>
            </xsl:if>
            <fo:block>
              <xsl:call-template name="header.content">
                <xsl:with-param name="pageclass" select="$pageclass"/>
                <xsl:with-param name="sequence" select="$sequence"/>
                <xsl:with-param name="position" select="$direction.align.end"/>
                <xsl:with-param name="gentext-key" select="$gentext-key"/>
              </xsl:call-template>
            </fo:block>
          </fo:table-cell>
        </fo:table-row>
      </fo:table-body>
    </fo:table>
  </xsl:variable>

  <!-- Really output a header? -->
  <xsl:choose>
    <xsl:when test="$pageclass = 'titlepage' and $gentext-key = 'book'
                    and $sequence='first'">
      <!-- no, book titlepages have no headers at all -->
    </xsl:when>
    <xsl:when test="$sequence = 'blank' and $headers.on.blank.pages = 0">
      <!-- no output -->
    </xsl:when>
    <xsl:otherwise>
      <xsl:copy-of select="$candidate"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template name="header.content">
  <xsl:param name="pageclass" select="''"/>
  <xsl:param name="sequence" select="''"/>
  <xsl:param name="position" select="''"/>
  <xsl:param name="gentext-key" select="''"/>

<!--
  <fo:block>
    <xsl:value-of select="$pageclass"/>
    <xsl:text>, </xsl:text>
    <xsl:value-of select="$sequence"/>
    <xsl:text>, </xsl:text>
    <xsl:value-of select="$position"/>
    <xsl:text>, </xsl:text>
    <xsl:value-of select="$gentext-key"/>
  </fo:block>
-->

  <fo:block>

    <!-- sequence can be odd, even, first, blank -->
    <!-- position can be left, center, right -->
    <xsl:choose>
      <xsl:when test="$sequence = 'blank'">
        <!-- nothing -->
      </xsl:when>

      <xsl:when test="$position='left'">
        <!-- Same for odd, even, empty, and blank sequences -->
        <xsl:call-template name="draft.text"/>
      </xsl:when>

      <xsl:when test="($sequence='odd' or $sequence='even') and $position='center'">
        <xsl:if test="$pageclass != 'titlepage'">
          <xsl:choose>
            <xsl:when test="ancestor::book and ($double.sided != 0)">
              <fo:retrieve-marker retrieve-class-name="section.head.marker"
                                  retrieve-position="first-including-carryover"
                                  retrieve-boundary="page-sequence"/>
            </xsl:when>
            <xsl:otherwise>
              <xsl:apply-templates select="." mode="titleabbrev.markup"/>
            </xsl:otherwise>
          </xsl:choose>
        </xsl:if>
      </xsl:when>

      <xsl:when test="$position='center'">
        <!-- nothing for empty and blank sequences -->
      </xsl:when>

      <xsl:when test="$position='right'">
        <!-- Same for odd, even, empty, and blank sequences -->
        <xsl:call-template name="draft.text"/>
      </xsl:when>

      <xsl:when test="$sequence = 'first'">
        <!-- nothing for first pages -->
      </xsl:when>

      <xsl:when test="$sequence = 'blank'">
        <!-- nothing for blank pages -->
      </xsl:when>
    </xsl:choose>
  </fo:block>
</xsl:template>

<xsl:template name="header.footer.width">
  <xsl:param name="location" select="'header'"/>
  <xsl:param name="position" select="1"/>
  <xsl:param name="pageclass" select="''"/>
  <xsl:param name="sequence" select="''"/>
  <xsl:param name="gentext-key" select="''"/>

  <!-- The location param is either 'header' or 'footer'.
       The position param is one of '1', '2', or '3' to indicate
       which column of the header or footer table. -->
       
  <!-- The pageclass, sequence, and gentext-key values are passed
       from the header.table or footer.table template.  They are
       not currently used, but are made available here
       for customization of this template. -->

  <xsl:variable name="width.set">
    <xsl:choose>
      <xsl:when test="$location = 'header'">
        <xsl:value-of select="normalize-space($header.column.widths)"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:value-of select="normalize-space($footer.column.widths)"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>


  <xsl:variable name="width">
    <xsl:choose>
      <xsl:when test="$position = 1">
        <xsl:value-of select="substring-before($width.set, ' ')"/>
      </xsl:when>
      <xsl:when test="$position = 2">
        <xsl:value-of select="substring-before(substring-after($width.set, ' '), ' ')"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:value-of select="substring-after(substring-after($width.set, ' '), ' ')"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <!-- Make sure it is a number -->
  <xsl:choose>
    <xsl:when test = "$width = number($width)">
      <xsl:value-of select="$width"/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:message>Error: value in <xsl:value-of select="$location"/>.column.widths at position <xsl:value-of select="$position"/> is not a number.</xsl:message>
      <xsl:text>1</xsl:text>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template name="draft.text">
  <xsl:choose>
    <xsl:when test="$draft.mode = 'yes'">
      <xsl:call-template name="gentext">
        <xsl:with-param name="key" select="'Draft'"/>
      </xsl:call-template>
    </xsl:when>
    <xsl:when test="$draft.mode = 'no'">
      <!-- nop -->
    </xsl:when>
    <xsl:when test="ancestor-or-self::*[@status][1]/@status = 'draft'">
      <xsl:call-template name="gentext">
        <xsl:with-param name="key" select="'Draft'"/>
      </xsl:call-template>
    </xsl:when>
    <xsl:otherwise>
      <!-- nop -->
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<!-- ==================================================================== -->

<xsl:template match="*" mode="running.foot.mode">
  <xsl:param name="master-reference" select="'unknown'"/>
  <xsl:param name="gentext-key" select="local-name(.)"/>

  <!-- remove -draft from reference -->
  <xsl:variable name="pageclass">
    <xsl:choose>
      <xsl:when test="contains($master-reference, '-draft')">
        <xsl:value-of select="substring-before($master-reference, '-draft')"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:value-of select="$master-reference"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <fo:static-content flow-name="xsl-region-after-first">
    <fo:block xsl:use-attribute-sets="footer.content.properties">
      <xsl:call-template name="footer.table">
        <xsl:with-param name="pageclass" select="$pageclass"/>
        <xsl:with-param name="sequence" select="'first'"/>
        <xsl:with-param name="gentext-key" select="$gentext-key"/>
      </xsl:call-template>
    </fo:block>
  </fo:static-content>

  <fo:static-content flow-name="xsl-region-after-odd">
    <fo:block xsl:use-attribute-sets="footer.content.properties">
      <xsl:call-template name="footer.table">
        <xsl:with-param name="pageclass" select="$pageclass"/>
        <xsl:with-param name="sequence" select="'odd'"/>
        <xsl:with-param name="gentext-key" select="$gentext-key"/>
      </xsl:call-template>
    </fo:block>
  </fo:static-content>

  <fo:static-content flow-name="xsl-region-after-even">
    <fo:block xsl:use-attribute-sets="footer.content.properties">
      <xsl:call-template name="footer.table">
        <xsl:with-param name="pageclass" select="$pageclass"/>
        <xsl:with-param name="sequence" select="'even'"/>
        <xsl:with-param name="gentext-key" select="$gentext-key"/>
      </xsl:call-template>
    </fo:block>
  </fo:static-content>

  <fo:static-content flow-name="xsl-region-after-blank">
    <fo:block xsl:use-attribute-sets="footer.content.properties">
      <xsl:call-template name="footer.table">
        <xsl:with-param name="pageclass" select="$pageclass"/>
        <xsl:with-param name="sequence" select="'blank'"/>
        <xsl:with-param name="gentext-key" select="$gentext-key"/>
      </xsl:call-template>
    </fo:block>
  </fo:static-content>
</xsl:template>

<xsl:template name="footer.table">
  <xsl:param name="pageclass" select="''"/>
  <xsl:param name="sequence" select="''"/>
  <xsl:param name="gentext-key" select="''"/>

  <!-- default is a single table style for all footers -->
  <!-- Customize it for different page classes or sequence location -->

  <xsl:choose>
      <xsl:when test="$pageclass = 'index'">
          <xsl:attribute name="margin-{$direction.align.start}">0pt</xsl:attribute>
      </xsl:when>
  </xsl:choose>

  <xsl:variable name="column1">
    <xsl:choose>
      <xsl:when test="$double.sided = 0">1</xsl:when>
      <xsl:when test="$sequence = 'first' or $sequence = 'odd'">1</xsl:when>
      <xsl:otherwise>3</xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:variable name="column3">
    <xsl:choose>
      <xsl:when test="$double.sided = 0">3</xsl:when>
      <xsl:when test="$sequence = 'first' or $sequence = 'odd'">3</xsl:when>
      <xsl:otherwise>1</xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:variable name="candidate">
    <fo:table xsl:use-attribute-sets="footer.table.properties">
      <xsl:call-template name="foot.sep.rule">
        <xsl:with-param name="pageclass" select="$pageclass"/>
        <xsl:with-param name="sequence" select="$sequence"/>
        <xsl:with-param name="gentext-key" select="$gentext-key"/>
      </xsl:call-template>
      <fo:table-column column-number="1">
        <xsl:attribute name="column-width">
          <xsl:text>proportional-column-width(</xsl:text>
          <xsl:call-template name="header.footer.width">
            <xsl:with-param name="location">footer</xsl:with-param>
            <xsl:with-param name="position" select="$column1"/>
            <xsl:with-param name="pageclass" select="$pageclass"/>
            <xsl:with-param name="sequence" select="$sequence"/>
            <xsl:with-param name="gentext-key" select="$gentext-key"/>
          </xsl:call-template>
          <xsl:text>)</xsl:text>
        </xsl:attribute>
      </fo:table-column>
      <fo:table-column column-number="2">
        <xsl:attribute name="column-width">
          <xsl:text>proportional-column-width(</xsl:text>
          <xsl:call-template name="header.footer.width">
            <xsl:with-param name="location">footer</xsl:with-param>
            <xsl:with-param name="position" select="2"/>
            <xsl:with-param name="pageclass" select="$pageclass"/>
            <xsl:with-param name="sequence" select="$sequence"/>
            <xsl:with-param name="gentext-key" select="$gentext-key"/>
          </xsl:call-template>
          <xsl:text>)</xsl:text>
        </xsl:attribute>
      </fo:table-column>
      <fo:table-column column-number="3">
        <xsl:attribute name="column-width">
          <xsl:text>proportional-column-width(</xsl:text>
          <xsl:call-template name="header.footer.width">
            <xsl:with-param name="location">footer</xsl:with-param>
            <xsl:with-param name="position" select="$column3"/>
            <xsl:with-param name="pageclass" select="$pageclass"/>
            <xsl:with-param name="sequence" select="$sequence"/>
            <xsl:with-param name="gentext-key" select="$gentext-key"/>
          </xsl:call-template>
          <xsl:text>)</xsl:text>
        </xsl:attribute>
      </fo:table-column>

      <fo:table-body>
        <fo:table-row>
          <xsl:attribute name="block-progression-dimension.minimum">
            <xsl:value-of select="$footer.table.height"/>
          </xsl:attribute>
          <fo:table-cell text-align="start"
                         display-align="after">
            <xsl:if test="$fop.extensions = 0">
              <xsl:attribute name="relative-align">baseline</xsl:attribute>
            </xsl:if>
            <fo:block>
              <xsl:call-template name="footer.content">
                <xsl:with-param name="pageclass" select="$pageclass"/>
                <xsl:with-param name="sequence" select="$sequence"/>
                <xsl:with-param name="position" select="$direction.align.start"/>
                <xsl:with-param name="gentext-key" select="$gentext-key"/>
              </xsl:call-template>
            </fo:block>
          </fo:table-cell>
          <fo:table-cell text-align="center"
                         display-align="after">
            <xsl:if test="$fop.extensions = 0">
              <xsl:attribute name="relative-align">baseline</xsl:attribute>
            </xsl:if>
            <fo:block>
              <xsl:call-template name="footer.content">
                <xsl:with-param name="pageclass" select="$pageclass"/>
                <xsl:with-param name="sequence" select="$sequence"/>
                <xsl:with-param name="position" select="'center'"/>
                <xsl:with-param name="gentext-key" select="$gentext-key"/>
              </xsl:call-template>
            </fo:block>
          </fo:table-cell>
          <fo:table-cell text-align="end"
                         display-align="after">
            <xsl:if test="$fop.extensions = 0">
              <xsl:attribute name="relative-align">baseline</xsl:attribute>
            </xsl:if>
            <fo:block>
              <xsl:call-template name="footer.content">
                <xsl:with-param name="pageclass" select="$pageclass"/>
                <xsl:with-param name="sequence" select="$sequence"/>
                <xsl:with-param name="position" select="$direction.align.end"/>
                <xsl:with-param name="gentext-key" select="$gentext-key"/>
              </xsl:call-template>
            </fo:block>
          </fo:table-cell>
        </fo:table-row>
      </fo:table-body>
    </fo:table>
  </xsl:variable>

  <!-- Really output a footer? -->
  <xsl:choose>
    <xsl:when test="$pageclass='titlepage' and $gentext-key='book'
                    and $sequence='first'">
      <!-- no, book titlepages have no footers at all -->
    </xsl:when>
    <xsl:when test="$sequence = 'blank' and $footers.on.blank.pages = 0">
      <!-- no output -->
    </xsl:when>
    <xsl:otherwise>
      <xsl:copy-of select="$candidate"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template name="footer.content">
  <xsl:param name="pageclass" select="''"/>
  <xsl:param name="sequence" select="''"/>
  <xsl:param name="position" select="''"/>
  <xsl:param name="gentext-key" select="''"/>

<!--
  <fo:block>
    <xsl:value-of select="$pageclass"/>
    <xsl:text>, </xsl:text>
    <xsl:value-of select="$sequence"/>
    <xsl:text>, </xsl:text>
    <xsl:value-of select="$position"/>
    <xsl:text>, </xsl:text>
    <xsl:value-of select="$gentext-key"/>
  </fo:block>
-->

  <fo:block>
    <!-- pageclass can be front, body, back -->
    <!-- sequence can be odd, even, first, blank -->
    <!-- position can be left, center, right -->
    <xsl:choose>
      <xsl:when test="$pageclass = 'titlepage'">
        <!-- nop; no footer on title pages -->
      </xsl:when>

      <xsl:when test="$double.sided != 0 and $sequence = 'even'
                      and $position='left'">
        <fo:page-number/>
      </xsl:when>

      <xsl:when test="$double.sided != 0 and ($sequence = 'odd' or $sequence = 'first')
                      and $position='right'">
        <fo:page-number/>
      </xsl:when>

      <xsl:when test="$double.sided = 0 and $position='center'">
        <fo:page-number/>
      </xsl:when>

      <xsl:when test="$sequence='blank'">
        <xsl:choose>
          <xsl:when test="$double.sided != 0 and $position = 'left'">
            <fo:page-number/>
          </xsl:when>
          <xsl:when test="$double.sided = 0 and $position = 'center'">
            <fo:page-number/>
          </xsl:when>
          <xsl:otherwise>
            <!-- nop -->
          </xsl:otherwise>
        </xsl:choose>
      </xsl:when>


      <xsl:otherwise>
        <!-- nop -->
      </xsl:otherwise>
    </xsl:choose>
  </fo:block>
</xsl:template>

<!-- ==================================================================== -->
<xsl:template match="*" mode="region.inner.mode">
  <xsl:param name="master-reference" select="'unknown'"/>
  <xsl:param name="gentext-key" select="local-name(.)"/>

  <!-- remove -draft from reference -->
  <xsl:variable name="pageclass">
    <xsl:choose>
      <xsl:when test="contains($master-reference, '-draft')">
        <xsl:value-of select="substring-before($master-reference, '-draft')"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:value-of select="$master-reference"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <fo:static-content flow-name="xsl-region-inner-first">
    <xsl:call-template name="inner.region.content">
      <xsl:with-param name="pageclass" select="$pageclass"/>
      <xsl:with-param name="sequence" select="'first'"/>
      <xsl:with-param name="gentext-key" select="$gentext-key"/>
    </xsl:call-template>
  </fo:static-content>

  <fo:static-content flow-name="xsl-region-inner-odd">
    <xsl:call-template name="inner.region.content">
      <xsl:with-param name="pageclass" select="$pageclass"/>
      <xsl:with-param name="sequence" select="'odd'"/>
      <xsl:with-param name="gentext-key" select="$gentext-key"/>
    </xsl:call-template>
  </fo:static-content>

  <fo:static-content flow-name="xsl-region-inner-even">
    <xsl:call-template name="inner.region.content">
      <xsl:with-param name="pageclass" select="$pageclass"/>
      <xsl:with-param name="sequence" select="'even'"/>
      <xsl:with-param name="gentext-key" select="$gentext-key"/>
    </xsl:call-template>
  </fo:static-content>

  <fo:static-content flow-name="xsl-region-inner-blank">
    <xsl:call-template name="inner.region.content">
      <xsl:with-param name="pageclass" select="$pageclass"/>
      <xsl:with-param name="sequence" select="'blank'"/>
      <xsl:with-param name="gentext-key" select="$gentext-key"/>
    </xsl:call-template>
  </fo:static-content>

</xsl:template>

<xsl:template match="*" mode="region.outer.mode">
  <xsl:param name="master-reference" select="'unknown'"/>
  <xsl:param name="gentext-key" select="local-name(.)"/>

  <!-- remove -draft from reference -->
  <xsl:variable name="pageclass">
    <xsl:choose>
      <xsl:when test="contains($master-reference, '-draft')">
        <xsl:value-of select="substring-before($master-reference, '-draft')"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:value-of select="$master-reference"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <fo:static-content flow-name="xsl-region-outer-first">
    <xsl:call-template name="outer.region.content">
      <xsl:with-param name="pageclass" select="$pageclass"/>
      <xsl:with-param name="sequence" select="'first'"/>
      <xsl:with-param name="gentext-key" select="$gentext-key"/>
    </xsl:call-template>
  </fo:static-content>

  <fo:static-content flow-name="xsl-region-outer-odd">
    <xsl:call-template name="outer.region.content">
      <xsl:with-param name="pageclass" select="$pageclass"/>
      <xsl:with-param name="sequence" select="'odd'"/>
      <xsl:with-param name="gentext-key" select="$gentext-key"/>
    </xsl:call-template>
  </fo:static-content>

  <fo:static-content flow-name="xsl-region-outer-even">
    <xsl:call-template name="outer.region.content">
      <xsl:with-param name="pageclass" select="$pageclass"/>
      <xsl:with-param name="sequence" select="'even'"/>
      <xsl:with-param name="gentext-key" select="$gentext-key"/>
    </xsl:call-template>
  </fo:static-content>

  <fo:static-content flow-name="xsl-region-outer-blank">
    <xsl:call-template name="outer.region.content">
      <xsl:with-param name="pageclass" select="$pageclass"/>
      <xsl:with-param name="sequence" select="'blank'"/>
      <xsl:with-param name="gentext-key" select="$gentext-key"/>
    </xsl:call-template>
  </fo:static-content>

</xsl:template>

<xsl:template name="inner.region.content">
  <xsl:param name="pageclass" select="''"/>
  <xsl:param name="sequence" select="''"/>
  <xsl:param name="position" select="''"/>
  <xsl:param name="gentext-key" select="''"/>

  <!-- pageclass can be front, body, back -->
  <!-- sequence can be odd, even, first, blank -->
  <!-- position can be left, center, right -->

  <!-- Customize to add side region content-->
  <fo:block xsl:use-attribute-sets="inner.region.content.properties">
    <!-- Add your content here -->
  </fo:block>
</xsl:template>

<xsl:template name="outer.region.content">
  <xsl:param name="pageclass" select="''"/>
  <xsl:param name="sequence" select="''"/>
  <xsl:param name="position" select="''"/>
  <xsl:param name="gentext-key" select="''"/>

  <!-- pageclass can be front, body, back -->
  <!-- sequence can be odd, even, first, blank -->
  <!-- position can be left, center, right -->

  <!-- Customize to add side region content-->
  <fo:block xsl:use-attribute-sets="outer.region.content.properties">
    <!-- Add your content here -->
  </fo:block>
</xsl:template>

<!-- ==================================================================== -->

<xsl:template name="page.number.format">
  <xsl:param name="element" select="local-name(.)"/>
  <xsl:param name="master-reference" select="''"/>

  <xsl:choose>
    <xsl:when test="$element = 'toc' and self::book">i</xsl:when>
    <xsl:when test="$element = 'set'">i</xsl:when>
    <xsl:when test="$element = 'book'">i</xsl:when>
    <xsl:when test="$element = 'preface'">i</xsl:when>
    <xsl:when test="$element = 'dedication'">i</xsl:when>
    <xsl:when test="$element = 'acknowledgements'">i</xsl:when>
    <xsl:otherwise>1</xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template name="initial.page.number">
  <xsl:param name="element" select="local-name(.)"/>
  <xsl:param name="master-reference" select="''"/>

  <xsl:variable name="first">
    <xsl:choose>
      <xsl:when test="$force.blank.pages = 0">auto</xsl:when>
      <xsl:otherwise>auto-odd</xsl:otherwise>
    </xsl:choose>
  </xsl:variable>
  <!-- Select the first content that the stylesheet places
       after the TOC -->
  <xsl:variable name="first.book.content" 
                select="ancestor::book/*[
                          not(self::title or
                              self::subtitle or
                              self::titleabbrev or
                              self::bookinfo or
                              self::info or
                              self::dedication or
                              self::acknowledgements or
                              self::preface or
                              self::toc or
                              self::lot)][1]"/>
  <xsl:choose>
    <!-- double-sided output -->
    <xsl:when test="$double.sided != 0">
      <xsl:choose>
        <xsl:when test="$element = 'toc'"><xsl:value-of select="$first"/></xsl:when>
        <xsl:when test="$element = 'book'"><xsl:value-of select="$first"/></xsl:when>
        <!-- preface typically continues TOC roman numerals -->
        <!-- If changed to 1 here, then change page.number.format too -->
        <xsl:when test="$element = 'preface'"><xsl:value-of select="$first"/></xsl:when>
        <xsl:when test="($element = 'dedication' or $element = 'article') 
                    and not(preceding::chapter
                            or preceding::preface
                            or preceding::appendix
                            or preceding::article
                            or preceding::dedication
                            or parent::part
                            or parent::reference)">1</xsl:when>
        <xsl:when test="generate-id($first.book.content) =
                        generate-id(.)">1</xsl:when>
        <xsl:otherwise><xsl:value-of select="$first"/></xsl:otherwise>
      </xsl:choose>
    </xsl:when>

    <!-- single-sided output -->
    <xsl:otherwise>
      <xsl:choose>
        <xsl:when test="$element = 'toc'">auto</xsl:when>
        <xsl:when test="$element = 'book'">auto</xsl:when>
        <xsl:when test="$element = 'preface'">auto</xsl:when>
       <xsl:when test="($element = 'dedication' or $element = 'article') and
                        not(preceding::chapter
                            or preceding::preface
                            or preceding::appendix
                            or preceding::article
                            or preceding::dedication
                            or parent::part
                            or parent::reference)">1</xsl:when>
        <xsl:when test="generate-id($first.book.content) =
                        generate-id(.)">1</xsl:when>
        <xsl:otherwise>auto</xsl:otherwise>
      </xsl:choose>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template name="force.page.count">
  <xsl:param name="element" select="local-name(.)"/>
  <xsl:param name="master-reference" select="''"/>

  <xsl:choose>
    <!-- no automatic even blank pages at end of chapters -->
    <xsl:when test="$force.blank.pages = 0">no-force</xsl:when>
    <!-- double-sided output -->
    <xsl:when test="$double.sided != 0">end-on-even</xsl:when>
    <!-- single-sided output -->
    <xsl:otherwise>no-force</xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template name="set.flow.properties">
  <xsl:param name="element" select="local-name(.)"/>
  <xsl:param name="master-reference" select="''"/>

  <!-- This template is called after each <fo:flow> starts. -->
  <!-- Customize this template to set attributes on fo:flow -->

  <!-- remove -draft from reference -->
  <xsl:variable name="pageclass">
    <xsl:choose>
      <xsl:when test="contains($master-reference, '-draft')">
        <xsl:value-of select="substring-before($master-reference, '-draft')"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:value-of select="$master-reference"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:choose>
    <xsl:when test="$fop.extensions != 0">
      <!-- body.start.indent does not work well with these processors -->
    </xsl:when>
    <xsl:when test="starts-with($pageclass, 'body') or
                    starts-with($pageclass, 'lot') or
                    starts-with($pageclass, 'front') or
                    $element = 'preface' or
                    (starts-with($pageclass, 'back') and
                    $element = 'appendix')">
      <xsl:attribute name="start-indent">
        <xsl:value-of select="$body.start.indent"/>
      </xsl:attribute>
      <xsl:attribute name="end-indent">
        <xsl:value-of select="$body.end.indent"/>
      </xsl:attribute>
    </xsl:when>
  </xsl:choose>

</xsl:template>
<!-- ==================================================================== -->

<!-- Customize this template for custom side regions -->
<xsl:template name="region.inner">
  <xsl:param name="sequence">blank</xsl:param>
  <xsl:param name="classname">blank</xsl:param>

  <xsl:choose>
    <xsl:when test="$sequence = 'first' or $sequence = 'odd'">
      <fo:region-start xsl:use-attribute-sets="region.inner.properties">
        <xsl:attribute name="region-name">
          <xsl:text>xsl-region-inner-</xsl:text>
          <xsl:value-of select="$sequence"/>
        </xsl:attribute>
        <xsl:attribute name="precedence">
          <xsl:value-of select="$region.start.precedence"/>
        </xsl:attribute>
        <xsl:attribute name="extent">
          <xsl:value-of select="$region.inner.extent"/>
        </xsl:attribute>
      </fo:region-start>
    </xsl:when>
    <xsl:otherwise>
      <fo:region-end xsl:use-attribute-sets="region.inner.properties">
        <xsl:attribute name="region-name">
          <xsl:text>xsl-region-inner-</xsl:text>
          <xsl:value-of select="$sequence"/>
        </xsl:attribute>
        <xsl:attribute name="precedence">
          <xsl:value-of select="$region.end.precedence"/>
        </xsl:attribute>
        <xsl:attribute name="extent">
          <xsl:value-of select="$region.inner.extent"/>
        </xsl:attribute>
      </fo:region-end>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<!-- Customize this template for custom side regions -->
<xsl:template name="region.outer">
  <xsl:param name="sequence">blank</xsl:param>
  <xsl:param name="classname">blank</xsl:param>

  <xsl:choose>
    <xsl:when test="$sequence = 'first' or $sequence = 'odd'">
      <fo:region-end xsl:use-attribute-sets="region.outer.properties">
        <xsl:attribute name="region-name">
          <xsl:text>xsl-region-outer-</xsl:text>
          <xsl:value-of select="$sequence"/>
        </xsl:attribute>
        <xsl:attribute name="precedence">
          <xsl:value-of select="$region.start.precedence"/>
        </xsl:attribute>
        <xsl:attribute name="extent">
          <xsl:value-of select="$region.outer.extent"/>
        </xsl:attribute>
      </fo:region-end>
    </xsl:when>
    <xsl:otherwise>
      <fo:region-start xsl:use-attribute-sets="region.outer.properties">
        <xsl:attribute name="region-name">
          <xsl:text>xsl-region-outer-</xsl:text>
          <xsl:value-of select="$sequence"/>
        </xsl:attribute>
        <xsl:attribute name="precedence">
          <xsl:value-of select="$region.end.precedence"/>
        </xsl:attribute>
        <xsl:attribute name="extent">
          <xsl:value-of select="$region.outer.extent"/>
        </xsl:attribute>
      </fo:region-start>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

</xsl:stylesheet>
