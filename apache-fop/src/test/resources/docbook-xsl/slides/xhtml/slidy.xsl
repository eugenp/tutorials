<?xml version="1.0" encoding="ASCII"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
		xmlns="http://www.w3.org/1999/xhtml"
		xmlns:db="http://docbook.org/ns/docbook"
		xmlns:dbs="http://docbook.org/ns/docbook-slides"
		exclude-result-prefixes="dbs db"
		version="1.0">

<xsl:import href="plain.xsl"/>

<xsl:param name="wrap.slidecontent">0</xsl:param>

<xsl:template name="xhtml.head">
  <meta name="copyright">
    <xsl:attribute name="content">
      <xsl:if test="/dbs:slides/db:info/db:copyright">
	<xsl:call-template name="slide.copyright"/>
      </xsl:if>
    </xsl:attribute>
  </meta>

  <xsl:if test="$slidy.duration != 0">
    <meta name="duration" content="{$slidy.duration}"/>
  </xsl:if>

  <link rel="stylesheet" href="{concat($slidy.path.prefix, $slidy.slidy.css)}" type="text/css"/>
  <xsl:if test="$slidy.user.css">
    <link rel="stylesheet" href="{concat($slidy.path.prefix, $slidy.user.css)}" type="text/css"/>
  </xsl:if>
  <link rel="stylesheet" href="{$user.css}" type="text/css"/>
  <script src="{concat($slidy.path.prefix, $slidy.slidy.js)}" charset="utf-8" type="text/javascript"/>
</xsl:template>

<xsl:template name="slideshow.head">
  <div class="background"/>
</xsl:template>

<xsl:template name="slideshow.content">
  <xsl:if test="$generate.titlepage != 0">
    <xsl:apply-templates select="/dbs:slides" mode="titlepage"/>
  </xsl:if>

  <xsl:apply-templates select="/dbs:slides/dbs:foil|dbs:slides/dbs:foilgroup"/>
</xsl:template>

<xsl:template match="db:xref">
  <xsl:variable name="target" select="id(./@linkend)"/>

  <xsl:choose>
    <xsl:when test="($target[self::dbs:foil] or $target[self::dbs:foilgroup])">
      <!-- foil no: preceding foil(group)s + titlepage + 1 -->
      <xsl:variable name="target.no" select="count($target/preceding::dbs:foil|$target/preceding::dbs:foilgroup) + 1 + $generate.titlepage"/>

      <a href="{concat('#(', $target.no, ')')}">
	<xsl:apply-templates select="$target" mode="xref-to"/>
      </a>
    </xsl:when>

    <xsl:otherwise>
      <xsl:call-template name="xref"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="db:biblioentry" mode="xref-to">
  <xsl:variable name="id" select="@xml:id"/>
  <xsl:variable name="entry" select="//db:bibliography/*[@xml:id=$id][1]"/>

  <a>
    <xsl:attribute name="href">
      <xsl:value-of select="concat('#(', count($entry/preceding::dbs:foil|$entry/preceding::dbs:foilgroup) + 1 + $generate.titlepage, ')')"/>
    </xsl:attribute>
    <xsl:choose>
      <xsl:when test="$bibliography.numbered != 0">
        <xsl:number from="db:bibliography" count="db:biblioentry|db:bibliomixed" level="any" format="1"/>
      </xsl:when>

      <xsl:otherwise>
        <xsl:value-of select="$id"/>
      </xsl:otherwise>
    </xsl:choose>
  </a>
</xsl:template>

</xsl:stylesheet>
