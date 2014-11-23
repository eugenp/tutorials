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
     $Id: inline.xsl 9718 2013-01-30 18:29:51Z bobstayton $
     ********************************************************************

     This file is part of the XSL DocBook Stylesheet distribution.
     See ../README or http://docbook.sf.net/release/xsl/current/ for
     copyright and other information.

     ******************************************************************** -->

<xsl:key name="glossentries" match="glossentry" use="normalize-space(glossterm)"/>
<xsl:key name="glossentries" match="glossentry" use="normalize-space(glossterm/@baseform)"/>

<xsl:template name="simple.xlink">
  <xsl:param name="node" select="."/>
  <xsl:param name="content">
    <xsl:apply-templates/>
  </xsl:param>
  <xsl:param name="linkend" select="$node/@linkend"/>
  <xsl:param name="xhref" select="$node/@xlink:href"/>

  <xsl:choose>
    <xsl:when test="$xhref
                    and (not($node/@xlink:type) or 
                         $node/@xlink:type='simple')">

      <!-- Is it a local idref? -->
      <xsl:variable name="is.idref">
        <xsl:choose>
          <!-- if the href starts with # and does not contain an "(" -->
          <!-- or if the href starts with #xpointer(id(, it's just an ID -->
          <xsl:when test="starts-with($xhref,'#')
                          and (not(contains($xhref,'&#40;'))
                          or starts-with($xhref,
                                     '#xpointer&#40;id&#40;'))">1</xsl:when>
          <xsl:otherwise>0</xsl:otherwise>
        </xsl:choose>
      </xsl:variable>

      <!-- Is it an olink ? -->
      <xsl:variable name="is.olink">
        <xsl:choose>
          <!-- If xlink:role="http://docbook.org/xlink/role/olink" -->
          <!-- and if the href contains # -->
          <xsl:when test="contains($xhref,'#') and
               @xlink:role = $xolink.role">1</xsl:when>
          <xsl:otherwise>0</xsl:otherwise>
        </xsl:choose>
      </xsl:variable>

      <xsl:choose>
        <xsl:when test="$is.olink = 1">
          <xsl:call-template name="olink">
            <xsl:with-param name="content" select="$content"/>
          </xsl:call-template>
        </xsl:when>

        <xsl:when test="$is.idref = 1">

          <xsl:variable name="idref">
            <xsl:call-template name="xpointer.idref">
              <xsl:with-param name="xpointer" select="$xhref"/>
            </xsl:call-template>
          </xsl:variable>

          <xsl:variable name="targets" select="key('id',$idref)"/>
          <xsl:variable name="target" select="$targets[1]"/>

          <xsl:call-template name="check.id.unique">
            <xsl:with-param name="linkend" select="$idref"/>
          </xsl:call-template>

          <xsl:choose>
            <xsl:when test="count($target) = 0">
              <xsl:message>
                <xsl:text>XLink to nonexistent id: </xsl:text>
                <xsl:value-of select="$idref"/>
              </xsl:message>
              <xsl:copy-of select="$content"/>
            </xsl:when>

            <xsl:otherwise>
              <fo:basic-link internal-destination="{$idref}">
                <xsl:apply-templates select="." mode="simple.xlink.properties"/>
                <xsl:copy-of select="$content"/>
              </fo:basic-link>
            </xsl:otherwise>
          </xsl:choose>
        </xsl:when>

        <!-- otherwise it's a URI -->
        <xsl:otherwise>
          <fo:basic-link external-destination="url({$xhref})">
            <xsl:apply-templates select="." mode="simple.xlink.properties"/>
            <xsl:copy-of select="$content"/>
          </fo:basic-link>
          <!-- * Call the template for determining whether the URL for this -->
          <!-- * hyperlink is displayed, and how to display it (either inline or -->
          <!-- * as a numbered footnote). -->
          <xsl:call-template name="hyperlink.url.display">
            <xsl:with-param name="url" select="$xhref"/>
          </xsl:call-template>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:when>

    <xsl:when test="$linkend">
      <xsl:variable name="targets" select="key('id',$linkend)"/>
      <xsl:variable name="target" select="$targets[1]"/>

      <xsl:call-template name="check.id.unique">
        <xsl:with-param name="linkend" select="$linkend"/>
      </xsl:call-template>

      <xsl:choose>
        <xsl:when test="count($target) = 0">
          <xsl:message>
            <xsl:text>XLink to nonexistent id: </xsl:text>
            <xsl:value-of select="$linkend"/>
          </xsl:message>
          <xsl:copy-of select="$content"/>
        </xsl:when>

        <xsl:otherwise>
          <fo:basic-link internal-destination="{$linkend}">
            <xsl:apply-templates select="." mode="simple.xlink.properties"/>
            <xsl:copy-of select="$content"/>
          </fo:basic-link>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:when>

    <xsl:otherwise>
      <xsl:copy-of select="$content"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template name="inline.sansseq">
  <xsl:param name="content">
    <xsl:call-template name="simple.xlink">
        <xsl:with-param name="content">
          <xsl:apply-templates/>
        </xsl:with-param>
    </xsl:call-template>
  </xsl:param>

  <fo:inline font-family="{$sans.font.family}">
    <xsl:choose>
      <xsl:when test="@dir">
        <fo:inline>
          <xsl:attribute name="direction">
            <xsl:choose>
              <xsl:when test="@dir = 'ltr' or @dir = 'lro'">ltr</xsl:when>
              <xsl:otherwise>rtl</xsl:otherwise>
            </xsl:choose>
          </xsl:attribute>
          <xsl:copy-of select="$content"/>
        </fo:inline>
      </xsl:when>
      <xsl:otherwise>
        <xsl:copy-of select="$content"/>
      </xsl:otherwise>
    </xsl:choose>
  </fo:inline>
</xsl:template>

<xsl:template name="inline.charseq">
  <xsl:param name="content">
    <xsl:call-template name="simple.xlink">
      <xsl:with-param name="content">
        <xsl:apply-templates/>
      </xsl:with-param>
    </xsl:call-template>
  </xsl:param>

  <xsl:choose>
    <xsl:when test="@dir">
      <fo:inline>
        <xsl:attribute name="direction">
          <xsl:choose>
            <xsl:when test="@dir = 'ltr' or @dir = 'lro'">ltr</xsl:when>
            <xsl:otherwise>rtl</xsl:otherwise>
          </xsl:choose>
        </xsl:attribute>
        <xsl:copy-of select="$content"/>
      </fo:inline>
    </xsl:when>
    <xsl:otherwise>
      <xsl:copy-of select="$content"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template name="inline.monoseq">
  <xsl:param name="content">
    <xsl:call-template name="simple.xlink">
      <xsl:with-param name="content">
        <xsl:apply-templates/>
      </xsl:with-param>
    </xsl:call-template>
  </xsl:param>

  <fo:inline xsl:use-attribute-sets="monospace.properties">
    <xsl:call-template name="anchor"/>
    <xsl:if test="@dir">
      <xsl:attribute name="direction">
        <xsl:choose>
          <xsl:when test="@dir = 'ltr' or @dir = 'lro'">ltr</xsl:when>
          <xsl:otherwise>rtl</xsl:otherwise>
        </xsl:choose>
      </xsl:attribute>
    </xsl:if>
    <xsl:copy-of select="$content"/>
  </fo:inline>
</xsl:template>

<xsl:template name="inline.boldseq">
  <xsl:param name="content">
    <xsl:call-template name="simple.xlink">
      <xsl:with-param name="content">
        <xsl:apply-templates/>
      </xsl:with-param>
    </xsl:call-template>
  </xsl:param>

  <fo:inline font-weight="bold">
    <xsl:if test="@dir">
      <xsl:attribute name="direction">
        <xsl:choose>
          <xsl:when test="@dir = 'ltr' or @dir = 'lro'">ltr</xsl:when>
          <xsl:otherwise>rtl</xsl:otherwise>
        </xsl:choose>
      </xsl:attribute>
    </xsl:if>
    <xsl:copy-of select="$content"/>
  </fo:inline>
</xsl:template>

<xsl:template name="inline.italicseq">
  <xsl:param name="content">
    <xsl:call-template name="simple.xlink">
      <xsl:with-param name="content">
        <xsl:apply-templates/>
      </xsl:with-param>
    </xsl:call-template>
  </xsl:param>

  <fo:inline font-style="italic">
    <xsl:call-template name="anchor"/>
    <xsl:if test="@dir">
      <xsl:attribute name="direction">
        <xsl:choose>
          <xsl:when test="@dir = 'ltr' or @dir = 'lro'">ltr</xsl:when>
          <xsl:otherwise>rtl</xsl:otherwise>
        </xsl:choose>
      </xsl:attribute>
    </xsl:if>
    <xsl:copy-of select="$content"/>
  </fo:inline>
</xsl:template>

<xsl:template name="inline.boldmonoseq">
  <xsl:param name="content">
    <xsl:call-template name="simple.xlink">
      <xsl:with-param name="content">
        <xsl:apply-templates/>
      </xsl:with-param>
    </xsl:call-template>
  </xsl:param>

  <fo:inline font-weight="bold" xsl:use-attribute-sets="monospace.properties">
    <xsl:call-template name="anchor"/>
    <xsl:if test="@dir">
      <xsl:attribute name="direction">
        <xsl:choose>
          <xsl:when test="@dir = 'ltr' or @dir = 'lro'">ltr</xsl:when>
          <xsl:otherwise>rtl</xsl:otherwise>
        </xsl:choose>
      </xsl:attribute>
    </xsl:if>
    <xsl:copy-of select="$content"/>
  </fo:inline>
</xsl:template>

<xsl:template name="inline.italicmonoseq">
  <xsl:param name="content">
    <xsl:call-template name="simple.xlink">
      <xsl:with-param name="content">
        <xsl:apply-templates/>
      </xsl:with-param>
    </xsl:call-template>
  </xsl:param>

  <fo:inline font-style="italic" xsl:use-attribute-sets="monospace.properties">
    <xsl:call-template name="anchor"/>
    <xsl:if test="@dir">
      <xsl:attribute name="direction">
        <xsl:choose>
          <xsl:when test="@dir = 'ltr' or @dir = 'lro'">ltr</xsl:when>
          <xsl:otherwise>rtl</xsl:otherwise>
        </xsl:choose>
      </xsl:attribute>
    </xsl:if>
    <xsl:copy-of select="$content"/>
  </fo:inline>
</xsl:template>

<xsl:template name="inline.superscriptseq">
  <xsl:param name="content">
    <xsl:call-template name="simple.xlink">
      <xsl:with-param name="content">
        <xsl:apply-templates/>
      </xsl:with-param>
    </xsl:call-template>
  </xsl:param>

  <fo:inline xsl:use-attribute-sets="superscript.properties">
    <xsl:call-template name="anchor"/>
    <xsl:if test="@dir">
      <xsl:attribute name="direction">
        <xsl:choose>
          <xsl:when test="@dir = 'ltr' or @dir = 'lro'">ltr</xsl:when>
          <xsl:otherwise>rtl</xsl:otherwise>
        </xsl:choose>
      </xsl:attribute>
    </xsl:if>
    <xsl:choose>
      <xsl:when test="$fop.extensions != 0">
        <xsl:attribute name="vertical-align">super</xsl:attribute>
      </xsl:when>
      <xsl:otherwise>
        <xsl:attribute name="baseline-shift">super</xsl:attribute>
      </xsl:otherwise>
    </xsl:choose>
    <xsl:copy-of select="$content"/>
  </fo:inline>
</xsl:template>

<xsl:template name="inline.subscriptseq">
  <xsl:param name="content">
    <xsl:call-template name="simple.xlink">
      <xsl:with-param name="content">
        <xsl:apply-templates/>
      </xsl:with-param>
    </xsl:call-template>
  </xsl:param>

  <fo:inline xsl:use-attribute-sets="subscript.properties">
    <xsl:call-template name="anchor"/>
    <xsl:if test="@dir">
      <xsl:attribute name="direction">
        <xsl:choose>
          <xsl:when test="@dir = 'ltr' or @dir = 'lro'">ltr</xsl:when>
          <xsl:otherwise>rtl</xsl:otherwise>
        </xsl:choose>
      </xsl:attribute>
    </xsl:if>
    <xsl:choose>
      <xsl:when test="$fop.extensions != 0">
        <xsl:attribute name="vertical-align">sub</xsl:attribute>
      </xsl:when>
      <xsl:otherwise>
        <xsl:attribute name="baseline-shift">sub</xsl:attribute>
      </xsl:otherwise>
    </xsl:choose>
    <xsl:copy-of select="$content"/>
  </fo:inline>
</xsl:template>

<!-- ==================================================================== -->
<!-- some special cases -->

<xsl:template match="author">
  <xsl:call-template name="simple.xlink">
    <xsl:with-param name="content">
      <xsl:call-template name="person.name"/>
    </xsl:with-param>
  </xsl:call-template>
</xsl:template>

<xsl:template match="editor">
  <xsl:call-template name="simple.xlink">
    <xsl:with-param name="content">
      <xsl:call-template name="person.name"/>
    </xsl:with-param>
  </xsl:call-template>
</xsl:template>

<xsl:template match="othercredit">
  <xsl:call-template name="simple.xlink">
    <xsl:with-param name="content">
      <xsl:call-template name="person.name"/>
    </xsl:with-param>
  </xsl:call-template>
</xsl:template>

<xsl:template match="authorinitials">
  <xsl:call-template name="inline.charseq"/>
</xsl:template>

<!-- ==================================================================== -->

<xsl:template match="accel">
  <xsl:call-template name="inline.charseq"/>
</xsl:template>

<xsl:template match="action">
  <xsl:call-template name="inline.charseq"/>
</xsl:template>

<xsl:template match="application">
  <xsl:call-template name="inline.charseq"/>
</xsl:template>

<xsl:template match="classname">
  <xsl:call-template name="inline.monoseq"/>
</xsl:template>

<xsl:template match="exceptionname">
  <xsl:call-template name="inline.monoseq"/>
</xsl:template>

<xsl:template match="interfacename">
  <xsl:call-template name="inline.monoseq"/>
</xsl:template>

<xsl:template match="methodname">
  <xsl:call-template name="inline.monoseq"/>
</xsl:template>

<xsl:template match="command">
  <xsl:call-template name="inline.boldseq"/>
</xsl:template>

<xsl:template match="computeroutput">
  <xsl:call-template name="inline.monoseq"/>
</xsl:template>

<xsl:template match="constant">
  <xsl:call-template name="inline.monoseq"/>
</xsl:template>

<xsl:template match="database">
  <xsl:call-template name="inline.charseq"/>
</xsl:template>

<xsl:template match="date">
  <!-- should this support locale-specific formatting? how? -->
  <xsl:call-template name="inline.charseq"/>
</xsl:template>

<xsl:template match="errorcode">
  <xsl:call-template name="inline.charseq"/>
</xsl:template>

<xsl:template match="errorname">
  <xsl:call-template name="inline.charseq"/>
</xsl:template>

<xsl:template match="errortype">
  <xsl:call-template name="inline.charseq"/>
</xsl:template>

<xsl:template match="errortext">
  <xsl:call-template name="inline.charseq"/>
</xsl:template>

<xsl:template match="envar">
  <xsl:call-template name="inline.monoseq"/>
</xsl:template>

<xsl:template match="filename">
  <xsl:call-template name="inline.monoseq"/>
</xsl:template>

<xsl:template match="function">
  <xsl:choose>
    <xsl:when test="$function.parens != '0'
                    and (parameter or function or replaceable)">
      <xsl:variable name="nodes" select="text()|*"/>
      <xsl:call-template name="inline.monoseq">
        <xsl:with-param name="content">
          <xsl:call-template name="simple.xlink">
            <xsl:with-param name="content">
              <xsl:apply-templates select="$nodes[1]"/>
            </xsl:with-param>
          </xsl:call-template>
        </xsl:with-param>
      </xsl:call-template>
      <xsl:text>(</xsl:text>
      <xsl:apply-templates select="$nodes[position()>1]"/>
      <xsl:text>)</xsl:text>
    </xsl:when>
    <xsl:otherwise>
     <xsl:call-template name="inline.monoseq"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="function/parameter" priority="2">
  <xsl:call-template name="inline.italicmonoseq"/>
  <xsl:if test="$function.parens != 0 and following-sibling::*">
    <xsl:text>, </xsl:text>
  </xsl:if>
</xsl:template>

<xsl:template match="function/replaceable" priority="2">
  <xsl:call-template name="inline.italicmonoseq"/>
  <xsl:if test="$function.parens != 0 and following-sibling::*">
    <xsl:text>, </xsl:text>
  </xsl:if>
</xsl:template>

<xsl:template match="guibutton">
  <xsl:call-template name="inline.charseq"/>
</xsl:template>

<xsl:template match="guiicon">
  <xsl:call-template name="inline.charseq"/>
</xsl:template>

<xsl:template match="guilabel">
  <xsl:call-template name="inline.charseq"/>
</xsl:template>

<xsl:template match="guimenu">
  <xsl:call-template name="inline.charseq"/>
</xsl:template>

<xsl:template match="guimenuitem">
  <xsl:call-template name="inline.charseq"/>
</xsl:template>

<xsl:template match="guisubmenu">
  <xsl:call-template name="inline.charseq"/>
</xsl:template>

<xsl:template match="hardware">
  <xsl:call-template name="inline.charseq"/>
</xsl:template>

<xsl:template match="interface">
  <xsl:call-template name="inline.charseq"/>
</xsl:template>

<xsl:template match="interfacedefinition">
  <xsl:call-template name="inline.charseq"/>
</xsl:template>

<xsl:template match="keycap">
  <xsl:choose>
    <xsl:when test="@function and normalize-space(.) = ''">
      <xsl:call-template name="inline.boldseq">
        <xsl:with-param name="content">
          <xsl:call-template name="gentext.template">
            <xsl:with-param name="context" select="'keycap'"/>
            <xsl:with-param name="name" select="@function"/>
          </xsl:call-template>
        </xsl:with-param>
      </xsl:call-template>
    </xsl:when>
    <xsl:otherwise>
      <xsl:call-template name="inline.boldseq"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="keycode">
  <xsl:call-template name="inline.charseq"/>
</xsl:template>

<xsl:template match="keysym">
  <xsl:call-template name="inline.charseq"/>
</xsl:template>

<xsl:template match="literal">
  <xsl:call-template name="inline.monoseq"/>
</xsl:template>

<xsl:template match="code">
  <xsl:call-template name="inline.monoseq"/>
</xsl:template>

<xsl:template match="medialabel">
  <xsl:call-template name="inline.italicseq"/>
</xsl:template>

<xsl:template match="shortcut">
  <xsl:call-template name="inline.boldseq"/>
</xsl:template>

<xsl:template match="mousebutton">
  <xsl:call-template name="inline.charseq"/>
</xsl:template>

<xsl:template match="option">
  <xsl:call-template name="inline.monoseq"/>
</xsl:template>

<xsl:template match="package">
  <xsl:call-template name="inline.charseq"/>
</xsl:template>

<xsl:template match="parameter">
  <xsl:call-template name="inline.italicmonoseq"/>
</xsl:template>

<xsl:template match="property">
  <xsl:call-template name="inline.charseq"/>
</xsl:template>

<xsl:template match="prompt">
  <xsl:call-template name="inline.monoseq"/>
</xsl:template>

<xsl:template match="replaceable">
  <xsl:call-template name="inline.italicmonoseq"/>
</xsl:template>

<xsl:template match="returnvalue">
  <xsl:call-template name="inline.charseq"/>
</xsl:template>

<xsl:template match="structfield">
  <xsl:call-template name="inline.italicmonoseq"/>
</xsl:template>

<xsl:template match="structname">
  <xsl:call-template name="inline.charseq"/>
</xsl:template>

<xsl:template match="symbol">
  <xsl:call-template name="inline.charseq"/>
</xsl:template>

<xsl:template match="systemitem">
  <xsl:call-template name="inline.monoseq"/>
</xsl:template>

<xsl:template match="token">
  <xsl:call-template name="inline.charseq"/>
</xsl:template>

<xsl:template match="type">
  <xsl:call-template name="inline.charseq"/>
</xsl:template>

<xsl:template match="userinput">
  <xsl:call-template name="inline.boldmonoseq"/>
</xsl:template>

<xsl:template match="abbrev">
  <xsl:call-template name="inline.charseq"/>
</xsl:template>

<xsl:template match="acronym">
  <xsl:call-template name="inline.charseq"/>
</xsl:template>

<xsl:template match="citerefentry">
  <xsl:call-template name="inline.charseq"/>
</xsl:template>

<xsl:template match="citetitle">
  <xsl:choose>
    <xsl:when test="@pubwork = 'article'">
      <xsl:call-template name="gentext.startquote"/>
      <xsl:call-template name="inline.charseq"/>
      <xsl:call-template name="gentext.endquote"/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:call-template name="inline.italicseq"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="emphasis">
  <xsl:variable name="depth">
    <xsl:call-template name="dot.count">
      <xsl:with-param name="string">
        <xsl:number level="multiple"/>
      </xsl:with-param>
    </xsl:call-template>
  </xsl:variable>

  <xsl:choose>
    <xsl:when test="@role='bold' or @role='strong'">
      <xsl:call-template name="inline.boldseq"/>
    </xsl:when>
    <xsl:when test="@role='underline'">
      <fo:inline text-decoration="underline">
        <xsl:call-template name="inline.charseq"/>
      </fo:inline>
    </xsl:when>
    <xsl:when test="@role='strikethrough'">
      <fo:inline text-decoration="line-through">
        <xsl:call-template name="inline.charseq"/>
      </fo:inline>
    </xsl:when>
    <xsl:otherwise>
      <xsl:choose>
        <xsl:when test="$depth mod 2 = 1">
          <fo:inline font-style="normal">
            <xsl:apply-templates/>
          </fo:inline>
        </xsl:when>
        <xsl:otherwise>
          <xsl:call-template name="inline.italicseq"/>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="foreignphrase">
  <xsl:call-template name="inline.italicseq"/>
</xsl:template>

<xsl:template match="markup">
  <xsl:call-template name="inline.charseq"/>
</xsl:template>

<xsl:template match="phrase">
  <fo:inline>
    <xsl:call-template name="anchor"/>
    <xsl:call-template name="inline.charseq"/>
  </fo:inline>
</xsl:template>

<xsl:template match="quote">
  <xsl:variable name="depth">
    <xsl:call-template name="dot.count">
      <xsl:with-param name="string"><xsl:number level="multiple"/></xsl:with-param>
    </xsl:call-template>
  </xsl:variable>
  <xsl:variable name="content">
    <xsl:choose>
      <xsl:when test="$depth mod 2 = 0">
        <xsl:call-template name="gentext.startquote"/>
        <xsl:call-template name="inline.charseq"/>
        <xsl:call-template name="gentext.endquote"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:call-template name="gentext.nestedstartquote"/>
        <xsl:call-template name="inline.charseq"/>
        <xsl:call-template name="gentext.nestedendquote"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <fo:inline>
    <xsl:call-template name="anchor"/>
    <xsl:copy-of select="$content"/>
  </fo:inline>

</xsl:template>

<xsl:template match="varname">
  <xsl:call-template name="inline.monoseq"/>
</xsl:template>

<xsl:template match="wordasword">
  <xsl:call-template name="inline.italicseq"/>
</xsl:template>

<xsl:template match="lineannotation">
  <fo:inline font-style="italic">
    <xsl:call-template name="inline.charseq"/>
  </fo:inline>
</xsl:template>

<xsl:template match="superscript">
  <xsl:call-template name="inline.superscriptseq"/>
</xsl:template>

<xsl:template match="subscript">
  <xsl:call-template name="inline.subscriptseq"/>
</xsl:template>

<xsl:template match="trademark">
  <xsl:call-template name="inline.charseq"/>
  <xsl:choose>
    <xsl:when test="@class = 'copyright'
                    or @class = 'registered'">
      <xsl:call-template name="dingbat">
        <xsl:with-param name="dingbat" select="@class"/>
      </xsl:call-template>
    </xsl:when>
    <xsl:when test="@class = 'service'">
      <xsl:call-template name="inline.superscriptseq">
        <xsl:with-param name="content" select="'SM'"/>
      </xsl:call-template>
    </xsl:when>
    <xsl:otherwise>
      <xsl:call-template name="dingbat">
        <xsl:with-param name="dingbat" select="'trademark'"/>
      </xsl:call-template>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="firstterm">
  <xsl:call-template name="glossterm">
    <xsl:with-param name="firstterm" select="1"/>
  </xsl:call-template>
</xsl:template>

<xsl:template match="glossterm" name="glossterm">
  <xsl:param name="firstterm" select="0"/>

  <xsl:choose>
    <xsl:when test="($firstterm.only.link = 0 or $firstterm = 1) and @linkend">
      <xsl:variable name="targets" select="key('id',@linkend)"/>
      <xsl:variable name="target" select="$targets[1]"/>

      <xsl:choose>
        <xsl:when test="$target">
          <fo:basic-link internal-destination="{@linkend}" 
                         xsl:use-attribute-sets="xref.properties">
            <xsl:call-template name="inline.italicseq"/>
          </fo:basic-link>
        </xsl:when>
        <xsl:otherwise>
          <xsl:call-template name="inline.italicseq"/>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:when>

    <xsl:when test="not(@linkend)
                    and ($firstterm.only.link = 0 or $firstterm = 1)
                    and ($glossterm.auto.link != 0)
                    and $glossary.collection != ''">
      <xsl:variable name="term">
        <xsl:choose>
          <xsl:when test="@baseform"><xsl:value-of select="@baseform"/></xsl:when>
          <xsl:otherwise><xsl:value-of select="."/></xsl:otherwise>
        </xsl:choose>
      </xsl:variable>
      <xsl:variable name="cterm"
           select="(document($glossary.collection,.)//glossentry[glossterm=$term])[1]"/>

      <xsl:choose>
        <xsl:when test="not($cterm)">
          <xsl:message>
            <xsl:text>There's no entry for </xsl:text>
            <xsl:value-of select="$term"/>
            <xsl:text> in </xsl:text>
            <xsl:value-of select="$glossary.collection"/>
          </xsl:message>
          <xsl:call-template name="inline.italicseq"/>
        </xsl:when>
        <xsl:otherwise>
          <xsl:variable name="id">
            <xsl:call-template name="object.id">
              <xsl:with-param name="object" select="$cterm"/>
            </xsl:call-template>
          </xsl:variable>
          <fo:basic-link internal-destination="{$id}"
                         xsl:use-attribute-sets="xref.properties">
            <xsl:call-template name="inline.italicseq"/>
          </fo:basic-link>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:when>

    <xsl:when test="not(@linkend)
                    and ($firstterm.only.link = 0 or $firstterm = 1)
                    and $glossterm.auto.link != 0">
      <xsl:variable name="term">
        <xsl:choose>
          <xsl:when test="@baseform">
            <xsl:value-of select="normalize-space(@baseform)"/>
          </xsl:when>
          <xsl:otherwise>
            <xsl:value-of select="normalize-space(.)"/>
          </xsl:otherwise>
        </xsl:choose>
      </xsl:variable>

      <xsl:variable name="targets"
                    select="key('glossentries', $term)"/>
      <xsl:variable name="target" select="$targets[1]"/>

      <xsl:choose>
        <xsl:when test="count($targets)=0">
          <xsl:message>
            <xsl:text>Error: no glossentry for glossterm: </xsl:text>
            <xsl:value-of select="."/>
            <xsl:text>.</xsl:text>
          </xsl:message>
          <xsl:call-template name="inline.italicseq"/>
        </xsl:when>
        <xsl:otherwise>
          <xsl:variable name="termid">
            <xsl:call-template name="object.id">
              <xsl:with-param name="object" select="$target"/>
            </xsl:call-template>
          </xsl:variable>

          <fo:basic-link internal-destination="{$termid}"
                         xsl:use-attribute-sets="xref.properties">
            <xsl:call-template name="inline.italicseq"/>
          </fo:basic-link>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:when>
    <xsl:otherwise>
      <xsl:call-template name="inline.italicseq"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="termdef">
  <fo:inline>
    <xsl:call-template name="gentext.template">
      <xsl:with-param name="context" select="'termdef'"/>
      <xsl:with-param name="name" select="'prefix'"/>
    </xsl:call-template>
    <xsl:apply-templates/>
    <xsl:call-template name="gentext.template">
      <xsl:with-param name="context" select="'termdef'"/>
      <xsl:with-param name="name" select="'suffix'"/>
    </xsl:call-template>
  </fo:inline>
</xsl:template>

<xsl:template match="sgmltag|tag">
  <xsl:variable name="class">
    <xsl:choose>
      <xsl:when test="@class">
        <xsl:value-of select="@class"/>
      </xsl:when>
      <xsl:otherwise>element</xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:choose>
    <xsl:when test="$class='attribute'">
      <xsl:call-template name="inline.monoseq"/>
    </xsl:when>
    <xsl:when test="$class='attvalue'">
      <xsl:call-template name="inline.monoseq"/>
    </xsl:when>
    <xsl:when test="$class='element'">
      <xsl:call-template name="inline.monoseq"/>
    </xsl:when>
    <xsl:when test="$class='endtag'">
      <xsl:call-template name="inline.monoseq">
        <xsl:with-param name="content">
          <xsl:text>&lt;/</xsl:text>
          <xsl:apply-templates/>
          <xsl:text>&gt;</xsl:text>
        </xsl:with-param>
      </xsl:call-template>
    </xsl:when>
    <xsl:when test="$class='genentity'">
      <xsl:call-template name="inline.monoseq">
        <xsl:with-param name="content">
          <xsl:text>&amp;</xsl:text>
          <xsl:apply-templates/>
          <xsl:text>;</xsl:text>
        </xsl:with-param>
      </xsl:call-template>
    </xsl:when>
    <xsl:when test="$class='numcharref'">
      <xsl:call-template name="inline.monoseq">
        <xsl:with-param name="content">
          <xsl:text>&amp;#</xsl:text>
          <xsl:apply-templates/>
          <xsl:text>;</xsl:text>
        </xsl:with-param>
      </xsl:call-template>
    </xsl:when>
    <xsl:when test="$class='paramentity'">
      <xsl:call-template name="inline.monoseq">
        <xsl:with-param name="content">
          <xsl:text>%</xsl:text>
          <xsl:apply-templates/>
          <xsl:text>;</xsl:text>
        </xsl:with-param>
      </xsl:call-template>
    </xsl:when>
    <xsl:when test="$class='pi'">
      <xsl:call-template name="inline.monoseq">
        <xsl:with-param name="content">
          <xsl:text>&lt;?</xsl:text>
          <xsl:apply-templates/>
          <xsl:text>&gt;</xsl:text>
        </xsl:with-param>
      </xsl:call-template>
    </xsl:when>
    <xsl:when test="$class='xmlpi'">
      <xsl:call-template name="inline.monoseq">
        <xsl:with-param name="content">
          <xsl:text>&lt;?</xsl:text>
          <xsl:apply-templates/>
          <xsl:text>?&gt;</xsl:text>
        </xsl:with-param>
      </xsl:call-template>
    </xsl:when>
    <xsl:when test="$class='starttag'">
      <xsl:call-template name="inline.monoseq">
        <xsl:with-param name="content">
          <xsl:text>&lt;</xsl:text>
          <xsl:apply-templates/>
          <xsl:text>&gt;</xsl:text>
        </xsl:with-param>
      </xsl:call-template>
    </xsl:when>
    <xsl:when test="$class='emptytag'">
      <xsl:call-template name="inline.monoseq">
        <xsl:with-param name="content">
          <xsl:text>&lt;</xsl:text>
          <xsl:apply-templates/>
          <xsl:text>/&gt;</xsl:text>
        </xsl:with-param>
      </xsl:call-template>
    </xsl:when>
    <xsl:when test="$class='sgmlcomment' or $class='comment'">
      <xsl:call-template name="inline.monoseq">
        <xsl:with-param name="content">
          <xsl:text>&lt;!--</xsl:text>
          <xsl:apply-templates/>
          <xsl:text>--&gt;</xsl:text>
        </xsl:with-param>
      </xsl:call-template>
    </xsl:when>
    <xsl:otherwise>
      <xsl:call-template name="inline.charseq"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="email">
  <xsl:call-template name="inline.monoseq">
    <xsl:with-param name="content">
      <fo:inline keep-together.within-line="always" hyphenate="false">
        <xsl:if test="not($email.delimiters.enabled = 0)">
          <xsl:text>&lt;</xsl:text>
        </xsl:if>
        <xsl:choose>
          <xsl:when test="not($email.mailto.enabled = 0)">
            <fo:basic-link xsl:use-attribute-sets="xref.properties"
                           keep-together.within-line="always" hyphenate="false">
              <xsl:attribute name="external-destination">
                mailto:<xsl:value-of select="string(.)" />
              </xsl:attribute>
              <xsl:apply-templates/>
            </fo:basic-link>
          </xsl:when>
          <xsl:otherwise>
            <xsl:apply-templates/>
          </xsl:otherwise>
        </xsl:choose>
        <xsl:if test="not($email.delimiters.enabled = 0)">
          <xsl:text>&gt;</xsl:text>
        </xsl:if>
      </fo:inline>
    </xsl:with-param>
  </xsl:call-template>
</xsl:template>

<xsl:template match="keycombo">
  <xsl:variable name="action" select="@action"/>
  <xsl:variable name="joinchar">
    <xsl:choose>
      <xsl:when test="$action='seq'"><xsl:text> </xsl:text></xsl:when>
      <xsl:when test="$action='simul'">+</xsl:when>
      <xsl:when test="$action='press'">-</xsl:when>
      <xsl:when test="$action='click'">-</xsl:when>
      <xsl:when test="$action='double-click'">-</xsl:when>
      <xsl:when test="$action='other'"></xsl:when>
      <xsl:otherwise>+</xsl:otherwise>
    </xsl:choose>
  </xsl:variable>
  <xsl:for-each select="*">
    <xsl:if test="position()>1"><xsl:value-of select="$joinchar"/></xsl:if>
    <xsl:apply-templates select="."/>
  </xsl:for-each>
</xsl:template>

<xsl:template match="uri">
  <xsl:call-template name="inline.monoseq"/>
</xsl:template>

<!-- ==================================================================== -->

<xsl:template match="menuchoice">
  <xsl:variable name="shortcut" select="./shortcut"/>
  <xsl:call-template name="process.menuchoice"/>
  <xsl:if test="$shortcut">
    <xsl:text> (</xsl:text>
    <xsl:apply-templates select="$shortcut"/>
    <xsl:text>)</xsl:text>
  </xsl:if>
</xsl:template>

<xsl:template name="process.menuchoice">
  <xsl:param name="nodelist" select="guibutton|guiicon|guilabel|guimenu|guimenuitem|guisubmenu|interface"/><!-- not(shortcut) -->
  <xsl:param name="count" select="1"/>

  <xsl:variable name="mm.separator">
    <xsl:choose>
      <xsl:when test="($fop.extensions != 0 or $fop1.extensions != 0 ) and
                contains($menuchoice.menu.separator, '&#x2192;')">
        <fo:inline font-family="Symbol">
          <xsl:copy-of select="$menuchoice.menu.separator"/>
        </fo:inline>
      </xsl:when>
      <xsl:otherwise>
        <xsl:copy-of select="$menuchoice.menu.separator"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:choose>
    <xsl:when test="$count>count($nodelist)"></xsl:when>
    <xsl:when test="$count=1">
      <xsl:apply-templates select="$nodelist[$count=position()]"/>
      <xsl:call-template name="process.menuchoice">
        <xsl:with-param name="nodelist" select="$nodelist"/>
        <xsl:with-param name="count" select="$count+1"/>
      </xsl:call-template>
    </xsl:when>
    <xsl:otherwise>
      <xsl:variable name="node" select="$nodelist[$count=position()]"/>
      <xsl:choose>
        <xsl:when test="local-name($node)='guimenuitem'
                        or local-name($node)='guisubmenu'">
          <xsl:copy-of select="$mm.separator"/>
        </xsl:when>
        <xsl:otherwise>
          <xsl:copy-of select="$menuchoice.separator"/>
        </xsl:otherwise>
      </xsl:choose>
      <xsl:apply-templates select="$node"/>
      <xsl:call-template name="process.menuchoice">
        <xsl:with-param name="nodelist" select="$nodelist"/>
        <xsl:with-param name="count" select="$count+1"/>
      </xsl:call-template>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<!-- ==================================================================== -->

<xsl:template match="optional">
  <xsl:value-of select="$arg.choice.opt.open.str"/>
  <xsl:call-template name="inline.charseq"/>
  <xsl:value-of select="$arg.choice.opt.close.str"/>
</xsl:template>

<xsl:template match="citation">
  <!-- todo: integrate with bibliography collection -->
  <xsl:variable name="targets" select="(//biblioentry | //bibliomixed)[abbrev = string(current())]"/>
  <xsl:variable name="target" select="$targets[1]"/>

  <xsl:choose>
    <!-- try automatic linking based on match to abbrev -->
    <xsl:when test="$target and not(xref) and not(link)">

      <xsl:text>[</xsl:text>
      <fo:basic-link>
        <xsl:attribute name="internal-destination">
          <xsl:call-template name="object.id">
            <xsl:with-param name="object" select="$target"/>
          </xsl:call-template>
        </xsl:attribute>

        <xsl:choose>
          <xsl:when test="$bibliography.numbered != 0">
            <xsl:apply-templates select="$target" mode="citation"/>
          </xsl:when>
          <xsl:otherwise>
            <xsl:call-template name="inline.charseq"/>
          </xsl:otherwise>
        </xsl:choose>
     
      </fo:basic-link>
      <xsl:text>]</xsl:text>
    </xsl:when>

    <xsl:otherwise>
      <xsl:text>[</xsl:text>
      <xsl:call-template name="inline.charseq"/>
      <xsl:text>]</xsl:text>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="citebiblioid">
  <xsl:variable name="targets" select="//*[biblioid = string(current())]"/>
  <xsl:variable name="target" select="$targets[1]"/>

  <xsl:choose>
    <!-- try automatic linking based on match to parent of biblioid -->
    <xsl:when test="$target and not(xref) and not(link)">

      <xsl:text>[</xsl:text>
      <fo:basic-link>
        <xsl:attribute name="internal-destination">
          <xsl:call-template name="object.id">
            <xsl:with-param name="object" select="$target"/>
          </xsl:call-template>
        </xsl:attribute>

        <xsl:call-template name="inline.charseq"/>
            
      </fo:basic-link>
      <xsl:text>]</xsl:text>
    </xsl:when>

    <xsl:otherwise>
      <xsl:text>[</xsl:text>
      <xsl:call-template name="inline.charseq"/>
      <xsl:text>]</xsl:text>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="biblioentry|bibliomixed" mode="citation">
  <xsl:number from="bibliography" count="biblioentry|bibliomixed"
              level="any" format="1"/>
</xsl:template>

<!-- ==================================================================== -->

<xsl:template match="comment[&comment.block.parents;]|remark[&comment.block.parents;]">
  <xsl:if test="$show.comments != 0">
    <fo:block font-style="italic">
      <xsl:call-template name="inline.charseq"/>
    </fo:block>
  </xsl:if>
</xsl:template>

<xsl:template match="comment|remark">
  <xsl:if test="$show.comments != 0">
    <fo:inline font-style="italic">
      <xsl:call-template name="inline.charseq"/>
    </fo:inline>
  </xsl:if>
</xsl:template>

<!-- ==================================================================== -->

<xsl:template match="productname">
  <xsl:call-template name="inline.charseq"/>
  <xsl:if test="@class">
    <xsl:call-template name="dingbat">
      <xsl:with-param name="dingbat" select="@class"/>
    </xsl:call-template>
  </xsl:if>
</xsl:template>

<xsl:template match="productnumber">
  <xsl:call-template name="inline.charseq"/>
</xsl:template>

<!-- ==================================================================== -->

<xsl:template match="pob|street|city|state|postcode|country|otheraddr">
  <xsl:call-template name="inline.charseq"/>
</xsl:template>

<xsl:template match="phone|fax">
  <xsl:call-template name="inline.charseq"/>
</xsl:template>

<!-- in Addresses, for example -->
<xsl:template match="honorific|firstname|surname|lineage|othername">
  <xsl:call-template name="inline.charseq"/>
</xsl:template>

<!-- ==================================================================== -->

<xsl:template match="person">
  <xsl:apply-templates select="personname"/>
</xsl:template>

<xsl:template match="personname">
  <xsl:call-template name="simple.xlink">
    <xsl:with-param name="content">
      <xsl:call-template name="person.name"/>
    </xsl:with-param>
  </xsl:call-template>
</xsl:template>

<xsl:template match="jobtitle">
  <xsl:call-template name="simple.xlink">
    <xsl:with-param name="content">
      <xsl:apply-templates/>
    </xsl:with-param>
  </xsl:call-template>
</xsl:template>

<!-- ==================================================================== -->

<xsl:template match="org">
  <xsl:call-template name="inline.charseq"/>
</xsl:template>

<xsl:template match="orgname">
  <xsl:call-template name="inline.charseq"/>
</xsl:template>

<xsl:template match="orgdiv">
  <xsl:call-template name="inline.charseq"/>
</xsl:template>

<xsl:template match="affiliation">
  <xsl:call-template name="inline.charseq"/>
</xsl:template>

<!-- ==================================================================== -->

<xsl:template match="beginpage">
  <!-- does nothing; this *is not* markup to force a page break. -->
</xsl:template>

<xsl:template match="*" mode="simple.xlink.properties">
  <!-- Placeholder template to apply properties to links made from
       elements other than xref, link, and olink.
       This template should generate attributes only, as it is
       applied right after the opening <fo:basic-link> tag.
       -->
  <!-- for example
  <xsl:attribute name="color">blue</xsl:attribute>
  -->
  <!-- Since this is a mode, you can create different
       templates with different properties for different linking elements -->
</xsl:template>

</xsl:stylesheet>

