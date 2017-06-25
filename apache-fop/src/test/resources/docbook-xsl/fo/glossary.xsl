<?xml version='1.0'?>
<!DOCTYPE xsl:stylesheet [
<!ENTITY % common.entities SYSTEM "../common/entities.ent">
%common.entities;
]>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fo="http://www.w3.org/1999/XSL/Format"
                xmlns:xlink='http://www.w3.org/1999/xlink'
                exclude-result-prefixes="xlink"
                version='1.0'>

<!-- ********************************************************************
     $Id: glossary.xsl 8591 2010-02-27 20:42:27Z bobstayton $
     ********************************************************************

     This file is part of the XSL DocBook Stylesheet distribution.
     See ../README or http://docbook.sf.net/release/xsl/current/ for
     copyright and other information.

     ******************************************************************** -->

<!-- ==================================================================== -->

<xsl:template match="glossary">
  <xsl:call-template name="make-glossary"/>
</xsl:template>

<xsl:template match="glossdiv/title"/>
<xsl:template match="glossdiv/subtitle"/>
<xsl:template match="glossdiv/titleabbrev"/>

<!-- ==================================================================== -->

<xsl:template name="make-glossary">
  <xsl:param name="divs" select="glossdiv"/>
  <xsl:param name="entries" select="glossentry"/>
  <xsl:param name="preamble" select="*[not(self::title
                                           or self::subtitle
                                           or self::glossdiv
                                           or self::glossentry)]"/>

  &setup-language-variable;

  <xsl:variable name="id">
    <xsl:call-template name="object.id"/>
  </xsl:variable>

  <xsl:variable name="presentation">
    <xsl:call-template name="pi.dbfo_glossary-presentation"/>
  </xsl:variable>

  <xsl:variable name="term-width">
    <xsl:call-template name="pi.dbfo_glossterm-width"/>
  </xsl:variable>

  <xsl:variable name="width">
    <xsl:choose>
      <xsl:when test="$term-width = ''">
        <xsl:value-of select="$glossterm.width"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:value-of select="$term-width"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <fo:block id="{$id}">
    <xsl:call-template name="glossary.titlepage"/>
  </fo:block>

  <xsl:if test="$preamble">
    <xsl:apply-templates select="$preamble"/>
  </xsl:if>

  <xsl:choose>
    <xsl:when test="$presentation = 'list'">
      <xsl:apply-templates select="$divs" mode="glossary.as.list">
        <xsl:with-param name="width" select="$width"/>
      </xsl:apply-templates>
      <xsl:if test="$entries">
        <fo:list-block provisional-distance-between-starts="{$width}"
                       provisional-label-separation="{$glossterm.separation}"
                       xsl:use-attribute-sets="normal.para.spacing">
          <xsl:choose>
            <xsl:when test="$glossary.sort != 0">
              <xsl:apply-templates select="$entries" mode="glossary.as.list">
                                  <xsl:sort lang="{$language}" select="normalize-space(translate(concat(@sortas, glossterm[not(parent::glossentry/@sortas) or parent::glossentry/@sortas = '']), &lowercase;, &uppercase;))"/>
              </xsl:apply-templates>
            </xsl:when>
            <xsl:otherwise>
              <xsl:apply-templates select="$entries" mode="glossary.as.list"/>
            </xsl:otherwise>
          </xsl:choose>
        </fo:list-block>
      </xsl:if>
    </xsl:when>
    <xsl:when test="$presentation = 'blocks'">
      <xsl:apply-templates select="$divs" mode="glossary.as.blocks"/>
      <xsl:choose>
        <xsl:when test="$glossary.sort != 0">
          <xsl:apply-templates select="$entries" mode="glossary.as.blocks">
                          <xsl:sort lang="{$language}" select="normalize-space(translate(concat(@sortas, glossterm[not(parent::glossentry/@sortas) or parent::glossentry/@sortas = '']), &lowercase;, &uppercase;))"/>
          </xsl:apply-templates>
        </xsl:when>
        <xsl:otherwise>
          <xsl:apply-templates select="$entries" mode="glossary.as.blocks"/>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:when>
    <xsl:when test="$glossary.as.blocks != 0">
      <xsl:apply-templates select="$divs" mode="glossary.as.blocks"/>
      <xsl:choose>
        <xsl:when test="$glossary.sort != 0">
          <xsl:apply-templates select="$entries" mode="glossary.as.blocks">
                                <xsl:sort lang="{$language}" select="normalize-space(translate(concat(@sortas, glossterm[not(parent::glossentry/@sortas) or parent::glossentry/@sortas = '']), &lowercase;, &uppercase;))"/>
          </xsl:apply-templates>
        </xsl:when>
        <xsl:otherwise>
          <xsl:apply-templates select="$entries" mode="glossary.as.blocks"/>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:when>
    <xsl:otherwise>
      <xsl:apply-templates select="$divs" mode="glossary.as.list">
        <xsl:with-param name="width" select="$width"/>
      </xsl:apply-templates>
      <xsl:if test="$entries">
        <fo:list-block provisional-distance-between-starts="{$width}"
                       provisional-label-separation="{$glossterm.separation}"
                       xsl:use-attribute-sets="normal.para.spacing">
          <xsl:choose>
            <xsl:when test="$glossary.sort != 0">
              <xsl:apply-templates select="$entries" mode="glossary.as.list">
                                        <xsl:sort lang="{$language}" select="normalize-space(translate(concat(@sortas, glossterm[not(parent::glossentry/@sortas) or parent::glossentry/@sortas = '']), &lowercase;, &uppercase;))"/>
              </xsl:apply-templates>
            </xsl:when>
            <xsl:otherwise>
              <xsl:apply-templates select="$entries" mode="glossary.as.list"/>
            </xsl:otherwise>
          </xsl:choose>
        </fo:list-block>
      </xsl:if>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="book/glossary|part/glossary|/glossary" priority="2">
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

      <xsl:call-template name="make-glossary"/>
    </fo:flow>
  </fo:page-sequence>
</xsl:template>

<xsl:template match="glossary/glossaryinfo"></xsl:template>
<xsl:template match="glossary/info"></xsl:template>
<xsl:template match="glossary/title"></xsl:template>
<xsl:template match="glossary/subtitle"></xsl:template>
<xsl:template match="glossary/titleabbrev"></xsl:template>

<!-- ==================================================================== -->

<xsl:template match="glosslist">
  &setup-language-variable;

  <xsl:variable name="presentation">
    <xsl:call-template name="pi.dbfo_glosslist-presentation"/>
  </xsl:variable>

  <xsl:variable name="term-width">
    <xsl:call-template name="pi.dbfo_glossterm-width"/>
  </xsl:variable>

  <xsl:variable name="width">
    <xsl:choose>
      <xsl:when test="$term-width = ''">
        <xsl:value-of select="$glossterm.width"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:value-of select="$term-width"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:if test="title or info/title">
    <xsl:apply-templates select="(title|info/title)[1]" mode="list.title.mode"/>
  </xsl:if>

  <xsl:choose>
    <xsl:when test="$presentation = 'list'">
      <fo:list-block provisional-distance-between-starts="{$width}"
                     provisional-label-separation="{$glossterm.separation}"
                     xsl:use-attribute-sets="normal.para.spacing">
        <xsl:choose>
          <xsl:when test="$glossary.sort != 0">
            <xsl:apply-templates select="glossentry" mode="glossary.as.list">
                                <xsl:sort lang="{$language}" select="normalize-space(translate(concat(@sortas, glossterm[not(parent::glossentry/@sortas) or parent::glossentry/@sortas = '']), &lowercase;, &uppercase;))"/>
            </xsl:apply-templates>
          </xsl:when>
          <xsl:otherwise>
            <xsl:apply-templates select="glossentry" mode="glossary.as.list"/>
          </xsl:otherwise>
        </xsl:choose>
      </fo:list-block>
    </xsl:when>
    <xsl:when test="$presentation = 'blocks'">
      <xsl:choose>
        <xsl:when test="$glossary.sort != 0">
          <xsl:apply-templates select="glossentry" mode="glossary.as.blocks">
                                <xsl:sort lang="{$language}" select="normalize-space(translate(concat(@sortas, glossterm[not(parent::glossentry/@sortas) or parent::glossentry/@sortas = '']), &lowercase;, &uppercase;))"/>
          </xsl:apply-templates>
        </xsl:when>
        <xsl:otherwise>
          <xsl:apply-templates select="glossentry" mode="glossary.as.blocks"/>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:when>
    <xsl:when test="$glosslist.as.blocks != 0">
      <xsl:choose>
        <xsl:when test="$glossary.sort != 0">
          <xsl:apply-templates select="glossentry" mode="glossary.as.blocks">
                                <xsl:sort lang="{$language}" select="normalize-space(translate(concat(@sortas, glossterm[not(parent::glossentry/@sortas) or parent::glossentry/@sortas = '']), &lowercase;, &uppercase;))"/>
          </xsl:apply-templates>
        </xsl:when>
        <xsl:otherwise>
          <xsl:apply-templates select="glossentry" mode="glossary.as.blocks"/>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:when>
    <xsl:otherwise>
      <fo:list-block provisional-distance-between-starts="{$width}"
                     provisional-label-separation="{$glossterm.separation}"
                     xsl:use-attribute-sets="normal.para.spacing">
        <xsl:choose>
          <xsl:when test="$glossary.sort != 0">
            <xsl:apply-templates select="glossentry" mode="glossary.as.list">
                                <xsl:sort lang="{$language}" select="normalize-space(translate(concat(@sortas, glossterm[not(parent::glossentry/@sortas) or parent::glossentry/@sortas = '']), &lowercase;, &uppercase;))"/>
            </xsl:apply-templates>
          </xsl:when>
          <xsl:otherwise>
            <xsl:apply-templates select="glossentry" mode="glossary.as.list"/>
          </xsl:otherwise>
        </xsl:choose>
      </fo:list-block>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<!-- ==================================================================== -->
<!-- Glossary collection -->

<xsl:template match="glossary[@role='auto']" priority="2">
  <xsl:variable name="collection" select="document($glossary.collection, .)"/>
  <xsl:if test="$glossary.collection = ''">
    <xsl:message>
      <xsl:text>Warning: processing automatic glossary </xsl:text>
      <xsl:text>without a glossary.collection file.</xsl:text>
    </xsl:message>
  </xsl:if>

  <xsl:if test="not($collection) and $glossary.collection != ''">
    <xsl:message>
      <xsl:text>Warning: processing automatic glossary but unable to </xsl:text>
      <xsl:text>open glossary.collection file '</xsl:text>
      <xsl:value-of select="$glossary.collection"/>
      <xsl:text>'</xsl:text>
    </xsl:message>
  </xsl:if>

  <xsl:call-template name="make-auto-glossary"/>
</xsl:template>

<xsl:template name="make-auto-glossary">
  <xsl:param name="collection" select="document($glossary.collection, .)"/>
  <xsl:param name="terms" select="//glossterm[not(parent::glossdef)]|//firstterm"/>
  <xsl:param name="preamble" select="*[not(self::title
                                           or self::subtitle
                                           or self::glossdiv
                                           or self::glossentry)]"/>

  &setup-language-variable;

  <xsl:variable name="id">
    <xsl:call-template name="object.id"/>
  </xsl:variable>

  <xsl:variable name="presentation">
    <xsl:call-template name="pi.dbfo_glossary-presentation"/>
  </xsl:variable>

  <xsl:variable name="term-width">
    <xsl:call-template name="pi.dbfo_glossterm-width"/>
  </xsl:variable>

  <xsl:variable name="width">
    <xsl:choose>
      <xsl:when test="$term-width = ''">
        <xsl:value-of select="$glossterm.width"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:value-of select="$term-width"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:if test="$glossary.collection = ''">
    <xsl:message>
      <xsl:text>Warning: processing automatic glossary </xsl:text>
      <xsl:text>without a glossary.collection file.</xsl:text>
    </xsl:message>
  </xsl:if>

  <fo:block id="{$id}">
    <xsl:call-template name="glossary.titlepage"/>
  </fo:block>

  <xsl:if test="$preamble">
    <xsl:apply-templates select="$preamble"/>
  </xsl:if>

  <xsl:choose>
    <xsl:when test="glossdiv and $collection//glossdiv">
      <xsl:for-each select="$collection//glossdiv">
        <!-- first see if there are any in this div -->
        <xsl:variable name="exist.test">
          <xsl:for-each select="glossentry">
            <xsl:variable name="cterm" select="glossterm"/>
            <xsl:if test="$terms[@baseform = $cterm or . = $cterm]">
              <xsl:value-of select="glossterm"/>
            </xsl:if>
          </xsl:for-each>
        </xsl:variable>

        <xsl:if test="$exist.test != ''">
          <xsl:choose>
            <xsl:when test="$presentation = 'list'">
              <xsl:apply-templates select="." mode="auto-glossary-as-list">
                <xsl:with-param name="width" select="$width"/>
                <xsl:with-param name="terms" select="$terms"/>
              </xsl:apply-templates>
            </xsl:when>
            <xsl:when test="$presentation = 'blocks'">
              <xsl:apply-templates select="." mode="auto-glossary-as-blocks">
                <xsl:with-param name="terms" select="$terms"/>
              </xsl:apply-templates>
            </xsl:when>
            <xsl:when test="$glossary.as.blocks != 0">
              <xsl:apply-templates select="." mode="auto-glossary-as-blocks">
                <xsl:with-param name="terms" select="$terms"/>
              </xsl:apply-templates>
            </xsl:when>
            <xsl:otherwise>
              <xsl:apply-templates select="." mode="auto-glossary-as-list">
                <xsl:with-param name="width" select="$width"/>
                <xsl:with-param name="terms" select="$terms"/>
              </xsl:apply-templates>
            </xsl:otherwise>
          </xsl:choose>
        </xsl:if>
      </xsl:for-each>
    </xsl:when>
    <xsl:otherwise>
      <xsl:choose>
        <xsl:when test="$presentation = 'list'">
          <fo:list-block provisional-distance-between-starts="{$width}"
                         provisional-label-separation="{$glossterm.separation}"
                         xsl:use-attribute-sets="normal.para.spacing">
            <xsl:choose>
              <xsl:when test="$glossary.sort != 0">
                <xsl:for-each select="$collection//glossentry">
                                <xsl:sort lang="{$language}" select="normalize-space(translate(concat(@sortas, glossterm[not(parent::glossentry/@sortas) or parent::glossentry/@sortas = '']), &lowercase;, &uppercase;))"/>
                  <xsl:variable name="cterm" select="glossterm"/>
                  <xsl:if test="$terms[@baseform = $cterm or . = $cterm]">
                    <xsl:apply-templates select="." 
                                         mode="auto-glossary-as-list"/>
                  </xsl:if>
                </xsl:for-each>
              </xsl:when>
              <xsl:otherwise>
                <xsl:for-each select="$collection//glossentry">
                  <xsl:variable name="cterm" select="glossterm"/>
                  <xsl:if test="$terms[@baseform = $cterm or . = $cterm]">
                    <xsl:apply-templates select="." 
                                         mode="auto-glossary-as-list"/>
                  </xsl:if>
                </xsl:for-each>
              </xsl:otherwise>
            </xsl:choose>
          </fo:list-block>
        </xsl:when>
        <xsl:when test="$presentation = 'blocks' or
                        $glossary.as.blocks != 0">
          <xsl:choose>
            <xsl:when test="$glossary.sort != 0">
              <xsl:for-each select="$collection//glossentry">
                                        <xsl:sort lang="{$language}" select="normalize-space(translate(concat(@sortas, glossterm[not(parent::glossentry/@sortas) or parent::glossentry/@sortas = '']), &lowercase;, &uppercase;))"/>
                <xsl:variable name="cterm" select="glossterm"/>
                <xsl:if test="$terms[@baseform = $cterm or . = $cterm]">
                  <xsl:apply-templates select="." 
                                       mode="auto-glossary-as-blocks"/>
                </xsl:if>
              </xsl:for-each>
            </xsl:when>
            <xsl:otherwise>
              <xsl:for-each select="$collection//glossentry">
                <xsl:variable name="cterm" select="glossterm"/>
                <xsl:if test="$terms[@baseform = $cterm or . = $cterm]">
                  <xsl:apply-templates select="." 
                                       mode="auto-glossary-as-blocks"/>
                </xsl:if>
              </xsl:for-each>
            </xsl:otherwise>
          </xsl:choose>
        </xsl:when>
        <xsl:otherwise>
          <fo:list-block provisional-distance-between-starts="{$width}"
                         provisional-label-separation="{$glossterm.separation}"
                         xsl:use-attribute-sets="normal.para.spacing">
            <xsl:choose>
              <xsl:when test="$glossary.sort != 0">
                <xsl:for-each select="$collection//glossentry">

                                <xsl:sort lang="{$language}" select="normalize-space(translate(concat(@sortas, glossterm[not(parent::glossentry/@sortas) or parent::glossentry/@sortas = '']), &lowercase;, &uppercase;))"/>
                  <xsl:variable name="cterm" select="glossterm"/>
                  <xsl:if test="$terms[@baseform = $cterm or . = $cterm]">
                    <xsl:apply-templates select="." 
                                         mode="auto-glossary-as-list"/>
                  </xsl:if>
                </xsl:for-each>
              </xsl:when>
              <xsl:otherwise>
                <xsl:for-each select="$collection//glossentry">
                  <xsl:variable name="cterm" select="glossterm"/>
                  <xsl:if test="$terms[@baseform = $cterm or . = $cterm]">
                    <xsl:apply-templates select="." 
                                         mode="auto-glossary-as-list"/>
                  </xsl:if>
                </xsl:for-each>
              </xsl:otherwise>
            </xsl:choose>
          </fo:list-block>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="book/glossary[@role='auto']|
                     part/glossary[@role='auto']|
                     /glossary[@role='auto']" priority="2.5">
  <xsl:variable name="id"><xsl:call-template name="object.id"/></xsl:variable>

  <xsl:variable name="master-reference">
    <xsl:call-template name="select.pagemaster"/>
  </xsl:variable>

  <xsl:if test="$glossary.collection = ''">
    <xsl:message>
      <xsl:text>Warning: processing automatic glossary </xsl:text>
      <xsl:text>without a glossary.collection file.</xsl:text>
    </xsl:message>
  </xsl:if>

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

      <xsl:call-template name="make-auto-glossary"/>
    </fo:flow>
  </fo:page-sequence>
</xsl:template>

<xsl:template match="glossdiv" mode="auto-glossary-as-list">
  <xsl:param name="width" select="$glossterm.width"/>
  <xsl:param name="terms" select="."/>

  &setup-language-variable;

  <xsl:variable name="id">
    <xsl:call-template name="object.id"/>
  </xsl:variable>

  <xsl:variable name="preamble"
                select="*[not(self::title
                            or self::subtitle
                            or self::glossentry)]"/>

  <fo:block id="{$id}">
    <xsl:call-template name="glossdiv.titlepage"/>
  </fo:block>

  <xsl:apply-templates select="$preamble"/>

  <fo:list-block provisional-distance-between-starts="{$width}"
                 provisional-label-separation="{$glossterm.separation}"
                 xsl:use-attribute-sets="normal.para.spacing">
    <xsl:choose>
      <xsl:when test="$glossary.sort != 0">
        <xsl:for-each select="glossentry">
                                <xsl:sort lang="{$language}" select="normalize-space(translate(concat(@sortas, glossterm[not(parent::glossentry/@sortas) or parent::glossentry/@sortas = '']), &lowercase;, &uppercase;))"/>
          <xsl:variable name="cterm" select="glossterm"/>
          <xsl:if test="$terms[@baseform = $cterm or . = $cterm]">
            <xsl:apply-templates select="." mode="auto-glossary-as-list"/>
          </xsl:if>
        </xsl:for-each>
      </xsl:when>
      <xsl:otherwise>
        <xsl:for-each select="glossentry">
          <xsl:variable name="cterm" select="glossterm"/>
          <xsl:if test="$terms[@baseform = $cterm or . = $cterm]">
            <xsl:apply-templates select="." mode="auto-glossary-as-list"/>
          </xsl:if>
        </xsl:for-each>
      </xsl:otherwise>
    </xsl:choose>
  </fo:list-block>
</xsl:template>

<xsl:template match="glossentry" mode="auto-glossary-as-list">
  <xsl:apply-templates select="." mode="glossary.as.list"/>
</xsl:template>

<xsl:template match="glossdiv" mode="auto-glossary-as-blocks">
  <xsl:param name="terms" select="."/>

  &setup-language-variable;

  <xsl:variable name="id">
    <xsl:call-template name="object.id"/>
  </xsl:variable>

  <xsl:variable name="preamble"
                select="*[not(self::title
                            or self::subtitle
                            or self::glossentry)]"/>

  <fo:block id="{$id}">
    <xsl:call-template name="glossdiv.titlepage"/>
  </fo:block>

  <xsl:apply-templates select="$preamble"/>

  <xsl:choose>
    <xsl:when test="$glossary.sort != 0">
      <xsl:for-each select="glossentry">
                                <xsl:sort lang="{$language}" select="normalize-space(translate(concat(@sortas, glossterm[not(parent::glossentry/@sortas) or parent::glossentry/@sortas = '']), &lowercase;, &uppercase;))"/>
        <xsl:variable name="cterm" select="glossterm"/>
        <xsl:if test="$terms[@baseform = $cterm or . = $cterm]">
          <xsl:apply-templates select="." mode="auto-glossary-as-blocks"/>
        </xsl:if>
      </xsl:for-each>
    </xsl:when>
    <xsl:otherwise>
      <xsl:for-each select="glossentry">
        <xsl:variable name="cterm" select="glossterm"/>
        <xsl:if test="$terms[@baseform = $cterm or . = $cterm]">
          <xsl:apply-templates select="." mode="auto-glossary-as-blocks"/>
        </xsl:if>
      </xsl:for-each>
    </xsl:otherwise>
  </xsl:choose>

</xsl:template>

<xsl:template match="glossentry" mode="auto-glossary-as-blocks">
  <xsl:apply-templates select="." mode="glossary.as.blocks"/>
</xsl:template>

<!-- ==================================================================== -->
<!-- Format glossary as a list -->

<xsl:template match="glossdiv" mode="glossary.as.list">
  <xsl:param name="width" select="$glossterm.width"/>

  &setup-language-variable;

  <xsl:variable name="id">
    <xsl:call-template name="object.id"/>
  </xsl:variable>

  <xsl:variable name="entries" select="glossentry"/>

  <xsl:variable name="preamble"
                select="*[not(self::title
                            or self::subtitle
                            or self::glossentry)]"/>

  <fo:block id="{$id}">
    <xsl:call-template name="glossdiv.titlepage"/>
  </fo:block>

  <xsl:apply-templates select="$preamble"/>

  <fo:list-block provisional-distance-between-starts="{$width}"
                 provisional-label-separation="{$glossterm.separation}"
                 xsl:use-attribute-sets="normal.para.spacing">
    <xsl:choose>
      <xsl:when test="$glossary.sort != 0">
        <xsl:apply-templates select="$entries" mode="glossary.as.list">
                                <xsl:sort lang="{$language}" select="normalize-space(translate(concat(@sortas, glossterm[not(parent::glossentry/@sortas) or parent::glossentry/@sortas = '']), &lowercase;, &uppercase;))"/>
        </xsl:apply-templates>
      </xsl:when>
      <xsl:otherwise>
        <xsl:apply-templates select="$entries" mode="glossary.as.list"/>
      </xsl:otherwise>
    </xsl:choose>
  </fo:list-block>
</xsl:template>

<!--
GlossEntry ::=
  GlossTerm, Acronym?, Abbrev?,
  (IndexTerm)*,
  RevHistory?,
  (GlossSee | GlossDef+)
-->

<xsl:template match="glossentry" mode="glossary.as.list">
  <xsl:variable name="id">
    <xsl:call-template name="object.id"/>
  </xsl:variable>

  <fo:list-item xsl:use-attribute-sets="glossentry.list.item.properties">
    <xsl:call-template name="anchor">
      <xsl:with-param name="conditional">
        <xsl:choose>
          <xsl:when test="$glossterm.auto.link != 0
                          or $glossary.collection != ''">0</xsl:when>
          <xsl:otherwise>1</xsl:otherwise>
        </xsl:choose>
      </xsl:with-param>
    </xsl:call-template>

    <fo:list-item-label end-indent="label-end()">
      <fo:block xsl:use-attribute-sets="glossterm.list.properties">
        <xsl:choose>
          <xsl:when test="$glossentry.show.acronym = 'primary'">
            <xsl:choose>
              <xsl:when test="acronym|abbrev">
                <xsl:apply-templates select="acronym|abbrev" 
                                     mode="glossary.as.list"/>
                <xsl:text> (</xsl:text>
                <xsl:apply-templates select="glossterm" 
                                     mode="glossary.as.list"/>
                <xsl:text>)</xsl:text>
              </xsl:when>
              <xsl:otherwise>
                <xsl:apply-templates select="glossterm" 
                                     mode="glossary.as.list"/>
              </xsl:otherwise>
            </xsl:choose>
          </xsl:when>

          <xsl:when test="$glossentry.show.acronym = 'yes'">
            <xsl:apply-templates select="glossterm" mode="glossary.as.list"/>

            <xsl:if test="acronym|abbrev">
              <xsl:text> (</xsl:text>
              <xsl:apply-templates select="acronym|abbrev" 
                                   mode="glossary.as.list"/>
              <xsl:text>)</xsl:text>
            </xsl:if>
          </xsl:when>

          <xsl:otherwise>
            <xsl:apply-templates select="glossterm" mode="glossary.as.list"/>
          </xsl:otherwise>
        </xsl:choose>
        <xsl:apply-templates select="indexterm"/>
      </fo:block>
    </fo:list-item-label>

    <fo:list-item-body start-indent="body-start()">
      <fo:block xsl:use-attribute-sets="glossdef.list.properties">
        <xsl:apply-templates select="glosssee|glossdef" mode="glossary.as.list"/>
      </fo:block>
    </fo:list-item-body>
  </fo:list-item>
</xsl:template>

<xsl:template match="glossentry/glossterm" mode="glossary.as.list">
  <xsl:variable name="id">
    <xsl:call-template name="object.id"/>
  </xsl:variable>
  <fo:inline id="{$id}">
    <xsl:apply-templates/>
  </fo:inline>
  <xsl:if test="following-sibling::glossterm">, </xsl:if>
</xsl:template>

<xsl:template match="glossentry/acronym" mode="glossary.as.list">
  <xsl:apply-templates/>
  <xsl:if test="following-sibling::acronym|following-sibling::abbrev">, </xsl:if>
</xsl:template>

<xsl:template match="glossentry/abbrev" mode="glossary.as.list">
  <xsl:apply-templates/>
  <xsl:if test="following-sibling::acronym|following-sibling::abbrev">, </xsl:if>
</xsl:template>

<xsl:template match="glossentry/revhistory" mode="glossary.as.list">
</xsl:template>

<xsl:template match="glossentry/glosssee" mode="glossary.as.list">
  <xsl:variable name="otherterm" select="@otherterm"/>
  <xsl:variable name="targets" select="key('id', $otherterm)"/>
  <xsl:variable name="target" select="$targets[1]"/>
  <xsl:variable name="xlink" select="@xlink:href"/>

  <fo:block>
    <xsl:variable name="template">
      <xsl:call-template name="gentext.template">
        <xsl:with-param name="context" select="'glossary'"/>
        <xsl:with-param name="name" select="'see'"/>
      </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="title">
      <xsl:choose>
        <xsl:when test="$target">
          <fo:basic-link internal-destination="{$otherterm}"
                         xsl:use-attribute-sets="xref.properties">
            <xsl:apply-templates select="$target" mode="xref-to"/>
          </fo:basic-link>
        </xsl:when>
        <xsl:when test="$xlink">
          <xsl:call-template name="simple.xlink">
            <xsl:with-param name="content">
              <xsl:apply-templates/>
            </xsl:with-param>
          </xsl:call-template>
        </xsl:when>
        <xsl:when test="$otherterm != '' and not($target)">
          <xsl:message>
            <xsl:text>Warning: glosssee @otherterm reference not found: </xsl:text>
            <xsl:value-of select="$otherterm"/>
          </xsl:message>
          <xsl:apply-templates mode="glossary.as.list"/>
        </xsl:when>
        <xsl:otherwise>
          <xsl:apply-templates mode="glossary.as.list"/>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:call-template name="substitute-markup">
      <xsl:with-param name="template" select="$template"/>
      <xsl:with-param name="title" select="$title"/>
    </xsl:call-template>
  </fo:block>
</xsl:template>

<xsl:template match="glossentry/glossdef" mode="glossary.as.list">
  <xsl:apply-templates select="*[local-name(.) != 'glossseealso']"/>
  <xsl:if test="glossseealso">
    <fo:block>
      <xsl:variable name="template">
        <xsl:call-template name="gentext.template">
          <xsl:with-param name="context" select="'glossary'"/>
          <xsl:with-param name="name" select="'seealso'"/>
        </xsl:call-template>
      </xsl:variable>
      <xsl:variable name="title">
        <xsl:apply-templates select="glossseealso" mode="glossary.as.list"/>
      </xsl:variable>
      <xsl:call-template name="substitute-markup">
        <xsl:with-param name="template" select="$template"/>
        <xsl:with-param name="title" select="$title"/>
      </xsl:call-template>
    </fo:block>
  </xsl:if>
</xsl:template>

<xsl:template match="glossentry/glossdef/para[1]|glossentry/glossdef/simpara[1]"
              mode="glossary.as.list">
  <fo:block>
    <xsl:apply-templates/>
  </fo:block>
</xsl:template>

<xsl:template match="glossseealso" mode="glossary.as.list">
  <xsl:variable name="otherterm" select="@otherterm"/>
  <xsl:variable name="targets" select="key('id', $otherterm)"/>
  <xsl:variable name="target" select="$targets[1]"/>
  <xsl:variable name="xlink" select="@xlink:href"/>

  <xsl:choose>
    <xsl:when test="$target">
      <fo:basic-link internal-destination="{$otherterm}"
                     xsl:use-attribute-sets="xref.properties">
        <xsl:apply-templates select="$target" mode="xref-to"/>
      </fo:basic-link>
    </xsl:when>
    <xsl:when test="$xlink">
      <xsl:call-template name="simple.xlink">
        <xsl:with-param name="content">
          <xsl:apply-templates/>
        </xsl:with-param>
      </xsl:call-template>
    </xsl:when>
    <xsl:when test="$otherterm != '' and not($target)">
      <xsl:message>
        <xsl:text>Warning: glossseealso @otherterm reference not found: </xsl:text>
        <xsl:value-of select="$otherterm"/>
      </xsl:message>
      <xsl:apply-templates mode="glossary.as.list"/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:apply-templates mode="glossary.as.list"/>
    </xsl:otherwise>
  </xsl:choose>

  <xsl:choose>
    <xsl:when test="position() = last()"/>
    <xsl:otherwise>
		<xsl:call-template name="gentext.template">
		  <xsl:with-param name="context" select="'glossary'"/>
		  <xsl:with-param name="name" select="'seealso-separator'"/>
		</xsl:call-template>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<!-- ==================================================================== -->
<!-- Format glossary blocks -->

<xsl:template match="glossdiv" mode="glossary.as.blocks">
  &setup-language-variable;

  <xsl:variable name="id">
    <xsl:call-template name="object.id"/>
  </xsl:variable>

  <xsl:variable name="entries" select="glossentry"/>
  <xsl:variable name="preamble"
                select="*[not(self::title
                            or self::subtitle
                            or self::glossentry)]"/>

  <fo:block id="{$id}">
    <xsl:call-template name="glossdiv.titlepage"/>
  </fo:block>

  <xsl:apply-templates select="$preamble"/>

  <xsl:choose>
    <xsl:when test="$glossary.sort != 0">
      <xsl:apply-templates select="$entries" mode="glossary.as.blocks">
                  <xsl:sort lang="{$language}" select="normalize-space(translate(concat(@sortas, glossterm[not(parent::glossentry/@sortas) or parent::glossentry/@sortas = '']), &lowercase;, &uppercase;))"/>
      </xsl:apply-templates>
    </xsl:when>
    <xsl:otherwise>
      <xsl:apply-templates select="$entries" mode="glossary.as.blocks"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<!--
GlossEntry ::=
  GlossTerm, Acronym?, Abbrev?,
  (IndexTerm)*,
  RevHistory?,
  (GlossSee | GlossDef+)
-->

<xsl:template match="glossentry" mode="glossary.as.blocks">
  <xsl:variable name="id">
    <xsl:call-template name="object.id"/>
  </xsl:variable>

  <fo:block xsl:use-attribute-sets="glossterm.block.properties">
    <xsl:call-template name="anchor">
      <xsl:with-param name="conditional">
        <xsl:choose>
          <xsl:when test="$glossterm.auto.link != 0
                          or $glossary.collection != ''">0</xsl:when>
          <xsl:otherwise>1</xsl:otherwise>
        </xsl:choose>
      </xsl:with-param>
    </xsl:call-template>

    <xsl:choose>
      <xsl:when test="$glossentry.show.acronym = 'primary'">
        <xsl:choose>
          <xsl:when test="acronym|abbrev">
            <xsl:apply-templates select="acronym|abbrev" mode="glossary.as.blocks"/>
            <xsl:text> (</xsl:text>
            <xsl:apply-templates select="glossterm" mode="glossary.as.blocks"/>
            <xsl:text>)</xsl:text>
          </xsl:when>
          <xsl:otherwise>
            <xsl:apply-templates select="glossterm" mode="glossary.as.blocks"/>
          </xsl:otherwise>
        </xsl:choose>
      </xsl:when>

      <xsl:when test="$glossentry.show.acronym = 'yes'">
        <xsl:apply-templates select="glossterm" mode="glossary.as.blocks"/>

        <xsl:if test="acronym|abbrev">
          <xsl:text> (</xsl:text>
          <xsl:apply-templates select="acronym|abbrev" mode="glossary.as.blocks"/>
          <xsl:text>)</xsl:text>
        </xsl:if>
      </xsl:when>

      <xsl:otherwise>
        <xsl:apply-templates select="glossterm" mode="glossary.as.blocks"/>
      </xsl:otherwise>
    </xsl:choose>

    <xsl:apply-templates select="indexterm"/>
  </fo:block>

  <fo:block xsl:use-attribute-sets="glossdef.block.properties">
    <xsl:apply-templates select="glosssee|glossdef" mode="glossary.as.blocks"/>
  </fo:block>
</xsl:template>

<xsl:template match="glossentry/glossterm" mode="glossary.as.blocks">
  <xsl:variable name="id">
    <xsl:call-template name="object.id"/>
  </xsl:variable>
  <fo:inline id="{$id}">
    <xsl:apply-templates/>
  </fo:inline>
  <xsl:if test="following-sibling::glossterm">, </xsl:if>
</xsl:template>

<xsl:template match="glossentry/acronym" mode="glossary.as.blocks">
  <xsl:apply-templates/>
  <xsl:if test="following-sibling::acronym|following-sibling::abbrev">, </xsl:if>
</xsl:template>

<xsl:template match="glossentry/abbrev" mode="glossary.as.blocks">
  <xsl:apply-templates/>
  <xsl:if test="following-sibling::acronym|following-sibling::abbrev">, </xsl:if>
</xsl:template>

<xsl:template match="glossentry/glosssee" mode="glossary.as.blocks">
  <xsl:variable name="otherterm" select="@otherterm"/>
  <xsl:variable name="targets" select="key('id', $otherterm)"/>
  <xsl:variable name="target" select="$targets[1]"/>
  <xsl:variable name="xlink" select="@xlink:href"/>

  <xsl:variable name="template">
    <xsl:call-template name="gentext.template">
      <xsl:with-param name="context" select="'glossary'"/>
      <xsl:with-param name="name" select="'see'"/>
    </xsl:call-template>
  </xsl:variable>
  <xsl:variable name="title">
    <xsl:choose>
      <xsl:when test="$target">
        <fo:basic-link internal-destination="{$otherterm}"
                       xsl:use-attribute-sets="xref.properties">
          <xsl:apply-templates select="$target" mode="xref-to"/>
        </fo:basic-link>
      </xsl:when>
      <xsl:when test="$xlink">
        <xsl:call-template name="simple.xlink">
          <xsl:with-param name="content">
            <xsl:apply-templates/>
          </xsl:with-param>
        </xsl:call-template>
      </xsl:when>
      <xsl:when test="$otherterm != '' and not($target)">
        <xsl:message>
          <xsl:text>Warning: glosssee @otherterm reference not found: </xsl:text>
          <xsl:value-of select="$otherterm"/>
        </xsl:message>
        <xsl:apply-templates mode="glossary.as.blocks"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:apply-templates mode="glossary.as.blocks"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>
  <xsl:call-template name="substitute-markup">
    <xsl:with-param name="template" select="$template"/>
    <xsl:with-param name="title" select="$title"/>
  </xsl:call-template>
</xsl:template>

<xsl:template match="glossentry/glossdef" mode="glossary.as.blocks">
  <xsl:apply-templates select="*[local-name(.) != 'glossseealso']"
                       mode="glossary.as.blocks"/>
  <xsl:if test="glossseealso">
    <fo:block>
      <xsl:variable name="template">
        <xsl:call-template name="gentext.template">
          <xsl:with-param name="context" select="'glossary'"/>
          <xsl:with-param name="name" select="'seealso'"/>
        </xsl:call-template>
      </xsl:variable>
      <xsl:variable name="title">
        <xsl:apply-templates select="glossseealso" mode="glossary.as.blocks"/>
      </xsl:variable>
      <xsl:call-template name="substitute-markup">
        <xsl:with-param name="template" select="$template"/>
        <xsl:with-param name="title" select="$title"/>
      </xsl:call-template>
    </fo:block>
  </xsl:if>
</xsl:template>

<xsl:template match="glossentry/glossdef/para[1]|glossentry/glossdef/simpara[1]"
              mode="glossary.as.blocks">
  <fo:block>
    <xsl:apply-templates/>
  </fo:block>
</xsl:template>

<!-- Handle any other glossdef content normally -->
<xsl:template match="*" mode="glossary.as.blocks">
  <xsl:apply-templates select="." />
</xsl:template>

<xsl:template match="glossseealso" mode="glossary.as.blocks">
  <xsl:variable name="otherterm" select="@otherterm"/>
  <xsl:variable name="targets" select="key('id', $otherterm)"/>
  <xsl:variable name="target" select="$targets[1]"/>
  <xsl:variable name="xlink" select="@xlink:href"/>

  <xsl:choose>
    <xsl:when test="$target">
      <fo:basic-link internal-destination="{$otherterm}"
                     xsl:use-attribute-sets="xref.properties">
        <xsl:apply-templates select="$target" mode="xref-to"/>
      </fo:basic-link>
    </xsl:when>
    <xsl:when test="$xlink">
      <xsl:call-template name="simple.xlink">
        <xsl:with-param name="content">
          <xsl:apply-templates/>
        </xsl:with-param>
      </xsl:call-template>
    </xsl:when>
    <xsl:when test="$otherterm != '' and not($target)">
      <xsl:message>
        <xsl:text>Warning: glossseealso @otherterm reference not found: </xsl:text>
        <xsl:value-of select="$otherterm"/>
      </xsl:message>
      <xsl:apply-templates mode="glossary.as.blocks"/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:apply-templates mode="glossary.as.blocks"/>
    </xsl:otherwise>
  </xsl:choose>

  <xsl:choose>
    <xsl:when test="position() = last()"/>
    <xsl:otherwise>
		<xsl:call-template name="gentext.template">
		  <xsl:with-param name="context" select="'glossary'"/>
		  <xsl:with-param name="name" select="'seealso-separator'"/>
		</xsl:call-template>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<!-- ==================================================================== -->

</xsl:stylesheet>
