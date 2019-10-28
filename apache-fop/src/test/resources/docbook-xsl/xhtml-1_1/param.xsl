<?xml version="1.0" encoding="ASCII"?><!--This file was created automatically by html2xhtml--><!--from the HTML stylesheets.--><!-- This file is generated from param.xweb --><xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns="http://www.w3.org/1999/xhtml" version="1.0">

<!-- ********************************************************************
     $Id: param.xweb 9658 2012-10-29 22:28:34Z bobstayton $
     ********************************************************************

     This file is part of the XSL DocBook Stylesheet distribution.
     See ../README or http://docbook.sf.net/release/xsl/current/ for
     copyright and other information.

     ******************************************************************** -->

<xsl:param name="abstract.notitle.enabled" select="0"/>
<xsl:param name="activate.external.olinks" select="1"/>
<xsl:param name="admon.graphics.extension">.png</xsl:param>
<xsl:param name="admon.graphics" select="0"/>
<xsl:param name="admon.graphics.path">images/</xsl:param>
<xsl:param name="admon.style">
  <xsl:value-of select="concat('margin-', $direction.align.start,            ': 0.5in; margin-', $direction.align.end, ': 0.5in;')"/>
</xsl:param>
<xsl:param name="admon.textlabel" select="1"/>
<xsl:param name="annotate.toc" select="1"/>
<xsl:param name="annotation.css">
/* ======================================================================
   Annotations
*/

div.annotation-list  { visibility: hidden;
                     }

div.annotation-nocss { position: absolute;
                       visibility: hidden;
                     }

div.annotation-popup { position: absolute;
                       z-index: 4;
                       visibility: hidden;
                       padding: 0px;
                       margin: 2px;
                       border-style: solid;
                       border-width: 1px;
                       width: 200px;
		       background-color: white;
                     }

div.annotation-title { padding: 1px;
                       font-weight: bold;
                       border-bottom-style: solid;
                       border-bottom-width: 1px;
		       color: white;
		       background-color: black;
                     }

div.annotation-body  { padding: 2px;
                     }

div.annotation-body p { margin-top: 0px;
                        padding-top: 0px;
                      }

div.annotation-close { position: absolute;
                       top: 2px;
                       right: 2px;
                     }
</xsl:param>
<xsl:param name="annotation.graphic.close">
http://docbook.sourceforge.net/release/images/annot-close.png</xsl:param>
<xsl:param name="annotation.graphic.open">http://docbook.sourceforge.net/release/images/annot-open.png</xsl:param>

<xsl:param name="annotation.js">
<xsl:text>http://docbook.sourceforge.net/release/script/AnchorPosition.js http://docbook.sourceforge.net/release/script/PopupWindow.js</xsl:text></xsl:param>

<xsl:param name="annotation.support" select="0"/>
<xsl:param name="appendix.autolabel">A</xsl:param>
<xsl:param name="author.othername.in.middle" select="1"/>
<xsl:param name="autotoc.label.in.hyperlink" select="1"/>
<xsl:param name="autotoc.label.separator">. </xsl:param>
<xsl:param name="base.dir"/>
<xsl:param name="biblioentry.item.separator">. </xsl:param>
<xsl:param name="bibliography.collection">http://docbook.sourceforge.net/release/bibliography/bibliography.xml</xsl:param>

<xsl:param name="bibliography.numbered" select="0"/>
<xsl:param name="bibliography.style">normal</xsl:param>
<xsl:param name="blurb.on.titlepage.enabled" select="0"/>
<xsl:param name="bridgehead.in.toc" select="0"/>
<xsl:param name="callout.defaultcolumn">60</xsl:param>
<xsl:param name="callout.graphics.extension">.png</xsl:param>

<xsl:param name="callout.graphics" select="1"/>
<xsl:param name="callout.graphics.number.limit">15</xsl:param>

<xsl:param name="callout.graphics.path">images/callouts/</xsl:param>
<xsl:param name="callout.list.table" select="1"/>
<xsl:param name="callout.unicode" select="0"/>
<xsl:param name="callout.unicode.number.limit">10</xsl:param>
<xsl:param name="callout.unicode.start.character">10102</xsl:param>
<xsl:param name="callouts.extension" select="1"/>
<xsl:param name="chapter.autolabel" select="1"/>
<xsl:param name="chunk.append"/>
<xsl:param name="chunk.first.sections" select="0"/>
<xsl:param name="chunk.quietly" select="0"/>
<xsl:param name="chunk.section.depth" select="1"/>
<xsl:param name="chunk.separate.lots" select="0"/>
<xsl:param name="chunk.toc"/>
<xsl:param name="chunk.tocs.and.lots" select="0"/>
<xsl:param name="chunk.tocs.and.lots.has.title" select="1"/>
<xsl:param name="chunked.filename.prefix"/>
<xsl:param name="citerefentry.link" select="0"/>
<xsl:param name="collect.xref.targets">no</xsl:param>
<xsl:param name="component.label.includes.part.label" select="0"/>
<xsl:param name="contrib.inline.enabled">1</xsl:param>
<xsl:param name="css.decoration" select="0"/>
<xsl:param name="current.docid"/>
<xsl:param name="custom.css.source"/>
<xsl:param name="default.float.class">
  <xsl:choose>
    <xsl:when test="contains($stylesheet.result.type,'html')">left</xsl:when>
    <xsl:otherwise>before</xsl:otherwise>
  </xsl:choose>
</xsl:param>
<xsl:param name="default.image.width"/>
<xsl:param name="default.table.frame">all</xsl:param>
<xsl:param name="default.table.width"/>
<xsl:param name="docbook.css.link" select="1"/>
<xsl:param name="docbook.css.source">docbook.css.xml</xsl:param>
<xsl:param name="draft.mode">no</xsl:param>
<xsl:param name="draft.watermark.image">images/draft.png</xsl:param>
<xsl:param name="ebnf.assignment">
<code>::=</code>
</xsl:param>

<xsl:param name="ebnf.statement.terminator"/>

<xsl:param name="ebnf.table.bgcolor">#F5DCB3</xsl:param>
<xsl:param name="ebnf.table.border" select="1"/>
<xsl:param name="eclipse.autolabel" select="0"/>
<xsl:param name="eclipse.plugin.id">com.example.help</xsl:param>
<xsl:param name="eclipse.plugin.name">DocBook Online Help Sample</xsl:param>
<xsl:param name="eclipse.plugin.provider">Example provider</xsl:param>
<xsl:param name="editedby.enabled">1</xsl:param>
<xsl:param name="email.delimiters.enabled" select="1"/>
<xsl:param name="emphasis.propagates.style" select="1"/>
<xsl:param name="entry.propagates.style" select="1"/>
<xsl:param name="exsl.node.set.available"> 
  <xsl:choose>
    <xsl:when xmlns:exsl="http://exslt.org/common" exsl:foo="" test="function-available('exsl:node-set') or                        contains(system-property('xsl:vendor'),                          'Apache Software Foundation')">1</xsl:when>
    <xsl:otherwise>0</xsl:otherwise>
  </xsl:choose>
</xsl:param>
<xsl:param name="firstterm.only.link" select="0"/>
<xsl:param name="footer.rule" select="1"/>
<xsl:param name="footnote.number.format">1</xsl:param>
<xsl:param name="footnote.number.symbols"/>
<xsl:param name="formal.procedures" select="1"/>
<xsl:param name="formal.title.placement">
figure before
example before
equation before
table before
procedure before
task before
</xsl:param>
<xsl:param name="funcsynopsis.decoration" select="1"/>
<xsl:param name="funcsynopsis.style">kr</xsl:param>
<xsl:param name="function.parens" select="0"/>
<xsl:param name="generate.consistent.ids" select="0"/>
<xsl:param name="generate.css.header" select="0"/>
<xsl:param name="generate.id.attributes" select="0"/>
<xsl:param name="generate.index" select="1"/>
<xsl:param name="generate.legalnotice.link" select="0"/>
<xsl:param name="generate.manifest" select="0"/>
<xsl:param name="generate.meta.abstract" select="1"/>
<xsl:param name="generate.revhistory.link" select="0"/>
<xsl:param name="generate.section.toc.level" select="0"/>
<xsl:param name="generate.toc">
appendix  toc,title
article/appendix  nop
article   toc,title
book      toc,title,figure,table,example,equation
chapter   toc,title
part      toc,title
preface   toc,title
qandadiv  toc
qandaset  toc
reference toc,title
sect1     toc
sect2     toc
sect3     toc
sect4     toc
sect5     toc
section   toc
set       toc,title
</xsl:param>

<xsl:param name="glossary.collection"/>
<xsl:param name="glossary.sort" select="0"/>
<xsl:param name="glossentry.show.acronym">no</xsl:param>
<xsl:param name="glossterm.auto.link" select="0"/>
<xsl:param name="graphic.default.extension"/>
<xsl:param name="graphicsize.extension" select="1"/>
<xsl:param name="graphicsize.use.img.src.path" select="0"/>
<xsl:param name="header.rule" select="1"/>
<xsl:param name="highlight.default.language"/>
<xsl:param name="highlight.source" select="0"/>
<xsl:param name="highlight.xslthl.config"/>
<xsl:param name="html.append"/>
<xsl:param name="html.base"/>
<xsl:param name="html.cellpadding"/>
<xsl:param name="html.cellspacing"/>
<xsl:param name="html.cleanup" select="1"/>
<xsl:param name="html.ext">.html</xsl:param>
<xsl:param name="html.extra.head.links" select="0"/>
<xsl:param name="html.head.legalnotice.link.multiple" select="1"/>
<xsl:param name="html.head.legalnotice.link.types">copyright</xsl:param>
<xsl:param name="html.longdesc" select="0"/>
<xsl:param name="html.longdesc.link" select="$html.longdesc"/>
<xsl:param name="html.script"/>
<xsl:param name="html.script.type">text/javascript</xsl:param>
<xsl:param name="html.stylesheet"/>
<xsl:param name="html.stylesheet.type">text/css</xsl:param>
<xsl:param name="htmlhelp.alias.file">alias.h</xsl:param>
<xsl:param name="htmlhelp.autolabel" select="0"/>
<xsl:param name="htmlhelp.button.back" select="1"/>
<xsl:param name="htmlhelp.button.forward" select="0"/>
<xsl:param name="htmlhelp.button.hideshow" select="1"/>
<xsl:param name="htmlhelp.button.home" select="0"/>
<xsl:param name="htmlhelp.button.home.url"/>
<xsl:param name="htmlhelp.button.jump1" select="0"/>
<xsl:param name="htmlhelp.button.jump1.title">User1</xsl:param>
<xsl:param name="htmlhelp.button.jump1.url"/>
<xsl:param name="htmlhelp.button.jump2" select="0"/>
<xsl:param name="htmlhelp.button.jump2.title">User2</xsl:param>
<xsl:param name="htmlhelp.button.jump2.url"/>
<xsl:param name="htmlhelp.button.locate" select="0"/>
<xsl:param name="htmlhelp.button.next" select="1"/>
<xsl:param name="htmlhelp.button.options" select="1"/>
<xsl:param name="htmlhelp.button.prev" select="1"/>
<xsl:param name="htmlhelp.button.print" select="1"/>
<xsl:param name="htmlhelp.button.refresh" select="0"/>
<xsl:param name="htmlhelp.button.stop" select="0"/>
<xsl:param name="htmlhelp.button.zoom" select="0"/>
<xsl:param name="htmlhelp.chm">htmlhelp.chm</xsl:param>
<xsl:param name="htmlhelp.default.topic"/>
<xsl:param name="htmlhelp.display.progress" select="1"/>
<xsl:param name="htmlhelp.encoding">iso-8859-1</xsl:param>
<xsl:param name="htmlhelp.enhanced.decompilation" select="0"/>
<xsl:param name="htmlhelp.enumerate.images" select="0"/>
<xsl:param name="htmlhelp.force.map.and.alias" select="0"/>
<xsl:param name="htmlhelp.hhc.binary" select="1"/>
<xsl:param name="htmlhelp.hhc.folders.instead.books" select="1"/>
<xsl:param name="htmlhelp.hhc">toc.hhc</xsl:param>
<xsl:param name="htmlhelp.hhc.section.depth">5</xsl:param>
<xsl:param name="htmlhelp.hhc.show.root" select="1"/>
<xsl:param name="htmlhelp.hhc.width"/>
<xsl:param name="htmlhelp.hhk">index.hhk</xsl:param>
<xsl:param name="htmlhelp.hhp">htmlhelp.hhp</xsl:param>
<xsl:param name="htmlhelp.hhp.tail"/>
<xsl:param name="htmlhelp.hhp.window">Main</xsl:param>
<xsl:param name="htmlhelp.hhp.windows"/>
<xsl:param name="htmlhelp.map.file">context.h</xsl:param>
<xsl:param name="htmlhelp.only" select="0"/>
<xsl:param name="htmlhelp.remember.window.position" select="0"/>
<xsl:param name="htmlhelp.show.advanced.search" select="0"/>
<xsl:param name="htmlhelp.show.favorities" select="0"/>
<xsl:param name="htmlhelp.show.menu" select="0"/>
<xsl:param name="htmlhelp.show.toolbar.text" select="1"/>
<xsl:param name="htmlhelp.title"/>
<xsl:param name="htmlhelp.use.hhk" select="0"/>
<xsl:param name="htmlhelp.window.geometry"/>
<xsl:param name="id.warnings" select="0"/>
<xsl:param name="ignore.image.scaling" select="0"/>
<xsl:param name="img.src.path"/>
<xsl:param name="index.links.to.section" select="1"/>
<xsl:param name="index.method">basic</xsl:param>
<xsl:param name="index.number.separator"/>
<xsl:param name="index.on.role" select="0"/>
<xsl:param name="index.on.type" select="0"/>
<xsl:param name="index.prefer.titleabbrev" select="0"/>
<xsl:param name="index.range.separator"/>
<xsl:param name="index.term.separator"/>
<xsl:param name="inherit.keywords" select="1"/>
<xsl:param name="insert.olink.page.number">no</xsl:param>
<xsl:param name="insert.olink.pdf.frag" select="0"/>
<xsl:param name="insert.xref.page.number">no</xsl:param>
<xsl:param name="javahelp.encoding">iso-8859-1</xsl:param>
<xsl:param name="keep.relative.image.uris" select="1"/>

<xsl:param name="l10n.gentext.default.language">en</xsl:param>
<xsl:param name="l10n.gentext.language"/>
<xsl:param name="l10n.gentext.use.xref.language" select="0"/>
<xsl:param name="l10n.lang.value.rfc.compliant" select="1"/>
<xsl:param name="label.from.part" select="0"/>
<xsl:param name="linenumbering.everyNth">5</xsl:param>
<xsl:param name="linenumbering.extension" select="1"/>
<xsl:param name="linenumbering.separator"><xsl:text> </xsl:text></xsl:param>
<xsl:param name="linenumbering.width">3</xsl:param>
<xsl:param name="link.mailto.url"/>
<xsl:param name="make.clean.html" select="0"/>
<xsl:param name="make.graphic.viewport" select="1"/>
<xsl:param name="make.single.year.ranges" select="0"/>
<xsl:param name="make.valid.html" select="1"/>
<xsl:param name="make.year.ranges" select="0"/>
    <xsl:param name="manifest">HTML.manifest</xsl:param>
    
<xsl:param name="manifest.in.base.dir" select="0"/>
<xsl:param name="manual.toc"/>
<xsl:param name="menuchoice.menu.separator"> &#8594; </xsl:param>
<xsl:param name="menuchoice.separator">+</xsl:param>
<xsl:param name="navig.graphics.extension">.gif</xsl:param>
<xsl:param name="navig.graphics" select="0"/>
<xsl:param name="navig.graphics.path">images/</xsl:param>
<xsl:param name="navig.showtitles">1</xsl:param>
<xsl:param name="nominal.image.depth" select="4 * $pixels.per.inch"/>
<xsl:param name="nominal.image.width" select="6 * $pixels.per.inch"/>
<xsl:param name="nominal.table.width">6in</xsl:param>
<xsl:param name="olink.base.uri"/>
<xsl:param name="olink.debug" select="0"/>
<xsl:param name="olink.doctitle">no</xsl:param> 
<xsl:param name="olink.lang.fallback.sequence"/>
<xsl:attribute-set name="olink.properties">
  <xsl:attribute name="show-destination">replace</xsl:attribute>
</xsl:attribute-set>
<xsl:param name="othercredit.like.author.enabled">0</xsl:param>
<xsl:param name="para.propagates.style" select="1"/>
<xsl:param name="part.autolabel">I</xsl:param>
<xsl:param name="phrase.propagates.style" select="1"/>
<xsl:param name="pixels.per.inch">90</xsl:param>
<xsl:param name="points.per.em">10</xsl:param>
<xsl:param name="preface.autolabel" select="0"/>
<xsl:param name="prefer.internal.olink" select="0"/>
<xsl:param name="preferred.mediaobject.role"/>
<xsl:param name="process.empty.source.toc" select="0"/>
<xsl:param name="process.source.toc" select="0"/>
<xsl:param name="profile.arch"/>
<xsl:param name="profile.attribute"/>
<xsl:param name="profile.audience"/>
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
<xsl:param name="qanda.inherit.numeration" select="1"/>
<xsl:param name="qanda.nested.in.toc" select="0"/>
<xsl:param name="qandadiv.autolabel" select="1"/>
<xsl:param name="refclass.suppress" select="0"/>
<xsl:param name="refentry.generate.name" select="1"/>
<xsl:param name="refentry.generate.title" select="0"/>
<xsl:param name="refentry.separator" select="1"/>
<xsl:param name="refentry.xref.manvolnum" select="1"/>
  <xsl:param name="reference.autolabel">I</xsl:param>
<xsl:param name="root.filename">index</xsl:param>
<xsl:param name="rootid"/>
<xsl:param name="runinhead.default.title.end.punct">.</xsl:param>
<xsl:param name="runinhead.title.end.punct">.!?:</xsl:param>
<xsl:param name="section.autolabel" select="0"/>
<xsl:param name="section.autolabel.max.depth">8</xsl:param>
<xsl:param name="section.label.includes.component.label" select="0"/>
<xsl:param name="segmentedlist.as.table" select="0"/>
<xsl:param name="shade.verbatim" select="0"/>
<xsl:attribute-set name="shade.verbatim.style">
  <xsl:attribute name="border">0</xsl:attribute>
  <xsl:attribute name="style"><xsl:text>background-color: </xsl:text>#E0E0E0</xsl:attribute>
</xsl:attribute-set>

<xsl:param name="show.comments" select="1"/>
<xsl:param name="show.revisionflag" select="0"/>
<xsl:param name="simplesect.in.toc" select="0"/>
<xsl:param name="spacing.paras" select="0"/>
<xsl:param name="suppress.footer.navigation">0</xsl:param>
<xsl:param name="suppress.header.navigation" select="0"/>
<xsl:param name="suppress.navigation" select="0"/>
<xsl:param name="table.borders.with.css" select="0"/>
<xsl:param name="table.cell.border.color"/>

<xsl:param name="table.cell.border.style">solid</xsl:param>
<xsl:param name="table.cell.border.thickness">0.5pt</xsl:param>
<xsl:param name="table.footnote.number.format">a</xsl:param>
<xsl:param name="table.footnote.number.symbols"/>
<xsl:param name="table.frame.border.color"/>

<xsl:param name="table.frame.border.style">solid</xsl:param>
<xsl:param name="table.frame.border.thickness">0.5pt</xsl:param>
<xsl:param name="tablecolumns.extension" select="1"/>
 <xsl:param name="target.database.document">olinkdb.xml</xsl:param>
<xsl:param name="targets.filename">target.db</xsl:param>
<xsl:param name="tex.math.delims" select="1"/>
<xsl:param name="tex.math.file">tex-math-equations.tex</xsl:param>
<xsl:param name="tex.math.in.alt"/>
<xsl:param name="textdata.default.encoding"/>
  <xsl:param name="textinsert.extension" select="1"/>
<xsl:param name="toc.list.type">dl</xsl:param>
<xsl:param name="toc.max.depth">8</xsl:param>
<xsl:param name="toc.section.depth">2</xsl:param>
<xsl:param name="ulink.target"/>
<xsl:param name="use.embed.for.svg" select="0"/>
<xsl:param name="use.extensions" select="0"/>
<xsl:param name="use.id.as.filename" select="0"/>
<xsl:param name="use.local.olink.style" select="0"/> 
<xsl:param name="use.role.as.xrefstyle" select="1"/>
<xsl:param name="use.role.for.mediaobject" select="1"/>
<xsl:param name="use.svg" select="1"/>
<xsl:param name="variablelist.as.table" select="0"/>
<xsl:param name="variablelist.term.break.after">0</xsl:param>
<xsl:param name="variablelist.term.separator">, </xsl:param>
<xsl:param name="webhelp.autolabel">0</xsl:param>
<xsl:param name="webhelp.base.dir">docs</xsl:param>
<xsl:param name="webhelp.common.dir">../common/</xsl:param>
<xsl:param name="webhelp.default.topic">index.html</xsl:param>
<xsl:param name="webhelp.include.search.tab">true</xsl:param>
<xsl:param name="webhelp.indexer.language">en</xsl:param>
<xsl:param name="webhelp.start.filename">index.html</xsl:param>
<xsl:param name="webhelp.tree.cookie.id" select="concat( 'treeview-', count(//node()) )"/>
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
<xsl:param name="xref.label-page.separator"><xsl:text> </xsl:text></xsl:param>
<xsl:param name="xref.label-title.separator">: </xsl:param>
<xsl:param name="xref.title-page.separator"><xsl:text> </xsl:text></xsl:param>
<xsl:param name="xref.with.number.and.title" select="1"/>

</xsl:stylesheet>