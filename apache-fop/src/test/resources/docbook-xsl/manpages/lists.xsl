<?xml version='1.0'?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                version='1.0'>

<!-- ********************************************************************
     $Id: lists.xsl 9684 2012-12-12 17:05:54Z bobstayton $
     ********************************************************************

     This file is part of the XSL DocBook Stylesheet distribution.
     See ../README or http://docbook.sf.net/release/xsl/current/ for
     copyright and other information.

     ******************************************************************** -->

<xsl:variable name="list-indent">
  <xsl:choose>
    <xsl:when test="not($man.indent.lists = 0)">
      <xsl:value-of select="$man.indent.width"/>
    </xsl:when>
    <xsl:when test="not($man.indent.refsect = 0)">
      <!-- * "zq" is the name of a register we set for -->
      <!-- * preserving the original default indent value -->
      <!-- * when $man.indent.refsect is non-zero; -->
      <!-- * "u" is a roff unit specifier -->
      <xsl:text>\n(zqu</xsl:text>
    </xsl:when>
    <xsl:otherwise/> <!-- * otherwise, just leave it empty -->
  </xsl:choose>
</xsl:variable>

<!-- ================================================================== -->

<xsl:template match="para[ancestor::listitem or ancestor::step or ancestor::glossdef]|
  simpara[ancestor::listitem or ancestor::step or ancestor::glossdef]|
  remark[ancestor::listitem or ancestor::step or ancestor::glossdef]">
  <xsl:call-template name="mixed-block"/>
  <xsl:text>&#10;</xsl:text>
  <xsl:if test="following-sibling::*[1][
    self::para or
    self::simpara or
    self::remark
    ]">
    <!-- * Make sure multiple paragraphs within a list item don't -->
    <!-- * merge together.                                        -->
    <xsl:text>.sp&#10;</xsl:text>
  </xsl:if>
</xsl:template>

<xsl:template match="bibliolist">
  <xsl:apply-templates/>
  <xsl:text>&#10;</xsl:text>
</xsl:template>

<xsl:template match="variablelist|glosslist">
  <xsl:text>&#10;</xsl:text>
  <xsl:if test="title">
    <xsl:text>.PP&#10;</xsl:text>
    <xsl:call-template name="bold">
      <xsl:with-param name="node" select="title"/>
      <xsl:with-param name="context" select="."/>
    </xsl:call-template>
    <xsl:text>&#10;</xsl:text>
  </xsl:if>
  <xsl:apply-templates/>
</xsl:template>

<xsl:template match="varlistentry|glossentry">
  <xsl:text>.PP&#10;</xsl:text> 
  <xsl:for-each select="term|glossterm">
    <xsl:variable name="content">
      <xsl:apply-templates/>
    </xsl:variable>
    <xsl:value-of select="normalize-space($content)"/>
    <xsl:choose>
      <xsl:when test="position() = last()"/> <!-- do nothing -->
      <xsl:otherwise>
        <!-- * if we have multiple terms in the same varlistentry, generate -->
        <!-- * a separator (", " by default) and/or an additional line -->
        <!-- * break after each one except the last -->
        <!-- * -->
        <!-- * note that it is not valid to have multiple glossterms -->
        <!-- * within a glossentry, so this logic never gets exercised -->
        <!-- * for glossterms (every glossterm is always the last in -->
        <!-- * its parent glossentry) -->
        <xsl:value-of select="$variablelist.term.separator"/>
        <xsl:if test="not($variablelist.term.break.after = '0')">
          <xsl:text>&#10;</xsl:text>
          <xsl:text>.br&#10;</xsl:text>
        </xsl:if>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:for-each>
  <xsl:text>&#10;</xsl:text>
  <xsl:text>.RS</xsl:text> 
  <xsl:if test="not($list-indent = '')">
    <xsl:text> </xsl:text>
    <xsl:value-of select="$list-indent"/>
  </xsl:if>
  <xsl:text>&#10;</xsl:text>
  <xsl:apply-templates/>
  <xsl:text>.RE&#10;</xsl:text>
</xsl:template>

<xsl:template match="varlistentry/term"/>
<xsl:template match="glossentry/glossterm"/>

<xsl:template match="variablelist[ancestor::listitem or ancestor::step or ancestor::glossdef]|
  glosslist[ancestor::listitem or ancestor::step or ancestor::glossdef]">
  <xsl:apply-templates/>
  <xsl:if test="following-sibling::node() or
    parent::para[following-sibling::node()] or
    parent::simpara[following-sibling::node()] or
    parent::remark[following-sibling::node()]">
    <xsl:text>.sp</xsl:text> 
    <xsl:text>&#10;</xsl:text>
  </xsl:if>
</xsl:template>

<xsl:template match="varlistentry/listitem|glossentry/glossdef">
  <xsl:apply-templates/>
</xsl:template>

<xsl:template match="itemizedlist/listitem">
  <!-- * We output a real bullet here (rather than, "\(bu", -->
  <!-- * the roff bullet) because, when we do character-map -->
  <!-- * processing before final output, the character-map will -->
  <!-- * handle conversion of the &#x2022; to "\(bu" for us -->
  <xsl:text>&#10;</xsl:text>
  <xsl:text>.sp</xsl:text>
  <xsl:text>&#10;</xsl:text>
  <xsl:text>.RS</xsl:text>
  <xsl:if test="not($list-indent = '')">
    <xsl:text> </xsl:text>
    <xsl:value-of select="$list-indent"/>
  </xsl:if>
  <xsl:text>&#10;</xsl:text>
  <!-- * if "n" then we are using "nroff", which means the output is for -->
  <!-- * TTY; so we do some fixed-width-font hackery with \h to make a -->
  <!-- * hanging indent (instead of using .IP, which has some -->
  <!-- * undesirable side effects under certain circumstances) -->
  <xsl:call-template name="roff-if-else-start"/>
  <xsl:text>\h'-</xsl:text>
  <xsl:choose>
    <xsl:when test="not($list-indent = '')">
      <xsl:text>0</xsl:text>
      <xsl:value-of select="$list-indent"/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:text>\n(INu</xsl:text>
    </xsl:otherwise>
  </xsl:choose>
  <xsl:text>'</xsl:text>
  <xsl:text>&#x2022;</xsl:text>
  <xsl:text>\h'+</xsl:text>
  <xsl:choose>
    <xsl:when test="not($list-indent = '')">
      <xsl:text>0</xsl:text>
      <xsl:value-of select="$list-indent - 1"/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:text>\n(INu-1</xsl:text>
    </xsl:otherwise>
  </xsl:choose>
  <xsl:text>'\c&#10;</xsl:text>
  <!-- * else, we are not using for "nroff", but instead "troff" - which -->
  <!-- * means not for TTY, but for PS or whatever; so we’re not using a -->
  <!-- * fixed-width font, so use a real .IP instead -->
  <xsl:call-template name="roff-else"/>
  <!-- * .IP generates a blank like of space, so let’s go backwards one -->
  <!-- * line up to compensate for that -->
  <xsl:text>.sp -1&#10;</xsl:text>
  <xsl:text>.IP \(bu 2.3&#10;</xsl:text>
  <!-- * The value 2.3 is the amount of indentation; we use 2.3 instead -->
  <!-- * of 2 because when the font family is New Century Schoolbook it -->
  <!-- * seems to require the extra space. -->
  <xsl:call-template name="roff-if-end"/>
  <xsl:apply-templates/>
  <xsl:text>.RE&#10;</xsl:text>
</xsl:template>

<xsl:template match="orderedlist/listitem/title|
                     procedure/step/title">
  <xsl:call-template name="bold">
    <xsl:with-param name="node" select="."/>
    <xsl:with-param name="context" select=".."/>
  </xsl:call-template>
  <xsl:text>&#10;</xsl:text>
  <xsl:text>.PP&#10;</xsl:text>
</xsl:template>

<xsl:template match="orderedlist/listitem|procedure/step">
  <xsl:text>&#10;</xsl:text>
  <xsl:text>.sp</xsl:text>
  <xsl:text>&#10;</xsl:text>
  <xsl:text>.RS</xsl:text>
  <xsl:if test="not($list-indent = '')">
    <xsl:text> </xsl:text>
    <xsl:value-of select="$list-indent"/>
  </xsl:if>
  <xsl:text>&#10;</xsl:text>
  <!-- * if "n" then we are using "nroff", which means the output is for -->
  <!-- * TTY; so we do some fixed-width-font hackery with \h to make a -->
  <!-- * hanging indents (instead of using .IP, which has some -->
  <!-- * undesirable side effects under certain circumstances) -->
  <xsl:call-template name="roff-if-else-start"/>
  <xsl:text>\h'-</xsl:text>
  <xsl:choose>
    <xsl:when test="not($list-indent = '')">
      <xsl:text>0</xsl:text>
      <xsl:value-of select="$list-indent"/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:text>\n(INu+3n</xsl:text>
    </xsl:otherwise>
  </xsl:choose>
  <xsl:text>'</xsl:text>
  <xsl:if test="count(preceding-sibling::listitem) &lt; 9">
    <xsl:text> </xsl:text>
  </xsl:if>
  <xsl:number format="1."/>
  <xsl:text>\h'+</xsl:text>
  <xsl:choose>
    <xsl:when test="not($list-indent = '')">
      <xsl:text>0</xsl:text>
      <xsl:value-of select="$list-indent - 3"/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:text>1n</xsl:text>
    </xsl:otherwise>
  </xsl:choose>
  <xsl:text>'\c&#10;</xsl:text>
  <!-- * else, we are not using for "nroff", but instead "troff" - which -->
  <!-- * means not for TTY, but for PS or whatever; so we’re not using a -->
  <!-- * fixed-width font, so use a real .IP instead -->
  <xsl:call-template name="roff-else"/>
  <!-- * .IP generates a blank line of space, so let’s go backwards one -->
  <!-- * line up to compensate for that -->
  <xsl:text>.sp -1&#10;</xsl:text>
  <xsl:text>.IP "</xsl:text>
  <xsl:if test="count(preceding-sibling::listitem) &lt; 9">
    <xsl:text>  </xsl:text>
  </xsl:if>
  <xsl:number format="1."/>
  <xsl:text>" 4.2&#10;</xsl:text>
  <!-- * The value 4.2 is the amount of indentation; we use 4.2 instead -->
  <!-- * of 4 because when the font family is Bookman it seems to require -->
  <!-- * the extra space. -->
  <xsl:call-template name="roff-if-end"/>
  <xsl:apply-templates/>
  <xsl:text>.RE&#10;</xsl:text>
  <xsl:text>&#10;</xsl:text>
</xsl:template>

<xsl:template match="itemizedlist|orderedlist|procedure">
  <xsl:if test="title">
    <xsl:text>.PP&#10;</xsl:text>
    <xsl:call-template name="bold">
      <xsl:with-param name="node" select="title"/>
      <xsl:with-param name="context" select="."/>
    </xsl:call-template>
    <xsl:text>&#10;</xsl:text>
  </xsl:if>
  <!-- * DocBook allows just about any block content to appear in -->
  <!-- * lists before the actual list items, so we need to get that -->
  <!-- * content (if any) before getting the list items -->
  <xsl:apply-templates
    select="*[not(self::listitem) and not(self::title)]"/>
  <xsl:apply-templates select="listitem"/>
  <!-- * If this list is a child of para and has content following -->
  <!-- * it, within the same para, then add a blank line and move -->
  <!-- * the left margin back to where it was -->
  <xsl:if test="parent::para and following-sibling::node()">
    <xsl:text>.sp</xsl:text>
    <xsl:text>&#10;</xsl:text>
  </xsl:if>
</xsl:template>

<xsl:template match="itemizedlist[ancestor::listitem or ancestor::step  or ancestor::glossdef]|
  orderedlist[ancestor::listitem or ancestor::step or ancestor::glossdef]|
  procedure[ancestor::listitem or ancestor::step or ancestor::glossdef]">
  <xsl:if test="title">
    <xsl:text>.PP&#10;</xsl:text>
    <xsl:call-template name="bold">
      <xsl:with-param name="node" select="title"/>
      <xsl:with-param name="context" select="."/>
    </xsl:call-template>
    <xsl:text>&#10;</xsl:text>
  </xsl:if>
  <xsl:apply-templates/>
  <xsl:if test="following-sibling::node() or
    parent::para[following-sibling::node()] or
    parent::simpara[following-sibling::node()] or
    parent::remark[following-sibling::node()]">
    <xsl:text>.sp</xsl:text> 
    <xsl:text>&#10;</xsl:text>
  </xsl:if>
</xsl:template>

<!-- ================================================================== -->
  
<!-- * for simplelist type="inline", render it as a comma-separated list -->
<xsl:template match="simplelist[@type='inline']">
  <!-- * if dbchoice PI exists, use that to determine the choice separator -->
  <!-- * (that is, equivalent of "and" or "or" in current locale), or literal -->
  <!-- * value of "choice" otherwise -->
  <xsl:variable name="localized-choice-separator">
    <xsl:choose>
      <xsl:when test="processing-instruction('dbchoice')">
        <xsl:call-template name="select.choice.separator"/>
      </xsl:when>
      <xsl:otherwise>
        <!-- * empty -->
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:for-each select="member">
    <xsl:apply-templates/>
    <xsl:choose>
      <xsl:when test="position() = last()"/> <!-- do nothing -->
      <xsl:otherwise>
        <xsl:text>, </xsl:text>
        <xsl:if test="position() = last() - 1">
          <xsl:if test="$localized-choice-separator != ''">
            <xsl:value-of select="$localized-choice-separator"/>
            <xsl:text> </xsl:text>
          </xsl:if>
        </xsl:if>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:for-each>
  <xsl:text>&#10;</xsl:text>
</xsl:template>

<!-- * if simplelist type is not inline, render it as a one-column vertical -->
<!-- * list (ignoring the values of the type and columns attributes) -->
<xsl:template match="simplelist">
  <xsl:for-each select="member">
    <xsl:text>.RS</xsl:text> 
    <xsl:if test="not($list-indent = '')">
      <xsl:text> </xsl:text>
      <xsl:value-of select="$list-indent"/>
    </xsl:if>
    <xsl:text>&#10;</xsl:text>
    <xsl:apply-templates/>
    <xsl:text>&#10;</xsl:text>
    <xsl:text>.RE&#10;</xsl:text>
  </xsl:for-each>
</xsl:template>

<!-- ================================================================== -->

<!-- * We output Segmentedlist as a table, using tbl(1) markup. There -->
<!-- * is no option for outputting it in manpages in "list" form. -->
<xsl:template match="segmentedlist">
  <xsl:if test="title">
    <xsl:text>.PP&#10;</xsl:text>
    <xsl:call-template name="bold">
      <xsl:with-param name="node" select="title"/>
      <xsl:with-param name="context" select="."/>
    </xsl:call-template>
    <xsl:text>&#10;</xsl:text>
  </xsl:if>
  <xsl:text>.\" line length increase to cope w/ tbl weirdness&#10;</xsl:text>
  <xsl:text>.ll +(\n(LLu * 62u / 100u)&#10;</xsl:text>
  <!-- * .TS = "Table Start" -->
  <xsl:text>.TS&#10;</xsl:text>
  <!-- * first output the table "format" spec, which tells tbl(1) how -->
  <!-- * how to format each row and column. -->
  <xsl:for-each select=".//segtitle">
    <!-- * l = "left", which hard-codes left-alignment for tabular -->
    <!-- * output of all segmentedlist content -->
    <xsl:text>l</xsl:text>
  </xsl:for-each>
  <!-- * last line of table format section must end with a dot -->
  <xsl:text>.&#10;</xsl:text>
  <!-- * optionally suppress output of segtitle -->
  <xsl:choose>
    <xsl:when test="$man.segtitle.suppress != 0">
      <!-- * non-zero = "suppress", so do nothing -->
    </xsl:when>
    <xsl:otherwise>
      <!-- * "0" = "do not suppress", so output the segtitle(s) -->
      <xsl:apply-templates select=".//segtitle" mode="table-title"/>
      <xsl:text>&#10;</xsl:text>
    </xsl:otherwise>
  </xsl:choose>
  <xsl:apply-templates/>
  <!-- * .TE = "Table End" -->
  <xsl:text>.TE&#10;</xsl:text>
  <xsl:text>.\" line length decrease back to previous value&#10;</xsl:text>
  <xsl:text>.ll -(\n(LLu * 62u / 100u)&#10;</xsl:text>
  <!-- * put a blank line of space below the table -->
  <xsl:text>.sp&#10;</xsl:text>
</xsl:template>

<xsl:template match="segmentedlist/segtitle" mode="table-title">
  <xsl:call-template name="italic">
    <xsl:with-param name="node" select="."/>
    <xsl:with-param name="context" select="."/>
  </xsl:call-template>
  <xsl:choose>
    <xsl:when test="position() = last()"/> <!-- do nothing -->
    <xsl:otherwise>
      <!-- * tbl(1) treats tab characters as delimiters between -->
      <!-- * cells; so we need to output a tab after each -->
      <!-- * segtitle except the last one -->
      <xsl:text>&#09;</xsl:text>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="segmentedlist/seglistitem">
  <xsl:apply-templates/>
  <xsl:text>&#10;</xsl:text>
</xsl:template>

<xsl:template match="segmentedlist/seglistitem/seg">
  <!-- * the T{ and T} stuff are delimiters to tell tbl(1) that -->
  <!-- * the delimited contents are "text blocks" that groff(1) -->
  <!-- * needs to process -->
  <xsl:text>T{&#10;</xsl:text>
  <xsl:variable name="contents">
    <xsl:apply-templates/>
  </xsl:variable>
  <xsl:value-of select="normalize-space($contents)"/>
  <xsl:text>&#10;T}</xsl:text>
  <xsl:choose>
    <xsl:when test="position() = last()"/> <!-- do nothing -->
    <xsl:otherwise>
      <!-- * tbl(1) treats tab characters as delimiters between -->
      <!-- * cells; so we need to output a tab after each -->
      <!-- * segtitle except the last one -->
      <xsl:text>&#09;</xsl:text>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<!-- ==================================================================== -->

<xsl:template match="calloutlist">
  <xsl:if test="title|info/title">
    <xsl:call-template name="formal.object.heading"/>
  </xsl:if>
  <!-- * This template was originally copied over from the HTML -->
  <!-- * calloutlist template, which precedes the following -->
  <!-- * apply-templates with the comment "Preserve order of PIs and -->
  <!-- * comments"; I'm not certain that it will actually have that -->
  <!-- * effect for all cases, and it seems like there is probably a -->
  <!-- * better way to do it, but anyway, I’m preserving it here for -->
  <!-- * consistency. -->
  <xsl:apply-templates 
    select="*[not(self::callout or self::title or self::titleabbrev)]
    |comment()[not(preceding-sibling::callout)]
    |processing-instruction()[not(preceding-sibling::callout)]"/>
  <!-- * put callout list into a table -->
  <xsl:text>.TS&#10;</xsl:text>
  <xsl:text>tab(:);&#10;</xsl:text>
  <!-- * the following defines the row layout for the table: two columns, -->
  <!-- * with the first cell in each row right-aligned, and the second -->
  <!-- * cell left aligned with a width of 75% of the line length -->
  <xsl:text>r lw(\n(.lu*75u/100u).&#10;</xsl:text>
  <xsl:apply-templates select="callout
    |comment()[preceding-sibling::callout]
    |processing-instruction()[preceding-sibling::callout]"/>
  <xsl:text>.TE&#10;</xsl:text>
</xsl:template>

<xsl:template match="calloutlist/title"/>

<xsl:template match="callout">
  <!-- * first cell of each row is the set of callout numbers for this -->
  <!-- * particular callout -->
  <xsl:call-template name="callout.arearefs">
    <xsl:with-param name="arearefs" select="@arearefs"/>
  </xsl:call-template>
  <!-- * end of the first cell in the row; the \h hackery is to correct -->
  <!-- * for the excessive horizontal whitespace that tbl(1) adds between -->
  <!-- * cells in the same row -->
  <xsl:text>\h'-2n':</xsl:text>
  <!-- * start the next cell in the row, which has the prose contents -->
  <!-- * (description/explanation) for the callout -->
  <xsl:text>T{&#10;</xsl:text>
  <xsl:apply-templates/>
  <xsl:text>T}&#10;</xsl:text>
  <!-- * end of the last cell and end of the row -->
</xsl:template>

<xsl:template name="callout.arearefs">
  <xsl:param name="arearefs"></xsl:param>
  <!-- * callout can have multiple values in its arearefs attribute, so -->
  <!-- * we use the position param to track the postion of each value -->
  <xsl:param name="position">1</xsl:param>
  <xsl:if test="$arearefs!=''">
    <xsl:choose>
      <xsl:when test="substring-before($arearefs,' ')=''">
        <xsl:call-template name="callout.arearef">
          <xsl:with-param name="arearef" select="$arearefs"/>
          <xsl:with-param name="position" select="$position"/>
        </xsl:call-template>
      </xsl:when>
      <xsl:otherwise>
        <xsl:call-template name="callout.arearef">
          <xsl:with-param name="arearef"
            select="substring-before($arearefs,' ')"/>
          <xsl:with-param name="position" select="$position"/>
        </xsl:call-template>
      </xsl:otherwise>
    </xsl:choose>
    <xsl:call-template name="callout.arearefs">
      <xsl:with-param name="arearefs"
        select="substring-after($arearefs,' ')"/>
      <xsl:with-param name="position" select="$position + 1"/>
    </xsl:call-template>
  </xsl:if>
</xsl:template>

<xsl:template name="callout.arearef">
  <xsl:param name="arearef"></xsl:param>
  <!-- * callout can have multiple values in its arearefs attribute, so -->
  <!-- * we use the position param to track the postion of each value -->
  <xsl:param name="position"></xsl:param>
  <xsl:variable name="targets" select="key('id',$arearef)"/>
  <xsl:variable name="target" select="$targets[1]"/>

  <xsl:call-template name="check.id.unique">
    <xsl:with-param name="linkend" select="$arearef"/>
  </xsl:call-template>

  <xsl:choose>
    <xsl:when test="count($target)=0">
      <xsl:text>???</xsl:text>
    </xsl:when>
    <xsl:when test="local-name($target)='co'">
      <!-- * if this is not the first value in the set of values in the -->
      <!-- * arearef attribute for this callout, then we prepend a groff -->
      <!-- * non-breaking space to it, to prevent groff from injecting -->
      <!-- * linebreaks into the output. For callout instances with -->
      <!-- * multiple values in their arearefs attributes, that results -->
      <!-- * in all of callout numbers beings listed on the same line. -->
      <xsl:if test="not($position = 1)">
        <xsl:text>\ </xsl:text>
      </xsl:if>
      <xsl:apply-templates select="$target"
        mode="calloutlist-callout-number"/>
    </xsl:when>
    <!-- * the manpages stylesheet does not really support areaset and -->
    <!-- * area (because we can't/don't actually render the callout bugs -->
    <!-- * at the specified coordinates); however, the following (for -->
    <!-- * what it's worth) might cause the callout numbers in the -->
    <!-- * calloutlist to be render at least (then again, maybe it won't; -->
    <!-- * it's not actually been tested... -->
    <xsl:when test="local-name($target)='areaset'">
      <xsl:call-template name="callout-bug">
        <xsl:with-param name="conum">
          <xsl:apply-templates select="$target" mode="conumber"/>
        </xsl:with-param>
      </xsl:call-template>
    </xsl:when>
    <xsl:when test="local-name($target)='area'">
      <xsl:choose>
        <xsl:when test="$target/parent::areaset">
          <xsl:call-template name="callout-bug">
            <xsl:with-param name="conum">
              <xsl:apply-templates
                select="$target/parent::areaset" mode="conumber"/>
            </xsl:with-param>
          </xsl:call-template>
        </xsl:when>
        <xsl:otherwise>
          <xsl:call-template name="callout-bug">
            <xsl:with-param name="conum">
              <xsl:apply-templates select="$target"
                mode="conumber"/>
            </xsl:with-param>
          </xsl:call-template>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:when>
    <xsl:otherwise>
      <xsl:text>???</xsl:text>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<!-- * we bold the actual callout bugs and put -->
<!-- * parenthesis around them -->
<xsl:template name="callout-bug">
  <xsl:param name="conum" select='1'/>
  <xsl:text>\fB(</xsl:text>
  <xsl:value-of select="$conum"/>
  <xsl:text>)\fR</xsl:text>
</xsl:template>

<!-- * we bold the callout numbers and follow each -->
<!-- * with a period -->
<xsl:template name="calloutlist-callout-number">
  <xsl:param name="conum" select='1'/>
  <xsl:text>\fB</xsl:text>
  <xsl:value-of select="$conum"/>
  <xsl:text>.\fR</xsl:text>
</xsl:template>

<xsl:template match="co" mode="calloutlist-callout-number">
  <xsl:call-template name="calloutlist-callout-number">
    <xsl:with-param name="conum">
      <xsl:number count="co"
        level="any"
        from="programlisting|screen|literallayout|synopsis"
        format="1"/>
    </xsl:with-param>
  </xsl:call-template>
</xsl:template>

</xsl:stylesheet>
