<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fo="http://www.w3.org/1999/XSL/Format"
		xmlns:db="http://docbook.org/ns/docbook"
		xmlns:dbs="http://docbook.org/ns/docbook-slides"
		xmlns:exsl="http://exslt.org/common"
		exclude-result-prefixes="dbs db"
		extension-element-prefixes="exsl"
                version="1.0">

<xsl:import href="../../fo/docbook.xsl"/>
<xsl:import href="../common/common.xsl"/>
<xsl:include href="plain-titlepage.xsl"/>
<xsl:include href="param.xsl"/>

<xsl:output indent="yes"/>

<xsl:param name="local.l10n.xml" select="document('')"/>
<i18n xmlns="http://docbook.sourceforge.net/xmlns/l10n/1.0">
  <l:l10n xmlns:l="http://docbook.sourceforge.net/xmlns/l10n/1.0" language="en">
    <l:gentext key="Continued" text="(Continued)"/>
    <l:gentext key="Speakernotes" text="Speaker Notes"/>
    <l:gentext key="Handoutnotes" text="Handout Notes"/>
    <l:context name="title">
      <l:template name="slides" text="%t"/>
      <l:template name="foilgroup" text="%t"/>
      <l:template name="foil" text="%t"/>
    </l:context>
  </l:l10n>
</i18n>

<!-- Start of overrides -->

<xsl:param name="page.margin.top" select="'0.25in'"/>
<xsl:param name="page.margin.bottom" select="'0.25in'"/>
<xsl:param name="page.margin.inner" select="'0.25in'"/>
<xsl:param name="page.margin.outer" select="'0.25in'"/>
<xsl:param name="body.margin.top" select="'1in'"/>
<xsl:param name="body.margin.bottom" select="'0.5in'"/>
<xsl:param name="region.before.extent" select="'0.75in'"/>
<xsl:param name="region.after.extent" select="'0.5in'"/>
<xsl:param name="column.count.body" select="1"/>
<xsl:param name="body.font.size">20</xsl:param>

<xsl:param name="callout.icon.size" select="'40pt'"/>
<xsl:param name="alignment" select="'start'"/>
<xsl:param name="preferred.mediaobject.role" select="'print'"/>
<xsl:param name="page.orientation" select="'landscape'"/>

<xsl:variable name="root.elements" select="' slides '"/>

<xsl:attribute-set name="formal.title.properties"
                   use-attribute-sets="normal.para.spacing">
  <xsl:attribute name="font-weight">bold</xsl:attribute>
  <xsl:attribute name="font-size">
    <xsl:value-of select="$body.font.master * 1.2"/>
    <xsl:text>pt</xsl:text>
  </xsl:attribute>
  <xsl:attribute name="hyphenate">false</xsl:attribute>
  <xsl:attribute name="space-after.minimum">8pt</xsl:attribute>
  <xsl:attribute name="space-after.optimum">6pt</xsl:attribute>
  <xsl:attribute name="space-after.maximum">10pt</xsl:attribute>
</xsl:attribute-set>

<xsl:attribute-set name="list.block.spacing">
  <xsl:attribute name="space-before.optimum">12pt</xsl:attribute>
  <xsl:attribute name="space-before.minimum">8pt</xsl:attribute>
  <xsl:attribute name="space-before.maximum">14pt</xsl:attribute>
  <xsl:attribute name="space-after.optimum">0pt</xsl:attribute>
  <xsl:attribute name="space-after.minimum">0pt</xsl:attribute>
  <xsl:attribute name="space-after.maximum">0pt</xsl:attribute>
</xsl:attribute-set>

<xsl:attribute-set name="list.item.spacing">
  <xsl:attribute name="space-before.optimum">6pt</xsl:attribute>
  <xsl:attribute name="space-before.minimum">4pt</xsl:attribute>
  <xsl:attribute name="space-before.maximum">8pt</xsl:attribute>
</xsl:attribute-set>

<xsl:attribute-set name="normal.para.spacing">
  <xsl:attribute name="space-before.optimum">8pt</xsl:attribute>
  <xsl:attribute name="space-before.minimum">6pt</xsl:attribute>
  <xsl:attribute name="space-before.maximum">10pt</xsl:attribute>
</xsl:attribute-set>

<xsl:attribute-set name="orderedlist.properties">
  <xsl:attribute name="font-size">
    <xsl:value-of select="$body.font.size"/>
  </xsl:attribute>
</xsl:attribute-set>

<xsl:attribute-set name="footnote.properties">
  <xsl:attribute name="font-size">
    <xsl:value-of select="$body.font.size * 0.8"/>
  </xsl:attribute>
</xsl:attribute-set>

<xsl:attribute-set name="slides.titlepage.recto.style">
  <xsl:attribute name="font-family">
    <xsl:value-of select="$slide.font.family"/>
  </xsl:attribute>
</xsl:attribute-set>

<xsl:attribute-set name="slides.titlepage.verso.style">
  <xsl:attribute name="font-family">
    <xsl:value-of select="$slide.font.family"/>
  </xsl:attribute>
</xsl:attribute-set>

<xsl:template name="bibliography.titlepage"/>

<!-- Do not add db namespace to dbs elements -->
<xsl:template match="*[namespace-uri() = 'http://docbook.org/ns/docbook-slides']" mode="addNS">
  <xsl:copy-of select="."/>
</xsl:template>

<!-- End of overrides -->

<xsl:template name="user.pagemasters">
  <fo:simple-page-master master-name="slides-titlepage-master"
			 xsl:use-attribute-sets="slides.titlepage.master.properties">
    <fo:region-body xsl:use-attribute-sets="slides.titlepage.region-body.properties"/>
  </fo:simple-page-master>

  <fo:simple-page-master master-name="slides-foil-master"
			 xsl:use-attribute-sets="foil.master.properties">
    <fo:region-body xsl:use-attribute-sets="foil.region-body.properties"/>
    <fo:region-before region-name="xsl-region-before-foil" xsl:use-attribute-sets="foil.region-before.properties"/>
    <fo:region-after region-name="xsl-region-after-foil" xsl:use-attribute-sets="foil.region-after.properties"/>
  </fo:simple-page-master>

  <fo:simple-page-master master-name="slides-foil-continued-master"
			 xsl:use-attribute-sets="foil.master.properties">
    <fo:region-body xsl:use-attribute-sets="foil.region-body.properties"/>
    <fo:region-before region-name="xsl-region-before-foil-continued" xsl:use-attribute-sets="foil.region-before.properties"/>
    <fo:region-after region-name="xsl-region-after-foil-continued" xsl:use-attribute-sets="foil.region-after.properties"/>
  </fo:simple-page-master>

  <fo:page-sequence-master master-name="slides-titlepage">
    <fo:repeatable-page-master-alternatives>
      <fo:conditional-page-master-reference master-reference="slides-titlepage-master"/>
    </fo:repeatable-page-master-alternatives>
  </fo:page-sequence-master>

  <fo:page-sequence-master master-name="slides-foil">
    <fo:repeatable-page-master-alternatives>
      <fo:conditional-page-master-reference master-reference="slides-foil-master"
                                            page-position="first"/>
      <fo:conditional-page-master-reference master-reference="slides-foil-continued-master"/>
    </fo:repeatable-page-master-alternatives>
  </fo:page-sequence-master>
</xsl:template>

<xsl:template name="presentation.title">
  <xsl:call-template name="get.title">
    <xsl:with-param name="ctx" select="/dbs:slides"/>
  </xsl:call-template>
</xsl:template>

<xsl:template name="slides.bookmarks">
  <fo:bookmark-tree>
    <xsl:apply-templates select="/dbs:slides/dbs:foil|/dbs:slides/dbs:foilgroup" mode="bookmark.mode"/>
  </fo:bookmark-tree>
</xsl:template>

<xsl:template match="dbs:foil|dbs:foilgroup" mode="bookmark.mode">
  <fo:bookmark>
    <xsl:attribute name="internal-destination">
      <xsl:call-template name="object.id"/>
    </xsl:attribute>

    <fo:bookmark-title>
      <xsl:call-template name="get.title"/>
    </fo:bookmark-title>

    <xsl:if test="self::dbs:foilgroup">
      <xsl:apply-templates select="dbs:foil" mode="bookmark.mode"/>
    </xsl:if>
  </fo:bookmark>
</xsl:template>

<xsl:template match="db:author" mode="titlepage.mode">
  <fo:block>
    <xsl:apply-templates select="db:personname" mode="titlepage.mode"/>
  </fo:block>

  <fo:block>
    <xsl:apply-templates select="db:affiliation" mode="titlepage.mode"/>
  </fo:block>

  <fo:block>
    <xsl:apply-templates select="db:email" mode="titlepage.mode"/>
  </fo:block>
</xsl:template>

<xsl:template match="/">
  <fo:root xsl:use-attribute-sets="slides.properties">
    <fo:layout-master-set>
      <xsl:call-template name="user.pagemasters"/>
    </fo:layout-master-set>

    <xsl:call-template name="slides.bookmarks"/>

    <xsl:if test="$generate.titlepage != 0">
      <fo:page-sequence hyphenate="{$hyphenate}"
			master-reference="slides-titlepage">
        <xsl:attribute name="language">
	  <xsl:call-template name="l10n.language"/>
        </xsl:attribute>

        <fo:flow flow-name="xsl-region-body">
	  <fo:block>
	    <xsl:apply-templates select="/dbs:slides" mode="titlepage"/>
	  </fo:block>
        </fo:flow>
      </fo:page-sequence>
    </xsl:if>

    <xsl:apply-templates select="/dbs:slides/dbs:foil|/dbs:slides/dbs:foilgroup"/>
  </fo:root>
</xsl:template>

<xsl:template match="dbs:slides" mode="titlepage">
  <xsl:call-template name="slides.titlepage"/>
</xsl:template>

<xsl:template name="page.template">
  <xsl:param name="mode" select="'normal'"/>

  <xsl:param name="title">
    <xsl:call-template name="get.title"/>
  </xsl:param>

  <xsl:param name="subtitle">
    <xsl:call-template name="get.subtitle"/>
  </xsl:param>

  <fo:page-sequence master-reference="slides-foil" xsl:use-attribute-sets="foil.page-sequence.properties">
    <xsl:attribute name="language">
      <xsl:call-template name="l10n.language"/>
    </xsl:attribute>

    <xsl:attribute name="id">
      <xsl:call-template name="object.id"/>
    </xsl:attribute>

    <fo:static-content flow-name="xsl-region-before-foil">
      <fo:block xsl:use-attribute-sets="foil.header.properties">
        <fo:block xsl:use-attribute-sets="foil.title.properties">
          <xsl:value-of select="$title"/>
        </fo:block>

        <fo:block xsl:use-attribute-sets="foil.subtitle.properties">
          <xsl:value-of select="$subtitle"/>
       </fo:block>
      </fo:block>
    </fo:static-content>

    <fo:static-content flow-name="xsl-region-before-foil-continued">
      <fo:block xsl:use-attribute-sets="foil.header.properties">
        <fo:block xsl:use-attribute-sets="foil.title.properties">
          <xsl:value-of select="$title"/>
          <xsl:text> </xsl:text>
          <xsl:call-template name="gentext">
            <xsl:with-param name="key" select="'Continued'"/>
          </xsl:call-template>
	</fo:block>
      </fo:block>
    </fo:static-content>

    <fo:static-content flow-name="xsl-region-after-foil">
      <xsl:call-template name="generate.footer"/>
    </fo:static-content>

    <fo:static-content flow-name="xsl-region-after-foil-continued">
      <xsl:call-template name="generate.footer"/>
    </fo:static-content>

    <fo:flow flow-name="xsl-region-body">
      <fo:block xsl:use-attribute-sets="foil.properties">
	<xsl:choose>
	  <xsl:when test="$mode = 'normal'">
	    <xsl:apply-templates select="*[not(self::dbs:foil)][not(self::db:info)][not(self::db:title)][not(self::db:titleabbrev)][not(self::db:subtitle)][not(self::dbs:speakernotes)][not(self::dbs:handoutnotes)]"/>

	    <xsl:if test="self::dbs:foilgroup and ($generate.foilgroup.toc != 0)">
	      <xsl:call-template name="foilgroup.generate.toc"/>
	    </xsl:if>
	  </xsl:when>

	  <xsl:when test="$mode = 'speakernotes'">
	    <xsl:apply-templates select="dbs:speakernotes"/>
	  </xsl:when>

	  <xsl:when test="$mode = 'handoutnotes'">
	    <xsl:apply-templates select="dbs:handoutnotes"/>
	  </xsl:when>
	</xsl:choose>
      </fo:block>
    </fo:flow>
  </fo:page-sequence>
</xsl:template>

<xsl:template match="dbs:foil|dbs:foilgroup">
  <xsl:call-template name="page.template"/>

  <xsl:call-template name="generate.slide.notes"/>

  <xsl:if test="self::dbs:foilgroup">
    <xsl:apply-templates select="dbs:foil"/>
  </xsl:if>
</xsl:template>

<xsl:template name="generate.slide.notes">
  <xsl:variable name="subtitle.handoutnotes">
    <xsl:call-template name="gentext">
      <xsl:with-param name="key" select="'Handoutnotes'"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:variable name="subtitle.speakernotes">
    <xsl:call-template name="gentext">
      <xsl:with-param name="key" select="'Speakernotes'"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:if test="($generate.handoutnotes != 0) and ./dbs:handoutnotes">
    <xsl:call-template name="page.template">
      <xsl:with-param name="mode" select="'handoutnotes'"/>
      <xsl:with-param name="subtitle" select="$subtitle.handoutnotes"/>
    </xsl:call-template>
  </xsl:if>

  <xsl:if test="($generate.speakernotes != 0) and ./dbs:speakernotes">
    <xsl:call-template name="page.template">
      <xsl:with-param name="mode" select="'speakernotes'"/>
      <xsl:with-param name="subtitle" select="$subtitle.speakernotes"/>
    </xsl:call-template>
  </xsl:if>
</xsl:template>

<xsl:template match="dbs:handoutnotes">
  <fo:block xsl:use-attribute-sets="handoutnotes.properties">
    <xsl:apply-templates/>
  </fo:block>
</xsl:template>

<xsl:template match="dbs:speakernotes">
  <fo:block xsl:use-attribute-sets="speakernotes.properties">
    <xsl:apply-templates/>
  </fo:block>
</xsl:template>

<xsl:template match="dbs:block">
  <xsl:apply-templates/>
</xsl:template>

<xsl:template name="generate.footer">
<fo:block xsl:use-attribute-sets="foil.footer.properties">
  <fo:table>
    <fo:table-column column-number="1" column-width="33%"/>
    <fo:table-column column-number="2" column-width="34%"/>
    <fo:table-column column-number="3" column-width="33%"/>

    <fo:table-body>
      <fo:table-row height="14pt">
	<fo:table-cell text-align="left">
	  <xsl:call-template name="footer.left"/>
	</fo:table-cell>

	<fo:table-cell text-align="center">
	  <xsl:call-template name="footer.center"/>
	</fo:table-cell>

	<fo:table-cell text-align="right">
	  <xsl:call-template name="footer.right"/>
	</fo:table-cell>
      </fo:table-row>
    </fo:table-body>
  </fo:table>
</fo:block>
</xsl:template>

<xsl:template name="footer.left">
  <fo:block/>
</xsl:template>

<xsl:template name="footer.center">
  <xsl:if test="($generate.copyright != 0) and /dbs:slides/db:info/db:copyright">
    <fo:block>
      <xsl:call-template name="gentext">
	<xsl:with-param name="key" select="'Copyright'"/>
      </xsl:call-template>
      <xsl:call-template name="gentext.space"/>
      <xsl:text>&#xa9;</xsl:text>
      <xsl:call-template name="gentext.space"/>
      <xsl:value-of select="/dbs:slides/db:info/db:copyright/db:year"/>
      <xsl:call-template name="gentext.space"/>
      <xsl:value-of select="/dbs:slides/db:info/db:copyright/db:holder"/>
    </fo:block>
  </xsl:if>

  <xsl:if test="($generate.pubdate != 0) and /dbs:slides/db:info/db:pubdate">
    <xsl:call-template name="slide.pubdate"/>
  </xsl:if>
</xsl:template>

<xsl:template name="footer.right">
  <fo:block>
    <xsl:if test="$generate.page.number != 'no'">
      <fo:page-number/>
    </xsl:if>

    <xsl:if test="$generate.page.number = 'full'">
      <xsl:text>&#160;/&#160;</xsl:text>
      <fo:page-number-citation>
	<xsl:attribute name="ref-id">
	  <xsl:call-template name="object.id">
	    <xsl:with-param name="object" select="(//dbs:foilgroup|//dbs:foil)[last()]"/>
	  </xsl:call-template>
	</xsl:attribute>
      </fo:page-number-citation>
    </xsl:if>
  </fo:block>
</xsl:template>

<xsl:template name="slide.pubdate">
  <fo:block>
    <xsl:call-template name="gentext">
      <xsl:with-param name="key" select="'Published'"/>
    </xsl:call-template>
    <xsl:text>: </xsl:text>
    <xsl:value-of select="/dbs:slides/db:info/db:pubdate"/>
  </fo:block>
</xsl:template>

<xsl:template name="foilgroup.generate.toc">
  <xsl:choose>
    <xsl:when test="$generate.foilgroup.numbered.toc != 0">
      <fo:list-block xsl:use-attribute-sets="list.block.spacing orderedlist.properties">
	<xsl:for-each select="./dbs:foil">
	  <fo:list-item xsl:use-attribute-sets="list.item.spacing">
	    <fo:list-item-label end-indent="label-end()" xsl:use-attribute-sets="orderedlist.label.properties">
	      <fo:block>
		<xsl:value-of select="position()"/>
	      </fo:block>
	    </fo:list-item-label>

	    <fo:list-item-body start-indent="body-start()">
	      <fo:block>
		<xsl:call-template name="get.title"/>
	      </fo:block>
	    </fo:list-item-body>
	  </fo:list-item>
	</xsl:for-each>
      </fo:list-block>
    </xsl:when>

    <xsl:otherwise>
      <fo:list-block xsl:use-attribute-sets="list.block.spacing itemizedlist.properties">
        <xsl:for-each select="./dbs:foil">
          <fo:list-item xsl:use-attribute-sets="list.item.spacing">
            <fo:list-item-label end-indent="label-end()" xsl:use-attribute-sets="itemizedlist.label.properties">
              <fo:block>
		<xsl:call-template name="itemizedlist.label.markup">
		  <xsl:with-param name="itemsymbol">
		    <xsl:call-template name="list.itemsymbol"/>
		  </xsl:with-param>
		</xsl:call-template>
              </fo:block>
            </fo:list-item-label>

            <fo:list-item-body start-indent="body-start()">
              <fo:block>
                <xsl:call-template name="get.title"/>
              </fo:block>
            </fo:list-item-body>
          </fo:list-item>
        </xsl:for-each>
      </fo:list-block>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="*[namespace-uri() = 'http://www.w3.org/2000/svg']">
  <xsl:call-template name="handle.embedded">
    <xsl:with-param name="modeParam" select="$svg.embedding.mode"/>
    <xsl:with-param name="fileExt" select="'.svg'"/>
  </xsl:call-template>
</xsl:template>

<xsl:template match="*[namespace-uri() = 'http://www.w3.org/1998/Math/MathML']">
  <xsl:call-template name="handle.embedded">
    <xsl:with-param name="modeParam" select="$mml.embedding.mode"/>
    <xsl:with-param name="fileExt" select="'.mml'"/>
  </xsl:call-template>
</xsl:template>

<xsl:template name="handle.embedded">
  <xsl:param name="modeParam">inline</xsl:param>
  <xsl:param name="fileExt"/>

  <xsl:choose>
    <xsl:when test="$modeParam = 'inline'">
      <xsl:copy-of select="."/>
    </xsl:when>

    <xsl:when test="$modeParam = 'instream-foreign-object'">
      <fo:instream-foreign-object>
	<xsl:copy-of select="."/>
      </fo:instream-foreign-object>
    </xsl:when>

    <xsl:otherwise>
      <xsl:variable name="id">
        <xsl:call-template name="object.id"/>
      </xsl:variable>
      <xsl:variable name="fname">
        <xsl:value-of select="concat($id, $fileExt)"/>
      </xsl:variable>
      <xsl:variable name="prefix">url('</xsl:variable>
      <xsl:variable name="suffix">')</xsl:variable>
      <xsl:variable name="file.uri">
	<xsl:value-of select="concat($prefix, $fname, $suffix)"/>
      </xsl:variable>

      <exsl:document href="{$fname}">
        <xsl:copy-of select="."/>

        <xsl:fallback>
          <xsl:message terminate="yes">
            Your XSLT processor does not support exsl:document.
            You can only use inline SVG images.
          </xsl:message>
        </xsl:fallback>
      </exsl:document>

      <xsl:choose>
        <xsl:when test="$modeParam = 'external-graphic'">
	  <fo:external-graphic src="{$file.uri}"/>
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

<xsl:template match="dbs:foil|dbs:foilgroup" mode="xref-to">
  <xsl:param name="referrer"/>
  <xsl:param name="xrefstyle"/>

  <xsl:apply-templates select="." mode="object.xref.markup">
    <xsl:with-param name="purpose" select="'xref'"/>
    <xsl:with-param name="xrefstyle" select="$xrefstyle"/>
    <xsl:with-param name="referrer" select="$referrer"/>
  </xsl:apply-templates>
</xsl:template>

</xsl:stylesheet>
