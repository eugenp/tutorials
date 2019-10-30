<?xml version='1.0'?>
<!DOCTYPE xsl:stylesheet [
<!ENTITY % common.entities SYSTEM "../common/entities.ent">
%common.entities;
]>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fo="http://www.w3.org/1999/XSL/Format"
                xmlns:rx="http://www.renderx.com/XSL/Extensions"
                version='1.0'>

<!-- ********************************************************************
     $Id: index.xsl 9286 2012-04-19 10:10:58Z bobstayton $
     ********************************************************************

     This file is part of the XSL DocBook Stylesheet distribution.
     See ../README or http://docbook.sf.net/release/xsl/current/ for
     copyright and other information.

     ******************************************************************** -->

<!-- ==================================================================== -->

<xsl:template match="index">
  <xsl:variable name="id">
    <xsl:call-template name="object.id"/>
  </xsl:variable>

 <xsl:if test="$generate.index != 0">
  <xsl:choose>
    <xsl:when test="$make.index.markup != 0">
      <fo:block>
        <xsl:call-template name="generate-index-markup">
          <xsl:with-param name="scope" select="(ancestor::book|/)[last()]"/>
        </xsl:call-template>
      </fo:block>
    </xsl:when>
    <xsl:otherwise>
      <fo:block id="{$id}">
        <xsl:call-template name="index.titlepage"/>
      </fo:block>
      <xsl:apply-templates/>
      <xsl:if test="count(indexentry) = 0 and count(indexdiv) = 0">
        <xsl:call-template name="generate-index">
          <xsl:with-param name="scope" select="(ancestor::book|/)[last()]"/>
        </xsl:call-template>
      </xsl:if>
    </xsl:otherwise>
  </xsl:choose>
 </xsl:if>
</xsl:template>

<xsl:template match="book/index|part/index">
  <xsl:variable name="id">
    <xsl:call-template name="object.id"/>
  </xsl:variable>

 <xsl:if test="$generate.index != 0">
  <xsl:variable name="master-reference">
    <xsl:call-template name="select.pagemaster">
      <xsl:with-param name="pageclass">
        <xsl:if test="$make.index.markup != 0">body</xsl:if>
      </xsl:with-param>
    </xsl:call-template>
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
                xsl:use-attribute-sets="component.titlepage.properties">
        <xsl:call-template name="index.titlepage"/>
      </fo:block>
      <xsl:apply-templates/>
      <xsl:if test="count(indexentry) = 0 and count(indexdiv) = 0">

        <xsl:choose>
          <xsl:when test="$make.index.markup != 0">
            <fo:block wrap-option='no-wrap'
                      white-space-collapse='false'
                      xsl:use-attribute-sets="monospace.verbatim.properties"
                      linefeed-treatment="preserve">
              <xsl:call-template name="generate-index-markup">
                <xsl:with-param name="scope" select="(ancestor::book|/)[last()]"/>
              </xsl:call-template>
            </fo:block>
          </xsl:when>
          <xsl:when test="indexentry|indexdiv/indexentry">
            <xsl:apply-templates/>
          </xsl:when>
          <xsl:otherwise>
            <xsl:call-template name="generate-index">
              <xsl:with-param name="scope" select="(ancestor::book|/)[last()]"/>
            </xsl:call-template>
          </xsl:otherwise>
        </xsl:choose>
      </xsl:if>
    </fo:flow>
  </fo:page-sequence>
 </xsl:if>
</xsl:template>

<xsl:template match="setindex">
  <xsl:variable name="id">
    <xsl:call-template name="object.id"/>
  </xsl:variable>

 <xsl:if test="$generate.index != 0">
  <xsl:variable name="master-reference">
    <xsl:call-template name="select.pagemaster">
      <xsl:with-param name="pageclass">
        <xsl:choose>
          <xsl:when test="$make.index.markup != 0">body</xsl:when>
          <xsl:otherwise>index</xsl:otherwise>
        </xsl:choose>
      </xsl:with-param>
    </xsl:call-template>
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

      <fo:block id="{$id}">
        <xsl:call-template name="setindex.titlepage"/>
      </fo:block>
      <xsl:apply-templates/>
      <xsl:if test="count(indexentry) = 0 and count(indexdiv) = 0">

        <xsl:choose>
          <xsl:when test="$make.index.markup != 0">
            <fo:block wrap-option='no-wrap'
                      white-space-collapse='false'
                      xsl:use-attribute-sets="monospace.verbatim.properties"
                      linefeed-treatment="preserve">
              <xsl:call-template name="generate-index-markup">
                <xsl:with-param name="scope" select="/"/>
              </xsl:call-template>
            </fo:block>
          </xsl:when>
          <xsl:when test="indexentry|indexdiv/indexentry">
            <xsl:apply-templates/>
          </xsl:when>
          <xsl:otherwise>
            <xsl:call-template name="generate-index">
              <xsl:with-param name="scope" select="/"/>
            </xsl:call-template>
          </xsl:otherwise>
        </xsl:choose>
      </xsl:if>
    </fo:flow>
  </fo:page-sequence>
 </xsl:if>
</xsl:template>

<xsl:template match="index/indexinfo"></xsl:template>
<xsl:template match="index/info"></xsl:template>
<xsl:template match="index/title"></xsl:template>
<xsl:template match="index/subtitle"></xsl:template>
<xsl:template match="index/titleabbrev"></xsl:template>

<!-- ==================================================================== -->

<xsl:template name="indexdiv.title">
  <xsl:param name="title"/>
  <xsl:param name="titlecontent"/>

  <fo:block xsl:use-attribute-sets="index.div.title.properties">
    <xsl:choose>
      <xsl:when test="$title">
        <xsl:apply-templates select="." mode="object.title.markup">
          <xsl:with-param name="allow-anchors" select="1"/>
        </xsl:apply-templates>
      </xsl:when>
      <xsl:otherwise>
        <xsl:copy-of select="$titlecontent"/>
      </xsl:otherwise>
    </xsl:choose>
  </fo:block>
</xsl:template>

<xsl:template match="indexdiv">
  <fo:block>
    <xsl:call-template name="indexdiv.titlepage"/>
    <xsl:apply-templates/>
  </fo:block>
</xsl:template>

<xsl:template match="indexdiv/title"/>
<xsl:template match="indexdiv/subtitle"/>
<xsl:template match="indexdiv/titleabbrev"/>

<!-- ==================================================================== -->

<!-- Text used for distiguishing between normal and significant entries -->
<xsl:variable name="significant.flag">.tnacifingis</xsl:variable>

<xsl:template match="indexterm" name="indexterm">
  <!-- Temporal workaround for bug in AXF -->
  <xsl:variable name="wrapper.name">
    <xsl:choose>
      <xsl:when test="$axf.extensions != 0 or $fop1.extensions != 0">
        <xsl:call-template name="inline.or.block"/>
      </xsl:when>
      <xsl:otherwise>fo:wrapper</xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:element name="{$wrapper.name}">
    <xsl:attribute name="id">
      <xsl:call-template name="object.id"/>
    </xsl:attribute>
    <xsl:choose>
      <xsl:when test="$xep.extensions != 0">
        <xsl:attribute name="rx:key">
          <xsl:value-of select="&primary;"/>
          <xsl:if test="@significance='preferred'"><xsl:value-of select="$significant.flag"/></xsl:if>
          <xsl:if test="secondary">
            <xsl:text>, </xsl:text>
            <xsl:value-of select="&secondary;"/>
          </xsl:if>
          <xsl:if test="tertiary">
            <xsl:text>, </xsl:text>
            <xsl:value-of select="&tertiary;"/>
          </xsl:if>
        </xsl:attribute>
      </xsl:when>
      <xsl:otherwise>
        <xsl:comment>
          <xsl:call-template name="comment-escape-string">
            <xsl:with-param name="string">
              <xsl:value-of select="primary"/>
              <xsl:if test="secondary">
                <xsl:text>, </xsl:text>
                <xsl:value-of select="secondary"/>
              </xsl:if>
              <xsl:if test="tertiary">
                <xsl:text>, </xsl:text>
                <xsl:value-of select="tertiary"/>
              </xsl:if>
            </xsl:with-param>
          </xsl:call-template>
        </xsl:comment>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:element>
</xsl:template>

<xsl:template match="indexterm[@class='startofrange']">
  <xsl:choose>
    <xsl:when test="$xep.extensions != 0">
      <rx:begin-index-range>
        <xsl:call-template name="anchor"/>
        <xsl:attribute name="rx:key">
          <xsl:value-of select="&primary;"/>
          <xsl:if test="@significance='preferred'"><xsl:value-of select="$significant.flag"/></xsl:if>
          <xsl:if test="secondary">
            <xsl:text>, </xsl:text>
            <xsl:value-of select="&secondary;"/>
          </xsl:if>
          <xsl:if test="tertiary">
            <xsl:text>, </xsl:text>
            <xsl:value-of select="&tertiary;"/>
          </xsl:if>
        </xsl:attribute>
      </rx:begin-index-range>
    </xsl:when>
    <xsl:otherwise>
      <xsl:call-template name="indexterm"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="indexterm[@class='endofrange']">
  <xsl:choose>
    <xsl:when test="$xep.extensions != 0">
      <rx:end-index-range>
        <xsl:attribute name="ref-id">
          <xsl:value-of select="@startref"/>
        </xsl:attribute>
      </rx:end-index-range>
    </xsl:when>
    <xsl:otherwise>
      <xsl:call-template name="indexterm"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<!-- ==================================================================== -->

<xsl:template match="indexentry">
  <fo:block>
    <!-- don't process 'seeie's from here -->
    <xsl:apply-templates select="primaryie|secondaryie|tertiaryie|seealsoie"/>
  </fo:block>
</xsl:template>

<xsl:template match="primaryie">
  <fo:block>
    <xsl:apply-templates/>
    <xsl:if test="following-sibling::seeie">
      <xsl:text> (</xsl:text>
      <xsl:call-template name="gentext">
        <xsl:with-param name="key" select="'see'"/>
      </xsl:call-template>
      <xsl:text> </xsl:text>
      <xsl:apply-templates select="following-sibling::seeie"/>
      <xsl:text>)</xsl:text>
    </xsl:if>
  </fo:block>
</xsl:template>

<xsl:template match="secondaryie">
  <fo:block start-indent="1pc">
    <xsl:apply-templates/>
    <xsl:if test="following-sibling::seeie">
      <xsl:text> (</xsl:text>
      <xsl:call-template name="gentext">
        <xsl:with-param name="key" select="'see'"/>
      </xsl:call-template>
      <xsl:text> </xsl:text>
      <xsl:apply-templates select="following-sibling::seeie"/>
      <xsl:text>)</xsl:text>
    </xsl:if>
  </fo:block>
</xsl:template>

<xsl:template match="tertiaryie">
  <fo:block start-indent="2pc">
    <xsl:apply-templates/>
    <xsl:if test="following-sibling::seeie">
      <xsl:text> (</xsl:text>
      <xsl:call-template name="gentext">
        <xsl:with-param name="key" select="'see'"/>
      </xsl:call-template>
      <xsl:text> </xsl:text>
      <xsl:apply-templates select="following-sibling::seeie"/>
      <xsl:text>)</xsl:text>
    </xsl:if>
  </fo:block>
</xsl:template>

<xsl:template match="seeie">
  <fo:inline>
    <xsl:apply-templates/>
  </fo:inline>
</xsl:template>

<xsl:template match="seealsoie">
  <fo:block>
    <xsl:attribute name="start-indent">
      <xsl:choose>
        <xsl:when test="(preceding-sibling::tertiaryie |
                         preceding-sibling::secondaryie)[last()]
                         [self::tertiaryie]">3pc</xsl:when>
        <xsl:when test="(preceding-sibling::tertiaryie |
                         preceding-sibling::secondaryie)[last()]
                         [self::secondaryie]">2pc</xsl:when>
        <xsl:otherwise>1pc</xsl:otherwise>
      </xsl:choose>
    </xsl:attribute>
    <xsl:text>(</xsl:text>
    <xsl:call-template name="gentext">
      <xsl:with-param name="key" select="'seealso'"/>
    </xsl:call-template>
    <xsl:text> </xsl:text>
    <xsl:apply-templates/>
    <xsl:text>)</xsl:text>
  </fo:block>
</xsl:template>

<!-- Determines if an object should be inserted as an fo:inline
     or an fo:block.  Used for indexterms -->
<xsl:template name="inline.or.block">
  <xsl:param name="parentnode" select=".."/>

  <xsl:variable name="parent" select="concat('|', local-name($parentnode), '|')"/>

  <xsl:variable name="block.parents" select="'|answer|appendix|appendixinfo|article|articleinfo|bibliodiv|bibliography|bibliographyinfo|blockinfo|blockquote|bookinfo|callout|caution|chapter|chapterinfo|dedication|example|figure|formalpara|funcsynopsisinfo|glossary|glossaryinfo|glossdef|glossdiv|glossentry|highlights|important|index|indexinfo|info|informalexample|informalfigure|informaltable|itemizedlist|legalnotice|listitem|msgexplan|msgtext|note|objectinfo|orderedlist|partinfo|partintro|preface|prefaceinfo|procedure|qandadiv|qandaset|question|refentry|refentryinfo|referenceinfo|refmeta|refmiscinfo|refsect1|refsect1info|refsect2|refsect2info|refsect3|refsect3info|refsection|refsectioninfo|refsynopsisdiv|refsynopsisdivinfo|revdescription|screeninfo|sect1|sect1info|sect2|sect2info|sect3|sect3info|sect4|sect4info|sect5|sect5info|section|sectioninfo|setindex|setindexinfo|setinfo|sidebar|sidebarinfo|simplesect|step|table|task|taskprerequisites|taskrelated|tasksummary|tip|topic|variablelist|warning|'"/>

  <xsl:choose>
    <xsl:when test="contains($block.parents, $parent)">fo:block</xsl:when>
    <xsl:when test="$fop1.extensions != 0">fo:wrapper</xsl:when>
    <xsl:otherwise>fo:inline</xsl:otherwise>
  </xsl:choose>
</xsl:template>

</xsl:stylesheet>
