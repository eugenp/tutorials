<?xml version='1.0'?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:doc="http://nwalsh.com/xsl/documentation/1.0"
                xmlns:fo="http://www.w3.org/1999/XSL/Format"
                xmlns:rx="http://www.renderx.com/XSL/Extensions"
                xmlns:stbl="http://nwalsh.com/xslt/ext/com.nwalsh.saxon.Table"
                xmlns:xtbl="com.nwalsh.xalan.Table"
                xmlns:lxslt="http://xml.apache.org/xslt"
                xmlns:ptbl="http://nwalsh.com/xslt/ext/xsltproc/python/Table"
                exclude-result-prefixes="doc stbl xtbl lxslt ptbl"
                version='1.0'>

<xsl:include href="../common/table.xsl"/>

<!-- ********************************************************************
     $Id: table.xsl 9666 2012-11-14 04:42:56Z bobstayton $
     ********************************************************************

     This file is part of the XSL DocBook Stylesheet distribution.
     See ../README or http://docbook.sf.net/release/xsl/current/ for
     copyright and other information.

     ******************************************************************** -->

<doc:reference xmlns="" xml:id="table-templates">
  <?dbhtml dir="fo"?>
  <info>
    <title>Formatting Object Table Reference</title>
    <releaseinfo role="meta">
      $Id: table.xsl 9666 2012-11-14 04:42:56Z bobstayton $
    </releaseinfo>
  </info>
  <partintro xml:id="partintro">
    <title>Introduction</title>
    <para>This is technical reference documentation for the FO
      table-processing templates in the DocBook XSL Stylesheets.</para>
    <para>This is not intended to be user documentation.  It is
      provided for developers writing customization layers for the
      stylesheets.</para>
  </partintro>
</doc:reference>

<!-- ==================================================================== -->

<lxslt:component prefix="xtbl"
                 functions="adjustColumnWidths"/>

<!-- ==================================================================== -->

<xsl:template name="make.table.content">
  <xsl:choose>
    <xsl:when test="tgroup|mediaobject|graphic">
      <xsl:call-template name="calsTable"/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:apply-templates select="." mode="htmlTable"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<!-- ==================================================================== -->

<xsl:template name="calsTable">

  <xsl:variable name="keep.together">
    <xsl:call-template name="pi.dbfo_keep-together"/>
  </xsl:variable>

  <xsl:for-each select="tgroup">

    <fo:table xsl:use-attribute-sets="table.table.properties">
      <xsl:if test="$keep.together != ''">
        <xsl:attribute name="keep-together.within-column">
          <xsl:value-of select="$keep.together"/>
        </xsl:attribute>
      </xsl:if>
      <xsl:call-template name="table.frame"/>
      <xsl:if test="following-sibling::tgroup">
        <xsl:attribute name="border-bottom-width">0pt</xsl:attribute>
        <xsl:attribute name="border-bottom-style">none</xsl:attribute>
        <xsl:attribute name="padding-bottom">0pt</xsl:attribute>
        <xsl:attribute name="margin-bottom">0pt</xsl:attribute>
        <xsl:attribute name="space-after">0pt</xsl:attribute>
        <xsl:attribute name="space-after.minimum">0pt</xsl:attribute>
        <xsl:attribute name="space-after.optimum">0pt</xsl:attribute>
        <xsl:attribute name="space-after.maximum">0pt</xsl:attribute>
      </xsl:if>
      <xsl:if test="preceding-sibling::tgroup">
        <xsl:attribute name="border-top-width">0pt</xsl:attribute>
        <xsl:attribute name="border-top-style">none</xsl:attribute>
        <xsl:attribute name="padding-top">0pt</xsl:attribute>
        <xsl:attribute name="margin-top">0pt</xsl:attribute>
        <xsl:attribute name="space-before">0pt</xsl:attribute>
        <xsl:attribute name="space-before.minimum">0pt</xsl:attribute>
        <xsl:attribute name="space-before.optimum">0pt</xsl:attribute>
        <xsl:attribute name="space-before.maximum">0pt</xsl:attribute>
      </xsl:if>
      <xsl:apply-templates select="."/>
    </fo:table>

    <xsl:for-each select="mediaobject|graphic">
      <xsl:apply-templates select="."/>
    </xsl:for-each>

  </xsl:for-each>

  <xsl:apply-templates select="caption"/>

</xsl:template>

<!-- ==================================================================== -->

<!-- Placeholder template enables wrapping a fo:table in
     another table for purposes of layout or applying
     extensions such as XEP table-omit-initial-header to
     create "continued" titles on page breaks. -->
<xsl:template name="table.layout">
  <xsl:param name="table.content" select="NOTANODE"/>

  <xsl:copy-of select="$table.content"/>
</xsl:template>

<xsl:template name="table.block">
  <xsl:param name="table.layout" select="NOTANODE"/>

  <xsl:variable name="id">
    <xsl:call-template name="object.id"/>
  </xsl:variable>

  <xsl:variable name="param.placement"
                select="substring-after(normalize-space(
                   $formal.title.placement), concat(local-name(.), ' '))"/>

  <xsl:variable name="placement">
    <xsl:choose>
      <xsl:when test="contains($param.placement, ' ')">
        <xsl:value-of select="substring-before($param.placement, ' ')"/>
      </xsl:when>
      <xsl:when test="$param.placement = ''">before</xsl:when>
      <xsl:otherwise>
        <xsl:value-of select="$param.placement"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:variable name="keep.together">
    <xsl:call-template name="pi.dbfo_keep-together"/>
  </xsl:variable>

  <xsl:choose>
    <xsl:when test="self::table">
      <fo:block id="{$id}"
                xsl:use-attribute-sets="table.properties">
        <xsl:if test="$keep.together != ''">
          <xsl:attribute name="keep-together.within-column">
            <xsl:value-of select="$keep.together"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:if test="$placement = 'before'">
          <xsl:call-template name="formal.object.heading">
            <xsl:with-param name="placement" select="$placement"/>
          </xsl:call-template>
        </xsl:if>
        <xsl:copy-of select="$table.layout"/>
        <xsl:call-template name="table.footnote.block"/>
        <xsl:if test="$placement != 'before'">
          <xsl:call-template name="formal.object.heading">
            <xsl:with-param name="placement" select="$placement"/>
          </xsl:call-template>
        </xsl:if>
      </fo:block>
    </xsl:when>
    <xsl:otherwise>
      <fo:block id="{$id}"
                xsl:use-attribute-sets="informaltable.properties">
        <xsl:if test="$keep.together != ''">
          <xsl:attribute name="keep-together.within-column">
            <xsl:value-of select="$keep.together"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:copy-of select="$table.layout"/>
        <xsl:call-template name="table.footnote.block"/>
      </fo:block>
    </xsl:otherwise>
  </xsl:choose>


</xsl:template>

<!-- Output a table's footnotes in a block -->
<xsl:template name="table.footnote.block">
  <xsl:if test=".//footnote">
    <fo:block keep-with-previous.within-column="always">
      <xsl:apply-templates select=".//footnote" mode="table.footnote.mode"/>
    </fo:block>
  </xsl:if>
</xsl:template>

<!-- ==================================================================== -->

<xsl:template name="table.container">
  <xsl:param name="table.block"/>
  <xsl:choose>
    <xsl:when test="@orient='land' and 
                    $fop.extensions = 0" >
      <fo:block-container reference-orientation="90"
            padding="6pt"
            xsl:use-attribute-sets="list.block.spacing">
        <xsl:attribute name="width">
          <xsl:call-template name="table.width"/>
        </xsl:attribute>
        <fo:block start-indent="0pt" end-indent="0pt">
          <xsl:copy-of select="$table.block"/>
        </fo:block>
      </fo:block-container>
    </xsl:when>
    <xsl:when test="@pgwide = 1">
      <fo:block xsl:use-attribute-sets="pgwide.properties">
        <xsl:copy-of select="$table.block"/>
      </fo:block>
    </xsl:when>
    <xsl:otherwise>
      <xsl:copy-of select="$table.block"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<!-- ==================================================================== -->

<xsl:template name="empty.table.cell">
  <xsl:param name="colnum" select="0"/>

  <xsl:variable name="rowsep">
    <xsl:choose>
      <!-- If this is the last row, rowsep never applies (except when 
           the ancestor tgroup has a following sibling tgroup) -->
      <xsl:when test="not(ancestor-or-self::row[1]/following-sibling::row
                          or ancestor-or-self::thead/following-sibling::tbody
                          or ancestor-or-self::tbody/preceding-sibling::tfoot)
                          and not(ancestor::tgroup/following-sibling::tgroup)">
        <xsl:value-of select="0"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:call-template name="inherited.table.attribute">
          <xsl:with-param name="entry" select="NOT-AN-ELEMENT-NAME"/>
          <xsl:with-param name="row" select="ancestor-or-self::row[1]"/>
          <xsl:with-param name="colnum" select="$colnum"/>
          <xsl:with-param name="attribute" select="'rowsep'"/>
        </xsl:call-template>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:variable name="colsep">
    <xsl:choose>
      <!-- If this is the last column, colsep never applies. -->
      <xsl:when test="number($colnum) &gt;= ancestor::tgroup/@cols">0</xsl:when>
      <xsl:otherwise>
        <xsl:call-template name="inherited.table.attribute">
          <xsl:with-param name="entry" select="NOT-AN-ELEMENT-NAME"/>
          <xsl:with-param name="row" select="ancestor-or-self::row[1]"/>
          <xsl:with-param name="colnum" select="$colnum"/>
          <xsl:with-param name="attribute" select="'colsep'"/>
        </xsl:call-template>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <fo:table-cell text-align="center"
                 display-align="center"
                 xsl:use-attribute-sets="table.cell.padding">
    <xsl:if test="$xep.extensions != 0">
      <!-- Suggested by RenderX to workaround a bug in their implementation -->
      <xsl:attribute name="keep-together.within-column">always</xsl:attribute>
    </xsl:if>
    <xsl:if test="$rowsep &gt; 0">
      <xsl:call-template name="border">
        <xsl:with-param name="side" select="'bottom'"/>
      </xsl:call-template>
    </xsl:if>

    <xsl:if test="$colsep &gt; 0 and number($colnum) &lt; ancestor::tgroup/@cols">
      <xsl:call-template name="border">
        <xsl:with-param name="side" select="'end'"/>
      </xsl:call-template>
    </xsl:if>

    <!-- fo:table-cell should not be empty -->
    <fo:block/>
  </fo:table-cell>
</xsl:template>

<!-- ==================================================================== -->
<xsl:template name="table.frame">
  <xsl:param name="frame">
    <xsl:choose>
      <xsl:when test="../@frame">
        <xsl:value-of select="../@frame"/>
      </xsl:when>
      <xsl:when test="$default.table.frame != ''">
        <xsl:value-of select="$default.table.frame"/>
      </xsl:when>
      <xsl:otherwise>all</xsl:otherwise>
    </xsl:choose>
  </xsl:param>


  <xsl:choose>
    <xsl:when test="$frame='all'">
      <xsl:attribute name="border-start-style">
        <xsl:value-of select="$table.frame.border.style"/>
      </xsl:attribute>
      <xsl:attribute name="border-end-style">
        <xsl:value-of select="$table.frame.border.style"/>
      </xsl:attribute>
      <xsl:attribute name="border-top-style">
        <xsl:value-of select="$table.frame.border.style"/>
      </xsl:attribute>
      <xsl:attribute name="border-bottom-style">
        <xsl:value-of select="$table.frame.border.style"/>
      </xsl:attribute>
      <xsl:attribute name="border-start-width">
        <xsl:value-of select="$table.frame.border.thickness"/>
      </xsl:attribute>
      <xsl:attribute name="border-end-width">
        <xsl:value-of select="$table.frame.border.thickness"/>
      </xsl:attribute>
      <xsl:attribute name="border-top-width">
        <xsl:value-of select="$table.frame.border.thickness"/>
      </xsl:attribute>
      <xsl:attribute name="border-bottom-width">
        <xsl:value-of select="$table.frame.border.thickness"/>
      </xsl:attribute>
      <xsl:attribute name="border-start-color">
        <xsl:value-of select="$table.frame.border.color"/>
      </xsl:attribute>
      <xsl:attribute name="border-end-color">
        <xsl:value-of select="$table.frame.border.color"/>
      </xsl:attribute>
      <xsl:attribute name="border-top-color">
        <xsl:value-of select="$table.frame.border.color"/>
      </xsl:attribute>
      <xsl:attribute name="border-bottom-color">
        <xsl:value-of select="$table.frame.border.color"/>
      </xsl:attribute>
    </xsl:when>
    <xsl:when test="$frame='bottom'">
      <xsl:attribute name="border-start-style">none</xsl:attribute>
      <xsl:attribute name="border-end-style">none</xsl:attribute>
      <xsl:attribute name="border-top-style">none</xsl:attribute>
      <xsl:attribute name="border-bottom-style">
        <xsl:value-of select="$table.frame.border.style"/>
      </xsl:attribute>
      <xsl:attribute name="border-bottom-width">
        <xsl:value-of select="$table.frame.border.thickness"/>
      </xsl:attribute>
      <xsl:attribute name="border-bottom-color">
        <xsl:value-of select="$table.frame.border.color"/>
      </xsl:attribute>
    </xsl:when>
    <xsl:when test="$frame='sides'">
      <xsl:attribute name="border-start-style">
        <xsl:value-of select="$table.frame.border.style"/>
      </xsl:attribute>
      <xsl:attribute name="border-end-style">
        <xsl:value-of select="$table.frame.border.style"/>
      </xsl:attribute>
      <xsl:attribute name="border-top-style">none</xsl:attribute>
      <xsl:attribute name="border-bottom-style">none</xsl:attribute>
      <xsl:attribute name="border-start-width">
        <xsl:value-of select="$table.frame.border.thickness"/>
      </xsl:attribute>
      <xsl:attribute name="border-end-width">
        <xsl:value-of select="$table.frame.border.thickness"/>
      </xsl:attribute>
      <xsl:attribute name="border-start-color">
        <xsl:value-of select="$table.frame.border.color"/>
      </xsl:attribute>
      <xsl:attribute name="border-end-color">
        <xsl:value-of select="$table.frame.border.color"/>
      </xsl:attribute>
    </xsl:when>
    <xsl:when test="$frame='lhs'">
      <xsl:attribute name="border-start-style">
        <xsl:value-of select="$table.frame.border.style"/>
      </xsl:attribute>
      <xsl:attribute name="border-end-style">none</xsl:attribute>
      <xsl:attribute name="border-top-style">none</xsl:attribute>
      <xsl:attribute name="border-bottom-style">none</xsl:attribute>
      <xsl:attribute name="border-start-width">
        <xsl:value-of select="$table.frame.border.thickness"/>
      </xsl:attribute>
      <xsl:attribute name="border-start-color">
        <xsl:value-of select="$table.frame.border.color"/>
      </xsl:attribute>
    </xsl:when>
    <xsl:when test="$frame='rhs'">
      <xsl:attribute name="border-end-style">
        <xsl:value-of select="$table.frame.border.style"/>
      </xsl:attribute>
      <xsl:attribute name="border-end-style">none</xsl:attribute>
      <xsl:attribute name="border-top-style">none</xsl:attribute>
      <xsl:attribute name="border-bottom-style">none</xsl:attribute>
      <xsl:attribute name="border-end-width">
        <xsl:value-of select="$table.frame.border.thickness"/>
      </xsl:attribute>
      <xsl:attribute name="border-end-color">
        <xsl:value-of select="$table.frame.border.color"/>
      </xsl:attribute>
    </xsl:when>
    <xsl:when test="$frame='top'">
      <xsl:attribute name="border-start-style">none</xsl:attribute>
      <xsl:attribute name="border-end-style">none</xsl:attribute>
      <xsl:attribute name="border-top-style">
        <xsl:value-of select="$table.frame.border.style"/>
      </xsl:attribute>
      <xsl:attribute name="border-bottom-style">none</xsl:attribute>
      <xsl:attribute name="border-top-width">
        <xsl:value-of select="$table.frame.border.thickness"/>
      </xsl:attribute>
      <xsl:attribute name="border-top-color">
        <xsl:value-of select="$table.frame.border.color"/>
      </xsl:attribute>
    </xsl:when>
    <xsl:when test="$frame='topbot'">
      <xsl:attribute name="border-start-style">none</xsl:attribute>
      <xsl:attribute name="border-end-style">none</xsl:attribute>
      <xsl:attribute name="border-top-style">
        <xsl:value-of select="$table.frame.border.style"/>
      </xsl:attribute>
      <xsl:attribute name="border-bottom-style">
        <xsl:value-of select="$table.frame.border.style"/>
      </xsl:attribute>
      <xsl:attribute name="border-top-width">
        <xsl:value-of select="$table.frame.border.thickness"/>
      </xsl:attribute>
      <xsl:attribute name="border-bottom-width">
        <xsl:value-of select="$table.frame.border.thickness"/>
      </xsl:attribute>
      <xsl:attribute name="border-top-color">
        <xsl:value-of select="$table.frame.border.color"/>
      </xsl:attribute>
      <xsl:attribute name="border-bottom-color">
        <xsl:value-of select="$table.frame.border.color"/>
      </xsl:attribute>
    </xsl:when>
    <xsl:when test="$frame='none'">
      <xsl:attribute name="border-start-style">none</xsl:attribute>
      <xsl:attribute name="border-end-style">none</xsl:attribute>
      <xsl:attribute name="border-top-style">none</xsl:attribute>
      <xsl:attribute name="border-bottom-style">none</xsl:attribute>
    </xsl:when>
    <xsl:otherwise>
      <xsl:message>
        <xsl:text>Impossible frame on table: </xsl:text>
        <xsl:value-of select="$frame"/>
      </xsl:message>
      <xsl:attribute name="border-start-style">none</xsl:attribute>
      <xsl:attribute name="border-end-style">none</xsl:attribute>
      <xsl:attribute name="border-top-style">none</xsl:attribute>
      <xsl:attribute name="border-bottom-style">none</xsl:attribute>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<!-- ==================================================================== -->

<xsl:template name="border">
  <xsl:param name="side" select="'start'"/>

  <xsl:attribute name="border-{$side}-width">
    <xsl:value-of select="$table.cell.border.thickness"/>
  </xsl:attribute>
  <xsl:attribute name="border-{$side}-style">
    <xsl:value-of select="$table.cell.border.style"/>
  </xsl:attribute>
  <xsl:attribute name="border-{$side}-color">
    <xsl:value-of select="$table.cell.border.color"/>
  </xsl:attribute>
</xsl:template>

<!-- ==================================================================== -->

<xsl:template match="tgroup" name="tgroup">
  <xsl:if test="not(@cols) or @cols = '' or string(number(@cols)) = 'NaN'">
    <xsl:message terminate="yes">
      <xsl:text>Error: CALS tables must specify the number of columns.</xsl:text>
    </xsl:message>
  </xsl:if>

  <xsl:variable name="table.width">
    <xsl:call-template name="table.width"/>
  </xsl:variable>

  <xsl:variable name="colspecs">
    <xsl:choose>
      <xsl:when test="$use.extensions != 0
                      and $tablecolumns.extension != 0">
        <xsl:call-template name="generate.colgroup.raw">
          <xsl:with-param name="cols" select="@cols"/>
        </xsl:call-template>
      </xsl:when>
      <xsl:otherwise>
        <xsl:call-template name="generate.colgroup">
          <xsl:with-param name="cols" select="@cols"/>
        </xsl:call-template>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:variable name="prop-columns"
                select=".//colspec[contains(@colwidth, '*')]"/>
  <xsl:if test="count($prop-columns) != 0 or
                $fop.extensions != 0 or
                $fop1.extensions != 0">
    <xsl:attribute name="table-layout">fixed</xsl:attribute>
  </xsl:if>
 
    <xsl:attribute name="width">
      <xsl:value-of select="$table.width"/>
    </xsl:attribute>
 
  <xsl:choose>
    <xsl:when test="$use.extensions != 0
                    and $tablecolumns.extension != 0">
      <xsl:choose>
        <xsl:when test="function-available('stbl:adjustColumnWidths')">
          <xsl:copy-of select="stbl:adjustColumnWidths($colspecs)"/>
        </xsl:when>
        <xsl:when test="function-available('xtbl:adjustColumnWidths')">
          <xsl:copy-of select="xtbl:adjustColumnWidths($colspecs)"/>
        </xsl:when>
        <xsl:when test="function-available('ptbl:adjustColumnWidths')">
          <xsl:copy-of select="ptbl:adjustColumnWidths($colspecs)"/>
        </xsl:when>
        <xsl:otherwise>
          <xsl:message terminate="yes">
            <xsl:text>No adjustColumnWidths function available.</xsl:text>
          </xsl:message>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:when>
    <xsl:otherwise>
      <xsl:copy-of select="$colspecs"/>
    </xsl:otherwise>
  </xsl:choose>

  <xsl:apply-templates select="thead"/>
  <xsl:apply-templates select="tfoot"/>
  <xsl:apply-templates select="tbody"/>
</xsl:template>

<xsl:template match="colspec"></xsl:template>

<xsl:template name="table.width">

  <xsl:variable name="numcols">
    <xsl:call-template name="widest-html-row">
      <xsl:with-param name="rows" select=".//tr"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:variable name="explicit.table.width">
    <xsl:choose>
      <xsl:when test="self::entrytbl">
        <xsl:call-template name="pi.dbfo_table-width"/>
      </xsl:when>
      <xsl:when test="self::table or self::informaltable">
        <xsl:call-template name="pi.dbfo_table-width"/>
      </xsl:when>
      <xsl:otherwise>
        <!-- * no dbfo@table-width PI as a child of this table, so check -->
        <!-- * the parent of this table to see if the table has any -->
        <!-- * sibling dbfo@table-width PIs (FIXME: 2007-07 MikeSmith: we -->
        <!-- * should really instead be checking here just to see if the -->
        <!-- * first preceding sibling of this table is a -->
        <!-- * dbfo@table-width PI) -->
        <xsl:call-template name="pi.dbfo_table-width">
          <xsl:with-param name="node" select=".."/>
        </xsl:call-template>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:variable name="column.sum">
    <xsl:choose>
      <!-- CALS table -->
      <xsl:when test="tgroup/@cols">
        <!-- change context to the first tgroup -->
        <xsl:for-each select="tgroup[1]">
          <xsl:if test="count(colspec) = @cols">
            <xsl:for-each select="colspec">
              <xsl:if test="position() != 1">
                <xsl:text> + </xsl:text>
              </xsl:if>
              <xsl:choose>
                <xsl:when test="not(@colwidth)">NOWIDTH</xsl:when>
                <xsl:when test="contains(@colwidth, '*')">NOWIDTH</xsl:when>
                <xsl:otherwise>
                  <xsl:value-of select="@colwidth"/>
                </xsl:otherwise>
              </xsl:choose>
            </xsl:for-each>
          </xsl:if>
        </xsl:for-each>
      </xsl:when>
      <xsl:otherwise>
        <!-- HTML table -->
        <xsl:if test="count(col|colgroup/col) = $numcols">
          <xsl:for-each select="col|colgroup/col">
            <xsl:if test="position() != 1">
              <xsl:text> + </xsl:text>
            </xsl:if>
            <xsl:choose>
              <xsl:when test="not(@width)">NOWIDTH</xsl:when>
              <xsl:when test="contains(@width, '%')">NOWIDTH</xsl:when>
              <xsl:otherwise>
                <xsl:value-of select="@width"/>
              </xsl:otherwise>
            </xsl:choose>
          </xsl:for-each>
        </xsl:if>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:variable name="column.sum.width">
    <xsl:if test="not(contains($column.sum, 'NOWIDTH'))">
      <xsl:value-of select="$column.sum"/>
    </xsl:if>
  </xsl:variable>

  <xsl:variable name="prop-columns"
                select=".//colspec[contains(@colwidth, '*')]"/>

  <xsl:variable name="table.width">
    <xsl:choose>
      <xsl:when test="$explicit.table.width != ''">
        <xsl:value-of select="$explicit.table.width"/>
      </xsl:when>
      <xsl:when test="$column.sum.width != ''">
        <xsl:value-of select="$column.sum.width"/>
      </xsl:when>
      <xsl:when test="$default.table.width = ''">
        <xsl:choose>
          <!-- These processors don't support table-layout="auto" -->
          <xsl:when test="$fop.extensions != 0 or
                          $fop1.extensions != 0">
            <xsl:text>100%</xsl:text>
          </xsl:when>
          <!-- Proportional columns imply 100% width -->
          <xsl:when test="count($prop-columns) != 0">
            <xsl:text>100%</xsl:text>
          </xsl:when>
          <xsl:otherwise>
            <xsl:text>auto</xsl:text>
          </xsl:otherwise>
        </xsl:choose>
      </xsl:when>
      <xsl:otherwise>
        <xsl:value-of select="$default.table.width"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>
  <xsl:value-of select="$table.width"/>

</xsl:template>

<xsl:template match="spanspec"></xsl:template>

<xsl:template match="thead">
  <xsl:variable name="tgroup" select="parent::*"/>

  <fo:table-header start-indent="0pt" end-indent="0pt">
    <xsl:choose>
      <!-- Use recursion if @morerows is used -->
      <xsl:when test="row/entry/@morerows|row/entrytbl/@morerows">
        <xsl:apply-templates select="row[1]">
          <xsl:with-param name="spans">
            <xsl:call-template name="blank.spans">
              <xsl:with-param name="cols" select="../@cols"/>
            </xsl:call-template>
          </xsl:with-param>
          <xsl:with-param name="browserows" select="'recurse'"/>
        </xsl:apply-templates>
      </xsl:when>
      <xsl:otherwise>
        <xsl:apply-templates select="row">
          <xsl:with-param name="spans">
            <xsl:call-template name="blank.spans">
              <xsl:with-param name="cols" select="../@cols"/>
            </xsl:call-template>
          </xsl:with-param>
          <xsl:with-param name="browserows" select="'loop'" />
        </xsl:apply-templates>
      </xsl:otherwise>
    </xsl:choose>
  </fo:table-header>
</xsl:template>

<xsl:template match="tfoot">
  <xsl:variable name="tgroup" select="parent::*"/>

  <fo:table-footer start-indent="0pt" end-indent="0pt">
    <xsl:choose>
      <!-- Use recursion if @morerows is used -->
      <xsl:when test="row/entry/@morerows|row/entrytbl/@morerows">
        <xsl:apply-templates select="row[1]">
          <xsl:with-param name="spans">
            <xsl:call-template name="blank.spans">
              <xsl:with-param name="cols" select="../@cols"/>
            </xsl:call-template>
          </xsl:with-param>
          <xsl:with-param name="browserows" select="'recurse'"/>
        </xsl:apply-templates>
      </xsl:when>
      <xsl:otherwise>
        <xsl:apply-templates select="row">
          <xsl:with-param name="spans">
            <xsl:call-template name="blank.spans">
              <xsl:with-param name="cols" select="../@cols"/>
            </xsl:call-template>
          </xsl:with-param>
          <xsl:with-param name="browserows" select="'loop'" />
        </xsl:apply-templates>
      </xsl:otherwise>
    </xsl:choose>
  </fo:table-footer>
</xsl:template>

<xsl:template match="tbody">
  <xsl:variable name="tgroup" select="parent::*"/>

  <fo:table-body start-indent="0pt" end-indent="0pt">
    <xsl:choose>
      <!-- Use recursion if @morerows is used -->
      <xsl:when test="row/entry/@morerows|row/entrytbl/@morerows">
        <xsl:apply-templates select="row[1]">
          <xsl:with-param name="spans">
            <xsl:call-template name="blank.spans">
              <xsl:with-param name="cols" select="../@cols"/>
            </xsl:call-template>
          </xsl:with-param>
          <xsl:with-param name="browserows" select="'recurse'"/>
        </xsl:apply-templates>
      </xsl:when>
      <xsl:otherwise>
        <xsl:apply-templates select="row">
          <xsl:with-param name="spans">
            <xsl:call-template name="blank.spans">
              <xsl:with-param name="cols" select="../@cols"/>
            </xsl:call-template>
          </xsl:with-param>
          <xsl:with-param name="browserows" select="'loop'" />
        </xsl:apply-templates>
      </xsl:otherwise>
    </xsl:choose>
  </fo:table-body>
</xsl:template>

<xsl:template match="row">
  <xsl:param name="spans"/>
  <xsl:param name="browserows"/>

  <xsl:choose>
    <xsl:when test="contains($spans, '0')">
      <xsl:call-template name="normal-row">
        <xsl:with-param name="spans" select="$spans"/>
        <xsl:with-param name="browserows" select="$browserows"/>
      </xsl:call-template>
    </xsl:when>
    <xsl:otherwise>
      <!--
      <xsl:message>
        <xsl:text>Ignoring row: </xsl:text>
        <xsl:value-of select="$spans"/>
        <xsl:text> = </xsl:text>
        <xsl:call-template name="consume-row">
          <xsl:with-param name="spans" select="$spans"/>
        </xsl:call-template>
      </xsl:message>
      -->

      <xsl:if test="normalize-space(.//text()) != ''">
        <xsl:message>Warning: overlapped row contains content!</xsl:message>
      </xsl:if>

      <fo:table-row>
        <xsl:comment> This row intentionally left blank </xsl:comment>
        <fo:table-cell><fo:block/></fo:table-cell>
      </fo:table-row>

      <xsl:if test="$browserows = 'recurse'">
        <xsl:apply-templates select="following-sibling::row[1]">
          <xsl:with-param name="spans">
            <xsl:call-template name="consume-row">
              <xsl:with-param name="spans" select="$spans"/>
            </xsl:call-template>
          </xsl:with-param>
        </xsl:apply-templates>
      </xsl:if>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template name="normal-row">
  <xsl:param name="spans"/>
  <xsl:param name="browserows"/>

  <fo:table-row>
    <xsl:call-template name="table.row.properties"/>
    <xsl:call-template name="anchor"/>

    <xsl:apply-templates select="(entry|entrytbl)[1]">
      <xsl:with-param name="spans" select="$spans"/>
    </xsl:apply-templates>
  </fo:table-row>

  <xsl:if test="$browserows = 'recurse'">
    <xsl:if test="following-sibling::row">
      <xsl:variable name="nextspans">
        <xsl:apply-templates select="(entry|entrytbl)[1]" mode="span">
          <xsl:with-param name="spans" select="$spans"/>
        </xsl:apply-templates>
      </xsl:variable>
  
      <xsl:apply-templates select="following-sibling::row[1]">
        <xsl:with-param name="spans" select="$nextspans"/>
        <xsl:with-param name="browserows" select="$browserows"/>
      </xsl:apply-templates>
    </xsl:if>
  </xsl:if>
</xsl:template>

<!-- customize this template to add row properties -->
<xsl:template name="table.row.properties">

  <xsl:variable name="row-height">
    <xsl:if test="processing-instruction('dbfo')">
      <xsl:call-template name="pi.dbfo_row-height"/>
    </xsl:if>
  </xsl:variable>

  <xsl:if test="$row-height != ''">
    <xsl:attribute name="block-progression-dimension">
      <xsl:value-of select="$row-height"/>
    </xsl:attribute>
  </xsl:if>

  <xsl:variable name="bgcolor">
    <xsl:call-template name="pi.dbfo_bgcolor"/>
  </xsl:variable>

  <xsl:if test="$bgcolor != ''">
    <xsl:attribute name="background-color">
      <xsl:value-of select="$bgcolor"/>
    </xsl:attribute>
  </xsl:if>

  <!-- Keep header row with next row -->
  <xsl:if test="ancestor::thead">
    <xsl:attribute name="keep-with-next.within-column">always</xsl:attribute>
  </xsl:if>

</xsl:template>

<xsl:template match="entry|entrytbl" name="entry">
  <xsl:param name="col" select="1"/>
  <xsl:param name="spans"/>

  <xsl:variable name="row" select="parent::row"/>
  <xsl:variable name="group" select="$row/parent::*[1]"/>
  <xsl:variable name="frame" select="ancestor::tgroup/parent::*/@frame"/>

  <xsl:variable name="empty.cell" select="count(node()) = 0"/>

  <xsl:variable name="named.colnum">
    <xsl:call-template name="entry.colnum"/>
  </xsl:variable>

  <xsl:variable name="entry.colnum">
    <xsl:choose>
      <xsl:when test="$named.colnum &gt; 0">
        <xsl:value-of select="$named.colnum"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:value-of select="$col"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:variable name="entry.colspan">
    <xsl:choose>
      <xsl:when test="@spanname or @namest">
        <xsl:call-template name="calculate.colspan"/>
      </xsl:when>
      <xsl:otherwise>1</xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:variable name="following.spans">
    <xsl:call-template name="calculate.following.spans">
      <xsl:with-param name="colspan" select="$entry.colspan"/>
      <xsl:with-param name="spans" select="$spans"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:variable name="rowsep">
    <xsl:choose>
      <!-- If this is the last row, rowsep never applies (except when 
           the ancestor tgroup has a following sibling tgroup) -->
      <xsl:when test="not(ancestor-or-self::row[1]/following-sibling::row
                          or ancestor-or-self::thead/following-sibling::tbody
                          or ancestor-or-self::tbody/preceding-sibling::tfoot)
                          and not(ancestor::tgroup/following-sibling::tgroup)">
        <xsl:value-of select="0"/>
      </xsl:when>
      <!-- Check for morerows too -->
      <xsl:when test="(@morerows and count(ancestor-or-self::row[1]/
                       following-sibling::row) = @morerows )
                      and not (ancestor-or-self::thead/following-sibling::tbody
                       or ancestor-or-self::tbody/preceding-sibling::tfoot)
                       and not(ancestor::tgroup/following-sibling::tgroup)">
        <xsl:value-of select="0"/>
      </xsl:when>

      <xsl:otherwise>
        <xsl:call-template name="inherited.table.attribute">
          <xsl:with-param name="entry" select="."/>
          <xsl:with-param name="colnum" select="$entry.colnum"/>
          <xsl:with-param name="attribute" select="'rowsep'"/>
        </xsl:call-template>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

<!--
  <xsl:message><xsl:value-of select="."/>: <xsl:value-of select="$rowsep"/></xsl:message>
-->

  <xsl:variable name="colsep">
    <xsl:choose>
      <!-- If this is the last column, colsep never applies. -->
      <xsl:when test="$following.spans = ''">0</xsl:when>
      <xsl:otherwise>
        <xsl:call-template name="inherited.table.attribute">
          <xsl:with-param name="entry" select="."/>
          <xsl:with-param name="colnum" select="$entry.colnum"/>
          <xsl:with-param name="attribute" select="'colsep'"/>
        </xsl:call-template>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:variable name="valign">
    <xsl:call-template name="inherited.table.attribute">
      <xsl:with-param name="entry" select="."/>
      <xsl:with-param name="colnum" select="$entry.colnum"/>
      <xsl:with-param name="attribute" select="'valign'"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:variable name="align">
    <xsl:call-template name="inherited.table.attribute">
      <xsl:with-param name="entry" select="."/>
      <xsl:with-param name="colnum" select="$entry.colnum"/>
      <xsl:with-param name="attribute" select="'align'"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:variable name="char">
    <xsl:call-template name="inherited.table.attribute">
      <xsl:with-param name="entry" select="."/>
      <xsl:with-param name="colnum" select="$entry.colnum"/>
      <xsl:with-param name="attribute" select="'char'"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:variable name="charoff">
    <xsl:call-template name="inherited.table.attribute">
      <xsl:with-param name="entry" select="."/>
      <xsl:with-param name="colnum" select="$entry.colnum"/>
      <xsl:with-param name="attribute" select="'charoff'"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:choose>
    <xsl:when test="$spans != '' and not(starts-with($spans,'0:'))">
      <xsl:call-template name="entry">
        <xsl:with-param name="col" select="$col+1"/>
        <xsl:with-param name="spans" select="substring-after($spans,':')"/>
      </xsl:call-template>
    </xsl:when>

    <xsl:when test="number($entry.colnum) &gt; $col">
      <xsl:call-template name="empty.table.cell">
        <xsl:with-param name="colnum" select="$col"/>
      </xsl:call-template>
      <xsl:call-template name="entry">
        <xsl:with-param name="col" select="$col+1"/>
        <xsl:with-param name="spans" select="substring-after($spans,':')"/>
      </xsl:call-template>
    </xsl:when>

    <xsl:otherwise>
      <xsl:variable name="cell.content">
        <fo:block>
          <xsl:call-template name="table.cell.block.properties"/>

          <!-- are we missing any indexterms? -->
          <xsl:if test="not(preceding-sibling::entry)
                        and not(parent::row/preceding-sibling::row)">
            <!-- this is the first entry of the first row -->
            <xsl:if test="ancestor::thead or
                          (ancestor::tbody
                           and not(ancestor::tbody/preceding-sibling::thead
                                   or ancestor::tbody/preceding-sibling::tbody))">
              <!-- of the thead or the first tbody -->
              <xsl:apply-templates select="ancestor::tgroup/preceding-sibling::indexterm"/>
            </xsl:if>
          </xsl:if>

          <!--
          <xsl:text>(</xsl:text>
          <xsl:value-of select="$rowsep"/>
          <xsl:text>,</xsl:text>
          <xsl:value-of select="$colsep"/>
          <xsl:text>)</xsl:text>
          -->
          <xsl:choose>
            <xsl:when test="$empty.cell">
              <xsl:text>&#160;</xsl:text>
            </xsl:when>
            <xsl:when test="self::entrytbl">
              <xsl:variable name="prop-columns"
                            select=".//colspec[contains(@colwidth, '*')]"/>
              <fo:table xsl:use-attribute-sets="table.table.properties">
                <xsl:if test="count($prop-columns) != 0">
                  <xsl:attribute name="table-layout">fixed</xsl:attribute>
                </xsl:if>
                <xsl:call-template name="tgroup"/>
              </fo:table>
            </xsl:when>
            <xsl:otherwise>
              <xsl:apply-templates/>
            </xsl:otherwise>
          </xsl:choose>
        </fo:block>
      </xsl:variable>

      <xsl:variable name="cell-orientation">
        <xsl:call-template name="pi.dbfo_orientation">
          <xsl:with-param name="node" select="ancestor-or-self::entry"/>
        </xsl:call-template>
      </xsl:variable>

      <xsl:variable name="row-orientation">
        <xsl:call-template name="pi.dbfo_orientation">
          <xsl:with-param name="node" select="ancestor-or-self::row"/>
        </xsl:call-template>
      </xsl:variable>

      <xsl:variable name="cell-width">
        <xsl:call-template name="pi.dbfo_rotated-width">
          <xsl:with-param name="node" select="ancestor-or-self::entry"/>
        </xsl:call-template>
      </xsl:variable>

      <xsl:variable name="row-width">
        <xsl:call-template name="pi.dbfo_rotated-width">
          <xsl:with-param name="node" select="ancestor-or-self::row"/>
        </xsl:call-template>
      </xsl:variable>

      <xsl:variable name="orientation">
        <xsl:choose>
          <xsl:when test="$cell-orientation != ''">
            <xsl:value-of select="$cell-orientation"/>
          </xsl:when>
          <xsl:otherwise>
            <xsl:value-of select="$row-orientation"/>
          </xsl:otherwise>
        </xsl:choose>
      </xsl:variable>

      <xsl:variable name="rotated-width">
        <xsl:choose>
          <xsl:when test="$cell-width != ''">
            <xsl:value-of select="$cell-width"/>
          </xsl:when>
          <xsl:otherwise>
            <xsl:value-of select="$row-width"/>
          </xsl:otherwise>
        </xsl:choose>
      </xsl:variable>

      <xsl:variable name="bgcolor">
        <xsl:call-template name="pi.dbfo_bgcolor">
          <xsl:with-param name="node" select="ancestor-or-self::entry"/>
        </xsl:call-template>
      </xsl:variable>

      <fo:table-cell xsl:use-attribute-sets="table.cell.padding">
        <xsl:call-template name="table.cell.properties">
          <xsl:with-param name="bgcolor.pi" select="$bgcolor"/>
          <xsl:with-param name="rowsep.inherit" select="$rowsep"/>
          <xsl:with-param name="colsep.inherit" select="$colsep"/>
          <xsl:with-param name="col" select="$col"/>
          <xsl:with-param name="valign.inherit" select="$valign"/>
          <xsl:with-param name="align.inherit" select="$align"/>
          <xsl:with-param name="char.inherit" select="$char"/>
        </xsl:call-template>

        <xsl:call-template name="anchor"/>

        <xsl:if test="@morerows">
          <xsl:attribute name="number-rows-spanned">
            <xsl:value-of select="@morerows+1"/>
          </xsl:attribute>
        </xsl:if>

        <xsl:if test="$entry.colspan &gt; 1">
          <xsl:attribute name="number-columns-spanned">
            <xsl:value-of select="$entry.colspan"/>
          </xsl:attribute>
        </xsl:if>

<!--
        <xsl:if test="@charoff">
          <xsl:attribute name="charoff">
            <xsl:value-of select="@charoff"/>
          </xsl:attribute>
        </xsl:if>
-->

        <xsl:choose>
          <xsl:when test="$fop.extensions = 0
                          and $orientation != ''">
            <fo:block-container reference-orientation="{$orientation}">
              <xsl:if test="$rotated-width != ''">
                <xsl:attribute name="width">
                  <xsl:value-of select="$rotated-width"/>
                </xsl:attribute>
              </xsl:if>
              <xsl:copy-of select="$cell.content"/>
            </fo:block-container>
          </xsl:when>
          <xsl:otherwise>
            <xsl:copy-of select="$cell.content"/>
          </xsl:otherwise>
        </xsl:choose>
      </fo:table-cell>

      <xsl:choose>
        <xsl:when test="following-sibling::entry|following-sibling::entrytbl">
          <xsl:apply-templates select="(following-sibling::entry
                                       |following-sibling::entrytbl)[1]">
            <xsl:with-param name="col" select="$col+$entry.colspan"/>
            <xsl:with-param name="spans" select="$following.spans"/>
          </xsl:apply-templates>
        </xsl:when>
        <xsl:otherwise>
          <xsl:call-template name="finaltd">
            <xsl:with-param name="spans" select="$following.spans"/>
            <xsl:with-param name="col" select="$col+$entry.colspan"/>
          </xsl:call-template>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<!-- Expand this template to add properties to any fo:table-cell -->
<xsl:template name="table.cell.properties">
  <xsl:param name="bgcolor.pi" select="''"/>
  <xsl:param name="rowsep.inherit" select="1"/>
  <xsl:param name="colsep.inherit" select="1"/>
  <xsl:param name="col" select="1"/>
  <xsl:param name="valign.inherit" select="''"/>
  <xsl:param name="align.inherit" select="''"/>
  <xsl:param name="char.inherit" select="''"/>

  <xsl:choose>
    <xsl:when test="ancestor::tgroup">
      <xsl:if test="$bgcolor.pi != ''">
        <xsl:attribute name="background-color">
          <xsl:value-of select="$bgcolor.pi"/>
        </xsl:attribute>
      </xsl:if>

      <xsl:if test="$rowsep.inherit &gt; 0">
        <xsl:call-template name="border">
          <xsl:with-param name="side" select="'bottom'"/>
        </xsl:call-template>
      </xsl:if>

      <xsl:if test="$colsep.inherit &gt; 0 and 
                      $col &lt; (ancestor::tgroup/@cols|ancestor::entrytbl/@cols)[last()]">
        <xsl:call-template name="border">
          <xsl:with-param name="side" select="'end'"/>
        </xsl:call-template>
      </xsl:if>

      <xsl:if test="$valign.inherit != ''">
        <xsl:attribute name="display-align">
          <xsl:choose>
            <xsl:when test="$valign.inherit='top'">before</xsl:when>
            <xsl:when test="$valign.inherit='middle'">center</xsl:when>
            <xsl:when test="$valign.inherit='bottom'">after</xsl:when>
            <xsl:otherwise>
              <xsl:message>
                <xsl:text>Unexpected valign value: </xsl:text>
                <xsl:value-of select="$valign.inherit"/>
                <xsl:text>, center used.</xsl:text>
              </xsl:message>
              <xsl:text>center</xsl:text>
            </xsl:otherwise>
          </xsl:choose>
        </xsl:attribute>
      </xsl:if>

      <xsl:choose>
        <xsl:when test="$align.inherit = 'char' and $char.inherit != ''">
          <xsl:attribute name="text-align">
            <xsl:value-of select="$char.inherit"/>
          </xsl:attribute>
        </xsl:when>
        <xsl:when test="$align.inherit != ''">
          <xsl:attribute name="text-align">
            <xsl:value-of select="$align.inherit"/>
          </xsl:attribute>
        </xsl:when>
      </xsl:choose>

    </xsl:when>
    <xsl:otherwise>
      <!-- HTML table -->
      <xsl:if test="$bgcolor.pi != ''">
        <xsl:attribute name="background-color">
          <xsl:value-of select="$bgcolor.pi"/>
        </xsl:attribute>
      </xsl:if>

      <xsl:if test="$align.inherit != ''">
        <xsl:attribute name="text-align">
          <xsl:value-of select="$align.inherit"/>
        </xsl:attribute>
      </xsl:if>

      <xsl:if test="$valign.inherit != ''">
        <xsl:attribute name="display-align">
          <xsl:choose>
            <xsl:when test="$valign.inherit='top'">before</xsl:when>
            <xsl:when test="$valign.inherit='middle'">center</xsl:when>
            <xsl:when test="$valign.inherit='bottom'">after</xsl:when>
            <xsl:otherwise>
              <xsl:message>
                <xsl:text>Unexpected valign value: </xsl:text>
                <xsl:value-of select="$valign.inherit"/>
                <xsl:text>, center used.</xsl:text>
              </xsl:message>
              <xsl:text>center</xsl:text>
            </xsl:otherwise>
          </xsl:choose>
        </xsl:attribute>
      </xsl:if>

      <xsl:call-template name="html.table.cell.rules"/>

    </xsl:otherwise>
  </xsl:choose>

</xsl:template>

<!-- Expand this template to add properties to any cell's block -->
<xsl:template name="table.cell.block.properties">
  <!-- highlight this entry? -->
  <xsl:choose>
    <xsl:when test="ancestor::thead or ancestor::tfoot">
      <xsl:attribute name="font-weight">bold</xsl:attribute>
    </xsl:when>
    <!-- Make row headers bold too -->
    <xsl:when test="ancestor::tbody and 
                    (ancestor::table[@rowheader = 'firstcol'] or
                    ancestor::informaltable[@rowheader = 'firstcol']) and
                    ancestor-or-self::entry[1][count(preceding-sibling::entry) = 0]">
      <xsl:attribute name="font-weight">bold</xsl:attribute>
    </xsl:when>
  </xsl:choose>
</xsl:template>

<xsl:template match="entry|entrytbl" name="sentry" mode="span">
  <xsl:param name="col" select="1"/>
  <xsl:param name="spans"/>

  <xsl:variable name="entry.colnum">
    <xsl:call-template name="entry.colnum"/>
  </xsl:variable>

  <xsl:variable name="entry.colspan">
    <xsl:choose>
      <xsl:when test="@spanname or @namest">
        <xsl:call-template name="calculate.colspan"/>
      </xsl:when>
      <xsl:otherwise>1</xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:variable name="following.spans">
    <xsl:call-template name="calculate.following.spans">
      <xsl:with-param name="colspan" select="$entry.colspan"/>
      <xsl:with-param name="spans" select="$spans"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:choose>
    <xsl:when test="$spans != '' and not(starts-with($spans,'0:'))">
      <xsl:value-of select="substring-before($spans,':')-1"/>
      <xsl:text>:</xsl:text>
      <xsl:call-template name="sentry">
        <xsl:with-param name="col" select="$col+1"/>
        <xsl:with-param name="spans" select="substring-after($spans,':')"/>
      </xsl:call-template>
    </xsl:when>

    <xsl:when test="number($entry.colnum) &gt; $col">
      <xsl:text>0:</xsl:text>
      <xsl:call-template name="sentry">
        <xsl:with-param name="col" select="$col + 1"/>
        <xsl:with-param name="spans" select="substring-after($spans,':')"/>
      </xsl:call-template>
    </xsl:when>

    <xsl:otherwise>
      <xsl:call-template name="copy-string">
        <xsl:with-param name="count" select="$entry.colspan"/>
        <xsl:with-param name="string">
          <xsl:choose>
            <xsl:when test="@morerows">
              <xsl:value-of select="@morerows"/>
            </xsl:when>
            <xsl:otherwise>0</xsl:otherwise>
          </xsl:choose>
          <xsl:text>:</xsl:text>
        </xsl:with-param>
      </xsl:call-template>

      <xsl:choose>
        <xsl:when test="following-sibling::entry|following-sibling::entrytbl">
          <xsl:apply-templates select="(following-sibling::entry
                                       |following-sibling::entrytbl)[1]"
                               mode="span">
            <xsl:with-param name="col" select="$col+$entry.colspan"/>
            <xsl:with-param name="spans" select="$following.spans"/>
          </xsl:apply-templates>
        </xsl:when>
        <xsl:otherwise>
          <xsl:call-template name="sfinaltd">
            <xsl:with-param name="spans" select="$following.spans"/>
          </xsl:call-template>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template name="generate.colgroup.raw">
  <xsl:param name="cols" select="1"/>
  <xsl:param name="count" select="1"/>

  <xsl:choose>
    <xsl:when test="$count>$cols"></xsl:when>
    <xsl:otherwise>
      <xsl:call-template name="generate.col.raw">
        <xsl:with-param name="countcol" select="$count"/>
      </xsl:call-template>
      <xsl:call-template name="generate.colgroup.raw">
        <xsl:with-param name="cols" select="$cols"/>
        <xsl:with-param name="count" select="$count+1"/>
      </xsl:call-template>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template name="generate.colgroup">
  <xsl:param name="cols" select="1"/>
  <xsl:param name="count" select="1"/>

  <xsl:choose>
    <xsl:when test="$count>$cols"></xsl:when>
    <xsl:otherwise>
      <xsl:call-template name="generate.col">
        <xsl:with-param name="countcol" select="$count"/>
      </xsl:call-template>
      <xsl:call-template name="generate.colgroup">
        <xsl:with-param name="cols" select="$cols"/>
        <xsl:with-param name="count" select="$count+1"/>
      </xsl:call-template>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template name="generate.col.raw">
  <!-- generate the table-column for column countcol -->
  <xsl:param name="countcol">1</xsl:param>
  <xsl:param name="colspecs" select="./colspec"/>
  <xsl:param name="count">1</xsl:param>
  <xsl:param name="colnum">1</xsl:param>

  <xsl:choose>
    <xsl:when test="$count>count($colspecs)">
      <fo:table-column column-number="{$countcol}"/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:variable name="colspec" select="$colspecs[$count=position()]"/>

      <xsl:variable name="colspec.colnum">
        <xsl:choose>
          <xsl:when test="$colspec/@colnum">
            <xsl:value-of select="$colspec/@colnum"/>
          </xsl:when>
          <xsl:otherwise>
            <xsl:value-of select="$colnum"/>
          </xsl:otherwise>
        </xsl:choose>
      </xsl:variable>

      <xsl:variable name="colspec.colwidth">
        <xsl:choose>
          <xsl:when test="normalize-space($colspec/@colwidth)='*'">1*</xsl:when>
          <xsl:when test="$colspec/@colwidth">
            <xsl:value-of select="$colspec/@colwidth"/>
          </xsl:when>
          <xsl:otherwise>1*</xsl:otherwise>
        </xsl:choose>
      </xsl:variable>

      <xsl:choose>
        <xsl:when test="$colspec.colnum=$countcol">
          <fo:table-column column-number="{$countcol}">
            <xsl:attribute name="column-width">
              <xsl:value-of select="$colspec.colwidth"/>
            </xsl:attribute>
          </fo:table-column>
        </xsl:when>
        <xsl:otherwise>
          <xsl:call-template name="generate.col.raw">
            <xsl:with-param name="countcol" select="$countcol"/>
            <xsl:with-param name="colspecs" select="$colspecs"/>
            <xsl:with-param name="count" select="$count+1"/>
            <xsl:with-param name="colnum">
              <xsl:choose>
                <xsl:when test="$colspec/@colnum">
                  <xsl:value-of select="$colspec/@colnum + 1"/>
                </xsl:when>
                <xsl:otherwise>
                  <xsl:value-of select="$colnum + 1"/>
                </xsl:otherwise>
              </xsl:choose>
            </xsl:with-param>
           </xsl:call-template>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template name="generate.col">
  <!-- generate the table-column for column countcol -->
  <xsl:param name="countcol">1</xsl:param>
  <xsl:param name="colspecs" select="./colspec"/>
  <xsl:param name="count">1</xsl:param>
  <xsl:param name="colnum">1</xsl:param>

  <xsl:choose>
    <xsl:when test="$count>count($colspecs)">
      <fo:table-column column-number="{$countcol}">
        <xsl:variable name="colwidth">
          <xsl:call-template name="calc.column.width"/>
        </xsl:variable>
          <xsl:attribute name="column-width">
            <xsl:value-of select="$colwidth"/>
          </xsl:attribute>
      </fo:table-column>
    </xsl:when>
    <xsl:otherwise>
      <xsl:variable name="colspec" select="$colspecs[$count=position()]"/>

      <xsl:variable name="colspec.colnum">
        <xsl:choose>
          <xsl:when test="$colspec/@colnum">
            <xsl:value-of select="$colspec/@colnum"/>
          </xsl:when>
          <xsl:otherwise>
            <xsl:value-of select="$colnum"/>
          </xsl:otherwise>
        </xsl:choose>
      </xsl:variable>

      <xsl:variable name="colspec.colwidth">
        <xsl:choose>
          <xsl:when test="$colspec/@colwidth">
            <xsl:value-of select="$colspec/@colwidth"/>
          </xsl:when>
          <xsl:otherwise>1*</xsl:otherwise>
        </xsl:choose>
      </xsl:variable>

      <xsl:choose>
        <xsl:when test="$colspec.colnum=$countcol">
          <fo:table-column column-number="{$countcol}">
            <xsl:variable name="colwidth">
              <xsl:call-template name="calc.column.width">
                <xsl:with-param name="colwidth">
                  <xsl:value-of select="$colspec.colwidth"/>
                </xsl:with-param>
              </xsl:call-template>
            </xsl:variable>
              <xsl:attribute name="column-width">
                <xsl:value-of select="$colwidth"/>
              </xsl:attribute>
          </fo:table-column>
        </xsl:when>
        <xsl:otherwise>
          <xsl:call-template name="generate.col">
            <xsl:with-param name="countcol" select="$countcol"/>
            <xsl:with-param name="colspecs" select="$colspecs"/>
            <xsl:with-param name="count" select="$count+1"/>
            <xsl:with-param name="colnum">
              <xsl:choose>
                <xsl:when test="$colspec/@colnum">
                  <xsl:value-of select="$colspec/@colnum + 1"/>
                </xsl:when>
                <xsl:otherwise>
                  <xsl:value-of select="$colnum + 1"/>
                </xsl:otherwise>
              </xsl:choose>
            </xsl:with-param>
           </xsl:call-template>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<doc:template name="calc.column.width" xmlns="">
<refpurpose>Calculate an XSL FO table column width specification from a
CALS table column width specification.</refpurpose>

<refdescription>
<para>CALS expresses table column widths in the following basic
forms:</para>

<itemizedlist>
<listitem>
<para><emphasis>99.99units</emphasis>, a fixed length specifier.</para>
</listitem>
<listitem>
<para><emphasis>99.99</emphasis>, a fixed length specifier without any units.</para>
</listitem>
<listitem>
<para><emphasis>99.99*</emphasis>, a relative length specifier.</para>
</listitem>
<listitem>
<para><emphasis>99.99*+99.99units</emphasis>, a combination of both.</para>
</listitem>
</itemizedlist>

<para>The CALS units are points (pt), picas (pi), centimeters (cm),
millimeters (mm), and inches (in). These are the same units as XSL,
except that XSL abbreviates picas "pc" instead of "pi". If a length
specifier has no units, the CALS default unit (pt) is assumed.</para>

<para>Relative length specifiers are represented in XSL with the
proportional-column-width() function.</para>

<para>Here are some examples:</para>

<itemizedlist>
<listitem>
<para>"36pt" becomes "36pt"</para>
</listitem>
<listitem>
<para>"3pi" becomes "3pc"</para>
</listitem>
<listitem>
<para>"36" becomes "36pt"</para>
</listitem>
<listitem>
<para>"3*" becomes "proportional-column-width(3)"</para>
</listitem>
<listitem>
<para>"3*+2pi" becomes "proportional-column-width(3)+2pc"</para>
</listitem>
<listitem>
<para>"1*+2" becomes "proportional-column-width(1)+2pt"</para>
</listitem>
</itemizedlist>
</refdescription>

<refparameter>
<variablelist>
<varlistentry><term>colwidth</term>
<listitem>
<para>The CALS column width specification.</para>
</listitem>
</varlistentry>
</variablelist>
</refparameter>

<refreturn>
<para>The XSL column width specification.</para>
</refreturn>
</doc:template>

<xsl:template name="calc.column.width">
  <xsl:param name="colwidth">1*</xsl:param>

  <!-- Ok, the colwidth could have any one of the following forms: -->
  <!--        1*       = proportional width -->
  <!--         *       = same as 1* -->
  <!--     1unit       = 1.0 units wide -->
  <!--         1       = 1pt wide -->
  <!--  1*+1unit       = proportional width + some fixed width -->
  <!--      1*+1       = proportional width + some fixed width -->

  <!-- If it has a proportional width, translate it to XSL -->
  <xsl:if test="contains($colwidth, '*')">
    <xsl:text>proportional-column-width(</xsl:text>
    <xsl:choose>
      <xsl:when test="substring-before($colwidth, '*') != ''"> 
        <xsl:value-of select="substring-before($colwidth, '*')"/>
      </xsl:when>
      <xsl:otherwise>
         <xsl:text>1.00</xsl:text>
      </xsl:otherwise>
    </xsl:choose>
    <xsl:text>)</xsl:text>
  </xsl:if>

  <!-- Now grab the non-proportional part of the specification -->
  <xsl:variable name="width-units">
    <xsl:choose>
      <xsl:when test="contains($colwidth, '*')">
        <xsl:value-of
             select="normalize-space(substring-after($colwidth, '*'))"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:value-of select="normalize-space($colwidth)"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <!-- Ok, now the width-units could have any one of the following forms: -->
  <!--                 = <empty string> -->
  <!--     1unit       = 1.0 units wide -->
  <!--         1       = 1pt wide -->
  <!-- with an optional leading sign -->

  <!-- Grab the width part by blanking out the units part and discarding -->
  <!-- whitespace. It's not pretty, but it works. -->
  <xsl:variable name="width"
       select="normalize-space(translate($width-units,
                                         '+-0123456789.abcdefghijklmnopqrstuvwxyz',
                                         '+-0123456789.'))"/>

  <!-- Grab the units part by blanking out the width part and discarding -->
  <!-- whitespace. It's not pretty, but it works. -->
  <xsl:variable name="units"
       select="normalize-space(translate($width-units,
                                         'abcdefghijklmnopqrstuvwxyz+-0123456789.',
                                         'abcdefghijklmnopqrstuvwxyz'))"/>

  <!-- Output the width -->
  <xsl:value-of select="$width"/>

  <!-- Output the units, translated appropriately -->
  <xsl:choose>
    <xsl:when test="$units = 'pi'">pc</xsl:when>
    <xsl:when test="$units = '' and $width != ''">pt</xsl:when>
    <xsl:otherwise><xsl:value-of select="$units"/></xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="table/caption">
  <fo:block xsl:use-attribute-sets="table.caption.properties">
    <xsl:apply-templates/>
  </fo:block>
</xsl:template>

<!-- ==================================================================== -->

</xsl:stylesheet>
