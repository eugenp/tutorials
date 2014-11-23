<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

<!-- This file is generated from param.xweb -->

<!-- ********************************************************************
     $Id: param.xweb 9130 2011-10-11 08:05:37Z dpawson $
     ********************************************************************

     This file is part of the XSL DocBook Stylesheet distribution.
     See ../README or http://docbook.sf.net/release/xsl/current/ for
     copyright and other information.

     ******************************************************************** -->

<xsl:param name="man.authors.section.enabled">1</xsl:param>
<xsl:param name="man.break.after.slash">0</xsl:param>
<xsl:param name="man.base.url.for.relative.links">[set $man.base.url.for.relative.links]/</xsl:param>
<xsl:param name="man.charmap.enabled" select="1"/>
<xsl:param name="man.charmap.subset.profile">
@*[local-name() = 'block'] = 'Miscellaneous Technical' or
(@*[local-name() = 'block'] = 'C1 Controls And Latin-1 Supplement (Latin-1 Supplement)' and
 (@*[local-name() = 'class'] = 'symbols' or
  @*[local-name() = 'class'] = 'letters')
) or
@*[local-name() = 'block'] = 'Latin Extended-A'
or
(@*[local-name() = 'block'] = 'General Punctuation' and
 (@*[local-name() = 'class'] = 'spaces' or
  @*[local-name() = 'class'] = 'dashes' or
  @*[local-name() = 'class'] = 'quotes' or
  @*[local-name() = 'class'] = 'bullets'
 )
) or
@*[local-name() = 'name'] = 'HORIZONTAL ELLIPSIS' or
@*[local-name() = 'name'] = 'WORD JOINER' or
@*[local-name() = 'name'] = 'SERVICE MARK' or
@*[local-name() = 'name'] = 'TRADE MARK SIGN' or
@*[local-name() = 'name'] = 'ZERO WIDTH NO-BREAK SPACE'
</xsl:param>
<xsl:param name="man.charmap.subset.profile.english">
@*[local-name() = 'block'] = 'Miscellaneous Technical' or
(@*[local-name() = 'block'] = 'C1 Controls And Latin-1 Supplement (Latin-1 Supplement)' and
 @*[local-name() = 'class'] = 'symbols')
or
(@*[local-name() = 'block'] = 'General Punctuation' and
 (@*[local-name() = 'class'] = 'spaces' or
  @*[local-name() = 'class'] = 'dashes' or
  @*[local-name() = 'class'] = 'quotes' or
  @*[local-name() = 'class'] = 'bullets'
 )
) or
@*[local-name() = 'name'] = 'HORIZONTAL ELLIPSIS' or
@*[local-name() = 'name'] = 'WORD JOINER' or
@*[local-name() = 'name'] = 'SERVICE MARK' or
@*[local-name() = 'name'] = 'TRADE MARK SIGN' or
@*[local-name() = 'name'] = 'ZERO WIDTH NO-BREAK SPACE'
</xsl:param>
<xsl:param name="man.charmap.uri"/>
<xsl:param name="man.charmap.use.subset" select="1"/>
<xsl:param name="man.copyright.section.enabled">1</xsl:param>
<xsl:param name="man.endnotes.are.numbered">1</xsl:param>
<xsl:param name="man.endnotes.list.enabled">1</xsl:param>
<xsl:param name="man.endnotes.list.heading"/>
  <xsl:param name="man.font.funcprototype">BI</xsl:param>
  <xsl:param name="man.font.funcsynopsisinfo">B</xsl:param>
<xsl:param name="man.font.links">B</xsl:param>
  <xsl:param name="man.font.table.headings">B</xsl:param>
  <xsl:param name="man.font.table.title">B</xsl:param>
<xsl:param name="man.funcsynopsis.style">ansi</xsl:param>
<xsl:param name="man.hyphenate.computer.inlines">0</xsl:param>
<xsl:param name="man.hyphenate.filenames">0</xsl:param>
<xsl:param name="man.hyphenate">0</xsl:param>
<xsl:param name="man.hyphenate.urls">0</xsl:param>
<xsl:param name="man.indent.blurbs" select="1"/>
<xsl:param name="man.indent.lists" select="1"/>
<xsl:param name="man.indent.refsect" select="0"/>
<xsl:param name="man.indent.verbatims" select="1"/>
<xsl:param name="man.indent.width">4</xsl:param>
<xsl:param name="man.justify">0</xsl:param>
<xsl:param name="man.output.base.dir">man/</xsl:param>
<xsl:param name="man.output.encoding">UTF-8</xsl:param>
<xsl:param name="man.output.in.separate.dir" select="0"/>
<xsl:param name="man.output.lang.in.name.enabled" select="0"/>
<xsl:param name="man.output.manifest.enabled" select="0"/>
<xsl:param name="man.output.manifest.filename">MAN.MANIFEST</xsl:param>
<xsl:param name="man.output.better.ps.enabled">0</xsl:param>
<xsl:param name="man.output.quietly" select="0"/>
<xsl:param name="man.output.subdirs.enabled" select="1"/>
<xsl:param name="man.segtitle.suppress" select="0"/>
<xsl:param name="man.string.subst.map">

  <!-- * remove no-break marker at beginning of line (stylesheet artifact) --> 
  <substitution oldstring="&#x2592;&#x2580;" newstring="&#x2592;"/>
  <!-- * replace U+2580 no-break marker (stylesheet-added) w/ no-break space -->
  <substitution oldstring="&#x2580;" newstring="\ "/>

  <!-- ==================================================================== -->

  <!-- * squeeze multiple newlines before a roff request  -->
  <substitution oldstring="&#10;&#10;." newstring="&#10;."/>
  <!-- * remove any .sp instances that directly precede a .PP  -->
  <substitution oldstring=".sp&#10;.PP" newstring=".PP"/>
  <!-- * remove any .sp instances that directly follow a .PP  -->
  <substitution oldstring=".sp&#10;.sp" newstring=".sp"/>
  <!-- * squeeze multiple .sp instances into a single .sp-->
  <substitution oldstring=".PP&#10;.sp" newstring=".PP"/>
  <!-- * squeeze multiple newlines after start of no-fill (verbatim) env. -->
  <substitution oldstring=".nf&#10;&#10;" newstring=".nf&#10;"/>
  <!-- * squeeze multiple newlines after REstoring margin -->
  <substitution oldstring=".RE&#10;&#10;" newstring=".RE&#10;"/>
  <!-- * U+2591 is a marker we add before and after every Parameter in -->
  <!-- * Funcprototype output -->
  <substitution oldstring="&#x2591;" newstring=" "/>
  <!-- * U+2592 is a marker we add for the newline before output of <sbr>; -->
  <substitution oldstring="&#x2592;" newstring="&#10;"/>
  <!-- * -->
  <!-- * Now deal with some other characters that are added by the -->
  <!-- * stylesheets during processing. -->
  <!-- * -->
  <!-- * bullet -->
  <substitution oldstring="&#x2022;" newstring="\(bu"/>
  <!-- * left double quote -->
  <substitution oldstring="&#x201C;" newstring="\(lq"/>
  <!-- * right double quote -->
  <substitution oldstring="&#x201D;" newstring="\(rq"/>
  <!-- * left single quote -->
  <substitution oldstring="&#x2018;" newstring="\(oq"/>
  <!-- * right single quote -->
  <substitution oldstring="&#x2019;" newstring="\(cq"/>
  <!-- * copyright sign -->
  <substitution oldstring="&#xA9;" newstring="\(co"/>
  <!-- * registered sign -->
  <substitution oldstring="&#xAE;" newstring="\(rg"/>
  <!-- * ...servicemark... -->
  <!-- * There is no groff equivalent for it. -->
  <substitution oldstring="&#x2120;" newstring="(SM)"/>
  <!-- * ...trademark... -->
  <!-- * We don't do "\(tm" because for console output, -->
  <!-- * groff just renders that as "tm"; that is: -->
  <!-- * -->
  <!-- *   Product&#x2122; -> Producttm -->
  <!-- * -->
  <!-- * So we just make it to "(TM)" instead; thus: -->
  <!-- * -->
  <!-- *   Product&#x2122; -> Product(TM) -->
  <substitution oldstring="&#x2122;" newstring="(TM)"/>

</xsl:param>
<xsl:param name="man.string.subst.map.local.post"/>
  <xsl:param name="man.string.subst.map.local.pre"/>
<xsl:param name="man.subheading.divider.enabled">0</xsl:param>
<xsl:param name="man.subheading.divider">========================================================================</xsl:param>
<xsl:param name="man.table.footnotes.divider">----</xsl:param>
<xsl:param name="man.th.extra1.suppress">0</xsl:param>
<xsl:param name="man.th.extra2.max.length">30</xsl:param>
<xsl:param name="man.th.extra2.suppress">0</xsl:param>
<xsl:param name="man.th.extra3.max.length">30</xsl:param>
<xsl:param name="man.th.extra3.suppress">0</xsl:param>
<xsl:param name="man.th.title.max.length">20</xsl:param>
<xsl:param name="refentry.date.profile.enabled">0</xsl:param>
<xsl:param name="refentry.date.profile">
  (($info[//date])[last()]/date)[1]|
  (($info[//pubdate])[last()]/pubdate)[1]
</xsl:param>
<xsl:param name="refentry.manual.fallback.profile">
refmeta/refmiscinfo[not(@class = 'date')][1]/node()</xsl:param>
<xsl:param name="refentry.manual.profile.enabled">0</xsl:param>
<xsl:param name="refentry.manual.profile">
  (($info[//title])[last()]/title)[1]|
  ../title/node()
</xsl:param>
<xsl:param name="refentry.meta.get.quietly" select="0"/>
<xsl:param name="refentry.source.fallback.profile">
refmeta/refmiscinfo[not(@class = 'date')][1]/node()</xsl:param>
<xsl:param name="refentry.source.name.profile.enabled">0</xsl:param>
<xsl:param name="refentry.source.name.profile">
  (($info[//productname])[last()]/productname)[1]|
  (($info[//corpname])[last()]/corpname)[1]|
  (($info[//corpcredit])[last()]/corpcredit)[1]|
  (($info[//corpauthor])[last()]/corpauthor)[1]|
  (($info[//orgname])[last()]/orgname)[1]|
  (($info[//publishername])[last()]/publishername)[1]
</xsl:param>
<xsl:param name="refentry.source.name.suppress">0</xsl:param>
<xsl:param name="refentry.version.profile.enabled">0</xsl:param>
<xsl:param name="refentry.version.profile">
  (($info[//productnumber])[last()]/productnumber)[1]|
  (($info[//edition])[last()]/edition)[1]|
  (($info[//releaseinfo])[last()]/releaseinfo)[1]
</xsl:param>
<xsl:param name="refentry.version.suppress">0</xsl:param>
</xsl:stylesheet>

