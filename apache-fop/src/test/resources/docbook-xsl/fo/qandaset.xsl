<?xml version='1.0'?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fo="http://www.w3.org/1999/XSL/Format"
                version='1.0'>

<!-- ********************************************************************
     $Id: qandaset.xsl 8350 2009-03-17 07:24:29Z bobstayton $
     ********************************************************************

     This file is part of the XSL DocBook Stylesheet distribution.
     See ../README or http://docbook.sf.net/release/xsl/current/ for
     copyright and other information.

     ******************************************************************** -->

<!-- ==================================================================== -->

<xsl:template match="qandaset" name="process.qandaset">
  <xsl:variable name="id">
    <xsl:call-template name="object.id"/>
  </xsl:variable>

  <xsl:variable name="label-length">
    <xsl:call-template name="qandaset.label.length"/>
  </xsl:variable>
  
  <xsl:variable name="toc">
    <xsl:call-template name="pi.dbfo_toc"/>
  </xsl:variable>

  <xsl:variable name="toc.params">
    <xsl:call-template name="find.path.params">
      <xsl:with-param name="table" select="normalize-space($generate.toc)"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:variable name="content">
    <fo:block id="{$id}">
      <xsl:choose>
        <xsl:when test="parent::*">
          <xsl:if test="blockinfo/title|info/title|title">
            <xsl:apply-templates select="(blockinfo/title|
                                          info/title|title)[1]"/>
          </xsl:if>
        </xsl:when>
        <!-- If it is the root element -->
        <xsl:otherwise>
          <xsl:call-template name="qandaset.titlepage"/>
        </xsl:otherwise>
      </xsl:choose>
  
      <xsl:if test="(contains($toc.params, 'toc') and $toc != '0') 
                    or $toc = '1'">
        <xsl:call-template name="qandaset.toc">
          <xsl:with-param name="toc.title.p"
                          select="contains($toc.params, 'title')"/>
        </xsl:call-template>
      </xsl:if>

      <xsl:call-template name="qandaset.toc.separator"/>

      <xsl:apply-templates select="*[local-name(.) != 'title'
                                   and local-name(.) != 'titleabbrev'
                                   and local-name(.) != 'qandadiv'
                                   and local-name(.) != 'qandaentry']"/>
      <xsl:apply-templates select="qandadiv"/>
  
      <xsl:if test="qandaentry">
        <fo:list-block xsl:use-attribute-sets="list.block.spacing"
                       provisional-label-separation="0.2em">
          <xsl:attribute name="provisional-distance-between-starts">
            <xsl:choose>
              <xsl:when test="$label-length != ''">
                <xsl:value-of select="$label-length"/>
              </xsl:when>
              <xsl:otherwise>2.5em</xsl:otherwise>
            </xsl:choose>
          </xsl:attribute>
          <xsl:apply-templates select="qandaentry"/>
        </fo:list-block>
      </xsl:if>
    </fo:block>
  </xsl:variable>

  <xsl:choose>
    <xsl:when test="parent::*">
      <xsl:copy-of select="$content"/>
    </xsl:when>
    <!-- Otherwise create a page sequence -->
    <xsl:otherwise>
      <xsl:apply-templates select="." mode="page.sequence">
        <xsl:with-param name="content" select="$content"/>
        <xsl:with-param name="master-reference" select="'body'"/>
      </xsl:apply-templates>
    </xsl:otherwise>
  </xsl:choose>
  
</xsl:template>

<xsl:template name="qandaset.label.length">
  <xsl:param name="deflabel">
    <xsl:apply-templates select="." mode="qanda.defaultlabel"/>
  </xsl:param>

  <xsl:variable name="label-width">
    <xsl:call-template name="pi.dbfo_label-width"/>
  </xsl:variable>

  <xsl:choose>
    <xsl:when test="$label-width != ''">
      <xsl:value-of select="$label-width"/>
    </xsl:when>
    <xsl:when test="descendant::label">
      <xsl:call-template name="longest.term">
        <xsl:with-param name="terms" select="descendant::label"/>
        <xsl:with-param name="maxlength" select="20"/>
      </xsl:call-template>
      <xsl:text>em * 0.50</xsl:text>
    </xsl:when>
    <xsl:when test="contains($deflabel, 'qnumber') and
                    $qandadiv.autolabel != 0 and
                    $qanda.inherit.numeration != 0">
      <xsl:text>5em</xsl:text>
    </xsl:when>
    <xsl:when test="$deflabel ='qnumber' and
                    $qandadiv.autolabel != 0 and
                    $qanda.inherit.numeration != 0">
      <xsl:text>4em</xsl:text>
    </xsl:when>
    <xsl:when test="$deflabel = 'number'">
      <xsl:text>3em</xsl:text>
    </xsl:when>
    <xsl:otherwise>2.5em</xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="qandaset/blockinfo/title|qandset/info/title|qandaset/title">
  <xsl:variable name="enclsect" select="(ancestor::section
                                        | ancestor::simplesect
                                        | ancestor::sect5
                                        | ancestor::sect4
                                        | ancestor::sect3
                                        | ancestor::sect2
                                        | ancestor::sect1
                                        | ancestor::refsect3
                                        | ancestor::refsect2
                                        | ancestor::refsect1)[last()]"/>
  <xsl:variable name="sectlvl">
    <xsl:call-template name="section.level">
      <xsl:with-param name="node" select="$enclsect"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:call-template name="qanda.heading">
    <xsl:with-param name="level" select="$sectlvl + 1"/>
    <xsl:with-param name="marker" select="0"/>
    <xsl:with-param name="title">
      <xsl:apply-templates/>
    </xsl:with-param>
  </xsl:call-template>
</xsl:template>

<xsl:template match="qandaset/blockinfo|qandaset/info">
  <!-- what should this template really do? -->
  <xsl:apply-templates select="legalnotice" mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="qandadiv">
  <xsl:variable name="id"><xsl:call-template name="object.id"/></xsl:variable>


  <xsl:variable name="label-length">
    <xsl:call-template name="qandaset.label.length"/>
  </xsl:variable>
  
  <fo:block id="{$id}">
    <xsl:apply-templates select="(blockinfo/title|info/title|title)[1]"/>
    <xsl:apply-templates select="*[local-name(.) != 'title'
                                 and local-name(.) != 'titleabbrev'
                                 and local-name(.) != 'qandadiv'
                                 and local-name(.) != 'qandaentry']"/>
    <fo:block>
      <xsl:apply-templates select="qandadiv"/>

      <xsl:if test="qandaentry">
        <fo:list-block xsl:use-attribute-sets="list.block.spacing"
                       provisional-label-separation="0.2em">
          <xsl:attribute name="provisional-distance-between-starts">
            <xsl:choose>
              <xsl:when test="$label-length != ''">
                <xsl:value-of select="$label-length"/>
              </xsl:when>
              <xsl:otherwise>2.5em</xsl:otherwise>
            </xsl:choose>
          </xsl:attribute>
          <xsl:apply-templates select="qandaentry"/>
        </fo:list-block>
      </xsl:if>
    </fo:block>
  </fo:block>
</xsl:template>

<xsl:template match="qandadiv/blockinfo/title|qandadiv/info/title|qandadiv/title">
  <xsl:variable name="enclsect" select="(ancestor::section
                                        | ancestor::simplesect
                                        | ancestor::sect5
                                        | ancestor::sect4
                                        | ancestor::sect3
                                        | ancestor::sect2
                                        | ancestor::sect1
                                        | ancestor::refsect3
                                        | ancestor::refsect2
                                        | ancestor::refsect1)[last()]"/>
  <xsl:variable name="sectlvl">
    <xsl:call-template name="section.level">
      <xsl:with-param name="node" select="$enclsect"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:call-template name="qanda.heading">
    <xsl:with-param name="level"  select="$sectlvl + 1 + count(ancestor::qandadiv)"/>
    <xsl:with-param name="marker" select="0"/>
    <xsl:with-param name="title">
      <xsl:apply-templates select="parent::qandadiv" mode="label.markup"/>
      <xsl:if test="$qandadiv.autolabel != 0">
        <xsl:apply-templates select="." mode="intralabel.punctuation"/>
        <xsl:text> </xsl:text>
      </xsl:if>
      <xsl:apply-templates/>
    </xsl:with-param>
  </xsl:call-template>
</xsl:template>

<xsl:template match="qandaentry">
  <!-- Omit revhistory from fo:list-block because it is a table -->
  <xsl:apply-templates select="question|answer"/>
</xsl:template>

<xsl:template match="question">
  <xsl:variable name="id"><xsl:call-template name="object.id"/></xsl:variable>

  <xsl:variable name="entry.id">
    <xsl:call-template name="object.id">
      <xsl:with-param name="object" select="parent::*"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:variable name="deflabel">
    <xsl:apply-templates select="." mode="qanda.defaultlabel"/>
  </xsl:variable>


  <xsl:variable name="label.content">
    <xsl:apply-templates select="." mode="label.markup"/>
    <xsl:if test="contains($deflabel, 'number') and not(label)">
      <xsl:apply-templates select="." mode="intralabel.punctuation"/>
    </xsl:if>
  </xsl:variable>

  <fo:list-item id="{$entry.id}" xsl:use-attribute-sets="list.item.spacing">
    <fo:list-item-label id="{$id}" end-indent="label-end()">
        <xsl:if test="string-length($label.content) &gt; 0">
			<fo:block font-weight="bold">
			  <xsl:copy-of select="$label.content"/>          
			</fo:block>
        </xsl:if>
    </fo:list-item-label>
    <fo:list-item-body start-indent="body-start()">
      <xsl:choose>
        <xsl:when test="$deflabel = 'none' and not(label)">
          <fo:block font-weight="bold">
            <xsl:apply-templates select="*[local-name(.)!='label']"/>
          </fo:block>
        </xsl:when>
        <xsl:otherwise>
          <xsl:apply-templates select="*[local-name(.)!='label']"/>
        </xsl:otherwise>
      </xsl:choose>
      <!-- Uncomment this line to get revhistory output in the question -->
      <!-- <xsl:apply-templates select="preceding-sibling::revhistory"/> -->
    </fo:list-item-body>
  </fo:list-item>
</xsl:template>

<xsl:template match="answer">
  <xsl:variable name="id"><xsl:call-template name="object.id"/></xsl:variable>
  <xsl:variable name="entry.id">
    <xsl:call-template name="object.id">
      <xsl:with-param name="object" select="parent::*"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:variable name="deflabel">
    <xsl:apply-templates select="." mode="qanda.defaultlabel"/>
  </xsl:variable>

      <xsl:variable name="answer.label">
        <xsl:apply-templates select="." mode="label.markup"/>
      </xsl:variable>

  <fo:list-item xsl:use-attribute-sets="list.item.spacing">
    <fo:list-item-label id="{$id}" end-indent="label-end()">
      <xsl:choose>
        <xsl:when test="string-length($answer.label) &gt; 0">
			<fo:block font-weight="bold">
			  <xsl:copy-of select="$answer.label"/>
			</fo:block>
        </xsl:when>
        <xsl:otherwise>
			<fo:block/>
        </xsl:otherwise>
      </xsl:choose>
    </fo:list-item-label>
    <fo:list-item-body start-indent="body-start()">
      <xsl:apply-templates select="*[local-name(.)!='label' and local-name(.) != 'qandaentry']"/>
      <!-- * handle nested answer/qandaentry instances -->
      <!-- * (bug 1509043 from Daniel Leidert) -->
      <xsl:if test="descendant::question">
        <xsl:call-template name="process.qandaset"/>
      </xsl:if>
    </fo:list-item-body>
  </fo:list-item>
</xsl:template>

<xsl:template match="*" mode="qanda.defaultlabel">
  <xsl:choose>
    <xsl:when test="ancestor-or-self::*[@defaultlabel]">
      <xsl:value-of select="(ancestor-or-self::*[@defaultlabel])[last()]
                            /@defaultlabel"/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:value-of select="$qanda.defaultlabel"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="label">
  <xsl:apply-templates/>
</xsl:template>

<xsl:template name="qanda.heading">
  <xsl:param name="level" select="1"/>
  <xsl:param name="marker" select="0"/>
  <xsl:param name="title"/>
  <xsl:param name="titleabbrev"/>

  <fo:block xsl:use-attribute-sets="qanda.title.properties">
    <xsl:if test="$marker != 0">
      <fo:marker marker-class-name="section.head.marker">
        <xsl:choose>
          <xsl:when test="$titleabbrev = ''">
            <xsl:value-of select="$title"/>
          </xsl:when>
          <xsl:otherwise>
            <xsl:value-of select="$titleabbrev"/>
          </xsl:otherwise>
        </xsl:choose>
      </fo:marker>
    </xsl:if>
    <xsl:choose>
      <xsl:when test="$level=1">
        <fo:block xsl:use-attribute-sets="qanda.title.level1.properties">
          <xsl:copy-of select="$title"/>
        </fo:block>
      </xsl:when>
      <xsl:when test="$level=2">
        <fo:block xsl:use-attribute-sets="qanda.title.level2.properties">
          <xsl:copy-of select="$title"/>
        </fo:block>
      </xsl:when>
      <xsl:when test="$level=3">
        <fo:block xsl:use-attribute-sets="qanda.title.level3.properties">
          <xsl:copy-of select="$title"/>
        </fo:block>
      </xsl:when>
      <xsl:when test="$level=4">
        <fo:block xsl:use-attribute-sets="qanda.title.level4.properties">
          <xsl:copy-of select="$title"/>
        </fo:block>
      </xsl:when>
      <xsl:when test="$level=5">
        <fo:block xsl:use-attribute-sets="qanda.title.level5.properties">
          <xsl:copy-of select="$title"/>
        </fo:block>
      </xsl:when>
      <xsl:otherwise>
        <fo:block xsl:use-attribute-sets="qanda.title.level6.properties">
          <xsl:copy-of select="$title"/>
        </fo:block>
      </xsl:otherwise>
    </xsl:choose>
  </fo:block>
</xsl:template>

</xsl:stylesheet>
