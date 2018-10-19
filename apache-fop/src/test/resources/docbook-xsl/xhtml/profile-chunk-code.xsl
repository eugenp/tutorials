<?xml version="1.0" encoding="ASCII"?>
<!--This file was created automatically by html2xhtml-->
<!--from the HTML stylesheets.-->
<!--This file was created automatically by xsl2profile-->
<!--from the DocBook XSL stylesheets.-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:exsl="http://exslt.org/common" xmlns:cf="http://docbook.sourceforge.net/xmlns/chunkfast/1.0" xmlns:ng="http://docbook.org/docbook-ng" xmlns:db="http://docbook.org/ns/docbook" xmlns:exslt="http://exslt.org/common" xmlns="http://www.w3.org/1999/xhtml" exslt:dummy="dummy" ng:dummy="dummy" db:dummy="dummy" extension-element-prefixes="exslt" exclude-result-prefixes="exsl cf ng db exslt" version="1.0">

<!-- ********************************************************************
     $Id: chunk-code.xsl 9328 2012-05-03 16:28:23Z bobstayton $
     ********************************************************************

     This file is part of the XSL DocBook Stylesheet distribution.
     See ../README or http://docbook.sf.net/release/xsl/current/ for
     copyright and other information.

     ******************************************************************** -->

<!-- ==================================================================== -->


<xsl:template match="*" mode="chunk-filename">
  <!-- returns the filename of a chunk -->
  <xsl:variable name="ischunk">
    <xsl:call-template name="chunk"/>
  </xsl:variable>

  <xsl:variable name="fn">
    <xsl:apply-templates select="." mode="recursive-chunk-filename"/>
  </xsl:variable>

  <!--
  <xsl:message>
    <xsl:value-of select="$ischunk"/>
    <xsl:text> (</xsl:text>
    <xsl:value-of select="local-name(.)"/>
    <xsl:text>) </xsl:text>
    <xsl:value-of select="$fn"/>
    <xsl:text>, </xsl:text>
    <xsl:call-template name="dbhtml-dir"/>
  </xsl:message>
  -->

  <!-- 2003-11-25 by ndw:
       The following test used to read test="$ischunk != 0 and $fn != ''"
       I've removed the ischunk part of the test so that href.to.uri and
       href.from.uri will be fully qualified even if the source or target
       isn't a chunk. I *think* that if $fn != '' then it's appropriate
       to put the directory on the front, even if the element isn't a
       chunk. I could be wrong. -->

  <xsl:if test="$fn != ''">
    <xsl:call-template name="dbhtml-dir"/>
  </xsl:if>

  <xsl:value-of select="$chunked.filename.prefix"/>

  <xsl:value-of select="$fn"/>
  <!-- You can't add the html.ext here because dbhtml filename= may already -->
  <!-- have added it. It really does have to be handled in the recursive template -->
</xsl:template>

<xsl:template match="*" mode="recursive-chunk-filename">
  <xsl:param name="recursive" select="false()"/>

  <!-- returns the filename of a chunk -->
  <xsl:variable name="ischunk">
    <xsl:call-template name="chunk"/>
  </xsl:variable>

  <xsl:variable name="dbhtml-filename">
    <xsl:call-template name="pi.dbhtml_filename"/>
  </xsl:variable>

  <xsl:variable name="filename">
    <xsl:choose>
      <xsl:when test="$dbhtml-filename != ''">
        <xsl:value-of select="$dbhtml-filename"/>
      </xsl:when>
      <!-- if this is the root element, use the root.filename -->
      <xsl:when test="not(parent::*) and $root.filename != ''">
        <xsl:value-of select="$root.filename"/>
        <xsl:value-of select="$html.ext"/>
      </xsl:when>
      <!-- Special case -->
      <xsl:when test="self::legalnotice and not($generate.legalnotice.link = 0)">
        <xsl:choose>
          <xsl:when test="(@id or @xml:id) and not($use.id.as.filename = 0)">
            <!-- * if this legalnotice has an ID, then go ahead and use -->
            <!-- * just the value of that ID as the basename for the file -->
            <!-- * (that is, without prepending an "ln-" too it) -->
            <xsl:value-of select="(@id|@xml:id)[1]"/>
            <xsl:value-of select="$html.ext"/>
          </xsl:when>
          <xsl:otherwise>
            <!-- * otherwise, if this legalnotice does not have an ID, -->
            <!-- * then we generate an ID... -->
            <xsl:variable name="id">
              <xsl:call-template name="object.id"/>
            </xsl:variable>
            <!-- * ...and then we take that generated ID, prepend an -->
            <!-- * "ln-" to it, and use that as the basename for the file -->
            <xsl:value-of select="concat('ln-',$id,$html.ext)"/>
          </xsl:otherwise>
        </xsl:choose>
      </xsl:when>
      <!-- if there's no dbhtml filename, and if we're to use IDs as -->
      <!-- filenames, then use the ID to generate the filename. -->
      <xsl:when test="(@id or @xml:id) and $use.id.as.filename != 0">
        <xsl:value-of select="(@id|@xml:id)[1]"/>
        <xsl:value-of select="$html.ext"/>
      </xsl:when>
      <xsl:otherwise/>
    </xsl:choose>
  </xsl:variable>

  <xsl:choose>
    <xsl:when test="$ischunk='0'">
      <!-- if called on something that isn't a chunk, walk up... -->
      <xsl:choose>
        <xsl:when test="count(parent::*)&gt;0">
          <xsl:apply-templates mode="recursive-chunk-filename" select="parent::*">
            <xsl:with-param name="recursive" select="$recursive"/>
          </xsl:apply-templates>
        </xsl:when>
        <!-- unless there is no up, in which case return "" -->
        <xsl:otherwise/>
      </xsl:choose>
    </xsl:when>

    <xsl:when test="not($recursive) and $filename != ''">
      <!-- if this chunk has an explicit name, use it -->
      <xsl:value-of select="$filename"/>
    </xsl:when>

    <xsl:when test="self::set">
      <xsl:value-of select="$root.filename"/>
      <xsl:if test="not($recursive)">
        <xsl:value-of select="$html.ext"/>
      </xsl:if>
    </xsl:when>

    <xsl:when test="self::book">
      <xsl:text>bk</xsl:text>
      <xsl:number level="any" format="01"/>
      <xsl:if test="not($recursive)">
        <xsl:value-of select="$html.ext"/>
      </xsl:if>
    </xsl:when>

    <xsl:when test="self::article">
      <xsl:if test="/set">
        <!-- in a set, make sure we inherit the right book info... -->
        <xsl:apply-templates mode="recursive-chunk-filename" select="parent::*">
          <xsl:with-param name="recursive" select="true()"/>
        </xsl:apply-templates>
      </xsl:if>

      <xsl:text>ar</xsl:text>
      <xsl:number level="any" format="01" from="book"/>
      <xsl:if test="not($recursive)">
        <xsl:value-of select="$html.ext"/>
      </xsl:if>
    </xsl:when>

    <xsl:when test="self::preface">
      <xsl:if test="/set">
        <!-- in a set, make sure we inherit the right book info... -->
        <xsl:apply-templates mode="recursive-chunk-filename" select="parent::*">
          <xsl:with-param name="recursive" select="true()"/>
        </xsl:apply-templates>
      </xsl:if>

      <xsl:text>pr</xsl:text>
      <xsl:number level="any" format="01" from="book"/>
      <xsl:if test="not($recursive)">
        <xsl:value-of select="$html.ext"/>
      </xsl:if>
    </xsl:when>

    <xsl:when test="self::chapter">
      <xsl:if test="/set">
        <!-- in a set, make sure we inherit the right book info... -->
        <xsl:apply-templates mode="recursive-chunk-filename" select="parent::*">
          <xsl:with-param name="recursive" select="true()"/>
        </xsl:apply-templates>
      </xsl:if>

      <xsl:text>ch</xsl:text>
      <xsl:number level="any" format="01" from="book"/>
      <xsl:if test="not($recursive)">
        <xsl:value-of select="$html.ext"/>
      </xsl:if>
    </xsl:when>

    <xsl:when test="self::appendix">
      <xsl:if test="/set">
        <!-- in a set, make sure we inherit the right book info... -->
        <xsl:apply-templates mode="recursive-chunk-filename" select="parent::*">
          <xsl:with-param name="recursive" select="true()"/>
        </xsl:apply-templates>
      </xsl:if>

      <xsl:text>ap</xsl:text>
      <xsl:number level="any" format="a" from="book"/>
      <xsl:if test="not($recursive)">
        <xsl:value-of select="$html.ext"/>
      </xsl:if>
    </xsl:when>

    <xsl:when test="self::part">
      <xsl:choose>
        <xsl:when test="/set">
          <!-- in a set, make sure we inherit the right book info... -->
          <xsl:apply-templates mode="recursive-chunk-filename" select="parent::*">
            <xsl:with-param name="recursive" select="true()"/>
          </xsl:apply-templates>
        </xsl:when>
        <xsl:otherwise>
        </xsl:otherwise>
      </xsl:choose>

      <xsl:text>pt</xsl:text>
      <xsl:number level="any" format="01" from="book"/>
      <xsl:if test="not($recursive)">
        <xsl:value-of select="$html.ext"/>
      </xsl:if>
    </xsl:when>

    <xsl:when test="self::reference">
      <xsl:choose>
        <xsl:when test="/set">
          <!-- in a set, make sure we inherit the right book info... -->
          <xsl:apply-templates mode="recursive-chunk-filename" select="parent::*">
            <xsl:with-param name="recursive" select="true()"/>
          </xsl:apply-templates>
        </xsl:when>
        <xsl:otherwise>
        </xsl:otherwise>
      </xsl:choose>

      <xsl:text>rn</xsl:text>
      <xsl:number level="any" format="01" from="book"/>
      <xsl:if test="not($recursive)">
        <xsl:value-of select="$html.ext"/>
      </xsl:if>
    </xsl:when>

    <xsl:when test="self::refentry">
      <xsl:choose>
        <xsl:when test="parent::reference">
          <xsl:apply-templates mode="recursive-chunk-filename" select="parent::*">
            <xsl:with-param name="recursive" select="true()"/>
          </xsl:apply-templates>
        </xsl:when>
        <xsl:otherwise>
          <xsl:if test="/set">
            <!-- in a set, make sure we inherit the right book info... -->
            <xsl:apply-templates mode="recursive-chunk-filename" select="parent::*">
              <xsl:with-param name="recursive" select="true()"/>
            </xsl:apply-templates>
          </xsl:if>
        </xsl:otherwise>
      </xsl:choose>

      <xsl:text>re</xsl:text>
      <xsl:number level="any" format="01" from="book"/>
      <xsl:if test="not($recursive)">
        <xsl:value-of select="$html.ext"/>
      </xsl:if>
    </xsl:when>

    <xsl:when test="self::colophon">
      <xsl:choose>
        <xsl:when test="/set">
          <!-- in a set, make sure we inherit the right book info... -->
          <xsl:apply-templates mode="recursive-chunk-filename" select="parent::*">
            <xsl:with-param name="recursive" select="true()"/>
          </xsl:apply-templates>
        </xsl:when>
        <xsl:otherwise>
        </xsl:otherwise>
      </xsl:choose>

      <xsl:text>co</xsl:text>
      <xsl:number level="any" format="01" from="book"/>
      <xsl:if test="not($recursive)">
        <xsl:value-of select="$html.ext"/>
      </xsl:if>
    </xsl:when>

    <xsl:when test="self::sect1                     or self::sect2                     or self::sect3                     or self::sect4                     or self::sect5                     or self::section">
      <xsl:apply-templates mode="recursive-chunk-filename" select="parent::*">
        <xsl:with-param name="recursive" select="true()"/>
      </xsl:apply-templates>
      <xsl:text>s</xsl:text>
      <xsl:number format="01"/>
      <xsl:if test="not($recursive)">
        <xsl:value-of select="$html.ext"/>
      </xsl:if>
    </xsl:when>

    <xsl:when test="self::bibliography">
      <xsl:choose>
        <xsl:when test="/set">
          <!-- in a set, make sure we inherit the right book info... -->
          <xsl:apply-templates mode="recursive-chunk-filename" select="parent::*">
            <xsl:with-param name="recursive" select="true()"/>
          </xsl:apply-templates>
        </xsl:when>
        <xsl:otherwise>
        </xsl:otherwise>
      </xsl:choose>

      <xsl:text>bi</xsl:text>
      <xsl:number level="any" format="01" from="book"/>
      <xsl:if test="not($recursive)">
        <xsl:value-of select="$html.ext"/>
      </xsl:if>
    </xsl:when>

    <xsl:when test="self::glossary">
      <xsl:choose>
        <xsl:when test="/set">
          <!-- in a set, make sure we inherit the right book info... -->
          <xsl:apply-templates mode="recursive-chunk-filename" select="parent::*">
            <xsl:with-param name="recursive" select="true()"/>
          </xsl:apply-templates>
        </xsl:when>
        <xsl:otherwise>
        </xsl:otherwise>
      </xsl:choose>

      <xsl:text>go</xsl:text>
      <xsl:number level="any" format="01" from="book"/>
      <xsl:if test="not($recursive)">
        <xsl:value-of select="$html.ext"/>
      </xsl:if>
    </xsl:when>

    <xsl:when test="self::index">
      <xsl:choose>
        <xsl:when test="/set">
          <!-- in a set, make sure we inherit the right book info... -->
          <xsl:apply-templates mode="recursive-chunk-filename" select="parent::*">
            <xsl:with-param name="recursive" select="true()"/>
          </xsl:apply-templates>
        </xsl:when>
        <xsl:otherwise>
        </xsl:otherwise>
      </xsl:choose>

      <xsl:text>ix</xsl:text>
      <xsl:number level="any" format="01" from="book"/>
      <xsl:if test="not($recursive)">
        <xsl:value-of select="$html.ext"/>
      </xsl:if>
    </xsl:when>

    <xsl:when test="self::setindex">
      <xsl:text>si</xsl:text>
      <xsl:number level="any" format="01" from="set"/>
      <xsl:if test="not($recursive)">
        <xsl:value-of select="$html.ext"/>
      </xsl:if>
    </xsl:when>

    <xsl:when test="self::topic">
      <xsl:choose>
        <xsl:when test="/set">
          <!-- in a set, make sure we inherit the right book info... -->
          <xsl:apply-templates mode="recursive-chunk-filename" select="parent::*">
            <xsl:with-param name="recursive" select="true()"/>
          </xsl:apply-templates>
        </xsl:when>
        <xsl:otherwise>
        </xsl:otherwise>
      </xsl:choose>

      <xsl:text>to</xsl:text>
      <xsl:number level="any" format="01" from="book"/>
      <xsl:if test="not($recursive)">
        <xsl:value-of select="$html.ext"/>
      </xsl:if>
    </xsl:when>

    <xsl:otherwise>
      <xsl:text>chunk-filename-error-</xsl:text>
      <xsl:value-of select="name(.)"/>
      <xsl:number level="any" format="01" from="set"/>
      <xsl:if test="not($recursive)">
        <xsl:value-of select="$html.ext"/>
      </xsl:if>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<!-- ==================================================================== -->



<xsl:template match="processing-instruction('dbhtml')">
  <!-- nop -->
</xsl:template>

<!-- ==================================================================== -->


<xsl:template match="*" mode="find.chunks">
  <xsl:variable name="chunk">
    <xsl:call-template name="chunk"/>
  </xsl:variable>

  <xsl:choose>
    <xsl:when test="$chunk != 0">
      <cf:div id="{generate-id()}">
        <xsl:apply-templates select="." mode="class.attribute"/>
        <xsl:apply-templates select="*" mode="find.chunks"/>
      </cf:div>
    </xsl:when>
    <xsl:otherwise>
      <xsl:apply-templates select="*" mode="find.chunks"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<!-- Leave legalnotice chunk out of the list for Next and Prev -->
<xsl:template match="legalnotice" mode="find.chunks"/>

<xslo:include xmlns:xslo="http://www.w3.org/1999/XSL/Transform" href="../profiling/profile-mode.xsl"/><xslo:variable xmlns:xslo="http://www.w3.org/1999/XSL/Transform" name="profiled-content"><xslo:choose><xslo:when test="*/self::ng:* or */self::db:*"><xslo:message>Note: namesp. cut : stripped namespace before processing</xslo:message><xslo:variable name="stripped-content"><xslo:apply-templates select="/" mode="stripNS"/></xslo:variable><xslo:message>Note: namesp. cut : processing stripped document</xslo:message><xslo:apply-templates select="exslt:node-set($stripped-content)" mode="profile"/></xslo:when><xslo:otherwise><xslo:apply-templates select="/" mode="profile"/></xslo:otherwise></xslo:choose></xslo:variable><xslo:variable xmlns:xslo="http://www.w3.org/1999/XSL/Transform" name="profiled-nodes" select="exslt:node-set($profiled-content)"/><xsl:template match="/">
  <!-- * Get a title for current doc so that we let the user -->
  <!-- * know what document we are processing at this point. -->
  <xsl:variable name="doc.title">
    <xsl:call-template name="get.doc.title"/>
  </xsl:variable>
  <xsl:choose>
    <!-- Hack! If someone hands us a DocBook V5.x or DocBook NG document,
         toss the namespace and continue.  Use the docbook5 namespaced
	 stylesheets for DocBook5 if you don't want to use this feature.-->
    <xsl:when test="false()"/>
    <!-- Can't process unless namespace removed -->
    <xsl:when test="false()"/>
    <xsl:otherwise>
      <xsl:choose>
        <xsl:when test="$rootid != ''">
          <xsl:choose>
            <xsl:when test="count($profiled-nodes//*[@id=$rootid or @xml:id=$rootid]) = 0">
              <xsl:message terminate="yes">
                <xsl:text>ID '</xsl:text>
                <xsl:value-of select="$rootid"/>
                <xsl:text>' not found in document.</xsl:text>
              </xsl:message>
            </xsl:when>
            <xsl:otherwise>
              <xsl:if test="$collect.xref.targets = 'yes' or                             $collect.xref.targets = 'only'">
                <xsl:apply-templates select="key('id', $rootid)" mode="collect.targets"/>
              </xsl:if>
              <xsl:if test="$collect.xref.targets != 'only'">
                <xsl:apply-templates select="$profiled-nodes//*[@id=$rootid or @xml:id=$rootid]" mode="process.root"/>
                <xsl:if test="$tex.math.in.alt != ''">
                  <xsl:apply-templates select="$profiled-nodes//*[@id=$rootid or @xml:id=$rootid]" mode="collect.tex.math"/>
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
          <xsl:if test="$collect.xref.targets = 'yes' or                         $collect.xref.targets = 'only'">
            <xsl:apply-templates select="$profiled-nodes" mode="collect.targets"/>
          </xsl:if>
          <xsl:if test="$collect.xref.targets != 'only'">
            <xsl:apply-templates select="$profiled-nodes" mode="process.root"/>
            <xsl:if test="$tex.math.in.alt != ''">
              <xsl:apply-templates select="$profiled-nodes" mode="collect.tex.math"/>
            </xsl:if>
            <xsl:if test="$generate.manifest != 0">
              <xsl:call-template name="generate.manifest">
                <xsl:with-param name="node" select="$profiled-nodes"/>
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
  <xsl:call-template name="generate.css.files"/>
</xsl:template>

<!-- ====================================================================== -->

<xsl:template match="set|book|part|preface|chapter|appendix                      |article                      |topic                      |reference|refentry                      |book/glossary|article/glossary|part/glossary                      |book/bibliography|article/bibliography|part/bibliography                      |colophon">
  <xsl:choose>
    <xsl:when test="$onechunk != 0 and parent::*">
      <xsl:apply-imports/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:call-template name="process-chunk-element"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="sect1|sect2|sect3|sect4|sect5|section">
  <xsl:variable name="ischunk">
    <xsl:call-template name="chunk"/>
  </xsl:variable>

  <xsl:choose>
    <xsl:when test="not(parent::*)">
      <xsl:call-template name="process-chunk-element"/>
    </xsl:when>
    <xsl:when test="$ischunk = 0">
      <xsl:apply-imports/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:call-template name="process-chunk-element"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="setindex                      |book/index                      |article/index                      |part/index">
  <!-- some implementations use completely empty index tags to indicate -->
  <!-- where an automatically generated index should be inserted. so -->
  <!-- if the index is completely empty, skip it. -->
  <xsl:if test="count(*)&gt;0 or $generate.index != '0'">
    <xsl:call-template name="process-chunk-element"/>
  </xsl:if>
</xsl:template>

<!-- Resolve xml:base attributes -->
<xsl:template match="@fileref">
  <!-- need a check for absolute urls -->
  <xsl:choose>
    <xsl:when test="contains(., ':')">
      <!-- it has a uri scheme so it is an absolute uri -->
      <xsl:value-of select="."/>
    </xsl:when>
    <xsl:when test="$keep.relative.image.uris != 0">
      <!-- leave it alone -->
      <xsl:value-of select="."/>
    </xsl:when>
    <xsl:otherwise>
      <!-- its a relative uri -->
      <xsl:call-template name="relative-uri">
        <xsl:with-param name="destdir">
          <xsl:call-template name="dbhtml-dir">
            <xsl:with-param name="context" select=".."/>
          </xsl:call-template>
        </xsl:with-param>
      </xsl:call-template>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<!-- ==================================================================== -->
<xsl:template match="set|book|part|preface|chapter|appendix                      |article                      |topic                      |reference|refentry                      |sect1|sect2|sect3|sect4|sect5                      |section                      |book/glossary|article/glossary|part/glossary                      |book/bibliography|article/bibliography|part/bibliography                      |colophon" mode="enumerate-files">
  <xsl:variable name="ischunk"><xsl:call-template name="chunk"/></xsl:variable>
  <xsl:if test="$ischunk='1'">
    <xsl:call-template name="make-relative-filename">
      <xsl:with-param name="base.dir">
        <xsl:if test="$manifest.in.base.dir = 0">
          <xsl:value-of select="$chunk.base.dir"/>
        </xsl:if>
      </xsl:with-param>
      <xsl:with-param name="base.name">
        <xsl:apply-templates mode="chunk-filename" select="."/>
      </xsl:with-param>
    </xsl:call-template>
    <xsl:text>
</xsl:text>
  </xsl:if>
  <xsl:apply-templates select="*" mode="enumerate-files"/>
</xsl:template>

<xsl:template match="book/index|article/index|part/index" mode="enumerate-files">
  <xsl:if test="$htmlhelp.output != 1">
    <xsl:variable name="ischunk"><xsl:call-template name="chunk"/></xsl:variable>
    <xsl:if test="$ischunk='1'">
      <xsl:call-template name="make-relative-filename">
        <xsl:with-param name="base.dir">
          <xsl:if test="$manifest.in.base.dir = 0">
            <xsl:value-of select="$chunk.base.dir"/>
          </xsl:if>
        </xsl:with-param>
        <xsl:with-param name="base.name">
          <xsl:apply-templates mode="chunk-filename" select="."/>
        </xsl:with-param>
      </xsl:call-template>
      <xsl:text>
</xsl:text>
    </xsl:if>
    <xsl:apply-templates select="*" mode="enumerate-files"/>
  </xsl:if>
</xsl:template>

<xsl:template match="legalnotice" mode="enumerate-files">
  <xsl:variable name="id"><xsl:call-template name="object.id"/></xsl:variable>
  <xsl:if test="$generate.legalnotice.link != 0">
    <xsl:call-template name="make-relative-filename">
      <xsl:with-param name="base.dir">
        <xsl:if test="$manifest.in.base.dir = 0">
          <xsl:value-of select="$chunk.base.dir"/>
        </xsl:if>
      </xsl:with-param>
      <xsl:with-param name="base.name">
        <xsl:apply-templates mode="chunk-filename" select="."/>
      </xsl:with-param>
    </xsl:call-template>
    <xsl:text>
</xsl:text>
  </xsl:if>
</xsl:template>

<xsl:template match="mediaobject[imageobject] | inlinemediaobject[imageobject]" mode="enumerate-files">
  <xsl:variable name="longdesc.uri">
    <xsl:call-template name="longdesc.uri">
      <xsl:with-param name="mediaobject" select="."/>
    </xsl:call-template>
  </xsl:variable>
  <xsl:variable name="mediaobject" select="."/>

  <xsl:if test="$html.longdesc != 0 and $mediaobject/textobject[not(phrase)]">
    <xsl:call-template name="longdesc.uri">
      <xsl:with-param name="mediaobject" select="$mediaobject"/>
    </xsl:call-template>
    <xsl:text>
</xsl:text>
  </xsl:if>
</xsl:template>

<xsl:template match="text()" mode="enumerate-files">
</xsl:template>

</xsl:stylesheet>
