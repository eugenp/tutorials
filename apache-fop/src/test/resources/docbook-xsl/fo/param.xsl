<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

<!-- This file is generated from param.xweb -->

<!-- ********************************************************************
     $Id: param.xweb 9673 2012-12-02 20:06:41Z bobstayton $
     ********************************************************************

     This file is part of the XSL DocBook Stylesheet distribution.
     See ../README or http://docbook.sf.net/release/xsl/current/ for
     copyright and other information.

     ******************************************************************** -->

<xsl:attribute-set name="abstract.properties">
  <xsl:attribute name="start-indent">0.0in</xsl:attribute>
  <xsl:attribute name="end-indent">0.0in</xsl:attribute>
</xsl:attribute-set>
<xsl:attribute-set name="abstract.title.properties">
  <xsl:attribute name="font-family"><xsl:value-of select="$title.fontset"/></xsl:attribute>
  <xsl:attribute name="font-weight">bold</xsl:attribute>
  <xsl:attribute name="keep-with-next.within-column">always</xsl:attribute>
  <xsl:attribute name="keep-with-next.within-column">always</xsl:attribute>
  <xsl:attribute name="space-before.optimum"><xsl:value-of select="concat($body.font.master, 'pt')"/></xsl:attribute>
  <xsl:attribute name="space-before.minimum"><xsl:value-of select="concat($body.font.master, 'pt * 0.8')"/></xsl:attribute>
  <xsl:attribute name="space-before.maximum"><xsl:value-of select="concat($body.font.master, 'pt * 1.2')"/></xsl:attribute>
  <xsl:attribute name="hyphenate">false</xsl:attribute>
  <xsl:attribute name="text-align">center</xsl:attribute>
</xsl:attribute-set>
<xsl:param name="activate.external.olinks" select="1"/>
<xsl:param name="admon.graphics.extension">.png</xsl:param>
<xsl:param name="admon.graphics" select="0"/>
<xsl:param name="admon.graphics.path">images/</xsl:param>
<xsl:param name="admon.textlabel" select="1"/>
<xsl:attribute-set name="admonition.properties"/>
<xsl:attribute-set name="admonition.title.properties">
  <xsl:attribute name="font-size">14pt</xsl:attribute>
  <xsl:attribute name="font-weight">bold</xsl:attribute>
  <xsl:attribute name="hyphenate">false</xsl:attribute>
  <xsl:attribute name="keep-with-next.within-column">always</xsl:attribute>
</xsl:attribute-set>
<xsl:param name="base.dir"/>
<xsl:attribute-set name="graphical.admonition.properties">
  <xsl:attribute name="space-before.optimum">1em</xsl:attribute>
  <xsl:attribute name="space-before.minimum">0.8em</xsl:attribute>
  <xsl:attribute name="space-before.maximum">1.2em</xsl:attribute>
  <xsl:attribute name="space-after.optimum">1em</xsl:attribute>
  <xsl:attribute name="space-after.minimum">0.8em</xsl:attribute>
  <xsl:attribute name="space-after.maximum">1.2em</xsl:attribute>
</xsl:attribute-set>
<xsl:attribute-set name="nongraphical.admonition.properties">
  <xsl:attribute name="space-before.minimum">0.8em</xsl:attribute>
  <xsl:attribute name="space-before.optimum">1em</xsl:attribute>
  <xsl:attribute name="space-before.maximum">1.2em</xsl:attribute>
  <xsl:attribute name="margin-{$direction.align.start}">0.25in</xsl:attribute>
  <xsl:attribute name="margin-{$direction.align.end}">0.25in</xsl:attribute>
</xsl:attribute-set>
<xsl:param name="alignment">justify</xsl:param>
<xsl:param name="appendix.autolabel">A</xsl:param>
<xsl:param name="arbortext.extensions" select="0"/>
<xsl:attribute-set name="article.appendix.title.properties" use-attribute-sets="section.title.properties                          section.title.level1.properties">
</xsl:attribute-set>
<xsl:param name="author.othername.in.middle" select="1"/>
<xsl:param name="autotoc.label.separator">. </xsl:param>
<xsl:param name="axf.extensions" select="0"/>
<xsl:param name="biblioentry.item.separator">. </xsl:param>
<xsl:attribute-set name="biblioentry.properties" use-attribute-sets="normal.para.spacing">
  <xsl:attribute name="start-indent">0.5in</xsl:attribute>
  <xsl:attribute name="text-indent">-0.5in</xsl:attribute>
</xsl:attribute-set>
<xsl:param name="bibliography.collection">http://docbook.sourceforge.net/release/bibliography/bibliography.xml</xsl:param>

<xsl:param name="bibliography.numbered" select="0"/>
<xsl:param name="bibliography.style">normal</xsl:param>
<xsl:attribute-set name="blockquote.properties">
<xsl:attribute name="margin-{$direction.align.start}">0.5in</xsl:attribute>
<xsl:attribute name="margin-{$direction.align.end}">0.5in</xsl:attribute>
<xsl:attribute name="space-after.minimum">0.5em</xsl:attribute>
<xsl:attribute name="space-after.optimum">1em</xsl:attribute>
<xsl:attribute name="space-after.maximum">2em</xsl:attribute>
</xsl:attribute-set>
<xsl:param name="body.font.family">serif</xsl:param>
<xsl:param name="body.font.master">10</xsl:param>
<xsl:param name="body.font.size">
 <xsl:value-of select="$body.font.master"/><xsl:text>pt</xsl:text>
</xsl:param>
<xsl:param name="body.margin.bottom">0.5in</xsl:param>
<xsl:param name="body.margin.top">0.5in</xsl:param>
<xsl:param name="body.start.indent">
  <xsl:choose>
    <xsl:when test="$fop.extensions != 0">0pt</xsl:when>
    <xsl:when test="$passivetex.extensions != 0">0pt</xsl:when>
    <xsl:otherwise>4pc</xsl:otherwise>
  </xsl:choose>
</xsl:param>
<xsl:param name="body.end.indent">0pt</xsl:param>
<xsl:param name="bookmarks.collapse" select="1"/>
<xsl:param name="bridgehead.in.toc" select="0"/>
<xsl:attribute-set name="calloutlist.properties">
  <xsl:attribute name="space-before.optimum">1em</xsl:attribute>
  <xsl:attribute name="space-before.minimum">0.8em</xsl:attribute>
  <xsl:attribute name="space-before.maximum">1.2em</xsl:attribute>
  <xsl:attribute name="provisional-distance-between-starts">2.2em</xsl:attribute>
  <xsl:attribute name="provisional-label-separation">0.2em</xsl:attribute>
</xsl:attribute-set>
<xsl:attribute-set name="callout.properties">
</xsl:attribute-set>
<xsl:param name="callout.defaultcolumn">60</xsl:param>

<xsl:param name="callout.graphics.extension">.svg</xsl:param>
<xsl:param name="callout.graphics" select="1"/>
<xsl:param name="callout.icon.size">7pt</xsl:param>

<xsl:param name="callout.graphics.number.limit">30</xsl:param>
<xsl:param name="callout.graphics.path">images/callouts/</xsl:param>
<xsl:param name="callout.unicode.font">ZapfDingbats</xsl:param>
<xsl:param name="callout.unicode" select="0"/>
<xsl:param name="callout.unicode.number.limit">10</xsl:param>
<xsl:param name="callout.unicode.start.character">10102</xsl:param>
<xsl:param name="callouts.extension" select="1"/>
<xsl:param name="chapter.autolabel" select="1"/>
<xsl:param name="chunk.quietly" select="0"/>
<xsl:param name="collect.xref.targets">no</xsl:param>
<xsl:param name="column.count.back" select="1"/>
<xsl:param name="column.count.body" select="1"/>
<xsl:param name="column.count.front" select="1"/>
<xsl:param name="column.count.index">2</xsl:param>
<xsl:param name="column.count.lot" select="1"/>
<xsl:param name="column.count.titlepage" select="1"/>
<xsl:param name="column.gap.back">12pt</xsl:param>
<xsl:param name="column.gap.body">12pt</xsl:param>
<xsl:param name="column.gap.front">12pt</xsl:param>
<xsl:param name="column.gap.index">12pt</xsl:param>
<xsl:param name="column.gap.lot">12pt</xsl:param>
<xsl:param name="column.gap.titlepage">12pt</xsl:param>
<xsl:attribute-set name="compact.list.item.spacing">
  <xsl:attribute name="space-before.optimum">0em</xsl:attribute>
  <xsl:attribute name="space-before.minimum">0em</xsl:attribute>
  <xsl:attribute name="space-before.maximum">0.2em</xsl:attribute>
</xsl:attribute-set>
<xsl:param name="component.label.includes.part.label" select="0"/>
<xsl:attribute-set name="component.title.properties">
  <xsl:attribute name="keep-with-next.within-column">always</xsl:attribute>
  <xsl:attribute name="space-before.optimum"><xsl:value-of select="concat($body.font.master, 'pt')"/></xsl:attribute>
  <xsl:attribute name="space-before.minimum"><xsl:value-of select="concat($body.font.master, 'pt * 0.8')"/></xsl:attribute>
  <xsl:attribute name="space-before.maximum"><xsl:value-of select="concat($body.font.master, 'pt * 1.2')"/></xsl:attribute>
  <xsl:attribute name="hyphenate">false</xsl:attribute>
  <xsl:attribute name="text-align">
    <xsl:choose>
      <xsl:when test="((parent::article | parent::articleinfo | parent::info/parent::article) and not(ancestor::book) and not(self::bibliography))         or (parent::slides | parent::slidesinfo)">center</xsl:when>
      <xsl:otherwise>start</xsl:otherwise>
    </xsl:choose>
  </xsl:attribute>
  <xsl:attribute name="start-indent"><xsl:value-of select="$title.margin.left"/></xsl:attribute>
</xsl:attribute-set>
<xsl:attribute-set name="component.titlepage.properties">
</xsl:attribute-set>
<xsl:param name="crop.marks" select="0"/>
<xsl:param name="crop.mark.width">0.5pt</xsl:param>
<xsl:param name="crop.mark.offset">24pt</xsl:param>
<xsl:param name="crop.mark.bleed">6pt</xsl:param>
<xsl:param name="current.docid"/>
<xsl:param name="default.float.class">
  <xsl:choose>
    <xsl:when test="contains($stylesheet.result.type,'html')">left</xsl:when>
    <xsl:otherwise>before</xsl:otherwise>
  </xsl:choose>
</xsl:param>
<xsl:param name="default.image.width"/>
<xsl:param name="default.table.width"/>
<xsl:param name="default.table.frame">all</xsl:param>
<xsl:param name="default.table.rules">none</xsl:param>
<xsl:param name="default.units">pt</xsl:param>
<xsl:param name="dingbat.font.family">serif</xsl:param>
<xsl:param name="double.sided" select="0"/>
<xsl:param name="draft.mode">no</xsl:param>
<xsl:param name="draft.watermark.image">images/draft.png</xsl:param>

<xsl:param name="ebnf.assignment">
  <fo:inline xmlns:fo="http://www.w3.org/1999/XSL/Format" font-family="{$monospace.font.family}">
    <xsl:text>::=</xsl:text>
  </fo:inline>
</xsl:param>

<xsl:param name="ebnf.statement.terminator"/>
<xsl:param name="email.delimiters.enabled" select="1"/>
<xsl:param name="email.mailto.enabled" select="0"/>
<xsl:attribute-set name="equation.properties" use-attribute-sets="formal.object.properties"/>
<xsl:attribute-set name="equation.number.properties">
  <xsl:attribute name="text-align">end</xsl:attribute>
  <xsl:attribute name="display-align">center</xsl:attribute>
</xsl:attribute-set>
<xsl:attribute-set name="example.properties" use-attribute-sets="formal.object.properties">
    <xsl:attribute name="keep-together.within-column">auto</xsl:attribute>
</xsl:attribute-set>
<xsl:param name="exsl.node.set.available"> 
  <xsl:choose>
    <xsl:when xmlns:exsl="http://exslt.org/common" exsl:foo="" test="function-available('exsl:node-set') or                        contains(system-property('xsl:vendor'),                          'Apache Software Foundation')">1</xsl:when>
    <xsl:otherwise>0</xsl:otherwise>
  </xsl:choose>
</xsl:param>
<xsl:attribute-set name="figure.properties" use-attribute-sets="formal.object.properties"/>
<xsl:param name="firstterm.only.link" select="0"/>
<xsl:attribute-set name="footer.content.properties">
  <xsl:attribute name="font-family">
    <xsl:value-of select="$body.fontset"/>
  </xsl:attribute>
  <xsl:attribute name="margin-left">
    <xsl:value-of select="$title.margin.left"/>
  </xsl:attribute>
</xsl:attribute-set>
<xsl:param name="footer.rule" select="1"/>
<xsl:param name="footer.column.widths">1 1 1</xsl:param>
<xsl:param name="footer.table.height">14pt</xsl:param>
<xsl:attribute-set name="footer.table.properties">
  <xsl:attribute name="table-layout">fixed</xsl:attribute>
  <xsl:attribute name="width">100%</xsl:attribute>
</xsl:attribute-set>
<xsl:param name="footers.on.blank.pages" select="1"/>
<xsl:param name="footnote.font.size">
 <xsl:value-of select="$body.font.master * 0.8"/><xsl:text>pt</xsl:text>
</xsl:param>
<xsl:param name="footnote.number.format">1</xsl:param>
<xsl:param name="footnote.number.symbols"/>
<xsl:attribute-set name="footnote.mark.properties">
  <xsl:attribute name="font-family"><xsl:value-of select="$body.fontset"/></xsl:attribute>
  <xsl:attribute name="font-size">75%</xsl:attribute>
  <xsl:attribute name="font-weight">normal</xsl:attribute>
  <xsl:attribute name="font-style">normal</xsl:attribute>
</xsl:attribute-set>
<xsl:attribute-set name="footnote.properties">
  <xsl:attribute name="font-family"><xsl:value-of select="$body.fontset"/></xsl:attribute>
  <xsl:attribute name="font-size"><xsl:value-of select="$footnote.font.size"/></xsl:attribute>
  <xsl:attribute name="font-weight">normal</xsl:attribute>
  <xsl:attribute name="font-style">normal</xsl:attribute>
  <xsl:attribute name="text-align"><xsl:value-of select="$alignment"/></xsl:attribute>
  <xsl:attribute name="start-indent">0pt</xsl:attribute>
  <xsl:attribute name="end-indent">0pt</xsl:attribute>
  <xsl:attribute name="text-indent">0pt</xsl:attribute>
  <xsl:attribute name="hyphenate"><xsl:value-of select="$hyphenate"/></xsl:attribute>
  <xsl:attribute name="wrap-option">wrap</xsl:attribute>
  <xsl:attribute name="linefeed-treatment">treat-as-space</xsl:attribute>
</xsl:attribute-set>
<xsl:attribute-set name="footnote.sep.leader.properties">
  <xsl:attribute name="color">black</xsl:attribute>
  <xsl:attribute name="leader-pattern">rule</xsl:attribute>
  <xsl:attribute name="leader-length">1in</xsl:attribute>
</xsl:attribute-set>
<xsl:param name="fop.extensions" select="0"/>
<xsl:param name="fop1.extensions" select="0"/>
<xsl:param name="force.blank.pages" select="1"/>
<xsl:attribute-set name="formal.object.properties">
  <xsl:attribute name="space-before.minimum">0.5em</xsl:attribute>
  <xsl:attribute name="space-before.optimum">1em</xsl:attribute>
  <xsl:attribute name="space-before.maximum">2em</xsl:attribute>
  <xsl:attribute name="space-after.minimum">0.5em</xsl:attribute>
  <xsl:attribute name="space-after.optimum">1em</xsl:attribute>
  <xsl:attribute name="space-after.maximum">2em</xsl:attribute>
  <xsl:attribute name="keep-together.within-column">always</xsl:attribute>
</xsl:attribute-set>
<xsl:param name="formal.procedures" select="1"/>
<xsl:param name="formal.title.placement">
figure before
example before
equation before
table before
procedure before
task before
</xsl:param>
<xsl:attribute-set name="formal.title.properties" use-attribute-sets="normal.para.spacing">
  <xsl:attribute name="font-weight">bold</xsl:attribute>
  <xsl:attribute name="font-size">
    <xsl:value-of select="$body.font.master * 1.2"/>
    <xsl:text>pt</xsl:text>
  </xsl:attribute>
  <xsl:attribute name="hyphenate">false</xsl:attribute>
  <xsl:attribute name="space-after.minimum">0.4em</xsl:attribute>
  <xsl:attribute name="space-after.optimum">0.6em</xsl:attribute>
  <xsl:attribute name="space-after.maximum">0.8em</xsl:attribute>
</xsl:attribute-set>
<xsl:param name="funcsynopsis.decoration" select="1"/>
<xsl:param name="funcsynopsis.style">kr</xsl:param>
<xsl:param name="function.parens" select="0"/>
<xsl:param name="generate.consistent.ids" select="0"/>
<xsl:param name="generate.index" select="1"/>
<xsl:param name="generate.section.toc.level" select="0"/>

<xsl:param name="generate.toc">
/appendix toc,title
article/appendix  nop
/article  toc,title
book      toc,title,figure,table,example,equation
/chapter  toc,title
part      toc,title
/preface  toc,title
reference toc,title
/sect1    toc
/sect2    toc
/sect3    toc
/sect4    toc
/sect5    toc
/section  toc
set       toc,title
</xsl:param>
<xsl:param name="glossary.as.blocks" select="0"/>
<xsl:param name="glossary.collection"/>
<xsl:param name="glossary.sort" select="0"/>
<xsl:param name="glossentry.show.acronym">no</xsl:param>
<xsl:param name="glosslist.as.blocks" select="0"/>
<xsl:param name="glossterm.auto.link" select="0"/>
<xsl:param name="glossterm.separation">0.25in</xsl:param>
<xsl:param name="glossterm.width">2in</xsl:param>
<xsl:attribute-set name="glossentry.list.item.properties">
  <xsl:attribute name="space-before.optimum">1em</xsl:attribute>
  <xsl:attribute name="space-before.minimum">0.8em</xsl:attribute>
  <xsl:attribute name="space-before.maximum">1.2em</xsl:attribute>
</xsl:attribute-set>
<xsl:attribute-set name="glossterm.list.properties">
</xsl:attribute-set>
<xsl:attribute-set name="glossterm.block.properties">
  <xsl:attribute name="space-before.optimum">1em</xsl:attribute>
  <xsl:attribute name="space-before.minimum">0.8em</xsl:attribute>
  <xsl:attribute name="space-before.maximum">1.2em</xsl:attribute>
  <xsl:attribute name="keep-with-next.within-column">always</xsl:attribute>
  <xsl:attribute name="keep-together.within-column">always</xsl:attribute>
</xsl:attribute-set>
<xsl:attribute-set name="glossdef.list.properties">
</xsl:attribute-set>
<xsl:attribute-set name="glossdef.block.properties">
  <xsl:attribute name="margin-{$direction.align.start}">.25in</xsl:attribute>
</xsl:attribute-set>
<xsl:param name="graphic.default.extension"/>
<xsl:attribute-set name="header.content.properties">
  <xsl:attribute name="font-family">
    <xsl:value-of select="$body.fontset"/>
  </xsl:attribute>
  <xsl:attribute name="margin-left">
    <xsl:value-of select="$title.margin.left"/>
  </xsl:attribute>
</xsl:attribute-set>
<xsl:param name="header.rule" select="1"/>
<xsl:param name="header.column.widths">1 1 1</xsl:param>
<xsl:param name="header.table.height">14pt</xsl:param>
<xsl:attribute-set name="header.table.properties">
  <xsl:attribute name="table-layout">fixed</xsl:attribute>
  <xsl:attribute name="width">100%</xsl:attribute>
</xsl:attribute-set>
<xsl:param name="headers.on.blank.pages" select="1"/>
<xsl:param name="highlight.default.language"/>
<xsl:param name="highlight.source" select="0"/>
<xsl:param name="highlight.xslthl.config"/>
<xsl:param name="hyphenate">true</xsl:param>
<xsl:param name="hyphenate.verbatim" select="0"/>
<xsl:param name="hyphenate.verbatim.characters"/>
<xsl:param name="ignore.image.scaling" select="0"/>
<xsl:param name="img.src.path"/>
<xsl:param name="index.method">basic</xsl:param>
<xsl:param name="index.on.role" select="0"/>
<xsl:param name="index.on.type" select="0"/>
<xsl:attribute-set name="index.page.number.properties">
</xsl:attribute-set>
<xsl:attribute-set name="informalequation.properties" use-attribute-sets="informal.object.properties"/>
<xsl:attribute-set name="informalexample.properties" use-attribute-sets="informal.object.properties"/>
<xsl:attribute-set name="informalfigure.properties" use-attribute-sets="informal.object.properties"/>
<xsl:attribute-set name="informal.object.properties">
  <xsl:attribute name="space-before.minimum">0.5em</xsl:attribute>
  <xsl:attribute name="space-before.optimum">1em</xsl:attribute>
  <xsl:attribute name="space-before.maximum">2em</xsl:attribute>
  <xsl:attribute name="space-after.minimum">0.5em</xsl:attribute>
  <xsl:attribute name="space-after.optimum">1em</xsl:attribute>
  <xsl:attribute name="space-after.maximum">2em</xsl:attribute>
</xsl:attribute-set>
<xsl:attribute-set name="informaltable.properties" use-attribute-sets="informal.object.properties"/>
<xsl:attribute-set name="index.preferred.page.properties">
  <xsl:attribute name="font-weight">bold</xsl:attribute>
</xsl:attribute-set>
<xsl:attribute-set name="index.div.title.properties">
  <xsl:attribute name="margin-{$direction.align.start}">0pt</xsl:attribute>
  <xsl:attribute name="font-size">14.4pt</xsl:attribute>
  <xsl:attribute name="font-family"><xsl:value-of select="$title.fontset"/></xsl:attribute>
  <xsl:attribute name="font-weight">bold</xsl:attribute>
  <xsl:attribute name="keep-with-next.within-column">always</xsl:attribute>
  <xsl:attribute name="space-before.optimum"><xsl:value-of select="concat($body.font.master,'pt')"/></xsl:attribute>
  <xsl:attribute name="space-before.minimum"><xsl:value-of select="concat($body.font.master,'pt * 0.8')"/></xsl:attribute>
  <xsl:attribute name="space-before.maximum"><xsl:value-of select="concat($body.font.master,'pt * 1.2')"/></xsl:attribute>
  <xsl:attribute name="start-indent">0pt</xsl:attribute>
</xsl:attribute-set>
<xsl:attribute-set name="index.entry.properties">
  <xsl:attribute name="start-indent">0pt</xsl:attribute>
</xsl:attribute-set>
<xsl:param name="index.number.separator"/>
<xsl:param name="index.range.separator"/>
<xsl:param name="index.term.separator"/>
<xsl:param name="insert.link.page.number">no</xsl:param>
<xsl:param name="insert.xref.page.number">no</xsl:param>
<xsl:attribute-set name="itemizedlist.properties" use-attribute-sets="list.block.properties">
</xsl:attribute-set>
<xsl:attribute-set name="itemizedlist.label.properties">
</xsl:attribute-set>
    <xsl:param name="itemizedlist.label.width">1.0em</xsl:param>
  

<xsl:param name="keep.relative.image.uris" select="0"/>
<xsl:param name="l10n.gentext.default.language">en</xsl:param>
<xsl:param name="l10n.gentext.language"/>
<xsl:param name="l10n.gentext.use.xref.language" select="0"/>
<xsl:param name="l10n.lang.value.rfc.compliant" select="1"/>
<xsl:param name="label.from.part" select="0"/>
<xsl:param name="line-height">normal</xsl:param>
<xsl:param name="linenumbering.everyNth">5</xsl:param>
<xsl:param name="linenumbering.extension" select="1"/>
<xsl:param name="linenumbering.separator"><xsl:text> </xsl:text></xsl:param>
<xsl:param name="linenumbering.width">3</xsl:param>
<xsl:attribute-set name="list.block.properties">
  <xsl:attribute name="provisional-label-separation">0.2em</xsl:attribute>
  <xsl:attribute name="provisional-distance-between-starts">1.5em</xsl:attribute>
</xsl:attribute-set>
<xsl:attribute-set name="list.block.spacing">
  <xsl:attribute name="space-before.optimum">1em</xsl:attribute>
  <xsl:attribute name="space-before.minimum">0.8em</xsl:attribute>
  <xsl:attribute name="space-before.maximum">1.2em</xsl:attribute>
  <xsl:attribute name="space-after.optimum">1em</xsl:attribute>
  <xsl:attribute name="space-after.minimum">0.8em</xsl:attribute>
  <xsl:attribute name="space-after.maximum">1.2em</xsl:attribute>
</xsl:attribute-set>
<xsl:attribute-set name="list.item.spacing">
  <xsl:attribute name="space-before.optimum">1em</xsl:attribute>
  <xsl:attribute name="space-before.minimum">0.8em</xsl:attribute>
  <xsl:attribute name="space-before.maximum">1.2em</xsl:attribute>
</xsl:attribute-set>
<xsl:param name="make.index.markup" select="0"/>
<xsl:param name="make.single.year.ranges" select="0"/>
<xsl:param name="make.year.ranges" select="0"/>
<xsl:attribute-set name="margin.note.properties">
  <xsl:attribute name="font-size">90%</xsl:attribute>
  <xsl:attribute name="text-align">start</xsl:attribute>
</xsl:attribute-set>
<xsl:attribute-set name="margin.note.title.properties">
  <xsl:attribute name="font-weight">bold</xsl:attribute>
  <xsl:attribute name="hyphenate">false</xsl:attribute>
  <xsl:attribute name="text-align">start</xsl:attribute>
  <xsl:attribute name="keep-with-next.within-column">always</xsl:attribute>
</xsl:attribute-set>
<xsl:param name="margin.note.float.type">none</xsl:param>
<xsl:param name="margin.note.width">1in</xsl:param>
<xsl:param name="marker.section.level">2</xsl:param>
<xsl:param name="menuchoice.menu.separator"> → </xsl:param>
<xsl:param name="menuchoice.separator">+</xsl:param>
<xsl:param name="monospace.font.family">monospace</xsl:param>
<xsl:attribute-set name="monospace.properties">
  <xsl:attribute name="font-family">
    <xsl:value-of select="$monospace.font.family"/>
  </xsl:attribute>
</xsl:attribute-set>
<xsl:attribute-set name="monospace.verbatim.properties" use-attribute-sets="verbatim.properties monospace.properties">
  <xsl:attribute name="text-align">start</xsl:attribute>
  <xsl:attribute name="wrap-option">no-wrap</xsl:attribute>
</xsl:attribute-set>
<xsl:param name="monospace.verbatim.font.width">0.60em</xsl:param>
<xsl:param name="nominal.table.width">6in</xsl:param>
<xsl:attribute-set name="normal.para.spacing">
  <xsl:attribute name="space-before.optimum">1em</xsl:attribute>
  <xsl:attribute name="space-before.minimum">0.8em</xsl:attribute>
  <xsl:attribute name="space-before.maximum">1.2em</xsl:attribute>
</xsl:attribute-set>
<xsl:param name="olink.doctitle">no</xsl:param> 
<xsl:param name="olink.base.uri"/>
<xsl:param name="olink.debug" select="0"/>
<xsl:attribute-set name="olink.properties">
  <xsl:attribute name="show-destination">replace</xsl:attribute>
</xsl:attribute-set>
<xsl:param name="olink.lang.fallback.sequence"/>
<xsl:attribute-set name="orderedlist.properties" use-attribute-sets="list.block.properties">
  <xsl:attribute name="provisional-distance-between-starts">2em</xsl:attribute>
</xsl:attribute-set>
<xsl:attribute-set name="orderedlist.label.properties">
</xsl:attribute-set>
<xsl:param name="orderedlist.label.width">1.2em</xsl:param>
<xsl:param name="prefer.internal.olink" select="0"/>
<xsl:param name="insert.olink.page.number">no</xsl:param>
<xsl:param name="insert.olink.pdf.frag" select="0"/>
<xsl:param name="page.height">
  <xsl:choose>
    <xsl:when test="$page.orientation = 'portrait'">
      <xsl:value-of select="$page.height.portrait"/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:value-of select="$page.width.portrait"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:param>
<xsl:param name="page.height.portrait">
  <xsl:choose>
    <xsl:when test="$paper.type = 'A4landscape'">210mm</xsl:when>
    <xsl:when test="$paper.type = 'USletter'">11in</xsl:when>
    <xsl:when test="$paper.type = 'USlandscape'">8.5in</xsl:when>
    <xsl:when test="$paper.type = 'USlegal'">14in</xsl:when>
    <xsl:when test="$paper.type = 'USlegallandscape'">8.5in</xsl:when>
    <xsl:when test="$paper.type = '4A0'">2378mm</xsl:when>
    <xsl:when test="$paper.type = '2A0'">1682mm</xsl:when>
    <xsl:when test="$paper.type = 'A0'">1189mm</xsl:when>
    <xsl:when test="$paper.type = 'A1'">841mm</xsl:when>
    <xsl:when test="$paper.type = 'A2'">594mm</xsl:when>
    <xsl:when test="$paper.type = 'A3'">420mm</xsl:when>
    <xsl:when test="$paper.type = 'A4'">297mm</xsl:when>
    <xsl:when test="$paper.type = 'A5'">210mm</xsl:when>
    <xsl:when test="$paper.type = 'A6'">148mm</xsl:when>
    <xsl:when test="$paper.type = 'A7'">105mm</xsl:when>
    <xsl:when test="$paper.type = 'A8'">74mm</xsl:when>
    <xsl:when test="$paper.type = 'A9'">52mm</xsl:when>
    <xsl:when test="$paper.type = 'A10'">37mm</xsl:when>
    <xsl:when test="$paper.type = 'B0'">1414mm</xsl:when>
    <xsl:when test="$paper.type = 'B1'">1000mm</xsl:when>
    <xsl:when test="$paper.type = 'B2'">707mm</xsl:when>
    <xsl:when test="$paper.type = 'B3'">500mm</xsl:when>
    <xsl:when test="$paper.type = 'B4'">353mm</xsl:when>
    <xsl:when test="$paper.type = 'B5'">250mm</xsl:when>
    <xsl:when test="$paper.type = 'B6'">176mm</xsl:when>
    <xsl:when test="$paper.type = 'B7'">125mm</xsl:when>
    <xsl:when test="$paper.type = 'B8'">88mm</xsl:when>
    <xsl:when test="$paper.type = 'B9'">62mm</xsl:when>
    <xsl:when test="$paper.type = 'B10'">44mm</xsl:when>
    <xsl:when test="$paper.type = 'C0'">1297mm</xsl:when>
    <xsl:when test="$paper.type = 'C1'">917mm</xsl:when>
    <xsl:when test="$paper.type = 'C2'">648mm</xsl:when>
    <xsl:when test="$paper.type = 'C3'">458mm</xsl:when>
    <xsl:when test="$paper.type = 'C4'">324mm</xsl:when>
    <xsl:when test="$paper.type = 'C5'">229mm</xsl:when>
    <xsl:when test="$paper.type = 'C6'">162mm</xsl:when>
    <xsl:when test="$paper.type = 'C7'">114mm</xsl:when>
    <xsl:when test="$paper.type = 'C8'">81mm</xsl:when>
    <xsl:when test="$paper.type = 'C9'">57mm</xsl:when>
    <xsl:when test="$paper.type = 'C10'">40mm</xsl:when>
    <xsl:otherwise>11in</xsl:otherwise>
  </xsl:choose>
</xsl:param>
<xsl:param name="page.margin.bottom">0.5in</xsl:param>
<xsl:param name="page.margin.inner">
  <xsl:choose>
    <xsl:when test="$double.sided != 0">1.25in</xsl:when>
    <xsl:otherwise>1in</xsl:otherwise>
  </xsl:choose>
</xsl:param>
<xsl:param name="page.margin.outer">
  <xsl:choose>
    <xsl:when test="$double.sided != 0">0.75in</xsl:when>
    <xsl:otherwise>1in</xsl:otherwise>
  </xsl:choose>
</xsl:param>
<xsl:param name="page.margin.top">0.5in</xsl:param>
<xsl:param name="page.orientation">portrait</xsl:param>
<xsl:param name="page.width">
  <xsl:choose>
    <xsl:when test="$page.orientation = 'portrait'">
      <xsl:value-of select="$page.width.portrait"/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:value-of select="$page.height.portrait"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:param>
<xsl:param name="page.width.portrait">
  <xsl:choose>
    <xsl:when test="$paper.type = 'USletter'">8.5in</xsl:when>
    <xsl:when test="$paper.type = 'USlandscape'">11in</xsl:when>
    <xsl:when test="$paper.type = 'USlegal'">8.5in</xsl:when>
    <xsl:when test="$paper.type = 'USlegallandscape'">14in</xsl:when>
    <xsl:when test="$paper.type = '4A0'">1682mm</xsl:when>
    <xsl:when test="$paper.type = '2A0'">1189mm</xsl:when>
    <xsl:when test="$paper.type = 'A0'">841mm</xsl:when>
    <xsl:when test="$paper.type = 'A1'">594mm</xsl:when>
    <xsl:when test="$paper.type = 'A2'">420mm</xsl:when>
    <xsl:when test="$paper.type = 'A3'">297mm</xsl:when>
    <xsl:when test="$paper.type = 'A4'">210mm</xsl:when>
    <xsl:when test="$paper.type = 'A5'">148mm</xsl:when>
    <xsl:when test="$paper.type = 'A6'">105mm</xsl:when>
    <xsl:when test="$paper.type = 'A7'">74mm</xsl:when>
    <xsl:when test="$paper.type = 'A8'">52mm</xsl:when>
    <xsl:when test="$paper.type = 'A9'">37mm</xsl:when>
    <xsl:when test="$paper.type = 'A10'">26mm</xsl:when>
    <xsl:when test="$paper.type = 'B0'">1000mm</xsl:when>
    <xsl:when test="$paper.type = 'B1'">707mm</xsl:when>
    <xsl:when test="$paper.type = 'B2'">500mm</xsl:when>
    <xsl:when test="$paper.type = 'B3'">353mm</xsl:when>
    <xsl:when test="$paper.type = 'B4'">250mm</xsl:when>
    <xsl:when test="$paper.type = 'B5'">176mm</xsl:when>
    <xsl:when test="$paper.type = 'B6'">125mm</xsl:when>
    <xsl:when test="$paper.type = 'B7'">88mm</xsl:when>
    <xsl:when test="$paper.type = 'B8'">62mm</xsl:when>
    <xsl:when test="$paper.type = 'B9'">44mm</xsl:when>
    <xsl:when test="$paper.type = 'B10'">31mm</xsl:when>
    <xsl:when test="$paper.type = 'C0'">917mm</xsl:when>
    <xsl:when test="$paper.type = 'C1'">648mm</xsl:when>
    <xsl:when test="$paper.type = 'C2'">458mm</xsl:when>
    <xsl:when test="$paper.type = 'C3'">324mm</xsl:when>
    <xsl:when test="$paper.type = 'C4'">229mm</xsl:when>
    <xsl:when test="$paper.type = 'C5'">162mm</xsl:when>
    <xsl:when test="$paper.type = 'C6'">114mm</xsl:when>
    <xsl:when test="$paper.type = 'C7'">81mm</xsl:when>
    <xsl:when test="$paper.type = 'C8'">57mm</xsl:when>
    <xsl:when test="$paper.type = 'C9'">40mm</xsl:when>
    <xsl:when test="$paper.type = 'C10'">28mm</xsl:when>
    <xsl:otherwise>8.5in</xsl:otherwise>
  </xsl:choose>
</xsl:param>
<xsl:param name="paper.type">USletter</xsl:param>
<xsl:param name="part.autolabel">I</xsl:param>
<xsl:param name="passivetex.extensions" select="0"/>
<xsl:attribute-set name="pgwide.properties">
  <xsl:attribute name="start-indent">0pt</xsl:attribute>
</xsl:attribute-set>
<xsl:param name="preface.autolabel" select="0"/>
<xsl:param name="preferred.mediaobject.role"/>
<xsl:attribute-set name="procedure.properties" use-attribute-sets="formal.object.properties">
  <xsl:attribute name="keep-together.within-column">auto</xsl:attribute>
</xsl:attribute-set>
<xsl:param name="process.empty.source.toc" select="0"/>
<xsl:param name="process.source.toc" select="0"/>
<xsl:param name="profile.arch"/>
<xsl:param name="profile.audience"/>
<xsl:param name="profile.attribute"/>
<xsl:param name="profile.condition"/>
<xsl:param name="profile.conformance"/>
<xsl:param name="profile.lang"/>
<xsl:param name="profile.os"/>
<xsl:param name="profile.revision"/>
<xsl:param name="profile.revisionflag"/>
<xsl:param name="profile.role"/>
<xsl:param name="profile.security"/>
<xsl:param name="profile.separator">;</xsl:param>
<xsl:param name="profile.status"/>
<xsl:param name="profile.userlevel"/>
<xsl:param name="profile.value"/>
<xsl:param name="profile.vendor"/>
<xsl:param name="profile.wordsize"/>
<xsl:param name="punct.honorific">.</xsl:param>
<xsl:param name="qanda.defaultlabel">number</xsl:param>
<xsl:param name="qanda.in.toc" select="0"/>
<xsl:param name="qanda.nested.in.toc" select="0"/>
<xsl:param name="qanda.inherit.numeration" select="1"/>
<xsl:param name="qandadiv.autolabel" select="1"/>
<xsl:attribute-set name="qanda.title.level1.properties">
  <xsl:attribute name="font-size">
    <xsl:value-of select="$body.font.master * 2.0736"/>
    <xsl:text>pt</xsl:text>
  </xsl:attribute>
</xsl:attribute-set>
<xsl:attribute-set name="qanda.title.level2.properties">
  <xsl:attribute name="font-size">
    <xsl:value-of select="$body.font.master * 1.728"/>
    <xsl:text>pt</xsl:text>
  </xsl:attribute>
</xsl:attribute-set>
<xsl:attribute-set name="qanda.title.level3.properties">
  <xsl:attribute name="font-size">
    <xsl:value-of select="$body.font.master * 1.44"/>
    <xsl:text>pt</xsl:text>
  </xsl:attribute>
</xsl:attribute-set>
<xsl:attribute-set name="qanda.title.level4.properties">
  <xsl:attribute name="font-size">
    <xsl:value-of select="$body.font.master * 1.2"/>
    <xsl:text>pt</xsl:text>
  </xsl:attribute>
</xsl:attribute-set>
<xsl:attribute-set name="qanda.title.level5.properties">
  <xsl:attribute name="font-size">
    <xsl:value-of select="$body.font.master"/>
    <xsl:text>pt</xsl:text>
  </xsl:attribute>
</xsl:attribute-set>
<xsl:attribute-set name="qanda.title.level6.properties">
  <xsl:attribute name="font-size">
    <xsl:value-of select="$body.font.master"/>
    <xsl:text>pt</xsl:text>
  </xsl:attribute>
</xsl:attribute-set>
<xsl:attribute-set name="qanda.title.properties">
  <xsl:attribute name="font-family">
    <xsl:value-of select="$title.fontset"/>
  </xsl:attribute>
  <xsl:attribute name="font-weight">bold</xsl:attribute>
  <!-- font size is calculated dynamically by qanda.heading template -->
  <xsl:attribute name="keep-with-next.within-column">always</xsl:attribute>
  <xsl:attribute name="space-before.minimum">0.8em</xsl:attribute>
  <xsl:attribute name="space-before.optimum">1.0em</xsl:attribute>
  <xsl:attribute name="space-before.maximum">1.2em</xsl:attribute>
</xsl:attribute-set>
<xsl:param name="refentry.generate.name" select="1"/>
<xsl:param name="refentry.generate.title" select="0"/>
<xsl:param name="refentry.pagebreak" select="1"/>
<xsl:attribute-set name="refentry.title.properties">
  <xsl:attribute name="font-family">
    <xsl:value-of select="$title.fontset"/>
  </xsl:attribute>
  <xsl:attribute name="font-size">18pt</xsl:attribute>
  <xsl:attribute name="font-weight">bold</xsl:attribute>
  <xsl:attribute name="space-after">1em</xsl:attribute>
  <xsl:attribute name="hyphenate">false</xsl:attribute>
  <xsl:attribute name="keep-with-next.within-column">always</xsl:attribute>
  <xsl:attribute name="space-before.minimum">0.8em</xsl:attribute>
  <xsl:attribute name="space-before.optimum">1.0em</xsl:attribute>
  <xsl:attribute name="space-before.maximum">1.2em</xsl:attribute>
  <xsl:attribute name="space-after.optimum">0.5em</xsl:attribute>
  <xsl:attribute name="space-after.minimum">0.4em</xsl:attribute>
  <xsl:attribute name="space-after.maximum">0.6em</xsl:attribute>
  <xsl:attribute name="start-indent"><xsl:value-of select="$title.margin.left"/></xsl:attribute>
</xsl:attribute-set>
<xsl:param name="refentry.xref.manvolnum" select="1"/>
  <xsl:param name="reference.autolabel">I</xsl:param>
<xsl:param name="refclass.suppress" select="0"/>
<xsl:param name="region.after.extent">0.4in</xsl:param>
<xsl:param name="region.before.extent">0.4in</xsl:param>
<xsl:attribute-set name="revhistory.table.properties">
</xsl:attribute-set>
<xsl:attribute-set name="revhistory.table.cell.properties">
</xsl:attribute-set>
<xsl:attribute-set name="revhistory.title.properties">
</xsl:attribute-set>
<xsl:attribute-set name="root.properties">
  <xsl:attribute name="font-family">
    <xsl:value-of select="$body.fontset"/>
  </xsl:attribute>
  <xsl:attribute name="font-size">
    <xsl:value-of select="$body.font.size"/>
  </xsl:attribute>
  <xsl:attribute name="text-align">
    <xsl:value-of select="$alignment"/>
  </xsl:attribute>
  <xsl:attribute name="line-height">
    <xsl:value-of select="$line-height"/>
  </xsl:attribute>
  <xsl:attribute name="font-selection-strategy">character-by-character</xsl:attribute>
  <xsl:attribute name="line-height-shift-adjustment">disregard-shifts</xsl:attribute>
  <xsl:attribute name="writing-mode">
    <xsl:value-of select="$direction.mode"/>
  </xsl:attribute>
</xsl:attribute-set>
<xsl:param name="rootid"/>
<xsl:param name="runinhead.default.title.end.punct">.</xsl:param>
<xsl:param name="runinhead.title.end.punct">.!?:</xsl:param>
<xsl:param name="sans.font.family">sans-serif</xsl:param>
<xsl:param name="section.autolabel" select="0"/>
<xsl:param name="section.autolabel.max.depth">8</xsl:param>
<xsl:param name="section.container.element">block</xsl:param>
<xsl:param name="section.label.includes.component.label" select="0"/>
<xsl:attribute-set name="section.title.level1.properties">
  <xsl:attribute name="font-size">
    <xsl:value-of select="$body.font.master * 2.0736"/>
    <xsl:text>pt</xsl:text>
  </xsl:attribute>
</xsl:attribute-set>
<xsl:attribute-set name="section.title.level2.properties">
  <xsl:attribute name="font-size">
    <xsl:value-of select="$body.font.master * 1.728"/>
    <xsl:text>pt</xsl:text>
  </xsl:attribute>
</xsl:attribute-set>
<xsl:attribute-set name="section.title.level3.properties">
  <xsl:attribute name="font-size">
    <xsl:value-of select="$body.font.master * 1.44"/>
    <xsl:text>pt</xsl:text>
  </xsl:attribute>
</xsl:attribute-set>
<xsl:attribute-set name="section.title.level4.properties">
  <xsl:attribute name="font-size">
    <xsl:value-of select="$body.font.master * 1.2"/>
    <xsl:text>pt</xsl:text>
  </xsl:attribute>
</xsl:attribute-set>
<xsl:attribute-set name="section.title.level5.properties">
  <xsl:attribute name="font-size">
    <xsl:value-of select="$body.font.master"/>
    <xsl:text>pt</xsl:text>
  </xsl:attribute>
</xsl:attribute-set>
<xsl:attribute-set name="section.title.level6.properties">
  <xsl:attribute name="font-size">
    <xsl:value-of select="$body.font.master"/>
    <xsl:text>pt</xsl:text>
  </xsl:attribute>
</xsl:attribute-set>
<xsl:attribute-set name="section.title.properties">
  <xsl:attribute name="font-family">
    <xsl:value-of select="$title.fontset"/>
  </xsl:attribute>
  <xsl:attribute name="font-weight">bold</xsl:attribute>
  <!-- font size is calculated dynamically by section.heading template -->
  <xsl:attribute name="keep-with-next.within-column">always</xsl:attribute>
  <xsl:attribute name="space-before.minimum">0.8em</xsl:attribute>
  <xsl:attribute name="space-before.optimum">1.0em</xsl:attribute>
  <xsl:attribute name="space-before.maximum">1.2em</xsl:attribute>
  <xsl:attribute name="text-align">start</xsl:attribute>
  <xsl:attribute name="start-indent"><xsl:value-of select="$title.margin.left"/></xsl:attribute>
</xsl:attribute-set>
<xsl:attribute-set name="section.level1.properties" use-attribute-sets="section.properties">
</xsl:attribute-set>
<xsl:attribute-set name="section.level2.properties" use-attribute-sets="section.properties">
</xsl:attribute-set>
<xsl:attribute-set name="section.level3.properties" use-attribute-sets="section.properties">
</xsl:attribute-set>
<xsl:attribute-set name="section.level4.properties" use-attribute-sets="section.properties">
</xsl:attribute-set>
<xsl:attribute-set name="section.level5.properties" use-attribute-sets="section.properties">
</xsl:attribute-set>
<xsl:attribute-set name="section.level6.properties" use-attribute-sets="section.properties">
</xsl:attribute-set>
<xsl:attribute-set name="section.properties">
</xsl:attribute-set>
<xsl:param name="segmentedlist.as.table" select="0"/>
<xsl:param name="shade.verbatim" select="0"/>

<xsl:attribute-set name="shade.verbatim.style">
  <xsl:attribute name="background-color">#E0E0E0</xsl:attribute>
</xsl:attribute-set>
<xsl:param name="show.comments" select="1"/>
<xsl:attribute-set name="sidebar.properties" use-attribute-sets="formal.object.properties">
  <xsl:attribute name="border-style">solid</xsl:attribute>
  <xsl:attribute name="border-width">1pt</xsl:attribute>
  <xsl:attribute name="border-color">black</xsl:attribute>
  <xsl:attribute name="background-color">#DDDDDD</xsl:attribute>
  <xsl:attribute name="padding-start">12pt</xsl:attribute>
  <xsl:attribute name="padding-end">12pt</xsl:attribute>
  <xsl:attribute name="padding-top">6pt</xsl:attribute>
  <xsl:attribute name="padding-bottom">6pt</xsl:attribute>
  <xsl:attribute name="margin-{$direction.align.start}">0pt</xsl:attribute>
  <xsl:attribute name="margin-{$direction.align.end}">0pt</xsl:attribute>
<!--
  <xsl:attribute name="margin-top">6pt</xsl:attribute>
  <xsl:attribute name="margin-bottom">6pt</xsl:attribute>
-->
</xsl:attribute-set>
<xsl:attribute-set name="sidebar.title.properties">
  <xsl:attribute name="font-weight">bold</xsl:attribute>
  <xsl:attribute name="hyphenate">false</xsl:attribute>
  <xsl:attribute name="text-align">start</xsl:attribute>
  <xsl:attribute name="keep-with-next.within-column">always</xsl:attribute>
</xsl:attribute-set>
<xsl:param name="sidebar.float.type">none</xsl:param>
<xsl:param name="sidebar.float.width">1in</xsl:param>
<xsl:param name="simplesect.in.toc" select="0"/>
<xsl:attribute-set name="subscript.properties">
  <xsl:attribute name="font-size">75%</xsl:attribute>
</xsl:attribute-set>
<xsl:attribute-set name="superscript.properties">
  <xsl:attribute name="font-size">75%</xsl:attribute>
</xsl:attribute-set>
<xsl:param name="symbol.font.family">Symbol,ZapfDingbats</xsl:param>

<xsl:param name="table.cell.border.color">black</xsl:param>
<xsl:param name="table.cell.border.style">solid</xsl:param>
<xsl:param name="table.cell.border.thickness">0.5pt</xsl:param>
<xsl:attribute-set name="table.cell.padding">
  <xsl:attribute name="padding-start">2pt</xsl:attribute>
  <xsl:attribute name="padding-end">2pt</xsl:attribute>
  <xsl:attribute name="padding-top">2pt</xsl:attribute>
  <xsl:attribute name="padding-bottom">2pt</xsl:attribute>
</xsl:attribute-set>
<xsl:param name="table.footnote.number.format">a</xsl:param>
<xsl:param name="table.footnote.number.symbols"/>
<xsl:attribute-set name="table.footnote.properties">
  <xsl:attribute name="font-family"><xsl:value-of select="$body.fontset"/></xsl:attribute>
  <xsl:attribute name="font-size"><xsl:value-of select="$footnote.font.size"/></xsl:attribute>
  <xsl:attribute name="font-weight">normal</xsl:attribute>
  <xsl:attribute name="font-style">normal</xsl:attribute>
  <xsl:attribute name="space-before">2pt</xsl:attribute>
  <xsl:attribute name="text-align"><xsl:value-of select="$alignment"/></xsl:attribute>
</xsl:attribute-set>

<xsl:param name="table.frame.border.color">black</xsl:param>
<xsl:param name="table.frame.border.style">solid</xsl:param>
<xsl:param name="table.frame.border.thickness">0.5pt</xsl:param>
<xsl:attribute-set name="table.properties" use-attribute-sets="formal.object.properties">
  <xsl:attribute name="keep-together.within-column">auto</xsl:attribute>
</xsl:attribute-set>
<xsl:param name="tablecolumns.extension" select="1"/>
<xsl:attribute-set name="table.table.properties">
  <xsl:attribute name="border-before-width.conditionality">retain</xsl:attribute>
  <xsl:attribute name="border-collapse">collapse</xsl:attribute>
</xsl:attribute-set>
<xsl:attribute-set name="table.caption.properties">
  <xsl:attribute name="keep-together.within-column">always</xsl:attribute>
</xsl:attribute-set>
 <xsl:param name="target.database.document">olinkdb.xml</xsl:param>
<xsl:param name="targets.filename">target.db</xsl:param>
<xsl:attribute-set name="task.properties" use-attribute-sets="formal.object.properties">
    <xsl:attribute name="keep-together.within-column">auto</xsl:attribute>
</xsl:attribute-set>
<xsl:param name="textdata.default.encoding"/>
<xsl:param name="tex.math.delims" select="1"/>
<xsl:param name="tex.math.in.alt"/>
  <xsl:param name="textinsert.extension" select="1"/>
<xsl:param name="title.font.family">sans-serif</xsl:param>
<xsl:param name="title.margin.left">
  <xsl:choose>
    <xsl:when test="$fop.extensions != 0">-4pc</xsl:when>
    <xsl:when test="$passivetex.extensions != 0">0pt</xsl:when>
    <xsl:otherwise>0pt</xsl:otherwise>
  </xsl:choose>
</xsl:param>
<xsl:param name="toc.indent.width">24</xsl:param>
<!-- inconsistant point specification? -->
<xsl:attribute-set name="toc.line.properties">
  <xsl:attribute name="text-align-last">justify</xsl:attribute>
  <xsl:attribute name="text-align">start</xsl:attribute>
  <xsl:attribute name="end-indent"><xsl:value-of select="concat($toc.indent.width, 'pt')"/></xsl:attribute>
  <xsl:attribute name="last-line-end-indent"><xsl:value-of select="concat('-', $toc.indent.width, 'pt')"/></xsl:attribute>
</xsl:attribute-set>
<xsl:attribute-set name="toc.margin.properties">
  <xsl:attribute name="space-before.minimum">0.5em</xsl:attribute>
  <xsl:attribute name="space-before.optimum">1em</xsl:attribute>
  <xsl:attribute name="space-before.maximum">2em</xsl:attribute>
  <xsl:attribute name="space-after.minimum">0.5em</xsl:attribute>
  <xsl:attribute name="space-after.optimum">1em</xsl:attribute>
  <xsl:attribute name="space-after.maximum">2em</xsl:attribute>
</xsl:attribute-set>
<xsl:param name="toc.max.depth">8</xsl:param>
<xsl:param name="toc.section.depth">2</xsl:param>
<xsl:param name="ulink.footnotes" select="0"/>
<xsl:param name="ulink.hyphenate"/>
<xsl:param name="ulink.hyphenate.chars">/</xsl:param>
<xsl:param name="ulink.show" select="1"/>
<xsl:param name="use.extensions" select="0"/>
<xsl:param name="use.local.olink.style" select="0"/> 
<xsl:param name="use.role.as.xrefstyle" select="1"/>
<xsl:param name="use.role.for.mediaobject" select="1"/>
<xsl:param name="use.svg" select="1"/>
<xsl:param name="variablelist.as.blocks" select="0"/>
<xsl:param name="variablelist.max.termlength">24</xsl:param>
<xsl:param name="variablelist.term.separator">, </xsl:param>
<xsl:attribute-set name="variablelist.term.properties">
</xsl:attribute-set>
<xsl:param name="variablelist.term.break.after">0</xsl:param>
<xsl:attribute-set name="verbatim.properties">
  <xsl:attribute name="space-before.minimum">0.8em</xsl:attribute>
  <xsl:attribute name="space-before.optimum">1em</xsl:attribute>
  <xsl:attribute name="space-before.maximum">1.2em</xsl:attribute>
  <xsl:attribute name="space-after.minimum">0.8em</xsl:attribute>
  <xsl:attribute name="space-after.optimum">1em</xsl:attribute>
  <xsl:attribute name="space-after.maximum">1.2em</xsl:attribute>
  <xsl:attribute name="hyphenate">false</xsl:attribute>
  <xsl:attribute name="wrap-option">no-wrap</xsl:attribute>
  <xsl:attribute name="white-space-collapse">false</xsl:attribute>
  <xsl:attribute name="white-space-treatment">preserve</xsl:attribute>
  <xsl:attribute name="linefeed-treatment">preserve</xsl:attribute>
  <xsl:attribute name="text-align">start</xsl:attribute>
</xsl:attribute-set>
<xsl:param name="writing.mode">
  <xsl:call-template name="gentext">
    <xsl:with-param name="key">writing-mode</xsl:with-param>
    <xsl:with-param name="lang">
      <xsl:call-template name="l10n.language">
        <xsl:with-param name="target" select="/*[1]"/>
      </xsl:call-template>
    </xsl:with-param>
  </xsl:call-template>
</xsl:param>
<xsl:param name="xep.extensions" select="0"/>
<xsl:attribute-set name="xep.index.item.properties" use-attribute-sets="index.page.number.properties">
  <xsl:attribute name="merge-subsequent-page-numbers">true</xsl:attribute>
  <xsl:attribute name="link-back">true</xsl:attribute>
</xsl:attribute-set>
<xsl:param name="xref.label-page.separator"><xsl:text> </xsl:text></xsl:param>
<xsl:param name="xref.label-title.separator">: </xsl:param>
<xsl:attribute-set name="xref.properties">
</xsl:attribute-set>
<xsl:param name="xref.title-page.separator"><xsl:text> </xsl:text></xsl:param>
<xsl:param name="xref.with.number.and.title" select="1"/>
<xsl:param name="region.inner.extent">0in</xsl:param>
<xsl:param name="region.outer.extent">0in</xsl:param>
<xsl:param name="body.margin.inner">0in</xsl:param>
<xsl:param name="body.margin.outer">0in</xsl:param>
<xsl:param name="side.region.precedence">false</xsl:param>
<xsl:attribute-set name="inner.region.content.properties">
</xsl:attribute-set>
<xsl:attribute-set name="outer.region.content.properties">
</xsl:attribute-set>
<xsl:attribute-set name="region.inner.properties">
  <xsl:attribute name="border-width">0</xsl:attribute>
  <xsl:attribute name="padding">0</xsl:attribute>
  <xsl:attribute name="reference-orientation">90</xsl:attribute>
</xsl:attribute-set>
<xsl:attribute-set name="region.outer.properties">
  <xsl:attribute name="border-width">0</xsl:attribute>
  <xsl:attribute name="padding">0</xsl:attribute>
  <xsl:attribute name="reference-orientation">90</xsl:attribute>
</xsl:attribute-set>
<xsl:attribute-set name="para.properties" use-attribute-sets="normal.para.spacing">
</xsl:attribute-set>

</xsl:stylesheet>

