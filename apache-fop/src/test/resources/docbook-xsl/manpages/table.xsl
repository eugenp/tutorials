<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:exsl="http://exslt.org/common"
                exclude-result-prefixes="exsl"
                version='1.0'>

  <!-- ********************************************************************
       $Id: table.xsl 8400 2009-04-08 07:44:54Z bobstayton $
       ********************************************************************

       This file is part of the XSL DocBook Stylesheet distribution.
       See ../README or http://docbook.sf.net/release/xsl/current/ for
       copyright and other information.

       ******************************************************************** -->
  <!--
  <xsl:import href="http://docbook.sourceforge.net/release/xsl/current/html/docbook.xsl"/>
  <xsl:param name="tbl.font.title">B</xsl:param>
  <xsl:param name="tbl.font.headings">B</xsl:param>
  -->
  <xsl:param name="tbl.running.header.from.thead" select="0"/>
  <xsl:param name="tbl.column.separator.char">:</xsl:param>

  <!-- ==================================================================== -->

  <!-- * This stylesheet transforms DocBook and HTML table source into -->
  <!-- * tbl(1) markup. -->
  <!-- * -->
  <!-- * For details on tbl(1) and its markup syntaxt, see M. E. Lesk,-->
  <!-- * "Tbl - A Program to Format Tables": -->
  <!-- * -->
  <!-- *   http://cm.bell-labs.com/7thEdMan/vol2/tbl -->
  <!-- *   http://cm.bell-labs.com/cm/cs/doc/76/tbl.ps.gz -->
  <!-- *   http://www.snake.net/software/troffcvt/tbl.html -->

  <xsl:template match="table|informaltable" mode="to.tbl">
    <!--* the "source" param is an optional param; it can be any -->
    <!--* string you want to use that gives some indication of the -->
    <!--* source context for a table; it gets passed down to the named -->
    <!--* templates that do the actual table processing; this -->
    <!--* stylesheet currently uses the "source" information for -->
    <!--* logging purposes -->
    <xsl:param name="source"/>
    <xsl:param name="title">
      <xsl:if test="local-name(.) = 'table'">
        <xsl:apply-templates select="." mode="object.title.markup.textonly"/>
      </xsl:if>
    </xsl:param>
    <!-- * ============================================================== -->
    <!-- *    Set global table parameters                                 -->
    <!-- * ============================================================== -->
    <!-- * First, set a few parameters based on attributes specified in -->
    <!-- * the table source. -->
    <xsl:param name="allbox">
    <xsl:if test="not(@frame = 'none') and not(@border = '0')">
      <!-- * By default, put a box around table and between all cells, -->
      <!-- * unless frame="none" or border="0" -->
      <xsl:text>allbox </xsl:text>
    </xsl:if>
    </xsl:param>
    <xsl:param name="center">
    <!-- * If align="center", center the table. Otherwise, tbl(1) -->
    <!-- * left-aligns it by default; note that there is no support -->
    <!-- * in tbl(1) for specifying right alignment. -->
    <xsl:if test="@align = 'center' or tgroup/@align = 'center'">
      <xsl:text>center </xsl:text>
    </xsl:if>
    </xsl:param>
    <xsl:param name="expand">
    <!-- * If pgwide="1" or width="100%", then "expand" the table by -->
    <!-- * making it "as wide as the current line length" (to quote -->
    <!-- * the tbl(1) guide). -->
    <xsl:if test="@pgwide = '1' or @width = '100%'">
      <xsl:text>expand </xsl:text>
    </xsl:if>
    </xsl:param>

    <!-- * ============================================================== -->
    <!-- *    Convert table to HTML                                       -->
    <!-- * ============================================================== -->
    <!-- * Process the table by applying the HTML templates from the -->
    <!-- * DocBook XSL stylesheets to the whole thing; because we don't -->
    <!-- * override any of the <row>, <entry>, <tr>, <td>, etc. templates, -->
    <!-- * the templates in the HTML stylesheets (which we import) are -->
    <!-- * used to process those. -->
    <xsl:param name="html-table-output">
      <xsl:choose>
        <xsl:when test=".//tr">
          <!-- * If this table has a TR child, it means that it's an -->
          <!-- * HTML table in the DocBook source, instead of a CALS -->
          <!-- * table. So we just copy it as-is, while wrapping it -->
          <!-- * in an element with same name as its original parent. -->
          <xsl:for-each select="descendant-or-self::table|descendant-or-self::informaltable">
            <xsl:element name="{local-name(..)}">
              <table>
                <xsl:copy-of select="*"/>
              </table>
            </xsl:element>
          </xsl:for-each>
        </xsl:when>
        <xsl:otherwise>
          <!-- * Otherwise, this is a CALS table in the DocBook source, -->
          <!-- * so we need to apply the templates in the HTML -->
          <!-- * stylesheets to transform it into HTML before we do -->
          <!-- * any further processing of it. -->
          <xsl:apply-templates/>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:param>
    <xsl:param name="contents" select="exsl:node-set($html-table-output)"/>

    <!-- ==================================================================== -->
    <!-- *                       Output the table -->
    <!-- ==================================================================== -->
    <!-- * -->
    <!-- * This is the "driver" part of the code; it calls a series of named
         * templates (further below) to generate the actual tbl(1) markup, -->
    <!-- * including the optional "options line", required "format section", -->
    <!-- * and then the actual contents of the table. -->
    <!-- * -->
    <!-- ==================================================================== -->

    <xsl:for-each select="$contents//table">
      <!-- * ============================================================== -->
      <!-- *   Output table title                                           -->
      <!-- * ============================================================== -->
      <xsl:if test="$title != '' or parent::td">
        <xsl:text>.sp&#10;</xsl:text>
        <xsl:call-template name="pinch.together"/>
        <xsl:text>.</xsl:text>
        <xsl:value-of select="$tbl.font.title"/>
        <xsl:text> </xsl:text>
        <xsl:if test="parent::td">
          <xsl:text>*[nested&#x2580;table]</xsl:text>
        </xsl:if>
        <xsl:value-of select="normalize-space($title)"/>
        <xsl:text>&#10;</xsl:text>
      </xsl:if>
      
      <!-- * mark the start of the table -->
      <!-- * "TS" = "table start" -->
      <xsl:text>.TS</xsl:text>
      <xsl:if test="thead and $tbl.running.header.from.thead">
        <!-- * H = "has header" -->
        <xsl:text> H</xsl:text>
      </xsl:if>
      <xsl:text>&#10;</xsl:text>

      <!-- * ============================================================== -->
      <!-- *   Output "options line"                                         -->
      <!-- * ============================================================== -->
      <xsl:variable name="options-line">
        <xsl:value-of select="$allbox"/>
        <xsl:value-of select="$center"/>
        <xsl:value-of select="$expand"/>
        <xsl:text>tab(</xsl:text>
        <xsl:value-of select="$tbl.column.separator.char"/>
        <xsl:text>)</xsl:text>
      </xsl:variable>
      <xsl:if test="normalize-space($options-line) != ''">
        <xsl:value-of select="normalize-space($options-line)"/>
        <xsl:text>;&#10;</xsl:text>
      </xsl:if>

      <!-- * ============================================================== -->
      <!-- *   Output table header rows                                     -->
      <!-- * ============================================================== -->
      <xsl:if test="thead">
        <xsl:call-template name="output.rows">
          <xsl:with-param name="rows" select="thead/tr"/>
        </xsl:call-template> 
        <xsl:text>&#10;</xsl:text>

        <!-- * mark the end of table-header rows -->
        <xsl:choose>
          <xsl:when test="$tbl.running.header.from.thead">
            <!-- * "TH" = "table header end" -->
            <xsl:text>.TH&#10;</xsl:text>
          </xsl:when>
          <xsl:otherwise>
            <!-- * "T&" = "table continuation" and is meant just as a kind -->
            <!-- * of convenience macro and is sorta equivalent to a "TE" -->
            <!-- * (table end) followed immediately by a "TS" (table start); -->
            <!-- * in this case, it marks the end of a table "subsection" -->
            <!-- * with header rows, and the start of a subsection with body -->
            <!-- * rows. It's necessary to output it here because the "TH" -->
            <!-- * macro is not being output, so there's otherwise no way -->
            <!-- * for tbl(1) to know we have the table "sectioned". -->
            <xsl:text>.T&amp;&#10;</xsl:text>
          </xsl:otherwise>
        </xsl:choose>
      </xsl:if>
      
      <!-- * ============================================================== -->
      <!-- *  Output table body rows                                        -->
      <!-- * ============================================================== -->
      <!-- * First create node set with all non-thead rows (tbody+tfoot), -->
      <!-- * but reordered with the tfoot rows at the end of the node set -->
      <xsl:variable name="rows-set">
        <xsl:copy-of select="tbody/tr|tr"/>
        <xsl:copy-of select="tfoot/tr"/>
      </xsl:variable>
      <xsl:call-template name="output.rows">
        <xsl:with-param name="source" select="$source"/>
        <xsl:with-param name="rows" select="exsl:node-set($rows-set)"/>
      </xsl:call-template>

      <!-- * mark the end of the table -->
      <xsl:text>&#10;</xsl:text>
      <!-- * .TE = "Table End" -->
      <xsl:text>.TE&#10;</xsl:text>
      <!-- * put a blank line of space below the table -->
      <xsl:text>.sp 1&#10;</xsl:text>
    </xsl:for-each>
  </xsl:template>

  <!-- ==================================================================== -->
  <!-- *                        named templates -->
  <!-- ==================================================================== -->
  <!-- * -->
  <!-- * All of the following are named templates that get called directly -->
  <!-- * or indirectly by the main "driver" part of the code (above) -->
  <!-- * -->
  <!-- ==================================================================== -->
  
  <xsl:template name="output.rows">
    <xsl:param name="source"/>
    <xsl:param name="rows"/>
    <!-- * ============================================================== -->
    <!-- *   Flatten row set into simple list of cells                    -->
    <!-- * ============================================================== -->
    <!-- * Now we flatten the structure further into just a set of -->
    <!-- * cells without the row parents. This basically creates a -->
    <!-- * copy of the entire contents of the original table, but -->
    <!-- * restructured in such a way that we can more easily generate -->
    <!-- * the corresponding tbl(1) markup we need to output. -->
    <xsl:variable name="cells-list">
      <xsl:call-template name="build.cell.list">
        <xsl:with-param name="source" select="$source"/>
        <xsl:with-param name="rows" select="$rows"/>
      </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="cells" select="exsl:node-set($cells-list)"/>

    <!-- * Output the table "format section", which tells tbl(1) how to -->
    <!-- * format each row and column -->
    <xsl:call-template name="create.table.format">
      <xsl:with-param name="cells" select="$cells"/>
    </xsl:call-template>

    <!--* Output the formatted contents of each cell. -->
    <xsl:for-each select="$cells/cell">
      <xsl:call-template name="output.cell"/>
    </xsl:for-each>
  </xsl:template>

  <!-- * ============================================================== -->
  <!-- *    Output the tbl(1)-formatted contents of each cell.            -->
  <!-- * ============================================================== -->
  <xsl:template name="output.cell">
    <xsl:choose>
      <xsl:when test="preceding-sibling::cell[1]/@row != @row or
                      not(preceding-sibling::cell)">
        <!-- * If the value of the "row" attribute on this cell is -->
        <!-- * different from the value of that on the previous cell, it -->
        <!-- * means we have a new row. So output a line break (as long -->
        <!-- * as this isn't the first cell in the table) -->
        <xsl:text>&#10;</xsl:text>
      </xsl:when>
      <xsl:otherwise>
        <!-- * Otherwise we are not at the start of a new row, so we -->
        <!-- * output a tab character to delimit the contents of this -->
        <!-- * cell from the contents of the next one. -->
        <xsl:value-of select="$tbl.column.separator.char"/>
      </xsl:otherwise>
    </xsl:choose>
    <xsl:choose>
      <xsl:when test="@type = '^'">
        <!-- * If this is a dummy cell resulting from the presence of -->
        <!-- * rowpan attribute in the source, it has no contents, so -->
        <!-- * we need to handle it differently. -->
        <xsl:if test="@colspan and @colspan > 1">
          <!-- * If there is a colspan attribute on this dummy row, then -->
          <!-- * we need to output a tab character for each column that -->
          <!-- * it spans. -->
          <xsl:call-template name="copy-string">
            <xsl:with-param name="string" select="$tbl.column.separator.char"/>
            <xsl:with-param name="count">
              <xsl:value-of select="@colspan - 1"/>
            </xsl:with-param>
          </xsl:call-template>
        </xsl:if>
      </xsl:when>
      <xsl:otherwise>
        <!-- * Otherwise, we have a "real" cell (not a dummy one) with -->
        <!-- * contents that we need to output, -->
        <!-- * -->
        <!-- * The "T{" and "T}" stuff are delimiters to tell tbl(1) that -->
        <!-- * the delimited contents are "text blocks" that roff -->
        <!-- * needs to process -->
        <xsl:text>T{&#10;</xsl:text>
        <xsl:copy-of select="."/>
        <xsl:text>&#10;T}</xsl:text>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>

  <!-- * ============================================================== -->
  <!-- *   Build a restructured "cell list" copy of the entire table    -->
  <!-- * ============================================================== -->
  <xsl:template name="build.cell.list">
    <xsl:param name="source"/>
    <xsl:param name="rows"/>
    <xsl:param  name="cell-data-unsorted">
      <!-- * This param collects all the "real" cells from the table, -->
      <!-- * along with "dummy" rows that we generate for keeping -->
      <!-- * track of Rowspan instances. -->
      <xsl:apply-templates select="$rows" mode="cell.list">
        <xsl:with-param name="source" select="$source"/>
      </xsl:apply-templates>
    </xsl:param>
    <xsl:param  name="cell-data-sorted">
      <!-- * Sort the cells so that the dummy cells get put where we -->
      <!-- * need them in the structure. Note that we need to specify -->
      <!-- * data-type="number" here because the default sorting method -->
      <!-- * for xsl:sort is "text" (alphabetical). -->
      <xsl:for-each select="exsl:node-set($cell-data-unsorted)/cell">
        <xsl:sort select="@row" data-type="number"/>
        <xsl:sort select="@slot" data-type="number"/>
        <xsl:copy-of select="."/>
      </xsl:for-each>
    </xsl:param>
    <!-- * Return the sorted cell list -->
    <xsl:copy-of select="$cell-data-sorted"/>
  </xsl:template>

  <xsl:template match="tr" mode="cell.list">
    <xsl:param name="source"/>
    <xsl:variable name="row">
      <xsl:value-of select="count(preceding-sibling::tr) + 1"/>
    </xsl:variable>
    <xsl:for-each select="td|th">
      <xsl:call-template name="cell">
        <xsl:with-param name="source" select="$source"/>
        <xsl:with-param name="row" select="$row"/>
        <!-- * pass on the element name so we can select the appropriate -->
        <!-- * roff font for styling the cell contents -->
        <xsl:with-param name="class" select="name(.)"/>
      </xsl:call-template>
    </xsl:for-each>
  </xsl:template>

  <xsl:template name="cell">
    <xsl:param name="source"/>
    <xsl:param name="row"/>
    <xsl:param name="class"/>
    <xsl:param name="slot">
      <!-- * The "slot" is the horizontal position of this cell (usually -->
      <!-- * just the same as its column, but not so when it is preceded -->
      <!-- * by cells that have colspans or cells in preceding rows that -->
      <!-- * that have rowspans). -->
      <xsl:value-of select="position()"/>
    </xsl:param>
    <!-- * For each real TD cell, create a Cell instance; contents will -->
    <!-- * be the roff-formatted contents of its original table cell. -->
    <cell type=""
          row="{$row}"
          slot="{$slot}"
          class="{$class}"
          colspan="{@colspan}"
          align="{@align}"
          valign="{@valign}"
          >
      <xsl:choose>
        <xsl:when test=".//tr">
          <xsl:call-template name="log.message">
            <xsl:with-param name="level">Warn</xsl:with-param>
            <xsl:with-param name="source" select="$source"/>
            <xsl:with-param name="context-desc">tbl convert</xsl:with-param>
            <xsl:with-param name="message">
              <xsl:text>Extracted a nested table</xsl:text>
            </xsl:with-param>
          </xsl:call-template>
          <xsl:text>[\fInested&#x2580;table\fR]*&#10;</xsl:text>
        </xsl:when>
        <xsl:otherwise>
          <!-- * Apply templates to the child contents of this cell, to -->
          <!-- * transform them into marked-up roff. -->
          <xsl:variable name="contents">
            <xsl:apply-templates/>
          </xsl:variable>
          <!-- * We now have the contents in roff (plain-text) form, -->
          <!-- * but we may also still have unnecessary whitespace at -->
          <!-- * the beginning and/or end of it, so trim it off. -->
          <xsl:call-template name="trim.text">
            <xsl:with-param name="contents" select="$contents"/>
          </xsl:call-template>
        </xsl:otherwise>
      </xsl:choose>
    </cell>

    <!-- * For each instance of a rowspan attribute found, we create N -->
    <!-- * dummy cells, where N is equal to the value of the rowspan. -->
    <xsl:if test="@rowspan and @rowspan > 0">
      <!-- * If this cell is preceded in the same row by cells that -->
      <!-- * have colspan attributes, then we need to calculate the -->
      <!-- * "offset" caused by those colspan instances; the formula -->
      <!-- * is to (1) check for all the preceding cells that have -->
      <!-- * colspan attributes that are not empty and which have a -->
      <!-- * value greater than 1, then (2) take the sum of the values -->
      <!-- * of all those colspan attributes, and subtract from that -->
      <!-- * the number of such colspan instances found. -->
      <xsl:variable name="colspan-offset">
        <xsl:value-of
            select="sum(preceding-sibling::td[@colspan != ''
                    and @colspan > 1]/@colspan) -
                    count(preceding-sibling::td[@colspan != ''
                    and @colspan > 1]/@colspan)"/>
      </xsl:variable>
      <xsl:call-template name="create.dummy.cells">
        <xsl:with-param name="row" select="$row + 1"/>
        <!-- * The slot value on each dummy cell must be offset by the -->
        <!-- * value of $colspan-offset to adjust for preceding colpans -->
        <xsl:with-param name="slot" select="$slot + $colspan-offset"/>
        <xsl:with-param name="colspan" select="@colspan"/>
        <xsl:with-param name="rowspan" select="@rowspan"/>
      </xsl:call-template>
    </xsl:if>
  </xsl:template>

  <xsl:template name="create.dummy.cells">
    <xsl:param name="row"/>
    <xsl:param name="slot"/>
    <xsl:param name="colspan"/>
    <xsl:param name="rowspan"/>
    <xsl:choose>
      <xsl:when test="$rowspan > 1">
        <!-- * Tail recurse until we have no more rowspans, creating -->
        <!-- * an empty dummy cell each time. The type value, '^' -->
        <!-- * is the marker that tbl(1) uses to indicate a -->
        <!-- * "vertically spanned heading". -->
        <cell row="{$row}" slot="{$slot}" type="^" colspan="{@colspan}"/>
        <xsl:call-template name="create.dummy.cells">
          <xsl:with-param name="row" select="$row + 1"/>
          <xsl:with-param name="slot" select="$slot"/>
          <xsl:with-param name="colspan" select="$colspan"/>
          <xsl:with-param name="rowspan" select="$rowspan - 1"/>
        </xsl:call-template>
      </xsl:when>
    </xsl:choose>
  </xsl:template>

  <!-- * ============================================================== -->
  <!-- *    Build the "format section" for the table                    -->
  <!-- * ============================================================== -->
  <!-- * Description from the tbl(1) guide: -->
  <!-- * -->
  <!-- * "The format section of the table specifies the layout of the -->
  <!-- * columns.  Each line in this section corresponds to one line of -->
  <!-- * the table... and each line contains a key-letter for each -->
  <!-- * column of the table." -->
  <xsl:template name="create.table.format">
    <xsl:param name="cells"/>
    <xsl:apply-templates mode="table.format" select="$cells"/>
    <!-- * last line of table format section must end with a dot -->
    <xsl:text>.</xsl:text>
  </xsl:template>

  <xsl:template match="cell" mode="table.format">
    <xsl:choose>
      <xsl:when test="preceding-sibling::cell[1]/@row != @row">
        <!-- * If the value of the row attribute on this cell is -->
        <!-- * different from the value of that on the previous cell, it -->
        <!-- * means we have a new row. So output a line break. -->
        <xsl:text>&#xa;</xsl:text>
      </xsl:when>
      <xsl:otherwise>
        <!-- * If this isn't the first cell, output a space before it to -->
        <!-- * separate it from the preceding key letter. -->
        <xsl:if test="position() != 1">
          <xsl:text> </xsl:text>
        </xsl:if>
      </xsl:otherwise>
    </xsl:choose>
    <!-- * Select an appropriate "alignment" key letter based on this -->
    <!-- * cell's attributes. -->
    <xsl:choose>
      <xsl:when test="@type = '^'">
        <xsl:text>^</xsl:text>
      </xsl:when>
      <xsl:when test="@align = 'center'">
        <xsl:text>c</xsl:text>
      </xsl:when>
      <xsl:when test="@align = 'right'">
        <xsl:text>r</xsl:text>
      </xsl:when>
      <xsl:when test="@align = 'char'">
        <xsl:text>n</xsl:text>
      </xsl:when>
      <xsl:otherwise>
        <!-- * Default to left alignment. -->
        <xsl:text>l</xsl:text>
      </xsl:otherwise>
    </xsl:choose>
    <!-- * By default, tbl(1) vertically centers cell contents within -->
    <!-- * their cells; the "t" key latter tells it to top-align the -->
    <!-- * contents instead. Note that tbl(1) has no options for -->
    <!-- * bottom or baseline alignment. -->
    <xsl:if test="@valign = 'top'">
      <xsl:text>t</xsl:text>
    </xsl:if>
    <xsl:if test="@class = 'th'">
      <!-- * If this is a heading row, generate a font indicator (B or I), -->
      <!-- * or if the value of $tbl.font.headings is empty, nothing. -->
      <xsl:value-of select="$tbl.font.headings"/>
    </xsl:if>
    <!-- * We only need to deal with colspans whose value is greater -->
    <!-- * than one (a colspan="1" is the same as having no colspan -->
    <!-- * attribute at all). -->
    <xsl:if test="@colspan > 1">
      <xsl:call-template name="process.colspan">
        <xsl:with-param name="colspan" select="@colspan - 1"/>
        <xsl:with-param name="type" select="@type"/>
      </xsl:call-template>
    </xsl:if>
  </xsl:template>
  
  <xsl:template name="process.colspan">
    <xsl:param name="colspan"/>
    <xsl:param name="type"/>
    <!-- * Output a space to separate this key letter from preceding one. -->
    <xsl:text> </xsl:text>
    <xsl:choose>
      <xsl:when test="$type = '^'">
        <!-- * A '^' ("vertically spanned heading" marker) indicates -->
        <!-- * that the "parent" of this spanned cell is a dummy cell; -->
        <!-- * in this case, we need to generate a '^' instead of the -->
        <!-- * normal 's'. -->
        <xsl:text>^</xsl:text>
      </xsl:when>
      <xsl:otherwise>
        <!-- * s = 'spanned heading' -->
        <xsl:text>s</xsl:text>
      </xsl:otherwise>
    </xsl:choose>
    <xsl:if test="$colspan > 1">
      <!-- * Tail recurse until we have no more colspans, outputting -->
      <!-- * another marker each time. -->
      <xsl:call-template name="process.colspan">
        <xsl:with-param name="colspan" select="$colspan - 1"/>
        <xsl:with-param name="type" select="$type"/>
      </xsl:call-template>
    </xsl:if>
  </xsl:template>

  <!-- * ============================================================== -->
  <!-- *    colgroup and col                                            -->
  <!-- * ============================================================== -->
  <!-- * We currently don't do anything with colgroup. Not sure if it -->
  <!-- * is widely used enough to bother adding support for it -->
  <xsl:template match="colgroup"/>
  <xsl:template match="col"/>

  <!-- * ============================================================== -->
  <!-- *    table footnotes                                      -->
  <!-- * ============================================================== -->
  <xsl:template match="footnote" mode="table.footnote.mode">
    <xsl:variable name="footnotes" select=".//footnote"/>
    <xsl:variable name="table.footnotes"
                  select=".//tgroup//footnote"/>
    <xsl:value-of select="$man.table.footnotes.divider"/>
    <xsl:text>&#10;</xsl:text>
    <xsl:text>.br&#10;</xsl:text>
    <xsl:apply-templates select="*[1]" mode="footnote.body.number"/>
    <xsl:apply-templates select="*[position() &gt; 1]"/>
  </xsl:template>

  <!-- * The following template for footnote.body.number mode was just -->
  <!-- * lifted from the HTML stylesheets with some minor adjustments -->
  <xsl:template match="*"  mode="footnote.body.number">
    <xsl:variable name="name">
      <xsl:text>ftn.</xsl:text>
      <xsl:call-template name="object.id">
        <xsl:with-param name="object" select="ancestor::footnote"/>
      </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="href">
      <xsl:text>#</xsl:text>
      <xsl:call-template name="object.id">
        <xsl:with-param name="object" select="ancestor::footnote"/>
      </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="footnote.mark">
      <xsl:text>[</xsl:text>
      <xsl:apply-templates select="ancestor::footnote"
                           mode="footnote.number"/>
      <xsl:text>]&#10;</xsl:text>
    </xsl:variable>
    <xsl:variable name="html">
      <xsl:apply-templates select="."/>
    </xsl:variable>
    <xsl:choose>
      <xsl:when test="$exsl.node.set.available != 0">
        <xsl:variable name="html-nodes" select="exsl:node-set($html)"/>
        <xsl:choose>
          <xsl:when test="$html-nodes//p">
            <xsl:apply-templates select="$html-nodes" mode="insert.html.p">
              <xsl:with-param name="mark" select="$footnote.mark"/>
            </xsl:apply-templates>
          </xsl:when>
          <xsl:otherwise>
            <xsl:apply-templates select="$html-nodes" mode="insert.html.text">
              <xsl:with-param name="mark" select="$footnote.mark"/>
            </xsl:apply-templates>
          </xsl:otherwise>
        </xsl:choose>
      </xsl:when>
      <xsl:otherwise>
        <xsl:copy-of select="$html"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>

  <!-- * The HTML stylesheets output <sup><a>...</a></sup> around -->
  <!-- * footnote markers in tables -->
  <xsl:template match="th/sup">
    <xsl:apply-templates/>
  </xsl:template>
  <xsl:template match="a">
    <xsl:apply-templates/>
  </xsl:template>

</xsl:stylesheet>
