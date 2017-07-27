<?xml version='1.0'?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                version='1.0'>

<!-- ********************************************************************
     $Id: refentry.xsl 9297 2012-04-22 03:56:16Z bobstayton $
     ********************************************************************

     This file is part of the XSL DocBook Stylesheet distribution.
     See ../README or http://docbook.sf.net/release/xsl/current/ for
     copyright and other information.

     ******************************************************************** -->

<!-- ==================================================================== -->

<xsl:template match="reference">
  <xsl:call-template name="id.warning"/>

  <div>
    <xsl:call-template name="common.html.attributes">
      <xsl:with-param name="inherit" select="1"/>
    </xsl:call-template>
    <xsl:call-template name="id.attribute">
      <xsl:with-param name="conditional" select="0"/>
    </xsl:call-template>

    <xsl:call-template name="reference.titlepage"/>

    <xsl:variable name="toc.params">
      <xsl:call-template name="find.path.params">
        <xsl:with-param name="table" select="normalize-space($generate.toc)"/>
      </xsl:call-template>
    </xsl:variable>

    <xsl:if test="not(partintro) and contains($toc.params, 'toc')">
      <xsl:call-template name="division.toc"/>
    </xsl:if>
    <xsl:apply-templates/>
  </div>
</xsl:template>

<xsl:template match="reference" mode="division.number">
  <xsl:number from="book" count="reference" format="I."/>
</xsl:template>

<xsl:template match="reference/docinfo"></xsl:template>
<xsl:template match="reference/referenceinfo"></xsl:template>
<xsl:template match="reference/title"></xsl:template>
<xsl:template match="reference/subtitle"></xsl:template>
<xsl:template match="reference/titleabbrev"></xsl:template>

<!-- ==================================================================== -->

<xsl:template name="refentry.title">
  <xsl:param name="node" select="."/>
  <xsl:variable name="refmeta" select="$node//refmeta"/>
  <xsl:variable name="refentrytitle" select="$refmeta//refentrytitle"/>
  <xsl:variable name="refnamediv" select="$node//refnamediv"/>
  <xsl:variable name="refname" select="$refnamediv//refname"/>
  <xsl:variable name="refdesc" select="$refnamediv//refdescriptor"/>
  <xsl:variable name="title">
    <xsl:choose>
      <xsl:when test="$refentrytitle">
        <xsl:apply-templates select="$refentrytitle[1]" mode="title"/>
      </xsl:when>
      <xsl:when test="$refdesc">
	<xsl:apply-templates select="$refdesc[1]" mode="title"/>
      </xsl:when>
      <xsl:when test="$refname">
        <xsl:apply-templates select="$refname[1]" mode="title"/>
      </xsl:when>
      <xsl:otherwise></xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <h1 class="title">
    <xsl:copy-of select="$title"/>
  </h1>
</xsl:template>

<xsl:template match="refentry">
  <xsl:call-template name="id.warning"/>

  <div>
    <xsl:call-template name="common.html.attributes">
      <xsl:with-param name="inherit" select="1"/>
    </xsl:call-template>
    <xsl:call-template name="id.attribute">
      <xsl:with-param name="conditional" select="0"/>
    </xsl:call-template>
    <xsl:if test="$refentry.separator != 0 and preceding-sibling::refentry">
      <div class="refentry.separator">
        <hr/>
      </div>
    </xsl:if>
    <xsl:call-template name="anchor">
      <xsl:with-param name="conditional" select="0"/>
    </xsl:call-template>
    <xsl:call-template name="refentry.titlepage"/>
    <xsl:apply-templates/>
    <xsl:call-template name="process.footnotes"/>
  </div>
</xsl:template>

<xsl:template match="refentry/docinfo|refentry/refentryinfo"></xsl:template>
<xsl:template match="refentry/info"></xsl:template>

<xsl:template match="refentrytitle|refname|refdescriptor" mode="title">
  <xsl:apply-templates/>
</xsl:template>

<xsl:template match="refmeta">
</xsl:template>

<xsl:template match="manvolnum">
  <xsl:if test="$refentry.xref.manvolnum != 0">
    <xsl:text>(</xsl:text>
    <xsl:apply-templates/>
    <xsl:text>)</xsl:text>
  </xsl:if>
</xsl:template>

<xsl:template match="refmiscinfo">
</xsl:template>

<xsl:template match="refentrytitle">
  <xsl:call-template name="inline.charseq"/>
</xsl:template>

<xsl:template match="refnamediv">
  <div>
    <xsl:call-template name="common.html.attributes">
      <xsl:with-param name="inherit" select="1"/>
    </xsl:call-template>
    <xsl:call-template name="id.attribute"/>
    <xsl:call-template name="anchor"/>

    <xsl:choose>
      <xsl:when test="preceding-sibling::refnamediv">
	<!-- no title on secondary refnamedivs! -->
      </xsl:when>
      <xsl:when test="$refentry.generate.name != 0">
        <h2>
          <xsl:call-template name="gentext">
            <xsl:with-param name="key" select="'RefName'"/>
          </xsl:call-template>
        </h2>
      </xsl:when>
      <xsl:when test="$refentry.generate.title != 0">
        <h2>
          <xsl:choose>
            <xsl:when test="../refmeta/refentrytitle">
              <xsl:apply-templates select="../refmeta/refentrytitle"/>
            </xsl:when>
            <xsl:otherwise>
              <xsl:apply-templates select="refname[1]"/>
            </xsl:otherwise>
          </xsl:choose>
        </h2>
      </xsl:when>
    </xsl:choose>

    <p>
      <xsl:apply-templates/>
    </p>
  </div>
</xsl:template>

<xsl:template match="refname">
  <xsl:if test="not(preceding-sibling::refdescriptor)">
    <xsl:apply-templates/>
    <xsl:if test="following-sibling::refname">
      <xsl:text>, </xsl:text>
    </xsl:if>
  </xsl:if>
</xsl:template>

<xsl:template match="refpurpose">
  <xsl:if test="node()">
    <xsl:text> </xsl:text>
    <xsl:call-template name="dingbat">
      <xsl:with-param name="dingbat">em-dash</xsl:with-param>
    </xsl:call-template>
    <xsl:text> </xsl:text>
    <xsl:apply-templates/>
  </xsl:if>
</xsl:template>

<xsl:template match="refdescriptor">
  <xsl:apply-templates/>
</xsl:template>

<xsl:template match="refclass">
  <xsl:if test="$refclass.suppress = 0">
  <b>
    <xsl:if test="@role">
      <xsl:value-of select="@role"/>
      <xsl:text>: </xsl:text>
    </xsl:if>
    <xsl:apply-templates/>
  </b>
  </xsl:if>
</xsl:template>

<xsl:template match="refsynopsisdiv">
  <div>
    <xsl:call-template name="common.html.attributes">
      <xsl:with-param name="inherit" select="1"/>
    </xsl:call-template>
    <xsl:call-template name="id.attribute"/>
    <xsl:call-template name="anchor"/>
    <h2>
      <xsl:choose>
        <xsl:when test="refsynopsisdiv/title|title">
          <xsl:apply-templates select="(refsynopsisdiv/title|title)[1]"
                               mode="titlepage.mode"/>
        </xsl:when>
        <xsl:otherwise>
          <xsl:call-template name="gentext">
            <xsl:with-param name="key" select="'RefSynopsisDiv'"/>
          </xsl:call-template>
        </xsl:otherwise>
      </xsl:choose>
    </h2>
    <xsl:apply-templates/>
  </div>
</xsl:template>

<xsl:template match="refsynopsisdivinfo"></xsl:template>

<xsl:template match="refsynopsisdiv/title">
</xsl:template>

<xsl:template match="refsynopsisdiv/title" mode="titlepage.mode">
  <xsl:apply-templates/>
</xsl:template>

<xsl:template match="refsection|refsect1|refsect2|refsect3">
  <div>
    <xsl:call-template name="common.html.attributes">
      <xsl:with-param name="inherit" select="1"/>
    </xsl:call-template>
    <xsl:call-template name="id.attribute">
      <xsl:with-param name="conditional" select="0"/>
    </xsl:call-template>
    <xsl:call-template name="anchor">
      <xsl:with-param name="conditional" select="0"/>
    </xsl:call-template>
    <!-- pick up info title -->
    <xsl:apply-templates select="(title|info/title)[1]"/>
    <xsl:apply-templates select="node()[not(self::title) and not(self::info)]"/>
  </div>
</xsl:template>

<xsl:template match="refsection/title|refsection/info/title">
  <!-- the ID is output in the block.object call for refsect1 -->
  <xsl:variable name="level" select="count(ancestor-or-self::refsection)"/>
  <xsl:variable name="refsynopsisdiv">
    <xsl:text>0</xsl:text>
    <xsl:if test="ancestor::refsynopsisdiv">1</xsl:if>
  </xsl:variable>
  <xsl:variable name="hlevel">
    <xsl:choose>
      <xsl:when test="$level+$refsynopsisdiv &gt; 5">6</xsl:when>
      <xsl:otherwise>
        <xsl:value-of select="$level+1+$refsynopsisdiv"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>
  <xsl:element name="h{$hlevel}">
    <xsl:apply-templates/>
  </xsl:element>
</xsl:template>

<xsl:template match="refsect1/title|refsect1/info/title">
  <!-- the ID is output in the block.object call for refsect1 -->
  <h2>
    <xsl:apply-templates/>
  </h2>
</xsl:template>

<xsl:template match="refsect2/title|refsect2/info/title">
  <!-- the ID is output in the block.object call for refsect2 -->
  <h3>
    <xsl:apply-templates/>
  </h3>
</xsl:template>

<xsl:template match="refsect3/title|refsect3/info/title">
  <!-- the ID is output in the block.object call for refsect3 -->
  <h4>
    <xsl:apply-templates/>
  </h4>
</xsl:template>

<xsl:template match="refsectioninfo|refsection/info"></xsl:template>
<xsl:template match="refsect1info|refsect1/info"></xsl:template>
<xsl:template match="refsect2info|refsect2/info"></xsl:template>
<xsl:template match="refsect3info|refsect3/info"></xsl:template>


<!-- ==================================================================== -->

</xsl:stylesheet>
