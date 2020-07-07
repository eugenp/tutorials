<?xml version='1.0'?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:doc="http://nwalsh.com/xsl/documentation/1.0"
                exclude-result-prefixes="doc"
                version='1.0'>

<!-- ********************************************************************
     $Id: gentext.xsl 9713 2013-01-22 22:08:30Z bobstayton $
     ********************************************************************

     This file is part of the XSL DocBook Stylesheet distribution.
     See ../README or http://docbook.sf.net/release/xsl/current/ for
     copyright and other information.

     ******************************************************************** -->

<!-- ==================================================================== -->


<xsl:template match="*" mode="object.title.template">
  <xsl:call-template name="gentext.template">
    <xsl:with-param name="context" select="'title'"/>
    <xsl:with-param name="name">
      <xsl:call-template name="xpath.location"/>
    </xsl:with-param>
  </xsl:call-template>
</xsl:template>

<xsl:template match="chapter" mode="object.title.template">
  <xsl:choose>
    <xsl:when test="string($chapter.autolabel) != 0">
      <xsl:call-template name="gentext.template">
        <xsl:with-param name="context" select="'title-numbered'"/>
        <xsl:with-param name="name">
          <xsl:call-template name="xpath.location"/>
        </xsl:with-param>
      </xsl:call-template>
    </xsl:when>
    <xsl:otherwise>
      <xsl:call-template name="gentext.template">
        <xsl:with-param name="context" select="'title-unnumbered'"/>
        <xsl:with-param name="name">
          <xsl:call-template name="xpath.location"/>
        </xsl:with-param>
      </xsl:call-template>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="appendix" mode="object.title.template">
  <xsl:choose>
    <xsl:when test="string($appendix.autolabel) != 0">
      <xsl:call-template name="gentext.template">
        <xsl:with-param name="context" select="'title-numbered'"/>
        <xsl:with-param name="name">
          <xsl:call-template name="xpath.location"/>
        </xsl:with-param>
      </xsl:call-template>
    </xsl:when>
    <xsl:otherwise>
      <xsl:call-template name="gentext.template">
        <xsl:with-param name="context" select="'title-unnumbered'"/>
        <xsl:with-param name="name">
          <xsl:call-template name="xpath.location"/>
        </xsl:with-param>
      </xsl:call-template>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="part" mode="object.title.template">
  <xsl:choose>
    <xsl:when test="string($part.autolabel) != 0">
      <xsl:call-template name="gentext.template">
        <xsl:with-param name="context" select="'title-numbered'"/>
        <xsl:with-param name="name">
          <xsl:call-template name="xpath.location"/>
        </xsl:with-param>
      </xsl:call-template>
    </xsl:when>
    <xsl:otherwise>
      <xsl:call-template name="gentext.template">
        <xsl:with-param name="context" select="'title-unnumbered'"/>
        <xsl:with-param name="name">
          <xsl:call-template name="xpath.location"/>
        </xsl:with-param>
      </xsl:call-template>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="section|sect1|sect2|sect3|sect4|sect5|simplesect
                     |bridgehead|topic"
              mode="object.title.template">
  <xsl:variable name="is.numbered">
    <xsl:call-template name="label.this.section"/>
  </xsl:variable>
  <xsl:choose>
    <xsl:when test="$is.numbered != 0">
      <xsl:call-template name="gentext.template">
        <xsl:with-param name="context" select="'title-numbered'"/>
        <xsl:with-param name="name">
          <xsl:call-template name="xpath.location"/>
        </xsl:with-param>
      </xsl:call-template>
    </xsl:when>
    <xsl:otherwise>
      <xsl:call-template name="gentext.template">
        <xsl:with-param name="context" select="'title-unnumbered'"/>
        <xsl:with-param name="name">
          <xsl:call-template name="xpath.location"/>
        </xsl:with-param>
      </xsl:call-template>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="procedure" mode="object.title.template">
  <xsl:choose>
    <xsl:when test="$formal.procedures != 0 and title">
      <xsl:call-template name="gentext.template">
        <xsl:with-param name="context" select="'title'"/>
        <xsl:with-param name="name">
          <xsl:call-template name="xpath.location"/>
          <xsl:text>.formal</xsl:text>
        </xsl:with-param>
      </xsl:call-template>
    </xsl:when>
    <xsl:otherwise>
      <xsl:call-template name="gentext.template">
        <xsl:with-param name="context" select="'title'"/>
        <xsl:with-param name="name">
          <xsl:call-template name="xpath.location"/>
        </xsl:with-param>
      </xsl:call-template>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<!-- ============================================================ -->

<xsl:template match="*" mode="object.subtitle.template">
  <xsl:call-template name="gentext.template">
    <xsl:with-param name="context" select="'subtitle'"/>
    <xsl:with-param name="name">
      <xsl:call-template name="xpath.location"/>
    </xsl:with-param>
  </xsl:call-template>
</xsl:template>

<!-- ============================================================ -->

<xsl:template match="*" mode="is.autonumber">
  <xsl:value-of select="'0'"/>
</xsl:template>

<xsl:template match="section|sect1|sect2|sect3|sect4|sect5" 
              mode="is.autonumber">
  <xsl:call-template name="label.this.section"/>
</xsl:template>

<xsl:template match="figure|example|table|equation" mode="is.autonumber">
  <xsl:value-of select="'1'"/>
</xsl:template>

<xsl:template match="appendix" mode="is.autonumber">
  <xsl:value-of select="$appendix.autolabel"/>
</xsl:template>

<xsl:template match="chapter" mode="is.autonumber">
  <xsl:value-of select="$chapter.autolabel"/>
</xsl:template>

<xsl:template match="part" mode="is.autonumber">
  <xsl:value-of select="$part.autolabel"/>
</xsl:template>

<xsl:template match="preface" mode="is.autonumber">
  <xsl:value-of select="$preface.autolabel"/>
</xsl:template>

<xsl:template match="question|answer" mode="is.autonumber">
  <xsl:choose>
    <xsl:when test="$qanda.defaultlabel = 'number'
                    and not(label)">
      <xsl:value-of select="'1'"/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:value-of select="'0'"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="qandadiv" mode="is.autonumber">
  <xsl:value-of select="$qandadiv.autolabel"/>
</xsl:template>

<xsl:template match="bridgehead" mode="is.autonumber">
  <!-- bridgeheads are not numbered -->
  <xsl:text>0</xsl:text>
</xsl:template>

<xsl:template match="procedure" mode="is.autonumber">
  <xsl:value-of select="$formal.procedures"/>
</xsl:template>


<xsl:template match="*" mode="object.xref.template">
  <xsl:param name="purpose"/>
  <xsl:param name="xrefstyle"/>
  <xsl:param name="referrer"/>

  <!-- Is autonumbering on? -->
  <xsl:variable name="autonumber">
    <xsl:apply-templates select="." mode="is.autonumber"/>
  </xsl:variable>

  <xsl:variable name="number-and-title-template">
    <xsl:call-template name="gentext.template.exists">
      <xsl:with-param name="context" select="'xref-number-and-title'"/>
      <xsl:with-param name="name">
        <xsl:call-template name="xpath.location"/>
      </xsl:with-param>
    </xsl:call-template>
  </xsl:variable>

  <xsl:variable name="number-template">
    <xsl:call-template name="gentext.template.exists">
      <xsl:with-param name="context" select="'xref-number'"/>
      <xsl:with-param name="name">
        <xsl:call-template name="xpath.location"/>
      </xsl:with-param>
    </xsl:call-template>
  </xsl:variable>

  <xsl:variable name="context">
    <xsl:choose>
      <xsl:when test="self::equation and not(title) and not(info/title)">
         <xsl:value-of select="'xref-number'"/>
      </xsl:when>
      <xsl:when test="string($autonumber) != 0 
                      and $number-and-title-template != 0
                      and $xref.with.number.and.title != 0">
         <xsl:value-of select="'xref-number-and-title'"/>
      </xsl:when>
      <xsl:when test="string($autonumber) != 0 
                      and $number-template != 0">
         <xsl:value-of select="'xref-number'"/>
      </xsl:when>
      <xsl:otherwise>
         <xsl:value-of select="'xref'"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:call-template name="gentext.template">
    <xsl:with-param name="context" select="$context"/>
    <xsl:with-param name="name">
      <xsl:call-template name="xpath.location"/>
    </xsl:with-param>
    <xsl:with-param name="purpose" select="$purpose"/>
    <xsl:with-param name="xrefstyle" select="$xrefstyle"/>
    <xsl:with-param name="referrer" select="$referrer"/>
  </xsl:call-template>

</xsl:template>


<!-- ============================================================ -->

<xsl:template match="*" mode="object.title.markup">
  <xsl:param name="allow-anchors" select="0"/>
  <xsl:variable name="template">
    <xsl:apply-templates select="." mode="object.title.template"/>
  </xsl:variable>

<!--
  <xsl:message>
    <xsl:text>object.title.markup: </xsl:text>
    <xsl:value-of select="local-name(.)"/>
    <xsl:text>: </xsl:text>
    <xsl:value-of select="$template"/>
  </xsl:message>
-->

  <xsl:call-template name="substitute-markup">
    <xsl:with-param name="allow-anchors" select="$allow-anchors"/>
    <xsl:with-param name="template" select="$template"/>
  </xsl:call-template>
</xsl:template>

<xsl:template match="*" mode="object.title.markup.textonly">
  <xsl:variable name="title">
    <xsl:apply-templates select="." mode="object.title.markup"/>
  </xsl:variable>
  <xsl:value-of select="normalize-space($title)"/>
</xsl:template>

<!-- ============================================================ -->

<xsl:template match="*" mode="object.titleabbrev.markup">
  <xsl:param name="allow-anchors" select="0"/>

  <!-- Just for consistency in template naming -->

  <xsl:apply-templates select="." mode="titleabbrev.markup">
    <xsl:with-param name="allow-anchors" select="$allow-anchors"/>
  </xsl:apply-templates>
</xsl:template>

<!-- ============================================================ -->

<xsl:template match="*" mode="object.subtitle.markup">
  <xsl:variable name="template">
    <xsl:apply-templates select="." mode="object.subtitle.template"/>
  </xsl:variable>

  <xsl:call-template name="substitute-markup">
    <xsl:with-param name="template" select="$template"/>
  </xsl:call-template>
</xsl:template>

<!-- ============================================================ -->

<xsl:template match="*" mode="object.xref.markup">
  <xsl:param name="purpose"/>
  <xsl:param name="xrefstyle"/>
  <xsl:param name="referrer"/>
  <xsl:param name="verbose" select="1"/>

  <xsl:variable name="template">
    <xsl:choose>
      <xsl:when test="starts-with(normalize-space($xrefstyle), 'select:')">
        <xsl:call-template name="make.gentext.template">
          <xsl:with-param name="xrefstyle" select="$xrefstyle"/>
          <xsl:with-param name="purpose" select="$purpose"/>
          <xsl:with-param name="referrer" select="$referrer"/>
        </xsl:call-template>
      </xsl:when>
      <xsl:when test="starts-with(normalize-space($xrefstyle), 'template:')">
        <xsl:value-of select="substring-after(normalize-space($xrefstyle), 'template:')"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:apply-templates select="." mode="object.xref.template">
          <xsl:with-param name="purpose" select="$purpose"/>
          <xsl:with-param name="xrefstyle" select="$xrefstyle"/>
          <xsl:with-param name="referrer" select="$referrer"/>
        </xsl:apply-templates>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

<!-- 
  <xsl:message>
    <xsl:text>object.xref.markup: </xsl:text>
    <xsl:value-of select="local-name(.)"/>
    <xsl:text>(</xsl:text>
    <xsl:value-of select="$xrefstyle"/>
    <xsl:text>, </xsl:text>
    <xsl:value-of select="$purpose"/>
    <xsl:text>)</xsl:text>
    <xsl:text>: [</xsl:text>
    <xsl:value-of select="$template"/>
    <xsl:text>]</xsl:text>
  </xsl:message>
-->

  <xsl:if test="$template = '' and $verbose != 0">
    <xsl:message>
      <xsl:text>object.xref.markup: empty xref template</xsl:text>
      <xsl:text> for linkend="</xsl:text>
      <xsl:value-of select="@id|@xml:id"/>
      <xsl:text>" and @xrefstyle="</xsl:text>
      <xsl:value-of select="$xrefstyle"/>
      <xsl:text>"</xsl:text>
    </xsl:message>
  </xsl:if>

  <xsl:call-template name="substitute-markup">
    <xsl:with-param name="purpose" select="$purpose"/>
    <xsl:with-param name="xrefstyle" select="$xrefstyle"/>
    <xsl:with-param name="referrer" select="$referrer"/>
    <xsl:with-param name="template" select="$template"/>
    <xsl:with-param name="verbose" select="$verbose"/>
  </xsl:call-template>
</xsl:template>

<xsl:template match="listitem" mode="object.xref.markup">
  <xsl:param name="verbose" select="1"/>

  <xsl:choose>
    <xsl:when test="parent::orderedlist">
      <xsl:variable name="template">
        <xsl:apply-templates select="." mode="object.xref.template"/>
      </xsl:variable>
      <xsl:call-template name="substitute-markup">
        <xsl:with-param name="template" select="$template"/>
      </xsl:call-template>
    </xsl:when>
    <xsl:when test="$verbose != 0">
      <xsl:message>
        <xsl:text>Xref is only supported to listitems in an</xsl:text>
        <xsl:text> orderedlist: </xsl:text>
        <xsl:value-of select=".//@id|.//@xml:id"/>
      </xsl:message>
      <xsl:text>???</xsl:text>
    </xsl:when>
  </xsl:choose>
</xsl:template>

<xsl:template match="question" mode="object.xref.markup">
  <xsl:param name="purpose"/>
  <xsl:param name="xrefstyle"/>
  <xsl:param name="referrer"/>

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

  <xsl:variable name="template">
    <xsl:choose>
      <!-- This avoids double Q: Q: in xref when defaultlabel=qanda -->
      <xsl:when test="$deflabel = 'qanda' and not(label)">%n</xsl:when>
      <xsl:otherwise>
        <xsl:apply-templates select="." mode="object.xref.template">
          <xsl:with-param name="purpose" select="$purpose"/>
          <xsl:with-param name="xrefstyle" select="$xrefstyle"/>
          <xsl:with-param name="referrer" select="$referrer"/>
        </xsl:apply-templates>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:call-template name="substitute-markup">
    <xsl:with-param name="purpose" select="$purpose"/>
    <xsl:with-param name="xrefstyle" select="$xrefstyle"/>
    <xsl:with-param name="referrer" select="$referrer"/>
    <xsl:with-param name="template" select="$template"/>
  </xsl:call-template>
</xsl:template>

<!-- ============================================================ -->

<xsl:template name="substitute-markup">
  <xsl:param name="template" select="''"/>
  <xsl:param name="allow-anchors" select="'0'"/>
  <xsl:param name="title" select="''"/>
  <xsl:param name="subtitle" select="''"/>
  <xsl:param name="docname" select="''"/>
  <xsl:param name="label" select="''"/>
  <xsl:param name="pagenumber" select="''"/>
  <xsl:param name="purpose"/>
  <xsl:param name="xrefstyle"/>
  <xsl:param name="referrer"/>
  <xsl:param name="verbose"/>

  <xsl:choose>
    <xsl:when test="contains($template, '%')">
      <xsl:value-of select="substring-before($template, '%')"/>
      <xsl:variable name="candidate"
             select="substring(substring-after($template, '%'), 1, 1)"/>
      <xsl:choose>
        <xsl:when test="$candidate = 't'">
          <xsl:apply-templates select="." mode="insert.title.markup">
            <xsl:with-param name="purpose" select="$purpose"/>
            <xsl:with-param name="xrefstyle" select="$xrefstyle"/>
            <xsl:with-param name="title">
              <xsl:choose>
                <xsl:when test="$title != ''">
                  <xsl:copy-of select="$title"/>
                </xsl:when>
                <xsl:when test="$purpose = 'xref'">
                  <xsl:apply-templates select="." mode="titleabbrev.markup">
                    <xsl:with-param name="allow-anchors" select="$allow-anchors"/>
                    <xsl:with-param name="verbose" select="$verbose"/>
                  </xsl:apply-templates>
                </xsl:when>
                <xsl:otherwise>
                  <xsl:apply-templates select="." mode="title.markup">
                    <xsl:with-param name="allow-anchors" select="$allow-anchors"/>
                    <xsl:with-param name="verbose" select="$verbose"/>
                  </xsl:apply-templates>
                </xsl:otherwise>
              </xsl:choose>
            </xsl:with-param>
          </xsl:apply-templates>
        </xsl:when>
        <xsl:when test="$candidate = 's'">
          <xsl:apply-templates select="." mode="insert.subtitle.markup">
            <xsl:with-param name="purpose" select="$purpose"/>
            <xsl:with-param name="xrefstyle" select="$xrefstyle"/>
            <xsl:with-param name="subtitle">
              <xsl:choose>
                <xsl:when test="$subtitle != ''">
                  <xsl:copy-of select="$subtitle"/>
                </xsl:when>
                <xsl:otherwise>
                  <xsl:apply-templates select="." mode="subtitle.markup">
                    <xsl:with-param name="allow-anchors" select="$allow-anchors"/>
                  </xsl:apply-templates>
                </xsl:otherwise>
              </xsl:choose>
            </xsl:with-param>
          </xsl:apply-templates>
        </xsl:when>
        <xsl:when test="$candidate = 'n'">
          <xsl:apply-templates select="." mode="insert.label.markup">
            <xsl:with-param name="purpose" select="$purpose"/>
            <xsl:with-param name="xrefstyle" select="$xrefstyle"/>
            <xsl:with-param name="label">
              <xsl:choose>
                <xsl:when test="$label != ''">
                  <xsl:copy-of select="$label"/>
                </xsl:when>
                <xsl:otherwise>
                  <xsl:apply-templates select="." mode="label.markup"/>
                </xsl:otherwise>
              </xsl:choose>
            </xsl:with-param>
          </xsl:apply-templates>
        </xsl:when>
        <xsl:when test="$candidate = 'p'">
          <xsl:apply-templates select="." mode="insert.pagenumber.markup">
            <xsl:with-param name="purpose" select="$purpose"/>
            <xsl:with-param name="xrefstyle" select="$xrefstyle"/>
            <xsl:with-param name="pagenumber">
              <xsl:choose>
                <xsl:when test="$pagenumber != ''">
                  <xsl:copy-of select="$pagenumber"/>
                </xsl:when>
                <xsl:otherwise>
                  <xsl:apply-templates select="." mode="pagenumber.markup"/>
                </xsl:otherwise>
              </xsl:choose>
            </xsl:with-param>
          </xsl:apply-templates>
        </xsl:when>
        <xsl:when test="$candidate = 'o'">
          <!-- olink target document title -->
          <xsl:apply-templates select="." mode="insert.olink.docname.markup">
            <xsl:with-param name="purpose" select="$purpose"/>
            <xsl:with-param name="xrefstyle" select="$xrefstyle"/>
            <xsl:with-param name="docname">
              <xsl:choose>
                <xsl:when test="$docname != ''">
                  <xsl:copy-of select="$docname"/>
                </xsl:when>
                <xsl:otherwise>
                  <xsl:apply-templates select="." mode="olink.docname.markup"/>
                </xsl:otherwise>
              </xsl:choose>
            </xsl:with-param>
          </xsl:apply-templates>
        </xsl:when>
        <xsl:when test="$candidate = 'd'">
          <xsl:apply-templates select="." mode="insert.direction.markup">
            <xsl:with-param name="purpose" select="$purpose"/>
            <xsl:with-param name="xrefstyle" select="$xrefstyle"/>
            <xsl:with-param name="direction">
              <xsl:choose>
                <xsl:when test="$referrer">
                  <xsl:variable name="referent-is-below">
                    <xsl:for-each select="preceding::xref">
                      <xsl:if test="generate-id(.) = generate-id($referrer)">1</xsl:if>
                    </xsl:for-each>
                  </xsl:variable>
                  <xsl:choose>
                    <xsl:when test="$referent-is-below = ''">
                      <xsl:call-template name="gentext">
                        <xsl:with-param name="key" select="'above'"/>
                      </xsl:call-template>
                    </xsl:when>
                    <xsl:otherwise>
                      <xsl:call-template name="gentext">
                        <xsl:with-param name="key" select="'below'"/>
                      </xsl:call-template>
                    </xsl:otherwise>
                  </xsl:choose>
                </xsl:when>
                <xsl:otherwise>
                  <xsl:message>Attempt to use %d in gentext with no referrer!</xsl:message>
                </xsl:otherwise>
              </xsl:choose>
            </xsl:with-param>
          </xsl:apply-templates>
        </xsl:when>
        <xsl:when test="$candidate = '%' ">
          <xsl:text>%</xsl:text>
        </xsl:when>
        <xsl:otherwise>
          <xsl:text>%</xsl:text><xsl:value-of select="$candidate"/>
        </xsl:otherwise>
      </xsl:choose>
      <!-- recurse with the rest of the template string -->
      <xsl:variable name="rest"
            select="substring($template,
            string-length(substring-before($template, '%'))+3)"/>
      <xsl:call-template name="substitute-markup">
        <xsl:with-param name="template" select="$rest"/>
        <xsl:with-param name="allow-anchors" select="$allow-anchors"/>
        <xsl:with-param name="title" select="$title"/>
        <xsl:with-param name="subtitle" select="$subtitle"/>
        <xsl:with-param name="docname" select="$docname"/>
        <xsl:with-param name="label" select="$label"/>
        <xsl:with-param name="pagenumber" select="$pagenumber"/>
        <xsl:with-param name="purpose" select="$purpose"/>
        <xsl:with-param name="xrefstyle" select="$xrefstyle"/>
        <xsl:with-param name="referrer" select="$referrer"/>
        <xsl:with-param name="verbose" select="$verbose"/>
      </xsl:call-template>
    </xsl:when>
    <xsl:otherwise>
      <xsl:value-of select="$template"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<!-- ============================================================ -->

<xsl:template name="make.gentext.template">
  <xsl:param name="xrefstyle" select="''"/>
  <xsl:param name="purpose"/>
  <xsl:param name="referrer"/>
  <xsl:param name="lang">
    <xsl:call-template name="l10n.language"/>
  </xsl:param>
  <xsl:param name="target.elem" select="local-name(.)"/>

  <!-- parse xrefstyle to get parts -->
  <xsl:variable name="parts"
      select="substring-after(normalize-space($xrefstyle), 'select:')"/>

  <xsl:variable name="labeltype">
    <xsl:choose>
      <xsl:when test="contains($parts, 'labelnumber')">
         <xsl:text>labelnumber</xsl:text>
      </xsl:when>
      <xsl:when test="contains($parts, 'labelname')">
         <xsl:text>labelname</xsl:text>
      </xsl:when>
      <xsl:when test="contains($parts, 'label')">
         <xsl:text>label</xsl:text>
      </xsl:when>
    </xsl:choose>
  </xsl:variable>

  <xsl:variable name="titletype">
    <xsl:choose>
      <xsl:when test="contains($parts, 'quotedtitle')">
         <xsl:text>quotedtitle</xsl:text>
      </xsl:when>
      <xsl:when test="contains($parts, 'title')">
         <xsl:text>title</xsl:text>
      </xsl:when>
    </xsl:choose>
  </xsl:variable>

  <xsl:variable name="pagetype">
    <xsl:choose>
      <xsl:when test="$insert.olink.page.number = 'no' and
                      local-name($referrer) = 'olink'">
        <!-- suppress page numbers -->
      </xsl:when>
      <xsl:when test="$insert.xref.page.number = 'no' and
                      local-name($referrer) != 'olink'">
        <!-- suppress page numbers -->
      </xsl:when>
      <xsl:when test="contains($parts, 'nopage')">
         <xsl:text>nopage</xsl:text>
      </xsl:when>
      <xsl:when test="contains($parts, 'pagenumber')">
         <xsl:text>pagenumber</xsl:text>
      </xsl:when>
      <xsl:when test="contains($parts, 'pageabbrev')">
         <xsl:text>pageabbrev</xsl:text>
      </xsl:when>
      <xsl:when test="contains($parts, 'Page')">
         <xsl:text>Page</xsl:text>
      </xsl:when>
      <xsl:when test="contains($parts, 'page')">
         <xsl:text>page</xsl:text>
      </xsl:when>
    </xsl:choose>
  </xsl:variable>

  <xsl:variable name="docnametype">
    <xsl:choose>
      <xsl:when test="($olink.doctitle = 0 or
                       $olink.doctitle = 'no') and
                      local-name($referrer) = 'olink'">
        <!-- suppress docname -->
      </xsl:when>
      <xsl:when test="contains($parts, 'nodocname')">
         <xsl:text>nodocname</xsl:text>
      </xsl:when>
      <xsl:when test="contains($parts, 'docnamelong')">
         <xsl:text>docnamelong</xsl:text>
      </xsl:when>
      <xsl:when test="contains($parts, 'docname')">
         <xsl:text>docname</xsl:text>
      </xsl:when>
    </xsl:choose>
  </xsl:variable>

  <xsl:if test="$labeltype != ''">
    <xsl:choose>
      <xsl:when test="$labeltype = 'labelname'">
        <xsl:call-template name="gentext">
          <xsl:with-param name="key">
            <xsl:choose>
              <xsl:when test="local-name($referrer) = 'olink'">
                <xsl:value-of select="$target.elem"/>
              </xsl:when>
              <xsl:otherwise>
                <xsl:value-of select="local-name(.)"/>
              </xsl:otherwise>
            </xsl:choose>
          </xsl:with-param>
        </xsl:call-template>
      </xsl:when>
      <xsl:when test="$labeltype = 'labelnumber'">
        <xsl:text>%n</xsl:text>
      </xsl:when>
      <xsl:when test="$labeltype = 'label'">
        <xsl:call-template name="gentext.template">
          <xsl:with-param name="context" select="'xref-number'"/>
          <xsl:with-param name="name">
            <xsl:choose>
              <xsl:when test="local-name($referrer) = 'olink'">
                <xsl:value-of select="$target.elem"/>
              </xsl:when>
              <xsl:otherwise>
                <xsl:call-template name="xpath.location"/>
              </xsl:otherwise>
            </xsl:choose>
          </xsl:with-param>
          <xsl:with-param name="purpose" select="$purpose"/>
          <xsl:with-param name="xrefstyle" select="$xrefstyle"/>
          <xsl:with-param name="referrer" select="$referrer"/>
        </xsl:call-template>
      </xsl:when>
    </xsl:choose>

    <xsl:choose>
      <xsl:when test="$titletype != ''">
        <xsl:value-of select="$xref.label-title.separator"/>
      </xsl:when>
      <xsl:when test="$pagetype != '' and $pagetype != 'nopage'">
        <xsl:value-of select="$xref.label-page.separator"/>
      </xsl:when>
    </xsl:choose>
  </xsl:if>

  <xsl:if test="$titletype != ''">
    <xsl:choose>
      <xsl:when test="$titletype = 'title'">
        <xsl:text>%t</xsl:text>
      </xsl:when>
      <xsl:when test="$titletype = 'quotedtitle'">
        <xsl:call-template name="gentext.dingbat">
          <xsl:with-param name="dingbat" select="'startquote'"/>
        </xsl:call-template>
        <xsl:text>%t</xsl:text>
        <xsl:call-template name="gentext.dingbat">
          <xsl:with-param name="dingbat" select="'endquote'"/>
        </xsl:call-template>
      </xsl:when>
    </xsl:choose>

    <xsl:choose>
      <xsl:when test="$pagetype != '' and $pagetype != 'nopage'">
        <xsl:value-of select="$xref.title-page.separator"/>
      </xsl:when>
    </xsl:choose>
  </xsl:if>
  
  <!-- special case: use regular xref template if just turning off page -->
  <xsl:if test="($pagetype = 'nopage' or $docnametype = 'nodocname')
                  and local-name($referrer) != 'olink'
                  and $labeltype = '' 
                  and $titletype = ''">
    <xsl:apply-templates select="." mode="object.xref.template">
      <xsl:with-param name="purpose" select="$purpose"/>
      <xsl:with-param name="xrefstyle" select="$xrefstyle"/>
      <xsl:with-param name="referrer" select="$referrer"/>
    </xsl:apply-templates>
  </xsl:if>

  <xsl:if test="$pagetype != ''">
    <xsl:choose>
      <xsl:when test="$pagetype = 'page'">
        <xsl:call-template name="gentext.template">
          <xsl:with-param name="context" select="'xref'"/>
          <xsl:with-param name="name" select="'page'"/>
        </xsl:call-template>
      </xsl:when>
      <xsl:when test="$pagetype = 'Page'">
        <xsl:call-template name="gentext.template">
          <xsl:with-param name="context" select="'xref'"/>
          <xsl:with-param name="name" select="'Page'"/>
        </xsl:call-template>
      </xsl:when>
      <xsl:when test="$pagetype = 'pageabbrev'">
        <xsl:call-template name="gentext.template">
          <xsl:with-param name="context" select="'xref'"/>
          <xsl:with-param name="name" select="'pageabbrev'"/>
        </xsl:call-template>
      </xsl:when>
      <xsl:when test="$pagetype = 'pagenumber'">
        <xsl:text>%p</xsl:text>
      </xsl:when>
    </xsl:choose>

  </xsl:if>

  <!-- Add reference to other document title -->
  <xsl:if test="$docnametype != '' and local-name($referrer) = 'olink'">
    <!-- Any separator should be in the gentext template -->
    <xsl:choose>
      <xsl:when test="$docnametype = 'docnamelong'">
        <xsl:call-template name="gentext.template">
          <xsl:with-param name="context" select="'xref'"/>
          <xsl:with-param name="name" select="'docnamelong'"/>
        </xsl:call-template>
      </xsl:when>
      <xsl:when test="$docnametype = 'docname'">
        <xsl:call-template name="gentext.template">
          <xsl:with-param name="context" select="'xref'"/>
          <xsl:with-param name="name" select="'docname'"/>
        </xsl:call-template>
      </xsl:when>
    </xsl:choose>

  </xsl:if>
  
</xsl:template>

</xsl:stylesheet>
