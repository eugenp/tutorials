<?xml version="1.0"?>
<!DOCTYPE xsl:stylesheet [
<!ENTITY lf '<xsl:text xmlns:xsl="http://www.w3.org/1999/XSL/Transform">&#xA;</xsl:text>'>
]>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns:doc="http://nwalsh.com/xsl/documentation/1.0"
  xmlns:exsl="http://exslt.org/common"
  xmlns:set="http://exslt.org/sets"
  xmlns:h="urn:x-hex"
  xmlns:ng="http://docbook.org/docbook-ng"
  xmlns:db="http://docbook.org/ns/docbook"
  version="1.0"
  exclude-result-prefixes="doc exsl set h db ng">

<!-- ********************************************************************
     $Id: htmlhelp-common.xsl 9151 2011-11-12 00:16:19Z bobstayton $
     ******************************************************************** -->

<!-- ==================================================================== -->
<!-- Customizations of standard HTML stylesheet parameters -->

<!-- no navigation on pages by default, HTML Help provides its own navigation controls -->
<xsl:param name="suppress.navigation" select="1"/>

<!-- no separate HTML page with index, index is built inside CHM index pane -->
<xsl:param name="generate.index" select="0"/>

<!-- ==================================================================== -->

<xsl:param name="htmlhelp.generate.index" select="//indexterm[1]|//db:indexterm[1]|//ng:indexterm[1]"/>
  
<!-- Set up HTML Help flag -->
<xsl:variable name="htmlhelp.output" select="1"/>

<!-- ==================================================================== -->

<xsl:template match="/">

  <!-- * Get a title for current doc so that we let the user -->
  <!-- * know what document we are processing at this point. -->
  <xsl:variable name="doc.title">
    <xsl:call-template name="get.doc.title"/>
  </xsl:variable>
  <xsl:choose>
    <!-- Hack! If someone hands us a DocBook V5.x or DocBook NG document,
         toss the namespace and continue.  Use the docbook5 namespaced
         stylesheets for DocBook5 if you don't want to use this feature.-->
    <xsl:when test="$exsl.node.set.available != 0
                    and (*/self::ng:* or */self::db:*)">
      <xsl:call-template name="log.message">
        <xsl:with-param name="level">Note</xsl:with-param>
        <xsl:with-param name="source" select="$doc.title"/>
        <xsl:with-param name="context-desc">
          <xsl:text>namesp. cut</xsl:text>
        </xsl:with-param>
        <xsl:with-param name="message">
          <xsl:text>stripped namespace before processing</xsl:text>
        </xsl:with-param>
      </xsl:call-template>
      <xsl:variable name="nons">
        <xsl:apply-templates mode="stripNS"/>
      </xsl:variable>
      <xsl:call-template name="log.message">
        <xsl:with-param name="level">Note</xsl:with-param>
        <xsl:with-param name="source" select="$doc.title"/>
        <xsl:with-param name="context-desc">
          <xsl:text>namesp. cut</xsl:text>
        </xsl:with-param>
        <xsl:with-param name="message">
          <xsl:text>processing stripped document</xsl:text>
        </xsl:with-param>
      </xsl:call-template>
      <xsl:apply-templates select="exsl:node-set($nons)"/>
    </xsl:when>
    <xsl:otherwise>
  <xsl:if test="$htmlhelp.only != 1">
    <xsl:choose>
      <xsl:when test="$rootid != ''">
        <xsl:choose>
          <xsl:when test="count(key('id',$rootid)) = 0">
            <xsl:message terminate="yes">
              <xsl:text>ID '</xsl:text>
              <xsl:value-of select="$rootid"/>
              <xsl:text>' not found in document.</xsl:text>
            </xsl:message>
          </xsl:when>
          <xsl:otherwise>
            <xsl:message>Formatting from <xsl:value-of select="$rootid"/></xsl:message>
            <xsl:apply-templates select="key('id',$rootid)" mode="process.root"/>
          </xsl:otherwise>
        </xsl:choose>
      </xsl:when>
      <xsl:otherwise>
        <xsl:if test="$collect.xref.targets = 'yes' or
                      $collect.xref.targets = 'only'">
          <xsl:apply-templates select="/" mode="collect.targets"/>
        </xsl:if>
        <xsl:if test="$collect.xref.targets != 'only'">
          <xsl:apply-templates select="/" mode="process.root"/>
        </xsl:if>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:if>


  <xsl:if test="$collect.xref.targets != 'only'">
    <xsl:call-template name="hhp"/>
    <xsl:call-template name="hhc"/>
    <xsl:if test="($rootid = '' and //processing-instruction('dbhh')) or
                  ($rootid != '' and key('id',$rootid)//processing-instruction('dbhh'))">
      <xsl:call-template name="hh-map"/>
      <xsl:call-template name="hh-alias"/>
    </xsl:if>
    <xsl:if test="$htmlhelp.generate.index">
      <xsl:call-template name="hhk"/>
    </xsl:if>
  </xsl:if>
</xsl:otherwise>
</xsl:choose>
</xsl:template>

<!-- ==================================================================== -->

<xsl:template name="hhp">
  <xsl:call-template name="write.text.chunk">
    <xsl:with-param name="filename">
      <xsl:if test="$manifest.in.base.dir != 0">
        <xsl:value-of select="$chunk.base.dir"/>
      </xsl:if>
      <xsl:value-of select="$htmlhelp.hhp"/>
    </xsl:with-param>
    <xsl:with-param name="method" select="'text'"/>
    <xsl:with-param name="content">
      <xsl:call-template name="hhp-main"/>
    </xsl:with-param>
    <xsl:with-param name="encoding" select="$htmlhelp.encoding"/>
    <xsl:with-param name="quiet" select="$chunk.quietly"/>
  </xsl:call-template>
</xsl:template>

<!-- ==================================================================== -->
<xsl:template name="hhp-main">

  <xsl:variable name="raw.help.title">
    <xsl:choose>
      <xsl:when test="$htmlhelp.title = ''">
        <xsl:choose>
          <xsl:when test="$rootid != ''">
            <xsl:apply-templates select="key('id',$rootid)" mode="title.markup"/>
          </xsl:when>
          <xsl:otherwise>
            <xsl:apply-templates select="/*" mode="title.markup"/>
          </xsl:otherwise>
        </xsl:choose>
      </xsl:when>
      <xsl:otherwise>
        <xsl:value-of select="$htmlhelp.title"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:variable name="help.title" select="normalize-space($raw.help.title)"/>
  
<xsl:variable name="default.topic">
  <xsl:choose>
    <xsl:when test="$htmlhelp.default.topic != ''">
      <xsl:value-of select="$htmlhelp.default.topic"/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:call-template name="make-relative-filename">
        <xsl:with-param name="base.dir">
          <xsl:if test="$manifest.in.base.dir = 0">
            <xsl:value-of select="$chunk.base.dir"/>
          </xsl:if>
        </xsl:with-param>
        <xsl:with-param name="base.name">
          <xsl:choose>
            <xsl:when test="$rootid != ''">
              <xsl:apply-templates select="key('id',$rootid)" mode="chunk-filename"/>
            </xsl:when>
            <xsl:otherwise>
              <xsl:apply-templates select="/" mode="chunk-filename"/>
            </xsl:otherwise>
          </xsl:choose>
        </xsl:with-param>
      </xsl:call-template>
    </xsl:otherwise>
  </xsl:choose>
</xsl:variable>
<xsl:variable name="xnavigation">
  <xsl:text>0x</xsl:text>
  <xsl:call-template name="toHex">
    <xsl:with-param name="n" select="9504 + $htmlhelp.show.menu * 65536
                                          + $htmlhelp.show.advanced.search * 131072
                                          + $htmlhelp.show.favorities * 4096
                                          + (1 - $htmlhelp.show.toolbar.text) * 64
                                          + $htmlhelp.remember.window.position * 262144"/>
  </xsl:call-template>
</xsl:variable>
<xsl:variable name="xbuttons">
  <xsl:text>0x</xsl:text>
  <xsl:call-template name="toHex">
    <xsl:with-param name="n" select="0 + $htmlhelp.button.hideshow * 2
                                       + $htmlhelp.button.back * 4
                                       + $htmlhelp.button.forward * 8
                                       + $htmlhelp.button.stop * 16
                                       + $htmlhelp.button.refresh * 32
                                       + $htmlhelp.button.home * 64
                                       + $htmlhelp.button.options * 4096
                                       + $htmlhelp.button.print * 8192
                                       + $htmlhelp.button.locate * 2048
                                       + $htmlhelp.button.jump1 * 262144
                                       + $htmlhelp.button.jump2 * 524288
                                       + $htmlhelp.button.next * 2097152
                                       + $htmlhelp.button.prev * 4194304
                                       + $htmlhelp.button.zoom * 1048576"/>
  </xsl:call-template>
</xsl:variable>
<xsl:text>[OPTIONS]
</xsl:text>
<xsl:if test="$htmlhelp.generate.index">
<xsl:text>Auto Index=Yes
</xsl:text></xsl:if>
<xsl:if test="$htmlhelp.hhc.binary != 0">
<xsl:text>Binary TOC=Yes
</xsl:text></xsl:if>
<xsl:text>Compatibility=1.1 or later
Compiled file=</xsl:text><xsl:value-of select="$htmlhelp.chm"/><xsl:text>
Contents file=</xsl:text><xsl:value-of select="$htmlhelp.hhc"/><xsl:text>
</xsl:text>
<xsl:if test="$htmlhelp.hhp.window != ''">
<xsl:text>Default Window=</xsl:text><xsl:value-of select="$htmlhelp.hhp.window"/><xsl:text>
</xsl:text></xsl:if>
<xsl:text>Default topic=</xsl:text><xsl:value-of select="$default.topic"/>
<xsl:text>
Display compile progress=</xsl:text>
  <xsl:choose>
    <xsl:when test="$htmlhelp.display.progress != 1">
      <xsl:text>No</xsl:text>
    </xsl:when>
    <xsl:otherwise>
      <xsl:text>Yes</xsl:text>
    </xsl:otherwise>
  </xsl:choose>
<xsl:text>
Full-text search=Yes
</xsl:text>
<xsl:if test="$htmlhelp.generate.index">
<xsl:text>Index file=</xsl:text><xsl:value-of select="$htmlhelp.hhk"/><xsl:text>
</xsl:text></xsl:if>
<xsl:text>Language=</xsl:text>
<xsl:for-each select="*">   <!-- Change context from / to root element -->
  <xsl:call-template name="gentext.template">
    <xsl:with-param name="context" select="'htmlhelp'"/>
    <xsl:with-param name="name" select="'langcode'"/>
  </xsl:call-template>
</xsl:for-each>
<xsl:text>
Title=</xsl:text>
  <xsl:value-of select="$help.title"/>
<xsl:text>
Enhanced decompilation=</xsl:text>
  <xsl:choose>
    <xsl:when test="$htmlhelp.enhanced.decompilation != 0">
      <xsl:text>Yes</xsl:text>
    </xsl:when>
    <xsl:otherwise>
      <xsl:text>No</xsl:text>
    </xsl:otherwise>
  </xsl:choose>

<xsl:if test="$htmlhelp.hhp.window != ''">
  <xsl:text>

[WINDOWS]
</xsl:text>
<xsl:value-of select="$htmlhelp.hhp.window"/>
<xsl:text>="</xsl:text>
<xsl:value-of select="$help.title"/>
<xsl:text>","</xsl:text><xsl:value-of select="$htmlhelp.hhc"/>
<xsl:text>",</xsl:text>
<xsl:if test="$htmlhelp.generate.index">
  <xsl:text>"</xsl:text>
  <xsl:value-of select="$htmlhelp.hhk"/>
  <xsl:text>"</xsl:text>
</xsl:if>
<xsl:text>,"</xsl:text>
<xsl:value-of select="$default.topic"/>
<xsl:text>",</xsl:text>
<xsl:text>"</xsl:text>
<xsl:choose>
  <xsl:when test="$htmlhelp.button.home != 0">
    <xsl:value-of select="$htmlhelp.button.home.url"/>
  </xsl:when>
  <xsl:otherwise>
    <xsl:value-of select="$default.topic"/>
  </xsl:otherwise>
</xsl:choose>
<xsl:text>"</xsl:text>
<xsl:text>,</xsl:text>
<xsl:if test="$htmlhelp.button.jump1 != 0">
  <xsl:text>"</xsl:text>
  <xsl:value-of select="$htmlhelp.button.jump1.url"/>
  <xsl:text>"</xsl:text>
</xsl:if>
<xsl:text>,</xsl:text>
<xsl:if test="$htmlhelp.button.jump1 != 0">
  <xsl:text>"</xsl:text>
  <xsl:value-of select="$htmlhelp.button.jump1.title"/>
  <xsl:text>"</xsl:text>
</xsl:if>
<xsl:text>,</xsl:text>
<xsl:if test="$htmlhelp.button.jump2 != 0">
  <xsl:text>"</xsl:text>
  <xsl:value-of select="$htmlhelp.button.jump2.url"/>
  <xsl:text>"</xsl:text>
</xsl:if>
<xsl:text>,</xsl:text>
<xsl:if test="$htmlhelp.button.jump2 != 0">
  <xsl:text>"</xsl:text>
  <xsl:value-of select="$htmlhelp.button.jump2.title"/>
  <xsl:text>"</xsl:text>
</xsl:if>
<xsl:text>,</xsl:text>
<xsl:value-of select="$xnavigation"/>
<xsl:text>,</xsl:text><xsl:value-of select="$htmlhelp.hhc.width"/><xsl:text>,</xsl:text>
<xsl:value-of select="$xbuttons"/>
<xsl:text>,</xsl:text><xsl:value-of select="$htmlhelp.window.geometry"/><xsl:text>,,,,,,,0
</xsl:text>
</xsl:if>

<!-- 
  Needs more investigation to generate propetly all fields 
<xsl:text>search="</xsl:text>
<xsl:value-of select="normalize-space(//title[1])"/>
<xsl:text>","toc.hhc","index.hhk","</xsl:text>
<xsl:value-of select="$root.filename"/>
<xsl:text>.html","</xsl:text>
<xsl:value-of select="$root.filename"/>
<xsl:text>.html",,,,,</xsl:text>
<xsl:value-of select="$xnavigation"/>
<xsl:text>,</xsl:text>
<xsl:value-of select="$htmlhelp.hhc.width"/>
<xsl:text>,</xsl:text>
<xsl:value-of select="$xbuttons"/>
<xsl:text>,</xsl:text>
<xsl:value-of select="$htmlhelp.window.geometry"/>
<xsl:text>,,,,,2,,0
</xsl:text>
-->

<xsl:if test="$htmlhelp.hhp.windows">
  <xsl:value-of select="$htmlhelp.hhp.windows"/>
</xsl:if>
<xsl:text>

[FILES]
</xsl:text>

<xsl:choose>
  <xsl:when test="$rootid != ''">
    <xsl:apply-templates select="key('id',$rootid)" mode="enumerate-files"/>
  </xsl:when>
  <xsl:otherwise>
    <xsl:apply-templates select="/" mode="enumerate-files"/>
  </xsl:otherwise>
</xsl:choose>

<xsl:if test="$htmlhelp.enumerate.images">
  <xsl:variable name="imagelist">
    <xsl:choose>
      <xsl:when test="$rootid != ''">
        <xsl:apply-templates select="key('id',$rootid)" mode="enumerate-images"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:apply-templates select="/" mode="enumerate-images"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>
  <xsl:choose>
    <xsl:when test="$exsl.node.set.available != 0
                    and function-available('set:distinct')">
      <xsl:for-each select="set:distinct(exsl:node-set($imagelist)/filename)">
        <xsl:value-of select="."/>
        <xsl:text>&#10;</xsl:text>
      </xsl:for-each>
    </xsl:when>
    <xsl:otherwise>
      <xsl:value-of select="$imagelist"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:if>

<xsl:if test="($htmlhelp.force.map.and.alias != 0) or 
              ($rootid = '' and //processing-instruction('dbhh')) or
              ($rootid != '' and key('id',$rootid)//processing-instruction('dbhh'))">
  <xsl:text>
[ALIAS]
#include </xsl:text><xsl:value-of select="$htmlhelp.alias.file"/><xsl:text>

[MAP]
#include </xsl:text><xsl:value-of select="$htmlhelp.map.file"/><xsl:text>
</xsl:text>
</xsl:if>

<xsl:value-of select="$htmlhelp.hhp.tail"/>
</xsl:template>

<!-- ==================================================================== -->

<xsl:template match="graphic|inlinegraphic[@format!='linespecific']" mode="enumerate-images">
  <xsl:call-template name="write.filename.enumerate-images">
    <xsl:with-param name="filename">
      <xsl:call-template name="mediaobject.filename.enumerate-images">
        <xsl:with-param name="object" select="."/>
      </xsl:call-template>  
    </xsl:with-param>
  </xsl:call-template>
</xsl:template>

<xsl:template match="mediaobject|inlinemediaobject" mode="enumerate-images">
  <xsl:call-template name="select.mediaobject.enumerate-images"/>
</xsl:template>

<xsl:template name="select.mediaobject.enumerate-images">
  <xsl:param name="olist"
             select="imageobject|imageobjectco
                     |videoobject|audioobject|textobject"/>
  <xsl:param name="count">1</xsl:param>

  <xsl:if test="$count &lt;= count($olist)">
    <xsl:variable name="object" select="$olist[position()=$count]"/>

    <xsl:variable name="useobject">
      <xsl:choose>
        <!-- The phrase is never used -->
        <xsl:when test="name($object)='textobject' and $object/phrase">
          <xsl:text>0</xsl:text>
        </xsl:when>
        <!-- The first textobject is a reasonable fallback (but not for image in HH) -->
        <xsl:when test="name($object)='textobject'">
          <xsl:text>0</xsl:text>
        </xsl:when>
        <!-- If there's only one object, use it -->
        <xsl:when test="$count = 1 and count($olist) = 1">
          <xsl:text>1</xsl:text>
        </xsl:when>
        <!-- Otherwise, see if this one is a useable graphic -->
        <xsl:otherwise>
          <xsl:choose>
            <!-- peek inside imageobjectco to simplify the test -->
            <xsl:when test="local-name($object) = 'imageobjectco'">
              <xsl:call-template name="is.acceptable.mediaobject">
                <xsl:with-param name="object" select="$object/imageobject"/>
              </xsl:call-template>
            </xsl:when>
            <xsl:otherwise>
              <xsl:call-template name="is.acceptable.mediaobject">
                <xsl:with-param name="object" select="$object"/>
              </xsl:call-template>
            </xsl:otherwise>
          </xsl:choose>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:variable>

    <xsl:choose>
      <xsl:when test="$useobject='1' and $object[not(*/@format='linespecific')]">
        <xsl:call-template name="write.filename.enumerate-images">
          <xsl:with-param name="filename">
            <xsl:call-template name="mediaobject.filename.enumerate-images">
              <xsl:with-param name="object" select="$object"/>
            </xsl:call-template>
          </xsl:with-param>
        </xsl:call-template>
      </xsl:when>
      <xsl:otherwise>
        <xsl:call-template name="select.mediaobject.enumerate-images">
          <xsl:with-param name="olist" select="$olist"/>
          <xsl:with-param name="count" select="$count + 1"/>
        </xsl:call-template>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:if>
</xsl:template>

<xsl:template name="mediaobject.filename.enumerate-images">
  <xsl:param name="object"/>

  <xsl:variable name="urifilename">
    <xsl:call-template name="mediaobject.filename">
      <xsl:with-param name="object" select="$object"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:variable name="filename">
    <xsl:choose>
      <xsl:when test="starts-with($urifilename, 'file:/')">
        <xsl:value-of select="substring-after($urifilename, 'file:/')"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:value-of select="$urifilename"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:value-of select="translate($filename, '/', '\')"/>

</xsl:template>

<xsl:template match="text()" mode="enumerate-images">
</xsl:template>

<xsl:template name="write.filename.enumerate-images">
  <xsl:param name="filename"/>
  <xsl:choose>
    <xsl:when test="function-available('exsl:node-set') and function-available('set:distinct')">
      <filename><xsl:value-of select="$filename"/></filename>
    </xsl:when>
    <xsl:otherwise>
      <xsl:value-of select="$filename"/>
      <xsl:text>&#10;</xsl:text>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<!-- ==================================================================== -->

<!-- HHC and HHK files are processed by compiler line by line
     and therefore are very sensitive to whitespaces (linefeeds for sure).  -->

<xsl:template name="hhc">
  <xsl:call-template name="write.chunk">
    <xsl:with-param name="filename">
      <xsl:if test="$manifest.in.base.dir != 0">
        <xsl:value-of select="$chunk.base.dir"/>
      </xsl:if>
      <xsl:value-of select="$htmlhelp.hhc"/>
    </xsl:with-param>
    <xsl:with-param name="indent" select="'no'"/>
    <xsl:with-param name="content">
      <xsl:call-template name="hhc-main"/>
    </xsl:with-param>
    <xsl:with-param name="encoding" select="$htmlhelp.encoding"/>
    <xsl:with-param name="quiet" select="$chunk.quietly"/>
  </xsl:call-template>
</xsl:template>

<xsl:template name="hhc-main">
<HTML>&lf;
 <HEAD></HEAD>&lf;
 <BODY>&lf;
  <xsl:if test="$htmlhelp.hhc.folders.instead.books != 0">
   <OBJECT type="text/site properties">&lf;
     <param name="ImageType" value="Folder"/>&lf;
   </OBJECT>&lf;
  </xsl:if>
  <xsl:variable name="content">
    <xsl:choose>
      <xsl:when test="$rootid != ''">
        <xsl:apply-templates select="key('id',$rootid)" mode="hhc"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:apply-templates select="/" mode="hhc"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:choose>
    <xsl:when test="$htmlhelp.hhc.show.root != 0">
      <UL>&lf;
        <xsl:copy-of select="$content"/>
      </UL>&lf;
    </xsl:when>
    <xsl:otherwise>
    <xsl:copy-of select="$content"/>
    </xsl:otherwise>
  </xsl:choose>

 </BODY>
</HTML>
</xsl:template>

<xsl:template name="hhc.entry">
  <xsl:param name="title">
    <xsl:if test="$htmlhelp.autolabel=1">
      <xsl:variable name="label.markup">
        <xsl:apply-templates select="." mode="label.markup"/>
      </xsl:variable>
      <xsl:if test="normalize-space($label.markup)">
        <xsl:value-of select="concat($label.markup,$autotoc.label.separator)"/>
      </xsl:if>
    </xsl:if>
    <xsl:apply-templates select="." mode="title.markup"/>
  </xsl:param>

  <LI><OBJECT type="text/sitemap">&lf;
    <param name="Name">
      <xsl:attribute name="value">
          <xsl:value-of select="normalize-space($title)"/>
      </xsl:attribute>
    </param>&lf;
    <param name="Local">
      <xsl:attribute name="value">
          <xsl:call-template name="href.target.with.base.dir"/>
      </xsl:attribute>
    </param>
  </OBJECT></LI>&lf;
</xsl:template>

<xsl:template match="set" mode="hhc">
  <xsl:if test="$htmlhelp.hhc.show.root != 0">
    <xsl:call-template name="hhc.entry"/>
  </xsl:if>
  <xsl:if test="book">
    <xsl:variable name="toc.params">
      <xsl:call-template name="find.path.params">
        <xsl:with-param name="table" select="normalize-space($generate.toc)"/>
      </xsl:call-template>
    </xsl:variable>
    <UL>
      <xsl:if test="contains($toc.params, 'toc') and $htmlhelp.hhc.show.root = 0">
      <LI><OBJECT type="text/sitemap">&lf;
          <param name="Name">
            <xsl:attribute name="value">
            <xsl:call-template name="gentext">
              <xsl:with-param name="key" select="'TableofContents'"/>
            </xsl:call-template>
            </xsl:attribute>
          </param>&lf;
          <param name="Local">
            <xsl:attribute name="value">
              <xsl:choose>
                <xsl:when test="$chunk.tocs.and.lots != 0">
                  <xsl:apply-templates select="." mode="recursive-chunk-filename">
                    <xsl:with-param name="recursive" select="true()"/>
                  </xsl:apply-templates>
                  <xsl:text>-toc</xsl:text>
                  <xsl:value-of select="$html.ext"/>
                </xsl:when>
                <xsl:otherwise>
                  <xsl:call-template name="href.target.with.base.dir"/>
                </xsl:otherwise>
              </xsl:choose>
            </xsl:attribute>
          </param>
      </OBJECT></LI>&lf;
      </xsl:if>
      <xsl:apply-templates select="book" mode="hhc"/>
    </UL>&lf;
  </xsl:if>
</xsl:template>

<xsl:template match="book" mode="hhc">
  <xsl:if test="$htmlhelp.hhc.show.root != 0 or parent::*">
    <xsl:call-template name="hhc.entry"/>
  </xsl:if>
  <xsl:if test="part|reference|preface|chapter|appendix|bibliography|article|colophon|glossary">
    <xsl:variable name="toc.params">
      <xsl:call-template name="find.path.params">
        <xsl:with-param name="table" select="normalize-space($generate.toc)"/>
      </xsl:call-template>
    </xsl:variable>
    <UL>
      <xsl:if test="contains($toc.params, 'toc') and $htmlhelp.hhc.show.root = 0 and not(parent::*)">
        <LI><OBJECT type="text/sitemap">&lf;
            <param name="Name">
              <xsl:attribute name="value">
                <xsl:call-template name="gentext">
                  <xsl:with-param name="key" select="'TableofContents'"/>
                </xsl:call-template>
              </xsl:attribute>
            </param>&lf;
            <param name="Local">
              <xsl:attribute name="value">
                <xsl:choose>
                  <xsl:when test="$chunk.tocs.and.lots != 0">
                    <xsl:apply-templates select="." mode="recursive-chunk-filename">
                      <xsl:with-param name="recursive" select="true()"/>
                    </xsl:apply-templates>
                    <xsl:text>-toc</xsl:text>
                    <xsl:value-of select="$html.ext"/>
                  </xsl:when>
                  <xsl:otherwise>
                    <xsl:call-template name="href.target.with.base.dir"/>
                  </xsl:otherwise>
                </xsl:choose>
              </xsl:attribute>
            </param>
        </OBJECT></LI>&lf;
      </xsl:if>
      <xsl:apply-templates select="part|reference|preface|chapter|bibliography|appendix|article|colophon|glossary"
                           mode="hhc"/>
    </UL>&lf;
  </xsl:if>
</xsl:template>

<xsl:template match="part|reference|preface|chapter|bibliography|appendix|article|glossary"
              mode="hhc">
  <xsl:if test="$htmlhelp.hhc.show.root != 0 or parent::*">
    <xsl:call-template name="hhc.entry"/>
  </xsl:if>
  <xsl:if test="article|reference|preface|chapter|appendix|refentry|section|sect1|bibliodiv">
    <UL>&lf;
      <xsl:apply-templates
        select="article|reference|preface|chapter|appendix|refentry|section|sect1|bibliodiv"
        mode="hhc"/>
    </UL>
  </xsl:if>
</xsl:template>

<xsl:template match="section" mode="hhc">
  <xsl:if test="$htmlhelp.hhc.show.root != 0 or parent::*">
    <xsl:call-template name="hhc.entry"/>
  </xsl:if>
  <xsl:if test="section[count(ancestor::section) &lt; $htmlhelp.hhc.section.depth]|refentry">
    <UL>&lf;
      <xsl:apply-templates select="section|refentry" mode="hhc"/>
    </UL>
  </xsl:if>
</xsl:template>

<xsl:template match="sect1" mode="hhc">
  <xsl:if test="$htmlhelp.hhc.show.root != 0 or parent::*">
    <xsl:call-template name="hhc.entry"/>
  </xsl:if>
  <xsl:if test="sect2[$htmlhelp.hhc.section.depth > 1]|refentry">
    <UL>&lf;
      <xsl:apply-templates select="sect2|refentry"
                           mode="hhc"/>
    </UL>
  </xsl:if>
</xsl:template>

<xsl:template match="sect2" mode="hhc">
  <xsl:if test="$htmlhelp.hhc.show.root != 0 or parent::*">
    <xsl:call-template name="hhc.entry"/>
  </xsl:if>
  <xsl:if test="sect3[$htmlhelp.hhc.section.depth > 2]|refentry">
    <UL>&lf;
      <xsl:apply-templates select="sect3|refentry"
                           mode="hhc"/>
    </UL>
  </xsl:if>
</xsl:template>

<xsl:template match="sect3" mode="hhc">
  <xsl:if test="$htmlhelp.hhc.show.root != 0 or parent::*">
    <xsl:call-template name="hhc.entry"/>
  </xsl:if>
  <xsl:if test="sect4[$htmlhelp.hhc.section.depth > 3]|refentry">
    <UL>&lf;
      <xsl:apply-templates select="sect4|refentry"
                           mode="hhc"/>
    </UL>
  </xsl:if>
</xsl:template>

<xsl:template match="sect4" mode="hhc">
  <xsl:if test="$htmlhelp.hhc.show.root != 0 or parent::*">
    <xsl:call-template name="hhc.entry"/>
  </xsl:if>
  <xsl:if test="sect5[$htmlhelp.hhc.section.depth > 4]|refentry">
    <UL>&lf;
      <xsl:apply-templates select="sect5|refentry"
                           mode="hhc"/>
    </UL>
  </xsl:if>
</xsl:template>

<xsl:template match="sect5|refentry|colophon|bibliodiv" mode="hhc">
  <xsl:if test="$htmlhelp.hhc.show.root != 0 or parent::*">
    <xsl:call-template name="hhc.entry"/>
  </xsl:if>
  <xsl:if test="refentry">
    <UL>&lf;
      <xsl:apply-templates select="refentry"
                           mode="hhc"/>
    </UL>
  </xsl:if>
</xsl:template>

<!-- ==================================================================== -->

<xsl:template match="indexterm">
  <xsl:choose>
    <xsl:when test="$htmlhelp.use.hhk = 0">
  
      <xsl:variable name="primary" select="normalize-space(primary)"/>
      <xsl:variable name="secondary" select="normalize-space(secondary)"/>
      <xsl:variable name="tertiary" select="normalize-space(tertiary)"/>
      
      <xsl:variable name="text">
        <xsl:value-of select="$primary"/>
        <xsl:if test="secondary">
          <xsl:text>, </xsl:text>
          <xsl:value-of select="$secondary"/>
        </xsl:if>
        <xsl:if test="tertiary">
          <xsl:text>, </xsl:text>
          <xsl:value-of select="$tertiary"/>
        </xsl:if>
      </xsl:variable>
      
      <xsl:if test="secondary">
        <xsl:if test="not(//indexterm[normalize-space(primary)=$primary and not(secondary)])">
          <xsl:call-template name="write.indexterm">
            <xsl:with-param name="text" select="$primary"/>
          </xsl:call-template>
        </xsl:if>
      </xsl:if>

      <xsl:if test="tertiary">
        <xsl:if test="not(//indexterm[normalize-space(primary)=$primary and 
                                      normalize-space(secondary)=$secondary and not(tertiary)])">
          <xsl:call-template name="write.indexterm">
            <xsl:with-param name="text" select="concat($primary, ', ', $secondary)"/>
          </xsl:call-template>
        </xsl:if>
      </xsl:if>
      
      <xsl:call-template name="write.indexterm">
        <xsl:with-param name="text" select="$text"/>
      </xsl:call-template>
      
    </xsl:when>
    <xsl:otherwise>
      <a>
        <xsl:attribute name="name">
          <xsl:call-template name="object.id"/>
        </xsl:attribute>
      </a>
    </xsl:otherwise>
    
  </xsl:choose>
</xsl:template>

<xsl:template name="write.indexterm">
  <xsl:param name="text"/>
  <OBJECT type="application/x-oleobject"
          classid="clsid:1e2a7bd0-dab9-11d0-b93a-00c04fc99f9e">
    <param name="Keyword" value="{$text}"/>
  </OBJECT>
</xsl:template>

<!-- ==================================================================== -->

<xsl:template name="hhk">
  <xsl:call-template name="write.chunk">
    <xsl:with-param name="filename">
      <xsl:if test="$manifest.in.base.dir != 0">
        <xsl:value-of select="$chunk.base.dir"/>
      </xsl:if>
      <xsl:value-of select="$htmlhelp.hhk"/>
    </xsl:with-param>
    <xsl:with-param name="indent" select="'no'"/>
    <xsl:with-param name="content"><xsl:text disable-output-escaping="yes"><![CDATA[<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<HTML>
<HEAD>
<meta name="GENERATOR" content="Microsoft&reg; HTML Help Workshop 4.1">
<!-- Sitemap 1.0 -->
</HEAD><BODY>
<OBJECT type="text/site properties">
</OBJECT>
<UL>]]>
</xsl:text>
<xsl:if test="($htmlhelp.use.hhk != 0) and $htmlhelp.generate.index">
  <xsl:choose>
    <xsl:when test="$rootid != ''">
      <xsl:apply-templates select="key('id',$rootid)" mode="hhk"/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:apply-templates select="/" mode="hhk"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:if>
<xsl:text disable-output-escaping="yes"><![CDATA[</UL>
</BODY></HTML>]]>
</xsl:text></xsl:with-param>
    <xsl:with-param name="encoding" select="$htmlhelp.encoding"/>
    <xsl:with-param name="quiet" select="$chunk.quietly"/>
  </xsl:call-template>
</xsl:template>

<xsl:template match="indexterm[@class='endofrange']" mode="hhk"/>

<xsl:template match="indexterm" mode="hhk">
  <xsl:variable name="primary" select="normalize-space(primary)"/>
  <xsl:variable name="secondary" select="normalize-space(secondary)"/>
  <xsl:variable name="tertiary" select="normalize-space(tertiary)"/>

  <xsl:call-template name="write.indexterm.hhk">
    <xsl:with-param name="text" select="$primary"/>
    <xsl:with-param name="seealso" select="seealso"/>
  </xsl:call-template>

  <xsl:if test="secondary">
    <xsl:if test="not(//indexterm[normalize-space(primary)=$primary and not(secondary)])">
      <xsl:call-template name="write.indexterm.hhk">
        <!-- We must create fake entry when there is secondary without primary --> 
        <xsl:with-param name="text" select="$primary"/>
        <xsl:with-param name="seealso" select="$primary"/>
      </xsl:call-template>
    </xsl:if>
    <UL>
    <xsl:call-template name="write.indexterm.hhk">
      <xsl:with-param name="text" select="$secondary"/>
      <xsl:with-param name="seealso" select="secondary/seealso"/>
    </xsl:call-template>
    <xsl:if test="tertiary">
      <UL>&lf;
      <xsl:call-template name="write.indexterm.hhk">
        <xsl:with-param name="text" select="$tertiary"/>
        <xsl:with-param name="seealso" select="tertiary/seealso"/>
      </xsl:call-template>
      </UL>
    </xsl:if>
    </UL>
  </xsl:if>

</xsl:template>

<xsl:template name="write.indexterm.hhk">
  <xsl:param name="text"/>
  <xsl:param name="seealso"/>

  <LI> <OBJECT type="text/sitemap">&lf;
    <param name="Name">
      <xsl:attribute name="value">
        <xsl:value-of select="$text"/>
      </xsl:attribute>
    </param>&lf;

      <xsl:if test="not(seealso)">
        <xsl:variable name="href">
          <xsl:call-template name="href.target.with.base.dir"/>
        </xsl:variable>
        <xsl:variable name="title">
          <xsl:call-template name="nearest.title">
            <xsl:with-param name="object" select=".."/>
          </xsl:call-template>
        </xsl:variable>

        <param name="Name">
          <xsl:attribute name="value">
          <xsl:value-of select="$title"/>
          </xsl:attribute>
        </param>&lf;
        <param name="Local">
          <xsl:attribute name="value">
          <xsl:value-of select="$href"/>
          </xsl:attribute>
        </param>&lf;
      </xsl:if>

      <xsl:if test="seealso">
        <param name="See Also">
          <xsl:attribute name="value">
          <xsl:value-of select="$seealso"/>
          </xsl:attribute>
        </param>&lf;
      </xsl:if>
      </OBJECT></LI>
</xsl:template>

<xsl:template match="text()" mode="hhk"/>

<xsl:template name="nearest.title">
  <xsl:param name="object"/>
  <xsl:apply-templates select="$object/ancestor-or-self::*[title][1]" mode="title.markup"/>
</xsl:template>

<!-- ==================================================================== -->

<xsl:template name="hh-map">
  <xsl:call-template name="write.text.chunk">
    <xsl:with-param name="filename">
      <xsl:if test="$manifest.in.base.dir != 0">
        <xsl:value-of select="$chunk.base.dir"/>
      </xsl:if>
      <xsl:value-of select="$htmlhelp.map.file"/>
    </xsl:with-param>
    <xsl:with-param name="method" select="'text'"/>
    <xsl:with-param name="content">
     <xsl:choose>
       <xsl:when test="$rootid != ''">
         <xsl:apply-templates select="key('id',$rootid)" mode="hh-map"/>
       </xsl:when>
       <xsl:otherwise>
         <xsl:apply-templates select="/" mode="hh-map"/>
       </xsl:otherwise>
     </xsl:choose>
    </xsl:with-param>
    <xsl:with-param name="encoding" select="$htmlhelp.encoding"/>
    <xsl:with-param name="quiet" select="$chunk.quietly"/>
  </xsl:call-template>
</xsl:template>

<xsl:template match="processing-instruction('dbhh')" mode="hh-map">
  <xsl:variable name="topicname">
    <xsl:call-template name="pi-attribute">
      <xsl:with-param name="pis"
                      select="."/>
      <xsl:with-param name="attribute" select="'topicname'"/>
    </xsl:call-template>
  </xsl:variable>
  <xsl:variable name="topicid">
    <xsl:call-template name="pi-attribute">
      <xsl:with-param name="pis"
                      select="."/>
      <xsl:with-param name="attribute" select="'topicid'"/>
    </xsl:call-template>
  </xsl:variable>
  <xsl:text>#define </xsl:text>
  <xsl:value-of select="$topicname"/>
  <xsl:text>&#9;</xsl:text>
  <xsl:value-of select="$topicid"/>
  <xsl:text>&#xA;</xsl:text>
</xsl:template>

<xsl:template match="text()" mode="hh-map"/>

<!-- ==================================================================== -->

<xsl:template name="hh-alias">
  <xsl:call-template name="write.text.chunk">
    <xsl:with-param name="filename">
      <xsl:if test="$manifest.in.base.dir != 0">
        <xsl:value-of select="$chunk.base.dir"/>
      </xsl:if>
      <xsl:value-of select="$htmlhelp.alias.file"/>
    </xsl:with-param>
    <xsl:with-param name="method" select="'text'"/>
    <xsl:with-param name="content">
     <xsl:choose>
       <xsl:when test="$rootid != ''">
         <xsl:apply-templates select="key('id',$rootid)" mode="hh-alias"/>
       </xsl:when>
       <xsl:otherwise>
         <xsl:apply-templates select="/" mode="hh-alias"/>
       </xsl:otherwise>
     </xsl:choose>
    </xsl:with-param>
    <xsl:with-param name="encoding" select="$htmlhelp.encoding"/>
    <xsl:with-param name="quiet" select="$chunk.quietly"/>
  </xsl:call-template>
</xsl:template>

<xsl:template match="processing-instruction('dbhh')" mode="hh-alias">
  <xsl:variable name="topicname">
    <xsl:call-template name="pi-attribute">
      <xsl:with-param name="pis"
                      select="."/>
      <xsl:with-param name="attribute" select="'topicname'"/>
    </xsl:call-template>
  </xsl:variable>
  <xsl:variable name="href">
    <xsl:call-template name="href.target.with.base.dir">
      <xsl:with-param name="object" select=".."/>
    </xsl:call-template>
  </xsl:variable>
  <xsl:value-of select="$topicname"/>
  <xsl:text>=</xsl:text>
  <!-- Some versions of HH doesn't like fragment identifires, but some does. -->
  <!-- <xsl:value-of select="substring-before(concat($href, '#'), '#')"/> -->
  <xsl:value-of select="$href"/>
  <xsl:text>&#xA;</xsl:text>
</xsl:template>

<xsl:template match="text()" mode="hh-alias"/>

<!-- ==================================================================== -->
<!-- This code can be used to convert any number to hexadecimal format -->

  <h:hex>
    <d>0</d>
    <d>1</d>
    <d>2</d>
    <d>3</d>
    <d>4</d>
    <d>5</d>
    <d>6</d>
    <d>7</d>
    <d>8</d>
    <d>9</d>
    <d>A</d>
    <d>B</d>
    <d>C</d>
    <d>D</d>
    <d>E</d>
    <d>F</d>
  </h:hex>

  <xsl:template name="toHex">
    <xsl:param name="n" select="0"/>
    <xsl:param name="digit" select="$n mod 16"/>
    <xsl:param name="rest" select="floor($n div 16)"/>
    <xsl:if test="$rest > 0">
      <xsl:call-template name="toHex">
        <xsl:with-param name="n" select="$rest"/>
      </xsl:call-template>
    </xsl:if>
    <xsl:value-of select="document('')//h:hex/d[$digit+1]"/>
  </xsl:template>

<!-- ==================================================================== -->
<!-- Modification to standard HTML stylesheets -->

<!-- There are links from ToC pane to bibliodivs, so there must be anchor -->
<xsl:template match="bibliodiv/title">
  <h3 class="{name(.)}">
    <xsl:call-template name="anchor">
      <xsl:with-param name="node" select=".."/>
      <xsl:with-param name="conditional" select="0"/>
    </xsl:call-template>
    <xsl:apply-templates/>
  </h3>
</xsl:template>

</xsl:stylesheet>
