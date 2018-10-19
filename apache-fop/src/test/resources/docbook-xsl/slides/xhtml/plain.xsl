<?xml version="1.0" encoding="ASCII"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
		xmlns="http://www.w3.org/1999/xhtml"
		xmlns:xlink="http://www.w3.org/1999/xlink"
		xmlns:db="http://docbook.org/ns/docbook"
		xmlns:dbs="http://docbook.org/ns/docbook-slides"
		xmlns:exsl="http://exslt.org/common"
		exclude-result-prefixes="dbs db xlink"
		extension-element-prefixes="exsl"
		version="1.0">

<xsl:import href="../../xhtml/chunk.xsl"/>
<xsl:import href="../common/common.xsl"/>
<xsl:import href="plain-titlepage.xsl"/>
<xsl:import href="param.xsl"/>

<xsl:param name="local.l10n.xml" select="document('')"/>
<i18n xmlns="http://docbook.sourceforge.net/xmlns/l10n/1.0">
  <l:l10n xmlns:l="http://docbook.sourceforge.net/xmlns/l10n/1.0" language="en">
    <l:gentext key="Foilgroup" text="Foil Group"/>
    <l:gentext key="Foil" text="Foil"/>
    <l:gentext key="Speakernotes" text="Speaker Notes"/>
    <l:gentext key="Handoutnotes" text="Handout Notes"/>
    <l:gentext key="SVGImage" text="SVG image"/>
    <l:gentext key="MathMLFormula" text="MathML formula"/>

    <l:context name="title">
      <l:gentext key="foil" text="Foil %n %t"/>
      <l:gentext key="foilgroup" text="Foil %n %t"/>
    </l:context>
  </l:l10n>
</i18n>

<!-- Overrides from DocBook XSL -->
<xsl:template name="process.qanda.toc"/>

<!-- Main content starts here -->

<xsl:template name="xhtml.head">
  <meta name="generator" content="DocBook Slides Stylesheets V{$VERSION}"/>
  <link rel="stylesheet" href="{$user.css}" type="text/css"/>
</xsl:template>

<xsl:template name="slideshow.head"/>

<xsl:template name="slideshow.content">
  <div class="presentation">
    <xsl:if test="$generate.titlepage != 0">
      <xsl:apply-templates select="/dbs:slides" mode="titlepage"/>
    </xsl:if>

    <xsl:apply-templates select="/dbs:slides/dbs:foil|dbs:slides/dbs:foilgroup"/>
  </div>
</xsl:template>

<xsl:template match="/dbs:slides" mode="titlepage">
  <xsl:call-template name="slides.titlepage"/>
</xsl:template>

<xsl:template name="slide.notes">
  <xsl:if test="($generate.speakernotes != 0) and ./dbs:speakernotes">
    <div class="notes">
      <h2 class="notes">
	<xsl:call-template name="gentext">
	  <xsl:with-param name="key" select="'Speakernotes'"/>
	</xsl:call-template>
      </h2>

      <xsl:apply-templates select="dbs:speakernotes" mode="notes.mode"/>
    </div>
  </xsl:if>

  <xsl:if test="($generate.handoutnotes != 0) and ./dbs:handoutnotes">
    <div class="handout">
      <h2 class="handout">
        <xsl:call-template name="gentext">
          <xsl:with-param name="key" select="'Handoutnotes'"/>
        </xsl:call-template>
      </h2>

      <xsl:apply-templates select="dbs:handoutnotes" mode="notes.mode"/>
    </div>
  </xsl:if>
</xsl:template>

<xsl:template match="/">
  <html>
    <xsl:if test="/dbs:slides/@xml:lang">
      <xsl:attribute name="xml:lang">
	<xsl:value-of select="/dbs:slides/@xml:lang"/>
      </xsl:attribute>
    </xsl:if>

    <head>
      <title>
	<xsl:call-template name="get.title">
	  <xsl:with-param name="ctx" select="/dbs:slides"/>
	</xsl:call-template>
      </title>

      <xsl:call-template name="xhtml.head"/>
    </head>

    <body>
      <xsl:call-template name="slideshow.head"/>

      <xsl:call-template name="slideshow.content"/>
    </body>
  </html>
</xsl:template>

<xsl:template name="foilgroup.content">
      <xsl:apply-templates select="*[not(self::dbs:foil)]"/>

      <xsl:if test="($generate.foilgroup.toc != 0)">
        <xsl:choose>
          <xsl:when test="($generate.foilgroup.numbered.toc != 0)">
            <ol>
              <xsl:for-each select="dbs:foil">
                <li><xsl:call-template name="get.title"/></li>
              </xsl:for-each>
            </ol>
          </xsl:when>

          <xsl:otherwise>
            <ul>
              <xsl:for-each select="dbs:foil">
                <li><xsl:call-template name="get.title"/></li>
              </xsl:for-each>
            </ul>
          </xsl:otherwise>
        </xsl:choose>
    </xsl:if>
</xsl:template>

<xsl:template name="foil.classes">
  <xsl:variable name="classValue">
    <xsl:call-template name="process.dbs.attributes">
      <!-- Do not put incremental or collapsible on foils -->
      <xsl:with-param name="attributeSet" select="self::*/@dbs:style"/>
      <xsl:with-param name="stored">
        <xsl:value-of select="'slide'"/>
        <xsl:if test="@*[namespace-uri() = 'http://docbook.org/ns/docbook-slides']">
          <xsl:text> </xsl:text>
        </xsl:if>
      </xsl:with-param>
    </xsl:call-template>
  </xsl:variable>

  <xsl:attribute name="class">
    <xsl:value-of select="$classValue"/>
  </xsl:attribute>
</xsl:template>

<xsl:template match="dbs:foilgroup">
  <xsl:call-template name="generate.anchor"/>
  <div>
    <xsl:call-template name="foil.classes"/>

    <xsl:choose>
      <xsl:when test="($wrap.slidecontent != 0)">
	<div class="slidecontent">
	  <xsl:call-template name="foilgroup.content"/>
	</div>
      </xsl:when>

      <xsl:otherwise>
	<xsl:call-template name="foilgroup.content"/>
      </xsl:otherwise>
    </xsl:choose>

    <xsl:call-template name="slide.notes"/>
  </div>

  <xsl:apply-templates select="dbs:foil"/>
</xsl:template>

<xsl:template match="dbs:foil">
  <xsl:call-template name="generate.anchor"/>
  <div>
    <xsl:call-template name="foil.classes"/>

    <xsl:choose>
      <xsl:when test="($wrap.slidecontent != 0)">
	<div class="slidecontent">
	  <xsl:apply-templates select="*"/>
	</div>
      </xsl:when>

      <xsl:otherwise>
	<xsl:apply-templates/>
      </xsl:otherwise>
    </xsl:choose>

    <xsl:call-template name="process.footnotes"/>

    <xsl:call-template name="slide.notes"/>
  </div>
</xsl:template>

<xsl:template match="dbs:handoutnotes"/>

<xsl:template match="dbs:speakernotes"/>

<xsl:template match="dbs:handoutnotes" mode="notes.mode">
  <div class="handoutnotes">
    <xsl:apply-templates/>
  </div>
</xsl:template>

<xsl:template match="dbs:speakernotes" mode="notes.mode">
  <div class="speakernotes">
    <xsl:apply-templates/>
  </div>
</xsl:template>

<xsl:template name="process.dbs.attributes">
  <xsl:param name="attributeSet"/>
  <xsl:param name="stored" select="''"/>

  <xsl:variable name="gotIncremental">
    <xsl:if test="((local-name($attributeSet[1]) = 'incremental') and ($attributeSet[1] = '1'))">1</xsl:if>
  </xsl:variable>

  <xsl:variable name="enableIncremental">
    <xsl:if test="($disable.incremental = '0') and ($gotIncremental = '1')">1</xsl:if>
  </xsl:variable>

  <xsl:variable name="gotCollapsible">
    <xsl:if test="((local-name($attributeSet[1]) = 'collapsible') and ($attributeSet[1] = '1'))">1</xsl:if>
  </xsl:variable>

  <xsl:variable name="enableCollapsible">
    <xsl:if test="($disable.collapsible = '0') and ($gotCollapsible = '1')">1</xsl:if>
  </xsl:variable>

  <xsl:variable name="append">
    <xsl:choose>
      <xsl:when test="local-name($attributeSet[1]) = 'style'">
	<xsl:value-of select="$attributeSet[1]"/>
      </xsl:when>

      <xsl:when test="$enableCollapsible = '1'">
        <xsl:value-of select="'outline'"/>
      </xsl:when>
 
      <xsl:when test="$enableIncremental = '1'">
	<xsl:value-of select="'incremental'"/>
      </xsl:when>
    </xsl:choose>

    <xsl:if test="count($attributeSet) &gt; 1">
      <xsl:text> </xsl:text>
    </xsl:if>
  </xsl:variable>

  <xsl:choose>
    <xsl:when test="not($attributeSet)">
      <xsl:value-of select="$stored"/>
    </xsl:when>

    <xsl:when test="count($attributeSet) &lt;= 1">
      <xsl:value-of select="concat($stored, $append)"/>
    </xsl:when>

    <xsl:otherwise>
      <xsl:call-template name="process.dbs.attributes">
	<xsl:with-param name="attributeSet" select="$attributeSet[position() != 1]"/>
	<xsl:with-param name="stored" select="concat($stored, $append)"/>
      </xsl:call-template>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="dbs:block">
  <xsl:variable name="classValue">
    <xsl:call-template name="process.dbs.attributes">
      <xsl:with-param name="attributeSet" select="(ancestor-or-self::*/@dbs:incremental)[last()] | (ancestor-or-self::*/@dbs:collapsible)[last()] | self::*/@dbs:style"/>
    </xsl:call-template>
  </xsl:variable>

  <div class="{$classValue}">
    <xsl:apply-templates/>
  </div>
</xsl:template>

<xsl:template match="db:info">
  <xsl:apply-templates select="db:title|db:titleabbrev|db:subtitle|db:author|db:authorgroup/db:author"/>
</xsl:template>

<xsl:template match="db:title|db:titleabbrev">
  <xsl:if test="not(self::db:title) or (not(preceding-sibling::db:titleabbrev) and not(following-sibling::db:titleabbrev))">
    <h1 class="title"><xsl:value-of select="."/></h1>
  </xsl:if>
</xsl:template>

<xsl:template match="db:subtitle">
  <h1 class="subtitle"><xsl:value-of select="."/></h1>
</xsl:template>

<xsl:template match="db:author">
  <h3 class="author"><xsl:apply-templates select="db:personname|db:orgname"/></h3>
  <h4 class="email"><xsl:apply-templates select="db:email"/></h4>
  <xsl:if test="db:affiliation">
    <h4 class="affiliation"><xsl:value-of select="db:affiliation"/></h4>
  </xsl:if>
</xsl:template>

<xsl:template match="db:email">
  <a>
    <xsl:attribute name="href">
      <xsl:text>mailto:</xsl:text><xsl:value-of select="."/>
    </xsl:attribute>

    &lt;<xsl:value-of select="."/>&gt;
  </a>
</xsl:template>

<xsl:template name="list.content">
  <xsl:variable name="classValue">
    <xsl:call-template name="process.dbs.attributes">
      <xsl:with-param name="attributeSet" select="(ancestor-or-self::*/@dbs:incremental)[last()] | (ancestor-or-self::*/@dbs:collapsible)[last()] | self::*/@dbs:style"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:attribute name="class">
    <xsl:value-of select="$classValue"/>
  </xsl:attribute>

  <xsl:apply-templates select="*"/>
</xsl:template>

<xsl:template match="db:itemizedlist">
  <ul>
    <xsl:call-template name="list.content"/>
  </ul>
</xsl:template>

<xsl:template match="db:orderedlist">
  <ol>
    <xsl:call-template name="list.content"/>
  </ol>
</xsl:template>

<xsl:template match="db:mediaobject">
  <xsl:variable name="classValue">
    <xsl:call-template name="process.dbs.attributes">
      <xsl:with-param name="attributeSet" select="(ancestor-or-self::*/@dbs:incremental)[last()] | (ancestor-or-self::*/@dbs:collapsible)[last()] | self::*/@dbs:style"/>
    </xsl:call-template>
  </xsl:variable>

  <div class="{$classValue}">
    <xsl:apply-templates select="db:imageobject[1]"/>
  </div>
</xsl:template>

<xsl:template name="bibliography.titlepage"/>

<xsl:template match="db:bibliosource" mode="bibliography.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:choose>
      <xsl:when test="@xlink:href">
	<a href="{@xlink:href}">
    	  <xsl:apply-templates mode="bibliography.mode"/>
	</a>
      </xsl:when>

      <xsl:otherwise>
	<xsl:apply-templates mode="bibliomixed.mode"/>
      </xsl:otherwise>
    </xsl:choose>
  </span>
</xsl:template>

<xsl:template name="href.target.uri">
  <xsl:param name="object" select="."/>
  <xsl:variable name="ischunk">
    <xsl:call-template name="chunk">
      <xsl:with-param name="node" select="$object"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:if test="$ischunk='0'">
    <xsl:text>#</xsl:text>
    <xsl:call-template name="object.id">
      <xsl:with-param name="object" select="$object"/>
    </xsl:call-template>
  </xsl:if>
</xsl:template>

<xsl:template match="dbs:foil|dbs:foilgroup" mode="xref-to">
  <xsl:call-template name="gentext">
    <xsl:with-param name="key" select="'Foil'"/>
  </xsl:call-template>
  <xsl:call-template name="gentext.space"/>
  <xsl:value-of select="count(preceding::dbs:foil|preceding::dbs:foilgroup) + 1"/>
  <xsl:text>: </xsl:text>
  <xsl:call-template name="get.title"/>
</xsl:template>

<xsl:template name="extension.process.image.attributes">
  <xsl:variable name="classValue">
    <xsl:call-template name="process.dbs.attributes">
      <xsl:with-param name="attributeSet" select="(ancestor-or-self::*/@dbs:incremental)[last()] | (ancestor-or-self::*/@dbs:collapsible)[last()] | self::*/@dbs:style"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:if test="./@*[namespace-uri() = 'http://docbook.org/ns/docbook-slides']">
    <xsl:attribute name="class">
      <xsl:value-of select="$classValue"/>
    </xsl:attribute>
  </xsl:if>
</xsl:template>

<xsl:template match="*[namespace-uri() = 'http://www.w3.org/2000/svg']">
  <xsl:call-template name="handle.embedded">
    <xsl:with-param name="modeParam" select="$svg.embedding.mode"/>
    <xsl:with-param name="fileExt" select="'.svg'"/>
    <xsl:with-param name="mimeType" select="'image/svg+xml'"/>
    <xsl:with-param name="gentextKey" select="'SVGImage'"/>
  </xsl:call-template>
</xsl:template>

<xsl:template match="*[namespace-uri() = 'http://www.w3.org/1998/Math/MathML']">
  <xsl:call-template name="handle.embedded">
    <xsl:with-param name="modeParam" select="$mml.embedding.mode"/>
    <xsl:with-param name="fileExt" select="'.mml'"/>
    <xsl:with-param name="mimeType" select="'application/mathml-presentation+xml'"/>
    <xsl:with-param name="gentextKey" select="'MathMLFormula'"/>
  </xsl:call-template>
</xsl:template>

<xsl:template name="handle.embedded">
  <xsl:param name="modeParam">inline</xsl:param>
  <xsl:param name="fileExt"/>
  <xsl:param name="mimeType"/>
  <xsl:param name="gentextKey"/>

  <xsl:choose>
    <xsl:when test="$modeParam = 'inline'">
      <xsl:copy-of select="."/>
    </xsl:when>

    <xsl:otherwise>
      <xsl:variable name="id">
	<xsl:call-template name="object.id"/>
      </xsl:variable>
      <xsl:variable name="fname">
	<xsl:value-of select="concat($id, $fileExt)"/>
      </xsl:variable>

      <exsl:document href="{$fname}">
	<xsl:copy-of select="."/>
      </exsl:document>

      <xsl:choose>
        <xsl:when test="$modeParam = 'object'">
	  <object data="{$fname}" type="{$mimeType}"/>
        </xsl:when>

        <xsl:when test="$modeParam = 'image'">
	  <img alt="{$mimeType} object" src="{$fname}"/>
        </xsl:when>

        <xsl:when test="$modeParam = 'link'">
	  <a href="{$fname}">
	    <xsl:call-template name="gentext">
	      <xsl:with-param name="key" select="$gentextKey"/>
	    </xsl:call-template>
	  </a> 
        </xsl:when>

        <xsl:when test="$modeParam = 'iframe'">
	  <iframe src="{$fname}"/>
        </xsl:when>

        <xsl:when test="$modeParam = 'embed'">
	  <embed src="{$fname}" type="{$mimeType}" /> 
        </xsl:when>

	<xsl:otherwise>
	  <xsl:message terminate="yes">
	    Unknown processing mode <xsl:value-of select="$modeParam"/>.
	  </xsl:message>
	</xsl:otherwise>
      </xsl:choose>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template name="generate.anchor">
  <a>
    <xsl:attribute name="name">
      <xsl:call-template name="object.id"/>
    </xsl:attribute>
  </a>
</xsl:template>

<xsl:template name="slide.copyright">
  <div class="copyright">
    <xsl:call-template name="gentext">
      <xsl:with-param name="key" select="'Copyright'"/>
    </xsl:call-template>
    <xsl:call-template name="gentext.space"/>
    <xsl:text>&#xa9;</xsl:text>
    <xsl:call-template name="gentext.space"/>
    <xsl:value-of select="/dbs:slides/db:info/db:copyright/db:year"/>
    <xsl:call-template name="gentext.space"/>
    <xsl:value-of select="/dbs:slides/db:info/db:copyright/db:holder"/>
  </div>
</xsl:template>

<xsl:template name="slide.pubdate">
  <div class="pubdate">
    <xsl:call-template name="gentext">
      <xsl:with-param name="key" select="'Published'"/>
    </xsl:call-template>
    <xsl:text>: </xsl:text>
    <xsl:value-of select="/dbs:slides/db:info/db:pubdate"/>
  </div>
</xsl:template>

<xsl:template match="/" mode="slide.header.mode"/>

<xsl:template match="/" mode="slide.footer.mode">
  <xsl:if test="($generate.copyright != 0) and /dbs:slides/db:info/db:copyright">
    <xsl:call-template name="slide.copyright"/>
  </xsl:if>
  <xsl:if test="($generate.pubdate != 0) and /dbs:slides/db:info/db:pubdate">
    <xsl:call-template name="slide.pubdate"/>
  </xsl:if>
</xsl:template>
</xsl:stylesheet>
