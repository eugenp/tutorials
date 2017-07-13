<?xml version="1.0" encoding="ASCII"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
		xmlns="http://www.w3.org/1999/xhtml"
		xmlns:db="http://docbook.org/ns/docbook"
		xmlns:dbs="http://docbook.org/ns/docbook-slides"
		exclude-result-prefixes="dbs db"
		version="1.0">

<xsl:import href="plain.xsl"/>

<!-- XXX: recommended by S5 but DocBook XSL produces XHTML Transitional

<xsl:output doctype-public="-//W3C//DTD XHTML 1.0 Strict//EN"
	    doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"/>
-->

<xsl:template name="xhtml.head">
  <xsl:variable name="s5.controls.visible">
    <xsl:choose>
      <xsl:when test="$s5.controls != 0">visible</xsl:when>

      <xsl:otherwise>hidden</xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <meta name="generator" content="DocBook Slides Stylesheets V{$VERSION}"/>
  <meta name="version" content="S5 1.1"/>
  <meta name="defaultView" content="{$s5.defaultview}"/>
  <meta name="controlVis" content="{$s5.controls.visible}"/>

  <link rel="stylesheet" href="{concat($s5.path.prefix, $s5.slides.css)}" type="text/css" media="projection" id="slideProj" />
  <link rel="stylesheet" href="{concat($s5.path.prefix, $s5.outline.css)}" type="text/css" media="screen" id="outlineStyle" />
  <link rel="stylesheet" href="{concat($s5.path.prefix, $s5.print.css)}" type="text/css" media="print" id="slidePrint" />
  <link rel="stylesheet" href="{concat($s5.path.prefix, $s5.opera.css)}" type="text/css" media="projection" id="operaFix" />
  <link rel="stylesheet" href="{$user.css}" type="text/css"/>

  <script src="{concat($s5.path.prefix, $s5.slides.js)}" type="text/javascript"></script>
</xsl:template>

<xsl:template name="slideshow.head">
  <div class="layout">
    <div id="controls"/>
    <div id="currentSlide"/>
    <div id="header">
      <xsl:apply-templates select="/" mode="slide.header.mode"/>
    </div>
    <div id="footer">
      <xsl:apply-templates select="/" mode="slide.footer.mode"/>
    </div>
  </div>
</xsl:template>

<xsl:template match="db:xref">
  <xsl:variable name="target" select="id(./@linkend)"/>

  <xsl:choose>
    <xsl:when test="($target[self::dbs:foil] or $target[self::dbs:foilgroup])">
      <xsl:variable name="target.no" select="count(preceding::dbs:foil|preceding::dbs:foilgroup) + 1"/>

      <xsl:apply-templates select="$target" mode="xref-to"/>
    </xsl:when>

    <xsl:otherwise>
      <xsl:call-template name="xref"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="db:biblioentry" mode="xref-to">
  <xsl:variable name="id" select="@xml:id"/>

  <xsl:choose>
    <xsl:when test="$bibliography.numbered != 0">
      <xsl:number from="db:bibliography" count="db:biblioentry|db:bibliomixed" level="any" format="1"/>
    </xsl:when>

    <xsl:otherwise>
      <xsl:value-of select="$id"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="db:mediaobject">
  <p>
    <xsl:if test="@dbs:style">
      <xsl:attribute name="class">
	<xsl:value-of select="@dbs:style"/>
      </xsl:attribute>
    </xsl:if>

    <xsl:apply-templates/>
  </p>
</xsl:template>

</xsl:stylesheet>
