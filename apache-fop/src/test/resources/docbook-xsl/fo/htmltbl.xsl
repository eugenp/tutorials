<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fo="http://www.w3.org/1999/XSL/Format"
                version="1.0">

<!-- ********************************************************************
     $Id: htmltbl.xsl 9647 2012-10-26 17:42:03Z bobstayton $
     ********************************************************************

     This file is part of the XSL DocBook Stylesheet distribution.
     See ../README or http://docbook.sf.net/release/xsl/current/ for
     copyright and other information.

     ******************************************************************** -->

<!-- ==================================================================== -->

<!-- Outputs an fo:table only, not the caption -->
<xsl:template match="table|informaltable" mode="htmlTable">

  <xsl:variable name="numcols">
    <xsl:call-template name="widest-html-row">
      <xsl:with-param name="rows" select=".//tr"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:variable name="prop-columns"
                select=".//col[contains(@width, '%')] |
                        .//colgroup[contains(@width, '%')]"/>

  <xsl:variable name="table.width">
    <xsl:call-template name="table.width"/>
  </xsl:variable>

  <fo:table xsl:use-attribute-sets="table.table.properties">
    <xsl:choose>
      <xsl:when test="$fop.extensions != 0 or
                      $fop1.extensions != 0">
        <xsl:attribute name="table-layout">fixed</xsl:attribute>
      </xsl:when>
    </xsl:choose>

    <xsl:attribute name="width">
      <xsl:choose>
        <xsl:when test="@width">
          <xsl:value-of select="@width"/>
        </xsl:when>
        <xsl:when test="$table.width">
          <xsl:value-of select="$table.width"/>
        </xsl:when>
        <xsl:otherwise>100%</xsl:otherwise>
      </xsl:choose>
    </xsl:attribute>

    <xsl:call-template name="table.frame">
      <xsl:with-param name="frame">
        <xsl:choose>
          <xsl:when test="@frame = 'box'">all</xsl:when>
          <xsl:when test="@frame = 'border'">all</xsl:when>
          <xsl:when test="@frame = 'below'">bottom</xsl:when>
          <xsl:when test="@frame = 'above'">top</xsl:when>
          <xsl:when test="@frame = 'hsides'">topbot</xsl:when>
          <xsl:when test="@frame = 'vsides'">sides</xsl:when>
          <xsl:when test="@frame = 'lhs'">lhs</xsl:when>
          <xsl:when test="@frame = 'rhs'">rhs</xsl:when>
          <xsl:when test="@frame = 'void'">none</xsl:when>
          <xsl:when test="@border != '' and @border != 0">all</xsl:when>
          <xsl:when test="@border != '' and @border = 0">none</xsl:when>
          <xsl:when test="@frame != ''">
            <xsl:value-of select="@frame"/>
          </xsl:when>
          <xsl:when test="$default.table.frame != ''">
            <xsl:value-of select="$default.table.frame"/>
          </xsl:when>
          <xsl:otherwise>all</xsl:otherwise>
        </xsl:choose>
      </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="make-html-table-columns">
      <xsl:with-param name="count" select="$numcols"/>
    </xsl:call-template>

    <xsl:apply-templates select="thead" mode="htmlTable"/>
    <xsl:apply-templates select="tfoot" mode="htmlTable"/>
    <xsl:choose>
      <xsl:when test="tbody">
        <xsl:apply-templates select="tbody" mode="htmlTable"/>
      </xsl:when>
      <xsl:otherwise>
        <fo:table-body start-indent="0pt" end-indent="0pt">
          <xsl:apply-templates select="tr" mode="htmlTable"/>
        </fo:table-body>
      </xsl:otherwise>
    </xsl:choose>
  </fo:table>

</xsl:template>

<!-- This template writes rowsep or colsep equivalant for html tables -->
<xsl:template name="html.table.cell.rules">
  <xsl:variable name="border" 
                select="(ancestor::table |
                         ancestor::informaltable)[last()]/@border"/>
  <xsl:variable name="table.rules"
                select="(ancestor::table |
                         ancestor::informaltable)[last()]/@rules"/>

  <xsl:variable name="rules">
    <xsl:choose>
      <xsl:when test="$table.rules != ''">
        <xsl:value-of select="$table.rules"/>
      </xsl:when>
      <xsl:when test="$default.table.rules != ''">
        <xsl:value-of select="$default.table.rules"/>
      </xsl:when>
    </xsl:choose>
  </xsl:variable>

  <xsl:choose>
    <xsl:when test="$border != '' and $border != 0">
      <xsl:attribute name="border">
        <xsl:value-of select="$table.cell.border.thickness"/>
        <xsl:text> </xsl:text>
        <xsl:value-of select="$table.cell.border.style"/>
        <xsl:text> </xsl:text>
        <xsl:value-of select="$table.cell.border.color"/>
      </xsl:attribute>
    </xsl:when>
    <xsl:when test="$rules = 'none'">
      <xsl:attribute name="border-start-style">none</xsl:attribute>
      <xsl:attribute name="border-end-style">none</xsl:attribute>
      <xsl:attribute name="border-top-style">none</xsl:attribute>
      <xsl:attribute name="border-bottom-style">none</xsl:attribute>
    </xsl:when>

    <xsl:when test="$rules = 'cols' and following-sibling::*">
      <!-- If not the last column, add border after -->
      <xsl:attribute name="border-start-style">none</xsl:attribute>
      <xsl:attribute name="border-top-style">none</xsl:attribute>
      <xsl:attribute name="border-bottom-style">none</xsl:attribute>
      <xsl:attribute name="border-end-style">
        <xsl:value-of select="$table.frame.border.style"/>
      </xsl:attribute>
      <xsl:attribute name="border-end-width">
        <xsl:value-of select="$table.frame.border.thickness"/>
      </xsl:attribute>
      <xsl:attribute name="border-end-color">
        <xsl:value-of select="$table.frame.border.color"/>
      </xsl:attribute>
    </xsl:when>
    <!-- If not the last row, add border below -->
    <xsl:when test="$rules = 'rows'">
      <xsl:variable name="rowborder">
        <xsl:choose>
          <!-- If in thead and tbody has rows, add border -->
          <xsl:when test="parent::tr/parent::thead/
                          following-sibling::tbody/tr">1</xsl:when>
          <!-- If in tbody and tfoot has rows, add border -->
          <xsl:when test="parent::tr/parent::tbody/
                          following-sibling::tfoot/tr">1</xsl:when>
          <xsl:when test="parent::tr/parent::tbody/
                          preceding-sibling::tfoot/tr">1</xsl:when>
          <!-- If following rows, but not rowspan reaches last row -->
          <xsl:when test="parent::tr/following-sibling::tr and
             not(@rowspan = count(parent::tr/following-sibling::tr) + 1)">1</xsl:when>
          <xsl:otherwise>0</xsl:otherwise>
        </xsl:choose>
      </xsl:variable>

      <xsl:if test="$rowborder = 1">
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
      </xsl:if>
    </xsl:when>
    <xsl:when test="$rules = 'all'">
      <xsl:attribute name="border-start-style">none</xsl:attribute>
      <xsl:attribute name="border-top-style">none</xsl:attribute>

      <xsl:variable name="rowborder">
        <xsl:choose>
          <!-- If in thead and tbody has rows, add border -->
          <xsl:when test="parent::tr/parent::thead/
                          following-sibling::tbody/tr">1</xsl:when>
          <!-- If in tbody and tfoot has rows, add border -->
          <xsl:when test="parent::tr/parent::tbody/
                          following-sibling::tfoot/tr">1</xsl:when>
          <xsl:when test="parent::tr/parent::tbody/
                          preceding-sibling::tfoot/tr">1</xsl:when>
          <!-- If following rows, but not rowspan reaches last row -->
          <xsl:when test="parent::tr/following-sibling::tr and
             not(@rowspan = count(parent::tr/following-sibling::tr) + 1)">1</xsl:when>
          <xsl:otherwise>0</xsl:otherwise>
        </xsl:choose>
      </xsl:variable>

      <xsl:if test="$rowborder = 1">
        <xsl:attribute name="border-bottom-style">
          <xsl:value-of select="$table.frame.border.style"/>
        </xsl:attribute>
        <xsl:attribute name="border-bottom-width">
          <xsl:value-of select="$table.frame.border.thickness"/>
        </xsl:attribute>
        <xsl:attribute name="border-bottom-color">
          <xsl:value-of select="$table.frame.border.color"/>
        </xsl:attribute>
      </xsl:if>

      <xsl:if test="following-sibling::*">
        <!-- If not the last column, add border after -->
        <xsl:attribute name="border-end-style">
          <xsl:value-of select="$table.frame.border.style"/>
        </xsl:attribute>
        <xsl:attribute name="border-end-width">
          <xsl:value-of select="$table.frame.border.thickness"/>
        </xsl:attribute>
        <xsl:attribute name="border-end-color">
          <xsl:value-of select="$table.frame.border.color"/>
        </xsl:attribute>
      </xsl:if>
    </xsl:when>
    <xsl:when test="$rules = 'groups' and ancestor::thead 
                    and not(parent::tr/following-sibling::tr)">
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
    <xsl:when test="$rules = 'groups' and ancestor::tfoot 
                    and not(parent::tr/preceding-sibling::tr)">
      <xsl:attribute name="border-start-style">none</xsl:attribute>
      <xsl:attribute name="border-end-style">none</xsl:attribute>
      <xsl:attribute name="border-top-style">none</xsl:attribute>
      <xsl:attribute name="border-top-style">
        <xsl:value-of select="$table.frame.border.style"/>
      </xsl:attribute>
      <xsl:attribute name="border-top-width">
        <xsl:value-of select="$table.frame.border.thickness"/>
      </xsl:attribute>
      <xsl:attribute name="border-top-color">
        <xsl:value-of select="$table.frame.border.color"/>
      </xsl:attribute>
    </xsl:when>
  </xsl:choose>
</xsl:template>

<xsl:template match="caption" mode="htmlTable">
  <!-- Handled by formal.object.heading -->
</xsl:template>

<xsl:template name="widest-html-row">
  <xsl:param name="rows" select="''"/>
  <xsl:param name="count" select="0"/>
  <xsl:choose>
    <xsl:when test="count($rows) = 0">
      <xsl:value-of select="$count"/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:choose>
        <xsl:when test="$count &gt; count($rows[1]/*)">
          <xsl:call-template name="widest-html-row">
            <xsl:with-param name="rows" select="$rows[position() &gt; 1]"/>
            <xsl:with-param name="count" select="$count"/>
          </xsl:call-template>
        </xsl:when>
        <xsl:otherwise>
          <xsl:call-template name="widest-html-row">
            <xsl:with-param name="rows" select="$rows[position() &gt; 1]"/>
            <xsl:with-param name="count" select="count($rows[1]/*)"/>
          </xsl:call-template>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template name="make-html-table-columns">
  <xsl:param name="count" select="0"/>
  <xsl:param name="number" select="1"/>

  <xsl:choose>
    <xsl:when test="col|colgroup/col">
      <xsl:for-each select="col|colgroup/col">
        <fo:table-column>
          <xsl:attribute name="column-number">
            <xsl:number from="table|informaltable" level="any" format="1"/>
          </xsl:attribute>
          <xsl:if test="@width">
            <xsl:attribute name="column-width">
              <xsl:choose>
                <xsl:when test="$fop.extensions != 0 and 
                                contains(@width, '%')">
                  <xsl:value-of select="concat('proportional-column-width(',
                                               substring-before(@width, '%'),
                                               ')')"/>
                </xsl:when>
                <xsl:otherwise>
                  <xsl:value-of select="@width"/>
                </xsl:otherwise>
              </xsl:choose>
            </xsl:attribute>
          </xsl:if>
        </fo:table-column>
      </xsl:for-each>
    </xsl:when>
    <xsl:when test="$fop.extensions != 0">
      <xsl:if test="$number &lt;= $count">
        <fo:table-column column-number="{$number}"
                         column-width="{6.5 div $count}in"/>
        <xsl:call-template name="make-html-table-columns">
          <xsl:with-param name="count" select="$count"/>
          <xsl:with-param name="number" select="$number + 1"/>
        </xsl:call-template>
      </xsl:if>
    </xsl:when>
  </xsl:choose>
</xsl:template>

<xsl:template match="tbody" mode="htmlTable">
  <fo:table-body start-indent="0pt"
                 end-indent="0pt">
    <xsl:apply-templates mode="htmlTable"/>
  </fo:table-body>
</xsl:template>

<xsl:template match="tfoot" mode="htmlTable">
  <fo:table-footer start-indent="0pt"
                   end-indent="0pt">
    <xsl:apply-templates mode="htmlTable"/>
  </fo:table-footer>
</xsl:template>

<xsl:template match="th|td" mode="htmlTable">
  <xsl:variable name="bgcolor.pi">
    <xsl:call-template name="pi.dbfo_bgcolor"/>
  </xsl:variable>

  <xsl:variable name="bgcolor">
    <xsl:choose>
      <xsl:when test="$bgcolor.pi != ''">
        <xsl:value-of select="$bgcolor.pi"/>
      </xsl:when>
      <xsl:when test="string-length(@bgcolor) != 0">
        <xsl:value-of select="@bgcolor"/>
      </xsl:when>
    </xsl:choose>
  </xsl:variable>

  <xsl:variable name="align">
    <xsl:call-template name="inherited.table.attribute">
      <xsl:with-param name="entry" select="."/>
      <xsl:with-param name="row" select="parent::tr"/>
      <xsl:with-param name="attribute" select="'align'"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:variable name="valign">
    <xsl:call-template name="inherited.table.attribute">
      <xsl:with-param name="entry" select="."/>
      <xsl:with-param name="row" select="parent::tr"/>
      <xsl:with-param name="attribute" select="'valign'"/>
    </xsl:call-template>
  </xsl:variable>

  <fo:table-cell xsl:use-attribute-sets="table.cell.padding">
    <xsl:call-template name="table.cell.properties">
      <xsl:with-param name="bgcolor.pi" select="$bgcolor"/>
      <xsl:with-param name="rowsep.inherit" select="0"/>
      <xsl:with-param name="align.inherit" select="$align"/>
      <xsl:with-param name="valign.inherit" select="$valign"/>
      <xsl:with-param name="colsep.inherit" select="0"/>
    </xsl:call-template>

    <xsl:if test="@colspan &gt; 1">
      <xsl:attribute name="number-columns-spanned">
        <xsl:value-of select="@colspan"/>
      </xsl:attribute>
    </xsl:if>

    <xsl:if test="@rowspan &gt; 1">
      <xsl:attribute name="number-rows-spanned">
        <xsl:value-of select="@rowspan"/>
      </xsl:attribute>
    </xsl:if>

    <fo:block>
      <xsl:call-template name="table.cell.block.properties"/>
      <xsl:apply-templates/>
    </fo:block>
  </fo:table-cell>
</xsl:template>

<xsl:template match="thead" mode="htmlTable">
  <fo:table-header start-indent="0pt"
                   end-indent="0pt">
    <xsl:apply-templates mode="htmlTable"/>
  </fo:table-header>
</xsl:template>

<xsl:template match="tr" mode="htmlTable">
  <fo:table-row>
    <xsl:call-template name="table.row.properties"/>
    <xsl:apply-templates mode="htmlTable"/>
  </fo:table-row>
</xsl:template>

</xsl:stylesheet>
