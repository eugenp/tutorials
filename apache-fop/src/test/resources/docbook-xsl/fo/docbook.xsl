<?xml version='1.0'?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:exsl="http://exslt.org/common"
                xmlns:fo="http://www.w3.org/1999/XSL/Format"
                xmlns:ng="http://docbook.org/docbook-ng"
                xmlns:db="http://docbook.org/ns/docbook"
                exclude-result-prefixes="db ng exsl"
                version='1.0'>

<!-- It is important to use indent="no" here, otherwise verbatim -->
<!-- environments get broken by indented tags...at least when the -->
<!-- callout extension is used...at least with some processors -->
<xsl:output method="xml" indent="no"/>

<!-- ********************************************************************
     $Id: docbook.xsl 9647 2012-10-26 17:42:03Z bobstayton $
     ********************************************************************

     This file is part of the XSL DocBook Stylesheet distribution.
     See ../README or http://docbook.sf.net/release/xsl/current/ for
     copyright and other information.

     ******************************************************************** -->

<!-- ==================================================================== -->

<xsl:include href="../VERSION.xsl"/>
<xsl:include href="param.xsl"/>
<xsl:include href="../lib/lib.xsl"/>
<xsl:include href="../common/l10n.xsl"/>
<xsl:include href="../common/common.xsl"/>
<xsl:include href="../common/utility.xsl"/>
<xsl:include href="../common/labels.xsl"/>
<xsl:include href="../common/titles.xsl"/>
<xsl:include href="../common/subtitles.xsl"/>
<xsl:include href="../common/gentext.xsl"/>
<xsl:include href="../common/olink.xsl"/>
<xsl:include href="../common/targets.xsl"/>
<xsl:include href="../common/pi.xsl"/>
<xsl:include href="autotoc.xsl"/>
<xsl:include href="autoidx.xsl"/>
<xsl:include href="lists.xsl"/>
<xsl:include href="callout.xsl"/>
<xsl:include href="verbatim.xsl"/>
<xsl:include href="graphics.xsl"/>
<xsl:include href="xref.xsl"/>
<xsl:include href="formal.xsl"/>
<xsl:include href="table.xsl"/>
<xsl:include href="htmltbl.xsl"/>
<xsl:include href="sections.xsl"/>
<xsl:include href="inline.xsl"/>
<xsl:include href="footnote.xsl"/>
<xsl:include href="fo.xsl"/>
<xsl:include href="fo-rtf.xsl"/>
<xsl:include href="info.xsl"/>
<xsl:include href="keywords.xsl"/>
<xsl:include href="division.xsl"/>
<xsl:include href="index.xsl"/>
<xsl:include href="toc.xsl"/>
<xsl:include href="refentry.xsl"/>
<xsl:include href="math.xsl"/>
<xsl:include href="admon.xsl"/>
<xsl:include href="component.xsl"/>
<xsl:include href="biblio.xsl"/>
<xsl:include href="biblio-iso690.xsl"/>
<xsl:include href="glossary.xsl"/>
<xsl:include href="block.xsl"/>
<xsl:include href="task.xsl"/>
<xsl:include href="qandaset.xsl"/>
<xsl:include href="synop.xsl"/>
<xsl:include href="titlepage.xsl"/>
<xsl:include href="titlepage.templates.xsl"/>
<xsl:include href="pagesetup.xsl"/>
<xsl:include href="pi.xsl"/>
<xsl:include href="spaces.xsl"/>
<xsl:include href="ebnf.xsl"/>
<xsl:include href="../html/chunker.xsl"/>
<xsl:include href="annotations.xsl"/>
<xsl:include href="../common/stripns.xsl"/>

<xsl:include href="fop.xsl"/>
<xsl:include href="fop1.xsl"/>
<xsl:include href="xep.xsl"/>
<xsl:include href="axf.xsl"/>
<xsl:include href="ptc.xsl"/>

<xsl:param name="stylesheet.result.type" select="'fo'"/>

<!-- ==================================================================== -->

<xsl:key name="id" match="*" use="@id|@xml:id"/>

<!-- ==================================================================== -->

<xsl:template match="*">
  <xsl:message>
    <xsl:text>Element </xsl:text>
    <xsl:value-of select="local-name(.)"/>
    <xsl:text> in namespace '</xsl:text>
    <xsl:value-of select="namespace-uri(.)"/>
    <xsl:text>' encountered</xsl:text>
    <xsl:if test="parent::*">
      <xsl:text> in </xsl:text>
      <xsl:value-of select="name(parent::*)"/>
    </xsl:if>
    <xsl:text>, but no template matches.</xsl:text>
  </xsl:message>
  
  <fo:block color="red">
    <xsl:text>&lt;</xsl:text>
    <xsl:value-of select="name(.)"/>
    <xsl:text>&gt;</xsl:text>
    <xsl:apply-templates/> 
    <xsl:text>&lt;/</xsl:text>
    <xsl:value-of select="name(.)"/>
    <xsl:text>&gt;</xsl:text>
  </fo:block>
</xsl:template>

<!-- Update this list if new root elements supported -->
<xsl:variable name="root.elements" select="' appendix article bibliography book chapter colophon dedication glossary index part preface qandaset refentry reference sect1 section set setindex '"/>

<xsl:template match="/">
  <!-- * Get a title for current doc so that we let the user -->
  <!-- * know what document we are processing at this point. -->
  <xsl:variable name="doc.title">
    <xsl:call-template name="get.doc.title"/>
  </xsl:variable>
  <xsl:choose>
    <!-- Hack! If someone hands us a DocBook V5.x or DocBook NG document,
         toss the namespace and continue.  Use the docbook5 namespaced
         stylesheets for DocBook5 if you don't want to use this feature.-->
    <xsl:when test="$exsl.node.set.available != 0
                    and (*/self::ng:* or */self::db:*)">
      <xsl:call-template name="log.message">
        <xsl:with-param name="level">Note</xsl:with-param>
        <xsl:with-param name="source" select="$doc.title"/>
        <xsl:with-param name="context-desc">
          <xsl:text>namesp. cut</xsl:text>
        </xsl:with-param>
        <xsl:with-param name="message">
          <xsl:text>stripped namespace before processing</xsl:text>
        </xsl:with-param>
      </xsl:call-template>
      <xsl:variable name="nons">
        <xsl:apply-templates mode="stripNS"/>
      </xsl:variable>
      <xsl:call-template name="log.message">
        <xsl:with-param name="level">Note</xsl:with-param>
        <xsl:with-param name="source" select="$doc.title"/>
        <xsl:with-param name="context-desc">
          <xsl:text>namesp. cut</xsl:text>
        </xsl:with-param>
        <xsl:with-param name="message">
          <xsl:text>processing stripped document</xsl:text>
        </xsl:with-param>
      </xsl:call-template>
      <xsl:apply-templates select="exsl:node-set($nons)"/>
    </xsl:when>
    <!-- Can't process unless namespace removed -->
    <xsl:when test="*/self::ng:* or */self::db:*">
      <xsl:message terminate="yes">
        <xsl:text>Unable to strip the namespace from DB5 document,</xsl:text>
        <xsl:text> cannot proceed.</xsl:text>
      </xsl:message>
    </xsl:when>
    <xsl:otherwise>
      <xsl:choose>
        <xsl:when test="$rootid != ''">
          <xsl:variable name="root.element" select="key('id', $rootid)"/>
          <xsl:choose>
            <xsl:when test="count($root.element) = 0">
              <xsl:message terminate="yes">
                <xsl:text>ID '</xsl:text>
                <xsl:value-of select="$rootid"/>
                <xsl:text>' not found in document.</xsl:text>
              </xsl:message>
            </xsl:when>
            <xsl:when test="not(contains($root.elements, concat(' ', local-name($root.element), ' ')))">
              <xsl:message terminate="yes">
                <xsl:text>ERROR: Document root element ($rootid=</xsl:text>
                <xsl:value-of select="$rootid"/>
                <xsl:text>) for FO output </xsl:text>
                <xsl:text>must be one of the following elements:</xsl:text>
                <xsl:value-of select="$root.elements"/>
              </xsl:message>
            </xsl:when>
            <!-- Otherwise proceed -->
            <xsl:otherwise>
              <xsl:if test="$collect.xref.targets = 'yes' or
                            $collect.xref.targets = 'only'">
                <xsl:apply-templates select="$root.element"
                                     mode="collect.targets"/>
              </xsl:if>
              <xsl:if test="$collect.xref.targets != 'only'">
                <xsl:apply-templates select="$root.element"
                                     mode="process.root"/>
              </xsl:if>
            </xsl:otherwise>
          </xsl:choose>
        </xsl:when>
        <!-- Otherwise process the document root element -->
        <xsl:otherwise>
          <xsl:variable name="document.element" select="*[1]"/>
          <xsl:choose>
            <xsl:when test="not(contains($root.elements,
                     concat(' ', local-name($document.element), ' ')))">
              <xsl:message terminate="yes">
                <xsl:text>ERROR: Document root element for FO output </xsl:text>
                <xsl:text>must be one of the following elements:</xsl:text>
                <xsl:value-of select="$root.elements"/>
              </xsl:message>
            </xsl:when>
            <!-- Otherwise proceed -->
            <xsl:otherwise>
              <xsl:if test="$collect.xref.targets = 'yes' or
                            $collect.xref.targets = 'only'">
                <xsl:apply-templates select="/"
                                     mode="collect.targets"/>
              </xsl:if>
              <xsl:if test="$collect.xref.targets != 'only'">
                <xsl:apply-templates select="/"
                                     mode="process.root"/>
              </xsl:if>
            </xsl:otherwise>
          </xsl:choose>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="*" mode="process.root">
  <xsl:variable name="document.element" select="self::*"/>

  <xsl:call-template name="root.messages"/>

  <xsl:variable name="title">
    <xsl:choose>
      <xsl:when test="$document.element/title[1]">
        <xsl:value-of select="$document.element/title[1]"/>
      </xsl:when>
      <xsl:otherwise>[could not find document title]</xsl:otherwise>
    </xsl:choose>
  </xsl:variable>
  
  <!-- Include all id values in XEP output -->
  <xsl:if test="$xep.extensions != 0">
    <xsl:processing-instruction 
     name="xep-pdf-drop-unused-destinations">false</xsl:processing-instruction>
  </xsl:if>

  <fo:root xsl:use-attribute-sets="root.properties">
    <xsl:attribute name="language">
      <xsl:call-template name="l10n.language">
        <xsl:with-param name="target" select="/*[1]"/>
      </xsl:call-template>
    </xsl:attribute>

    <xsl:if test="$xep.extensions != 0">
      <xsl:call-template name="xep-pis"/>
      <xsl:call-template name="xep-document-information"/>
    </xsl:if>
    <xsl:if test="$axf.extensions != 0">
      <xsl:call-template name="axf-document-information"/>
    </xsl:if>

    <xsl:call-template name="setup.pagemasters"/>

    <xsl:if test="$fop.extensions != 0">
      <xsl:apply-templates select="$document.element" mode="fop.outline"/>
    </xsl:if>

    <xsl:if test="$fop1.extensions != 0">
      <xsl:call-template name="fop1-document-information"/>
      <xsl:variable name="bookmarks">
        <xsl:apply-templates select="$document.element" 
                             mode="fop1.outline"/>
      </xsl:variable>
      <xsl:if test="string($bookmarks) != ''">
        <fo:bookmark-tree>
          <xsl:copy-of select="$bookmarks"/>
        </fo:bookmark-tree>
      </xsl:if>
      <xsl:apply-templates select="$document.element" 
                           mode="fop1.foxdest"/>
    </xsl:if>

    <xsl:if test="$xep.extensions != 0">
      <xsl:variable name="bookmarks">
        <xsl:apply-templates select="$document.element" mode="xep.outline"/>
      </xsl:variable>
      <xsl:if test="string($bookmarks) != ''">
        <rx:outline xmlns:rx="http://www.renderx.com/XSL/Extensions">
          <xsl:copy-of select="$bookmarks"/>
        </rx:outline>
      </xsl:if>
    </xsl:if>

    <xsl:if test="$arbortext.extensions != 0 and $ati.xsl11.bookmarks != 0">
      <xsl:variable name="bookmarks">
        <xsl:apply-templates select="$document.element"
                             mode="ati.xsl11.bookmarks"/>
      </xsl:variable>
      <xsl:if test="string($bookmarks) != ''">
        <fo:bookmark-tree>
          <xsl:copy-of select="$bookmarks"/>
        </fo:bookmark-tree>
      </xsl:if>
    </xsl:if>

    <xsl:apply-templates select="$document.element"/>
  </fo:root>
</xsl:template>

<xsl:template name="root.messages">
  <!-- redefine this any way you'd like to output messages -->
  <!-- DO NOT OUTPUT ANYTHING FROM THIS TEMPLATE -->
  <xsl:message>
    <xsl:text>Making </xsl:text>
    <xsl:value-of select="$page.orientation"/>
    <xsl:text> pages on </xsl:text>
    <xsl:value-of select="$paper.type"/>
    <xsl:text> paper (</xsl:text>
    <xsl:value-of select="$page.width"/>
    <xsl:text>x</xsl:text>
    <xsl:value-of select="$page.height"/>
    <xsl:text>)</xsl:text>
  </xsl:message>
</xsl:template>

<xsl:template match="screen">
  <fo:block font-size="9pt" font-family="monospace"
 white-space-treatment="preserve"
 linefeed-treatment="preserve"
 margin-top="0.5em">
    <xsl:apply-templates/>
  </fo:block>
</xsl:template>

<!-- ==================================================================== -->

</xsl:stylesheet>
