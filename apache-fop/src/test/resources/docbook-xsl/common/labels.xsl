<?xml version='1.0'?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:doc="http://nwalsh.com/xsl/documentation/1.0"
                exclude-result-prefixes="doc"
                version='1.0'>

<!-- ********************************************************************
     $Id: labels.xsl 9706 2013-01-16 18:56:16Z bobstayton $
     ********************************************************************

     This file is part of the XSL DocBook Stylesheet distribution.
     See ../README or http://docbook.sf.net/release/xsl/current/ for
     copyright and other information.

     ******************************************************************** -->

<!-- ==================================================================== -->

<!-- label markup -->

<doc:mode mode="label.markup" xmlns="">
<refpurpose>Provides access to element labels</refpurpose>
<refdescription id="label.markup-desc">
<para>Processing an element in the
<literal role="mode">label.markup</literal> mode produces the
element label.</para>
<para>Trailing punctuation is not added to the label.
</para>
</refdescription>
</doc:mode>

<xsl:template match="*" mode="intralabel.punctuation">
  <xsl:text>.</xsl:text>
</xsl:template>

<xsl:template match="*" mode="label.markup">
  <xsl:param name="verbose" select="1"/>
  <xsl:if test="$verbose">
    <xsl:message>
      <xsl:text>Request for label of unexpected element: </xsl:text>
      <xsl:value-of select="local-name(.)"/>
    </xsl:message>
  </xsl:if>
</xsl:template>

<xsl:template match="set|book" mode="label.markup">
  <xsl:if test="@label">
    <xsl:value-of select="@label"/>
  </xsl:if>
</xsl:template>

<xsl:template match="part" mode="label.markup">
  <xsl:choose>
    <xsl:when test="@label">
      <xsl:value-of select="@label"/>
    </xsl:when>
    <xsl:when test="string($part.autolabel) != 0">
      <xsl:variable name="format">
        <xsl:call-template name="autolabel.format">
          <xsl:with-param name="format" select="$part.autolabel"/>
        </xsl:call-template>
      </xsl:variable>
      <xsl:number from="book" count="part" format="{$format}"/>
    </xsl:when>
  </xsl:choose>
</xsl:template>

<xsl:template match="partintro" mode="label.markup">
  <!-- no label -->
</xsl:template>

<xsl:template match="preface" mode="label.markup">
  <xsl:choose>
    <xsl:when test="@label">
      <xsl:value-of select="@label"/>
    </xsl:when>
    <xsl:when test="string($preface.autolabel) != 0">
      <xsl:if test="$component.label.includes.part.label != 0 and
                      ancestor::part">
        <xsl:variable name="part.label">
          <xsl:apply-templates select="ancestor::part" 
                               mode="label.markup"/>
        </xsl:variable>
        <xsl:if test="$part.label != ''">
          <xsl:value-of select="$part.label"/>
          <xsl:apply-templates select="ancestor::part" 
                               mode="intralabel.punctuation"/>
        </xsl:if>
      </xsl:if>
      <xsl:variable name="format">
        <xsl:call-template name="autolabel.format">
          <xsl:with-param name="format" select="$preface.autolabel"/>
        </xsl:call-template>
      </xsl:variable>
      <xsl:choose>
        <xsl:when test="$label.from.part != 0 and ancestor::part">
          <xsl:number from="part" count="preface" format="{$format}" level="any"/>
        </xsl:when>
        <xsl:otherwise>
          <xsl:number from="book" count="preface" format="{$format}" level="any"/>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:when>
  </xsl:choose>
</xsl:template>

<xsl:template match="chapter" mode="label.markup">
  <xsl:choose>
    <xsl:when test="@label">
      <xsl:value-of select="@label"/>
    </xsl:when>
    <xsl:when test="string($chapter.autolabel) != 0">
      <xsl:if test="$component.label.includes.part.label != 0 and
                      ancestor::part">
        <xsl:variable name="part.label">
          <xsl:apply-templates select="ancestor::part" 
                               mode="label.markup"/>
        </xsl:variable>
        <xsl:if test="$part.label != ''">
          <xsl:value-of select="$part.label"/>
          <xsl:apply-templates select="ancestor::part" 
                               mode="intralabel.punctuation"/>
        </xsl:if>
      </xsl:if>
      <xsl:variable name="format">
        <xsl:call-template name="autolabel.format">
          <xsl:with-param name="format" select="$chapter.autolabel"/>
        </xsl:call-template>
      </xsl:variable>
      <xsl:choose>
        <xsl:when test="$label.from.part != 0 and ancestor::part">
          <xsl:number from="part" count="chapter" format="{$format}" level="any"/>
        </xsl:when>
        <xsl:otherwise>
          <xsl:number from="book" count="chapter" format="{$format}" level="any"/>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:when>
  </xsl:choose>
</xsl:template>

<xsl:template match="appendix" mode="label.markup">
  <xsl:choose>
    <xsl:when test="@label">
      <xsl:value-of select="@label"/>
    </xsl:when>
    <xsl:when test="string($appendix.autolabel) != 0">
      <xsl:if test="$component.label.includes.part.label != 0 and
                      ancestor::part">
        <xsl:variable name="part.label">
          <xsl:apply-templates select="ancestor::part" 
                               mode="label.markup"/>
        </xsl:variable>
        <xsl:if test="$part.label != ''">
          <xsl:value-of select="$part.label"/>
          <xsl:apply-templates select="ancestor::part" 
                               mode="intralabel.punctuation"/>
        </xsl:if>
      </xsl:if>
      <xsl:variable name="format">
        <xsl:call-template name="autolabel.format">
          <xsl:with-param name="format" select="$appendix.autolabel"/>
        </xsl:call-template>
      </xsl:variable>
      <xsl:choose>
        <xsl:when test="$label.from.part != 0 and ancestor::part">
          <xsl:number from="part" count="appendix" format="{$format}" level="any"/>
        </xsl:when>
        <xsl:otherwise>
          <xsl:number from="book|article"
                      count="appendix" format="{$format}" level="any"/>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:when>
  </xsl:choose>
</xsl:template>

<xsl:template match="article" mode="label.markup">
  <xsl:if test="@label">
    <xsl:value-of select="@label"/>
  </xsl:if>
</xsl:template>

<xsl:template match="dedication|colophon" mode="label.markup">
  <xsl:if test="@label">
    <xsl:value-of select="@label"/>
  </xsl:if>
</xsl:template>

<xsl:template match="reference" mode="label.markup">
  <xsl:choose>
    <xsl:when test="@label">
      <xsl:value-of select="@label"/>
    </xsl:when>
    <xsl:when test="string($reference.autolabel) != 0">
      <xsl:if test="$component.label.includes.part.label != 0 and
                      ancestor::part">
        <xsl:variable name="part.label">
          <xsl:apply-templates select="ancestor::part" 
                               mode="label.markup"/>
        </xsl:variable>
        <xsl:if test="$part.label != ''">
          <xsl:value-of select="$part.label"/>
          <xsl:apply-templates select="ancestor::part" 
                               mode="intralabel.punctuation"/>
        </xsl:if>
      </xsl:if>
      <xsl:variable name="format">
        <xsl:call-template name="autolabel.format">
          <xsl:with-param name="format" select="$reference.autolabel"/>
        </xsl:call-template>
      </xsl:variable>
      <xsl:choose>
        <xsl:when test="$label.from.part != 0 and ancestor::part">
          <xsl:number from="part" count="reference" format="{$format}" level="any"/>
        </xsl:when>
        <xsl:otherwise>
          <xsl:number from="book" count="reference" format="{$format}" level="any"/>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:when>
  </xsl:choose>
</xsl:template>

<xsl:template match="refentry" mode="label.markup">
  <xsl:if test="@label">
    <xsl:value-of select="@label"/>
  </xsl:if>
</xsl:template>

<xsl:template match="section" mode="label.markup">
  <!-- if this is a nested section, label the parent -->
  <xsl:if test="local-name(..) = 'section'">
    <xsl:variable name="parent.section.label">
      <xsl:call-template name="label.this.section">
        <xsl:with-param name="section" select=".."/>
      </xsl:call-template>
    </xsl:variable>
    <xsl:if test="$parent.section.label != '0'">
      <xsl:apply-templates select=".." mode="label.markup"/>
      <xsl:apply-templates select=".." mode="intralabel.punctuation"/>
    </xsl:if>
  </xsl:if>

  <!-- if the parent is a component, maybe label that too -->
  <xsl:variable name="parent.is.component">
    <xsl:call-template name="is.component">
      <xsl:with-param name="node" select=".."/>
    </xsl:call-template>
  </xsl:variable>

  <!-- does this section get labelled? -->
  <xsl:variable name="label">
    <xsl:call-template name="label.this.section">
      <xsl:with-param name="section" select="."/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:if test="$section.label.includes.component.label != 0
                and $parent.is.component != 0">
    <xsl:variable name="parent.label">
      <xsl:apply-templates select=".." mode="label.markup"/>
    </xsl:variable>
    <xsl:if test="$parent.label != ''">
      <xsl:apply-templates select=".." mode="label.markup"/>
      <xsl:apply-templates select=".." mode="intralabel.punctuation"/>
    </xsl:if>
  </xsl:if>

<!--
  <xsl:message>
    test: <xsl:value-of select="$label"/>, <xsl:number count="section"/>
  </xsl:message>
-->

  <xsl:choose>
    <xsl:when test="@label">
      <xsl:value-of select="@label"/>
    </xsl:when>
    <xsl:when test="$label != 0">      
      <xsl:variable name="format">
        <xsl:call-template name="autolabel.format">
          <xsl:with-param name="format" select="$section.autolabel"/>
        </xsl:call-template>
      </xsl:variable>
      <xsl:number format="{$format}" count="section"/>
    </xsl:when>
  </xsl:choose>
</xsl:template>

<xsl:template match="sect1" mode="label.markup">
  <!-- if the parent is a component, maybe label that too -->
  <xsl:variable name="parent.is.component">
    <xsl:call-template name="is.component">
      <xsl:with-param name="node" select=".."/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:variable name="component.label">
    <xsl:if test="$section.label.includes.component.label != 0
                  and $parent.is.component != 0">
      <xsl:variable name="parent.label">
        <xsl:apply-templates select=".." mode="label.markup"/>
      </xsl:variable>
      <xsl:if test="$parent.label != ''">
        <xsl:apply-templates select=".." mode="label.markup"/>
        <xsl:apply-templates select=".." mode="intralabel.punctuation"/>
      </xsl:if>
    </xsl:if>
  </xsl:variable>


  <xsl:variable name="is.numbered">
    <xsl:call-template name="label.this.section"/>
  </xsl:variable>

  <xsl:choose>
    <xsl:when test="@label">
      <xsl:value-of select="@label"/>
    </xsl:when>
    <xsl:when test="$is.numbered != 0">
      <xsl:variable name="format">
        <xsl:call-template name="autolabel.format">
          <xsl:with-param name="format" select="$section.autolabel"/>
        </xsl:call-template>
      </xsl:variable>
      <xsl:copy-of select="$component.label"/>
      <xsl:number format="{$format}" count="sect1"/>
    </xsl:when>
  </xsl:choose>
</xsl:template>

<xsl:template match="sect2|sect3|sect4|sect5" mode="label.markup">
  <!-- label the parent -->
  <xsl:variable name="parent.section.label">
    <xsl:call-template name="label.this.section">
      <xsl:with-param name="section" select=".."/>
    </xsl:call-template>
  </xsl:variable>
  <xsl:if test="$parent.section.label != '0'">
    <xsl:apply-templates select=".." mode="label.markup"/>
    <xsl:apply-templates select=".." mode="intralabel.punctuation"/>
  </xsl:if>

  <xsl:variable name="is.numbered">
    <xsl:call-template name="label.this.section"/>
  </xsl:variable>

  <xsl:choose>
    <xsl:when test="@label">
      <xsl:value-of select="@label"/>
    </xsl:when>
    <xsl:when test="$is.numbered != 0">
      <xsl:variable name="format">
        <xsl:call-template name="autolabel.format">
          <xsl:with-param name="format" select="$section.autolabel"/>
        </xsl:call-template>
      </xsl:variable>
      <xsl:choose>
        <xsl:when test="local-name(.) = 'sect2'">
          <xsl:number format="{$format}" count="sect2"/>
        </xsl:when>
        <xsl:when test="local-name(.) = 'sect3'">
          <xsl:number format="{$format}" count="sect3"/>
        </xsl:when>
        <xsl:when test="local-name(.) = 'sect4'">
          <xsl:number format="{$format}" count="sect4"/>
        </xsl:when>
        <xsl:when test="local-name(.) = 'sect5'">
          <xsl:number format="{$format}" count="sect5"/>
        </xsl:when>
        <xsl:otherwise>
          <xsl:message>label.markup: this can't happen!</xsl:message>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:when>
  </xsl:choose>
</xsl:template>

<xsl:template match="bridgehead" mode="label.markup">
  <!-- bridgeheads are not normally numbered -->
</xsl:template>

<xsl:template match="refsect1" mode="label.markup">
  <xsl:choose>
    <xsl:when test="@label">
      <xsl:value-of select="@label"/>
    </xsl:when>
    <xsl:when test="$section.autolabel != 0">
      <xsl:variable name="format">
        <xsl:call-template name="autolabel.format">
          <xsl:with-param name="format" select="$section.autolabel"/>
        </xsl:call-template>
      </xsl:variable>
      <xsl:number count="refsect1" format="{$format}"/>
    </xsl:when>
  </xsl:choose>
</xsl:template>

<xsl:template match="refsect2|refsect3" mode="label.markup">
  <!-- label the parent -->
  <xsl:variable name="parent.label">
    <xsl:apply-templates select=".." mode="label.markup"/>
  </xsl:variable>
  <xsl:if test="$parent.label != ''">
    <xsl:apply-templates select=".." mode="label.markup"/>
    <xsl:apply-templates select=".." mode="intralabel.punctuation"/>
  </xsl:if>

  <xsl:choose>
    <xsl:when test="@label">
      <xsl:value-of select="@label"/>
    </xsl:when>
    <xsl:when test="$section.autolabel != 0">
      <xsl:variable name="format">
        <xsl:call-template name="autolabel.format">
          <xsl:with-param name="format" select="$section.autolabel"/>
        </xsl:call-template>
      </xsl:variable>
      <xsl:choose>
        <xsl:when test="local-name(.) = 'refsect2'">
          <xsl:number count="refsect2" format="{$format}"/>
        </xsl:when>
        <xsl:otherwise>
          <xsl:number count="refsect3" format="{$format}"/>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:when>
  </xsl:choose>
</xsl:template>

<xsl:template match="simplesect" mode="label.markup">
  <!-- if this is a nested section, label the parent -->
  <xsl:if test="local-name(..) = 'section'
                or local-name(..) = 'sect1'
                or local-name(..) = 'sect2'
                or local-name(..) = 'sect3'
                or local-name(..) = 'sect4'
                or local-name(..) = 'sect5'">
    <xsl:variable name="parent.section.label">
      <xsl:apply-templates select=".." mode="label.markup"/>
    </xsl:variable>
    <xsl:if test="$parent.section.label != ''">
      <xsl:apply-templates select=".." mode="label.markup"/>
      <xsl:apply-templates select=".." mode="intralabel.punctuation"/>
    </xsl:if>
  </xsl:if>

  <!-- if the parent is a component, maybe label that too -->
  <xsl:variable name="parent.is.component">
    <xsl:call-template name="is.component">
      <xsl:with-param name="node" select=".."/>
    </xsl:call-template>
  </xsl:variable>

  <!-- does this section get labelled? -->
  <xsl:variable name="label">
    <xsl:call-template name="label.this.section">
      <xsl:with-param name="section" select="."/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:if test="$section.label.includes.component.label != 0
                and $parent.is.component != 0">
    <xsl:variable name="parent.label">
      <xsl:apply-templates select=".." mode="label.markup"/>
    </xsl:variable>
    <xsl:if test="$parent.label != ''">
      <xsl:apply-templates select=".." mode="label.markup"/>
      <xsl:apply-templates select=".." mode="intralabel.punctuation"/>
    </xsl:if>
  </xsl:if>

  <xsl:choose>
    <xsl:when test="@label">
      <xsl:value-of select="@label"/>
    </xsl:when>
    <xsl:when test="$label != 0">
      <xsl:variable name="format">
        <xsl:call-template name="autolabel.format">
          <xsl:with-param name="format" select="$section.autolabel"/>
        </xsl:call-template>
      </xsl:variable>
      <xsl:number format="{$format}" count="simplesect"/>
    </xsl:when>
  </xsl:choose>
</xsl:template>

<xsl:template match="topic" mode="label.markup">
  <!-- topics are not numbered by default -->
</xsl:template>

<xsl:template match="qandadiv" mode="label.markup">
  <xsl:variable name="lparent" select="(ancestor::set
                                       |ancestor::book
                                       |ancestor::chapter
                                       |ancestor::appendix
                                       |ancestor::preface
                                       |ancestor::section
                                       |ancestor::simplesect
                                       |ancestor::sect1
                                       |ancestor::sect2
                                       |ancestor::sect3
                                       |ancestor::sect4
                                       |ancestor::sect5
                                       |ancestor::refsect1
                                       |ancestor::refsect2
                                       |ancestor::refsect3)[last()]"/>

  <xsl:variable name="lparent.prefix">
    <xsl:apply-templates select="$lparent" mode="label.markup"/>
  </xsl:variable>

  <xsl:variable name="prefix">
    <xsl:if test="$qanda.inherit.numeration != 0">
      <xsl:if test="$lparent.prefix != ''">
        <xsl:apply-templates select="$lparent" mode="label.markup"/>
        <xsl:apply-templates select="$lparent" mode="intralabel.punctuation"/>
      </xsl:if>
    </xsl:if>
  </xsl:variable>

  <xsl:choose>
    <xsl:when test="$qandadiv.autolabel != 0">
      <xsl:variable name="format">
        <xsl:call-template name="autolabel.format">
          <xsl:with-param name="format" select="$qandadiv.autolabel"/>
        </xsl:call-template>
      </xsl:variable>
      <xsl:value-of select="$prefix"/>
      <xsl:number level="multiple" count="qandadiv" format="{$format}"/>
    </xsl:when>
  </xsl:choose>
</xsl:template>

<xsl:template match="question|answer" mode="label.markup">
  <xsl:variable name="lparent" select="(ancestor::set
                                       |ancestor::book
                                       |ancestor::chapter
                                       |ancestor::appendix
                                       |ancestor::preface
                                       |ancestor::section
                                       |ancestor::simplesect
                                       |ancestor::sect1
                                       |ancestor::sect2
                                       |ancestor::sect3
                                       |ancestor::sect4
                                       |ancestor::sect5
                                       |ancestor::refsect1
                                       |ancestor::refsect2
                                       |ancestor::refsect3)[last()]"/>

  <xsl:variable name="lparent.prefix">
    <xsl:apply-templates select="$lparent" mode="label.markup"/>
  </xsl:variable>

  <xsl:variable name="prefix">
    <xsl:if test="$qanda.inherit.numeration != 0">
      <xsl:choose>
        <xsl:when test="ancestor::qandadiv">
          <xsl:variable name="div.label">
            <xsl:apply-templates select="ancestor::qandadiv[1]" mode="label.markup"/>
          </xsl:variable>
          <xsl:if test="string-length($div.label) != 0">
            <xsl:copy-of select="$div.label"/>
            <xsl:apply-templates select="ancestor::qandadiv[1]"
                                 mode="intralabel.punctuation"/>
          </xsl:if>
        </xsl:when>
        <xsl:when test="$lparent.prefix != ''">
          <xsl:apply-templates select="$lparent" mode="label.markup"/>
          <xsl:apply-templates select="$lparent" mode="intralabel.punctuation"/>
        </xsl:when>
      </xsl:choose>
    </xsl:if>
  </xsl:variable>

  <xsl:variable name="inhlabel"
                select="ancestor-or-self::qandaset/@defaultlabel[1]"/>

  <xsl:variable name="deflabel">
    <xsl:choose>
      <xsl:when test="$inhlabel != ''">
        <xsl:value-of select="$inhlabel"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:value-of select="$qanda.defaultlabel"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:variable name="label" select="label"/>

  <xsl:choose>
    <xsl:when test="count($label)>0">
      <xsl:apply-templates select="$label"/>
    </xsl:when>

    <xsl:when test="$deflabel = 'qanda' and self::question">
      <xsl:call-template name="gentext">
        <xsl:with-param name="key" select="'Question'"/>
      </xsl:call-template>
    </xsl:when>

    <xsl:when test="$deflabel = 'qanda' and self::answer">
      <xsl:call-template name="gentext">
        <xsl:with-param name="key" select="'Answer'"/>
      </xsl:call-template>
    </xsl:when>

    <xsl:when test="($deflabel = 'qnumber' or
                     $deflabel = 'qnumberanda') and self::question">
      <xsl:call-template name="gentext">
        <xsl:with-param name="key" select="'Question'"/>
      </xsl:call-template>
      <xsl:text>&#xA0;</xsl:text>
      <xsl:value-of select="$prefix"/>
      <xsl:number level="multiple" count="qandaentry" format="1"/>
    </xsl:when>

    <xsl:when test="$deflabel = 'qnumberanda' and self::answer">
      <xsl:call-template name="gentext">
        <xsl:with-param name="key" select="'Answer'"/>
      </xsl:call-template>
    </xsl:when>

    <xsl:when test="$deflabel = 'number' and self::question">
      <xsl:value-of select="$prefix"/>
      <xsl:number level="multiple" count="qandaentry" format="1"/>
    </xsl:when>
  </xsl:choose>
</xsl:template>

<xsl:template match="bibliography|glossary|
                     qandaset|index|setindex" mode="label.markup">
  <xsl:if test="@label">
    <xsl:value-of select="@label"/>
  </xsl:if>
</xsl:template>

<xsl:template match="figure|table|example" mode="label.markup">
  <xsl:variable name="pchap"
                select="(ancestor::chapter
                        |ancestor::appendix
                        |ancestor::article[ancestor::book])[last()]"/>

  <xsl:variable name="prefix">
    <xsl:if test="count($pchap) &gt; 0">
      <xsl:apply-templates select="$pchap" mode="label.markup"/>
    </xsl:if>
  </xsl:variable>

  <xsl:choose>
    <xsl:when test="@label">
      <xsl:value-of select="@label"/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:choose>
        <xsl:when test="$prefix != ''">
            <xsl:apply-templates select="$pchap" mode="label.markup"/>
            <xsl:apply-templates select="$pchap" mode="intralabel.punctuation"/>
          <xsl:number format="1" from="chapter|appendix" level="any"/>
        </xsl:when>
        <xsl:otherwise>
          <xsl:number format="1" from="book|article" level="any"/>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="procedure" mode="label.markup">
  <xsl:variable name="pchap"
                select="ancestor::chapter
                        |ancestor::appendix
                        |ancestor::article[ancestor::book]"/>

  <xsl:variable name="prefix">
    <xsl:if test="count($pchap) &gt; 0">
      <xsl:apply-templates select="$pchap" mode="label.markup"/>
    </xsl:if>
  </xsl:variable>

  <xsl:choose>
    <xsl:when test="@label">
      <xsl:value-of select="@label"/>
    </xsl:when>
    <xsl:when test="$formal.procedures = 0">
      <!-- No label -->
    </xsl:when>
    <xsl:otherwise>
      <xsl:choose>
        <xsl:when test="count($pchap)>0">
          <xsl:if test="$prefix != ''">
            <xsl:apply-templates select="$pchap" mode="label.markup"/>
            <xsl:apply-templates select="$pchap" mode="intralabel.punctuation"/>
          </xsl:if>
          <xsl:number count="procedure[title]" format="1" 
                      from="chapter|appendix" level="any"/>
        </xsl:when>
        <xsl:otherwise>
          <xsl:number count="procedure[title]" format="1" 
                      from="book|article" level="any"/>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="equation" mode="label.markup">
  <xsl:variable name="pchap"
                select="ancestor::chapter
                        |ancestor::appendix
                        |ancestor::article[ancestor::book]"/>

  <xsl:variable name="prefix">
    <xsl:if test="count($pchap) &gt; 0">
      <xsl:apply-templates select="$pchap" mode="label.markup"/>
    </xsl:if>
  </xsl:variable>

  <xsl:choose>
    <xsl:when test="@label">
      <xsl:value-of select="@label"/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:choose>
        <xsl:when test="count($pchap)>0">
          <xsl:if test="$prefix != ''">
            <xsl:apply-templates select="$pchap" mode="label.markup"/>
            <xsl:apply-templates select="$pchap" mode="intralabel.punctuation"/>
          </xsl:if>
          <xsl:number format="1" count="equation" 
                      from="chapter|appendix" level="any"/>
        </xsl:when>
        <xsl:otherwise>
          <xsl:number format="1" count="equation" 
                      from="book|article" level="any"/>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="orderedlist/listitem" mode="label.markup">
  <xsl:variable name="numeration">
    <xsl:call-template name="list.numeration">
      <xsl:with-param name="node" select="parent::orderedlist"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:variable name="type">
    <xsl:choose>
      <xsl:when test="$numeration='arabic'">1</xsl:when>
      <xsl:when test="$numeration='loweralpha'">a</xsl:when>
      <xsl:when test="$numeration='lowerroman'">i</xsl:when>
      <xsl:when test="$numeration='upperalpha'">A</xsl:when>
      <xsl:when test="$numeration='upperroman'">I</xsl:when>
      <!-- What!? This should never happen -->
      <xsl:otherwise>
        <xsl:message>
          <xsl:text>Unexpected numeration: </xsl:text>
          <xsl:value-of select="$numeration"/>
        </xsl:message>
        <xsl:value-of select="1."/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:variable name="item-number">
    <xsl:call-template name="orderedlist-item-number"/>
  </xsl:variable>

  <xsl:number value="$item-number" format="{$type}"/>
</xsl:template>

<xsl:template match="production" mode="label.markup">
  <xsl:number count="production" level="any"/>
</xsl:template>

<xsl:template match="abstract" mode="label.markup">
  <!-- nop -->
</xsl:template>

<xsl:template match="sidebar" mode="label.markup">
  <!-- nop -->
</xsl:template>

<xsl:template match="glossdiv|glosslist" mode="label.markup">
  <!-- nop -->
</xsl:template>

<xsl:template match="glossentry" mode="label.markup">
  <!-- nop -->
</xsl:template>

<!-- ============================================================ -->

<xsl:template name="label.this.section">
  <xsl:param name="section" select="."/>

  <xsl:variable name="level">
    <xsl:call-template name="section.level"/>
  </xsl:variable>

  <xsl:choose>
    <!-- bridgeheads are not numbered -->
    <xsl:when test="$section/self::bridgehead">0</xsl:when>
    <xsl:when test="$level &lt;= $section.autolabel.max.depth">      
      <xsl:value-of select="$section.autolabel"/>
    </xsl:when>
    <xsl:otherwise>0</xsl:otherwise>
  </xsl:choose>
</xsl:template>

<doc:template name="label.this.section" xmlns="">
<refpurpose>Returns true if $section should be labelled</refpurpose>
<refdescription id="label.this.section-desc">
<para>Returns true if the specified section should be labelled.
By default, this template returns zero unless 
the section level is less than or equal to the value of the
<literal>$section.autolabel.max.depth</literal> parameter, in
which case it returns
<literal>$section.autolabel</literal>.
Custom stylesheets may override it to get more selective behavior.</para>
</refdescription>
</doc:template>

<!-- ============================================================ -->

<xsl:template name="default.autolabel.format">
  <xsl:param name="context" select="."/>
  <xsl:choose>
    <xsl:when test="local-name($context) = 'appendix'">
      <xsl:value-of select="'A'"/>
    </xsl:when>
    <xsl:when test="local-name($context) = 'part'">
      <xsl:value-of select="'I'"/>
    </xsl:when>
    <xsl:otherwise>1</xsl:otherwise>
  </xsl:choose>  
</xsl:template>
  
<xsl:template name="autolabel.format">
  <xsl:param name="context" select="."/>
  <xsl:param name="format"/>

  <xsl:choose>
    <xsl:when test="string($format) != 0">
      <xsl:choose>
        <xsl:when test="string($format)='arabic' or $format='1'">1</xsl:when>
        <xsl:when test="$format='loweralpha' or $format='a'">
          <xsl:value-of select="'a'"/>
        </xsl:when>
        <xsl:when test="$format='lowerroman' or $format='i'">
          <xsl:value-of select="'i'"/>
        </xsl:when>
        <xsl:when test="$format='upperalpha' or $format='A'">
          <xsl:value-of select="'A'"/>
        </xsl:when>
        <xsl:when test="$format='upperroman' or $format='I'">
          <xsl:value-of select="'I'"/>
        </xsl:when>      
        <xsl:when test="$format='arabicindic' or $format='&#x661;'">
          <xsl:value-of select="'&#x661;'"/>
        </xsl:when>
        <xsl:otherwise>
          <xsl:message>
            <xsl:text>Unexpected </xsl:text><xsl:value-of select="local-name(.)"/><xsl:text>.autolabel value: </xsl:text>
            <xsl:value-of select="$format"/><xsl:text>; using default.</xsl:text>
          </xsl:message>
          <xsl:call-template name="default.autolabel.format"/>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:when>
  </xsl:choose>
</xsl:template>

<doc:template name="autolabel.format" xmlns="">
<refpurpose>Returns format for autolabel parameters</refpurpose>
<refdescription id="autolabel.format-desc">
<para>Returns format passed as parameter if non zero. Supported
  format are 'arabic' or '1', 'loweralpha' or 'a', 'lowerroman' or 'i', 
  'upperlapha' or 'A', 'upperroman' or 'I', 'arabicindic' or '&#x661;'.
  If its not one of these then 
  returns the default format.</para>
</refdescription>
</doc:template>

<!-- ============================================================ -->

</xsl:stylesheet>
