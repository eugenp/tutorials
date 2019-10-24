<?xml version='1.0'?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fo="http://www.w3.org/1999/XSL/Format"
                xmlns:axf="http://www.antennahouse.com/names/XSL/Extensions"
                version='1.0'>

<!-- ********************************************************************
     $Id: division.xsl 9730 2013-03-15 15:26:25Z bobstayton $
     ********************************************************************

     This file is part of the XSL DocBook Stylesheet distribution.
     See ../README or http://docbook.sf.net/release/xsl/current/ for
     copyright and other information.

     ******************************************************************** -->

<!-- ==================================================================== -->

<xsl:template name="division.title">
  <xsl:param name="node" select="."/>
  <xsl:variable name="id">
    <xsl:call-template name="object.id">
      <xsl:with-param name="object" select="$node"/>
    </xsl:call-template>
  </xsl:variable>
  <xsl:variable name="title">
    <xsl:apply-templates select="$node" mode="object.title.markup">
      <xsl:with-param name="allow-anchors" select="1"/>
    </xsl:apply-templates>
  </xsl:variable>

  <fo:block keep-with-next.within-column="always"
            hyphenate="false">
    <xsl:if test="$axf.extensions != 0">
      <xsl:attribute name="axf:outline-level">
        <xsl:choose>
          <xsl:when test="count($node/ancestor::*) > 0">
            <xsl:value-of select="count($node/ancestor::*)"/>
          </xsl:when>
          <xsl:otherwise>1</xsl:otherwise>
        </xsl:choose>
      </xsl:attribute>
      <xsl:attribute name="axf:outline-expand">false</xsl:attribute>
      <xsl:attribute name="axf:outline-title">
        <xsl:value-of select="normalize-space($title)"/>
      </xsl:attribute>
    </xsl:if>
    <xsl:copy-of select="$title"/>
  </fo:block>
</xsl:template>

<!-- ==================================================================== -->

<xsl:template match="set">
  <xsl:variable name="id">
    <xsl:call-template name="object.id"/>
  </xsl:variable>

  <xsl:variable name="preamble"
                select="*[not(self::book or self::set or self::setindex)]"/>

  <xsl:variable name="content" select="book|set|setindex"/>

  <xsl:variable name="titlepage-master-reference">
    <xsl:call-template name="select.pagemaster">
      <xsl:with-param name="pageclass" select="'titlepage'"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:variable name="lot-master-reference">
    <xsl:call-template name="select.pagemaster">
      <xsl:with-param name="pageclass" select="'lot'"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:if test="$preamble">
    <fo:page-sequence hyphenate="{$hyphenate}"
                      master-reference="{$titlepage-master-reference}">
      <xsl:attribute name="language">
        <xsl:call-template name="l10n.language"/>
      </xsl:attribute>
      <xsl:attribute name="format">
        <xsl:call-template name="page.number.format">
          <xsl:with-param name="master-reference" 
                          select="$titlepage-master-reference"/>
        </xsl:call-template>
      </xsl:attribute>

      <xsl:attribute name="initial-page-number">
        <xsl:call-template name="initial.page.number">
          <xsl:with-param name="master-reference" 
                          select="$titlepage-master-reference"/>
        </xsl:call-template>
      </xsl:attribute>

      <xsl:attribute name="force-page-count">
        <xsl:call-template name="force.page.count">
          <xsl:with-param name="master-reference" 
                          select="$titlepage-master-reference"/>
        </xsl:call-template>
      </xsl:attribute>

      <xsl:attribute name="hyphenation-character">
        <xsl:call-template name="gentext">
          <xsl:with-param name="key" select="'hyphenation-character'"/>
        </xsl:call-template>
      </xsl:attribute>
      <xsl:attribute name="hyphenation-push-character-count">
        <xsl:call-template name="gentext">
          <xsl:with-param name="key" select="'hyphenation-push-character-count'"/>
        </xsl:call-template>
      </xsl:attribute>
      <xsl:attribute name="hyphenation-remain-character-count">
        <xsl:call-template name="gentext">
          <xsl:with-param name="key" select="'hyphenation-remain-character-count'"/>
        </xsl:call-template>
      </xsl:attribute>

      <xsl:apply-templates select="." mode="running.head.mode">
        <xsl:with-param name="master-reference" select="$titlepage-master-reference"/>
      </xsl:apply-templates>

      <xsl:apply-templates select="." mode="running.foot.mode">
        <xsl:with-param name="master-reference" select="$titlepage-master-reference"/>
      </xsl:apply-templates>

      <fo:flow flow-name="xsl-region-body">
        <xsl:call-template name="set.flow.properties">
          <xsl:with-param name="element" select="local-name(.)"/>
          <xsl:with-param name="master-reference" 
                          select="$titlepage-master-reference"/>
        </xsl:call-template>

        <fo:block id="{$id}">
          <xsl:call-template name="set.titlepage"/>
        </fo:block>
      </fo:flow>
    </fo:page-sequence>
  </xsl:if>

  <xsl:variable name="toc.params">
    <xsl:call-template name="find.path.params">
      <xsl:with-param name="table" select="normalize-space($generate.toc)"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:if test="contains($toc.params, 'toc')">
    <fo:page-sequence hyphenate="{$hyphenate}"
                      master-reference="{$lot-master-reference}">
      <xsl:attribute name="language">
        <xsl:call-template name="l10n.language"/>
      </xsl:attribute>
      <xsl:attribute name="format">
        <xsl:call-template name="page.number.format">
          <xsl:with-param name="element" select="'toc'"/>
          <xsl:with-param name="master-reference"
                          select="$lot-master-reference"/>
        </xsl:call-template>
      </xsl:attribute>
      <xsl:attribute name="initial-page-number">
        <xsl:call-template name="initial.page.number">
          <xsl:with-param name="master-reference"
                          select="$lot-master-reference"/>
        </xsl:call-template>
      </xsl:attribute>
      <xsl:attribute name="force-page-count">
        <xsl:call-template name="force.page.count">
          <xsl:with-param name="master-reference"
                          select="$lot-master-reference"/>
        </xsl:call-template>
      </xsl:attribute>

      <xsl:attribute name="hyphenation-character">
        <xsl:call-template name="gentext">
          <xsl:with-param name="key" select="'hyphenation-character'"/>
        </xsl:call-template>
      </xsl:attribute>
      <xsl:attribute name="hyphenation-push-character-count">
        <xsl:call-template name="gentext">
          <xsl:with-param name="key" select="'hyphenation-push-character-count'"/>
        </xsl:call-template>
      </xsl:attribute>
      <xsl:attribute name="hyphenation-remain-character-count">
        <xsl:call-template name="gentext">
          <xsl:with-param name="key" select="'hyphenation-remain-character-count'"/>
        </xsl:call-template>
      </xsl:attribute>

      <xsl:apply-templates select="." mode="running.head.mode">
        <xsl:with-param name="master-reference" select="$lot-master-reference"/>
      </xsl:apply-templates>

      <xsl:apply-templates select="." mode="running.foot.mode">
        <xsl:with-param name="master-reference" select="$lot-master-reference"/>
      </xsl:apply-templates>

      <fo:flow flow-name="xsl-region-body">
        <xsl:call-template name="set.flow.properties">
          <xsl:with-param name="element" select="local-name(.)"/>
          <xsl:with-param name="master-reference" 
                          select="$lot-master-reference"/>
        </xsl:call-template>

        <xsl:call-template name="set.toc"/>
      </fo:flow>
    </fo:page-sequence>
  </xsl:if>

  <xsl:apply-templates select="$content"/>
</xsl:template>

<xsl:template match="set/setinfo"></xsl:template>
<xsl:template match="set/title"></xsl:template>
<xsl:template match="set/subtitle"></xsl:template>
<xsl:template match="set/titleabbrev"></xsl:template>

<!-- ==================================================================== -->

<xsl:template match="book">
  <xsl:variable name="id">
    <xsl:call-template name="object.id"/>
  </xsl:variable>

  <xsl:variable name="preamble"
                select="title|subtitle|titleabbrev|bookinfo|info"/>

  <xsl:variable name="content"
                select="node()[not(self::title or self::subtitle
                            or self::titleabbrev
                            or self::info
                            or self::bookinfo)]"/>

  <xsl:variable name="titlepage-master-reference">
    <xsl:call-template name="select.pagemaster">
      <xsl:with-param name="pageclass" select="'titlepage'"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:call-template name="front.cover"/>

  <xsl:if test="$preamble">
    <xsl:call-template name="page.sequence">
      <xsl:with-param name="master-reference"
                      select="$titlepage-master-reference"/>
      <xsl:with-param name="content">
        <fo:block id="{$id}">
          <xsl:call-template name="book.titlepage"/>
        </fo:block>
      </xsl:with-param>
    </xsl:call-template>
  </xsl:if>

  <xsl:apply-templates select="dedication" mode="dedication"/>
  <xsl:apply-templates select="acknowledgements" mode="acknowledgements"/>

  <xsl:call-template name="make.book.tocs"/>

  <xsl:apply-templates select="$content"/>

  <xsl:call-template name="back.cover"/>

</xsl:template>

<xsl:template match="book/bookinfo"></xsl:template>
<xsl:template match="book/info"></xsl:template>
<xsl:template match="book/title"></xsl:template>
<xsl:template match="book/subtitle"></xsl:template>
<xsl:template match="book/titleabbrev"></xsl:template>

<!-- Placeholder templates -->
<xsl:template name="front.cover"/>
<xsl:template name="back.cover"/>

<!-- ================================================================= -->
<xsl:template name="make.book.tocs">

  <xsl:variable name="lot-master-reference">
    <xsl:call-template name="select.pagemaster">
      <xsl:with-param name="pageclass" select="'lot'"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:variable name="toc.params">
    <xsl:call-template name="find.path.params">
      <xsl:with-param name="table" select="normalize-space($generate.toc)"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:if test="contains($toc.params, 'toc')">
    <xsl:call-template name="page.sequence">
      <xsl:with-param name="master-reference"
                      select="$lot-master-reference"/>
      <xsl:with-param name="element" select="'toc'"/>
      <xsl:with-param name="gentext-key" select="'TableofContents'"/>
      <xsl:with-param name="content">
        <xsl:call-template name="division.toc">
          <xsl:with-param name="toc.title.p" 
                          select="contains($toc.params, 'title')"/>
        </xsl:call-template>
      </xsl:with-param>
    </xsl:call-template>
  </xsl:if>

  <xsl:if test="contains($toc.params,'figure') and .//figure">
    <xsl:call-template name="page.sequence">
      <xsl:with-param name="master-reference"
                      select="$lot-master-reference"/>
      <xsl:with-param name="element" select="'toc'"/>
      <xsl:with-param name="gentext-key" select="'ListofFigures'"/>
      <xsl:with-param name="content">
        <xsl:call-template name="list.of.titles">
          <xsl:with-param name="titles" select="'figure'"/>
          <xsl:with-param name="nodes" select=".//figure"/>
        </xsl:call-template>
      </xsl:with-param>
    </xsl:call-template>
  </xsl:if>

  <xsl:if test="contains($toc.params,'table') and .//table">
    <xsl:call-template name="page.sequence">
      <xsl:with-param name="master-reference"
                      select="$lot-master-reference"/>
      <xsl:with-param name="element" select="'toc'"/>
      <xsl:with-param name="gentext-key" select="'ListofTables'"/>
      <xsl:with-param name="content">
        <xsl:call-template name="list.of.titles">
          <xsl:with-param name="titles" select="'table'"/>
          <xsl:with-param name="nodes" select=".//table[not(@tocentry = 0)]"/>
        </xsl:call-template>
      </xsl:with-param>
    </xsl:call-template>
  </xsl:if>

  <xsl:if test="contains($toc.params,'example') and .//example">
    <xsl:call-template name="page.sequence">
      <xsl:with-param name="master-reference"
                      select="$lot-master-reference"/>
      <xsl:with-param name="element" select="'toc'"/>
      <xsl:with-param name="gentext-key" select="'ListofExample'"/>
      <xsl:with-param name="content">
        <xsl:call-template name="list.of.titles">
          <xsl:with-param name="titles" select="'example'"/>
          <xsl:with-param name="nodes" select=".//example"/>
        </xsl:call-template>
      </xsl:with-param>
    </xsl:call-template>
  </xsl:if>

  <xsl:if test="contains($toc.params,'equation') and 
                 .//equation[title or info/title]">
    <xsl:call-template name="page.sequence">
      <xsl:with-param name="master-reference"
                      select="$lot-master-reference"/>
      <xsl:with-param name="element" select="'toc'"/>
      <xsl:with-param name="gentext-key" select="'ListofEquations'"/>
      <xsl:with-param name="content">
        <xsl:call-template name="list.of.titles">
          <xsl:with-param name="titles" select="'equation'"/>
          <xsl:with-param name="nodes" 
                          select=".//equation[title or info/title]"/>
        </xsl:call-template>
      </xsl:with-param>
    </xsl:call-template>
  </xsl:if>

  <xsl:if test="contains($toc.params,'procedure') and 
                 .//procedure[title or info/title]">
    <xsl:call-template name="page.sequence">
      <xsl:with-param name="master-reference"
                      select="$lot-master-reference"/>
      <xsl:with-param name="element" select="'toc'"/>
      <xsl:with-param name="gentext-key" select="'ListofProcedures'"/>
      <xsl:with-param name="content">
        <xsl:call-template name="list.of.titles">
          <xsl:with-param name="titles" select="'procedure'"/>
          <xsl:with-param name="nodes" 
                          select=".//procedure[title or info/title]"/>
        </xsl:call-template>
      </xsl:with-param>
    </xsl:call-template>
  </xsl:if>
</xsl:template>
<!-- ==================================================================== -->

<xsl:template match="part">
  <xsl:if test="not(partintro)">
    <xsl:apply-templates select="." mode="part.titlepage.mode"/>
    <xsl:call-template name="generate.part.toc"/>
  </xsl:if>
  <xsl:apply-templates/>
</xsl:template>

<xsl:template match="part" mode="part.titlepage.mode">
  <!-- done this way to force the context node to be the part -->
  <xsl:param name="additional.content"/>

  <xsl:variable name="id">
    <xsl:call-template name="object.id"/>
  </xsl:variable>

  <xsl:variable name="titlepage-master-reference">
    <xsl:call-template name="select.pagemaster">
      <xsl:with-param name="pageclass" select="'titlepage'"/>
    </xsl:call-template>
  </xsl:variable>

  <fo:page-sequence hyphenate="{$hyphenate}"
                    master-reference="{$titlepage-master-reference}">
    <xsl:attribute name="language">
      <xsl:call-template name="l10n.language"/>
    </xsl:attribute>
    <xsl:attribute name="format">
      <xsl:call-template name="page.number.format">
        <xsl:with-param name="master-reference" 
                        select="$titlepage-master-reference"/>
      </xsl:call-template>
    </xsl:attribute>

    <xsl:attribute name="initial-page-number">
      <xsl:call-template name="initial.page.number">
        <xsl:with-param name="master-reference" 
                        select="$titlepage-master-reference"/>
      </xsl:call-template>
    </xsl:attribute>

    <xsl:attribute name="force-page-count">
      <xsl:call-template name="force.page.count">
        <xsl:with-param name="master-reference" 
                        select="$titlepage-master-reference"/>
      </xsl:call-template>
    </xsl:attribute>

    <xsl:attribute name="hyphenation-character">
      <xsl:call-template name="gentext">
        <xsl:with-param name="key" select="'hyphenation-character'"/>
      </xsl:call-template>
    </xsl:attribute>
    <xsl:attribute name="hyphenation-push-character-count">
      <xsl:call-template name="gentext">
        <xsl:with-param name="key" select="'hyphenation-push-character-count'"/>
      </xsl:call-template>
    </xsl:attribute>
    <xsl:attribute name="hyphenation-remain-character-count">
      <xsl:call-template name="gentext">
        <xsl:with-param name="key" select="'hyphenation-remain-character-count'"/>
      </xsl:call-template>
    </xsl:attribute>

    <xsl:apply-templates select="." mode="running.head.mode">
      <xsl:with-param name="master-reference" select="$titlepage-master-reference"/>
    </xsl:apply-templates>

    <xsl:apply-templates select="." mode="running.foot.mode">
      <xsl:with-param name="master-reference" select="$titlepage-master-reference"/>
    </xsl:apply-templates>

    <fo:flow flow-name="xsl-region-body">
      <xsl:call-template name="set.flow.properties">
        <xsl:with-param name="element" select="local-name(.)"/>
        <xsl:with-param name="master-reference" 
                        select="$titlepage-master-reference"/>
      </xsl:call-template>

      <fo:block id="{$id}">
        <xsl:call-template name="part.titlepage"/>
      </fo:block>
      <xsl:copy-of select="$additional.content"/>
    </fo:flow>
  </fo:page-sequence>
</xsl:template>

<xsl:template match="part/docinfo|partinfo"></xsl:template>
<xsl:template match="part/info"></xsl:template>
<xsl:template match="part/title"></xsl:template>
<xsl:template match="part/subtitle"></xsl:template>
<xsl:template match="part/titleabbrev"></xsl:template>

<!-- ==================================================================== -->

<xsl:template name="generate.part.toc">
  <xsl:param name="part" select="."/>

  <xsl:variable name="lot-master-reference">
    <xsl:call-template name="select.pagemaster">
      <xsl:with-param name="pageclass" select="'lot'"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:variable name="toc.params">
    <xsl:call-template name="find.path.params">
      <xsl:with-param name="node" select="$part"/>
      <xsl:with-param name="table" select="normalize-space($generate.toc)"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:variable name="nodes" select="$part/reference|
                                     $part/preface|
                                     $part/chapter|
                                     $part/appendix|
                                     $part/article|
                                     $part/bibliography|
                                     $part/glossary|
                                     $part/index"/>

  <xsl:if test="count($nodes) &gt; 0 and contains($toc.params, 'toc')">
    <fo:page-sequence hyphenate="{$hyphenate}"
                      master-reference="{$lot-master-reference}">
      <xsl:attribute name="language">
        <xsl:call-template name="l10n.language"/>
      </xsl:attribute>
      <xsl:attribute name="format">
        <xsl:call-template name="page.number.format">
          <xsl:with-param name="element" select="'toc'"/>
          <xsl:with-param name="master-reference" 
                          select="$lot-master-reference"/>
        </xsl:call-template>
      </xsl:attribute>
      <xsl:attribute name="initial-page-number">
        <xsl:call-template name="initial.page.number">
          <xsl:with-param name="element" select="'toc'"/>
          <xsl:with-param name="master-reference" 
                          select="$lot-master-reference"/>
         </xsl:call-template>
      </xsl:attribute>
      <xsl:attribute name="force-page-count">
        <xsl:call-template name="force.page.count">
          <xsl:with-param name="master-reference" 
                          select="$lot-master-reference"/>
        </xsl:call-template>
      </xsl:attribute>

      <xsl:attribute name="hyphenation-character">
        <xsl:call-template name="gentext">
          <xsl:with-param name="key" select="'hyphenation-character'"/>
        </xsl:call-template>
      </xsl:attribute>
      <xsl:attribute name="hyphenation-push-character-count">
        <xsl:call-template name="gentext">
          <xsl:with-param name="key" select="'hyphenation-push-character-count'"/>
        </xsl:call-template>
      </xsl:attribute>
      <xsl:attribute name="hyphenation-remain-character-count">
        <xsl:call-template name="gentext">
          <xsl:with-param name="key" select="'hyphenation-remain-character-count'"/>
        </xsl:call-template>
      </xsl:attribute>

      <xsl:apply-templates select="$part" mode="running.head.mode">
        <xsl:with-param name="master-reference" select="$lot-master-reference"/>
      </xsl:apply-templates>

      <xsl:apply-templates select="$part" mode="running.foot.mode">
        <xsl:with-param name="master-reference" select="$lot-master-reference"/>
      </xsl:apply-templates>

      <fo:flow flow-name="xsl-region-body">
        <xsl:call-template name="set.flow.properties">
          <xsl:with-param name="element" select="local-name(.)"/>
          <xsl:with-param name="master-reference" 
                          select="$lot-master-reference"/>
        </xsl:call-template>

        <xsl:call-template name="division.toc">
          <xsl:with-param name="toc-context" select="$part"/>
          <xsl:with-param name="toc.title.p" 
                          select="contains($toc.params, 'title')"/>
        </xsl:call-template>

      </fo:flow>
    </fo:page-sequence>
  </xsl:if>
</xsl:template>

<!-- ==================================================================== -->

<xsl:template match="part/partintro">
  <xsl:apply-templates select=".." mode="part.titlepage.mode">
    <xsl:with-param name="additional.content">
      <xsl:if test="title">
        <xsl:call-template name="partintro.titlepage"/>
      </xsl:if>
      <xsl:apply-templates/>
    </xsl:with-param>
  </xsl:apply-templates>

  <xsl:call-template name="generate.part.toc">
    <xsl:with-param name="part" select=".."/>
  </xsl:call-template>
</xsl:template>

<xsl:template match="partintro/title"></xsl:template>
<xsl:template match="partintro/subtitle"></xsl:template>
<xsl:template match="partintro/titleabbrev"></xsl:template>

<!-- ==================================================================== -->

<xsl:template match="book" mode="division.number">
  <xsl:number from="set" count="book" format="1."/>
</xsl:template>

<xsl:template match="part" mode="division.number">
  <xsl:number from="book" count="part" format="I."/>
</xsl:template>

</xsl:stylesheet>

