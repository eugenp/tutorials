<?xml version='1.0'?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:exsl="http://exslt.org/common"
                version='1.0'>

<!-- ********************************************************************
     $Id: inline.xsl 7897 2008-03-10 15:46:03Z xmldoc $
     ********************************************************************

     This file is part of the XSL DocBook Stylesheet distribution.
     See ../README or http://docbook.sf.net/release/xsl/current/ for
     copyright and other information.

     ******************************************************************** -->

<!-- ==================================================================== -->

<xsl:template match="replaceable|varname|structfield">
  <xsl:if test="$man.hyphenate.computer.inlines = 0">
    <xsl:call-template name="suppress.hyphenation"/>
  </xsl:if>
  <xsl:call-template name="italic">
    <xsl:with-param name="node" select="."/>
    <xsl:with-param name="context" select="."/>
  </xsl:call-template>
</xsl:template>

<xsl:template match="option|userinput|envar|errorcode|constant|markup">
  <xsl:if test="$man.hyphenate.computer.inlines = 0">
    <xsl:call-template name="suppress.hyphenation"/>
  </xsl:if>
  <xsl:call-template name="bold">
    <xsl:with-param name="node" select="."/>
    <xsl:with-param name="context" select="."/>
  </xsl:call-template>
</xsl:template>

<xsl:template match="classname">
  <xsl:if test="$man.hyphenate.computer.inlines = 0">
    <xsl:call-template name="suppress.hyphenation"/>
  </xsl:if>
  <xsl:apply-templates/>
</xsl:template>

<xsl:template match="command">
  <xsl:if test="$man.hyphenate.computer.inlines = 0">
    <xsl:call-template name="suppress.hyphenation"/>
  </xsl:if>
  <xsl:call-template name="bold">
    <xsl:with-param name="node" select="."/>
    <xsl:with-param name="context" select="."/>
  </xsl:call-template>
</xsl:template>

<xsl:template match="type[not(ancestor::cmdsynopsis) and
                     not(ancestor::funcsynopsis)]">
  <xsl:if test="$man.hyphenate.computer.inlines = 0">
    <xsl:call-template name="suppress.hyphenation"/>
  </xsl:if>
  <xsl:call-template name="bold">
    <xsl:with-param name="node" select="."/>
    <xsl:with-param name="context" select="."/>
  </xsl:call-template>
</xsl:template>

<xsl:template match="function[not(ancestor::cmdsynopsis) and
                     not(ancestor::funcsynopsis)]">
  <xsl:if test="$man.hyphenate.computer.inlines = 0">
    <xsl:call-template name="suppress.hyphenation"/>
  </xsl:if>
  <xsl:call-template name="bold">
    <xsl:with-param name="node" select="."/>
    <xsl:with-param name="context" select="."/>
  </xsl:call-template>
</xsl:template>

<xsl:template match="parameter[not(ancestor::cmdsynopsis) and
                     not(ancestor::funcsynopsis)]">
  <xsl:if test="$man.hyphenate.computer.inlines = 0">
    <xsl:call-template name="suppress.hyphenation"/>
  </xsl:if>
  <xsl:call-template name="italic">
    <xsl:with-param name="node" select="."/>
    <xsl:with-param name="context" select="."/>
  </xsl:call-template>
</xsl:template>

<xsl:template match="filename">
  <!-- * add hyphenation suppression in Filename output only if -->
  <!-- * break.after.slash is also non-zero -->
  <xsl:if test="$man.hyphenate.filenames = 0 and
                $man.break.after.slash = 0">
    <xsl:call-template name="suppress.hyphenation"/>
  </xsl:if>
  <!-- * part of the old man(7) man page, now man-pages(7), says, -->
  <!-- * "Filenames (whether pathnames, or references to files in the -->
  <!-- * /usr/include directory) are always in italics". But that's dumb, -->
  <!-- * and looks like crap in PS/printed/PDF output, and there's no -->
  <!-- * sound rationale for it, so we don't do it. -->
  <xsl:call-template name="inline.monoseq"/>
</xsl:template>

<xsl:template match="emphasis">
  <xsl:choose>
    <xsl:when test="
      @role = 'bold' or
      @role = 'strong' or
      @remap = 'B'">
      <xsl:call-template name="bold">
        <xsl:with-param name="node" select="."/>
        <xsl:with-param name="context" select="."/>
      </xsl:call-template>
    </xsl:when>
    <xsl:otherwise>
      <xsl:call-template name="italic">
        <xsl:with-param name="node" select="."/>
        <xsl:with-param name="context" select="."/>
      </xsl:call-template>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="optional">
  <xsl:value-of select="$arg.choice.opt.open.str"/>
  <xsl:apply-templates/>
  <xsl:value-of select="$arg.choice.opt.close.str"/>
</xsl:template>

<xsl:template name="do-citerefentry">
  <xsl:param name="refentrytitle" select="''"/>
  <xsl:param name="manvolnum" select="''"/>
  <xsl:variable name="title">
    <xsl:value-of select="$refentrytitle"/>
  </xsl:variable>
  <xsl:call-template name="bold">
    <xsl:with-param name="node" select="exsl:node-set($title)"/>
    <xsl:with-param name="context" select="."/>
  </xsl:call-template>
  <xsl:text>(</xsl:text>
  <xsl:value-of select="$manvolnum"/>
  <xsl:text>)</xsl:text>
</xsl:template>

<xsl:template match="citerefentry">
  <xsl:call-template name="do-citerefentry">
    <xsl:with-param name="refentrytitle" select="refentrytitle"/>
    <xsl:with-param name="manvolnum" select="manvolnum"/>
  </xsl:call-template>
</xsl:template>

<xsl:template match="trademark|productname">
  <xsl:apply-templates/>
  <xsl:choose>
    <!-- * Just use true Unicode chars for copyright, trademark, etc., -->
    <!-- * symbols (by default, we later automatically translate them -->
    <!-- * with the apply-string-subst-map template, or with the -->
    <!-- * default character map, if man.charmap.enabled is true). -->
    <xsl:when test="@class = 'copyright'">
      <xsl:text>&#x00a9;</xsl:text>
    </xsl:when>
    <xsl:when test="@class = 'registered'">
      <xsl:text>&#x00ae;</xsl:text>
    </xsl:when>
    <xsl:when test="@class = 'service'">
      <xsl:text>&#x2120;</xsl:text>
    </xsl:when>
    <xsl:when test="@class = 'trade'">
      <xsl:text>&#x2122;</xsl:text>
    </xsl:when>
    <!-- * for Trademark element, render a trademark symbol by default -->
    <!-- * even if no "class" value is specified -->
    <xsl:when test="self::trademark" >
      <xsl:text>&#x2122;</xsl:text>
    </xsl:when>
    <xsl:otherwise>
      <!-- * otherwise we have a Productname with no value for the -->
      <!-- * "class" attribute, so don't render any symbol by default -->
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<!-- * span seems to sneak through into output sometimes, possibly due -->
<!-- * to failed Olink processing; so we need to catch it -->
<xsl:template match="span">
  <xsl:apply-templates/>
</xsl:template>

<xsl:template match="inlinemediaobject">
  <xsl:apply-templates/>
</xsl:template>

<!-- * indexterm instances produce groff comments like this: -->
<!-- * .\" primary: secondary: tertiary -->
<xsl:template match="indexterm">
  <xsl:text>.\" </xsl:text>
  <xsl:apply-templates/>
  <xsl:text>&#10;</xsl:text>
</xsl:template>

<xsl:template match="primary">
  <xsl:value-of select="normalize-space(.)"/>
</xsl:template>

<xsl:template match="secondary|tertiary">
  <xsl:text>: </xsl:text>
  <xsl:value-of select="normalize-space(.)"/>
</xsl:template>

<!-- * non-empty remark instances produce groff comments -->
<xsl:template match="remark">
  <xsl:variable name="content" select="normalize-space(.)"/>
  <xsl:if test="not($content = '')">
    <xsl:text>.\" </xsl:text>
    <xsl:value-of select="$content"/>
    <xsl:text>&#10;</xsl:text>
  </xsl:if>
</xsl:template>

</xsl:stylesheet>
