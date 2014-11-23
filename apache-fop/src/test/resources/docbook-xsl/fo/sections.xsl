<?xml version='1.0'?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fo="http://www.w3.org/1999/XSL/Format"
                xmlns:axf="http://www.antennahouse.com/names/XSL/Extensions"
                version='1.0'>

<!-- ********************************************************************
     $Id: sections.xsl 9647 2012-10-26 17:42:03Z bobstayton $
     ********************************************************************

     This file is part of the XSL DocBook Stylesheet distribution.
     See ../README or http://docbook.sf.net/release/xsl/current/ for
     copyright and other information.

     ******************************************************************** -->

<!-- ==================================================================== -->

<xsl:template match="section">
  <xsl:choose>
    <xsl:when test="$rootid = @id or $rootid = @xml:id">
      <xsl:call-template name="section.page.sequence"/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:variable name="id">
        <xsl:call-template name="object.id"/>
      </xsl:variable>

      <xsl:variable name="renderas">
        <xsl:choose>
          <xsl:when test="@renderas = 'sect1'">1</xsl:when>
          <xsl:when test="@renderas = 'sect2'">2</xsl:when>
          <xsl:when test="@renderas = 'sect3'">3</xsl:when>
          <xsl:when test="@renderas = 'sect4'">4</xsl:when>
          <xsl:when test="@renderas = 'sect5'">5</xsl:when>
          <xsl:otherwise><xsl:value-of select="''"/></xsl:otherwise>
        </xsl:choose>
      </xsl:variable>

      <xsl:variable name="level">
        <xsl:choose>
          <xsl:when test="$renderas != ''">
            <xsl:value-of select="$renderas"/>
          </xsl:when>
          <xsl:otherwise>
            <xsl:call-template name="section.level"/>
          </xsl:otherwise>
        </xsl:choose>
      </xsl:variable>

      <!-- xsl:use-attribute-sets takes only a Qname, not a variable -->
      <xsl:choose>
        <xsl:when test="$level = 1">
          <xsl:element name="fo:{$section.container.element}"
		       use-attribute-sets="section.level1.properties">
            <xsl:attribute name="id"><xsl:value-of 
                                select="$id"/></xsl:attribute>
            <xsl:call-template name="section.content"/>
          </xsl:element>
        </xsl:when>
        <xsl:when test="$level = 2">
          <xsl:element name="fo:{$section.container.element}"
		       use-attribute-sets="section.level2.properties">
            <xsl:attribute name="id"><xsl:value-of 
                                select="$id"/></xsl:attribute>
            <xsl:call-template name="section.content"/>
          </xsl:element>
        </xsl:when>
        <xsl:when test="$level = 3">
          <xsl:element name="fo:{$section.container.element}"
                       use-attribute-sets="section.level3.properties">
            <xsl:attribute name="id"><xsl:value-of 
                                select="$id"/></xsl:attribute>
            <xsl:call-template name="section.content"/>
          </xsl:element>
        </xsl:when>
        <xsl:when test="$level = 4">
          <xsl:element name="fo:{$section.container.element}"
                       use-attribute-sets="section.level4.properties">
            <xsl:attribute name="id"><xsl:value-of 
                                select="$id"/></xsl:attribute>
            <xsl:call-template name="section.content"/>
          </xsl:element>
        </xsl:when>
        <xsl:when test="$level = 5">
          <xsl:element name="fo:{$section.container.element}"
		       use-attribute-sets="section.level5.properties">
            <xsl:attribute name="id"><xsl:value-of 
                                select="$id"/></xsl:attribute>
            <xsl:call-template name="section.content"/>
          </xsl:element>
        </xsl:when>
        <xsl:otherwise>
          <xsl:element name="fo:{$section.container.element}"
		       use-attribute-sets="section.level6.properties">
            <xsl:attribute name="id"><xsl:value-of 
                                select="$id"/></xsl:attribute>
            <xsl:call-template name="section.content"/>
          </xsl:element>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template name="section.content">
  <xsl:call-template name="section.titlepage"/>

  <xsl:variable name="toc.params">
    <xsl:call-template name="find.path.params">
      <xsl:with-param name="table" select="normalize-space($generate.toc)"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:if test="contains($toc.params, 'toc')
                and (count(ancestor::section)+1) &lt;= 
		$generate.section.toc.level">
    <xsl:call-template name="section.toc">
      <xsl:with-param name="toc.title.p" 
                      select="contains($toc.params, 'title')"/>
    </xsl:call-template>
   <xsl:call-template name="section.toc.separator"/>
  </xsl:if>

  <xsl:apply-templates/>
</xsl:template>

<xsl:template match="/section" name="section.page.sequence">
  <xsl:variable name="id">
    <xsl:call-template name="object.id"/>
  </xsl:variable>

  <xsl:variable name="master-reference">
    <xsl:call-template name="select.pagemaster"/>
  </xsl:variable>

  <fo:page-sequence hyphenate="{$hyphenate}"
                    master-reference="{$master-reference}">
    <xsl:attribute name="language">
      <xsl:call-template name="l10n.language"/>
    </xsl:attribute>
    <xsl:attribute name="format">
      <xsl:call-template name="page.number.format">
        <xsl:with-param name="master-reference" select="$master-reference"/>
      </xsl:call-template>
    </xsl:attribute>

    <xsl:attribute name="initial-page-number">
      <xsl:call-template name="initial.page.number">
        <xsl:with-param name="master-reference" select="$master-reference"/>
      </xsl:call-template>
    </xsl:attribute>

    <xsl:attribute name="force-page-count">
      <xsl:call-template name="force.page.count">
        <xsl:with-param name="master-reference" select="$master-reference"/>
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
      <xsl:with-param name="master-reference" select="$master-reference"/>
    </xsl:apply-templates>
    <xsl:apply-templates select="." mode="running.foot.mode">
      <xsl:with-param name="master-reference" select="$master-reference"/>
    </xsl:apply-templates>

    <fo:flow flow-name="xsl-region-body">
      <xsl:call-template name="set.flow.properties">
        <xsl:with-param name="element" select="local-name(.)"/>
        <xsl:with-param name="master-reference" select="$master-reference"/>
      </xsl:call-template>

      <fo:block id="{$id}" 
            xsl:use-attribute-sets="section.level1.properties">
        <xsl:call-template name="section.titlepage"/>
      </fo:block>

      <xsl:variable name="toc.params">
        <xsl:call-template name="find.path.params">
          <xsl:with-param name="table" select="normalize-space($generate.toc)"/>
        </xsl:call-template>
      </xsl:variable>

      <xsl:if test="contains($toc.params, 'toc')
                    and (count(ancestor::section)+1) &lt;= 
		    $generate.section.toc.level">
        <xsl:call-template name="section.toc">
          <xsl:with-param name="toc.title.p" 
                          select="contains($toc.params, 'title')"/>
        </xsl:call-template>
        <xsl:call-template name="section.toc.separator"/>
      </xsl:if>

      <xsl:apply-templates/>
   </fo:flow>
  </fo:page-sequence>
</xsl:template>

<xsl:template match="section/title
                     |simplesect/title
                     |sect1/title
                     |sect2/title
                     |sect3/title
                     |sect4/title
                     |sect5/title
                     |section/info/title
                     |simplesect/info/title
                     |sect1/info/title
                     |sect2/info/title
                     |sect3/info/title
                     |sect4/info/title
                     |sect5/info/title
                     |section/sectioninfo/title
                     |sect1/sect1info/title
                     |sect2/sect2info/title
                     |sect3/sect3info/title
                     |sect4/sect4info/title
                     |sect5/sect5info/title"
              mode="titlepage.mode"
              priority="2">

  <xsl:variable name="section" 
                select="(ancestor::section |
                        ancestor::simplesect |
                        ancestor::sect1 |
                        ancestor::sect2 |
                        ancestor::sect3 |
                        ancestor::sect4 |
                        ancestor::sect5)[position() = last()]"/>

  <fo:block keep-with-next.within-column="always">
    <xsl:variable name="id">
      <xsl:call-template name="object.id">
        <xsl:with-param name="object" select="$section"/>
      </xsl:call-template>
    </xsl:variable>

    <xsl:variable name="renderas">
      <xsl:choose>
        <xsl:when test="$section/@renderas = 'sect1'">1</xsl:when>
        <xsl:when test="$section/@renderas = 'sect2'">2</xsl:when>
        <xsl:when test="$section/@renderas = 'sect3'">3</xsl:when>
        <xsl:when test="$section/@renderas = 'sect4'">4</xsl:when>
        <xsl:when test="$section/@renderas = 'sect5'">5</xsl:when>
        <xsl:otherwise><xsl:value-of select="''"/></xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
  
    <xsl:variable name="level">
      <xsl:choose>
        <xsl:when test="$renderas != ''">
          <xsl:value-of select="$renderas"/>
        </xsl:when>
        <xsl:otherwise>
          <xsl:call-template name="section.level">
            <xsl:with-param name="node" select="$section"/>
          </xsl:call-template>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:variable>

    <xsl:variable name="marker">
      <xsl:choose>
        <xsl:when test="$level &lt;= $marker.section.level">1</xsl:when>
        <xsl:otherwise>0</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>

    <xsl:variable name="title">
      <xsl:apply-templates select="$section" mode="object.title.markup">
        <xsl:with-param name="allow-anchors" select="1"/>
      </xsl:apply-templates>
    </xsl:variable>

    <xsl:variable name="marker.title">
      <xsl:apply-templates select="$section" mode="titleabbrev.markup">
        <xsl:with-param name="allow-anchors" select="0"/>
      </xsl:apply-templates>
    </xsl:variable>

    <xsl:if test="$axf.extensions != 0">
      <xsl:attribute name="axf:outline-level">
        <xsl:value-of select="count(ancestor::*)-1"/>
      </xsl:attribute>
      <xsl:attribute name="axf:outline-expand">false</xsl:attribute>
      <xsl:attribute name="axf:outline-title">
        <xsl:value-of select="normalize-space($title)"/>
      </xsl:attribute>
    </xsl:if>

    <xsl:call-template name="section.heading">
      <xsl:with-param name="level" select="$level"/>
      <xsl:with-param name="title" select="$title"/>
      <xsl:with-param name="marker" select="$marker"/>
      <xsl:with-param name="marker.title" select="$marker.title"/>
    </xsl:call-template>
  </fo:block>
</xsl:template>

<xsl:template match="sect1">
  <xsl:variable name="id">
    <xsl:call-template name="object.id"/>
  </xsl:variable>

  <xsl:element name="fo:{$section.container.element}"
               use-attribute-sets="section.level1.properties">
    <xsl:attribute name="id"><xsl:value-of 
                        select="$id"/></xsl:attribute>
    <xsl:call-template name="sect1.titlepage"/>

    <xsl:variable name="toc.params">
      <xsl:call-template name="find.path.params">
        <xsl:with-param name="table" select="normalize-space($generate.toc)"/>
      </xsl:call-template>
    </xsl:variable>

    <xsl:if test="contains($toc.params, 'toc')
                  and $generate.section.toc.level &gt;= 1">
      <xsl:call-template name="section.toc">
        <xsl:with-param name="toc.title.p" 
                        select="contains($toc.params, 'title')"/>
      </xsl:call-template>
      <xsl:call-template name="section.toc.separator"/>
    </xsl:if>

    <xsl:apply-templates/>
  </xsl:element>
</xsl:template>

<xsl:template match="/sect1">
  <xsl:variable name="id">
    <xsl:call-template name="object.id"/>
  </xsl:variable>
  <xsl:variable name="master-reference">
    <xsl:call-template name="select.pagemaster"/>
  </xsl:variable>

  <fo:page-sequence hyphenate="{$hyphenate}"
                    master-reference="{$master-reference}">
    <xsl:attribute name="language">
      <xsl:call-template name="l10n.language"/>
    </xsl:attribute>
    <xsl:attribute name="format">
      <xsl:call-template name="page.number.format">
        <xsl:with-param name="master-reference" select="$master-reference"/>
      </xsl:call-template>
    </xsl:attribute>

    <xsl:attribute name="initial-page-number">
      <xsl:call-template name="initial.page.number">
        <xsl:with-param name="master-reference" select="$master-reference"/>
      </xsl:call-template>
    </xsl:attribute>

    <xsl:attribute name="force-page-count">
      <xsl:call-template name="force.page.count">
        <xsl:with-param name="master-reference" select="$master-reference"/>
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
      <xsl:with-param name="master-reference" select="$master-reference"/>
    </xsl:apply-templates>
    <xsl:apply-templates select="." mode="running.foot.mode">
      <xsl:with-param name="master-reference" select="$master-reference"/>
    </xsl:apply-templates>

    <fo:flow flow-name="xsl-region-body">
      <xsl:call-template name="set.flow.properties">
        <xsl:with-param name="element" select="local-name(.)"/>
        <xsl:with-param name="master-reference" select="$master-reference"/>
      </xsl:call-template>

      <fo:block id="{$id}" 
                xsl:use-attribute-sets="section.level1.properties">
        <xsl:call-template name="sect1.titlepage"/>
      </fo:block>

      <xsl:variable name="toc.params">
        <xsl:call-template name="find.path.params">
          <xsl:with-param name="table" select="normalize-space($generate.toc)"/>
        </xsl:call-template>
      </xsl:variable>

      <xsl:if test="contains($toc.params, 'toc')
                    and $generate.section.toc.level &gt;= 1">
        <xsl:call-template name="section.toc">
          <xsl:with-param name="toc.title.p" 
                          select="contains($toc.params, 'title')"/>
        </xsl:call-template>
        <xsl:call-template name="section.toc.separator"/>
      </xsl:if>

      <xsl:apply-templates/>
   </fo:flow>
  </fo:page-sequence>
</xsl:template>

<xsl:template match="sect2">
  <xsl:variable name="id">
    <xsl:call-template name="object.id"/>
  </xsl:variable>

  <xsl:element name="fo:{$section.container.element}"
	       use-attribute-sets="section.level2.properties">
    <xsl:attribute name="id"><xsl:value-of 
                        select="$id"/></xsl:attribute>
    <xsl:call-template name="sect2.titlepage"/>

    <xsl:variable name="toc.params">
      <xsl:call-template name="find.path.params">
        <xsl:with-param name="table" select="normalize-space($generate.toc)"/>
      </xsl:call-template>
    </xsl:variable>

    <xsl:if test="contains($toc.params, 'toc')
                   and $generate.section.toc.level &gt;= 2">
      <xsl:call-template name="section.toc">
        <xsl:with-param name="toc.title.p" 
                        select="contains($toc.params, 'title')"/>
      </xsl:call-template>
      <xsl:call-template name="section.toc.separator"/>
    </xsl:if>

    <xsl:apply-templates/>
  </xsl:element>
</xsl:template>

<xsl:template match="sect3">
  <xsl:variable name="id">
    <xsl:call-template name="object.id"/>
  </xsl:variable>

  <xsl:element name="fo:{$section.container.element}"
	       use-attribute-sets="section.level3.properties">
    <xsl:attribute name="id"><xsl:value-of 
                        select="$id"/></xsl:attribute>
    <xsl:call-template name="sect3.titlepage"/>

    <xsl:variable name="toc.params">
      <xsl:call-template name="find.path.params">
        <xsl:with-param name="table" select="normalize-space($generate.toc)"/>
      </xsl:call-template>
    </xsl:variable>

    <xsl:if test="contains($toc.params, 'toc')
                  and $generate.section.toc.level &gt;= 3">
      <xsl:call-template name="section.toc">
        <xsl:with-param name="toc.title.p" 
                        select="contains($toc.params, 'title')"/>
      </xsl:call-template>
      <xsl:call-template name="section.toc.separator"/>
    </xsl:if>

    <xsl:apply-templates/>
  </xsl:element>
</xsl:template>

<xsl:template match="sect4">
  <xsl:variable name="id">
    <xsl:call-template name="object.id"/>
  </xsl:variable>

  <xsl:element name="fo:{$section.container.element}"
	       use-attribute-sets="section.level4.properties">
    <xsl:attribute name="id"><xsl:value-of 
                        select="$id"/></xsl:attribute>
    <xsl:call-template name="sect4.titlepage"/>

    <xsl:variable name="toc.params">
      <xsl:call-template name="find.path.params">
        <xsl:with-param name="table" select="normalize-space($generate.toc)"/>
      </xsl:call-template>
    </xsl:variable>

    <xsl:if test="contains($toc.params, 'toc')
                  and $generate.section.toc.level &gt;= 4">
      <xsl:call-template name="section.toc">
        <xsl:with-param name="toc.title.p" 
                        select="contains($toc.params, 'title')"/>
      </xsl:call-template>
      <xsl:call-template name="section.toc.separator"/>
    </xsl:if>

    <xsl:apply-templates/>
  </xsl:element>
</xsl:template>

<xsl:template match="sect5">
  <xsl:variable name="id">
    <xsl:call-template name="object.id"/>
  </xsl:variable>

  <xsl:element name="fo:{$section.container.element}"
	       use-attribute-sets="section.level5.properties">
    <xsl:attribute name="id"><xsl:value-of 
                        select="$id"/></xsl:attribute>
    <xsl:call-template name="sect5.titlepage"/>

    <xsl:variable name="toc.params">
      <xsl:call-template name="find.path.params">
        <xsl:with-param name="table" select="normalize-space($generate.toc)"/>
      </xsl:call-template>
    </xsl:variable>

    <xsl:if test="contains($toc.params, 'toc')
                  and $generate.section.toc.level &gt;= 5">
      <xsl:call-template name="section.toc">
        <xsl:with-param name="toc.title.p" 
                        select="contains($toc.params, 'title')"/>
      </xsl:call-template>
      <xsl:call-template name="section.toc.separator"/>
    </xsl:if>

    <xsl:apply-templates/>
  </xsl:element>
</xsl:template>

<xsl:template match="simplesect">
  <xsl:variable name="id">
    <xsl:call-template name="object.id"/>
  </xsl:variable>

  <xsl:element name="fo:{$section.container.element}">
    <xsl:attribute name="id"><xsl:value-of 
                        select="$id"/></xsl:attribute>
    <xsl:call-template name="simplesect.titlepage"/>
    <xsl:apply-templates/>
  </xsl:element>
</xsl:template>

<xsl:template match="sectioninfo"></xsl:template>
<xsl:template match="section/info"></xsl:template>
<xsl:template match="section/title"></xsl:template>
<xsl:template match="section/titleabbrev"></xsl:template>
<xsl:template match="section/subtitle"></xsl:template>

<xsl:template match="sect1info"></xsl:template>
<xsl:template match="sect1/info"></xsl:template>
<xsl:template match="sect1/title"></xsl:template>
<xsl:template match="sect1/titleabbrev"></xsl:template>
<xsl:template match="sect1/subtitle"></xsl:template>

<xsl:template match="sect2info"></xsl:template>
<xsl:template match="sect2/info"></xsl:template>
<xsl:template match="sect2/title"></xsl:template>
<xsl:template match="sect2/titleabbrev"></xsl:template>
<xsl:template match="sect2/subtitle"></xsl:template>

<xsl:template match="sect3info"></xsl:template>
<xsl:template match="sect3/info"></xsl:template>
<xsl:template match="sect3/title"></xsl:template>
<xsl:template match="sect3/titleabbrev"></xsl:template>
<xsl:template match="sect3/subtitle"></xsl:template>

<xsl:template match="sect4info"></xsl:template>
<xsl:template match="sect4/info"></xsl:template>
<xsl:template match="sect4/title"></xsl:template>
<xsl:template match="sect4/titleabbrev"></xsl:template>
<xsl:template match="sect4/subtitle"></xsl:template>

<xsl:template match="sect5info"></xsl:template>
<xsl:template match="sect5/info"></xsl:template>
<xsl:template match="sect5/title"></xsl:template>
<xsl:template match="sect5/titleabbrev"></xsl:template>
<xsl:template match="sect5/subtitle"></xsl:template>

<xsl:template match="simplesect/info"></xsl:template>
<xsl:template match="simplesect/title"></xsl:template>
<xsl:template match="simplesect/titleabbrev"></xsl:template>
<xsl:template match="simplesect/subtitle"></xsl:template>

<!-- ==================================================================== -->

<xsl:template name="section.heading">
  <xsl:param name="level" select="1"/>
  <xsl:param name="marker" select="1"/>
  <xsl:param name="title"/>
  <xsl:param name="marker.title"/>

  <fo:block xsl:use-attribute-sets="section.title.properties">
    <xsl:if test="$marker != 0">
      <fo:marker marker-class-name="section.head.marker">
        <xsl:copy-of select="$marker.title"/>
      </fo:marker>
    </xsl:if>

    <xsl:choose>
      <xsl:when test="$level=1">
        <fo:block xsl:use-attribute-sets="section.title.level1.properties">
          <xsl:copy-of select="$title"/>
        </fo:block>
      </xsl:when>
      <xsl:when test="$level=2">
        <fo:block xsl:use-attribute-sets="section.title.level2.properties">
          <xsl:copy-of select="$title"/>
        </fo:block>
      </xsl:when>
      <xsl:when test="$level=3">
        <fo:block xsl:use-attribute-sets="section.title.level3.properties">
          <xsl:copy-of select="$title"/>
        </fo:block>
      </xsl:when>
      <xsl:when test="$level=4">
        <fo:block xsl:use-attribute-sets="section.title.level4.properties">
          <xsl:copy-of select="$title"/>
        </fo:block>
      </xsl:when>
      <xsl:when test="$level=5">
        <fo:block xsl:use-attribute-sets="section.title.level5.properties">
          <xsl:copy-of select="$title"/>
        </fo:block>
      </xsl:when>
      <xsl:otherwise>
        <fo:block xsl:use-attribute-sets="section.title.level6.properties">
          <xsl:copy-of select="$title"/>
        </fo:block>
      </xsl:otherwise>
    </xsl:choose>
  </fo:block>
</xsl:template>

<!-- ==================================================================== -->

<xsl:template match="bridgehead">
  <xsl:variable name="container"
                select="(ancestor::appendix
                        |ancestor::article
                        |ancestor::bibliography
                        |ancestor::chapter
                        |ancestor::glossary
                        |ancestor::glossdiv
                        |ancestor::index
                        |ancestor::partintro
                        |ancestor::preface
                        |ancestor::refsect1
                        |ancestor::refsect2
                        |ancestor::refsect3
                        |ancestor::sect1
                        |ancestor::sect2
                        |ancestor::sect3
                        |ancestor::sect4
                        |ancestor::sect5
                        |ancestor::section
                        |ancestor::setindex
                        |ancestor::simplesect)[last()]"/>

  <xsl:variable name="clevel">
    <xsl:choose>
      <xsl:when test="local-name($container) = 'appendix'
                      or local-name($container) = 'chapter'
                      or local-name($container) = 'article'
                      or local-name($container) = 'bibliography'
                      or local-name($container) = 'glossary'
                      or local-name($container) = 'index'
                      or local-name($container) = 'partintro'
                      or local-name($container) = 'preface'
                      or local-name($container) = 'setindex'">2</xsl:when>
      <xsl:when test="local-name($container) = 'glossdiv'">
        <xsl:value-of select="count(ancestor::glossdiv)+2"/>
      </xsl:when>
      <xsl:when test="local-name($container) = 'sect1'
                      or local-name($container) = 'sect2'
                      or local-name($container) = 'sect3'
                      or local-name($container) = 'sect4'
                      or local-name($container) = 'sect5'
                      or local-name($container) = 'refsect1'
                      or local-name($container) = 'refsect2'
                      or local-name($container) = 'refsect3'
                      or local-name($container) = 'section'
                      or local-name($container) = 'simplesect'">
        <xsl:variable name="slevel">
          <xsl:call-template name="section.level">
            <xsl:with-param name="node" select="$container"/>
          </xsl:call-template>
        </xsl:variable>
        <xsl:value-of select="$slevel + 1"/>
      </xsl:when>
      <xsl:otherwise>2</xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:variable name="level">
    <xsl:choose>
      <xsl:when test="@renderas = 'sect1'">1</xsl:when>
      <xsl:when test="@renderas = 'sect2'">2</xsl:when>
      <xsl:when test="@renderas = 'sect3'">3</xsl:when>
      <xsl:when test="@renderas = 'sect4'">4</xsl:when>
      <xsl:when test="@renderas = 'sect5'">5</xsl:when>
      <xsl:otherwise>
        <xsl:value-of select="$clevel"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:variable name="marker">
    <xsl:choose>
      <xsl:when test="$level &lt;= $marker.section.level">1</xsl:when>
      <xsl:otherwise>0</xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:variable name="marker.title">
    <xsl:apply-templates/>
  </xsl:variable>

  <xsl:variable name="id">
    <xsl:call-template name="object.id"/>
  </xsl:variable>

  <fo:block id="{$id}">
    <xsl:call-template name="section.heading">
      <xsl:with-param name="level" select="$level"/>
      <xsl:with-param name="title">
        <xsl:apply-templates/>
      </xsl:with-param>
      <xsl:with-param name="marker" select="$marker"/>
      <xsl:with-param name="marker.title" select="$marker.title"/>
    </xsl:call-template>
  </fo:block>
</xsl:template>

</xsl:stylesheet>

