<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:doc="http://nwalsh.com/xsl/documentation/1.0"
		version="1.0"
                exclude-result-prefixes="doc">

<!-- ********************************************************************
     $Id: chunktoc.xsl 9286 2012-04-19 10:10:58Z bobstayton $
     ********************************************************************

     This file is part of the XSL DocBook Stylesheet distribution.
     See ../README or http://docbook.sf.net/release/xsl/current/ for
     copyright and other information.

     ******************************************************************** -->

<!-- ==================================================================== -->

<xsl:import href="docbook.xsl"/>
<xsl:import href="chunk-common.xsl"/>

<xsl:template name="chunk">
  <xsl:param name="node" select="."/>
  <!-- returns 1 if $node is a chunk -->

  <xsl:variable name="id">
    <xsl:call-template name="object.id">
      <xsl:with-param name="object" select="$node"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:variable name="chunks" select="document($chunk.toc,/)"/>

  <xsl:choose>
    <xsl:when test="$chunks//tocentry[@linkend=$id]">1</xsl:when>
    <xsl:otherwise>0</xsl:otherwise>
  </xsl:choose>
</xsl:template>

<!-- ==================================================================== -->

<xsl:template match="*" mode="chunk-filename">
  <!-- returns the filename of a chunk -->

  <xsl:variable name="id">
    <xsl:call-template name="object.id"/>
  </xsl:variable>

  <xsl:variable name="chunks" select="document($chunk.toc,/)"/>

  <xsl:variable name="chunk" select="$chunks//tocentry[@linkend=$id]"/>
  <xsl:variable name="filename">
    <xsl:call-template name="pi.dbhtml_filename">
      <xsl:with-param name="node" select="$chunk"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:choose>
    <xsl:when test="$chunk">
      <xsl:value-of select="$filename"/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:apply-templates select="parent::*" mode="chunk-filename"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<!-- ==================================================================== -->

<xsl:template name="process-chunk">
  <xsl:variable name="id">
    <xsl:call-template name="object.id"/>
  </xsl:variable>

  <xsl:variable name="chunks" select="document($chunk.toc,/)"/>

  <xsl:variable name="chunk" select="$chunks//tocentry[@linkend=$id]"/>
  <xsl:variable name="prev-id"
                select="($chunk/preceding::tocentry
                         |$chunk/ancestor::tocentry)[last()]/@linkend"/>
  <xsl:variable name="next-id"
                select="($chunk/following::tocentry
                         |$chunk/child::tocentry)[1]/@linkend"/>

  <xsl:variable name="prev" select="key('id',$prev-id)"/>
  <xsl:variable name="next" select="key('id',$next-id)"/>

  <xsl:variable name="ischunk">
    <xsl:call-template name="chunk"/>
  </xsl:variable>

  <xsl:variable name="chunkfn">
    <xsl:if test="$ischunk='1'">
      <xsl:apply-templates mode="chunk-filename" select="."/>
    </xsl:if>
  </xsl:variable>

  <xsl:variable name="filename">
    <xsl:call-template name="make-relative-filename">
      <xsl:with-param name="base.dir" select="$chunk.base.dir"/>
      <xsl:with-param name="base.name" select="$chunkfn"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:choose>
    <xsl:when test="$ischunk = 0">
      <xsl:apply-imports/>
    </xsl:when>

    <xsl:otherwise>
      <xsl:call-template name="write.chunk">
        <xsl:with-param name="filename" select="$filename"/>
        <xsl:with-param name="content">
          <xsl:call-template name="chunk-element-content">
            <xsl:with-param name="prev" select="$prev"/>
            <xsl:with-param name="next" select="$next"/>
          </xsl:call-template>
        </xsl:with-param>
        <xsl:with-param name="quiet" select="$chunk.quietly"/>
      </xsl:call-template>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<!-- ==================================================================== -->

<xsl:template match="set">
  <xsl:call-template name="process-chunk"/>
</xsl:template>

<xsl:template match="book">
  <xsl:call-template name="process-chunk"/>
</xsl:template>

<xsl:template match="book/appendix">
  <xsl:call-template name="process-chunk"/>
</xsl:template>

<xsl:template match="book/glossary">
  <xsl:call-template name="process-chunk"/>
</xsl:template>

<xsl:template match="book/bibliography">
  <xsl:call-template name="process-chunk"/>
</xsl:template>

<xsl:template match="dedication" mode="dedication">
  <xsl:call-template name="process-chunk"/>
</xsl:template>

<xsl:template match="preface|chapter">
  <xsl:call-template name="process-chunk"/>
</xsl:template>

<xsl:template match="part|reference">
  <xsl:call-template name="process-chunk"/>
</xsl:template>

<xsl:template match="refentry">
  <xsl:call-template name="process-chunk"/>
</xsl:template>

<xsl:template match="colophon">
  <xsl:call-template name="process-chunk"/>
</xsl:template>

<xsl:template match="article">
  <xsl:call-template name="process-chunk"/>
</xsl:template>

<xsl:template match="topic">
  <xsl:call-template name="process-chunk"/>
</xsl:template>

<xsl:template match="article/appendix">
  <xsl:call-template name="process-chunk"/>
</xsl:template>

<xsl:template match="article/glossary">
  <xsl:call-template name="process-chunk"/>
</xsl:template>

<xsl:template match="article/bibliography">
  <xsl:call-template name="process-chunk"/>
</xsl:template>

<xsl:template match="sect1|sect2|sect3|sect4|sect5|section">
  <xsl:variable name="ischunk">
    <xsl:call-template name="chunk"/>
  </xsl:variable>

  <xsl:choose>
    <xsl:when test="$ischunk != 0">
      <xsl:call-template name="process-chunk"/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:apply-imports/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="setindex
                     |book/index
                     |article/index">
  <!-- some implementations use completely empty index tags to indicate -->
  <!-- where an automatically generated index should be inserted. so -->
  <!-- if the index is completely empty, skip it. -->
  <xsl:if test="count(*)>0 or $generate.index != '0'">
    <xsl:call-template name="process-chunk"/>
  </xsl:if>
</xsl:template>

<!-- ==================================================================== -->

<xsl:template match="/">
  <!-- * Get a title for current doc so that we let the user -->
  <!-- * know what document we are processing at this point. -->
  <xsl:variable name="doc.title">
    <xsl:call-template name="get.doc.title"/>
  </xsl:variable>
  <xsl:choose>
    <xsl:when test="$chunk.toc = ''">
      <xsl:message terminate="yes">
        <xsl:text>The chunk.toc file is not set.</xsl:text>
      </xsl:message>
    </xsl:when>
    <!-- Hack! If someone hands us a DocBook V5.x or DocBook NG document,
         toss the namespace and continue.  Use the docbook5 namespaced
	 stylesheets for DocBook5 if you don't want to use this feature.-->
    <!-- include extra test for Xalan quirk -->
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
    <!-- Can't process unless namespace removed -->
    <xsl:when test="*/self::ng:* or */self::db:*">
      <xsl:message terminate="yes">
        <xsl:text>Unable to strip the namespace from DB5 document,</xsl:text>
        <xsl:text> cannot proceed.</xsl:text>
      </xsl:message>
    </xsl:when>
    <xsl:otherwise>
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
              <xsl:if test="$collect.xref.targets = 'yes' or
                            $collect.xref.targets = 'only'">
                <xsl:apply-templates select="key('id', $rootid)"
                                     mode="collect.targets"/>
              </xsl:if>
              <xsl:if test="$collect.xref.targets != 'only'">
                <xsl:apply-templates select="key('id',$rootid)"
                                     mode="process.root"/>
                <xsl:if test="$tex.math.in.alt != ''">
                  <xsl:apply-templates select="key('id',$rootid)"
                                       mode="collect.tex.math"/>
                </xsl:if>
                <xsl:if test="$generate.manifest != 0">
                  <xsl:call-template name="generate.manifest">
                    <xsl:with-param name="node" select="key('id',$rootid)"/>
                  </xsl:call-template>
                </xsl:if>
              </xsl:if>
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
            <xsl:if test="$tex.math.in.alt != ''">
              <xsl:apply-templates select="/" mode="collect.tex.math"/>
            </xsl:if>
            <xsl:if test="$generate.manifest != 0">
              <xsl:call-template name="generate.manifest">
                <xsl:with-param name="node" select="/"/>
              </xsl:call-template>
            </xsl:if>
          </xsl:if>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="*" mode="process.root">
  <xsl:apply-templates select="."/>
  <xsl:call-template name="generate.css"/>
</xsl:template>

<xsl:template name="make.lots">
  <xsl:param name="toc.params" select="''"/>
  <xsl:param name="toc"/>

  <xsl:variable name="lots">
    <xsl:if test="contains($toc.params, 'toc')">
      <xsl:copy-of select="$toc"/>
    </xsl:if>

    <xsl:if test="contains($toc.params, 'figure')">
      <xsl:choose>
        <xsl:when test="$chunk.separate.lots != '0'">
          <xsl:call-template name="make.lot.chunk">
            <xsl:with-param name="type" select="'figure'"/>
            <xsl:with-param name="lot">
              <xsl:call-template name="list.of.titles">
                <xsl:with-param name="titles" select="'figure'"/>
                <xsl:with-param name="nodes" select=".//figure"/>
              </xsl:call-template>
            </xsl:with-param>
          </xsl:call-template>
        </xsl:when>
        <xsl:otherwise>
          <xsl:call-template name="list.of.titles">
            <xsl:with-param name="titles" select="'figure'"/>
            <xsl:with-param name="nodes" select=".//figure"/>
          </xsl:call-template>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:if>

    <xsl:if test="contains($toc.params, 'table')">
      <xsl:choose>
        <xsl:when test="$chunk.separate.lots != '0'">
          <xsl:call-template name="make.lot.chunk">
            <xsl:with-param name="type" select="'table'"/>
            <xsl:with-param name="lot">
              <xsl:call-template name="list.of.titles">
                <xsl:with-param name="titles" select="'table'"/>
                <xsl:with-param name="nodes" select=".//table"/>
              </xsl:call-template>
            </xsl:with-param>
          </xsl:call-template>
        </xsl:when>
        <xsl:otherwise>
          <xsl:call-template name="list.of.titles">
            <xsl:with-param name="titles" select="'table'"/>
            <xsl:with-param name="nodes" select=".//table"/>
          </xsl:call-template>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:if>

    <xsl:if test="contains($toc.params, 'example')">
      <xsl:choose>
        <xsl:when test="$chunk.separate.lots != '0'">
          <xsl:call-template name="make.lot.chunk">
            <xsl:with-param name="type" select="'example'"/>
            <xsl:with-param name="lot">
              <xsl:call-template name="list.of.titles">
                <xsl:with-param name="titles" select="'example'"/>
                <xsl:with-param name="nodes" select=".//example"/>
              </xsl:call-template>
            </xsl:with-param>
          </xsl:call-template>
        </xsl:when>
        <xsl:otherwise>
          <xsl:call-template name="list.of.titles">
            <xsl:with-param name="titles" select="'example'"/>
            <xsl:with-param name="nodes" select=".//example"/>
          </xsl:call-template>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:if>

    <xsl:if test="contains($toc.params, 'equation')">
      <xsl:choose>
        <xsl:when test="$chunk.separate.lots != '0'">
          <xsl:call-template name="make.lot.chunk">
            <xsl:with-param name="type" select="'equation'"/>
            <xsl:with-param name="lot">
              <xsl:call-template name="list.of.titles">
                <xsl:with-param name="titles" select="'equation'"/>
                <xsl:with-param name="nodes" select=".//equation"/>
              </xsl:call-template>
            </xsl:with-param>
          </xsl:call-template>
        </xsl:when>
        <xsl:otherwise>
          <xsl:call-template name="list.of.titles">
            <xsl:with-param name="titles" select="'equation'"/>
            <xsl:with-param name="nodes" select=".//equation"/>
          </xsl:call-template>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:if>

    <xsl:if test="contains($toc.params, 'procedure')">
      <xsl:choose>
        <xsl:when test="$chunk.separate.lots != '0'">
          <xsl:call-template name="make.lot.chunk">
            <xsl:with-param name="type" select="'procedure'"/>
            <xsl:with-param name="lot">
              <xsl:call-template name="list.of.titles">
                <xsl:with-param name="titles" select="'procedure'"/>
                <xsl:with-param name="nodes" select=".//procedure[title]"/>
              </xsl:call-template>
            </xsl:with-param>
          </xsl:call-template>
        </xsl:when>
        <xsl:otherwise>
          <xsl:call-template name="list.of.titles">
            <xsl:with-param name="titles" select="'procedure'"/>
            <xsl:with-param name="nodes" select=".//procedure[title]"/>
          </xsl:call-template>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:if>
  </xsl:variable>

  <xsl:if test="string($lots) != ''">
    <xsl:choose>
      <xsl:when test="$chunk.tocs.and.lots != 0 and not(parent::*)">
        <xsl:call-template name="write.chunk">
          <xsl:with-param name="filename">
            <xsl:call-template name="make-relative-filename">
              <xsl:with-param name="base.dir" select="$chunk.base.dir"/>
              <xsl:with-param name="base.name">
                <xsl:call-template name="dbhtml-dir"/>
                <xsl:apply-templates select="." mode="recursive-chunk-filename">
                  <xsl:with-param name="recursive" select="true()"/>
                </xsl:apply-templates>
                <xsl:text>-toc</xsl:text>
                <xsl:value-of select="$html.ext"/>
              </xsl:with-param>
            </xsl:call-template>
          </xsl:with-param>
          <xsl:with-param name="content">
            <xsl:call-template name="chunk-element-content">
              <xsl:with-param name="prev" select="/foo"/>
              <xsl:with-param name="next" select="/foo"/>
              <xsl:with-param name="nav.context" select="'toc'"/>
              <xsl:with-param name="content">
                <h1>
                  <xsl:apply-templates select="." mode="object.title.markup"/>
                </h1>
                <xsl:copy-of select="$lots"/>
              </xsl:with-param>
            </xsl:call-template>
          </xsl:with-param>
          <xsl:with-param name="quiet" select="$chunk.quietly"/>
        </xsl:call-template>
      </xsl:when>
      <xsl:otherwise>
        <xsl:copy-of select="$lots"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:if>
</xsl:template>

<xsl:template name="make.lot.chunk">
  <xsl:param name="type" select="''"/>
  <xsl:param name="lot"/>

  <xsl:if test="string($lot) != ''">
    <xsl:variable name="filename">
      <xsl:call-template name="make-relative-filename">
        <xsl:with-param name="base.dir" select="$chunk.base.dir"/>
        <xsl:with-param name="base.name">
          <xsl:call-template name="dbhtml-dir"/>
          <xsl:value-of select="$type"/>
          <xsl:text>-toc</xsl:text>
          <xsl:value-of select="$html.ext"/>
        </xsl:with-param>
      </xsl:call-template>
    </xsl:variable>

    <xsl:variable name="href">
      <xsl:call-template name="make-relative-filename">
        <xsl:with-param name="base.dir" select="''"/>
        <xsl:with-param name="base.name">
          <xsl:call-template name="dbhtml-dir"/>
          <xsl:value-of select="$type"/>
          <xsl:text>-toc</xsl:text>
          <xsl:value-of select="$html.ext"/>
        </xsl:with-param>
      </xsl:call-template>
    </xsl:variable>

    <xsl:call-template name="write.chunk">
      <xsl:with-param name="filename" select="$filename"/>
      <xsl:with-param name="content">
        <xsl:call-template name="chunk-element-content">
          <xsl:with-param name="prev" select="/foo"/>
          <xsl:with-param name="next" select="/foo"/>
          <xsl:with-param name="nav.context" select="'toc'"/>
          <xsl:with-param name="content">
            <xsl:copy-of select="$lot"/>
          </xsl:with-param>
        </xsl:call-template>
      </xsl:with-param>
      <xsl:with-param name="quiet" select="$chunk.quietly"/>
    </xsl:call-template>
    <!-- And output a link to this file -->
    <div>
      <xsl:attribute name="class">
        <xsl:text>ListofTitles</xsl:text>
      </xsl:attribute>
      <a href="{$href}">
        <xsl:call-template name="gentext">
          <xsl:with-param name="key">
            <xsl:choose>
              <xsl:when test="$type='table'">ListofTables</xsl:when>
              <xsl:when test="$type='figure'">ListofFigures</xsl:when>
              <xsl:when test="$type='equation'">ListofEquations</xsl:when>
              <xsl:when test="$type='example'">ListofExamples</xsl:when>
              <xsl:when test="$type='procedure'">ListofProcedures</xsl:when>
              <xsl:otherwise>ListofUnknown</xsl:otherwise>
            </xsl:choose>
          </xsl:with-param>
        </xsl:call-template>
      </a>
    </div>
  </xsl:if>
</xsl:template>

</xsl:stylesheet>
