<?xml version='1.0'?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:doc="http://nwalsh.com/xsl/documentation/1.0"
                xmlns:xlink="http://www.w3.org/1999/xlink"
                exclude-result-prefixes="doc"
                version='1.0'>

<!-- ********************************************************************
     $Id: titles.xsl 9715 2013-01-24 00:16:57Z bobstayton $
     ********************************************************************

     This file is part of the XSL DocBook Stylesheet distribution.
     See ../README or http://docbook.sf.net/release/xsl/current/ for
     copyright and other information.

     ******************************************************************** -->

<!-- ==================================================================== -->

<!-- title markup -->

<doc:mode mode="title.markup" xmlns="">
<refpurpose>Provides access to element titles</refpurpose>
<refdescription id="title.markup-desc">
<para>Processing an element in the
<literal role="mode">title.markup</literal> mode produces the
title of the element. This does not include the label.
</para>
</refdescription>
</doc:mode>

<xsl:template match="*" mode="title.markup">
  <xsl:param name="allow-anchors" select="0"/>
  <xsl:param name="verbose" select="1"/>
  <xsl:choose>
    <!-- * FIXME: this should handle other *info elements as well -->
    <!-- * but this is good enough for now. -->
    <xsl:when test="title|info/title">
      <xsl:apply-templates select="(title|info/title)[1]" mode="title.markup">
        <xsl:with-param name="allow-anchors" select="$allow-anchors"/>
      </xsl:apply-templates>
    </xsl:when>
    <xsl:when test="local-name(.) = 'partintro'">
      <!-- partintro's don't have titles, use the parent (part or reference)
           title instead. -->
      <xsl:apply-templates select="parent::*" mode="title.markup"/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:if test="$verbose != 0">
        <xsl:message>
          <xsl:text>Request for title of element with no title: </xsl:text>
          <xsl:value-of select="local-name(.)"/>
          <xsl:choose>
            <xsl:when test="@id">
              <xsl:text> (id="</xsl:text>
              <xsl:value-of select="@id"/>
              <xsl:text>")</xsl:text>
            </xsl:when>
            <xsl:when test="@xml:id">
              <xsl:text> (xml:id="</xsl:text>
              <xsl:value-of select="@xml:id"/>
              <xsl:text>")</xsl:text>
            </xsl:when>
            <xsl:otherwise>
              <xsl:text> (contained in </xsl:text>
              <xsl:value-of select="local-name(..)"/>
              <xsl:if test="../@id or ../@xml:id">
                <xsl:text> with id </xsl:text>
                <xsl:value-of select="../@id | ../@xml:id"/>
              </xsl:if>
              <xsl:text>)</xsl:text>
            </xsl:otherwise>
          </xsl:choose>
        </xsl:message>
      </xsl:if>
      <xsl:text>???TITLE???</xsl:text>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="title" mode="title.markup">
  <xsl:param name="allow-anchors" select="0"/>

  <xsl:choose>
    <xsl:when test="$allow-anchors != 0">
      <xsl:apply-templates/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:apply-templates mode="no.anchor.mode"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<!-- only occurs in HTML Tables! -->
<xsl:template match="caption" mode="title.markup">
  <xsl:param name="allow-anchors" select="0"/>

  <xsl:choose>
    <xsl:when test="$allow-anchors != 0">
      <xsl:apply-templates/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:apply-templates mode="no.anchor.mode"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="set" mode="title.markup">
  <xsl:param name="allow-anchors" select="0"/>
  <xsl:apply-templates select="(setinfo/title|info/title|title)[1]"
                       mode="title.markup">
    <xsl:with-param name="allow-anchors" select="$allow-anchors"/>
  </xsl:apply-templates>
</xsl:template>

<xsl:template match="book" mode="title.markup">
  <xsl:param name="allow-anchors" select="0"/>
  <xsl:apply-templates select="(bookinfo/title|info/title|title)[1]"
                       mode="title.markup">
    <xsl:with-param name="allow-anchors" select="$allow-anchors"/>
  </xsl:apply-templates>
</xsl:template>

<xsl:template match="part" mode="title.markup">
  <xsl:param name="allow-anchors" select="0"/>
  <xsl:apply-templates select="(partinfo/title|info/title|docinfo/title|title)[1]"
                       mode="title.markup">
    <xsl:with-param name="allow-anchors" select="$allow-anchors"/>
  </xsl:apply-templates>
</xsl:template>

<xsl:template match="preface|chapter|appendix" mode="title.markup">
  <xsl:param name="allow-anchors" select="0"/>

<!--
  <xsl:message>
    <xsl:value-of select="local-name(.)"/>
    <xsl:text> </xsl:text>
    <xsl:value-of select="$allow-anchors"/>
  </xsl:message>
-->

  <xsl:variable name="title" select="(docinfo/title
                                      |info/title
                                      |prefaceinfo/title
                                      |chapterinfo/title
                                      |appendixinfo/title
                                      |title)[1]"/>
  <xsl:apply-templates select="$title" mode="title.markup">
    <xsl:with-param name="allow-anchors" select="$allow-anchors"/>
  </xsl:apply-templates>
</xsl:template>

<xsl:template match="dedication" mode="title.markup">
  <xsl:param name="allow-anchors" select="0"/>
  <xsl:choose>
    <xsl:when test="title|info/title">
      <xsl:apply-templates select="(title|info/title)[1]" mode="title.markup">
        <xsl:with-param name="allow-anchors" select="$allow-anchors"/>
      </xsl:apply-templates>
    </xsl:when>
    <xsl:otherwise>
      <xsl:call-template name="gentext">
        <xsl:with-param name="key" select="'Dedication'"/>
      </xsl:call-template>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="acknowledgements" mode="title.markup">
  <xsl:param name="allow-anchors" select="0"/>
  <xsl:choose>
    <xsl:when test="title|info/title">
      <xsl:apply-templates select="(title|info/title)[1]" mode="title.markup">
        <xsl:with-param name="allow-anchors" select="$allow-anchors"/>
      </xsl:apply-templates>
    </xsl:when>
    <xsl:otherwise>
      <xsl:call-template name="gentext">
        <xsl:with-param name="key" select="'Acknowledgements'"/>
      </xsl:call-template>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="colophon" mode="title.markup">
  <xsl:param name="allow-anchors" select="0"/>
  <xsl:choose>
    <xsl:when test="title|info/title">
      <xsl:apply-templates select="(title|info/title)[1]" mode="title.markup">
        <xsl:with-param name="allow-anchors" select="$allow-anchors"/>
      </xsl:apply-templates>
    </xsl:when>
    <xsl:otherwise>
      <xsl:call-template name="gentext">
        <xsl:with-param name="key" select="'Colophon'"/>
      </xsl:call-template>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="article" mode="title.markup">
  <xsl:param name="allow-anchors" select="0"/>
  <xsl:variable name="title" select="(artheader/title
                                      |articleinfo/title
                                      |info/title
                                      |title)[1]"/>

  <xsl:apply-templates select="$title" mode="title.markup">
    <xsl:with-param name="allow-anchors" select="$allow-anchors"/>
  </xsl:apply-templates>
</xsl:template>

<xsl:template match="reference" mode="title.markup">
  <xsl:param name="allow-anchors" select="0"/>
  <xsl:apply-templates select="(referenceinfo/title|docinfo/title|info/title|title)[1]"
                       mode="title.markup">
    <xsl:with-param name="allow-anchors" select="$allow-anchors"/>
  </xsl:apply-templates>
</xsl:template>

<xsl:template match="refentry" mode="title.markup">
  <xsl:param name="allow-anchors" select="0"/>
  <xsl:variable name="refmeta" select=".//refmeta"/>
  <xsl:variable name="refentrytitle" select="$refmeta//refentrytitle"/>
  <xsl:variable name="refnamediv" select=".//refnamediv"/>
  <xsl:variable name="refname" select="$refnamediv//refname"/>
  <xsl:variable name="refdesc" select="$refnamediv//refdescriptor"/>

  <xsl:variable name="title">
    <xsl:choose>
      <xsl:when test="$refentrytitle">
        <xsl:apply-templates select="$refentrytitle[1]" mode="title.markup"/>
      </xsl:when>
      <xsl:when test="$refdesc">
        <xsl:apply-templates select="$refdesc" mode="title.markup"/>
      </xsl:when>
      <xsl:when test="$refname">
        <xsl:apply-templates select="$refname[1]" mode="title.markup"/>
      </xsl:when>
      <xsl:otherwise>REFENTRY WITHOUT TITLE???</xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:copy-of select="$title"/>
</xsl:template>

<xsl:template match="refentrytitle|refname|refdescriptor" mode="title.markup">
  <xsl:param name="allow-anchors" select="0"/>
  <xsl:choose>
    <xsl:when test="$allow-anchors != 0">
      <xsl:apply-templates/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:apply-templates mode="no.anchor.mode"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="section
                     |sect1|sect2|sect3|sect4|sect5
                     |refsect1|refsect2|refsect3|refsection
                     |topic
                     |simplesect"
              mode="title.markup">
  <xsl:param name="allow-anchors" select="0"/>
  <xsl:variable name="title" select="(info/title
                                      |sectioninfo/title
                                      |sect1info/title
                                      |sect2info/title
                                      |sect3info/title
                                      |sect4info/title
                                      |sect5info/title
                                      |refsect1info/title
                                      |refsect2info/title
                                      |refsect3info/title
                                      |refsectioninfo/title
                                      |title)[1]"/>

  <xsl:apply-templates select="$title" mode="title.markup">
    <xsl:with-param name="allow-anchors" select="$allow-anchors"/>
  </xsl:apply-templates>
</xsl:template>

<xsl:template match="bridgehead" mode="title.markup">
  <xsl:apply-templates/> 
</xsl:template>

<xsl:template match="refsynopsisdiv" mode="title.markup">
  <xsl:param name="allow-anchors" select="0"/>
  <xsl:choose>
    <xsl:when test="title|info/title">
      <xsl:apply-templates select="(title|info/title)[1]" mode="title.markup">
        <xsl:with-param name="allow-anchors" select="$allow-anchors"/>
      </xsl:apply-templates>
    </xsl:when>
    <xsl:otherwise>
      <xsl:call-template name="gentext">
        <xsl:with-param name="key" select="'RefSynopsisDiv'"/>
      </xsl:call-template>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="bibliography" mode="title.markup">
  <xsl:param name="allow-anchors" select="0"/>
  <xsl:variable name="title" select="(bibliographyinfo/title|info/title|title)[1]"/>
  <xsl:choose>
    <xsl:when test="$title">
      <xsl:apply-templates select="$title" mode="title.markup">
        <xsl:with-param name="allow-anchors" select="$allow-anchors"/>
      </xsl:apply-templates>
    </xsl:when>
    <xsl:otherwise>
      <xsl:call-template name="gentext">
        <xsl:with-param name="key" select="'Bibliography'"/>
      </xsl:call-template>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="glossary" mode="title.markup">
  <xsl:param name="allow-anchors" select="0"/>
  <xsl:variable name="title" select="(glossaryinfo/title|info/title|title)[1]"/>
  <xsl:choose>
    <xsl:when test="$title">
      <xsl:apply-templates select="$title" mode="title.markup">
        <xsl:with-param name="allow-anchors" select="$allow-anchors"/>
      </xsl:apply-templates>
    </xsl:when>
    <xsl:otherwise>
      <xsl:call-template name="gentext.element.name">
        <xsl:with-param name="element.name" select="local-name(.)"/>
      </xsl:call-template>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="glossdiv" mode="title.markup">
  <xsl:param name="allow-anchors" select="0"/>
  <xsl:variable name="title" select="(info/title|title)[1]"/>
  <xsl:choose>
    <xsl:when test="$title">
      <xsl:apply-templates select="$title" mode="title.markup">
        <xsl:with-param name="allow-anchors" select="$allow-anchors"/>
      </xsl:apply-templates>
    </xsl:when>
    <xsl:otherwise>
      <xsl:message>ERROR: glossdiv missing its required title</xsl:message>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="glossentry" mode="title.markup">
  <xsl:param name="allow-anchors" select="0"/>
  <xsl:apply-templates select="glossterm" mode="title.markup">
    <xsl:with-param name="allow-anchors" select="$allow-anchors"/>
  </xsl:apply-templates>
</xsl:template>

<xsl:template match="glossterm|firstterm" mode="title.markup">
  <xsl:param name="allow-anchors" select="0"/>

  <xsl:choose>
    <xsl:when test="$allow-anchors != 0">
      <xsl:apply-templates/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:apply-templates mode="no.anchor.mode"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="index" mode="title.markup">
  <xsl:param name="allow-anchors" select="0"/>
  <xsl:variable name="title" select="(indexinfo/title|info/title|title)[1]"/>
  <xsl:choose>
    <xsl:when test="$title">
      <xsl:apply-templates select="$title" mode="title.markup">
        <xsl:with-param name="allow-anchors" select="$allow-anchors"/>
      </xsl:apply-templates>
    </xsl:when>
    <xsl:otherwise>
      <xsl:call-template name="gentext">
        <xsl:with-param name="key" select="'Index'"/>
      </xsl:call-template>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="setindex" mode="title.markup">
  <xsl:param name="allow-anchors" select="0"/>
  <xsl:variable name="title" select="(setindexinfo/title|info/title|title)[1]"/>
  <xsl:choose>
    <xsl:when test="$title">
      <xsl:apply-templates select="$title" mode="title.markup">
        <xsl:with-param name="allow-anchors" select="$allow-anchors"/>
      </xsl:apply-templates>
    </xsl:when>
    <xsl:otherwise>
      <xsl:call-template name="gentext">
        <xsl:with-param name="key" select="'SetIndex'"/>
      </xsl:call-template>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="figure|example|equation" mode="title.markup">
  <xsl:param name="allow-anchors" select="0"/>
  <xsl:apply-templates select="(title|info/title)[1]" mode="title.markup">
    <xsl:with-param name="allow-anchors" select="$allow-anchors"/>
  </xsl:apply-templates>
</xsl:template>

<xsl:template match="table" mode="title.markup">
  <xsl:param name="allow-anchors" select="0"/>
  <xsl:apply-templates select="(title|info/title|caption)[1]" mode="title.markup">
    <xsl:with-param name="allow-anchors" select="$allow-anchors"/>
  </xsl:apply-templates>
</xsl:template>

<xsl:template match="procedure" mode="title.markup">
  <xsl:param name="allow-anchors" select="0"/>
  <xsl:apply-templates select="(title|info/title)[1]" mode="title.markup">
    <xsl:with-param name="allow-anchors" select="$allow-anchors"/>
  </xsl:apply-templates>
</xsl:template>

<xsl:template match="task" mode="title.markup">
  <xsl:param name="allow-anchors" select="0"/>
  <xsl:apply-templates select="(title|info/title)[1]" mode="title.markup">
    <xsl:with-param name="allow-anchors" select="$allow-anchors"/>
  </xsl:apply-templates>
</xsl:template>

<xsl:template match="sidebar" mode="title.markup">
  <xsl:param name="allow-anchors" select="0"/>
  <xsl:apply-templates select="(info/title|sidebarinfo/title|title)[1]"
                       mode="title.markup">
    <xsl:with-param name="allow-anchors" select="$allow-anchors"/>
  </xsl:apply-templates>
</xsl:template>

<xsl:template match="abstract" mode="title.markup">
  <xsl:param name="allow-anchors" select="0"/>
  <xsl:choose>
    <xsl:when test="title|info/title">
      <xsl:apply-templates select="(title|info/title)[1]" mode="title.markup">
        <xsl:with-param name="allow-anchors" select="$allow-anchors"/>
      </xsl:apply-templates>
    </xsl:when>
    <xsl:otherwise>
      <xsl:call-template name="gentext">
        <xsl:with-param name="key" select="'Abstract'"/>
      </xsl:call-template>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="caution|tip|warning|important|note" mode="title.markup">
  <xsl:param name="allow-anchors" select="0"/>
  <xsl:variable name="title" select="(title|info/title)[1]"/>
  <xsl:choose>
    <xsl:when test="$title">
      <xsl:apply-templates select="$title" mode="title.markup">
        <xsl:with-param name="allow-anchors" select="$allow-anchors"/>
      </xsl:apply-templates>
    </xsl:when>
    <xsl:otherwise>
      <xsl:call-template name="gentext">
        <xsl:with-param name="key">
          <xsl:choose>
            <xsl:when test="local-name(.)='note'">Note</xsl:when>
            <xsl:when test="local-name(.)='important'">Important</xsl:when>
            <xsl:when test="local-name(.)='caution'">Caution</xsl:when>
            <xsl:when test="local-name(.)='warning'">Warning</xsl:when>
            <xsl:when test="local-name(.)='tip'">Tip</xsl:when>
          </xsl:choose>
        </xsl:with-param>
      </xsl:call-template>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="question" mode="title.markup">
  <!-- questions don't have titles -->
  <xsl:text>Question</xsl:text>
</xsl:template>

<xsl:template match="answer" mode="title.markup">
  <!-- answers don't have titles -->
  <xsl:text>Answer</xsl:text>
</xsl:template>

<xsl:template match="qandaentry" mode="title.markup">
  <!-- qandaentrys are represented by the first question in them -->
  <xsl:text>Question</xsl:text>
</xsl:template>

<xsl:template match="qandaset" mode="title.markup">
  <xsl:param name="allow-anchors" select="0"/>
  <xsl:variable name="title" select="(info/title|
                                      blockinfo/title|
                                      title)[1]"/>
  <xsl:choose>
    <xsl:when test="$title">
      <xsl:apply-templates select="$title" mode="title.markup">
        <xsl:with-param name="allow-anchors" select="$allow-anchors"/>
      </xsl:apply-templates>
    </xsl:when>
    <xsl:otherwise>
      <xsl:call-template name="gentext">
        <xsl:with-param name="key" select="'QandASet'"/>
      </xsl:call-template>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="legalnotice" mode="title.markup">
  <xsl:param name="allow-anchors" select="0"/>
  <xsl:choose>
    <xsl:when test="title|info/title">
      <xsl:apply-templates select="(title|info/title)[1]" mode="title.markup">
        <xsl:with-param name="allow-anchors" select="$allow-anchors"/>
      </xsl:apply-templates>
    </xsl:when>
    <xsl:otherwise>
      <xsl:call-template name="gentext">
        <xsl:with-param name="key" select="'LegalNotice'"/>
      </xsl:call-template>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<!-- ============================================================ -->

<!-- titleabbrev is always processed in a mode -->
<xsl:template match="titleabbrev"/>

<xsl:template match="*" mode="titleabbrev.markup">
  <xsl:param name="allow-anchors" select="0"/>
  <xsl:param name="verbose" select="1"/>

  <xsl:choose>
    <xsl:when test="titleabbrev">
      <xsl:apply-templates select="titleabbrev[1]" mode="title.markup">
        <xsl:with-param name="allow-anchors" select="$allow-anchors"/>
      </xsl:apply-templates>
    </xsl:when>
    <xsl:when test="info/titleabbrev">
      <xsl:apply-templates select="info/titleabbrev[1]" mode="title.markup">
        <xsl:with-param name="allow-anchors" select="$allow-anchors"/>
      </xsl:apply-templates>
    </xsl:when>
    <xsl:otherwise>
      <xsl:apply-templates select="." mode="title.markup">
        <xsl:with-param name="allow-anchors" select="$allow-anchors"/>
        <xsl:with-param name="verbose" select="$verbose"/>
      </xsl:apply-templates>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="book|part|set|preface|chapter|appendix" mode="titleabbrev.markup">
  <xsl:param name="allow-anchors" select="0"/>
  <xsl:param name="verbose" select="1"/>

  <xsl:variable name="titleabbrev" select="(docinfo/titleabbrev
                                           |bookinfo/titleabbrev
                                           |info/titleabbrev
                                           |prefaceinfo/titleabbrev
                                           |setinfo/titleabbrev
                                           |partinfo/titleabbrev
                                           |chapterinfo/titleabbrev
                                           |appendixinfo/titleabbrev
                                           |titleabbrev)[1]"/>

  <xsl:choose>
    <xsl:when test="$titleabbrev">
      <xsl:apply-templates select="$titleabbrev" mode="title.markup">
        <xsl:with-param name="allow-anchors" select="$allow-anchors"/>
      </xsl:apply-templates>
    </xsl:when>
    <xsl:otherwise>
      <xsl:apply-templates select="." mode="title.markup">
        <xsl:with-param name="allow-anchors" select="$allow-anchors"/>
        <xsl:with-param name="verbose" select="$verbose"/>
      </xsl:apply-templates>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="article" mode="titleabbrev.markup">
  <xsl:param name="allow-anchors" select="0"/>
  <xsl:param name="verbose" select="1"/>

  <xsl:variable name="titleabbrev" select="(artheader/titleabbrev
                                           |articleinfo/titleabbrev
                                           |info/titleabbrev
                                           |titleabbrev)[1]"/>

  <xsl:choose>
    <xsl:when test="$titleabbrev">
      <xsl:apply-templates select="$titleabbrev" mode="title.markup">
        <xsl:with-param name="allow-anchors" select="$allow-anchors"/>
      </xsl:apply-templates>
    </xsl:when>
    <xsl:otherwise>
      <xsl:apply-templates select="." mode="title.markup">
        <xsl:with-param name="allow-anchors" select="$allow-anchors"/>
        <xsl:with-param name="verbose" select="$verbose"/>
      </xsl:apply-templates>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="section
                     |sect1|sect2|sect3|sect4|sect5
                     |refsect1|refsect2|refsect3
                     |topic
                     |simplesect"
              mode="titleabbrev.markup">
  <xsl:param name="allow-anchors" select="0"/>
  <xsl:param name="verbose" select="1"/>

  <xsl:variable name="titleabbrev" select="(info/titleabbrev
                                            |sectioninfo/titleabbrev
                                            |sect1info/titleabbrev
                                            |sect2info/titleabbrev
                                            |sect3info/titleabbrev
                                            |sect4info/titleabbrev
                                            |sect5info/titleabbrev
                                            |refsect1info/titleabbrev
                                            |refsect2info/titleabbrev
                                            |refsect3info/titleabbrev
                                            |titleabbrev)[1]"/>

  <xsl:choose>
    <xsl:when test="$titleabbrev">
      <xsl:apply-templates select="$titleabbrev" mode="title.markup">
        <xsl:with-param name="allow-anchors" select="$allow-anchors"/>
      </xsl:apply-templates>
    </xsl:when>
    <xsl:otherwise>
      <xsl:apply-templates select="." mode="title.markup">
        <xsl:with-param name="allow-anchors" select="$allow-anchors"/>
        <xsl:with-param name="verbose" select="$verbose"/>
      </xsl:apply-templates>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="titleabbrev" mode="title.markup">
  <xsl:param name="allow-anchors" select="0"/>

  <xsl:choose>
    <xsl:when test="$allow-anchors != 0">
      <xsl:apply-templates/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:apply-templates mode="no.anchor.mode"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<!-- ============================================================ -->

<xsl:template match="*" mode="no.anchor.mode">
  <!-- Switch to normal mode if no links -->
  <xsl:choose>
    <xsl:when test="descendant-or-self::footnote or
                    descendant-or-self::anchor or
                    descendant-or-self::ulink or
                    descendant-or-self::link or
                    descendant-or-self::olink or
                    descendant-or-self::xref or
                    descendant-or-self::indexterm or
		    (ancestor::title and (@id or @xml:id))">

      <xsl:apply-templates mode="no.anchor.mode"/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:apply-templates select="."/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="footnote" mode="no.anchor.mode">
  <!-- nop, suppressed -->
</xsl:template>

<xsl:template match="anchor" mode="no.anchor.mode">
  <!-- nop, suppressed -->
</xsl:template>

<xsl:template match="ulink" mode="no.anchor.mode">
  <xsl:apply-templates/>
</xsl:template>

<xsl:template match="link" mode="no.anchor.mode">
  <xsl:choose>
    <xsl:when test="count(child::node()) &gt; 0">
      <!-- If it has content, use it -->
      <xsl:apply-templates/>
    </xsl:when>
	<!-- look for an endterm -->
    <xsl:when test="@endterm">
      <xsl:variable name="etargets" select="key('id',@endterm)"/>
      <xsl:variable name="etarget" select="$etargets[1]"/>
      <xsl:choose>
	<xsl:when test="count($etarget) = 0">
          <xsl:message>
	    <xsl:value-of select="count($etargets)"/>
	    <xsl:text>Endterm points to nonexistent ID: </xsl:text>
	    <xsl:value-of select="@endterm"/>
          </xsl:message>
	  <xsl:text>???</xsl:text>
	</xsl:when>
        <xsl:otherwise>
	  <xsl:apply-templates select="$etarget" mode="endterm"/>
	</xsl:otherwise>
      </xsl:choose>
    </xsl:when>
    <xsl:otherwise>
      <xsl:apply-templates/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="olink" mode="no.anchor.mode">
  <xsl:apply-templates/>
</xsl:template>

<xsl:template match="indexterm" mode="no.anchor.mode">
  <!-- nop, suppressed -->
</xsl:template>

<xsl:template match="xref" mode="no.anchor.mode">
  <xsl:variable name="targets" select="key('id',@linkend)|key('id',substring-after(@xlink:href,'#'))"/>
  <xsl:variable name="target" select="$targets[1]"/>
  <xsl:variable name="refelem" select="local-name($target)"/>
  
  <xsl:call-template name="check.id.unique">
    <xsl:with-param name="linkend" select="@linkend"/>
  </xsl:call-template>

  <xsl:choose>
    <xsl:when test="count($target) = 0">
      <xsl:message>
        <xsl:text>XRef to nonexistent id: </xsl:text>
        <xsl:value-of select="@linkend"/> 
        <xsl:value-of select="@xlink:href"/>
      </xsl:message>
      <xsl:text>???</xsl:text>
    </xsl:when>

    <xsl:when test="@endterm">
      <xsl:variable name="etargets" select="key('id',@endterm)"/>
      <xsl:variable name="etarget" select="$etargets[1]"/>
      <xsl:choose>
        <xsl:when test="count($etarget) = 0">
          <xsl:message>
            <xsl:value-of select="count($etargets)"/>
            <xsl:text>Endterm points to nonexistent ID: </xsl:text>
            <xsl:value-of select="@endterm"/>
          </xsl:message>
          <xsl:text>???</xsl:text>
        </xsl:when>
        <xsl:otherwise>
          <xsl:apply-templates select="$etarget" mode="endterm"/>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:when>

    <xsl:when test="$target/@xreflabel">
      <xsl:call-template name="xref.xreflabel">
        <xsl:with-param name="target" select="$target"/>
      </xsl:call-template>
    </xsl:when>

    <xsl:otherwise>
   
      <xsl:choose>
	<!-- Watch out for the case when there is a xref or link inside 
	     a title. See bugs #1811721 and #1838136. -->
	<xsl:when test="not(ancestor::*[@id = $target/@id] or ancestor::*[@xml:id = $target/@xml:id])">

	  <xsl:apply-templates select="$target" mode="xref-to-prefix"/>
	  
	  <xsl:apply-templates select="$target" mode="xref-to">
	    
	    <xsl:with-param name="referrer" select="."/>
	    <xsl:with-param name="xrefstyle">
	      <xsl:choose>
		<xsl:when test="@role and not(@xrefstyle) and $use.role.as.xrefstyle != 0">
		  <xsl:value-of select="@role"/>
		</xsl:when>
		<xsl:otherwise>
		  <xsl:value-of select="@xrefstyle"/>
		</xsl:otherwise>
	      </xsl:choose>
	    </xsl:with-param>
	  </xsl:apply-templates>
	  
	  <xsl:apply-templates select="$target" mode="xref-to-suffix"/>
	</xsl:when>
	
	<xsl:otherwise>
	  <xsl:apply-templates/>
	</xsl:otherwise>
      
      </xsl:choose>
    </xsl:otherwise>
  </xsl:choose>

</xsl:template>

<!-- ============================================================ -->

</xsl:stylesheet>

