<?xml version='1.0'?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:mml="http://www.w3.org/1998/Math/MathML"
                exclude-result-prefixes="mml"
                version='1.0'>

<!-- ********************************************************************
     $Id: math.xsl 9297 2012-04-22 03:56:16Z bobstayton $
     ********************************************************************

     This file is part of the XSL DocBook Stylesheet distribution.
     See ../README or http://docbook.sf.net/release/xsl/current/ for
     copyright and other information.

     ******************************************************************** -->

<xsl:template match="inlineequation">
  <xsl:apply-templates/>
</xsl:template>

<xsl:template match="alt">
</xsl:template>

<xsl:template match="mathphrase">
  <span>
    <xsl:apply-templates select="." mode="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates/>
  </span>
</xsl:template>

<!-- "Support" for MathML -->

<xsl:template match="mml:*" xmlns:mml="http://www.w3.org/1998/Math/MathML">
  <xsl:copy>
    <xsl:copy-of select="@*"/>
    <xsl:apply-templates/>
  </xsl:copy>
</xsl:template>

<!-- Support for TeX math in alt -->

<xsl:template match="*" mode="collect.tex.math">
  <xsl:call-template name="write.text.chunk">
    <xsl:with-param name="filename" select="$tex.math.file"/>
    <xsl:with-param name="method" select="'text'"/>
    <xsl:with-param name="content">
      <xsl:choose>
        <xsl:when test="$tex.math.in.alt = 'plain'">
          <xsl:call-template name="tex.math.plain.head"/>
          <xsl:apply-templates select="." mode="collect.tex.math.plain"/>
          <xsl:call-template name="tex.math.plain.tail"/>
        </xsl:when>
        <xsl:when test="$tex.math.in.alt = 'latex'">
          <xsl:call-template name="tex.math.latex.head"/>
          <xsl:apply-templates select="." mode="collect.tex.math.latex"/>
          <xsl:call-template name="tex.math.latex.tail"/>
        </xsl:when>
        <xsl:otherwise>
          <xsl:message>
            Unsupported TeX math notation: 
            <xsl:value-of select="$tex.math.in.alt"/>
          </xsl:message>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:with-param>
    <xsl:with-param name="encoding" select="$chunker.output.encoding"/>
  </xsl:call-template>
</xsl:template>

<!-- PlainTeX -->

<xsl:template name="tex.math.plain.head">
  <xsl:text>\nopagenumbers &#xA;</xsl:text>
</xsl:template>

<xsl:template name="tex.math.plain.tail">
  <xsl:text>\bye &#xA;</xsl:text>
</xsl:template>

<xsl:template match="inlineequation" mode="collect.tex.math.plain">
  <xsl:variable name="filename">
    <xsl:choose>
      <xsl:when test="graphic">
        <xsl:call-template name="mediaobject.filename">
          <xsl:with-param name="object" select="graphic"/>
        </xsl:call-template>
      </xsl:when>
      <xsl:otherwise>
        <xsl:call-template name="select.mediaobject.filename">
          <xsl:with-param name="olist" select="inlinemediaobject/*"/>
        </xsl:call-template>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>
  <xsl:variable name="output.delims">
    <xsl:call-template name="tex.math.output.delims"/>
  </xsl:variable>
  <xsl:variable name="tex" select="alt[@role='tex'] | inlinemediaobject/textobject[@role='tex']"/>
  <xsl:if test="$tex">
    <xsl:text>\special{dvi2bitmap outputfile </xsl:text>
    <xsl:value-of select="$filename"/>
    <xsl:text>} &#xA;</xsl:text>
    <xsl:if test="$output.delims != 0">
      <xsl:text>$</xsl:text>
    </xsl:if>
    <xsl:value-of select="$tex"/>
    <xsl:if test="$output.delims != 0">
      <xsl:text>$ &#xA;</xsl:text>
    </xsl:if>
    <xsl:text>\vfill\eject &#xA;</xsl:text>
  </xsl:if>
</xsl:template>

<xsl:template match="equation|informalequation" mode="collect.tex.math.plain">
  <xsl:variable name="filename">
    <xsl:choose>
      <xsl:when test="graphic">
        <xsl:call-template name="mediaobject.filename">
          <xsl:with-param name="object" select="graphic"/>
        </xsl:call-template>
      </xsl:when>
      <xsl:otherwise>
        <xsl:call-template name="select.mediaobject.filename">
          <xsl:with-param name="olist" select="mediaobject/*"/>
        </xsl:call-template>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>
  <xsl:variable name="output.delims">
    <xsl:call-template name="tex.math.output.delims"/>
  </xsl:variable>
  <xsl:variable name="tex" select="alt[@role='tex'] | mediaobject/textobject[@role='tex']"/>
  <xsl:if test="$tex">
    <xsl:text>\special{dvi2bitmap outputfile </xsl:text>
    <xsl:value-of select="$filename"/>
    <xsl:text>} &#xA;</xsl:text>
    <xsl:if test="$output.delims != 0">
      <xsl:text>$$</xsl:text>
    </xsl:if>
    <xsl:value-of select="$tex"/>
    <xsl:if test="$output.delims != 0">
      <xsl:text>$$ &#xA;</xsl:text>
    </xsl:if>
    <xsl:text>\vfill\eject &#xA;</xsl:text>
  </xsl:if>
</xsl:template>

<xsl:template match="text()" mode="collect.tex.math.plain"/>

<!-- LaTeX -->

<xsl:template name="tex.math.latex.head">
  <xsl:text>\documentclass{article} &#xA;</xsl:text>
  <xsl:text>\pagestyle{empty} &#xA;</xsl:text>
  <xsl:text>\begin{document} &#xA;</xsl:text>
</xsl:template>

<xsl:template name="tex.math.latex.tail">
  <xsl:text>\end{document} &#xA;</xsl:text>
</xsl:template>

<xsl:template match="inlineequation" mode="collect.tex.math.latex">
  <xsl:variable name="filename">
    <xsl:choose>
      <xsl:when test="graphic">
        <xsl:call-template name="mediaobject.filename">
          <xsl:with-param name="object" select="graphic"/>
        </xsl:call-template>
      </xsl:when>
      <xsl:otherwise>
        <xsl:call-template name="select.mediaobject.filename">
          <xsl:with-param name="olist" select="inlinemediaobject/*"/>
        </xsl:call-template>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>
  <xsl:variable name="output.delims">
    <xsl:call-template name="tex.math.output.delims"/>
  </xsl:variable>
  <xsl:variable name="tex" select="alt[@role='tex'] | inlinemediaobject/textobject[@role='tex']"/>
  <xsl:if test="$tex">
    <xsl:text>\special{dvi2bitmap outputfile </xsl:text>
    <xsl:value-of select="$filename"/>
    <xsl:text>} &#xA;</xsl:text>
    <xsl:if test="$output.delims != 0">  
      <xsl:text>$</xsl:text>
    </xsl:if>
    <xsl:value-of select="$tex"/>
    <xsl:if test="$output.delims != 0">  
      <xsl:text>$ &#xA;</xsl:text>
    </xsl:if>
    <xsl:text>\newpage &#xA;</xsl:text>
  </xsl:if>
</xsl:template>

<xsl:template match="equation|informalequation" mode="collect.tex.math.latex">
  <xsl:variable name="filename">
    <xsl:choose>
      <xsl:when test="graphic">
        <xsl:call-template name="mediaobject.filename">
          <xsl:with-param name="object" select="graphic"/>
        </xsl:call-template>
      </xsl:when>
      <xsl:otherwise>
        <xsl:call-template name="select.mediaobject.filename">
          <xsl:with-param name="olist" select="mediaobject/*"/>
        </xsl:call-template>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>
  <xsl:variable name="output.delims">
    <xsl:call-template name="tex.math.output.delims"/>
  </xsl:variable>
  <xsl:variable name="tex" select="alt[@role='tex'] | mediaobject/textobject[@role='tex']"/>
  <xsl:if test="$tex">
    <xsl:text>\special{dvi2bitmap outputfile </xsl:text>
    <xsl:value-of select="$filename"/>
    <xsl:text>} &#xA;</xsl:text>
    <xsl:if test="$output.delims != 0">
      <xsl:text>$$</xsl:text>
    </xsl:if>
    <xsl:value-of select="$tex"/>
    <xsl:if test="$output.delims != 0">
      <xsl:text>$$ &#xA;</xsl:text>
    </xsl:if>
    <xsl:text>\newpage &#xA;</xsl:text>
  </xsl:if>
</xsl:template>

<xsl:template match="text()" mode="collect.tex.math.latex"/>

<!-- Extracting image filename from mediaobject and graphic elements -->

<xsl:template name="select.mediaobject.filename">
  <xsl:param name="olist"
             select="imageobject|imageobjectco
                     |videoobject|audioobject|textobject"/>

  <xsl:variable name="mediaobject.index">
    <xsl:call-template name="select.mediaobject.index">
      <xsl:with-param name="olist" select="$olist"/>
      <xsl:with-param name="count" select="1"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:if test="$mediaobject.index != ''">
    <xsl:call-template name="mediaobject.filename">
      <xsl:with-param name="object"
                      select="$olist[position() = $mediaobject.index]"/>
    </xsl:call-template>
  </xsl:if>
</xsl:template>

<xsl:template name="tex.math.output.delims">
  <xsl:variable name="pi.delims">
    <xsl:call-template name="pi.dbtex_delims">
      <xsl:with-param name="node" select="descendant-or-self::*"/>
    </xsl:call-template>
  </xsl:variable>
  <xsl:variable name="result">
    <xsl:choose>
      <xsl:when test="$pi.delims = 'no'">0</xsl:when>
      <xsl:when test="$pi.delims = '' and $tex.math.delims = 0">0</xsl:when>
      <xsl:otherwise>1</xsl:otherwise>
    </xsl:choose>
  </xsl:variable>
  <xsl:value-of select="$result"/>
</xsl:template>

</xsl:stylesheet>
