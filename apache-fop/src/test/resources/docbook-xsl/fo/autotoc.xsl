<?xml version='1.0'?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fo="http://www.w3.org/1999/XSL/Format"
                xmlns:axf="http://www.antennahouse.com/names/XSL/Extensions"
                version='1.0'>

<!-- ********************************************************************
     $Id: autotoc.xsl 9647 2012-10-26 17:42:03Z bobstayton $
     ********************************************************************

     This file is part of the XSL DocBook Stylesheet distribution.
     See ../README or http://docbook.sf.net/release/xsl/current/ for
     copyright and other information.

     ******************************************************************** -->

<!-- ==================================================================== -->

<xsl:template name="set.toc">

  <xsl:param name="toc-context" select="."/>

  <xsl:variable name="id">
    <xsl:call-template name="object.id"/>
  </xsl:variable>

  <xsl:variable name="cid">
    <xsl:call-template name="object.id">
      <xsl:with-param name="object" select="$toc-context"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:variable name="nodes" select="book|set|setindex"/>

  <xsl:if test="$nodes">
    <fo:block id="toc...{$id}"
              xsl:use-attribute-sets="toc.margin.properties">
      <xsl:if test="$axf.extensions != 0">
        <xsl:attribute name="axf:outline-level">1</xsl:attribute>
        <xsl:attribute name="axf:outline-expand">false</xsl:attribute>
        <xsl:attribute name="axf:outline-title">
          <xsl:call-template name="gentext">
            <xsl:with-param name="key" select="'TableofContents'"/>
          </xsl:call-template>
        </xsl:attribute>
      </xsl:if>
      <xsl:call-template name="table.of.contents.titlepage"/>
      <xsl:apply-templates select="$nodes" mode="toc">
        <xsl:with-param name="toc-context" select="$toc-context"/>
      </xsl:apply-templates>
    </fo:block>
  </xsl:if>
</xsl:template>

<xsl:template name="division.toc">
  <xsl:param name="toc-context" select="."/>
  <xsl:param name="toc.title.p" select="true()"/>

  <xsl:variable name="cid">
    <xsl:call-template name="object.id">
      <xsl:with-param name="object" select="$toc-context"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:variable name="nodes"
                select="$toc-context/part
                        |$toc-context/reference
                        |$toc-context/preface
                        |$toc-context/chapter
                        |$toc-context/appendix
                        |$toc-context/article
                        |$toc-context/topic
                        |$toc-context/bibliography
                        |$toc-context/glossary
                        |$toc-context/index"/>

  <xsl:if test="$nodes">
    <fo:block id="toc...{$cid}"
              xsl:use-attribute-sets="toc.margin.properties">
      <xsl:if test="$axf.extensions != 0">
        <xsl:attribute name="axf:outline-level">1</xsl:attribute>
        <xsl:attribute name="axf:outline-expand">false</xsl:attribute>
        <xsl:attribute name="axf:outline-title">
          <xsl:call-template name="gentext">
            <xsl:with-param name="key" select="'TableofContents'"/>
          </xsl:call-template>
        </xsl:attribute>
      </xsl:if>
      <xsl:if test="$toc.title.p">
        <xsl:call-template name="table.of.contents.titlepage"/>
      </xsl:if>
      <xsl:apply-templates select="$nodes" mode="toc">
        <xsl:with-param name="toc-context" select="$toc-context"/>
      </xsl:apply-templates>
    </fo:block>
  </xsl:if>
</xsl:template>

<xsl:template name="component.toc">
  <xsl:param name="toc-context" select="."/>
  <xsl:param name="toc.title.p" select="true()"/>

  <xsl:variable name="id">
    <xsl:call-template name="object.id"/>
  </xsl:variable>

  <xsl:variable name="cid">
    <xsl:call-template name="object.id">
      <xsl:with-param name="object" select="$toc-context"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:variable name="nodes" select="section|sect1|refentry
                                     |article|topic|bibliography|glossary
                                     |qandaset[$qanda.in.toc != 0]
                                     |appendix|index"/>
  <xsl:if test="$nodes">
    <fo:block id="toc...{$id}"
              xsl:use-attribute-sets="toc.margin.properties">
      <xsl:if test="$toc.title.p">
        <xsl:call-template name="table.of.contents.titlepage"/>
      </xsl:if>
      <xsl:apply-templates select="$nodes" mode="toc">
        <xsl:with-param name="toc-context" select="$toc-context"/>
      </xsl:apply-templates>
    </fo:block>
  </xsl:if>
</xsl:template>

<xsl:template name="component.toc.separator">
  <!-- Customize to output something between
       component.toc and first output -->
</xsl:template>

<xsl:template name="section.toc">
  <xsl:param name="toc-context" select="."/>
  <xsl:param name="toc.title.p" select="true()"/>

  <xsl:variable name="id">
    <xsl:call-template name="object.id"/>
  </xsl:variable>

  <xsl:variable name="cid">
    <xsl:call-template name="object.id">
      <xsl:with-param name="object" select="$toc-context"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:variable name="nodes"
                select="section|sect1|sect2|sect3|sect4|sect5|refentry
                        |qandaset[$qanda.in.toc != 0]
                        |bridgehead[$bridgehead.in.toc != 0]"/>

  <xsl:variable name="level">
    <xsl:call-template name="section.level"/>
  </xsl:variable>

  <xsl:if test="$nodes">
    <fo:block id="toc...{$id}"
              xsl:use-attribute-sets="toc.margin.properties">

      <xsl:if test="$toc.title.p">
        <xsl:call-template name="section.heading">
          <xsl:with-param name="level" select="$level + 1"/>
          <xsl:with-param name="title">
            <fo:block space-after="0.5em">
              <xsl:call-template name="gentext">
                <xsl:with-param name="key" select="'TableofContents'"/>
              </xsl:call-template>
            </fo:block>
          </xsl:with-param>
        </xsl:call-template>
      </xsl:if>

      <xsl:apply-templates select="$nodes" mode="toc">
        <xsl:with-param name="toc-context" select="$toc-context"/>
      </xsl:apply-templates>
    </fo:block>
  </xsl:if>
</xsl:template>

<xsl:template name="section.toc.separator">
  <!-- Customize to output something between
       section.toc and first output -->
</xsl:template>
<!-- ==================================================================== -->

<xsl:template name="toc.line">
  <xsl:param name="toc-context" select="NOTANODE"/>

  <xsl:variable name="id">
    <xsl:call-template name="object.id"/>
  </xsl:variable>

  <xsl:variable name="label">
    <xsl:apply-templates select="." mode="label.markup"/>
  </xsl:variable>

  <fo:block xsl:use-attribute-sets="toc.line.properties">
    <fo:inline keep-with-next.within-line="always">
      <fo:basic-link internal-destination="{$id}">
        <xsl:if test="$label != ''">
          <xsl:copy-of select="$label"/>
          <xsl:value-of select="$autotoc.label.separator"/>
        </xsl:if>
        <xsl:apply-templates select="." mode="titleabbrev.markup"/>
      </fo:basic-link>
    </fo:inline>
    <fo:inline keep-together.within-line="always">
      <xsl:text> </xsl:text>
      <fo:leader leader-pattern="dots"
                 leader-pattern-width="3pt"
                 leader-alignment="reference-area"
                 keep-with-next.within-line="always"/>
      <xsl:text> </xsl:text> 
      <fo:basic-link internal-destination="{$id}">
        <fo:page-number-citation ref-id="{$id}"/>
      </fo:basic-link>
    </fo:inline>
  </fo:block>
</xsl:template>

<!-- ==================================================================== -->
<xsl:template name="qandaset.toc">
  <xsl:param name="toc-context" select="."/>
  <xsl:param name="toc.title.p" select="true()"/>

  <xsl:variable name="id">
    <xsl:call-template name="object.id"/>
  </xsl:variable>

  <xsl:variable name="cid">
    <xsl:call-template name="object.id">
      <xsl:with-param name="object" select="$toc-context"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:variable name="nodes" select="qandadiv|qandaentry"/>

  <xsl:if test="$nodes">
    <fo:block id="toc...{$id}"
              xsl:use-attribute-sets="toc.margin.properties">
      <xsl:if test="$toc.title.p">
        <xsl:call-template name="table.of.contents.titlepage"/>
      </xsl:if>
      <xsl:apply-templates select="$nodes" mode="toc">
        <xsl:with-param name="toc-context" select="$toc-context"/>
      </xsl:apply-templates>
    </fo:block>
  </xsl:if>
</xsl:template>

<xsl:template name="qandaset.toc.separator">
  <!-- Customize to output something between
       qandaset.toc and first output -->
</xsl:template>

<xsl:template match="qandadiv" mode="toc">
  <xsl:param name="toc-context" select="."/>

  <xsl:variable name="id">
    <xsl:call-template name="object.id"/>
  </xsl:variable>

  <xsl:variable name="cid">
    <xsl:call-template name="object.id">
      <xsl:with-param name="object" select="$toc-context"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:call-template name="toc.line">
    <xsl:with-param name="toc-context" select="$toc-context"/>
  </xsl:call-template>

  <xsl:variable name="nodes" select="qandadiv|qandaentry"/>

  <xsl:if test="$nodes">
    <fo:block id="toc.{$cid}.{$id}">
      <xsl:attribute name="margin-{$direction.align.start}">
        <xsl:call-template name="set.toc.indent"/>
      </xsl:attribute>

      <xsl:apply-templates select="$nodes" mode="toc">
        <xsl:with-param name="toc-context" select="$toc-context"/>
      </xsl:apply-templates>
    </fo:block>
  </xsl:if>
</xsl:template>

<xsl:template match="qandaentry" mode="toc">
  <xsl:apply-templates select="question" mode="toc"/>
</xsl:template>

<xsl:template match="question" mode="toc">
  <xsl:variable name="firstchunk">
    <!-- Use a titleabbrev or title if available -->
    <xsl:choose>
      <xsl:when test="../blockinfo/titleabbrev">
        <xsl:apply-templates select="../blockinfo/titleabbrev[1]/node()"/>
      </xsl:when>
      <xsl:when test="../blockinfo/title">
        <xsl:apply-templates select="../blockinfo/title[1]/node()"/>
      </xsl:when>
      <xsl:when test="../info/titleabbrev">
        <xsl:apply-templates select="../info/titleabbrev[1]/node()"/>
      </xsl:when>
      <xsl:when test="../titleabbrev">
        <xsl:apply-templates select="../titleabbrev[1]/node()"/>
      </xsl:when>
      <xsl:when test="../info/title">
        <xsl:apply-templates select="../info/title[1]/node()"/>
      </xsl:when>
      <xsl:when test="../title">
        <xsl:apply-templates select="../title[1]/node()"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:apply-templates select="(*[local-name(.)!='label'])[1]/node()"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:variable name="deflabel">
    <xsl:choose>
      <xsl:when test="ancestor-or-self::*[@defaultlabel]">
        <xsl:value-of select="(ancestor-or-self::*[@defaultlabel])[last()]
                              /@defaultlabel"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:value-of select="$qanda.defaultlabel"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:variable name="id">
    <xsl:call-template name="object.id"/>
  </xsl:variable>

  <xsl:variable name="label">
    <xsl:apply-templates select="." mode="label.markup"/>
  </xsl:variable>

  <fo:block xsl:use-attribute-sets="toc.line.properties"
            end-indent="{$toc.indent.width}pt"
            last-line-end-indent="-{$toc.indent.width}pt">
    <xsl:attribute name="margin-{$direction.align.start}">3em</xsl:attribute>
    <xsl:attribute name="text-indent">-3em</xsl:attribute>
    <fo:inline keep-with-next.within-line="always">
      <fo:basic-link internal-destination="{$id}">
        <xsl:if test="$label != ''">
          <xsl:copy-of select="$label"/>
          <xsl:if test="$deflabel = 'number' and not(label)">
            <xsl:value-of select="$autotoc.label.separator"/>
          </xsl:if>
	  <xsl:text> </xsl:text>
        </xsl:if>
        <xsl:copy-of select="$firstchunk"/>
      </fo:basic-link>
    </fo:inline>
    <fo:inline keep-together.within-line="always">
      <xsl:text> </xsl:text>
      <fo:leader leader-pattern="dots"
                 leader-pattern-width="3pt"
                 leader-alignment="reference-area"
                 keep-with-next.within-line="always"/>
      <xsl:text> </xsl:text> 
      <fo:basic-link internal-destination="{$id}">
        <fo:page-number-citation ref-id="{$id}"/>
      </fo:basic-link>
    </fo:inline>
  </fo:block>

</xsl:template>

<!-- ==================================================================== -->

<xsl:template match="book|setindex" mode="toc">
  <xsl:param name="toc-context" select="."/>

  <xsl:variable name="id">
    <xsl:call-template name="object.id"/>
  </xsl:variable>

  <xsl:variable name="cid">
    <xsl:call-template name="object.id">
      <xsl:with-param name="object" select="$toc-context"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:call-template name="toc.line">
    <xsl:with-param name="toc-context" select="$toc-context"/>
  </xsl:call-template>

  <xsl:variable name="nodes" select="glossary|bibliography|preface|chapter
                                     |reference|part|article|topic|appendix|index"/>

  <xsl:variable name="depth.from.context" select="count(ancestor::*)-count($toc-context/ancestor::*)"/>

  <xsl:if test="$toc.max.depth > $depth.from.context
                and $nodes">
    <fo:block id="toc.{$cid}.{$id}">
      <xsl:attribute name="margin-{$direction.align.start}">
        <xsl:call-template name="set.toc.indent"/>
      </xsl:attribute>

      <xsl:apply-templates select="$nodes" mode="toc">
        <xsl:with-param name="toc-context" select="$toc-context"/>
      </xsl:apply-templates>
    </fo:block>
  </xsl:if>
</xsl:template>

<xsl:template match="set" mode="toc">
  <xsl:param name="toc-context" select="."/>

  <xsl:variable name="id">
    <xsl:call-template name="object.id"/>
  </xsl:variable>

  <xsl:variable name="cid">
    <xsl:call-template name="object.id">
      <xsl:with-param name="object" select="$toc-context"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:call-template name="toc.line">
    <xsl:with-param name="toc-context" select="$toc-context"/>
  </xsl:call-template>

  <xsl:variable name="nodes" select="set|book|setindex"/>

  <xsl:variable name="depth.from.context" select="count(ancestor::*)-count($toc-context/ancestor::*)"/>

  <xsl:if test="$toc.max.depth > $depth.from.context
                and $nodes">
    <fo:block id="toc.{$cid}.{$id}">
      <xsl:attribute name="margin-{$direction.align.start}">
        <xsl:call-template name="set.toc.indent"/>
      </xsl:attribute>
      
      <xsl:apply-templates select="$nodes" mode="toc">
        <xsl:with-param name="toc-context" select="$toc-context"/>
      </xsl:apply-templates>
    </fo:block>
  </xsl:if>
</xsl:template>

<xsl:template match="part" mode="toc">
  <xsl:param name="toc-context" select="."/>

  <xsl:variable name="id">
    <xsl:call-template name="object.id"/>
  </xsl:variable>

  <xsl:variable name="cid">
    <xsl:call-template name="object.id">
      <xsl:with-param name="object" select="$toc-context"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:call-template name="toc.line">
    <xsl:with-param name="toc-context" select="$toc-context"/>
  </xsl:call-template>

  <xsl:variable name="nodes" select="chapter|appendix|preface|reference|
                                     refentry|article|topic|index|glossary|
                                     bibliography"/>

  <xsl:variable name="depth.from.context" select="count(ancestor::*)-count($toc-context/ancestor::*)"/>

  <xsl:if test="$toc.max.depth > $depth.from.context
                and $nodes">
    <fo:block id="toc.{$cid}.{$id}">
      <xsl:attribute name="margin-{$direction.align.start}">
        <xsl:call-template name="set.toc.indent"/>
      </xsl:attribute>
      
      <xsl:apply-templates select="$nodes" mode="toc">
        <xsl:with-param name="toc-context" select="$toc-context"/>
      </xsl:apply-templates>
    </fo:block>
  </xsl:if>
</xsl:template>

<xsl:template match="reference" mode="toc">
  <xsl:param name="toc-context" select="."/>

  <xsl:variable name="id">
    <xsl:call-template name="object.id"/>
  </xsl:variable>

  <xsl:variable name="cid">
    <xsl:call-template name="object.id">
      <xsl:with-param name="object" select="$toc-context"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:variable name="depth.from.context" select="count(ancestor::*)-count($toc-context/ancestor::*)"/>

  <xsl:call-template name="toc.line">
    <xsl:with-param name="toc-context" select="$toc-context"/>
  </xsl:call-template>

  <xsl:if test="$toc.section.depth > 0
                and $toc.max.depth > $depth.from.context
                and refentry">
    <fo:block id="toc.{$cid}.{$id}">
      <xsl:attribute name="margin-{$direction.align.start}">
        <xsl:call-template name="set.toc.indent"/>
      </xsl:attribute>
              
      <xsl:apply-templates select="refentry" mode="toc">
        <xsl:with-param name="toc-context" select="$toc-context"/>
      </xsl:apply-templates>
    </fo:block>
  </xsl:if>
</xsl:template>

<xsl:template match="refentry" mode="toc">
  <xsl:param name="toc-context" select="."/>

  <xsl:call-template name="toc.line">
    <xsl:with-param name="toc-context" select="$toc-context"/>
  </xsl:call-template>
</xsl:template>

<xsl:template match="preface|chapter|appendix|article"
              mode="toc">
  <xsl:param name="toc-context" select="."/>

  <xsl:variable name="id">
    <xsl:call-template name="object.id"/>
  </xsl:variable>

  <xsl:variable name="cid">
    <xsl:call-template name="object.id">
      <xsl:with-param name="object" select="$toc-context"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:call-template name="toc.line">
    <xsl:with-param name="toc-context" select="$toc-context"/>
  </xsl:call-template>

  <xsl:variable name="nodes" select="section|sect1
                                     |qandaset[$qanda.in.toc != 0]
                                     |simplesect[$simplesect.in.toc != 0]
                                     |topic
                                     |refentry|appendix"/>

  <xsl:variable name="depth.from.context" select="count(ancestor::*)-count($toc-context/ancestor::*)"/>

  <xsl:if test="$toc.section.depth > 0 
                and $toc.max.depth > $depth.from.context
                and $nodes">
    <fo:block id="toc.{$cid}.{$id}">
      <xsl:attribute name="margin-{$direction.align.start}">
        <xsl:call-template name="set.toc.indent"/>
      </xsl:attribute>
              
      <xsl:apply-templates select="$nodes" mode="toc">
        <xsl:with-param name="toc-context" select="$toc-context"/>
      </xsl:apply-templates>
    </fo:block>
  </xsl:if>
</xsl:template>

<xsl:template match="sect1" mode="toc">
  <xsl:param name="toc-context" select="."/>

  <xsl:variable name="id">
    <xsl:call-template name="object.id"/>
  </xsl:variable>

  <xsl:variable name="cid">
    <xsl:call-template name="object.id">
      <xsl:with-param name="object" select="$toc-context"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:call-template name="toc.line">
    <xsl:with-param name="toc-context" select="$toc-context"/>
  </xsl:call-template>

  <xsl:variable name="depth.from.context" select="count(ancestor::*)-count($toc-context/ancestor::*)"/>

  <xsl:if test="$toc.section.depth > 1 
                and $toc.max.depth > $depth.from.context
                and sect2">
    <fo:block id="toc.{$cid}.{$id}">
      <xsl:attribute name="margin-{$direction.align.start}">
        <xsl:call-template name="set.toc.indent"/>
      </xsl:attribute>
              
      <xsl:apply-templates select="sect2|qandaset[$qanda.in.toc != 0]" 
                           mode="toc">
        <xsl:with-param name="toc-context" select="$toc-context"/>
      </xsl:apply-templates>
    </fo:block>
  </xsl:if>
</xsl:template>

<xsl:template match="sect2" mode="toc">
  <xsl:param name="toc-context" select="."/>

  <xsl:variable name="id">
    <xsl:call-template name="object.id"/>
  </xsl:variable>

  <xsl:variable name="cid">
    <xsl:call-template name="object.id">
      <xsl:with-param name="object" select="$toc-context"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:call-template name="toc.line">
    <xsl:with-param name="toc-context" select="$toc-context"/>
  </xsl:call-template>

  <xsl:variable name="reldepth"
                select="count(ancestor::*)-count($toc-context/ancestor::*)"/>

  <xsl:variable name="depth.from.context" select="count(ancestor::*)-count($toc-context/ancestor::*)"/>

  <xsl:if test="$toc.section.depth > 2 
                and $toc.max.depth > $depth.from.context
                and sect3">
    <fo:block id="toc.{$cid}.{$id}">
      <xsl:attribute name="margin-{$direction.align.start}">
        <xsl:call-template name="set.toc.indent">
          <xsl:with-param name="reldepth" select="$reldepth"/>
        </xsl:call-template>
      </xsl:attribute>
              
      <xsl:apply-templates select="sect3|qandaset[$qanda.in.toc != 0]" 
                           mode="toc">
        <xsl:with-param name="toc-context" select="$toc-context"/>
      </xsl:apply-templates>
    </fo:block>
  </xsl:if>
</xsl:template>

<xsl:template match="sect3" mode="toc">
  <xsl:param name="toc-context" select="."/>

  <xsl:variable name="id">
    <xsl:call-template name="object.id"/>
  </xsl:variable>

  <xsl:variable name="cid">
    <xsl:call-template name="object.id">
      <xsl:with-param name="object" select="$toc-context"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:call-template name="toc.line">
    <xsl:with-param name="toc-context" select="$toc-context"/>
  </xsl:call-template>

  <xsl:variable name="reldepth"
                select="count(ancestor::*)-count($toc-context/ancestor::*)"/>

  <xsl:variable name="depth.from.context" select="count(ancestor::*)-count($toc-context/ancestor::*)"/>

  <xsl:if test="$toc.section.depth > 3 
                and $toc.max.depth > $depth.from.context
                and sect4">
    <fo:block id="toc.{$cid}.{$id}">
      <xsl:attribute name="margin-{$direction.align.start}">
        <xsl:call-template name="set.toc.indent">
          <xsl:with-param name="reldepth" select="$reldepth"/>
        </xsl:call-template>
      </xsl:attribute>
              
      <xsl:apply-templates select="sect4|qandaset[$qanda.in.toc != 0]" 
                           mode="toc">
        <xsl:with-param name="toc-context" select="$toc-context"/>
      </xsl:apply-templates>
    </fo:block>
  </xsl:if>
</xsl:template>

<xsl:template match="sect4" mode="toc">
  <xsl:param name="toc-context" select="."/>

  <xsl:variable name="id">
    <xsl:call-template name="object.id"/>
  </xsl:variable>

  <xsl:variable name="cid">
    <xsl:call-template name="object.id">
      <xsl:with-param name="object" select="$toc-context"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:call-template name="toc.line">
    <xsl:with-param name="toc-context" select="$toc-context"/>
  </xsl:call-template>

  <xsl:variable name="reldepth"
                select="count(ancestor::*)-count($toc-context/ancestor::*)"/>

  <xsl:variable name="depth.from.context" select="count(ancestor::*)-count($toc-context/ancestor::*)"/>

  <xsl:if test="$toc.section.depth > 4 
                and $toc.max.depth > $depth.from.context
                and sect5">
    <fo:block id="toc.{$cid}.{$id}">
      <xsl:attribute name="margin-{$direction.align.start}">
        <xsl:call-template name="set.toc.indent">
          <xsl:with-param name="reldepth" select="$reldepth"/>
        </xsl:call-template>
      </xsl:attribute>
              
      <xsl:apply-templates select="sect5|qandaset[$qanda.in.toc != 0]" 
                           mode="toc">
        <xsl:with-param name="toc-context" select="$toc-context"/>
      </xsl:apply-templates>
    </fo:block>
  </xsl:if>
</xsl:template>

<xsl:template match="sect5|simplesect" mode="toc">
  <xsl:param name="toc-context" select="."/>

  <xsl:call-template name="toc.line">
    <xsl:with-param name="toc-context" select="$toc-context"/>
  </xsl:call-template>
</xsl:template>

<xsl:template match="topic" mode="toc">
  <xsl:param name="toc-context" select="."/>

  <xsl:call-template name="toc.line">
    <xsl:with-param name="toc-context" select="$toc-context"/>
  </xsl:call-template>
</xsl:template>

<xsl:template name="set.toc.indent">
  <xsl:param name="reldepth"/>

  <xsl:variable name="depth">
    <xsl:choose>
      <xsl:when test="$reldepth != ''">
        <xsl:value-of select="$reldepth"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:value-of select="count(ancestor::*)"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:choose>
    <xsl:when test="$fop.extensions != 0">
       <xsl:value-of select="concat($depth*$toc.indent.width, 'pt')"/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:value-of select="concat($toc.indent.width, 'pt')"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>


<xsl:template match="section" mode="toc">
  <xsl:param name="toc-context" select="."/>

  <xsl:variable name="id">
    <xsl:call-template name="object.id"/>
  </xsl:variable>

  <xsl:variable name="cid">
    <xsl:call-template name="object.id">
      <xsl:with-param name="object" select="$toc-context"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:variable name="depth" select="count(ancestor::section) + 1"/>
  <xsl:variable name="reldepth"
                select="count(ancestor::*)-count($toc-context/ancestor::*)"/>

  <xsl:variable name="depth.from.context" select="count(ancestor::*)-count($toc-context/ancestor::*)"/>

  <xsl:if test="$toc.section.depth &gt;= $depth">
    <xsl:call-template name="toc.line">
      <xsl:with-param name="toc-context" select="$toc-context"/>
    </xsl:call-template>

    <xsl:if test="$toc.section.depth > $depth 
                  and $toc.max.depth > $depth.from.context
                  and section">
      <fo:block id="toc.{$cid}.{$id}">
        <xsl:attribute name="margin-{$direction.align.start}">
          <xsl:call-template name="set.toc.indent">
            <xsl:with-param name="reldepth" select="$reldepth"/>
          </xsl:call-template>
        </xsl:attribute>
                
        <xsl:apply-templates select="section|qandaset[$qanda.in.toc != 0]" 
                           mode="toc">
          <xsl:with-param name="toc-context" select="$toc-context"/>
        </xsl:apply-templates>
      </fo:block>
    </xsl:if>
  </xsl:if>
</xsl:template>

<xsl:template match="bibliography|glossary"
              mode="toc">
  <xsl:param name="toc-context" select="."/>

  <xsl:call-template name="toc.line">
    <xsl:with-param name="toc-context" select="$toc-context"/>
  </xsl:call-template>
</xsl:template>

<xsl:template match="index" mode="toc">
  <xsl:param name="toc-context" select="."/>

  <xsl:if test="* or $generate.index != 0">
    <xsl:call-template name="toc.line">
    <xsl:with-param name="toc-context" select="$toc-context"/>
  </xsl:call-template>
  </xsl:if>
</xsl:template>

<xsl:template match="title" mode="toc">
  <xsl:apply-templates/>
</xsl:template>

<!-- ==================================================================== -->

<xsl:template name="list.of.titles">
  <xsl:param name="titles" select="'table'"/>
  <xsl:param name="nodes" select=".//table"/>
  <xsl:param name="toc-context" select="."/>

  <xsl:variable name="id">
    <xsl:call-template name="object.id"/>
  </xsl:variable>

  <xsl:if test="$nodes">
    <fo:block id="lot...{$titles}...{$id}">
      <xsl:choose>
        <xsl:when test="$titles='table'">
          <xsl:call-template name="list.of.tables.titlepage"/>
        </xsl:when>
        <xsl:when test="$titles='figure'">
          <xsl:call-template name="list.of.figures.titlepage"/>
        </xsl:when>
        <xsl:when test="$titles='equation'">
          <xsl:call-template name="list.of.equations.titlepage"/>
        </xsl:when>
        <xsl:when test="$titles='example'">
          <xsl:call-template name="list.of.examples.titlepage"/>
        </xsl:when>
        <xsl:when test="$titles='procedure'">
          <xsl:call-template name="list.of.procedures.titlepage"/>
        </xsl:when>
        <xsl:otherwise>
          <xsl:call-template name="list.of.unknowns.titlepage"/>
        </xsl:otherwise>
      </xsl:choose>
      <xsl:apply-templates select="$nodes" mode="toc">
        <xsl:with-param name="toc-context" select="$toc-context"/>
      </xsl:apply-templates>
    </fo:block>
  </xsl:if>
</xsl:template>

<xsl:template name="component.list.of.titles">
  <xsl:param name="titles" select="'table'"/>
  <xsl:param name="nodes" select=".//table"/>
  <xsl:param name="toc-context" select="."/>

  <xsl:variable name="id">
    <xsl:call-template name="object.id"/>
  </xsl:variable>

  <xsl:if test="$nodes">
    <fo:block id="lot...{$titles}...{$id}">
      <xsl:choose>
        <xsl:when test="$titles='table'">
          <xsl:call-template name="component.list.of.tables.titlepage"/>
        </xsl:when>
        <xsl:when test="$titles='figure'">
          <xsl:call-template name="component.list.of.figures.titlepage"/>
        </xsl:when>
        <xsl:when test="$titles='equation'">
          <xsl:call-template name="component.list.of.equations.titlepage"/>
        </xsl:when>
        <xsl:when test="$titles='example'">
          <xsl:call-template name="component.list.of.examples.titlepage"/>
        </xsl:when>
        <xsl:when test="$titles='procedure'">
          <xsl:call-template name="component.list.of.procedures.titlepage"/>
        </xsl:when>
        <xsl:otherwise>
          <xsl:call-template name="component.list.of.unknowns.titlepage"/>
        </xsl:otherwise>
      </xsl:choose>
      <xsl:apply-templates select="$nodes" mode="toc">
        <xsl:with-param name="toc-context" select="$toc-context"/>
      </xsl:apply-templates>
    </fo:block>
  </xsl:if>
</xsl:template>

<xsl:template match="figure|table|example|equation|procedure" mode="toc">
  <xsl:param name="toc-context" select="."/>
  <xsl:call-template name="toc.line">
    <xsl:with-param name="toc-context" select="$toc-context"/>
  </xsl:call-template>
</xsl:template>

<!-- ==================================================================== -->

<!-- qandaset handled like a section when qanda.in.toc is set -->
<xsl:template match="qandaset" mode="toc">
  <xsl:param name="toc-context" select="."/>

  <xsl:variable name="id">
    <xsl:call-template name="object.id"/>
  </xsl:variable>

  <xsl:variable name="cid">
    <xsl:call-template name="object.id">
      <xsl:with-param name="object" select="$toc-context"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:variable name="depth" select="count(ancestor::section) + 1"/>
  <xsl:variable name="reldepth"
                select="count(ancestor::*)-count($toc-context/ancestor::*)"/>

  <xsl:variable name="depth.from.context" select="count(ancestor::*)-count($toc-context/ancestor::*)"/>

  <xsl:if test="$toc.section.depth &gt;= $depth">
    <xsl:call-template name="toc.line">
      <xsl:with-param name="toc-context" select="$toc-context"/>
    </xsl:call-template>

    <xsl:if test="$toc.section.depth > $depth 
                  and $toc.max.depth > $depth.from.context
                  and (child::qandadiv or child::qandaentry)">
      <fo:block id="toc.{$cid}.{$id}">
        <xsl:attribute name="margin-{$direction.align.start}">
          <xsl:call-template name="set.toc.indent">
            <xsl:with-param name="reldepth" select="$reldepth"/>
          </xsl:call-template>
        </xsl:attribute>
                
        <xsl:apply-templates select="qandadiv|qandaentry" mode="toc">
          <xsl:with-param name="toc-context" select="$toc-context"/>
        </xsl:apply-templates>
      </fo:block>
    </xsl:if>
  </xsl:if>
</xsl:template>

</xsl:stylesheet>

