<?xml version="1.0" encoding="ASCII"?>
<!--This file was created automatically by html2xhtml-->
<!--from the HTML stylesheets.-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns="http://www.w3.org/1999/xhtml" version="1.0">

<!-- ********************************************************************
     $Id: component.xsl 9500 2012-07-15 23:24:21Z bobstayton $
     ********************************************************************

     This file is part of the XSL DocBook Stylesheet distribution.
     See ../README or http://docbook.sf.net/release/xsl/current/ for
     copyright and other information.

     ******************************************************************** -->

<!-- ==================================================================== -->

<!-- Set to 2 for backwards compatibility -->
<xsl:param name="component.heading.level" select="2"/>

<xsl:template name="component.title">
  <xsl:param name="node" select="."/>

  <!-- This handles the case where a component (bibliography, for example)
       occurs inside a section; will we need parameters for this? -->

  <!-- This "level" is a section level.  To compute <h> level, add 1. -->
  <xsl:variable name="level">
    <xsl:choose>
      <!-- chapters and other book children should get <h1> -->
      <xsl:when test="$node/parent::book">0</xsl:when>
      <xsl:when test="ancestor::section">
        <xsl:value-of select="count(ancestor::section)+1"/>
      </xsl:when>
      <xsl:when test="ancestor::sect5">6</xsl:when>
      <xsl:when test="ancestor::sect4">5</xsl:when>
      <xsl:when test="ancestor::sect3">4</xsl:when>
      <xsl:when test="ancestor::sect2">3</xsl:when>
      <xsl:when test="ancestor::sect1">2</xsl:when>
      <xsl:otherwise>1</xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:element name="h{$level+1}" namespace="http://www.w3.org/1999/xhtml">
    <xsl:attribute name="class">title</xsl:attribute>
    <xsl:call-template name="anchor">
      <xsl:with-param name="node" select="$node"/>
      <xsl:with-param name="conditional" select="0"/>
    </xsl:call-template>
    <xsl:apply-templates select="$node" mode="object.title.markup">
      <xsl:with-param name="allow-anchors" select="1"/>
    </xsl:apply-templates>
  </xsl:element>
</xsl:template>

<xsl:template name="component.subtitle">
  <xsl:param name="node" select="."/>
  <xsl:variable name="subtitle" select="($node/docinfo/subtitle                         |$node/info/subtitle                         |$node/prefaceinfo/subtitle                         |$node/chapterinfo/subtitle                         |$node/appendixinfo/subtitle                         |$node/articleinfo/subtitle                         |$node/artheader/subtitle                         |$node/subtitle)[1]"/>

  <xsl:if test="$subtitle">
    <h3 class="subtitle">
      <xsl:call-template name="id.attribute"/>
      <em xmlns:xslo="http://www.w3.org/1999/XSL/Transform">
        <xsl:apply-templates select="$node" mode="object.subtitle.markup"/>
      </em>
    </h3>
  </xsl:if>
</xsl:template>

<xsl:template name="component.separator">
</xsl:template>

<!-- ==================================================================== -->

<xsl:template match="dedication" mode="dedication">
  <xsl:call-template name="id.warning"/>

  <div>
    <xsl:call-template name="common.html.attributes">
      <xsl:with-param name="inherit" select="1"/>
    </xsl:call-template>
    <xsl:call-template name="id.attribute">
      <xsl:with-param name="conditional" select="0"/>
    </xsl:call-template>
    <xsl:call-template name="dedication.titlepage"/>
    <xsl:apply-templates/>
    <xsl:call-template name="process.footnotes"/>
  </div>
</xsl:template>

<xsl:template match="dedication/title|dedication/info/title" mode="titlepage.mode" priority="2">
  <xsl:call-template name="component.title">
    <xsl:with-param name="node" select="ancestor::dedication[1]"/>
  </xsl:call-template>
</xsl:template>

<xsl:template match="dedication/subtitle|dedication/info/subtitle" mode="titlepage.mode" priority="2">
  <xsl:call-template name="component.subtitle">
    <xsl:with-param name="node" select="ancestor::dedication[1]"/>
  </xsl:call-template>
</xsl:template>

<xsl:template match="dedication"/> <!-- see mode="dedication" -->
<xsl:template match="dedication/title"/>
<xsl:template match="dedication/subtitle"/>
<xsl:template match="dedication/titleabbrev"/>

<!-- ==================================================================== -->

<xsl:template match="acknowledgements" mode="acknowledgements">
  <xsl:call-template name="id.warning"/>

  <div>
    <xsl:call-template name="common.html.attributes">
      <xsl:with-param name="inherit" select="1"/>
    </xsl:call-template>
    <xsl:call-template name="id.attribute">
      <xsl:with-param name="conditional" select="0"/>
    </xsl:call-template>
    <xsl:call-template name="acknowledgements.titlepage"/>
    <xsl:apply-templates/>
    <xsl:call-template name="process.footnotes"/>
  </div>
</xsl:template>

<xsl:template match="acknowledgements/title|acknowledgements/info/title" mode="titlepage.mode" priority="2">
  <xsl:call-template name="component.title">
    <xsl:with-param name="node" select="ancestor::acknowledgements[1]"/>
  </xsl:call-template>
</xsl:template>

<xsl:template match="acknowledgements/subtitle|acknowledgements/info/subtitle" mode="titlepage.mode" priority="2">
  <xsl:call-template name="component.subtitle">
    <xsl:with-param name="node" select="ancestor::acknowledgements[1]"/>
  </xsl:call-template>
</xsl:template>

<xsl:template match="acknowledgements"/> <!-- see mode="acknowledgements" -->
<xsl:template match="acknowledgements/title"/>
<xsl:template match="acknowledgements/subtitle"/>
<xsl:template match="acknowledgements/titleabbrev"/>

<!-- ==================================================================== -->

<xsl:template match="colophon">
  <xsl:call-template name="id.warning"/>

  <div>
    <xsl:call-template name="common.html.attributes">
      <xsl:with-param name="inherit" select="1"/>
    </xsl:call-template>
    <xsl:call-template name="id.attribute">
      <xsl:with-param name="conditional" select="0"/>
    </xsl:call-template>

    <xsl:call-template name="component.separator"/>
    <xsl:call-template name="component.title"/>
    <xsl:call-template name="component.subtitle"/>

    <xsl:apply-templates/>
    <xsl:call-template name="process.footnotes"/>
  </div>
</xsl:template>

<xsl:template match="colophon/title"/>
<xsl:template match="colophon/subtitle"/>
<xsl:template match="colophon/titleabbrev"/>

<!-- ==================================================================== -->

<xsl:template match="preface">
  <xsl:call-template name="id.warning"/>

  <xsl:element name="{$div.element}" namespace="http://www.w3.org/1999/xhtml">
    <xsl:call-template name="common.html.attributes">
      <xsl:with-param name="inherit" select="1"/>
    </xsl:call-template>
    <xsl:call-template name="id.attribute">
      <xsl:with-param name="conditional" select="0"/>
    </xsl:call-template>

    <xsl:call-template name="component.separator"/>
    <xsl:call-template name="preface.titlepage"/>

    <xsl:variable name="toc.params">
      <xsl:call-template name="find.path.params">
        <xsl:with-param name="table" select="normalize-space($generate.toc)"/>
      </xsl:call-template>
    </xsl:variable>

    <xsl:if test="contains($toc.params, 'toc')">
      <xsl:call-template name="component.toc">
        <xsl:with-param name="toc.title.p" select="contains($toc.params, 'title')"/>
      </xsl:call-template>
      <xsl:call-template name="component.toc.separator"/>
    </xsl:if>
    <xsl:apply-templates/>
    <xsl:call-template name="process.footnotes"/>
  </xsl:element>
</xsl:template>

<xsl:template match="preface/title" mode="titlepage.mode" priority="2">
  <xsl:call-template name="component.title">
    <xsl:with-param name="node" select="ancestor::preface[1]"/>
  </xsl:call-template>
</xsl:template>

<xsl:template match="preface/subtitle                      |preface/prefaceinfo/subtitle                      |preface/info/subtitle                      |preface/docinfo/subtitle" mode="titlepage.mode" priority="2">
  <xsl:call-template name="component.subtitle">
    <xsl:with-param name="node" select="ancestor::preface[1]"/>
  </xsl:call-template>
</xsl:template>

<xsl:template match="preface/docinfo|prefaceinfo"/>
<xsl:template match="preface/info"/>
<xsl:template match="preface/title"/>
<xsl:template match="preface/titleabbrev"/>
<xsl:template match="preface/subtitle"/>

<!-- ==================================================================== -->

<xsl:template match="chapter">
  <xsl:call-template name="id.warning"/>

  <xsl:element name="{$div.element}" namespace="http://www.w3.org/1999/xhtml">
    <xsl:call-template name="common.html.attributes">
      <xsl:with-param name="inherit" select="1"/>
    </xsl:call-template>
    <xsl:call-template name="id.attribute">
      <xsl:with-param name="conditional" select="0"/>
    </xsl:call-template>

    <xsl:call-template name="component.separator"/>
    <xsl:call-template name="chapter.titlepage"/>

    <xsl:variable name="toc.params">
      <xsl:call-template name="find.path.params">
        <xsl:with-param name="table" select="normalize-space($generate.toc)"/>
      </xsl:call-template>
    </xsl:variable>
    <xsl:if test="contains($toc.params, 'toc')">
      <xsl:call-template name="component.toc">
        <xsl:with-param name="toc.title.p" select="contains($toc.params, 'title')"/>
      </xsl:call-template>
      <xsl:call-template name="component.toc.separator"/>
    </xsl:if>
    <xsl:apply-templates/>
    <xsl:call-template name="process.footnotes"/>
  </xsl:element>
</xsl:template>

<xsl:template match="chapter/title|chapter/chapterinfo/title|chapter/info/title" mode="titlepage.mode" priority="2">
  <xsl:call-template name="component.title">
    <xsl:with-param name="node" select="ancestor::chapter[1]"/>
  </xsl:call-template>
</xsl:template>

<xsl:template match="chapter/subtitle                      |chapter/chapterinfo/subtitle                      |chapter/info/subtitle                      |chapter/docinfo/subtitle" mode="titlepage.mode" priority="2">
  <xsl:call-template name="component.subtitle">
    <xsl:with-param name="node" select="ancestor::chapter[1]"/>
  </xsl:call-template>
</xsl:template>

<xsl:template match="chapter/docinfo|chapterinfo"/>
<xsl:template match="chapter/info"/>
<xsl:template match="chapter/title"/>
<xsl:template match="chapter/titleabbrev"/>
<xsl:template match="chapter/subtitle"/>

<!-- ==================================================================== -->

<xsl:template match="appendix">
  <xsl:variable name="ischunk">
    <xsl:call-template name="chunk"/>
  </xsl:variable>

  <xsl:call-template name="id.warning"/>

  <xsl:element name="{$div.element}" namespace="http://www.w3.org/1999/xhtml">
    <xsl:call-template name="common.html.attributes">
      <xsl:with-param name="inherit" select="1"/>
    </xsl:call-template>
    <xsl:call-template name="id.attribute">
      <xsl:with-param name="conditional" select="0"/>
    </xsl:call-template>

    <xsl:choose>
      <xsl:when test="parent::article and $ischunk = 0">
        <xsl:call-template name="section.heading">
          <xsl:with-param name="level" select="1"/>
          <xsl:with-param name="title">
            <xsl:apply-templates select="." mode="object.title.markup"/>
          </xsl:with-param>
        </xsl:call-template>
      </xsl:when>
      <xsl:otherwise>
        <xsl:call-template name="component.separator"/>
        <xsl:call-template name="appendix.titlepage"/>
      </xsl:otherwise>
    </xsl:choose>

    <xsl:variable name="toc.params">
      <xsl:call-template name="find.path.params">
        <xsl:with-param name="table" select="normalize-space($generate.toc)"/>
      </xsl:call-template>
    </xsl:variable>

    <xsl:if test="contains($toc.params, 'toc')">
      <xsl:call-template name="component.toc">
        <xsl:with-param name="toc.title.p" select="contains($toc.params, 'title')"/>
      </xsl:call-template>
      <xsl:call-template name="component.toc.separator"/>
    </xsl:if>

    <xsl:apply-templates/>

    <xsl:if test="not(parent::article) or $ischunk != 0">
      <xsl:call-template name="process.footnotes"/>
    </xsl:if>
  </xsl:element>
</xsl:template>

<xsl:template match="appendix/title|appendix/appendixinfo/title" mode="titlepage.mode" priority="2">
  <xsl:call-template name="component.title">
    <xsl:with-param name="node" select="ancestor::appendix[1]"/>
  </xsl:call-template>
</xsl:template>

<xsl:template match="appendix/subtitle                      |appendix/appendixinfo/subtitle                      |appendix/info/subtitle                      |appendix/docinfo/subtitle" mode="titlepage.mode" priority="2">
  <xsl:call-template name="component.subtitle">
    <xsl:with-param name="node" select="ancestor::appendix[1]"/>
  </xsl:call-template>
</xsl:template>

<xsl:template match="appendix/docinfo|appendixinfo"/>
<xsl:template match="appendix/info"/>
<xsl:template match="appendix/title"/>
<xsl:template match="appendix/titleabbrev"/>
<xsl:template match="appendix/subtitle"/>

<!-- ==================================================================== -->

<xsl:template match="article">
  <xsl:call-template name="id.warning"/>

  <xsl:element name="{$div.element}" namespace="http://www.w3.org/1999/xhtml">
    <xsl:call-template name="common.html.attributes">
      <xsl:with-param name="inherit" select="1"/>
    </xsl:call-template>
    <xsl:call-template name="id.attribute">
      <xsl:with-param name="conditional" select="0"/>
    </xsl:call-template>

    <xsl:call-template name="article.titlepage"/>

    <xsl:variable name="toc.params">
      <xsl:call-template name="find.path.params">
        <xsl:with-param name="table" select="normalize-space($generate.toc)"/>
      </xsl:call-template>
    </xsl:variable>

    <xsl:call-template name="make.lots">
      <xsl:with-param name="toc.params" select="$toc.params"/>
      <xsl:with-param name="toc">
        <xsl:call-template name="component.toc">
          <xsl:with-param name="toc.title.p" select="contains($toc.params, 'title')"/>
        </xsl:call-template>
      </xsl:with-param>
    </xsl:call-template>

    <xsl:apply-templates/>
    <xsl:call-template name="process.footnotes"/>
  </xsl:element>
</xsl:template>

<xsl:template match="article/title|article/articleinfo/title" mode="titlepage.mode" priority="2">
  <xsl:call-template name="component.title">
    <xsl:with-param name="node" select="ancestor::article[1]"/>
  </xsl:call-template>
</xsl:template>

<xsl:template match="article/subtitle                      |article/articleinfo/subtitle                      |article/info/subtitle                      |article/artheader/subtitle" mode="titlepage.mode" priority="2">
  <xsl:call-template name="component.subtitle">
    <xsl:with-param name="node" select="ancestor::article[1]"/>
  </xsl:call-template>
</xsl:template>

<xsl:template match="article/artheader|article/articleinfo"/>
<xsl:template match="article/info"/>
<xsl:template match="article/title"/>
<xsl:template match="article/titleabbrev"/>
<xsl:template match="article/subtitle"/>

<!-- ==================================================================== -->

<xsl:template match="topic">
  <xsl:call-template name="id.warning"/>

  <xsl:element name="{$div.element}" namespace="http://www.w3.org/1999/xhtml">
    <xsl:call-template name="common.html.attributes">
      <xsl:with-param name="inherit" select="1"/>
    </xsl:call-template>
    <xsl:call-template name="id.attribute">
      <xsl:with-param name="conditional" select="0"/>
    </xsl:call-template>

    <xsl:call-template name="topic.titlepage"/>

    <xsl:variable name="toc.params">
      <xsl:call-template name="find.path.params">
        <xsl:with-param name="table" select="normalize-space($generate.toc)"/>
      </xsl:call-template>
    </xsl:variable>

    <xsl:apply-templates/>

    <xsl:call-template name="process.footnotes"/>
  </xsl:element>
</xsl:template>

<xsl:template match="topic/title|topic/info/title" mode="titlepage.mode" priority="2">
  <xsl:call-template name="component.title">
    <xsl:with-param name="node" select="ancestor::topic[1]"/>
  </xsl:call-template>
</xsl:template>

<xsl:template match="topic/subtitle                      |topic/info/subtitle" mode="titlepage.mode" priority="2">
  <xsl:call-template name="component.subtitle">
    <xsl:with-param name="node" select="ancestor::topic[1]"/>
  </xsl:call-template>
</xsl:template>

<xsl:template match="topic/info"/>
<xsl:template match="topic/title"/>
<xsl:template match="topic/titleabbrev"/>
<xsl:template match="topic/subtitle"/>

</xsl:stylesheet>
