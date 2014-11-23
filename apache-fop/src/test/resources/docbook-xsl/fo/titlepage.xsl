<?xml version='1.0'?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fo="http://www.w3.org/1999/XSL/Format"
                version='1.0'>

<!-- ********************************************************************
     $Id: titlepage.xsl 9286 2012-04-19 10:10:58Z bobstayton $
     ********************************************************************

     This file is part of the XSL DocBook Stylesheet distribution.
     See ../README or http://docbook.sf.net/release/xsl/current/ for
     copyright and other information.

     ******************************************************************** -->

<!-- ==================================================================== -->

<xsl:attribute-set name="book.titlepage.recto.style">
  <xsl:attribute name="font-family">
    <xsl:value-of select="$title.fontset"/>
  </xsl:attribute>
  <xsl:attribute name="font-weight">bold</xsl:attribute>
  <xsl:attribute name="font-size">12pt</xsl:attribute>
  <xsl:attribute name="text-align">center</xsl:attribute>
</xsl:attribute-set>

<xsl:attribute-set name="book.titlepage.verso.style">
  <xsl:attribute name="font-size">10pt</xsl:attribute>
</xsl:attribute-set>

<xsl:attribute-set name="article.titlepage.recto.style"/>
<xsl:attribute-set name="article.titlepage.verso.style"/>

<xsl:attribute-set name="set.titlepage.recto.style"/>
<xsl:attribute-set name="set.titlepage.verso.style"/>

<xsl:attribute-set name="part.titlepage.recto.style">
  <xsl:attribute name="text-align">center</xsl:attribute>
</xsl:attribute-set>

<xsl:attribute-set name="part.titlepage.verso.style"/>

<xsl:attribute-set name="partintro.titlepage.recto.style"/>
<xsl:attribute-set name="partintro.titlepage.verso.style"/>

<xsl:attribute-set name="reference.titlepage.recto.style"/>
<xsl:attribute-set name="reference.titlepage.verso.style"/>

<xsl:attribute-set name="dedication.titlepage.recto.style"/>
<xsl:attribute-set name="dedication.titlepage.verso.style"/>

<xsl:attribute-set name="acknowledgements.titlepage.recto.style"/>
<xsl:attribute-set name="acknowledgements.titlepage.verso.style"/>

<xsl:attribute-set name="preface.titlepage.recto.style"/>
<xsl:attribute-set name="preface.titlepage.verso.style"/>

<xsl:attribute-set name="chapter.titlepage.recto.style"/>
<xsl:attribute-set name="chapter.titlepage.verso.style"/>

<xsl:attribute-set name="appendix.titlepage.recto.style"/>
<xsl:attribute-set name="appendix.titlepage.verso.style"/>

<xsl:attribute-set name="bibliography.titlepage.recto.style"/>
<xsl:attribute-set name="bibliography.titlepage.verso.style"/>

<xsl:attribute-set name="bibliodiv.titlepage.recto.style"/>
<xsl:attribute-set name="bibliodiv.titlepage.verso.style"/>

<xsl:attribute-set name="glossary.titlepage.recto.style"/>
<xsl:attribute-set name="glossary.titlepage.verso.style"/>

<xsl:attribute-set name="glossdiv.titlepage.recto.style"/>
<xsl:attribute-set name="glossdiv.titlepage.verso.style"/>

<xsl:attribute-set name="index.titlepage.recto.style"/>
<xsl:attribute-set name="index.titlepage.verso.style"/>

<xsl:attribute-set name="setindex.titlepage.recto.style"/>
<xsl:attribute-set name="setindex.titlepage.verso.style"/>

<xsl:attribute-set name="indexdiv.titlepage.recto.style"/>
<xsl:attribute-set name="indexdiv.titlepage.verso.style"/>

<xsl:attribute-set name="colophon.titlepage.recto.style"/>
<xsl:attribute-set name="colophon.titlepage.verso.style"/>

<xsl:attribute-set name="sidebar.titlepage.recto.style"/>
<xsl:attribute-set name="sidebar.titlepage.verso.style"/>

<xsl:attribute-set name="qandaset.titlepage.recto.style"/>
<xsl:attribute-set name="qandaset.titlepage.verso.style"/>

<xsl:attribute-set name="section.titlepage.recto.style">
  <xsl:attribute name="keep-together.within-column">always</xsl:attribute>
</xsl:attribute-set>

<xsl:attribute-set name="section.titlepage.verso.style">
  <xsl:attribute name="keep-together.within-column">always</xsl:attribute>
  <xsl:attribute name="keep-with-next.within-column">always</xsl:attribute>
</xsl:attribute-set>

<xsl:attribute-set name="sect1.titlepage.recto.style"
                   use-attribute-sets="section.titlepage.recto.style"/>
<xsl:attribute-set name="sect1.titlepage.verso.style"
                   use-attribute-sets="section.titlepage.verso.style"/>

<xsl:attribute-set name="sect2.titlepage.recto.style"
                   use-attribute-sets="section.titlepage.recto.style"/>
<xsl:attribute-set name="sect2.titlepage.verso.style"
                   use-attribute-sets="section.titlepage.verso.style"/>

<xsl:attribute-set name="sect3.titlepage.recto.style"
                   use-attribute-sets="section.titlepage.recto.style"/>
<xsl:attribute-set name="sect3.titlepage.verso.style"
                   use-attribute-sets="section.titlepage.verso.style"/>

<xsl:attribute-set name="sect4.titlepage.recto.style"
                   use-attribute-sets="section.titlepage.recto.style"/>
<xsl:attribute-set name="sect4.titlepage.verso.style"
                   use-attribute-sets="section.titlepage.verso.style"/>

<xsl:attribute-set name="sect5.titlepage.recto.style"
                   use-attribute-sets="section.titlepage.recto.style"/>
<xsl:attribute-set name="sect5.titlepage.verso.style"
                   use-attribute-sets="section.titlepage.verso.style"/>

<xsl:attribute-set name="simplesect.titlepage.recto.style"
                   use-attribute-sets="section.titlepage.recto.style"/>
<xsl:attribute-set name="simplesect.titlepage.verso.style"
                   use-attribute-sets="section.titlepage.verso.style"/>

<xsl:attribute-set name="topic.titlepage.recto.style"/>
<xsl:attribute-set name="topic.titlepage.verso.style"/>

<xsl:attribute-set name="refnamediv.titlepage.recto.style"
                   use-attribute-sets="section.titlepage.recto.style"/>
<xsl:attribute-set name="refnamediv.titlepage.verso.style"
                   use-attribute-sets="section.titlepage.verso.style"/>

<xsl:attribute-set name="refsynopsisdiv.titlepage.recto.style"
                   use-attribute-sets="section.titlepage.recto.style"/>
<xsl:attribute-set name="refsynopsisdiv.titlepage.verso.style"
                   use-attribute-sets="section.titlepage.verso.style"/>

<xsl:attribute-set name="refsection.titlepage.recto.style"
                   use-attribute-sets="section.titlepage.recto.style"/>
<xsl:attribute-set name="refsection.titlepage.verso.style"
                   use-attribute-sets="section.titlepage.verso.style"/>

<xsl:attribute-set name="refsect1.titlepage.recto.style"
                   use-attribute-sets="section.titlepage.recto.style"/>
<xsl:attribute-set name="refsect1.titlepage.verso.style"
                   use-attribute-sets="section.titlepage.verso.style"/>

<xsl:attribute-set name="refsect2.titlepage.recto.style"
                   use-attribute-sets="section.titlepage.recto.style"/>
<xsl:attribute-set name="refsect2.titlepage.verso.style"
                   use-attribute-sets="section.titlepage.verso.style"/>

<xsl:attribute-set name="refsect3.titlepage.recto.style"
                   use-attribute-sets="section.titlepage.recto.style"/>
<xsl:attribute-set name="refsect3.titlepage.verso.style"
                   use-attribute-sets="section.titlepage.verso.style"/>

<xsl:attribute-set name="table.of.contents.titlepage.recto.style"/>
<xsl:attribute-set name="table.of.contents.titlepage.verso.style"/>

<xsl:attribute-set name="list.of.tables.titlepage.recto.style"/>
<xsl:attribute-set name="list.of.tables.contents.titlepage.verso.style"/>

<xsl:attribute-set name="list.of.figures.titlepage.recto.style"/>
<xsl:attribute-set name="list.of.figures.contents.titlepage.verso.style"/>

<xsl:attribute-set name="list.of.equations.titlepage.recto.style"/>
<xsl:attribute-set name="list.of.equations.contents.titlepage.verso.style"/>

<xsl:attribute-set name="list.of.examples.titlepage.recto.style"/>
<xsl:attribute-set name="list.of.examples.contents.titlepage.verso.style"/>

<xsl:attribute-set name="list.of.procedures.titlepage.recto.style"/>
<xsl:attribute-set name="list.of.procedures.contents.titlepage.verso.style"/>

<xsl:attribute-set name="list.of.unknowns.titlepage.recto.style"/>
<xsl:attribute-set name="list.of.unknowns.contents.titlepage.verso.style"/>

<xsl:attribute-set name="component.list.of.tables.titlepage.recto.style"/>
<xsl:attribute-set name="component.list.of.tables.contents.titlepage.verso.style"/>

<xsl:attribute-set name="component.list.of.figures.titlepage.recto.style"/>
<xsl:attribute-set name="component.list.of.figures.contents.titlepage.verso.style"/>

<xsl:attribute-set name="component.list.of.equations.titlepage.recto.style"/>
<xsl:attribute-set name="component.list.of.equations.contents.titlepage.verso.style"/>

<xsl:attribute-set name="component.list.of.examples.titlepage.recto.style"/>
<xsl:attribute-set name="component.list.of.examples.contents.titlepage.verso.style"/>

<xsl:attribute-set name="component.list.of.procedures.titlepage.recto.style"/>
<xsl:attribute-set name="component.list.of.procedures.contents.titlepage.verso.style"/>

<xsl:attribute-set name="component.list.of.unknowns.titlepage.recto.style"/>
<xsl:attribute-set name="component.list.of.unknowns.contents.titlepage.verso.style"/>

<!-- ==================================================================== -->

<xsl:template match="*" mode="titlepage.mode">
  <!-- if an element isn't found in this mode, try the default mode -->
  <xsl:apply-templates select="."/>
</xsl:template>

<xsl:template match="abbrev" mode="titlepage.mode">
  <xsl:apply-templates mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="abstract" mode="titlepage.mode">
  <fo:block xsl:use-attribute-sets="abstract.properties">
    <fo:block xsl:use-attribute-sets="abstract.title.properties">
      <xsl:choose>
	<xsl:when test="title|info/title">
	  <xsl:apply-templates select="title|info/title"/>
	</xsl:when>
	<xsl:otherwise>
	  <xsl:call-template name="gentext">
	    <xsl:with-param name="key" select="'Abstract'"/>
	  </xsl:call-template>
	</xsl:otherwise>
      </xsl:choose>
    </fo:block>
    <xsl:apply-templates select="*[not(self::title)]" mode="titlepage.mode"/>
  </fo:block>
</xsl:template>

<xsl:template match="abstract/title" mode="titlepage.mode"/>

<xsl:template match="abstract/title" mode="titlepage.abstract.title.mode">
  <xsl:apply-templates mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="address" mode="titlepage.mode">
  <!-- use the normal address handling code -->
  <xsl:apply-templates select="."/>
</xsl:template>

<xsl:template match="affiliation" mode="titlepage.mode">
  <fo:block>
    <xsl:apply-templates mode="titlepage.mode"/>
  </fo:block>
</xsl:template>

<xsl:template match="artpagenums" mode="titlepage.mode">
  <xsl:apply-templates mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="author" mode="titlepage.mode">
  <fo:block>
    <xsl:call-template name="anchor"/>
    <xsl:choose>
      <xsl:when test="orgname">
        <xsl:apply-templates/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:call-template name="person.name"/>
        <xsl:if test="affiliation/orgname">
          <xsl:text>, </xsl:text>
          <xsl:apply-templates select="affiliation/orgname" mode="titlepage.mode"/>
        </xsl:if>
        <xsl:if test="email|affiliation/address/email">
          <xsl:text> </xsl:text>
          <xsl:apply-templates select="(email|affiliation/address/email)[1]"/>
        </xsl:if>
      </xsl:otherwise>
    </xsl:choose>
  </fo:block>
</xsl:template>

<xsl:template match="authorblurb" mode="titlepage.mode">
  <xsl:apply-templates mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="authorgroup" mode="titlepage.mode">
  <fo:wrapper>
    <xsl:call-template name="anchor"/>
    <xsl:apply-templates mode="titlepage.mode"/>
  </fo:wrapper>
</xsl:template>

<xsl:template match="authorinitials" mode="titlepage.mode">
  <xsl:apply-templates mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="bibliomisc" mode="titlepage.mode">
  <xsl:apply-templates mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="bibliomset" mode="titlepage.mode">
  <xsl:apply-templates mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="collab" mode="titlepage.mode">
  <xsl:apply-templates mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="collabname" mode="titlepage.mode">
  <xsl:apply-templates mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="confgroup" mode="titlepage.mode">
  <fo:block>
    <xsl:apply-templates mode="titlepage.mode"/>
  </fo:block>
</xsl:template>

<xsl:template match="confdates" mode="titlepage.mode">
  <fo:block>
    <xsl:apply-templates mode="titlepage.mode"/>
  </fo:block>
</xsl:template>

<xsl:template match="conftitle" mode="titlepage.mode">
  <fo:block>
    <xsl:apply-templates mode="titlepage.mode"/>
  </fo:block>
</xsl:template>

<xsl:template match="confnum" mode="titlepage.mode">
  <!-- suppress -->
</xsl:template>

<xsl:template match="contractnum" mode="titlepage.mode">
  <xsl:apply-templates mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="contractsponsor" mode="titlepage.mode">
  <xsl:apply-templates mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="contrib" mode="titlepage.mode">
  <xsl:apply-templates mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="copyright" mode="titlepage.mode">
  <xsl:call-template name="gentext">
    <xsl:with-param name="key" select="'Copyright'"/>
  </xsl:call-template>
  <xsl:call-template name="gentext.space"/>
  <xsl:call-template name="dingbat">
    <xsl:with-param name="dingbat">copyright</xsl:with-param>
  </xsl:call-template>
  <xsl:call-template name="gentext.space"/>
  <xsl:call-template name="copyright.years">
    <xsl:with-param name="years" select="year"/>
    <xsl:with-param name="print.ranges" select="$make.year.ranges"/>
    <xsl:with-param name="single.year.ranges"
                    select="$make.single.year.ranges"/>
  </xsl:call-template>
  <xsl:call-template name="gentext.space"/>
  <xsl:apply-templates select="holder" mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="year" mode="titlepage.mode">
  <xsl:apply-templates/>
</xsl:template>

<xsl:template match="holder" mode="titlepage.mode">
  <xsl:apply-templates/>
  <xsl:if test="position() &lt; last()">
    <xsl:text>, </xsl:text>
  </xsl:if>
</xsl:template>

<xsl:template match="corpauthor" mode="titlepage.mode">
  <xsl:apply-templates mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="corpcredit" mode="titlepage.mode">
  <xsl:apply-templates mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="corpname" mode="titlepage.mode">
  <xsl:apply-templates mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="date" mode="titlepage.mode">
  <xsl:apply-templates mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="edition" mode="titlepage.mode">
  <xsl:apply-templates mode="titlepage.mode"/>
  <xsl:call-template name="gentext.space"/>
  <xsl:call-template name="gentext">
    <xsl:with-param name="key" select="'Edition'"/>
  </xsl:call-template>
</xsl:template>

<xsl:template match="editor" mode="titlepage.mode">
  <!-- The first editor is dealt with in the following template,
       which in turn displays all editors of the same mode. -->
</xsl:template>

<xsl:template match="editor[1]" priority="2" mode="titlepage.mode">
  <xsl:call-template name="gentext.edited.by"/>
  <xsl:call-template name="gentext.space"/>
  <xsl:call-template name="person.name.list">
    <xsl:with-param name="person.list" select="../editor"/>
  </xsl:call-template>
</xsl:template>

<xsl:template match="firstname" mode="titlepage.mode">
  <xsl:apply-templates mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="graphic" mode="titlepage.mode">
  <!-- use the normal graphic handling code -->
  <xsl:apply-templates select="."/>
</xsl:template>

<xsl:template match="honorific" mode="titlepage.mode">
  <xsl:apply-templates mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="isbn" mode="titlepage.mode">
  <xsl:apply-templates mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="issn" mode="titlepage.mode">
  <xsl:apply-templates mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="biblioid" mode="titlepage.mode">
  <xsl:apply-templates mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="itermset" mode="titlepage.mode">
  <xsl:apply-templates select="indexterm"/>
</xsl:template>

<xsl:template match="invpartnumber" mode="titlepage.mode">
  <xsl:apply-templates mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="issuenum" mode="titlepage.mode">
  <xsl:apply-templates mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="jobtitle" mode="titlepage.mode">
  <fo:block>
    <xsl:apply-templates mode="titlepage.mode"/>
  </fo:block>
</xsl:template>

<xsl:template match="keywordset" mode="titlepage.mode">
</xsl:template>

<xsl:template match="legalnotice" mode="titlepage.mode">

  <xsl:variable name="id">
    <xsl:call-template name="object.id"/>
  </xsl:variable>

  <fo:block id="{$id}">
    <xsl:if test="title"> <!-- FIXME: add param for using default title? -->
      <xsl:call-template name="formal.object.heading"/>
    </xsl:if>
    <xsl:apply-templates mode="titlepage.mode"/>
  </fo:block>
</xsl:template>

<xsl:template match="legalnotice/title" mode="titlepage.mode">
</xsl:template>

<xsl:template match="lineage" mode="titlepage.mode">
  <xsl:apply-templates mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="modespec" mode="titlepage.mode">
  <!-- discard -->
</xsl:template>

<xsl:template match="orgdiv" mode="titlepage.mode">
  <xsl:if test="preceding-sibling::*[1][self::orgname]">
    <xsl:text> </xsl:text>
  </xsl:if>
  <xsl:apply-templates mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="orgname" mode="titlepage.mode">
  <xsl:apply-templates mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="othercredit" mode="titlepage.mode">
  <xsl:variable name="contrib" select="string(contrib)"/>
  <xsl:choose>
    <xsl:when test="contrib">
      <xsl:if test="not(preceding-sibling::othercredit[string(contrib)=$contrib])">
        <fo:block>
          <xsl:apply-templates mode="titlepage.mode" select="contrib"/>
          <xsl:text>: </xsl:text>
          <xsl:call-template name="person.name"/>
          <xsl:apply-templates mode="titlepage.mode" select="affiliation"/>
          <xsl:apply-templates select="following-sibling::othercredit[string(contrib)=$contrib]" mode="titlepage.othercredits"/>
        </fo:block>
      </xsl:if>
    </xsl:when>
    <xsl:otherwise>
      <fo:block><xsl:call-template name="person.name"/></fo:block>
      <xsl:apply-templates mode="titlepage.mode" select="./affiliation"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="othercredit" mode="titlepage.othercredits">
  <xsl:text>, </xsl:text>
  <xsl:call-template name="person.name"/>
</xsl:template>

<xsl:template match="othername" mode="titlepage.mode">
  <xsl:apply-templates mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="pagenums" mode="titlepage.mode">
  <xsl:apply-templates mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="printhistory" mode="titlepage.mode">
  <xsl:apply-templates mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="productname" mode="titlepage.mode">
  <xsl:apply-templates mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="productnumber" mode="titlepage.mode">
  <xsl:apply-templates mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="pubdate" mode="titlepage.mode">
  <xsl:apply-templates mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="publisher" mode="titlepage.mode">
  <fo:block>
    <xsl:apply-templates mode="titlepage.mode"/>
  </fo:block>
</xsl:template>

<xsl:template match="publishername" mode="titlepage.mode">
  <xsl:apply-templates mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="pubsnumber" mode="titlepage.mode">
  <xsl:apply-templates mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="releaseinfo" mode="titlepage.mode">
  <xsl:apply-templates mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="revhistory" mode="titlepage.mode">

  <xsl:variable name="explicit.table.width">
    <xsl:call-template name="pi.dbfo_table-width"/>
  </xsl:variable>

  <xsl:variable name="table.width">
    <xsl:choose>
      <xsl:when test="$explicit.table.width != ''">
        <xsl:value-of select="$explicit.table.width"/>
      </xsl:when>
      <xsl:when test="$default.table.width = ''">
        <xsl:text>100%</xsl:text>
      </xsl:when>
      <xsl:otherwise>
        <xsl:value-of select="$default.table.width"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

 <fo:table table-layout="fixed" width="{$table.width}" xsl:use-attribute-sets="revhistory.table.properties">
    <fo:table-column column-number="1" column-width="proportional-column-width(1)"/>
    <fo:table-column column-number="2" column-width="proportional-column-width(1)"/>
    <fo:table-column column-number="3" column-width="proportional-column-width(1)"/>
    <fo:table-body start-indent="0pt" end-indent="0pt">
      <fo:table-row>
        <fo:table-cell number-columns-spanned="3" xsl:use-attribute-sets="revhistory.table.cell.properties">
          <fo:block xsl:use-attribute-sets="revhistory.title.properties">
	    <xsl:choose>
	      <xsl:when test="title|info/title">
		<xsl:apply-templates select="title|info/title" mode="titlepage.mode"/>
	      </xsl:when>
	      <xsl:otherwise>
		<xsl:call-template name="gentext">
		  <xsl:with-param name="key" select="'RevHistory'"/>
		</xsl:call-template>
	      </xsl:otherwise>
	    </xsl:choose>
	  </fo:block>
        </fo:table-cell>
      </fo:table-row>
      <xsl:apply-templates select="*[not(self::title)]" mode="titlepage.mode"/>
    </fo:table-body>
  </fo:table>

</xsl:template>


<xsl:template match="revhistory/revision" mode="titlepage.mode">
  <xsl:variable name="revnumber" select="revnumber"/>
  <xsl:variable name="revdate"   select="date"/>
  <xsl:variable name="revauthor" select="authorinitials|author"/>
  <xsl:variable name="revremark" select="revremark|revdescription"/>
  <fo:table-row>
    <fo:table-cell xsl:use-attribute-sets="revhistory.table.cell.properties">
      <fo:block>
        <xsl:if test="$revnumber">
          <xsl:call-template name="gentext">
            <xsl:with-param name="key" select="'Revision'"/>
          </xsl:call-template>
          <xsl:call-template name="gentext.space"/>
          <xsl:apply-templates select="$revnumber[1]" mode="titlepage.mode"/>
        </xsl:if>
      </fo:block>
    </fo:table-cell>
    <fo:table-cell xsl:use-attribute-sets="revhistory.table.cell.properties">
      <fo:block>
        <xsl:apply-templates select="$revdate[1]" mode="titlepage.mode"/>
      </fo:block>
    </fo:table-cell>
    <fo:table-cell xsl:use-attribute-sets="revhistory.table.cell.properties">
      <fo:block>
        <xsl:for-each select="$revauthor">
          <xsl:apply-templates select="." mode="titlepage.mode"/>
          <xsl:if test="position() != last()">
            <xsl:text>, </xsl:text>
          </xsl:if>
        </xsl:for-each>
      </fo:block>
    </fo:table-cell>
  </fo:table-row>
  <xsl:if test="$revremark">
    <fo:table-row>
      <fo:table-cell number-columns-spanned="3" xsl:use-attribute-sets="revhistory.table.cell.properties">
        <fo:block>
          <xsl:apply-templates select="$revremark[1]" mode="titlepage.mode"/>
        </fo:block>
      </fo:table-cell>
    </fo:table-row>
  </xsl:if>
</xsl:template>

<xsl:template match="revision/revnumber" mode="titlepage.mode">
  <xsl:apply-templates mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="revision/date" mode="titlepage.mode">
  <xsl:apply-templates mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="revision/authorinitials" mode="titlepage.mode">
  <xsl:apply-templates mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="revision/author" mode="titlepage.mode">
  <xsl:apply-templates mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="revision/revremark" mode="titlepage.mode">
  <xsl:apply-templates mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="revision/revdescription" mode="titlepage.mode">
  <xsl:apply-templates mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="seriesvolnums" mode="titlepage.mode">
  <xsl:apply-templates mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="shortaffil" mode="titlepage.mode">
  <xsl:apply-templates mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="subjectset" mode="titlepage.mode">
  <!-- discard -->
</xsl:template>

<xsl:template match="subtitle" mode="titlepage.mode">
  <xsl:apply-templates mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="surname" mode="titlepage.mode">
  <xsl:apply-templates mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="title" mode="titlepage.mode">
  <xsl:apply-templates mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="titleabbrev" mode="titlepage.mode">
  <xsl:apply-templates mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="volumenum" mode="titlepage.mode">
  <xsl:apply-templates mode="titlepage.mode"/>
</xsl:template>

<!-- ==================================================================== -->
<!-- Book templates -->

<!-- Note: these templates cannot use *.titlepage.recto.mode or
     *.titlepage.verso.mode. If they do then subsequent use of a custom
     titlepage.templates.xml file will not work correctly. -->

<!-- book recto -->

<xsl:template match="bookinfo/authorgroup|book/info/authorgroup"
              mode="titlepage.mode" priority="2">
  <fo:block>
    <xsl:call-template name="anchor"/>
    <xsl:apply-templates mode="titlepage.mode"/>
  </fo:block>
</xsl:template>

<!-- book verso -->

<xsl:template name="book.verso.title">
  <fo:block>
    <xsl:apply-templates mode="titlepage.mode"/>

    <xsl:if test="following-sibling::subtitle
                  |following-sibling::info/subtitle
                  |following-sibling::bookinfo/subtitle">
      <xsl:text>: </xsl:text>

      <xsl:apply-templates select="(following-sibling::subtitle
                                   |following-sibling::info/subtitle
                                   |following-sibling::bookinfo/subtitle)[1]"
                           mode="book.verso.subtitle.mode"/>
    </xsl:if>
  </fo:block>
</xsl:template>

<xsl:template match="subtitle" mode="book.verso.subtitle.mode">
  <xsl:apply-templates mode="titlepage.mode"/>
  <xsl:if test="following-sibling::subtitle">
    <xsl:text>: </xsl:text>
    <xsl:apply-templates select="following-sibling::subtitle[1]"
                         mode="book.verso.subtitle.mode"/>
  </xsl:if>
</xsl:template>

<xsl:template name="verso.authorgroup">
  <fo:block>
    <xsl:call-template name="gentext">
      <xsl:with-param name="key" select="'by'"/>
    </xsl:call-template>
    <xsl:text> </xsl:text>
    <xsl:call-template name="person.name.list">
      <xsl:with-param name="person.list" select="author|corpauthor|editor"/>
    </xsl:call-template>
  </fo:block>
  <xsl:apply-templates select="othercredit" mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="bookinfo/author|book/info/author"
              mode="titlepage.mode" priority="2">
  <fo:block>
    <xsl:call-template name="person.name"/>
  </fo:block>
</xsl:template>

<xsl:template match="bookinfo/corpauthor|book/info/corpauthor"
              mode="titlepage.mode" priority="2">
  <fo:block>
    <xsl:apply-templates/>
  </fo:block>
</xsl:template>

<xsl:template match="bookinfo/pubdate|book/info/pubdate"
              mode="titlepage.mode" priority="2">
  <fo:block>
    <xsl:call-template name="gentext">
      <xsl:with-param name="key" select="'pubdate'"/>
    </xsl:call-template>
    <xsl:text> </xsl:text>
    <xsl:apply-templates mode="titlepage.mode"/>
  </fo:block>
</xsl:template>

<!-- ==================================================================== -->

</xsl:stylesheet>
